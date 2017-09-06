/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import entity.Parameter;
import dao.ParameterJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import entity.Utilisateur;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ManagerParameter {

    public static ParameterJpaController ParameterJpaController = dao.FactoryDao.getParameterJpaController();

    //******************************************************
    public static entity.Parameter findParameter(String nom) {
        List<Parameter> list = ParameterJpaController.findParameterEntities();
        Iterator<Parameter> it = list.iterator();
        while (it.hasNext()) {
            Parameter u = it.next();
            if (u.getNom().equals(nom)) {
                return u;
            }
        }
        return null;
    }

    //******************************************************
    public static entity.Parameter findParameter(int id) {
        Parameter p = ParameterJpaController.findParameter(id);
        if (p != null) {
            return p;
        }
        return null;
    }

    //******************************************************
    public static void create(Parameter parameter) {
        ParameterJpaController.create(parameter);
    }

    //******************************************************
    public static void destroy(int id) {
        try {
            ParameterJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerParameter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static void edit(Parameter parameter) {
        try {
            ParameterJpaController.edit(parameter);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManagerParameter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManagerParameter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //******************************************************
    public static List<Parameter> findParameterEntities() {

        List<Parameter> list = ParameterJpaController.findParameterEntities();
        return list;
    }
    //******************************************************
   
}
