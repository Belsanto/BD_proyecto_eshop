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
import java.util.List;
import com.entities.Ciudad;
import com.entities.Pais;
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
        if (pais.getDeliveryList() == null) {
            pais.setDeliveryList(new ArrayList<Delivery>());
        }
        if (pais.getCiudadList() == null) {
            pais.setCiudadList(new ArrayList<Ciudad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Delivery> attachedDeliveryList = new ArrayList<Delivery>();
            for (Delivery deliveryListDeliveryToAttach : pais.getDeliveryList()) {
                deliveryListDeliveryToAttach = em.getReference(deliveryListDeliveryToAttach.getClass(), deliveryListDeliveryToAttach.getCodigo());
                attachedDeliveryList.add(deliveryListDeliveryToAttach);
            }
            pais.setDeliveryList(attachedDeliveryList);
            List<Ciudad> attachedCiudadList = new ArrayList<Ciudad>();
            for (Ciudad ciudadListCiudadToAttach : pais.getCiudadList()) {
                ciudadListCiudadToAttach = em.getReference(ciudadListCiudadToAttach.getClass(), ciudadListCiudadToAttach.getCodigo());
                attachedCiudadList.add(ciudadListCiudadToAttach);
            }
            pais.setCiudadList(attachedCiudadList);
            em.persist(pais);
            for (Delivery deliveryListDelivery : pais.getDeliveryList()) {
                Pais oldPaisCodigoOfDeliveryListDelivery = deliveryListDelivery.getPaisCodigo();
                deliveryListDelivery.setPaisCodigo(pais);
                deliveryListDelivery = em.merge(deliveryListDelivery);
                if (oldPaisCodigoOfDeliveryListDelivery != null) {
                    oldPaisCodigoOfDeliveryListDelivery.getDeliveryList().remove(deliveryListDelivery);
                    oldPaisCodigoOfDeliveryListDelivery = em.merge(oldPaisCodigoOfDeliveryListDelivery);
                }
            }
            for (Ciudad ciudadListCiudad : pais.getCiudadList()) {
                Pais oldPaisCodigoOfCiudadListCiudad = ciudadListCiudad.getPaisCodigo();
                ciudadListCiudad.setPaisCodigo(pais);
                ciudadListCiudad = em.merge(ciudadListCiudad);
                if (oldPaisCodigoOfCiudadListCiudad != null) {
                    oldPaisCodigoOfCiudadListCiudad.getCiudadList().remove(ciudadListCiudad);
                    oldPaisCodigoOfCiudadListCiudad = em.merge(oldPaisCodigoOfCiudadListCiudad);
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
            List<Delivery> deliveryListOld = persistentPais.getDeliveryList();
            List<Delivery> deliveryListNew = pais.getDeliveryList();
            List<Ciudad> ciudadListOld = persistentPais.getCiudadList();
            List<Ciudad> ciudadListNew = pais.getCiudadList();
            List<String> illegalOrphanMessages = null;
            for (Delivery deliveryListOldDelivery : deliveryListOld) {
                if (!deliveryListNew.contains(deliveryListOldDelivery)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Delivery " + deliveryListOldDelivery + " since its paisCodigo field is not nullable.");
                }
            }
            for (Ciudad ciudadListOldCiudad : ciudadListOld) {
                if (!ciudadListNew.contains(ciudadListOldCiudad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ciudad " + ciudadListOldCiudad + " since its paisCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Delivery> attachedDeliveryListNew = new ArrayList<Delivery>();
            for (Delivery deliveryListNewDeliveryToAttach : deliveryListNew) {
                deliveryListNewDeliveryToAttach = em.getReference(deliveryListNewDeliveryToAttach.getClass(), deliveryListNewDeliveryToAttach.getCodigo());
                attachedDeliveryListNew.add(deliveryListNewDeliveryToAttach);
            }
            deliveryListNew = attachedDeliveryListNew;
            pais.setDeliveryList(deliveryListNew);
            List<Ciudad> attachedCiudadListNew = new ArrayList<Ciudad>();
            for (Ciudad ciudadListNewCiudadToAttach : ciudadListNew) {
                ciudadListNewCiudadToAttach = em.getReference(ciudadListNewCiudadToAttach.getClass(), ciudadListNewCiudadToAttach.getCodigo());
                attachedCiudadListNew.add(ciudadListNewCiudadToAttach);
            }
            ciudadListNew = attachedCiudadListNew;
            pais.setCiudadList(ciudadListNew);
            pais = em.merge(pais);
            for (Delivery deliveryListNewDelivery : deliveryListNew) {
                if (!deliveryListOld.contains(deliveryListNewDelivery)) {
                    Pais oldPaisCodigoOfDeliveryListNewDelivery = deliveryListNewDelivery.getPaisCodigo();
                    deliveryListNewDelivery.setPaisCodigo(pais);
                    deliveryListNewDelivery = em.merge(deliveryListNewDelivery);
                    if (oldPaisCodigoOfDeliveryListNewDelivery != null && !oldPaisCodigoOfDeliveryListNewDelivery.equals(pais)) {
                        oldPaisCodigoOfDeliveryListNewDelivery.getDeliveryList().remove(deliveryListNewDelivery);
                        oldPaisCodigoOfDeliveryListNewDelivery = em.merge(oldPaisCodigoOfDeliveryListNewDelivery);
                    }
                }
            }
            for (Ciudad ciudadListNewCiudad : ciudadListNew) {
                if (!ciudadListOld.contains(ciudadListNewCiudad)) {
                    Pais oldPaisCodigoOfCiudadListNewCiudad = ciudadListNewCiudad.getPaisCodigo();
                    ciudadListNewCiudad.setPaisCodigo(pais);
                    ciudadListNewCiudad = em.merge(ciudadListNewCiudad);
                    if (oldPaisCodigoOfCiudadListNewCiudad != null && !oldPaisCodigoOfCiudadListNewCiudad.equals(pais)) {
                        oldPaisCodigoOfCiudadListNewCiudad.getCiudadList().remove(ciudadListNewCiudad);
                        oldPaisCodigoOfCiudadListNewCiudad = em.merge(oldPaisCodigoOfCiudadListNewCiudad);
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
            List<Delivery> deliveryListOrphanCheck = pais.getDeliveryList();
            for (Delivery deliveryListOrphanCheckDelivery : deliveryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Delivery " + deliveryListOrphanCheckDelivery + " in its deliveryList field has a non-nullable paisCodigo field.");
            }
            List<Ciudad> ciudadListOrphanCheck = pais.getCiudadList();
            for (Ciudad ciudadListOrphanCheckCiudad : ciudadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Ciudad " + ciudadListOrphanCheckCiudad + " in its ciudadList field has a non-nullable paisCodigo field.");
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
