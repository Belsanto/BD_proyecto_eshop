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
import java.util.Collection;
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
        if (usuario.getCompraCollection() == null) {
            usuario.setCompraCollection(new ArrayList<Compra>());
        }
        if (usuario.getProductoSubastaCollection() == null) {
            usuario.setProductoSubastaCollection(new ArrayList<ProductoSubasta>());
        }
        if (usuario.getCanjeCollection() == null) {
            usuario.setCanjeCollection(new ArrayList<Canje>());
        }
        if (usuario.getProductoCollection() == null) {
            usuario.setProductoCollection(new ArrayList<Producto>());
        }
        if (usuario.getComentarioCollection() == null) {
            usuario.setComentarioCollection(new ArrayList<Comentario>());
        }
        if (usuario.getSubastaUsuarioCollection() == null) {
            usuario.setSubastaUsuarioCollection(new ArrayList<SubastaUsuario>());
        }
        if (usuario.getCarteraCollection() == null) {
            usuario.setCarteraCollection(new ArrayList<Cartera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamentoUsuarioCodigo = usuario.getDepartamentoUsuarioCodigo();
            if (departamentoUsuarioCodigo != null) {
                departamentoUsuarioCodigo = em.getReference(departamentoUsuarioCodigo.getClass(), departamentoUsuarioCodigo.getCodigo());
                usuario.setDepartamentoUsuarioCodigo(departamentoUsuarioCodigo);
            }
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : usuario.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getCodigo());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            usuario.setCompraCollection(attachedCompraCollection);
            Collection<ProductoSubasta> attachedProductoSubastaCollection = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaCollectionProductoSubastaToAttach : usuario.getProductoSubastaCollection()) {
                productoSubastaCollectionProductoSubastaToAttach = em.getReference(productoSubastaCollectionProductoSubastaToAttach.getClass(), productoSubastaCollectionProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaCollection.add(productoSubastaCollectionProductoSubastaToAttach);
            }
            usuario.setProductoSubastaCollection(attachedProductoSubastaCollection);
            Collection<Canje> attachedCanjeCollection = new ArrayList<Canje>();
            for (Canje canjeCollectionCanjeToAttach : usuario.getCanjeCollection()) {
                canjeCollectionCanjeToAttach = em.getReference(canjeCollectionCanjeToAttach.getClass(), canjeCollectionCanjeToAttach.getCodigo());
                attachedCanjeCollection.add(canjeCollectionCanjeToAttach);
            }
            usuario.setCanjeCollection(attachedCanjeCollection);
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : usuario.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getCodigo());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            usuario.setProductoCollection(attachedProductoCollection);
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : usuario.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getCodigo());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            usuario.setComentarioCollection(attachedComentarioCollection);
            Collection<SubastaUsuario> attachedSubastaUsuarioCollection = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioCollectionSubastaUsuarioToAttach : usuario.getSubastaUsuarioCollection()) {
                subastaUsuarioCollectionSubastaUsuarioToAttach = em.getReference(subastaUsuarioCollectionSubastaUsuarioToAttach.getClass(), subastaUsuarioCollectionSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioCollection.add(subastaUsuarioCollectionSubastaUsuarioToAttach);
            }
            usuario.setSubastaUsuarioCollection(attachedSubastaUsuarioCollection);
            Collection<Cartera> attachedCarteraCollection = new ArrayList<Cartera>();
            for (Cartera carteraCollectionCarteraToAttach : usuario.getCarteraCollection()) {
                carteraCollectionCarteraToAttach = em.getReference(carteraCollectionCarteraToAttach.getClass(), carteraCollectionCarteraToAttach.getCodigo());
                attachedCarteraCollection.add(carteraCollectionCarteraToAttach);
            }
            usuario.setCarteraCollection(attachedCarteraCollection);
            em.persist(usuario);
            if (departamentoUsuarioCodigo != null) {
                departamentoUsuarioCodigo.getUsuarioCollection().add(usuario);
                departamentoUsuarioCodigo = em.merge(departamentoUsuarioCodigo);
            }
            for (Compra compraCollectionCompra : usuario.getCompraCollection()) {
                Usuario oldCompradorCodigoOfCompraCollectionCompra = compraCollectionCompra.getCompradorCodigo();
                compraCollectionCompra.setCompradorCodigo(usuario);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldCompradorCodigoOfCompraCollectionCompra != null) {
                    oldCompradorCodigoOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldCompradorCodigoOfCompraCollectionCompra = em.merge(oldCompradorCodigoOfCompraCollectionCompra);
                }
            }
            for (ProductoSubasta productoSubastaCollectionProductoSubasta : usuario.getProductoSubastaCollection()) {
                Usuario oldVendedorCodigoOfProductoSubastaCollectionProductoSubasta = productoSubastaCollectionProductoSubasta.getVendedorCodigo();
                productoSubastaCollectionProductoSubasta.setVendedorCodigo(usuario);
                productoSubastaCollectionProductoSubasta = em.merge(productoSubastaCollectionProductoSubasta);
                if (oldVendedorCodigoOfProductoSubastaCollectionProductoSubasta != null) {
                    oldVendedorCodigoOfProductoSubastaCollectionProductoSubasta.getProductoSubastaCollection().remove(productoSubastaCollectionProductoSubasta);
                    oldVendedorCodigoOfProductoSubastaCollectionProductoSubasta = em.merge(oldVendedorCodigoOfProductoSubastaCollectionProductoSubasta);
                }
            }
            for (Canje canjeCollectionCanje : usuario.getCanjeCollection()) {
                Usuario oldClienteCodigoOfCanjeCollectionCanje = canjeCollectionCanje.getClienteCodigo();
                canjeCollectionCanje.setClienteCodigo(usuario);
                canjeCollectionCanje = em.merge(canjeCollectionCanje);
                if (oldClienteCodigoOfCanjeCollectionCanje != null) {
                    oldClienteCodigoOfCanjeCollectionCanje.getCanjeCollection().remove(canjeCollectionCanje);
                    oldClienteCodigoOfCanjeCollectionCanje = em.merge(oldClienteCodigoOfCanjeCollectionCanje);
                }
            }
            for (Producto productoCollectionProducto : usuario.getProductoCollection()) {
                Usuario oldVendedorCodigoOfProductoCollectionProducto = productoCollectionProducto.getVendedorCodigo();
                productoCollectionProducto.setVendedorCodigo(usuario);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldVendedorCodigoOfProductoCollectionProducto != null) {
                    oldVendedorCodigoOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldVendedorCodigoOfProductoCollectionProducto = em.merge(oldVendedorCodigoOfProductoCollectionProducto);
                }
            }
            for (Comentario comentarioCollectionComentario : usuario.getComentarioCollection()) {
                Usuario oldUserComentCodigoOfComentarioCollectionComentario = comentarioCollectionComentario.getUserComentCodigo();
                comentarioCollectionComentario.setUserComentCodigo(usuario);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldUserComentCodigoOfComentarioCollectionComentario != null) {
                    oldUserComentCodigoOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldUserComentCodigoOfComentarioCollectionComentario = em.merge(oldUserComentCodigoOfComentarioCollectionComentario);
                }
            }
            for (SubastaUsuario subastaUsuarioCollectionSubastaUsuario : usuario.getSubastaUsuarioCollection()) {
                Usuario oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionSubastaUsuario = subastaUsuarioCollectionSubastaUsuario.getUsuarioSubastaCodigo();
                subastaUsuarioCollectionSubastaUsuario.setUsuarioSubastaCodigo(usuario);
                subastaUsuarioCollectionSubastaUsuario = em.merge(subastaUsuarioCollectionSubastaUsuario);
                if (oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionSubastaUsuario != null) {
                    oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionSubastaUsuario.getSubastaUsuarioCollection().remove(subastaUsuarioCollectionSubastaUsuario);
                    oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionSubastaUsuario = em.merge(oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionSubastaUsuario);
                }
            }
            for (Cartera carteraCollectionCartera : usuario.getCarteraCollection()) {
                Usuario oldUsuarioCarteraCodigoOfCarteraCollectionCartera = carteraCollectionCartera.getUsuarioCarteraCodigo();
                carteraCollectionCartera.setUsuarioCarteraCodigo(usuario);
                carteraCollectionCartera = em.merge(carteraCollectionCartera);
                if (oldUsuarioCarteraCodigoOfCarteraCollectionCartera != null) {
                    oldUsuarioCarteraCodigoOfCarteraCollectionCartera.getCarteraCollection().remove(carteraCollectionCartera);
                    oldUsuarioCarteraCodigoOfCarteraCollectionCartera = em.merge(oldUsuarioCarteraCodigoOfCarteraCollectionCartera);
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
            Departamento departamentoUsuarioCodigoOld = persistentUsuario.getDepartamentoUsuarioCodigo();
            Departamento departamentoUsuarioCodigoNew = usuario.getDepartamentoUsuarioCodigo();
            Collection<Compra> compraCollectionOld = persistentUsuario.getCompraCollection();
            Collection<Compra> compraCollectionNew = usuario.getCompraCollection();
            Collection<ProductoSubasta> productoSubastaCollectionOld = persistentUsuario.getProductoSubastaCollection();
            Collection<ProductoSubasta> productoSubastaCollectionNew = usuario.getProductoSubastaCollection();
            Collection<Canje> canjeCollectionOld = persistentUsuario.getCanjeCollection();
            Collection<Canje> canjeCollectionNew = usuario.getCanjeCollection();
            Collection<Producto> productoCollectionOld = persistentUsuario.getProductoCollection();
            Collection<Producto> productoCollectionNew = usuario.getProductoCollection();
            Collection<Comentario> comentarioCollectionOld = persistentUsuario.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = usuario.getComentarioCollection();
            Collection<SubastaUsuario> subastaUsuarioCollectionOld = persistentUsuario.getSubastaUsuarioCollection();
            Collection<SubastaUsuario> subastaUsuarioCollectionNew = usuario.getSubastaUsuarioCollection();
            Collection<Cartera> carteraCollectionOld = persistentUsuario.getCarteraCollection();
            Collection<Cartera> carteraCollectionNew = usuario.getCarteraCollection();
            List<String> illegalOrphanMessages = null;
            for (Cartera carteraCollectionOldCartera : carteraCollectionOld) {
                if (!carteraCollectionNew.contains(carteraCollectionOldCartera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cartera " + carteraCollectionOldCartera + " since its usuarioCarteraCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoUsuarioCodigoNew != null) {
                departamentoUsuarioCodigoNew = em.getReference(departamentoUsuarioCodigoNew.getClass(), departamentoUsuarioCodigoNew.getCodigo());
                usuario.setDepartamentoUsuarioCodigo(departamentoUsuarioCodigoNew);
            }
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getCodigo());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            usuario.setCompraCollection(compraCollectionNew);
            Collection<ProductoSubasta> attachedProductoSubastaCollectionNew = new ArrayList<ProductoSubasta>();
            for (ProductoSubasta productoSubastaCollectionNewProductoSubastaToAttach : productoSubastaCollectionNew) {
                productoSubastaCollectionNewProductoSubastaToAttach = em.getReference(productoSubastaCollectionNewProductoSubastaToAttach.getClass(), productoSubastaCollectionNewProductoSubastaToAttach.getCodigo());
                attachedProductoSubastaCollectionNew.add(productoSubastaCollectionNewProductoSubastaToAttach);
            }
            productoSubastaCollectionNew = attachedProductoSubastaCollectionNew;
            usuario.setProductoSubastaCollection(productoSubastaCollectionNew);
            Collection<Canje> attachedCanjeCollectionNew = new ArrayList<Canje>();
            for (Canje canjeCollectionNewCanjeToAttach : canjeCollectionNew) {
                canjeCollectionNewCanjeToAttach = em.getReference(canjeCollectionNewCanjeToAttach.getClass(), canjeCollectionNewCanjeToAttach.getCodigo());
                attachedCanjeCollectionNew.add(canjeCollectionNewCanjeToAttach);
            }
            canjeCollectionNew = attachedCanjeCollectionNew;
            usuario.setCanjeCollection(canjeCollectionNew);
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getCodigo());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            usuario.setProductoCollection(productoCollectionNew);
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getCodigo());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            usuario.setComentarioCollection(comentarioCollectionNew);
            Collection<SubastaUsuario> attachedSubastaUsuarioCollectionNew = new ArrayList<SubastaUsuario>();
            for (SubastaUsuario subastaUsuarioCollectionNewSubastaUsuarioToAttach : subastaUsuarioCollectionNew) {
                subastaUsuarioCollectionNewSubastaUsuarioToAttach = em.getReference(subastaUsuarioCollectionNewSubastaUsuarioToAttach.getClass(), subastaUsuarioCollectionNewSubastaUsuarioToAttach.getCodigo());
                attachedSubastaUsuarioCollectionNew.add(subastaUsuarioCollectionNewSubastaUsuarioToAttach);
            }
            subastaUsuarioCollectionNew = attachedSubastaUsuarioCollectionNew;
            usuario.setSubastaUsuarioCollection(subastaUsuarioCollectionNew);
            Collection<Cartera> attachedCarteraCollectionNew = new ArrayList<Cartera>();
            for (Cartera carteraCollectionNewCarteraToAttach : carteraCollectionNew) {
                carteraCollectionNewCarteraToAttach = em.getReference(carteraCollectionNewCarteraToAttach.getClass(), carteraCollectionNewCarteraToAttach.getCodigo());
                attachedCarteraCollectionNew.add(carteraCollectionNewCarteraToAttach);
            }
            carteraCollectionNew = attachedCarteraCollectionNew;
            usuario.setCarteraCollection(carteraCollectionNew);
            usuario = em.merge(usuario);
            if (departamentoUsuarioCodigoOld != null && !departamentoUsuarioCodigoOld.equals(departamentoUsuarioCodigoNew)) {
                departamentoUsuarioCodigoOld.getUsuarioCollection().remove(usuario);
                departamentoUsuarioCodigoOld = em.merge(departamentoUsuarioCodigoOld);
            }
            if (departamentoUsuarioCodigoNew != null && !departamentoUsuarioCodigoNew.equals(departamentoUsuarioCodigoOld)) {
                departamentoUsuarioCodigoNew.getUsuarioCollection().add(usuario);
                departamentoUsuarioCodigoNew = em.merge(departamentoUsuarioCodigoNew);
            }
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    compraCollectionOldCompra.setCompradorCodigo(null);
                    compraCollectionOldCompra = em.merge(compraCollectionOldCompra);
                }
            }
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    Usuario oldCompradorCodigoOfCompraCollectionNewCompra = compraCollectionNewCompra.getCompradorCodigo();
                    compraCollectionNewCompra.setCompradorCodigo(usuario);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldCompradorCodigoOfCompraCollectionNewCompra != null && !oldCompradorCodigoOfCompraCollectionNewCompra.equals(usuario)) {
                        oldCompradorCodigoOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldCompradorCodigoOfCompraCollectionNewCompra = em.merge(oldCompradorCodigoOfCompraCollectionNewCompra);
                    }
                }
            }
            for (ProductoSubasta productoSubastaCollectionOldProductoSubasta : productoSubastaCollectionOld) {
                if (!productoSubastaCollectionNew.contains(productoSubastaCollectionOldProductoSubasta)) {
                    productoSubastaCollectionOldProductoSubasta.setVendedorCodigo(null);
                    productoSubastaCollectionOldProductoSubasta = em.merge(productoSubastaCollectionOldProductoSubasta);
                }
            }
            for (ProductoSubasta productoSubastaCollectionNewProductoSubasta : productoSubastaCollectionNew) {
                if (!productoSubastaCollectionOld.contains(productoSubastaCollectionNewProductoSubasta)) {
                    Usuario oldVendedorCodigoOfProductoSubastaCollectionNewProductoSubasta = productoSubastaCollectionNewProductoSubasta.getVendedorCodigo();
                    productoSubastaCollectionNewProductoSubasta.setVendedorCodigo(usuario);
                    productoSubastaCollectionNewProductoSubasta = em.merge(productoSubastaCollectionNewProductoSubasta);
                    if (oldVendedorCodigoOfProductoSubastaCollectionNewProductoSubasta != null && !oldVendedorCodigoOfProductoSubastaCollectionNewProductoSubasta.equals(usuario)) {
                        oldVendedorCodigoOfProductoSubastaCollectionNewProductoSubasta.getProductoSubastaCollection().remove(productoSubastaCollectionNewProductoSubasta);
                        oldVendedorCodigoOfProductoSubastaCollectionNewProductoSubasta = em.merge(oldVendedorCodigoOfProductoSubastaCollectionNewProductoSubasta);
                    }
                }
            }
            for (Canje canjeCollectionOldCanje : canjeCollectionOld) {
                if (!canjeCollectionNew.contains(canjeCollectionOldCanje)) {
                    canjeCollectionOldCanje.setClienteCodigo(null);
                    canjeCollectionOldCanje = em.merge(canjeCollectionOldCanje);
                }
            }
            for (Canje canjeCollectionNewCanje : canjeCollectionNew) {
                if (!canjeCollectionOld.contains(canjeCollectionNewCanje)) {
                    Usuario oldClienteCodigoOfCanjeCollectionNewCanje = canjeCollectionNewCanje.getClienteCodigo();
                    canjeCollectionNewCanje.setClienteCodigo(usuario);
                    canjeCollectionNewCanje = em.merge(canjeCollectionNewCanje);
                    if (oldClienteCodigoOfCanjeCollectionNewCanje != null && !oldClienteCodigoOfCanjeCollectionNewCanje.equals(usuario)) {
                        oldClienteCodigoOfCanjeCollectionNewCanje.getCanjeCollection().remove(canjeCollectionNewCanje);
                        oldClienteCodigoOfCanjeCollectionNewCanje = em.merge(oldClienteCodigoOfCanjeCollectionNewCanje);
                    }
                }
            }
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.setVendedorCodigo(null);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Usuario oldVendedorCodigoOfProductoCollectionNewProducto = productoCollectionNewProducto.getVendedorCodigo();
                    productoCollectionNewProducto.setVendedorCodigo(usuario);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldVendedorCodigoOfProductoCollectionNewProducto != null && !oldVendedorCodigoOfProductoCollectionNewProducto.equals(usuario)) {
                        oldVendedorCodigoOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldVendedorCodigoOfProductoCollectionNewProducto = em.merge(oldVendedorCodigoOfProductoCollectionNewProducto);
                    }
                }
            }
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    comentarioCollectionOldComentario.setUserComentCodigo(null);
                    comentarioCollectionOldComentario = em.merge(comentarioCollectionOldComentario);
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Usuario oldUserComentCodigoOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getUserComentCodigo();
                    comentarioCollectionNewComentario.setUserComentCodigo(usuario);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldUserComentCodigoOfComentarioCollectionNewComentario != null && !oldUserComentCodigoOfComentarioCollectionNewComentario.equals(usuario)) {
                        oldUserComentCodigoOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldUserComentCodigoOfComentarioCollectionNewComentario = em.merge(oldUserComentCodigoOfComentarioCollectionNewComentario);
                    }
                }
            }
            for (SubastaUsuario subastaUsuarioCollectionOldSubastaUsuario : subastaUsuarioCollectionOld) {
                if (!subastaUsuarioCollectionNew.contains(subastaUsuarioCollectionOldSubastaUsuario)) {
                    subastaUsuarioCollectionOldSubastaUsuario.setUsuarioSubastaCodigo(null);
                    subastaUsuarioCollectionOldSubastaUsuario = em.merge(subastaUsuarioCollectionOldSubastaUsuario);
                }
            }
            for (SubastaUsuario subastaUsuarioCollectionNewSubastaUsuario : subastaUsuarioCollectionNew) {
                if (!subastaUsuarioCollectionOld.contains(subastaUsuarioCollectionNewSubastaUsuario)) {
                    Usuario oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionNewSubastaUsuario = subastaUsuarioCollectionNewSubastaUsuario.getUsuarioSubastaCodigo();
                    subastaUsuarioCollectionNewSubastaUsuario.setUsuarioSubastaCodigo(usuario);
                    subastaUsuarioCollectionNewSubastaUsuario = em.merge(subastaUsuarioCollectionNewSubastaUsuario);
                    if (oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionNewSubastaUsuario != null && !oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionNewSubastaUsuario.equals(usuario)) {
                        oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionNewSubastaUsuario.getSubastaUsuarioCollection().remove(subastaUsuarioCollectionNewSubastaUsuario);
                        oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionNewSubastaUsuario = em.merge(oldUsuarioSubastaCodigoOfSubastaUsuarioCollectionNewSubastaUsuario);
                    }
                }
            }
            for (Cartera carteraCollectionNewCartera : carteraCollectionNew) {
                if (!carteraCollectionOld.contains(carteraCollectionNewCartera)) {
                    Usuario oldUsuarioCarteraCodigoOfCarteraCollectionNewCartera = carteraCollectionNewCartera.getUsuarioCarteraCodigo();
                    carteraCollectionNewCartera.setUsuarioCarteraCodigo(usuario);
                    carteraCollectionNewCartera = em.merge(carteraCollectionNewCartera);
                    if (oldUsuarioCarteraCodigoOfCarteraCollectionNewCartera != null && !oldUsuarioCarteraCodigoOfCarteraCollectionNewCartera.equals(usuario)) {
                        oldUsuarioCarteraCodigoOfCarteraCollectionNewCartera.getCarteraCollection().remove(carteraCollectionNewCartera);
                        oldUsuarioCarteraCodigoOfCarteraCollectionNewCartera = em.merge(oldUsuarioCarteraCodigoOfCarteraCollectionNewCartera);
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
            Collection<Cartera> carteraCollectionOrphanCheck = usuario.getCarteraCollection();
            for (Cartera carteraCollectionOrphanCheckCartera : carteraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cartera " + carteraCollectionOrphanCheckCartera + " in its carteraCollection field has a non-nullable usuarioCarteraCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento departamentoUsuarioCodigo = usuario.getDepartamentoUsuarioCodigo();
            if (departamentoUsuarioCodigo != null) {
                departamentoUsuarioCodigo.getUsuarioCollection().remove(usuario);
                departamentoUsuarioCodigo = em.merge(departamentoUsuarioCodigo);
            }
            Collection<Compra> compraCollection = usuario.getCompraCollection();
            for (Compra compraCollectionCompra : compraCollection) {
                compraCollectionCompra.setCompradorCodigo(null);
                compraCollectionCompra = em.merge(compraCollectionCompra);
            }
            Collection<ProductoSubasta> productoSubastaCollection = usuario.getProductoSubastaCollection();
            for (ProductoSubasta productoSubastaCollectionProductoSubasta : productoSubastaCollection) {
                productoSubastaCollectionProductoSubasta.setVendedorCodigo(null);
                productoSubastaCollectionProductoSubasta = em.merge(productoSubastaCollectionProductoSubasta);
            }
            Collection<Canje> canjeCollection = usuario.getCanjeCollection();
            for (Canje canjeCollectionCanje : canjeCollection) {
                canjeCollectionCanje.setClienteCodigo(null);
                canjeCollectionCanje = em.merge(canjeCollectionCanje);
            }
            Collection<Producto> productoCollection = usuario.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.setVendedorCodigo(null);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            Collection<Comentario> comentarioCollection = usuario.getComentarioCollection();
            for (Comentario comentarioCollectionComentario : comentarioCollection) {
                comentarioCollectionComentario.setUserComentCodigo(null);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
            }
            Collection<SubastaUsuario> subastaUsuarioCollection = usuario.getSubastaUsuarioCollection();
            for (SubastaUsuario subastaUsuarioCollectionSubastaUsuario : subastaUsuarioCollection) {
                subastaUsuarioCollectionSubastaUsuario.setUsuarioSubastaCodigo(null);
                subastaUsuarioCollectionSubastaUsuario = em.merge(subastaUsuarioCollectionSubastaUsuario);
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
