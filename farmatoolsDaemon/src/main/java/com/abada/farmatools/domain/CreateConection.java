/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.farmatools.domain;

import com.abada.farmatools.object.Farmatools_Order;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.farmatools.enums.DBPrefix;
import com.abada.farmatools.enums.Domain;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *conexion bd, sinonimos,dblink, acceso property
 *
 * @author david
 */
public class CreateConection {

    private static Log log = LogFactory.getLog(CreateConection.class);
    private Properties property;
    private EnumMap<DBPrefix, Connection> connections;
    private EnumMap<Domain,IDomain> domainObjects;

    /**
     * Creates the DB connection, property, dblinks and synonyms (if needed)
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public CreateConection() throws IOException, ClassNotFoundException, SQLException {
        connections = new EnumMap<DBPrefix, Connection>(DBPrefix.class);
        domainObjects = new EnumMap<Domain, IDomain>(Domain.class);

        //Load Properties File
        property = new Properties();
        InputStream fileproperty;
        try {
            fileproperty = new FileInputStream("messages.properties");
        } catch (FileNotFoundException ex) {
            log.error("\n" + ex.getMessage() + " \n looking /com/abada/farmatools/properties/messages.properties...");
            fileproperty = this.getClass().getResourceAsStream("/com/abada/farmatools/properties/messages.properties");
        }
        property.load(fileproperty);

        //Create local database Connection
        createDBConnection(DBPrefix.LOCAL);

        //formatDate fechas
        String formatdate = "alter session set NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS.FF'";
        executeStatement(connections.get(DBPrefix.LOCAL), formatdate, "Error Fecha NLS_TIMESTAMP_FORMAT not alter");
        
        log.warn("Warning. DBLink is not enabled. Falling back to normal (slow) mode");

        //Create remote database Connection
        createDBConnection(DBPrefix.FARMATOOLS);
        //formatDate fechas
        formatdate = "alter session set NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS.FF'";
        executeStatement(connections.get(DBPrefix.FARMATOOLS), formatdate, "Error Fecha NLS_TIMESTAMP_FORMAT not alter");

        domainObjects.put(Domain.PATIENT, new PatientDomain(connections, property));
        domainObjects.put(Domain.PRESCRIPTION, new PrescriptionDomain(connections, property));
    }

    /**
     * Creates a DB Connection and add it to the Connections HashMap
     * @param dbPrefix
     * @throws SQLException
     */
    private void createDBConnection(DBPrefix dbPrefix) throws SQLException, ClassNotFoundException {
        String sDBPrefix = dbPrefix.toString().toLowerCase();
        String user = property.getProperty(sDBPrefix + ".user");
        String pass = property.getProperty(sDBPrefix + ".pass");
        String host = property.getProperty(sDBPrefix + ".host");
        String port = property.getProperty(sDBPrefix + ".port");
        String serviceName = property.getProperty(sDBPrefix + ".service_name");

        //Load OracleDBDriver
        String url = null;
        if (property.getProperty(sDBPrefix + ".driver").equalsIgnoreCase("JDBC")) {
            log.info("Initilizing Oracle JDBC Driver");

            //Load JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + serviceName;
            connections.put(dbPrefix, DriverManager.getConnection(url, user, pass));
            log.info("Oracle JDBC Driver connection stablished");

        } else {
            //Load ODBC driver
            log.info("Initilizing Oracle in XEClient Driver");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            //First, tryes Oracle in XEClient drive
            //url = "jdbc:odbc:Driver={Oracle in XEClient};dbq=" + host + ":" + port + "/" + serviceName;
            url = "jdbc:odbc:Driver={Oracle en OraClient10g_home1};dbq=" + host + ":" + port + "/" + serviceName;
            //try{
            connections.put(dbPrefix, DriverManager.getConnection(url, user, pass));
            log.info("Oracle in XEClient Driver connection stablished");
            /*}catch(SQLException ex){
            log.warn("Oracle in XEClient Driver not found");
            log.info("Trying Oracle en OraClient10g_home1 Driver connection");
            url = "jdbc:odbc:Driver={Oracle en OraClient10g_home1};dbq=" + host + ":" + port + "/" + serviceName;
            try{
            connections.put(dbPrefix, DriverManager.getConnection(url, user, pass));
            log.info("Oracle en OraClient10g_home1 Driver connection stablished");
            }
            catch(SQLException ex2){
            log.warn("Oracle en OraClient10g_home1 Driver not found");
            log.info("Trying Oracle in OraClient10g_home1 Driver connection");
            url = "jdbc:odbc:Driver={Oracle in OraClient10g_home1};dbq=" + host + ":" + port + "/" + serviceName;
            connections.put(dbPrefix, DriverManager.getConnection(url, user, pass));
            log.info("Oracle in OraClient10g_home1 Driver connection stablished");
            }
            }*/
        }

        /*Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        url = "jdbc:odbc:Driver=(Oracle in XEClient);dbq=111.21.31.99:1521/XE;Uid=myUsername;Pwd=myPassword";
        url = "jdbc:odbc:Driver={Microsoft ODBC Driver for Oracle};ConnectString=OracleServer.world;Uid=myUsername;Pwd=myPassword";
        url = "jdbc:odbc:Driver={Microsoft ODBC for Oracle};Server=myServerAddress;Uid=myUsername;Pwd=myPassword";
        url = "jdbc:odbc:Driver={Microsoft ODBC for Oracle};Server=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=199.199.199.199)(PORT=1523))(CONNECT_DATA=(SID=dbName)));Uid=myUsername;Pwd=myPassword";
        url = "jdbc:odbc:Driver={Oracle in OraHome92};Dbq=myTNSServiceName;Uid=myUsername;Pwd=myPassword";*/
    }

