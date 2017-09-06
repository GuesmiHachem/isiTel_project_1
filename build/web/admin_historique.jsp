
<%@page import="java.util.Date"%>
<%@page import="entity.Message"%>
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Index</title>
        <!--   LINK_SCRIPT ===================================================================== -->
        <%@include file="linkandscript.jsp"%>
        <script type="text/javascript">
            $(function () {
                if (!Modernizr.inputtypes.date) {
                    console.log("The 'date' input type is not supported, so using JQueryUI datepicker instead.");
                    $("#theDate").datepicker();
                }
            });
        </script>

    </head>

    <%
        Utilisateur sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
        List<entity.Utilisateur> listUtilisateur;
        if (sessionUser == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (sessionUser.getRole().equals("USER")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            listUtilisateur = manager.ManagerUtilisateur.findUtilisateurEntities();


    %>

    <body class="gris_noir">
        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary ">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title glyphicon glyphicon-time"> Evenement</h3>
                        </div>
                        <div class="panel-body ">

                            <div   class="col-xs-12">
                                <!--==============================================================-->
                                <div  class="col-xs-12  ">
                                    <a href="supprimerHistorique" class="btn btn-danger" >
                                        Supprimer Historique
                                    </a>
                                    <br> <br>
                                </div>
                                <!--==============================================================-->
                                <div class=" col-xs-12 btn-group">
                                    <div class="form-group">
                                        <label for="Utilisateur" >Utilisateur</label>
                                        <select class="form-control" id="list_utilisateur" onchange="contenuHistorique()">
                                        </select>
                                    </div>
                                    <br>
                                </div>
                               
                                <!--==============================================================-->
                                <div id="historique" class="col-xs-12  ">
                                </div>
                            </div>

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
    function contenuHistorique() {
        var xhttp;

        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("historique").innerHTML = this.responseText;
            }
        };

        id_user = document.getElementById("list_utilisateur").selectedIndex;
        xhttp.open("GET", "ajax_contenu_historique.jsp?id_user=" + id_user , true);
        //xhttp.open("GET", "ajax_contenu_historique.jsp?id_user=1", true);
        xhttp.send();
    }
    function contenuHistorique1() {
        var xhttp;

        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("historique").innerHTML = this.responseText;
            }
        };

        
        xhttp.open("GET", "ajax_contenu_historique.jsp", true);
        //xhttp.open("GET", "ajax_contenu_historique.jsp?id_user=1", true);
        xhttp.send();


    }
    function list_utilisateur() {
        var xhttp;

        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("list_utilisateur").innerHTML = this.responseText;
            }
        };



        xhttp.open("GET", "ajax_list_utilisateur.jsp", true);
        xhttp.send();


    }
    function tout()
    {

        message();

    }
    window.onload = function () {
        list_utilisateur();
        contenuHistorique1();
        tout();
        setInterval(tout, 1);

    }

</script>
