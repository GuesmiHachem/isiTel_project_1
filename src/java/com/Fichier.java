/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.File;

/**
 *
 * @author admin
 */
public class Fichier {

    public static String[] tab_word = new String[]{"doc", "docx"};
    public static String[] tab_excel = new String[]{"xlsx", "xlsm", "xls"};

    public static String getExtension(String name) {
        int indice = name.lastIndexOf(".");
        return name.substring(indice + 1);
    }

    public static boolean existIn(String[] tab, String ext) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].equals(ext)) {
                return true;
            }
        }
        return false;
    }

    public static File getFile(entity.Fichier f) {
        File ficher = new File(f.getChemin());
        return ficher;
    }
    
    public static boolean isFichierExcel(entity.Fichier f) {
       String ext=getExtension(f.getNom());
       if(existIn(tab_excel, ext))
       {
       return true;
       }
       return false;
    }
    public static boolean isFichierWord(entity.Fichier f) {
       String ext=getExtension(f.getNom());
       if(existIn(tab_word, ext))
       {
       return true;
       }
       return false;
    }
    public static boolean isPictureExist(int id) {
        String path = manager.ManagerParameter.findParameter("dossier_user").getValeur();
        File f = new File(path + "/"+id + ".jpg");
        if (f.exists()) {
            return true;
        }
        return false;

    }
    
}
