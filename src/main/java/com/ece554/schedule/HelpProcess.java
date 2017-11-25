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
public class HelpProcess {

	private String action;
	private String fileName;
	private MessagePanel panel;
	private String eventName = MessagePanel.HELP_EVENT;

	public HelpProcess(MessagePanel panel, String action) {
		this.panel = panel;
		this.action = action;
	}
	
	public HelpProcess(MessagePanel panel, String action, String eventName) {
		this(panel, action);
		this.panel = panel;
		this.action = action;
		this.eventName = eventName;
	}

	public void readConfigFile() {
		try {
			this.fileName = Config.getInstance().getStringVal(this.action);
		} catch(SystemException se) {
			se.printStackTrace();
		}
	}
	
	public void setTextInPanel() {
		if (this.fileName == null) {
			panel.setMessage(eventName, "OPTION INVALID");
		} else {
			panel.setMessage(eventName, this.fileName);
		}
	}
}
