<%-- 
    Document   : MyOrder
    Created on : Mar 4, 2025, 9:46:00 AM
    Author     : Dell
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="DAO.OrderDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="Models.Order" %>

<div class="container mt-4">
    <h2>My Orders</h2>
    <p>${message}</p>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>STT</th>
                <th>Product</th>
                <th>Size, Color</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${orderDetailList}">
                <tr>
                    
                    <td><c:out value="${order[0].orderdetailId}" /></td>
                    <td><c:out value="${order[1].name}" /></td>
                    <td>$<c:out value="${order[2]}" /></td>
                    <td><c:out value="${order[0].quantity}" /></td>
                     <td><c:out value="${order[0].price}" /></td>
                    <td>
                    <a href="OrderDetail.jsp?orderId=${order.orderId}" class="btn btn-primary">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>



