package com.ece554.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
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
public class Block extends Object3D {
	
	public Block(ParamsObject params) {
		super (params);
	}
	
	protected void createObject() {
		Primitive obs;
		obs = new Box(params.widthX, params.heightY, params.widthZ, 
						Primitive.GENERATE_TEXTURE_COORDS |
						Primitive.GENERATE_NORMALS, (Appearance)params.listAppearances.get(0) );
		if (params.action != null) obs.setUserData(params.action);
		Transform3D trans = new Transform3D();
		trans.setTranslation( new Vector3d(params.x, params.y, params.z) );
		tg.setTransform(trans);
		tg.addChild(obs); 
	}
}
