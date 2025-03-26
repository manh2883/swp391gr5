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
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }
            .pagination li {
                margin: 0 5px;
            }
            .pagination .page-link {
                padding: 8px 12px;
                border: 1px solid #007bff;
                color: #007bff;
                text-decoration: none;
                border-radius: 5px;
            }
            .pagination .active .page-link {
                background-color: #007bff;
                color: white;
            }
        </style>

        <script>
            function enableEdit(row) {
                $("#editBtn" + row).hide();
                $("#saveBtn" + row).show();
                $("#cancelBtn" + row).show();
                $("#name" + row).prop("readonly", false);
                $("#value" + row).prop("readonly", false);
            }

            function cancelEdit(row) {
                $("#editBtn" + row).show();
                $("#saveBtn" + row).hide();
                $("#cancelBtn" + row).hide();
                $("#name" + row).prop("readonly", true);
                $("#value" + row).prop("readonly", true);
            }

            function saveSetting(row) {
                let settingId = row;
                let settingName = $("#name" + row).val();
                let settingValue = $("#value" + row).val();

                $.ajax({
                    url: "settings",
                    type: "POST",
                    data: {
                        settingId: settingId,
                        settingName: settingName,
                        settingValue: settingValue
                    },
                    success: function (response) {
                        if (response.success) {
                            alert("Update successful!");
                            cancelEdit(row);
                        } else {
                            alert("Update failed!");
                        }
                    }
                });
            }
        </script>
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
                        <h2>Settings List</h2>

                        <!-- Search & Filter Form -->
                        <form action="SettingsListServlet" method="get">
                            <input type="text" name="search" value="${param.search}" placeholder="Search Name or Value">

                            <select name="type">
                                <option value="">All Types</option>
                                <c:forEach var="t" items="${types}">
                                    <option value="${t}" ${t == type ? 'selected' : ''}>${t}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>

                        <!-- Settings Table -->
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Value</th>
                                    <th>Type</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="setting" items="${settings}">
                                    <tr>
                                        <td>${setting[0]}</td>
                                        <td><input type="text" id="name${setting[0]}" value="${setting[1]}" readonly class="form-control"></td>
                                        <td><input type="text" id="value${setting[0]}" value="${setting[2]}" readonly class="form-control"></td>
                                        <td>${setting[3]}</td>
                                        <td>
                                            <button id="editBtn${setting[0]}" class="btn btn-warning" onclick="enableEdit(${setting[0]})">Edit</button>
                                            <button id="saveBtn${setting[0]}" class="btn btn-success" style="display:none;" onclick="saveSetting(${setting[0]})">Save</button>
                                            <button id="cancelBtn${setting[0]}" class="btn btn-secondary" style="display:none;" onclick="cancelEdit(${setting[0]})">Cancel</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- Pagination -->
                        <div>
                            <c:if test="${totalPages > 1}">
                                <ul class="pagination">
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&search=${param.search}&type=${param.type}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
