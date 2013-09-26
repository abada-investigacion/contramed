/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.treatment.impl;

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

import com.abada.trazability.dao.measureunitconversion.MeasureUnitConversionDao;
import com.abada.trazability.dao.treatment.ThreatmentDao;
import com.abada.trazability.entity.CatalogoMedicamentos;
import com.abada.trazability.entity.GivesHistoric;
import com.abada.trazability.entity.MeasureUnit;
import com.abada.trazability.entity.Order1;
import com.abada.trazability.entity.OrderMedication;
import com.abada.trazability.entity.OrderMedicationInstruction;
import com.abada.trazability.entity.OrderMedicationObservation;
import com.abada.trazability.entity.OrderTiming;
import com.abada.trazability.entity.PrepareHistoric;
import com.abada.trazability.entity.PrincipioActivo;
import com.abada.trazability.entity.Table0162;
import com.abada.trazability.entity.enums.TypeGivesHistoric;
import com.abada.trazability.entity.enums.TypePrepareHistoric;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.entity.temp.GivesHistoricGroupType;
import com.abada.trazability.entity.temp.PrescriptionType;
import com.abada.trazability.entity.temp.ThreatmentInfo;
import com.abada.trazability.entity.temp.ThreatmentInfoHistoric;
import com.abada.trazability.entity.temp.ThreatmentInfoStatus;
import com.abada.trazability.entity.temp.TreatmentActionMode;
import com.abada.trazability.entity.temp.TypeThreatmentInfoStatus;
import com.abada.trazability.exception.GenericWebContramedException;
import com.abada.trazability.exception.WebContramedException;
import com.abada.trazabiliity.utils.RangeItem;
import com.abada.trazabiliity.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("threatmentDao")
public class ThreatmentDaoImpl extends JpaDaoUtils implements ThreatmentDao {

    @Resource(name = "messageSource")
    private MessageSource locale;
    @Resource(name = "measureUnitConversionDao")
    private MeasureUnitConversionDao measureUnitConversionDao;

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

// <editor-fold defaultstate="collapsed" desc="Devuelve los tratamientos">
    @Transactional(readOnly = true, timeout = 60)
    public List<ThreatmentInfo> getActiveThreatmentByPharmacy(Long idPatient, Date startDate, Date endDate) throws WebContramedException {
        if (idPatient == null) {
            throw new GenericWebContramedException("No se ha seleccionado ning&uacute;n paciente.");

        }
        if (startDate == null) {
            throw new GenericWebContramedException("No se ha seleccionado d&iacute;a de comienzo.");

        }
        if (endDate == null) {
            throw new GenericWebContramedException("No se ha seleccionado d&iacute;a de fin.");
            //Lo primero es cargar la información de los tramientos

        }
        List<ThreatmentInfo> resultAux = this.getActiveThreatment(idPatient, startDate, endDate);
        //En farmacia se filtran los medicamentos si precisa
        List<ThreatmentInfo> result = new ArrayList<ThreatmentInfo>();
        for (ThreatmentInfo th : resultAux) {
            if (!th.isIfNecesary()) {
                result.add(th);
            }
        }
        //Marcar como administrada la dosis
        generateAdministrationStatusByPharmacy(result);
        return result;
    }

