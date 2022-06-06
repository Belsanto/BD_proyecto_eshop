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
        if (ciudad.getDepartamentoCollection() == null) {
            ciudad.setDepartamentoCollection(new ArrayList<Departamento>());
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
            Collection<Departamento> attachedDepartamentoCollection = new ArrayList<Departamento>();
            for (Departamento departamentoCollectionDepartamentoToAttach : ciudad.getDepartamentoCollection()) {
                departamentoCollectionDepartamentoToAttach = em.getReference(departamentoCollectionDepartamentoToAttach.getClass(), departamentoCollectionDepartamentoToAttach.getCodigo());
                attachedDepartamentoCollection.add(departamentoCollectionDepartamentoToAttach);
            }
            ciudad.setDepartamentoCollection(attachedDepartamentoCollection);
            em.persist(ciudad);
            if (paisCodigo != null) {
                paisCodigo.getCiudadCollection().add(ciudad);
                paisCodigo = em.merge(paisCodigo);
            }
            for (Departamento departamentoCollectionDepartamento : ciudad.getDepartamentoCollection()) {
                Ciudad oldCiudadCodigoOfDepartamentoCollectionDepartamento = departamentoCollectionDepartamento.getCiudadCodigo();
                departamentoCollectionDepartamento.setCiudadCodigo(ciudad);
                departamentoCollectionDepartamento = em.merge(departamentoCollectionDepartamento);
                if (oldCiudadCodigoOfDepartamentoCollectionDepartamento != null) {
                    oldCiudadCodigoOfDepartamentoCollectionDepartamento.getDepartamentoCollection().remove(departamentoCollectionDepartamento);
                    oldCiudadCodigoOfDepartamentoCollectionDepartamento = em.merge(oldCiudadCodigoOfDepartamentoCollectionDepartamento);
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
            Collection<Departamento> departamentoCollectionOld = persistentCiudad.getDepartamentoCollection();
            Collection<Departamento> departamentoCollectionNew = ciudad.getDepartamentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Departamento departamentoCollectionOldDepartamento : departamentoCollectionOld) {
                if (!departamentoCollectionNew.contains(departamentoCollectionOldDepartamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Departamento " + departamentoCollectionOldDepartamento + " since its ciudadCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisCodigoNew != null) {
                paisCodigoNew = em.getReference(paisCodigoNew.getClass(), paisCodigoNew.getCodigo());
                ciudad.setPaisCodigo(paisCodigoNew);
            }
            Collection<Departamento> attachedDepartamentoCollectionNew = new ArrayList<Departamento>();
            for (Departamento departamentoCollectionNewDepartamentoToAttach : departamentoCollectionNew) {
                departamentoCollectionNewDepartamentoToAttach = em.getReference(departamentoCollectionNewDepartamentoToAttach.getClass(), departamentoCollectionNewDepartamentoToAttach.getCodigo());
                attachedDepartamentoCollectionNew.add(departamentoCollectionNewDepartamentoToAttach);
            }
            departamentoCollectionNew = attachedDepartamentoCollectionNew;
            ciudad.setDepartamentoCollection(departamentoCollectionNew);
            ciudad = em.merge(ciudad);
            if (paisCodigoOld != null && !paisCodigoOld.equals(paisCodigoNew)) {
                paisCodigoOld.getCiudadCollection().remove(ciudad);
                paisCodigoOld = em.merge(paisCodigoOld);
            }
            if (paisCodigoNew != null && !paisCodigoNew.equals(paisCodigoOld)) {
                paisCodigoNew.getCiudadCollection().add(ciudad);
                paisCodigoNew = em.merge(paisCodigoNew);
            }
            for (Departamento departamentoCollectionNewDepartamento : departamentoCollectionNew) {
                if (!departamentoCollectionOld.contains(departamentoCollectionNewDepartamento)) {
                    Ciudad oldCiudadCodigoOfDepartamentoCollectionNewDepartamento = departamentoCollectionNewDepartamento.getCiudadCodigo();
                    departamentoCollectionNewDepartamento.setCiudadCodigo(ciudad);
                    departamentoCollectionNewDepartamento = em.merge(departamentoCollectionNewDepartamento);
                    if (oldCiudadCodigoOfDepartamentoCollectionNewDepartamento != null && !oldCiudadCodigoOfDepartamentoCollectionNewDepartamento.equals(ciudad)) {
                        oldCiudadCodigoOfDepartamentoCollectionNewDepartamento.getDepartamentoCollection().remove(departamentoCollectionNewDepartamento);
                        oldCiudadCodigoOfDepartamentoCollectionNewDepartamento = em.merge(oldCiudadCodigoOfDepartamentoCollectionNewDepartamento);
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
            Collection<Departamento> departamentoCollectionOrphanCheck = ciudad.getDepartamentoCollection();
            for (Departamento departamentoCollectionOrphanCheckDepartamento : departamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Departamento " + departamentoCollectionOrphanCheckDepartamento + " in its departamentoCollection field has a non-nullable ciudadCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisCodigo = ciudad.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo.getCiudadCollection().remove(ciudad);
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
