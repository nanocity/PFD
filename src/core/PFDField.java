/* 
 * PFD - A 3D paintball fields designer.
 * Copyright (C) 2010 PFD Luis Ciudad Garc�a
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

/**
 * Clase que mantiene el tipo b�sico PFDField. Almacena la informaci�n relativa al terreno
 * sobre el que se dise�a el campo de juego adem�s de alguna propiedades sobre la
 * visualizaci�n del mismo.
 * 
 * @author Luis Ciudad Garc�a.
 *
 */

public class PFDField {
	public int width;
	public int height;
	
	public boolean vLines;
	public int vLinesGap;
	
	public boolean hLines;
	public int hLinesGap;
	
	
	//PROVISIONAL PARA PROBAR
	public PFDField(int width, int height){
		this.width = width;
		this.height = height;
		}
	
	public PFDField(PFDField field){
		this.width = field.width;
		this.height = field.height;
		
		this.vLines = field.vLines;
		this.vLinesGap = field.vLinesGap;
		
		this.hLines = field.hLines;
		this.hLinesGap = field.hLinesGap;
		}
	}
