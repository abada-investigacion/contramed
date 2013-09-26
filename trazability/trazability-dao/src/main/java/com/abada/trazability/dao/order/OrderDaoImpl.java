/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.order;

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

import ca.uhn.hl7v2.HL7Exception;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.dao.catalogoMedicamentos.CatalogoMedicamentosDao;
import com.abada.trazability.dao.measureUnit.MeasureUnitDao;
import com.abada.trazability.dao.patient.PatientDao;
import com.abada.trazability.dao.principioActivo.PrincipioActivoDao;
import com.abada.trazability.dao.table0119.Table0119Dao;
import com.abada.trazability.dao.table0162.Table0162Dao;
import com.abada.trazability.entity.CatalogoMedicamentos;
import com.abada.trazability.entity.MeasureUnit;
import com.abada.trazability.entity.Order1;
import com.abada.trazability.entity.OrderMedication;
import com.abada.trazability.entity.OrderMedicationInstruction;
import com.abada.trazability.entity.OrderMedicationObservation;
import com.abada.trazability.entity.OrderTiming;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.PrincipioActivo;
import com.abada.trazability.entity.Table0119;
import com.abada.trazability.entity.Table0162;
import com.abada.trazability.entity.TelephoneDoctor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 *
 * Dao de la entidad Order, trabajamos con datos de tramientos
 */
@Repository("orderDao")
public class OrderDaoImpl extends JpaDaoUtils implements OrderDao {

    private PatientDao patientDao;
    private Table0119Dao table0119Dao;
    private MeasureUnitDao measureUnitDao;
    private Table0162Dao table0162Dao;
    private CatalogoMedicamentosDao catalogoMedicamentosDao;
    private PrincipioActivoDao principioActivoDao;

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    
    /**
     *
     * @param catalogoMedicamentosDao
     */
    @Resource(name = "catalogoMedicamentosDao")
    public void setCatalogoMedicamentosDao(CatalogoMedicamentosDao catalogoMedicamentosDao) {
        this.catalogoMedicamentosDao = catalogoMedicamentosDao;
    }

    /**
     *
     * @param principioActivo
     */
    @Resource(name = "principioActivoDao")
    public void setPrincipioActivoDao(PrincipioActivoDao principioActivoDao) {
        this.principioActivoDao = principioActivoDao;
    }

    /**
     *
     * @param table0162Dao
     */
    @Resource(name = "table0162Dao")
    public void setTable0162Dao(Table0162Dao table0162Dao) {
        this.table0162Dao = table0162Dao;
    }

    /**
     *
     * @param measureUnitDao
     */
    @Resource(name = "measureUnitDao")
    public void setMeasureUnitDao(MeasureUnitDao measureUnitDao) {
        this.measureUnitDao = measureUnitDao;
    }

    /**
     *
     * @param table0119Dao
     */
    @Resource(name = "table0119Dao")
    public void setTable0119Dao(Table0119Dao table0119Dao) {
        this.table0119Dao = table0119Dao;
    }

