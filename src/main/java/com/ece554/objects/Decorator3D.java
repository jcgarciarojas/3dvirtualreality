package com.ece554.objects;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public abstract class Decorator3D extends Object3D {

	protected Object3D obj;
	public Decorator3D(Object3D obj, ParamsObject params) {
		super (params);
		this.obj = obj;
	}
	
}
