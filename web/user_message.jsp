
<%@page import="entity.Message"%>
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Index</title>
        <!--   LINK_SCRIPT ===================================================================== -->
        <%@include file="linkandscript.jsp"%>


    </head>

    <%!String id;%>
    <%!

        public static boolean test(HttpServletRequest request) {
            String id_destination = request.getParameter("id");
            if (id_destination != null) {
                if (manager.ManagerMessage.isInteger(id_destination)) {
                    if (manager.ManagerUtilisateur.findUtilisateur(Integer.parseInt(id_destination)) != null) {
                        return true;
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            }
            return false;
        }
    %>

    <%
        
        entity.Utilisateur sessionUser = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        List<Utilisateur> listUtilisateur;
        if ((manager.ManagerParameter.findParameter("activer").getValeur().equals("non")) || (sessionUser.getActive() == false)) {
            session.setAttribute(com.Parameter.sessionUser, null);
            session.setAttribute(com.Parameter.sessionError, null);
    %>
    <!--   ERREUR ===================================================================== -->
    <%@include file="include_erreur.jsp"%>
    <%
    } else if (sessionUser == null) {
        response.sendRedirect("");
    } else if (sessionUser.getRole().equals("ADMIN")) {
        response.sendRedirect("");
    } else {
        listUtilisateur = manager.ManagerUtilisateur.findUtilisateurEntities();
        id = request.getParameter("id");

    %>

    <body class="gris_noir">



        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_user.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">

                <!-- =========================================================================== -->
                <!--   PANEL1 ================================================================== -->
                <!-- =========================================================================== -->

                <!-- =========================================================================== -->
                <!--   PANEL2 ================================================================== -->
                <!-- =========================================================================== -->
                <div class="col-lg-12">
                    <div class="panel panel-primary ">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title  glyphicon glyphicon-envelope"> Message</h3>
                        </div>
                        <div class="panel-body ">
                            <%if (test(request)) {%>
                            <div   class="col-xs-12">
                                <!-- =========================================================================== -->
                                <!--   LIST_MESSAGE ============================================================ -->
                                <!-- =========================================================================== -->
                                <div id="mydiv" class="col-xs-12 message ">
                                    <%
                                        int id1 = Integer.parseInt(id);
                                        int id2 = sessionUser.getId();
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
                                </div>

                                <!-- =========================================================================== -->
                                <!--   WRITE_MESSAGE =========================================================== -->
                                <!-- =========================================================================== -->
                                <div class="col-xs-12">
                                    <form action="AddMessage" method="GET">

                                        <div class="input-group">
                                            <input type="text" class="form-control input" name="contenu">
                                            <input type="hidden" class="form-control input-lg" name="id_source" value="<%=sessionUser.getId()%>">
                                            <input type="hidden" class="form-control input-lg" name="id_destination" value="<%=id%>">
                                            <span class="input-group-btn">
                                                <input type="submit"  class="btn  bg-primary " value="Envoyer">
                                            </span>

                                        </div>
                                    </form>
                                </div>
                            </div>
                            <%} %>
                        </div>

                    </div> 
                </div>
            </div>
        </div>

        <!--   FOOTER ===================================================================== -->
        <%@include file="include_footer.jsp"%>               
    </body>
    <%}
    %>
</html>
<script>

    function message() {
        var xhttp;

        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("message_user").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "ajax_message.jsp", true);
        xhttp.send();


    }

    function scrollToBottom() {
        var div = document.getElementById("mydiv");
        div.scrollTop = div.scrollHeight - div.clientHeight;
    }
    function tout()
    {

        message();

    }
    window.onload = function () {
        scrollToBottom();
        tout();
        setInterval(tout, 10);


    }
</script>
