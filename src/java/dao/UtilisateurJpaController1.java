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
import entity.Message;
import entity.Affectation;
import entity.Utilisateur;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class UtilisateurJpaController1 implements Serializable {

    public UtilisateurJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilisateur utilisateur) {
        if (utilisateur.getActionList() == null) {
            utilisateur.setActionList(new ArrayList<Action>());
        }
        if (utilisateur.getMessageList() == null) {
            utilisateur.setMessageList(new ArrayList<Message>());
        }
        if (utilisateur.getMessageList1() == null) {
            utilisateur.setMessageList1(new ArrayList<Message>());
        }
        if (utilisateur.getAffectationList() == null) {
            utilisateur.setAffectationList(new ArrayList<Affectation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Action> attachedActionList = new ArrayList<Action>();
            for (Action actionListActionToAttach : utilisateur.getActionList()) {
                actionListActionToAttach = em.getReference(actionListActionToAttach.getClass(), actionListActionToAttach.getId());
                attachedActionList.add(actionListActionToAttach);
            }
            utilisateur.setActionList(attachedActionList);
            List<Message> attachedMessageList = new ArrayList<Message>();
            for (Message messageListMessageToAttach : utilisateur.getMessageList()) {
                messageListMessageToAttach = em.getReference(messageListMessageToAttach.getClass(), messageListMessageToAttach.getId());
                attachedMessageList.add(messageListMessageToAttach);
            }
            utilisateur.setMessageList(attachedMessageList);
            List<Message> attachedMessageList1 = new ArrayList<Message>();
            for (Message messageList1MessageToAttach : utilisateur.getMessageList1()) {
                messageList1MessageToAttach = em.getReference(messageList1MessageToAttach.getClass(), messageList1MessageToAttach.getId());
                attachedMessageList1.add(messageList1MessageToAttach);
            }
            utilisateur.setMessageList1(attachedMessageList1);
            List<Affectation> attachedAffectationList = new ArrayList<Affectation>();
            for (Affectation affectationListAffectationToAttach : utilisateur.getAffectationList()) {
                affectationListAffectationToAttach = em.getReference(affectationListAffectationToAttach.getClass(), affectationListAffectationToAttach.getId());
                attachedAffectationList.add(affectationListAffectationToAttach);
            }
            utilisateur.setAffectationList(attachedAffectationList);
            em.persist(utilisateur);
            for (Action actionListAction : utilisateur.getActionList()) {
                Utilisateur oldIdUserOfActionListAction = actionListAction.getIdUser();
                actionListAction.setIdUser(utilisateur);
                actionListAction = em.merge(actionListAction);
                if (oldIdUserOfActionListAction != null) {
                    oldIdUserOfActionListAction.getActionList().remove(actionListAction);
                    oldIdUserOfActionListAction = em.merge(oldIdUserOfActionListAction);
                }
            }
            for (Message messageListMessage : utilisateur.getMessageList()) {
                Utilisateur oldIdSourceOfMessageListMessage = messageListMessage.getIdSource();
                messageListMessage.setIdSource(utilisateur);
                messageListMessage = em.merge(messageListMessage);
                if (oldIdSourceOfMessageListMessage != null) {
                    oldIdSourceOfMessageListMessage.getMessageList().remove(messageListMessage);
                    oldIdSourceOfMessageListMessage = em.merge(oldIdSourceOfMessageListMessage);
                }
            }
            for (Message messageList1Message : utilisateur.getMessageList1()) {
                Utilisateur oldIdDestinationOfMessageList1Message = messageList1Message.getIdDestination();
                messageList1Message.setIdDestination(utilisateur);
                messageList1Message = em.merge(messageList1Message);
                if (oldIdDestinationOfMessageList1Message != null) {
                    oldIdDestinationOfMessageList1Message.getMessageList1().remove(messageList1Message);
                    oldIdDestinationOfMessageList1Message = em.merge(oldIdDestinationOfMessageList1Message);
                }
            }
            for (Affectation affectationListAffectation : utilisateur.getAffectationList()) {
                Utilisateur oldIdUserOfAffectationListAffectation = affectationListAffectation.getIdUser();
                affectationListAffectation.setIdUser(utilisateur);
                affectationListAffectation = em.merge(affectationListAffectation);
                if (oldIdUserOfAffectationListAffectation != null) {
                    oldIdUserOfAffectationListAffectation.getAffectationList().remove(affectationListAffectation);
                    oldIdUserOfAffectationListAffectation = em.merge(oldIdUserOfAffectationListAffectation);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Utilisateur utilisateur) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur persistentUtilisateur = em.find(Utilisateur.class, utilisateur.getId());
            List<Action> actionListOld = persistentUtilisateur.getActionList();
            List<Action> actionListNew = utilisateur.getActionList();
            List<Message> messageListOld = persistentUtilisateur.getMessageList();
            List<Message> messageListNew = utilisateur.getMessageList();
            List<Message> messageList1Old = persistentUtilisateur.getMessageList1();
            List<Message> messageList1New = utilisateur.getMessageList1();
            List<Affectation> affectationListOld = persistentUtilisateur.getAffectationList();
            List<Affectation> affectationListNew = utilisateur.getAffectationList();
            List<String> illegalOrphanMessages = null;
            for (Action actionListOldAction : actionListOld) {
                if (!actionListNew.contains(actionListOldAction)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Action " + actionListOldAction + " since its idUser field is not nullable.");
                }
            }
            for (Message messageListOldMessage : messageListOld) {
                if (!messageListNew.contains(messageListOldMessage)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Message " + messageListOldMessage + " since its idSource field is not nullable.");
                }
            }
            for (Message messageList1OldMessage : messageList1Old) {
                if (!messageList1New.contains(messageList1OldMessage)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Message " + messageList1OldMessage + " since its idDestination field is not nullable.");
                }
            }
            for (Affectation affectationListOldAffectation : affectationListOld) {
                if (!affectationListNew.contains(affectationListOldAffectation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Affectation " + affectationListOldAffectation + " since its idUser field is not nullable.");
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
            utilisateur.setActionList(actionListNew);
            List<Message> attachedMessageListNew = new ArrayList<Message>();
            for (Message messageListNewMessageToAttach : messageListNew) {
                messageListNewMessageToAttach = em.getReference(messageListNewMessageToAttach.getClass(), messageListNewMessageToAttach.getId());
                attachedMessageListNew.add(messageListNewMessageToAttach);
            }
            messageListNew = attachedMessageListNew;
            utilisateur.setMessageList(messageListNew);
            List<Message> attachedMessageList1New = new ArrayList<Message>();
            for (Message messageList1NewMessageToAttach : messageList1New) {
                messageList1NewMessageToAttach = em.getReference(messageList1NewMessageToAttach.getClass(), messageList1NewMessageToAttach.getId());
                attachedMessageList1New.add(messageList1NewMessageToAttach);
            }
            messageList1New = attachedMessageList1New;
            utilisateur.setMessageList1(messageList1New);
            List<Affectation> attachedAffectationListNew = new ArrayList<Affectation>();
            for (Affectation affectationListNewAffectationToAttach : affectationListNew) {
                affectationListNewAffectationToAttach = em.getReference(affectationListNewAffectationToAttach.getClass(), affectationListNewAffectationToAttach.getId());
                attachedAffectationListNew.add(affectationListNewAffectationToAttach);
            }
            affectationListNew = attachedAffectationListNew;
            utilisateur.setAffectationList(affectationListNew);
            utilisateur = em.merge(utilisateur);
            for (Action actionListNewAction : actionListNew) {
                if (!actionListOld.contains(actionListNewAction)) {
                    Utilisateur oldIdUserOfActionListNewAction = actionListNewAction.getIdUser();
                    actionListNewAction.setIdUser(utilisateur);
                    actionListNewAction = em.merge(actionListNewAction);
                    if (oldIdUserOfActionListNewAction != null && !oldIdUserOfActionListNewAction.equals(utilisateur)) {
                        oldIdUserOfActionListNewAction.getActionList().remove(actionListNewAction);
                        oldIdUserOfActionListNewAction = em.merge(oldIdUserOfActionListNewAction);
                    }
                }
            }
            for (Message messageListNewMessage : messageListNew) {
                if (!messageListOld.contains(messageListNewMessage)) {
                    Utilisateur oldIdSourceOfMessageListNewMessage = messageListNewMessage.getIdSource();
                    messageListNewMessage.setIdSource(utilisateur);
                    messageListNewMessage = em.merge(messageListNewMessage);
                    if (oldIdSourceOfMessageListNewMessage != null && !oldIdSourceOfMessageListNewMessage.equals(utilisateur)) {
                        oldIdSourceOfMessageListNewMessage.getMessageList().remove(messageListNewMessage);
                        oldIdSourceOfMessageListNewMessage = em.merge(oldIdSourceOfMessageListNewMessage);
                    }
                }
            }
            for (Message messageList1NewMessage : messageList1New) {
                if (!messageList1Old.contains(messageList1NewMessage)) {
                    Utilisateur oldIdDestinationOfMessageList1NewMessage = messageList1NewMessage.getIdDestination();
                    messageList1NewMessage.setIdDestination(utilisateur);
                    messageList1NewMessage = em.merge(messageList1NewMessage);
                    if (oldIdDestinationOfMessageList1NewMessage != null && !oldIdDestinationOfMessageList1NewMessage.equals(utilisateur)) {
                        oldIdDestinationOfMessageList1NewMessage.getMessageList1().remove(messageList1NewMessage);
                        oldIdDestinationOfMessageList1NewMessage = em.merge(oldIdDestinationOfMessageList1NewMessage);
                    }
                }
            }
            for (Affectation affectationListNewAffectation : affectationListNew) {
                if (!affectationListOld.contains(affectationListNewAffectation)) {
                    Utilisateur oldIdUserOfAffectationListNewAffectation = affectationListNewAffectation.getIdUser();
                    affectationListNewAffectation.setIdUser(utilisateur);
                    affectationListNewAffectation = em.merge(affectationListNewAffectation);
                    if (oldIdUserOfAffectationListNewAffectation != null && !oldIdUserOfAffectationListNewAffectation.equals(utilisateur)) {
                        oldIdUserOfAffectationListNewAffectation.getAffectationList().remove(affectationListNewAffectation);
                        oldIdUserOfAffectationListNewAffectation = em.merge(oldIdUserOfAffectationListNewAffectation);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = utilisateur.getId();
                if (findUtilisateur(id) == null) {
                    throw new NonexistentEntityException("The utilisateur with id " + id + " no longer exists.");
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
            Utilisateur utilisateur;
            try {
                utilisateur = em.getReference(Utilisateur.class, id);
                utilisateur.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilisateur with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Action> actionListOrphanCheck = utilisateur.getActionList();
            for (Action actionListOrphanCheckAction : actionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Action " + actionListOrphanCheckAction + " in its actionList field has a non-nullable idUser field.");
            }
            List<Message> messageListOrphanCheck = utilisateur.getMessageList();
            for (Message messageListOrphanCheckMessage : messageListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Message " + messageListOrphanCheckMessage + " in its messageList field has a non-nullable idSource field.");
            }
            List<Message> messageList1OrphanCheck = utilisateur.getMessageList1();
            for (Message messageList1OrphanCheckMessage : messageList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Message " + messageList1OrphanCheckMessage + " in its messageList1 field has a non-nullable idDestination field.");
            }
            List<Affectation> affectationListOrphanCheck = utilisateur.getAffectationList();
            for (Affectation affectationListOrphanCheckAffectation : affectationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Affectation " + affectationListOrphanCheckAffectation + " in its affectationList field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(utilisateur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Utilisateur> findUtilisateurEntities() {
        return findUtilisateurEntities(true, -1, -1);
    }

    public List<Utilisateur> findUtilisateurEntities(int maxResults, int firstResult) {
        return findUtilisateurEntities(false, maxResults, firstResult);
    }

    private List<Utilisateur> findUtilisateurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilisateur.class));
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

    public Utilisateur findUtilisateur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilisateur.class, id);
        } finally {
            em.close();
        }
    }

    public int getUtilisateurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilisateur> rt = cq.from(Utilisateur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
