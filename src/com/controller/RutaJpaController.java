/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Departamento;
import com.entities.Delivery;
import com.entities.Ruta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class RutaJpaController implements Serializable {

    public RutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RutaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ruta ruta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamentoCodigo = ruta.getDepartamentoCodigo();
            if (departamentoCodigo != null) {
                departamentoCodigo = em.getReference(departamentoCodigo.getClass(), departamentoCodigo.getCodigo());
                ruta.setDepartamentoCodigo(departamentoCodigo);
            }
            Delivery empresaCodigo = ruta.getEmpresaCodigo();
            if (empresaCodigo != null) {
                empresaCodigo = em.getReference(empresaCodigo.getClass(), empresaCodigo.getCodigo());
                ruta.setEmpresaCodigo(empresaCodigo);
            }
            em.persist(ruta);
            if (departamentoCodigo != null) {
                departamentoCodigo.getRutaList().add(ruta);
                departamentoCodigo = em.merge(departamentoCodigo);
            }
            if (empresaCodigo != null) {
                empresaCodigo.getRutaList().add(ruta);
                empresaCodigo = em.merge(empresaCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ruta ruta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta persistentRuta = em.find(Ruta.class, ruta.getCodigo());
            Departamento departamentoCodigoOld = persistentRuta.getDepartamentoCodigo();
            Departamento departamentoCodigoNew = ruta.getDepartamentoCodigo();
            Delivery empresaCodigoOld = persistentRuta.getEmpresaCodigo();
            Delivery empresaCodigoNew = ruta.getEmpresaCodigo();
            if (departamentoCodigoNew != null) {
                departamentoCodigoNew = em.getReference(departamentoCodigoNew.getClass(), departamentoCodigoNew.getCodigo());
                ruta.setDepartamentoCodigo(departamentoCodigoNew);
            }
            if (empresaCodigoNew != null) {
                empresaCodigoNew = em.getReference(empresaCodigoNew.getClass(), empresaCodigoNew.getCodigo());
                ruta.setEmpresaCodigo(empresaCodigoNew);
            }
            ruta = em.merge(ruta);
            if (departamentoCodigoOld != null && !departamentoCodigoOld.equals(departamentoCodigoNew)) {
                departamentoCodigoOld.getRutaList().remove(ruta);
                departamentoCodigoOld = em.merge(departamentoCodigoOld);
            }
            if (departamentoCodigoNew != null && !departamentoCodigoNew.equals(departamentoCodigoOld)) {
                departamentoCodigoNew.getRutaList().add(ruta);
                departamentoCodigoNew = em.merge(departamentoCodigoNew);
            }
            if (empresaCodigoOld != null && !empresaCodigoOld.equals(empresaCodigoNew)) {
                empresaCodigoOld.getRutaList().remove(ruta);
                empresaCodigoOld = em.merge(empresaCodigoOld);
            }
            if (empresaCodigoNew != null && !empresaCodigoNew.equals(empresaCodigoOld)) {
                empresaCodigoNew.getRutaList().add(ruta);
                empresaCodigoNew = em.merge(empresaCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ruta.getCodigo();
                if (findRuta(id) == null) {
                    throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta ruta;
            try {
                ruta = em.getReference(Ruta.class, id);
                ruta.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.", enfe);
            }
            Departamento departamentoCodigo = ruta.getDepartamentoCodigo();
            if (departamentoCodigo != null) {
                departamentoCodigo.getRutaList().remove(ruta);
                departamentoCodigo = em.merge(departamentoCodigo);
            }
            Delivery empresaCodigo = ruta.getEmpresaCodigo();
            if (empresaCodigo != null) {
                empresaCodigo.getRutaList().remove(ruta);
                empresaCodigo = em.merge(empresaCodigo);
            }
            em.remove(ruta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ruta> findRutaEntities() {
        return findRutaEntities(true, -1, -1);
    }

    public List<Ruta> findRutaEntities(int maxResults, int firstResult) {
        return findRutaEntities(false, maxResults, firstResult);
    }

    private List<Ruta> findRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ruta.class));
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

    public Ruta findRuta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ruta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ruta> rt = cq.from(Ruta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
