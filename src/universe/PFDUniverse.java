/* 
 * PFD - A 3D paintball fields designer.
 * Copyright (C) 2010 PFD Luis Ciudad García
 * 
 * This file is part of PFD - Paintball Field Designer.
 *
 * PFD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PFD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PFD.  If not, see <http://www.gnu.org/licenses/>.
 */

package universe;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import util.PFDUtil;

import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.picking.PickCanvas;

import core.PFDBunker;
import core.PFDBunkerType;
import core.PFDField;
import core.PFDPosition;
import core.PFDSelectionEvent;
import core.PFDSelectionListener;

/**
 * Esta es la clase sobre la que gira la aplicación, se encarga de mantener la escena
 * que representa un campo de paintball (Sup'Airball). Para ello es capaz de
 * agregar, modificar y eliminar obstaculos a partir de un identificador, que debe
 * garantizar único para cada obstaculo, y otras propiedades necesarias en función
 * de la operación a realizar.
 * El universo esta formado por varias ramas:
 *    -Un conjunto de ramas que mantiene propiedades de la escena como luces, 
 *     fondo, etc.
 *    -Un conjunto de ramas que representan cada uno de los bunkers agregados
 *     al terreno de juego.
 *    -Una rama de previsualización del bunker a agregar en la escena.
 *    
 * Este objeto se encarga además de disparar los eventos necesarios para hacer
 * saber a la interfaz de usuario los cambios que están ocurriendo en la escena.
 * 
 * @author Luis Ciudad Garcia
 *
 * @see PFDBunkerGraph
 * @see PFDBunkerPreviewGraph
 * @see PFDFieldGraph
 */

public class PFDUniverse extends Canvas3D implements MouseListener, MouseMotionListener {
	static final long serialVersionUID = 1L;
	
	public static final int MODE_SELECT = 0;
	public static final int MODE_DRAW = 1;
	public static final int MODE_FREECAMERA = 2;
	public static final int MODE_FPCAMERA = 3;
	
	private static final BoundingSphere BOUNDINGS = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
	private static final Color3f BACKGROUND_COLOR = new Color3f(0.7f,0.7f,0.7f);
	private static final Color3f AMBIENTLIGHT_COLOR = new Color3f(0.6f,0.6f,0.6f);
	private static final double BACK_CLIP_DISTANCE = 100.0d;
	
	private boolean ready;
	private int mode;
	
	private SimpleUniverse SU;
	private BranchGroup initialBranch;
		private Background backgroundLeaf;
		private AmbientLight ambientLightLeaf;
		private BranchGroup bunkersBranch;
		private PFDFieldGraph fieldBranch;
		
	private PFDBunkerPreviewGraph previewBunker;
	private Shape3D selectedModel;
	
	private int count = 1;
	
	private List listeners = new ArrayList();
	
	private Robot mousetrap;
	
	public PFDUniverse(){
		super(SimpleUniverse.getPreferredConfiguration());
		
		this.ready = false;
		
		this.previewBunker = new PFDBunkerPreviewGraph(this, PFDUniverse.BOUNDINGS);
		
		this.initialBranch = new BranchGroup();
			this.initialBranch.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
			
			this.backgroundLeaf = this.createBackground();
			this.ambientLightLeaf = this.createLights();
			this.bunkersBranch = this.createBunkersBranch();
				this.bunkersBranch.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
				this.bunkersBranch.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
			
			this.initialBranch.addChild(this.backgroundLeaf);
	        this.initialBranch.addChild(this.ambientLightLeaf);
	        this.initialBranch.addChild(this.bunkersBranch);    
	        
		this.initialBranch.compile();
		
		this.SU = new SimpleUniverse(this);
        this.SU.addBranchGraph(this.initialBranch);
        
        try{ this.mousetrap = new Robot(); }
		catch(Exception e){}
        
		
        this.addMouseListener(this);
		}
	
