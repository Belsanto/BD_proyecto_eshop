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
import com.entities.Usuario;
import com.entities.Subasta;
import com.entities.SubastaUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author EQUIPO
 */
public class SubastaUsuarioJpaController implements Serializable {

    public SubastaUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public SubastaUsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SubastaUsuario subastaUsuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioSubastaCodigo = subastaUsuario.getUsuarioSubastaCodigo();
            if (usuarioSubastaCodigo != null) {
                usuarioSubastaCodigo = em.getReference(usuarioSubastaCodigo.getClass(), usuarioSubastaCodigo.getCodigo());
                subastaUsuario.setUsuarioSubastaCodigo(usuarioSubastaCodigo);
            }
            Subasta subastaProductoCodigo = subastaUsuario.getSubastaProductoCodigo();
            if (subastaProductoCodigo != null) {
                subastaProductoCodigo = em.getReference(subastaProductoCodigo.getClass(), subastaProductoCodigo.getCodigo());
                subastaUsuario.setSubastaProductoCodigo(subastaProductoCodigo);
            }
            em.persist(subastaUsuario);
            if (usuarioSubastaCodigo != null) {
                usuarioSubastaCodigo.getSubastaUsuarioList().add(subastaUsuario);
                usuarioSubastaCodigo = em.merge(usuarioSubastaCodigo);
            }
            if (subastaProductoCodigo != null) {
                subastaProductoCodigo.getSubastaUsuarioList().add(subastaUsuario);
                subastaProductoCodigo = em.merge(subastaProductoCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubastaUsuario subastaUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubastaUsuario persistentSubastaUsuario = em.find(SubastaUsuario.class, subastaUsuario.getCodigo());
            Usuario usuarioSubastaCodigoOld = persistentSubastaUsuario.getUsuarioSubastaCodigo();
            Usuario usuarioSubastaCodigoNew = subastaUsuario.getUsuarioSubastaCodigo();
            Subasta subastaProductoCodigoOld = persistentSubastaUsuario.getSubastaProductoCodigo();
            Subasta subastaProductoCodigoNew = subastaUsuario.getSubastaProductoCodigo();
            if (usuarioSubastaCodigoNew != null) {
                usuarioSubastaCodigoNew = em.getReference(usuarioSubastaCodigoNew.getClass(), usuarioSubastaCodigoNew.getCodigo());
                subastaUsuario.setUsuarioSubastaCodigo(usuarioSubastaCodigoNew);
            }
            if (subastaProductoCodigoNew != null) {
                subastaProductoCodigoNew = em.getReference(subastaProductoCodigoNew.getClass(), subastaProductoCodigoNew.getCodigo());
                subastaUsuario.setSubastaProductoCodigo(subastaProductoCodigoNew);
            }
            subastaUsuario = em.merge(subastaUsuario);
            if (usuarioSubastaCodigoOld != null && !usuarioSubastaCodigoOld.equals(usuarioSubastaCodigoNew)) {
                usuarioSubastaCodigoOld.getSubastaUsuarioList().remove(subastaUsuario);
                usuarioSubastaCodigoOld = em.merge(usuarioSubastaCodigoOld);
            }
            if (usuarioSubastaCodigoNew != null && !usuarioSubastaCodigoNew.equals(usuarioSubastaCodigoOld)) {
                usuarioSubastaCodigoNew.getSubastaUsuarioList().add(subastaUsuario);
                usuarioSubastaCodigoNew = em.merge(usuarioSubastaCodigoNew);
            }
            if (subastaProductoCodigoOld != null && !subastaProductoCodigoOld.equals(subastaProductoCodigoNew)) {
                subastaProductoCodigoOld.getSubastaUsuarioList().remove(subastaUsuario);
                subastaProductoCodigoOld = em.merge(subastaProductoCodigoOld);
            }
            if (subastaProductoCodigoNew != null && !subastaProductoCodigoNew.equals(subastaProductoCodigoOld)) {
                subastaProductoCodigoNew.getSubastaUsuarioList().add(subastaUsuario);
                subastaProductoCodigoNew = em.merge(subastaProductoCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subastaUsuario.getCodigo();
                if (findSubastaUsuario(id) == null) {
                    throw new NonexistentEntityException("The subastaUsuario with id " + id + " no longer exists.");
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
            SubastaUsuario subastaUsuario;
            try {
                subastaUsuario = em.getReference(SubastaUsuario.class, id);
                subastaUsuario.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subastaUsuario with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioSubastaCodigo = subastaUsuario.getUsuarioSubastaCodigo();
            if (usuarioSubastaCodigo != null) {
                usuarioSubastaCodigo.getSubastaUsuarioList().remove(subastaUsuario);
                usuarioSubastaCodigo = em.merge(usuarioSubastaCodigo);
            }
            Subasta subastaProductoCodigo = subastaUsuario.getSubastaProductoCodigo();
            if (subastaProductoCodigo != null) {
                subastaProductoCodigo.getSubastaUsuarioList().remove(subastaUsuario);
                subastaProductoCodigo = em.merge(subastaProductoCodigo);
            }
            em.remove(subastaUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SubastaUsuario> findSubastaUsuarioEntities() {
        return findSubastaUsuarioEntities(true, -1, -1);
    }

    public List<SubastaUsuario> findSubastaUsuarioEntities(int maxResults, int firstResult) {
        return findSubastaUsuarioEntities(false, maxResults, firstResult);
    }

    private List<SubastaUsuario> findSubastaUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubastaUsuario.class));
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

    public SubastaUsuario findSubastaUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubastaUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubastaUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubastaUsuario> rt = cq.from(SubastaUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
