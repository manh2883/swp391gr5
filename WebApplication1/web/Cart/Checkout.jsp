
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
        <form action="Checkout" id="checkoutForm" method="post" onsubmit="return validateForm(event)">
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
                            <div class="col-sm-4 clearfix" >
                                <div class="bill-to">
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
                                    <div class="payment-options col-sm-9">
                                        <span>
                                            <label><input type="radio" name="paymentMethod" value="1" required> Bank Transfer</label>
                                        </span>
                                        <span>
                                            <label><input type="radio" name="paymentMethod" value="2" required> COD</label>
                                        </span>
                                    </div>

                                    <!-- Nút xác nhận đơn hàng -->
                                    <div class="text-right col-sm-3 ">
                                        <button type="submit" class="btn btn-primary" name="action" value="confirmOrder">Confirm Order</button>
                                    </div>
                                </div>          
                            </div>               
                        </div>
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

            document.addEventListener("DOMContentLoaded", function () {
                const nameInput = document.getElementById("nameInput");
                const contactInput = document.getElementById("contactInput");
                const addressSelect = document.getElementById("address");
                const newAddressInput = document.getElementById("newAddress");
                const paymentMethods = document.querySelectorAll("input[name='paymentMethod']");
                const confirmButton = document.querySelector("button[name='action'][value='confirmOrder']");
                const noteInput = document.getElementById("orderNote");
                function validateName() {
                    const name = nameInput.value.trim();
                    const errorElement = document.getElementById("nameError");
                    if (name === "") {
                        errorElement.textContent = "Name cannot be empty.";
                        return false;
                    } else if (name.length > 50) {
                        errorElement.textContent = "Name cannot exceed 50 characters.";
                        return false;
                    } else {
                        errorElement.textContent = "";
                        return true;
                    }
                }

                function validateContact() {
                    const contact = contactInput.value.trim();
                    const errorElement = document.getElementById("contactError");
                    const phoneRegex = /^(0[2-9]{1}[0-9]{8,9})$/;
                    if (contact === "") {
                        errorElement.textContent = "Contact cannot be empty.";
                        return false;
                    } else if (contact.length > 50) {
                        errorElement.textContent = "Contact cannot exceed 50 characters.";
                        return false;
                    } else if (!phoneRegex.test(contact)) {
                        errorElement.textContent = "Invalid Vietnamese phone number format.";
                        return false;
                    } else {
                        errorElement.textContent = "";
                        return true;
                    }
                }

                function validateAddress() {
                    const selectedValue = addressSelect.value;
                    const errorElement = document.getElementById("addressError");
                    let isValid = true;
                    if (selectedValue === "") {
                        errorElement.textContent = "Please select an address.";
                        isValid = false;
                    } else if (selectedValue === "Other") {
                        const newAddress = newAddressInput.value.trim();
                        if (newAddress === "") {
                            errorElement.textContent = "New address cannot be empty.";
                            isValid = false;
                        } else if (newAddress.length > 255) {
                            errorElement.textContent = "New address cannot exceed 255 characters.";
                            isValid = false;
                        } else {
                            errorElement.textContent = "";
                        }
                    } else {
                        errorElement.textContent = "";
                    }
                    return isValid;
                }

                function validatePaymentMethod() {
                    const errorElement = document.getElementById("paymentMethodError") || document.createElement("div");
                    errorElement.id = "paymentMethodError";
                    errorElement.classList.add("text-danger");
                    if (!document.querySelector("input[name='paymentMethod']:checked")) {
                        errorElement.textContent = "Please select a payment method.";
                        if (!document.getElementById("paymentMethodError")) {
                            document.querySelector(".payment-options").appendChild(errorElement);
                        }
                        return false;
                    } else {
                        errorElement.textContent = "";
                        return true;
                    }
                }

                function validateOrderNote() {
                    const selectedValue = noteInput.value;
                    const errorElement = document.getElementById("orderNoteError");
                    let isValid = true;
                    if (selectedValue.length > 255) {
                        errorElement.textContent = "Note is too long.";
                        isValid = false;
                    } else {
                        errorElement.textContent = "";
                    }

                    return isValid;
                }

                function validateForm() {
                    const isNameValid = validateName();
                    const isContactValid = validateContact();
                    const isAddressValid = validateAddress();
                    const isPaymentValid = validatePaymentMethod();
                    const isNoteValid = validateOrderNote();
                    confirmButton.disabled = !(isNameValid && isContactValid && isAddressValid && isPaymentValid && isNoteValid);
                }

                nameInput.addEventListener("input", validateForm);
                contactInput.addEventListener("input", validateForm);
                addressSelect.addEventListener("change", validateForm);
                newAddressInput.addEventListener("input", validateForm);
                noteInput.addEventListener("input", validateForm);
                paymentMethods.forEach(method => method.addEventListener("change", validateForm));

                validateForm();
            });
            fetch("CheckoutServlet", {
                method: "POST",
                body: formData
            })

//            document.querySelector("form").addEventListener("submit", validateForm);
        </script>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
