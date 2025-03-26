<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<c:if test="${empty sessionScope.account or sessionScope.account.roleId != 1}">
    <c:redirect url="/Home/Error404.jsp" />
</c:if>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Settings List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>User Manager</title>
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
        <section id="settings_list">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">${breadcrumbs}</li>
                    </ol>
                </div>
                <div class="row">

                    <div class="col-sm-3">
                        <%@ include file="/Template/left_side_bar_admin.jspf" %>
                        </div>
                        <div class="col-sm-9">

                            
                            
                            <!-- Search and Filter Form -->
                            <form method="get" class="mb-3">
                                <div class="row">
                                    <div class="col-md-4">
                                        <input type="text" name="search" value="${searchValue}" class="form-control" placeholder="Search">
                                    </div>
<!--                                    <div class="col-md-3">
                                        <select name="type" class="form-control">
                                            <option value="">All Types</option>
                                            <option value="type1" ${filterType == 'type1' ? 'selected' : ''}>Type 1</option>
                                            <option value="type2" ${filterType == 'type2' ? 'selected' : ''}>Type 2</option>
                                        </select>
                                    </div>-->
                                    <div class="col-md-2">
                                        <select name="status" class="form-control">
                                            <option value="">All Status</option>
                                            <option value="1" ${filterStatus == 1 ? 'selected' : ''}>Active</option>
                                            <option value="0" ${filterStatus == 0 ? 'selected' : ''}>Inactive</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="submit" class="btn btn-primary">Filter</button>
                                        
                                    </div>
                                </div>
                            </form>

                            <!-- Settings Table -->
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th><a href="?sortBy=setting_id&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}">ID</a></th>
<!--                                        <th><a href="?sortBy=setting_type&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}">Type</a></th>-->
                                        <th><a href="?sortBy=setting_value&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}">Value</a></th>
                                        <th><a href="?sortBy=setting_order&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}">Order</a></th>
                                        <th><a href="?sortBy=setting_status&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}">Status</a></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="setting" items="${settings}">
                                        <tr>
                                            <td>${setting[0]}</td>
                                            <td>${setting[1]}</td>
                                            <td>${setting[2]}</td>
                                            <td>${setting[3]}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${setting[4] == 1}"><span class="badge bg-success">Active</span></c:when>
                                                    <c:otherwise><span class="badge bg-danger">Inactive</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>


                            <!-- Pagination -->
                            <nav>
                                <ul class="pagination">
                                    <<c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&searchValue=${param.searchValue}&typeFilter=${param.typeFilter}&statusFilter=${param.statusFilter}&sort=${param.sort}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>

                </div>
                </div>
            </div>
                                    
        </section>

        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
