
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
        if (sessionUser == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (sessionUser.getRole().equals("USER")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {


    %>
    <body class="gris_noir" >
        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-xs-12 col-md-12">

                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title glyphicon glyphicon-user"> Mon Profile </h3>
                        </div>
                        <div class="panel-body ">
                            <div class="row">
                                <div class="col-sm-12">
                                    <a href="#">

                                        <%
                                            if (com.Fichier.isPictureExist(sessionUser.getId())) {
                                        %>
                                        <img class=" img-rounded" src="view/img_user/<%=sessionUser.getId()%>.jpg" width="100" height="100"/><br>

                                        <%
                                        } else {
                                        %>
                                        <img class=" img-rounded" src="view/img_user/inconnu.jpg" width="100" height="100"/>

                                        <%
                                            }

                                        %> </a>
                                </div>
                                <div class="col-xs-12 ">
                                    <form action="UpdateMyProfile" method="post" class="form-horizontal pull-left col-xs-12">
                                        <!---------------------------------------------------------->
                                        <div class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label pull-left"> Nom</label>
                                            <div class="col-sm-9">
                                                <input value="<%=sessionUser.getNom()%>" name="name" type="text" class="form-control" id="inputEmail3" placeholder="Nom" >
                                            </div>
                                        </div>
                                        <!---------------------------------------------------------->
                                        <div class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label pull-left"> Prenom</label>
                                            <div class="col-sm-9">
                                                <input value="<%=sessionUser.getPrenom()%>" name="firstname" type="text" class="form-control" id="inputEmail3" placeholder="Prenom">
                                            </div>
                                        </div>
                                        <!---------------------------------------------------------->
                                        <div class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label pull-left">Login</label>
                                            <div class="col-sm-9">
                                                <input value="<%=sessionUser.getLogin()%>" name="login" type="text" class="form-control" id="inputEmail3" placeholder="Login">
                                            </div>
                                        </div>
                                        <!---------------------------------------------------------->
                                        <div class="form-group">
                                            <label for="inputEmail3" class="col-sm-3 control-label pull-left"> Mot de passe</label>
                                            <div class="col-sm-9">
                                                <input value="<%=sessionUser.getPassword()%>" name="password" type="password" class="form-control" id="inputEmail3" placeholder="Mot de passe">
                                            </div>
                                        </div>
                                        <!---------------------------------------------------------->
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <button type="submit" class="btn btn-primary"> Modifier </button>
                                                <button type="reset" class="btn   btn-default"> Annuler </button>
                                            </div>
                                        </div>
                                        <!---------------------------------------------------------->
                                    </form>
                                </div>

                            </div>
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