package com.kanven.schedual.dispatcher.report;

import com.kanven.schedual.network.protoc.RequestProto.TaskReportor;

public interface ReportHandler {

	public void handle(TaskReportor reportor) throws Exception;

}
