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
import com.entities.Delivery;
import java.util.ArrayList;
import java.util.Collection;
import com.entities.Ciudad;
import com.entities.Pais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PaisJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getDeliveryCollection() == null) {
            pais.setDeliveryCollection(new ArrayList<Delivery>());
        }
        if (pais.getCiudadCollection() == null) {
            pais.setCiudadCollection(new ArrayList<Ciudad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Delivery> attachedDeliveryCollection = new ArrayList<Delivery>();
            for (Delivery deliveryCollectionDeliveryToAttach : pais.getDeliveryCollection()) {
                deliveryCollectionDeliveryToAttach = em.getReference(deliveryCollectionDeliveryToAttach.getClass(), deliveryCollectionDeliveryToAttach.getCodigo());
                attachedDeliveryCollection.add(deliveryCollectionDeliveryToAttach);
            }
            pais.setDeliveryCollection(attachedDeliveryCollection);
            Collection<Ciudad> attachedCiudadCollection = new ArrayList<Ciudad>();
            for (Ciudad ciudadCollectionCiudadToAttach : pais.getCiudadCollection()) {
                ciudadCollectionCiudadToAttach = em.getReference(ciudadCollectionCiudadToAttach.getClass(), ciudadCollectionCiudadToAttach.getCodigo());
                attachedCiudadCollection.add(ciudadCollectionCiudadToAttach);
            }
            pais.setCiudadCollection(attachedCiudadCollection);
            em.persist(pais);
            for (Delivery deliveryCollectionDelivery : pais.getDeliveryCollection()) {
                Pais oldPaisCodigoOfDeliveryCollectionDelivery = deliveryCollectionDelivery.getPaisCodigo();
                deliveryCollectionDelivery.setPaisCodigo(pais);
                deliveryCollectionDelivery = em.merge(deliveryCollectionDelivery);
                if (oldPaisCodigoOfDeliveryCollectionDelivery != null) {
                    oldPaisCodigoOfDeliveryCollectionDelivery.getDeliveryCollection().remove(deliveryCollectionDelivery);
                    oldPaisCodigoOfDeliveryCollectionDelivery = em.merge(oldPaisCodigoOfDeliveryCollectionDelivery);
                }
            }
            for (Ciudad ciudadCollectionCiudad : pais.getCiudadCollection()) {
                Pais oldPaisCodigoOfCiudadCollectionCiudad = ciudadCollectionCiudad.getPaisCodigo();
                ciudadCollectionCiudad.setPaisCodigo(pais);
                ciudadCollectionCiudad = em.merge(ciudadCollectionCiudad);
                if (oldPaisCodigoOfCiudadCollectionCiudad != null) {
                    oldPaisCodigoOfCiudadCollectionCiudad.getCiudadCollection().remove(ciudadCollectionCiudad);
                    oldPaisCodigoOfCiudadCollectionCiudad = em.merge(oldPaisCodigoOfCiudadCollectionCiudad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getCodigo());
            Collection<Delivery> deliveryCollectionOld = persistentPais.getDeliveryCollection();
            Collection<Delivery> deliveryCollectionNew = pais.getDeliveryCollection();
            Collection<Ciudad> ciudadCollectionOld = persistentPais.getCiudadCollection();
            Collection<Ciudad> ciudadCollectionNew = pais.getCiudadCollection();
            List<String> illegalOrphanMessages = null;
            for (Delivery deliveryCollectionOldDelivery : deliveryCollectionOld) {
                if (!deliveryCollectionNew.contains(deliveryCollectionOldDelivery)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Delivery " + deliveryCollectionOldDelivery + " since its paisCodigo field is not nullable.");
                }
            }
            for (Ciudad ciudadCollectionOldCiudad : ciudadCollectionOld) {
                if (!ciudadCollectionNew.contains(ciudadCollectionOldCiudad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ciudad " + ciudadCollectionOldCiudad + " since its paisCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Delivery> attachedDeliveryCollectionNew = new ArrayList<Delivery>();
            for (Delivery deliveryCollectionNewDeliveryToAttach : deliveryCollectionNew) {
                deliveryCollectionNewDeliveryToAttach = em.getReference(deliveryCollectionNewDeliveryToAttach.getClass(), deliveryCollectionNewDeliveryToAttach.getCodigo());
                attachedDeliveryCollectionNew.add(deliveryCollectionNewDeliveryToAttach);
            }
            deliveryCollectionNew = attachedDeliveryCollectionNew;
            pais.setDeliveryCollection(deliveryCollectionNew);
            Collection<Ciudad> attachedCiudadCollectionNew = new ArrayList<Ciudad>();
            for (Ciudad ciudadCollectionNewCiudadToAttach : ciudadCollectionNew) {
                ciudadCollectionNewCiudadToAttach = em.getReference(ciudadCollectionNewCiudadToAttach.getClass(), ciudadCollectionNewCiudadToAttach.getCodigo());
                attachedCiudadCollectionNew.add(ciudadCollectionNewCiudadToAttach);
            }
            ciudadCollectionNew = attachedCiudadCollectionNew;
            pais.setCiudadCollection(ciudadCollectionNew);
            pais = em.merge(pais);
            for (Delivery deliveryCollectionNewDelivery : deliveryCollectionNew) {
                if (!deliveryCollectionOld.contains(deliveryCollectionNewDelivery)) {
                    Pais oldPaisCodigoOfDeliveryCollectionNewDelivery = deliveryCollectionNewDelivery.getPaisCodigo();
                    deliveryCollectionNewDelivery.setPaisCodigo(pais);
                    deliveryCollectionNewDelivery = em.merge(deliveryCollectionNewDelivery);
                    if (oldPaisCodigoOfDeliveryCollectionNewDelivery != null && !oldPaisCodigoOfDeliveryCollectionNewDelivery.equals(pais)) {
                        oldPaisCodigoOfDeliveryCollectionNewDelivery.getDeliveryCollection().remove(deliveryCollectionNewDelivery);
                        oldPaisCodigoOfDeliveryCollectionNewDelivery = em.merge(oldPaisCodigoOfDeliveryCollectionNewDelivery);
                    }
                }
            }
            for (Ciudad ciudadCollectionNewCiudad : ciudadCollectionNew) {
                if (!ciudadCollectionOld.contains(ciudadCollectionNewCiudad)) {
                    Pais oldPaisCodigoOfCiudadCollectionNewCiudad = ciudadCollectionNewCiudad.getPaisCodigo();
                    ciudadCollectionNewCiudad.setPaisCodigo(pais);
                    ciudadCollectionNewCiudad = em.merge(ciudadCollectionNewCiudad);
                    if (oldPaisCodigoOfCiudadCollectionNewCiudad != null && !oldPaisCodigoOfCiudadCollectionNewCiudad.equals(pais)) {
                        oldPaisCodigoOfCiudadCollectionNewCiudad.getCiudadCollection().remove(ciudadCollectionNewCiudad);
                        oldPaisCodigoOfCiudadCollectionNewCiudad = em.merge(oldPaisCodigoOfCiudadCollectionNewCiudad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getCodigo();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Delivery> deliveryCollectionOrphanCheck = pais.getDeliveryCollection();
            for (Delivery deliveryCollectionOrphanCheckDelivery : deliveryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Delivery " + deliveryCollectionOrphanCheckDelivery + " in its deliveryCollection field has a non-nullable paisCodigo field.");
            }
            Collection<Ciudad> ciudadCollectionOrphanCheck = pais.getCiudadCollection();
            for (Ciudad ciudadCollectionOrphanCheckCiudad : ciudadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Ciudad " + ciudadCollectionOrphanCheckCiudad + " in its ciudadCollection field has a non-nullable paisCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
