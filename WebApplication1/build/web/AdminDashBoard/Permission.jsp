<%-- 
    Document   : CustomerList
    Created on : Feb 27, 2025, 8:50:22 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${empty sessionScope.account }">
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
                width: 121px;
                height: 112px;
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
                        <li><a href="${pageContext.request.contextPath}">Home</a></li>
                        <li class="active">${breadcrumbs}</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/left_side_bar_admin.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <div class="table-responsive cart_info">

                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Permission</th>
                                            <c:forEach var="role" items="${roleIds}">
                                            <th>${role}</th>
                                            </c:forEach>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="row" items="${permissions}">
                                        <tr>
                                            <td>${row[1]}</td> <!-- Permission Name -->
                                            <c:forEach var="index" begin="2" end="${fn:length(row) - 1}">
                                                <td>
                                                    <form method="post" action="PermissionManager">

                                                        <input type="hidden" name="permissionId" value="${row[0]}">
                                                        <input type="hidden" name="roleId" value="${index-1}">
                                                        <button type="submit" class="btn btn-sm ${row[index] ? 'btn-success' : 'btn-danger'}">
                                                            ${row[index] ? 'ON' : 'OFF'}
                                                        </button>
                                                    </form>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
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
//        function togglePermission(button) {
//            let permissionId = button.getAttribute("data-permission-id");
//            let roleId = button.getAttribute("data-role-id");
//            let currentState = button.getAttribute("data-state") === "true";
//            let newState = !currentState;
//
//            $.post("RolePermissionServlet", {
//                permissionId: permissionId,
//                roleId: roleId,
//                newState: newState
//            }).done(function (response) {
//                if (response === "success") {
//                    button.setAttribute("data-state", newState);
//                    button.textContent = newState ? "ON" : "OFF";
//                    button.classList.toggle("btn-success", newState);
//                    button.classList.toggle("btn-danger", !newState);
//                }
//            }).fail(function () {
//                alert("Lỗi cập nhật quyền");
//            });
//        }
    </script>
</html>