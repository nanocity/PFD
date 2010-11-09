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

/**
 * Esta clase trata de compactar los parámetro que definen una posición dentro del campo
 * de juego en dos dimensiones.
 * 
 * X - Eje que define la anchura del campo, paralelo a la linea que corta el campo en dos
 *     mitades simétricas.
 * 
 * Y - Eje que define el largo del campo. Este eje se puede trazar desde la 'dead box' de
 *     un equipo hasta el otro.
 * 
 * @author Luis Ciudad García.
 */
public class PFDPosition {
	public double x;
	public double y;
	}
