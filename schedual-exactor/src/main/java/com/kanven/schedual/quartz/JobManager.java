package com.kanven.schedual.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
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

	private JobManager() throws SchedulerException {
		SchedulerFactory factory = new StdSchedulerFactory();
		scheduler = factory.getScheduler();
		scheduler.start();
	}

	public synchronized static JobManager getInstance() throws SchedulerException {
		if (instance == null) {
			instance = new JobManager();
		}
		return instance;
	}

	public void add(JobConfig config) throws SchedulerException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		if (scheduler.checkExists(key)) {
			throw new RuntimeException("group:" + config.getGroup() + ",name:" + config.getName() + "任务已经存在！　");
		}
		JobDetail detail = JobBuilder.newJob().ofType(QuartzJob.class).withIdentity(key)
				.usingJobData("url", config.getUrl()).build();
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger().withIdentity(config.getName() + "-trigger",
				config.getGroup());
		if (StringUtils.isEmpty(config.getCron())) {
			tb.withSchedule(SimpleScheduleBuilder.simpleSchedule());
		} else {
			tb.withSchedule(CronScheduleBuilder.cronSchedule(config.getCron()));
		}
		scheduler.scheduleJob(detail, tb.build());
	}

	public void remove(JobConfig config) throws SchedulerException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		if (scheduler.checkExists(key)) {
			scheduler.deleteJob(key);
		}
	}

	public void pause(JobConfig config) throws SchedulerException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		if (scheduler.checkExists(key)) {
			scheduler.pauseJob(key);
		}
	}

	public void resume(JobConfig config) throws SchedulerException {
		JobKey key = new JobKey(config.getName(), config.getGroup());
		if (scheduler.checkExists(key)) {
			scheduler.resumeJob(key);
		}
	}

	protected Object readResolve() {
		return instance;
	}

}
