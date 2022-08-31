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
        if (producto.getComentarioList() == null) {
            producto.setComentarioList(new ArrayList<Comentario>());
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
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : producto.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getCodigo());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            producto.setComentarioList(attachedComentarioList);
            em.persist(producto);
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoList().add(producto);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            if (departamentoProductoCodigo != null) {
                departamentoProductoCodigo.getProductoList().add(producto);
                departamentoProductoCodigo = em.merge(departamentoProductoCodigo);
            }
            for (Comentario comentarioListComentario : producto.getComentarioList()) {
                Producto oldProductocCodigoOfComentarioListComentario = comentarioListComentario.getProductocCodigo();
                comentarioListComentario.setProductocCodigo(producto);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldProductocCodigoOfComentarioListComentario != null) {
                    oldProductocCodigoOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldProductocCodigoOfComentarioListComentario = em.merge(oldProductocCodigoOfComentarioListComentario);
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
            List<Comentario> comentarioListOld = persistentProducto.getComentarioList();
            List<Comentario> comentarioListNew = producto.getComentarioList();
            if (vendedorCodigoNew != null) {
                vendedorCodigoNew = em.getReference(vendedorCodigoNew.getClass(), vendedorCodigoNew.getCodigo());
                producto.setVendedorCodigo(vendedorCodigoNew);
            }
            if (departamentoProductoCodigoNew != null) {
                departamentoProductoCodigoNew = em.getReference(departamentoProductoCodigoNew.getClass(), departamentoProductoCodigoNew.getCodigo());
                producto.setDepartamentoProductoCodigo(departamentoProductoCodigoNew);
            }
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getCodigo());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            producto.setComentarioList(comentarioListNew);
            producto = em.merge(producto);
            if (vendedorCodigoOld != null && !vendedorCodigoOld.equals(vendedorCodigoNew)) {
                vendedorCodigoOld.getProductoList().remove(producto);
                vendedorCodigoOld = em.merge(vendedorCodigoOld);
            }
            if (vendedorCodigoNew != null && !vendedorCodigoNew.equals(vendedorCodigoOld)) {
                vendedorCodigoNew.getProductoList().add(producto);
                vendedorCodigoNew = em.merge(vendedorCodigoNew);
            }
            if (departamentoProductoCodigoOld != null && !departamentoProductoCodigoOld.equals(departamentoProductoCodigoNew)) {
                departamentoProductoCodigoOld.getProductoList().remove(producto);
                departamentoProductoCodigoOld = em.merge(departamentoProductoCodigoOld);
            }
            if (departamentoProductoCodigoNew != null && !departamentoProductoCodigoNew.equals(departamentoProductoCodigoOld)) {
                departamentoProductoCodigoNew.getProductoList().add(producto);
                departamentoProductoCodigoNew = em.merge(departamentoProductoCodigoNew);
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    comentarioListOldComentario.setProductocCodigo(null);
                    comentarioListOldComentario = em.merge(comentarioListOldComentario);
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Producto oldProductocCodigoOfComentarioListNewComentario = comentarioListNewComentario.getProductocCodigo();
                    comentarioListNewComentario.setProductocCodigo(producto);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldProductocCodigoOfComentarioListNewComentario != null && !oldProductocCodigoOfComentarioListNewComentario.equals(producto)) {
                        oldProductocCodigoOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldProductocCodigoOfComentarioListNewComentario = em.merge(oldProductocCodigoOfComentarioListNewComentario);
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

    public Producto findProducto(int cod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
