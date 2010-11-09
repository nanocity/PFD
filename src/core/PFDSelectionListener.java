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

import display.PFDMainWindow;

/**
 * Interfaz a implementar por el objeto que escucha los eventos producidos por PFDUniverse.
 * 
 * @author Luis Ciudad García.
 *
 * @see PFDSelectionEvent
 * @see PFDMainWindow
 */
public interface PFDSelectionListener {
	public void bunkerSelected(PFDSelectionEvent event);
	}
