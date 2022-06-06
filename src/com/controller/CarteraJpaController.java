/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.controller.exceptions.PreexistingEntityException;
import com.entities.Cartera;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import com.entities.Puntos;
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
public class CarteraJpaController implements Serializable {

    public CarteraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public CarteraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("eShop_BDPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cartera cartera) throws PreexistingEntityException, Exception {
        if (cartera.getPuntosList() == null) {
            cartera.setPuntosList(new ArrayList<Puntos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioCarteraCodigo = cartera.getUsuarioCarteraCodigo();
            if (usuarioCarteraCodigo != null) {
                usuarioCarteraCodigo = em.getReference(usuarioCarteraCodigo.getClass(), usuarioCarteraCodigo.getCodigo());
                cartera.setUsuarioCarteraCodigo(usuarioCarteraCodigo);
            }
            List<Puntos> attachedPuntosList = new ArrayList<Puntos>();
            for (Puntos puntosListPuntosToAttach : cartera.getPuntosList()) {
                puntosListPuntosToAttach = em.getReference(puntosListPuntosToAttach.getClass(), puntosListPuntosToAttach.getCodigo());
                attachedPuntosList.add(puntosListPuntosToAttach);
            }
            cartera.setPuntosList(attachedPuntosList);
            em.persist(cartera);
            if (usuarioCarteraCodigo != null) {
                usuarioCarteraCodigo.getCarteraList().add(cartera);
                usuarioCarteraCodigo = em.merge(usuarioCarteraCodigo);
            }
            for (Puntos puntosListPuntos : cartera.getPuntosList()) {
                Cartera oldCarteraCodigoOfPuntosListPuntos = puntosListPuntos.getCarteraCodigo();
                puntosListPuntos.setCarteraCodigo(cartera);
                puntosListPuntos = em.merge(puntosListPuntos);
                if (oldCarteraCodigoOfPuntosListPuntos != null) {
                    oldCarteraCodigoOfPuntosListPuntos.getPuntosList().remove(puntosListPuntos);
                    oldCarteraCodigoOfPuntosListPuntos = em.merge(oldCarteraCodigoOfPuntosListPuntos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCartera(cartera.getCodigo()) != null) {
                throw new PreexistingEntityException("Cartera " + cartera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cartera cartera) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartera persistentCartera = em.find(Cartera.class, cartera.getCodigo());
            Usuario usuarioCarteraCodigoOld = persistentCartera.getUsuarioCarteraCodigo();
            Usuario usuarioCarteraCodigoNew = cartera.getUsuarioCarteraCodigo();
            List<Puntos> puntosListOld = persistentCartera.getPuntosList();
            List<Puntos> puntosListNew = cartera.getPuntosList();
            List<String> illegalOrphanMessages = null;
            for (Puntos puntosListOldPuntos : puntosListOld) {
                if (!puntosListNew.contains(puntosListOldPuntos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Puntos " + puntosListOldPuntos + " since its carteraCodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioCarteraCodigoNew != null) {
                usuarioCarteraCodigoNew = em.getReference(usuarioCarteraCodigoNew.getClass(), usuarioCarteraCodigoNew.getCodigo());
                cartera.setUsuarioCarteraCodigo(usuarioCarteraCodigoNew);
            }
            List<Puntos> attachedPuntosListNew = new ArrayList<Puntos>();
            for (Puntos puntosListNewPuntosToAttach : puntosListNew) {
                puntosListNewPuntosToAttach = em.getReference(puntosListNewPuntosToAttach.getClass(), puntosListNewPuntosToAttach.getCodigo());
                attachedPuntosListNew.add(puntosListNewPuntosToAttach);
            }
            puntosListNew = attachedPuntosListNew;
            cartera.setPuntosList(puntosListNew);
            cartera = em.merge(cartera);
            if (usuarioCarteraCodigoOld != null && !usuarioCarteraCodigoOld.equals(usuarioCarteraCodigoNew)) {
                usuarioCarteraCodigoOld.getCarteraList().remove(cartera);
                usuarioCarteraCodigoOld = em.merge(usuarioCarteraCodigoOld);
            }
            if (usuarioCarteraCodigoNew != null && !usuarioCarteraCodigoNew.equals(usuarioCarteraCodigoOld)) {
                usuarioCarteraCodigoNew.getCarteraList().add(cartera);
                usuarioCarteraCodigoNew = em.merge(usuarioCarteraCodigoNew);
            }
            for (Puntos puntosListNewPuntos : puntosListNew) {
                if (!puntosListOld.contains(puntosListNewPuntos)) {
                    Cartera oldCarteraCodigoOfPuntosListNewPuntos = puntosListNewPuntos.getCarteraCodigo();
                    puntosListNewPuntos.setCarteraCodigo(cartera);
                    puntosListNewPuntos = em.merge(puntosListNewPuntos);
                    if (oldCarteraCodigoOfPuntosListNewPuntos != null && !oldCarteraCodigoOfPuntosListNewPuntos.equals(cartera)) {
                        oldCarteraCodigoOfPuntosListNewPuntos.getPuntosList().remove(puntosListNewPuntos);
                        oldCarteraCodigoOfPuntosListNewPuntos = em.merge(oldCarteraCodigoOfPuntosListNewPuntos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cartera.getCodigo();
                if (findCartera(id) == null) {
                    throw new NonexistentEntityException("The cartera with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartera cartera;
            try {
                cartera = em.getReference(Cartera.class, id);
                cartera.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartera with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Puntos> puntosListOrphanCheck = cartera.getPuntosList();
            for (Puntos puntosListOrphanCheckPuntos : puntosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cartera (" + cartera + ") cannot be destroyed since the Puntos " + puntosListOrphanCheckPuntos + " in its puntosList field has a non-nullable carteraCodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioCarteraCodigo = cartera.getUsuarioCarteraCodigo();
            if (usuarioCarteraCodigo != null) {
                usuarioCarteraCodigo.getCarteraList().remove(cartera);
                usuarioCarteraCodigo = em.merge(usuarioCarteraCodigo);
            }
            em.remove(cartera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cartera> findCarteraEntities() {
        return findCarteraEntities(true, -1, -1);
    }

    public List<Cartera> findCarteraEntities(int maxResults, int firstResult) {
        return findCarteraEntities(false, maxResults, firstResult);
    }

    private List<Cartera> findCarteraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cartera.class));
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

    public Cartera findCartera(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cartera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarteraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cartera> rt = cq.from(Cartera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
