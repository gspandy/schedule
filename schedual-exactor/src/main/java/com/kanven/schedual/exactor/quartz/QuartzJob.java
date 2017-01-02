package com.kanven.schedual.exactor.quartz;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.exactor.http.HttpClient;
import com.kanven.schedual.exactor.quartz.JobStatus.Status;

/**
 * 任务执行者
 * 
 * @author kanven
 *
 */
public class QuartzJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(QuartzJob.class);

	private JobStatus createJobStatus(JobDetail detail) {
		JobStatus status = new JobStatus();
		JobDataMap params = detail.getJobDataMap();
		Long id = params.getLong("id");
		status.setId(id);
		String name = params.getString("name");
		status.setName(name);
		String group = params.getString("group");
		status.setGroup(group);
		String url = params.getString("url");
		status.setUrl(url);
		return status;
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobStatus status = createJobStatus(context.getJobDetail());
		String url = status.getUrl();
		if (StringUtils.isNotEmpty(url)) {
			try {
				status.setResult(HttpClient.doGet(url, 3000, 5000));
				status.setStatus(Status.SUCCESS);
			} catch (ClientProtocolException e) {
				status.setThrowable(e);
				status.setStatus(Status.FAILURE);
				log.error("任务（id:" + status.getId() + ",group:" + status.getGroup() + ",name:" + status.getName()
						+ "）执行出现异常！", e);
			} catch (IOException e) {
				status.setThrowable(e);
				status.setStatus(Status.FAILURE);
				log.error("任务（id:" + status.getId() + ",group:" + status.getGroup() + ",name:" + status.getName()
						+ "）执行出现异常！", e);
			}
		} else {
			status.setStatus(Status.FAILURE);
			status.setThrowable(new RuntimeException("任务（id:" + status.getId() + ",group:" + status.getGroup()
					+ ",name:" + status.getName() + "）没有指定接口地址！"));
		}
		status.finish();
		JobMonitor.getMonitor().handler(status);
	}

}
