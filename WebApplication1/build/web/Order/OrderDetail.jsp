<%-- 
    Document   : MyOrder
    Created on : Mar 4, 2025, 9:46:00 AM
    Author     : Dell
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Checkout</title>
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
        <c:import url="/Template/header1.jsp" />
        <c:import url="/Template/header2.jsp" />
        <form action="Checkout" method="post" onsubmit="return validateForm(event)">
            <section id="cart_items">
                <div class="container">
                    <div class="breadcrumbs">
                        <ol class="breadcrumb">
                            <li><a href="#">Home</a></li>
                            <li class="active">My Order</li>
                        </ol>
                    </div>
                    <p style="color: red">${message}</p>
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-sm-4 clearfix" >
                                <div class="bill-to">
                                    <p>${orderInformation}</p>
                                    <p>Bill To</p>
                                    <!--<div class="" style="float: left; border:1px solid darkgreen">-->
<!--                                        <input type="text" name="email" placeholder="Email*" value="${user.email}" required>-->
                                    <div class="form-floating mb-10">
                                        <input id="nameInput" class="form-control" type="text" name="name" style="height: 45px; margin-bottom: 10px"
                                               placeholder="Name*" value="${user.firstName} ${user.lastName}" required >
                                        <label for="nameInput">Receiver:</label>
                                        <div class="text-danger" id="nameError"></div>
                                    </div>

                                    <div class="form-floating mb-10">
                                        <input id="contactInput" class="form-control" type="text" name="contact" style="height: 45px; margin-bottom: 10px"
                                               placeholder="Contact*" value="${user.phoneNumber}" required>
                                        <label for="contactInput">Contact:</label>
                                        <div class="text-danger" id="contactError"></div>
                                    </div>    

                                    <div class="form-floating mb-10">
                                        <select name="address" id="address" class="form-control" style="height: 45px;margin-bottom: 10px;" onchange="toggleAddressInput(this)">
                                            <option value="">-- Select Address --</option>
                                            <c:forEach var="addr" items="${userAddresses}">
                                                <option value="${addr.addressLine}">${addr.addressLine}</option>
                                            </c:forEach>
                                            <option value="Other">Other</option>
                                        </select>

                                        <span id="newAddressError" class="error-message"></span>

                                        <input   type="text" name="newAddress" id="newAddress" class="form-control mt-2"
                                                 style="height: 45px;margin-bottom: 10px; display:none;"
                                                 placeholder="Enter new address" />


                                        <label for="adress">Address</label>
                                        <div class="text-danger" id="addressError"></div>
                                    </div>    
                                    <div class="order-message">
                                        <p>Shipping Order</p>
                                        <textarea name="orderNote" id="orderNote" placeholder="Notes about your order, Special Notes for Delivery" rows="6"></textarea>
                                        <!--<label><input type="checkbox"> Shipping to bill address</label>-->
                                        <span id="orderNoteError" class="error-message text-danger"></span>
                                    </div>
                                    <!--</div>-->
                                </div>
                            </div>
                            <div class="col-sm-8" >

                                <div class="review-payment">
                                    <h2>Review & Payment</h2>
                                </div>

                                <div class="table-responsive cart_info">
                                    <table class="table">
                                        <thead style="background-color: #FE980F;">
                                            <tr>
                                                <th>Item</th>
                                                <th>Quantity</th>
                                                <th>Price</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="cartTotal" value="0" />
                                            <c:forEach var="item" items="${checkoutItems}">
                                                <tr>
                                                    <td>${item.product.name}</td>
                                                    <td>${item.quantity}</td>
                                                    <td>${item.product.price}</td>
                                                    <td class="subtotal">
                                                        <c:set var="itemTotal" value="${item.quantity * item.product.price}" />
                                                        ${itemTotal}
                                                        <c:set var="cartTotal" value="${cartTotal + itemTotal}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td colspan="3">Total</td>
                                                <td><strong>${cartTotal}</strong></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="row" style="height: 50px; vertical-align: middle;">
                     
                                    <div class="text-right col-sm-3 ">
                                        <button type="submit" class="btn btn-primary" >Button</button>
                                    </div>
                                </div>          
                            </div>               
                        </div>
                    </div>



                </div>
            </section>
        </form>

       
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>






<div class="container mt-4">
    <h2>My Orders</h2>
    <p>${message}</p>
    <p>${orderInformation}</p>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>STT</th>
                <th>Product</th>
                <th>Size, Color</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty orderDetailList}">
                    <c:forEach var="order" items="${orderDetailList}">
                        <tr>
                            <td><c:out value="${order[0].orderdetailId}" /></td>
                            <td><c:out value="${order[1].name}" /></td>
                            <td><c:out value="${order[2]}" /></td>
                            <td><c:out value="${order[0].quantity}" /></td>
                            <td><c:out value="${order[0].price}" /></td>
                            
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6" style="text-align: center; font-weight: bold;">No items found</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>

    </table>
</div>



