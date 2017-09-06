

<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Action"%>
<%@page import="java.sql.Time"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Activation</title>
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
            function session() {
                var xhttp;

                xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("session").innerHTML = this.responseText;
                    }
                };
                xhttp.open("GET", "ajax_session.jsp", true);
                xhttp.send();
            }
            function tout() {
                message();
                session();
            }
            window.onload = function () {
                tout();
                setInterval(tout, 1000);


            }
        </script>

    </head>
    <%
        Utilisateur sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);

    %>
    <body class="gris_noir" translate="no" >
        <div class="container">
            <!--   HEADER ===================================================================== -->
            <%@include file="header_admin.jsp"%>
            <!--   Contenu ===================================================================== -->   
            <div class="row">
                <div class="col-xs-12">
                    <div class="panel panel-primary ">
                        <div class="panel-heading panel-primary">
                            <h3 class=" panel-title">Tableau De Bord</h3>
                        </div>
                        <div class="panel-body ">
                            <div id="session">

                            </div>


                        </div>
                    </div>
                    <!--====================================================================-->
                    <div class="panel panel-primary ">
                        <div class="panel-heading panel-primary">
                            <h3 class=" panel-title">Tableau De Bord</h3>
                        </div>
                        <div class="panel-body ">
                            <div class="col-xs-6">
                                <canvas id="myChart" width="100" height="100"></canvas>
                                <script>
                                    var ctx = document.getElementById("myChart").getContext('2d');
                                    var myChart = new Chart(ctx, {
                                        type: 'bar',
                                        data: {
                                            labels: <%=getLabels()%>,
                                            datasets: [{
                                                    label: 'Duree de connexion par heure',
                                                    data: <%=getData()%>,
                                                    backgroundColor: <%=getColor()%>,
                                                    borderColor: <%=getColor()%>,
                                                    borderWidth: 1
                                                }]
                                        },
                                        options: {
                                            scales: {
                                                yAxes: [{
                                                        ticks: {
                                                            beginAtZero: true
                                                        }
                                                    }]
                                            }
                                        }
                                    });
                                </script> 
                            </div>
                        </div>
                    </div>

                </div>
                <div class="col-xs-12">
                    <%
                    List<HttpSession> list=(List<HttpSession>)application.getAttribute("listSession");
                    for(int i=0;i<list.size();i++){
                    HttpSession sess=list.get(i);
                    Utilisateur u=(Utilisateur)sess.getAttribute(com.Parameter.sessionUser);
                    out.println(i+"  "+u.getNom()+"<br>");
                    }
                    %>
                </div>
                <!--====================================================================-->
            </div>
        </div>
        <!--   FOOTER ===================================================================== -->
        <%@include file="include_footer.jsp"%>    
    </body>

</html>

<%!public String getLabels() {
        List<Utilisateur> list = manager.ManagerUtilisateur.findUtilisateurEntities();

        String ch = "[";
        for (int i = 0; i < list.size(); i++) {
            Utilisateur u = list.get(i);
            ch = ch + "\"" + u.getPrenom() + "\",";
        }
        char[] tab = ch.toCharArray();
        int index = ch.lastIndexOf(",");
        tab[index] = ']';
        ch = String.valueOf(tab);
        return ch;

    }

%>

<%!public String getData() {
        List<Utilisateur> list = manager.ManagerUtilisateur.findUtilisateurEntities();
        float nb;
        String ch = "[";
        for (int i = 0; i < list.size(); i++) {
            Utilisateur u = list.get(i);
            nb = manager.ManagerAction.getDureeActivation(u);
            ch = ch + nb + ",";
        }
        char[] tab = ch.toCharArray();
        int index = ch.lastIndexOf(",");
        tab[index] = ']';
        ch = String.valueOf(tab);
        return ch;

    }

%>

<%!public String getColor() {
        List<Utilisateur> list = manager.ManagerUtilisateur.findUtilisateurEntities();
        List<String> listColor = new ArrayList<String>();
        listColor.add("#fc7474");
        listColor.add("#65b5f2");
        listColor.add("#eded44");
        listColor.add("#f4b23f");
        listColor.add("#966819");
        listColor.add("#70ff60");
        listColor.add("#a260ff");
        listColor.add("#f91313");
        listColor.add("#aaaa80");
        listColor.add("#383811");
        listColor.add("#ef70d4");
        listColor.add("#0056ea");
        listColor.add("#7d8491");
        listColor.add("#62f751");
        listColor.add("#ba8809");
        float nb;
        String ch = "[";
        for (int i = 0; i < list.size(); i++) {
            Utilisateur u = list.get(i);
            nb = manager.ManagerAction.getDureeActivation(u);
            ch = ch + "\"" + listColor.get(i) + "\",";
        }
        char[] tab = ch.toCharArray();
        int index = ch.lastIndexOf(",");
        tab[index] = ']';
        ch = String.valueOf(tab);
        return ch;

    }

%>