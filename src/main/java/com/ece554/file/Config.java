package com.ece554.file;

import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.*;
//import java.net.URL;

import com.ece554.exception.SystemException;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */

public class Config {
	
	private static final String PROP_FILE_NAME ="library.properties";

	private static Config instance;
	private static Properties props = null; 
	
	//initialize the factory 
	private Config () throws SystemException
	{
		props = this.getProperties();
	}

	/**
	 * This is the method of the singleton that gets the instance of the class
	 * @return
	 * @throws DictionaryException
	 */
	public static Config  getInstance() throws SystemException
	{
		if (instance == null) {
			instance = new Config ();
		}

		return instance;
	}



	/**
	 * This methods reads the properties file, 
	 * this file contains the mapping of the architecture and the factory class 
	 * @return
	 * @throws DictionaryException
	 */
	private Properties getProperties() throws SystemException {

		Properties props = new Properties ( ) ;
		try {

			//URL objURL = this.getClass().getResource(PROP_FILE_NAME);
			//props.load(objURL.openStream());
			props.load ( new FileInputStream ( new File ( PROP_FILE_NAME ) ) ) ;

		} catch ( IOException ie ) {
			throw new SystemException(ie+"Error reading file"); 
		}
		
		return props;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getStringVal(String key) {
		return (String)props.get(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getIntVal(String key) {
		String val = (String)props.get(key);
		if (val == null) return 0; 
		return new Integer((String)val).intValue();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public float getFloatVal(String key) {
		String val = (String)props.get(key);
		if (val == null) return 0.f; 
		else return new Float(val).floatValue();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getLongVal(String key) {
		String val = (String)props.get(key);
		if (val == null) return 0; 
		else return new Long(val).longValue();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public double getDoubleVal(String key) {
		String val = (String)props.get(key);
		if (val == null) return 0.0;
		else return new Double(val).doubleValue();
	}	

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBooleanVal(String key) {
		String val = (String)props.get(key);
		if (val != null)
			return new Boolean(val).booleanValue();
		else return true;
	}	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public List getListVal(String key) {
		
		List l = new Vector();
		String listVal = (String)props.get(key);
		String delimiter = getStringVal("LIST_DELIMITER");		
		StringTokenizer st = new StringTokenizer(listVal, delimiter);
		
		while(st.hasMoreTokens()) {
			String val = st.nextToken();
			l.add(val);
		}
		return l;
	}
	

}
