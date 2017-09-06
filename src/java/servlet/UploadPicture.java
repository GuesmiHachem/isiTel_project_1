/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

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

/**
 *
 * @author admin
 */
public class UploadPicture extends HttpServlet {

    private java.io.File f;
    private HttpSession session;
    private entity.Utilisateur sessionUser;
    private entity.Utilisateur user;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathDirector = manager.ManagerParameter.findParameter("dossier_user").getValeur();
        session = request.getSession();
        sessionUser = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        user = (entity.Utilisateur) session.getAttribute("user");

        if (manager.ManagerUtilisateur.isAdmin(request)) {
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    session = request.getSession();
                    FileItemFactory itemF = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(itemF);
                    List<FileItem> items = upload.parseRequest(request);
                    for (FileItem i : items) {
                        if (i.getContentType().substring(0, 5).equals("image")) {

                            f = new File(pathDirector + "/" + user.getId() + ".jpg");
                            f.createNewFile();
                            while (f == null) {

                            }
                            if (f != null) {
                                i.write(f);
                            }

                            request.getRequestDispatcher("MyUsers").forward(request, response);
                            f.notifyAll();
                        } else {
                            session.setAttribute(com.Parameter.sessionError, "Erreur");
                            request.getRequestDispatcher("User?num=" + user.getId()).forward(request, response);

                        }

                    }

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
