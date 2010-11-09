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
import javax.media.j3d.ImageComponent2D;

import core.PFDField;

/**
 * Esta clase representa los elementos gráficos necesarios para renderizar el
 * terreno de juego así como mantiene la información relativa a las propiedades
 * de dicho campo.
 * 
 * @author Luis Ciudad García.
 */
public class PFDFieldGraph extends BranchGroup {
	public PFDField fieldProperties;
	private BranchGroup inter;
	private PFDTexturedPlane plane;
	
	public PFDFieldGraph(PFDField field, ImageComponent2D textureImage){
		super();

		this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		
		this.fieldProperties = new PFDField(field);
		
		this.plane = new PFDTexturedPlane(
						this.fieldProperties.width, 
						this.fieldProperties.height, 
						textureImage);
		
		this.inter = new BranchGroup();
			this.inter.addChild(this.plane);
			
		this.addChild(this.inter);
		}
	
	public void setTexture(ImageComponent2D textureImage){
		this.inter.removeChild(this.plane);
		
		this.plane = new PFDTexturedPlane(
				this.fieldProperties.width, 
				this.fieldProperties.height, 
				textureImage);
		this.inter.addChild(this.plane);
		}
	}
