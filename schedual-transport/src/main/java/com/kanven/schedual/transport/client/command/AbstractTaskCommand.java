package com.kanven.schedual.transport.client.command;

import com.kanven.schedual.network.protoc.RequestProto.Request.Builder;
import com.kanven.schedual.network.protoc.RequestProto.Task;

public abstract class AbstractTaskCommand<T> extends Command<T> {

	public AbstractTaskCommand(CommandType type, T value) {
		super(type, value);
	}

	@Override
	public void buildContent(Builder builder) {
		builder.setTask(buildTask(getValue()));
	}
	
	public abstract Task buildTask(T value);

}
