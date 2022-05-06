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
import com.entities.Ciudad;
import com.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import com.entities.DetalleCanje;
import com.entities.Comentario;
import com.entities.DetalleCompra;
import com.entities.Producto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getUsuarioList() == null) {
            producto.setUsuarioList(new ArrayList<Usuario>());
        }
        if (producto.getDetalleCanjeList() == null) {
            producto.setDetalleCanjeList(new ArrayList<DetalleCanje>());
        }
        if (producto.getComentarioList() == null) {
            producto.setComentarioList(new ArrayList<Comentario>());
        }
        if (producto.getDetalleCompraList() == null) {
            producto.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudadProductoCodigo = producto.getCiudadProductoCodigo();
            if (ciudadProductoCodigo != null) {
                ciudadProductoCodigo = em.getReference(ciudadProductoCodigo.getClass(), ciudadProductoCodigo.getCodigo());
                producto.setCiudadProductoCodigo(ciudadProductoCodigo);
            }
            Usuario vendedorCodigo = producto.getVendedorCodigo();
            if (vendedorCodigo != null) {
                vendedorCodigo = em.getReference(vendedorCodigo.getClass(), vendedorCodigo.getCodigo());
                producto.setVendedorCodigo(vendedorCodigo);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : producto.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getCodigo());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            producto.setUsuarioList(attachedUsuarioList);
            List<DetalleCanje> attachedDetalleCanjeList = new ArrayList<DetalleCanje>();
            for (DetalleCanje detalleCanjeListDetalleCanjeToAttach : producto.getDetalleCanjeList()) {
                detalleCanjeListDetalleCanjeToAttach = em.getReference(detalleCanjeListDetalleCanjeToAttach.getClass(), detalleCanjeListDetalleCanjeToAttach.getCodigo());
                attachedDetalleCanjeList.add(detalleCanjeListDetalleCanjeToAttach);
            }
            producto.setDetalleCanjeList(attachedDetalleCanjeList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : producto.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getCodigo());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            producto.setComentarioList(attachedComentarioList);
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : producto.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getCodigo());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            producto.setDetalleCompraList(attachedDetalleCompraList);
            em.persist(producto);
            if (ciudadProductoCodigo != null) {
                ciudadProductoCodigo.getProductoList().add(producto);
                ciudadProductoCodigo = em.merge(ciudadProductoCodigo);
            }
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoList().add(producto);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            for (Usuario usuarioListUsuario : producto.getUsuarioList()) {
                usuarioListUsuario.getProductoList().add(producto);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (DetalleCanje detalleCanjeListDetalleCanje : producto.getDetalleCanjeList()) {
                Producto oldProductoCanjeCodigoOfDetalleCanjeListDetalleCanje = detalleCanjeListDetalleCanje.getProductoCanjeCodigo();
                detalleCanjeListDetalleCanje.setProductoCanjeCodigo(producto);
                detalleCanjeListDetalleCanje = em.merge(detalleCanjeListDetalleCanje);
                if (oldProductoCanjeCodigoOfDetalleCanjeListDetalleCanje != null) {
                    oldProductoCanjeCodigoOfDetalleCanjeListDetalleCanje.getDetalleCanjeList().remove(detalleCanjeListDetalleCanje);
                    oldProductoCanjeCodigoOfDetalleCanjeListDetalleCanje = em.merge(oldProductoCanjeCodigoOfDetalleCanjeListDetalleCanje);
                }
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
            for (DetalleCompra detalleCompraListDetalleCompra : producto.getDetalleCompraList()) {
                Producto oldProductoCompraCodigoOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getProductoCompraCodigo();
                detalleCompraListDetalleCompra.setProductoCompraCodigo(producto);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldProductoCompraCodigoOfDetalleCompraListDetalleCompra != null) {
                    oldProductoCompraCodigoOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldProductoCompraCodigoOfDetalleCompraListDetalleCompra = em.merge(oldProductoCompraCodigoOfDetalleCompraListDetalleCompra);
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
            Ciudad ciudadProductoCodigoOld = persistentProducto.getCiudadProductoCodigo();
            Ciudad ciudadProductoCodigoNew = producto.getCiudadProductoCodigo();
            Usuario vendedorCodigoOld = persistentProducto.getVendedorCodigo();
            Usuario vendedorCodigoNew = producto.getVendedorCodigo();
            List<Usuario> usuarioListOld = persistentProducto.getUsuarioList();
            List<Usuario> usuarioListNew = producto.getUsuarioList();
            List<DetalleCanje> detalleCanjeListOld = persistentProducto.getDetalleCanjeList();
            List<DetalleCanje> detalleCanjeListNew = producto.getDetalleCanjeList();
            List<Comentario> comentarioListOld = persistentProducto.getComentarioList();
            List<Comentario> comentarioListNew = producto.getComentarioList();
            List<DetalleCompra> detalleCompraListOld = persistentProducto.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = producto.getDetalleCompraList();
            if (ciudadProductoCodigoNew != null) {
                ciudadProductoCodigoNew = em.getReference(ciudadProductoCodigoNew.getClass(), ciudadProductoCodigoNew.getCodigo());
                producto.setCiudadProductoCodigo(ciudadProductoCodigoNew);
            }
            if (vendedorCodigoNew != null) {
                vendedorCodigoNew = em.getReference(vendedorCodigoNew.getClass(), vendedorCodigoNew.getCodigo());
                producto.setVendedorCodigo(vendedorCodigoNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getCodigo());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            producto.setUsuarioList(usuarioListNew);
            List<DetalleCanje> attachedDetalleCanjeListNew = new ArrayList<DetalleCanje>();
            for (DetalleCanje detalleCanjeListNewDetalleCanjeToAttach : detalleCanjeListNew) {
                detalleCanjeListNewDetalleCanjeToAttach = em.getReference(detalleCanjeListNewDetalleCanjeToAttach.getClass(), detalleCanjeListNewDetalleCanjeToAttach.getCodigo());
                attachedDetalleCanjeListNew.add(detalleCanjeListNewDetalleCanjeToAttach);
            }
            detalleCanjeListNew = attachedDetalleCanjeListNew;
            producto.setDetalleCanjeList(detalleCanjeListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getCodigo());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            producto.setComentarioList(comentarioListNew);
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getCodigo());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            producto.setDetalleCompraList(detalleCompraListNew);
            producto = em.merge(producto);
            if (ciudadProductoCodigoOld != null && !ciudadProductoCodigoOld.equals(ciudadProductoCodigoNew)) {
                ciudadProductoCodigoOld.getProductoList().remove(producto);
                ciudadProductoCodigoOld = em.merge(ciudadProductoCodigoOld);
            }
            if (ciudadProductoCodigoNew != null && !ciudadProductoCodigoNew.equals(ciudadProductoCodigoOld)) {
                ciudadProductoCodigoNew.getProductoList().add(producto);
                ciudadProductoCodigoNew = em.merge(ciudadProductoCodigoNew);
            }
            if (vendedorCodigoOld != null && !vendedorCodigoOld.equals(vendedorCodigoNew)) {
                vendedorCodigoOld.getProductoList().remove(producto);
                vendedorCodigoOld = em.merge(vendedorCodigoOld);
            }
            if (vendedorCodigoNew != null && !vendedorCodigoNew.equals(vendedorCodigoOld)) {
                vendedorCodigoNew.getProductoList().add(producto);
                vendedorCodigoNew = em.merge(vendedorCodigoNew);
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getProductoList().remove(producto);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getProductoList().add(producto);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (DetalleCanje detalleCanjeListOldDetalleCanje : detalleCanjeListOld) {
                if (!detalleCanjeListNew.contains(detalleCanjeListOldDetalleCanje)) {
                    detalleCanjeListOldDetalleCanje.setProductoCanjeCodigo(null);
                    detalleCanjeListOldDetalleCanje = em.merge(detalleCanjeListOldDetalleCanje);
                }
            }
            for (DetalleCanje detalleCanjeListNewDetalleCanje : detalleCanjeListNew) {
                if (!detalleCanjeListOld.contains(detalleCanjeListNewDetalleCanje)) {
                    Producto oldProductoCanjeCodigoOfDetalleCanjeListNewDetalleCanje = detalleCanjeListNewDetalleCanje.getProductoCanjeCodigo();
                    detalleCanjeListNewDetalleCanje.setProductoCanjeCodigo(producto);
                    detalleCanjeListNewDetalleCanje = em.merge(detalleCanjeListNewDetalleCanje);
                    if (oldProductoCanjeCodigoOfDetalleCanjeListNewDetalleCanje != null && !oldProductoCanjeCodigoOfDetalleCanjeListNewDetalleCanje.equals(producto)) {
                        oldProductoCanjeCodigoOfDetalleCanjeListNewDetalleCanje.getDetalleCanjeList().remove(detalleCanjeListNewDetalleCanje);
                        oldProductoCanjeCodigoOfDetalleCanjeListNewDetalleCanje = em.merge(oldProductoCanjeCodigoOfDetalleCanjeListNewDetalleCanje);
                    }
                }
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
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    detalleCompraListOldDetalleCompra.setProductoCompraCodigo(null);
                    detalleCompraListOldDetalleCompra = em.merge(detalleCompraListOldDetalleCompra);
                }
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Producto oldProductoCompraCodigoOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getProductoCompraCodigo();
                    detalleCompraListNewDetalleCompra.setProductoCompraCodigo(producto);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldProductoCompraCodigoOfDetalleCompraListNewDetalleCompra != null && !oldProductoCompraCodigoOfDetalleCompraListNewDetalleCompra.equals(producto)) {
                        oldProductoCompraCodigoOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldProductoCompraCodigoOfDetalleCompraListNewDetalleCompra = em.merge(oldProductoCompraCodigoOfDetalleCompraListNewDetalleCompra);
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
            Ciudad ciudadProductoCodigo = producto.getCiudadProductoCodigo();
            if (ciudadProductoCodigo != null) {
                ciudadProductoCodigo.getProductoList().remove(producto);
                ciudadProductoCodigo = em.merge(ciudadProductoCodigo);
            }
            Usuario vendedorCodigo = producto.getVendedorCodigo();
            if (vendedorCodigo != null) {
                vendedorCodigo.getProductoList().remove(producto);
                vendedorCodigo = em.merge(vendedorCodigo);
            }
            List<Usuario> usuarioList = producto.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getProductoList().remove(producto);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<DetalleCanje> detalleCanjeList = producto.getDetalleCanjeList();
            for (DetalleCanje detalleCanjeListDetalleCanje : detalleCanjeList) {
                detalleCanjeListDetalleCanje.setProductoCanjeCodigo(null);
                detalleCanjeListDetalleCanje = em.merge(detalleCanjeListDetalleCanje);
            }
            List<Comentario> comentarioList = producto.getComentarioList();
            for (Comentario comentarioListComentario : comentarioList) {
                comentarioListComentario.setProductocCodigo(null);
                comentarioListComentario = em.merge(comentarioListComentario);
            }
            List<DetalleCompra> detalleCompraList = producto.getDetalleCompraList();
            for (DetalleCompra detalleCompraListDetalleCompra : detalleCompraList) {
                detalleCompraListDetalleCompra.setProductoCompraCodigo(null);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
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
