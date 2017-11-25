package com.ece554.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class BlockDecorator extends Decorator3D {
	TransformGroup tg1;

	public BlockDecorator(Object3D obj, ParamsObject params) {
		super (obj,params);
	}
	
	protected void createObject() {
		
		Primitive obs = new Box(params.widthX1, params.heightY1, params.widthZ1, 
				Primitive.GENERATE_TEXTURE_COORDS |
				Primitive.GENERATE_NORMALS, (Appearance)params.listAppearances.get(1) );

		if (params.action != null) obs.setUserData(params.action);
		
		tg1 = new TransformGroup();
		Transform3D trans1 = new Transform3D();
		trans1.setTranslation( new Vector3d(params.x1, params.y1+params.height, params.z1) );
		tg1.setTransform(trans1);
		tg1.addChild(obs); 
	}
	
	public TransformGroup getTransform() {
		
		tg = obj.getTransform();
		createObject();
		tg.addChild(tg1);
		return tg;
	}
	
}
