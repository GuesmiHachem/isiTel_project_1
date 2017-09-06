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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class TableauDeBord extends HttpServlet {

    private HttpSession session;
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
        
        if (sessionUser == null) {
            String login = request.getParameter("login");
            String pwd = request.getParameter("pwd");
            sessionUser = ManagerUtilisateur.findUtilisateur(login, pwd);
        }
        if (sessionUser == null) {
            response.sendRedirect("");
        } else {
            session.setAttribute(com.Parameter.sessionUser, sessionUser);
            if (manager.ManagerUtilisateur.isAdmin(request)) {
                request.getRequestDispatcher("admin_tableauDeBord.jsp").forward(request, response);
            } else if (manager.ManagerUtilisateur.isUser(request)) {
                if (!manager.ManagerUtilisateur.estConnecter(sessionUser, getServletContext())) {
                    manager.ManagerUtilisateur.do_Connexion(request);
                    response.sendRedirect("MyFiles");
                } else {
                    session.invalidate();
                    response.sendRedirect("");
                }

            } else {
                response.sendRedirect("");
            }
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
