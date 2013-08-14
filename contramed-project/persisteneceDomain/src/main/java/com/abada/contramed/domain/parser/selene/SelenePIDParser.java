/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.domain.parser.selene;

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

import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.datatype.CX;
import ca.uhn.hl7v2.model.v25.datatype.DLN;
import ca.uhn.hl7v2.model.v25.datatype.ID;
import ca.uhn.hl7v2.model.v25.datatype.TS;
import ca.uhn.hl7v2.model.v25.datatype.XPN;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.datatype.IS;
import ca.uhn.hl7v2.model.v25.datatype.ST;
import ca.uhn.hl7v2.model.v25.datatype.XAD;
import ca.uhn.hl7v2.model.v25.datatype.XTN;
import com.abada.contramed.persistence.entity.Address;
import com.abada.contramed.persistence.entity.City;
import com.abada.contramed.persistence.entity.Nation;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PatientId;
import com.abada.contramed.persistence.entity.Province;
import com.abada.contramed.persistence.entity.Region;
import com.abada.contramed.persistence.entity.Table0001;
import com.abada.contramed.persistence.entity.Table0002;
import com.abada.contramed.persistence.entity.Tablez029;
import com.abada.contramed.persistence.entity.Telephone;
import com.abada.contramed.persistence.entity.enums.TypeTelephone;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parseador especifico par el segmento PID de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento PID contiene la informacion especifica de un paciente.
 * @author katsu
 */
class SelenePIDParser {

    private static final Log log = LogFactory.getLog(SelenePIDParser.class);

