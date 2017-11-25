package com.ece554.objects;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class SphereDecorator extends Decorator3D {
	TransformGroup tg1;

	public SphereDecorator(Object3D obj, ParamsObject params) {
		super (obj,params);
	}
	
	protected void createObject() {
		tg1 = new TransformGroup();

		TransformGroup tspin = new TransformGroup();
		tspin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	    Transform3D trans = new Transform3D();
	    trans.set(new Vector3d(params.x1, params.y1, params.z1));
	    TransformGroup objTrans = new TransformGroup(trans);
	    tg1.addChild(objTrans);
	    objTrans.addChild(tspin);

	    tspin.addChild(new Sphere(params.widthX1, Primitive.GENERATE_TEXTURE_COORDS, (Appearance)params.listAppearances.get(1)));
		
		Alpha rotationAlpha = new Alpha(-1, 4000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, tspin);
		
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		tspin.addChild(rotator);		

	}
	
	public TransformGroup getTransform() {
		
		tg = obj.getTransform();
		createObject();
		tg.addChild(tg1);
		return tg;
	}
	
}
