/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import dao.UtilisateurJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import entity.Action;
import entity.Fichier;
import entity.Utilisateur;
import java.io.File;
import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
public class ManagerUtilisateur {

    public static UtilisateurJpaController utilisateurJpaController = dao.FactoryDao.getUtilisateurJpaController();

    //******************************************************
    public static entity.Utilisateur findUtilisateur(String login, String pwd) {

        List<Utilisateur> list = utilisateurJpaController.findUtilisateurEntities();
        Iterator<Utilisateur> it = list.iterator();
        while (it.hasNext()) {
            Utilisateur u = it.next();
            if (u.getLogin().equals(login) && u.getPassword().equals(pwd)) {
                return u;
            }
        }
        return null;
    }

    //******************************************************
    public static entity.Utilisateur findUtilisateur(String login) {
        List<Utilisateur> list = utilisateurJpaController.findUtilisateurEntities();
        Iterator<Utilisateur> it = list.iterator();
        while (it.hasNext()) {
            Utilisateur u = it.next();
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    //******************************************************
    public static entity.Utilisateur findUtilisateur(int id) {
        Utilisateur u = utilisateurJpaController.findUtilisateur(id);
        if (u != null) {
            return u;
        }
        return null;
    }

    //******************************************************
    public static void create(Utilisateur user) {
        utilisateurJpaController.create(user);
    }

    //******************************************************
    public static void destroy(int id) {
        try {
            String dossier_fichier = manager.ManagerParameter.findParameter("dossier_user").getValeur();
            String chemin = dossier_fichier + "/" + id + ".jpg";
            File f = new File(chemin);
            f.delete();
            utilisateurJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ManagerUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static void edit(Utilisateur user) {
        try {
            utilisateurJpaController.edit(user);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManagerUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static List<Utilisateur> findUtilisateurEntities() {

        List<Utilisateur> list = utilisateurJpaController.findUtilisateurEntities();
        return list;
    }
    //******************************************************

    public static int getOrderInTheList(Utilisateur utilisateur) {

        List<Utilisateur> list = utilisateurJpaController.findUtilisateurEntities();
        for (int i = 0; i < list.size(); i++) {
            Utilisateur u = list.get(i);
            if (u.equals(utilisateur)) {
                return i;
            }
        }
        return 0;
    }
    //******************************************************

    public static boolean estConnecter(Utilisateur utilisateur, ServletContext app) {

        List<HttpSession> listSession = (List<HttpSession>) app.getAttribute("listSession");
        HttpSession session;
        Utilisateur sessionUser;
        for (int i = 0; i < listSession.size(); i++) {
            session = listSession.get(i);
            sessionUser = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
            if (sessionUser.getId().equals(utilisateur.getId())) {
                return true;
            }
        }
        return false;
    }

    //******************************************************
    public static boolean isAdmin(HttpServletRequest req) {

        HttpSession session;
        Utilisateur utilisateur;
        session = req.getSession();
        utilisateur = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        if (utilisateur == null) {
            return false;
        }
        if (!utilisateur.getRole().equals("ADMIN")) {
            return false;
        }
        return true;
    }
    //******************************************************

    public static boolean isUser(HttpServletRequest req) {
        HttpSession session;
        Utilisateur utilisateur;
        session = req.getSession();
        utilisateur = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        if (utilisateur == null) {
            return false;
        }
        if (!utilisateur.getRole().equals("USER")) {
            return false;
        }
        return true;
    }

    public static void do_Deconnexion(HttpServletRequest req, Utilisateur utilisateur) {
        ServletContext application = req.getServletContext();
        List<HttpSession> list = (List<HttpSession>) application.getAttribute("listSession");
        if (estConnecter(utilisateur, application)) {
            for (int i = 0; i < list.size(); i++) {
                HttpSession session = list.get(i);
                Utilisateur u = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
                if (u.getId() == utilisateur.getId()) {
                    Action a = new Action(null, new Date(), new Time(new Date().getTime()), 2,new Time(manager.ManagerAction.getActiveTime(session)), utilisateur, null);
                    manager.ManagerAction.create(a);
                    list.remove(i);
                }
            }
            application.setAttribute("listSession", list);

        }
        req.getSession().invalidate();
    }

    public static void do_Connexion(HttpServletRequest req) {
        ServletContext application = req.getServletContext();
        HttpSession session = req.getSession();
        Date d = new Date();
        session.setAttribute(com.Parameter.sessionTime, d.getTime());
        session.setAttribute(com.Parameter.sessionDate, d);

        Utilisateur u = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        List<HttpSession> list = (List<HttpSession>) application.getAttribute("listSession");
        if (!estConnecter(u, application)) {
            list.add(session);
        }
        application.setAttribute("listSession", list);
        Action a = new Action(0, new Date(), new Time(new Date().getTime()), 1, null, u, null);
        manager.ManagerAction.create(a);

    }

    public static void main(String[] args )
    {
    
    
    }
    
}
