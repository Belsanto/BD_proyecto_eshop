/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import com.entities.Departamento;
import com.entities.ProductoSubasta;
import com.entities.Subasta;
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
public class ProductoSubastaJpaController implements Serializable {

    public ProductoSubastaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProductoSubastaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoSubasta productoSubasta) throws PreexistingEntityException, Exception {
        if (productoSubasta.getSubastaCollection() == null) {
            productoSubasta.setSubastaCollection(new ArrayList<Subasta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario vendedorCodigo = productoSubasta.getVendedorCodigo();
            if (vendedorCodigo != null) {
                vendedorCodigo = em.getReference(vendedorCodigo.getClass(), vendedorCodigo.getCodigo());
                productoSubasta.setVendedorCodigo(vendedorCodigo);
            }
            Departamento departamentoCodigo = productoSubasta.getDepartamentoCodigo();
            if (departamentoCodigo != null) {
                departamentoCodigo = em.getReference(departamentoCodigo.getClass(), departamentoCodigo.getCodigo());
                productoSubasta.setDepartamentoCodigo(departamentoCodigo);
            }
            Collection<Subasta> attachedSubastaCollection = new ArrayList<Subasta>();
            for (Subasta subastaCollectionSubastaToAttach : productoSubasta.getSubastaCollection()) {
                subastaCollectionSubastaToAttach = em.getReference(subastaCollectionSubastaToAttach.getClass(), subastaCollectionSubastaToAttach.getCodigo());
                attachedSubastaCollection.add(subastaCollectionSubastaToAttach);
            }
            productoSubasta.setSubastaCollection(attachedSubastaCollection);
            em.persist(productoSubasta);
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoSubastaCollection().add(productoSubasta);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            if (departamentoCodigo != null) {
                departamentoCodigo.getProductoSubastaCollection().add(productoSubasta);
                departamentoCodigo = em.merge(departamentoCodigo);
            }
            for (Subasta subastaCollectionSubasta : productoSubasta.getSubastaCollection()) {
                ProductoSubasta oldProductoEnSubastaCodigoOfSubastaCollectionSubasta = subastaCollectionSubasta.getProductoEnSubastaCodigo();
                subastaCollectionSubasta.setProductoEnSubastaCodigo(productoSubasta);
                subastaCollectionSubasta = em.merge(subastaCollectionSubasta);
                if (oldProductoEnSubastaCodigoOfSubastaCollectionSubasta != null) {
                    oldProductoEnSubastaCodigoOfSubastaCollectionSubasta.getSubastaCollection().remove(subastaCollectionSubasta);
                    oldProductoEnSubastaCodigoOfSubastaCollectionSubasta = em.merge(oldProductoEnSubastaCodigoOfSubastaCollectionSubasta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoSubasta(productoSubasta.getCodigo()) != null) {
                throw new PreexistingEntityException("ProductoSubasta " + productoSubasta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoSubasta productoSubasta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoSubasta persistentProductoSubasta = em.find(ProductoSubasta.class, productoSubasta.getCodigo());
            Usuario vendedorCodigoOld = persistentProductoSubasta.getVendedorCodigo();
            Usuario vendedorCodigoNew = productoSubasta.getVendedorCodigo();
            Departamento departamentoCodigoOld = persistentProductoSubasta.getDepartamentoCodigo();
            Departamento departamentoCodigoNew = productoSubasta.getDepartamentoCodigo();
            Collection<Subasta> subastaCollectionOld = persistentProductoSubasta.getSubastaCollection();
            Collection<Subasta> subastaCollectionNew = productoSubasta.getSubastaCollection();
            if (vendedorCodigoNew != null) {
                vendedorCodigoNew = em.getReference(vendedorCodigoNew.getClass(), vendedorCodigoNew.getCodigo());
                productoSubasta.setVendedorCodigo(vendedorCodigoNew);
            }
            if (departamentoCodigoNew != null) {
                departamentoCodigoNew = em.getReference(departamentoCodigoNew.getClass(), departamentoCodigoNew.getCodigo());
                productoSubasta.setDepartamentoCodigo(departamentoCodigoNew);
            }
            Collection<Subasta> attachedSubastaCollectionNew = new ArrayList<Subasta>();
            for (Subasta subastaCollectionNewSubastaToAttach : subastaCollectionNew) {
                subastaCollectionNewSubastaToAttach = em.getReference(subastaCollectionNewSubastaToAttach.getClass(), subastaCollectionNewSubastaToAttach.getCodigo());
                attachedSubastaCollectionNew.add(subastaCollectionNewSubastaToAttach);
            }
            subastaCollectionNew = attachedSubastaCollectionNew;
            productoSubasta.setSubastaCollection(subastaCollectionNew);
            productoSubasta = em.merge(productoSubasta);
            if (vendedorCodigoOld != null && !vendedorCodigoOld.equals(vendedorCodigoNew)) {
                vendedorCodigoOld.getProductoSubastaCollection().remove(productoSubasta);
                vendedorCodigoOld = em.merge(vendedorCodigoOld);
            }
            if (vendedorCodigoNew != null && !vendedorCodigoNew.equals(vendedorCodigoOld)) {
                vendedorCodigoNew.getProductoSubastaCollection().add(productoSubasta);
                vendedorCodigoNew = em.merge(vendedorCodigoNew);
            }
            if (departamentoCodigoOld != null && !departamentoCodigoOld.equals(departamentoCodigoNew)) {
                departamentoCodigoOld.getProductoSubastaCollection().remove(productoSubasta);
                departamentoCodigoOld = em.merge(departamentoCodigoOld);
            }
            if (departamentoCodigoNew != null && !departamentoCodigoNew.equals(departamentoCodigoOld)) {
                departamentoCodigoNew.getProductoSubastaCollection().add(productoSubasta);
                departamentoCodigoNew = em.merge(departamentoCodigoNew);
            }
            for (Subasta subastaCollectionOldSubasta : subastaCollectionOld) {
                if (!subastaCollectionNew.contains(subastaCollectionOldSubasta)) {
                    subastaCollectionOldSubasta.setProductoEnSubastaCodigo(null);
                    subastaCollectionOldSubasta = em.merge(subastaCollectionOldSubasta);
                }
            }
            for (Subasta subastaCollectionNewSubasta : subastaCollectionNew) {
                if (!subastaCollectionOld.contains(subastaCollectionNewSubasta)) {
                    ProductoSubasta oldProductoEnSubastaCodigoOfSubastaCollectionNewSubasta = subastaCollectionNewSubasta.getProductoEnSubastaCodigo();
                    subastaCollectionNewSubasta.setProductoEnSubastaCodigo(productoSubasta);
                    subastaCollectionNewSubasta = em.merge(subastaCollectionNewSubasta);
                    if (oldProductoEnSubastaCodigoOfSubastaCollectionNewSubasta != null && !oldProductoEnSubastaCodigoOfSubastaCollectionNewSubasta.equals(productoSubasta)) {
                        oldProductoEnSubastaCodigoOfSubastaCollectionNewSubasta.getSubastaCollection().remove(subastaCollectionNewSubasta);
                        oldProductoEnSubastaCodigoOfSubastaCollectionNewSubasta = em.merge(oldProductoEnSubastaCodigoOfSubastaCollectionNewSubasta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = productoSubasta.getCodigo();
                if (findProductoSubasta(id) == null) {
                    throw new NonexistentEntityException("The productoSubasta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoSubasta productoSubasta;
            try {
                productoSubasta = em.getReference(ProductoSubasta.class, id);
                productoSubasta.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoSubasta with id " + id + " no longer exists.", enfe);
            }
            Usuario vendedorCodigo = productoSubasta.getVendedorCodigo();
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoSubastaCollection().remove(productoSubasta);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            Departamento departamentoCodigo = productoSubasta.getDepartamentoCodigo();
            if (departamentoCodigo != null) {
                departamentoCodigo.getProductoSubastaCollection().remove(productoSubasta);
                departamentoCodigo = em.merge(departamentoCodigo);
            }
            Collection<Subasta> subastaCollection = productoSubasta.getSubastaCollection();
            for (Subasta subastaCollectionSubasta : subastaCollection) {
                subastaCollectionSubasta.setProductoEnSubastaCodigo(null);
                subastaCollectionSubasta = em.merge(subastaCollectionSubasta);
            }
            em.remove(productoSubasta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoSubasta> findProductoSubastaEntities() {
        return findProductoSubastaEntities(true, -1, -1);
    }

    public List<ProductoSubasta> findProductoSubastaEntities(int maxResults, int firstResult) {
        return findProductoSubastaEntities(false, maxResults, firstResult);
    }

    private List<ProductoSubasta> findProductoSubastaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoSubasta.class));
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

    public ProductoSubasta findProductoSubasta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoSubasta.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoSubastaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoSubasta> rt = cq.from(ProductoSubasta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
