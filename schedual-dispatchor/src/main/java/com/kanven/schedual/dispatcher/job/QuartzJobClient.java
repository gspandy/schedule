package com.kanven.schedual.dispatcher.job;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.network.protoc.RequestProto.Task;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.transport.client.api.Transform;
import com.kanven.schedual.transport.client.command.AbstractTaskCommand;
import com.kanven.schedual.transport.client.command.CommandType;

public class QuartzJobClient implements JobClient {

	private static final Logger log = LoggerFactory.getLogger(QuartzJobClient.class);

	private Clustor<Job> clustor;

	public QuartzJobClient() {

	}

	public QuartzJobClient(Clustor<Job> clustor) {
		this.clustor = clustor;
	}

	public boolean add(Job job) {
		check(job);
		try {
			clustor.send(new TaskCommand(CommandType.TASK_ADD, job), transform);
			return true;
		} catch (Exception e) {
			log.error("任务(" + job + ")添加失败！", e);
		}
		return false;
	}

	public boolean del(Job job) {
		check(job);
		try {
			clustor.send(new TaskCommand(CommandType.TASK_DELETE, job), transform);
			return true;
		} catch (Exception e) {
			log.error("任务(" + job + ")删除失败！", e);
		}
		return false;
	}

	public boolean pause(Job job) {
		check(job);
		try {
			clustor.send(new TaskCommand(CommandType.TASK_PAUSE, job), transform);
			return true;
		} catch (Exception e) {
			log.error("任务(" + job + ")暂停失败！", e);
		}
		return false;
	}

	public boolean recove(Job job) {
		check(job);
		try {
			clustor.send(new TaskCommand(CommandType.TASK_RECORVE, job), transform);
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

	private static class TaskCommand extends AbstractTaskCommand<Job> {

		public TaskCommand(CommandType type, Job value) {
			super(type, value);
		}

		@Override
		public Task buildTask(Job job) {
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

	}

	private Transform transform = new Transform() {

		@SuppressWarnings("unchecked")
		public <T> T transformResponse(Response response) {
			return (T) response;
		}

	};

	public void setClustor(Clustor<Job> clustor) {
		this.clustor = clustor;
	}

}
