

<%@page import="entity.Fichier"%>
<%@page import="java.util.List"%>
<%@page import="entity.Utilisateur"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URI"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>

<!DOCTYPE html>
<html>
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
    <body class=""  >
        <%
           
            Utilisateur sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
            List<entity.Fichier> listFichier;
            if (sessionUser == null) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else if (sessionUser.getRole().equals("USER")) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                listFichier = manager.ManagerFichier.findFichierEntities();
        %>


        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-xs-12 col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading panel-primary">
                            <h3 class="panel-title  glyphicon glyphicon-briefcase"> Fichier</h3>
                        </div>
                        <div class="panel-body ">
                            <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>-->
                            <script>
                                function openExcel(path) {
                                    var Excel = new ActiveXObject("Excel.Application");
                                    Excel.Visible = true;
                                    Excel.Workbooks.Open(path);
                                    Excel.WindowState = 2;
                                    Excel.WindowState = 1;


                                }
                                function openWord(path) {
                                    var word = new ActiveXObject("Word.Application");
                                    word.Visible = true;
                                    word.Documents.Open(path);
                                    word.WindowState = 2;
                                    word.WindowState = 1;
                                }
                            </script>
                            <table class="table">
                                <tr class="">
                                    <td class=" col-xs-1 "><h4>#</h4></td>
                                    <td class=" col-xs-1 "><h4>Fichier</h4></td>
                                    <td class="col-xs-3 "><h4>Nom de Fichier</h4></td>
                                    <td class="col-xs-4 "><h4><b><u></u></b></h4></td>
                                    <td class="col-xs-1 "><h4><b><u></u></b></h4></td>
                                    <td class="col-xs-1 "><h4><b><u></u></b></h4></td>
                                    <td class="col-xs-1 "><h4><b><u></u></b></h4></td>
                                </tr>

                                <%
                                    for (int i = 0; i < listFichier.size(); i++) {

                                        Fichier f = listFichier.get(i);
                                        out.println("<tr >");
                                        //---------------------------------
                                        out.println("<td class=''>");
                                        out.println(f.getId());
                                        out.println("</td>");
                                        //---------------------------------
                                        if (com.Fichier.isFichierExcel(f)) {

                                %>
                                <td>
                                    <a href="#" onClick="openExcel('<%=f.getChemin()%>')">
                                        <img src='view/img/excel.png' class='img-responsive' alt='' width='60' height='60'/>
                                    </a>
                                </td>
                                <td><%=f.getNom()%></td>

                                <%
                                } else {
                                %>
                                <td>
                                    <a href="#" onClick="openWord('<%=f.getChemin()%>')">
                                        <img src='view/img/word.png' class='img-responsive' alt='' width='60' height='60'/>
                                    </a>
                                </td> 
                                <td><%=f.getNom()%></td>
                                <%
                                    }
                                %>
                                <form method="post" action="RenameFile?num=<%=f.getId()%>">
                                    <td>
                                        <input type="text" class=" form-control" placeholder="" name="nomFichier" required>
                                    </td>
                                    <td>
                                        <button type="submit" class="col-xs-12 glyphicon glyphicon-pencil btn btn-info"> </button>
                                    </td>
                                </form>
                                <td>
                                    <a href='DeleteFile?num=<%=f.getId()%>' class='col-xs-12  glyphicon glyphicon-trash btn btn-danger' > </a>
                                </td>
                                <td>
                                    <a href='<%=f.getChemin()%>' class='col-xs-12 glyphicon  glyphicon-download-alt btn btn-success '> </a>
                                </td>
                                </tr >

                                <%}%>

                            </table>
                            <!-- Modal -->




                        </div><!-- panel 2 body-->
                    </div><!-- panel 2 -->
                </div><!-- col 2 -->

            </div><!-- row 2 -->

        </div>
        <!--   FOOTER ===================================================================== -->
        <%@include file="include_footer.jsp"%>                                    
    </body>
    <%}%>
</html>
