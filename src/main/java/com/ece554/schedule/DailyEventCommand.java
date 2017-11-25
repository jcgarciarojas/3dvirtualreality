package com.ece554.schedule;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class DailyEventCommand extends Command {
	
	private DailyEventProcess process;
	
	public DailyEventCommand(DailyEventProcess process) {
		this.process = process;
	}
	
	public void executePhase1(){
		process.readEventFromConfig();
		setState(Command.EXECUTING_STATE);
	}

	public void executePhase2(){
		process.setEventInPanel();
		setState(Command.TERMINATED_STATE);
	}

}