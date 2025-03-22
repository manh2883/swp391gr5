<%-- 
    Document   : Cart
    Created on : Feb 7, 2025, 5:33:55 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
    public String formatCurrency(double amount) {
        return String.format("%.2f", amount);
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Cart</title>
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
                width: 121px;
                height: 112px;
                object-fit: contain; /* Giữ nguyên tỷ lệ, có thể có khoảng trắng */
                background-color: #f8f8f8; /* Màu nền cho khoảng trống */
            }
            .tr-button{
                text-align: center; /* Căn giữa nội dung văn bản */
                vertical-align: middle;
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
                        <li class="active">Shopping Cart</li>
                    </ol>
                </div>
                <div class="table-responsive cart_info">
                    <!-- Form cho các hành động cập nhật giỏ hàng -->
                    <form action="ViewCart" method="get" id="cartForm">
                        <input type="hidden" name="cartDetailID" id="cartDetailID">
                        <input type="hidden" name="action" id="action">
                    </form>

                    <!-- Form cho hành động Checkout -->
                    <form action="ViewCart" method="post" id="checkoutForm"></form>

                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="select " style="text-align: center; /* Căn giữa nội dung văn bản */
                                    vertical-align: middle;">
                                    <input type="checkbox" id="selectAll"  style="width: 16px; height: 16px; border-radius: 0px"  /></td>
                                <td class="image"></td>
                                <td class="description">Item</td>
                                <td class="price">Price</td>
                                <td class="quantity">Quantity</td>
                                <td class="total">Total</td>
                                <td ></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty cartDetailList}">
                                    <c:set var="totalPrice" value="0" scope="page" />
                                    <c:forEach var="cart" items="${cartDetailList}">
                                        <tr>
                                            <td class="select " style="text-align: center; /* Căn giữa nội dung văn bản */
                                                vertical-align: middle;">
                                                <input type="checkbox" name="selectedItems" 
                                                       style="width: 16px; height: 16px; border-radius: 0px" 
                                                       value="${cart[0].cartDetailID}" class="itemCheckbox" />
                                            </td>
                                            <td class="cart_product">
                                                <a href="${pageContext.request.contextPath}/ProductDetail?productId=${cart[0].productID}">
                                                    <img class="product-img"
                                                                src="${pageContext.request.contextPath}/${cart[1]}" alt="${cart[5]}"></a>
                                            </td>
                                            <td class="cart_description">
                                                <h4>
                                                    <a href="${pageContext.request.contextPath}/ProductDetail?productId=${cart[0].productID}" 
                                                       style="padding-bottom:10px">
                                                        ${cart[5]}
                                                    </a>
                                                </h4>
                                                <p>${cart[3]}</p>
                                                <!--<p>In stock: ${cart[4]}</p>-->
                                                <p></p>
                                            </td>
                                            <td class="cart_price">
                                                <p>${cart[2].intValue()}</p>
                                            </td>
                                            <td class="cart_quantity">
                                                <div class="cart_quantity_button d-flex flex-row search_box" style="width: 35px; height: 35px;">
                                                    <button type="button" class="btn" style="width: 35px; height: 35px; border-radius: 0px" 
                                                            onclick="submitCartForm(${cart[0].cartDetailID}, 'decrement')"> - </button>

                                                    <input class="" type="text" name="quantity" value="${cart[0].quantity}" autocomplete="off" size="2" readonly>

                                                    <button type="button" class="btn" style="width: 35px; height: 35px; border-radius: 0px" 
                                                            onclick="submitCartForm(${cart[0].cartDetailID}, 'increment')"> + </button>  
                                                </div>
                                            </td>
                                            <td class="cart_total">
                                                <c:set var="itemTotal" value="${cart[0].quantity * cart[2].intValue()}" />
                                                <p class="cart_total_price">${itemTotal}</p>
                                            </td>
                                            <td class="cart_delete tr-button">
                                                <button type="button" class="cart_quantity_delete btn" 
                                                        onclick="submitCartForm(${cart[0].cartDetailID}, 'delete')">
                                                    <i class="fa fa-times"></i>
                                                </button>                               
                                            </td>
                                        </tr>
                                        <c:set var="totalPrice" value="${totalPrice + itemTotal}" />
                                    </c:forEach>
                                    <tr>
                                        <td colspan="5" style="text-align: right;">Cart Value:</td>
                                        <td class="cart_total">
                                            <p class="cart_total_price">0
                                                <!--${totalPrice.intValue()}-->
                                            </p>
                                        </td>
                                        <td></td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="7" style="text-align: center;">No items in your cart.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>        

                        </tbody>
                    </table>

                    <div style="text-align: right; margin-top: 20px; margin-right: 5vh; margin-bottom: 10vh">
                        <button type="button" class="btn btn-primary" id="checkoutButton" onclick="submitCheckout()" disabled>Checkout</button>

                    </div>

                </div>
        </section> <!--/#cart_items-->
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script>
                            $(document).ready(function () {
                                $(".itemCheckbox, #selectAll").change(function () {
                                    updateCartTotal();
                                });

                                function updateCartTotal() {
                                    let total = 0;
                                    $(".itemCheckbox:checked").each(function () {
                                        let row = $(this).closest("tr");
                                        let itemTotal = parseFloat(row.find(".cart_total_price").text());
                                        total += itemTotal;
                                    });
                                    $(".cart_total_price:last").text(total);
                                    $("#checkoutButton").prop("disabled", total === 0);
                                }

                                $("#selectAll").click(function () {
                                    $(".itemCheckbox").prop("checked", this.checked).trigger("change");
                                });
                            });

                            document.addEventListener("DOMContentLoaded", function () {
                                const checkboxes = document.querySelectorAll('.itemCheckbox');
                                const checkoutButton = document.getElementById('checkoutButton');
                                const selectAll = document.getElementById('selectAll');

                                function updateCheckoutButton() {
                                    checkoutButton.disabled = !document.querySelector('.itemCheckbox:checked');
                                }

                                checkboxes.forEach(checkbox => {
                                    checkbox.addEventListener('change', updateCheckoutButton);
                                });

                                if (selectAll) {
                                    selectAll.addEventListener('change', function () {
                                        checkboxes.forEach(checkbox => checkbox.checked = selectAll.checked);
                                        updateCheckoutButton();
                                    });
                                }
                            });

                            function submitCartForm(cartDetailID, action) {
                                document.getElementById('cartDetailID').value = cartDetailID;
                                document.getElementById('action').value = action;
                                document.getElementById('cartForm').submit();
                            }

                            function submitCheckout() {
                                const selectedItems = document.querySelectorAll('.itemCheckbox:checked');
                                if (selectedItems.length === 0) {
                                    alert("Please select at least one item to checkout.");
                                    return;
                                }

                                const checkoutForm = document.getElementById('checkoutForm');
                                checkoutForm.innerHTML = ""; // Xóa input cũ nếu có

                                selectedItems.forEach(item => {
                                    let input = document.createElement("input");
                                    input.type = "hidden";
                                    input.name = "selectedItems"; // Servlet sẽ nhận mảng `selectedItems[]`
                                    input.value = item.value;
                                    checkoutForm.appendChild(input);
                                });

                                checkoutForm.submit();
                            }

                            document.querySelectorAll('.itemCheckbox').forEach(checkbox => {
                                checkbox.addEventListener('change', function () {
                                    let selected = document.querySelectorAll('.itemCheckbox:checked').length;
                                    if (selected >= 5) {
                                        document.querySelectorAll('.itemCheckbox:not(:checked)').forEach(cb => cb.disabled = true);
                                    } else {
                                        document.querySelectorAll('.itemCheckbox').forEach(cb => cb.disabled = false);
                                    }
                                });
                            });


                            document.addEventListener("DOMContentLoaded", function () {
                                document.querySelectorAll(".cart_quantity_button").forEach(container => {
                                    const plusButton = container.querySelector("button:last-child");
                                    const quantityInput = container.querySelector("input[name='quantity']");
                                    const row = container.closest("tr");
                                    const stockText = row.querySelector(".cart_description p:nth-child(3)");
                                    const checkbox = row.querySelector('input[type="checkbox"]'); // Tìm checkbox trong hàng đó

                                    // Lưu giá trị gốc của số lượng tồn kho
                                    if (!stockText.dataset.originalText) {
                                        stockText.dataset.originalText = stockText.textContent;
                                    }

                                    // Lấy số lượng tồn kho từ nội dung ban đầu
                                    const stockMatch = stockText.dataset.originalText.match(/\d+/);
                                    const stock = stockMatch ? parseInt(stockMatch[0], 10) : 0;
                                    const maxQuantity = ${maxQuan};
                                    console.log(maxQuantity);
                                    function updateButtonState() {
                                        const quantity = parseInt(quantityInput.value, 10);

                                        if (quantity >= maxQuantity) {
                                            plusButton.disabled = true;
                                            stockText.textContent = "Bạn đã đạt giới hạn số lượng";
                                            stockText.style.color = "red";
                                            if (checkbox) {
                                                checkbox.disabled = true; // Disable checkbox
                                            }
                                        } else {
                                            plusButton.disabled = false;
                                            stockText.textContent = stockText.dataset.originalText; // Khôi phục nội dung gốc
                                            stockText.style.color = "black";
                                        }
                                    }

                                    // Kiểm tra ngay khi tải trang
                                    updateButtonState();

                                    // Theo dõi sự thay đổi của input số lượng
                                    const observer = new MutationObserver(updateButtonState);
                                    observer.observe(quantityInput, {attributes: true, childList: true, characterData: true});
                                });
                            });
        </script>

        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
