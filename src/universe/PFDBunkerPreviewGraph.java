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

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Text2D;

import core.PFDBunker;

/**
 * Se trata de un caso particular de búnker incluido en la escena. El obstaculo
 * representado por esta clase no está incluido en el terreno de juego, es solamente
 * una previsualización de un bunker candidato a ser agregado al campo. Por ello
 * su ID no debe ser tenida en cuenta, y si finalmente el obstáculo representado
 * es agregado al terreno de juego, se creará una nueva instancia de PFDBunkerGraph
 * para mantener la información de dicho obstaculo.
 * 
 * @author Luis Ciudad García.
 */
public class PFDBunkerPreviewGraph extends PFDBunkerGraph {
	private PFDPreviewBehavior behavior;
	private Shape3D nonLiveModel;
	
	public PFDBunkerPreviewGraph(PFDUniverse parent, BoundingSphere boundings){
		//super(new PFDBunker(), new Shape3D());
		super(new PFDBunker(), (Shape3D) new Text2D("Hola", new Color3f(0.0f,0.0f,0.0f), "Verdana", 120, 1));
		
		this.setCapability(BranchGroup.ALLOW_DETACH);
		
		this.nonLiveModel = new Shape3D();
		
		this.behavior = new PFDPreviewBehavior(parent, this.TG);
			this.behavior.setEnable(false);
			this.behavior.setSchedulingBounds(boundings);
			
		this.addChild(this.behavior);
		}
	
	public void setBehaviorEnable(boolean state){
		this.behavior.setEnable(state);
		}
	public void setModel(Shape3D model){
		BranchGroup parent = (BranchGroup)this.getParent();
		
		this.detach();
		
			this.TG.removeAllChildren();
			this.model = (Shape3D)model.cloneNode(true);
			this.TG.addChild(this.model);
		
		if(parent != null) //Solo si en ese momento se esta en DRAW
			parent.addChild(this);
		
		this.nonLiveModel = model;
		}
	
	public Shape3D getModelClone(){
		return (Shape3D)this.nonLiveModel.cloneNode(true);
		}
	}
