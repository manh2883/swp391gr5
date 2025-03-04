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
                <th>Order ID</th>
                <th>Date</th>
                <th>Total Amount</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><c:out value="${order.orderId}" /></td>
                    <td><c:out value="${order.createAt}" /></td>
                    <td>$<c:out value="${order.totalamount}" /></td>
                    <td><c:out value="${order.statusId}" /></td>
                    <td>
                        <a href="OrderDetail.jsp?orderId=${order.orderId}" class="btn btn-primary">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>



