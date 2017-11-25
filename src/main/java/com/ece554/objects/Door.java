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
public class Door extends Object3D {
	
	public static int RIGHT_DOOR = 1;
	public static int LEFT_DOOR = 2;
	private int direction;
	
	public Door(ParamsObject params, int direction) {
		super (params);
		this.direction=direction;
	}
	
	protected void createObject() {
		if (direction == RIGHT_DOOR) {
			tg.addChild(createRightDoor());
		} else {
			tg.addChild(createLeftDoor());
		}
	}
	
	protected Shape3D createRightDoor() {
		QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
                | GeometryArray.TEXTURE_COORDINATE_2);
		//This adjustment is needed due to this is a 2D structure and I need to
		//move it so it matches with the location of the 3D Boxes
		params.x+=-0.5f;
		params.z+=-0.5f;
		
		Point3f p = null;
		//open
		float xMov = -0.7f;
		float zMov = 0.3f;
		p = new Point3f(params.x,  params.y,  params.z );
		plane.setCoordinate(0, p);
		p = new Point3f(params.x,  params.y+params.heightY,  params.z );
		plane.setCoordinate(1, p);
		p = new Point3f(params.x+xMov ,  params.y+params.heightY,  params.z+zMov );
		plane.setCoordinate(2, p);
		p = new Point3f(params.x+xMov ,  params.y,  params.z+zMov );
		plane.setCoordinate(3, p);

		TexCoord2f q = new TexCoord2f( 0.0f,  1.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);
		
		return new Shape3D(plane, (Appearance)params.listAppearances.get(0));
	}

	protected Shape3D createLeftDoor() {
		QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
	            | GeometryArray.TEXTURE_COORDINATE_2);
		//This adjustment is needed due to this is a 2D structure and I need to
		//move it so it matches with the location of the 3D Boxes
		params.x+=-0.5f;
		params.z+=-0.5f;
		
		Point3f p = null;
		float xMov = -0.3f;
		float zMov = 0.7f;
		p = new Point3f(params.x,  params.y,  params.z+1 );
		plane.setCoordinate(0, p);
		p = new Point3f(params.x,  params.y+params.heightY,  params.z+1 );
		plane.setCoordinate(1, p);
		p = new Point3f(params.x+xMov ,  params.y+params.heightY,  params.z+zMov );
		plane.setCoordinate(2, p);
		p = new Point3f(params.x+xMov ,  params.y,  params.z+zMov );
		plane.setCoordinate(3, p);
	
		TexCoord2f q = new TexCoord2f( 0.0f,  1.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);
		
		return new Shape3D(plane, (Appearance)params.listAppearances.get(0));
	}

	protected Shape3D createDoor() {
		QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
	            | GeometryArray.TEXTURE_COORDINATE_2);
		//This adjustment is needed due to this is a 2D structure and I need to
		//move it so it matches with the location of the 3D Boxes
		params.x+=-0.5f;
		params.z+=-0.5f;
		
		Point3f p = null;
		//open
	float xMov =0;
	float zMov = 0;
	int choice = 3;
	if (choice == 1) {//Open
	xMov = 1f;
	zMov = 0f;
	} else if (choice == 2) {
	xMov = 0.7f;
	zMov = 0.3f;
	} else if (choice == 3) {
	xMov = 0.5f;
	zMov = 0.5f;
	} else if (choice == 4) {
	xMov = 0.3f;
	zMov = 0.7f;
	} else if (choice == 5) {//Closed
	xMov = 0f;
	zMov = 1f;
	}
	p = new Point3f(params.x,  params.y,  params.z );
	plane.setCoordinate(0, p);
	p = new Point3f(params.x,  params.y+params.heightY,  params.z );
	plane.setCoordinate(1, p);
	p = new Point3f(params.x+xMov ,  params.y+params.heightY,  params.z+zMov );
	plane.setCoordinate(2, p);
	p = new Point3f(params.x+xMov ,  params.y,  params.z+zMov );
	plane.setCoordinate(3, p);
	
		TexCoord2f q = new TexCoord2f( 0.0f,  1.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);
		
		return new Shape3D(plane, (Appearance)params.listAppearances.get(0));
	}

}
