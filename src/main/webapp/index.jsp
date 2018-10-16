<%-- 
    Document   : index
    Created on : 12-10-2018, 10:08:24
    Author     : Claus
--%>
<%@page import="ck.presentation.FrontController"%>
<%
    String errorText = (String)request.getAttribute(FrontController.ERROR_TEXT);    
%>
<%@page import="ck.presentation.command.Command"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Legohouse</title>
        <jsp:include page="bootstrap/bootstrapcdn.jsp"></jsp:include>
    </head>
    <body>
        <div class="container">
        <jsp:include page="/WEB-INF/Topmenu/content.jsp" />            
            <div id="loginDiv">
                <h1>Login</h1>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="<%= Command.LOGIN %>">
                    <input type="text" placeholder="Email" name="email">
                    <input type="password" placeholder="Password" name="password">
                    <input type="submit" value="Login">
                </form>
            </div>
            <div id="createUserDiv">
                <h1>Create user</h1>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="<%= Command.CREATE_USER %>">
                    <input type="text" placeholder="Username" name="username">
                    <input type="text" placeholder="Email" name="email">
                    <input type="password" placeholder="Password" name="password">
                    <input type="password" placeholder="Password again" name="password2">
                    <label for="employee">Employee</label>
                    <input type="checkbox" placeholder="Employee" name="employee">
                    <input type="submit" value="Create user">
                </form>
            </div>
            <div class="error">
                <%= errorText == null ? "" : errorText %>
            </div>
        </div>
    </body>
</html>