    /**
     *
     * @param patientDao
     */
    @Resource(name = "patientDao")
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    /**
     * Analizamos segun venga la order de control; lo que vamos hacer con la order
     * @param order
     * @param patient
     * @throws HL7Exception
     */
    @Transactional
    public void controlOrder(Order1 order, Patient patient) throws HL7Exception {
        Order1 orderbd;
        if (order.getOrderId() != null) {
            orderbd = findOrderPresent(order);
            //orderbd Canceladas CA
            if (orderbd == null) {//orderbd si viene a null todas las order con ese id estan canceladas, en bd
                //order nueva NW
                if (order.getControl().getCode().equalsIgnoreCase("NW")) {
                    save(order, patient);
                } //order congelada HD
                else if (order.getControl().getCode().equalsIgnoreCase("HD")) {
                    throw new HL7Exception("order this previously cancel or does not exist, can not be frozen (HD) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                }//order liberada RL
                else if (order.getControl().getCode().equalsIgnoreCase("RL")) {
                    throw new HL7Exception("order this previously cancel or does not exist, can not be free (RL) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                }//order Mezcla XO
                else if (order.getControl().getCode().equalsIgnoreCase("XO")) {
                    throw new HL7Exception("order this previously cancel or does not exist, can not be update (XO) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                } else if (order.getControl().getCode().equalsIgnoreCase("EQ")) {
                    throw new HL7Exception("order is already previously canceled (CA) or does not exist; can not (EQ) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                }

            } else {
                //orderbd esta congelada HD
                if (orderbd.getControl().getCode().equalsIgnoreCase("HD")) {
                    //order nueva NW
                    if (order.getControl().getCode().equalsIgnoreCase("NW")) {
                        throw new HL7Exception("order this previously frozen, can not create new order (nw) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    } //order Cancelada CA
                    else if (order.getControl().getCode().equalsIgnoreCase("CA")) {
                        orderCancel(orderbd);
                    }//order liberada RL
                    else if (order.getControl().getCode().equalsIgnoreCase("RL")) {
                        orderFree(orderbd);
                        update(order, patient, orderbd);
                    }//order Mezcla XO
                    else if (order.getControl().getCode().equalsIgnoreCase("XO")) {
                        throw new HL7Exception("order this previously frozen (HD), can not update order (XO) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }//order control HD igual a la que ya habia
                    else if (order.getControl().getCode().equalsIgnoreCase("HD")) {
                        throw new HL7Exception("order is already previously frozen HD, can not HD, with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    } //TODO order EQ no se sabe no cumple el stadar

                    //order bbdd viene liberada RL
                } else if (orderbd.getControl().getCode().equalsIgnoreCase("RL")) {
                    //order nueva NW
                    if (order.getControl().getCode().equalsIgnoreCase("NW")) {
                        throw new HL7Exception("order this previously free, can not create new order (nw) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    } //order Cancelada CA
                    else if (order.getControl().getCode().equalsIgnoreCase("CA")) {
                        orderCancel(orderbd);
                    }//order Congelar HD
                    else if (order.getControl().getCode().equalsIgnoreCase("HD")) {
                        orderStop(orderbd);
                    }//order Mezcla XO
                    else if (order.getControl().getCode().equalsIgnoreCase("XO")) {
                        update(order, patient, orderbd);
                    } //order de control ya liberada de antes
                    else if (order.getControl().getCode().equalsIgnoreCase("RL")) {
                        throw new HL7Exception("order is already previously free RL, can not RL with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }

                    // orderbd es nueva NW
                } else if (orderbd.getControl().getCode().equalsIgnoreCase("NW")) {
                    //order liberarla
                    if (order.getControl().getCode().equalsIgnoreCase("RL")) {
                        throw new HL7Exception("order this previously new, can not free order (RL) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    } //order Cancelada CA
                    else if (order.getControl().getCode().equalsIgnoreCase("CA")) {
                        orderCancel(orderbd);
                    }//order Congelar HD
                    else if (order.getControl().getCode().equalsIgnoreCase("HD")) {
                        orderStop(orderbd);
                    }//order Mezcla XO
                    else if (order.getControl().getCode().equalsIgnoreCase("XO")) {
                        update(order, patient, orderbd);
                    }//order NW nueva igual a la de la base de datos
                    else if (order.getControl().getCode().equalsIgnoreCase("NW")) {
                        throw new HL7Exception("order is already previously New NW, can not new order (NW) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }

                    // order bd es XO mezcla
                } else if (orderbd.getControl().getCode().equalsIgnoreCase("XO")) {
                    //order liberarla
                    if (order.getControl().getCode().equalsIgnoreCase("RL")) {
                        throw new HL7Exception("order this previously Update (XO), can not free order (RL) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    } //order Cancelada CA
                    else if (order.getControl().getCode().equalsIgnoreCase("CA")) {
                        orderCancel(orderbd);
                    }//order Congelar HD
                    else if (order.getControl().getCode().equalsIgnoreCase("HD")) {
                        orderStop(orderbd);
                    }//order nueva
                    else if (order.getControl().getCode().equalsIgnoreCase("NW")) {
                        throw new HL7Exception("order this previously Update (XO), can not create new order (NW) with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }//order XO mezcla de una order
                    else if (order.getControl().getCode().equalsIgnoreCase("XO")) {
                        update(order, patient, orderbd);
                    } //TODO order EQ no se sabe no cumple el stadar segun parece no hace nada
                }
            }
        } else {
            throw new HL7Exception("There is not idorder in order", HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
    }

    /**
     * añadimos una peticion de un paciente
     * @param order
     * @param patient
     * @throws HL7Exception
     */
    @Transactional
    private void save(Order1 order, Patient patient) throws HL7Exception {
        int a;
        if(order.getOrderId().equals("17724_1231_5"))
            a=0;
        List<Patient> patients = patientDao.findPatients(patient);
        if (patients != null && patients.size() == 1) {
            List<Table0119> ltable0119 = table0119Dao.findByCode(order.getControl().getCode());
            if (ltable0119 != null && ltable0119.size() == 1) {
                //vamos por los id autoincrementales de nuestra base de datos
                if (order.getOrderTimingList() != null) {
                    for (OrderTiming orderTiming : order.getOrderTimingList()) {
                        orderTimingbd(orderTiming);
                    }
                }
                if (order.getOrderMedication() != null) {
                    order.getOrderMedication().setOrder1(order);
                    orderMedication(order.getOrderMedication());
                    //borramos si existia anteriormente una medicación para ese tratamiento su instruccion y observaciones
                    //if (!orderMedicationbd.isEmpty() && orderMedicationbd.size() == 1) {
                    //    deleteInstruObserorderMedication(orderMedicationbd.get(0));
                   // }
                }
                order.setHistoric(false);
                patients.get(0).addOrder1(order);
                order.setControl(ltable0119.get(0));
                this.entityManager.persist(order);

                if (order.getOrderMedication() != null) {
                    order.getOrderMedication().setOrderIdorder(order.getIdorder());
                    this.entityManager.persist(order.getOrderMedication());
                }

            } else {
                throw new HL7Exception("order can not be save because not found code in Master table 0119, idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else if (patients.size() > 1) {
            throw new HL7Exception("Found more than one patient to the same order, idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        } else if (patients.isEmpty()) {
            throw new HL7Exception("No patient found for order, idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }

    }

    /**
     * ampliamos la informacion order.control: RP y XO<BR>
     * ampliamos orderTiming
     * 
     * @param order
     * @param orderbd
     */
    @Transactional
    private void orderAmplify(Order1 order, Order1 orderbd) throws HL7Exception {

        if (orderbd != null) {
            if (orderbd.getOrderTimingList() != null) {
                List<OrderTiming> lorderTimingbd = orderbd.getOrderTimingList();
                for (OrderTiming orderTimingbd : lorderTimingbd) {
                    orderTimingbd.setHistoric(true);
                }
            }
            //añadimos los nuevos orderTiming
            if (order.getOrderTimingList() != null) {
                for (OrderTiming orderTiming : order.getOrderTimingList()) {
                    if (orderTiming.getGiveAmount().intValue() > 9999999) {
                        throw new HL7Exception("OrderTiming, Give Amount of order Timing number is bigger 7 digits  with orderid: " + orderTiming.getOrderIdorder().getIdorder(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }
                    orderTimingbd(orderTiming);
                    orderbd.addOrderTiming(orderTiming);
                }
            }
            //añadimos el telefono del medico
            if (order.getTelephoneDoctorList() != null && orderbd.getTelephoneDoctorList() != null) {
                List<TelephoneDoctor> lTelephoneDoctornew = order.getTelephoneDoctorList();
                for (TelephoneDoctor tnew : lTelephoneDoctornew) {
                    boolean add = true;
                    for (TelephoneDoctor telephoneDoctorbd : orderbd.getTelephoneDoctorList()) {
                        if (telephoneDoctorbd.getTelephone().equals(tnew.getTelephone())) {
                            add = false;
                        }
                    }
                    if (add) {
                        orderbd.addTelephoneDoctor(tnew);
                    }
                }
            } else if (order.getTelephoneDoctorList() != null) {
                for (TelephoneDoctor t : order.getTelephoneDoctorList()) {
                    orderbd.addTelephoneDoctor(t);
                }
            }
        }

    }

    /**
     * obtenemos la order presente que no es historica o no esta todavia cancelada
     * @param order
     * @return {@link Order1}
     * @throws HL7Exception
     */
    @Transactional
    private Order1 findOrderPresent(Order1 order) throws HL7Exception {
        Order1 orderpresent = null;
        if (order.getOrderId() != null) {
            List<Order1> lorder = findByOrderId(order.getOrderId(), order.getOrderGroupId());
            for (Order1 order1 : lorder) {
                if (order1.getHistoric() == false || !order1.getControl().getCode().equalsIgnoreCase("CA")) {
                    orderpresent = order1;
                }
            }
        } else {
            throw new HL7Exception("orderId not given ", HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        return orderpresent;
    }

    /**
     * modificamos un tratamiento
     * @param order
     * @param patient
     * @param orderoriginal
     * @throws HL7Exception
     */
    @Transactional
    private void update(Order1 order, Patient patient, Order1 orderoriginalbd) throws HL7Exception {
        if (order.getControl() != null) {
            List<Table0119> ltable0119 = table0119Dao.findByCode(order.getControl().getCode());
            if (ltable0119 != null && ltable0119.size() == 1) {
                orderoriginalbd.setControl(ltable0119.get(0));
            } else {
                throw new HL7Exception("order can not be update because not found code: " + order.getControl().getCode() + " in Master table 0119, idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }
        if (order.getOrderGroupId() != null) {
            orderoriginalbd.setOrderGroupId(order.getOrderGroupId());
        }
        if (order.getOrderStatus() != null) {
            orderoriginalbd.setOrderStatus(order.getOrderStatus());
        }
        if (order.getPriority() != null) {
            orderoriginalbd.setPriority(order.getPriority());
        }
        if (order.getOrderFather() != null) {
            orderoriginalbd.setOrderFather(order.getOrderFather());
        }
        if (order.getEventDate() != null) {
            orderoriginalbd.setEventDate(order.getEventDate());
        }
        if (order.getCreationEventUser() != null) {
            orderoriginalbd.setCreationEventUser(order.getCreationEventUser());
        }
        if (order.getValidationEventUser() != null) {
            orderoriginalbd.setValidationEventUser(order.getValidationEventUser());
        }
        if (order.getEventDoctor() != null) {
            orderoriginalbd.setEventDoctor(order.getEventDoctor());
        }
        if (order.getCreationSystem() != null) {
            orderoriginalbd.setCreationSystem(order.getCreationSystem());
        }
        if (order.getStartDate() != null) {
            orderoriginalbd.setStartDate(order.getStartDate());
        }
        if (order.getReason() != null) {
            orderoriginalbd.setReason(order.getReason());
        }
        this.entityManager.flush();
        if (patient != null) {
            List<Patient> patients = patientDao.findPatients(patient);
            if (patients != null && patients.size() == 1) {
                orderoriginalbd.getPatientId().getOrder1List().remove(orderoriginalbd);
                patients.get(0).addOrder1(orderoriginalbd);
            }
        }
        //añadimos mas ordeTiming
        orderAmplify(order, orderoriginalbd);
        //modificamos el OrderMedication
        if (order.getOrderMedication() != null) {
            order.getOrderMedication().setOrder1(orderoriginalbd);
            orderMedication(order.getOrderMedication());
            order.getOrderMedication().setOrderIdorder(orderoriginalbd.getIdorder());
            if (orderoriginalbd.getOrderMedication() == null) {
                this.entityManager.persist(order.getOrderMedication());
            }
            //orderoriginalbd.setOrderMedication(order.getOrderMedication());
            deleteInstruObserorderMedication(orderoriginalbd.getOrderMedication());
            this.entityManager.merge(order.getOrderMedication());


        }


    }

    /**
     * borramos las instrucciones y observaciones anteriores del tratamiento si vienen nuevas
     * @param ordermedicationbd
     * @param ordermedication
     */
    @Transactional
    private void deleteInstruObserorderMedication(OrderMedication ordermedicationbd) {
             for (OrderMedicationInstruction o : ordermedicationbd.getOrderMedicationInstructionList()) {
                this.entityManager.remove(o);
            }
        
             for (OrderMedicationObservation o : ordermedicationbd.getOrderMedicationObservationList()) {
                this.entityManager.remove(o);
            }
    }

    /**
     * cancelamos un tratamiento poniendolo a historico
     * @param order
     * @throws HL7Exception
     */
    @Transactional
    public void orderCancel(Order1 order) throws HL7Exception {
        //cambiamos el codigo de control a CA id 2
        List<Table0119> table0119 = table0119Dao.findByCode("CA");
        if (table0119 != null && table0119.size() == 1) {
            order.setControl(table0119.get(0));
            order.setHistoric(true);
            List<OrderTiming> lorderTiming = order.getOrderTimingList();
            for (OrderTiming ot : lorderTiming) {
                ot.setHistoric(true);
            }
        } else {
            throw new HL7Exception("order can not be canceled because not found (CA) code in Master table 0119 with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }


    }

    /**
     * actualizamos los id del tiempo del tratamiento de nuestra base de datos
     *
     * @param orderTimingList
     */
    @Transactional
    private boolean orderTimingbd(OrderTiming orderTiming) throws HL7Exception {
        boolean error = false;
        //vamos por id de measureUnit en nuestra base de datos
        if (orderTiming.getMeasureUnitIdmeasureUnit() != null) {
            MeasureUnit measureUnit = measureUnit(orderTiming.getMeasureUnitIdmeasureUnit());
            if (measureUnit != null) {
                orderTiming.setMeasureUnitIdmeasureUnit(measureUnit);
            } else {
                throw new HL7Exception("orderTiming, No measureUnit  for orderid: " + orderTiming.getOrderIdorder().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }
        //son obligatorios
        if (orderTiming.getStartDate() == null) {
            error = true;
            throw new HL7Exception("orderTiming, No date on which should begin to administer medication for orderid: " + orderTiming.getOrderIdorder().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        if (orderTiming.getTime() == null) {
            error = true;
            throw new HL7Exception("orderTiming, No Time at which medication should be administered for orderid: " + orderTiming.getOrderIdorder(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        return error;
    }

    /**
     * obtenemos de la tabla maestra measureUnit, el id o name
     * @param measureUnit
     * @return
     */
    @Transactional
    private MeasureUnit measureUnit(MeasureUnit measureUnit) {
        List<MeasureUnit> lmeasureUnits = new ArrayList();
        if (measureUnit.getIdmeasureUnit() != null) {
            lmeasureUnits = measureUnitDao.findByIdmeasureUnit(measureUnit.getIdmeasureUnit());
        } else if (measureUnit.getName() != null) {
            lmeasureUnits = measureUnitDao.findByName(measureUnit.getName());
        }
        if (lmeasureUnits != null && lmeasureUnits.size() > 0) {
            return lmeasureUnits.get(0);
        } else {
            return null;
        }
    }

    /**
     * obtenemos de nuestra tabla maestra, los id que
     * necesitamos para orderMedication
     * @param orderMedication
     */
    @Transactional
    private void orderMedication(OrderMedication orderMedication) throws HL7Exception {
        //no puede superar este valor
        if (orderMedication.getGiveAmount().intValue() > 9999999) {
            throw new HL7Exception("OrderMedication, Give Amount of order medication number is bigger 7 digits  with orderid: " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        if (orderMedication.getAdministrationType() != null) {
            List<Table0162> lTable0162 = new ArrayList();
            if (orderMedication.getAdministrationType().getCode() != null) {
                lTable0162 = table0162Dao.findByCode(orderMedication.getAdministrationType().getCode());
            } else if (orderMedication.getAdministrationType().getDetails() != null) {
                lTable0162 = table0162Dao.findByDetails(orderMedication.getAdministrationType().getDetails());
            }
            if (lTable0162 != null && lTable0162.size() == 1) {
                orderMedication.setAdministrationType(lTable0162.get(0));
            } else {
                throw new HL7Exception("OrderMedication, No AdministrationType code: " + orderMedication.getAdministrationType().getCode() + " for orderid " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }

        } else {
            throw new HL7Exception("OrderMedication, No AdministrationType for orderid " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        if (orderMedication.getCatalogomedicamentosCODIGO() != null && orderMedication.getCatalogomedicamentosCODIGO().getCodigo() != null) {
            List<CatalogoMedicamentos> lCatalogoMedicamentos = catalogoMedicamentosDao.findByCodigo(orderMedication.getCatalogomedicamentosCODIGO().getCodigo());
            if (lCatalogoMedicamentos != null && lCatalogoMedicamentos.size() == 1) {
                orderMedication.setCatalogomedicamentosCODIGO(lCatalogoMedicamentos.get(0));
            } else {
                throw new HL7Exception("OrderMedication, Product catalog medicine code not found: " + orderMedication.getCatalogomedicamentosCODIGO().getCodigo() + " for orderid " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }

        } else if (orderMedication.getPrincipioActivo() != null && orderMedication.getPrincipioActivo().getCodigo() != null) {
            List<PrincipioActivo> lprincipioActivo = principioActivoDao.findByCodigo(orderMedication.getPrincipioActivo().getCodigo());
            if (lprincipioActivo != null && lprincipioActivo.size() == 1) {
                orderMedication.setPrincipioActivo(lprincipioActivo.get(0));
            } else {
                throw new HL7Exception("OrderMedication, active component code not found: " + orderMedication.getPrincipioActivo().getCodigo() + " for orderid " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else {
            throw new HL7Exception("OrderMedication, medication code and active component not found for orderid " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }

        if (orderMedication.getMeasureUnitIdmeasureUnit() != null) {
            MeasureUnit measureUnit = measureUnit(orderMedication.getMeasureUnitIdmeasureUnit());
            if (measureUnit != null) {
                orderMedication.setMeasureUnitIdmeasureUnit(measureUnit);
            } else {
                throw new HL7Exception("OrderMedication, No found measureUnit for orderid " + orderMedication.getOrder1().getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }
    }

    /**
     * Congelamos la order poniendola a historica
     * @param order
     */
    @Transactional
    private void orderStop(Order1 order) throws HL7Exception {
        List<Table0119> table0119 = table0119Dao.findByCode("HD");
        if (table0119 != null && table0119.size() == 1) {
            //cambiamos el codigo de control a HD id 10
            order.setHistoric(true);
            order.setControl(table0119.get(0));
        } else {
            throw new HL7Exception("order can not be Stop because not found (HD) code in Master table 0119 with idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
    }

    /**
     * liberamos la order dada quitando el historico
     * @param order
     * @throws HL7Exception
     */
    @Transactional
    private void orderFree(Order1 order) throws HL7Exception {
        order.setHistoric(false);
    }

    /**
     *obtenemos la lista de todos los tratamientos
     * @return list {@link Order1}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Order1> findAll() {
        return this.entityManager.createNamedQuery("Order1.findAll").getResultList();
    }

   

    /**
     *obtenemos la lista de un tratamiento a partir de su orderId y grupo
     * @param orderId
     * @param groupId
     * @return list {@link Order1}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Order1> findByOrderId(String orderId, String groupId) {
        return this.entityManager.createQuery("SELECT o FROM Order1 o WHERE o.orderId = :orderId and o.orderGroupId=:groupId").setParameter("orderId", orderId).setParameter("groupId", groupId).getResultList();
    }
}
