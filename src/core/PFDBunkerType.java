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

package core;

import javax.media.j3d.Shape3D;
import javax.swing.ImageIcon;

import universe.PFDBunkerGraph;


/**
 * Clase que mantiene el tipo básico PFDBunkerType. Se encarga de almacenar la información 
 * relativa a un tipo búnker concreto, es decir, a una familia de obstaculos. De esta forma
 * si el set de bunkers disponible se componen de templos, doritos y cakes se cargaran tres
 * instancias distintas de PFDBunkerType cada una con las caracteristicas propias de cada
 * tipo de obstaculo.
 * Su función es la de servir como referente a la hora de crear modelos en la escena. Se
 * pretende evitar que por cada rama que representa gráficamente un bunker en la escena
 * esten duplicados el icono del tipo de bunker y su forma 3D.
 * Su identificador 'id' debe ser único dentro de un set de tipos bunkers.
 * 
 * @author Luis Ciudad García.
 * 
 * @see PFDBunker
 * @see PFDBunkerGraph
 */
public class PFDBunkerType {
	public String id;
	public String name;
	
	public Shape3D model;
	public ImageIcon icon;
	
	public PFDBunkerType(String id, String name, Shape3D model, ImageIcon icon){
		this.id = id;
		this.name = name;
		this.model = model;
		this.icon = icon;
		}
	
	public String getCaption(){
		return this.name;
		}
	
	public ImageIcon getIcon(){
		return this.icon;
		}
	
	public Shape3D getModel(){
		return (Shape3D)this.model.cloneNode(true);
		}
	
	}
