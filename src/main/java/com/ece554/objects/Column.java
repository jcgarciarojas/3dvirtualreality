package com.ece554.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class Column extends Object3D {
	
	public Column(ParamsObject params) {
		super (params);
	}
	protected void createObject() {

		Primitive obs = new Cylinder(params.widthX, params.heightY,
						Primitive.GENERATE_TEXTURE_COORDS |
						Primitive.GENERATE_NORMALS, (Appearance)params.listAppearances.get(0) );
		
		if (params.action != null) obs.setUserData(params.action);
		
		Transform3D trans = new Transform3D();
		trans.setTranslation( new Vector3d(params.x, params.y, params.z) );
		tg.setTransform(trans);
		tg.addChild(obs); 	
	}

}
