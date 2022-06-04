/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Canje;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class CanjeJpaController implements Serializable {

    public CanjeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CanjeJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Canje canje) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario clienteCodigo = canje.getClienteCodigo();
            if (clienteCodigo != null) {
                clienteCodigo = em.getReference(clienteCodigo.getClass(), clienteCodigo.getCodigo());
                canje.setClienteCodigo(clienteCodigo);
            }
            em.persist(canje);
            if (clienteCodigo != null) {
                clienteCodigo.getCanjeList().add(canje);
                clienteCodigo = em.merge(clienteCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Canje canje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canje persistentCanje = em.find(Canje.class, canje.getCodigo());
            Usuario clienteCodigoOld = persistentCanje.getClienteCodigo();
            Usuario clienteCodigoNew = canje.getClienteCodigo();
            if (clienteCodigoNew != null) {
                clienteCodigoNew = em.getReference(clienteCodigoNew.getClass(), clienteCodigoNew.getCodigo());
                canje.setClienteCodigo(clienteCodigoNew);
            }
            canje = em.merge(canje);
            if (clienteCodigoOld != null && !clienteCodigoOld.equals(clienteCodigoNew)) {
                clienteCodigoOld.getCanjeList().remove(canje);
                clienteCodigoOld = em.merge(clienteCodigoOld);
            }
            if (clienteCodigoNew != null && !clienteCodigoNew.equals(clienteCodigoOld)) {
                clienteCodigoNew.getCanjeList().add(canje);
                clienteCodigoNew = em.merge(clienteCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = canje.getCodigo();
                if (findCanje(id) == null) {
                    throw new NonexistentEntityException("The canje with id " + id + " no longer exists.");
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
            Canje canje;
            try {
                canje = em.getReference(Canje.class, id);
                canje.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canje with id " + id + " no longer exists.", enfe);
            }
            Usuario clienteCodigo = canje.getClienteCodigo();
            if (clienteCodigo != null) {
                clienteCodigo.getCanjeList().remove(canje);
                clienteCodigo = em.merge(clienteCodigo);
            }
            em.remove(canje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Canje> findCanjeEntities() {
        return findCanjeEntities(true, -1, -1);
    }

    public List<Canje> findCanjeEntities(int maxResults, int firstResult) {
        return findCanjeEntities(false, maxResults, firstResult);
    }

    private List<Canje> findCanjeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Canje.class));
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

    public Canje findCanje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Canje.class, id);
        } finally {
            em.close();
        }
    }

    public int getCanjeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Canje> rt = cq.from(Canje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
