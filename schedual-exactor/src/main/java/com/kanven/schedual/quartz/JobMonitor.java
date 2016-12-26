package com.kanven.schedual.quartz;

/**
 * 任务执行情况监控
 * 
 * @author kanven
 *
 */
public class JobMonitor {

	private JobMonitor() {

	}

	public static JobMonitor getMonitor() {
		return JobMonitorHolder.INSTANCE;
	}

	public void handler(JobStatus status) {
		// todo
	}

	private static class JobMonitorHolder {
		private static final JobMonitor INSTANCE = new JobMonitor();
	}

}
