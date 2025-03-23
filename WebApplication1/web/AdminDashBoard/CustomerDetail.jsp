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
        <title>My Profile</title>
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
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                height: 20%;
                width: 30%;
                background-color: rgba(0,0,0,0.4);
            }
            .modal-content {
                background-color: white;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 20%;
                text-align: center;
            }
        </style>
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

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}">Home</a></li>
                         <li><a href="${pageContext.request.contextPath}/customerlist">Customer List</a></li>
                        <li class="active">Customer Profile</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <h2 class="title text-center">Customer Detail</h2>

                        <p class="text-center" style="color: green">${message}</p>
                        <input name="email" id="email" value="${email}" type="hidden">
                        <form method="post" action="MyProfile" id="profileForm">
                            <table style="width: 60%; table-layout: fixed; margin: auto">
                                <tbody>
                                    <tr style="visibility: hidden">
                                        <td style="width: 30%">
                                            1
                                        </td>
                                        <td class=" text-start" style="width: 70%">
                                            1
                                        </td>
                                    </tr>
                                    <tr >
                                        <td class="text-end">
                                            <label>User Name:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="text" id="userName" class="form-control" name="userName" required
                                                   minlength="8" maxlength="20" pattern="[a-zA-Z0-9]+" value="${userName}" readonly
                                                   style="margin-bottom: 10px;height: 35px;">
                                            <div class="text-danger" id="userNameError"></div>
                                        </td>
                                    </tr>
                                    <tr style="margin-bottom: 10px;">
                                        <td class="text-end">
                                            <label>First Name:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="text" id="firstName" class="form-control" name="firstName" required
                                                   minlength="1" maxlength="20" style="margin-bottom: 10px; height: 35px;"
                                                   pattern="[a-zA-Z]+" value="${firstName}" readonly>
                                            <div class="text-danger" id="firstNameError"></div>
                                        </td>
                                    </tr>
                                    <tr >
                                        <td class="text-end">
                                            <label>Last Name:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="text" id="lastName" class="form-control" name="lastName" required
                                                   minlength="1" maxlength="20" pattern="[a-zA-Z]+" value="${lastName}" 
                                                   style="margin-bottom: 10px;height: 35px;" readonly>
                                            <div class="text-danger" id="lastNameError"></div>
                                        </td>
                                    </tr>
                                    <tr >
                                        <td class="text-end">
                                            <label>Gender:</label>
                                        </td>
                                        <td class="text-start">
                                            <select class="form-select" id="gender" name="gender" style="margin-bottom: 10px;height: 35px; display: none;  font-family: 'roboto';
                                                    font-size: 12px;
                                                    font-weight: 300;">
                                                <option value="1" ${gender != null && gender == 1 ? "selected" : ""}>Male</option>
                                                <option value="0" ${gender != null && gender == 0 ? "selected" : ""}>Female</option>
                                                <option value="2" ${gender != null && gender == 2 ? "selected" : ""}>Other</option>
                                            </select>
                                            <input type="text" id="genderShow" class="form-control"  required
                                                   value="${genderString}" 
                                                   style="margin-bottom: 10px;height: 35px; " readonly>

                                        </td>
                                    </tr>
                                    <tr style="margin-bottom: 10px;">
                                        <td class="text-end">
                                            <label>DoB:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="date" id="dob" class="form-control" name="dob" 
                                                   required value="${dob}" readonly style="margin-bottom: 10px; height: 35px;">
                                            <div class="text-danger" id="dobError"></div>
                                        </td>
                                    </tr>
                                    
                                </tbody>
                            </table>
                        </form>
                        <br><br><br>
                        <!-- Modal -->                      
                        <div id="confirmModal" class="modal" style="display: none;">
                            <div class="modal-content" style="width: 30%;">
                                <h2 class="title text-center" style="color: black">Edit your profile?</h2>
                                <br>
                                <table>
                                    <tr>
                                        <td class="w-50">
                                            <button id="confirmSubmit" class="btn btn-default get-manh" style="padding-left: 15px;padding-right: 15px;">Yes</button>
                                        </td>
                                        <td class="w-50">
                                            <button id="cancelSubmit" class="btn btn-default get-manh" style="background-color: crimson;padding-left: 15px;padding-right: 15px;">No</button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <h2 class="title text-center">Customer Order</h2>
                        <div class="category-tab shop-details-tab">
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="${activeTab == '1' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/CustomerDetail?userId=${userId}&status=1">Pending</a>
                                    </li>

                                    <li class="${activeTab == '2' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/CustomerDetail?userId=${userId}&status=2">Accepted</a>
                                    </li>

                                    <li class="${activeTab == '3' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/CustomerDetail?userId=${userId}&status=3">Shipping</a>
                                    </li>

                                    <li class="${activeTab == '4' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/CustomerDetail?userId=${userId}&status=4">Delivered</a>
                                    </li>

                                    <li class="${activeTab == '9' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/CustomerDetail?userId=${userId}&status=9">Canceled</a>
                                    </li>
                                    <li class="${activeTab == '8' ? 'active' : ''}">
                                        <a href="${pageContext.request.contextPath}/CustomerDetail?userId=${userId}&status=8">Received</a>
                                    </li>
                                </ul>

                            </div>
                            <div class="table-responsive cart_info">
                                <table class="table table-condensed">
                                    <thead>
                                        <tr class="cart_menu">
                                            <td class="price">ID</td>
                                            <td class="price" style="width: 75px">Status</td>
                                            <td class="price">Amount</td>
                                            <td class="total">Created At</td>
                                            <td class="total">Completed At</td>
                                            <td class="total"></td>   
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty orders}">
                                                <c:forEach var="order" items="${orders}">
                                                    <tr style="vertical-align: middle;"
                                                        onclick="window.location.href =
                                                                        '${pageContext.request.contextPath}/OrderDetail?orderId=${order.orderId}'">
                                                        <td class="cart_price">
                                                            <p>${order.orderId}</p> 
                                                        </td>
                                                        <td class="" style="font-size: 9px;">
                                                            <p class=" status-${order.statusId}">
                                                                <c:choose>
                                                                    <c:when test="${order.statusId == '1'}">Pending</c:when>
                                                                    <c:when test="${order.statusId == '6'}">Accepted</c:when>
                                                                    <c:when test="${order.statusId == '2'}">Shipping</c:when>
                                                                    <c:when test="${order.statusId == '3'}">Delivered</c:when>
                                                                    <c:when test="${order.statusId == '4'}">Canceled By Customer</c:when>
                                                                    <c:when test="${order.statusId == '5'}">Canceled By Seller</c:when>
                                                                    <c:otherwise>Unknown</c:otherwise>
                                                                </c:choose>
                                                            </p> 
                                                        </td>
                                                        <td class="cart_price" style="padding-left: 10px;">
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
                    </div>
                    <br><!-- comment -->
                    <br>
                    <br>
                </div>

        </section>
        <c:import url="/Template/footer1.jsp" />
<!--        <script>
            $(document).ready(function () {
                $(".nav-tabs li").click(function (e) {
                    e.preventDefault();
                    let status = $(this).data("status");
                    let userId = $("#userId").val(); // Lấy userId từ hidden input hoặc JSP

                    $(".nav-tabs li").removeClass("active");
                    $(this).addClass("active");

                    $.ajax({
                        url: "CustomerDetail",
                        type: "GET",
                        data: {userId: userId, status: status},
                        beforeSend: function () {
                            $("#tabContent").html("Loading...");
                        },
                        success: function (response) {
                            $("#tabContent").html(response);
                        },
                        error: function () {
                            $("#tabContent").html("Error loading data.");
                        }
                    });
                });

                $(".nav-tabs li.active").trigger("click");
            });

        </script>-->
    </body>

</html>
