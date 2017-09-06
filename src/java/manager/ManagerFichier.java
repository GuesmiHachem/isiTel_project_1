/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import dao.FichierJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import entity.Fichier;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ManagerFichier {

    public static FichierJpaController fichierJpaController = dao.FactoryDao.getFichierJpaController();

    //******************************************************
    public static entity.Fichier findFichierbyNom(String nom) {

        List<Fichier> list = fichierJpaController.findFichierEntities();
        Iterator<Fichier> it = list.iterator();
        while (it.hasNext()) {
            Fichier f = it.next();
            if (f.getNom().equals(nom)) {
                return f;
            }
        }
        return null;
    }

    //******************************************************
    public static entity.Fichier findFichierbyChemin(String chemin) {

        List<Fichier> list = fichierJpaController.findFichierEntities();
        Iterator<Fichier> it = list.iterator();
        while (it.hasNext()) {
            Fichier f = it.next();
            if (f.getNom().equals(chemin)) {
                return f;
            }
        }
        return null;
    }

    //******************************************************
    public static entity.Fichier findFichier(int id) {
        Fichier f = fichierJpaController.findFichier(id);
        if (f != null) {
            return f;
        }
        return null;
    }

    //******************************************************
    public static void create(Fichier f) {
        fichierJpaController.create(f);
    }

    //******************************************************
    public static void destroy(int id) {
        try {
            Fichier f = findFichier(id);
            File file = new File(f.getChemin());
            if (file.delete()) {
                fichierJpaController.destroy(f.getId());
            }
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ManagerFichier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static void edit(Fichier f) {
        try {
            fichierJpaController.edit(f);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerFichier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManagerFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //******************************************************

    public static String getExtension(String name) {
        int indice = name.lastIndexOf(".");
        return name.substring(indice + 1);
    }

    //******************************************************
    public static List<Fichier> findFichierEntities() {

        List<Fichier> list = fichierJpaController.findFichierEntities();
        return list;
    }

    //******************************************************
    public static void renommer(Fichier f, String nom) {
        File file = new File(f.getChemin());
        String path = manager.ManagerParameter.findParameter("dossier_fichier").getValeur();
        String ext = getExtension(f.getNom());
        String nouveau_nom = nom + "." + ext;
        String nouveau_chemin = path + "/" + nom + "." + ext;
        if (file.renameTo(new File(nouveau_chemin))) {
            f.setNom(nouveau_nom);
            f.setChemin(nouveau_chemin);
            edit(f);
        }

    }

    //******************************************************
}
