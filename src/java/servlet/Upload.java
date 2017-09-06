package servlet;

import entity.Fichier;
import entity.Utilisateur;
import java.io.File;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Upload extends HttpServlet {

    private HttpSession session;
    private Utilisateur sessionUser;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @SuppressWarnings("empty-statement")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      
        if (manager.ManagerUtilisateur.isAdmin(request)) {
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    String[] tab = new String[]{"xlsx", "xlsm", "docx", "xls"};
                    FileItemFactory itemF = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(itemF);
                    List<FileItem> items = upload.parseRequest(request);
                    FileItem i = items.get(0);
                    response.getWriter().print(i.getName());
                    String nom = getNomDeFichier(i.getName());
                    String ext = manager.ManagerFichier.getExtension(nom);
                    String dossier_fichier = manager.ManagerParameter.findParameter("dossier_fichier").getValeur();
                    String chemin = dossier_fichier + "/" + nom;
                    entity.Fichier f = new Fichier(0, nom, chemin);
                    if (com.Fichier.existIn(tab, ext)) {
                        File file = new File(chemin);
                        if (file.createNewFile()) {
                            if (file != null) {
                                i.write(file);
                            }
                            manager.ManagerFichier.create(f);
                        }
                    } 
                    response.sendRedirect("MyFiles");

                } catch (FileUploadException e) {
                    System.out.print(e.getMessage());
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        } else {
            response.sendRedirect("");
        }

    }
    
    public static String getNomDeFichier(String ch)
    {
        
      if(ch.contains("\\"))
      {
      return ch.substring(ch.lastIndexOf("\\")+1);
      
      }
      return ch;
    }
    
    

}
