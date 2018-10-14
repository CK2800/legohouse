<%-- 
    Document   : customer
    Created on : 12-10-2018, 11:46:06
    Author     : Claus
--%>
<%@page import="ck.data.UserDTO"%>
<%@page import="ck.presentation.Utils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ck.data.OrderDTO"%>
<%
    // Get the list of orders.
    ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>)request.getAttribute("orders");
    // Convert the list to html representation.
    String ordersTable = orders != null ? Utils.ordersToHtmlTable(orders, false) : "No orders in system."; 
    // Get username.    
    String username = ((UserDTO)request.getSession().getAttribute("userDTO")).getUsername();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer page</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="Topmenu/content.jsp" />
            <h1>Hello <%= username%>!</h1>
            <%= Utils.logOutForm() %>
            <%= ordersTable %>
        </div>
    </body>
</html>
