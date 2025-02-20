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
                    <form action="${pageContext.request.contextPath}/Checkout" method="get">
                        <table class="table table-condensed">
                            <!-- Bắt đầu form -->
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
                                                <!-- Cột checkbox -->
                                                <td class="select">
                                                    <input type="checkbox" name="selectedItems" value="${cart.cartDetailID}" class="itemCheckbox" />
                                                </td>
                                                <!-- Các cột khác -->
                                                <td class="cart_product">
                                                    <a href=""><img src="${pageContext.request.contextPath}/Images/cart/${cart.productID}.png" alt=""></a>
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
                                                    <div class="cart_quantity_button">
                                                        <form action="ViewCart" method="get" style="display:inline;">
                                                            <input type="hidden" name="cartDetailID" value="${cart.cartDetailID}">
                                                            <input type="hidden" name="action" value="increment">
                                                            <button type="submit" class="cart_quantity_up"> + </button>
                                                        </form>

                                                        <input class="quantity-input" type="text" name="quantity" value="${cart.quantity}" autocomplete="off" size="2" readonly>

                                                        <form action="ViewCart" method="get" style="display:inline;">
                                                            <input type="hidden" name="cartDetailID" value="${cart.cartDetailID}">
                                                            <input type="hidden" name="action" value="decrement">
                                                            <button type="submit" class="cart_quantity_down"> - </button>
                                                        </form>
                                                    </div>
                                                </td>
                                                <!-- Tính và hiển thị tổng giá cho sản phẩm này -->
                                                <td class="cart_total">
                                                    <c:set var="itemTotal" value="${cart.quantity * cart.product.price}" />
                                                    <p class="cart_total_price">${itemTotal}</p>
                                                </td>
                                                <td class="cart_delete">
                                                    <form action="${pageContext.request.contextPath}/ViewCart" method="get">
                                                        <input type="hidden" name="cartDetailID" value="${cart.cartDetailID}">
                                                        <input type="hidden" name="action" value="delete">
                                                        <button type="submit" class="cart_quantity_delete"><i class="fa fa-times"></i></button>
                                                    </form>
                                                </td>
                                            </tr>
                                            <!-- Cộng dồn tổng giá trị giỏ hàng -->
                                            <c:set var="totalPrice" value="${totalPrice + itemTotal}" />
                                        </c:forEach>
                                        <!-- Hiển thị tổng giá trị giỏ hàng -->
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
                        <!-- Nút Checkout -->
                        <div style="text-align: right; margin-top: 20px;">
                            <button type="submit" class="btn btn-primary">Checkout</button>
                        </div>
                    </form> 
                    <!-- Kết thúc form -->
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

        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
