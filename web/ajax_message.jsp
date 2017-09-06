<%-- 
    Document   : nb_message_non_lu
    Created on : 15 août 2017, 14:44:58
    Author     : admin
--%>
<%@page import="entity.Utilisateur"%>
<% 
    Utilisateur sessionUser_aa = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
    int nb = manager.ManagerMessage.nombreDeMessageNonLuTotale(sessionUser_aa.getId());
    if (nb > 0) {
        out.print(nb);
    }else{
    out.print("");
    } 

%>