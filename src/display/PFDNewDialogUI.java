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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

/**
 * Clase base que define el aspecto de la ventana de creación de un nuevo terreno de
 * juego. Esta clase es abstracta porque no define el comportamiento de la ventana
 * solo su apariencia. Todos los elementos de interfaz que aparezcan en este
 * dialogo deben estar declarados en esta clase.
 * 
 * @author Luis Ciudad García.
 * 
 * @see PFDNewDialog
 */
public abstract class PFDNewDialogUI extends JDialog implements ActionListener{
	static final long serialVersionUID = 1L;
	
	static final int CLOSE_ACCEPT = 1;
	static final int CLOSE_CANCEL = 0;
	
	protected JLabel pathLabel;
	protected JLabel widthLabel;
	protected JLabel heightLabel;
	protected JLabel hLinesGapLabel;
	protected JLabel vLinesGapLabel;
	protected JLabel texturePicture;
	
	protected JTextField pathField;
	protected JFormattedTextField widthField;
	protected JFormattedTextField heightField;
	protected JFormattedTextField hLinesGapField;
	protected JFormattedTextField vLinesGapField;
	
	protected JCheckBox hLinesBox;
	protected JCheckBox vLinesBox;
	
	protected ButtonGroup hLinesGroup;
	protected ButtonGroup vLinesGroup;
	
	protected JRadioButton hLinesCenterRadio;
	protected JRadioButton hLinesSideRadio;
	protected JRadioButton vLinesCenterRadio;
	protected JRadioButton vLinesSideRadio;
	
	protected JComboBox textureCombo;
	
	protected JButton pathButton;
	protected JButton acceptButton;
	protected JButton cancelButton;
	
