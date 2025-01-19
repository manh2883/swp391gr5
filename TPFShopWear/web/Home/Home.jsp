<%-- 
    Document   : Home
    Created on : Jan 19, 2025, 2:28:48 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <h1>${message}</h1>
        <a href="${pageContext.request.contextPath}/Login">Login</a>
    </body>
</html>


