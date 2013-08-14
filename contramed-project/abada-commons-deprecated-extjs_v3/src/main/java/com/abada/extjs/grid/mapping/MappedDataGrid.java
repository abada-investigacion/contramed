/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.extjs.grid.mapping;

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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase usada para facilitar el mapeo de datos en los grid panels de extjs.<br/>
 * Las key de los Map se corresponde con el nombre que se genera durante la fase de reflexion.
 * @author katsu
 */
public class MappedDataGrid<T> {
    /**
     * Informacion de las variables que tiene la clase o clases de las que se compone.<br/>
     * La informacion de variables esta filtrada ya por los valores de fielsForMap<br/>
     *
     */
    private Map<String, FieldInformation> targetInformation;
    /**
     * Valores que tiene el objeto que se esta mapeando
     */
    private Map<String, Object> values;
    /**
     * Campos que se deben mapear, se ignoran todos los demas. Una lista blanca(whitelist)
     */
    private List<String> fieldsForMap;
    /**
     * Objeto a mapear
     */
    private T target;

    /**
     * Constructor
     * @param target Objecto a mapear
     * @param fieldsForMap listado de variables a mapear
     */
    MappedDataGrid(T target, List<String> fieldsForMap) {
        this.target = target;
        this.fieldsForMap = fieldsForMap;
        this.generateMapping();
        this.generateValues();
    }

    public Map<String, FieldInformation> getTargetInformation() {
        return targetInformation;
    }

    public Map<String, Object> getValues() {
        if (values == null) {
            values = new HashMap<String, Object>();
        }
        return values;
    }

    /**
     * Devuelve los nombres de las variables ya filtradas
     * @return
     */
    public Collection<String> getFieldsName() {
        return this.targetInformation.keySet();
    }

    /**
     * Devuelve los nombre de las variables que tenian algun valor
     * @return
     */
    public Collection<String> getFieldsNameWithValue() {
        return this.getValues().keySet();
    }

// <editor-fold defaultstate="collapsed" desc="Carga los valores de la clase objetivo">
    private void generateValues() {
        if (this.target != null) {
            for (FieldInformation fi : targetInformation.values()) {
                Object value = this.getValue(fi.getMapping());
                if (value != null) {
                    this.getValues().put(fi.getName(), value);
                }
            }
        }
    }

    private Object getValue(String mapping) {
        String[] aux = mapping.split("\\.");
        if (aux.length == 1) {
            //Caso base
            return getValue(mapping, this.target);
        } else {
            String mappingAux = cutLastPoint(mapping);
            Object target = getValue(mappingAux);
            if (target != null) {
                return getValue(mapping.substring(mappingAux.length() + 1, mapping.length()), target);
            }
        }
        return null;
    }

    private String cutLastPoint(String cad) {
        int i = cad.lastIndexOf('.');
        if (i >= 0) {
            return cad.substring(0, i);
        } else {
            return cad;
        }
    }

    private Object getValue(String mapping, Object target) {
        Object result = null;
        Method method = this.getGetterFor(mapping, target.getClass());
        if (method != null) {
            try {
                result = method.invoke(target);
            } catch (Exception e) {
            }
        }
        return result;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Inicia el array con los mapping para los filtros del grid">
    private void generateMapping() {
        if (this.targetInformation == null) {
            this.targetInformation = new HashMap<String, FieldInformation>();
        } else {
            this.targetInformation.clear();
        }

        List<FieldInformation> result = this.generate(target.getClass(), "", new ArrayList<Class>());
        if (result != null) {
            for (FieldInformation fi : result) {
                if (this.fieldsForMap == null) {
                    this.targetInformation.put(fi.getName(), fi);
                } else {
                    if (this.fieldsForMap.contains(fi.getName())) {
                        this.targetInformation.put(fi.getName(), fi);
                    }
                }
            }
        }
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Reflection calls">
    private List<FieldInformation> generate(Class entity, String parent, List<Class> visited) {
        List<FieldInformation> result = new ArrayList<FieldInformation>();
        if (!visited.contains(entity)) {
            //visitado
            visited.add(entity);
            //recorrer parametros
            for (Field f : entity.getDeclaredFields()) {
                List<Class<?>> aux = Arrays.asList(f.getType().getInterfaces());
                if (aux.contains(Collection.class)) {
                } else if (Arrays.asList(f.getType().getClasses()).contains(Map.class)) {
                } else if (f.getType().isPrimitive() || f.getType().getName().startsWith("java.lang") || f.getType().isEnum() || f.getType().getName().startsWith("java.util") || f.getType().getName().startsWith("java.math")) {
                    result.add(generateInformation(f, parent));
                } else {
                    String parentAux = parent + f.getName() + ".";
                    result.addAll(generate(f.getType(), parentAux, clone(visited)));
                }
            }
        }
        return result;
    }

    private FieldInformation generateInformation(Field f, String parent) {
        FieldInformation result = new FieldInformation();
        if ("".equals(parent)) {
            result.setName(f.getName());

        } else {
            result.setName(parent.replace(".", "_") + f.getName());

        }
        result.setMapping(parent + f.getName());
        return result;
    }

    private Method getGetterFor(Field field) {
        return this.getGetterFor(field.getName(), field.getDeclaringClass());
    }

    private Method getGetterFor(String methodName, Class entity) {
        for (Method m : entity.getDeclaredMethods()) {
            if (m.getName().equals(getGetterMethodName(methodName, false)) || m.getName().equals(getGetterMethodName(methodName, true))) {
                if (m.getGenericParameterTypes().length == 0) {
                    return m;
                }
            }
        }
        return null;
    }

    private String getGetterMethodName(String fieldName, boolean is) {
        if (is) {
            return "is" + firstCharUpper(fieldName);

        } else {
            return "get" + firstCharUpper(fieldName);

        }
    }

    private String firstCharUpper(String cad) {
        String first = cad.substring(0, 1).toUpperCase();
        String aux = cad.substring(1);
        return first + aux;
    }

    private List<Class> clone(List<Class> list) {
        List<Class> result = new ArrayList<Class>();
        for (Class c : list) {
            result.add(c);

        }
        return result;
    }// </editor-fold>
}
