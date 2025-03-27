<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<c:if test="${empty sessionScope.account}">
    <c:redirect url="/Home/Error404.jsp" />
</c:if>--%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Change Password</title>
<!--<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="${pageContext.request.contextPath}/CSS/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/responsive.css" rel="stylesheet">  

        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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

                <div class="row">
                    <!-- Sidebar -->
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <!-- Address Manager -->
                    <div class="col-sm-9">
                        <h2 class="title text-center">Address Manager</h2>
                        <!--<p class="text-center" style="color: green">${message}</p>-->
                        
                        <div class="d-flex justify-content-between align-items-center">
                            <h4>My Addresses</h4>
                            <form action="AddressManager" method="post" class="d-flex gap-2">
                                <input type="hidden" name="action" value="add" />
                                <input type="text" name="newAddress" placeholder="Enter new address" class="form-control" required />
                                <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" 
                                        style="height: 38px; margin-top: auto; margin-bottom: auto;">
                                    + Add New Address
                                </button>
                            </form>
                        </div>



                        <div class="mt-3">
                            <c:forEach var="address" items="${addressList}">    
                                <div class="address-card p-3 border rounded mb-3">
                                    <div>
                                        <strong>${user.firstName} ${user.lastName}</strong> | ${user.phoneNumber}
                                        <table class="w-100 mt-2">
                                            <tr>

                                                <td class="w-75">
                                                    <form action="AddressManager" method="post">
                                                        <input type="hidden" name="action" value="update" />
                                                        <input type="hidden" name="addressId" value="${address.addressID}" />
                                                        <input type="text" name="updatedAddress" value="${address.addressLine}" 
                                                               class="form-control" />
                                                </td>

                                                <td class="w-15">
                                                    <button type="submit" class="btn btn-warning w-100">Update</button>
                                                    </form>
                                                </td>

                                                <td class="w-10">
                                                    <form action="${pageContext.request.contextPath}/AddressManager" method="post" 
                                                          onsubmit="return confirm('Are you sure to delete this address?')">
                                                        <input type="hidden" name="action" value="delete" />
                                                        <input type="hidden" name="addressId" value="${address.addressID}" />
                                                        <button type="submit" class="btn btn-danger w-100">Delete</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>


                    </div>
                </div>
        </section>

        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
