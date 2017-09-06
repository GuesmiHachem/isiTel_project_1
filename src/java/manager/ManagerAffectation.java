/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import dao.AffectationJpaController;
import dao.exceptions.NonexistentEntityException;
import entity.Affectation;
import entity.Fichier;
import entity.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTMLDocument;
import static manager.ManagerUtilisateur.utilisateurJpaController;

/**
 *
 * @author admin
 */
public class ManagerAffectation {
    
    public static AffectationJpaController affectationJpaController = dao.FactoryDao.getAffectationJpaController();

    //******************************************************
    public static entity.Affectation findAffectation(int id) {
        Affectation f = affectationJpaController.findAffectation(id);
        if (f != null) {
            return f;
        }
        return null;
    }

    //******************************************************
    public static void create(Affectation f) {
        affectationJpaController.create(f);
    }

    //******************************************************
    public static void destroy(int id) {
        try {
            affectationJpaController.destroy(id);
        } catch (dao.exceptions.NonexistentEntityException ex) {
            Logger.getLogger(ManagerAffectation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static void edit(Affectation f) {
        try {
            affectationJpaController.edit(f);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerAffectation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManagerAffectation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static List<Affectation> findAffectationEntities() {
        
        List<Affectation> list = affectationJpaController.findAffectationEntities();
        return list;
    }

    //******************************************************
    public static void deleteAllAffectationOfUser(Utilisateur user) {
        
        List<Affectation> list = findAffectationEntities();
        for (int i = 0; i < list.size(); i++) {
            entity.Affectation a = list.get(i);
            if (a.getIdUser().getId() == user.getId()) {
                destroy(a.getId());
            }
        }
    }
//******************************************************

    public static void affecte(List<Fichier> list, Utilisateur user) {
        deleteAllAffectationOfUser(user);
        Affectation a;
        for (int i = 0; i < list.size(); i++) {
            Fichier f = list.get(i);
            a = new Affectation();
            a.setIdFichier(f);
            a.setIdUser(user);
            create(a);
        }
        
    }

    //******************************************************
    public static List<Fichier> findAllFichierByUser(int id) {
        
        List<Affectation> list = findAffectationEntities();
        List<Fichier> list_fichier = new ArrayList<Fichier>();
        for (int i = 0; i < list.size(); i++) {
            Affectation a = list.get(i);
            if (a.getIdUser().getId() == id) {
                list_fichier.add(a.getIdFichier());
            }
         }
        return list_fichier;
    }
    //******************************************************
    public static boolean isAffectationExist(Fichier fichier,Utilisateur utilisateur) {
        
        List<Affectation> list = findAffectationEntities();
       List<Fichier> list_fichier = new ArrayList<Fichier>();
        for (int i = 0; i < list.size(); i++) {
            Affectation a = list.get(i);
            if (a.getIdUser().equals(utilisateur)&&a.getIdFichier().equals(fichier)) {
               return true;
            }
         }
        return false;
    }
}
