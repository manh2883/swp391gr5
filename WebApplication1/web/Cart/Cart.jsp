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
                    <form action="Checkout" method="post" id="checkoutForm">
                        <input type="hidden" name="selectedItems" id="selectedItems">
                    </form>

                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="select"><input type="checkbox" id="selectAll" /></td>
                                <td class="image">Item</td>
                                <td class="description"></td>
                                <td class="price">Price</td>
                                <td class="quantity">Quantity</td>
                                <td class="total">Total</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty cartDetails}">
                                    <c:set var="totalPrice" value="0" scope="page" />
                                    <c:forEach var="cart" items="${cartDetails}">
                                        <tr>
                                            <td class="select">
                                                <input type="checkbox" name="selectedItems" value="${cart.cartDetailID}" class="itemCheckbox" />
                                            </td>
                                            <td class="cart_product">
                                                <a href=""><img src="${pageContext.request.contextPath}/${cart.product.imgUrl}" alt=""></a>
                                            </td>
                                            <td class="cart_description">
                                                <h4><a href="">Product ${cart.productID}</a></h4>
                                                <p>Web ID: ${cart.cartDetailID}</p>
                                                <c:if test="${cart.productVariantID > 0}">
                                                    <p>Variant: ${cart.productVariantID}</p>
                                                </c:if>
                                            </td>
                                            <td class="cart_price">
                                                <p>${cart.product.price}</p>
                                            </td>
                                            <td class="cart_quantity">
                                                <div class="cart_quantity_button d-flex flex-row search_box" style="width: 35px; height: 35px;">
                                                    <button type="button" class="btn" style="width: 35px; height: 35px; border-radius: 0px" 
                                                            onclick="submitCartForm(${cart.cartDetailID}, 'decrement')"> - </button>
                                                    <input class="" type="text" name="quantity" value="${cart.quantity}" autocomplete="off" size="2" readonly>
                                                    <button type="button" class="btn" style="width: 35px; height: 35px; border-radius: 0px" 
                                                            onclick="submitCartForm(${cart.cartDetailID}, 'increment')"> + </button>
                                                </div>
                                            </td>
                                            <td class="cart_total">
                                                <c:set var="itemTotal" value="${cart.quantity * cart.product.price}" />
                                                <p class="cart_total_price">${itemTotal}</p>
                                            </td>
                                            <td class="cart_delete">
                                                <button type="button" class="cart_quantity_delete btn" 
                                                        onclick="submitCartForm(${cart.cartDetailID}, 'delete')">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <c:set var="totalPrice" value="${totalPrice + itemTotal}" />
                                    </c:forEach>
                                    <tr>
                                        <td colspan="5" style="text-align: right;">Tổng cộng:</td>
                                        <td class="cart_total">
                                            <p class="cart_total_price">${totalPrice}</p>
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

                    <div style="text-align: right; margin-top: 20px;">
                        <button type="button" class="btn btn-primary" onclick="submitCheckout()">Checkout</button>
                    </div>

                </div>
        </section> <!--/#cart_items-->


        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script>
                            document.getElementById('selectAll').onclick = function () {
                                var checkboxes = document.getElementsByClassName('itemCheckbox');
                                for (var checkbox of checkboxes) {
                                    checkbox.checked = this.checked;
                                }
                            };
        </script>

        <script>
            function submitCartForm(cartDetailID, action) {
                document.getElementById('cartDetailID').value = cartDetailID;
                document.getElementById('action').value = action;
                document.getElementById('cartForm').submit();
            }

            function submitCheckout() {
                const selectedItems = document.querySelectorAll('.itemCheckbox:checked');
                let selectedValues = [];

                selectedItems.forEach(item => {
                    selectedValues.push(item.value);
                });

                if (selectedValues.length === 0) {
                    alert("Please select at least one item to checkout.");
                    return;
                }

                document.getElementById('selectedItems').value = selectedValues.join(",");
                document.getElementById('checkoutForm').submit();
            }
        </script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
