package com.ece554.schedule;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class NavigationCommand extends Command {
	
	private NavigationProcess process;
	
	public NavigationCommand(NavigationProcess process) {
		this.process = process;
	}

	public void executePhase1(){
		process.testMove();
		setState(Command.EXECUTING_STATE);
		//System.out.println("navigation task 1");

	}

	public void executePhase2(){
		process.move();
		setState(Command.TERMINATED_STATE);
	}

}