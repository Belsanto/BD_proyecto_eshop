/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Delivery;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Pais;
import com.entities.Ruta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class DeliveryJpaController implements Serializable {

    public DeliveryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public DeliveryJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Delivery delivery) {
        if (delivery.getRutaCollection() == null) {
            delivery.setRutaCollection(new ArrayList<Ruta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisCodigo = delivery.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo = em.getReference(paisCodigo.getClass(), paisCodigo.getCodigo());
                delivery.setPaisCodigo(paisCodigo);
            }
            Collection<Ruta> attachedRutaCollection = new ArrayList<Ruta>();
            for (Ruta rutaCollectionRutaToAttach : delivery.getRutaCollection()) {
                rutaCollectionRutaToAttach = em.getReference(rutaCollectionRutaToAttach.getClass(), rutaCollectionRutaToAttach.getCodigo());
                attachedRutaCollection.add(rutaCollectionRutaToAttach);
            }
            delivery.setRutaCollection(attachedRutaCollection);
            em.persist(delivery);
            if (paisCodigo != null) {
                paisCodigo.getDeliveryCollection().add(delivery);
                paisCodigo = em.merge(paisCodigo);
            }
            for (Ruta rutaCollectionRuta : delivery.getRutaCollection()) {
                Delivery oldEmpresaCodigoOfRutaCollectionRuta = rutaCollectionRuta.getEmpresaCodigo();
                rutaCollectionRuta.setEmpresaCodigo(delivery);
                rutaCollectionRuta = em.merge(rutaCollectionRuta);
                if (oldEmpresaCodigoOfRutaCollectionRuta != null) {
                    oldEmpresaCodigoOfRutaCollectionRuta.getRutaCollection().remove(rutaCollectionRuta);
                    oldEmpresaCodigoOfRutaCollectionRuta = em.merge(oldEmpresaCodigoOfRutaCollectionRuta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Delivery delivery) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delivery persistentDelivery = em.find(Delivery.class, delivery.getCodigo());
            Pais paisCodigoOld = persistentDelivery.getPaisCodigo();
            Pais paisCodigoNew = delivery.getPaisCodigo();
            Collection<Ruta> rutaCollectionOld = persistentDelivery.getRutaCollection();
            Collection<Ruta> rutaCollectionNew = delivery.getRutaCollection();
            if (paisCodigoNew != null) {
                paisCodigoNew = em.getReference(paisCodigoNew.getClass(), paisCodigoNew.getCodigo());
                delivery.setPaisCodigo(paisCodigoNew);
            }
            Collection<Ruta> attachedRutaCollectionNew = new ArrayList<Ruta>();
            for (Ruta rutaCollectionNewRutaToAttach : rutaCollectionNew) {
                rutaCollectionNewRutaToAttach = em.getReference(rutaCollectionNewRutaToAttach.getClass(), rutaCollectionNewRutaToAttach.getCodigo());
                attachedRutaCollectionNew.add(rutaCollectionNewRutaToAttach);
            }
            rutaCollectionNew = attachedRutaCollectionNew;
            delivery.setRutaCollection(rutaCollectionNew);
            delivery = em.merge(delivery);
            if (paisCodigoOld != null && !paisCodigoOld.equals(paisCodigoNew)) {
                paisCodigoOld.getDeliveryCollection().remove(delivery);
                paisCodigoOld = em.merge(paisCodigoOld);
            }
            if (paisCodigoNew != null && !paisCodigoNew.equals(paisCodigoOld)) {
                paisCodigoNew.getDeliveryCollection().add(delivery);
                paisCodigoNew = em.merge(paisCodigoNew);
            }
            for (Ruta rutaCollectionOldRuta : rutaCollectionOld) {
                if (!rutaCollectionNew.contains(rutaCollectionOldRuta)) {
                    rutaCollectionOldRuta.setEmpresaCodigo(null);
                    rutaCollectionOldRuta = em.merge(rutaCollectionOldRuta);
                }
            }
            for (Ruta rutaCollectionNewRuta : rutaCollectionNew) {
                if (!rutaCollectionOld.contains(rutaCollectionNewRuta)) {
                    Delivery oldEmpresaCodigoOfRutaCollectionNewRuta = rutaCollectionNewRuta.getEmpresaCodigo();
                    rutaCollectionNewRuta.setEmpresaCodigo(delivery);
                    rutaCollectionNewRuta = em.merge(rutaCollectionNewRuta);
                    if (oldEmpresaCodigoOfRutaCollectionNewRuta != null && !oldEmpresaCodigoOfRutaCollectionNewRuta.equals(delivery)) {
                        oldEmpresaCodigoOfRutaCollectionNewRuta.getRutaCollection().remove(rutaCollectionNewRuta);
                        oldEmpresaCodigoOfRutaCollectionNewRuta = em.merge(oldEmpresaCodigoOfRutaCollectionNewRuta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = delivery.getCodigo();
                if (findDelivery(id) == null) {
                    throw new NonexistentEntityException("The delivery with id " + id + " no longer exists.");
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
            Delivery delivery;
            try {
                delivery = em.getReference(Delivery.class, id);
                delivery.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The delivery with id " + id + " no longer exists.", enfe);
            }
            Pais paisCodigo = delivery.getPaisCodigo();
            if (paisCodigo != null) {
                paisCodigo.getDeliveryCollection().remove(delivery);
                paisCodigo = em.merge(paisCodigo);
            }
            Collection<Ruta> rutaCollection = delivery.getRutaCollection();
            for (Ruta rutaCollectionRuta : rutaCollection) {
                rutaCollectionRuta.setEmpresaCodigo(null);
                rutaCollectionRuta = em.merge(rutaCollectionRuta);
            }
            em.remove(delivery);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Delivery> findDeliveryEntities() {
        return findDeliveryEntities(true, -1, -1);
    }

    public List<Delivery> findDeliveryEntities(int maxResults, int firstResult) {
        return findDeliveryEntities(false, maxResults, firstResult);
    }

    private List<Delivery> findDeliveryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Delivery.class));
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

    public Delivery findDelivery(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Delivery.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeliveryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Delivery> rt = cq.from(Delivery.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
