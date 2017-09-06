/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import entity.Message;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Utilisateur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author admin
 */
public class MessageJpaController implements Serializable {

    public MessageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Message message) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utilisateur idSource = message.getIdSource();
            if (idSource != null) {
                idSource = em.getReference(idSource.getClass(), idSource.getId());
                message.setIdSource(idSource);
            }
            Utilisateur idDestination = message.getIdDestination();
            if (idDestination != null) {
                idDestination = em.getReference(idDestination.getClass(), idDestination.getId());
                message.setIdDestination(idDestination);
            }
            em.persist(message);
            if (idSource != null) {
                idSource.getMessageList().add(message);
                idSource = em.merge(idSource);
            }
            if (idDestination != null) {
                idDestination.getMessageList().add(message);
                idDestination = em.merge(idDestination);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Message message) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Message persistentMessage = em.find(Message.class, message.getId());
            Utilisateur idSourceOld = persistentMessage.getIdSource();
            Utilisateur idSourceNew = message.getIdSource();
            Utilisateur idDestinationOld = persistentMessage.getIdDestination();
            Utilisateur idDestinationNew = message.getIdDestination();
            if (idSourceNew != null) {
                idSourceNew = em.getReference(idSourceNew.getClass(), idSourceNew.getId());
                message.setIdSource(idSourceNew);
            }
            if (idDestinationNew != null) {
                idDestinationNew = em.getReference(idDestinationNew.getClass(), idDestinationNew.getId());
                message.setIdDestination(idDestinationNew);
            }
            message = em.merge(message);
            if (idSourceOld != null && !idSourceOld.equals(idSourceNew)) {
                idSourceOld.getMessageList().remove(message);
                idSourceOld = em.merge(idSourceOld);
            }
            if (idSourceNew != null && !idSourceNew.equals(idSourceOld)) {
                idSourceNew.getMessageList().add(message);
                idSourceNew = em.merge(idSourceNew);
            }
            if (idDestinationOld != null && !idDestinationOld.equals(idDestinationNew)) {
                idDestinationOld.getMessageList().remove(message);
                idDestinationOld = em.merge(idDestinationOld);
            }
            if (idDestinationNew != null && !idDestinationNew.equals(idDestinationOld)) {
                idDestinationNew.getMessageList().add(message);
                idDestinationNew = em.merge(idDestinationNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = message.getId();
                if (findMessage(id) == null) {
                    throw new NonexistentEntityException("The message with id " + id + " no longer exists.");
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
            Message message;
            try {
                message = em.getReference(Message.class, id);
                message.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
            }
            Utilisateur idSource = message.getIdSource();
            if (idSource != null) {
                idSource.getMessageList().remove(message);
                idSource = em.merge(idSource);
            }
            Utilisateur idDestination = message.getIdDestination();
            if (idDestination != null) {
                idDestination.getMessageList().remove(message);
                idDestination = em.merge(idDestination);
            }
            em.remove(message);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Message> findMessageEntities() {
        return findMessageEntities(true, -1, -1);
    }

    public List<Message> findMessageEntities(int maxResults, int firstResult) {
        return findMessageEntities(false, maxResults, firstResult);
    }

    private List<Message> findMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Message.class));
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

    public Message findMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Message> rt = cq.from(Message.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
