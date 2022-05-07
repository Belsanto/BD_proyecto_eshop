/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Comentario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Producto;
import com.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author EQUIPO
 */
public class ComentarioJpaController implements Serializable {

    public ComentarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ComentarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentario comentario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productocCodigo = comentario.getProductocCodigo();
            if (productocCodigo != null) {
                productocCodigo = em.getReference(productocCodigo.getClass(), productocCodigo.getCodigo());
                comentario.setProductocCodigo(productocCodigo);
            }
            Usuario userComentCodigo = comentario.getUserComentCodigo();
            if (userComentCodigo != null) {
                userComentCodigo = em.getReference(userComentCodigo.getClass(), userComentCodigo.getCodigo());
                comentario.setUserComentCodigo(userComentCodigo);
            }
            em.persist(comentario);
            if (productocCodigo != null) {
                productocCodigo.getComentarioList().add(comentario);
                productocCodigo = em.merge(productocCodigo);
            }
            if (userComentCodigo != null) {
                userComentCodigo.getComentarioList().add(comentario);
                userComentCodigo = em.merge(userComentCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario persistentComentario = em.find(Comentario.class, comentario.getCodigo());
            Producto productocCodigoOld = persistentComentario.getProductocCodigo();
            Producto productocCodigoNew = comentario.getProductocCodigo();
            Usuario userComentCodigoOld = persistentComentario.getUserComentCodigo();
            Usuario userComentCodigoNew = comentario.getUserComentCodigo();
            if (productocCodigoNew != null) {
                productocCodigoNew = em.getReference(productocCodigoNew.getClass(), productocCodigoNew.getCodigo());
                comentario.setProductocCodigo(productocCodigoNew);
            }
            if (userComentCodigoNew != null) {
                userComentCodigoNew = em.getReference(userComentCodigoNew.getClass(), userComentCodigoNew.getCodigo());
                comentario.setUserComentCodigo(userComentCodigoNew);
            }
            comentario = em.merge(comentario);
            if (productocCodigoOld != null && !productocCodigoOld.equals(productocCodigoNew)) {
                productocCodigoOld.getComentarioList().remove(comentario);
                productocCodigoOld = em.merge(productocCodigoOld);
            }
            if (productocCodigoNew != null && !productocCodigoNew.equals(productocCodigoOld)) {
                productocCodigoNew.getComentarioList().add(comentario);
                productocCodigoNew = em.merge(productocCodigoNew);
            }
            if (userComentCodigoOld != null && !userComentCodigoOld.equals(userComentCodigoNew)) {
                userComentCodigoOld.getComentarioList().remove(comentario);
                userComentCodigoOld = em.merge(userComentCodigoOld);
            }
            if (userComentCodigoNew != null && !userComentCodigoNew.equals(userComentCodigoOld)) {
                userComentCodigoNew.getComentarioList().add(comentario);
                userComentCodigoNew = em.merge(userComentCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentario.getCodigo();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
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
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            Producto productocCodigo = comentario.getProductocCodigo();
            if (productocCodigo != null) {
                productocCodigo.getComentarioList().remove(comentario);
                productocCodigo = em.merge(productocCodigo);
            }
            Usuario userComentCodigo = comentario.getUserComentCodigo();
            if (userComentCodigo != null) {
                userComentCodigo.getComentarioList().remove(comentario);
                userComentCodigo = em.merge(userComentCodigo);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
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

    public Comentario findComentario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
