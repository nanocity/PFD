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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.ImageComponent2D;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.j3d.utils.image.TextureLoader;

import core.PFDField;

/**
 * Esta clase maneja el comportamiento de la ventana para crear un nuevo terreno de
 * juego. Aquí solo de define el comportamiento, mientras que es la clase padre
 * es donde se construye la interfaz.
 * 
 * @author Luis Ciudad García.
 *
 * @see PFDNewDialogUI
 * @see PFDMainWindow
 */
public class PFDNewDialog extends PFDNewDialogUI{
	
	static final long serialVersionUID = 1L;
	
	private int closing = PFDNewDialog.CLOSE_CANCEL;
	
	public PFDNewDialog(JFrame parent){
		super(parent);
		
		this.populateTextures();
		this.setTexturePreview(this.textureCombo.getItemAt(0).toString());
		
		this.pack();
		this.setVisible(true);
		}
	
	private void populateTextures(){
		File textureDir = new File("textures/");
		String[] files = textureDir.list();	
		
		for(int i = 0; i < files.length; i++)
			this.textureCombo.addItem(files[i]);
		}
	
	private void changeStateLines(int box, boolean state){
		if(box == 0){
			this.vLinesCenterRadio.setEnabled(state);
			this.vLinesSideRadio.setEnabled(state);
			this.vLinesGapField.setEnabled(state);
			this.vLinesGapLabel.setEnabled(state);
			}
		else{
			this.hLinesCenterRadio.setEnabled(state);
			this.hLinesSideRadio.setEnabled(state);
			this.hLinesGapField.setEnabled(state);
			this.hLinesGapLabel.setEnabled(state);
			}
		}
	
	public String getPath(){
		return this.pathField.getText();
		}
	
	public ImageComponent2D getTexture(){
		String texture = "textures/" + this.textureCombo.getSelectedItem().toString();
		TextureLoader loader = new TextureLoader(texture, new JPanel());
		ImageComponent2D textureImage = loader.getImage();
		
		return textureImage;
		}
	
	public PFDField getFieldConfig(){
		int width = (Integer) this.widthField.getValue();
		int height = (Integer) this.heightField.getValue();
		boolean vLines = this.vLinesBox.isSelected();
		int vLinesGap = (Integer) this.vLinesGapField.getValue();
		boolean hLines = this.hLinesBox.isSelected();
		int hLinesGap = (Integer) this.hLinesGapField.getValue();
		
		return new PFDField(width, height);
		}
	
	public int getClosing(){
		return this.closing;
		}
	
	private void setPath(){
		JFileChooser fc = new JFileChooser();
		int retVal = fc.showOpenDialog(this.getContentPane());
		if(retVal == JFileChooser.APPROVE_OPTION){
			this.pathField.setText(fc.getSelectedFile().getAbsolutePath());
			}
		}
	
	private void setTexturePreview(String filename){
		try{
			BufferedImage buffImg = ImageIO.read(new File("textures/" + filename));
			Image image = buffImg.getScaledInstance(200, 200, Image.SCALE_FAST);
			
			this.texturePicture.setIcon(new ImageIcon(image));
			}
		catch(IOException e){}
		}
	
	private boolean checkDialog(){
		boolean valid = true;
		String error = "";
		
		if(this.pathField.getText().isEmpty()){
			valid = false;
			error += "No bunker set file selected\n";
			}
		
		if(this.textureCombo.getSelectedItem() == null || this.textureCombo.getSelectedItem().toString().isEmpty()){
			valid = false;
			error += "No texture selected\n";
			}
		
		if(valid == false){
			JOptionPane.showMessageDialog(this, error);
			}
		
		return valid;
		}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == this.pathButton)
			this.setPath();
		
		if(event.getSource() == this.cancelButton){
			this.closing = PFDNewDialog.CLOSE_CANCEL;
			this.dispose();
			}
		
		if(event.getSource() == this.acceptButton){
			if(this.checkDialog() == true){
				this.closing = PFDNewDialog.CLOSE_ACCEPT;
				this.dispose();
				}
			}
		
		if(event.getSource() == this.textureCombo){
			JComboBox source = (JComboBox)event.getSource();
			this.setTexturePreview(source.getSelectedItem().toString());
			}
		
		if(event.getSource() == this.vLinesBox){
			JCheckBox source = (JCheckBox)event.getSource();
			this.changeStateLines(0, source.isSelected());
			}
		
		if(event.getSource() == this.hLinesBox){
			JCheckBox source = (JCheckBox)event.getSource();
			this.changeStateLines(1, source.isSelected());
			}
		}
	}
