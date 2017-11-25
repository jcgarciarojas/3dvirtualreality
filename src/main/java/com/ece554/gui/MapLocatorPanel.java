package com.ece554.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

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

public class MapLocatorPanel extends JPanel {
	
	public static final long serialVersionUID=190;
	private Map mapFirstFloor;
	private Map mapBasement;
	private BufferedImage imageFirstFloor;
	private BufferedImage imageBasement;
	private BufferedImage currentImage; 
	
  	private static int moveForward;
  	private static int moveBackward;		
  	private static int moveLeft;
  	private static int moveRight;
  	private int currentMove;
  	private static Hashtable moves = new Hashtable(); 
  	

	private Image me;
	private Point point;
	private int step = 240/40;
	private Font font = null;

	/**
	 * 
	 * @throws SystemException
	 */
	public MapLocatorPanel () throws SystemException {
	    setBackground(Color.white);
	    setPreferredSize( new Dimension(150, 240));

		this.mapFirstFloor = Map.getInstance(Map.FIRST_FLOOR);
		this.mapBasement = Map.getInstance(Map.MIDDLE_FLOOR);
		//int l = 240;
		imageFirstFloor = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);
		imageBasement = new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);

		generateMap((Graphics) imageFirstFloor.createGraphics(), mapFirstFloor, "MAIN LEVEL");
		generateMap((Graphics) imageBasement.createGraphics(), mapBasement, "MIDDLE LEVEL");
		currentImage = imageFirstFloor;
	    
	  	moveForward = Config.getInstance().getIntVal("MAP_FORWARD");;
	  	moveBackward = Config.getInstance().getIntVal("MAP_BACKWARD");		
	  	moveLeft = Config.getInstance().getIntVal("MAP_LEFT");
	  	moveRight = Config.getInstance().getIntVal("MAP_RIGHT");
	  	currentMove = moveForward;
	  	
	  	moves.put(""+moveForward, new Point(1,0)); 
	  	moves.put(""+moveBackward, new Point(-1,0)); 
	  	moves.put(""+moveLeft, new Point(0,-1)); 
	  	moves.put(""+moveRight, new Point(0,1)); 
	  	font = new Font("SansSerif", Font.BOLD, 24);
	  	
	  	initPosition();
	    repaint();

	}
	/**
	 * 
	 * @throws SystemException
	 */
	private void initPosition() throws SystemException {
		ImageIcon meIcon = new ImageIcon(Config.getInstance().getStringVal("MY_IMAGE")); 
	    me = meIcon.getImage();
	    int z = new Double(Config.getInstance().getStringVal("MY_INIT_POS_Z")).intValue();
	    int x = new Double(Config.getInstance().getStringVal("MY_INIT_POS_X")).intValue();
		point = new Point(x,z);
	}
	
	/**
	 * 
	 * @param dir
	 * @param isFirstFloor
	 */
	public void setMove(int dir, boolean isFirstFloor)
	{
		if (isFirstFloor) {
			currentImage = imageFirstFloor;			
		} else {
			currentImage = imageBasement;	
		}
	    int actualHd = (currentMove + dir) % 4;
	    Point move = (Point)moves.get(""+actualHd);
	    point.x += move.x;
	    point.y += move.y;
		repaint();
	}

	public void setRotation(int dir)
	{ 
		currentMove = (currentMove + dir) % 4;
		
	}

	/**
	 * 
	 */
	public void paintComponent(Graphics g)
	{ 
		super.paintComponent(g);
		g.drawImage(currentImage, 0, 0, null);
		int xPos = point.x * step;
		int yPos = point.y * step;
		g.drawImage(me, xPos, yPos, null);
	      
	}

	/**
	 * 
	 * @param g
	 * @param map
	 * @param text
	 * @throws SystemException
	 */
	protected void generateMap(Graphics g, Map map, String text) throws SystemException {
	    g.setColor(Color.white);
	    List l = Config.getInstance().getListVal("ELEMENT_LIST");
	    int x=0;
	    Color c = null;
	    while( x< map.getHeightMap()) {
	    	int z=0;
	    	while (z< map.getWidthMap()) {
	    		String elem = map.getElementMap(x, z);
    			if (elem != null && l.contains(elem) && Config.getInstance().getBooleanVal("CHECK_COLLISSION_"+elem))
    			{
					if(elem.equals("b"))
		    			c = Color.blue;
		    		else
		    			c = Color.CYAN;
					g.setColor(c);
					g.fillRect(x*step, z*step, step, step);
    			}
	    		z++;
	    	}
	    	x++;
	    }
		g.setColor(Color.BLACK);
        g.setFont(font);
    	g.drawString(text, x, 10);
	    g.dispose();
	}

}
