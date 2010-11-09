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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

/**
 * Clase base que define el aspecto de la ventana principal del programa. Esta clase es
 * abstracta ya que no define comportamiento alguno, solo apariencia.
 * Todos los elementos de la interfaz deben estar definidos en esta clase.
 * 
 * @author Luis Ciudad García.
 *
 * @see PFDMainWindow
 */
public abstract class PFDMainWindowUI extends JFrame implements ActionListener{	
	static final long serialVersionUID = 1L;
	
	protected final JMenuBar menuBar;
	protected final JMenuItem newItem;
	protected final JMenuItem saveItem;
	protected final JMenuItem loadItem;
	protected final JMenuItem closeItem;
	protected final JMenuItem helpItem;
	protected final JMenuItem aboutItem;
	
	protected final JToolBar toolBar;
	protected final JButton newButton;
	protected final JButton saveButton;
	protected final JButton loadButton;
	
	protected final ButtonGroup designGroup;
	protected final JToggleButton selectButton;
	protected final JToggleButton drawButton;
	protected final JToggleButton freeCameraButton;
	protected final JToggleButton fpCameraButton;
	
	protected final JList bunkerList;
	protected final DefaultListModel listModel;
	protected final JSplitPane horizontalDiv;
	protected final JSplitPane verticalDiv;
	
	protected final JPanel canvasPanel;
	
	public PFDMainWindowUI(){
		super("Paintball Field Designer");
				
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1024, 768));
		
		/**
		 * Create menu bar.
		 */
		this.newItem = new JMenuItem("New");
		this.saveItem = new JMenuItem("Save");
		this.loadItem = new JMenuItem("Load");
		this.closeItem = new JMenuItem("Close");
		this.helpItem = new JMenuItem("Help Content");
		this.aboutItem = new JMenuItem("About PFD");
		
		this.newItem.setIcon(new ImageIcon("icons/new.png"));
		this.saveItem.setIcon(new ImageIcon("icons/save.png"));
		this.loadItem.setIcon(new ImageIcon("icons/load.png"));
		
		this.menuBar = new JMenuBar();
			JMenu fileMenu = new JMenu("File");
				fileMenu.add(this.newItem);
				fileMenu.addSeparator();
				fileMenu.add(this.saveItem);
				fileMenu.add(this.loadItem);
				fileMenu.addSeparator();
				fileMenu.add(this.closeItem);
				
			JMenu helpMenu = new JMenu("Help");
				helpMenu.add(this.helpItem);
				helpMenu.addSeparator();
				helpMenu.add(this.aboutItem);
				
		this.menuBar.add(fileMenu);
		this.menuBar.add(helpMenu);
		
		this.setJMenuBar(this.menuBar);
		
		/**
		 * Create tool bar.
		 */	
		this.newButton = new JButton(new ImageIcon("icons/new.png"));
		this.saveButton = new JButton(new ImageIcon("icons/save.png"));
		this.loadButton = new JButton(new ImageIcon("icons/load.png"));
		
		this.designGroup = new ButtonGroup();
		this.selectButton = new JToggleButton("Select");
		this.drawButton = new JToggleButton("Draw");
		this.freeCameraButton = new JToggleButton("Free Cam");
		this.fpCameraButton = new JToggleButton("First Person");
		
		this.selectButton.setSelected(true);
		
		this.designGroup.add(this.selectButton);
		this.designGroup.add(this.drawButton);
		this.designGroup.add(this.freeCameraButton);
		this.designGroup.add(this.fpCameraButton);
		
		this.toolBar = new JToolBar();
			this.toolBar.add(this.newButton);
			this.toolBar.addSeparator();
			this.toolBar.add(this.saveButton);
			this.toolBar.add(this.loadButton);
			this.toolBar.addSeparator();
			this.toolBar.add(this.selectButton);
			this.toolBar.add(this.drawButton);
			this.toolBar.add(this.freeCameraButton);
			this.toolBar.add(this.fpCameraButton);
		
		this.add(this.toolBar, BorderLayout.PAGE_START);
			
		/**
		 * Create bunker list.
		 */
		this.bunkerList = new JList();
		this.listModel = new DefaultListModel();
		this.bunkerList.setCellRenderer(new PFDListBunkerCellRenderer());
		this.bunkerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.bunkerList.setModel(this.listModel);
		JScrollPane bunkerListPanel = new JScrollPane(bunkerList);
		bunkerListPanel.setPreferredSize(new Dimension(200, 400));
		bunkerListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Bunkers"));
		
		
		/**
		 * Create properties panel.
		 */
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setPreferredSize(new Dimension(200, 400));
		propertiesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Properties"));
	
		
		/**
		 * Create Canvas3D panel
		 */
		this.canvasPanel = new JPanel();
		this.canvasPanel.setPreferredSize(new Dimension(800, 600));
		this.canvasPanel.setLayout(new BoxLayout(this.canvasPanel, BoxLayout.PAGE_AXIS));
		
		/**
		 * Create splitters.
		 */
		this.verticalDiv = new JSplitPane(JSplitPane.VERTICAL_SPLIT, bunkerListPanel, propertiesPanel);
		this.horizontalDiv = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.verticalDiv, this.canvasPanel);
		this.verticalDiv.setResizeWeight(0.5);
		this.horizontalDiv.setResizeWeight(0);
		
		/**
		 * Set up connections.
		 */
		this.selectButton.addActionListener(this);
		this.drawButton.addActionListener(this);
		this.freeCameraButton.addActionListener(this);
		this.fpCameraButton.addActionListener(this);
		
		this.newItem.addActionListener(this);
		this.saveItem.addActionListener(this);
		this.loadItem.addActionListener(this);
		this.closeItem.addActionListener(this);
		this.helpItem.addActionListener(this);
		this.aboutItem.addActionListener(this);
		
		this.newButton.addActionListener(this);
		this.saveButton.addActionListener(this);
		this.loadButton.addActionListener(this);
		
		this.add(this.horizontalDiv, BorderLayout.CENTER);
		}

	public void actionPerformed(ActionEvent event){
		//All behavior is defined in subclass PFDMainWindow
		}
	}


