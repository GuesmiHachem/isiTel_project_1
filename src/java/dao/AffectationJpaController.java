/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import entity.Affectation;
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
public class AffectationJpaController implements Serializable {

    public AffectationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Affectation affectation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur idUser = affectation.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getId());
                affectation.setIdUser(idUser);
            }
            Fichier idFichier = affectation.getIdFichier();
            if (idFichier != null) {
                idFichier = em.getReference(idFichier.getClass(), idFichier.getId());
                affectation.setIdFichier(idFichier);
            }
            em.persist(affectation);
            if (idUser != null) {
                idUser.getAffectationList().add(affectation);
                idUser = em.merge(idUser);
            }
            if (idFichier != null) {
                idFichier.getAffectationList().add(affectation);
                idFichier = em.merge(idFichier);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Affectation affectation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Affectation persistentAffectation = em.find(Affectation.class, affectation.getId());
            Utilisateur idUserOld = persistentAffectation.getIdUser();
            Utilisateur idUserNew = affectation.getIdUser();
            Fichier idFichierOld = persistentAffectation.getIdFichier();
            Fichier idFichierNew = affectation.getIdFichier();
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getId());
                affectation.setIdUser(idUserNew);
            }
            if (idFichierNew != null) {
                idFichierNew = em.getReference(idFichierNew.getClass(), idFichierNew.getId());
                affectation.setIdFichier(idFichierNew);
            }
            affectation = em.merge(affectation);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getAffectationList().remove(affectation);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getAffectationList().add(affectation);
                idUserNew = em.merge(idUserNew);
            }
            if (idFichierOld != null && !idFichierOld.equals(idFichierNew)) {
                idFichierOld.getAffectationList().remove(affectation);
                idFichierOld = em.merge(idFichierOld);
            }
            if (idFichierNew != null && !idFichierNew.equals(idFichierOld)) {
                idFichierNew.getAffectationList().add(affectation);
                idFichierNew = em.merge(idFichierNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = affectation.getId();
                if (findAffectation(id) == null) {
                    throw new NonexistentEntityException("The affectation with id " + id + " no longer exists.");
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
            Affectation affectation;
            try {
                affectation = em.getReference(Affectation.class, id);
                affectation.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The affectation with id " + id + " no longer exists.", enfe);
            }
            Utilisateur idUser = affectation.getIdUser();
            if (idUser != null) {
                idUser.getAffectationList().remove(affectation);
                idUser = em.merge(idUser);
            }
            Fichier idFichier = affectation.getIdFichier();
            if (idFichier != null) {
                idFichier.getAffectationList().remove(affectation);
                idFichier = em.merge(idFichier);
            }
            em.remove(affectation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Affectation> findAffectationEntities() {
        return findAffectationEntities(true, -1, -1);
    }

    public List<Affectation> findAffectationEntities(int maxResults, int firstResult) {
        return findAffectationEntities(false, maxResults, firstResult);
    }

    private List<Affectation> findAffectationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Affectation.class));
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

    public Affectation findAffectation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Affectation.class, id);
        } finally {
            em.close();
        }
    }

    public int getAffectationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Affectation> rt = cq.from(Affectation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
