<%-- 
    Document   : CustomerList
    Created on : Feb 27, 2025, 8:50:22 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<c:if test="${empty sessionScope.account or sessionScope.account.roleId != 1}">
    <c:redirect url="/Home/Error404.jsp" />
</c:if>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="../../../index.html" />
        <meta name="Version" content="v1.2.0" />
        <!-- favicon -->
        <!--<link rel="shortcut icon" href="${pageContext.request.contextPath}/Images/Doctor/favicon.ico.png">-->
        <!-- Bootstrap -->

        <!--<link href="https://unicons.iconscout.com/release/v3.0.6/CSS/line.css"  rel="stylesheet">-->
        <!-- Css -->

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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


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

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">${breadcrumbs}</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/left_side_bar_admin.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <div class="table-responsive cart_info ">
                            <!-- Form cho các hành động cập nhật giỏ hàng -->
                            <form action="ViewCart" method="get" id="cartForm">
                                <input type="hidden" name="cartDetailID" id="cartDetailID">
                                <input type="hidden" name="action" id="action">
                            </form>

                            <!-- Form cho hành động Checkout -->
                            <form action="Checkout" method="post" id="checkoutForm">
                                <input type="hidden" name="selectedItems" id="selectedItems">
                            </form>

                            <table class="table table-condensed">
                                <thead>
                                    <tr class="cart_menu">

                                        <td class="image">Customer</td>
                                        <!--image + name + contact-->
                                        <td class="description"></td>
                                        <td class="price">User Name</td>
                                        <td class="quantity">Status</td>
                                        <td class="quantity">Orders</td>
                                        <td class="quantity">Total Spending</td>
                                        <td class="total">Ave.Amount/Order</td>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty cartDetails}">
                                            <c:set var="totalPrice" value="0" scope="page" />
                                            <c:forEach var="cart" items="${cartDetails}">
                                                <tr>
                                                    <td class="cart_product">
                                                        <!--Image-->
                                                        <a href=""><img src="${pageContext.request.contextPath}/${cart.product.imgUrl}" alt=""></a>
                                                    </td>
                                                    <td class="cart_description">
                                                        <h4><a href="">Product ${cart.productID}</a></h4>
                                                        <p>Email: ${cart.cartDetailID}</p>
                                                        <p>Phone: ${cart.productVariantID}</p>
                                                    </td>
                                                    <td class="cart_price">
                                                        <p>${cart.product.price}</p>
                                                    </td>
                                                    <td class="cart_quantity">
                                                        <p>${cart.product.price}</p>
                                                    </td>


                                                    <td class="cart_price">
                                                        <button type="button" class="cart_quantity_delete btn" 
                                                                onclick="submitCartForm(${cart.cartDetailID}, 'delete')">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </td>
                                                </tr>

                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="7" style="text-align: center;">No items in your cart.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
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
        </section> <!--/#cart_items-->
        <c:import url="/Template/footer1.jsp" />
    </body>
    <script>

    </script>
</html>