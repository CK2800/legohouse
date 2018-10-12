<%-- 
    Document   : employee
    Created on : 12-10-2018, 11:46:37
    Author     : Claus
--%>
<%@page import="ck.presentation.Utils"%>
<%@page import="ck.presentation.viewmodels.OrderUserComposite"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ck.data.OrderDTO"%>
<%
    // get list of unshipped orders.
    ArrayList<OrderUserComposite> unshippedOrders = (ArrayList<OrderUserComposite>)request.getAttribute("unshippedOrders");
    String unshippedOrdersTable = Utils.OrderUserCompositesToHtmlTable(unshippedOrders);
    
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
        <%= unshippedOrdersTable %>
    </body>
</html>
