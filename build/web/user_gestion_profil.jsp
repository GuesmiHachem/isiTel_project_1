
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URI"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>User</title>
        <!--   LINK_SCRIPT ===================================================================== -->
        <%@include file="linkandscript.jsp"%>
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
        
        window.onload = function () {
            message();
            setInterval(message, 1000);
           
        }
    </script>
    </head>
    <%
        
        entity.Utilisateur sessionUser = (entity.Utilisateur) session.getAttribute(com.Parameter.sessionUser);
       if ((manager.ManagerParameter.findParameter("activer").getValeur().equals("non"))|| (sessionUser.getActive()==false)) {
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
        

    %>
    <body class="gris_noir"  >

        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_user.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-xs-12">

                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title"> My profile </h3>
                        </div>
                        <div class="panel-body">
                            <form action="" method="post" class="form-horizontal">
                                <!---------------------------------------------------------->
                                <div class="form-group">
                                    <label for="inputEmail3" class="col-sm-2 control-label">Name</label>
                                    <div class="col-sm-10">
                                        <input value="<%=sessionUser.getNom()%>" name="name" type="text" class="form-control" id="inputEmail3" placeholder="Name" disabled="">
                                    </div>
                                </div>
                                <!---------------------------------------------------------->
                                <div class="form-group">
                                    <label for="inputEmail3" class="col-sm-2 control-label">First Name</label>
                                    <div class="col-sm-10">
                                        <input value="<%=sessionUser.getPrenom()%>" name="firstname" type="text" class="form-control" id="inputEmail3" placeholder="First Name" disabled="">
                                    </div>
                                </div>

                                <!---------------------------------------------------------->
                                <div class="form-group">
                                    <label for="inputEmail3" class="col-sm-2 control-label">Login</label>
                                    <div class="col-sm-10">
                                        <input value="<%=sessionUser.getLogin()%>" name="login" type="text" class="form-control" id="inputEmail3" placeholder="Login" disabled="">
                                    </div>
                                </div>
                                <!---------------------------------------------------------->
                                <div class="form-group">
                                    <label for="inputEmail3" class="col-sm-2 control-label">Password</label>
                                    <div class="col-sm-10">
                                        <input value="<%=sessionUser.getPassword()%>" name="password" type="password" class="form-control" id="inputEmail3" placeholder="Email" disabled="">
                                    </div>
                                </div>

                                <!---------------------------------------------------------->
                            </form>
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