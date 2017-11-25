package com.ece554.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.j3d.Node;

import com.ece554.gui.MessagePanel;
import com.ece554.schedule.Scheduler;
import com.ece554.schedule.MouseCommand;
import com.ece554.schedule.MouseProcess;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */

public class MouseVirtualEvent implements MouseListener
{
	private PickCanvas pickCanvas;
	private MessagePanel mPanel;

	/**
	 * 
	 * @param pickCanvas
	 * @param mPanel
	 */
	public MouseVirtualEvent(PickCanvas pickCanvas, MessagePanel mPanel) {
		this.pickCanvas = pickCanvas;
		this.mPanel = mPanel;
	}

	/**
	 * 
	 * @param e
	 */
	public synchronized void mouseClicked(MouseEvent event) {
	
		 try {
			  pickCanvas.setShapeLocation( event );
		
			  PickResult pickResult = pickCanvas.pickClosest();
			  if( pickResult != null )
			  {
				   Node node = pickResult.getSceneGraphPath().getNode(0);
				   if (node != null && node.getUserData() != null)
				   {
					   String action = (String)node.getUserData();
					   Scheduler.getInstance().addMouseCommand(new MouseCommand(new MouseProcess(mPanel, action)));
				   }
			  }
		 } catch(Exception e) {}
	}

	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	public void mousePressed(MouseEvent event) {}
	
}