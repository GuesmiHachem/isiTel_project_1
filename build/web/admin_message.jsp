
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
        
        Utilisateur sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        List<entity.Utilisateur> listUtilisateur;
        if (sessionUser == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (sessionUser.getRole().equals("USER")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            listUtilisateur = manager.ManagerUtilisateur.findUtilisateurEntities();
            id = request.getParameter("id");


    %>

    <body class="gris_noir">
        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">

                <!-- =========================================================================== -->
                <!--   PANEL1 ================================================================== -->
                <!-- =========================================================================== -->
                <div class="col-lg-4">
                    <div class="list-group list-group-success">
                        <%
                            if (test(request)) {
                                for (int i = 0; i < listUtilisateur.size(); i++) {
                                    Utilisateur u = listUtilisateur.get(i);
                                    int nombreDeMessageNonLu = manager.ManagerMessage.nombreDeMessageNonLu(u.getId());
                                    if (u.getId() != sessionUser.getId()) {
                                        if (u.getId() == Integer.parseInt(id)) {
                                            out.println("<a href='Message?id=" + u.getId() + "' class='active  list-group-item '>");
                                            if (nombreDeMessageNonLu != 0) {
                                                out.println("<span class='badge'>");
                                                out.println(nombreDeMessageNonLu);
                                                out.println("</span>");
                                            }
                                            out.println(u.getPrenom() + " " + u.getNom());
                                            out.println("</a>");
                                        } else {
                                            out.println("<a href='Message?id=" + u.getId() + "' class='list-group-item '>");
                                            if (nombreDeMessageNonLu != 0) {
                                                out.println("<span class='badge '>");
                                                out.println(nombreDeMessageNonLu);
                                                out.println("</span>");
                                            }
                                            out.println(u.getPrenom() + " " + u.getNom());
                                            out.println("</a>");
                                        }

                                    }
                                }
                            } else {
                                for (int i = 0; i < listUtilisateur.size(); i++) {
                                    Utilisateur u = listUtilisateur.get(i);
                                    if (u.getId() != sessionUser.getId()) {
                                        int nombreDeMessageNonLu = manager.ManagerMessage.nombreDeMessageNonLu(u.getId());
                                        out.println("<a href='Message?id=" + u.getId() + "' class=' list-group-item ' >");
                                        if (nombreDeMessageNonLu != 0) {
                                            out.println("<span class='badge '>");
                                            out.println(nombreDeMessageNonLu);
                                            out.println("</span>");
                                        }
                                        out.println(u.getPrenom() + " " + u.getNom());
                                        out.println("</a>");
                                    }
                                }
                            }
                        %>
                    </div>

                </div>

                <!-- =========================================================================== -->
                <!--   PANEL2 ================================================================== -->
                <!-- =========================================================================== -->
                <div class="col-lg-8">
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
                                <div class="col-xs-12 ">
                                    <br>
                                    <a href="LireTousMessage?id=<%=id%>" class="btn btn-primary ">
                                        lire tout
                                    </a>
                                    <br>  <br>
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
                document.getElementById("message_admin").innerHTML = this.responseText;
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
        setInterval(tout, 1);
    }

</script>
