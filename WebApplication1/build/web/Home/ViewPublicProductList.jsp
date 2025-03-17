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

        <section style="">
            <div class="container" >
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/left_side_bar_public.jspf" %>
                    </div>

                    <div class="col-sm-9 padding-right" >
                        <div class="padding-right"   >
                            <div class="row">
                                <div class="col-sm-12">
                                    <form>
                                        <table style="align-items: center">
                                            <tbody>
                                                <tr style="border:1px solid blue">
                                                    <td>
                                                        <input type="text" placeholder="Zip / Postal Code *">
                                                    </td>
                                                    <td>
                                                        <select>
                                                            <option>-- Country --</option>
                                                            <option>United States</option>
                                                        </select><!-- comment -->
                                                    </td>
                                                    <td>
                                                        <select>
                                                            <option>-- State / Province / Region --</option>
                                                            <option>United States</option>
                                                            <option>Bangladesh</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <a class="btn btn-primary" href="">Continue</a>
                                                    </td> 	
                                                </tr>
                                                <tr style="padding-top: 20px;border:1px solid blue">
                                                    <td>
                                                        <select>
                                                            <option>-- State / Province / Region --</option>
                                                            <option>United States</option>
                                                            <option>Bangladesh</option>

                                                        </select>
                                                    </td>
                                                    <td>
                                                        <input type="text" placeholder="Zip / Postal Code *">
                                                    </td>
                                                    <td>
                                                        <select>
                                                            <option>-- Country --</option>
                                                            <option>United States</option>
                                                        </select><!-- comment -->
                                                    </td>
                                                    <td>
                                                        <a class="btn btn-primary" href="">Get Quotes</a>
                                                    </td>

                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>


                                </div>
                            </div>
                        </div>

                        <div class="features_items">
                            <h2 class="title text-center">New Products</h2>

                            <c:choose>
                                <c:when test="${not empty productList}">
                                    <c:forEach var="entry" items="${productList}">
                                        <c:set var="product" value="${entry.key}" />
                                        <c:set var="tagMap" value="${entry.value}" />

                                        <div class="col-sm-4">
                                            <div class="product-image-wrapper">
                                                <div class="single-products">
                                                    <div class="productinfo text-center">
                                                        <c:forEach var="tagEntry" items="${tagMap}">
                                                            <c:set var="isNew" value="${tagEntry.key}" />
                                                            <c:set var="sale" value="${tagEntry.value}" />
                                                        </c:forEach>
                                                        <img src="${pageContext.request.contextPath}/${product.imgUrl}" 
                                                             alt="${product.name}" class="product-img" />

                                                        <c:choose>
                                                            <c:when test="${not empty sale}">
                                                                <h2><span>${sale}</span>
                                                                    <span style="text-decoration: line-through; padding-right: 10px; font-size:20px;color: gray;">
                                                                        ${product.price}
                                                                    </span>
                                                                </h2>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <h2>${product.price}</h2>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <p>${product.name}</p>
                                                        <a href="${pageContext.request.contextPath}/ProductDetail?productId=${product.productId}" 
                                                           class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i> Add to cart</a>
                                                    </div>

                                                    <!-- Tag -->
                                                    <c:choose>
                                                        <c:when test="${not empty sale}">
                                                            <img src="${pageContext.request.contextPath}/Images/Home/sale.png" class="new" alt="Sale" />
                                                        </c:when>
                                                        <c:when test="${isNew}">
                                                            <img src="${pageContext.request.contextPath}/Images/Home/new.png" class="new" alt="New" />
                                                        </c:when>
                                                    </c:choose>

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
                         <!--<p>abc ${log} abc</p>-->
                        <!-- Pagination -->
                        <nav aria-label="Page navigation" style="text-align: center; margin-top: 20px;">
                            <ul class="pagination" style="justify-content: center;font-size: 14px">
                                <c:if test="${totalPages > 0}">
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class=" ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/PublicProductList?${currentLink}page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </c:if>
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


