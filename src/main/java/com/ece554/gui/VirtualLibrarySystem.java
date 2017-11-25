package com.ece554.gui;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.util.StringTokenizer;
import java.util.Calendar;
import javax.swing.JPanel;

import com.ece554.events.NotificationDailyEvent;
import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.schedule.Scheduler;
import com.sun.j3d.utils.applet.MainFrame;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class VirtualLibrarySystem extends Applet {
	public static final long serialVersionUID =0;
	private MessagePanel mPanel; 
	private static long delayEvent;
	private JPanel virtualWorld; 
	
	/**
	 * 
	 * @throws SystemException
	 */
	public VirtualLibrarySystem() throws SystemException {
		delayEvent = Config.getInstance().getLongVal("EVENT_DELAY")*60000;
		addComponents();
	}
	
	/**
	 * 
	 * @throws SystemException
	 */
	protected void addComponents() throws SystemException {
		setLayout(new BorderLayout());
		

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//VirtualMapPanel bckPanel = new VirtualMapPanel();
		//panel.add(bckPanel, BorderLayout.NORTH);
		MapLocatorPanel gMap = new MapLocatorPanel();
		panel.add(gMap, BorderLayout.NORTH);
		
		mPanel = new MessagePanel();
		panel.add(mPanel, BorderLayout.CENTER);

		//add(new VirtualWorld(mPanel, bckPanel.getTgBackCamera()), BorderLayout.CENTER);
		virtualWorld = new VirtualWorld(mPanel, gMap);
		add(virtualWorld, BorderLayout.CENTER);
		
		add(panel, BorderLayout.WEST);
		add(mPanel, BorderLayout.EAST);
		setVisible(true);
	}
	
	/**
	 * this method starts the scheduler and set the schedule events of the system 
	 * @throws SystemException
	 */
	public void setSchedule() throws SystemException {
		
		//if (true) return;
		Scheduler cr = Scheduler.getInstance();
		cr.start();
		System.out.println("Scheduler has started....");
		
		String events = Config.getInstance().getStringVal("DAILY_EVENTS_LIST");
		StringTokenizer tk = new StringTokenizer(events, ",");
		while(tk.hasMoreTokens()) {
			String eventKey = tk.nextToken();
			String time = Config.getInstance().getStringVal(eventKey+"_time");
			long delay = 0;
			if (time != null) {
				delay = getDelay(time);
			} else {
				delay = Config.getInstance().getLongVal(eventKey+"_delay")*1000;
			}
			
			if (delay >0) { 
				System.out.println("Event "+eventKey+" has been scheduled.... "+delay);
				Thread t = new NotificationDailyEvent(mPanel, eventKey, delay);
				t.start();
			} else {
				System.out.println("Please review Event "+eventKey+", it has a negative delay "+delay);
			}
		}
		
		System.out.println("Virtual reality program is running....");
	}
	
	public long getDelay(String time) {
		StringTokenizer tk = new StringTokenizer(time, ":"); 
		Calendar c = Calendar.getInstance();
		long base = c.getTimeInMillis();
		
		c.set(Calendar.HOUR_OF_DAY, new Integer(tk.nextToken()).intValue());
		c.set(Calendar.MINUTE, new Integer(tk.nextToken()).intValue());
		return c.getTimeInMillis()-delayEvent-base;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) 
	{
		try {
			VirtualLibrarySystem v = new VirtualLibrarySystem();
			MainFrame m = new MainFrame(v, 800, 450);
			m.setTitle(Config.getInstance().getStringVal("TITLE_MAIN_WINDOW"));
			v.setSchedule();
		} catch(SystemException se) {
			System.out.println(se);
			se.printStackTrace();
			System.exit(1);
		}
	}

}
