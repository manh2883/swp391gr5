<%-- 
    Document   : test
    Created on : Jan 19, 2025, 3:59:07 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
    </head>
    <body>
        <div>Test Screen </div>

        <h1>${message}</h1>
        <div>${message1}</div>

        <div>${message2}</div>
        <c:forEach var="entry" items="${message2}">
            <p>${entry}</p>
        </c:forEach>

</body>
</html>
