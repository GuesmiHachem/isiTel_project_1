/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entity.Action;
import entity.Utilisateur;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.ManagerUtilisateur;

/**
 *
 * @author admin
 */
public class OpenFile extends HttpServlet {

    private HttpSession session;
    private String login;
    private String pwd;
    private Utilisateur sessionUser;

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
       
        session = request.getSession();
        sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        if (manager.ManagerUtilisateur.isAdmin(request)) {
          response.sendRedirect("MyFiles");
        } else {
            int idFile=Integer.parseInt(request.getParameter("idFile"));
            int idUser=Integer.parseInt(request.getParameter("idUser"));
            long activeTime = manager.ManagerAction.getActiveTime(session);
            Action a = new Action(0, new Date(), new Time(new Date().getTime()), 3, new Time(activeTime), sessionUser, manager.ManagerFichier.findFichier(idFile));
            
            manager.ManagerAction.create(a);
            response.sendRedirect("MyFiles");
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
