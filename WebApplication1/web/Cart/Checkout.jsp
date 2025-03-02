
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">Check out</li>
                    </ol>
                </div><!--/breadcrums-->

                <div class="shopper-informations">
                    <div class="row">
                        <div class="col-sm-5 clearfix">
                            <div class="bill-to">
                                <p>Bill To</p>
                                <div class="form-one">
                                    <p>${message1}</p>
                                    <p>${message2}</p>
                                    <form>
                                        <input type="text" name="email" placeholder="Email*" value="${user.email}" required>
                                        <input type="text" name="firstName" placeholder="First Name*" value="${user.firstName}" required>
                                        <input type="text" name="lastName" placeholder="Last Name*" value="${user.lastName}" required>
                                    </form>
                                </div>
                                <div class="form-two">
                                    <form>
                                        <select name="address" id="address" class="form-control" onchange="toggleAddressInput(this)">
                                            <option value="">-- Select Address --</option>
                                            <c:forEach var="addr" items="${userAddresses}">
                                                <option value="${addr.addressLine}">${addr.addressLine}</option>
                                            </c:forEach>
                                            <option value="Other">Other</option>
                                        </select>
                                        <input type="text" name="newAddress" id="newAddress" class="form-control mt-2"
                                               placeholder="Enter new address" style="display:none;" />
                                        <input type="text" name="phone" placeholder="Phone *" value="${user.phoneNumber}" required>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="order-message">
                                <p>Shipping Order</p>
                                <textarea name="message"  placeholder="Notes about your order, Special Notes for Delivery" rows="8"></textarea>
                                <label><input type="checkbox"> Shipping to bill address</label>
                            </div>	
                        </div>					
                    </div>
                </div>
                <div class="review-payment">
                    <h2>Review & Payment</h2>
                </div>

                <div class="table-responsive cart_info">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Item</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${checkoutItems}">
                                <tr>
                                    <td>${item.product.name}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.product.price}</td>
                                    <td>${item.quantity * item.product.price}</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="3">Cart Sub Total</td>
                                <td>${cartTotal}</td>
                            </tr>
                            <tr>
                                <td colspan="3">Total (Shipping 20 included)</td>
                                <td><strong>${cartTotal + 20}</strong></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="payment-options">
                    <span>
                        <label><input type="radio" name="paymentMethod" value="BankTransfer" required> Bank Transfer</label>
                    </span>
                    <span>
                        <label><input type="radio" name="paymentMethod" value="QR"> QR Payment</label>
                    </span>
                </div>
                <!-- Nút xác nhận đơn hàng -->
                <div class="text-right mt-3">
                    <button type="submit" class="btn btn-primary" name="action" value="confirmOrder">Confirm Order</button>
                </div>
        </section> <!--/#cart_items-->
        <script>
            // Load trạng thái thanh toán khi F5
            document.addEventListener("DOMContentLoaded", function () {
                let savedPayment = sessionStorage.getItem("selectedPayment");
                if (savedPayment) {
                    document.querySelector(`input[name="paymentMethod"][value="${savedPayment}"]`).checked = true;
                }
            });

            // Lưu lại trạng thái khi chọn
            document.querySelectorAll('input[name="paymentMethod"]').forEach(input => {
                input.addEventListener("change", function () {
                    sessionStorage.setItem("selectedPayment", this.value);
                });
            });

            function toggleAddressInput(selectElement) {
                let newAddressInput = document.getElementById("newAddress");
                if (selectElement.value === "Other") {
                    newAddressInput.style.display = "block";
                    newAddressInput.required = true;
                } else {
                    newAddressInput.style.display = "none";
                    newAddressInput.required = false;
                }
            }
        </script>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
