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

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;

/**
 * Clase que mantiene diferentes métodos estatícos que devuelven varios tipos de
 * apariencias. Cualquiera de estas apariencia que se vaya a usar en la aplicación
 * debe estar definida aquí y será recuperada por el objeto que la necesite
 * llamando al método estático correspondiente.
 * 
 * @author Luis Ciudad García.
 */
public class PFDAppearancesFactory {
	static public Appearance getNonSelectedApp(){
		Appearance selectedApp = new Appearance();
			//selectedApp.setColoringAttributes(new ColoringAttributes(1f,1f,1f,ColoringAttributes.NICEST));
	
		return selectedApp;
		}
	
	static public Appearance getSelectedApp(){
		Appearance selectedApp = new Appearance();
			selectedApp.setColoringAttributes(new ColoringAttributes(1f,1f,1f,ColoringAttributes.NICEST));
		
		return selectedApp;
		}
	}
