package com.ece554.schedule;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class MouseCommand extends Command {
	
	private MouseProcess process;
	
	public MouseCommand(MouseProcess process) {
		this.process = process;
	}

	public void executePhase1(){
		process.getConfigData();
		setState(Command.EXECUTING_STATE);
	}

	public void executePhase2(){
		process.setTextInMessageWindow();
		setState(Command.TERMINATED_STATE);
	}

}