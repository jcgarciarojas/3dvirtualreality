package com.ece554.exception;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */

public class SystemException extends Exception{
	
	public static final long serialVersionUID = 0;

	public SystemException(String ex) {
		super(ex);
	}

	public SystemException(Exception ex) {
		super(ex);
	}
}