package com.kanven.schedual.quartz;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.report.JobNotify;

/**
 * 任务执行情况监控
 * 
 * @author kanven
 *
 */
public class JobMonitor {

	private static final Logger log = LoggerFactory.getLogger(JobMonitor.class);

	private JobMonitor() {

	}

	private Set<JobNotify> notifies = new HashSet<JobNotify>();

	public static JobMonitor getMonitor() {
		return JobMonitorHolder.INSTANCE;
	}

	public void handler(JobStatus status) {
		for (JobNotify notify : notifies) {
			try {
				notify.notify(status);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	private static class JobMonitorHolder {
		private static final JobMonitor INSTANCE = new JobMonitor();
	}

	public void addNotify(JobNotify notify) {
		if (!notifies.contains(notify)) {
			notifies.add(notify);
		}
	}

	public void removeNotify(JobNotify notify) {
		notifies.remove(notify);
	}

}
