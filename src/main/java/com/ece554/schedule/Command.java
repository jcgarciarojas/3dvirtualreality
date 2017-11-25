package com.ece554.schedule;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public abstract class Command {
	
	public static final int READY_STATE = 1;
	public static final int EXECUTING_STATE = 2;
	public static final int TERMINATED_STATE = 3;
	protected int state = READY_STATE;
	
	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public abstract void executePhase1();

	public abstract void executePhase2();
}
