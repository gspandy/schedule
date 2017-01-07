package com.kanven.schedual.transport.client.command;

import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;

public enum CommandType {

	TASK_ADD(MessageType.TASK_ADD), TASK_PAUSE(MessageType.TASK_PAUSE), TASK_RECORVE(
			MessageType.TASK_ECOVER), TASK_DELETE(MessageType.TASK_DELETE), TASK_REPORT(MessageType.TASK_REPORT);

	private CommandType(MessageType type) {
		this.type = type;
	}

	private MessageType type;

	public MessageType getType() {
		return this.type;
	}

}
