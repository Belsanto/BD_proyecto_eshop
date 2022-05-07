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
import com.entities.Cartera;
import com.entities.Ciudad;
import com.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author EQUIPO
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getCarteraList() == null) {
            usuario.setCarteraList(new ArrayList<Cartera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartera carteraPuntosCodigo = usuario.getCarteraPuntosCodigo();
            if (carteraPuntosCodigo != null) {
                carteraPuntosCodigo = em.getReference(carteraPuntosCodigo.getClass(), carteraPuntosCodigo.getCodigo());
                usuario.setCarteraPuntosCodigo(carteraPuntosCodigo);
            }
            Ciudad ciudadUsuarioCodigo = usuario.getCiudadUsuarioCodigo();
            if (ciudadUsuarioCodigo != null) {
                ciudadUsuarioCodigo = em.getReference(ciudadUsuarioCodigo.getClass(), ciudadUsuarioCodigo.getCodigo());
                usuario.setCiudadUsuarioCodigo(ciudadUsuarioCodigo);
            }
            List<Cartera> attachedCarteraList = new ArrayList<Cartera>();
            for (Cartera carteraListCarteraToAttach : usuario.getCarteraList()) {
                carteraListCarteraToAttach = em.getReference(carteraListCarteraToAttach.getClass(), carteraListCarteraToAttach.getCodigo());
                attachedCarteraList.add(carteraListCarteraToAttach);
            }
            usuario.setCarteraList(attachedCarteraList);
            em.persist(usuario);
            if (carteraPuntosCodigo != null) {
                carteraPuntosCodigo.getUsuarioList().add(usuario);
                carteraPuntosCodigo = em.merge(carteraPuntosCodigo);
            }
            if (ciudadUsuarioCodigo != null) {
                ciudadUsuarioCodigo.getUsuarioList().add(usuario);
                ciudadUsuarioCodigo = em.merge(ciudadUsuarioCodigo);
            }
            for (Cartera carteraListCartera : usuario.getCarteraList()) {
                Usuario oldUsuariosCodigoOfCarteraListCartera = carteraListCartera.getUsuariosCodigo();
                carteraListCartera.setUsuariosCodigo(usuario);
                carteraListCartera = em.merge(carteraListCartera);
                if (oldUsuariosCodigoOfCarteraListCartera != null) {
                    oldUsuariosCodigoOfCarteraListCartera.getCarteraList().remove(carteraListCartera);
                    oldUsuariosCodigoOfCarteraListCartera = em.merge(oldUsuariosCodigoOfCarteraListCartera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCodigo());
            Cartera carteraPuntosCodigoOld = persistentUsuario.getCarteraPuntosCodigo();
            Cartera carteraPuntosCodigoNew = usuario.getCarteraPuntosCodigo();
            Ciudad ciudadUsuarioCodigoOld = persistentUsuario.getCiudadUsuarioCodigo();
            Ciudad ciudadUsuarioCodigoNew = usuario.getCiudadUsuarioCodigo();
            List<Cartera> carteraListOld = persistentUsuario.getCarteraList();
            List<Cartera> carteraListNew = usuario.getCarteraList();
            if (carteraPuntosCodigoNew != null) {
                carteraPuntosCodigoNew = em.getReference(carteraPuntosCodigoNew.getClass(), carteraPuntosCodigoNew.getCodigo());
                usuario.setCarteraPuntosCodigo(carteraPuntosCodigoNew);
            }
            if (ciudadUsuarioCodigoNew != null) {
                ciudadUsuarioCodigoNew = em.getReference(ciudadUsuarioCodigoNew.getClass(), ciudadUsuarioCodigoNew.getCodigo());
                usuario.setCiudadUsuarioCodigo(ciudadUsuarioCodigoNew);
            }
            List<Cartera> attachedCarteraListNew = new ArrayList<Cartera>();
            for (Cartera carteraListNewCarteraToAttach : carteraListNew) {
                carteraListNewCarteraToAttach = em.getReference(carteraListNewCarteraToAttach.getClass(), carteraListNewCarteraToAttach.getCodigo());
                attachedCarteraListNew.add(carteraListNewCarteraToAttach);
            }
            carteraListNew = attachedCarteraListNew;
            usuario.setCarteraList(carteraListNew);
            usuario = em.merge(usuario);
            if (carteraPuntosCodigoOld != null && !carteraPuntosCodigoOld.equals(carteraPuntosCodigoNew)) {
                carteraPuntosCodigoOld.getUsuarioList().remove(usuario);
                carteraPuntosCodigoOld = em.merge(carteraPuntosCodigoOld);
            }
            if (carteraPuntosCodigoNew != null && !carteraPuntosCodigoNew.equals(carteraPuntosCodigoOld)) {
                carteraPuntosCodigoNew.getUsuarioList().add(usuario);
                carteraPuntosCodigoNew = em.merge(carteraPuntosCodigoNew);
            }
            if (ciudadUsuarioCodigoOld != null && !ciudadUsuarioCodigoOld.equals(ciudadUsuarioCodigoNew)) {
                ciudadUsuarioCodigoOld.getUsuarioList().remove(usuario);
                ciudadUsuarioCodigoOld = em.merge(ciudadUsuarioCodigoOld);
            }
            if (ciudadUsuarioCodigoNew != null && !ciudadUsuarioCodigoNew.equals(ciudadUsuarioCodigoOld)) {
                ciudadUsuarioCodigoNew.getUsuarioList().add(usuario);
                ciudadUsuarioCodigoNew = em.merge(ciudadUsuarioCodigoNew);
            }
            for (Cartera carteraListOldCartera : carteraListOld) {
                if (!carteraListNew.contains(carteraListOldCartera)) {
                    carteraListOldCartera.setUsuariosCodigo(null);
                    carteraListOldCartera = em.merge(carteraListOldCartera);
                }
            }
            for (Cartera carteraListNewCartera : carteraListNew) {
                if (!carteraListOld.contains(carteraListNewCartera)) {
                    Usuario oldUsuariosCodigoOfCarteraListNewCartera = carteraListNewCartera.getUsuariosCodigo();
                    carteraListNewCartera.setUsuariosCodigo(usuario);
                    carteraListNewCartera = em.merge(carteraListNewCartera);
                    if (oldUsuariosCodigoOfCarteraListNewCartera != null && !oldUsuariosCodigoOfCarteraListNewCartera.equals(usuario)) {
                        oldUsuariosCodigoOfCarteraListNewCartera.getCarteraList().remove(carteraListNewCartera);
                        oldUsuariosCodigoOfCarteraListNewCartera = em.merge(oldUsuariosCodigoOfCarteraListNewCartera);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Cartera carteraPuntosCodigo = usuario.getCarteraPuntosCodigo();
            if (carteraPuntosCodigo != null) {
                carteraPuntosCodigo.getUsuarioList().remove(usuario);
                carteraPuntosCodigo = em.merge(carteraPuntosCodigo);
            }
            Ciudad ciudadUsuarioCodigo = usuario.getCiudadUsuarioCodigo();
            if (ciudadUsuarioCodigo != null) {
                ciudadUsuarioCodigo.getUsuarioList().remove(usuario);
                ciudadUsuarioCodigo = em.merge(ciudadUsuarioCodigo);
            }
            List<Cartera> carteraList = usuario.getCarteraList();
            for (Cartera carteraListCartera : carteraList) {
                carteraListCartera.setUsuariosCodigo(null);
                carteraListCartera = em.merge(carteraListCartera);
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
