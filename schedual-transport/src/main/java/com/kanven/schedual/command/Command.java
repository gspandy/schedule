package com.kanven.schedual.command;

public abstract class Command<C> {

	private CommendType type;

	private C content;
	
	public Command(CommendType type){
		this.type = type;
	}

	public C getContent() {
		return content;
	}

	public void setContent(C content) {
		this.content = content;
	}

	public CommendType getType() {
		return type;
	}

}
