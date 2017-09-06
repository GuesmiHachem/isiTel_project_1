/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author admin
 */
public class FactoryDao {

    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManager() {

        emf = Persistence.createEntityManagerFactory("projectPU");
        return emf;
    }

    public static AffectationJpaController getAffectationJpaController() {
        return new AffectationJpaController(getEntityManager());
    }

    public static ActionJpaController getActionJpaController() {
        return new ActionJpaController(getEntityManager());
    }


    public static FichierJpaController getFichierJpaController() {
        return new FichierJpaController(getEntityManager());
    }


    public static MessageJpaController getMessageJpaController() {
        return new MessageJpaController(getEntityManager());
    }

    public static ParameterJpaController getParameterJpaController() {
        return new ParameterJpaController(getEntityManager());
    }

    public static UtilisateurJpaController getUtilisateurJpaController() {
        return new UtilisateurJpaController(getEntityManager());
    }

}
