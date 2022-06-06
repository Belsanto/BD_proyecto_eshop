/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Ciudad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Pais;
import com.entities.Departamento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CiudadJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) {
        if (ciudad.getDepartamentoList() == null) {
            ciudad.setDepartamentoList(new ArrayList<Departamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisCodigo = ciudad.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo = em.getReference(paisCodigo.getClass(), paisCodigo.getCodigo());
                ciudad.setPaisCodigo(paisCodigo);
            }
            List<Departamento> attachedDepartamentoList = new ArrayList<Departamento>();
            for (Departamento departamentoListDepartamentoToAttach : ciudad.getDepartamentoList()) {
                departamentoListDepartamentoToAttach = em.getReference(departamentoListDepartamentoToAttach.getClass(), departamentoListDepartamentoToAttach.getCodigo());
                attachedDepartamentoList.add(departamentoListDepartamentoToAttach);
            }
            ciudad.setDepartamentoList(attachedDepartamentoList);
            em.persist(ciudad);
            if (paisCodigo != null) {
                paisCodigo.getCiudadList().add(ciudad);
                paisCodigo = em.merge(paisCodigo);
            }
            for (Departamento departamentoListDepartamento : ciudad.getDepartamentoList()) {
                Ciudad oldCiudadCodigoOfDepartamentoListDepartamento = departamentoListDepartamento.getCiudadCodigo();
                departamentoListDepartamento.setCiudadCodigo(ciudad);
                departamentoListDepartamento = em.merge(departamentoListDepartamento);
                if (oldCiudadCodigoOfDepartamentoListDepartamento != null) {
                    oldCiudadCodigoOfDepartamentoListDepartamento.getDepartamentoList().remove(departamentoListDepartamento);
                    oldCiudadCodigoOfDepartamentoListDepartamento = em.merge(oldCiudadCodigoOfDepartamentoListDepartamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getCodigo());
            Pais paisCodigoOld = persistentCiudad.getPaisCodigo();
            Pais paisCodigoNew = ciudad.getPaisCodigo();
            List<Departamento> departamentoListOld = persistentCiudad.getDepartamentoList();
            List<Departamento> departamentoListNew = ciudad.getDepartamentoList();
            List<String> illegalOrphanMessages = null;
            for (Departamento departamentoListOldDepartamento : departamentoListOld) {
                if (!departamentoListNew.contains(departamentoListOldDepartamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Departamento " + departamentoListOldDepartamento + " since its ciudadCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisCodigoNew != null) {
                paisCodigoNew = em.getReference(paisCodigoNew.getClass(), paisCodigoNew.getCodigo());
                ciudad.setPaisCodigo(paisCodigoNew);
            }
            List<Departamento> attachedDepartamentoListNew = new ArrayList<Departamento>();
            for (Departamento departamentoListNewDepartamentoToAttach : departamentoListNew) {
                departamentoListNewDepartamentoToAttach = em.getReference(departamentoListNewDepartamentoToAttach.getClass(), departamentoListNewDepartamentoToAttach.getCodigo());
                attachedDepartamentoListNew.add(departamentoListNewDepartamentoToAttach);
            }
            departamentoListNew = attachedDepartamentoListNew;
            ciudad.setDepartamentoList(departamentoListNew);
            ciudad = em.merge(ciudad);
            if (paisCodigoOld != null && !paisCodigoOld.equals(paisCodigoNew)) {
                paisCodigoOld.getCiudadList().remove(ciudad);
                paisCodigoOld = em.merge(paisCodigoOld);
            }
            if (paisCodigoNew != null && !paisCodigoNew.equals(paisCodigoOld)) {
                paisCodigoNew.getCiudadList().add(ciudad);
                paisCodigoNew = em.merge(paisCodigoNew);
            }
            for (Departamento departamentoListNewDepartamento : departamentoListNew) {
                if (!departamentoListOld.contains(departamentoListNewDepartamento)) {
                    Ciudad oldCiudadCodigoOfDepartamentoListNewDepartamento = departamentoListNewDepartamento.getCiudadCodigo();
                    departamentoListNewDepartamento.setCiudadCodigo(ciudad);
                    departamentoListNewDepartamento = em.merge(departamentoListNewDepartamento);
                    if (oldCiudadCodigoOfDepartamentoListNewDepartamento != null && !oldCiudadCodigoOfDepartamentoListNewDepartamento.equals(ciudad)) {
                        oldCiudadCodigoOfDepartamentoListNewDepartamento.getDepartamentoList().remove(departamentoListNewDepartamento);
                        oldCiudadCodigoOfDepartamentoListNewDepartamento = em.merge(oldCiudadCodigoOfDepartamentoListNewDepartamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getCodigo();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Departamento> departamentoListOrphanCheck = ciudad.getDepartamentoList();
            for (Departamento departamentoListOrphanCheckDepartamento : departamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Departamento " + departamentoListOrphanCheckDepartamento + " in its departamentoList field has a non-nullable ciudadCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisCodigo = ciudad.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo.getCiudadList().remove(ciudad);
                paisCodigo = em.merge(paisCodigo);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
