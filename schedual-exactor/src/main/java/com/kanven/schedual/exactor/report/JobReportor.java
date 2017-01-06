package com.kanven.schedual.exactor.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.exactor.quartz.JobStatus;
import com.kanven.schedual.exactor.quartz.JobStatus.Status;
import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.TaskReportor;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.transport.client.api.Transform;

public class JobReportor implements JobNotify {

	private static final Logger log = LoggerFactory.getLogger(JobReportor.class);

	private Clustor<TaskReportor> clustor;

	private Transform<TaskReportor> transform = new Transform<TaskReportor>() {

		public Request transformRequest(TaskReportor reportor) {
			Request.Builder rb = Request.newBuilder();
			rb.setRequestId("");
			rb.setType(MessageType.TASK_REPORT);
			rb.setReportor(reportor);
			return rb.build();
		}

		@SuppressWarnings("unchecked")
		public <T> T transformResponse(Response response) {
			return (T) response;
		}

	};

	public void notify(JobStatus status) {
		TaskReportor.Builder tb = TaskReportor.newBuilder();
		tb.setId(status.getId());
		tb.setStatus(status.getStatus().getStatus());
		tb.setStartTime(status.getStartTime().getTime());
		tb.setEndTime(status.getEndTime().getTime());
		tb.setMsg(status.getResult());
		try {
			Response response = clustor.send(tb.build(), transform);
			if (response.getStatus() == Status.FAILURE.getStatus()) {
				log.error(status.getId() + "任务结果反馈处理失败！");
			}
			//TODO
		} catch (Exception e) {
			log.error(status.getId() + "任务结果反馈失败！", e);
		}
	}

	public void setClustor(Clustor<TaskReportor> clustor) {
		this.clustor = clustor;
	}

}
