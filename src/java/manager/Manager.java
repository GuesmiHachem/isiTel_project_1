/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;


import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author admin
 */
public class Manager implements Serializable {

    
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManager() {
        
        emf=Persistence.createEntityManagerFactory("projectPU");
        return emf;
    }

   public static void close() {
        
        emf.close();
    }
    
}
