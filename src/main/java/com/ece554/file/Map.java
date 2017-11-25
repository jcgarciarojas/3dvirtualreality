package com.ece554.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
import java.util.Hashtable;

import com.ece554.exception.SystemException;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class Map {
    private char[][]map = null;
    private char[][]roof = null;
    private int heightMap;
    private int widthMap;
    
    public static final int ROOF_MAP =1;
    public static final int BUILDING_MAP =2;
    private static Hashtable instance = new Hashtable();
    
    public static final String FIRST_FLOOR = "FIRST_FLOOR_MAP"; 
    public static final String MIDDLE_FLOOR = "BASE_FLOOR_MAP"; 
    
    /**
     * 
     * @param file
     * @throws SystemException
     */
	private Map(String key) throws SystemException  
	{
		
		heightMap = Config.getInstance().getIntVal("HEIGHT_MAP");
		widthMap = Config.getInstance().getIntVal("WIDTH_MAP");
		map = new char[heightMap][widthMap];
		roof = new char[heightMap][widthMap];
		readArrayMap(Config.getInstance().getStringVal(key));
		
		if (Config.getInstance().getStringVal("ROOF_"+key) != null)
		{
			readArrayRoof(Config.getInstance().getStringVal("ROOF_"+key));
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws SystemException
	 */
	public static synchronized Map getInstance(String key) throws SystemException {
		
		if(instance.get(key) == null) {
			instance.put(key, new Map(key));
		}
		return (Map)instance.get(key);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getHeightMap() {
		return heightMap;
	}
	
	public int getWidthMap() {
		return widthMap;
	}
	
	/**
	 * 
	 * @throws SystemException
	 */
	private void readArrayMap(String fileName) throws SystemException{
		
	      BufferedReader br= null;
	      String line = null;
	      try {
	    	  //URL objURL = this.getClass().getResource(fileName);
		      //br = new BufferedReader(new InputStreamReader(objURL.openStream()));
		      br = new BufferedReader(new FileReader(new File(fileName)));
		      int x=0;
		      
		      while((line = br.readLine())!= null) {
		    	 int z=0;
		    	 while(z< line.length()) {
			    	  char c = line.charAt(z);
			    	  map[x][line.length()-z]=c;
			    	  z++;
			      }
			      x++;
		      }
	      }
	      catch(IOException io) {
	    	  throw new SystemException(io);
	      }
	}
	
	/**
	 * 
	 * @throws SystemException
	 */
	private void readArrayRoof(String fileName) throws SystemException{
	      BufferedReader br= null;
	      String line = null;
	      
	      try {
	    	  //URL objURL = this.getClass().getResource(fileName);
		      //br = new BufferedReader(new InputStreamReader(objURL.openStream()));
		      br = new BufferedReader(new FileReader(new File(fileName)));
		      int x=0;
		      
		      while((line = br.readLine())!= null) {
		    	 int z=0;
		    	 while(z< line.length()) {
			    	  char c = line.charAt(z);
			    	  roof[x][line.length()-z]=c;
			    	  z++;
			      }
			      x++;
		      }
	      }
	      catch(IOException io) {
	    	  throw new SystemException(io);
	      }
	}
	
	/**
	 * 
	 * @param type
	 * @param x
	 * @param z
	 * @return
	 */
	public String getElement(int type, int x, int z) 
	{
		String elem = null;
		if (type == ROOF_MAP) {
			elem = getElementRoof(x, z);
		}
		else if (type == BUILDING_MAP) {
			elem = getElementMap(x, z);
		}
		return elem;
	}

	/**
	 * 
	 * @param x
	 * @param z
	 * @return
	 */
	public String getElementMap(int x, int z) 
	{
		String item = null;

		if(x<heightMap && z<widthMap){
			char c = map[x][z];
			item = new String(""+c);
		}
		
		return item;
	}

	/**
	 * 
	 * @param x
	 * @param z
	 * @return
	 */
	public String getElementRoof(int x, int z) 
	{
		String item = null;

		if(x<heightMap && z<widthMap){
			char c = roof[x][z];
			item = new String(""+c);
		}
		
		return item;
	}

}
