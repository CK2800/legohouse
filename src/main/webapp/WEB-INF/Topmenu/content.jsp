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
    String username = user == null ? "" : user.getUsername();
    
    
%>
<div>
    <img src="legologo.jpeg" />
    
</div>
<div>
    <%= navbar %>
</div>
<div>
    <h1>Hello <%= username %>!</h1>
</div>