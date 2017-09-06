/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Action;
import java.util.ArrayList;
import java.util.List;
import entity.Affectation;
import entity.Fichier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class FichierJpaController1 implements Serializable {

    public FichierJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fichier fichier) {
        if (fichier.getActionList() == null) {
            fichier.setActionList(new ArrayList<Action>());
        }
        if (fichier.getAffectationList() == null) {
            fichier.setAffectationList(new ArrayList<Affectation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Action> attachedActionList = new ArrayList<Action>();
            for (Action actionListActionToAttach : fichier.getActionList()) {
                actionListActionToAttach = em.getReference(actionListActionToAttach.getClass(), actionListActionToAttach.getId());
                attachedActionList.add(actionListActionToAttach);
            }
            fichier.setActionList(attachedActionList);
            List<Affectation> attachedAffectationList = new ArrayList<Affectation>();
            for (Affectation affectationListAffectationToAttach : fichier.getAffectationList()) {
                affectationListAffectationToAttach = em.getReference(affectationListAffectationToAttach.getClass(), affectationListAffectationToAttach.getId());
                attachedAffectationList.add(affectationListAffectationToAttach);
            }
            fichier.setAffectationList(attachedAffectationList);
            em.persist(fichier);
            for (Action actionListAction : fichier.getActionList()) {
                Fichier oldIdFichierOfActionListAction = actionListAction.getIdFichier();
                actionListAction.setIdFichier(fichier);
                actionListAction = em.merge(actionListAction);
                if (oldIdFichierOfActionListAction != null) {
                    oldIdFichierOfActionListAction.getActionList().remove(actionListAction);
                    oldIdFichierOfActionListAction = em.merge(oldIdFichierOfActionListAction);
                }
            }
            for (Affectation affectationListAffectation : fichier.getAffectationList()) {
                Fichier oldIdFichierOfAffectationListAffectation = affectationListAffectation.getIdFichier();
                affectationListAffectation.setIdFichier(fichier);
                affectationListAffectation = em.merge(affectationListAffectation);
                if (oldIdFichierOfAffectationListAffectation != null) {
                    oldIdFichierOfAffectationListAffectation.getAffectationList().remove(affectationListAffectation);
                    oldIdFichierOfAffectationListAffectation = em.merge(oldIdFichierOfAffectationListAffectation);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fichier fichier) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fichier persistentFichier = em.find(Fichier.class, fichier.getId());
            List<Action> actionListOld = persistentFichier.getActionList();
            List<Action> actionListNew = fichier.getActionList();
            List<Affectation> affectationListOld = persistentFichier.getAffectationList();
            List<Affectation> affectationListNew = fichier.getAffectationList();
            List<String> illegalOrphanMessages = null;
            for (Affectation affectationListOldAffectation : affectationListOld) {
                if (!affectationListNew.contains(affectationListOldAffectation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Affectation " + affectationListOldAffectation + " since its idFichier field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Action> attachedActionListNew = new ArrayList<Action>();
            for (Action actionListNewActionToAttach : actionListNew) {
                actionListNewActionToAttach = em.getReference(actionListNewActionToAttach.getClass(), actionListNewActionToAttach.getId());
                attachedActionListNew.add(actionListNewActionToAttach);
            }
            actionListNew = attachedActionListNew;
            fichier.setActionList(actionListNew);
            List<Affectation> attachedAffectationListNew = new ArrayList<Affectation>();
            for (Affectation affectationListNewAffectationToAttach : affectationListNew) {
                affectationListNewAffectationToAttach = em.getReference(affectationListNewAffectationToAttach.getClass(), affectationListNewAffectationToAttach.getId());
                attachedAffectationListNew.add(affectationListNewAffectationToAttach);
            }
            affectationListNew = attachedAffectationListNew;
            fichier.setAffectationList(affectationListNew);
            fichier = em.merge(fichier);
            for (Action actionListOldAction : actionListOld) {
                if (!actionListNew.contains(actionListOldAction)) {
                    actionListOldAction.setIdFichier(null);
                    actionListOldAction = em.merge(actionListOldAction);
                }
            }
            for (Action actionListNewAction : actionListNew) {
                if (!actionListOld.contains(actionListNewAction)) {
                    Fichier oldIdFichierOfActionListNewAction = actionListNewAction.getIdFichier();
                    actionListNewAction.setIdFichier(fichier);
                    actionListNewAction = em.merge(actionListNewAction);
                    if (oldIdFichierOfActionListNewAction != null && !oldIdFichierOfActionListNewAction.equals(fichier)) {
                        oldIdFichierOfActionListNewAction.getActionList().remove(actionListNewAction);
                        oldIdFichierOfActionListNewAction = em.merge(oldIdFichierOfActionListNewAction);
                    }
                }
            }
            for (Affectation affectationListNewAffectation : affectationListNew) {
                if (!affectationListOld.contains(affectationListNewAffectation)) {
                    Fichier oldIdFichierOfAffectationListNewAffectation = affectationListNewAffectation.getIdFichier();
                    affectationListNewAffectation.setIdFichier(fichier);
                    affectationListNewAffectation = em.merge(affectationListNewAffectation);
                    if (oldIdFichierOfAffectationListNewAffectation != null && !oldIdFichierOfAffectationListNewAffectation.equals(fichier)) {
                        oldIdFichierOfAffectationListNewAffectation.getAffectationList().remove(affectationListNewAffectation);
                        oldIdFichierOfAffectationListNewAffectation = em.merge(oldIdFichierOfAffectationListNewAffectation);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fichier.getId();
                if (findFichier(id) == null) {
                    throw new NonexistentEntityException("The fichier with id " + id + " no longer exists.");
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
            Fichier fichier;
            try {
                fichier = em.getReference(Fichier.class, id);
                fichier.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fichier with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Affectation> affectationListOrphanCheck = fichier.getAffectationList();
            for (Affectation affectationListOrphanCheckAffectation : affectationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fichier (" + fichier + ") cannot be destroyed since the Affectation " + affectationListOrphanCheckAffectation + " in its affectationList field has a non-nullable idFichier field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Action> actionList = fichier.getActionList();
            for (Action actionListAction : actionList) {
                actionListAction.setIdFichier(null);
                actionListAction = em.merge(actionListAction);
            }
            em.remove(fichier);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fichier> findFichierEntities() {
        return findFichierEntities(true, -1, -1);
    }

    public List<Fichier> findFichierEntities(int maxResults, int firstResult) {
        return findFichierEntities(false, maxResults, firstResult);
    }

    private List<Fichier> findFichierEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fichier.class));
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

    public Fichier findFichier(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fichier.class, id);
        } finally {
            em.close();
        }
    }

    public int getFichierCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fichier> rt = cq.from(Fichier.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