	public PFDNewDialogUI(JFrame parent){
		super(parent, "New Field", true);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		/**
		 * Create bunker set's patch panel.
		 */
		JPanel pathPanel = new JPanel();
		pathPanel.setAlignmentX(LEFT_ALIGNMENT);
		pathPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 45));
		
		pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.LINE_AXIS));
		pathPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Bunker Set"));
		
		this.pathLabel = new JLabel("Bunker Set Path:");
		this.pathField = new JTextField("");
		this.pathButton = new JButton("...");
		
		pathPanel.add(this.pathLabel);
		pathPanel.add(this.pathField);
		pathPanel.add(this.pathButton);
		
		/**
		 * Create field dimensions panel
		 */
		JPanel dimPanel = new JPanel();
		dimPanel.setLayout(new GridLayout(2,2));
		dimPanel.setAlignmentX(LEFT_ALIGNMENT);
		dimPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Dimensions"));
		
		this.widthLabel = new JLabel("Width:");
		this.heightLabel = new JLabel("Heigth:");
		
		NumberFormatter format = new NumberFormatter();
		format.setMinimum(1);
		format.setMaximum(300);
		
		this.widthField = new JFormattedTextField(format);
		this.heightField = new JFormattedTextField(format);
		
		this.widthField.setValue(30);
		this.heightField.setValue(40);
		
		dimPanel.add(this.widthLabel);
		dimPanel.add(this.widthField);
		
		dimPanel.add(this.heightLabel);
		dimPanel.add(this.heightField);
		
		/**
		 * Create field lines panel 
		 */
		JPanel linesPanel = new JPanel();
		linesPanel.setLayout(new BoxLayout(linesPanel, BoxLayout.PAGE_AXIS));
		linesPanel.setAlignmentX(LEFT_ALIGNMENT);
		linesPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Lines"));
		
			JPanel verticalPanel = new JPanel();
			verticalPanel.setAlignmentX(LEFT_ALIGNMENT);
			verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.PAGE_AXIS));
			verticalPanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.black),
					"Vertical"));
			
				JPanel propVPanel = new JPanel();
				propVPanel.setAlignmentX(LEFT_ALIGNMENT);
				propVPanel.setLayout(new BoxLayout(propVPanel, BoxLayout.PAGE_AXIS));
				propVPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black),
						"Properties"));
				
					JPanel originPropVPanel = new JPanel();
					originPropVPanel.setAlignmentX(LEFT_ALIGNMENT);
					originPropVPanel.setLayout(new BoxLayout(originPropVPanel, BoxLayout.LINE_AXIS));
						this.vLinesGroup = new ButtonGroup();
						this.vLinesCenterRadio = new JRadioButton("Center", true);
						this.vLinesSideRadio = new JRadioButton("Side");
						this.vLinesGroup.add(this.vLinesCenterRadio);
						this.vLinesGroup.add(this.vLinesSideRadio);
						originPropVPanel.add(this.vLinesCenterRadio);
						originPropVPanel.add(this.vLinesSideRadio);
					
			
					JPanel gapPropVPanel = new JPanel();
					gapPropVPanel.setAlignmentX(LEFT_ALIGNMENT);
					gapPropVPanel.setLayout(new BoxLayout(gapPropVPanel, BoxLayout.LINE_AXIS));
						this.vLinesGapLabel = new JLabel("Gap:");
						this.vLinesGapField = new JFormattedTextField(format); 
						this.vLinesGapField.setValue(5);
						gapPropVPanel.add(this.vLinesGapLabel);
						gapPropVPanel.add(this.vLinesGapField);
					
				propVPanel.add(originPropVPanel);
				propVPanel.add(gapPropVPanel);
			
			this.vLinesBox = new JCheckBox("Show Vertical Lines");	
			this.vLinesBox.setSelected(true);
			verticalPanel.add(this.vLinesBox);
			verticalPanel.add(propVPanel);
		
			JPanel horizontalPanel = new JPanel();
			horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.PAGE_AXIS));
			horizontalPanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.black),
					"Horizontal"));
			
				JPanel propHPanel = new JPanel();
				propHPanel.setAlignmentX(LEFT_ALIGNMENT);
				propHPanel.setLayout(new BoxLayout(propHPanel, BoxLayout.PAGE_AXIS));
				propHPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.black),
						"Properties"));
				
					JPanel originPropHPanel = new JPanel();
					originPropHPanel.setAlignmentX(LEFT_ALIGNMENT);
					originPropHPanel.setLayout(new BoxLayout(originPropHPanel, BoxLayout.LINE_AXIS));
						this.hLinesGroup = new ButtonGroup();
						this.hLinesCenterRadio = new JRadioButton("Center", true);
						this.hLinesSideRadio = new JRadioButton("Side");
						this.hLinesGroup.add(this.hLinesCenterRadio);
						this.hLinesGroup.add(this.hLinesSideRadio);
						originPropHPanel.add(this.hLinesCenterRadio);
						originPropHPanel.add(this.hLinesSideRadio);
					
			
					JPanel gapPropHPanel = new JPanel();
					gapPropHPanel.setAlignmentX(LEFT_ALIGNMENT);
					gapPropHPanel.setLayout(new BoxLayout(gapPropHPanel, BoxLayout.LINE_AXIS));
						this.hLinesGapLabel = new JLabel("Gap:");
						this.hLinesGapField = new JFormattedTextField(format); 
						this.hLinesGapField.setValue(5);
						gapPropHPanel.add(this.hLinesGapLabel);
						gapPropHPanel.add(this.hLinesGapField);
					
				propHPanel.add(originPropHPanel);
				propHPanel.add(gapPropHPanel);
			
			this.hLinesBox = new JCheckBox("Show Horizontal Lines");
			this.hLinesBox.setSelected(true);
			horizontalPanel.add(this.hLinesBox);
			horizontalPanel.add(propHPanel);
		
		linesPanel.add(verticalPanel);
		linesPanel.add(horizontalPanel);
		
		/**
		 * Create texture panel
		 */
		JPanel texturePanel = new JPanel();
		texturePanel.setLayout(new BoxLayout(texturePanel, BoxLayout.PAGE_AXIS));
		texturePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black),
				"Texture"));
		
		this.texturePicture = new JLabel("");
		this.texturePicture.setAlignmentX(CENTER_ALIGNMENT);
		this.texturePicture.setPreferredSize(new Dimension(200,200));
			
		this.textureCombo = new JComboBox();
		this.textureCombo.setMaximumSize(new Dimension(Short.MAX_VALUE, 45));
		
		texturePanel.add(this.texturePicture);
		texturePanel.add(this.textureCombo);
		texturePanel.add(Box.createVerticalGlue());
		
		/**
		 * Create buttons panel.
		 */
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		this.acceptButton = new JButton("Accept");
		this.cancelButton = new JButton("Cancel");
		
		buttonsPanel.add(Box.createHorizontalGlue());
		buttonsPanel.add(this.acceptButton);
		buttonsPanel.add(this.cancelButton);
		
		/**
		 * Place panels along the dialog.
		 */
		
		JPanel container = new JPanel();
		container.setAlignmentX(LEFT_ALIGNMENT);
		container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
			
			JPanel vContainer = new JPanel();
			vContainer.setLayout(new BoxLayout(vContainer, BoxLayout.PAGE_AXIS));
				
				vContainer.add(dimPanel);
				vContainer.add(linesPanel);
				
			container.add(vContainer);	
			container.add(texturePanel);		
		
		this.add(pathPanel);
		this.add(container);
		this.add(Box.createVerticalGlue());
		this.add(buttonsPanel);
		
		/**
		 * Set up connections
		 */		
		this.pathButton.addActionListener(this);
		this.acceptButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		
		this.textureCombo.addActionListener(this);
		
		this.vLinesBox.addActionListener(this);
		this.hLinesBox.addActionListener(this);
		}
	
	public void ActionPerformed(ActionEvent event){
		}
	}

