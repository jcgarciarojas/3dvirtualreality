package com.ece554.schedule;

import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.gui.MessagePanel;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class DailyEventProcess {

	private String action;
	private String fileName;
	private MessagePanel panel;

	public DailyEventProcess(MessagePanel panel, String action) {
		this.panel = panel;
		this.action = action;
	}
	
	public void readEventFromConfig() {
		try {
			this.fileName = Config.getInstance().getStringVal(this.action);
		} catch(SystemException se) {
			se.printStackTrace();
		}
	}
	
	public void setEventInPanel() {
		if (this.fileName == null) {
			panel.setMessage(MessagePanel.DAILY_EVENT, "OPTION INVALID");
		} else {
			panel.setMessage(MessagePanel.DAILY_EVENT, this.fileName);
		}
	}	
}
