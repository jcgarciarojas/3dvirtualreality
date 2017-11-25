package com.ece554.objects;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.media.j3d.Appearance;
import javax.media.j3d.TransformGroup;

import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.file.Map;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class MapStructure extends Structure {

	private Map map;
	
	Hashtable appearances;
	Hashtable dimensions;
	
	/**
	 * 
	 * @param params
	 * @throws SystemException
	 */
	public MapStructure(ParamsObject params, Map map) throws SystemException {
		super(params);
		this.map = map;
		initVariables();
		initAppearances();
	}
	
	/**
	 * 
	 * @throws SystemException
	 */
	public void initVariables() throws SystemException {
		//mazeImg = new BufferedImage(IMAGE_LEN, IMAGE_LEN, BufferedImage.TYPE_INT_ARGB_PRE);
		//map = Map.getInstance(Config.getInstance().getStringVal("FILE_NAME_MAP"));
		dimensions = new Hashtable();
	}

	/**
	 * 
	 */
	public Structure createObject() throws SystemException {
		
		createObject(Map.BUILDING_MAP); 
		createObject(Map.ROOF_MAP);
		return this;
	}
	
	/**
	 * 
	 * @param type
	 * @throws SystemException
	 */
	protected void createObject(int type) throws SystemException {

	    int x=0;

	    //SharedGroup share = new SharedGroup();
	    while( x< map.getHeightMap()) {

	    	int z=0;
	    	while (z< map.getWidthMap()) {
		    	String elem = map.getElement(type, x, z);
		    	TransformGroup tg = null;

		    	if (elem != null) {
		    		//if (!elem.equals("Z") && !elem.equals("x")) {z++; continue; };
		    		List l = (List)appearances.get(elem);
		    		if (l != null && l.size() > 0 ) {
		    			setParams(super.getParams(), elem, x, z, l);
		    			String objecType = Config.getInstance().getStringVal("OBJECT_TYPE_"+elem);
	    				tg = ObjectFactory.getInstance().createObject(objecType, super.getParams());
		    		}
		    	}
    			getBranchGroup().addChild(tg);
    			z++;
		    }
	    	x++;
	    }
		//g.dispose();
	}
	
	/**
	 * 
	 * @param params
	 * @param elem
	 * @param x
	 * @param z
	 * @param app
	 * @throws SystemException
	 */
	private void setParams(ParamsObject params, String elem, float x, float z, List l) throws SystemException
	{
		params.init();
		params.listAppearances.addAll(l);
		params.x = x;
		params.z = z;
		params.y = Config.getInstance().getFloatVal("OBJECT_POS_Y_"+elem)+params.height;
		params.widthX = Config.getInstance().getFloatVal("DIMENSIONS_X_"+elem);
		params.widthZ = Config.getInstance().getFloatVal("DIMENSIONS_Z_"+elem);
		params.heightY = Config.getInstance().getFloatVal("DIMENSIONS_Y_"+elem);
		params.angleZ = Config.getInstance().getFloatVal("ANGLE_Z_"+elem);
		params.angleX = Config.getInstance().getFloatVal("ANGLE_X_"+elem);

		if (l.size() > 1) {
			if (Config.getInstance().getStringVal("OBJECT_POS_Y1_"+elem) != null)
			{
				params.x1 = Config.getInstance().getFloatVal("OBJECT_POS_X1_"+elem);
				params.y1 = Config.getInstance().getFloatVal("OBJECT_POS_Y1_"+elem)-params.height;
				params.z1 = Config.getInstance().getFloatVal("OBJECT_POS_Z1_"+elem);
				params.widthX1 = Config.getInstance().getFloatVal("DIMENSIONS_X1_"+elem);
				params.widthZ1 = Config.getInstance().getFloatVal("DIMENSIONS_Z1_"+elem);
				params.heightY1 = Config.getInstance().getFloatVal("DIMENSIONS_Y1_"+elem);
			}
		}
		
		String action = Config.getInstance().getStringVal("ACTION_"+elem);
		if (action != null) params.action = action;
		
	}

	
	/**
	 * 
	 * @throws SystemException
	 */
	private void initAppearances() throws SystemException {
		appearances = new Hashtable();
		Iterator it = Config.getInstance().getListVal("ELEMENT_LIST").iterator();
		while(it.hasNext()) {
			String key = it.next().toString();
			String files = Config.getInstance().getStringVal("FILE_NAME_"+key);
			StringTokenizer tk = new StringTokenizer(files, ",");
			List v = new Vector();
			while(tk.hasMoreTokens()) {
				String file = tk.nextToken();
				Appearance app = ObjectFactory.getInstance().createAppearance(ColorFactory.getLightGray(), file, 120f);
				v.add(app);
			}
			appearances.put(key, v);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Map getMap() {
		return map;
	}
	
}