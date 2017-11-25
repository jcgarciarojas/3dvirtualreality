package com.ece554.objects;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class Ceiling extends Object3D {
	
	public Ceiling(ParamsObject params) {
		super (params);
	}
	
	protected void createObject() {
		
		QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
                | GeometryArray.TEXTURE_COORDINATE_2);
		//This adjustment is needed due to this is a 2D structure and I need to
		//move it so it matches with the location of the 3D Boxes
		params.x+=-0.5f;
		params.z+=-0.5f;
		
		Point3f p = new Point3f(params.x,  params.y,  params.z+1);
		plane.setCoordinate(0, p);
		p.set(params.x,  params.y,  params.z);
		plane.setCoordinate(1, p);
		p.set(params.x+1,  params.y,  params.z);
		plane.setCoordinate(2, p);
		p.set(params.x+1,  params.y,  params.z+1);
		plane.setCoordinate(3, p);
		
		TexCoord2f q = new TexCoord2f( 0.0f,  1.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);
		
		Shape3D planeObj = new Shape3D(plane, (Appearance)params.listAppearances.get(0));
		tg.addChild(planeObj);
	}
}
