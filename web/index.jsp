
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Utilisateur"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Index</title>
        <!--   LINK_SCRIPT ===================================================================== -->
        <%@include file="linkandscript.jsp"%>
    </head>
    
    <body class="noir_orange" >
<%
 if (application.getAttribute("listSession")== null) {
           application.setAttribute("listSession", new ArrayList<HttpSession>());
        }
%>

        <%
           
            Utilisateur sessionUser = (Utilisateur) session.getAttribute(com.Parameter.sessionUser);
            if (false) {%>
        <!--   ACTIVATION ===================================================================== -->
        <%@include file="include_activation.jsp"%>
        <%} else if (sessionUser != null) {
            request.getRequestDispatcher("MyFiles").forward(request, response);
        } else {
        %>
        <!--   ACCUEIL ===================================================================== -->
        <%@include file="include_accueil.jsp"%>
        <br>  <br>  <br>

        <!--   ACCUEIL ===================================================================== -->
        <%@include file="include_footer.jsp"%>
        <%}%>



    </body>
</html>