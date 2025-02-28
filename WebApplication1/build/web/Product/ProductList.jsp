<%-- 
    Document   : ProductList
    Created on : Feb 8, 2025, 6:02:26 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>Product List</title>
    </head>
    <body>
        <h1>Product List</h1>

    <c:forEach var="product" items="${productList}">
        <div>
            <img src="${product.imagePath}" alt="Product Image" width="100">
            <p>${product.name}</p>
            <p>${product.price} $</p>
            <a href="ProductDetail.jsp?productId=${product.id}">View Details</a>
        </div>
    </c:forEach>


<!--    <div th:each="product : ${products}">
    <h2 th:text="${product.name}"></h2>
    <p th:text="${product.description}"></p>
    <p>Price: $<span th:text="${product.price}"></span></p>
    <img th:src="${product.imageUrl}" alt="Product Image" width="200">
    <a th:href="@{/product/{id}(id=${product.id})}">View Details</a>
</div>-->
</body>
</html>