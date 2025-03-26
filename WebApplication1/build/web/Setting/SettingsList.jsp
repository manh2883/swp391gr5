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
                        <script>
                            function enableEdit(settingId) {
                                document.getElementById('name-' + settingId).removeAttribute('readonly');
                                document.getElementById('value-' + settingId).removeAttribute('readonly');
                                document.getElementById('save-' + settingId).style.display = 'inline';
                                document.getElementById('cancel-' + settingId).style.display = 'inline';
                            }

                            function cancelEdit(settingId) {
                                document.getElementById('name-' + settingId).setAttribute('readonly', true);
                                document.getElementById('value-' + settingId).setAttribute('readonly', true);
                                document.getElementById('save-' + settingId).style.display = 'none';
                                document.getElementById('cancel-' + settingId).style.display = 'none';
                            }
                        </script>

                        </head>
                        <body>
                            <h2>Settings List</h2>

                            <form action="SettingsListServlet" method="get">
                                Search Name/Value: <input type="text" name="searchValue" value="${searchValue}">
                                
                                <select name="filterType">
                                    <option value="">-- All Types --</option>
                                    <c:forEach var="type" items="${settingTypes}">
                                        <option value="${type}" ${type == filterType ? 'selected' : ''}>${type}</option>
                                    </c:forEach>
                                </select>

                                <input type="submit" value="Search">
                            </form>

                            <table border="1">
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Value</th>
                                    <th>Type</th>
                                    <th>Actions</th>
                                </tr>
                                <c:forEach var="setting" items="${settings}">
                                    <tr>
                                        <td>${setting[0]}</td>
                                        <td><input type="text" id="name-${setting[0]}" value="${setting[1]}" readonly></td>
                                        <td><input type="text" id="value-${setting[0]}" value="${setting[2]}" readonly></td>
                                        <td>${setting[3]}</td>
                                        <td>
                                            <button onclick="enableEdit(${setting[0]})">Edit</button>
                                            <button id="save-${setting[0]}" style="display:none;">Save</button>
                                            <button id="cancel-${setting[0]}" style="display:none;" onclick="cancelEdit(${setting[0]})">Cancel</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>

                            <div id="editForm" style="display: ${editSetting != null ? 'block' : 'none'};">
                                <h3>Edit Setting</h3>
                                <form action="SettingsListServlet" method="post">
                                    <input type="hidden" id="settingId" name="settingId" value="${editSetting[0]}">
                                    Name: <input type="text" id="settingName" name="settingName" value="${editSetting[1]}"><br>
                                    Value: <input type="number" id="settingValue" name="settingValue" value="${editSetting[2]}"><br>
                                    Type: <input type="text" id="settingType" name="settingType" value="${editSetting[3]}"><br>
                                    <input type="submit" value="Save">
                                    <button type="button" onclick="document.getElementById('editForm').style.display = 'none'">Cancel</button>
                                </form>
                            </div>
                                    
                                    

                        </body>

                    </div>
                </div>
            </div>

        </section>

        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
