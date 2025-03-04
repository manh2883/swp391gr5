<%-- 
    Document   : OrderList
    Created on : Feb 27, 2025, 5:43:34 AM
    Author     : Dell
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-4">
            <h2>Order List</h2>

            <!-- Search and Filter Form -->
            <form method="GET" action="OrderList" class="row g-3 mb-3">
                <div class="col-md-3">
                    <input type="text" name="search" class="form-control" placeholder="Search Order ID or Name" value="${param.search}">
                </div>
                <div class="col-md-2">
                    <select name="status" class="form-control">
                        <option value="">All Status</option>
                        <option value="1" ${param.status == '1' ? 'selected' : ''}>Pending</option>
                        <option value="2" ${param.status == '2' ? 'selected' : ''}>Processing</option>
                        <option value="3" ${param.status == '3' ? 'selected' : ''}>Completed</option>
                        <option value="4" ${param.status == '4' ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <input type="date" name="fromDate" class="form-control" value="${param.fromDate}">
                </div>
                <div class="col-md-2">
                    <input type="date" name="toDate" class="form-control" value="${param.toDate}">
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">Filter</button>
                </div>
            </form>

            <!-- Orders Table -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total Amount</th>
                        <th>Status</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.statusId}</td>
                            <td>${order.createAt}</td>
                            <td>$${order.totalamount}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.statusId == 1}"><span class="badge bg-warning">Pending</span></c:when>
                                    <c:when test="${order.statusId == 2}"><span class="badge bg-primary">Processing</span></c:when>
                                    <c:when test="${order.statusId == 3}"><span class="badge bg-success">Completed</span></c:when>
                                    <c:when test="${order.statusId == 4}"><span class="badge bg-danger">Cancelled</span></c:when>
                                </c:choose>
                            </td>
                            <td>
                                <a href="OrderDetail.jsp?orderId=${order.orderId}" class="btn btn-sm btn-info">View</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Pagination -->
            <nav>
                <ul class="pagination">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="OrderList?page=${i}&search=${param.search}&status=${param.status}&fromDate=${param.fromDate}&toDate=${param.toDate}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
