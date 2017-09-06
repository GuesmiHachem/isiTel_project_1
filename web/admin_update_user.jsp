
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
        List<entity.Fichier> sessionListFichier;
        Utilisateur user;
        if (sessionUser == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (sessionUser.getRole().equals("USER")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {

            user = (Utilisateur) session.getAttribute("user");
            sessionListFichier = manager.ManagerFichier.findFichierEntities();

    %>

    <body class="gris_noir" >

        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <!--================================================================================== --> 
                <!--   COLONNE 1 ===================================================================== -->
                <!--================================================================================== -->
                <div class="col-xs-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title glyphicon glyphicon-user"> Utilisateur</h3>
                        </div>
                        <div class="panel-body ">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="col-xs-12 col-md-5">
                                        <%
                                            if (com.Fichier.isPictureExist(user.getId())) {
                                        %>
                                        <img class=" img-rounded" src="view/img_user/<%=user.getId()%>.jpg" width="100" height="100"/><br>
                                        <%
                                        } else {
                                        %>
                                        <img class=" img-rounded" src="view/img_user/inconnu.jpg" width="100" height="100"/>

                                        <%
                                            }
                                        %>
                                    </div>
                                    <div class=" col-xs-12 col-md-3">
                                        <br>
                                    </div>
                                    <div class="  col-xs-12 col-md-4">
                                        <div class=" list-group list-group-success">
                                            <a href="DeleteUser" class="list-group-item list-group-item-danger active"> Supprimer</a>
                                        </div>
                                        <div class=" list-group list-group-success">
                                            <%if (user.getActive()) {
                                            %>
                                            <a href="ActiveUser" class="list-group-item list-group-item-danger "> Desactiver</a>
                                            <%} else { %>
                                            <a href="ActiveUser" class="list-group-item list-group-item-success "> Activer</a>
                                            <%}%>
                                        </div>
                                    </div>


                                </div>
                            </div>   
                            </br></br>
                            <div class="col-sm-8">
                                <form action="UpdateUserProfile?num=<%=user.getId()%>" method="post" class="form-horizontal">
                                    <!---------------------------------------------------------->
                                    <div class="form-group">
                                        <label for="inputEmail3" class="col-sm-3 control-label"> Nom</label>
                                        <div class="col-sm-9">
                                            <input value="<%=user.getNom()%>" name="name" type="text" class="form-control" id="inputEmail3" placeholder="Nom" >
                                        </div>
                                    </div>
                                    <!---------------------------------------------------------->
                                    <div class="form-group">
                                        <label for="inputEmail3" class="col-sm-3 control-label"> Prenom</label>
                                        <div class="col-sm-9">
                                            <input value="<%=user.getPrenom()%>" name="firstname" type="text" class="form-control" id="inputEmail3" placeholder="Prenom">
                                        </div>
                                    </div>
                                    <!---------------------------------------------------------->
                                    <div class="form-group">
                                        <label for="inputEmail3" class="col-sm-3 control-label"> Login</label>
                                        <div class="col-sm-9">
                                            <input value="<%=user.getLogin()%>" name="login" type="text" class="form-control" id="inputEmail3" placeholder="Login">
                                        </div>
                                    </div>
                                    <!---------------------------------------------------------->
                                    <div class="form-group">
                                        <label for="inputEmail3" class="col-sm-3 control-label"> Mot de passe</label>
                                        <div class="col-sm-9">
                                            <input value="<%=user.getPassword()%>" name="password" type="password" class="form-control" id="inputEmail3" placeholder="Mot de passe">
                                        </div>
                                    </div>
                                    <!---------------------------------------------------------->
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-primary"> Modifier </button>
                                            <button type="reset" class="btn btn-default"> Annuler </button>
                                        </div>
                                    </div>
                                    <!---------------------------------------------------------->
                                </form>
                            </div>
                            <div class="col-sm-4">
                                <form action="UploadPicture" method="post"  enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="inputEmail3" class="control-label"> Choisir une photos</label>
                                        <br>

                                        <div class="input-group">
                                            <label class="input-group-btn">
                                                <span class="btn btn-success">
                                                    Choisir&hellip; <input type="file" name="file" required style="display: none;" >
                                                </span>
                                            </label>
                                            <input type="text" placeholder="Fichier" name="s" class="form-control left-rounded">
                                            <div class="input-group-btn">
                                                <button type="submit" class="btn  btn-inverse right-rounded "> Ajouter</button>
                                            </div>
                                        </div>
                                    </div>

                                    <%
                                        String erreur = (String) session.getAttribute(com.Parameter.sessionError);
                                        if (erreur != null) {

                                    %>	
                                    <br>
                                    <div class="alert alert-danger" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                        <span class="sr-only"></span>
                                        <%=(String) session.getAttribute(com.Parameter.sessionError)%>
                                    </div>
                                    <%

                                        }
                                        session.setAttribute(com.Parameter.sessionError, null);
                                    %>


                                    <script>
                                        $(function () {

                                            // We can attach the `fileselect` event to all file inputs on the page
                                            $(document).on('change', ':file', function () {
                                                var input = $(this),
                                                        numFiles = input.get(0).files ? input.get(0).files.length : 1,
                                                        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                                                input.trigger('fileselect', [numFiles, label]);
                                            });

                                            // We can watch for our custom `fileselect` event like this
                                            $(document).ready(function () {
                                                $(':file').on('fileselect', function (event, numFiles, label) {

                                                    var input = $(this).parents('.input-group').find(':text'),
                                                            log = numFiles > 1 ? numFiles + ' files selected' : label;

                                                    if (input.length) {
                                                        input.val(log);
                                                    } else {
                                                        if (log)
                                                            alert(log);
                                                    }

                                                });
                                            });

                                        });
                                    </script>
                                </form>

                            </div>
                        </div><%--panel body --%>
                    </div><%--panel --%>  
                </div>

                <!--================================================================================== --> 
                <!--   COLONNE 2 ===================================================================== -->
                <!--================================================================================== -->
                <div class="col-xs-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title  glyphicon glyphicon-file"> Fichier</h3>
                        </div>
                        <div class="panel-body ">
                            <form method="post" action="Affectation">
                                <table class="table">
                                    <tr>
                                        <td><h4>Fichier</h4></td>
                                        <td><h4>Nom de fichier</h4></td>
                                        <td><h4>Affectation</h4></td>
                                    </tr>
                                    <%
                                        for (int i = 0; i < sessionListFichier.size(); i++) {
                                            entity.Fichier f = sessionListFichier.get(i);
                                            if (com.Fichier.isFichierExcel(f)) {
                                                out.println("<div class='col-xs-6 col-sm-6 col-md-4 col-lg-3'>");

                                                out.println("<td class='col-xs-6 col-sm-4 col-md-3 col-lg-2 '>");
                                                out.println("<img src='view/img/excel.png' class='img-responsive' alt='' width='45' height='45'/>");
                                                out.println("</td>");
                                            } else {
                                                out.println("<td class='col-xs-6 col-sm-4 col-md-3 col-lg-2 '>");
                                                out.println("<img src='view/img/word.png' class='img-responsive' alt='' width='45' height='45'/>");
                                                out.println("</td>");
                                            }
                                            out.println("<td>" + f.getNom() + "</td>");
                                            out.println("</td>");
                                            out.println("<td>");
                                            if (user.getRole().equals("ADMIN")) {
                                                out.println("<input type='checkbox' name='" + f.getId() + "' class='checkbox' value'" + f.getId() + "' checked>");
                                            } else if (manager.ManagerAffectation.isAffectationExist(f, user)) {
                                                out.println("<input type='checkbox' name='" + f.getId() + "' class='checkbox' value'" + f.getId() + "' checked>");
                                            } else {
                                                out.println("<input type='checkbox' name='" + f.getId() + "' class='checkbox' value'" + f.getId() + "'>");
                                            }
                                            out.println("</td>");
                                            out.print("</tr>");
                                        }
                                    %> 
                                </table>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button type="submit" class="btn btn-primary"> Modifier </button>
                                        <button type="reset" class="btn btn-default"> Annuler </button>
                                    </div>
                                </div>
                            </form>
                        </div><!-- panel 2 body-->
                    </div><!-- panel 2 -->
                </div><!-- col 2 -->
            </div>
        </div>
        <!--   FOOTER ===================================================================== -->
        <%@include file="include_footer.jsp"%>  
    </body>
    <%}%>
</html>

