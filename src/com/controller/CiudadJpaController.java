/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Ciudad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Pais;
import com.entities.ProductoSubasta;
import java.util.ArrayList;
import java.util.List;
import com.entities.Producto;
import com.entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author EQUIPO
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CiudadJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) {
        if (ciudad.getProductoSubastaList() == null) {
            ciudad.setProductoSubastaList(new ArrayList<ProductoSubasta>());
        }
        if (ciudad.getProductoList() == null) {
            ciudad.setProductoList(new ArrayList<Producto>());
        }
        if (ciudad.getUsuarioList() == null) {
            ciudad.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisCodigo = ciudad.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo = em.getReference(paisCodigo.getClass(), paisCodigo.getCodigo());
                ciudad.setPaisCodigo(paisCodigo);
            }
            List<ProductoSubasta> attachedProductoSubastaList = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaListProductoSubastaToAttach : ciudad.getProductoSubastaList()) {
                productoSubastaListProductoSubastaToAttach = em.getReference(productoSubastaListProductoSubastaToAttach.getClass(), productoSubastaListProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaList.add(productoSubastaListProductoSubastaToAttach);
            }
            ciudad.setProductoSubastaList(attachedProductoSubastaList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : ciudad.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getCodigo());
                attachedProductoList.add(productoListProductoToAttach);
            }
            ciudad.setProductoList(attachedProductoList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : ciudad.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getCodigo());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            ciudad.setUsuarioList(attachedUsuarioList);
            em.persist(ciudad);
            if (paisCodigo != null) {
                paisCodigo.getCiudadList().add(ciudad);
                paisCodigo = em.merge(paisCodigo);
            }
            for (ProductoSubasta productoSubastaListProductoSubasta : ciudad.getProductoSubastaList()) {
                Ciudad oldCiudadProductoCodigoOfProductoSubastaListProductoSubasta = productoSubastaListProductoSubasta.getCiudadProductoCodigo();
                productoSubastaListProductoSubasta.setCiudadProductoCodigo(ciudad);
                productoSubastaListProductoSubasta = em.merge(productoSubastaListProductoSubasta);
                if (oldCiudadProductoCodigoOfProductoSubastaListProductoSubasta != null) {
                    oldCiudadProductoCodigoOfProductoSubastaListProductoSubasta.getProductoSubastaList().remove(productoSubastaListProductoSubasta);
                    oldCiudadProductoCodigoOfProductoSubastaListProductoSubasta = em.merge(oldCiudadProductoCodigoOfProductoSubastaListProductoSubasta);
                }
            }
            for (Producto productoListProducto : ciudad.getProductoList()) {
                Ciudad oldCiudadProductoCodigoOfProductoListProducto = productoListProducto.getCiudadProductoCodigo();
                productoListProducto.setCiudadProductoCodigo(ciudad);
                productoListProducto = em.merge(productoListProducto);
                if (oldCiudadProductoCodigoOfProductoListProducto != null) {
                    oldCiudadProductoCodigoOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldCiudadProductoCodigoOfProductoListProducto = em.merge(oldCiudadProductoCodigoOfProductoListProducto);
                }
            }
            for (Usuario usuarioListUsuario : ciudad.getUsuarioList()) {
                Ciudad oldCiudadUsuarioCodigoOfUsuarioListUsuario = usuarioListUsuario.getCiudadUsuarioCodigo();
                usuarioListUsuario.setCiudadUsuarioCodigo(ciudad);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldCiudadUsuarioCodigoOfUsuarioListUsuario != null) {
                    oldCiudadUsuarioCodigoOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldCiudadUsuarioCodigoOfUsuarioListUsuario = em.merge(oldCiudadUsuarioCodigoOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getCodigo());
            Pais paisCodigoOld = persistentCiudad.getPaisCodigo();
            Pais paisCodigoNew = ciudad.getPaisCodigo();
            List<ProductoSubasta> productoSubastaListOld = persistentCiudad.getProductoSubastaList();
            List<ProductoSubasta> productoSubastaListNew = ciudad.getProductoSubastaList();
            List<Producto> productoListOld = persistentCiudad.getProductoList();
            List<Producto> productoListNew = ciudad.getProductoList();
            List<Usuario> usuarioListOld = persistentCiudad.getUsuarioList();
            List<Usuario> usuarioListNew = ciudad.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its ciudadUsuarioCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisCodigoNew != null) {
                paisCodigoNew = em.getReference(paisCodigoNew.getClass(), paisCodigoNew.getCodigo());
                ciudad.setPaisCodigo(paisCodigoNew);
            }
            List<ProductoSubasta> attachedProductoSubastaListNew = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaListNewProductoSubastaToAttach : productoSubastaListNew) {
                productoSubastaListNewProductoSubastaToAttach = em.getReference(productoSubastaListNewProductoSubastaToAttach.getClass(), productoSubastaListNewProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaListNew.add(productoSubastaListNewProductoSubastaToAttach);
            }
            productoSubastaListNew = attachedProductoSubastaListNew;
            ciudad.setProductoSubastaList(productoSubastaListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getCodigo());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            ciudad.setProductoList(productoListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getCodigo());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            ciudad.setUsuarioList(usuarioListNew);
            ciudad = em.merge(ciudad);
            if (paisCodigoOld != null && !paisCodigoOld.equals(paisCodigoNew)) {
                paisCodigoOld.getCiudadList().remove(ciudad);
                paisCodigoOld = em.merge(paisCodigoOld);
            }
            if (paisCodigoNew != null && !paisCodigoNew.equals(paisCodigoOld)) {
                paisCodigoNew.getCiudadList().add(ciudad);
                paisCodigoNew = em.merge(paisCodigoNew);
            }
            for (ProductoSubasta productoSubastaListOldProductoSubasta : productoSubastaListOld) {
                if (!productoSubastaListNew.contains(productoSubastaListOldProductoSubasta)) {
                    productoSubastaListOldProductoSubasta.setCiudadProductoCodigo(null);
                    productoSubastaListOldProductoSubasta = em.merge(productoSubastaListOldProductoSubasta);
                }
            }
            for (ProductoSubasta productoSubastaListNewProductoSubasta : productoSubastaListNew) {
                if (!productoSubastaListOld.contains(productoSubastaListNewProductoSubasta)) {
                    Ciudad oldCiudadProductoCodigoOfProductoSubastaListNewProductoSubasta = productoSubastaListNewProductoSubasta.getCiudadProductoCodigo();
                    productoSubastaListNewProductoSubasta.setCiudadProductoCodigo(ciudad);
                    productoSubastaListNewProductoSubasta = em.merge(productoSubastaListNewProductoSubasta);
                    if (oldCiudadProductoCodigoOfProductoSubastaListNewProductoSubasta != null && !oldCiudadProductoCodigoOfProductoSubastaListNewProductoSubasta.equals(ciudad)) {
                        oldCiudadProductoCodigoOfProductoSubastaListNewProductoSubasta.getProductoSubastaList().remove(productoSubastaListNewProductoSubasta);
                        oldCiudadProductoCodigoOfProductoSubastaListNewProductoSubasta = em.merge(oldCiudadProductoCodigoOfProductoSubastaListNewProductoSubasta);
                    }
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setCiudadProductoCodigo(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Ciudad oldCiudadProductoCodigoOfProductoListNewProducto = productoListNewProducto.getCiudadProductoCodigo();
                    productoListNewProducto.setCiudadProductoCodigo(ciudad);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldCiudadProductoCodigoOfProductoListNewProducto != null && !oldCiudadProductoCodigoOfProductoListNewProducto.equals(ciudad)) {
                        oldCiudadProductoCodigoOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldCiudadProductoCodigoOfProductoListNewProducto = em.merge(oldCiudadProductoCodigoOfProductoListNewProducto);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Ciudad oldCiudadUsuarioCodigoOfUsuarioListNewUsuario = usuarioListNewUsuario.getCiudadUsuarioCodigo();
                    usuarioListNewUsuario.setCiudadUsuarioCodigo(ciudad);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldCiudadUsuarioCodigoOfUsuarioListNewUsuario != null && !oldCiudadUsuarioCodigoOfUsuarioListNewUsuario.equals(ciudad)) {
                        oldCiudadUsuarioCodigoOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldCiudadUsuarioCodigoOfUsuarioListNewUsuario = em.merge(oldCiudadUsuarioCodigoOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getCodigo();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = ciudad.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable ciudadUsuarioCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisCodigo = ciudad.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo.getCiudadList().remove(ciudad);
                paisCodigo = em.merge(paisCodigo);
            }
            List<ProductoSubasta> productoSubastaList = ciudad.getProductoSubastaList();
            for (ProductoSubasta productoSubastaListProductoSubasta : productoSubastaList) {
                productoSubastaListProductoSubasta.setCiudadProductoCodigo(null);
                productoSubastaListProductoSubasta = em.merge(productoSubastaListProductoSubasta);
            }
            List<Producto> productoList = ciudad.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setCiudadProductoCodigo(null);
                productoListProducto = em.merge(productoListProducto);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
