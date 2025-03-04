
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
        <form action="Checkout" method="post">
            <section id="cart_items">
                <div class="container">
                    <div class="breadcrumbs">
                        <ol class="breadcrumb">
                            <li><a href="#">Home</a></li>
                            <li class="active">Check out</li>
                        </ol>
                    </div>
                    <p style="color: red">${message}</p>
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-sm-5 clearfix">
                                <div class="bill-to">
                                    <p>Bill To</p>
                                    <div class="form-one">
<!--                                        <input type="text" name="email" placeholder="Email*" value="${user.email}" required>-->
                                        <input type="text" name="name" placeholder="Name*" value="${user.firstName} ${user.lastName}" required>
                                    </div>
                                    <div class="form-two">
                                        <select name="address" id="address" class="form-control" onchange="toggleAddressInput(this)">
                                            <option value="">-- Select Address --</option>
                                            <c:forEach var="addr" items="${userAddresses}">
                                                <option value="${addr.addressLine}">${addr.addressLine}</option>
                                            </c:forEach>
                                            <option value="Other">Other</option>
                                        </select>
                                        <input type="text" name="newAddress" id="newAddress" class="form-control mt-2"
                                               placeholder="Enter new address" style="display:none;" />
                                        <input type="text" name="contact" placeholder="Contact*" value="${user.phoneNumber}" required>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="order-message">
                                    <p>Shipping Order</p>
                                    <textarea name="orderNote" placeholder="Notes about your order, Special Notes for Delivery" rows="8"></textarea>
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

                    <div class="payment-options">
                        <span>
                            <label><input type="radio" name="paymentMethod" value="1" required> Bank Transfer</label>
                        </span>
                        <span>
                            <label><input type="radio" name="paymentMethod" value="1" required> QR Payment</label>
                        </span>
                    </div>

                    <!-- Nút xác nhận đơn hàng -->
                    <div class="text-right mt-3">
                        <button type="submit" class="btn btn-primary" name="action" value="confirmOrder">Confirm Order</button>
                    </div>
                </div>
            </section>
        </form>

        <script>
            // Load trạng thái thanh toán khi F5
            document.addEventListener("DOMContentLoaded", function () {
                // Giữ trạng thái phương thức thanh toán sau khi F5
                const savedPayment = localStorage.getItem("selectedPayment");
                if (savedPayment) {
                    document.querySelector(`input[name="paymentMethod"][value="${savedPayment}"]`).checked = true;
                }
            });

            // Lưu lựa chọn vào localStorage khi thay đổi
            document.querySelectorAll("input[name='paymentMethod']").forEach(radio => {
                radio.addEventListener("change", function () {
                    localStorage.setItem("selectedPayment", this.value);
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
            function updateCheckoutTotal() {
                let total = 0;
                document.querySelectorAll(".checkout-item").forEach(row => {
                    let price = parseFloat(row.querySelector(".item-price").innerText.replace(/[^0-9.]/g, "")) || 0;
                    let quantity = parseInt(row.querySelector(".item-quantity").innerText) || 0;
                    total += price * quantity;
                });

                // Cập nhật tổng vào phần Cart Sub Total
                document.getElementById("checkoutTotal").innerText = total.toLocaleString("en-US", {style: "currency", currency: "USD"});
            }

            // Tính lại tổng khi trang tải
            document.addEventListener("DOMContentLoaded", updateCheckoutTotal);
        </script>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
