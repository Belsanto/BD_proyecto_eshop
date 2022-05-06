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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class SubastaJpaController implements Serializable {

    public SubastaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subasta subasta) {
        if (subasta.getSubastaUsuarioList() == null) {
            subasta.setSubastaUsuarioList(new ArrayList<SubastaUsuario>());
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
            List<SubastaUsuario> attachedSubastaUsuarioList = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioListSubastaUsuarioToAttach : subasta.getSubastaUsuarioList()) {
                subastaUsuarioListSubastaUsuarioToAttach = em.getReference(subastaUsuarioListSubastaUsuarioToAttach.getClass(), subastaUsuarioListSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioList.add(subastaUsuarioListSubastaUsuarioToAttach);
            }
            subasta.setSubastaUsuarioList(attachedSubastaUsuarioList);
            em.persist(subasta);
            if (productoEnSubastaCodigo != null) {
                productoEnSubastaCodigo.getSubastaList().add(subasta);
                productoEnSubastaCodigo = em.merge(productoEnSubastaCodigo);
            }
            for (SubastaUsuario subastaUsuarioListSubastaUsuario : subasta.getSubastaUsuarioList()) {
                Subasta oldSubastaProductoCodigoOfSubastaUsuarioListSubastaUsuario = subastaUsuarioListSubastaUsuario.getSubastaProductoCodigo();
                subastaUsuarioListSubastaUsuario.setSubastaProductoCodigo(subasta);
                subastaUsuarioListSubastaUsuario = em.merge(subastaUsuarioListSubastaUsuario);
                if (oldSubastaProductoCodigoOfSubastaUsuarioListSubastaUsuario != null) {
                    oldSubastaProductoCodigoOfSubastaUsuarioListSubastaUsuario.getSubastaUsuarioList().remove(subastaUsuarioListSubastaUsuario);
                    oldSubastaProductoCodigoOfSubastaUsuarioListSubastaUsuario = em.merge(oldSubastaProductoCodigoOfSubastaUsuarioListSubastaUsuario);
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
            List<SubastaUsuario> subastaUsuarioListOld = persistentSubasta.getSubastaUsuarioList();
            List<SubastaUsuario> subastaUsuarioListNew = subasta.getSubastaUsuarioList();
            if (productoEnSubastaCodigoNew != null) {
                productoEnSubastaCodigoNew = em.getReference(productoEnSubastaCodigoNew.getClass(), productoEnSubastaCodigoNew.getCodigo());
                subasta.setProductoEnSubastaCodigo(productoEnSubastaCodigoNew);
            }
            List<SubastaUsuario> attachedSubastaUsuarioListNew = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioListNewSubastaUsuarioToAttach : subastaUsuarioListNew) {
                subastaUsuarioListNewSubastaUsuarioToAttach = em.getReference(subastaUsuarioListNewSubastaUsuarioToAttach.getClass(), subastaUsuarioListNewSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioListNew.add(subastaUsuarioListNewSubastaUsuarioToAttach);
            }
            subastaUsuarioListNew = attachedSubastaUsuarioListNew;
            subasta.setSubastaUsuarioList(subastaUsuarioListNew);
            subasta = em.merge(subasta);
            if (productoEnSubastaCodigoOld != null && !productoEnSubastaCodigoOld.equals(productoEnSubastaCodigoNew)) {
                productoEnSubastaCodigoOld.getSubastaList().remove(subasta);
                productoEnSubastaCodigoOld = em.merge(productoEnSubastaCodigoOld);
            }
            if (productoEnSubastaCodigoNew != null && !productoEnSubastaCodigoNew.equals(productoEnSubastaCodigoOld)) {
                productoEnSubastaCodigoNew.getSubastaList().add(subasta);
                productoEnSubastaCodigoNew = em.merge(productoEnSubastaCodigoNew);
            }
            for (SubastaUsuario subastaUsuarioListOldSubastaUsuario : subastaUsuarioListOld) {
                if (!subastaUsuarioListNew.contains(subastaUsuarioListOldSubastaUsuario)) {
                    subastaUsuarioListOldSubastaUsuario.setSubastaProductoCodigo(null);
                    subastaUsuarioListOldSubastaUsuario = em.merge(subastaUsuarioListOldSubastaUsuario);
                }
            }
            for (SubastaUsuario subastaUsuarioListNewSubastaUsuario : subastaUsuarioListNew) {
                if (!subastaUsuarioListOld.contains(subastaUsuarioListNewSubastaUsuario)) {
                    Subasta oldSubastaProductoCodigoOfSubastaUsuarioListNewSubastaUsuario = subastaUsuarioListNewSubastaUsuario.getSubastaProductoCodigo();
                    subastaUsuarioListNewSubastaUsuario.setSubastaProductoCodigo(subasta);
                    subastaUsuarioListNewSubastaUsuario = em.merge(subastaUsuarioListNewSubastaUsuario);
                    if (oldSubastaProductoCodigoOfSubastaUsuarioListNewSubastaUsuario != null && !oldSubastaProductoCodigoOfSubastaUsuarioListNewSubastaUsuario.equals(subasta)) {
                        oldSubastaProductoCodigoOfSubastaUsuarioListNewSubastaUsuario.getSubastaUsuarioList().remove(subastaUsuarioListNewSubastaUsuario);
                        oldSubastaProductoCodigoOfSubastaUsuarioListNewSubastaUsuario = em.merge(oldSubastaProductoCodigoOfSubastaUsuarioListNewSubastaUsuario);
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
                productoEnSubastaCodigo.getSubastaList().remove(subasta);
                productoEnSubastaCodigo = em.merge(productoEnSubastaCodigo);
            }
            List<SubastaUsuario> subastaUsuarioList = subasta.getSubastaUsuarioList();
            for (SubastaUsuario subastaUsuarioListSubastaUsuario : subastaUsuarioList) {
                subastaUsuarioListSubastaUsuario.setSubastaProductoCodigo(null);
                subastaUsuarioListSubastaUsuario = em.merge(subastaUsuarioListSubastaUsuario);
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
