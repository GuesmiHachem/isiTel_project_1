
<%@page import="entity.Parameter"%>
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
        List<Parameter> listParameter = manager.ManagerParameter.findParameterEntities();
        if (sessionUser == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if (sessionUser.getRole().equals("USER")) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {


    %>
    <body class="gris_noir" translate="no" >
        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-xs-12 col-md-12">

                    <div class="panel panel-primary">
                        <div class="panel-heading " >
                            <h3 class="panel-title glyphicon glyphicon-cog"> Parameter </h3>
                        </div>
                        <div class="panel-body blanche_noir">
                            <div class="row">
                                <%for (int i = 0; i < listParameter.size(); i++) {%>
                                <%Parameter p = listParameter.get(i);%>
                                <div class="col-xs-12 col-sm-6 col-md-4">
                                    <div class="panel panel-danger active" >
                                        <div class=" panel-heading " >
                                            <h3 class="panel-title"> <%=p.getNom()%></h3>
                                        </div>
                                        <div class="panel-body panel-info">
                                            <form action="UpdateParameter" method="post" class="form-horizontal pull-left col-xs-12">
                                                <!---------------------------------------------------------->
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <input value="<%=p.getValeur()%>" name="valeur" type="text" class="form-control" id="inputEmail3" placeholder="Nom" >
                                                    </div>
                                                </div>
                                                <!---------------------------------------------------------->
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <input value="<%=p.getId()%>" name="id" type="hidden" class="form-control" id="inputEmail3"  >
                                                    </div>
                                                </div>
                                                <!---------------------------------------------------------->
                                                <div class="form-group ">
                                                    <div class="btn-group col-xs-12">
                                                        <button type="submit" class="col-xs-6 btn btn-primary"> modifier</button>
                                                        <button type="reset" class="col-xs-6 btn  btn-default "> annuler </button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
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