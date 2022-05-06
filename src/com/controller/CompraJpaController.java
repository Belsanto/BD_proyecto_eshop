/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Compra;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import com.entities.DetalleEntrega;
import com.entities.DetalleCompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        if (compra.getDetalleCompraList() == null) {
            compra.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        if (compra.getDetalleEntregaList() == null) {
            compra.setDetalleEntregaList(new ArrayList<DetalleEntrega>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario compradorCodigo = compra.getCompradorCodigo();
            if (compradorCodigo != null) {
                compradorCodigo = em.getReference(compradorCodigo.getClass(), compradorCodigo.getCodigo());
                compra.setCompradorCodigo(compradorCodigo);
            }
            DetalleEntrega detalleEntregaCodigo = compra.getDetalleEntregaCodigo();
            if (detalleEntregaCodigo != null) {
                detalleEntregaCodigo = em.getReference(detalleEntregaCodigo.getClass(), detalleEntregaCodigo.getCodigo());
                compra.setDetalleEntregaCodigo(detalleEntregaCodigo);
            }
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : compra.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getCodigo());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            compra.setDetalleCompraList(attachedDetalleCompraList);
            List<DetalleEntrega> attachedDetalleEntregaList = new ArrayList<DetalleEntrega>();
            for (DetalleEntrega detalleEntregaListDetalleEntregaToAttach : compra.getDetalleEntregaList()) {
                detalleEntregaListDetalleEntregaToAttach = em.getReference(detalleEntregaListDetalleEntregaToAttach.getClass(), detalleEntregaListDetalleEntregaToAttach.getCodigo());
                attachedDetalleEntregaList.add(detalleEntregaListDetalleEntregaToAttach);
            }
            compra.setDetalleEntregaList(attachedDetalleEntregaList);
            em.persist(compra);
            if (compradorCodigo != null) {
                compradorCodigo.getCompraList().add(compra);
                compradorCodigo = em.merge(compradorCodigo);
            }
            if (detalleEntregaCodigo != null) {
                detalleEntregaCodigo.getCompraList().add(compra);
                detalleEntregaCodigo = em.merge(detalleEntregaCodigo);
            }
            for (DetalleCompra detalleCompraListDetalleCompra : compra.getDetalleCompraList()) {
                Compra oldCompraCodigoOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getCompraCodigo();
                detalleCompraListDetalleCompra.setCompraCodigo(compra);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldCompraCodigoOfDetalleCompraListDetalleCompra != null) {
                    oldCompraCodigoOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldCompraCodigoOfDetalleCompraListDetalleCompra = em.merge(oldCompraCodigoOfDetalleCompraListDetalleCompra);
                }
            }
            for (DetalleEntrega detalleEntregaListDetalleEntrega : compra.getDetalleEntregaList()) {
                Compra oldCompraEntregarCodigoOfDetalleEntregaListDetalleEntrega = detalleEntregaListDetalleEntrega.getCompraEntregarCodigo();
                detalleEntregaListDetalleEntrega.setCompraEntregarCodigo(compra);
                detalleEntregaListDetalleEntrega = em.merge(detalleEntregaListDetalleEntrega);
                if (oldCompraEntregarCodigoOfDetalleEntregaListDetalleEntrega != null) {
                    oldCompraEntregarCodigoOfDetalleEntregaListDetalleEntrega.getDetalleEntregaList().remove(detalleEntregaListDetalleEntrega);
                    oldCompraEntregarCodigoOfDetalleEntregaListDetalleEntrega = em.merge(oldCompraEntregarCodigoOfDetalleEntregaListDetalleEntrega);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getCodigo());
            Usuario compradorCodigoOld = persistentCompra.getCompradorCodigo();
            Usuario compradorCodigoNew = compra.getCompradorCodigo();
            DetalleEntrega detalleEntregaCodigoOld = persistentCompra.getDetalleEntregaCodigo();
            DetalleEntrega detalleEntregaCodigoNew = compra.getDetalleEntregaCodigo();
            List<DetalleCompra> detalleCompraListOld = persistentCompra.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = compra.getDetalleCompraList();
            List<DetalleEntrega> detalleEntregaListOld = persistentCompra.getDetalleEntregaList();
            List<DetalleEntrega> detalleEntregaListNew = compra.getDetalleEntregaList();
            if (compradorCodigoNew != null) {
                compradorCodigoNew = em.getReference(compradorCodigoNew.getClass(), compradorCodigoNew.getCodigo());
                compra.setCompradorCodigo(compradorCodigoNew);
            }
            if (detalleEntregaCodigoNew != null) {
                detalleEntregaCodigoNew = em.getReference(detalleEntregaCodigoNew.getClass(), detalleEntregaCodigoNew.getCodigo());
                compra.setDetalleEntregaCodigo(detalleEntregaCodigoNew);
            }
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getCodigo());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            compra.setDetalleCompraList(detalleCompraListNew);
            List<DetalleEntrega> attachedDetalleEntregaListNew = new ArrayList<DetalleEntrega>();
            for (DetalleEntrega detalleEntregaListNewDetalleEntregaToAttach : detalleEntregaListNew) {
                detalleEntregaListNewDetalleEntregaToAttach = em.getReference(detalleEntregaListNewDetalleEntregaToAttach.getClass(), detalleEntregaListNewDetalleEntregaToAttach.getCodigo());
                attachedDetalleEntregaListNew.add(detalleEntregaListNewDetalleEntregaToAttach);
            }
            detalleEntregaListNew = attachedDetalleEntregaListNew;
            compra.setDetalleEntregaList(detalleEntregaListNew);
            compra = em.merge(compra);
            if (compradorCodigoOld != null && !compradorCodigoOld.equals(compradorCodigoNew)) {
                compradorCodigoOld.getCompraList().remove(compra);
                compradorCodigoOld = em.merge(compradorCodigoOld);
            }
            if (compradorCodigoNew != null && !compradorCodigoNew.equals(compradorCodigoOld)) {
                compradorCodigoNew.getCompraList().add(compra);
                compradorCodigoNew = em.merge(compradorCodigoNew);
            }
            if (detalleEntregaCodigoOld != null && !detalleEntregaCodigoOld.equals(detalleEntregaCodigoNew)) {
                detalleEntregaCodigoOld.getCompraList().remove(compra);
                detalleEntregaCodigoOld = em.merge(detalleEntregaCodigoOld);
            }
            if (detalleEntregaCodigoNew != null && !detalleEntregaCodigoNew.equals(detalleEntregaCodigoOld)) {
                detalleEntregaCodigoNew.getCompraList().add(compra);
                detalleEntregaCodigoNew = em.merge(detalleEntregaCodigoNew);
            }
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    detalleCompraListOldDetalleCompra.setCompraCodigo(null);
                    detalleCompraListOldDetalleCompra = em.merge(detalleCompraListOldDetalleCompra);
                }
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Compra oldCompraCodigoOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getCompraCodigo();
                    detalleCompraListNewDetalleCompra.setCompraCodigo(compra);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldCompraCodigoOfDetalleCompraListNewDetalleCompra != null && !oldCompraCodigoOfDetalleCompraListNewDetalleCompra.equals(compra)) {
                        oldCompraCodigoOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldCompraCodigoOfDetalleCompraListNewDetalleCompra = em.merge(oldCompraCodigoOfDetalleCompraListNewDetalleCompra);
                    }
                }
            }
            for (DetalleEntrega detalleEntregaListOldDetalleEntrega : detalleEntregaListOld) {
                if (!detalleEntregaListNew.contains(detalleEntregaListOldDetalleEntrega)) {
                    detalleEntregaListOldDetalleEntrega.setCompraEntregarCodigo(null);
                    detalleEntregaListOldDetalleEntrega = em.merge(detalleEntregaListOldDetalleEntrega);
                }
            }
            for (DetalleEntrega detalleEntregaListNewDetalleEntrega : detalleEntregaListNew) {
                if (!detalleEntregaListOld.contains(detalleEntregaListNewDetalleEntrega)) {
                    Compra oldCompraEntregarCodigoOfDetalleEntregaListNewDetalleEntrega = detalleEntregaListNewDetalleEntrega.getCompraEntregarCodigo();
                    detalleEntregaListNewDetalleEntrega.setCompraEntregarCodigo(compra);
                    detalleEntregaListNewDetalleEntrega = em.merge(detalleEntregaListNewDetalleEntrega);
                    if (oldCompraEntregarCodigoOfDetalleEntregaListNewDetalleEntrega != null && !oldCompraEntregarCodigoOfDetalleEntregaListNewDetalleEntrega.equals(compra)) {
                        oldCompraEntregarCodigoOfDetalleEntregaListNewDetalleEntrega.getDetalleEntregaList().remove(detalleEntregaListNewDetalleEntrega);
                        oldCompraEntregarCodigoOfDetalleEntregaListNewDetalleEntrega = em.merge(oldCompraEntregarCodigoOfDetalleEntregaListNewDetalleEntrega);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getCodigo();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            Usuario compradorCodigo = compra.getCompradorCodigo();
            if (compradorCodigo != null) {
                compradorCodigo.getCompraList().remove(compra);
                compradorCodigo = em.merge(compradorCodigo);
            }
            DetalleEntrega detalleEntregaCodigo = compra.getDetalleEntregaCodigo();
            if (detalleEntregaCodigo != null) {
                detalleEntregaCodigo.getCompraList().remove(compra);
                detalleEntregaCodigo = em.merge(detalleEntregaCodigo);
            }
            List<DetalleCompra> detalleCompraList = compra.getDetalleCompraList();
            for (DetalleCompra detalleCompraListDetalleCompra : detalleCompraList) {
                detalleCompraListDetalleCompra.setCompraCodigo(null);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
            }
            List<DetalleEntrega> detalleEntregaList = compra.getDetalleEntregaList();
            for (DetalleEntrega detalleEntregaListDetalleEntrega : detalleEntregaList) {
                detalleEntregaListDetalleEntrega.setCompraEntregarCodigo(null);
                detalleEntregaListDetalleEntrega = em.merge(detalleEntregaListDetalleEntrega);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
