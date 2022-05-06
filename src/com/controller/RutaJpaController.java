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
import com.entities.Delivery;
import com.entities.DetalleEntrega;
import com.entities.Ruta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class RutaJpaController implements Serializable {

    public RutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ruta ruta) {
        if (ruta.getDetalleEntregaList() == null) {
            ruta.setDetalleEntregaList(new ArrayList<DetalleEntrega>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delivery empresaCodigo = ruta.getEmpresaCodigo();
            if (empresaCodigo != null) {
                empresaCodigo = em.getReference(empresaCodigo.getClass(), empresaCodigo.getCodigo());
                ruta.setEmpresaCodigo(empresaCodigo);
            }
            List<DetalleEntrega> attachedDetalleEntregaList = new ArrayList<DetalleEntrega>();
            for (DetalleEntrega detalleEntregaListDetalleEntregaToAttach : ruta.getDetalleEntregaList()) {
                detalleEntregaListDetalleEntregaToAttach = em.getReference(detalleEntregaListDetalleEntregaToAttach.getClass(), detalleEntregaListDetalleEntregaToAttach.getCodigo());
                attachedDetalleEntregaList.add(detalleEntregaListDetalleEntregaToAttach);
            }
            ruta.setDetalleEntregaList(attachedDetalleEntregaList);
            em.persist(ruta);
            if (empresaCodigo != null) {
                empresaCodigo.getRutaList().add(ruta);
                empresaCodigo = em.merge(empresaCodigo);
            }
            for (DetalleEntrega detalleEntregaListDetalleEntrega : ruta.getDetalleEntregaList()) {
                Ruta oldRutaCodigoOfDetalleEntregaListDetalleEntrega = detalleEntregaListDetalleEntrega.getRutaCodigo();
                detalleEntregaListDetalleEntrega.setRutaCodigo(ruta);
                detalleEntregaListDetalleEntrega = em.merge(detalleEntregaListDetalleEntrega);
                if (oldRutaCodigoOfDetalleEntregaListDetalleEntrega != null) {
                    oldRutaCodigoOfDetalleEntregaListDetalleEntrega.getDetalleEntregaList().remove(detalleEntregaListDetalleEntrega);
                    oldRutaCodigoOfDetalleEntregaListDetalleEntrega = em.merge(oldRutaCodigoOfDetalleEntregaListDetalleEntrega);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ruta ruta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta persistentRuta = em.find(Ruta.class, ruta.getCodigo());
            Delivery empresaCodigoOld = persistentRuta.getEmpresaCodigo();
            Delivery empresaCodigoNew = ruta.getEmpresaCodigo();
            List<DetalleEntrega> detalleEntregaListOld = persistentRuta.getDetalleEntregaList();
            List<DetalleEntrega> detalleEntregaListNew = ruta.getDetalleEntregaList();
            if (empresaCodigoNew != null) {
                empresaCodigoNew = em.getReference(empresaCodigoNew.getClass(), empresaCodigoNew.getCodigo());
                ruta.setEmpresaCodigo(empresaCodigoNew);
            }
            List<DetalleEntrega> attachedDetalleEntregaListNew = new ArrayList<DetalleEntrega>();
            for (DetalleEntrega detalleEntregaListNewDetalleEntregaToAttach : detalleEntregaListNew) {
                detalleEntregaListNewDetalleEntregaToAttach = em.getReference(detalleEntregaListNewDetalleEntregaToAttach.getClass(), detalleEntregaListNewDetalleEntregaToAttach.getCodigo());
                attachedDetalleEntregaListNew.add(detalleEntregaListNewDetalleEntregaToAttach);
            }
            detalleEntregaListNew = attachedDetalleEntregaListNew;
            ruta.setDetalleEntregaList(detalleEntregaListNew);
            ruta = em.merge(ruta);
            if (empresaCodigoOld != null && !empresaCodigoOld.equals(empresaCodigoNew)) {
                empresaCodigoOld.getRutaList().remove(ruta);
                empresaCodigoOld = em.merge(empresaCodigoOld);
            }
            if (empresaCodigoNew != null && !empresaCodigoNew.equals(empresaCodigoOld)) {
                empresaCodigoNew.getRutaList().add(ruta);
                empresaCodigoNew = em.merge(empresaCodigoNew);
            }
            for (DetalleEntrega detalleEntregaListOldDetalleEntrega : detalleEntregaListOld) {
                if (!detalleEntregaListNew.contains(detalleEntregaListOldDetalleEntrega)) {
                    detalleEntregaListOldDetalleEntrega.setRutaCodigo(null);
                    detalleEntregaListOldDetalleEntrega = em.merge(detalleEntregaListOldDetalleEntrega);
                }
            }
            for (DetalleEntrega detalleEntregaListNewDetalleEntrega : detalleEntregaListNew) {
                if (!detalleEntregaListOld.contains(detalleEntregaListNewDetalleEntrega)) {
                    Ruta oldRutaCodigoOfDetalleEntregaListNewDetalleEntrega = detalleEntregaListNewDetalleEntrega.getRutaCodigo();
                    detalleEntregaListNewDetalleEntrega.setRutaCodigo(ruta);
                    detalleEntregaListNewDetalleEntrega = em.merge(detalleEntregaListNewDetalleEntrega);
                    if (oldRutaCodigoOfDetalleEntregaListNewDetalleEntrega != null && !oldRutaCodigoOfDetalleEntregaListNewDetalleEntrega.equals(ruta)) {
                        oldRutaCodigoOfDetalleEntregaListNewDetalleEntrega.getDetalleEntregaList().remove(detalleEntregaListNewDetalleEntrega);
                        oldRutaCodigoOfDetalleEntregaListNewDetalleEntrega = em.merge(oldRutaCodigoOfDetalleEntregaListNewDetalleEntrega);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ruta.getCodigo();
                if (findRuta(id) == null) {
                    throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.");
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
            Ruta ruta;
            try {
                ruta = em.getReference(Ruta.class, id);
                ruta.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.", enfe);
            }
            Delivery empresaCodigo = ruta.getEmpresaCodigo();
            if (empresaCodigo != null) {
                empresaCodigo.getRutaList().remove(ruta);
                empresaCodigo = em.merge(empresaCodigo);
            }
            List<DetalleEntrega> detalleEntregaList = ruta.getDetalleEntregaList();
            for (DetalleEntrega detalleEntregaListDetalleEntrega : detalleEntregaList) {
                detalleEntregaListDetalleEntrega.setRutaCodigo(null);
                detalleEntregaListDetalleEntrega = em.merge(detalleEntregaListDetalleEntrega);
            }
            em.remove(ruta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ruta> findRutaEntities() {
        return findRutaEntities(true, -1, -1);
    }

    public List<Ruta> findRutaEntities(int maxResults, int firstResult) {
        return findRutaEntities(false, maxResults, firstResult);
    }

    private List<Ruta> findRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ruta.class));
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

    public Ruta findRuta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ruta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ruta> rt = cq.from(Ruta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
