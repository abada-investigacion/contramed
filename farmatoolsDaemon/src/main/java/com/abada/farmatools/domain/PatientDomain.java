/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.farmatools.domain;

import com.abada.farmatools.enums.DBPrefix;
import java.util.EnumMap;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.farmatools.enums.Adt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static com.abada.farmatools.domain.SQLUtils.*;

/**
 *
 * @author aroldan
 */
public class PatientDomain implements IDomain{
    
    private static Log log = LogFactory.getLog(PatientDomain.class);
    protected Properties property;
    private EnumMap<DBPrefix, Connection> connections;

    public PatientDomain(EnumMap<DBPrefix, Connection> connections, Properties property) {
        this.property = property;
        this.connections = connections;
    }

    /**
     * select que obtiene los datos de los pacientes de bd, y los añada a un objeto
     * @param codigoSQL
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void selectPatient(Connection connection, String codigoSQL, List<Farmatools_Patient> lfarmatools_Patient, Adt adt) throws SQLException, ClassNotFoundException, IOException {
        ResultSet result = null;
        Farmatools_Patient farmatools_Patient;
        Statement sentencia = connection.createStatement();
        result = sentencia.executeQuery(codigoSQL);
        while (result.next()) {
            farmatools_Patient = new Farmatools_Patient();
            farmatools_Patient.setNumerohc(result.getInt("Numerohc"));
            if (result.wasNull()) {
                farmatools_Patient.setNumerohc(null);
            }
            if (adt.equals(adt.A17)||adt.equals(adt.A02)||adt.equals(adt.A03)) {
                farmatools_Patient.setPatientswapnumerohc(result.getInt("patientswapnumerohc"));
                if (result.wasNull()) {
                    farmatools_Patient.setPatientswapnumerohc(null);
                }
                farmatools_Patient.setPatientswapcama(result.getString("patientswapcama"));

            }
            if (adt.equals(adt.A47)) {
                farmatools_Patient.setNumerohcanterior(result.getInt("numerohcanterior"));
                if (result.wasNull()) {
                    farmatools_Patient.setNumerohcanterior(null);
                }
                farmatools_Patient.setNumerohcActual(result.getString("numerohcActual"));

            }
            mshAdt(farmatools_Patient, adt);
            farmatools_Patient.setCod_centro(result.getInt("cod_centro"));
            if (result.wasNull()) {
                farmatools_Patient.setCod_centro(null);
            }
            farmatools_Patient.setCip(result.getString("numtis"));
            farmatools_Patient.setNumeroseguridadSocial(result.getString("numeross"));
            farmatools_Patient.setApellid1(result.getString("Apellid1"));
            farmatools_Patient.setNombre(result.getString("nombre"));
            farmatools_Patient.setApellid2(result.getString("Apellid2"));
            farmatools_Patient.setCod_pais(result.getString("Cod_pais"));
            farmatools_Patient.setCod_postal(result.getString("Cod_postal"));
            farmatools_Patient.setDireccion(result.getString("Direccion"));
            farmatools_Patient.setFechanac(result.getDate("Fechanac"));
            farmatools_Patient.setCod_poblacion(result.getInt("cod_poblacion"));
            if (result.wasNull()) {
                farmatools_Patient.setCod_poblacion(null);
            }
            farmatools_Patient.setProvincia(result.getString("provincia"));
            farmatools_Patient.setCod_pais(result.getString("Cod_pais"));
            farmatools_Patient.setNifdni(result.getString("Nifdni"));
            farmatools_Patient.setPaciente_ncama(result.getString("Paciente_ncama"));
            farmatools_Patient.setSexo(result.getString("Sexo"));
            farmatools_Patient.setTelefono(result.getString("Telefono"));
            lfarmatools_Patient.add(farmatools_Patient);
        }

        sentencia.close();
    }

    /**
     * añadimos segun el adt su codigo, triger y estructura del segmento msh
     * @param farmatools_patient
     * @param adt
     */
    private void mshAdt(Farmatools_Patient farmatools_patient, Adt adt) {
        if (adt.equals(adt.A01)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A01");
            farmatools_patient.setMessageStructure("ADT_A01");

        } else if (adt.equals(adt.A02)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A02");
            farmatools_patient.setMessageStructure("ADT_A01");

        } else if (adt.equals(adt.A03)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A03");
            farmatools_patient.setMessageStructure("ADT_A03");

        } else if (adt.equals(adt.A17)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A17");
            farmatools_patient.setMessageStructure("ADT_A17");

        } else if (adt.equals(adt.A28)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A28");
            farmatools_patient.setMessageStructure("ADT_A05");

        } else if (adt.equals(adt.A29)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A29");
            farmatools_patient.setMessageStructure("ADT_A21");

        } else if (adt.equals(adt.A31)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A31");
            farmatools_patient.setMessageStructure("ADT_A05");
        } else if (adt.equals(adt.A40)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A40");
            farmatools_patient.setMessageStructure("ADT_A39");

        } else if (adt.equals(adt.A47)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A47");
            farmatools_patient.setMessageStructure("ADT_A30");
       } else if (adt.equals(adt.A69)) {
            farmatools_patient.setMessageCode("ADT");
            farmatools_patient.setTriggerEvent("A69");
            farmatools_patient.setMessageStructure("ADT_A01");

        }
    }

