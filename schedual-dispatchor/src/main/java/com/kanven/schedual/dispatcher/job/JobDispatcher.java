package com.kanven.schedual.dispatcher.job;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.command.Command;
import com.kanven.schedual.command.CommendType;
import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.Task;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.transport.client.api.Transform;

public class JobDispatcher implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(JobDispatcher.class);

	private static final int DEFAULT_CAPACITY = Integer.MAX_VALUE;

	private Clustor<Task> clustor;

	private int capacity = DEFAULT_CAPACITY;

	private AtomicBoolean start = new AtomicBoolean(false);

	private BlockingQueue<Job> jobs;

	public void addJob(Job job) {
		start();
		if (!jobs.add(job)) {
			throw new RuntimeException("添加任务失败：" + job);
		}
	}

	public void start() {
		if (start.compareAndSet(false, true)) {
			if (log.isDebugEnabled()) {
				log.debug("任务分发器开始启动...");
			}
			jobs = new LinkedBlockingQueue<Job>(capacity);
			Thread thread = new Thread(this);
			thread.start();
			if (log.isDebugEnabled()) {
				log.debug("任务分发器完成启动...");
			}
		}
	}

	public void setClustor(Clustor<Task> clustor) {
		this.clustor = clustor;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		if (capacity > 0) {
			this.capacity = capacity;
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			Job job = null;
			try {
				job = jobs.take();
				clustor.send(new TaskCommand(), transform);
				// TODO
			} catch (InterruptedException e) {
				log.error("获取任务失败！", e);
			} catch (Exception e) {
				log.error("分发任务(" + job + ")失败！", e);
			}
		}
		start.compareAndSet(true, false);
		throw new RuntimeException("任务分发线程中断...");
	}

	private Transform<Task> transform = new Transform<Task>() {

		public Request transformRequest(Command<Task> command) {
			Request.Builder rb = Request.newBuilder();
			rb.setRequestId("");
			rb.setType(MessageType.TASK_REPORT);
			rb.setTask(command.getContent());
			return rb.build();
		}

		@SuppressWarnings("unchecked")
		public <T> T transformResponse(Response response) {
			return (T) response;
		}

	};

	private static class TaskCommand extends Command<Task> {

		public TaskCommand() {
			super(CommendType.TASK);
		}

	}

}
