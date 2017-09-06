/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import dao.MessageJpaController;
import dao.exceptions.NonexistentEntityException;
import entity.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ManagerMessage {

    public static MessageJpaController messageJpaController = dao.FactoryDao.getMessageJpaController();

    //******************************************************
    public static entity.Message findMessage(int id) {
        Message f = messageJpaController.findMessage(id);
        if (f != null) {
            return f;
        }
        return null;
    }

    //******************************************************
    public static void create(Message f) {
        messageJpaController.create(f);
    }

    //******************************************************
    public static void destroy(int id) {
        try {
            messageJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static void edit(Message f) {
        try {
            messageJpaController.edit(f);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManagerMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static List<Message> findMessageEntities() {

        List<Message> list = messageJpaController.findMessageEntities();
        return list;
    }

    //******************************************************
    public static List<Message> findMessageEntities(int id1, int id2) {

        List<Message> list = messageJpaController.findMessageEntities();
        List<Message> list_return = new ArrayList<Message>();

        for (int i = 0; i < list.size(); i++) {
            Message m = list.get(i);
            if ((m.getIdSource().getId() == id1 && m.getIdDestination().getId() == id2)
                    || (m.getIdSource().getId() == id2 && m.getIdDestination().getId() == id1)) {
                list_return.add(m);
            }
        }
        return list_return;
    }
    //******************************************************
    public static boolean isInteger(String ch) {
        for (int i = 0; i < ch.length(); i++) {
            if (ch.charAt(i) < '0' || ch.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    //******************************************************
    public static int nombreDeMessageNonLuTotale(int id_destination)
    {
     List<Message> list = messageJpaController.findMessageEntities();
     int nb=0;
      for (int i = 0; i < list.size(); i++) {
            Message m = list.get(i);
            if ((m.getMessageLu()==false)&&(m.getIdDestination().getId()==id_destination)){
                   nb++;
            }
        }
      return nb;
    }
    //******************************************************
    public static int nombreDeMessageNonLu(int id_source)
    {
     List<Message> list = messageJpaController.findMessageEntities();
     int nb=0;
      for (int i = 0; i < list.size(); i++) {
            Message m = list.get(i);
            if ((m.getMessageLu()==false)&&(m.getIdSource().getId()==id_source)){
                   nb++;
            }
        }
      return nb;
    }
    //******************************************************
    public static void setAllMessageLu(int id_source)
    {
     List<Message> list = messageJpaController.findMessageEntities();
     int nb=0;
      for (int i = 0; i < list.size(); i++) {
            Message m = list.get(i);
            if (m.getIdSource().getId()==id_source){
                  m.setMessageLu(true);
                  edit(m);
            }
        }
    }
    //******************************************************
}
