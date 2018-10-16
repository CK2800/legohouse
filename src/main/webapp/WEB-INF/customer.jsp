<%-- 
    Document   : customer
    Created on : 12-10-2018, 11:46:06
    Author     : Claus
--%>
<%@page import="ck.data.LineItemDTO"%>
<%@page import="ck.presentation.command.Command"%>
<%@page import="ck.data.UserDTO"%>
<%@page import="ck.presentation.Utils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ck.data.OrderDTO"%>
<%
    // Get the list of orders.
    ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>)request.getAttribute("orders");
    // Convert the list to html representation.
    String ordersTable = orders != null ? Utils.ordersToHtmlTable(orders, false) : "No orders in system."; 
    // Get the list of brick patterns.
    ArrayList<String> brickPatterns = (ArrayList<String>)request.getAttribute("brickPatterns");
    // Convert to <select> html element.
    String brickPatternsSelect = brickPatterns != null ? Utils.brickPatternsToSelect(brickPatterns):"No brick patterns available.";
    // Get username.    
    String username = ((UserDTO)request.getSession().getAttribute("userDTO")).getUsername();
    // Get layers of calculated house from session if any.
    ArrayList<LineItemDTO>[] layers = (ArrayList[])request.getSession().getAttribute("layers");
    String layersTable = layers != null ? Utils.brickLayersToHtml(layers) : "No layers calculated.";
        
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
            <h2>Create new order</h2>
            <form action="FrontController" method="post">
                <input type="hidden" name="command" value="<%= Command.CALCULATE_ORDER %>">
                <input type="text" name="length" placeholder="Length of building">
                <input type="text" name="width" placeholder="Width of building">
                <input type="text" name="height" placeholder="Height of building">
                <%= brickPatternsSelect %>
                <input type="submit" value="Calculate order...">
            </form>
            <%= layersTable %>
            <%= ordersTable %>
        </div>
    </body>
</html>
