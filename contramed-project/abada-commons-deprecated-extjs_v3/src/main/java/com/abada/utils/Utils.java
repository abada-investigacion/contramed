package com.abada.utils;

/*
 * #%L
 * Contramed
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

/**
 * Clase con metodos para parsear enums en strings y viceversa
 * @author katsu
 */
public class Utils {
	public static String get(Object value){		
		return value.toString().toLowerCase();
	}
	
	public static Object get(String name,Class clazz){			
		Object [] enums=clazz.getEnumConstants();
		for (Object obj:enums){
			if (obj.toString().equalsIgnoreCase(name))
				return obj;
		}
		return null;
	}
}
