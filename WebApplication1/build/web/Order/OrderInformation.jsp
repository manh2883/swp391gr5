<%-- 
    Document   : OrderInformation
    Created on : Feb 27, 2025, 5:52:35 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Models.Order"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Order Information</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <h2>Order Information</h2>

        <% Order order = (Order) request.getAttribute("order");
       if (order != null) { %>
        <p><strong>Order Id:</strong> <%= order.getOrderId() %></p>
        <p><strong>Created at:</strong> <%= order.getCreatedAt() %></p>
        <p><strong>Status:</strong> <%= order.getStatusId() %></p>
        <p><strong>Payment method:</strong> <%= order.getPaymentMethod() %></p>
        <p><strong>Address:</strong> <%= order.getAddress()
    <% } else { %>
        <p>Not found order information.</p>
        <% } %>

        else { %>
        <p>Not found order information.</p>
        <% } %>
    </body>
</html>