    @Transactional(readOnly = true, timeout = 60)
    public List<ThreatmentInfo> getActiveThreatmentByNursing(Long idPatient, Date startTime, Date endTime) throws WebContramedException {
        if (idPatient == null) {
            throw new GenericWebContramedException("No se ha seleccionado ning&uacute;n paciente.");

        }
        if (startTime == null) {
            throw new GenericWebContramedException("No se ha seleccionado hora de comienzo.");

        }
        if (endTime == null) {
            throw new GenericWebContramedException("No se ha seleccionado hora de fin.");
            //Poner día de hoy en las fechas

        }
        /*Date now = new Date();
        startTime.setDate(now.getDate());
        endTime.setDate(now.getDate());
        startTime.setMonth(now.getMonth());
        endTime.setMonth(now.getMonth());
        startTime.setYear(now.getYear());
        endTime.setYear(now.getYear());*/
        //Lo primero es cargar la información de los tramientos
        List<ThreatmentInfo> result = this.getActiveThreatment(idPatient, startTime, endTime);
        //Marcar como administrada la dosis
        generateAdministrationStatusByNursing(result);
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private List<ThreatmentInfo> getActiveThreatment(Long idPatient, Date startTime, Date endTime) throws WebContramedException {
        StringBuilder sql = new StringBuilder("from OrderTiming ot where ");//De la tabla orderTiming
        sql.append("ot.historic = FALSE");//No es una orden antigua
        sql.append(" and ot.orderIdorder.patientId.id = ?");//del paciente correcto
        sql.append(" and ot.orderIdorder.historic = FALSE");//Y que la orden no sea antigua
        sql.append(" and ot.orderIdorder.control.code IS NOT 'HD'");//Y que la orden no sea antigua
        List<OrderTiming> ordersTiming = this.entityManager.find(sql.toString(), idPatient);
        if (ordersTiming != null) {
            List<ThreatmentInfo> result = generateThreatmentInfoByDate(ordersTiming, startTime, endTime);
            return result;
        }
        return null;
    }

    /**
     * Devuelve la informacion de cada una de las tomas que se deben administrar en el periodo seleccionado.
     * @param ordersTiming
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private List<ThreatmentInfo> generateThreatmentInfoByDate(List<OrderTiming> ordersTiming, Date startTime, Date endTime) throws WebContramedException {
        List<ThreatmentInfo> result = new ArrayList<ThreatmentInfo>();
        //Calculamos los dias que hay entre el comienzo y el fin del rango
        List<RangeItem> days = Utils.generateRange(startTime, endTime);
        List<RangeItem> auxRange;
        Date giveDate;

        for (OrderTiming ot : ordersTiming) {
            auxRange = Utils.filterRange(days, ot.getStartDate(), ot.getRepetitionPattern());
            for (RangeItem ri : auxRange) {
                if ((giveDate = isInDate(ot, ri.getStartTime(), ri.getEndTime())) == null) {
                    /*if (ifOrderTimingNecesary(ot)){
                    giveDate=new Date();
                    }*/
                }
                if (giveDate != null) {
                    ThreatmentInfo aux = generateThreatmentInfo(ot, giveDate);
                    if (aux != null) {
                        //FIXME Caso raro de si precisa con tomas. Tratamiento particular para filtralo
                        if (!aux.isIfNecesary() || (aux.isIfNecesary() && !containsThreatmentInfo(result, aux))) {
                            result.add(aux);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Usado para filtrar el numero de si precisas del mismo medicamento, con la misma cantidad que aparecen mostrados.
     * @param result
     * @param aux
     * @return
     */
    private boolean containsThreatmentInfo(List<ThreatmentInfo> result, ThreatmentInfo aux) {
        for (ThreatmentInfo t : result) {
            if (t.isIfNecesary()) {
                if (t.equals(aux)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Devuelve si un ordertiming se corresponde con el periodo de fecha seleccionado,
     * el dia de comienzo y fin deben ser el mismo día, aunque pueden tener diferentes horas
     *
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private Date isInDate(OrderTiming ot, Date startTime, Date endTime) {
        //comprobar que la fecha inicio es correcta
        Date startDate = ot.getStartDate();//primero con la fecha inicio de ordertiming
        if (startDate == null) {
            //si no habia ficha inicio en ordertiming miramos si hay hora inicio en order
            startDate = ot.getOrderIdorder().getStartDate();
            if (startDate == null) {
                // TODO una ostia gorda porque no tiene hora inicio
                if (ifOrderTimingNecesary(ot)) {
                    startDate = new Date();
                }
            }
        }
        if (startDate == null || startDate.after(endTime)) {
            return null;
            //comprobar que la fecha fin no se ha sobrepasado
        }

        Date endDate = ot.getEndDate();//primero con la fecha fin de ordertiming
        if (endDate == null) {
            //si no habia ficha fin en ordertiming se intenta calcula con la suma de
            //la fecha inicio mas duration_time que esta en horas
            if (ot.getDurationTime() != null && ot.getDurationTime() >= 0) {
                endDate = new Date(startDate.getTime());
                endDate.setHours(endDate.getHours() + ot.getDurationTime());
            }
        }
        if (endDate != null && (endDate.equals(startTime) || endDate.before(startTime))) {
            return null;
            //comprobar que la hora de la toma esta en el periodo que nos pasan
        }

        Date now = new Date(startTime.getTime());
        now.setSeconds(0);
        if (ifOrderTimingNecesary(ot)) {
            Date aux = new Date();
            now.setHours(aux.getHours());
            now.setMinutes(aux.getMinutes());
            //En el caso de ser una medicacion si precisa y que esté fuera del rango entonces se la hace entrar en el rango a las bravas
            //para que la medicación si precisa salga en cualquier rango.
            if (now.before(startTime)) {
                now.setHours(startTime.getHours());
                now.setMinutes(startTime.getMinutes());
            }
        } else {
            if (ot.getTime() != null) {
                now.setHours(ot.getTime().getHours());
                now.setMinutes(ot.getTime().getMinutes());
            } else {
                return null;
            }
        }        

        if (now.before(startTime) || (endTime != null && now.after(endTime))) {
            return null;
            //En caso de no cumplir nada de lo anterior la ordertiming es correcta
        }
        return now;
    }

    /***
     * Carga la informacion necesaria sobre un tratamiento
     * @param ot
     * @param giveDate
     * @return
     * @throws WebContramedException
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private ThreatmentInfo generateThreatmentInfo(OrderTiming ot, Date giveDate) throws WebContramedException {
        ThreatmentInfo result = new ThreatmentInfo();

        Order1 order = ot.getOrderIdorder();
        if (order == null) {
            throw new GenericWebContramedException("No hay información de la orden.");

        }
        OrderMedication medicine = order.getOrderMedication();
        if (medicine == null) {
            throw new GenericWebContramedException("No hay información de medicamento a administrar.");

        }
        Table0162 administrationType = medicine.getAdministrationType();
        if (administrationType == null) {
            throw new GenericWebContramedException("No hay información del modo de administración del medicamento");

        }
        CatalogoMedicamentos speciality = medicine.getCatalogomedicamentosCODIGO();
        if (speciality == null) {
            if (medicine.getPrincipioActivo() == null) {
                throw new GenericWebContramedException("No hay información de la especialidad ni del principio activo de medicamento a administrar");
            }
        }

        MeasureUnit measureUnit = medicine.getMeasureUnitIdmeasureUnit();
        if (measureUnit == null) {
            throw new GenericWebContramedException("No hay información de la unidad de adminstración");

        }
        PrincipioActivo principioActivo = null;
        if (speciality != null) {
            if (speciality.getPrincipioHasEspecialidadList() != null && !speciality.getPrincipioHasEspecialidadList().isEmpty()) {
                principioActivo = speciality.getPrincipioHasEspecialidadList().get(0).getCodigoPrincipio();
            }
        } else {
            principioActivo = medicine.getPrincipioActivo();
        }
        List<String> instructions = null;
        if (medicine.getOrderMedicationInstructionList() != null && !medicine.getOrderMedicationInstructionList().isEmpty()) {
            instructions = new ArrayList<String>();
            for (OrderMedicationInstruction omi : medicine.getOrderMedicationInstructionList()) {
                instructions.add(omi.getInstruction());
            }
        }
        List<String> observations = null;
        if (medicine.getOrderMedicationObservationList() != null && !medicine.getOrderMedicationObservationList().isEmpty()) {
            observations = new ArrayList<String>();
            for (OrderMedicationObservation omo : medicine.getOrderMedicationObservationList()) {
                observations.add(omo.getObservation());
            }
        }

        result.setCodeVia(administrationType.getCode());

        //via de administracion
        result.setAdministrationType(administrationType.getDetails());
        //Alergico
        result.setAlergy(medicine.getAlergy());
        //Medicamento a administrar
        if (speciality != null) {
            result.setPrescriptionType(PrescriptionType.ARTICLE);
            result.setCodigoNacionalMedicamento(speciality.getCodigo());
            result.setNombreMedicamento(speciality.getNombre());
        } else {
            result.setPrescriptionType(PrescriptionType.ACTIVE);
        }
        //Cantidad a administrar
        result.setGiveAmount(medicine.getGiveAmount());
        result.setIdMeasureUnit((long) measureUnit.getIdmeasureUnit());
        result.setMeasureUnit(measureUnit.getName());
        result.setMeasureUnitObject(measureUnit);
        //hora y dia de administracion
        result.setGiveTime(giveDate);
        //id de la orden timing
        result.setIdOrderTiming(ot.getIdorderTiming());
        if (result.getPrescriptionType() == PrescriptionType.ACTIVE && principioActivo == null) {
            throw new GenericWebContramedException("No hay información del principio activo de medicamento a administrar");
        }
        if (principioActivo != null) {
            result.setIdPrincipioActivo(principioActivo.getCodigo());
            result.setPrincipioActivo(principioActivo.getNombre());
        }
        //if necesary
        result.setIfNecesary(ifOrderTimingNecesary(ot));
        result.setInstructions(instructions);
        result.setObservations(observations);

        String aux = "";
        if (result.getNombreMedicamento() != null) {
            aux += result.getNombreMedicamento();
        }
        if (result.getPrincipioActivo() != null) {
            if (result.getNombreMedicamento() != null) {
                aux += "-" + result.getPrincipioActivo();
            } else {
                aux += result.getPrincipioActivo();
            }
        }
        result.setEspecialidadPActivo(aux);
        return result;
    }

    /**
     * Return true if ot is necesary or the repetition pattern is SDF
     * @param ot
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private boolean ifOrderTimingNecesary(OrderTiming ot) {
        return (ot.getIfNecesary() || "SP".equals(ot.getRepetitionPattern()));
    }

    /**
     * Genera el estado de la administracion de los tratamientos.
     * @param result
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private void generateAdministrationStatusByPharmacy(List<ThreatmentInfo> result) {
        for (ThreatmentInfo threatmentInfo : result) {
            generateAdministrationStatusByPharmacy(threatmentInfo);
        }
    }

    /**
     *
     * @param threatmentInfo
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private void generateAdministrationStatusByPharmacy(ThreatmentInfo threatmentInfo) {
        List<PrepareHistoric> historics = getPrepareHistoric(threatmentInfo);
        //sumar las dosis para ver si ya esta
        double cuantity = sumPrepareHistoric(historics);
        //Crear estado
        ThreatmentInfoStatus status = new ThreatmentInfoStatus();
        threatmentInfo.setThreatmentInfoStatus(status);
        double dif = cuantity - threatmentInfo.getGiveAmount().doubleValue();
        status.setDifference(dif);
        if (cuantity < threatmentInfo.getGiveAmount().doubleValue()) {
            status.setStatus(TypeThreatmentInfoStatus.NOT_COMPLETED);
            status.setText("Faltan " + (-dif) + " " + threatmentInfo.getMeasureUnit());
        } else if (cuantity == threatmentInfo.getGiveAmount().doubleValue()) {
            status.setStatus(TypeThreatmentInfoStatus.COMPLETED);
            if (historics != null && historics.size() == 1 && historics.get(0).getType().equals(TypePrepareHistoric.FORCEDBOX)) {
                status.setText("Añadido Manualmente");
            } else {
                status.setText("Añadido");
            }
        } else if (cuantity > threatmentInfo.getGiveAmount().doubleValue()) {
            status.setStatus(TypeThreatmentInfoStatus.NOT_COMPLETED);
            status.setText("Sobra " + dif + " " + threatmentInfo.getMeasureUnit());
        }
    }

    /**
     * Devuelve los PrepareHistoric que cuentan como añadidos. Elimina
     * todas las entradas de outbox e inbox que le correspondan
     * @param threatmentInfo
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<PrepareHistoric> getPrepareHistoric(ThreatmentInfo threatmentInfo) {
        List<PrepareHistoric> aux = getPrepareHistoricInt(threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime());
        List<PrepareHistoric> result = new ArrayList<PrepareHistoric>();
        //Mirar cuales son validos, recuerda que hay del tipo inbox y outbox
        Long idDose = null;
        int outbox, inbox, toAdd = 0;

        for (PrepareHistoric historic : aux) {
            if (historic.getDoseIddose() != null) {//FIXME Chapuza para "Aceptar todo"
                if (idDose == null || idDose != historic.getDoseIddose().getIddose()) {
                    idDose = historic.getDoseIddose().getIddose();
                    //comprobar cantidad de inbox y outbox con ese mismo id
                    outbox = this.getCountPrepareHistoric(aux, idDose, TypePrepareHistoric.OUTBOX);
                    inbox = this.getCountPrepareHistoric(aux, idDose, TypePrepareHistoric.INBOX);

                    toAdd = inbox - outbox;
                }
                if (toAdd > 0 && historic.getType().equals(TypePrepareHistoric.INBOX)) {
                    result.add(historic);
                    toAdd--;
                }
            } else {
                //FIXME Chapuza para "Aceptar todo"
                result.add(historic);
            }
        }
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private int getCountPrepareHistoric(List<PrepareHistoric> historics, Long idDose, TypePrepareHistoric typePrepareHistoric) {
        int result = 0;
        for (PrepareHistoric historic : historics) {
            if (historic.getDoseIddose() != null && historic.getDoseIddose().getIddose() == idDose && historic.getType().equals(typePrepareHistoric)) {
                result++;
            }
        }
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private double sumPrepareHistoric(List<PrepareHistoric> historics) {
        //presuponemos que la unidad de medida es la misma en todos
        // TODO Comprobor que todos tienen la misma unidad y que es la misma que la de la ordertiming
        double total = 0;
        if (historics != null) {
            for (PrepareHistoric historic : historics) {
                if (historic.getDoseIddose() != null) {
                    total += this.measureUnitConversionDao.convert(historic.getDoseIddose().getGiveAmount(), historic.getDoseIddose().getMeasureUnitIdmeasureUnit(), historic.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit()).doubleValue();
                } else {
                    //FIXME Chapuza para soportar Aceptar todo
                    return historic.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getGiveAmount().doubleValue();
                }
            }
        }
        return total;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private void generateAdministrationStatusByNursing(List<ThreatmentInfo> result) {
        for (ThreatmentInfo threatmentInfo : result) {
            generateAdministrationStatusByNursing(threatmentInfo);
        }
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private void generateAdministrationStatusByNursing(ThreatmentInfo threatmentInfo) {
        short aux;
        List<GivesHistoric> historics = getGivesHistoric(threatmentInfo);

        //Crear estado
        ThreatmentInfoStatus status = new ThreatmentInfoStatus();
        threatmentInfo.setThreatmentInfoStatus(status);

        if (historics.size() == 1 && TypeGivesHistoric.isNotGiven(historics.get(0).getType())) {
            status.setDifference(-threatmentInfo.getGiveAmount().doubleValue());
            status.setText("No Administrado");
            status.setStatus(TypeThreatmentInfoStatus.NOT_GIVEN);
        } else {
            //sumar las dosis para ver si ya esta
            double cuantity = sumGivesHistoric(historics);
            double dif = cuantity - threatmentInfo.getGiveAmount().doubleValue();

            status.setDifference(dif);
            if (cuantity < threatmentInfo.getGiveAmount().doubleValue()) {
                status.setStatus(TypeThreatmentInfoStatus.NOT_COMPLETED);
                status.setText("Faltan " + (-dif) + " " + threatmentInfo.getMeasureUnit());
            } else if (cuantity == threatmentInfo.getGiveAmount().doubleValue()) {
                status.setStatus(TypeThreatmentInfoStatus.COMPLETED);
                if (historics != null && historics.size() == 1 && historics.get(0).getType().equals(TypeGivesHistoric.FORCED)) {
                    status.setText("Administrado Manualmente");
                } else {
                    status.setText("Administrado");
                }
            } else if (cuantity > threatmentInfo.getGiveAmount().doubleValue()) {
                status.setStatus(TypeThreatmentInfoStatus.NOT_COMPLETED);
                status.setText("Sobra " + dif + " " + threatmentInfo.getMeasureUnit());
            }
        }
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private boolean isLastGivesHistoricTypeOf(Long idOrderTiming, Date giveDate, TypeGivesHistoric type) {
        List<GivesHistoric> result = this.entityManager.find("from GivesHistoric gh where gh.orderTimingIdorderTiming.idorderTiming = ? and gh.orderTimingDate = ? order by gh.eventDate desc", idOrderTiming, giveDate);//threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime());
        if (result != null && result.size() > 0) {
            return result.get(0).getType().equals(type);
        }
        return false;
    }

    /**
     * Devuelve los GivesHistoric que cuentan como añadidos y aquellos que no se han administrados desde el principio. Elimina
     * todas las demás entradas
     * @param threatmentInfo
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<GivesHistoric> getGivesHistoric(ThreatmentInfo threatmentInfo) {
        List<GivesHistoric> aux = getGivesHistoricInt(threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime());
        List<GivesHistoric> result = new ArrayList<GivesHistoric>();
        //Mirar cuales son validos, recuerda que hay del tipo añadido(REGULAR,OUT_ORDER,EQUIVALENT,FORCED) y sacar o no administrado(NOT_GIVEN)
        Long idDose = null;
        int notGiven, given, remove, toAdd = 0, toAddNotGiven = 0;

        for (GivesHistoric historic : aux) {
            if (historic.getDoseIddose() != null) {//FIXME Chapuza para "Aceptar todo"
                if (idDose == null || idDose != historic.getDoseIddose().getIddose()) {
                    idDose = historic.getDoseIddose().getIddose();
                    //comprobar cantidad de inbox y outbox con ese mismo id
                    notGiven = this.getCountGivesHistoric(aux, idDose, GivesHistoricGroupType.NOT_GIVEN);
                    given = this.getCountGivesHistoric(aux, idDose, GivesHistoricGroupType.ADD);
                    remove = this.getCountGivesHistoric(aux, idDose, GivesHistoricGroupType.REMOVE);

                    toAdd = given - remove;
                    if (notGiven >= 1 && this.isLastGivesHistoricTypeOf(threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime(), TypeGivesHistoric.NOT_GIVEN)) {
                        toAddNotGiven = 1;//notGiven;
                    }
                }
                if (toAdd > 0 && TypeGivesHistoric.isGiven(historic.getType())) {
                    result.add(historic);
                    toAdd--;
                }
                if (toAddNotGiven > 0 && TypeGivesHistoric.isNotGiven(historic.getType())) {
                    result.add(historic);
                    toAddNotGiven--;
                }
            } else {
                //FIXME Chapuza para "Aceptar todo"
                result.add(historic);
            }
        }
        return result;
    }

    /**
     * Cuenta las entradas historicas de un cierto tipo
     * @param historics
     * @param idDose
     * @param groupType determina el tipo de entradas que debe contar
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private int getCountGivesHistoric(List<GivesHistoric> historics, Long idDose, GivesHistoricGroupType groupType) {
        int result = 0;
        for (GivesHistoric historic : historics) {
            if (historic.getDoseIddose() != null && historic.getDoseIddose().getIddose() == idDose) {
                switch (groupType) {
                    case ADD:
                        if (TypeGivesHistoric.isGiven(historic.getType())) {
                            result++;
                        }
                        break;
                    case NOT_GIVEN:
                        if (TypeGivesHistoric.isNotGiven(historic.getType())) {
                            result++;
                        }
                        break;
                    case REMOVE:
                        if (TypeGivesHistoric.isRemove(historic.getType())) {
                            result++;
                        }
                        break;
                }
            }
        }
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private List<GivesHistoric> getGivesHistoricInt(Long idOrderTiming, Date giveDate) {
        List<GivesHistoric> result = this.entityManager.find("from GivesHistoric gh where gh.orderTimingIdorderTiming.idorderTiming = ? and gh.orderTimingDate = ? order by gh.doseIddose.iddose, gh.eventDate desc", idOrderTiming, giveDate);//threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime());
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private List<PrepareHistoric> getPrepareHistoricInt(Long idOrderTiming, Date giveDate) {
        return this.entityManager.find("from PrepareHistoric ph where ph.orderTimingIdorderTiming.idorderTiming = ? and ph.orderTimingDate = ? order by ph.doseIddose.iddose, ph.eventDate desc", idOrderTiming, giveDate);
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private double sumGivesHistoric(List<GivesHistoric> historics) {
        //presuponemos que la unidad de medida es la misma en todos
        // TODO Comprobor que todos tienen la misma unidad y que es la misma que la de la ordertiming
        double total = 0;
        if (historics != null) {
            for (GivesHistoric historic : historics) {
                if (historic.getDoseIddose() != null) {
                    if (!TypeGivesHistoric.isNotGiven(historic.getType())) {
                        total += this.measureUnitConversionDao.convert(historic.getDoseIddose().getGiveAmount(), historic.getDoseIddose().getMeasureUnitIdmeasureUnit(), historic.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit()).doubleValue();
                    }
                } else {
                    //FIXME Chapuza para soportar Aceptar todo
                    return historic.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getGiveAmount().doubleValue();
                }
            }
        }
        return total;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Devuelve historiales">
    @Transactional(value="trazability-txm",readOnly = true)
    public List<ThreatmentInfoHistoric> getPrepareHistoric(Long idOrderTiming, Date giveDate) {
        List<ThreatmentInfoHistoric> result = new ArrayList<ThreatmentInfoHistoric>();
        ThreatmentInfoHistoric aux;
        //Cargar los PrepareHistoric
        List<PrepareHistoric> historics = this.getPrepareHistoricInt(idOrderTiming, giveDate);
        if (historics != null) {
            for (PrepareHistoric ph : historics) {
                aux = createThreatmentInfoHistoric(ph);
                if (aux != null) {
                    result.add(aux);

                }
            }
        }
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public List<ThreatmentInfoHistoric> getGiveHistoric(Long idOrderTiming, Date giveDate) {
        List<ThreatmentInfoHistoric> result = new ArrayList<ThreatmentInfoHistoric>();
        ThreatmentInfoHistoric aux;
        //Cargar los PrepareHistoric
        List<GivesHistoric> historics = this.getGivesHistoricInt(idOrderTiming, giveDate);
        if (historics != null) {
            for (GivesHistoric ph : historics) {
                aux = createThreatmentInfoHistoric(ph);
                if (aux != null) {
                    result.add(aux);

                }
            }
        }
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private ThreatmentInfoHistoric createThreatmentInfoHistoric(PrepareHistoric ph) {
        ThreatmentInfoHistoric result = new ThreatmentInfoHistoric();
        result.setEventDate(ph.getEventDate());
        if (ph.getDoseIddose() != null) {//FIXME Chapuza para aceptar todo
            result.setIdDose(ph.getDoseIddose().getIddose());
            result.setCuantity(this.measureUnitConversionDao.convert(ph.getDoseIddose().getGiveAmount(), ph.getDoseIddose().getMeasureUnitIdmeasureUnit(), ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit()));
            result.setMeasureUnit(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit().getName());
            result.setSpeciality(ph.getDoseIddose().getCatalogomedicamentosCODIGO().getNombre());
        } else {
            //FIXME Chapuza para aceptar todo
            result.setIdDose(null);
            result.setCuantity(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getGiveAmount());
            result.setMeasureUnit(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit().getName());
            result.setSpeciality(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getCatalogomedicamentosCODIGO().getNombre());
        }
        result.setObservation(ph.getObservation());
        result.setStaff(ph.getStaffIdstaff().getUsername());
        result.setType(locale.getMessage(ph.getType().toString(), null, null));
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private ThreatmentInfoHistoric createThreatmentInfoHistoric(GivesHistoric ph) {
        ThreatmentInfoHistoric result = new ThreatmentInfoHistoric();
        result.setEventDate(ph.getEventDate());
        if (ph.getDoseIddose() != null) {//FIXME Chapuza para aceptar todo
            result.setIdDose(ph.getDoseIddose().getIddose());
            result.setCuantity(this.measureUnitConversionDao.convert(ph.getDoseIddose().getGiveAmount(), ph.getDoseIddose().getMeasureUnitIdmeasureUnit(), ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit()));
            result.setMeasureUnit(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit().getName());
            result.setSpeciality(ph.getDoseIddose().getCatalogomedicamentosCODIGO().getNombre());
        } else {
            //FIXME Chapuza para aceptar todo
            result.setIdDose(null);
            result.setCuantity(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getGiveAmount());
            result.setMeasureUnit(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getMeasureUnitIdmeasureUnit().getName());
            result.setSpeciality(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getCatalogomedicamentosCODIGO().getNombre());
        }
        result.setObservation(ph.getObservation());
        result.setStaff(ph.getStaffIdstaff().getUsername());
        result.setType(locale.getMessage(ph.getType().toString(), null, null));
        return result;
    }// </editor-fold>
}
