<%-- 
    Document   : MyOrder
    Created on : Mar 4, 2025, 9:46:00 AM
    Author     : Dell
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container mt-4">
    <h2>My Orders</h2>
    <p>${message}</p>
    <p>${orderInformation}</p>
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
            <c:choose>
                <c:when test="${not empty orderDetailList}">
                    <c:forEach var="order" items="${orderDetailList}">
                        <tr>
                            <td><c:out value="${order[0].orderdetailId}" /></td>
                            <td><c:out value="${order[1].name}" /></td>
                            <td><c:out value="${order[2]}" /></td>
                            <td><c:out value="${order[0].quantity}" /></td>
                            <td><c:out value="${order[0].price}" /></td>
                            
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6" style="text-align: center; font-weight: bold;">No items found</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>

    </table>
</div>



