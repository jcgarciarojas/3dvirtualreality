package com.ece554.events;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

import com.ece554.file.Map;
import com.ece554.gui.MapLocatorPanel;
import com.ece554.schedule.Scheduler;
import com.ece554.schedule.NavigationCommand;
import com.ece554.schedule.NavigationProcess;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */

public class KeyVirtualEvent extends ViewPlatformBehavior
{

	private WakeupCondition keyPress;
  	private Map firstFloor;
  	private Map basementMap;
  	//private TransformGroup tgBackCCamera;
  	private MapLocatorPanel gMap;

  /**
   * 
   * @param map
   */
	public KeyVirtualEvent(Map firstFloor, Map basementMap, MapLocatorPanel gMap)
	{
		
	    this.firstFloor = firstFloor;
	    this.basementMap = basementMap;
	    this.gMap = gMap;
	    keyPress = new WakeupOnAWTEvent( KeyEvent.KEY_PRESSED );
	}
	
	/**
	 * 
	 */
	public void initialize()
	{ 
		wakeupOn( keyPress ); 
	}

	/**
	 * 
	 */
	public synchronized void processStimulus( Enumeration criteria )
	{
		WakeupCriterion wakeup;
		AWTEvent[] event;

		while( criteria.hasMoreElements() ) {
			wakeup = (WakeupCriterion) criteria.nextElement();
			if( wakeup instanceof WakeupOnAWTEvent ) {
				event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
				for( int i = 0; i < event.length; i++ ) {
					if( event[i].getID() == KeyEvent.KEY_PRESSED ) {
						Scheduler.getInstance().addNavigationCommand(new NavigationCommand(new NavigationProcess(targetTG, this.gMap, firstFloor, basementMap, ((KeyEvent)event[i]).getKeyCode())));
						notifyAll();
						//break;
					}
				}
			}
		}
		wakeupOn( keyPress );
	}
}
