package com.ece554.objects;

import java.util.ArrayList;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.ece554.exception.SystemException;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class GroundTexture extends Shape3D {

	protected QuadArray quads;
	
	public GroundTexture(ArrayList coords, String textureFile, Vector3f upNormal) throws SystemException {
		int numberOfCoords = coords.size();
		quads = new QuadArray(numberOfCoords, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2|GeometryArray.NORMALS);
		
		createGeometry(coords, numberOfCoords, upNormal);
		setAppearance(ObjectFactory.getInstance().createAppearance(ColorFactory.getDarkGray(), textureFile, 1.f));
	}
	
	/**
	 * this methods assigns the texture to its of the queds in the horizontal structure
	 * @param coords
	 * @param numberOfCoords
	 * @param upNormal
	 */
	protected void createGeometry(ArrayList coords, int numberOfCoords, Vector3f upNormal) {
	    Point3f[] points = new Point3f[numberOfCoords];
	    coords.toArray( points );
	    quads.setCoordinates(0, points);

	    TexCoord2f[] tcoords = new TexCoord2f[numberOfCoords];
	    int i = 0;
	    while(i < numberOfCoords) {
	      tcoords[i] = new TexCoord2f(0.0f, 0.0f);
	      tcoords[i+1] = new TexCoord2f(1.0f, 0.0f);
	      tcoords[i+2] = new TexCoord2f(1.0f, 1.0f);
	      tcoords[i+3] = new TexCoord2f(0.0f, 1.0f);
	      i=i+4;
	    }
	    quads.setTextureCoordinates(0, 0, tcoords);

	    i = 0;
	    while(i < numberOfCoords) {
	    	quads.setNormal(i, upNormal);
	    	i++;
	    }
	    setGeometry(quads);	
	}

}















