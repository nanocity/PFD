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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import core.PFDBunker;

/**
 * Esta clase representa una rama del arbol gráfico con los componentes necesarios
 * para renderizar un obstaculo que está incluido en el terreno de juego.
 * Además de las propiedades gráficas propias del entorno 3D, también mantiene
 * propiedades del obstaculo que representa a través de una variable de tipo básico.
 * 
 * @author Luis Ciudad García.
 * 
 * @see PFDBunkerPreviewGraph
 */
public class PFDBunkerGraph extends BranchGroup{
	public PFDBunker bunkerProperties;
	protected TransformGroup TG;
	protected Shape3D model;
	
	public PFDBunkerGraph(PFDBunker bunker, Shape3D model){
		super();
		
		this.setCapability(BranchGroup.ALLOW_DETACH);
		
		this.bunkerProperties = new PFDBunker(bunker);
		
		this.model = model;
			model.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
			model.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		
		this.TG = new TransformGroup();
			TG.addChild(model);
			TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		this.addChild(TG);
		
		this.repaint();
		}
	
	public void editBunkerProperties(PFDBunker bunker){
		this.bunkerProperties = bunker;
		}
	
	public void repaint(){
		Transform3D tr = new Transform3D();
			tr.setTranslation(new Vector3d(
					this.bunkerProperties.position.x, 
					this.bunkerProperties.position.y,
					0));
			//Setear la rotacion el eje x,y
			
		this.TG.setTransform(tr);
		}
	}
