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

import javax.media.j3d.Behavior;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;

/**
 * Esta clase conforma el comportamiento de la cámara cuando la aplicación se encuentra
 * en el modo de "punto de vista libre".
 * 
 * @author Luis Ciudad García.
 *
 * @see PFDPreviewBehavior
 */
public class PFDCameraBehavior extends Behavior{
	/* The canvas to determine the middle of the screen */
	Canvas3D canvas3D;
 
	/* The wakeup criteria for each next time */
	private WakeupCondition mouseCriterion;
 
	/* The camera */
	private TransformGroup transformGroup;
 
	/* The transform to retreive the current vector */
	private Transform3D transform = new Transform3D();
 
	/* The transform to rotate the camera */
	private Transform3D rotator = new Transform3D();
 
	/* The different criteria to wake up for */
	private WakeupCriterion[] mouseEvents;
 
	/* The value of an mouse entering the screen must be ignored */
	private boolean outsideScreen = true;
 
	/**
	* Let the mouse determine the heading direction
	*
  * @param tg - the camera
	* @param canvas3D - the canvas to determine the middle of the screen
	*/
	public PFDCameraBehavior(TransformGroup tg, Canvas3D canvas3D)
	{
		transformGroup = tg;
		this.canvas3D = canvas3D;
	}
 
	/**
	* Override Behavior's initialize method to setup wakeup criteria
	*/
	public void initialize()
	{
		mouseEvents = new WakeupCriterion[2];
		mouseEvents[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		mouseEvents[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_EXITED);
		mouseCriterion = new WakeupOr(mouseEvents);
		wakeupOn(mouseCriterion);
	}
 
	/**
	* Override Behavior's stimulus method to handle the event
	*/
	public void processStimulus(Enumeration criteria)
	{
		while(criteria.hasMoreElements())
		{
			WakeupCriterion wakeup = (WakeupCriterion)criteria.nextElement();
 
			if(wakeup instanceof WakeupOnAWTEvent)
			{
				AWTEvent[] event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
				for (int i=0; i < event.length; i++)
				{
					if(event[i] instanceof MouseEvent)
						processMouseEvent((MouseEvent)event[i]);
				}
			}
		}
		wakeupOn(mouseCriterion);
	}
 
	/**
	* Processes the event and determens what to do
	*
	* @param mouseEvent the mouseEvent that was fired
	*/
	protected void processMouseEvent(MouseEvent mouseEvent)
	{
		int eventId = mouseEvent.getID();
 
		// the mouse left the screen
		if(eventId == MouseEvent.MOUSE_EXITED)
			outsideScreen = true;
 
		else if(eventId == MouseEvent.MOUSE_MOVED)
		{
			if(outsideScreen)
			{
				outsideScreen = false;
				return;
			}
 
			int dx = mouseEvent.getX() - canvas3D.getWidth()/2;
			int dy = mouseEvent.getY() - canvas3D.getHeight()/2;
 
			// rotate the camera over the x and y axis
			transformGroup.getTransform(transform);			
			rotator.rotY(0.001f * -dx * (float)Math.PI);
			transform.mul(rotator);
			rotator.rotX(0.001f * -dy * (float)Math.PI);
			transform.mul(rotator);
			
			transformGroup.setTransform(transform);
		}
	}
}