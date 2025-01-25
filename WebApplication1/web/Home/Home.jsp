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
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <style>
            .carousel {
                width: 25%;
                margin: 0 auto;
            }
            .carousel-inner {
                width: 100%;
            }
            .carousel-item img {
                width: 100%;
            }


        </style>
    </head>
    <body>
        <div>
            <h1>${message}</h1>
            <a href="${pageContext.request.contextPath}/Login">Login</a><br>
            <a href="${pageContext.request.contextPath}/ProductDetail">View Product</a><br>
            <a href="${pageContext.request.contextPath}/ViewPost">View Post</a><br>
        </div>
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3" aria-label="Slide 4"></button>
            </div>
            <br>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <a href="${pageContext.request.contextPath}/ViewPost">
                        <img src="${pageContext.request.contextPath}/Images/RUN.jpg" 
                             class="d-block w-100" alt="...">
                    </a>
                </div>
                <div class="carousel-item ">
                    <a href="${pageContext.request.contextPath}/ViewPost">
                        <img src="${pageContext.request.contextPath}/Images/RUN.jpg" 
                             class="d-block w-100" alt="...">
                    </a>
                </div>
                <div class="carousel-item ">
                    <a href="${pageContext.request.contextPath}/ViewPost">
                        <img src="${pageContext.request.contextPath}/Images/RUN.jpg" 
                             class="d-block w-100" alt="...">
                    </a>
                </div>
                <div class="carousel-item ">
                    <a href="${pageContext.request.contextPath}/ViewPost">
                        <img src="${pageContext.request.contextPath}/Images/RUN.jpg" 
                             class="d-block w-100" alt="...">
                    </a>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </body>

</html>


