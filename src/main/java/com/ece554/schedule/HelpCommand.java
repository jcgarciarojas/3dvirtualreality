package com.ece554.schedule;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class HelpCommand extends Command {
	
	private HelpProcess process;
	
	public HelpCommand(HelpProcess process) {
		this.process = process;
	}

	public void executePhase1(){
		process.readConfigFile();
		setState(Command.EXECUTING_STATE);
	}

	public void executePhase2(){
		process.setTextInPanel();
		setState(Command.TERMINATED_STATE);
	}

}
