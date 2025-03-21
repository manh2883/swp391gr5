<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%--<c:if test="${empty sessionScope.account}">
    <c:redirect url="/Home/Error404.jsp" />
</c:if>--%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Change Password</title>
<!--<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="${pageContext.request.contextPath}/CSS/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/template/responsive.css" rel="stylesheet">  

        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .productinfo .btn {
                display: inline-block;
            }
            .product-img {
                width: 121px;
                height: 112px;
                object-fit: contain;
                background-color: #f8f8f8;
            }
        </style>
        <script>
            function sendOtpRequest() {
                let phoneNumber = document.getElementById("email").value;

                fetch("Mail", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "email=" + encodeURIComponent(phoneNumber)
                })
                        .then(response => response.text())
                        .then(data => {
                            console.log("OTP Sent:", data);
                            startOtpTimer();
                        })
                        .catch(error => console.error("Error sending OTP:", error));
            }

            function startOtpTimer() {
                let button = document.getElementById("sendOtpBtn");
                button.disabled = true;
                let seconds = 60;
                button.innerText = "Wait " + seconds + "s to send again";

                let interval = setInterval(() => {
                    seconds--;
                    button.innerText = "Wait " + seconds + "s to send again";
                    if (seconds <= 0) {
                        clearInterval(interval);
                        button.disabled = false;
                        button.innerText = "Get OTP";
                    }
                }, 1000);
            }
        </script>
    </head>
    <body>
        <header>
            <c:import url="/Template/header1.jsp" />
            <c:import url="/Template/header2.jsp" />
        </header>

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}">Home</a></li>
                        <li class="active">${breadcrumbs}</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9 " >
                        <h2 class="title text-center">Change Password</h2>
                        <p class="text-center" style="color: green">${message}</p>
                        <input name="email" id="email" value="${email}" type="hidden">
                        <form method="post" action="ChangePassword" onsubmit="return validateForm()">
                            <table style="width: 60%; table-layout: fixed; margin: auto">
                                <tbody>
                                    <tr>
                                        <td style="width: 30%">

                                        </td>
                                        <td style="width: 70%">

                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-end">
                                            <label>Old Password:</label>
                                        </td>
                                        <td class="text-start" style="padding-bottom: 10px">
                                            <input type="password" id="name0" class="form-control mt-2" name="oldPassword" required
                                                   style="color: black; width: 100%; height: 35px; margin-bottom: 10px;" 
                                                   minlength="8" maxlength="20">
                                            <div class="text-danger" id="nameError0">${nameError0}</div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="text-end">
                                            <label>New Password:</label>
                                        </td>
                                        <td class="text-start" style="padding-bottom: 10px">
                                            <input type="password" id="name1" class="form-control mt-2" name="password" required
                                                   style="color: black; width: 100%; height: 35px; margin-bottom: 10px;" 
                                                   minlength="8" maxlength="20">
                                            <div class="text-danger" id="nameError1">${nameError1}</div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="text-end">
                                            <label>Re-Enter:</label>
                                        </td>
                                        <td class="text-start" style="padding-bottom: 10px">
                                            <input type="password" id="name2" class="form-control mt-2" name="rePassword" required
                                                   style="color: black; width: 100%; height: 35px; margin-bottom: 10px;" 
                                                   minlength="8" maxlength="20">
                                            <div class="text-danger" id="nameError2">${nameError2}</div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="text-end">
                                            <label>OTP:</label>
                                        </td>
                                        <td class="text-start" style="padding-bottom: 10px">
                                            <table class="col-12">
                                                <tr>
                                                    <td style="width: 50%">
                                                        <input type="text" id="name3" class="form-control mt-2" name="OTP" required
                                                               style="color: black; width: 100%; height: 35px; margin-bottom: 10px;" 
                                                               maxlength="6" oninput="limitInputLength(this, 6)">
                                                        <div class="text-danger" id="nameError3">${nameError3}</div>
                                                    </td>
                                                    <td>
                                                        <button type="button" id="otpButton" class="btn btn-default get-manh"
                                                                style="background-color: transparent;
                                                                color: black; font-size: 10px;font-weight: bold" 
                                                                onclick="sendOtpRequest()">
                                                            Get OTP
                                                        </button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>

                                    <tr >
                                        <td class="text-start"></td>
                                        <td class="text-start">
                                            <button type="reset" class="btn btn-default get-manh" style="background-color: #adadad">Cancel</button>
                                            <button type="submit" class="btn btn-default get-manh">Change</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
        </section>
        <c:import url="/Template/footer1.jsp" />
        <script>
            function validateForm() {
                let pass0 = document.getElementById("name0").value;
                let pass1 = document.getElementById("name1").value;
                let pass2 = document.getElementById("name2").value;
                let otp = document.getElementById("name3").value;
                let valid = true;

                if (pass1.length < 8 || pass1.length > 20) {
                    document.getElementById("nameError1").innerText = "Password must be between 8-20 characters.";
                    valid = false;
                } else {
                    document.getElementById("nameError1").innerText = "";
                }

                if (pass0.length < 8 || pass0.length > 20) {
                    document.getElementById("nameError0").innerText = "Password must be between 8-20 characters.";
                    valid = false;
                } else {
                    document.getElementById("nameError0").innerText = "";
                }

                if (pass2 !== pass1) {
                    document.getElementById("nameError2").innerText = "Passwords do not match.";
                    valid = false;
                } else {
                    document.getElementById("nameError2").innerText = "";
                }

                if (otp.length < 1 || otp.length > 6) {
                    document.getElementById("nameError3").innerText = "OTP must be 6 digits.";
                    valid = false;
                } else {
                    document.getElementById("nameError3").innerText = "";
                }

                return valid;
            }

            function limitInputLength(el, max) {
                if (el.value.length > max) {
                    el.value = el.value.slice(0, max);
                }
            }

        </script>
    </body>
</html>
