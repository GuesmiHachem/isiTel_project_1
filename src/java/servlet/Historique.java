/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entity.Utilisateur;
import java.io.IOException;
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
public class Historique extends HttpServlet {

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

        if (manager.ManagerUtilisateur.isAdmin(request)) {
            
            String ch = request.getParameter("select");
            int id;
            if (!estValide(ch)) {
                request.getRequestDispatcher("admin_historique.jsp?id=0").forward(request, response);
            } else {
                id = Integer.parseInt(ch);
                response.getWriter().print(ch);
                //request.getRequestDispatcher("admin_historique.jsp?id="+id).forward(request, response);
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

    public static boolean estValide(String ch) {
        if (ch == null) {
            return false;
        }
        if (ch.equals("")) {
            return false;
        }
        if (!estEntier(ch)) {
            return false;
        }
        if (manager.ManagerUtilisateur.findUtilisateur(Integer.parseInt(ch)) == null) {
            return false;
        }
        return true;
    }

    public static boolean estEntier(String ch) {

        for (int i = 0; i < ch.length(); i++) {
            char c = ch.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
