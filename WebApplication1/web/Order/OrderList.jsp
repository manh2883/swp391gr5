<%-- 
    Document   : OrderList
    Created on : Feb 27, 2025, 5:43:34 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Models.Order"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h2>Order List</h2>
    
    <table border="1">
        <tr>
            <th>order code</th>
            <th>day date</th>
            <th>status</th>
            <th>detail</th>
        </tr>
        <% List<Order> orders = (List<Order>) request.getAttribute("orders");
           if (orders != null) {
               for (Order order : orders) { %>
            <tr>
                <td><%= order.getOrderId() %></td>
                <td><%= order.getCreatedAt() %></td>
                <td><%= order.getTotalPrice() %> VND</td>
                <td><%= order.getStatus() %></td>
                <td><a href="OrderDetailServlet?orderId=<%= order.getOrderId() %>">Consult</a></td>
            </tr>
        <% } } %>
    </table>
</body>
</html>
