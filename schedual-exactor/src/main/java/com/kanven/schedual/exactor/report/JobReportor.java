package com.kanven.schedual.exactor.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.exactor.quartz.JobStatus;
import com.kanven.schedual.exactor.quartz.JobStatus.Status;
import com.kanven.schedual.network.protoc.RequestProto.TaskReportor;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.transport.client.api.Transform;
import com.kanven.schedual.transport.client.command.AbstractReportCommand;

public class JobReportor implements JobNotify {

	private static final Logger log = LoggerFactory.getLogger(JobReportor.class);

	private Clustor<JobStatus> clustor;

	public void notify(JobStatus status) {
		try {
			Response response = clustor.send(new ReportCommand(status), transform);
			if (response.getStatus() == Status.FAILURE.getStatus()) {
				log.error(status.getId() + "任务结果反馈处理失败！");
			}
			// TODO
		} catch (Exception e) {
			log.error(status.getId() + "任务结果反馈失败！", e);
		}
	}

	public void setClustor(Clustor<JobStatus> clustor) {
		this.clustor = clustor;
	}

	private static class ReportCommand extends AbstractReportCommand<JobStatus> {

		public ReportCommand(JobStatus value) {
			super(value);
		}

		@Override
		public TaskReportor buildReport(JobStatus status) {
			TaskReportor.Builder tb = TaskReportor.newBuilder();
			tb.setId(status.getId());
			tb.setStatus(status.getStatus().getStatus());
			tb.setStartTime(status.getStartTime().getTime());
			tb.setEndTime(status.getEndTime().getTime());
			tb.setMsg(status.getResult());
			return tb.build();
		}

	}

	private Transform transform = new Transform() {
		@SuppressWarnings("unchecked")
		public <T> T transformResponse(Response response) {
			return (T) response;
		}
	};

}
