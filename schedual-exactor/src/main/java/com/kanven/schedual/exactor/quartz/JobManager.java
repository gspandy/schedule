package com.kanven.schedual.exactor.quartz;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 任务管理器
 * 
 * @author kanven
 *
 */
public class JobManager {

	private static JobManager instance = null;

	private Scheduler scheduler;

	private JobManager() throws JobException {
		SchedulerFactory factory = new StdSchedulerFactory();
		try {
			scheduler = factory.getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			throw new JobException("任务管理器启动失败！", e);
		}
	}

	public synchronized static JobManager getInstance() throws JobException {
		if (instance == null) {
			instance = new JobManager();
		}
		return instance;
	}

	public void add(JobConfig config) throws JobException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		try {
			if (scheduler.checkExists(key)) {
				throw new JobException("任务（id:" + config.getId() + ",group:" + config.getGroup() + ",name:"
						+ config.getName() + "）已经存在！　");
			}
		} catch (SchedulerException e) {
			throw new JobException("添加任务（id:" + config.getId() + ",group:" + config.getGroup() + ",name:"
					+ config.getName() + "）出现异常！", e);
		}
		JobDataMap params = new JobDataMap();
		params.put("id", config.getId());
		params.put("name", config.getName());
		params.put("group", config.getGroup());
		params.put("url", config.getUrl());
		JobDetail detail = JobBuilder.newJob().ofType(QuartzJob.class).withIdentity(key).usingJobData(params).build();
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger().withIdentity(config.getName() + "-trigger",
				config.getGroup());
		if (StringUtils.isEmpty(config.getCron())) {
			tb.withSchedule(SimpleScheduleBuilder.simpleSchedule());
			Date startTime = config.getStartTime();
			Date now = new Date();
			if (startTime == null || now.getTime() >= startTime.getTime()) {
				tb.startNow();
			} else {
				tb.startAt(startTime);
			}
		} else {
			tb.withSchedule(CronScheduleBuilder.cronSchedule(config.getCron()));
		}
		try {
			scheduler.scheduleJob(detail, tb.build());
		} catch (SchedulerException e) {
			throw new JobException(
					"添加任务（id:" + config.getId() + ",group:" + config.getGroup() + ",name:" + config.getName() + "）失败！",
					e);
		}
	}

	public void remove(JobConfig config) throws JobException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		try {
			if (scheduler.checkExists(key)) {
				scheduler.deleteJob(key);
			}
		} catch (SchedulerException e) {
			throw new JobException(
					"移除任务（id:" + config.getId() + ",group:" + config.getGroup() + ",name:" + config.getName() + "）失败！",
					e);
		}

	}

	public void pause(JobConfig config) throws JobException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		try {
			if (scheduler.checkExists(key)) {
				scheduler.pauseJob(key);
			}
		} catch (SchedulerException e) {
			throw new JobException(
					"暂停任务（id:" + config.getId() + ",group:" + config.getGroup() + ",name:" + config.getName() + "）失败！",
					e);
		}
	}

	public void resume(JobConfig config) throws JobException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		try {
			if (scheduler.checkExists(key)) {
				scheduler.resumeJob(key);
			}
		} catch (SchedulerException e) {
			throw new JobException(
					"重启任务（id:" + config.getId() + ",group:" + config.getGroup() + ",name:" + config.getName() + "）失败！",
					e);
		}
	}

	protected Object readResolve() {
		return instance;
	}

}
