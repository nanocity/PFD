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

package util;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.swing.ImageIcon;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import ncsa.j3d.loaders.ModelLoader;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import org.jdom.Document;

import universe.PFDUniverse;

import com.sun.j3d.loaders.Scene;

import core.PFDBunkerType;
import core.PFDPosition;

/**
 * Esta clase contiene varios métodos estáticos que sirven de apoyo a la aplicación.
 * Cualquier proceso no dependiente del PFDUniverse debe estar declarado aquí.
 * Algunas de estas tareas son:
 *     -Carga de modelos desde archivo a objetos de tipo Shape3D.
 *     -Carga y parseo de sets de obstaculos.
 * 
 * La idea detrás de esta clase es externalizar los procesos que no son propios
 * del universo, creando una capa de abstracción entre los datos de entrada y dicho
 * universo. De esta forma, si se cambia el formato de los modelos que acepta la
 * aplicación o el esquema de definición de sets de bunkers, los cambios oportunos
 * se realizarán en esta clase.
 * 
 * @author Luis Ciudad García.
 */
public class PFDUtil {
	static public PFDPosition getVWorldPosition(PFDUniverse parent, MouseEvent event){
		Point3d eyePos = new Point3d();
		Point3d mousePos = new Point3d();
		Vector3d mouseVec = new Vector3d();		
		Transform3D motion = new Transform3D();
		PFDPosition position = new PFDPosition();
		
		parent.getCenterEyeInImagePlate(eyePos);
		parent.getPixelLocationInImagePlate(event.getX(), event.getY(), mousePos);
		parent.getImagePlateToVworld(motion);	

		motion.transform(eyePos);
		motion.transform(mousePos);
		mouseVec.sub(mousePos, eyePos);
		mouseVec.normalize();
		
		double t = -(eyePos.z / mouseVec.z);
		
		position.x = eyePos.x + mouseVec.x *t;
		position.y = eyePos.y + mouseVec.y *t;
		
		return position;
		}
	
	static public HashMap<String, PFDBunkerType> parseBunkerSet(String path){
		HashMap<String, PFDBunkerType> bunkerMap = new HashMap<String, PFDBunkerType>();
		
		try{
			SAXBuilder builder = new SAXBuilder();
			//builder.setFeature("./bunkertypeset.xsd", true);
			Document bsFile = builder.build(path);
			
			Iterator itr = bsFile.getRootElement().getChildren().iterator();
			
			while(itr.hasNext()){
				Element bunker = (Element) itr.next();
				
				String name = bunker.getChildText("id");
				String caption = bunker.getChildText("name");
				String iconPath = bunker.getChildText("icon");
				String modelPath = bunker.getChildText("model");
				
				Shape3D model = PFDUtil.loadModel(modelPath);
				ImageIcon icon = PFDUtil.loadIcon(iconPath);
				
				bunkerMap.put(name, new PFDBunkerType(name, caption, model, icon));
				}
			
			}
		catch(JDOMException e){ System.out.println(e.getMessage());}
		catch(IOException e){ System.out.println("Ou nous! en IOException");}
		
		return bunkerMap;
		}
	
	public static Shape3D loadModel(String modelPath){
		BranchGroup model = null;
        Scene loadedScene = null;
        
        try {
            ModelLoader loader = new ModelLoader();
            loadedScene = loader.load(modelPath);
            
            if(loadedScene != null){
                model = loadedScene.getSceneGroup();
                }
            }
        catch( FileNotFoundException ioe){ 
            System.err.println("No se encuentra el modelo: " + modelPath);
            }
        
		return (Shape3D)model.getChild(0).cloneNode(true);
		}
	
	public static ImageIcon loadIcon(String iconPath){
		return new ImageIcon(iconPath);
		}
	}
