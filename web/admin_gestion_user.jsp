
<%@page import="servlet.UploadPicture"%>
<%@page import="entity.Fichier"%>
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URI"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Administrateur</title>
        <!--   LINK_SCRIPT ===================================================================== -->
        <%@include file="linkandscript.jsp"%>
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
    window.onload = function () {
        message();
        setInterval(message, 1000);

    }
</script>
    </head>
    <%
        
        Utilisateur sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        List<entity.Utilisateur> sessionListUser;
        if (sessionUser == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (sessionUser.getRole().equals("USER")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            sessionListUser = manager.ManagerUtilisateur.findUtilisateurEntities();

    %>
    <body class="" >

        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   

            <div class="row">
                <div class="col-xs-12 col-md-12">

                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title  glyphicon glyphicon-user"> Utilisateurs </h3>
                        </div>
                        <div class="panel-body ">

                            <table class="table table-responsive table-hover">
                                <tr class="">
                                    <td><h4>#</h4></td>
                                    <td><h4>Photo</h4></td>
                                    <td><h4>Nom</h4></td>
                                    <td><h4>Prenom</h4></td>
                                    <td><h4>Login</h4></td>
                                    <td><h4>Password</h4></td>
                                    <td><h4>Active</h4></td>
                                    <td><h4>Role</h4></td>
                                </tr>
                                <%
                                    for (int i = 0; i < sessionListUser.size(); i++) {
                                        Utilisateur user = sessionListUser.get(i);
                                        int id = user.getId();
                                        String nameandLasName = user.getPrenom() + " " + user.getNom();
                                %>
                                <tr> 
                                    <td><%=id%></td>
                                    <td>
                                        <a href="User?num=<%=id%>">
                                            <%
                                                if (com.Fichier.isPictureExist(id)) {
                                            %>
                                            <img class="img img-rounded " src="view/img_user/<%=id%>.jpg" width="80" height="80"/><br>

                                            <%
                                            } else {
                                            %>
                                            <img class="img img-rounded" src="view/img_user/inconnu.jpg" width="80" height="80"/><br>

                                            <%
                                                }

                                            %>

                                        </a>
                                    </td>
                                    <td><h5><%=user.getNom()%></h5></td>
                                    <td><h5><%=user.getPrenom()%></h5></td>
                                    <td><h5><%=user.getLogin()%></h5></td>
                                    <td><h5><%=user.getPassword()%></h5></td>
                                    <td>
                                        <%if (user.getActive()== true) {%>
                                        <h5><span class="label label-success">Activer</span></h5>
                                        <%} else {%>
                                        <h5><span class="label label-danger">Desactiver</span></h5>
                                        <%}%>
                                    </td>
                                    <td><h5><%=user.getRole()%></h5></td>
                                </tr>
                                <%
                                    }
                                %>
                            </table>
                        </div><%--panel body --%>
                    </div><%--panel --%>  
                </div>
            </div>
        </div>
        <!--   FOOTER ===================================================================== -->
        <%@include file="include_footer.jsp"%>   
    </body>
    <%}%>
</html>