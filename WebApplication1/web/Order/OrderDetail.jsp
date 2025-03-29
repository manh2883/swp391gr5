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
        <title>${title}</title>
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
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap JS (bắt buộc để modal hoạt động) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


        <style>

            .productinfo .btn {
                display: inline-block; /* Đảm bảo các nút được xếp thành dòng ngang */
            }
            .status-1 { /* Pending */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #fff3cd;
                color: #856404;

            }

            .status-2 { /* Accepted */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #d4edda;
                color: #155724;
            }

            .status-3 { /* Shipping */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #cce5ff;
                color: #004085;
            }

            .status-4 { /* Delivered */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #d1ecf1;
                color: #0c5460;
            }

            .status-5 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .status-6 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .status-7 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .status-8 { /* Received */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #cce5ff; /* Màu xanh nhạt */
                color: #004085; /* Màu xanh đậm */
            }

            .default-status {
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #e2e3e5;
                color: #383d41;
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                height: 20%;
                width: 30%;
                background-color: rgba(0,0,0,0.4);
            }
            .modal-content {
                background-color: white;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 20%;
                text-align: center;
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
                        <li><a href="${pageContext.request.contextPath}/Home">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/${breadcumbLink}">${breadcumb}</a></li>
                        <li class="active">Order: ${orderInformation.orderId}</li>
                    </ol>
                </div>

                <div class="shopper-informations">
                    <div class="row">
                        <div class="col-sm-4 clearfix" >
                            <div class="bill-to">

                                <p>Bill To</p>
                                <div class="form-floating mb-10">
                                    <input id="nameInput" class="form-control" type="text" name="name" style="height: 45px; margin-bottom: 10px"
                                           placeholder="Name*" value="${orderInformation.userReceive}" readonly >
                                    <label for="nameInput">Receiver:</label>
                                    <div class="text-danger" id="nameError"></div>
                                </div>

                                <div class="form-floating mb-10">
                                    <input id="contactInput" class="form-control" type="text" name="contact" style="height: 45px; margin-bottom: 10px"
                                           placeholder="Contact*" value="${orderInformation.contact}" readonly>
                                    <label for="contactInput">Contact:</label>
                                    <div class="text-danger" id="contactError"></div>
                                </div>    

                                <div class="form-floating mb-10">
                                    <input id="contactInput" class="form-control" type="text" name="contact" style="height: 45px; margin-bottom: 10px"
                                           placeholder="Address*" value="${orderInformation.address}" readonly>
                                    <label for="adress">Address</label>
                                    <div class="text-danger" id="addressError"></div>
                                </div>  


                                <div class="form-floating mb-10">
                                    <table>
                                        <tbody>
                                            <tr style="visibility: hidden">
                                                <td style="width: 50%" >

                                                </td>
                                                <td style="width: 50%" >

                                                </td>
                                            </tr>
                                            <tr >
                                                <td class="text-start">
                                                    <span style="font-weight:  bold;font-size:17px ">Created At: </span>
                                                </td>
                                                <td class="text-start">
                                                    <span style="font-size: 15px">${orderInformation.createAt}</span>
                                                </td>
                                            </tr>
                                            <tr >
                                                <td class="text-start">
                                                    <span style="font-weight:  bold;font-size:17px ">Completed At: </span>
                                                </td>
                                                <td class="text-start">
                                                    <c:choose>
                                                        <c:when test="${not empty orderInformation.completedAt and orderInformation.completedAt != 'null'}">
                                                            <span style="font-size: 15px">${orderInformation.completedAt}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span>N/A</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr >
                                                <td class="text-start">
                                                    <span style="font-weight:  bold;font-size:17px ">Payment method: </span>
                                                </td>
                                                <td class="text-start">
                                                    <c:choose>
                                                        <c:when test="${not empty orderInformation.paymentmethod and orderInformation.paymentmethod ne null}">
                                                            <c:choose>
                                                                <c:when test="${orderInformation.paymentmethod == 1}">
                                                                    <span style="font-size: 15px">Online Banking</span>
                                                                </c:when>
                                                                <c:when test="${orderInformation.paymentmethod == 2}">
                                                                    <span style="font-size: 15px">COD</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="font-size: 15px">Unknown</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <tr >
                                                <td class="text-start">
                                                    <span style="font-weight:  bold;font-size:17px ">Payment Status: </span>
                                                </td>
                                                <td class="text-start">
                                                    <c:choose>
                                                        <c:when test="${not empty orderInformation.paymentStatus and orderInformation.paymentStatus ne null}">
                                                            <c:choose>
                                                                <c:when test="${orderInformation.paymentStatus == 1}">
                                                                    <span style="font-size: 15px">Paid</span>
                                                                </c:when>
                                                                <c:when test="${orderInformation.paymentStatus == 2}">
                                                                    <span style="font-size: 15px">Unpaid</span>
                                                                </c:when>
                                                                <c:when test="${orderInformation.paymentStatus == 3}">
                                                                    <span style="font-size: 15px">Refunded</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="font-size: 15px">Unknown</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>                                                                   
                                </div>  

                                <div class="order-message">
                                    <p>Shipping Order</p>

                                    <textarea name="orderNote" id="orderNote" placeholder="${orderInformation.orderNote}" rows="3" readonly></textarea>
                                    <!--<label><input type="checkbox"> Shipping to bill address</label>-->
                                    <!--<label for="orderNote">Order Note</label>-->
                                    <span id="orderNoteError" class="error-message text-danger"></span>
                                </div>
                                <!--</div>-->
                            </div>
                        </div>
                        <div class="col-sm-8" >
                            <p>
                                <span style="font-weight: bold">Status: </span>
                                <span style="font-size: 9px;" class=" status-${orderInformation.statusId}">
                                    <c:choose>
                                        <c:when test="${orderInformation.statusId == '1'}">Pending</c:when>
                                        <c:when test="${orderInformation.statusId == '2'}">Accepted</c:when>
                                        <c:when test="${orderInformation.statusId == '3'}">Shipping</c:when>
                                        <c:when test="${orderInformation.statusId == '4'}">Delivered</c:when>
                                        <c:when test="${orderInformation.statusId == '5'}">Canceled By Customer</c:when>
                                        <c:when test="${orderInformation.statusId == '6'}">Canceled By Seller</c:when>
                                        <c:when test="${orderInformation.statusId == '7'}">Canceled By Seller</c:when>
                                        <c:when test="${orderInformation.statusId == '8'}">Completed</c:when>
                                        <c:otherwise>Unknown</c:otherwise>
                                    </c:choose>
                                </span>
                            </p>
                            <div class="review-payment">
                                <h2>Review & Payment</h2>
                            </div>

                            <div class="table-responsive cart_info">
                                <table class="table">
                                    <thead style="background-color: #FE980F;">
                                        <tr>
                                            <th>Item</th>
                                            <th></th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                            <th>Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="cartTotal" value="0" />
                                        <c:forEach var="item" items="${orderDetailList}">
                                            <tr>

                                                <td> 
                                                    <a href="${pageContext.request.contextPath}/ProductDetail?productId=${item[1].productId}"
                                                       style="color: black">
                                                        ${item[1].name}
                                                    </a>
                                                </td> 

                                                <td>${item[2]}</td>
                                                <td>${item[0].quantity}</td>
                                                <td>${item[0].price}</td>

                                                <td class="subtotal">
                                                    <c:set var="itemTotal" value="${item[0].quantity * item[0].price}" />
                                                    ${itemTotal}
                                                    <c:set var="cartTotal" value="${cartTotal + itemTotal}" />
                                                </td>
                                            </tr>

                                        </c:forEach>
                                        <tr>
                                            <td colspan="4">Total</td>
                                            <td><strong>${cartTotal}</strong></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div class="text-right col-sm-12">
                                <table style="width: 100%;">
                                    <tbody>
                                        <c:if test="${role == 'manager'}">
                                            <tr>
                                                <td style="width: 40%; visibility: hidden"></td>
                                                <%-- Refund --%>
                                                <td style="width: 20%" class="text-start">
                                                    <c:choose>
                                                        <c:when test="${orderInformation.paymentStatus == 1}">
                                                            <button class="btn btn-primary get-manh" 
                                                                    onclick="submitOrderUpdate(${orderInformation.orderId}, 'refund')"
                                                                    ${!(orderInformation.paymentmethod == 1 && orderInformation.paymentStatus == 1 
                                                                      && (orderInformation.statusId == 5 || orderInformation.statusId == 6)) ? 'disabled' : ''}>
                                                                Refund
                                                            </button>
                                                        </c:when>
                                                        <c:when test="${orderInformation.paymentStatus == 2}">
                                                            <button class="btn btn-primary get-manh" 
                                                                    onclick="submitOrderUpdate(${orderInformation.orderId}, 'pay')"
                                                                    ${!( orderInformation.paymentStatus == 2 
                                                                      && (orderInformation.statusId == 4 )) ? 'disabled' : ''}>
                                                                Paid Confirm
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button class="btn btn-primary get-manh" 
                                                                    disabled >
                                                                Refund
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <%-- Cancel --%>
                                                <td style="width: 20%" class="text-start">
                                                    <button class="btn btn-primary get-manh"  style="background-color: crimson"
                                                            onclick="submitOrderUpdate(${orderInformation.orderId}, 'cancelBySeller')"
                                                            ${orderInformation.statusId != 1 ? 'disabled' : ''}>
                                                        Cancel
                                                    </button>
                                                </td>

                                                <%-- Order Actions --%>
                                                <td style="width: 20%" class="text-start">
                                                    <c:choose>
                                                        <c:when test="${orderInformation.statusId == 1}">
                                                            <button class="btn btn-primary get-manh" 
                                                                    onclick="submitOrderUpdate(${orderInformation.orderId}, 'accept')"
                                                                    ${orderInformation.paymentmethod == 1 && orderInformation.paymentStatus != 1 ? 'disabled' : ''}>
                                                                Accept
                                                            </button>
                                                        </c:when>
                                                        <c:when test="${orderInformation.statusId == 2}">
                                                            <button class="btn btn-primary get-manh" 
                                                                    onclick="submitOrderUpdate(${orderInformation.orderId}, 'ship')">
                                                                Ship
                                                            </button>
                                                        </c:when>
                                                        <c:when test="${orderInformation.statusId == 3}">
                                                            <button class="btn btn-primary get-manh" 
                                                                    onclick="submitOrderUpdate(${orderInformation.orderId}, 'delivered')">
                                                                Delivered
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button class="btn btn-primary get-manh" 
                                                                    onclick="submitOrderUpdate(${orderInformation.orderId}, 'delivered')" disabled>
                                                                Delivered
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>

                                        <c:if test="${role == 'customer'}">
                                            <tr>
                                                <td style="width: 60%; visibility: hidden"></td>

                                                <%-- Cancel --%>
                                                <td style="width: 20%" class="text-start">
                                                    <button class="btn btn-danger get-manh" style="background-color: crimson"
                                                            onclick="submitOrderUpdate(${orderInformation.orderId}, 'cancel')"
                                                            ${!(orderInformation.statusId == 1 || orderInformation.statusId == 2) ? 'disabled' : ''}>
                                                        Cancel
                                                    </button>
                                                </td>

                                                <%-- Received --%>
                                                <td style="width: 20%" class="text-start">
                                                    <button class="btn btn-success get-manh" style="background-color: green"
                                                            onclick="submitOrderUpdate(${orderInformation.orderId}, 'receive')"
                                                            ${!(orderInformation.statusId == 3 || orderInformation.statusId == 4) ? 'disabled' : ''}>
                                                        Received
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>               
                    </div>
                </div>
            </div>
        </section>
        <!-- Modal Xác Nhận -->


        <div id="confirmModal" class="modal" style="display: none;">
            <div class="modal-content" style="width: 30%;">
                <h2 class="title text-center" style="color: black">Edit your profile?</h2>
                <br>
                <table>
                    <tr>
                        <td class="w-50">
                            <button id="confirmSubmit" class="btn btn-default get-manh" 
                                    style="padding-left: 15px;padding-right: 15px;">Yes</button>
                        </td>
                        <td class="w-50">
                            <button id="cancelSubmit" class="btn btn-default get-manh" 
                                    style="background-color: crimson;padding-left: 15px;padding-right: 15px;">No</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <script>
            function submitOrderUpdate(orderId, action) {
                let actionText = {
                    'refund': 'Refund this order?',
                    'cancelBySeller': 'Cancel this order as a seller?',
                    'cancel': 'Cancel this order?',
                    'accept': 'Accept this order?',
                    'ship': 'Mark this order as shipped?',
                    'delivered': 'Mark this order as delivered?',
                    'receive': 'Confirm you received this order?'
                };

                let confirmText = actionText[action] || 'Are you sure?';

                // Kiểm tra modal có tồn tại không
                let modalElement = document.getElementById("confirmModal");
                if (!modalElement) {
                    console.error("Modal confirmModal không tồn tại!");
                    return;
                }

                // Hiển thị modal bằng cách thay đổi style
                modalElement.style.display = "block";

                // Cập nhật tiêu đề modal
                let modalTitle = modalElement.querySelector(".title");
                if (modalTitle) {
                    modalTitle.innerText = confirmText;
                }

                // Gán sự kiện cho nút "Yes"
                let confirmButton = document.getElementById("confirmSubmit");
                confirmButton.onclick = function () {
                    let form = document.createElement("form");
                    form.method = "POST";
                    form.action = "OrderDetail"; // Servlet xử lý

                    let orderInput = document.createElement("input");
                    orderInput.type = "hidden";
                    orderInput.name = "orderId";
                    orderInput.value = orderId;
                    form.appendChild(orderInput);

                    let actionInput = document.createElement("input");
                    actionInput.type = "hidden";
                    actionInput.name = "action";
                    actionInput.value = action;
                    form.appendChild(actionInput);

                    document.body.appendChild(form);
                    form.submit();
                };

                // Gán sự kiện cho nút "No" để đóng modal
                let cancelButton = document.getElementById("cancelSubmit");
                cancelButton.onclick = function () {
                    modalElement.style.display = "none";
                };

                // Đóng modal nếu click ra ngoài vùng modal
                window.onclick = function (event) {
                    if (event.target === modalElement) {
                        modalElement.style.display = "none";
                    }
                };
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

