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

package display;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import core.PFDBunkerType;

/**
 * Esta clase se encarga de crear una vista personalizada de los items de una lista.
 * Se utiliza para mostrar los distintos tipos de bunkers que la interfaz de usuario tiene
 * listos para agregar a la escena haciendo uso del icono y la etiqueta que acompañan a la
 * forma 3D del tipo de obstaculo.
 * 
 * @author Luis Ciudad García.
 * 
 * @see PFDMainWindowUI
 *
 */
public class PFDListBunkerCellRenderer extends JLabel implements ListCellRenderer {
	static final long serialVersionUID = 1L;
	
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	public PFDListBunkerCellRenderer() {
		setOpaque(true);
		setIconTextGap(12);
		}

	public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus){
		PFDBunkerType entry = (PFDBunkerType) value;
		setText(entry.getCaption());
		setIcon(entry.getIcon());
		
		if(isSelected){
			setBackground(HIGHLIGHT_COLOR);
			setForeground(Color.white);
			} 
		else{
			setBackground(Color.white);
			setForeground(Color.black);
			}
		
		return this;
		}
	}