    /**
     * Parseo de la entidad patient obteniendo el segmento pid
     * @param pid
     * @param patient
     */
    public void parsePID(PID pid, Patient patient) {
        if (patient.getPatientIdList() == null) {
            patient.setPatientIdList(new ArrayList<PatientId>());
        }

        //2.1 --> new CIP
        CX cx = pid.getPid2_PatientID();
        if (cx != null && cx.getCx1_IDNumber() != null && cx.getCx1_IDNumber().getValue() != null && !"".equals(cx.getCx1_IDNumber().getValue())) {
            addPatientId(patient, cx.getCx1_IDNumber().getValue(), "CIP");
        }
        //3.1 --> news PatientId  se puede repetir
        CX[] cxarray = pid.getPid3_PatientIdentifierList();
        if (cxarray != null && cxarray.length > 0) {
            for (CX cxx : cxarray) {
                addPatientId(patient, cxx.getCx1_IDNumber().getValue(), cxx.getCx5_IdentifierTypeCode().getValue());
            }
        }
        //5.123 --> news Patient
        XPN[] xpnarray = pid.getPid5_PatientName();
        if (xpnarray != null && xpnarray.length > 0) {
            String nombre = null, apellido1 = null, apellido2 = null;
            if (xpnarray[0].getXpn1_FamilyName() != null && xpnarray[0].getXpn1_FamilyName().getFn1_Surname() != null) {
                apellido1 = xpnarray[0].getXpn1_FamilyName().getFn1_Surname().getValue();
            }
            if (xpnarray[0].getXpn2_GivenName() != null) {
                nombre = xpnarray[0].getXpn2_GivenName().getValue();
            }
            if (xpnarray[0].getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof() != null) {
                apellido2 = xpnarray[0].getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().getValue();
            }
            if (nombre != null && !"".equals(nombre)) {
                patient.setName(nombre);
            }
            if (apellido1 != null && !"".equals(apellido1)) {
                patient.setSurname1(apellido1);
            }
            if (apellido2 != null && !"".equals(apellido2)) {
                patient.setSurname2(apellido2);
            }
        }
        //7.1-->news Patient fecha nacimiento
        TS ts = pid.getPid7_DateTimeOfBirth();
        if (ts != null && ts.getTs1_Time() != null && ts.getTs1_Time().getValue() != null && !"".equals(ts.getTs1_Time().getValue())) {
            patient.setBirthday(Utils.toDate(ts.getTs1_Time().getValue()));
        }
        //8.1-->news Patient sexo
        IS is = pid.getPid8_AdministrativeSex();
        if (is != null && is.getValue() != null && !"".equals(is.getValue())) {
            Table0001 table0001 = new Table0001();
            table0001.setCode(is.getValue());
            patient.setGenre(table0001);
        }
        //11.1-->news Address
        //11.2 numero
        //11.3-8 city
        XAD[] xadarray = pid.getPid11_PatientAddress();
        if (xadarray != null && xadarray.length > 0) {
            for (XAD xad : xadarray) {
                addPatientAddress(patient, xad);
            }
        }

        //13.1-->news telephone fijo
        //13.5 prefijo telefono
        XTN[] xtnarray = pid.getPid13_PhoneNumberHome();
        if (xtnarray != null && xtnarray.length > 0) {
            for (XTN xad : xtnarray) {
                addTelephone(patient, xad, TypeTelephone.HOME);
            }
        }
        //14.1-->news telephone movil
        //14.5 prefijo telefono
        xtnarray = pid.getPid14_PhoneNumberBusiness();
        if (xtnarray != null && xtnarray.length > 0) {
            for (XTN xad : xtnarray) {
                addTelephone(patient, xad, TypeTelephone.CELULAR);
            }
        }

        //16.1-->news patient

        CE ce = pid.getPid16_MaritalStatus();
        if (ce != null && ce.getCe1_Identifier() != null && ce.getCe1_Identifier().getValue() != null && !"".equals(ce.getCe1_Identifier().getValue())) {
            Table0002 table002 = new Table0002();
            table002.setCode(ce.getCe1_Identifier().getValue());
            patient.setMaritalStatus(table002);

        }
        //19.1-- news patient_id numero seguridad social
        ST st = pid.getPid19_SSNNumberPatient();
        if (st != null && st.getValue() != null && !"".equals(st.getValue())) {
            addPatientId(patient, st.getValue(), "SS");
        }
        //20.1-- news patient_id Número del DNI por tanto el type a NNESP
        DLN dln = pid.getPid20_DriverSLicenseNumberPatient();
        if (dln.getDln1_LicenseNumber() != null && dln.getDln1_LicenseNumber().getValue() != null && !"".equals(dln.getDln1_LicenseNumber().getValue())) {
            addPatientId(patient, dln.getDln1_LicenseNumber().getValue(), "NNESP");
        }

        // 29.1 -- news patient
        ts = pid.getPid29_PatientDeathDateAndTime();
        if (ts != null && ts.getTs1_Time() != null && ts.getTs1_Time().getValue() != null && !"".equals(ts.getTs1_Time().getValue())) {
            patient.setExitusDate(Utils.toDate(ts.getTs1_Time().getValue()));
        }
        // 30.1 -- news patient
        ID id = pid.getPid30_PatientDeathIndicator();
        if (id != null && id.getValue() != null && !"".equals(id.getValue())) {
            patient.setExitus(Utils.toBoolean(id.getValue()));
        }
        // 31.1 -- news patient waiting_exitus
        id = pid.getPid31_IdentityUnknownIndicator();
        if (id != null && id.getValue() != null && !"".equals(id.getValue())) {
            patient.setWaitingExitus(Utils.toBoolean(id.getValue()));
        }

        // 23.1, 26.1,27.1,27.4 Para determinar la población de nacimiento
        //nacionalidad de nacimiento el 17
        st = pid.getPid23_BirthPlace();
        CE[] cearray = pid.getPid26_Citizenship();
        ce = pid.getPid17_Religion();
        CE ce27 = pid.getPid27_VeteransMilitaryStatus();
        addBirthPlace(patient, st, cearray, ce, ce27);


    }

    /**
     * parseo de patientId añadiendo la lista si ya no tienes ese id
     * @param patient
     * @param value
     * @param type
     */
    public void addPatientId(Patient patient, String value, String type) {
        boolean existe = false;
        if (type != null && value != null && !"".equals(value) && !"".equals(type)) {
            for (int i = 0; i < patient.getPatientIdList().size(); i++) {
                if (patient.getPatientIdList().get(i).getType().getCode().equals(type)
                  ) {
                    existe = true;
                }
            }
            if (!existe) {
                PatientId patientId = new PatientId();
                patientId.setValue(value);
                Tablez029 tablez029 = new Tablez029();
                tablez029.setCode(type);
                patientId.setType(tablez029);
                patient.addPatientId(patientId);
            }

        }
    }

