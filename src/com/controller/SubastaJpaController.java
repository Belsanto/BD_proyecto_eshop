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
import com.entities.ProductoSubasta;
import com.entities.Subasta;
import com.entities.SubastaUsuario;
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
public class SubastaJpaController implements Serializable {

    public SubastaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SubastaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subasta subasta) {
        if (subasta.getSubastaUsuarioCollection() == null) {
            subasta.setSubastaUsuarioCollection(new ArrayList<SubastaUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoSubasta productoEnSubastaCodigo = subasta.getProductoEnSubastaCodigo();
            if (productoEnSubastaCodigo != null) {
                productoEnSubastaCodigo = em.getReference(productoEnSubastaCodigo.getClass(), productoEnSubastaCodigo.getCodigo());
                subasta.setProductoEnSubastaCodigo(productoEnSubastaCodigo);
            }
            Collection<SubastaUsuario> attachedSubastaUsuarioCollection = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioCollectionSubastaUsuarioToAttach : subasta.getSubastaUsuarioCollection()) {
                subastaUsuarioCollectionSubastaUsuarioToAttach = em.getReference(subastaUsuarioCollectionSubastaUsuarioToAttach.getClass(), subastaUsuarioCollectionSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioCollection.add(subastaUsuarioCollectionSubastaUsuarioToAttach);
            }
            subasta.setSubastaUsuarioCollection(attachedSubastaUsuarioCollection);
            em.persist(subasta);
            if (productoEnSubastaCodigo != null) {
                productoEnSubastaCodigo.getSubastaCollection().add(subasta);
                productoEnSubastaCodigo = em.merge(productoEnSubastaCodigo);
            }
            for (SubastaUsuario subastaUsuarioCollectionSubastaUsuario : subasta.getSubastaUsuarioCollection()) {
                Subasta oldSubastaProductoCodigoOfSubastaUsuarioCollectionSubastaUsuario = subastaUsuarioCollectionSubastaUsuario.getSubastaProductoCodigo();
                subastaUsuarioCollectionSubastaUsuario.setSubastaProductoCodigo(subasta);
                subastaUsuarioCollectionSubastaUsuario = em.merge(subastaUsuarioCollectionSubastaUsuario);
                if (oldSubastaProductoCodigoOfSubastaUsuarioCollectionSubastaUsuario != null) {
                    oldSubastaProductoCodigoOfSubastaUsuarioCollectionSubastaUsuario.getSubastaUsuarioCollection().remove(subastaUsuarioCollectionSubastaUsuario);
                    oldSubastaProductoCodigoOfSubastaUsuarioCollectionSubastaUsuario = em.merge(oldSubastaProductoCodigoOfSubastaUsuarioCollectionSubastaUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subasta subasta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subasta persistentSubasta = em.find(Subasta.class, subasta.getCodigo());
            ProductoSubasta productoEnSubastaCodigoOld = persistentSubasta.getProductoEnSubastaCodigo();
            ProductoSubasta productoEnSubastaCodigoNew = subasta.getProductoEnSubastaCodigo();
            Collection<SubastaUsuario> subastaUsuarioCollectionOld = persistentSubasta.getSubastaUsuarioCollection();
            Collection<SubastaUsuario> subastaUsuarioCollectionNew = subasta.getSubastaUsuarioCollection();
            if (productoEnSubastaCodigoNew != null) {
                productoEnSubastaCodigoNew = em.getReference(productoEnSubastaCodigoNew.getClass(), productoEnSubastaCodigoNew.getCodigo());
                subasta.setProductoEnSubastaCodigo(productoEnSubastaCodigoNew);
            }
            Collection<SubastaUsuario> attachedSubastaUsuarioCollectionNew = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioCollectionNewSubastaUsuarioToAttach : subastaUsuarioCollectionNew) {
                subastaUsuarioCollectionNewSubastaUsuarioToAttach = em.getReference(subastaUsuarioCollectionNewSubastaUsuarioToAttach.getClass(), subastaUsuarioCollectionNewSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioCollectionNew.add(subastaUsuarioCollectionNewSubastaUsuarioToAttach);
            }
            subastaUsuarioCollectionNew = attachedSubastaUsuarioCollectionNew;
            subasta.setSubastaUsuarioCollection(subastaUsuarioCollectionNew);
            subasta = em.merge(subasta);
            if (productoEnSubastaCodigoOld != null && !productoEnSubastaCodigoOld.equals(productoEnSubastaCodigoNew)) {
                productoEnSubastaCodigoOld.getSubastaCollection().remove(subasta);
                productoEnSubastaCodigoOld = em.merge(productoEnSubastaCodigoOld);
            }
            if (productoEnSubastaCodigoNew != null && !productoEnSubastaCodigoNew.equals(productoEnSubastaCodigoOld)) {
                productoEnSubastaCodigoNew.getSubastaCollection().add(subasta);
                productoEnSubastaCodigoNew = em.merge(productoEnSubastaCodigoNew);
            }
            for (SubastaUsuario subastaUsuarioCollectionOldSubastaUsuario : subastaUsuarioCollectionOld) {
                if (!subastaUsuarioCollectionNew.contains(subastaUsuarioCollectionOldSubastaUsuario)) {
                    subastaUsuarioCollectionOldSubastaUsuario.setSubastaProductoCodigo(null);
                    subastaUsuarioCollectionOldSubastaUsuario = em.merge(subastaUsuarioCollectionOldSubastaUsuario);
                }
            }
            for (SubastaUsuario subastaUsuarioCollectionNewSubastaUsuario : subastaUsuarioCollectionNew) {
                if (!subastaUsuarioCollectionOld.contains(subastaUsuarioCollectionNewSubastaUsuario)) {
                    Subasta oldSubastaProductoCodigoOfSubastaUsuarioCollectionNewSubastaUsuario = subastaUsuarioCollectionNewSubastaUsuario.getSubastaProductoCodigo();
                    subastaUsuarioCollectionNewSubastaUsuario.setSubastaProductoCodigo(subasta);
                    subastaUsuarioCollectionNewSubastaUsuario = em.merge(subastaUsuarioCollectionNewSubastaUsuario);
                    if (oldSubastaProductoCodigoOfSubastaUsuarioCollectionNewSubastaUsuario != null && !oldSubastaProductoCodigoOfSubastaUsuarioCollectionNewSubastaUsuario.equals(subasta)) {
                        oldSubastaProductoCodigoOfSubastaUsuarioCollectionNewSubastaUsuario.getSubastaUsuarioCollection().remove(subastaUsuarioCollectionNewSubastaUsuario);
                        oldSubastaProductoCodigoOfSubastaUsuarioCollectionNewSubastaUsuario = em.merge(oldSubastaProductoCodigoOfSubastaUsuarioCollectionNewSubastaUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subasta.getCodigo();
                if (findSubasta(id) == null) {
                    throw new NonexistentEntityException("The subasta with id " + id + " no longer exists.");
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
            Subasta subasta;
            try {
                subasta = em.getReference(Subasta.class, id);
                subasta.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subasta with id " + id + " no longer exists.", enfe);
            }
            ProductoSubasta productoEnSubastaCodigo = subasta.getProductoEnSubastaCodigo();
            if (productoEnSubastaCodigo != null) {
                productoEnSubastaCodigo.getSubastaCollection().remove(subasta);
                productoEnSubastaCodigo = em.merge(productoEnSubastaCodigo);
            }
            Collection<SubastaUsuario> subastaUsuarioCollection = subasta.getSubastaUsuarioCollection();
            for (SubastaUsuario subastaUsuarioCollectionSubastaUsuario : subastaUsuarioCollection) {
                subastaUsuarioCollectionSubastaUsuario.setSubastaProductoCodigo(null);
                subastaUsuarioCollectionSubastaUsuario = em.merge(subastaUsuarioCollectionSubastaUsuario);
            }
            em.remove(subasta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subasta> findSubastaEntities() {
        return findSubastaEntities(true, -1, -1);
    }

    public List<Subasta> findSubastaEntities(int maxResults, int firstResult) {
        return findSubastaEntities(false, maxResults, firstResult);
    }

    private List<Subasta> findSubastaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subasta.class));
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

    public Subasta findSubasta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subasta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubastaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subasta> rt = cq.from(Subasta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
