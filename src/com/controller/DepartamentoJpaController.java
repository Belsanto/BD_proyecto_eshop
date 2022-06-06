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
import java.util.List;
import com.entities.ProductoSubasta;
import com.entities.Producto;
import com.entities.Usuario;
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
        if (departamento.getRutaList() == null) {
            departamento.setRutaList(new ArrayList<Ruta>());
        }
        if (departamento.getProductoSubastaList() == null) {
            departamento.setProductoSubastaList(new ArrayList<ProductoSubasta>());
        }
        if (departamento.getProductoList() == null) {
            departamento.setProductoList(new ArrayList<Producto>());
        }
        if (departamento.getUsuarioList() == null) {
            departamento.setUsuarioList(new ArrayList<Usuario>());
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
            List<Ruta> attachedRutaList = new ArrayList<Ruta>();
            for (Ruta rutaListRutaToAttach : departamento.getRutaList()) {
                rutaListRutaToAttach = em.getReference(rutaListRutaToAttach.getClass(), rutaListRutaToAttach.getCodigo());
                attachedRutaList.add(rutaListRutaToAttach);
            }
            departamento.setRutaList(attachedRutaList);
            List<ProductoSubasta> attachedProductoSubastaList = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaListProductoSubastaToAttach : departamento.getProductoSubastaList()) {
                productoSubastaListProductoSubastaToAttach = em.getReference(productoSubastaListProductoSubastaToAttach.getClass(), productoSubastaListProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaList.add(productoSubastaListProductoSubastaToAttach);
            }
            departamento.setProductoSubastaList(attachedProductoSubastaList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : departamento.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getCodigo());
                attachedProductoList.add(productoListProductoToAttach);
            }
            departamento.setProductoList(attachedProductoList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : departamento.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getCodigo());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            departamento.setUsuarioList(attachedUsuarioList);
            em.persist(departamento);
            if (ciudadCodigo != null) {
                ciudadCodigo.getDepartamentoList().add(departamento);
                ciudadCodigo = em.merge(ciudadCodigo);
            }
            for (Ruta rutaListRuta : departamento.getRutaList()) {
                Departamento oldDepartamentoCodigoOfRutaListRuta = rutaListRuta.getDepartamentoCodigo();
                rutaListRuta.setDepartamentoCodigo(departamento);
                rutaListRuta = em.merge(rutaListRuta);
                if (oldDepartamentoCodigoOfRutaListRuta != null) {
                    oldDepartamentoCodigoOfRutaListRuta.getRutaList().remove(rutaListRuta);
                    oldDepartamentoCodigoOfRutaListRuta = em.merge(oldDepartamentoCodigoOfRutaListRuta);
                }
            }
            for (ProductoSubasta productoSubastaListProductoSubasta : departamento.getProductoSubastaList()) {
                Departamento oldDepartamentoCodigoOfProductoSubastaListProductoSubasta = productoSubastaListProductoSubasta.getDepartamentoCodigo();
                productoSubastaListProductoSubasta.setDepartamentoCodigo(departamento);
                productoSubastaListProductoSubasta = em.merge(productoSubastaListProductoSubasta);
                if (oldDepartamentoCodigoOfProductoSubastaListProductoSubasta != null) {
                    oldDepartamentoCodigoOfProductoSubastaListProductoSubasta.getProductoSubastaList().remove(productoSubastaListProductoSubasta);
                    oldDepartamentoCodigoOfProductoSubastaListProductoSubasta = em.merge(oldDepartamentoCodigoOfProductoSubastaListProductoSubasta);
                }
            }
            for (Producto productoListProducto : departamento.getProductoList()) {
                Departamento oldDepartamentoProductoCodigoOfProductoListProducto = productoListProducto.getDepartamentoProductoCodigo();
                productoListProducto.setDepartamentoProductoCodigo(departamento);
                productoListProducto = em.merge(productoListProducto);
                if (oldDepartamentoProductoCodigoOfProductoListProducto != null) {
                    oldDepartamentoProductoCodigoOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldDepartamentoProductoCodigoOfProductoListProducto = em.merge(oldDepartamentoProductoCodigoOfProductoListProducto);
                }
            }
            for (Usuario usuarioListUsuario : departamento.getUsuarioList()) {
                Departamento oldDepartamentoUsuarioCodigoOfUsuarioListUsuario = usuarioListUsuario.getDepartamentoUsuario();
                usuarioListUsuario.setDepartamentoUsuario(departamento);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldDepartamentoUsuarioCodigoOfUsuarioListUsuario != null) {
                    oldDepartamentoUsuarioCodigoOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldDepartamentoUsuarioCodigoOfUsuarioListUsuario = em.merge(oldDepartamentoUsuarioCodigoOfUsuarioListUsuario);
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
            List<Ruta> rutaListOld = persistentDepartamento.getRutaList();
            List<Ruta> rutaListNew = departamento.getRutaList();
            List<ProductoSubasta> productoSubastaListOld = persistentDepartamento.getProductoSubastaList();
            List<ProductoSubasta> productoSubastaListNew = departamento.getProductoSubastaList();
            List<Producto> productoListOld = persistentDepartamento.getProductoList();
            List<Producto> productoListNew = departamento.getProductoList();
            List<Usuario> usuarioListOld = persistentDepartamento.getUsuarioList();
            List<Usuario> usuarioListNew = departamento.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its departamentoUsuarioCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadCodigoNew != null) {
                ciudadCodigoNew = em.getReference(ciudadCodigoNew.getClass(), ciudadCodigoNew.getCodigo());
                departamento.setCiudadCodigo(ciudadCodigoNew);
            }
            List<Ruta> attachedRutaListNew = new ArrayList<Ruta>();
            for (Ruta rutaListNewRutaToAttach : rutaListNew) {
                rutaListNewRutaToAttach = em.getReference(rutaListNewRutaToAttach.getClass(), rutaListNewRutaToAttach.getCodigo());
                attachedRutaListNew.add(rutaListNewRutaToAttach);
            }
            rutaListNew = attachedRutaListNew;
            departamento.setRutaList(rutaListNew);
            List<ProductoSubasta> attachedProductoSubastaListNew = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaListNewProductoSubastaToAttach : productoSubastaListNew) {
                productoSubastaListNewProductoSubastaToAttach = em.getReference(productoSubastaListNewProductoSubastaToAttach.getClass(), productoSubastaListNewProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaListNew.add(productoSubastaListNewProductoSubastaToAttach);
            }
            productoSubastaListNew = attachedProductoSubastaListNew;
            departamento.setProductoSubastaList(productoSubastaListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getCodigo());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            departamento.setProductoList(productoListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getCodigo());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            departamento.setUsuarioList(usuarioListNew);
            departamento = em.merge(departamento);
            if (ciudadCodigoOld != null && !ciudadCodigoOld.equals(ciudadCodigoNew)) {
                ciudadCodigoOld.getDepartamentoList().remove(departamento);
                ciudadCodigoOld = em.merge(ciudadCodigoOld);
            }
            if (ciudadCodigoNew != null && !ciudadCodigoNew.equals(ciudadCodigoOld)) {
                ciudadCodigoNew.getDepartamentoList().add(departamento);
                ciudadCodigoNew = em.merge(ciudadCodigoNew);
            }
            for (Ruta rutaListOldRuta : rutaListOld) {
                if (!rutaListNew.contains(rutaListOldRuta)) {
                    rutaListOldRuta.setDepartamentoCodigo(null);
                    rutaListOldRuta = em.merge(rutaListOldRuta);
                }
            }
            for (Ruta rutaListNewRuta : rutaListNew) {
                if (!rutaListOld.contains(rutaListNewRuta)) {
                    Departamento oldDepartamentoCodigoOfRutaListNewRuta = rutaListNewRuta.getDepartamentoCodigo();
                    rutaListNewRuta.setDepartamentoCodigo(departamento);
                    rutaListNewRuta = em.merge(rutaListNewRuta);
                    if (oldDepartamentoCodigoOfRutaListNewRuta != null && !oldDepartamentoCodigoOfRutaListNewRuta.equals(departamento)) {
                        oldDepartamentoCodigoOfRutaListNewRuta.getRutaList().remove(rutaListNewRuta);
                        oldDepartamentoCodigoOfRutaListNewRuta = em.merge(oldDepartamentoCodigoOfRutaListNewRuta);
                    }
                }
            }
            for (ProductoSubasta productoSubastaListOldProductoSubasta : productoSubastaListOld) {
                if (!productoSubastaListNew.contains(productoSubastaListOldProductoSubasta)) {
                    productoSubastaListOldProductoSubasta.setDepartamentoCodigo(null);
                    productoSubastaListOldProductoSubasta = em.merge(productoSubastaListOldProductoSubasta);
                }
            }
            for (ProductoSubasta productoSubastaListNewProductoSubasta : productoSubastaListNew) {
                if (!productoSubastaListOld.contains(productoSubastaListNewProductoSubasta)) {
                    Departamento oldDepartamentoCodigoOfProductoSubastaListNewProductoSubasta = productoSubastaListNewProductoSubasta.getDepartamentoCodigo();
                    productoSubastaListNewProductoSubasta.setDepartamentoCodigo(departamento);
                    productoSubastaListNewProductoSubasta = em.merge(productoSubastaListNewProductoSubasta);
                    if (oldDepartamentoCodigoOfProductoSubastaListNewProductoSubasta != null && !oldDepartamentoCodigoOfProductoSubastaListNewProductoSubasta.equals(departamento)) {
                        oldDepartamentoCodigoOfProductoSubastaListNewProductoSubasta.getProductoSubastaList().remove(productoSubastaListNewProductoSubasta);
                        oldDepartamentoCodigoOfProductoSubastaListNewProductoSubasta = em.merge(oldDepartamentoCodigoOfProductoSubastaListNewProductoSubasta);
                    }
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setDepartamentoProductoCodigo(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Departamento oldDepartamentoProductoCodigoOfProductoListNewProducto = productoListNewProducto.getDepartamentoProductoCodigo();
                    productoListNewProducto.setDepartamentoProductoCodigo(departamento);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldDepartamentoProductoCodigoOfProductoListNewProducto != null && !oldDepartamentoProductoCodigoOfProductoListNewProducto.equals(departamento)) {
                        oldDepartamentoProductoCodigoOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldDepartamentoProductoCodigoOfProductoListNewProducto = em.merge(oldDepartamentoProductoCodigoOfProductoListNewProducto);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Departamento oldDepartamentoUsuarioCodigoOfUsuarioListNewUsuario = usuarioListNewUsuario.getDepartamentoUsuario();
                    usuarioListNewUsuario.setDepartamentoUsuario(departamento);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldDepartamentoUsuarioCodigoOfUsuarioListNewUsuario != null && !oldDepartamentoUsuarioCodigoOfUsuarioListNewUsuario.equals(departamento)) {
                        oldDepartamentoUsuarioCodigoOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldDepartamentoUsuarioCodigoOfUsuarioListNewUsuario = em.merge(oldDepartamentoUsuarioCodigoOfUsuarioListNewUsuario);
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
            List<Usuario> usuarioListOrphanCheck = departamento.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable departamentoUsuarioCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudadCodigo = departamento.getCiudadCodigo();
            if (ciudadCodigo != null) {
                ciudadCodigo.getDepartamentoList().remove(departamento);
                ciudadCodigo = em.merge(ciudadCodigo);
            }
            List<Ruta> rutaList = departamento.getRutaList();
            for (Ruta rutaListRuta : rutaList) {
                rutaListRuta.setDepartamentoCodigo(null);
                rutaListRuta = em.merge(rutaListRuta);
            }
            List<ProductoSubasta> productoSubastaList = departamento.getProductoSubastaList();
            for (ProductoSubasta productoSubastaListProductoSubasta : productoSubastaList) {
                productoSubastaListProductoSubasta.setDepartamentoCodigo(null);
                productoSubastaListProductoSubasta = em.merge(productoSubastaListProductoSubasta);
            }
            List<Producto> productoList = departamento.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setDepartamentoProductoCodigo(null);
                productoListProducto = em.merge(productoListProducto);
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
