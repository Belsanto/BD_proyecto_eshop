/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Ciudad;
import com.entities.Departamento;
import com.entities.Ruta;
import java.util.ArrayList;
import java.util.Collection;
import com.entities.ProductoSubasta;
import com.entities.Producto;
import com.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public DepartamentoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getRutaCollection() == null) {
            departamento.setRutaCollection(new ArrayList<Ruta>());
        }
        if (departamento.getProductoSubastaCollection() == null) {
            departamento.setProductoSubastaCollection(new ArrayList<ProductoSubasta>());
        }
        if (departamento.getProductoCollection() == null) {
            departamento.setProductoCollection(new ArrayList<Producto>());
        }
        if (departamento.getUsuarioCollection() == null) {
            departamento.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudadCodigo = departamento.getCiudadCodigo();
            if (ciudadCodigo != null) {
                ciudadCodigo = em.getReference(ciudadCodigo.getClass(), ciudadCodigo.getCodigo());
                departamento.setCiudadCodigo(ciudadCodigo);
            }
            Collection<Ruta> attachedRutaCollection = new ArrayList<Ruta>();
            for (Ruta rutaCollectionRutaToAttach : departamento.getRutaCollection()) {
                rutaCollectionRutaToAttach = em.getReference(rutaCollectionRutaToAttach.getClass(), rutaCollectionRutaToAttach.getCodigo());
                attachedRutaCollection.add(rutaCollectionRutaToAttach);
            }
            departamento.setRutaCollection(attachedRutaCollection);
            Collection<ProductoSubasta> attachedProductoSubastaCollection = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaCollectionProductoSubastaToAttach : departamento.getProductoSubastaCollection()) {
                productoSubastaCollectionProductoSubastaToAttach = em.getReference(productoSubastaCollectionProductoSubastaToAttach.getClass(), productoSubastaCollectionProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaCollection.add(productoSubastaCollectionProductoSubastaToAttach);
            }
            departamento.setProductoSubastaCollection(attachedProductoSubastaCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : departamento.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getCodigo());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            departamento.setProductoCollection(attachedProductoCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : departamento.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getCodigo());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            departamento.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(departamento);
            if (ciudadCodigo != null) {
                ciudadCodigo.getDepartamentoCollection().add(departamento);
                ciudadCodigo = em.merge(ciudadCodigo);
            }
            for (Ruta rutaCollectionRuta : departamento.getRutaCollection()) {
                Departamento oldDepartamentoCodigoOfRutaCollectionRuta = rutaCollectionRuta.getDepartamentoCodigo();
                rutaCollectionRuta.setDepartamentoCodigo(departamento);
                rutaCollectionRuta = em.merge(rutaCollectionRuta);
                if (oldDepartamentoCodigoOfRutaCollectionRuta != null) {
                    oldDepartamentoCodigoOfRutaCollectionRuta.getRutaCollection().remove(rutaCollectionRuta);
                    oldDepartamentoCodigoOfRutaCollectionRuta = em.merge(oldDepartamentoCodigoOfRutaCollectionRuta);
                }
            }
            for (ProductoSubasta productoSubastaCollectionProductoSubasta : departamento.getProductoSubastaCollection()) {
                Departamento oldDepartamentoCodigoOfProductoSubastaCollectionProductoSubasta = productoSubastaCollectionProductoSubasta.getDepartamentoCodigo();
                productoSubastaCollectionProductoSubasta.setDepartamentoCodigo(departamento);
                productoSubastaCollectionProductoSubasta = em.merge(productoSubastaCollectionProductoSubasta);
                if (oldDepartamentoCodigoOfProductoSubastaCollectionProductoSubasta != null) {
                    oldDepartamentoCodigoOfProductoSubastaCollectionProductoSubasta.getProductoSubastaCollection().remove(productoSubastaCollectionProductoSubasta);
                    oldDepartamentoCodigoOfProductoSubastaCollectionProductoSubasta = em.merge(oldDepartamentoCodigoOfProductoSubastaCollectionProductoSubasta);
                }
            }
            for (Producto productoCollectionProducto : departamento.getProductoCollection()) {
                Departamento oldDepartamentoProductoCodigoOfProductoCollectionProducto = productoCollectionProducto.getDepartamentoProductoCodigo();
                productoCollectionProducto.setDepartamentoProductoCodigo(departamento);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldDepartamentoProductoCodigoOfProductoCollectionProducto != null) {
                    oldDepartamentoProductoCodigoOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldDepartamentoProductoCodigoOfProductoCollectionProducto = em.merge(oldDepartamentoProductoCodigoOfProductoCollectionProducto);
                }
            }
            for (Usuario usuarioCollectionUsuario : departamento.getUsuarioCollection()) {
                Departamento oldDepartamentoUsuarioCodigoOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getDepartamentoUsuarioCodigo();
                usuarioCollectionUsuario.setDepartamentoUsuarioCodigo(departamento);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldDepartamentoUsuarioCodigoOfUsuarioCollectionUsuario != null) {
                    oldDepartamentoUsuarioCodigoOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldDepartamentoUsuarioCodigoOfUsuarioCollectionUsuario = em.merge(oldDepartamentoUsuarioCodigoOfUsuarioCollectionUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getCodigo());
            Ciudad ciudadCodigoOld = persistentDepartamento.getCiudadCodigo();
            Ciudad ciudadCodigoNew = departamento.getCiudadCodigo();
            Collection<Ruta> rutaCollectionOld = persistentDepartamento.getRutaCollection();
            Collection<Ruta> rutaCollectionNew = departamento.getRutaCollection();
            Collection<ProductoSubasta> productoSubastaCollectionOld = persistentDepartamento.getProductoSubastaCollection();
            Collection<ProductoSubasta> productoSubastaCollectionNew = departamento.getProductoSubastaCollection();
            Collection<Producto> productoCollectionOld = persistentDepartamento.getProductoCollection();
            Collection<Producto> productoCollectionNew = departamento.getProductoCollection();
            Collection<Usuario> usuarioCollectionOld = persistentDepartamento.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = departamento.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its departamentoUsuarioCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadCodigoNew != null) {
                ciudadCodigoNew = em.getReference(ciudadCodigoNew.getClass(), ciudadCodigoNew.getCodigo());
                departamento.setCiudadCodigo(ciudadCodigoNew);
            }
            Collection<Ruta> attachedRutaCollectionNew = new ArrayList<Ruta>();
            for (Ruta rutaCollectionNewRutaToAttach : rutaCollectionNew) {
                rutaCollectionNewRutaToAttach = em.getReference(rutaCollectionNewRutaToAttach.getClass(), rutaCollectionNewRutaToAttach.getCodigo());
                attachedRutaCollectionNew.add(rutaCollectionNewRutaToAttach);
            }
            rutaCollectionNew = attachedRutaCollectionNew;
            departamento.setRutaCollection(rutaCollectionNew);
            Collection<ProductoSubasta> attachedProductoSubastaCollectionNew = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaCollectionNewProductoSubastaToAttach : productoSubastaCollectionNew) {
                productoSubastaCollectionNewProductoSubastaToAttach = em.getReference(productoSubastaCollectionNewProductoSubastaToAttach.getClass(), productoSubastaCollectionNewProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaCollectionNew.add(productoSubastaCollectionNewProductoSubastaToAttach);
            }
            productoSubastaCollectionNew = attachedProductoSubastaCollectionNew;
            departamento.setProductoSubastaCollection(productoSubastaCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getCodigo());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            departamento.setProductoCollection(productoCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getCodigo());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            departamento.setUsuarioCollection(usuarioCollectionNew);
            departamento = em.merge(departamento);
            if (ciudadCodigoOld != null && !ciudadCodigoOld.equals(ciudadCodigoNew)) {
                ciudadCodigoOld.getDepartamentoCollection().remove(departamento);
                ciudadCodigoOld = em.merge(ciudadCodigoOld);
            }
            if (ciudadCodigoNew != null && !ciudadCodigoNew.equals(ciudadCodigoOld)) {
                ciudadCodigoNew.getDepartamentoCollection().add(departamento);
                ciudadCodigoNew = em.merge(ciudadCodigoNew);
            }
            for (Ruta rutaCollectionOldRuta : rutaCollectionOld) {
                if (!rutaCollectionNew.contains(rutaCollectionOldRuta)) {
                    rutaCollectionOldRuta.setDepartamentoCodigo(null);
                    rutaCollectionOldRuta = em.merge(rutaCollectionOldRuta);
                }
            }
            for (Ruta rutaCollectionNewRuta : rutaCollectionNew) {
                if (!rutaCollectionOld.contains(rutaCollectionNewRuta)) {
                    Departamento oldDepartamentoCodigoOfRutaCollectionNewRuta = rutaCollectionNewRuta.getDepartamentoCodigo();
                    rutaCollectionNewRuta.setDepartamentoCodigo(departamento);
                    rutaCollectionNewRuta = em.merge(rutaCollectionNewRuta);
                    if (oldDepartamentoCodigoOfRutaCollectionNewRuta != null && !oldDepartamentoCodigoOfRutaCollectionNewRuta.equals(departamento)) {
                        oldDepartamentoCodigoOfRutaCollectionNewRuta.getRutaCollection().remove(rutaCollectionNewRuta);
                        oldDepartamentoCodigoOfRutaCollectionNewRuta = em.merge(oldDepartamentoCodigoOfRutaCollectionNewRuta);
                    }
                }
            }
            for (ProductoSubasta productoSubastaCollectionOldProductoSubasta : productoSubastaCollectionOld) {
                if (!productoSubastaCollectionNew.contains(productoSubastaCollectionOldProductoSubasta)) {
                    productoSubastaCollectionOldProductoSubasta.setDepartamentoCodigo(null);
                    productoSubastaCollectionOldProductoSubasta = em.merge(productoSubastaCollectionOldProductoSubasta);
                }
            }
            for (ProductoSubasta productoSubastaCollectionNewProductoSubasta : productoSubastaCollectionNew) {
                if (!productoSubastaCollectionOld.contains(productoSubastaCollectionNewProductoSubasta)) {
                    Departamento oldDepartamentoCodigoOfProductoSubastaCollectionNewProductoSubasta = productoSubastaCollectionNewProductoSubasta.getDepartamentoCodigo();
                    productoSubastaCollectionNewProductoSubasta.setDepartamentoCodigo(departamento);
                    productoSubastaCollectionNewProductoSubasta = em.merge(productoSubastaCollectionNewProductoSubasta);
                    if (oldDepartamentoCodigoOfProductoSubastaCollectionNewProductoSubasta != null && !oldDepartamentoCodigoOfProductoSubastaCollectionNewProductoSubasta.equals(departamento)) {
                        oldDepartamentoCodigoOfProductoSubastaCollectionNewProductoSubasta.getProductoSubastaCollection().remove(productoSubastaCollectionNewProductoSubasta);
                        oldDepartamentoCodigoOfProductoSubastaCollectionNewProductoSubasta = em.merge(oldDepartamentoCodigoOfProductoSubastaCollectionNewProductoSubasta);
                    }
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.setDepartamentoProductoCodigo(null);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Departamento oldDepartamentoProductoCodigoOfProductoCollectionNewProducto = productoCollectionNewProducto.getDepartamentoProductoCodigo();
                    productoCollectionNewProducto.setDepartamentoProductoCodigo(departamento);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldDepartamentoProductoCodigoOfProductoCollectionNewProducto != null && !oldDepartamentoProductoCodigoOfProductoCollectionNewProducto.equals(departamento)) {
                        oldDepartamentoProductoCodigoOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldDepartamentoProductoCodigoOfProductoCollectionNewProducto = em.merge(oldDepartamentoProductoCodigoOfProductoCollectionNewProducto);
                    }
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Departamento oldDepartamentoUsuarioCodigoOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getDepartamentoUsuarioCodigo();
                    usuarioCollectionNewUsuario.setDepartamentoUsuarioCodigo(departamento);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldDepartamentoUsuarioCodigoOfUsuarioCollectionNewUsuario != null && !oldDepartamentoUsuarioCodigoOfUsuarioCollectionNewUsuario.equals(departamento)) {
                        oldDepartamentoUsuarioCodigoOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldDepartamentoUsuarioCodigoOfUsuarioCollectionNewUsuario = em.merge(oldDepartamentoUsuarioCodigoOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getCodigo();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuario> usuarioCollectionOrphanCheck = departamento.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable departamentoUsuarioCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudadCodigo = departamento.getCiudadCodigo();
            if (ciudadCodigo != null) {
                ciudadCodigo.getDepartamentoCollection().remove(departamento);
                ciudadCodigo = em.merge(ciudadCodigo);
            }
            Collection<Ruta> rutaCollection = departamento.getRutaCollection();
            for (Ruta rutaCollectionRuta : rutaCollection) {
                rutaCollectionRuta.setDepartamentoCodigo(null);
                rutaCollectionRuta = em.merge(rutaCollectionRuta);
            }
            Collection<ProductoSubasta> productoSubastaCollection = departamento.getProductoSubastaCollection();
            for (ProductoSubasta productoSubastaCollectionProductoSubasta : productoSubastaCollection) {
                productoSubastaCollectionProductoSubasta.setDepartamentoCodigo(null);
                productoSubastaCollectionProductoSubasta = em.merge(productoSubastaCollectionProductoSubasta);
            }
            Collection<Producto> productoCollection = departamento.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.setDepartamentoProductoCodigo(null);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
