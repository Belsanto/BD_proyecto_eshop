/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Canje;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import com.entities.DetalleEntrega;
import com.entities.DetalleCanje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author EQUIPO
 */
public class CanjeJpaController implements Serializable {

    public CanjeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CanjeJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Canje canje) {
        if (canje.getDetalleCanjeList() == null) {
            canje.setDetalleCanjeList(new ArrayList<DetalleCanje>());
        }
        if (canje.getDetalleEntregaList() == null) {
            canje.setDetalleEntregaList(new ArrayList<DetalleEntrega>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario clienteCodigo = canje.getClienteCodigo();
            if (clienteCodigo != null) {
                clienteCodigo = em.getReference(clienteCodigo.getClass(), clienteCodigo.getCodigo());
                canje.setClienteCodigo(clienteCodigo);
            }
            DetalleEntrega detalleEntregaCodigo = canje.getDetalleEntregaCodigo();
            if (detalleEntregaCodigo != null) {
                detalleEntregaCodigo = em.getReference(detalleEntregaCodigo.getClass(), detalleEntregaCodigo.getCodigo());
                canje.setDetalleEntregaCodigo(detalleEntregaCodigo);
            }
            List<DetalleCanje> attachedDetalleCanjeList = new ArrayList<DetalleCanje>();
            for (DetalleCanje detalleCanjeListDetalleCanjeToAttach : canje.getDetalleCanjeList()) {
                detalleCanjeListDetalleCanjeToAttach = em.getReference(detalleCanjeListDetalleCanjeToAttach.getClass(), detalleCanjeListDetalleCanjeToAttach.getCodigo());
                attachedDetalleCanjeList.add(detalleCanjeListDetalleCanjeToAttach);
            }
            canje.setDetalleCanjeList(attachedDetalleCanjeList);
            List<DetalleEntrega> attachedDetalleEntregaList = new ArrayList<DetalleEntrega>();
            for (DetalleEntrega detalleEntregaListDetalleEntregaToAttach : canje.getDetalleEntregaList()) {
                detalleEntregaListDetalleEntregaToAttach = em.getReference(detalleEntregaListDetalleEntregaToAttach.getClass(), detalleEntregaListDetalleEntregaToAttach.getCodigo());
                attachedDetalleEntregaList.add(detalleEntregaListDetalleEntregaToAttach);
            }
            canje.setDetalleEntregaList(attachedDetalleEntregaList);
            em.persist(canje);
            if (clienteCodigo != null) {
                clienteCodigo.getCanjeList().add(canje);
                clienteCodigo = em.merge(clienteCodigo);
            }
            if (detalleEntregaCodigo != null) {
                detalleEntregaCodigo.getCanjeList().add(canje);
                detalleEntregaCodigo = em.merge(detalleEntregaCodigo);
            }
            for (DetalleCanje detalleCanjeListDetalleCanje : canje.getDetalleCanjeList()) {
                Canje oldCanjeCodigoOfDetalleCanjeListDetalleCanje = detalleCanjeListDetalleCanje.getCanjeCodigo();
                detalleCanjeListDetalleCanje.setCanjeCodigo(canje);
                detalleCanjeListDetalleCanje = em.merge(detalleCanjeListDetalleCanje);
                if (oldCanjeCodigoOfDetalleCanjeListDetalleCanje != null) {
                    oldCanjeCodigoOfDetalleCanjeListDetalleCanje.getDetalleCanjeList().remove(detalleCanjeListDetalleCanje);
                    oldCanjeCodigoOfDetalleCanjeListDetalleCanje = em.merge(oldCanjeCodigoOfDetalleCanjeListDetalleCanje);
                }
            }
            for (DetalleEntrega detalleEntregaListDetalleEntrega : canje.getDetalleEntregaList()) {
                Canje oldCanjeEntregarCodigoOfDetalleEntregaListDetalleEntrega = detalleEntregaListDetalleEntrega.getCanjeEntregarCodigo();
                detalleEntregaListDetalleEntrega.setCanjeEntregarCodigo(canje);
                detalleEntregaListDetalleEntrega = em.merge(detalleEntregaListDetalleEntrega);
                if (oldCanjeEntregarCodigoOfDetalleEntregaListDetalleEntrega != null) {
                    oldCanjeEntregarCodigoOfDetalleEntregaListDetalleEntrega.getDetalleEntregaList().remove(detalleEntregaListDetalleEntrega);
                    oldCanjeEntregarCodigoOfDetalleEntregaListDetalleEntrega = em.merge(oldCanjeEntregarCodigoOfDetalleEntregaListDetalleEntrega);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Canje canje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Canje persistentCanje = em.find(Canje.class, canje.getCodigo());
            Usuario clienteCodigoOld = persistentCanje.getClienteCodigo();
            Usuario clienteCodigoNew = canje.getClienteCodigo();
            DetalleEntrega detalleEntregaCodigoOld = persistentCanje.getDetalleEntregaCodigo();
            DetalleEntrega detalleEntregaCodigoNew = canje.getDetalleEntregaCodigo();
            List<DetalleCanje> detalleCanjeListOld = persistentCanje.getDetalleCanjeList();
            List<DetalleCanje> detalleCanjeListNew = canje.getDetalleCanjeList();
            List<DetalleEntrega> detalleEntregaListOld = persistentCanje.getDetalleEntregaList();
            List<DetalleEntrega> detalleEntregaListNew = canje.getDetalleEntregaList();
            if (clienteCodigoNew != null) {
                clienteCodigoNew = em.getReference(clienteCodigoNew.getClass(), clienteCodigoNew.getCodigo());
                canje.setClienteCodigo(clienteCodigoNew);
            }
            if (detalleEntregaCodigoNew != null) {
                detalleEntregaCodigoNew = em.getReference(detalleEntregaCodigoNew.getClass(), detalleEntregaCodigoNew.getCodigo());
                canje.setDetalleEntregaCodigo(detalleEntregaCodigoNew);
            }
            List<DetalleCanje> attachedDetalleCanjeListNew = new ArrayList<DetalleCanje>();
            for (DetalleCanje detalleCanjeListNewDetalleCanjeToAttach : detalleCanjeListNew) {
                detalleCanjeListNewDetalleCanjeToAttach = em.getReference(detalleCanjeListNewDetalleCanjeToAttach.getClass(), detalleCanjeListNewDetalleCanjeToAttach.getCodigo());
                attachedDetalleCanjeListNew.add(detalleCanjeListNewDetalleCanjeToAttach);
            }
            detalleCanjeListNew = attachedDetalleCanjeListNew;
            canje.setDetalleCanjeList(detalleCanjeListNew);
            List<DetalleEntrega> attachedDetalleEntregaListNew = new ArrayList<DetalleEntrega>();
            for (DetalleEntrega detalleEntregaListNewDetalleEntregaToAttach : detalleEntregaListNew) {
                detalleEntregaListNewDetalleEntregaToAttach = em.getReference(detalleEntregaListNewDetalleEntregaToAttach.getClass(), detalleEntregaListNewDetalleEntregaToAttach.getCodigo());
                attachedDetalleEntregaListNew.add(detalleEntregaListNewDetalleEntregaToAttach);
            }
            detalleEntregaListNew = attachedDetalleEntregaListNew;
            canje.setDetalleEntregaList(detalleEntregaListNew);
            canje = em.merge(canje);
            if (clienteCodigoOld != null && !clienteCodigoOld.equals(clienteCodigoNew)) {
                clienteCodigoOld.getCanjeList().remove(canje);
                clienteCodigoOld = em.merge(clienteCodigoOld);
            }
            if (clienteCodigoNew != null && !clienteCodigoNew.equals(clienteCodigoOld)) {
                clienteCodigoNew.getCanjeList().add(canje);
                clienteCodigoNew = em.merge(clienteCodigoNew);
            }
            if (detalleEntregaCodigoOld != null && !detalleEntregaCodigoOld.equals(detalleEntregaCodigoNew)) {
                detalleEntregaCodigoOld.getCanjeList().remove(canje);
                detalleEntregaCodigoOld = em.merge(detalleEntregaCodigoOld);
            }
            if (detalleEntregaCodigoNew != null && !detalleEntregaCodigoNew.equals(detalleEntregaCodigoOld)) {
                detalleEntregaCodigoNew.getCanjeList().add(canje);
                detalleEntregaCodigoNew = em.merge(detalleEntregaCodigoNew);
            }
            for (DetalleCanje detalleCanjeListOldDetalleCanje : detalleCanjeListOld) {
                if (!detalleCanjeListNew.contains(detalleCanjeListOldDetalleCanje)) {
                    detalleCanjeListOldDetalleCanje.setCanjeCodigo(null);
                    detalleCanjeListOldDetalleCanje = em.merge(detalleCanjeListOldDetalleCanje);
                }
            }
            for (DetalleCanje detalleCanjeListNewDetalleCanje : detalleCanjeListNew) {
                if (!detalleCanjeListOld.contains(detalleCanjeListNewDetalleCanje)) {
                    Canje oldCanjeCodigoOfDetalleCanjeListNewDetalleCanje = detalleCanjeListNewDetalleCanje.getCanjeCodigo();
                    detalleCanjeListNewDetalleCanje.setCanjeCodigo(canje);
                    detalleCanjeListNewDetalleCanje = em.merge(detalleCanjeListNewDetalleCanje);
                    if (oldCanjeCodigoOfDetalleCanjeListNewDetalleCanje != null && !oldCanjeCodigoOfDetalleCanjeListNewDetalleCanje.equals(canje)) {
                        oldCanjeCodigoOfDetalleCanjeListNewDetalleCanje.getDetalleCanjeList().remove(detalleCanjeListNewDetalleCanje);
                        oldCanjeCodigoOfDetalleCanjeListNewDetalleCanje = em.merge(oldCanjeCodigoOfDetalleCanjeListNewDetalleCanje);
                    }
                }
            }
            for (DetalleEntrega detalleEntregaListOldDetalleEntrega : detalleEntregaListOld) {
                if (!detalleEntregaListNew.contains(detalleEntregaListOldDetalleEntrega)) {
                    detalleEntregaListOldDetalleEntrega.setCanjeEntregarCodigo(null);
                    detalleEntregaListOldDetalleEntrega = em.merge(detalleEntregaListOldDetalleEntrega);
                }
            }
            for (DetalleEntrega detalleEntregaListNewDetalleEntrega : detalleEntregaListNew) {
                if (!detalleEntregaListOld.contains(detalleEntregaListNewDetalleEntrega)) {
                    Canje oldCanjeEntregarCodigoOfDetalleEntregaListNewDetalleEntrega = detalleEntregaListNewDetalleEntrega.getCanjeEntregarCodigo();
                    detalleEntregaListNewDetalleEntrega.setCanjeEntregarCodigo(canje);
                    detalleEntregaListNewDetalleEntrega = em.merge(detalleEntregaListNewDetalleEntrega);
                    if (oldCanjeEntregarCodigoOfDetalleEntregaListNewDetalleEntrega != null && !oldCanjeEntregarCodigoOfDetalleEntregaListNewDetalleEntrega.equals(canje)) {
                        oldCanjeEntregarCodigoOfDetalleEntregaListNewDetalleEntrega.getDetalleEntregaList().remove(detalleEntregaListNewDetalleEntrega);
                        oldCanjeEntregarCodigoOfDetalleEntregaListNewDetalleEntrega = em.merge(oldCanjeEntregarCodigoOfDetalleEntregaListNewDetalleEntrega);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = canje.getCodigo();
                if (findCanje(id) == null) {
                    throw new NonexistentEntityException("The canje with id " + id + " no longer exists.");
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
            Canje canje;
            try {
                canje = em.getReference(Canje.class, id);
                canje.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canje with id " + id + " no longer exists.", enfe);
            }
            Usuario clienteCodigo = canje.getClienteCodigo();
            if (clienteCodigo != null) {
                clienteCodigo.getCanjeList().remove(canje);
                clienteCodigo = em.merge(clienteCodigo);
            }
            DetalleEntrega detalleEntregaCodigo = canje.getDetalleEntregaCodigo();
            if (detalleEntregaCodigo != null) {
                detalleEntregaCodigo.getCanjeList().remove(canje);
                detalleEntregaCodigo = em.merge(detalleEntregaCodigo);
            }
            List<DetalleCanje> detalleCanjeList = canje.getDetalleCanjeList();
            for (DetalleCanje detalleCanjeListDetalleCanje : detalleCanjeList) {
                detalleCanjeListDetalleCanje.setCanjeCodigo(null);
                detalleCanjeListDetalleCanje = em.merge(detalleCanjeListDetalleCanje);
            }
            List<DetalleEntrega> detalleEntregaList = canje.getDetalleEntregaList();
            for (DetalleEntrega detalleEntregaListDetalleEntrega : detalleEntregaList) {
                detalleEntregaListDetalleEntrega.setCanjeEntregarCodigo(null);
                detalleEntregaListDetalleEntrega = em.merge(detalleEntregaListDetalleEntrega);
            }
            em.remove(canje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Canje> findCanjeEntities() {
        return findCanjeEntities(true, -1, -1);
    }

    public List<Canje> findCanjeEntities(int maxResults, int firstResult) {
        return findCanjeEntities(false, maxResults, firstResult);
    }

    private List<Canje> findCanjeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Canje.class));
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

    public Canje findCanje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Canje.class, id);
        } finally {
            em.close();
        }
    }

    public int getCanjeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Canje> rt = cq.from(Canje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
