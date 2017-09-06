
<%@page import="java.sql.Time"%>
<%@page import="java.util.Date"%>
<%@page import="entity.Action"%>
<%@page import="entity.Fichier"%>
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URI"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>



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
        List<entity.Fichier> sessionListFichier;
        if ((manager.ManagerParameter.findParameter("activer").getValeur().equals("non"))|| (sessionUser.getActive()==false)) {
            session.setAttribute(com.Parameter.sessionUser, null);
            session.setAttribute(com.Parameter.sessionError, null);

    %>
    <!--   ERREUR ===================================================================== -->
    <%@include file="include_erreur.jsp"%>
    <%} else if (sessionUser == null) {
        response.sendRedirect("");
    } else if (sessionUser.getRole().equals("ADMIN")) {
        response.sendRedirect("");
    } else {
        sessionListFichier = manager.ManagerAffectation.findAllFichierByUser(sessionUser.getId());
    %>
    <body class="gris_noir" >

        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_user.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-xs-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title  glyphicon glyphicon-file"> Files</h3>
                        </div>
                        <div class="panel-body">
                            <script>
                                function openExcel(path) {
                                    var Excel = new ActiveXObject("Excel.Application");
                                    Excel.Visible = true;
                                    Excel.Workbooks.Open(path);
                                    Excel.WindowState = 2;
                                    Excel.WindowState = 1;
                                <%
                                  //  Action a = new Action(0, new Date(), new Time(new Date().getTime()), 3, sessionUser, manager.ManagerFichier.findFichierbyChemin("ee"));
                                  //  manager.ManagerAction.create(a);
                                %>

                                }
                                function openWord(path) {
                                    var word = new ActiveXObject("Word.Application");
                                    word.Visible = true;
                                    word.Documents.Open(path);
                                    word.WindowState = 2;
                                    word.WindowState = 1;
                                <%
                                    //    Action a2 = new Action(0, new Date(), new Time(new Date().getTime()), 3, sessionUser, manager.ManagerFichier.findFichierbyChemin("ee"));
                                    //    manager.ManagerAction.create(a2);
                                %>

                                }
                            </script>
                            <%                                for (int i = 0; i < sessionListFichier.size(); i++) {
                                    Fichier f = sessionListFichier.get(i);
                                    out.println("<div class='col-xs-3' >");
                                    //---------------------------------
                                    if (com.Fichier.isFichierExcel(f)) {

                            %>
                            <a href="OpenFile?idFile=<%=f.getId()%>&idUser=<%=sessionUser.getId()%>" onClick="openExcel('<%=f.getChemin()%>')">
                                <img src='view/img/excel.png' class='img-responsive' alt='' width='100' height='100'/>
                            </a>
                            <%=f.getNom()%>

                            <%
                            } else {
                            %>
                            <a href="OpenFile?idFile=<%=f.getId()%>&idUser=<%=sessionUser.getId()%>" onClick="openWord('<%=f.getChemin()%>')">
                                <img src='view/img/word.png' class='img-responsive' alt='' width='100' height='100'/>
                            </a>
                            <%=f.getNom()%>
                            <%
                                    }
                                    out.println("</div>");
                                }
                                %>

                            </table>
                            <!-- Modal -->




                        </div><!-- panel 2 body-->
                    </div><!-- panel 2 -->
                </div><!-- panel 2 -->
            </div><!-- col 2 -->
        </div>
        <!--   FOOTER ===================================================================== -->
        <%@include file="include_footer.jsp"%>    
    </body>
    <%}%>
</html>