package com.kanven.schedual.transport.client.command;

import com.kanven.schedual.network.protoc.RequestProto.Request.Builder;
import com.kanven.schedual.network.protoc.RequestProto.TaskReportor;

public abstract class AbstractReportCommand<T> extends Command<T> {

	public AbstractReportCommand(T value) {
		super(CommandType.TASK_REPORT, value);
	}

	@Override
	public void buildContent(Builder builder) {
		builder.setReportor(buildReport(getValue()));
	}

	public abstract TaskReportor buildReport(T value);

}
