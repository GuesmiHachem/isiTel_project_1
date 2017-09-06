<%-- 
    Document   : nb_message_non_lu
    Created on : 15 août 2017, 14:44:58
    Author     : admin
--%>
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<option>choisir ....</option>
<%
    List<Utilisateur> listAction = (List<Utilisateur>) manager.ManagerUtilisateur.findUtilisateurEntities();

   for (int i = 0; i < listAction.size() ; i++) {
        Utilisateur u = listAction.get(i);
%>
<option> <%=u.getPrenom()+" "+u.getNom()%></option>
<%
    }
%>