package com.kanven.schedual.transport.client.command;

import java.util.UUID;

import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.Request.Builder;

public abstract class Command<T> {

	private final String requestId;

	private CommandType type;

	private T value;

	{
		requestId = UUID.randomUUID().toString().replaceAll("-", "");
	}

	public Command(CommandType type, T value) {
		this.type = type;
		this.value = value;
	}

	public Request buildRequest() {
		Request.Builder rb = Request.newBuilder();
		rb.setRequestId(requestId);
		rb.setType(type.getType());
		return rb.build();
	}

	public abstract void buildContent(Builder builder);

	public String getRequestId() {
		return requestId;
	}

	public CommandType getType() {
		return type;
	}

	public T getValue() {
		return value;
	}

}
