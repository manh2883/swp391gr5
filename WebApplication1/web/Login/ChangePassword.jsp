<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

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
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9" style="border: 1px solid black">
                        <h2 class="title text-center">Change Password</h2>
                        <table class="col-12 " style="border: 1px solid red">
                            <tbody class="" style="border: 1px solid green">
                                <tr style="visibility: hidden;border:1px solid orange" class="col-12" >
                                    <td class="w-50">
                                    </td>

                                    <td class="w-50">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-end">
                                        <label>Name:</label>
                                    </td>

                                    <td class=" text-start " style="padding-bottom: 10px">
                                        <input type="text" id="name" class="form-control mt-2" name="name" required 
                                               style="color: black;width: 100%;
                                               height: 35px;margin-bottom: 10px;">
                                        <div class="text-danger" id="nameError"></div>
                                    </td>
                                </tr>
                                <tr style="padding-bottom: 20px">
                                    <td class= "text-end">
                                        <label>Description:</label>
                                    </td>

                                    <td class=" text-start " style="padding-bottom: 10px;height: max-content">
                                        <input type="text" id="description" class="form-control mt-2" name="description" style="color: black;width: 100%;
                                               height: 35px;margin-bottom: 10px;">
                                        <div class="text-danger" id="descriptionError"></div>
                                    </td>
                                </tr >
                                <tr style="padding-bottom: 20px">
                                    <td class=" text-end">
                                        <label>Price:</label>
                                    </td>
                                   
                                    <td class=" text-start " style="padding-bottom: 10px">
                                        <input type="number" id="price" class="form-control mt-2"
                                               name="price" style="color: black;width: 100%;
                                               height: 35px;margin-bottom: 10px;" required>
                                        <div class="text-danger" id="priceError"></div>
                                        <!--<button type="submit" class="btn btn-default get-manh " >Send OTP</button>-->
                                    </td>
                                </tr>
                                <tr style="border: 1px solid red">
                                    <td class=" text-start col-12"><button type="submit" class="btn btn-default get-manh " >Cancel</button></td>

                                    <td class=" text-start col-12"><button type="submit" class="btn btn-default get-manh " >Change</button></td>
                                </tr>

                            </tbody>
                        </table>

                    </div>
                </div>
        </section>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
