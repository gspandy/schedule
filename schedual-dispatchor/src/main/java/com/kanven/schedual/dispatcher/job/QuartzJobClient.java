package com.kanven.schedual.dispatcher.job;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
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

public class QuartzJobClient implements JobClient {

	private static final Logger log = LoggerFactory.getLogger(QuartzJobClient.class);

	private Clustor<Task> clustor;

	public QuartzJobClient() {

	}

	public QuartzJobClient(Clustor<Task> clustor) {
		this.clustor = clustor;
	}

	public boolean add(Job job) {
		check(job);
		try {
			clustor.send(new AddCommand(transform(job)), transform);
			return true;
		} catch (Exception e) {
			log.error("任务(" + job + ")添加失败！", e);
		}
		return false;
	}

	public boolean del(Job job) {
		check(job);
		try {
			clustor.send(new DelCommand(transform(job)), transform);
			return true;
		} catch (Exception e) {
			log.error("任务(" + job + ")删除失败！", e);
		}
		return false;
	}

	public boolean pause(Job job) {
		check(job);
		try {
			clustor.send(new PauseCommand(transform(job)), transform);
			return true;
		} catch (Exception e) {
			log.error("任务(" + job + ")暂停失败！", e);
		}
		return false;
	}

	public boolean recove(Job job) {
		check(job);
		try {
			clustor.send(new RecoveCommand(transform(job)), transform);
		} catch (Exception e) {
			log.error("任务(" + e + ")恢复失败！", e);
		}
		return false;
	}

	private void check(Job job) {
		if (job == null) {
			throw new IllegalArgumentException("任务为空！");
		}
		Long id = job.getId();
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("任务(" + job + ")编号为空！");
		}
		if (StringUtils.isEmpty(job.getName())) {
			throw new IllegalArgumentException("任务(" + job + ")没有指定名称！");
		}
		if (StringUtils.isEmpty(job.getGroup())) {
			throw new IllegalArgumentException("任务(" + job + ")没有指定分组！");
		}
	}

	private Task transform(Job job) {
		Task.Builder tb = Task.newBuilder();
		tb.setId(job.getId());
		tb.setName(job.getName());
		tb.setGroup(job.getGroup());
		tb.setUrl(job.getUrl());
		tb.setCron(job.getCron());
		Date createTime = job.getStartTime();
		if (createTime != null) {
			tb.setStartTime(createTime.getTime());
		}
		return tb.build();
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

	private static class AddCommand extends Command<Task> {

		public AddCommand(Task task) {
			super(CommendType.ADD);
			setContent(task);
		}

	}

	private static final class DelCommand extends Command<Task> {

		public DelCommand(Task task) {
			super(CommendType.DEL);
			setContent(task);
		}

	}

	private static final class PauseCommand extends Command<Task> {

		public PauseCommand(Task task) {
			super(CommendType.PAUSE);
			setContent(task);
		}

	}

	public static final class RecoveCommand extends Command<Task> {

		public RecoveCommand(Task task) {
			super(CommendType.RECOVER);
			setContent(task);
		}

	}

	public void setClustor(Clustor<Task> clustor) {
		this.clustor = clustor;
	}

}
