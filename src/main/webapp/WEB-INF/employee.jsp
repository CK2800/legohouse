<%-- 
    Document   : employee
    Created on : 12-10-2018, 11:46:37
    Author     : Claus
--%>
<%@page import="ck.presentation.FrontController"%>
<%@page import="ck.presentation.Utils"%>
<%@page import="ck.presentation.viewmodels.OrderUserComposite"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ck.data.OrderDTO"%>
<%
    // get error text if any.
    String errorText = (String)request.getAttribute(FrontController.ERROR_TEXT);
    // get list of orders.
    ArrayList<OrderUserComposite> unshippedOrders = (ArrayList<OrderUserComposite>)request.getAttribute("unshippedOrders");
    String unshippedOrdersTable = unshippedOrders != null ? Utils.OrderUserCompositesToHtmlTable(unshippedOrders, false) : "No unshipped orders";    
    // get list of shipped orders.
    ArrayList<OrderUserComposite> shippedOrders = (ArrayList<OrderUserComposite>)request.getAttribute("shippedOrders");
    String shippedOrdersTable = shippedOrders != null ? Utils.OrderUserCompositesToHtmlTable(shippedOrders, true) : "No shipped orders";
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee page</title>
    </head>
    <body>
        <h1>Hello employee!</h1>
        <%= Utils.logOutForm() %>
        <%= unshippedOrdersTable %>
        <%= shippedOrdersTable %>
        <div class="error">
            <%= errorText == null ? "" : "Error: " + errorText %>
        </div>
    </body>
</html>
