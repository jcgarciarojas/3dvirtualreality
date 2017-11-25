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
public class MouseProcess {

	private String action;
	private String fileName;
	private MessagePanel panel;

	public MouseProcess(MessagePanel panel, String action) {
		this.panel = panel;
		this.action = action;
	}
	
	public void getConfigData() {
		try {
			this.fileName = Config.getInstance().getStringVal(this.action);
		} catch(SystemException se) {
			se.printStackTrace();
		}
	}
	
	public void setTextInMessageWindow() {
		if (this.fileName == null) {
			panel.setMessage(MessagePanel.MOUSE_EVENT, "OPTION INVALID");
		} else { 
			panel.setMessage(MessagePanel.MOUSE_EVENT, this.fileName);
		}
	}
}
