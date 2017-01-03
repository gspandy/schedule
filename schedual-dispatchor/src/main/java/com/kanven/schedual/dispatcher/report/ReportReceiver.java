package com.kanven.schedual.dispatcher.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.server.AbstractServer;
import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.TaskReportor;
import com.kanven.schedual.network.protoc.ResponseProto.Response;

/**
 * 任务报告接收器
 * 
 * @author kanven
 *
 */
public class ReportReceiver extends AbstractServer {

	private static final Logger log = LoggerFactory.getLogger(ReportReceiver.class);

	/**
	 * 任务报告接收器默认端口号
	 */
	public static final int DEFAULT_REPORT_RECEIVER_PORT = 8100;

	/**
	 * 任务报告接收器默认注册根路径
	 */
	public static final String DEFAULT_REPORT_RECEIVER_ROOT = "/schedual/task/report";

	private ReportHandler handler;

	public Object receive(Object o) {
		if (o instanceof Request) {
			Request request = (Request) o;
			MessageType type = request.getType();
			if (type == MessageType.TASK_REPORT) {
				String requestId = request.getRequestId();
				Response.Builder rb = Response.newBuilder();
				rb.setRequestId(requestId);
				TaskReportor reportor = request.getReportor();
				if (handler == null) {
					rb.setStatus(400);
					rb.setMsg("没有指定任务处理者！");
					log.error("没有指定任务处理者！");
				} else {
					try {
						handler.handle(reportor);
						rb.setStatus(200);
						rb.setRequestId(requestId);
					} catch (Exception e) {
						log.error(requestId + "任务处理失败！", e);
						rb.setStatus(400);
					}
				}
				return rb.build();
			}
		}
		return null;
	}

	public void setHandler(ReportHandler handler) {
		this.handler = handler;
	}

}