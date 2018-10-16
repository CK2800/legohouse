<%-- 
    Document   : employee
    Created on : 12-10-2018, 11:46:37
    Author     : Claus
--%>
<%@page import="ck.data.UserDTO"%>
<%@page import="ck.presentation.FrontController"%>
<%@page import="ck.presentation.Utils"%>
<%@page import="ck.presentation.viewmodels.OrderUserComposite"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ck.data.OrderDTO"%>
<%
    // get error text if any.
    String errorText = (String)request.getAttribute(FrontController.ERROR_TEXT);
    // get list of orders.
    ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>)request.getAttribute("orders");    
    String ordersTable = orders != null ? Utils.ordersToHtmlTable(orders, true) : "No orders in system."; 
    String username = ((UserDTO)request.getSession().getAttribute("userDTO")).getUsername();    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee page</title>
        <jsp:include page="../bootstrap/bootstrapcdn.jsp"></jsp:include>
    </head>
    <body>
        <div class="container">
            <jsp:include page="Topmenu/content.jsp" />            
            <%= ordersTable %>        
            <div class="error">
                <%= errorText == null ? "" : "Error: " + errorText %>
            </div>
        </div>
    </body>
</html>
