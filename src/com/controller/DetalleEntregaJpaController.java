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
import com.entities.Compra;
import com.entities.Ruta;
import com.entities.Canje;
import com.entities.DetalleEntrega;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author EQUIPO
 */
public class DetalleEntregaJpaController implements Serializable {

    public DetalleEntregaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DetalleEntregaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleEntrega detalleEntrega) {
        if (detalleEntrega.getCompraList() == null) {
            detalleEntrega.setCompraList(new ArrayList<Compra>());
        }
        if (detalleEntrega.getCanjeList() == null) {
            detalleEntrega.setCanjeList(new ArrayList<Canje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra compraEntregarCodigo = detalleEntrega.getCompraEntregarCodigo();
            if (compraEntregarCodigo != null) {
                compraEntregarCodigo = em.getReference(compraEntregarCodigo.getClass(), compraEntregarCodigo.getCodigo());
                detalleEntrega.setCompraEntregarCodigo(compraEntregarCodigo);
            }
            Ruta rutaCodigo = detalleEntrega.getRutaCodigo();
            if (rutaCodigo != null) {
                rutaCodigo = em.getReference(rutaCodigo.getClass(), rutaCodigo.getCodigo());
                detalleEntrega.setRutaCodigo(rutaCodigo);
            }
            Canje canjeEntregarCodigo = detalleEntrega.getCanjeEntregarCodigo();
            if (canjeEntregarCodigo != null) {
                canjeEntregarCodigo = em.getReference(canjeEntregarCodigo.getClass(), canjeEntregarCodigo.getCodigo());
                detalleEntrega.setCanjeEntregarCodigo(canjeEntregarCodigo);
            }
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : detalleEntrega.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getCodigo());
                attachedCompraList.add(compraListCompraToAttach);
            }
            detalleEntrega.setCompraList(attachedCompraList);
            List<Canje> attachedCanjeList = new ArrayList<Canje>();
            for (Canje canjeListCanjeToAttach : detalleEntrega.getCanjeList()) {
                canjeListCanjeToAttach = em.getReference(canjeListCanjeToAttach.getClass(), canjeListCanjeToAttach.getCodigo());
                attachedCanjeList.add(canjeListCanjeToAttach);
            }
            detalleEntrega.setCanjeList(attachedCanjeList);
            em.persist(detalleEntrega);
            if (compraEntregarCodigo != null) {
                DetalleEntrega oldDetalleEntregaCodigoOfCompraEntregarCodigo = compraEntregarCodigo.getDetalleEntregaCodigo();
                if (oldDetalleEntregaCodigoOfCompraEntregarCodigo != null) {
                    oldDetalleEntregaCodigoOfCompraEntregarCodigo.setCompraEntregarCodigo(null);
                    oldDetalleEntregaCodigoOfCompraEntregarCodigo = em.merge(oldDetalleEntregaCodigoOfCompraEntregarCodigo);
                }
                compraEntregarCodigo.setDetalleEntregaCodigo(detalleEntrega);
                compraEntregarCodigo = em.merge(compraEntregarCodigo);
            }
            if (rutaCodigo != null) {
                rutaCodigo.getDetalleEntregaList().add(detalleEntrega);
                rutaCodigo = em.merge(rutaCodigo);
            }
            if (canjeEntregarCodigo != null) {
                DetalleEntrega oldDetalleEntregaCodigoOfCanjeEntregarCodigo = canjeEntregarCodigo.getDetalleEntregaCodigo();
                if (oldDetalleEntregaCodigoOfCanjeEntregarCodigo != null) {
                    oldDetalleEntregaCodigoOfCanjeEntregarCodigo.setCanjeEntregarCodigo(null);
                    oldDetalleEntregaCodigoOfCanjeEntregarCodigo = em.merge(oldDetalleEntregaCodigoOfCanjeEntregarCodigo);
                }
                canjeEntregarCodigo.setDetalleEntregaCodigo(detalleEntrega);
                canjeEntregarCodigo = em.merge(canjeEntregarCodigo);
            }
            for (Compra compraListCompra : detalleEntrega.getCompraList()) {
                DetalleEntrega oldDetalleEntregaCodigoOfCompraListCompra = compraListCompra.getDetalleEntregaCodigo();
                compraListCompra.setDetalleEntregaCodigo(detalleEntrega);
                compraListCompra = em.merge(compraListCompra);
                if (oldDetalleEntregaCodigoOfCompraListCompra != null) {
                    oldDetalleEntregaCodigoOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldDetalleEntregaCodigoOfCompraListCompra = em.merge(oldDetalleEntregaCodigoOfCompraListCompra);
                }
            }
            for (Canje canjeListCanje : detalleEntrega.getCanjeList()) {
                DetalleEntrega oldDetalleEntregaCodigoOfCanjeListCanje = canjeListCanje.getDetalleEntregaCodigo();
                canjeListCanje.setDetalleEntregaCodigo(detalleEntrega);
                canjeListCanje = em.merge(canjeListCanje);
                if (oldDetalleEntregaCodigoOfCanjeListCanje != null) {
                    oldDetalleEntregaCodigoOfCanjeListCanje.getCanjeList().remove(canjeListCanje);
                    oldDetalleEntregaCodigoOfCanjeListCanje = em.merge(oldDetalleEntregaCodigoOfCanjeListCanje);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleEntrega detalleEntrega) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleEntrega persistentDetalleEntrega = em.find(DetalleEntrega.class, detalleEntrega.getCodigo());
            Compra compraEntregarCodigoOld = persistentDetalleEntrega.getCompraEntregarCodigo();
            Compra compraEntregarCodigoNew = detalleEntrega.getCompraEntregarCodigo();
            Ruta rutaCodigoOld = persistentDetalleEntrega.getRutaCodigo();
            Ruta rutaCodigoNew = detalleEntrega.getRutaCodigo();
            Canje canjeEntregarCodigoOld = persistentDetalleEntrega.getCanjeEntregarCodigo();
            Canje canjeEntregarCodigoNew = detalleEntrega.getCanjeEntregarCodigo();
            List<Compra> compraListOld = persistentDetalleEntrega.getCompraList();
            List<Compra> compraListNew = detalleEntrega.getCompraList();
            List<Canje> canjeListOld = persistentDetalleEntrega.getCanjeList();
            List<Canje> canjeListNew = detalleEntrega.getCanjeList();
            if (compraEntregarCodigoNew != null) {
                compraEntregarCodigoNew = em.getReference(compraEntregarCodigoNew.getClass(), compraEntregarCodigoNew.getCodigo());
                detalleEntrega.setCompraEntregarCodigo(compraEntregarCodigoNew);
            }
            if (rutaCodigoNew != null) {
                rutaCodigoNew = em.getReference(rutaCodigoNew.getClass(), rutaCodigoNew.getCodigo());
                detalleEntrega.setRutaCodigo(rutaCodigoNew);
            }
            if (canjeEntregarCodigoNew != null) {
                canjeEntregarCodigoNew = em.getReference(canjeEntregarCodigoNew.getClass(), canjeEntregarCodigoNew.getCodigo());
                detalleEntrega.setCanjeEntregarCodigo(canjeEntregarCodigoNew);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getCodigo());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            detalleEntrega.setCompraList(compraListNew);
            List<Canje> attachedCanjeListNew = new ArrayList<Canje>();
            for (Canje canjeListNewCanjeToAttach : canjeListNew) {
                canjeListNewCanjeToAttach = em.getReference(canjeListNewCanjeToAttach.getClass(), canjeListNewCanjeToAttach.getCodigo());
                attachedCanjeListNew.add(canjeListNewCanjeToAttach);
            }
            canjeListNew = attachedCanjeListNew;
            detalleEntrega.setCanjeList(canjeListNew);
            detalleEntrega = em.merge(detalleEntrega);
            if (compraEntregarCodigoOld != null && !compraEntregarCodigoOld.equals(compraEntregarCodigoNew)) {
                compraEntregarCodigoOld.setDetalleEntregaCodigo(null);
                compraEntregarCodigoOld = em.merge(compraEntregarCodigoOld);
            }
            if (compraEntregarCodigoNew != null && !compraEntregarCodigoNew.equals(compraEntregarCodigoOld)) {
                DetalleEntrega oldDetalleEntregaCodigoOfCompraEntregarCodigo = compraEntregarCodigoNew.getDetalleEntregaCodigo();
                if (oldDetalleEntregaCodigoOfCompraEntregarCodigo != null) {
                    oldDetalleEntregaCodigoOfCompraEntregarCodigo.setCompraEntregarCodigo(null);
                    oldDetalleEntregaCodigoOfCompraEntregarCodigo = em.merge(oldDetalleEntregaCodigoOfCompraEntregarCodigo);
                }
                compraEntregarCodigoNew.setDetalleEntregaCodigo(detalleEntrega);
                compraEntregarCodigoNew = em.merge(compraEntregarCodigoNew);
            }
            if (rutaCodigoOld != null && !rutaCodigoOld.equals(rutaCodigoNew)) {
                rutaCodigoOld.getDetalleEntregaList().remove(detalleEntrega);
                rutaCodigoOld = em.merge(rutaCodigoOld);
            }
            if (rutaCodigoNew != null && !rutaCodigoNew.equals(rutaCodigoOld)) {
                rutaCodigoNew.getDetalleEntregaList().add(detalleEntrega);
                rutaCodigoNew = em.merge(rutaCodigoNew);
            }
            if (canjeEntregarCodigoOld != null && !canjeEntregarCodigoOld.equals(canjeEntregarCodigoNew)) {
                canjeEntregarCodigoOld.setDetalleEntregaCodigo(null);
                canjeEntregarCodigoOld = em.merge(canjeEntregarCodigoOld);
            }
            if (canjeEntregarCodigoNew != null && !canjeEntregarCodigoNew.equals(canjeEntregarCodigoOld)) {
                DetalleEntrega oldDetalleEntregaCodigoOfCanjeEntregarCodigo = canjeEntregarCodigoNew.getDetalleEntregaCodigo();
                if (oldDetalleEntregaCodigoOfCanjeEntregarCodigo != null) {
                    oldDetalleEntregaCodigoOfCanjeEntregarCodigo.setCanjeEntregarCodigo(null);
                    oldDetalleEntregaCodigoOfCanjeEntregarCodigo = em.merge(oldDetalleEntregaCodigoOfCanjeEntregarCodigo);
                }
                canjeEntregarCodigoNew.setDetalleEntregaCodigo(detalleEntrega);
                canjeEntregarCodigoNew = em.merge(canjeEntregarCodigoNew);
            }
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    compraListOldCompra.setDetalleEntregaCodigo(null);
                    compraListOldCompra = em.merge(compraListOldCompra);
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    DetalleEntrega oldDetalleEntregaCodigoOfCompraListNewCompra = compraListNewCompra.getDetalleEntregaCodigo();
                    compraListNewCompra.setDetalleEntregaCodigo(detalleEntrega);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldDetalleEntregaCodigoOfCompraListNewCompra != null && !oldDetalleEntregaCodigoOfCompraListNewCompra.equals(detalleEntrega)) {
                        oldDetalleEntregaCodigoOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldDetalleEntregaCodigoOfCompraListNewCompra = em.merge(oldDetalleEntregaCodigoOfCompraListNewCompra);
                    }
                }
            }
            for (Canje canjeListOldCanje : canjeListOld) {
                if (!canjeListNew.contains(canjeListOldCanje)) {
                    canjeListOldCanje.setDetalleEntregaCodigo(null);
                    canjeListOldCanje = em.merge(canjeListOldCanje);
                }
            }
            for (Canje canjeListNewCanje : canjeListNew) {
                if (!canjeListOld.contains(canjeListNewCanje)) {
                    DetalleEntrega oldDetalleEntregaCodigoOfCanjeListNewCanje = canjeListNewCanje.getDetalleEntregaCodigo();
                    canjeListNewCanje.setDetalleEntregaCodigo(detalleEntrega);
                    canjeListNewCanje = em.merge(canjeListNewCanje);
                    if (oldDetalleEntregaCodigoOfCanjeListNewCanje != null && !oldDetalleEntregaCodigoOfCanjeListNewCanje.equals(detalleEntrega)) {
                        oldDetalleEntregaCodigoOfCanjeListNewCanje.getCanjeList().remove(canjeListNewCanje);
                        oldDetalleEntregaCodigoOfCanjeListNewCanje = em.merge(oldDetalleEntregaCodigoOfCanjeListNewCanje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleEntrega.getCodigo();
                if (findDetalleEntrega(id) == null) {
                    throw new NonexistentEntityException("The detalleEntrega with id " + id + " no longer exists.");
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
            DetalleEntrega detalleEntrega;
            try {
                detalleEntrega = em.getReference(DetalleEntrega.class, id);
                detalleEntrega.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleEntrega with id " + id + " no longer exists.", enfe);
            }
            Compra compraEntregarCodigo = detalleEntrega.getCompraEntregarCodigo();
            if (compraEntregarCodigo != null) {
                compraEntregarCodigo.setDetalleEntregaCodigo(null);
                compraEntregarCodigo = em.merge(compraEntregarCodigo);
            }
            Ruta rutaCodigo = detalleEntrega.getRutaCodigo();
            if (rutaCodigo != null) {
                rutaCodigo.getDetalleEntregaList().remove(detalleEntrega);
                rutaCodigo = em.merge(rutaCodigo);
            }
            Canje canjeEntregarCodigo = detalleEntrega.getCanjeEntregarCodigo();
            if (canjeEntregarCodigo != null) {
                canjeEntregarCodigo.setDetalleEntregaCodigo(null);
                canjeEntregarCodigo = em.merge(canjeEntregarCodigo);
            }
            List<Compra> compraList = detalleEntrega.getCompraList();
            for (Compra compraListCompra : compraList) {
                compraListCompra.setDetalleEntregaCodigo(null);
                compraListCompra = em.merge(compraListCompra);
            }
            List<Canje> canjeList = detalleEntrega.getCanjeList();
            for (Canje canjeListCanje : canjeList) {
                canjeListCanje.setDetalleEntregaCodigo(null);
                canjeListCanje = em.merge(canjeListCanje);
            }
            em.remove(detalleEntrega);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleEntrega> findDetalleEntregaEntities() {
        return findDetalleEntregaEntities(true, -1, -1);
    }

    public List<DetalleEntrega> findDetalleEntregaEntities(int maxResults, int firstResult) {
        return findDetalleEntregaEntities(false, maxResults, firstResult);
    }

    private List<DetalleEntrega> findDetalleEntregaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleEntrega.class));
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

    public DetalleEntrega findDetalleEntrega(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleEntrega.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleEntregaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleEntrega> rt = cq.from(DetalleEntrega.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
