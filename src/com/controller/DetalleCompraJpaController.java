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
import com.entities.Producto;
import com.entities.Compra;
import com.entities.DetalleCompra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author EQUIPO
 */
public class DetalleCompraJpaController implements Serializable {

    public DetalleCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DetalleCompraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCompra detalleCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoCompraCodigo = detalleCompra.getProductoCompraCodigo();
            if (productoCompraCodigo != null) {
                productoCompraCodigo = em.getReference(productoCompraCodigo.getClass(), productoCompraCodigo.getCodigo());
                detalleCompra.setProductoCompraCodigo(productoCompraCodigo);
            }
            Compra compraCodigo = detalleCompra.getCompraCodigo();
            if (compraCodigo != null) {
                compraCodigo = em.getReference(compraCodigo.getClass(), compraCodigo.getCodigo());
                detalleCompra.setCompraCodigo(compraCodigo);
            }
            em.persist(detalleCompra);
            if (productoCompraCodigo != null) {
                productoCompraCodigo.getDetalleCompraList().add(detalleCompra);
                productoCompraCodigo = em.merge(productoCompraCodigo);
            }
            if (compraCodigo != null) {
                compraCodigo.getDetalleCompraList().add(detalleCompra);
                compraCodigo = em.merge(compraCodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCompra detalleCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCompra persistentDetalleCompra = em.find(DetalleCompra.class, detalleCompra.getCodigo());
            Producto productoCompraCodigoOld = persistentDetalleCompra.getProductoCompraCodigo();
            Producto productoCompraCodigoNew = detalleCompra.getProductoCompraCodigo();
            Compra compraCodigoOld = persistentDetalleCompra.getCompraCodigo();
            Compra compraCodigoNew = detalleCompra.getCompraCodigo();
            if (productoCompraCodigoNew != null) {
                productoCompraCodigoNew = em.getReference(productoCompraCodigoNew.getClass(), productoCompraCodigoNew.getCodigo());
                detalleCompra.setProductoCompraCodigo(productoCompraCodigoNew);
            }
            if (compraCodigoNew != null) {
                compraCodigoNew = em.getReference(compraCodigoNew.getClass(), compraCodigoNew.getCodigo());
                detalleCompra.setCompraCodigo(compraCodigoNew);
            }
            detalleCompra = em.merge(detalleCompra);
            if (productoCompraCodigoOld != null && !productoCompraCodigoOld.equals(productoCompraCodigoNew)) {
                productoCompraCodigoOld.getDetalleCompraList().remove(detalleCompra);
                productoCompraCodigoOld = em.merge(productoCompraCodigoOld);
            }
            if (productoCompraCodigoNew != null && !productoCompraCodigoNew.equals(productoCompraCodigoOld)) {
                productoCompraCodigoNew.getDetalleCompraList().add(detalleCompra);
                productoCompraCodigoNew = em.merge(productoCompraCodigoNew);
            }
            if (compraCodigoOld != null && !compraCodigoOld.equals(compraCodigoNew)) {
                compraCodigoOld.getDetalleCompraList().remove(detalleCompra);
                compraCodigoOld = em.merge(compraCodigoOld);
            }
            if (compraCodigoNew != null && !compraCodigoNew.equals(compraCodigoOld)) {
                compraCodigoNew.getDetalleCompraList().add(detalleCompra);
                compraCodigoNew = em.merge(compraCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCompra.getCodigo();
                if (findDetalleCompra(id) == null) {
                    throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.");
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
            DetalleCompra detalleCompra;
            try {
                detalleCompra = em.getReference(DetalleCompra.class, id);
                detalleCompra.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.", enfe);
            }
            Producto productoCompraCodigo = detalleCompra.getProductoCompraCodigo();
            if (productoCompraCodigo != null) {
                productoCompraCodigo.getDetalleCompraList().remove(detalleCompra);
                productoCompraCodigo = em.merge(productoCompraCodigo);
            }
            Compra compraCodigo = detalleCompra.getCompraCodigo();
            if (compraCodigo != null) {
                compraCodigo.getDetalleCompraList().remove(detalleCompra);
                compraCodigo = em.merge(compraCodigo);
            }
            em.remove(detalleCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCompra> findDetalleCompraEntities() {
        return findDetalleCompraEntities(true, -1, -1);
    }

    public List<DetalleCompra> findDetalleCompraEntities(int maxResults, int firstResult) {
        return findDetalleCompraEntities(false, maxResults, firstResult);
    }

    private List<DetalleCompra> findDetalleCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCompra.class));
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

    public DetalleCompra findDetalleCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCompra> rt = cq.from(DetalleCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
