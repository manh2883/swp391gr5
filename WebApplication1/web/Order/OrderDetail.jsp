<%-- 
    Document   : OrderDetail
    Created on : Feb 27, 2025, 5:43:43 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Models.OrderDetail"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Detail</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h2>Order Detail</h2>
    
    <table border="1">
        <tr>
            <th>Product name</th>
            <th>Stock</th>
            <th>Price</th>
            <th>Total</th>
        </tr>
        <% List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetails");
           if (orderDetails != null) {
               for (OrderDetail detail : orderDetails) { %>
            <tr>
                <td><%= detail.getProductName() %></td>
                <td><%= detail.getQuantity() %></td>
                <td><%= detail.getPrice() %> VND</td>
                <td><%= detail.getTotal() %> VND</td>
            </tr>
        <% } } %>
    </table>
    
    <a href="OrderList.jsp">Back to Order List</a>
</body>
</html>
