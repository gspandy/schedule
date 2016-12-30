package com.kanven.schedual.report;

import com.kanven.schedual.quartz.JobStatus;

public interface JobNotify {
 
	void notify(JobStatus jobStatus);

}
