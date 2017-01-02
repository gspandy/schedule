package com.kanven.schedual.dispatcher;

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

	/**
	 * 任务报告接收器默认端口号
	 */
	public static final int DEFAULT_REPORT_RECEIVER_PORT = 8100;

	/**
	 * 任务报告接收器默认注册根路径
	 */
	public static final String DEFAULT_REPORT_RECEIVER_ROOT = "/schedual/task/report";

	public Object receive(Object o) {
		if (o instanceof Request) {
			Request request = (Request) o;
			MessageType type = request.getType();
			if (type == MessageType.TASK_REPORT) {
				String requestId = request.getRequestId();
				Response.Builder rb = Response.newBuilder();
				rb.setRequestId(requestId);
				TaskReportor reportor = request.getReportor();
				System.out.println(reportor);
				// TODO
				return rb.build();
			}
		}
		return null;
	}

}
