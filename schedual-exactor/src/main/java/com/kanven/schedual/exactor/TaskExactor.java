package com.kanven.schedual.exactor;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.server.AbstractServer;
import com.kanven.schedual.exactor.quartz.JobConfig;
import com.kanven.schedual.exactor.quartz.JobException;
import com.kanven.schedual.exactor.quartz.JobManager;
import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.Task;
import com.kanven.schedual.network.protoc.ResponseProto.Response;

/**
 * 任务执行器
 * 
 * @author kanven
 *
 */
public class TaskExactor extends AbstractServer {

	private static final Logger log = LoggerFactory.getLogger(TaskExactor.class);

	/**
	 * 任务执行器默认端口号
	 */
	public static final int DEFAULT_TASK_EXACTOR_PORT = 8099;

	/**
	 * 任务执行器默认注册根路径
	 */
	public static final String DEFAULT_TASK_EXACTOR_ROOT = "/schedual/task/executor";

	public TaskExactor() {

	}

	public Object receive(Object o) {
		if (o instanceof Request) {
			Request request = (Request) o;
			if (request.getType() == MessageType.TASK) {
				Response.Builder rb = Response.newBuilder();
				rb.setRequestId(request.getRequestId());

				Task task = request.getTask();
				JobConfig config = new JobConfig();
				config.setId(task.getId());
				config.setGroup(task.getGroup());
				config.setName(task.getName());
				config.setUrl(task.getUrl());
				config.setStartTime(new Date(task.getStartTime()));
				config.setCron(task.getCron());
				try {
					JobManager.getInstance().add(config);
					rb.setStatus(200);
					rb.setMsg("添加任务成功！　");
				} catch (JobException e) {
					log.error("任务添加失败！", e);
					rb.setStatus(400);
					rb.setMsg(e.getMessage());
				}
				return rb.build();
			}
		}
		return null;
	}

}
