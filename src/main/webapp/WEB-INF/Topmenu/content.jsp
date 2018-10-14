<%-- 
    Document   : content
    Created on : 14-10-2018, 12:26:22
    Author     : Claus
--%>
<%@page import="ck.presentation.Utils"%>
<%@page import="ck.data.UserDTO"%>
<%
    UserDTO user = (UserDTO)request.getSession().getAttribute("userDTO");
    String navbar = user == null ? "" : Utils.createNavBar(user);
    
%>
<div>
    LOGO
</div>
<div>
    <%= navbar %>
</div>
