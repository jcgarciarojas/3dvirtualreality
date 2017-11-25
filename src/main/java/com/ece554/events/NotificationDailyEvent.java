package com.ece554.events;

import com.ece554.gui.MessagePanel;
import com.ece554.schedule.Scheduler;
import com.ece554.schedule.DailyEventCommand;
import com.ece554.schedule.DailyEventProcess;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class NotificationDailyEvent extends Thread {

	private MessagePanel panel;
	private String event;
	private long delay;
	
	public NotificationDailyEvent(MessagePanel panel, String event, long delay) {
		this.event = event;
		this.panel = panel;
		this.delay = delay;
	}
	
	public synchronized void run () {
		try {
			sleep(delay);
 		    Scheduler.getInstance().addHelpCommand(new DailyEventCommand(new DailyEventProcess(panel, event)));
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		
	}
	
	
}
