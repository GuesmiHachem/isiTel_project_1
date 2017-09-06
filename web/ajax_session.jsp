
<%@page import="java.sql.Time"%>
<%@page import="java.util.Date"%>
<%@page import="entity.Action"%>
<%@page import="entity.Utilisateur"%>
<%@page import="java.util.List"%>
<table class="table table-hover table-responsive">
    <tr>

        <td><h4>Nom et Prenom</h4></td>
        <td><h4>Date de connexion</h4></td>
        <td><h4>Heure de connexion</h4></td>
        <td><h4>Duree d'activation </h4></td>

    </tr>

    <%
        List<HttpSession> listSession = (List<HttpSession>) application.getAttribute("listSession");

        for (int i = 0; i < listSession.size(); i++) {
            HttpSession sess = (HttpSession) listSession.get(i);
            Utilisateur u = (Utilisateur) sess.getAttribute(com.Parameter.sessionUser);
            Date dateDeConnexion = (Date) sess.getAttribute(com.Parameter.sessionDate);
            long timeDeConnexion = (Long) sess.getAttribute(com.Parameter.sessionTime);
            long inactiveTime = manager.ManagerAction.getInactiveTime(sess);
            long activeTime = manager.ManagerAction.getActiveTime(sess);
            if (inactiveTime >= 10000) {
                Action a = new Action(null, new Date(), new Time(new Date().getTime()), 2, new Time(activeTime), u, null);
                manager.ManagerAction.create(a);
                listSession.remove(sess);
                sess.invalidate();
                application.setAttribute("listSession", listSession);

            }

    %>
    <tr>

        <td> <h4><a href="User?num=<%=u.getId()%>"><span class="label label-default"><%=u.getPrenom() + " " + u.getNom()%></span></a></h4></td>
        <td> <h4><span class="label label-info"><%=(dateDeConnexion.getYear() + 1900) + "-" + (dateDeConnexion.getMonth() + 1) + "-" + dateDeConnexion.getDate()%></span></h4></td>
        <td> <h4><span class="label label-warning"><%=new Time(timeDeConnexion)%></span></h4></td>
        <td>
            <h4>
                <span class="label label-success"><%= new Time(activeTime)%></span>
            </h4>
        </td>

    </tr>
    <%
        }
    %>  
</table>

