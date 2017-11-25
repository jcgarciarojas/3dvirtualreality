package com.ece554.schedule;

import java.util.List;
import java.util.Vector;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class Queue {
	
	private List l;
	
	public Queue () {
		init();
	}
	
	/**
	 * initialize queue
	 */
	public void init() {
		l = new Vector();
	}
	
	/**
	 * sync method to add an object to the queue
	 * @param obj (Command)
	 */
	public synchronized void add(Object obj) {
		l.add(obj);
	}
	
	/**
	 * sync method to get an object to the queue. this method
	 * removes the object form the queue.
	 * @return Object(Command)
	 */
	public synchronized Object get() {
		
		Object obj = null;
		
		if (l.get(0) != null) {
			obj = l.get(0);
			obj = l.remove(0);
		}
		
		return obj;
	}
	
	public int getSize() {
		return l.size();
	}
	
}