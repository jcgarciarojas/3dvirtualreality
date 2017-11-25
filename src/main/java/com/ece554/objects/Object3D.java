package com.ece554.objects;

import javax.media.j3d.TransformGroup;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public abstract class Object3D {
	protected TransformGroup tg;
	protected ParamsObject params;
	
	public Object3D(ParamsObject params) {
		this.tg = new TransformGroup();
		this.params = params;
	}
	
	public TransformGroup getTransform() {
		createObject();
		return tg;
	}

	protected abstract void createObject();
	
}
