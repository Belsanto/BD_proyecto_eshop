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
import com.entities.Comentario;
import com.entities.Producto;
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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getComentarioCollection() == null) {
            producto.setComentarioCollection(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario vendedorCodigo = producto.getVendedorCodigo();
            if (vendedorCodigo != null) {
                vendedorCodigo = em.getReference(vendedorCodigo.getClass(), vendedorCodigo.getCodigo());
                producto.setVendedorCodigo(vendedorCodigo);
            }
            Departamento departamentoProductoCodigo = producto.getDepartamentoProductoCodigo();
            if (departamentoProductoCodigo != null) {
                departamentoProductoCodigo = em.getReference(departamentoProductoCodigo.getClass(), departamentoProductoCodigo.getCodigo());
                producto.setDepartamentoProductoCodigo(departamentoProductoCodigo);
            }
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : producto.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getCodigo());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            producto.setComentarioCollection(attachedComentarioCollection);
            em.persist(producto);
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoCollection().add(producto);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            if (departamentoProductoCodigo != null) {
                departamentoProductoCodigo.getProductoCollection().add(producto);
                departamentoProductoCodigo = em.merge(departamentoProductoCodigo);
            }
            for (Comentario comentarioCollectionComentario : producto.getComentarioCollection()) {
                Producto oldProductocCodigoOfComentarioCollectionComentario = comentarioCollectionComentario.getProductocCodigo();
                comentarioCollectionComentario.setProductocCodigo(producto);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldProductocCodigoOfComentarioCollectionComentario != null) {
                    oldProductocCodigoOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldProductocCodigoOfComentarioCollectionComentario = em.merge(oldProductocCodigoOfComentarioCollectionComentario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getCodigo()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getCodigo());
            Usuario vendedorCodigoOld = persistentProducto.getVendedorCodigo();
            Usuario vendedorCodigoNew = producto.getVendedorCodigo();
            Departamento departamentoProductoCodigoOld = persistentProducto.getDepartamentoProductoCodigo();
            Departamento departamentoProductoCodigoNew = producto.getDepartamentoProductoCodigo();
            Collection<Comentario> comentarioCollectionOld = persistentProducto.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = producto.getComentarioCollection();
            if (vendedorCodigoNew != null) {
                vendedorCodigoNew = em.getReference(vendedorCodigoNew.getClass(), vendedorCodigoNew.getCodigo());
                producto.setVendedorCodigo(vendedorCodigoNew);
            }
            if (departamentoProductoCodigoNew != null) {
                departamentoProductoCodigoNew = em.getReference(departamentoProductoCodigoNew.getClass(), departamentoProductoCodigoNew.getCodigo());
                producto.setDepartamentoProductoCodigo(departamentoProductoCodigoNew);
            }
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getCodigo());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            producto.setComentarioCollection(comentarioCollectionNew);
            producto = em.merge(producto);
            if (vendedorCodigoOld != null && !vendedorCodigoOld.equals(vendedorCodigoNew)) {
                vendedorCodigoOld.getProductoCollection().remove(producto);
                vendedorCodigoOld = em.merge(vendedorCodigoOld);
            }
            if (vendedorCodigoNew != null && !vendedorCodigoNew.equals(vendedorCodigoOld)) {
                vendedorCodigoNew.getProductoCollection().add(producto);
                vendedorCodigoNew = em.merge(vendedorCodigoNew);
            }
            if (departamentoProductoCodigoOld != null && !departamentoProductoCodigoOld.equals(departamentoProductoCodigoNew)) {
                departamentoProductoCodigoOld.getProductoCollection().remove(producto);
                departamentoProductoCodigoOld = em.merge(departamentoProductoCodigoOld);
            }
            if (departamentoProductoCodigoNew != null && !departamentoProductoCodigoNew.equals(departamentoProductoCodigoOld)) {
                departamentoProductoCodigoNew.getProductoCollection().add(producto);
                departamentoProductoCodigoNew = em.merge(departamentoProductoCodigoNew);
            }
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    comentarioCollectionOldComentario.setProductocCodigo(null);
                    comentarioCollectionOldComentario = em.merge(comentarioCollectionOldComentario);
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Producto oldProductocCodigoOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getProductocCodigo();
                    comentarioCollectionNewComentario.setProductocCodigo(producto);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldProductocCodigoOfComentarioCollectionNewComentario != null && !oldProductocCodigoOfComentarioCollectionNewComentario.equals(producto)) {
                        oldProductocCodigoOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldProductocCodigoOfComentarioCollectionNewComentario = em.merge(oldProductocCodigoOfComentarioCollectionNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = producto.getCodigo();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Usuario vendedorCodigo = producto.getVendedorCodigo();
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoCollection().remove(producto);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            Departamento departamentoProductoCodigo = producto.getDepartamentoProductoCodigo();
            if (departamentoProductoCodigo != null) {
                departamentoProductoCodigo.getProductoCollection().remove(producto);
                departamentoProductoCodigo = em.merge(departamentoProductoCodigo);
            }
            Collection<Comentario> comentarioCollection = producto.getComentarioCollection();
            for (Comentario comentarioCollectionComentario : comentarioCollection) {
                comentarioCollectionComentario.setProductocCodigo(null);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
