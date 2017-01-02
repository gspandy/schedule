package com.kanven.schedual.exactor.report;

import com.kanven.schedual.exactor.quartz.JobStatus;

public interface JobNotify {
 
	void notify(JobStatus jobStatus);

}
