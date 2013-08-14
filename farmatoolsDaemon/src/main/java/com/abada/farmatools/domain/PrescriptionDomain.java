/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.farmatools.domain;

import com.abada.farmatools.object.Farmatools_Order;
import com.abada.farmatools.object.Farmatools_OrderTiming;
import com.abada.farmatools.enums.OrderControl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static com.abada.farmatools.domain.SQLUtils.*;
import com.abada.farmatools.enums.DBPrefix;
import java.util.EnumMap;

/**
 *
 * @author aroldan
 */
public class PrescriptionDomain implements IDomain{
    
    private static Log log = LogFactory.getLog(PrescriptionDomain.class);
    protected Properties property;
    private EnumMap<DBPrefix, Connection> connections;

    public PrescriptionDomain(EnumMap<DBPrefix, Connection> connections, Properties property) {
        this.property = property;
        this.connections = connections;
    }

    /**
     * select que obtiene las prescripciones de los insertados modificados y borrados
     * @param codigoSQL
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    protected void selectOrder(Connection connection, String codigoSQL, List<Farmatools_Order> lfarmatools_Order, OrderControl control) throws SQLException, ClassNotFoundException, IOException {
        String orderidanterior = "";
        List<Farmatools_OrderTiming> lfarmatools_OrderTiming = new ArrayList();
        Farmatools_OrderTiming farmatools_OrderTiming = new Farmatools_OrderTiming();
        ResultSet result = null;
        Farmatools_Order farmatools_Order = new Farmatools_Order();

        Statement sentencia = connection.createStatement();
        result = sentencia.executeQuery(codigoSQL);

        while (result.next()) {
            //se crea otra order si el orderid es distinto no tiene más ordertiming de esa order
            //Fixed Bug in JdbcOdbc : ODBC Oracle driver can't access a columns twice in the same row in the ResultSet
            String orderID = result.getString("ORDERID");
            Timestamp prepautHora = result.getTimestamp("PRE_PAUT_HORA");
            if (!orderID.equals(orderidanterior)) {
                if (!lfarmatools_OrderTiming.isEmpty()) {
                    farmatools_Order.setOrderTimings(lfarmatools_OrderTiming);
                }
                if (farmatools_Order.getOrderid() != null) {//añadimos la order a la lista
                    lfarmatools_Order.add(farmatools_Order);
                }
                farmatools_Order = new Farmatools_Order();
                farmatools_Order.setMessageCode("OMP");
                farmatools_Order.setTriggerEvent("O09");
                farmatools_Order.setControl(control.toString());
                farmatools_Order.setOrderid(orderID);
                farmatools_Order.setCod_centro(result.getInt("COD_CENTRO"));
                if (result.wasNull()) {
                    farmatools_Order.setCod_centro(null);
                }
                farmatools_Order.setNumerohc(result.getInt("NUMEROHC"));
                if (result.wasNull()) {
                    farmatools_Order.setNumerohc(null);
                }
                farmatools_Order.setNproceso(result.getInt("NPROCESO"));
                if (result.wasNull()) {
                    farmatools_Order.setNproceso(null);
                }
                farmatools_Order.setLinea(result.getInt("LINEA"));
                if (result.wasNull()) {
                    farmatools_Order.setLinea(null);
                }
                farmatools_Order.setLogin_usu(result.getString("LOGIN_USU"));
                farmatools_Order.setFecha_pres(result.getTimestamp("FECHA_PRES"));
                farmatools_Order.setCod_medico(result.getInt("COD_MEDICO"));
                if (result.wasNull()) {
                    farmatools_Order.setCod_medico(null);
                }
                farmatools_Order.setFecha_ini(result.getTimestamp("FECHA_INI"));
                farmatools_Order.setHora_duracion(result.getInt("LT_PAUTAS_HORAS_DURACION"));
                if (result.wasNull()) {
                    farmatools_Order.setHora_duracion(null);
                }
                farmatools_Order.setFecha_fin(result.getTimestamp("FECHA_FIN"));
                if (result.wasNull()) {
                    farmatools_Order.setFecha_fin(null);
                }
                farmatools_Order.setGive_amount(result.getDouble("DOSIS_PA"));
                if (result.wasNull()) {
                    farmatools_Order.setGive_amount(null);
                }
                farmatools_Order.setInstruccion(result.getString("LT_PAUTAS_DESCRIPCION"));

                result.getString("COD_PAUTA");
                //If cod_pauta = null => repetition pattern = repetir AND if necesary = PRECISA
                if(result.wasNull()){
                    farmatools_Order.setRepetition_pattern(result.getString("REPETIR"));
                    farmatools_Order.setIf_necesary(result.getString("PRECISA"));
                }
                //If cod_pauta != null => repetition pattern = repetition_pattern AND if necesary = if_necesary
                else{
                    farmatools_Order.setRepetition_pattern(result.getString("REPETITION_PATTERN"));
                    farmatools_Order.setIf_necesary(result.getString("IF_NECESARY"));
                }

                farmatools_Order.setId_measure_unit(result.getInt("ID_MEASURE_UNIT"));
                if (result.wasNull()) {
                    farmatools_Order.setId_measure_unit(null);
                }
                farmatools_Order.setMeasure_unit_en_mambrino(result.getString("measure_unit_en_mambrino"));
                farmatools_Order.setAlergia_s_n(result.getString("ALERGIA_S_N"));
                farmatools_Order.setModo_prescripcion(result.getString("modo_prescripcion"));
                farmatools_Order.setCod_principioActivo(result.getInt("cod_practivo"));
                if (result.wasNull()) {
                    farmatools_Order.setCod_principioActivo(null);
                }
                farmatools_Order.setCod_via(result.getInt("COD_VIA"));
                if (result.wasNull()) {
                    farmatools_Order.setCod_via(null);
                }
                farmatools_Order.setCod_articulo(result.getString("PRESCRIP_DISP_ARTICULO"));
                farmatools_Order.setObs_dispensa(result.getString("OBS_DISPENSA"));
                farmatools_Order.setObs_enferme(result.getString("OBS_ENFERME"));
                farmatools_Order.setObs_medico(result.getString("OBS_MEDICO"));
                farmatools_Order.setDescripcion_secuencia(result.getString("L1_SECUENC_DESCRIPCION"));
                farmatools_Order.setFh_ult_mod(result.getTimestamp("FH_ULT_MOD"));
                if (prepautHora != null) {
                    lfarmatools_OrderTiming = new ArrayList();
                    orderidanterior = result.getString("ORDERID");
                }
            }
            if (prepautHora != null) {
                farmatools_OrderTiming = new Farmatools_OrderTiming();
                farmatools_OrderTiming.setOrderid(orderID);
                farmatools_OrderTiming.setPre_paut_linea_disp(result.getInt("PRE_PAUT_LINEA_DISP"));
                farmatools_OrderTiming.setPre_paut_hora(result.getTimestamp("PRE_PAUT_HORA"));
                lfarmatools_OrderTiming.add(farmatools_OrderTiming);
            }
        }
        if (farmatools_Order.getOrderid() != null) {//añadimos el ultimo
            if (!lfarmatools_OrderTiming.isEmpty()) {
                farmatools_Order.setOrderTimings(lfarmatools_OrderTiming);
            }
            lfarmatools_Order.add(farmatools_Order);
        }
        sentencia.close();
    }

    /**
     * insertamos una order en farmatools_order
     * @param orderid
     * @param connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void insertorder(String orderid) {

        String insertqueryFarmatoolsOrder = "insert into farmatools_order (ORDERID,"
                + "COD_CENTRO,NUMEROHC,NPROCESO,LINEA,LOGIN_USU,FECHA_PRES,COD_MEDICO,FECHA_INI,"
                + "LT_PAUTAS_HORAS_DURACION,FECHA_FIN,DOSIS_PA,PRECISA,LT_PAUTAS_DESCRIPCION,COD_PAUTA,UNIDAD_MED,ALERGIA_S_N,modo_prescripcion,"
                + "COD_VIA,PRESCRIP_DISP_ARTICULO,OBS_DISPENSA,OBS_ENFERME,OBS_MEDICO,FH_ULT_MOD,cod_practivo,estado,L1_SECUENC_DESCRIPCION,"
                + "REPETIR)"
                + "select * from farmatools_order_tmp o where o.orderid='" + orderid + "'";
        String insertqueryFarmatoolsOrderTiming = "insert into farmatools_orderTiming (ORDERID,"
                + "PRE_PAUT_LINEA_DISP,PRE_PAUT_HORA)"
                + "select * from farmatools_orderTiming_tmp o where o.orderid='" + orderid + "'";
        try {
            execute(connections.get(DBPrefix.LOCAL), insertqueryFarmatoolsOrder, insertqueryFarmatoolsOrderTiming);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }


    }

    /**
     * modificamos los cambios en ese order
     * @param orderid
     * @param connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void updateorder(String orderid) {
        deleteorder(orderid);
        insertorder(orderid);
    }

    /**
     * Borramos esa order
     * @param orderid
     * @param connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void deleteorder(String orderid) {
        String deleteordertiming = "delete from farmatools_ordertiming f where f.orderid='" + orderid + "'";
        String deleteorder = "delete from farmatools_order f where f.orderid='" + orderid + "'";
        try {
            execute(connections.get(DBPrefix.LOCAL), deleteordertiming, deleteorder);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);

        }

    }

    private void updateDaemonData() {
        try {
            String tabla_prescrip = property.getProperty("farmatools.tabla_prescrip");
            String tabla_prescrip_disp = property.getProperty("farmatools.tabla_prescrip_disp");
            String tabla_lt_pautas = property.getProperty("farmatools.tabla_lt_pautas");
            String tabla_pre_paut = property.getProperty("farmatools.tabla_pre_paut");
            String tabla_articulos = property.getProperty("farmatools.tabla_articulos");
            String tabla_l1_secuenc = property.getProperty("farmatools.tabla_secuencias");
            String tabla_paciente = property.getProperty("farmatools.tabla_paciente");
            String tabla_car_cama = property.getProperty("farmatools.tabla_car_cama");
            String pacientes = property.getProperty("farmatools.pacientes");
            String filtropacientes = "";
            if (!pacientes.equals("")) {
                filtropacientes = " and p.numerohc in (" + pacientes + ") ";
            }
            String columnsSelect = "p.cod_centro,p.numerohc,p.nproceso,"
                    + "p.linea,p.login_usu,p.fecha_pres,p.cod_medico,p.fecha_ini,"
                    + "lt.horas_duracion as LT_PAUTAS_HORAS_DURACION,p.fecha_fin,pd.dosis as dosis_pa,p.precisa,"
                    + "lt.descripcion as LT_PAUTAS_DESCRIPCION,p.cod_pauta,ar.unidad_med,"
                    + "REPLACE(p.alergia_s_n,'S','Y') as alergia_s_n,p.modo_pres as modo_prescripcion,p.cod_via,pd.articulo as PRESCRIP_DISP_ARTICULO,"
                    + "p.Obs_dispensa,p.Obs_enferme,p.Obs_medico,p.fh_ult_mod,p.cod_practivo,p.estado,l1.descripcion as L1_SECUENC_DESCRIPCION,"
                    + "'Q'||p.repetir||'D' as repetir ";

            String deleteFarmatools_order_tmp = "delete from farmatools_order_tmp";
            String deleteFarmatools_orderTiming_tmp = "delete from farmatools_ordertiming_tmp";

            String queryFarmatools_mezclasConDiferentesArticulos =
                    "SELECT DISTINCT p.numerohc,p.nproceso,p.linea_pres as linea "
                    + "from prescrip_disp p,prescrip_disp p2 where p.num_mezcla != 0 "
                    + "and p.num_mezcla=p2.num_mezcla and p.numerohc = p2.numerohc and "
                    + "p.nproceso=p2.nproceso and p.linea_pres=p2.linea_pres and "
                    + "p.articulo != p2.articulo";

            String queryFarmatools_order = "SELECT DISTINCT p.numerohc||'_'||p.nproceso||'_'||p.linea as orderid ,"
                    + columnsSelect
                    + "FROM " + tabla_prescrip + " p LEFT OUTER JOIN " + tabla_prescrip_disp + " pd "
                    + "on p.cod_centro=pd.cod_centro and p.numerohc=pd.numerohc and "
                    + "p.nproceso=pd.nproceso and p.linea=pd.linea_pres "
                    + "LEFT OUTER JOIN  " + tabla_articulos + " ar "
                    + "on ar.codigo=pd.articulo  "
                    + "LEFT OUTER JOIN  " + tabla_lt_pautas + " lt "
                    + "on lt.cod_pauta=p.cod_pauta  "
                    + "LEFT OUTER JOIN "+ tabla_paciente + " pa "
                    + "on p.numerohc=pa.numerohc  "
                    + "LEFT OUTER JOIN "+ tabla_car_cama + " cc "
                    + "on cc.ncama=pa.ncama  "
                    + "LEFT OUTER JOIN "+ tabla_l1_secuenc + " l1 "
                    + "on l1.cod_variante=p.cod_variante and l1.cod_unidad=cc.unidad  "
                    + "where (p.numerohc,p.nproceso,p.linea) NOT IN ("
                    + queryFarmatools_mezclasConDiferentesArticulos + ")"
                    + filtropacientes
                    + "order by p.numerohc,p.nproceso,p.linea";

            String queryFarmatools_orderTiming = "SELECT paut.numerohc||'_'||paut.nproceso||'_'||paut.linea as orderid ,"
                    + "paut.linea_disp as PRE_PAUT_LINEA_DISP,paut.hora as PRE_PAUT_HORA FROM " + tabla_pre_paut + " paut, "
                    + tabla_prescrip + " p "
                    + "where p.numerohc=paut.numerohc and p.nproceso=paut.nproceso and p.linea=paut.linea "
                    + filtropacientes
                    + "and (p.numerohc,p.nproceso,p.linea) NOT IN ("
                    + queryFarmatools_mezclasConDiferentesArticulos + ")"
                    + "order by paut.numerohc,paut.nproceso,paut.linea,paut.linea_disp";


            //borro tablas temporales y me traigo los datos actuales de farmatools
            execute(connections.get(DBPrefix.LOCAL), deleteFarmatools_orderTiming_tmp, deleteFarmatools_order_tmp);
            ResultSet farmatoolsOrders = executeQuery(connections.get(DBPrefix.FARMATOOLS), queryFarmatools_order);
            insert(connections.get(DBPrefix.LOCAL), farmatoolsOrders, "farmatools_order_tmp",log);
            ResultSet farmatoolsOrderTimings = executeQuery(connections.get(DBPrefix.FARMATOOLS), queryFarmatools_orderTiming);
            insert(connections.get(DBPrefix.LOCAL), farmatoolsOrderTimings, "farmatools_ordertiming_tmp",log);

        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        }
    }

    /**
     * obtenemos lista de prescripcion, con los datos
     * nuevos modificados o borrados comparando farmatools_order y farmatools_orderTmp con los cambios
     * transcurridos en farmatools desde la ultima vez que se ejecuto esta función.
     * @return
     */
    public List<Farmatools_Order> getFarmatools_Order() {
        updateDaemonData();
        List<Farmatools_Order> farmatools_Order = new ArrayList<Farmatools_Order>();
        try {

            String columnas = "o.ORDERID,otime.pre_paut_linea_disp,o.COD_CENTRO,o.NUMEROHC,o.NPROCESO,o.LINEA,"
                    + "o.LOGIN_USU,o.FECHA_PRES,o.COD_MEDICO,o.FECHA_INI,o.LT_PAUTAS_HORAS_DURACION,"
                    + "o.FECHA_FIN,o.DOSIS_PA,REPLACE(o.PRECISA,'S','Y') as PRECISA,repe.if_necesary,o.LT_PAUTAS_DESCRIPCION,repe.REPETITION_PATTERN,"
                    + "o.ALERGIA_S_N,o.modo_prescripcion,o.PRESCRIP_DISP_ARTICULO,o.estado,o.L1_SECUENC_DESCRIPCION,"
                    + "o.OBS_DISPENSA,o.OBS_ENFERME,o.OBS_MEDICO,o.FH_ULT_MOD,o.cod_practivo,otime.pre_paut_hora,"
                    + "mu.id_measure_unit,mu.en_mambrino as measure_unit_en_mambrino,via.cod_via,o.cod_pauta,o.repetir";

            String columnasMinusTmp = "q.ORDERID,q.COD_CENTRO,q.NUMEROHC,q.NPROCESO,q.LINEA,q.LOGIN_USU,q.FECHA_PRES,"
                    + "q.COD_MEDICO,q.FECHA_INI,q.LT_PAUTAS_HORAS_DURACION,q.FECHA_FIN,q.DOSIS_PA,"
                    + "q.precisa,q.LT_PAUTAS_DESCRIPCION,q.cod_pauta,q.UNIDAD_MED,q.ALERGIA_S_N,"
                    + "q.modo_prescripcion,q.COD_VIA,q.PRESCRIP_DISP_ARTICULO,q.OBS_DISPENSA,q.estado,q.L1_SECUENC_DESCRIPCION,"
                    + "q.OBS_ENFERME,q.OBS_MEDICO,q.FH_ULT_MOD,q.cod_practivo,s.pre_paut_linea_disp,s.pre_paut_hora,q.repetir";

            String columnasMinus = "e.ORDERID,e.COD_CENTRO,e.NUMEROHC,e.NPROCESO,e.LINEA,e.LOGIN_USU,"
                    + "e.FECHA_PRES,e.COD_MEDICO,e.FECHA_INI,e.LT_PAUTAS_HORAS_DURACION,e.FECHA_FIN,"
                    + "e.DOSIS_PA,e.precisa,e.LT_PAUTAS_DESCRIPCION,e.cod_pauta,e.UNIDAD_MED,e.ALERGIA_S_N,"
                    + "e.modo_prescripcion,e.COD_VIA,e.PRESCRIP_DISP_ARTICULO,e.OBS_DISPENSA,e.estado,e.L1_SECUENC_DESCRIPCION,"
                    + "e.OBS_ENFERME,e.OBS_MEDICO,e.FH_ULT_MOD,e.cod_practivo,r.pre_paut_linea_disp,r.pre_paut_hora,e.repetir";

            String Orderby = " order by o.orderid, otime.pre_paut_linea_disp";

            String queryOrderDelete = "select distinct " + columnas + " from farmatools_order o LEFT OUTER JOIN "
                    + "FARMATOOLS_ORDERTIMING otime on o.orderid=otime.orderid  LEFT OUTER JOIN FARMATOOLS_0335 repe"
                    + " on o.cod_pauta=repe.cod_pauta LEFT OUTER JOIN FARMATOOLS_MEASURE_UNIT mu"
                    + " on o.unidad_med=mu.codigo LEFT OUTER JOIN FARMATOOLS_0162 via on o.cod_via=via.codigo"
                    + " where o.orderid in  "
                    + "(SELECT ord.orderid FROM farmatools_order ord minus SELECT otmp.orderid from farmatools_order_tmp otmp)"
                    + Orderby;

            String queryOrderNew = "select distinct " + columnas + " from farmatools_order_tmp o LEFT OUTER JOIN "
                    + "farmatools_ordertiming_tmp otime on o.orderid=otime.orderid LEFT OUTER JOIN FARMATOOLS_0335 repe"
                    + " on o.cod_pauta=repe.cod_pauta LEFT OUTER JOIN FARMATOOLS_MEASURE_UNIT mu"
                    + " on o.unidad_med=mu.codigo LEFT OUTER JOIN FARMATOOLS_0162 via on o.cod_via=via.codigo"
                    + " where o.orderid in "
                    + "(SELECT ot.orderid FROM farmatools_order_tmp ot where ot.estado != 'B' minus"
                    + " SELECT orde.orderid from farmatools_order orde)"
                    + Orderby;

            String queryOrderNewAndHold = "select distinct " + columnas + " from farmatools_order_tmp o LEFT OUTER JOIN "
                    + "farmatools_ordertiming_tmp otime on o.orderid=otime.orderid LEFT OUTER JOIN FARMATOOLS_0335 repe"
                    + " on o.cod_pauta=repe.cod_pauta LEFT OUTER JOIN FARMATOOLS_MEASURE_UNIT mu"
                    + " on o.unidad_med=mu.codigo LEFT OUTER JOIN FARMATOOLS_0162 via on o.cod_via=via.codigo"
                    + " where o.orderid in "
                    + "(SELECT ot.orderid FROM farmatools_order_tmp ot where ot.estado = 'B' minus"
                    + " SELECT orde.orderid from farmatools_order orde)"
                    + Orderby;

            String queryOrderHold = "select distinct " + columnas + " from farmatools_order_tmp o LEFT OUTER JOIN "
                    + "farmatools_ordertiming_tmp otime on o.orderid=otime.orderid LEFT OUTER JOIN FARMATOOLS_0335 repe"
                    + " on o.cod_pauta=repe.cod_pauta LEFT OUTER JOIN FARMATOOLS_MEASURE_UNIT mu"
                    + " on o.unidad_med=mu.codigo LEFT OUTER JOIN FARMATOOLS_0162 via on o.cod_via=via.codigo"
                    + " where o.orderid in "
                    + "(SELECT orde.orderid from farmatools_order orde,farmatools_order_tmp ot"
                    + " where orde.orderid=ot.orderid and orde.estado!='B' and ot.estado='B')"
                    + Orderby;

            String queryOrderRelease = "select distinct " + columnas + " from farmatools_order_tmp o LEFT OUTER JOIN "
                    + "farmatools_ordertiming_tmp otime on o.orderid=otime.orderid LEFT OUTER JOIN FARMATOOLS_0335 repe"
                    + " on o.cod_pauta=repe.cod_pauta LEFT OUTER JOIN FARMATOOLS_MEASURE_UNIT mu"
                    + " on o.unidad_med=mu.codigo LEFT OUTER JOIN FARMATOOLS_0162 via on o.cod_via=via.codigo"
                    + " where o.orderid in "
                    + "(SELECT orde.orderid from farmatools_order orde,farmatools_order_tmp ot"
                    + " where orde.orderid=ot.orderid and orde.estado='B' and ot.estado!='B')"
                    + Orderby;

            String queryOrderUpdate = "select distinct "
                    + columnas
                    + " from (SELECT "
                    + columnasMinusTmp
                    + " from farmatools_order_tmp q LEFT OUTER JOIN farmatools_ordertiming_tmp s on q.orderid=s.orderid "
                    + "minus select "
                    + columnasMinus
                    + " FROM farmatools_order e LEFT OUTER JOIN farmatools_ordertiming r on e.orderid=r.orderid)o"
                    + " LEFT OUTER JOIN FARMATOOLS_ORDERTIMING_TMP otime on o.orderid=otime.orderid LEFT OUTER JOIN FARMATOOLS_0335 repe"
                    + " on o.cod_pauta=repe.cod_pauta LEFT OUTER JOIN FARMATOOLS_MEASURE_UNIT mu"
                    + " on o.unidad_med=mu.codigo LEFT OUTER JOIN FARMATOOLS_0162 via on o.cod_via=via.codigo"
                    + " where o.orderid not IN "
                    + "(SELECT ot.orderid FROM farmatools_order_tmp ot minus SELECT o.orderid from farmatools_order o)"
                    + " and o.estado<>'B'"
                    + Orderby;
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderDelete, farmatools_Order, OrderControl.CA);
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderNew, farmatools_Order, OrderControl.NW);
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderNewAndHold, farmatools_Order, OrderControl.NW);
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderNewAndHold, farmatools_Order, OrderControl.HD);
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderHold, farmatools_Order, OrderControl.HD);
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderRelease, farmatools_Order, OrderControl.RL);
            selectOrder(connections.get(DBPrefix.LOCAL), queryOrderUpdate, farmatools_Order, OrderControl.XO);


        } catch (IOException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        } catch (SQLException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        } catch (ClassNotFoundException ex) {
            log.error(CreateConection.class.getName() + "ERROR", ex);
        }
        return farmatools_Order;
    }

}
