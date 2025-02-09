<%-- 
    Document   : header1
    Created on : Feb 2, 2025, 2:16:17 AM
    Author     : Acer
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="${pageContext.request.contextPath}/CSS/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/responsive.css" rel="stylesheet">     
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/header.css" rel="stylesheet"> 
    </head>

    <body>
        <header id="header"><!--header-->
            <div class="header_top"><!--header_top-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="contactinfo">
                                <ul class="nav nav-pills">
                                    <li><a href=""><i class="fa fa-phone"></i> 0343102032</a></li>
                                    <li><a href=""><i class="fa fa-envelope"></i> tpfshopwear@gmail.com</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="social-icons pull-right">
                                <ul class="nav navbar-nav d-flex flex-row">
                                    <li><a href="https://www.facebook.com/profile.php?id=61572903264830&sk=about"><i class="fab fa-facebook"></i></a></li>
                                    <li><a href="https://discord.gg/TbhFJakE4a"><i class="fab fa-discord"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!--/header_top-->

            <div class="header-middle" ><!--header-middle-->
                <div class="container">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="logo pull-left ">
                                <a href="${pageContext.request.contextPath}/Home">
                                    <img src="Images/logo.png" alt="" />
                                </a>
                            </div>

                        </div>
                        <div class="col-sm-8" >
                            <div class="shop-menu pull-right">
                                <ul class="nav navbar-nav d-flex flex-row ">

                                    <c:choose>
                                        <c:when test="${not empty sessionScope.account}">
                                            <li class="nav-item">
                                                <a href="#">
                                                    <i class="fa fa-star"></i> Wishlist
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="checkout.html">
                                                    <i class="fa fa-crosshairs"></i> Checkout
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="${pageContext.request.contextPath}/ViewCart">
                                                    <i class="fa fa-shopping-cart"></i> Cart
                                                </a>
                                            </li>
                                        
                                            
                                            


                                            <li class="nav-item dropdown">
                                                <a href="#">
                                                    <i class="fa fa-user-circle"></i> Account <i class="fa fa-angle-down"></i>
                                                </a>
                                                <ul class="sub-menu" role="menu">
                                                    <li><a href="profile.html">My Profile</a></li>
                                                    <li><a href="orders.html">My Orders</a></li>
                                                    <li><a href="${pageContext.request.contextPath}/Logout">Log Out</a></li>
                                                </ul>
                                            </li>


                                        </c:when>
                                        <c:otherwise>
                                            <li class="nav-item">
                                                <a href="${pageContext.request.contextPath}/Login" >
                                                    <i class="fa fa-user-circle"></i> Login</a></li>
                                            <li class="nav-item">
                                                <a href="${pageContext.request.contextPath}/Register" >
                                                    <i class="fa fa-suitcase"></i> Sign Up</a></li>        

                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>
