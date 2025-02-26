
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
                <!-- Breadcrumbs -->
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="home.jsp">Trang chủ</a></li>
                        <li class="active">Thanh toán</li>
                    </ol>
                </div>

                <div class="checkout-informations">
                    <div class="row">
                        <!-- Thông tin hóa đơn -->
                        <div class="col-sm-7 clearfix">
                            <div class="bill-to">
                                <p>Thông tin hóa đơn</p>
                                <form action="SaveCheckoutDetails" method="post">                                   
                                    <div class="form-two">
                                        <label>Chọn địa chỉ:</label>
                                        <select name="addressId" id="addressDropdown" onchange="updateAddressFields()">
                                            <option value="new">Thêm địa chỉ mới</option>
                                            <c:forEach var="addr" items="${user.addressList}">
                                                <option value="${addr.id}" 
                                                        data-address1="${addr.addressLine1}"
                                                        data-address2="${addr.addressLine2}">
                                                    ${addr.displayName}
                                                </option>
                                            </c:forEach>
                                        </select>

                                        <input type="text" id="address1" name="address1" placeholder="Địa chỉ 1 *" required>
                                        <textarea name="notes" placeholder="Ghi chú về đơn hàng của bạn" rows="3"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-default">Tiến hành thanh toán</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Xem lại & Thanh toán -->
                <div class="review-payment">
                    <h2>Xem lại & Thanh toán</h2>
                </div>

                <!-- Thông tin giỏ hàng -->
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Sản phẩm</td>
                                <td class="description"></td>
                                <td class="price">Giá</td>
                                <td class="quantity">Số lượng</td>
                                <td class="total">Tổng</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td class="cart_product">
                                        <a href=""><img src="${item.imageUrl}" alt=""></a>
                                    </td>
                                    <td class="cart_description">
                                        <h4><a href="">${item.name}</a></h4>
                                        <p>Mã sản phẩm: ${item.productId}</p>
                                    </td>
                                    <td class="cart_price">
                                        <p>${item.price}</p>
                                    </td>
                                    <td class="cart_quantity">
                                        <div class="cart_quantity_button">
                                            <a class="cart_quantity_up" href="UpdateCart?productId=${item.productId}&action=increment"> + </a>
                                            <input class="cart_quantity_input" type="text" name="quantity" value="${item.quantity}" autocomplete="off" size="2" disabled>
                                            <a class="cart_quantity_down" href="UpdateCart?productId=${item.productId}&action=decrement"> - </a>
                                        </div>
                                    </td>
                                    <td class="cart_total">
                                        <p class="cart_total_price">$${item.totalPrice}</p>
                                    </td>
                                    <td class="cart_delete">
                                        <a class="cart_quantity_delete" href="RemoveFromCart?productId=${item.productId}"><i class="fa fa-times"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="4">&nbsp;</td>
                                <td colspan="2">
                                    <table class="table table-condensed total-result">
                                        <tr>
                                            <td>Tổng phụ</td>
                                            <td>${cartSubTotal}</td>
                                        </tr>
                                        <tr>
                                            <td>Thuế</td>
                                            <td>${tax}</td>
                                        </tr>
                                        <tr class="shipping-cost">
                                            <td>Phí vận chuyển</td>
                                            <td>Miễn phí</td>
                                        </tr>
                                        <tr>
                                            <td>Tổng cộng</td>
                                            <td><span>${grandTotal}</span></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Tùy chọn thanh toán -->
                <div class="payment-options">
                    <span>
                        <label><input type="radio" name="paymentMethod" value="checkPayment"> Thanh toán bằng séc</label>
                    </span>
                    <span>
                        <label><input type="radio" name="paymentMethod" value="creditCard"> Thẻ tín dụng</label>
                    </span>
                    <span>
                        <label><input type="radio" name="paymentMethod" value="paypal" checked> PayPal</label>
                    </span>
                </div>

                <script>
                    function updateAddressFields() {
                        var dropdown = document.getElementById("addressDropdown");
                        var selectedOption = dropdown.options[dropdown.selectedIndex];

                        if (selectedOption.value === "new") {
                            document.getElementById("address1").value = "";
                            document.getElementById("address2").value = "";
                        } else {
                            document.getElementById("address1").value = selectedOption.getAttribute("data-address1");
                            document.getElementById("address2").value = selectedOption.getAttribute("data-address2");
                        }
                    }
                </script>


        </section>
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
