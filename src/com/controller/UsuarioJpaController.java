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
import com.entities.Departamento;
import com.entities.Compra;
import java.util.ArrayList;
import com.entities.ProductoSubasta;
import com.entities.Canje;
import com.entities.Producto;
import com.entities.Comentario;
import com.entities.SubastaUsuario;
import com.entities.Cartera;
import com.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getCompraList() == null) {
            usuario.setCompraList(new ArrayList<Compra>());
        }
        if (usuario.getProductoSubastaList() == null) {
            usuario.setProductoSubastaList(new ArrayList<ProductoSubasta>());
        }
        if (usuario.getCanjeList() == null) {
            usuario.setCanjeList(new ArrayList<Canje>());
        }
        if (usuario.getProductoList() == null) {
            usuario.setProductoList(new ArrayList<Producto>());
        }
        if (usuario.getComentarioList() == null) {
            usuario.setComentarioList(new ArrayList<Comentario>());
        }
        if (usuario.getSubastaUsuarioList() == null) {
            usuario.setSubastaUsuarioList(new ArrayList<SubastaUsuario>());
        }
        if (usuario.getCarteraList() == null) {
            usuario.setCarteraList(new ArrayList<Cartera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamentoUsuarioCodigo = usuario.getDepartamentoUsuario();
            if (departamentoUsuarioCodigo != null) {
                departamentoUsuarioCodigo = em.getReference(departamentoUsuarioCodigo.getClass(), departamentoUsuarioCodigo.getCodigo());
                usuario.setDepartamentoUsuario(departamentoUsuarioCodigo);
            }
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : usuario.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getCodigo());
                attachedCompraList.add(compraListCompraToAttach);
            }
            usuario.setCompraList(attachedCompraList);
            List<ProductoSubasta> attachedProductoSubastaList = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaListProductoSubastaToAttach : usuario.getProductoSubastaList()) {
                productoSubastaListProductoSubastaToAttach = em.getReference(productoSubastaListProductoSubastaToAttach.getClass(), productoSubastaListProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaList.add(productoSubastaListProductoSubastaToAttach);
            }
            usuario.setProductoSubastaList(attachedProductoSubastaList);
            List<Canje> attachedCanjeList = new ArrayList<Canje>();
            for (Canje canjeListCanjeToAttach : usuario.getCanjeList()) {
                canjeListCanjeToAttach = em.getReference(canjeListCanjeToAttach.getClass(), canjeListCanjeToAttach.getCodigo());
                attachedCanjeList.add(canjeListCanjeToAttach);
            }
            usuario.setCanjeList(attachedCanjeList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : usuario.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getCodigo());
                attachedProductoList.add(productoListProductoToAttach);
            }
            usuario.setProductoList(attachedProductoList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : usuario.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getCodigo());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            usuario.setComentarioList(attachedComentarioList);
            List<SubastaUsuario> attachedSubastaUsuarioList = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioListSubastaUsuarioToAttach : usuario.getSubastaUsuarioList()) {
                subastaUsuarioListSubastaUsuarioToAttach = em.getReference(subastaUsuarioListSubastaUsuarioToAttach.getClass(), subastaUsuarioListSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioList.add(subastaUsuarioListSubastaUsuarioToAttach);
            }
            usuario.setSubastaUsuarioList(attachedSubastaUsuarioList);
            List<Cartera> attachedCarteraList = new ArrayList<Cartera>();
            for (Cartera carteraListCarteraToAttach : usuario.getCarteraList()) {
                carteraListCarteraToAttach = em.getReference(carteraListCarteraToAttach.getClass(), carteraListCarteraToAttach.getCodigo());
                attachedCarteraList.add(carteraListCarteraToAttach);
            }
            usuario.setCarteraList(attachedCarteraList);
            em.persist(usuario);
            if (departamentoUsuarioCodigo != null) {
                departamentoUsuarioCodigo.getUsuarioList().add(usuario);
                departamentoUsuarioCodigo = em.merge(departamentoUsuarioCodigo);
            }
            for (Compra compraListCompra : usuario.getCompraList()) {
                Usuario oldCompradorCodigoOfCompraListCompra = compraListCompra.getCompradorCodigo();
                compraListCompra.setCompradorCodigo(usuario);
                compraListCompra = em.merge(compraListCompra);
                if (oldCompradorCodigoOfCompraListCompra != null) {
                    oldCompradorCodigoOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldCompradorCodigoOfCompraListCompra = em.merge(oldCompradorCodigoOfCompraListCompra);
                }
            }
            for (ProductoSubasta productoSubastaListProductoSubasta : usuario.getProductoSubastaList()) {
                Usuario oldVendedorCodigoOfProductoSubastaListProductoSubasta = productoSubastaListProductoSubasta.getVendedorCodigo();
                productoSubastaListProductoSubasta.setVendedorCodigo(usuario);
                productoSubastaListProductoSubasta = em.merge(productoSubastaListProductoSubasta);
                if (oldVendedorCodigoOfProductoSubastaListProductoSubasta != null) {
                    oldVendedorCodigoOfProductoSubastaListProductoSubasta.getProductoSubastaList().remove(productoSubastaListProductoSubasta);
                    oldVendedorCodigoOfProductoSubastaListProductoSubasta = em.merge(oldVendedorCodigoOfProductoSubastaListProductoSubasta);
                }
            }
            for (Canje canjeListCanje : usuario.getCanjeList()) {
                Usuario oldClienteCodigoOfCanjeListCanje = canjeListCanje.getClienteCodigo();
                canjeListCanje.setClienteCodigo(usuario);
                canjeListCanje = em.merge(canjeListCanje);
                if (oldClienteCodigoOfCanjeListCanje != null) {
                    oldClienteCodigoOfCanjeListCanje.getCanjeList().remove(canjeListCanje);
                    oldClienteCodigoOfCanjeListCanje = em.merge(oldClienteCodigoOfCanjeListCanje);
                }
            }
            for (Producto productoListProducto : usuario.getProductoList()) {
                Usuario oldVendedorCodigoOfProductoListProducto = productoListProducto.getVendedorCodigo();
                productoListProducto.setVendedorCodigo(usuario);
                productoListProducto = em.merge(productoListProducto);
                if (oldVendedorCodigoOfProductoListProducto != null) {
                    oldVendedorCodigoOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldVendedorCodigoOfProductoListProducto = em.merge(oldVendedorCodigoOfProductoListProducto);
                }
            }
            for (Comentario comentarioListComentario : usuario.getComentarioList()) {
                Usuario oldUserComentCodigoOfComentarioListComentario = comentarioListComentario.getUserComentCodigo();
                comentarioListComentario.setUserComentCodigo(usuario);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldUserComentCodigoOfComentarioListComentario != null) {
                    oldUserComentCodigoOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldUserComentCodigoOfComentarioListComentario = em.merge(oldUserComentCodigoOfComentarioListComentario);
                }
            }
            for (SubastaUsuario subastaUsuarioListSubastaUsuario : usuario.getSubastaUsuarioList()) {
                Usuario oldUsuarioSubastaCodigoOfSubastaUsuarioListSubastaUsuario = subastaUsuarioListSubastaUsuario.getUsuarioSubastaCodigo();
                subastaUsuarioListSubastaUsuario.setUsuarioSubastaCodigo(usuario);
                subastaUsuarioListSubastaUsuario = em.merge(subastaUsuarioListSubastaUsuario);
                if (oldUsuarioSubastaCodigoOfSubastaUsuarioListSubastaUsuario != null) {
                    oldUsuarioSubastaCodigoOfSubastaUsuarioListSubastaUsuario.getSubastaUsuarioList().remove(subastaUsuarioListSubastaUsuario);
                    oldUsuarioSubastaCodigoOfSubastaUsuarioListSubastaUsuario = em.merge(oldUsuarioSubastaCodigoOfSubastaUsuarioListSubastaUsuario);
                }
            }
            for (Cartera carteraListCartera : usuario.getCarteraList()) {
                Usuario oldUsuarioCarteraCodigoOfCarteraListCartera = carteraListCartera.getUsuarioCarteraCodigo();
                carteraListCartera.setUsuarioCarteraCodigo(usuario);
                carteraListCartera = em.merge(carteraListCartera);
                if (oldUsuarioCarteraCodigoOfCarteraListCartera != null) {
                    oldUsuarioCarteraCodigoOfCarteraListCartera.getCarteraList().remove(carteraListCartera);
                    oldUsuarioCarteraCodigoOfCarteraListCartera = em.merge(oldUsuarioCarteraCodigoOfCarteraListCartera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCodigo());
            Departamento departamentoUsuarioCodigoOld = persistentUsuario.getDepartamentoUsuario();
            Departamento departamentoUsuarioCodigoNew = usuario.getDepartamentoUsuario();
            List<Compra> compraListOld = persistentUsuario.getCompraList();
            List<Compra> compraListNew = usuario.getCompraList();
            List<ProductoSubasta> productoSubastaListOld = persistentUsuario.getProductoSubastaList();
            List<ProductoSubasta> productoSubastaListNew = usuario.getProductoSubastaList();
            List<Canje> canjeListOld = persistentUsuario.getCanjeList();
            List<Canje> canjeListNew = usuario.getCanjeList();
            List<Producto> productoListOld = persistentUsuario.getProductoList();
            List<Producto> productoListNew = usuario.getProductoList();
            List<Comentario> comentarioListOld = persistentUsuario.getComentarioList();
            List<Comentario> comentarioListNew = usuario.getComentarioList();
            List<SubastaUsuario> subastaUsuarioListOld = persistentUsuario.getSubastaUsuarioList();
            List<SubastaUsuario> subastaUsuarioListNew = usuario.getSubastaUsuarioList();
            List<Cartera> carteraListOld = persistentUsuario.getCarteraList();
            List<Cartera> carteraListNew = usuario.getCarteraList();
            List<String> illegalOrphanMessages = null;
            for (Cartera carteraListOldCartera : carteraListOld) {
                if (!carteraListNew.contains(carteraListOldCartera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cartera " + carteraListOldCartera + " since its usuarioCarteraCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoUsuarioCodigoNew != null) {
                departamentoUsuarioCodigoNew = em.getReference(departamentoUsuarioCodigoNew.getClass(), departamentoUsuarioCodigoNew.getCodigo());
                usuario.setDepartamentoUsuario(departamentoUsuarioCodigoNew);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getCodigo());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            usuario.setCompraList(compraListNew);
            List<ProductoSubasta> attachedProductoSubastaListNew = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaListNewProductoSubastaToAttach : productoSubastaListNew) {
                productoSubastaListNewProductoSubastaToAttach = em.getReference(productoSubastaListNewProductoSubastaToAttach.getClass(), productoSubastaListNewProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaListNew.add(productoSubastaListNewProductoSubastaToAttach);
            }
            productoSubastaListNew = attachedProductoSubastaListNew;
            usuario.setProductoSubastaList(productoSubastaListNew);
            List<Canje> attachedCanjeListNew = new ArrayList<Canje>();
            for (Canje canjeListNewCanjeToAttach : canjeListNew) {
                canjeListNewCanjeToAttach = em.getReference(canjeListNewCanjeToAttach.getClass(), canjeListNewCanjeToAttach.getCodigo());
                attachedCanjeListNew.add(canjeListNewCanjeToAttach);
            }
            canjeListNew = attachedCanjeListNew;
            usuario.setCanjeList(canjeListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getCodigo());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            usuario.setProductoList(productoListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getCodigo());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            usuario.setComentarioList(comentarioListNew);
            List<SubastaUsuario> attachedSubastaUsuarioListNew = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioListNewSubastaUsuarioToAttach : subastaUsuarioListNew) {
                subastaUsuarioListNewSubastaUsuarioToAttach = em.getReference(subastaUsuarioListNewSubastaUsuarioToAttach.getClass(), subastaUsuarioListNewSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioListNew.add(subastaUsuarioListNewSubastaUsuarioToAttach);
            }
            subastaUsuarioListNew = attachedSubastaUsuarioListNew;
            usuario.setSubastaUsuarioList(subastaUsuarioListNew);
            List<Cartera> attachedCarteraListNew = new ArrayList<Cartera>();
            for (Cartera carteraListNewCarteraToAttach : carteraListNew) {
                carteraListNewCarteraToAttach = em.getReference(carteraListNewCarteraToAttach.getClass(), carteraListNewCarteraToAttach.getCodigo());
                attachedCarteraListNew.add(carteraListNewCarteraToAttach);
            }
            carteraListNew = attachedCarteraListNew;
            usuario.setCarteraList(carteraListNew);
            usuario = em.merge(usuario);
            if (departamentoUsuarioCodigoOld != null && !departamentoUsuarioCodigoOld.equals(departamentoUsuarioCodigoNew)) {
                departamentoUsuarioCodigoOld.getUsuarioList().remove(usuario);
                departamentoUsuarioCodigoOld = em.merge(departamentoUsuarioCodigoOld);
            }
            if (departamentoUsuarioCodigoNew != null && !departamentoUsuarioCodigoNew.equals(departamentoUsuarioCodigoOld)) {
                departamentoUsuarioCodigoNew.getUsuarioList().add(usuario);
                departamentoUsuarioCodigoNew = em.merge(departamentoUsuarioCodigoNew);
            }
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    compraListOldCompra.setCompradorCodigo(null);
                    compraListOldCompra = em.merge(compraListOldCompra);
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Usuario oldCompradorCodigoOfCompraListNewCompra = compraListNewCompra.getCompradorCodigo();
                    compraListNewCompra.setCompradorCodigo(usuario);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldCompradorCodigoOfCompraListNewCompra != null && !oldCompradorCodigoOfCompraListNewCompra.equals(usuario)) {
                        oldCompradorCodigoOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldCompradorCodigoOfCompraListNewCompra = em.merge(oldCompradorCodigoOfCompraListNewCompra);
                    }
                }
            }
            for (ProductoSubasta productoSubastaListOldProductoSubasta : productoSubastaListOld) {
                if (!productoSubastaListNew.contains(productoSubastaListOldProductoSubasta)) {
                    productoSubastaListOldProductoSubasta.setVendedorCodigo(null);
                    productoSubastaListOldProductoSubasta = em.merge(productoSubastaListOldProductoSubasta);
                }
            }
            for (ProductoSubasta productoSubastaListNewProductoSubasta : productoSubastaListNew) {
                if (!productoSubastaListOld.contains(productoSubastaListNewProductoSubasta)) {
                    Usuario oldVendedorCodigoOfProductoSubastaListNewProductoSubasta = productoSubastaListNewProductoSubasta.getVendedorCodigo();
                    productoSubastaListNewProductoSubasta.setVendedorCodigo(usuario);
                    productoSubastaListNewProductoSubasta = em.merge(productoSubastaListNewProductoSubasta);
                    if (oldVendedorCodigoOfProductoSubastaListNewProductoSubasta != null && !oldVendedorCodigoOfProductoSubastaListNewProductoSubasta.equals(usuario)) {
                        oldVendedorCodigoOfProductoSubastaListNewProductoSubasta.getProductoSubastaList().remove(productoSubastaListNewProductoSubasta);
                        oldVendedorCodigoOfProductoSubastaListNewProductoSubasta = em.merge(oldVendedorCodigoOfProductoSubastaListNewProductoSubasta);
                    }
                }
            }
            for (Canje canjeListOldCanje : canjeListOld) {
                if (!canjeListNew.contains(canjeListOldCanje)) {
                    canjeListOldCanje.setClienteCodigo(null);
                    canjeListOldCanje = em.merge(canjeListOldCanje);
                }
            }
            for (Canje canjeListNewCanje : canjeListNew) {
                if (!canjeListOld.contains(canjeListNewCanje)) {
                    Usuario oldClienteCodigoOfCanjeListNewCanje = canjeListNewCanje.getClienteCodigo();
                    canjeListNewCanje.setClienteCodigo(usuario);
                    canjeListNewCanje = em.merge(canjeListNewCanje);
                    if (oldClienteCodigoOfCanjeListNewCanje != null && !oldClienteCodigoOfCanjeListNewCanje.equals(usuario)) {
                        oldClienteCodigoOfCanjeListNewCanje.getCanjeList().remove(canjeListNewCanje);
                        oldClienteCodigoOfCanjeListNewCanje = em.merge(oldClienteCodigoOfCanjeListNewCanje);
                    }
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setVendedorCodigo(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Usuario oldVendedorCodigoOfProductoListNewProducto = productoListNewProducto.getVendedorCodigo();
                    productoListNewProducto.setVendedorCodigo(usuario);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldVendedorCodigoOfProductoListNewProducto != null && !oldVendedorCodigoOfProductoListNewProducto.equals(usuario)) {
                        oldVendedorCodigoOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldVendedorCodigoOfProductoListNewProducto = em.merge(oldVendedorCodigoOfProductoListNewProducto);
                    }
                }
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    comentarioListOldComentario.setUserComentCodigo(null);
                    comentarioListOldComentario = em.merge(comentarioListOldComentario);
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Usuario oldUserComentCodigoOfComentarioListNewComentario = comentarioListNewComentario.getUserComentCodigo();
                    comentarioListNewComentario.setUserComentCodigo(usuario);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldUserComentCodigoOfComentarioListNewComentario != null && !oldUserComentCodigoOfComentarioListNewComentario.equals(usuario)) {
                        oldUserComentCodigoOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldUserComentCodigoOfComentarioListNewComentario = em.merge(oldUserComentCodigoOfComentarioListNewComentario);
                    }
                }
            }
            for (SubastaUsuario subastaUsuarioListOldSubastaUsuario : subastaUsuarioListOld) {
                if (!subastaUsuarioListNew.contains(subastaUsuarioListOldSubastaUsuario)) {
                    subastaUsuarioListOldSubastaUsuario.setUsuarioSubastaCodigo(null);
                    subastaUsuarioListOldSubastaUsuario = em.merge(subastaUsuarioListOldSubastaUsuario);
                }
            }
            for (SubastaUsuario subastaUsuarioListNewSubastaUsuario : subastaUsuarioListNew) {
                if (!subastaUsuarioListOld.contains(subastaUsuarioListNewSubastaUsuario)) {
                    Usuario oldUsuarioSubastaCodigoOfSubastaUsuarioListNewSubastaUsuario = subastaUsuarioListNewSubastaUsuario.getUsuarioSubastaCodigo();
                    subastaUsuarioListNewSubastaUsuario.setUsuarioSubastaCodigo(usuario);
                    subastaUsuarioListNewSubastaUsuario = em.merge(subastaUsuarioListNewSubastaUsuario);
                    if (oldUsuarioSubastaCodigoOfSubastaUsuarioListNewSubastaUsuario != null && !oldUsuarioSubastaCodigoOfSubastaUsuarioListNewSubastaUsuario.equals(usuario)) {
                        oldUsuarioSubastaCodigoOfSubastaUsuarioListNewSubastaUsuario.getSubastaUsuarioList().remove(subastaUsuarioListNewSubastaUsuario);
                        oldUsuarioSubastaCodigoOfSubastaUsuarioListNewSubastaUsuario = em.merge(oldUsuarioSubastaCodigoOfSubastaUsuarioListNewSubastaUsuario);
                    }
                }
            }
            for (Cartera carteraListNewCartera : carteraListNew) {
                if (!carteraListOld.contains(carteraListNewCartera)) {
                    Usuario oldUsuarioCarteraCodigoOfCarteraListNewCartera = carteraListNewCartera.getUsuarioCarteraCodigo();
                    carteraListNewCartera.setUsuarioCarteraCodigo(usuario);
                    carteraListNewCartera = em.merge(carteraListNewCartera);
                    if (oldUsuarioCarteraCodigoOfCarteraListNewCartera != null && !oldUsuarioCarteraCodigoOfCarteraListNewCartera.equals(usuario)) {
                        oldUsuarioCarteraCodigoOfCarteraListNewCartera.getCarteraList().remove(carteraListNewCartera);
                        oldUsuarioCarteraCodigoOfCarteraListNewCartera = em.merge(oldUsuarioCarteraCodigoOfCarteraListNewCartera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCodigo();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cartera> carteraListOrphanCheck = usuario.getCarteraList();
            for (Cartera carteraListOrphanCheckCartera : carteraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cartera " + carteraListOrphanCheckCartera + " in its carteraList field has a non-nullable usuarioCarteraCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento departamentoUsuarioCodigo = usuario.getDepartamentoUsuario();
            if (departamentoUsuarioCodigo != null) {
                departamentoUsuarioCodigo.getUsuarioList().remove(usuario);
                departamentoUsuarioCodigo = em.merge(departamentoUsuarioCodigo);
            }
            List<Compra> compraList = usuario.getCompraList();
            for (Compra compraListCompra : compraList) {
                compraListCompra.setCompradorCodigo(null);
                compraListCompra = em.merge(compraListCompra);
            }
            List<ProductoSubasta> productoSubastaList = usuario.getProductoSubastaList();
            for (ProductoSubasta productoSubastaListProductoSubasta : productoSubastaList) {
                productoSubastaListProductoSubasta.setVendedorCodigo(null);
                productoSubastaListProductoSubasta = em.merge(productoSubastaListProductoSubasta);
            }
            List<Canje> canjeList = usuario.getCanjeList();
            for (Canje canjeListCanje : canjeList) {
                canjeListCanje.setClienteCodigo(null);
                canjeListCanje = em.merge(canjeListCanje);
            }
            List<Producto> productoList = usuario.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setVendedorCodigo(null);
                productoListProducto = em.merge(productoListProducto);
            }
            List<Comentario> comentarioList = usuario.getComentarioList();
            for (Comentario comentarioListComentario : comentarioList) {
                comentarioListComentario.setUserComentCodigo(null);
                comentarioListComentario = em.merge(comentarioListComentario);
            }
            List<SubastaUsuario> subastaUsuarioList = usuario.getSubastaUsuarioList();
            for (SubastaUsuario subastaUsuarioListSubastaUsuario : subastaUsuarioList) {
                subastaUsuarioListSubastaUsuario.setUsuarioSubastaCodigo(null);
                subastaUsuarioListSubastaUsuario = em.merge(subastaUsuarioListSubastaUsuario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