	public void mouseMoved(MouseEvent e){
		mousetrap.mousePress(InputEvent.BUTTON1_MASK);
		mousetrap.mouseMove(this.getLocationOnScreen().x + this.getWidth()/2, this.getLocationOnScreen().y + this.getHeight()/2);
		mousetrap.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void mouseDragged(MouseEvent e){}
	
	public boolean isInitialized(){
		return this.ready;
		}
	public void setPreviewModel(Shape3D model){
		if(!this.isInitialized()) return;
		
		this.previewBunker.setModel(model);
		}
	public void toggleMode(int mode){
		if(!this.isInitialized()) return;
		
		this.mode = mode;
		this.setCameraByMode(this.mode);
		
		//Quitamos todo lo que haya y ponemos lo que queremos
		this.previewBunker.setBehaviorEnable(false);
		this.bunkersBranch.removeChild(this.previewBunker);
		
		switch(this.mode){
			case PFDUniverse.MODE_DRAW:
				this.previewBunker.setBehaviorEnable(true);
				this.bunkersBranch.addChild(this.previewBunker);
				break;
			}
		}

	/**************************************************
					CREATING UNIVERSE
	**************************************************/	
	private Background createBackground(){
		Background background = new Background(PFDUniverse.BACKGROUND_COLOR);
    	background.setApplicationBounds(PFDUniverse.BOUNDINGS);
    		
    	return background;
		}
	private AmbientLight createLights(){
		AmbientLight ambientLight = new AmbientLight(PFDUniverse.AMBIENTLIGHT_COLOR);
			ambientLight.setInfluencingBounds(PFDUniverse.BOUNDINGS);
		
		//Probando 1... 2...
			SpotLight dL1 = new SpotLight(
						PFDUniverse.AMBIENTLIGHT_COLOR,
						new Point3f(0.0f,0.0f,10.0f),
						new Point3f(1.5f,0.0f,0.0f),
						new Vector3f(1.0f,1.0f,-1.0f),
						(float)Math.PI,
						0.0f);
			SpotLight dL2 = new SpotLight(
					PFDUniverse.AMBIENTLIGHT_COLOR,
					new Point3f(30.0f,0.0f,10.0f),
					new Point3f(1.5f,0.0f,0.0f),
					new Vector3f(-1.0f,1.0f,-1.0f),
					(float)Math.PI,
					0.0f);
			SpotLight dL3 = new SpotLight(
					PFDUniverse.AMBIENTLIGHT_COLOR,
					new Point3f(30.0f,40.0f,10.0f),
					new Point3f(1.5f,0.0f,0.0f),
					new Vector3f(-1.0f,-1.0f,-1.0f),
					(float)Math.PI,
					0.0f);
			SpotLight dL4 = new SpotLight(
					PFDUniverse.AMBIENTLIGHT_COLOR,
					new Point3f(0.0f,40.0f,10.0f),
					new Point3f(1.5f,0.0f,0.0f),
					new Vector3f(1.0f,-1.0f,-1.0f),
					(float)Math.PI,
					0.0f);
			dL1.setInfluencingBounds(PFDUniverse.BOUNDINGS);
			dL2.setInfluencingBounds(PFDUniverse.BOUNDINGS);
			dL3.setInfluencingBounds(PFDUniverse.BOUNDINGS);
			dL4.setInfluencingBounds(PFDUniverse.BOUNDINGS);
			this.initialBranch.addChild(dL1);
			this.initialBranch.addChild(dL2);
			this.initialBranch.addChild(dL3);
			this.initialBranch.addChild(dL4);
		//fin del probando! ;)
			
		return ambientLight;
		}
	private BranchGroup createBunkersBranch(){
		BranchGroup bunkersBranch = new BranchGroup();
			bunkersBranch.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
			bunkersBranch.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
			
		return bunkersBranch;
		}
	
	private void setCameraByMode(int mode){
		if(!this.isInitialized()) return;
		
		Vector3d camVector = new Vector3d();
		TransformGroup viewTG = this.SU.getViewingPlatform().getMultiTransformGroup().getTransformGroup(0);
		int width = this.fieldBranch.fieldProperties.width;
		int height = this.fieldBranch.fieldProperties.height;
		
		switch(mode){
			case PFDUniverse.MODE_SELECT:
			case PFDUniverse.MODE_DRAW:
				camVector = new Vector3d(width/2, height/2, (width >= height ? 1.5 * width : 1.5 * height ));
				
				Transform3D moveCamera = new Transform3D();
		        	moveCamera.setTranslation(camVector);
		            viewTG.setTransform(moveCamera);
				
				break;
			case PFDUniverse.MODE_FPCAMERA:
			case PFDUniverse.MODE_FREECAMERA:
				this.addMouseMotionListener(this);
				
				camVector = new Vector3d(width/2, 10, 1.75f);
				
				Transform3D transform = new Transform3D();
				//viewTG.getTransform(transform);
				
				Transform3D translator = new Transform3D();
					translator.setTranslation(camVector);
				Transform3D rotator = new Transform3D();
					rotator.rotX(Math.PI/2);
				transform.mul(rotator);
				transform.mul(translator);
				
				viewTG.setTransform(transform);
				
				PFDCameraBehavior behavior = new PFDCameraBehavior(viewTG, this);
				   BranchGroup inter = new BranchGroup();
				   inter.addChild(behavior);
				   viewTG.addChild(inter);
				   behavior.setSchedulingBounds(PFDUniverse.BOUNDINGS);

				break;
			}
		
       
            
        this.SU.getViewer().getView().setBackClipDistance(PFDUniverse.BACK_CLIP_DISTANCE);
        this.SU.getViewer().getView().setFrontClipDistance(0.0001);

		}
	
	public void resetUniverse(){
		this.bunkersBranch.removeAllChildren();
		//this.initialBranch.removeChild(this.fieldBranch);
		this.fieldBranch = null;
		this.previewBunker = new PFDBunkerPreviewGraph(this, PFDUniverse.BOUNDINGS);
		
		this.ready = false;
		this.mode = PFDUniverse.MODE_SELECT;
		}
	
	/**************************************************
					EDITING FIELD
	**************************************************/
	public void setField(PFDField field, ImageComponent2D textureImage){
		this.fieldBranch = new PFDFieldGraph(field, textureImage);
		this.initialBranch.addChild(this.fieldBranch);
		
		this.ready = true;
		this.mode = PFDUniverse.MODE_SELECT;
		
		this.setCameraByMode(PFDUniverse.MODE_SELECT);
		}
	
	
	/**************************************************
					EDITING BUNKERS
	**************************************************/
	public boolean existsBunkerById(String id){
		for(Enumeration<PFDBunkerGraph> e = this.bunkersBranch.getAllChildren(); e.hasMoreElements();){
	        PFDBunkerGraph bunker = (PFDBunkerGraph)e.nextElement();
	        
	        if(bunker.bunkerProperties.id == id)
	        	return true;
	    	}
		
		return false;
		}	
	private PFDBunkerGraph getBunkerGraphById(String id){
		for(Enumeration<PFDBunkerGraph> e = this.bunkersBranch.getAllChildren(); e.hasMoreElements();){
	        PFDBunkerGraph bunker = (PFDBunkerGraph)e.nextElement();
	        
	        if(bunker.bunkerProperties.id == id)
	        	return bunker;
	    	}
		
		return null;
		}
	
	public void addBunker(PFDBunker bunker, Shape3D model) throws Exception{
		if(!this.isInitialized()) throw new Exception("Uninicilized scene");	
		if(this.existsBunkerById(bunker.id)) throw new Exception("Invalid bunker ID");
		
		PFDBunkerGraph bunkerGraph = new PFDBunkerGraph(bunker, model);
		
		this.bunkersBranch.addChild(bunkerGraph);
		}
	public void editBunker(String id, PFDBunker bunker) throws Exception{
		if(!this.isInitialized()) throw new Exception("Uninicilized scene");	
		if(!this.existsBunkerById(id)) throw new Exception("Invalid bunker ID");
		
		PFDBunkerGraph bunkerGraph = this.getBunkerGraphById(id);
		bunkerGraph.editBunkerProperties(bunker);
		}
	public void removeBunker(String id) throws Exception{
		if(!this.isInitialized()) throw new Exception("Uninicilized scene");	
		if(!this.existsBunkerById(id)) throw new Exception("Invalid bunker ID");
		
		PFDBunkerGraph bunkerGraph = this.getBunkerGraphById(id);
		this.bunkersBranch.removeChild(bunkerGraph);
		}
	public void removeAllBunkers() throws Exception{
		if(!this.isInitialized()) throw new Exception("Uninicilized scene");
		
		this.bunkersBranch.removeAllChildren();
		}
	
	public void editPreviewBunker(PFDBunkerType bunkerType){
		PFDBunker bunker = new PFDBunker("",bunkerType.id, new PFDPosition(), 0);
		this.previewBunker.editBunkerProperties(bunker);
		}
	
	public void mirrorFunction(){}
	
	/**************************************************
	 				MOUSE ACTIONS
	**************************************************/
	public void mouseEntered(MouseEvent event){}
	public void mouseExited(MouseEvent event){}
	public void mouseClicked(MouseEvent event){
		if(!this.isInitialized()) return;
		
		switch(this.mode){
			case PFDUniverse.MODE_SELECT:
				PickCanvas picker = new PickCanvas(this, this.bunkersBranch);
		    	picker.setMode(PickCanvas.GEOMETRY);
		    	picker.setShapeLocation(event);
		    	
		    	PickResult result = picker.pickClosest();

				if(result != null){	
					if(this.selectedModel != null)
						this.selectedModel.setAppearance(PFDAppearancesFactory.getNonSelectedApp());
					
					this.selectedModel = (Shape3D)result.getNode(PickResult.SHAPE3D);
		    			this.selectedModel.setAppearance(PFDAppearancesFactory.getSelectedApp());
		    		
		    		this.fireSelectionEvent(((PFDBunkerGraph)this.selectedModel.getParent().getParent()).bunkerProperties);	
		    		}
				break;
			case PFDUniverse.MODE_DRAW:
				PFDBunker bunker = this.previewBunker.bunkerProperties;
				PFDPosition position = PFDUtil.getVWorldPosition(this, event);
				
				PFDBunker newBunker = new PFDBunker(
						bunker.typeId+this.count, 
						bunker.typeId, 
						position, 0);
				
				Shape3D model = this.previewBunker.getModelClone();
				
				try{
					this.addBunker(newBunker, model);
					this.count++;
					}
				catch(Exception e){
					System.out.println(e.getMessage());
					}
				break;
			}
		}
	public void mousePressed(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	
	/**************************************************
					FIRING EVENTS ACTIONS
	**************************************************/
	public synchronized void addSelectionListener(PFDSelectionListener listener){
		this.listeners.add(listener);
		}
	public synchronized void removeSelectionListener(PFDSelectionListener listener){
		this.listeners.remove(listener);
		}
	private synchronized void fireSelectionEvent(PFDBunker bunker){
        PFDSelectionEvent event = new PFDSelectionEvent(this, bunker);
        Iterator listeners = this.listeners.iterator();
        while(listeners.hasNext()){
        	((PFDSelectionListener) listeners.next()).bunkerSelected(event);
        	}
    	}
	}
