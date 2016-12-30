package com.kanven.schedual.transport.netty.command;

import com.kanven.schedual.command.Command;
import com.kanven.schedual.command.CommendType;

public class TaskCommand extends Command<Job> {

	public TaskCommand() {
		super(CommendType.TASK);
	}

}
