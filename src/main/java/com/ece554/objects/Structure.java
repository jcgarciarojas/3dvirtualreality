package com.ece554.objects;

import javax.media.j3d.BranchGroup;

import com.ece554.exception.SystemException;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public abstract class Structure {

	private ParamsObject params;
	private BranchGroup bg;
	
	public Structure(ParamsObject params) throws SystemException
	{
		this.params = params;
		bg = new BranchGroup();
	}
	
	public ParamsObject getParams() {
		return params;
	}
	
	public BranchGroup getBranchGroup(){
		return bg;
	}
	
	public abstract Structure createObject() throws SystemException;
	
}
