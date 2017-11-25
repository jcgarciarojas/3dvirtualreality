package com.ece554.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.ece554.events.KeyVirtualEvent;
import com.ece554.events.MouseVirtualEvent;
import com.ece554.exception.SystemException;
import com.ece554.file.Config;
import com.ece554.file.Map;
import com.ece554.objects.ColorFactory;
import com.ece554.objects.ObjectFactory;
import com.ece554.objects.ParamsLight;
import com.ece554.objects.ParamsObject;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */
public class VirtualWorld extends JPanel{
	public static final long serialVersionUID =0;

	private SimpleUniverse su;
	private static int BOUNDSIZE = 100;
	private BoundingSphere bounds;
	private Transform3D t3d;
	private Map firstFloorMap;
	private Map basementMap;
	private PickCanvas pickCanvas;
	public static Canvas3D canvas3D;
	//private TransformGroup tgBackCCamera;
	private MapLocatorPanel gMap;

	/**
	 * 
	 */
	public VirtualWorld(MessagePanel mPanel, MapLocatorPanel gMap) throws SystemException {

		this.gMap = gMap;
		firstFloorMap = Map.getInstance(Map.FIRST_FLOOR);
		basementMap = Map.getInstance(Map.MIDDLE_FLOOR);
		setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        canvas3D = new Canvas3D(config);
        add(BorderLayout.CENTER, canvas3D);
        su = new SimpleUniverse(canvas3D);

        BranchGroup sceneBG = createSceneGraph();

        initUserPosition(sceneBG);
        setEvents(sceneBG, mPanel);
        
        //sceneBG.addChild(this.tgBackCCamera);
        sceneBG.compile();
        su.addBranchGraph(sceneBG);
	}

	/**
	 * This method creates the scene, in other words it adds the floor, cealing to the scene
	 * @return
	 */
	protected BranchGroup createSceneGraph() throws SystemException 
	{

		BranchGroup sceneBG = new BranchGroup();
	    createLights(sceneBG);
	    sceneBG.addChild(addBackground());
	    boolean isFirstFloor = true;
	    float positionY = Config.getInstance().getFloatVal("FIRST_FLOOR_POSITION_Y"); 
	    sceneBG.addChild(ObjectFactory.getInstance().createVisualStructure(firstFloorMap, ObjectFactory.OBJECT_FLOOR, getParamsFloor(isFirstFloor, positionY)).createObject().getBranchGroup());
	    sceneBG.addChild(ObjectFactory.getInstance().createVisualStructure(firstFloorMap, ObjectFactory.OBJECT_STRUCTURE, getParamsStructure(isFirstFloor, positionY)).createObject().getBranchGroup());
	    isFirstFloor = false;
	    positionY = Config.getInstance().getFloatVal("BASEMENT_FLOOR_POSITION_Y"); 
	    sceneBG.addChild(ObjectFactory.getInstance().createVisualStructure(basementMap, ObjectFactory.OBJECT_FLOOR, getParamsFloor(isFirstFloor, positionY)).createObject().getBranchGroup());
	    sceneBG.addChild(ObjectFactory.getInstance().createVisualStructure(basementMap, ObjectFactory.OBJECT_STRUCTURE, getParamsStructure(isFirstFloor, positionY)).createObject().getBranchGroup());
	    
		return sceneBG;
	}
	
	/**
	 * 
	 * @param sceneBG
	 */
	private void createLights(BranchGroup sceneBG) {
		bounds = new BoundingSphere(new Point3d(0,0,0), BOUNDSIZE);

		ParamsLight params = new ParamsLight();
		params.bounds = bounds;
		params.color = ColorFactory.getWhite();
		sceneBG.addChild((AmbientLight)ObjectFactory.getInstance().createLigth(ObjectFactory.AMBIENT_LIGHT, params));

		params.direction = new Vector3f(-1.0f, -1.0f, -1.0f);
		sceneBG.addChild((DirectionalLight)ObjectFactory.getInstance().createLigth(ObjectFactory.DIRECTIONAL_LIGHT, params));

		params.direction = new Vector3f(1.0f, -1.0f, 1.0f);
		sceneBG.addChild((DirectionalLight)ObjectFactory.getInstance().createLigth(ObjectFactory.DIRECTIONAL_LIGHT, params));		
	}
	
