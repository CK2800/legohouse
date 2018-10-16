<%-- 
    Document   : order
    Created on : 12-10-2018, 16:44:33
    Author     : Claus
--%>
<%@page import="ck.presentation.Utils"%>
<%@page import="ck.presentation.FrontController"%>
<%@page import="ck.data.OrderDTO"%>
<%
    // get error text if any.
    String errorText = (String)request.getAttribute(FrontController.ERROR_TEXT);
    // get order from request.
    OrderDTO orderDTO = (OrderDTO)request.getAttribute("orderDTO");    
    String order = orderDTO != null ? Utils.OrderToHtmlTable(orderDTO) : "No order requested.";
        
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="../bootstrap/bootstrapcdn.jsp"></jsp:include>
    </head>
    <body>
        <div class="container">
            <jsp:include page="Topmenu/content.jsp" />            
            <%= order %>
            <div class="error">
                <%= errorText == null ? "" : "Error: " + errorText %>
            </div>
        </div>
    </body>
</html>
