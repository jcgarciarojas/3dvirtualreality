package com.ece554.objects;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.file.Map;
import com.sun.j3d.utils.image.TextureLoader;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class ObjectFactory {
	private static ObjectFactory instance = new ObjectFactory();
	
	public static int OBJECT_CEILING =1;
	public static int OBJECT_FLOOR =2;
	public static int OBJECT_BOX =3;
	public static int OBJECT_DOOR =4;
	public static int OBJECT_SHELF =5;
	public static int OBJECT_WINDOW =6;
	public static int OBJECT_DESK =7;
	public static int OBJECT_STRUCTURE =8;
	
	public static int AMBIENT_LIGHT =8;
	public static int DIRECTIONAL_LIGHT=9;
	
	private ObjectFactory() {
	}
	
	public static ObjectFactory getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param objectType
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	public Structure createVisualStructure(Map map, int objectType, ParamsObject params)throws SystemException
	{
		Structure object = null;
		if (objectType == OBJECT_FLOOR) {
			object = new GroundStructure(params);
		}
		else if (objectType == OBJECT_STRUCTURE) {
			object = new MapStructure(params, map);
		}
		return object;
	}
	
	public TransformGroup createObject(String type, ParamsObject params) throws SystemException
	{
		TransformGroup tg = null;

		if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_CONE")))
			tg = new ConeDecorator(new Column(params), params).getTransform();

		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_TBOX")))
			tg = new BlockDecorator(new Block(params), params).getTransform();

		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_QUAD")))
			tg = new Ceiling(params).getTransform();
		
		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_BOX")))
			tg = new Block(params).getTransform();
		
		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_CYLINDER")))
			tg = new Column(params).getTransform();

		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_SBOX")))
			tg = new SphereDecorator(new Block(params), params).getTransform();

		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_RAMP")))
			tg = new Ramp(params).getTransform();

		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_RDOOR")))
			tg = new Door(params, Door.RIGHT_DOOR).getTransform();
		else if (type.equalsIgnoreCase(Config.getInstance().getStringVal("OBJECT_TYPE_LDOOR")))
			tg = new Door(params, Door.LEFT_DOOR).getTransform();

		return tg;
	}	

	/**
	 * 
	 * @param lightType
	 * @param params
	 * @return
	 */
	public Light createLigth(int lightType, ParamsLight params) 
	{
		Light light = null;
		if (lightType == AMBIENT_LIGHT) {
			light = getAmbientLightScene(params);
		}
		else if (lightType == DIRECTIONAL_LIGHT) {
			light = getDirectionalLight(params);
		}
		return light;
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	private AmbientLight getAmbientLightScene(ParamsLight params) {
		
		AmbientLight ambientLight = new AmbientLight(params.color);
		ambientLight.setInfluencingBounds(params.bounds);
		return ambientLight;
	}
	
	/**
	 * This method creates an oriented light to the scene
	 * @param white
	 * @param direction
	 * @param bounds
	 * @return
	 */
	private DirectionalLight getDirectionalLight(ParamsLight params)  
	{
		DirectionalLight light = new DirectionalLight(params.color, params.direction);
		light.setInfluencingBounds(params.bounds);
		return light;
	}
	
	/**
	 * 
	 * @param color
	 * @param texFnm
	 * @param shinines
	 * @return
	 */
	public Appearance createAppearance(Color3f color, String texFnm, float shinines) throws SystemException
	{
		Appearance app = new Appearance();
		
		TextureAttributes ta = new TextureAttributes();
		ta.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(ta);

		System.out.println("loading files.. "+Config.getInstance().getStringVal("IMAGE_DIRECTORY")+texFnm);
		TextureLoader loader = new TextureLoader(Config.getInstance().getStringVal("IMAGE_DIRECTORY")+texFnm, null);
		Texture2D texture = (Texture2D) loader.getTexture();
		app.setTexture(texture);
		
		Material mat = new Material(color, color, color, color, shinines);
		mat.setLightingEnable(true);
		app.setMaterial(mat);
		
		app.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0));
		return app;
	}	

}
