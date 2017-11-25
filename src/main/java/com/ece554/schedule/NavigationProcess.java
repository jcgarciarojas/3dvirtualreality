package com.ece554.schedule;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.file.Map;
import com.ece554.gui.MapLocatorPanel;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;


/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class NavigationProcess {

	public static final int FORWARD = 0;
	public static final int LEFT = 1;
	public static final int BACK = 2;
	public static final int RIGHT = 3;
	
  	private static Map currentMap;
  	private static boolean isFirstFloor;
  	private Map firstFloor;
  	private Map basementMap;
	private static double ROTATE_STEP;
	private static List LIST_ELEMENTS;
	private static Vector3d vForwardMove;
	private static Vector3d vBackwardMove;
	private static Vector3d vDownForwardMove;
	private static Vector3d vUpBackwardMove;
	private static Vector3d noMove;
	
	private TransformGroup targetTG; 
  	private Transform3D t3dPossibleMove = new Transform3D();
  	private Transform3D t3dMove = new Transform3D();
  	private Transform3D t3dRotate = new Transform3D();
  	private Vector3d t3dReceptorTest = new Vector3d();
  	
  	private Point3d nextLoc; 
  	private Point3d prevLoc;
  	private double radians;
  	private int direction;
  	
  	private static int myFirstFloorPosition;
  	private static int myBaseFloorPosition;
  	
  	private static int minBoundary;
  	private static int maxBoundary;
  	private static int mapForward;
  	private static int mapBackward;		
  	private static int mapLeft;
  	private static int mapRight;
	
  	private MapLocatorPanel gMap;
  	
  	/**
  	 * 
  	 * @param targetTG
  	 * @param map
  	 * @param keycode
  	 */
	public NavigationProcess(TransformGroup targetTG, MapLocatorPanel gMap, Map firstFloor, Map basementMap, int keycode) {
		this.targetTG = targetTG;
		this.gMap = gMap;
		this.firstFloor = firstFloor;
		this.basementMap = basementMap;
		this.direction = keycode;
		initValues();
	}

	/**
	 * 
	 */
	private static void initValues()  {
		try {
			LIST_ELEMENTS = Config.getInstance().getListVal("ELEMENT_LIST");
		    double MOVE_STEP = Config.getInstance().getDoubleVal("MY_STEP_LENGTH");
		    ROTATE_STEP = Math.PI / Config.getInstance().getFloatVal("MY_ROTATION");
			vForwardMove = new Vector3d(0,0,-MOVE_STEP);
			vBackwardMove = new Vector3d(0,0,MOVE_STEP);
			noMove = new Vector3d(0,0,0);

			vDownForwardMove = new Vector3d(0,-1,0);
			vUpBackwardMove = new Vector3d(0,1,0);

		  	myFirstFloorPosition = Config.getInstance().getIntVal("MY_FIST_FLOOR_POS_Y");;
		  	myBaseFloorPosition = Config.getInstance().getIntVal("MY_BASE_FLOOR_POS_Y");		
		  	minBoundary = Config.getInstance().getIntVal("MIN_BOUNDARY");
		  	maxBoundary = Config.getInstance().getIntVal("MAX_BOUNDARY");
		  	
		  	mapForward = Config.getInstance().getIntVal("MAP_FORWARD");;
		  	mapBackward = Config.getInstance().getIntVal("MAP_BACKWARD");		
		  	mapLeft = Config.getInstance().getIntVal("MAP_LEFT");
		  	mapRight = Config.getInstance().getIntVal("MAP_RIGHT");
		} catch(SystemException se) {
			se.printStackTrace();
		}
	}	

	/**
	 * 
	 */
	public void testMove()
	{
		getPrevLocation();
		if(this.direction == KeyEvent.VK_UP) {
			this.nextLoc = testMove(vForwardMove);
		}
		else if(this.direction == KeyEvent.VK_DOWN) {
			this.nextLoc = testMove(vBackwardMove);
		}
		else if(this.direction == KeyEvent.VK_LEFT) {
			this.radians = ROTATE_STEP;
		}
		else if(this.direction == KeyEvent.VK_RIGHT) {
			this.radians = -ROTATE_STEP;
		}
	}
	
	public void getPrevLocation() {
		t3dPossibleMove.get(t3dReceptorTest);
		targetTG.getTransform(t3dPossibleMove);
		t3dMove.setTranslation(noMove);
		t3dPossibleMove.mul(t3dMove);
		t3dPossibleMove.get(t3dReceptorTest);
		prevLoc = new Point3d( t3dReceptorTest.x, t3dReceptorTest.y, t3dReceptorTest.z);
		
	}
	
	/**
	 * 
	 * @param theMove
	 * @return
	 */
	private Point3d testMove(Vector3d theMove)
	{
		t3dPossibleMove.get(t3dReceptorTest);
		targetTG.getTransform(t3dPossibleMove);
		t3dMove.setTranslation(theMove);
		t3dPossibleMove.mul(t3dMove);
		t3dPossibleMove.get(t3dReceptorTest);

		return new Point3d( t3dReceptorTest.x, t3dReceptorTest.y, t3dReceptorTest.z);
	}

	/**
	 * 
	 */
	public void move()
	{
		if(this.direction == KeyEvent.VK_UP) {
			moveFrontBack(vBackwardMove, mapBackward);
		}
		else if(this.direction == KeyEvent.VK_DOWN) {
			moveFrontBack(vForwardMove, mapForward);
		}
		else if(this.direction == KeyEvent.VK_LEFT) {
			rotateY(mapLeft);
		}
		else if(this.direction == KeyEvent.VK_RIGHT) {
			rotateY(mapRight);
		}
		prevLoc = nextLoc;
	}

	/**
	 * 
	 */
	private void rotateY(int dir)
	{
		targetTG.getTransform(t3dPossibleMove);
		t3dRotate.rotY(this.radians);
		t3dPossibleMove.mul(t3dRotate);
		targetTG.setTransform(t3dPossibleMove);
		
		gMap.setRotation(dir);
	}
	
	/**
	 * 
	 */
	private void moveFrontBack(Vector3d move, int dir)
	{
		if (isNoCollision(nextLoc.x, nextLoc.z, nextLoc.y)) {
			//commits values set in testMove 
			targetTG.setTransform(t3dPossibleMove);   
			if (isVerticalMoveOk())
				targetTG.setTransform(t3dPossibleMove);   
			gMap.setMove(dir, isFirstFloor);
		}
	} 

	/**
	 * 
	 * @param xWorld
	 * @param zWorld
	 * @return
	 */
	private boolean isNoCollision(double xWorld, double zWorld, double yWorld) 
	{
		boolean value = true;

		int x = (int) Math.round(xWorld);
		int z = (int) Math.round(zWorld);
		//int y = (int) Math.round(yWorld);
	  
		//System.out.println(x+", "+y+", "+z);
		if (currentMap == null) {
			currentMap = firstFloor;
			isFirstFloor = true;
		}
		//I do not need to check for negative values because we loaded the map form 0,0
		if (x>maxBoundary || z>maxBoundary ||  x<minBoundary || z<minBoundary) return false;
		if (x > 0 && z > 0) {
			String elem =currentMap.getElementMap(x, z);
			//The object in the map is restricted.
			try{
				if (Config.getInstance().getBooleanVal("CHECK_COLLISSION_"+elem)) 
					if(LIST_ELEMENTS.contains(elem)) value = false;
			} catch(SystemException se) {}
		}
		return value;
	}	  
	
	/**
	 * 
	 * @return
	 */
	private boolean isVerticalMoveOk()
	{
		boolean value = false;
		int xCurrent = (int) Math.round(nextLoc.x);
		int zCurrent = (int) Math.round(nextLoc.z);
		int yCurrent = (int) Math.round(nextLoc.y);
	  
		int xPrev = (int) Math.round(prevLoc.x);

		if (zCurrent == 16 && (xCurrent>=14 && xCurrent<=17))
		{
			targetTG.setTransform(t3dPossibleMove);  
			t3dPossibleMove.get(t3dReceptorTest);
			targetTG.getTransform(t3dPossibleMove);
			
			Vector3d v = null; 
			
			//normal down
			if (isFirstFloor && xPrev == 14 && xCurrent == 15 && yCurrent > myBaseFloorPosition) {
				v = vDownForwardMove;
				value = true;
			}
			else if (isFirstFloor && xPrev == 15 && xCurrent == 16 && yCurrent > myBaseFloorPosition) {
				v = vDownForwardMove;
				value = true;
			}
			//rotation up
			else if (xPrev == 15 && xCurrent == 14 && yCurrent < myFirstFloorPosition) {
				v = vUpBackwardMove;
				value = true;
			}
			else if (xPrev == 16 && xCurrent == 15 && yCurrent < myFirstFloorPosition) {
				v = vUpBackwardMove;
				value = true;
			}

			//normal up
			else if (!isFirstFloor && xPrev == 16 && xCurrent == 15 && yCurrent < myFirstFloorPosition) {
				v = vUpBackwardMove;
				value = true;
			}
			else if (!isFirstFloor && xPrev == 17 && xCurrent == 16 && yCurrent < myFirstFloorPosition) {
				v = vUpBackwardMove;
				value = true;
			}
			//rotation down
			else if (xPrev == 15 && xCurrent == 16 && yCurrent > myBaseFloorPosition) {
				v = vDownForwardMove;
				value = true;
			}
			else if (xPrev == 16 && xCurrent == 17 && yCurrent > myBaseFloorPosition) {
				v = vDownForwardMove;
				value = true;
			}

			if (value && v != null) {
				t3dMove.setTranslation(v);
				t3dPossibleMove.mul(t3dMove);
				t3dPossibleMove.get(t3dReceptorTest);
			}
		}

		if (yCurrent == myBaseFloorPosition) {
			currentMap = basementMap;
			isFirstFloor = false;
		}
		else {
			currentMap = firstFloor;
			isFirstFloor = true;
		}
		return value;
	}	  


}
