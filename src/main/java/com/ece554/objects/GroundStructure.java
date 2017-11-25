package com.ece554.objects;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.ece554.exception.SystemException;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class GroundStructure extends Structure {

	/**
	 * 
	 * @param params
	 * @throws SystemException
	 */
	public GroundStructure(ParamsObject params) throws SystemException {
		super(params);
	}

	/**
	 * 
	 */
	public Structure createObject() 
	{
		ArrayList coords = createCoordsFloor();
		
		float height = super.getParams().height;
		Vector3f upNormal = new Vector3f(height, height-1, height);  
		try {
		super.getBranchGroup().addChild( new GroundTexture(coords, super.getParams().textureFile, upNormal));
		} catch(SystemException se) {
			se.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	protected ArrayList createCoordsFloor() {
		ArrayList coords = new ArrayList();
		float height = super.getParams().height;
		for (int x = super.getParams().initialPoint; x <= super.getParams().length; 
				x+=super.getParams().step) {
			for (int z = super.getParams().initialPoint; z <= super.getParams().length; 
				z+=super.getParams().step) {
				setCoords(x, height, z, coords);
			}			
		}
		return coords;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param array
	 */
	protected void setCoords(int x1, float y, int z1, ArrayList array) 
	{
		float x=(float)x1-0.5f;
		float z= (float)z1-0.5f;
		if (super.getParams().isFirstFloor && z1 ==18 &&  (x1==14 || x1==16))  return; 
		Point3f p1 = new Point3f(x,y,z);
		Point3f p2 = new Point3f(x+super.getParams().step,y,z);
		Point3f p3 = new Point3f(x+super.getParams().step,y,z-super.getParams().step);
		Point3f p4 = new Point3f(x,y,z-super.getParams().step);
		array.add(p1);
		array.add(p2);
		array.add(p3);
		array.add(p4);
	}

}
