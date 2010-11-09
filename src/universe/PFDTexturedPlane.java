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
import javax.media.j3d.GeometryArray;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

/**
 * Esta clase representa la superficie de un campo de juego, es decir, un terreno
 * rectangular con las propieades de visualización propias de un terreno con hierba
 * real.
 * 
 * @author Luis Ciudad García.
 *
 * @see PFDFieldGraph
 */
public class PFDTexturedPlane extends Shape3D{
	private static final int NUM_VERTS = 4;
	
	public ImageComponent2D textureImage;
	public int width;
	public int heigth;
	
	public PFDTexturedPlane(int width, int heigth, ImageComponent2D textureImage){	
		super();
		this.width = width;
		this.heigth = heigth;
		
		this.textureImage = textureImage;
		
	    createGeometry(this.width, this.heigth);
	    createAppearance();
	    }
	
	private void createGeometry(int width, int heigth){
	    QuadArray plane = new QuadArray(NUM_VERTS, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2 );
	
	    Point3f p1 = new Point3f(0.0f, 0.0f, 0.0f);
	    Point3f p2 = new Point3f((float) width, 0.0f, 0.0f);
	    Point3f p3 = new Point3f((float) width, (float) heigth, 0.0f);
	    Point3f p4 = new Point3f(0.0f, (float) heigth, 0.0f);
	    
	    plane.setCoordinate(0, p1);
	    plane.setCoordinate(1, p2);
	    plane.setCoordinate(2, p3);
	    plane.setCoordinate(3, p4);
	
	    TexCoord2f q = new TexCoord2f();
	    q.set(0.0f, 0.0f);    
	    plane.setTextureCoordinate(0, 0, q);
	    q.set(1.0f, 0.0f);   
	    plane.setTextureCoordinate(0, 1, q);
	    q.set(1.0f, 1.0f);    
	    plane.setTextureCoordinate(0, 2, q);
	    q.set(0.0f, 1.0f);   
	    plane.setTextureCoordinate(0, 3, q);  
	
	    this.setGeometry(plane);
	  	}


	private void createAppearance(){                       
	    PolygonAttributes polAttr = new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0, false);
	    TransparencyAttributes trasAttr = new TransparencyAttributes(TransparencyAttributes.NONE, 1.0f);
	    TextureAttributes texAttr = new TextureAttributes();
	    
	    Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture.RGBA, this.textureImage.getWidth(), this.textureImage.getHeight());
	    texture.setImage(0, this.textureImage);
	    texture.setMinFilter(Texture.NICEST);
	    texture.setMagFilter(Texture.NICEST);
	    texture.setCapability(Texture.ALLOW_IMAGE_WRITE);
	    
	    Appearance app = new Appearance();
		    app.setPolygonAttributes(polAttr);
		    app.setTransparencyAttributes(trasAttr);
		    app.setTextureAttributes(texAttr);
		    app.setTexture(texture);
	   
	    this.setAppearance(app);
	  	}
	}
