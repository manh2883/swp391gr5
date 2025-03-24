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
                        <div class="table-responsive cart_info">
                            <a href="add-setting.jsp" class="btn btn-primary mb-3">Add New Setting</a>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Type</th>
                                        <th>Value</th>
                                        <th>Order</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="setting" items="${settings}">
                                        <tr>
                                            <td>${setting[0]}</td>
                                            <td>${setting[3]}</td>
                                            <td>${setting[2]}</td>
                                            <td>${setting[4]}</td>
<!--                                            <td>${setting[5] == 1 ? "Active" : "Inactive"}</td>-->
                                            <td>
                                                <a href="view-setting.jsp?id=${setting[0]}" class="btn btn-info btn-sm">View</a>
                                                <a href="edit-setting.jsp?id=${setting[0]}" class="btn btn-warning btn-sm">Edit</a>
<!--                                                <a href="toggle-setting.jsp?id=${setting[0]}" class="btn btn-danger btn-sm">
                                                    ${setting[5] == 1 ? "Deactivate" : "Activate"}
                                                </a>-->
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <a href="?page=${i}" class="btn btn-secondary">${i}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