	/**
	 * This method adds the sky like background to the scene
	 * @return
	 */
	public Background addBackground() throws SystemException 
	{
		TextureLoader texture = new TextureLoader(Config.getInstance().getStringVal("FILE_NAME_SKY"), null);
		Sphere sphere = new Sphere(1.0f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD|
								Sphere.GENERATE_TEXTURE_COORDS, 4);
		Appearance ap = sphere.getAppearance();
		ap.setTexture(texture.getTexture());

		BranchGroup bg = new BranchGroup();
		bg.addChild(sphere);
		
		Background back = new Background();
		back.setApplicationBounds(bounds);
		back.setGeometry(bg);
		return back;
	}
	
	/**
	 * 
	 * @param sceneBG
	 * @throws SystemException
	 */
	private void initUserPosition(BranchGroup sceneBG) throws SystemException
	{
	    View userView = su.getViewer().getView();
	    userView.setFieldOfView( Math.toRadians(90.0));
	    //For long distances
	    userView.setBackClipDistance(20);
	    //for close objects 
	    userView.setFrontClipDistance(0.05);

	    ViewingPlatform vp = su.getViewingPlatform();

	    PlatformGeometry pg = new PlatformGeometry();
	    vp.setPlatformGeometry( pg );

	    TransformGroup steerTG = vp.getViewPlatformTransform();
	    initViewPosition(steerTG);
	}

	/**
	 * 
	 * @param steerTG
	 * @throws SystemException
	 */
	private void initViewPosition(TransformGroup steerTG) throws SystemException
	{
	    t3d = new Transform3D();
	    steerTG.getTransform(t3d);
	    Transform3D toRot = new Transform3D();
	    toRot.rotY(Math.PI/2);   
	    //toRot.rotY(-Math.PI);   
	
	    t3d.mul(toRot);
	    
	    t3d.setTranslation( new Vector3f(Config.getInstance().getFloatVal("MY_INIT_POS_X"),
							Config.getInstance().getFloatVal("MY_INIT_POS_Y"),
							Config.getInstance().getFloatVal("MY_INIT_POS_Z")));
	    steerTG.setTransform(t3d); 
	}

	/**
	 * 
	 * @param sceneBG
	 * @param mPanel
	 * @throws SystemException
	 */
	public void setEvents(BranchGroup sceneBG, MessagePanel mPanel)  throws SystemException {
		ViewingPlatform vp = su.getViewingPlatform();
		
		//setting key press event
	    KeyVirtualEvent keyEvent = new KeyVirtualEvent(firstFloorMap, basementMap, gMap);
	    keyEvent.setSchedulingBounds(new BoundingSphere(new Point3d(),400.0));
	    vp.setViewPlatformBehavior(keyEvent);
	    
	    //set mouse event
		pickCanvas = new PickCanvas( canvas3D, sceneBG );
		pickCanvas.setMode( PickTool.GEOMETRY_INTERSECT_INFO );
		pickCanvas.setTolerance( 4.0f );
		canvas3D.addMouseListener( new MouseVirtualEvent(pickCanvas, mPanel) );
		canvas3D.setCursor( new Cursor( Cursor.HAND_CURSOR ) );
		
	}
	
	protected ParamsObject getParamsFloor(boolean isFirstFloor, float yPosition) throws SystemException {
		ParamsObject params = new ParamsObject(); 
		params.isFirstFloor = isFirstFloor;
		params.height = yPosition;
		params.length = Config.getInstance().getIntVal("LENGTH_FLOOR");
		params.initialPoint = Config.getInstance().getIntVal("INITIAL_POINT_FLOOR");
		params.step = Config.getInstance().getIntVal("LENGTH_STEP");
		params.textureFile = Config.getInstance().getStringVal("FILE_NAME_FLOOR");		
		return params;
	}
	
	protected ParamsObject getParamsStructure(boolean isFirstFloor, float yPosition) {
		ParamsObject params = new ParamsObject(); 
		params.height = yPosition;
		params.isFirstFloor = isFirstFloor;
		
		return params;
	}
}