    /**
     * modificamos en farmatools_patient la cama del paciente
     * @param bed
     * @param numerohc
     */
    public void updatePatientbed(String bed, int numerohc, Integer cod_centro) {
        String cama = null;
        if (bed != null) {
            cama = "'" + bed + "'";
        }
        String update = "update farmatools_patient f set f.paciente_ncama=" + cama + ",f.cod_centro=" + cod_centro
                + " where f.numerohc=" + numerohc;
        try {
            execute(connections.get(DBPrefix.LOCAL), update);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
    }

    /**
     * insertamos en farmatools_patient un nuevo paciente
     * @param f
     */
    public void insertPatient(Farmatools_Patient f) {
        String apellid1 = null, nombre = null, apellid2 = null, sexo = null, direccion = null, cod_postal = null,
                provincia = null, cod_pais = null, telefono = null, nifdni = null, paciente_ncama = null,
                fechanac = null, cod_centro = null, cod_poblacion = null, numeroSeguridadSocial = null, cip = null;
        if (f.getApellid1() != null) {
            apellid1 = "'" + f.getApellid1() + "'";
        }
        if (f.getNombre() != null) {
            nombre = "'" + f.getNombre() + "'";
        }
        if (f.getApellid2() != null) {
            apellid2 = "'" + f.getApellid2() + "'";
        }
        if (f.getSexo() != null) {
            sexo = "'" + f.getSexo() + "'";
        }
        if (f.getDireccion() != null) {
            direccion = "'" + f.getDireccion() + "'";
        }
        if (f.getCod_postal() != null) {
            cod_postal = "'" + f.getCod_postal() + "'";
        }
        if (f.getProvincia() != null) {
            provincia = "'" + f.getProvincia() + "'";
        }
        if (f.getCod_pais() != null) {
            cod_pais = "'" + f.getCod_pais() + "'";
        }
        if (f.getTelefono() != null) {
            telefono = "'" + f.getTelefono() + "'";
        }
        if (f.getNifdni() != null) {
            nifdni = "'" + f.getNifdni() + "'";
        }
        if (f.getPaciente_ncama() != null) {
            paciente_ncama = "'" + f.getPaciente_ncama() + "'";
        }
        if (f.getFechanac() != null) {
            fechanac = "'" + f.getFechanac() + "'";
        }
        if (f.getCod_centro() != null) {
            cod_centro = "'" + f.getCod_centro() + "'";
        }
        if (f.getCod_poblacion() != null) {
            cod_poblacion = "'" + f.getCod_poblacion() + "'";
        }
        if (f.getNumeroseguridadSocial() != null) {
            numeroSeguridadSocial = "'" + f.getNumeroseguridadSocial() + "'";
        }
        if (f.getCip() != null) {
            cip = "'" + f.getCip() + "'";
        }
        String insert = "insert into farmatools_patient (numerohc,apellid1,nombre,"
                + "apellid2,fechanac,sexo,direccion,cod_postal,"
                + "cod_poblacion,provincia,cod_pais,telefono,nifdni,paciente_ncama,cod_centro,numtis,numeross,numerohcActual) values"
                + "(" + f.getNumerohc() + "," + apellid1 + "," + nombre + "," + apellid2 + "," + fechanac
                + "," + sexo + "," + direccion + "," + cod_postal + "," + cod_poblacion
                + "," + provincia + "," + cod_pais + "," + telefono + "," + nifdni
                + "," + paciente_ncama + "," + cod_centro + "," + cip + "," + numeroSeguridadSocial + ",'S')";
        try {
            execute(connections.get(DBPrefix.LOCAL), insert);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
    }

    /**
     *  modificamos en farmatools_patient los datos demograficos
     * @param f
     */
    public void updatePatientA31(Farmatools_Patient f) {
        String sexo = null, direccion = null, cod_postal = null,
                provincia = null, cod_pais = null, telefono = null,
                fechanac = null, apellid1 = null, nombre = null,
                apellid2 = null, cod_centro = null, cod_poblacion = null;

        if (f.getSexo() != null) {
            sexo = "'" + f.getSexo() + "'";
        }
        if (f.getDireccion() != null) {
            direccion = "'" + f.getDireccion() + "'";
        }
        if (f.getCod_postal() != null) {
            cod_postal = "'" + f.getCod_postal() + "'";
        }
        if (f.getProvincia() != null) {
            provincia = "'" + f.getProvincia() + "'";
        }
        if (f.getCod_pais() != null) {
            cod_pais = "'" + f.getCod_pais() + "'";
        }
        if (f.getTelefono() != null) {
            telefono = "'" + f.getTelefono() + "'";
        }
        if (f.getFechanac() != null) {
            fechanac = "'" + f.getFechanac() + "'";
        }
        if (f.getApellid1() != null) {
            apellid1 = "'" + f.getApellid1() + "'";
        }
        if (f.getNombre() != null) {
            nombre = "'" + f.getNombre() + "'";
        }
        if (f.getApellid2() != null) {
            apellid2 = "'" + f.getApellid2() + "'";
        }
        if (f.getCod_centro() != null) {
            cod_centro = "'" + f.getCod_centro() + "'";
        }
        if (f.getCod_poblacion() != null) {
            cod_poblacion = "'" + f.getCod_poblacion() + "'";
        }

        String update = "update farmatools_patient f set f.apellid1=" + apellid1
                + ", f.nombre=" + nombre + ", f.apellid2=" + apellid2 + ", f.sexo=" + sexo
                + ", f.direccion=" + direccion + ", f.cod_postal=" + cod_postal + ", f.cod_poblacion=" + cod_poblacion
                + ", f.provincia=" + provincia + ", f.cod_pais=" + cod_pais + ", f.telefono=" + telefono + ", f.fechanac=" + fechanac + ", f.cod_centro=" + cod_centro
                + " where f.numerohc=" + f.getNumerohc();
        try {
            execute(connections.get(DBPrefix.LOCAL), update);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
    }

    /**
     * modificacion numerohc por otro
     */
    private void updatePatient(String update) { 
        try {
            execute(connections.get(DBPrefix.LOCAL), update);
        } catch (SQLException ex) {
            log.error(SQLUtils.class.getName() + "ERROR", ex);
        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
    }

    /**
     * modificamos en farmatools_patient id dni, numeross, cif
     * @param f
     */
    public void updatePatientA47(Farmatools_Patient f) {
        String nifdni = null, cip = null, numeroSeguridadSocial = null;
        String update = null;
        if (f.getNifdni() != null) {
            nifdni = "'" + f.getNifdni() + "'";
        }
        if (f.getNumeroseguridadSocial() != null) {
            numeroSeguridadSocial = "'" + f.getNumeroseguridadSocial() + "'";
        }
        if (f.getCip() != null) {
            cip = "'" + f.getCip() + "'";
        }
        if (f.getNumerohcanterior() != null) {
            Statement sentencia;
            try {
                sentencia = connections.get(DBPrefix.LOCAL).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String codigoSQL = "Select p.numerohc from farmatools_patient_tmp p where p.numerohc in (" + f.getNumerohc() + ","
                        + f.getNumerohcanterior() + ")";
                ResultSet result = sentencia.executeQuery(codigoSQL);
                result.last();
                //si existen los 2 numerohc, insertamos es (numerohc nuevo) para farmatools_patient sino modificamos el que hay
                if (result.getRow() == 2) {
                    codigoSQL = "Select p.numerohc from farmatools_patient p where p.numerohc in (" + f.getNumerohc() + ","
                            + f.getNumerohcanterior() + ")";
                    result = sentencia.executeQuery(codigoSQL);
                    result.last();
                    if (result.getRow() < 2) {
                        insertPatient(f);
                    } else {
                        String cama = null;
                        if (f.getPaciente_ncama() != null) {
                            cama = "'" + f.getPaciente_ncama() + "'";
                        }
                        updatePatient("update farmatools_patient f set  f.paciente_ncama=" + cama + ",f.numerohcActual='S'"
                                + " where f.numerohc=" + f.getNumerohc());
                    }

                    update = "update farmatools_patient f set f.numerohcActual='N',f.paciente_ncama=null"
                            + " where f.numerohc=" + f.getNumerohcanterior();
                } else {
                    update = "update farmatools_patient f set  f.numerohc=" + f.getNumerohc() + ",f.nifdni=" + nifdni + ",f.numtis=" + cip + ",f.numeross=" + numeroSeguridadSocial
                            + " where f.numerohc=" + f.getNumerohcanterior();
                }
            } catch (SQLException ex) {
                log.error(SQLUtils.class.getName() + "ERROR", ex);
            }

        } else {
            update = "update farmatools_patient f set  f.nifdni=" + nifdni + ",f.numtis=" + cip + ",f.numeross=" + numeroSeguridadSocial
                    + " where f.numerohc=" + f.getNumerohc();
        }

        updatePatient(update);

    }

    /**
     * borramos paciente en farmatools_patient
     * @param numerohc
     * @param cod_centro
     */
    public void deletePatient(int numerohc) {
        String delete = "delete from farmatools_patient f where f.numerohc=" + numerohc;
        try {
            execute(connections.get(DBPrefix.LOCAL), delete);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
    }

    
 /**
     * borrado de los A47 que no necesitamos quedandonos con el numerohc actual en contramed
     */
    private void deleterepenumerohcA47(List<Farmatools_Patient> farmatools_patient) {
        for (int i = 0; i < farmatools_patient.size(); i++) {
            farmatools_patient.get(i).setPaciente_ncama(null);//quitamos la cama
            if ((farmatools_patient.get(i).getTriggerEvent().equalsIgnoreCase("A47") || farmatools_patient.get(i).getTriggerEvent().equalsIgnoreCase("A01")) && farmatools_patient.get(i).getNumerohcanterior() != null) {
                if (farmatools_patient.get(i).getNumerohcActual() == null || !farmatools_patient.get(i).getNumerohcActual().equalsIgnoreCase("S")) {
                    farmatools_patient.remove(i);
                    i = -1;
                }
            }
        }
    }

    /**
     * borro de farmatools_patient las filas de los numerohc no esten en farmatools_patient_tmp sabiendo
     * que numerohcactual en contramed ya no esta
     * @param connection
     */
    public void deleteFarmatoolsPatient() {
        String queryminusnumerohc = "(SELECT p.numerohc FROM farmatools_patient p minus SELECT pt.numerohc from farmatools_patient_tmp pt)";
        String delete = "delete from farmatools_patient f where f.numerohc in " + queryminusnumerohc
                + "and f.numerohcactual='N' ";
        try {
            execute(connections.get(DBPrefix.LOCAL), delete);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
    }

    public boolean findrecurso(String bed) {
        Statement sentencia;
        try {
            if (bed != null) {
                bed = "'" + bed + "'";
            }
            sentencia = connections.get(DBPrefix.LOCAL).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String codigoSQL = "Select * from farmatools_recurso r where r.nr=" + bed;
            ResultSet result = sentencia.executeQuery(codigoSQL);
            result.last();
            //si existe la cama devolvemos true
            if (result.getRow() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            log.error(SQLUtils.class.getName() + "ERROR", ex);
            return false;
        }
    }

    private void updateDaemonData() {
        try {
            String tabla_paci_his = property.getProperty("farmatools.tabla_paci_his");
            String tabla_paciente = property.getProperty("farmatools.tabla_paciente");
            String tabla_poblaciones = property.getProperty("farmatools.tabla_poblaciones");
            String pacientes = property.getProperty("farmatools.pacientes");
            String filtropacientes = "";


            if (!pacientes.equals("")) {
                filtropacientes = "where p.numerohc in (" + pacientes + ") ";
            }
            String columnsSelectfarmatools = "p.numerohc,p.apellid1,p.nombre,p.apellid2,p.fechanac,"
                    + "p.sexo,p.direccion,p.cod_postal,"
                    + "pobl.num_interno as cod_poblacion,pobl.provincia,pobl.cod_pais,p.telefono,p.nifdni,paci.ncama AS paciente_ncama,"
                    + "paci.cod_centro,p.numtis,REPLACE(p.numeross1||' '||p.numeross2||' '||p.numeross3,'  ',NULL) as numeroSS ";
            String deleteFarmatools_patient_tmp = "delete from FARMATOOLS_PATIENT_TMP";

            String queryFarmatools_patient = "SELECT distinct "
                    + columnsSelectfarmatools
                    + "FROM " + tabla_paci_his
                    + " p LEFT OUTER JOIN " + tabla_paciente + " paci "
                    + "on p.numerohc=paci.numerohc "
                    + "LEFT OUTER JOIN " + tabla_poblaciones + " pobl on pobl.nombre=p.muncia and pobl.provincia=p.procia and pobl.cod_pais=p.cod_pais "
                    + filtropacientes
                    + "order by p.apellid1,p.nombre,p.apellid2";

            //borro tablas temporales y me traigo los datos actuales de farmatools
            execute(connections.get(DBPrefix.LOCAL), deleteFarmatools_patient_tmp);
            ResultSet farmatoolsPatients = executeQuery(connections.get(DBPrefix.FARMATOOLS), queryFarmatools_patient);
            insert(connections.get(DBPrefix.LOCAL), farmatoolsPatients, "farmatools_patient_tmp",log);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        }
    }


     /**
     * obtenemos lista de ADT, con los datos
     * nuevos modificados o borrados, cambios en la cama del paciente comparando farmatools_patient y farmatools_patientTmp con los cambios
     * transcurridos en farmatools desde la ultima vez que se ejecuto esta función.
     * @return
     */
    public List<Farmatools_Patient> getFarmatools_Patient() {
        List<Farmatools_Patient> farmatools_patient = new ArrayList<Farmatools_Patient>();
        try {
            String columnas = "p.numerohc,p.apellid1,p.nombre,p.apellid2,p.fechanac,"
                    + "p.sexo,p.direccion,p.cod_postal,"
                    + "p.cod_poblacion,p.provincia,p.cod_pais,p.telefono,p.nifdni,p.cod_centro,"
                    + "p.numtis,p.numeross,p.paciente_ncama,";

            String columnasMinusTmpA31 = "pt.numerohc,pt.apellid1,pt.nombre,pt.apellid2,pt.fechanac,"
                    + "pt.sexo,pt.direccion,pt.cod_postal,"
                    + "pt.cod_poblacion,pt.provincia,pt.cod_pais,pt.telefono,pt.nifdni,pt.cod_centro";

            String columnasMinusA31 = "p.numerohc,p.apellid1,p.nombre,p.apellid2,p.fechanac,"
                    + "p.sexo,p.direccion,p.cod_postal,"
                    + "p.cod_poblacion,p.provincia,p.cod_pais,p.telefono,p.nifdni,p.cod_centro";

            String columnasMinusTmpA47 = "pt.numerohc,pt.nifdni"
                    //+ ",pt.numeross"
                    + ",pt.numtis ";

            String columnasMinusA47 = "p.numerohc,p.nifdni"
                    //+ ",p.numeross"
                    + ",p.numtis";

            String Orderby = " order by p.numerohc";
            //coge solo los nuevos que esten tambien en la tabla paciente de farmatools
            String queryminusnumerohctmp = "(SELECT pt.numerohc,pt.cod_centro FROM farmatools_patient_tmp pt minus SELECT p.numerohc,p.cod_centro from farmatools_patient p)";
            String queryminusnumerohcsolotmp = "(SELECT pt.numerohc FROM farmatools_patient_tmp pt where pt.cod_centro is not null minus SELECT p.numerohc from farmatools_patient p)";
            String queryminusnumerohc = "(SELECT p.numerohc FROM farmatools_patient p minus SELECT pt.numerohc from farmatools_patient_tmp pt)";
            String queryminusbedtmp = "(SELECT ot.numerohc,ot.paciente_ncama,ot.cod_centro FROM farmatools_patient_tmp ot "
                    + "minus SELECT p.numerohc,p.paciente_ncama,p.cod_centro from farmatools_patient p)";
            String queryminusbed = "(SELECT p.numerohc,p.paciente_ncama,p.cod_centro FROM farmatools_patient p "
                    + "minus SELECT t.numerohc,t.paciente_ncama,t.cod_centro from farmatools_patient_tmp t)";
            //modificar un id que no sea numerohc que es clave primaria
            String queryPatientA47modi = " from farmatools_patient pa,farmatools_patient_tmp p "
                    + "where p.numerohc in "
                    + queryminusnumerohcsolotmp
                    + "and (pa.apellid1=p.apellid1 "
                    + "and pa.apellid2=p.apellid2 and pa.nombre=p.nombre and  "
                    + "(pa.nifdni=p.nifdni or p.nifdni is null))";

            //query cambio numerohc
            String queryA47numerohc = "from farmatools_patient pa,"
                    + "farmatools_patient_tmp p where(p.numerohc ,p.paciente_ncama)"
                    + "in (SELECT pt.numerohc,pt.paciente_ncama  FROM farmatools_patient_tmp pt where pt.cod_centro is not null minus SELECT p.numerohc,p.paciente_ncama from farmatools_patient p)"
                    + " and (pa.apellid1=p.apellid1 and pa.apellid2=p.apellid2 and "
                    + "pa.nombre=p.nombre and  (pa.nifdni=p.nifdni or p.nifdni is null) and pa.numerohcActual='S'"
                    + " and pa.numerohc<>p.numerohc"
                    //TODO numero provisional puede tener distinto valor en diferentes numerohc para el mismo paciente
                    //+ " and (pa.numtis=p.numtis or p.numtis is null) "
                    //+ " and (p.numeross=pa.numeross or p.numeross is null )
                    + ")";

            //query para un nuevo paciente
            String query28 = " from farmatools_patient_tmp p where p.numerohc in ("
                    + queryminusnumerohcsolotmp
                    + "minus "
                    + "(select p.numerohc "
                    + queryPatientA47modi + "))";
            //obtenemos datos de un nuevo paciente
            String queryPatientA28 = "select distinct " + columnas + "null as numerohcActual,null as patientswapnumerohc,null as patientswapcama,null as numerohcanterior"
                    + query28
                    + Orderby;
            //query para traspaso de cama
            String querybed = " from farmatools_patient_tmp p,farmatools_patient pa where pa.numerohc=p.numerohc and (p.numerohc,"
                    + "p.paciente_ncama,p.cod_centro) in "
                    + queryminusbedtmp//cama del ingreso
                    + "and p.numerohc not in "
                    + "(select p.numerohc " + query28 + ")";

            // query saca datos para translado
            String queryPatientbed = "select distinct " + columnas
                    + "null as numerohcActual,null as patientswapnumerohc,pa.paciente_ncama as patientswapcama,null as numerohcanterior"
                    + querybed
                    + Orderby;


            //alta de un paciente
            String queryPatientA03 = "select distinct " + columnas
                    + "null as numerohcActual,null as patientswapnumerohc,pa.paciente_ncama as patientswapcama,null as numerohcanterior from farmatools_patient_tmp p,farmatools_patient pa where pa.numerohc=p.numerohc and (p.numerohc"
                    + ") in "
                    + "(select p.numerohc from "
                    + "farmatools_patient p where (p.numerohc,p.paciente_ncama,p.cod_centro) in "
                    + queryminusbed//cama se quedo libre
                    + ") and (p.numerohc) in "
                    + "(select p.numerohc "
                    + "from farmatools_patient_tmp p where p.paciente_ncama is null)"//la cama debe ser null quedando libre
                    + " and (p.numerohc not in "
                    + "(select pa.numerohc "
                    + queryA47numerohc + "))"// cuando modificamos numerohc no hay que dar el alta
                    + Orderby;
            //borrado de un historial clinico
            String queryPatientA29 = "select distinct " + columnas + "null as numerohcActual,null as patientswapnumerohc,null as patientswapcama,null as numerohcanterior"
                    + " from farmatools_patient p where p.numerohc in"
                    + "(select p.numerohc from farmatools_patient_tmp p where (p.numerohc) in"
                    + queryminusnumerohc
                    + "minus select p.numerohc"
                    + queryPatientA47modi + ")"
                    + Orderby;
            //modificaciones de un historial clinico
            String queryPatientA31 = "select distinct "
                    + columnas + "null as numerohcActual,null as patientswapnumerohc,null as patientswapcama,null as numerohcanterior from farmatools_patient_tmp p, "
                    + "(SELECT "
                    + columnasMinusTmpA31
                    + " from farmatools_patient_tmp pt "
                    + "minus select "
                    + columnasMinusA31
                    + " FROM farmatools_patient p) modi where modi.numerohc=p.numerohc"
                    + " and (p.numerohc,p.cod_centro) not IN "
                    + queryminusnumerohctmp
                    + Orderby;
            //modificaciones de id de pacientes
            String queryPatientA47 = "select distinct "
                    + columnas + "null as numerohcActual,null as patientswapnumerohc,null as patientswapcama,null as numerohcanterior from farmatools_patient_tmp p, "
                    + "(SELECT "
                    + columnasMinusTmpA47
                    + " from farmatools_patient_tmp pt "
                    + "minus select "
                    + columnasMinusA47
                    + " FROM farmatools_patient p) a47 where a47.numerohc=p.numerohc "
                    + " and (p.numerohc,p.cod_Centro) not IN "
                    + queryminusnumerohctmp //no sea una nueva historia clinica
                    + Orderby;

            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientA47, farmatools_patient, Adt.A47);
            //adt03 alta liberamos camas que lo mismo otro paciente ocupa
            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientA03, farmatools_patient, Adt.A03);
            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientA29, farmatools_patient, Adt.A29);
            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientA28, farmatools_patient, Adt.A28);
            // cambios en camas
            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientbed, farmatools_patient, Adt.A69);

            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientA31, farmatools_patient, Adt.A31);


        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
        return farmatools_patient;
    }

 /**
     * obtenemos lista de ADT47, con los datos
     * nuevos modificados o borrados, cambios en la cama del paciente comparando farmatools_patient y farmatools_patientTmp con los cambios
     * transcurridos en farmatools desde la ultima vez que se ejecuto esta función.
     * @return
     */
    public List<Farmatools_Patient> getFarmatools_Patient47() {
        updateDaemonData();
        List<Farmatools_Patient> farmatools_patient = new ArrayList<Farmatools_Patient>();
        try {
            String columnas = "p.numerohc,p.apellid1,p.nombre,p.apellid2,p.fechanac,"
                    + "p.sexo,p.direccion,p.cod_postal,"
                    + "p.cod_poblacion,p.provincia,p.cod_pais,p.telefono,p.nifdni,p.cod_centro,"
                    + "p.numtis,p.numeross,p.paciente_ncama,";

            //query cambio numerohc
            String queryA47numerohc = "from farmatools_patient pa,"
                    + "farmatools_patient_tmp p where(p.numerohc ,p.paciente_ncama)"
                    + "in (SELECT pt.numerohc,pt.paciente_ncama  FROM farmatools_patient_tmp pt where pt.cod_centro is not null minus SELECT p.numerohc,p.paciente_ncama from farmatools_patient p)"
                    + " and (pa.apellid1=p.apellid1 and pa.apellid2=p.apellid2 and "
                    + "pa.nombre=p.nombre and  (pa.nifdni=p.nifdni or p.nifdni is null) and pa.numerohcActual='S'"
                    + " and pa.numerohc<>p.numerohc"
                    //TODO numero provisional puede tener distinto valor en diferentes numerohc para el mismo paciente
                    //+ " and (pa.numtis=p.numtis or p.numtis is null) "
                    //+ " and (p.numeross=pa.numeross or p.numeross is null )
                    + ")";
            //modificar el numerohc de un paciente que ya esta
            String queryPatientA47numerohc = "select distinct " + columnas + "pa.numerohcActual,null as patientswapnumerohc,null as patientswapcama,pa.numerohc as numerohcanterior  "
                    + queryA47numerohc;

            //select de los distintos ADT
            selectPatient(connections.get(DBPrefix.LOCAL), queryPatientA47numerohc, farmatools_patient, Adt.A47);
            deleterepenumerohcA47(farmatools_patient);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }
        return farmatools_patient;
    }
}
