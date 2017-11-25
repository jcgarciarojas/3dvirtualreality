package com.ece554.schedule;

//import java.util.Calendar;

import com.ece554.events.InactiveUserEvent;
import com.ece554.exception.SystemException;
import com.ece554.file.Config;

//import java.util.Calendar;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class Scheduler extends Thread{

	private static Queue mouseQueue;
	private static Queue navigationQueue;
	private static Queue helpQueue;
	private static Queue eventQueue;
	private static Queue inactiveUserQueue;

	private static Command mouseCommand;
	private static Command navigationCommand;
	private static Command inactiveUserCommand;
	private static Command helpCommand;
	private static Command eventCommand;
	
	private static long schedulerDelay;
	
	private InactiveUserEvent inactiveEvent;

	
	private static Scheduler instance;
	
	/**
	 * 
	 */
	private Scheduler()  {
		init();
	}
	
	public static Scheduler getInstance()  {
		if (instance == null) {
			instance = new Scheduler();
		}
		return instance;
	}

	/**
	 * initiate queues
	 */
	public void init() {
		mouseQueue = new Queue();
		navigationQueue = new Queue();
		helpQueue = new Queue();
		eventQueue = new Queue();
		inactiveUserQueue = new Queue();
		inactiveEvent = new InactiveUserEvent();
		
		try {
			schedulerDelay = Config.getInstance().getLongVal("SCHEDULER_DELAY"); 
		}catch(SystemException se) {
			schedulerDelay = 100;
		}
	}

	/**
	 * infinite while loop use to check if there are processed to be executed  
	 */
	public synchronized void run() {

		while(true) {
			try {
				//System.out.println("start process ()"+Calendar.getInstance().getTimeInMillis());
				process();
				//System.out.println("sleep...1"+Calendar.getInstance().getTimeInMillis());
				sleep(schedulerDelay);
				//System.out.println("sleep...1"+Calendar.getInstance().getTimeInMillis());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * round robin scheduling of processes, the user navigation process is schedule twice
	 * due to is triggered more frequently than the other processes  
	 * @param currentProcess
	 * @param queue
	 */
	private void process() {


		this.navigationProcess();
		this.eventProcess();
		this.mouseProcess();
		//Including navigation command twice since navigation happens 
		//more frequently than the other events
		this.navigationProcess();
		this.helpTextMessageProcess();
		this.inactiveUserProcess();

		//If the user has been inactive for some time then I need to trigerred 
		// and inactive command and send it to the scheduler
		if (inactiveEvent.isUserInactive()) inactiveEvent.processInactiveEvent();


	}
	
	private void navigationProcess() {
		//System.out.println("overhead..."+Calendar.getInstance().getTimeInMillis());
		if (navigationCommand != null || navigationQueue.getSize() > 0) {
			
			if (navigationCommand == null)
				navigationCommand = (Command)navigationQueue.get();
			
			if (navigationCommand != null){ 
				//User just navigate through the virtual world so I need to reset inactive event user.
				inactiveEvent.resetInactiveStatus();
				//System.out.println("Navigation Command");
				process(navigationCommand);
				if (navigationCommand.getState() == Command.TERMINATED_STATE) navigationCommand = null;
			}
		}		
	}
	
	private void eventProcess() {
		//System.out.println("overhead..."+Calendar.getInstance().getTimeInMillis());
		if (eventCommand != null || eventQueue.getSize() > 0) {
			
			if (eventCommand == null)
				eventCommand = (Command)eventQueue.get();

			if (eventCommand != null){ 
				//For schedule events I do not need to reset inactive event
				//due to this is not a user event 
				//System.out.println("Event Command");
				process(eventCommand);
				if (eventCommand.getState() == Command.TERMINATED_STATE) eventCommand = null;
			}
		}
	}
	
	private void mouseProcess() {
		//System.out.println("overhead..."+Calendar.getInstance().getTimeInMillis());
		if (mouseCommand != null || mouseQueue.getSize() > 0) {
			
			if (mouseCommand == null)
				mouseCommand = (Command)mouseQueue.get();
			
			if (mouseCommand != null){ 
				//User just trigerred an mouse event so I need to reset inactive event user.
				inactiveEvent.resetInactiveStatus();
				//System.out.println("Mouse Command");
				process(mouseCommand);
				if (mouseCommand.getState() == Command.TERMINATED_STATE) mouseCommand = null;
			}
		}
	}

	private void helpTextMessageProcess() {
		//System.out.println("overhead..."+Calendar.getInstance().getTimeInMillis());
		if (helpCommand != null || helpQueue.getSize() > 0) {
			
			if (helpCommand == null)
				helpCommand = (Command)helpQueue.get();
			
			if (helpCommand != null){ 
				//User just type a command in the message window so I need to reset inactive event user.
				inactiveEvent.resetInactiveStatus();
				//System.out.println("Help Command");
				process(helpCommand);
				if (helpCommand.getState() == Command.TERMINATED_STATE) helpCommand = null;
			}
		}
	}

	private void inactiveUserProcess() {
		//System.out.println("overhead..."+Calendar.getInstance().getTimeInMillis());
		if (inactiveUserCommand != null || inactiveUserQueue.getSize() > 0) {
			
			if (inactiveUserCommand == null)
				inactiveUserCommand = (Command)inactiveUserQueue.get();
			
			if (inactiveUserCommand != null){ 
				//System.out.println("Idle Command");
				process(inactiveUserCommand);
				if (inactiveUserCommand.getState() == Command.TERMINATED_STATE) inactiveUserCommand = null;
			}
		}
	}

	/**
	 * This method executes a method based on the state of the command
	 * @param cmd
	 */
	private void process(Command cmd) {

		if (cmd.getState() == Command.READY_STATE){
			//System.out.println("start phase1.."+Calendar.getInstance().getTimeInMillis());
			cmd.executePhase1();
			//System.out.println("end phase1.."+Calendar.getInstance().getTimeInMillis());
			return;
		}

		else if (cmd.getState() == Command.EXECUTING_STATE) {
			//System.out.println("start phase2.."+Calendar.getInstance().getTimeInMillis());
			cmd.executePhase2();
			//System.out.println("end phase2.."+Calendar.getInstance().getTimeInMillis());
			return;
		}

	}
	
	/**
	 * add a command to the respective queue
	 * @param cmd
	 */
	public void addMouseCommand(Command cmd) {
		mouseQueue.add(cmd);
	}
	public void addNavigationCommand(Command cmd) {
		navigationQueue.add(cmd);
	}
	public void addHelpCommand(Command cmd) {
		helpQueue.add(cmd);
	}
	public void addInactiveUserCommand(Command cmd) {
		inactiveUserQueue.add(cmd);
	}
	public void addDailyEventCommand(Command cmd) {
		eventQueue.add(cmd);
	}

}