    /**
     * parseamos los datos de telefono al paciente
     * @param patient
     * @param telefono
     * @param type
     */
    private void addTelephone(Patient patient, XTN telefono, TypeTelephone type) {
        String prefijo = "";
        if (patient.getTelephoneList() == null) {
            patient.setTelephoneList(new ArrayList<Telephone>());
        }

        Telephone telephone = new Telephone();
        if (telefono.getXtn5_CountryCode() != null && telefono.getXtn5_CountryCode().getValue() != null && !"".equals(telefono.getXtn5_CountryCode().getValue())) {
            prefijo = telefono.getXtn5_CountryCode().getValue();
        }
        if (telefono.getXtn1_TelephoneNumber() != null && telefono.getXtn1_TelephoneNumber().getValue() != null && !"".equals(telefono.getXtn1_TelephoneNumber().getValue())) {
            telephone.setPhone(prefijo + telefono.getXtn1_TelephoneNumber().getValue());
            telephone.setType(type);//tipo de telefono movil
            patient.addTelephone(telephone);
        }

    }

    /**
     * parseamos al paciente su direccion
     * @param patient
     * @param direcion
     */
    private void addPatientAddress(Patient patient, XAD direcion) {
        //si la ciudad viene a null no insertamos Address para ese paciente
        if (direcion.getXad3_City() != null && direcion.getXad3_City().getValue() != null) {


            Address address = new Address();
            if (direcion.getXad1_StreetAddress() != null && direcion.getXad1_StreetAddress().getStreetName() != null) {
                String street = direcion.getXad1_StreetAddress().getStreetName().getValue();
                if (street != null && !"".equals(street)) {
                    address.setDirection(street);
                }
            }
            if (direcion.getXad5_ZipOrPostalCode() != null) {
                String codigopostal = direcion.getXad5_ZipOrPostalCode().getValue();
                if (codigopostal != null && !"".equals(codigopostal)) {
                    address.setPostalCode(codigopostal);
                }
            }
            if (direcion.getXad2_OtherDesignation() != null) {
                String numero = direcion.getXad2_OtherDesignation().getValue();
                if (numero != null && !"".equals(numero)) {
                    address.setNumber(numero);
                }
            }
            City city = new City();
            Province province = new Province();
            if (direcion.getXad3_City() != null) {
                String ciudad = direcion.getXad3_City().getValue();
                if (ciudad != null && !"".equals(ciudad)) {
                    city.setIdcity(Utils.toInteger(ciudad));
                }
            }

            if (direcion.getXad4_StateOrProvince() != null) {
                String provincia = direcion.getXad4_StateOrProvince().getValue();
                if (provincia != null && !"".equals(provincia)) {
                    province.setIdprovince(Utils.toInteger(provincia));
                }
            }
            Region region = new Region();
            Nation nation = new Nation();
            if (direcion.getXad6_Country().getValue() != null) {
                String nacion = direcion.getXad6_Country().getValue();
                if (nacion != null && !"".equals(nacion)) {
                    nation.setIdnation(Utils.toInteger(nacion));
                }
            }

            region.setNationIdnation(nation);//TODO no encuentro la region he puesto el pais
            province.setRegionIdregion(region);
            city.setProvinceIdprovince(province);
            address.setCityIdcity(city);
            patient.addAddress(address);
        }
    }

    /**
     * parseamos al paciente su lugar de nacimiento
     * @param patient
     * @param provincia
     * @param comunidad
     * @param nacion
     * @param municipio
     */
    private void addBirthPlace(Patient patient, ST provincia, CE[] comunidad, CE nacion, CE municipio) {
        Province province = new Province();
        Region region = new Region();
        City city = new City();
        Nation nation = new Nation();

        if (provincia != null && provincia.getValue() != null && !"".equals(provincia.getValue())) {
            province.setIdprovince(Utils.toInteger(provincia.getValue()));
        }
        if (comunidad != null && comunidad.length > 0 && comunidad[0].getCe1_Identifier().getValue() != null) {
            if (comunidad[0].getCe1_Identifier() != null && comunidad[0].getCe1_Identifier().getValue() != null && !"".equals(comunidad[0].getCe1_Identifier().getValue())) {
                region.setIdregion(Utils.toInteger(comunidad[0].getCe1_Identifier().getValue()));
            }
        }
        if (nacion != null && nacion.getCe1_Identifier() != null && nacion.getCe1_Identifier().getValue() != null) {
            nation.setIdnation(Utils.toInteger(nacion.getCe1_Identifier().getValue()));

        }
        region.setNationIdnation(nation);
        province.setRegionIdregion(region);
        city.setProvinceIdprovince(province);
        if (municipio != null && municipio.getCe1_Identifier() != null && municipio.getCe1_Identifier().getValue() != null && !"".equals(municipio.getCe1_Identifier().getValue())) {
            city.setIdcity(Utils.toInteger(municipio.getCe1_Identifier().getValue()));
            patient.setBirthCity(city);
        }

    }
}
