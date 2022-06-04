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
import com.entities.Cartera;
import com.entities.Puntos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class PuntosJpaController implements Serializable {

    public PuntosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PuntosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puntos puntos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartera carteraCodigo = puntos.getCarteraCodigo();
            if (carteraCodigo != null) {
                carteraCodigo = em.getReference(carteraCodigo.getClass(), carteraCodigo.getCodigo());
                puntos.setCarteraCodigo(carteraCodigo);
            }
            em.persist(puntos);
            if (carteraCodigo != null) {
                carteraCodigo.getPuntosList().add(puntos);
                carteraCodigo = em.merge(carteraCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puntos puntos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puntos persistentPuntos = em.find(Puntos.class, puntos.getCodigo());
            Cartera carteraCodigoOld = persistentPuntos.getCarteraCodigo();
            Cartera carteraCodigoNew = puntos.getCarteraCodigo();
            if (carteraCodigoNew != null) {
                carteraCodigoNew = em.getReference(carteraCodigoNew.getClass(), carteraCodigoNew.getCodigo());
                puntos.setCarteraCodigo(carteraCodigoNew);
            }
            puntos = em.merge(puntos);
            if (carteraCodigoOld != null && !carteraCodigoOld.equals(carteraCodigoNew)) {
                carteraCodigoOld.getPuntosList().remove(puntos);
                carteraCodigoOld = em.merge(carteraCodigoOld);
            }
            if (carteraCodigoNew != null && !carteraCodigoNew.equals(carteraCodigoOld)) {
                carteraCodigoNew.getPuntosList().add(puntos);
                carteraCodigoNew = em.merge(carteraCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puntos.getCodigo();
                if (findPuntos(id) == null) {
                    throw new NonexistentEntityException("The puntos with id " + id + " no longer exists.");
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
            Puntos puntos;
            try {
                puntos = em.getReference(Puntos.class, id);
                puntos.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntos with id " + id + " no longer exists.", enfe);
            }
            Cartera carteraCodigo = puntos.getCarteraCodigo();
            if (carteraCodigo != null) {
                carteraCodigo.getPuntosList().remove(puntos);
                carteraCodigo = em.merge(carteraCodigo);
            }
            em.remove(puntos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puntos> findPuntosEntities() {
        return findPuntosEntities(true, -1, -1);
    }

    public List<Puntos> findPuntosEntities(int maxResults, int firstResult) {
        return findPuntosEntities(false, maxResults, firstResult);
    }

    private List<Puntos> findPuntosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Puntos.class));
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

    public Puntos findPuntos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puntos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Puntos> rt = cq.from(Puntos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
