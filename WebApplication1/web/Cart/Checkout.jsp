
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
                                                        data-name="${addr.name}"
                                                        data-email="${addr.email}"
                                                        data-phone="${addr.phone}"
                                                        data-address1="${addr.addressLine1}"
                                                        data-address2="${addr.addressLine2}">
                                                    ${addr.displayName}
                                                </option>
                                            </c:forEach>
                                        </select>

                                        <label>Họ và tên:</label>
                                        <input type="text" id="name" name="name" placeholder="Họ và tên *" required>

                                        <label>Email:</label>
                                        <input type="email" id="email" name="email" placeholder="Email *" required>

                                        <label>Số điện thoại:</label>
                                        <input type="text" id="phone" name="phone" placeholder="Số điện thoại *" required>

                                        <label>Địa chỉ 1:</label>
                                        <input type="text" id="address1" name="address1" placeholder="Địa chỉ 1 *" required>

                                        <label>Ghi chú:</label>
                                        <textarea name="notes" placeholder="Ghi chú về đơn hàng của bạn" rows="3"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-default">Tiến hành thanh toán</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- Tùy chọn thanh toán -->
                <div class="payment-options">
                    <span>
                        <label><input type="radio" name="paymentMethod" value="creditCard"> Thẻ tín dụng</label>
                    </span>
                    <span>
                        <label><input type="radio" name="paymentMethod" value="paypal" checked> QR</label>
                    </span>
                </div>

                <script>
                    function updateAddressFields() {
                        var dropdown = document.getElementById("addressDropdown");
                        var selectedOption = dropdown.options[dropdown.selectedIndex];

                        if (selectedOption.value === "new") {
                            // Xóa dữ liệu để nhập mới
                            document.getElementById("name").value = "";
                            document.getElementById("email").value = "";
                            document.getElementById("phone").value = "";
                            document.getElementById("address1").value = "";
                        } else {
                            // Lấy dữ liệu từ option được chọn
                            document.getElementById("name").value = selectedOption.getAttribute("data-name");
                            document.getElementById("email").value = selectedOption.getAttribute("data-email");
                            document.getElementById("phone").value = selectedOption.getAttribute("data-phone");
                            document.getElementById("address1").value = selectedOption.getAttribute("data-address1");
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
