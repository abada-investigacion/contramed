package com.abada.trazability.dao.recurso;

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

import com.abada.extjs.ComboBoxResponse;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.Recurso;
import com.abada.trazability.entity.temp.BedDataView;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad Recurso, trabajamos con los datos de la cama y Recurso
 */
@Repository("recursoDao")
public class RecursoDaoImpl extends JpaDaoUtils implements RecursoDao {
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /**
     *obtenemos la lista de todos los Recurso
     * @return List {@link Recurso}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Recurso> findAll() {
        return this.entityManager.createNamedQuery("Recurso.findAll").getResultList();
    }

    /**
     *obtenemos la lista de Recurso a partir de idRecurso
     * @param idRecurso
     * @return List {@link Recurso}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Recurso> findByIdRecurso(Long idRecurso) {
        return this.entityManager.createNamedQuery("Recurso.findByIdRecurso").setParameter("idRecurso", idRecurso).getResultList();
    }

    /**
     *obtenemos la lista de Recurso a partir de nr
     * @param nr cama del paciente
     * @return List {@link Recurso}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Recurso> findByNr(String nr) {
        return this.entityManager.createNamedQuery("Recurso.findByNr").setParameter("nr", nr).getResultList();
    }

    /**
     * Devuelve la lista con el siguiente nivel de las rutas hijas distintas
     * que comienzan por rootPath
     * @param rootPath
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<ComboBoxResponse> findRecusoByPath(String rootPath) {
        String SQL = "SELECT DISTINCT(SUBSTRING(r.ruta," + (rootPath.length() + 1) + ")) FROM Recurso r WHERE r.ruta LIKE :path AND r.tipoRecurso = 'CAMA' AND r.ubicacionRecurso='HOS'";
        //String SQL="SELECT LOCATE(r.ruta,'/',"+(rootPath.length()+2)+") FROM Recurso r WHERE r.ruta LIKE :path";
        List<String> lruta = this.entityManager.createQuery(SQL).setParameter("path", rootPath + "%").getResultList();
        if (lruta != null && lruta.isEmpty()) {
            //return this.findRecusoByPath(rootPath.substring(0, rootPath.lastIndexOf('/')));
            return null;
        } else {
            List<ComboBoxResponse> result = new ArrayList<ComboBoxResponse>();
            int i;
            for (String aux : lruta) {
                if (aux != null) {
                    ComboBoxResponse add = new ComboBoxResponse();
                    if ((i = aux.indexOf('/', 1)) > 0) {
                        add.setId(rootPath + aux.substring(0, i) + "/");
                        add.setValue(aux.substring(0, i));
                    } else {
                        Recurso recurso = this.findByIdRecurso(Long.parseLong(aux)).get(0);
                        add.setId(recurso.getIdRecurso() + "");
                        add.setValue(recurso.getNr());
                    }
                    if (!result.contains(add)) {
                        result.add(add);
                    }
                }
            }
            if (result.size() == 1) {
                List<ComboBoxResponse> aux = this.findRecusoByPath(result.get(0).getId());
                if (aux != null) {
                    return aux;
                } else {
                    return result;
                }
            } else {
                return result;
            }
        }
    }

    /**
     * Devuelve la lista con el siguiente nivel de las rutas hijas distintas
     * que comienzan por rootPath
     * @param rootPath
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<ComboBoxResponse> findRecusoByLocalizador(String rootPath) {
        String SQL = "SELECT DISTINCT(SUBSTRING(r.localizador," + (rootPath.length() + 1) + ")) FROM Recurso r WHERE r.localizador LIKE :path AND r.tipoRecurso = 'CAMA' AND r.ubicacionRecurso='HOS'";
        //String SQL="SELECT LOCATE(r.ruta,'/',"+(rootPath.length()+2)+") FROM Recurso r WHERE r.ruta LIKE :path";
        List<String> lruta = this.entityManager.createQuery(SQL).setParameter("path", rootPath + "%").getResultList();
        if (lruta != null && lruta.isEmpty()) {
            //return this.findRecusoByPath(rootPath.substring(0, rootPath.lastIndexOf('/')));
            return null;
        } else {
            List<ComboBoxResponse> result = new ArrayList<ComboBoxResponse>();
            int i;
            for (String aux : lruta) {
                if (aux != null) {
                    ComboBoxResponse add = new ComboBoxResponse();
                    if ((i = aux.indexOf('/', 1)) > 0) {
                        add.setId(rootPath + aux.substring(0, i) + "/");
                        add.setValue(aux.substring(0, i));
                    }/* else {
                    Recurso recurso = this.findByIdRecurso(Long.parseLong(aux)).get(0);
                    add.setId(recurso.getIdRecurso() + "");
                    add.setValue(recurso.getNr());
                    }*/
                    if (!result.contains(add)) {
                        result.add(add);
                    }
                }
            }
            if (result.size() == 1) {
                List<ComboBoxResponse> aux = this.findRecusoByPath(result.get(0).getId());
                if (aux != null) {
                    return aux;
                } else {
                    return result;
                }
            } else {
                return result;
            }
        }
    }

    /**
     * Devuelve los datos de los recursos que se muestran en el mapa de camas
     * @param rootPath
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<BedDataView> findRecursoDataViewByLocalizador(String localizador) {

        Date now = new Date();
        Long anio, time, nowtime, edadtime, year;
        String namePatient = "", surname1 = "", surname2 = "";
        String SQL = "SELECT r FROM Recurso r WHERE r.localizador LIKE :localizador AND r.tipoRecurso = 'CAMA' AND r.ubicacionRecurso='HOS' ORDER BY r.nr";
        List<Recurso> aux = this.entityManager.createQuery(SQL).setParameter("localizador", localizador + "%").getResultList();
        List<BedDataView> result = new ArrayList<BedDataView>();
        if (aux != null) {
            BedDataView aux2;
            Patient aux3;
            for (Recurso r : aux) {
                aux2 = new BedDataView();
                if (r.getPatientList() != null && !r.getPatientList().isEmpty()) {
                    aux3 = r.getPatientList().get(0);
                } else {
                    aux3 = null;
                }

                if (aux3 == null) {
                    aux2.setGenre(0);
                    aux2.setNamePatient("");
                    aux2.setSurname1("");
                    aux2.setSurname2("");
                    aux2.setAge(0);
                    aux2.setInbed(false);
                } else {
                    aux2.setInbed(true);
                    if (aux3.getGenre() != null) {
                        aux2.setGenre(aux3.getGenre().getId());
                    } else {
                        aux2.setGenre(0);
                    }

                    if (aux3.getName() != null) {
                        namePatient = aux3.getName();
                        if (namePatient.length() > 45) {
                            namePatient = namePatient.substring(0, 44);
                            aux2.setNamePatient(namePatient);
                        } else {
                            aux2.setNamePatient(namePatient);
                        }
                    } else {
                        aux2.setNamePatient("");
                    }


                    if (aux3.getSurname1() != null) {
                        surname1 = aux3.getSurname1();
                        if (surname1.length() > 45) {
                            surname1 = surname1.substring(0, 44);
                            aux2.setSurname1(surname1);
                        } else {
                            aux2.setSurname1(surname1);
                        }
                    } else {
                        aux2.setSurname1("");
                    }

                    if (aux3.getSurname2() != null) {
                        surname2 = aux3.getSurname2();
                        if (surname2.length() > 45) {
                            surname2 = surname2.substring(0, 44);
                            aux2.setSurname2(surname2);
                        } else {
                            aux2.setSurname2(surname2);
                        }
                    } else {
                        aux2.setSurname2("");
                    }

                    if (aux3.getBirthday() != null) {
                        anio = new Long("31536000000");
                        time = aux3.getBirthday().getTime();
                        nowtime = now.getTime();
                        edadtime = nowtime - time;
                        year = edadtime / anio;
                        aux2.setAge(year.intValue());
                    } else {
                        aux2.setAge(-1);
                    }
                }

                aux2.setIdBed(r.getIdRecurso().toString());
                aux2.setNameBed(r.getNr());

                result.add(aux2);
            }
        }
        return result;
    }
}
