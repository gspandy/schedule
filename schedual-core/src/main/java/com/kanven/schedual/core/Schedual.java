package com.kanven.schedual.core;

import com.kanven.schedual.core.exactor.Exactor;

public class Schedual {

	private Decision decision;
	
	
	public void dispatchTask(Task task){
		Exactor exactor = decision.selectExactor();
	}
	
}