    /**
     * Close DB Connection
     * @throws SQLException
     */
    public void closeBDConnections() throws SQLException {
        for (Connection c : connections.values()) {
            try {
                c.close();
            } catch (Exception ex) {
                log.error(CreateConection.class.getName() + "ERROR", ex);
            }
        }
    }

    /**
     * Returns property
     * @return
     */
    public Properties getProperty() {
        return property;
    }

    /**
     * Return connections
     * @return
     */
    public Connection getConnections() {
        return (Connection) connections;
    }

    /**
     * Execute query
     * @param c
     * @param query
     * @param errorMessage
     * @return
     */
    private boolean executeStatement(Connection c, String query, String errorMessage) {
        Statement s = null;
        boolean result = false;
        try {
            s = c.createStatement();
            s.execute(query);
            result = true;
        } catch (SQLException ex) {
            log.error(errorMessage + "\n" + ex.getMessage());
        } finally {
            try {
                s.close();
            } catch (Exception ex) {
            }
            return result;
        }
    }



    /**
     * Return prescriptions list
     * @return
     */
    public List<Farmatools_Order> getFarmatools_Order() {
        return ((PrescriptionDomain)domainObjects.get(Domain.PRESCRIPTION)).getFarmatools_Order();
    }

    /**
     * Return patient's list
     * @return
     */
    public List<Farmatools_Patient> getFarmatools_Patient() {
        return ((PatientDomain)domainObjects.get(Domain.PATIENT)).getFarmatools_Patient();
    }

    /**
     * Deletes a farmatools_order
     * @param orderid
     */
    public void deleteorder(String orderid) {
        ((PrescriptionDomain)domainObjects.get(Domain.PRESCRIPTION)).deleteorder(orderid);
    }

    /**
     * Inserts a  farmatools_order
     * @param orderid
     */
    public void inserteorder(String orderid) {
        ((PrescriptionDomain)domainObjects.get(Domain.PRESCRIPTION)).insertorder(orderid);
    }

    /**
     * Updates a farmatools_order
     * @param orderid
     */
    public void updateorder(String orderid) {
        ((PrescriptionDomain)domainObjects.get(Domain.PRESCRIPTION)).updateorder(orderid);
    }

    /**
     * Updates a patient's bed
     * @param bed
     * @param numerohc
     * @param cod_centro
     */
    public void updatePatientbed(String bed, int numerohc, Integer codcentro) {
        ((PatientDomain)domainObjects.get(Domain.PATIENT)).updatePatientbed(bed, numerohc, codcentro);
    }

    /**
     * Inserts a patient
     * @param farmatoolpatient
     */
    public void insertPatient(Farmatools_Patient farmatoolpatient) {
        ((PatientDomain)domainObjects.get(Domain.PATIENT)).insertPatient(farmatoolpatient);
    }

    /**
     * Updates address and birthday_date from a patient
     * @param f
     */
    public void updatePatientA31(Farmatools_Patient f) {
        ((PatientDomain)domainObjects.get(Domain.PATIENT)).updatePatientA31(f);
    }

    /**
     * Updates, patient id, name and surnames
     * @param f
     */
    public void updatePatientA47(Farmatools_Patient f) {
        ((PatientDomain)domainObjects.get(Domain.PATIENT)).updatePatientA47(f);
    }

    /**
     * Deletes a patient
     * @param numerohc
     * @param cod_centro
     */
    public void deletePatient(int numerohc) {
        ((PatientDomain)domainObjects.get(Domain.PATIENT)).deletePatient(numerohc);
    }

    /**
     * Deletes a patient
     * @param numerohc
     * @param cod_centro
     */
    public void deleteFarmatoolsPatient() {
        ((PatientDomain)domainObjects.get(Domain.PATIENT)).deleteFarmatoolsPatient();
    }

    /**
     * nos dice si la cama se encuentra en el maestro
     * @param bed
     * @return
     */
    public boolean findrecurso(String bed) {
        return ((PatientDomain)domainObjects.get(Domain.PATIENT)).findrecurso(bed);
    }

    /**
     * modificamos clave primaria de los pacientes numerohc
     * @return
     */
    public List<Farmatools_Patient> getFarmatools_Patient47() {
        return ((PatientDomain)domainObjects.get(Domain.PATIENT)).getFarmatools_Patient47();
    }
    
}
