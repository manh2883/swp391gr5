<%-- 
    Document   : PostDetail
    Created on : Jan 26, 2025, 4:33:40 AM
    Author     : Acer
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Product Detail</title>
</head>
<body>
    <h1 th:text="${product.name}"></h1>
    <img th:src="${product.imageUrl}" alt="Product Image" width="300">
    <p th:text="${product.description}"></p>
    <p>Price: $<span th:text="${product.price}"></span></p>
    <a href="/products">Back to Products</a>
</body>
</html>