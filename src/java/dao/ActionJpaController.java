/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import entity.Action;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Utilisateur;
import entity.Fichier;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class ActionJpaController implements Serializable {

    public ActionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Action action) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur idUser = action.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getId());
                action.setIdUser(idUser);
            }
            Fichier idFichier = action.getIdFichier();
            if (idFichier != null) {
                idFichier = em.getReference(idFichier.getClass(), idFichier.getId());
                action.setIdFichier(idFichier);
            }
            em.persist(action);
            if (idUser != null) {
                idUser.getActionList().add(action);
                idUser = em.merge(idUser);
            }
            if (idFichier != null) {
                idFichier.getActionList().add(action);
                idFichier = em.merge(idFichier);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Action action) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Action persistentAction = em.find(Action.class, action.getId());
            Utilisateur idUserOld = persistentAction.getIdUser();
            Utilisateur idUserNew = action.getIdUser();
            Fichier idFichierOld = persistentAction.getIdFichier();
            Fichier idFichierNew = action.getIdFichier();
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getId());
                action.setIdUser(idUserNew);
            }
            if (idFichierNew != null) {
                idFichierNew = em.getReference(idFichierNew.getClass(), idFichierNew.getId());
                action.setIdFichier(idFichierNew);
            }
            action = em.merge(action);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getActionList().remove(action);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getActionList().add(action);
                idUserNew = em.merge(idUserNew);
            }
            if (idFichierOld != null && !idFichierOld.equals(idFichierNew)) {
                idFichierOld.getActionList().remove(action);
                idFichierOld = em.merge(idFichierOld);
            }
            if (idFichierNew != null && !idFichierNew.equals(idFichierOld)) {
                idFichierNew.getActionList().add(action);
                idFichierNew = em.merge(idFichierNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = action.getId();
                if (findAction(id) == null) {
                    throw new NonexistentEntityException("The action with id " + id + " no longer exists.");
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
            Action action;
            try {
                action = em.getReference(Action.class, id);
                action.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The action with id " + id + " no longer exists.", enfe);
            }
            Utilisateur idUser = action.getIdUser();
            if (idUser != null) {
                idUser.getActionList().remove(action);
                idUser = em.merge(idUser);
            }
            Fichier idFichier = action.getIdFichier();
            if (idFichier != null) {
                idFichier.getActionList().remove(action);
                idFichier = em.merge(idFichier);
            }
            em.remove(action);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Action> findActionEntities() {
        return findActionEntities(true, -1, -1);
    }

    public List<Action> findActionEntities(int maxResults, int firstResult) {
        return findActionEntities(false, maxResults, firstResult);
    }

    private List<Action> findActionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Action.class));
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

    public Action findAction(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Action.class, id);
        } finally {
            em.close();
        }
    }

    public int getActionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Action> rt = cq.from(Action.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
