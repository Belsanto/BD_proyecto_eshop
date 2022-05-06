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
import com.entities.Canje;
import com.entities.DetalleCanje;
import com.entities.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class DetalleCanjeJpaController implements Serializable {

    public DetalleCanjeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCanje detalleCanje) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canje canjeCodigo = detalleCanje.getCanjeCodigo();
            if (canjeCodigo != null) {
                canjeCodigo = em.getReference(canjeCodigo.getClass(), canjeCodigo.getCodigo());
                detalleCanje.setCanjeCodigo(canjeCodigo);
            }
            Producto productoCanjeCodigo = detalleCanje.getProductoCanjeCodigo();
            if (productoCanjeCodigo != null) {
                productoCanjeCodigo = em.getReference(productoCanjeCodigo.getClass(), productoCanjeCodigo.getCodigo());
                detalleCanje.setProductoCanjeCodigo(productoCanjeCodigo);
            }
            em.persist(detalleCanje);
            if (canjeCodigo != null) {
                canjeCodigo.getDetalleCanjeList().add(detalleCanje);
                canjeCodigo = em.merge(canjeCodigo);
            }
            if (productoCanjeCodigo != null) {
                productoCanjeCodigo.getDetalleCanjeList().add(detalleCanje);
                productoCanjeCodigo = em.merge(productoCanjeCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCanje detalleCanje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCanje persistentDetalleCanje = em.find(DetalleCanje.class, detalleCanje.getCodigo());
            Canje canjeCodigoOld = persistentDetalleCanje.getCanjeCodigo();
            Canje canjeCodigoNew = detalleCanje.getCanjeCodigo();
            Producto productoCanjeCodigoOld = persistentDetalleCanje.getProductoCanjeCodigo();
            Producto productoCanjeCodigoNew = detalleCanje.getProductoCanjeCodigo();
            if (canjeCodigoNew != null) {
                canjeCodigoNew = em.getReference(canjeCodigoNew.getClass(), canjeCodigoNew.getCodigo());
                detalleCanje.setCanjeCodigo(canjeCodigoNew);
            }
            if (productoCanjeCodigoNew != null) {
                productoCanjeCodigoNew = em.getReference(productoCanjeCodigoNew.getClass(), productoCanjeCodigoNew.getCodigo());
                detalleCanje.setProductoCanjeCodigo(productoCanjeCodigoNew);
            }
            detalleCanje = em.merge(detalleCanje);
            if (canjeCodigoOld != null && !canjeCodigoOld.equals(canjeCodigoNew)) {
                canjeCodigoOld.getDetalleCanjeList().remove(detalleCanje);
                canjeCodigoOld = em.merge(canjeCodigoOld);
            }
            if (canjeCodigoNew != null && !canjeCodigoNew.equals(canjeCodigoOld)) {
                canjeCodigoNew.getDetalleCanjeList().add(detalleCanje);
                canjeCodigoNew = em.merge(canjeCodigoNew);
            }
            if (productoCanjeCodigoOld != null && !productoCanjeCodigoOld.equals(productoCanjeCodigoNew)) {
                productoCanjeCodigoOld.getDetalleCanjeList().remove(detalleCanje);
                productoCanjeCodigoOld = em.merge(productoCanjeCodigoOld);
            }
            if (productoCanjeCodigoNew != null && !productoCanjeCodigoNew.equals(productoCanjeCodigoOld)) {
                productoCanjeCodigoNew.getDetalleCanjeList().add(detalleCanje);
                productoCanjeCodigoNew = em.merge(productoCanjeCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCanje.getCodigo();
                if (findDetalleCanje(id) == null) {
                    throw new NonexistentEntityException("The detalleCanje with id " + id + " no longer exists.");
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
            DetalleCanje detalleCanje;
            try {
                detalleCanje = em.getReference(DetalleCanje.class, id);
                detalleCanje.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCanje with id " + id + " no longer exists.", enfe);
            }
            Canje canjeCodigo = detalleCanje.getCanjeCodigo();
            if (canjeCodigo != null) {
                canjeCodigo.getDetalleCanjeList().remove(detalleCanje);
                canjeCodigo = em.merge(canjeCodigo);
            }
            Producto productoCanjeCodigo = detalleCanje.getProductoCanjeCodigo();
            if (productoCanjeCodigo != null) {
                productoCanjeCodigo.getDetalleCanjeList().remove(detalleCanje);
                productoCanjeCodigo = em.merge(productoCanjeCodigo);
            }
            em.remove(detalleCanje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCanje> findDetalleCanjeEntities() {
        return findDetalleCanjeEntities(true, -1, -1);
    }

    public List<DetalleCanje> findDetalleCanjeEntities(int maxResults, int firstResult) {
        return findDetalleCanjeEntities(false, maxResults, firstResult);
    }

    private List<DetalleCanje> findDetalleCanjeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCanje.class));
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

    public DetalleCanje findDetalleCanje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCanje.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCanjeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCanje> rt = cq.from(DetalleCanje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
