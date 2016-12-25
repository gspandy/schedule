package com.kanven.schedual.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 任务执行者
 * 
 * @author kanven
 *
 */
public class QuartzJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail detail = context.getJobDetail();
		JobDataMap params = detail.getJobDataMap();
		String url = params.getString("url");
		if (StringUtils.isEmpty(url)) {
			// 调用实际任务
		}
	}

}
