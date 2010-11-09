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

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.media.j3d.ImageComponent2D;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import universe.PFDUniverse;
import util.PFDUtil;
import core.PFDBunkerType;
import core.PFDField;
import core.PFDSelectionEvent;
import core.PFDSelectionListener;

/**
 * Clase que se encarga de hacer todo el trabajo intermedio entre el manejo de la escena 3D
 * y las instrucciones que el usuario ordena a traves de la interfaz. Además aquí se definen
 * las acciones de la venatana principal, mientras que en su clase padre solo se construye
 * la interfaz.
 * Es aquí donde se hace uso de otro objetos auxiliares para preparar los datos que requiere
 * el PFDUniverse, así como donde se recojen las señales enviadas por este.
 * 
 * @author Luis Ciudad García.
 * 
 * @see PFDUniverse
 * @see PFDMainWindowUI
 *
 */
public class PFDMainWindow extends PFDMainWindowUI implements PFDSelectionListener, ListSelectionListener {
	static final long serialVersionUID = 1L;

	static final int NON_INITIALIZED = 0;
	static final int INITIALIZED = 1;
	
	private HashMap<String, PFDBunkerType> bunkerSet;
	private int status;
	private PFDUniverse universe;
	
	public PFDMainWindow(){
		super();
		
		this.bunkerSet = new HashMap<String, PFDBunkerType>();
		
		this.universe = new PFDUniverse();
			this.universe.addSelectionListener(this);
		
		this.bunkerList.addListSelectionListener(this);	
			
		this.canvasPanel.add(universe);
		
		this.setNonInitializedUI();
		}
	
	private void setNonInitializedUI(){
		this.status = PFDMainWindow.NON_INITIALIZED;
		/**
		 * Disable save/select/draw items and buttons
		 */
		this.saveButton.setEnabled(false);
		this.saveItem.setEnabled(false);
		this.selectButton.setEnabled(false);
		this.drawButton.setEnabled(false);
		this.freeCameraButton.setEnabled(false);
		this.fpCameraButton.setEnabled(false);
		/**
		 * Reset universe
		 */
		this.universe.resetUniverse();
		
		/**
		 * Delete bunker set
		 */
		this.bunkerSet.clear();
		this.listModel.removeAllElements();
		this.bunkerList.setModel(this.listModel);
		}	
	private void setInitializedUI(String bunkerSetPath, PFDField field, ImageComponent2D textureImage){
		/**
		 * Enable save/select/draw items and buttons
		 */
		this.saveButton.setEnabled(true);
		this.saveItem.setEnabled(true);
		this.selectButton.setEnabled(true);
		this.drawButton.setEnabled(true);
		this.freeCameraButton.setEnabled(true);
		this.fpCameraButton.setEnabled(true);
		
		this.selectButton.doClick();
		
		/**
		 * Read bunker set and create the list
		 */
		this.bunkerSet = PFDUtil.parseBunkerSet(bunkerSetPath);
		this.bunkerList.setListData(this.bunkerSet.values().toArray());
		
		this.bunkerList.setSelectedIndex(0);
		
		/**
		 * Create and show the field
		 */
		this.universe.setField(field, textureImage);
		
		this.status = PFDMainWindow.INITIALIZED;
		}
	
	/**
	 * UI's actions
	 */
	public void actionPerformed(ActionEvent event){
		/**
		 * New Field
		 */
		if(event.getSource() == this.newItem || event.getSource() == this.newButton){
			if(this.status == PFDMainWindow.INITIALIZED){
				int option = JOptionPane.showConfirmDialog(this, "Current field will be closed.\nDo you want to save your changes?");
				switch(option){
					case JOptionPane.YES_OPTION:
						//this.save();
					case JOptionPane.NO_OPTION:
						this.setNonInitializedUI();
						this.launchNewDialog();
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
					}
				}
			else{
				this.launchNewDialog();
				}
			}
		else if(event.getSource() == this.selectButton){
			this.universe.toggleMode(PFDUniverse.MODE_SELECT);
			}
		else if(event.getSource() == this.drawButton){
			this.universe.toggleMode(PFDUniverse.MODE_DRAW);
			
			PFDBunkerType bunkerType = (PFDBunkerType)this.bunkerList.getSelectedValue();
			if(bunkerType != null){
				this.universe.editPreviewBunker(bunkerType);
				this.universe.setPreviewModel(bunkerType.getModel());
				}
			}
		else if(event.getSource() == this.freeCameraButton){
			this.universe.toggleMode(PFDUniverse.MODE_FREECAMERA);
			}
		else if(event.getSource() == this.fpCameraButton){
			this.universe.toggleMode(PFDUniverse.MODE_FPCAMERA);
			}
		}
	
	
	
	private void launchNewDialog(){
		PFDNewDialog dlg = new PFDNewDialog(this);
		
		if(dlg.getClosing() == PFDNewDialog.CLOSE_ACCEPT){
			this.setInitializedUI(dlg.getPath(), dlg.getFieldConfig(), dlg.getTexture());
			}
		}
	
	public void bunkerSelected(PFDSelectionEvent event){
		
		}
	
	public void valueChanged(ListSelectionEvent event){
		PFDBunkerType bunkerType = (PFDBunkerType)this.bunkerList.getSelectedValue();
		if(bunkerType != null){
			this.universe.editPreviewBunker(bunkerType);
			this.universe.setPreviewModel(bunkerType.getModel());
			}
		}
	}
