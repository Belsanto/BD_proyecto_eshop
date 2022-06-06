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
        if (productoSubasta.getSubastaList() == null) {
            productoSubasta.setSubastaList(new ArrayList<Subasta>());
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
            List<Subasta> attachedSubastaList = new ArrayList<Subasta>();
            for (Subasta subastaListSubastaToAttach : productoSubasta.getSubastaList()) {
                subastaListSubastaToAttach = em.getReference(subastaListSubastaToAttach.getClass(), subastaListSubastaToAttach.getCodigo());
                attachedSubastaList.add(subastaListSubastaToAttach);
            }
            productoSubasta.setSubastaList(attachedSubastaList);
            em.persist(productoSubasta);
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoSubastaList().add(productoSubasta);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            if (departamentoCodigo != null) {
                departamentoCodigo.getProductoSubastaList().add(productoSubasta);
                departamentoCodigo = em.merge(departamentoCodigo);
            }
            for (Subasta subastaListSubasta : productoSubasta.getSubastaList()) {
                ProductoSubasta oldProductoEnSubastaCodigoOfSubastaListSubasta = subastaListSubasta.getProductoEnSubastaCodigo();
                subastaListSubasta.setProductoEnSubastaCodigo(productoSubasta);
                subastaListSubasta = em.merge(subastaListSubasta);
                if (oldProductoEnSubastaCodigoOfSubastaListSubasta != null) {
                    oldProductoEnSubastaCodigoOfSubastaListSubasta.getSubastaList().remove(subastaListSubasta);
                    oldProductoEnSubastaCodigoOfSubastaListSubasta = em.merge(oldProductoEnSubastaCodigoOfSubastaListSubasta);
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
            List<Subasta> subastaListOld = persistentProductoSubasta.getSubastaList();
            List<Subasta> subastaListNew = productoSubasta.getSubastaList();
            if (vendedorCodigoNew != null) {
                vendedorCodigoNew = em.getReference(vendedorCodigoNew.getClass(), vendedorCodigoNew.getCodigo());
                productoSubasta.setVendedorCodigo(vendedorCodigoNew);
            }
            if (departamentoCodigoNew != null) {
                departamentoCodigoNew = em.getReference(departamentoCodigoNew.getClass(), departamentoCodigoNew.getCodigo());
                productoSubasta.setDepartamentoCodigo(departamentoCodigoNew);
            }
            List<Subasta> attachedSubastaListNew = new ArrayList<Subasta>();
            for (Subasta subastaListNewSubastaToAttach : subastaListNew) {
                subastaListNewSubastaToAttach = em.getReference(subastaListNewSubastaToAttach.getClass(), subastaListNewSubastaToAttach.getCodigo());
                attachedSubastaListNew.add(subastaListNewSubastaToAttach);
            }
            subastaListNew = attachedSubastaListNew;
            productoSubasta.setSubastaList(subastaListNew);
            productoSubasta = em.merge(productoSubasta);
            if (vendedorCodigoOld != null && !vendedorCodigoOld.equals(vendedorCodigoNew)) {
                vendedorCodigoOld.getProductoSubastaList().remove(productoSubasta);
                vendedorCodigoOld = em.merge(vendedorCodigoOld);
            }
            if (vendedorCodigoNew != null && !vendedorCodigoNew.equals(vendedorCodigoOld)) {
                vendedorCodigoNew.getProductoSubastaList().add(productoSubasta);
                vendedorCodigoNew = em.merge(vendedorCodigoNew);
            }
            if (departamentoCodigoOld != null && !departamentoCodigoOld.equals(departamentoCodigoNew)) {
                departamentoCodigoOld.getProductoSubastaList().remove(productoSubasta);
                departamentoCodigoOld = em.merge(departamentoCodigoOld);
            }
            if (departamentoCodigoNew != null && !departamentoCodigoNew.equals(departamentoCodigoOld)) {
                departamentoCodigoNew.getProductoSubastaList().add(productoSubasta);
                departamentoCodigoNew = em.merge(departamentoCodigoNew);
            }
            for (Subasta subastaListOldSubasta : subastaListOld) {
                if (!subastaListNew.contains(subastaListOldSubasta)) {
                    subastaListOldSubasta.setProductoEnSubastaCodigo(null);
                    subastaListOldSubasta = em.merge(subastaListOldSubasta);
                }
            }
            for (Subasta subastaListNewSubasta : subastaListNew) {
                if (!subastaListOld.contains(subastaListNewSubasta)) {
                    ProductoSubasta oldProductoEnSubastaCodigoOfSubastaListNewSubasta = subastaListNewSubasta.getProductoEnSubastaCodigo();
                    subastaListNewSubasta.setProductoEnSubastaCodigo(productoSubasta);
                    subastaListNewSubasta = em.merge(subastaListNewSubasta);
                    if (oldProductoEnSubastaCodigoOfSubastaListNewSubasta != null && !oldProductoEnSubastaCodigoOfSubastaListNewSubasta.equals(productoSubasta)) {
                        oldProductoEnSubastaCodigoOfSubastaListNewSubasta.getSubastaList().remove(subastaListNewSubasta);
                        oldProductoEnSubastaCodigoOfSubastaListNewSubasta = em.merge(oldProductoEnSubastaCodigoOfSubastaListNewSubasta);
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
                vendedorCodigo.getProductoSubastaList().remove(productoSubasta);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            Departamento departamentoCodigo = productoSubasta.getDepartamentoCodigo();
            if (departamentoCodigo != null) {
                departamentoCodigo.getProductoSubastaList().remove(productoSubasta);
                departamentoCodigo = em.merge(departamentoCodigo);
            }
            List<Subasta> subastaList = productoSubasta.getSubastaList();
            for (Subasta subastaListSubasta : subastaList) {
                subastaListSubasta.setProductoEnSubastaCodigo(null);
                subastaListSubasta = em.merge(subastaListSubasta);
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
