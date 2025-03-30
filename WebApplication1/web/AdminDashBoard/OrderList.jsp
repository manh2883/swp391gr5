
<%-- 
    Document   : OrderList
    Created on : Feb 27, 2025, 5:43:34 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>


<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Order List</title>
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

            .pagination-container {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }

            .pagination {
                list-style: none;
                display: flex;
                padding: 0;
            }

            .pagination .page-item {
                margin: 0 5px;
            }

            .pagination .page-link {
                padding: 8px 12px;
                border-radius: 5px;
                text-decoration: none;
                color: #ff7f00;
                border: 1px solid #ff7f00;
            }

            .pagination .page-link:hover {
                background-color: #ff7f00;
                color: white;
            }

            .pagination .active .page-link {
                background-color: #ff7f00;
                color: white;
            }


            .productinfo .btn {
                display: inline-block; /* Đảm bảo các nút được xếp thành dòng ngang */

            }
            .product-img {
                width: 242px;
                height: 225px;
                object-fit: contain; /* Giữ nguyên tỷ lệ, có thể có khoảng trắng */
                background-color: #f8f8f8; /* Màu nền cho khoảng trống */
            }

            .status-1 { /* Pending */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #fff3cd;
                color: #856404;

            }

            .status-2 { /* Accepted */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #d4edda;
                color: #155724;
            }

            .status-3 { /* Shipping */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #cce5ff;
                color: #004085;
            }

            .status-4 { /* Delivered */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #d1ecf1;
                color: #0c5460;
            }

            .status-5 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .status-6 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .status-7 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .status-8 { /* Received */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #cce5ff; /* Màu xanh nhạt */
                color: #004085; /* Màu xanh đậm */
            }

            .default-status {
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #e2e3e5;
                color: #383d41;
            }

            
        </style>
    </head>
    <body>
        <header>
            <c:import url="/Template/header1.jsp" />
            <c:import url="/Template/header2.jsp" />
        </header>

        <section id="order_list">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/Home">Home</a></li>
                        <li class="active">Order List</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/left_side_bar_admin.jspf" %>
                    </div>
                    <div class="col-sm-9">
                        <!-- Search and Filter Form -->
                        <form method="GET" action="OrderList" class="row g-3 mb-3">
                            <div class="col-md-3">
                                <input type="text" name="search" class="form-control" placeholder="Search" value="${param.search}">
                            </div>
                            <div class="col-md-2">
                                <select name="status" class="form-control">
                                    <option value="">All Status</option>
                                    <option value="1" ${param.status == '1' ? 'selected' : ''}>Pending</option>
                                    <option value="6" ${param.status == '2' ? 'selected' : ''}>Accepted</option>
                                    <option value="2" ${param.status == '3' ? 'selected' : ''}>Shipping</option>
                                    <option value="3" ${param.status == '4' ? 'selected' : ''}>Delivered</option>
                                    <option value="4" ${param.status == '5' ? 'selected' : ''}>Canceled By Customer</option>
                                    <option value="5" ${param.status == '6' ? 'selected' : ''}>Canceled By Seller</option>
                                    <option value="5" ${param.status == '7' ? 'selected' : ''}>Canceled By System</option>
                                    <option value="5" ${param.status == '8' ? 'selected' : ''}>Completed</option>

                                </select>
                            </div>
                            <div class="col-md-2">
                                <input type="date" name="fromDate" class="form-control" value="${param.fromDate}">
                            </div>
                            <div class="col-md-2">
                                <input type="date" name="toDate" class="form-control" value="${param.toDate}">
                            </div>

                            <div class="col-md-2">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>

                        <!-- Orders Table -->
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Username</th>
                                    <th>Total Amount</th>
                                    <th>Status</th>
                                    <th>Date</th>
                                    <th></th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${orders}">
                                    <tr>
                                        <td>${order[0]}</td>
                                        <td>${order[4]}</td>
                                        <td>${order[1]}</td>
                                        <td class="" style="font-size: 9px;">
                                            <p class=" status-${order[2]}">
                                                <c:choose>
                                                    <c:when test="${order[2] == '1'}">Pending</c:when>
                                                    <c:when test="${order[2] == '2'}">Accepted</c:when>
                                                    <c:when test="${order[2] == '3'}">Shipping</c:when>
                                                    <c:when test="${order[2] == '4'}">Delivered</c:when>
                                                    <c:when test="${order[2] == '5'}">Canceled By Customer</c:when>
                                                    <c:when test="${order[2] == '6'}">Canceled By Seller</c:when>
                                                    <c:when test="${order[2] == '7'}">Canceled By Seller</c:when>
                                                    <c:when test="${order[2] == '8'}">Received</c:when>
                                                    <c:otherwise>Unknown</c:otherwise>
                                                </c:choose>
                                            </p> 
                                        </td>
                                        <td>${order[3]}</td>

                                        <td>
                                            <a href="OrderDetail?orderId=${order[0]}" class="btn btn-sm btn-info">View</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <div class="pagination-container">
                            <ul class="pagination">

                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
                                    </li>
                                </c:if>


                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}">${i}</a>
                                    </li>
                                </c:forEach>


                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage + 1}">Next</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>


                    </div>
                </div>
            </div>
        </section> <!--/#cart_items-->
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>