package com.kanven.schedual.dispatcher;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.impl.ClustorFactory;
import com.kanven.schedual.dispatcher.job.Job;
import com.kanven.schedual.dispatcher.job.JobClient;
import com.kanven.schedual.dispatcher.job.QuartzJobClient;
import com.kanven.schedual.dispatcher.report.ReportReceiver;
import com.kanven.schedual.register.Register;

public class ReportReceiverBootstrap {

	public static void main(String[] args) {
		Register register = new Register("127.0.0.1:2181", 3000);
		
		ClustorFactory<Job> factory = new ClustorFactory<Job>();
		factory.setParent("/schedual/task/executor");
		factory.setRegister(register);
		factory.init();
		Clustor<Job> clustor = factory.getClustor();
		JobClient jobClient = new QuartzJobClient(clustor);
		Job job = new Job();
		job.setId(1L);
		job.setGroup("test");
		job.setName("test");
		job.setUrl("https://www.baidu.com");
		job.setCron("*/5 * * * * ?");
		jobClient.add(job);
		
		ReportReceiver receiver = new ReportReceiver();
		receiver.setRegister(register);
		receiver.setIp("127.0.0.1");
		receiver.setPort(ReportReceiver.DEFAULT_REPORT_RECEIVER_PORT);
		receiver.setRoot(ReportReceiver.DEFAULT_REPORT_RECEIVER_ROOT);
		receiver.start();
	}
	
}
