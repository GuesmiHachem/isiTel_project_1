<%@page import="entity.Utilisateur"%>
<%@page import="entity.Message"%>
<%@page import="java.util.List"%>
<%
    int id1 = Integer.parseInt(request.getParameter("id1"));
    int id2 = Integer.parseInt(request.getParameter("id2"));
    List<Message> list_message = manager.ManagerMessage.findMessageEntities(id1, id2);
    Utilisateur u1 = manager.ManagerUtilisateur.findUtilisateur(id1);
    Utilisateur u2 = manager.ManagerUtilisateur.findUtilisateur(id2);
    for (int i = 0; i < list_message.size(); i++) {
        Message m = list_message.get(i);
        String contenu = m.getContenu();
        if (m.getIdSource().getId() == id2) {

%>


<div class="">
    <span class="alert alert-danger alert-dismissable fade in col-xs-12  col-md-offset-7 col-md-5"><p><h5 class="text text-left"><%=contenu%></h5></p></span>
    <div class="col-xs-12">
        <br>
    </div>
</div>

<%} else {%>

<div class="">
    <span class="alert alert-success alert-dismissable fade in col-xs-12 col-md-5"><p><h5 class="text text-left"><%=contenu%></h5></p></span>
    <div class="col-xs-12">
        <br>
    </div>
</div>


<%}
    }
%>