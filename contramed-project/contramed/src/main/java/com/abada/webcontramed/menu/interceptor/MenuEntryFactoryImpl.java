/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.menu.interceptor;

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

import com.abada.contramed.persistence.entity.Staff;
import com.abada.contramed.persistence.entity.enums.TypeRole;
import com.abada.web.component.MenuEntry;
import com.abada.web.component.factory.MenuEntryFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author katsu
 */
public class MenuEntryFactoryImpl implements MenuEntryFactory {

    public void createMenu(ModelAndView modelAndView, Object user) {
        Staff staff = (Staff) user;
        if (staff != null && modelAndView != null) {
            this.createStaffMenu(modelAndView, staff);
            modelAndView.addObject(USER_MENU, this.createMenuEntries(staff.getRole()));
        }
    }

    private List<MenuEntry> createMenuEntries(TypeRole role) {
        List<MenuEntry> result = this.createMenuEntriesPriv(role, role);
        //Salir
        MenuEntry aux = new MenuEntry();
        aux.setText("Salir");
        aux.setAction("login.htm");
        aux.setIconCls("logout");
        aux.setTooltip("Abandonar la aplicaci&oacute;n");
        result.add(aux);

        return result;
    }

    private List<MenuEntry> createMenuEntriesPriv(TypeRole role, TypeRole initRole) {
        List<MenuEntry> result = new ArrayList<MenuEntry>();
        MenuEntry aux;
        switch (role) {
            case PHARMACIST:
                aux = new MenuEntry();
                aux.setText("Ges. Especialidades");
                aux.setAction("catalogoMedicamentos.htm");
                aux.setIconCls("capsulas");
                aux.setTooltip("Permite a&ntilde;adir/eliminar/modificar las diferentes especialidades con las que "
                        + "trabaja la aplicaci&oacute;n");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("His. Preparación Cajetines");
                aux.setAction("prepareGlobalHistoric.htm");
                aux.setIconCls("farmacia");
                aux.setTooltip("Son datos procedentes de farmacia, donde se muestra "
                        + "la información de los tratamientos de un determinado paciente"
                        + " y la persona que le administro la medicaci&oacute;n");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Inc. Preparaci&oacute;n Cajetines");
                aux.setAction("prepareIncidences.htm");
                aux.setIconCls("incidenciafarmacia2");
                aux.setTooltip("Anomal&iacute;as que se han producido en farmacia mientras se preparaba el cajet&iacute;n de medicamentos");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Ges. de Principios Activos");
                aux.setAction("principioActivo.htm");
                aux.setIconCls("principioactivo");
                aux.setTooltip("Muestra los diferentes principios activos que hay en la aplicaci&oacute;n. "
                        + "Permitiendo insertar m&aacute;s principios activos, modificarlos o eliminarlos");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Asig. de Especialides a P.Activos");
                aux.setAction("principioHasEspecialidad.htm");
                aux.setIconCls("espeprincipioactivo");
                aux.setTooltip("Muestra la especialidad asignada a cada principio activo. Permite abrir el panel para insertar "
                        + "y modificar la especialidad asociada a un principio activo");
                result.add(aux);
            case NURSE_PHARMACY:
                aux = new MenuEntry();
                aux.setText("His. Personal Pre. Cajetines");
                aux.setAction("prepareHistoricByStaff.htm");
                aux.setIconCls("historialfarmacia");
                aux.setTooltip("Informaci&oacute;n de cada usuario con los datos procedentes de farmacia, donde se muestra un grid con la "
                        + "informaci&oacute;n  de los tratamientos de un determinado paciente");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Inc. Personales Pre. Cajetines");
                aux.setAction("prepareIncidencesByStaff.htm");
                aux.setIconCls("incidenciafarmacia");
                aux.setTooltip("Distintas incidencias que produce un personal de Farmacia (cada usuario ve las suyas)");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Ges. de Plantillas");
                aux.setAction("templatesMedication.htm");
                aux.setIconCls("formulario");
                aux.setTooltip("Muestra las distintas plantillas que hay en la aplicaci&oacute;n");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Asig. de Plantilla a Especialidad");
                aux.setAction("catalogoHasTemplates.htm");
                aux.setIconCls("templatelapiz");
                aux.setTooltip("Muestra la plantilla asignada a cada especialidad. Permite abrir el panel para insertar "
                        + "y modificar la plantilla asociada a una Especialidad");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Ges. Dosis");
                aux.setAction("dose.htm");
                aux.setIconCls("jeringuilla");
                aux.setTooltip("Muestra las diferentes dosis que hay en la aplicaci&oacute;n. "
                        + "Permitiendo insertar m&aacute;s dosis y modificarlas");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Preparaci&oacute;n Cajet&iacute;n");
                aux.setAction("preparebox.htm");
                aux.setIconCls("carrito");
                aux.setTooltip("Permite la preparaci&oacute;n del cajet&iacute;n de las distintas"
                        + " dosis para cada cama");
                result.add(aux);


                break;

            case NURSE_PLANT_SUPERVISOR:
                if (initRole != TypeRole.SYSTEM_ADMINISTRATOR) {
                    aux = new MenuEntry();
                    aux.setText("Ges. Dosis");
                    aux.setAction("dose.htm");
                    aux.setIconCls("jeringuilla");
                    aux.setTooltip("Muestra las diferentes dosis que hay en la aplicaci&oacute;n. "
                            + "Permitiendo insertar m&aacutes dosis y modificarlas");
                    result.add(aux);
                }

                aux = new MenuEntry();
                aux.setText("His. Adm. Medicaci&oacute;n");
                aux.setAction("givesGlobalHistoric.htm");
                aux.setIconCls("enfermera");
                aux.setTooltip("Muestra el hist&oacute;rico de la administraci&oacute;n al paciente de su medicación,"
                        + " incluido el personal lo que administra y el paciente que lo recibe");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Inc. Adm. Medicaci&oacute;n");
                aux.setAction("givesIncidences.htm");
                aux.setIconCls("incidenciasenfermeria");
                aux.setTooltip("Muestra un hist&oacute;rico de anomalías ocurridas en la administraci&oacute;n de la medicaci&oacute;n");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Observaciones");
                aux.setAction("observation.htm");
                aux.setIconCls("observaciones1");
                aux.setTooltip("Muestra las observaciones que las enfermeras notifican.");
                result.add(aux);

            //break;
            case NURSE_PLANT:
                aux = new MenuEntry();
                aux.setText("His. Personal Adm. Medicaci&oacute;n");
                aux.setAction("givesHistoricByStaff.htm");
                aux.setIconCls("historialenfermeria");
                aux.setTooltip("Muestra el historial de administraci&oacute;n de medicación "
                        + "para un personal de enfermeria");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("His. Adm. Medicaci&oacute;n Precisa ");
                aux.setAction("givesHistoricPrecisa.htm");
                aux.setIconCls("historyprecisa");
                aux.setTooltip("Muestra el historial de administraci&oacute;n de medicación "
                        + "para aquellos medicamentos que si precisan  ");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Inc. Personal Adm. Medicaci&oacute;n");
                aux.setAction("givesIncidencesByStaff.htm");
                aux.setIconCls("incidenciasenfermeria2");
                aux.setTooltip("Incidencias propias que ha detectado una persona de enfermer&iacute;a");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Adm. Medicaci&oacute;n");
                aux.setAction("nursing.htm");
                aux.setIconCls("administracion");
                aux.setTooltip("El personal de enfermer&iacute;a dependiendo de la toma en la que se"
                        + " encuentra administra el tratamiento que debe dar al paciente");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Observaciones personales");
                aux.setAction("observationwithStaff.htm");
                aux.setIconCls("observaciones1");
                aux.setTooltip("Muestra las observaciones que las enfermeras notifican.");
                result.add(aux);
                break;
            case ADMINISTRATIVE:
                aux = new MenuEntry();
                aux.setText("Ges. Personal");
                aux.setAction("staff.htm");
                aux.setIconCls("user");
                aux.setTooltip("Dar de alta en la aplicaci&oacute;n a personal"
                        + " nuevo, modificarlo o borrarlo, ya sea de farmacia como de enfermeria, si esta dado de alta en el LDAP");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Ges. Tomas");
                aux.setAction("timingRange.htm");
                aux.setTooltip("Permite gestionar los distintos rangos horarios de las tomas");
                aux.setIconCls("hora");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Ges. Tags Pacientes");
                aux.setAction("patientTag.htm");
                aux.setIconCls("paciente");
                aux.setTooltip("Asignar una tarjeta de radiofrecuencia(RFID) a cada paciente. Para realizar esta"
                        + " tarea, lo primero que se debe realizar es la b&uacute;squeda de dicho paciente");
                result.add(aux);

                aux = new MenuEntry();
                aux.setText("Ges.Tags Camas/Recursos");
                aux.setAction("recursoTag.htm");
                aux.setTooltip("Permite la gesti&oacute;n de asignaci&oacute;n de dos tag distinto a una misma cama;"
                        + " insertandolo, modificandolo o borrando dicha asociaci&oacute;n");
                aux.setIconCls("cama");
                result.add(aux);
                break;
            case SYSTEM_ADMINISTRATOR:
                result.addAll(this.createMenuEntriesPriv(TypeRole.PHARMACIST, TypeRole.SYSTEM_ADMINISTRATOR));
                result.addAll(this.createMenuEntriesPriv(TypeRole.NURSE_PLANT_SUPERVISOR, TypeRole.SYSTEM_ADMINISTRATOR));
                result.addAll(this.createMenuEntriesPriv(TypeRole.ADMINISTRATIVE, TypeRole.SYSTEM_ADMINISTRATOR));
                break;
        }
        return result;
    }

    private void createStaffMenu(ModelAndView modelAndView, Staff staff) {
        modelAndView.addObject(USERNAME, staff.getUsername());
        modelAndView.addObject(NAME, staff.getName() + " " + staff.getSurname1() + " " + staff.getSurname2());
        modelAndView.addObject(ROLE, staff.getRole());
    }
}
