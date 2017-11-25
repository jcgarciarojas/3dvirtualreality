package com.ece554.events;

import java.util.Calendar;

import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.gui.MessagePanel;
import com.ece554.schedule.HelpCommand;
import com.ece554.schedule.HelpProcess;
import com.ece554.schedule.Scheduler;

public class InactiveUserEvent {

	private static long userDelay;
	private long processTimeInMillis;
	private boolean isUserInactive= false;

	/**
	 * constructor for inactive event class
	 * This class checks, sets, creates and sends inactive command to scheduler  
	 */
	public InactiveUserEvent () {
		init();	
	}
	
	/**
	 * init class variables for inactive event class 
	 */
	private void init() {
		try {
			userDelay = Config.getInstance().getLongVal("USER_IDLE")*60000;
		} catch(SystemException se) {
			userDelay = 300000;
		}
	    processTimeInMillis = Calendar.getInstance().getTimeInMillis();
		
	}
	
	/***
	 * creates inactive command and sends it to the scheduler
	 */
	public void processInactiveEvent() {
		
		Scheduler.getInstance().addInactiveUserCommand(new HelpCommand(new HelpProcess(MessagePanel.MESSAGE_PANEL, "idle", MessagePanel.IDLE_EVENT)));
		isUserInactive = true;
	}
	
	public void resetInactiveStatus() {
		processTimeInMillis = Calendar.getInstance().getTimeInMillis();
		isUserInactive = false;
	}
	
	public boolean isUserInactive() {
		return (!isUserInactive && (Calendar.getInstance().getTimeInMillis() - processTimeInMillis) >= userDelay);
	}
	


}
