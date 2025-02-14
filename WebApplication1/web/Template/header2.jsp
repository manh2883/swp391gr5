
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
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

        <div class="header-bottom"><!--header-bottom-->
            <div class="container">
                <div class="row" >
                    <div class="col-sm-8">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                        </div>
                        <div class="mainmenu pull-left">
                            <ul class="nav navbar-nav collapse navbar-collapse">
                                <li><a href="index.html">Home</a></li>
                                <li class="dropdown"><a href="${pageContext.request.contextPath}/ViewBlogs">Shop<i class="fa fa-angle-down"></i></a>
                                    <ul role="menu" class="sub-menu">
                                        <li><a href="${pageContext.request.contextPath}/ViewProduct">Products</a></li>
                                        <!--                                        <li><a href="product-details.html">Product Details</a></li> 
                                                                                <li><a href="checkout.html">Checkout</a></li> 
                                                                                <li><a href="cart.html">Cart</a></li> -->
                                        <!--                                        <li><a href="login.html" class="active">Login</a></li> -->
                                    </ul>
                                </li> 
                                <li class="dropdown"><a href="${pageContext.request.contextPath}/ViewBlogs">Blog<i class="fa fa-angle-down"></i></a>
                                    <ul role="menu" class="sub-menu">
                                        <li><a href="${pageContext.request.contextPath}/ViewBlogs">Blog List</a></li>
                                        <!--<li><a href="blog-single.html">Blog Single</a></li>-->
                                    </ul>
                                </li> 
                                <!--                                <li><a href="404.html">404</a></li>
                                                                <li><a href="contact-us.html">Contact</a></li>-->

                                <c:set var="account" value="${sessionScope.account}" />
                                <c:choose>
                                    <c:when test="${not empty account and account.roleId != 2 and  not empty account.roleId}">
                                        <li class="dropdown">
                                            <a href="${pageContext.request.contextPath}/DashBoard">Dashboard</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                      
                                    </c:otherwise>
                                </c:choose>


                            </ul>
                        </div>
                    </div>
                    <form class="col-sm-4" method="post" action="Search">
                        <div class="search_box d-flex flex-row pull-right align-items-center  " >
                            <input type="text" placeholder="Search" name="searchKey" />
                            <button type="button submit" class="btn btn-default get-manh" >Search</button>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
