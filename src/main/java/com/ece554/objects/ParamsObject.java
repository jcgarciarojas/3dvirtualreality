package com.ece554.objects;

import java.awt.Graphics;
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
public class ParamsObject {

	public int initialPoint;
	
	public boolean isFirstFloor;

	public int length;
	public float height;
	public int step;
	public String textureFile;
	Graphics graphic;

	public float widthX;
	public float widthZ;
	public float heightY;
	
	public float x;
	public float absolutePositionY;
	public float y;
	public float z;
	public List listAppearances = new Vector();
	public String action;
	
	public float widthX1;
	public float widthZ1;
	public float heightY1;
	public float y1;
	public float x1;
	public float z1;
	
	public float angleX;
	public float angleZ;
	
	public void init() {
		initialPoint = 0;

		length = 0;
		//height = 0.f;
		step  = 0;
		textureFile = null;
		graphic = null;

		widthX = 0.f;
		widthZ = 0.f;
		heightY = 0.f;

		widthX1 =0.f;
		widthZ1 =0.f;
		heightY1 =0.f;
		y1 =0.f;
		x1 = 0.f;
		z1 = 0.f;
		
		x = 0.f;
		y = 0.f;
		z = 0.f;
		angleX = 0.f;
		angleZ = 0.f;
		listAppearances.clear();
		action = null;
	}
	
}
