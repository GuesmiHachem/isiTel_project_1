
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
                                <div id="mydiv" class="col-xs-12  ">
                                    <div id="contenu" class="col-xs-12 ">
                                    </div>
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
    function contenumessage() {
        var xhttp;

        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("contenu").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "ajax_contenu_message.jsp?id1=<%=Integer.parseInt(id)%>&id2=<%=sessionUser.getId()%>", true);
        xhttp.send();
        //bottom();

    }
    function bottom()
    {
        $("#mydiv").scrollTop($("#mydiv")[0].scrollHeight);
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
        
        contenumessage() ;
        
        tout();
        setInterval(tout, 1);
        scrollToBottom();
    }

</script>
