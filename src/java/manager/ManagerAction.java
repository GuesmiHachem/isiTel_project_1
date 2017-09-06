/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import dao.ActionJpaController;
import dao.exceptions.NonexistentEntityException;
import entity.Action;
import entity.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class ManagerAction {

    public static ActionJpaController actionJpaController = dao.FactoryDao.getActionJpaController();

    //******************************************************
    public static entity.Action findAction(int id) {
        Action f = actionJpaController.findAction(id);
        if (f != null) {
            return f;
        }
        return null;
    }

    //******************************************************
    public static void create(Action f) {
        actionJpaController.create(f);
    }

    //******************************************************
    public static void destroy(int id) {
        try {
            actionJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static void edit(Action f) {
        try {
            actionJpaController.edit(f);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManagerAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static List<Action> findActionEntities() {

        List<Action> list = actionJpaController.findActionEntities();
        return list;
    }

    //******************************************************
    public static long getActiveTime(HttpSession session) {
        Date datenow = new Date();
        long timenow = datenow.getTime();
        Date dateDeConnexion = (Date) session.getAttribute(com.Parameter.sessionDate);
        long timeDeConnexion = (Long) session.getAttribute(com.Parameter.sessionTime);
        long activeTime = Math.abs(timenow - timeDeConnexion) - (60 * 60 * 1000);
        return activeTime;

    }

    //******************************************************
    public static long getInactiveTime(HttpSession session) {
        Date datenow = new Date();
        long timenow = datenow.getTime();
        Date dateDeConnexion = (Date) session.getAttribute(com.Parameter.sessionDate);
        long timeDeConnexion = (Long) session.getAttribute(com.Parameter.sessionTime);
        long inactiveTime = Math.abs(timenow - session.getLastAccessedTime());
        return inactiveTime;

    }

    //******************************************************
    public static float getDureeActivation(Utilisateur utilisateur) {
        List<Action> list = findActionEntities();
        long nb = 0;
        for (int i = 0; i < list.size(); i++) {
            Action a = list.get(i);
            Utilisateur u = a.getIdUser();
            int typeAction = a.getType();
            if (typeAction == 2) {
                if (u.equals(utilisateur)) {
                    Date t = a.getDuree();
                    int h = t.getHours();
                    int m = t.getMinutes();
                    int s = t.getSeconds();
                    int tot = s + m * 60 + h * 3600;
                    System.out.println(h + "     " + m + "     " + s + "   " + tot + "   " + (float) tot / 3600 + "\n");
                    nb = nb + tot;
                }

            }

        }
        return (float) nb / 3600;

    }

    //******************************************************

    public static List<Action> getListAction(int id_user) {

        List<Action> listAction = (List<Action>) manager.ManagerAction.findActionEntities();
        List<Action> listActionResultat = new ArrayList<Action>();

        for (int i = 0; i < listAction.size(); i++) {
            Action action = listAction.get(i);
            Utilisateur u = action.getIdUser();
            int order = manager.ManagerUtilisateur.getOrderInTheList(u);
            if ((order + 1 == id_user)) {
                listActionResultat.add(action);
            }

        }
        if(id_user==0)
        {
         return listAction;
        }
        else
        {
          return listActionResultat;
        }
       

    }

    //******************************************************
    public static void main(String[] a) {
        
       
        
    }

}
