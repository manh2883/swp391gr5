<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<c:if test="${empty sessionScope.account}">
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/Images/tpfv1_reverse.ico" type="image/x-icon">
        <link href="${pageContext.request.contextPath}/CSS/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/responsive.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .productinfo .btn {
                display: inline-block;
            }
            .product-img {
                width: 121px;
                height: 112px;
                object-fit: contain;
                background-color: #f8f8f8;
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
                        <li><a href="${pageContext.request.contextPath}">Home</a></li>
                        <li class="active">${breadcrumbs}</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <p>${message}</p>
                        <div class="category-tab shop-details-tab">
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="${activeTab == '1' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/MyOrder?status=1">Pending</a>
                                    </li>

                                    <li class="${activeTab == '6' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/MyOrder?status=6">Accepted</a>
                                    </li>

                                    <li class="${activeTab == '2' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/MyOrder?status=2">Shipping</a>
                                    </li>

                                    <li class="${activeTab == '3' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/MyOrder?status=3">Delivered</a>
                                    </li>

                                    <li class="${activeTab == '7' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/MyOrder?status=7">Canceled</a>
                                    </li>
                                </ul>

                            </div>
                            <div class="table-responsive cart_info">
                                <table class="table table-condensed">
                                    <thead>
                                        <tr class="cart_menu">
                                            <td class="price">ID</td>
                                            <td class="price">Status</td>
                                            <td class="price">Amount</td>
                                            <td class="total">Created At</td>
                                            <td class="total">Completed At</td>
                                            <td class="total"></td>   
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="statusMap" value="${{
                                                                        '1': 'Pending',
                                                                        '6': 'Accepted',
                                                                        '2': 'Shipping',
                                                                        '3': 'Delivered',
                                                                        '7': 'Canceled'
                                                                        }}" />

                                        <c:choose>
                                            <c:when test="${not empty orders}">
                                                <c:forEach var="order" items="${orders}">
                                                    <tr onclick="window.location.href = '${pageContext.request.contextPath}/OrderDetail?orderId=${order.orderId}'">
                                                        <td class="cart_price">
                                                            <p>${order.orderId}</p> 
                                                        </td>
                                                        <td class="cart_price">
                                                            <p>
                                                                <span class="label label-${order.statusId}">
                                                                    ${statusMap[order.statusId]}
                                                                </span>
                                                            </p> 
                                                        </td>
                                                        <td class="cart_price">
                                                            <p>${order.totalamount}</p> 
                                                        </td>
                                                        <td class="cart_quantity">
                                                            <div> 
                                                                <p>${order.createAt}</p>
                                                            </div>
                                                        </td>
                                                        <td class="cart_quantity">
                                                            <div> 
                                                                <c:choose>
                                                                    <c:when test="${not empty order.completedAt and order.completedAt != 'null'}">
                                                                        <p>${order.completedAt}</p>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <p>N/A</p>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="5" style="text-align: center;">You have no orders yet.</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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
        </section>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
