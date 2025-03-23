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

            .status-6 { /* Accepted */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #d4edda;
                color: #155724;
            }

            .status-2 { /* Shipping */
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #cce5ff;
                color: #004085;
            }

            .status-3 { /* Delivered */
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

            .status-4 { /* Canceled */
                padding: .2rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #f8d7da;
                color: #721c24;
            }

            .default-status {
                padding: .4rem 10px;
                border-radius: 2rem;
                text-align: center;
                background-color: #e2e3e5;
                color: #383d41;
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
                                    <p>
                                        <span style="font-weight:  bold;font-size:17px ">Created At: </span>
                                        <span style="font-size: 15px">${orderInformation.createAt}</span>
                                    </p>
                                    <p>
                                        <span style="font-weight:  bold;font-size:17px ">Completed At: </span>
                                        <c:choose>
                                            <c:when test="${not empty orderInformation.completedAt and orderInformation.completedAt != 'null'}">
                                                <span style="font-size: 15px">${orderInformation.completedAt}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>N/A</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p>
                                        <span style="font-weight:  bold;font-size:17px ">Payment method: </span>
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
                                    </p>
                                </div>  
                                <div class="order-message">
                                    <p>Shipping Order</p>
                                    <textarea name="orderNote" id="orderNote" placeholder="${orderInformation.orderNote}" rows="3"></textarea>
                                    <!--<label><input type="checkbox"> Shipping to bill address</label>-->
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
                                        <c:when test="${orderInformation.statusId == '6'}">Accepted</c:when>
                                        <c:when test="${orderInformation.statusId == '2'}">Shipping</c:when>
                                        <c:when test="${orderInformation.statusId == '3'}">Delivered</c:when>
                                        <c:when test="${orderInformation.statusId == '4'}">Canceled By Customer</c:when>
                                        <c:when test="${orderInformation.statusId == '5'}">Canceled By Seller</c:when>
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
                            <div class="row" style="height: 50px; vertical-align: middle;">
                                <p>Role: ${role}</p>
                                <p>Order ID: ${orderInformation.orderId}</p>
                                <p>Status ID: ${orderInformation.statusId}</p>
                                <p>Payment Method: ${orderInformation.paymentmethod}</p>
                                <p>Payment Status: ${orderInformation.paymentStatus}</p>

                                <div class="text-right col-sm-12" style="visibility: hidden">
                                    <c:set var="isDisabled" value="${orderInformation.statusId == 4 || orderInformation.statusId == 5 
                                                                     || orderInformation.statusId == 6 || orderInformation.statusId == 7 
                                                                     || orderInformation.statusId == 8}" />
                                    <c:set var="cannotAccept" value="${orderInformation.paymentmethod == 1 && orderInformation.paymentStatus != 1}" />

                                    <table style="border:1px solid red;width: 100%;">
                                        <tbody>
                                            <tr>
                                                <td style="width: 40%;visibility: hidden"></td>

                                                <%-- Manager Refund --%>
                                                <td style="width: 20%" class="text-start">
                                                    <c:if test="${role == 'manager' 
                                                                 
                                                          }">
                                                          <button class="btn btn-primary"
                                                                  onclick="submitOrderUpdate(${orderInformation.orderId}, 'refund')"
                                                                  ${isDisabled ? 'disabled' : ''}>Refund</button>
                                                    </c:if>
                                                </td>

                                                <%-- Manager Cancel --%>
                                                <td style="width: 20%" class="text-start">
                                                    <c:if test="${role == 'manager' }">
                                                        <button class="btn btn-danger"
                                                                onclick="submitOrderUpdate(${orderInformation.orderId}, 'cancelBySeller')"
                                                                ${isDisabled ? 'disabled' : ''}>Cancel</button>
                                                    </c:if>
                                                </td>

                                                <%-- Manager Update --%>
                                                <td style="width: 20%" class="text-start">
                                                    <c:if test="${role == 'manager' }">
                                                        <c:choose>
                                                            <%-- Accept Order --%>
                                                            <c:when test="${orderInformation.statusId == 1}">
                                                                <button class="btn btn-primary"
                                                                        onclick="submitOrderUpdate(${orderInformation.orderId}, 'accept')"
                                                                        ${cannotAccept ? 'disabled' : ''}>Accept</button>
                                                            </c:when>

                                                            <%-- Ship Order --%>
                                                            <c:when test="${orderInformation.statusId == 2}">
                                                                <button class="btn btn-primary"
                                                                        onclick="submitOrderUpdate(${orderInformation.orderId}, 'ship')">Ship</button>
                                                            </c:when>

                                                            <%-- Delivered --%>
                                                            <c:when test="${orderInformation.statusId == 3}">
                                                                <button class="btn btn-primary"
                                                                        onclick="submitOrderUpdate(${orderInformation.orderId}, 'delivered')">Delivered</button>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>

                                <%-- Customer Actions --%>
                                <div class="text-right col-sm-12" style="visibility: hidden">
                                    <table style="border:1px solid red;width: 100%;">
                                        <tbody>
                                            <tr>
                                                <td style="width: 40%;visibility: hidden"></td>

                                                <%-- Customer Cancel --%>
                                                <td style="width: 30%">
                                                    <c:if test="${role == 'customer' && (orderInformation.statusId == 1 || orderInformation.statusId == 2)}">
                                                        <button class="btn btn-danger"
                                                                onclick="submitOrderUpdate(${orderInformation.orderId}, 'cancel')"
                                                                ${isDisabled ? 'disabled' : ''}>Cancel</button>
                                                    </c:if>
                                                </td>

                                                <%-- Customer Received --%>
                                                <td style="width: 30%" class="text-start">
                                                    <c:if test="${role == 'customer' && (orderInformation.statusId == 3 || orderInformation.statusId == 4)}">
                                                        <button class="btn btn-success"
                                                                onclick="submitOrderUpdate(${orderInformation.orderId}, 'receive')"
                                                                ${isDisabled ? 'disabled' : ''}>Received</button>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>

                            </div>          
                        </div>               
                    </div>
                </div>
            </div>
        </section>

        <!-- Modal -->
        <div id="confirmModal" class="modal" style="display: none;">
            <div class="modal-content">
                <p id="modalMessage"></p>
                <button id="confirmBtn">Confirm</button>
                <button onclick="closeModal()">Cancel</button>
            </div>
        </div>

        <script>
            function openModal(orderId, action) {
                let message = "Are you sure you want to " + action + " this order?";
                document.getElementById("modalMessage").innerText = message;
                document.getElementById("confirmBtn").setAttribute("onclick", 'updateOrder(' + orderId + ',' + action + ')');
                document.getElementById("confirmModal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("confirmModal").style.display = "none";
            }

            function submitOrderUpdate(orderId, action) {
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

