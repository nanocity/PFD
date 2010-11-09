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

import universe.PFDUniverse;

/**
 * Clase que mantiene el tipo básico PFDBunker. Se encarga de almacenar la información 
 * relativa a un búnker concreto. Es decir por cada rama gráfica que haya en la escena
 * representando un obstaculo existirá una instancia de esta clase (como un miembro
 * de dicha rama) que mantiene las propiedades del obstaculo.
 * Pueden existir instancias de este objeto que no esten representados en la escena.
 * Dentro de un PFDUniverse el identificador 'id' debe ser único pero el encargado de que
 * eso ocurra es PFDUniverse.
 * 
 * @author Luis Ciudad García.
 * 
 * @see PFDUniverse
 */
public class PFDBunker {
	public String id;
	public String typeId;
	
	public PFDPosition position;
	public int rotation;
	
	public PFDBunker(){
		this.id = null;
		this.typeId = null;
		this.position = new PFDPosition();
		this.rotation = 0;
		}
	
	public PFDBunker(String id, String typeId, PFDPosition position, int rotation){
		this.id = id;
		this.typeId = typeId;
		this.position = position;
		this.rotation = rotation;
		}
	
	public PFDBunker(PFDBunker bunker){
		this.id = bunker.id;
		this.typeId = bunker.typeId;
		
		this.position = bunker.position;
		this.rotation = bunker.rotation;
		}
	}
