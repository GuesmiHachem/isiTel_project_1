<%-- 
    Document   : nb_message_non_lu
    Created on : 15 août 2017, 14:44:58
    Author     : admin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entity.Action"%>
<%@page import="java.util.Date"%>
<%@page import="entity.Utilisateur"%>
<%@page import="java.sql.Time"%>
<%@page import="java.util.List"%>
<table class="table table-hover table-responsive">
    <tr>
        <td><h4>ID</h4></td>
        <td><h4>Type d'action </h4></td>
        <td><h4>Date </h4></td>
        <td><h4>Heure </h4></td>
        <td><h4>Nom et Prenom</h4></td>
        <td><h4>Nom de Fichier</h4></td>
        <td><h4>Duree de Connexion</h4></td>


    </tr>

    <%

        List<Action> listAction = (List<Action>) getListAction(request);

        for (int i = listAction.size() - 1; i >= 0; i--) {
            Action action = listAction.get(i);
            if (action.getType() == 1) {

    %>
    <tr class='bg bg-info'>
        <td><h5><%=action.getId()%></h5></td>
        <td><h5 class="label label-success">Connexion</h5></td>
        <td><h5><%=getDateFormat(action.getDate())%> </h5></td>
        <td><h5><%=new Time(action.getHeure().getTime())%> </h5></td>
        <td><h5><%=action.getIdUser().getPrenom() + " " + action.getIdUser().getNom()%></h5></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
    </tr>
    <%        } else if (action.getType() == 2) {
    %>
    <tr class='bg bg-danger'>
        <td><h5><%=action.getId()%></h5></td>
        <td><h5 class="label label-danger">Deconnexion</h5></td>
        <td><h5><%=getDateFormat(action.getDate())%> </h5></td>
        <td><h5><%=new Time(action.getHeure().getTime())%> </h5></td>
        <td><h5><%=action.getIdUser().getPrenom() + " " + action.getIdUser().getNom()%></h5></td>
        <td><h5></h5></td>
        <td><h5><%=new Time(action.getDuree().getTime())%></h5></td>
    </tr>
    <%        } else {
    %>
    <tr class='bg bg-warning'>
        <td><h5><%=action.getId()%></h5></td>
        <td><h5 class="label label-warning">Ouverture de Fichier</h5></td>
        <td><h5><%=getDateFormat(action.getDate())%> </h5></td>
        <td><h5><%=new Time(action.getHeure().getTime())%> </h5></td>
        <td><h5><%=action.getIdUser().getPrenom() + " " + action.getIdUser().getNom()%></h5></td>
        <td><h5><%=action.getIdFichier().getNom()%> </h5></td>
        <td><h5></h5></td>
    </tr>
    <%        }
    %>

    <%
        }
    %>  
</table>

<%!public String getDateFormat(Date d) {
        int y = d.getYear();
        int m = d.getMonth();
        int j = d.getDate();

        String yy;
        String mm;
        String jj;

        m++;
        if (m <= 9) {
            mm = "0" + m;
        } else {
            mm = m + "";
        }

        if (j <= 9) {
            jj = "0" + j;
        } else {
            jj = j + "";
        }

        yy = (y + 1900) + "";

        return jj + "/" + mm + "/" + yy;

    }


%>

<%!public List<Action> getListAction(HttpServletRequest req) {
        String param1 = req.getParameter("id_user");

        int id_user = 0;
        
        boolean test1 = false;

        if (param1 != null) {
            id_user = Integer.parseInt(param1);
            test1 = true;
        }

        List<Action> listAction = (List<Action>) manager.ManagerAction.findActionEntities();
        List<Action> listActionResultat = new ArrayList<Action>();
        if (test1) {
            for (int i = 0; i < listAction.size(); i++) {
                Action action = listAction.get(i);
                Utilisateur u = action.getIdUser();
                int order = manager.ManagerUtilisateur.getOrderInTheList(u);
                if ((order + 1 == id_user)) {
                    listActionResultat.add(action);
                }

            }
            return listActionResultat;
        } else {
            return listAction;
        }

    }


%>