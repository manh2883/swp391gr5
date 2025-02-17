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
        <link rel="icon" href="${pageContext.request.contextPath}/Images/tpfv1_reverse.ico" type="image/x-icon">

        <link href="${pageContext.request.contextPath}/CSS/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


        <style>

            .productinfo .btn {
                display: inline-block; /* Đảm bảo các nút được xếp thành dòng ngang */

            }
            .product-img {
                width: 242px;
                height: 225px;
                object-fit: contain; /* Giữ nguyên tỷ lệ, có thể có khoảng trắng */
                background-color: #f8f8f8; /* Màu nền cho khoảng trống */
            }


        </style>
    </head>
    <body>
        <header>
            <c:import url="/Template/header1.jsp" />
            <c:import url="/Template/header2.jsp" />
        </header>


        <section id="slider" style=""><!--slider-->
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <!--current slider-->
                        <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                            <c:choose>
                                <c:when test="${not empty sliderDetailList}">
                                    <ol class="carousel-indicators">
                                        <c:forEach var="entry" items="${sliderDetailList}" varStatus="status">
                                            <li data-target="#slider-carousel" data-slide-to="${status.index}" class="${status.first ? 'active' : ''}"></li>
                                            </c:forEach>
                                    </ol>
                                    <div class="carousel-inner">
                                        <c:forEach var="entry" items="${sliderDetailList}" varStatus="status">
                                            <div class="carousel-item ${status.first ? 'active' : ''}">
                                                <div class="col-sm-6">
                                                    <h1><span>TPF</span>Shopwear</h1>
                                                    <h2>${entry.detailTitle}</h2>
                                                    <p>${entry.detailContent}</p>
                                                    <a href="${pageContext.request.contextPath}/${entry.backLink}">
                                                        <button type="button" class="btn btn-default get">Get it now</button>
                                                    </a>
                                                </div>
                                                <div class="col-sm-6">
                                                    <img src="${pageContext.request.contextPath}/${entry.sliderImgLink}" class="girl img-responsive" alt="" />
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p>No Item found</p>
                                </c:otherwise>
                            </c:choose>
                            <a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                                <i class="fa fa-angle-left"></i>
                            </a>
                            <a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </div>


                    </div>
                </div>
            </div>
        </section><!--/slider-->

        <section style="">
            <div class="container" >
                <div class="row" >
                    <div class="col-sm-3">
                        <c:import url="/Template/left_side_bar_public.jsp" />
                    </div>

                    <div class="col-sm-9 padding-right" >
                        <div class="features_items">
                            <h2 class="title text-center">New Products</h2>

                            <c:choose>
                                <c:when test="${not empty productList}">
                                    <c:forEach var="entry" items="${productList}">
                                        <c:set var="product" value="${entry.key}" />
                                        <div class="col-sm-4">
                                            <div class="product-image-wrapper">
                                                <div class="single-products">
                                                    <div class="productinfo text-center ">
                                                        <!-- Đặt width và height cho ảnh -->
                                                        <img src="${pageContext.request.contextPath}/${product.imgUrl}" 
                                                             alt="${product.name}" class="product-img"
                                                             />
                                                        <h2>${product.price}</h2>
                                                        <p>${product.name}</p>
                                                        <a href="${pageContext.request.contextPath}/ProductDetail?productId=${product.productId}" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p>No Item found</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!-- Pagination -->
                        <nav aria-label="Page navigation" style="text-align: center; margin-top: 20px;">
                            <ul class="pagination" style="justify-content: center;font-size: 14px">
                                <c:if test="${totalPages > 0}">
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="viewList?page=${i}&searchId=${searchId}
                                               &name=${name}&email=${email}&mobile=${mobile}
                                               &availability=${availability}&specialization=${specialization}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </nav>
                        <nav aria-label="Page navigation" style="text-align: center; margin-top: 20px;">
                            <ul class="pagination" style="justify-content: center;font-size: 14px">
                                <li class="page-item">
                                    <a class="page-link" href="#">Show More</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </section>
        <c:import url="/Template/footer1.jsp" />
    </body>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/price-range.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>

    <script>
        $(document).ready(function () {
            $('#recommended-item-carousel').carousel({
                interval: 3000, // Tự động chuyển slide sau 3 giây
                pause: "hover"  // Dừng khi hover chuột vào
            });
        });

        $(document).ready(function () {
            $('#slider-carousel').carousel({
                interval: 1000, // Tự động trượt sau mỗi 3 giây
                pause: "hover"  // Dừng khi di chuột vào
            });
        }
        );
    </script>
</html>


