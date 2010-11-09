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

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;

/**
 * Esta clase define el comportamiento de la rama gráfica de previsualización
 * de un bunker candidato a ser agregado al terreno de juego. Lo que pretende
 * es que un objeto gráfico que represente un búnker siga continuamente el
 * movimiento del ratón, para situar el centro del bunker en el punto del campo
 * que dicte la posición del ratón.
 * 
 * @author Luis Ciudad García.
 *
 * @see PFDBunkerPreviewGraph
 */
public class PFDPreviewBehavior extends MouseBehavior{

	private Canvas3D canvas;
	private WakeupOr conditions;
	
	public PFDPreviewBehavior(Canvas3D canvas, TransformGroup transform){
		super(transform);
		
		this.canvas = canvas;
		}
	
	public void initialize(){
		super.initialize();
		
		this.conditions = new WakeupOr(new WakeupCriterion[] {new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED)});
		
		this.wakeupOn(this.conditions);
		}
	
	public void processStimulus(Enumeration criteria){
		int x = 0, y = 0;
		
		while (criteria.hasMoreElements()){
	        WakeupOnAWTEvent event = (WakeupOnAWTEvent) criteria.nextElement();
	
	        for(AWTEvent awtEvent:event.getAWTEvent()){
	        	if (awtEvent instanceof MouseEvent){
	        		MouseEvent mouse = (MouseEvent) awtEvent;
	        		
	        		x = mouse.getX();
	        		y = mouse.getY();
	        		}
	        	}
	
			}

		
		/******************************************/
		Point3d eyePos = new Point3d();
		Point3d mousePos = new Point3d();
		
		Vector3d mouseVec = new Vector3d();		

		Transform3D motion = new Transform3D();
		
		this.canvas.getCenterEyeInImagePlate(eyePos);
		this.canvas.getPixelLocationInImagePlate(x,y,mousePos);
		this.canvas.getImagePlateToVworld(motion);	

		motion.transform(eyePos);
		motion.transform(mousePos);
		mouseVec.sub(mousePos, eyePos);
		mouseVec.normalize();
		
		double t = -(eyePos.z / mouseVec.z);		
		/*****************************************/
		
		Transform3D tr = new Transform3D();
			tr.setTranslation(new Vector3d(eyePos.x + mouseVec.x *t, eyePos.y + mouseVec.y *t, 0));
			
		this.transformGroup.setTransform(tr);
			
		this.wakeupOn(this.conditions);
		}
	}
