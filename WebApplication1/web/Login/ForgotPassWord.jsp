<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Forgot Password</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script>
            function startCountdown() {
                let countdownTime = 5;
                let countdownText = document.getElementById("countdownText");
                let sendOtpBtn = document.getElementById("sendOtpBtn");

                sendOtpBtn.disabled = true;
                sendOtpBtn.style.display = "none";
                countdownText.innerText = `Wait few seconds to resend...`;

                let countdownInterval = setInterval(function () {
                    countdownTime--;
                    countdownText.innerText = `Wait few seconds to resend...`;

                    if (countdownTime <= 0) {
                        clearInterval(countdownInterval);
                        countdownText.innerText = "";
                        sendOtpBtn.disabled = false;
                        sendOtpBtn.style.display = "block";
                    }
                }, 1000);

            }

            function validateInput() {
                let emailInput = document.getElementById("emailInput");
                let otpInput = document.getElementById("otpInput");
                let emailError = document.getElementById("emailError");
                let otpError = document.getElementById("otpError");
                let isValid = true;

                if (emailInput.value.trim() === "") {
                    emailError.textContent = "Email cannot be empty.";
                    isValid = false;
                } else {
                    emailError.textContent = "";
                }

                if (otpInput && otpInput.value.trim() === "") {
                    otpError.textContent = "OTP cannot be empty.";
                    isValid = false;
                } else {
                    otpError.textContent = "";
                }

                return isValid;
            }
        </script>
    </head>
    <body >
        <c:import url="/Template/header1.jsp" />

        <section >
            <c:choose>
                <c:when test="${availOTP}">
                    <div class="main-container" style="max-height: 250px;margin-top: 8vh; margin-bottom: 8vh;min-height: fit-content">
                        <div class="login-form">
                            <h3 class="text-center" style="margin-bottom: 40px;">Forgot Your Password</h3>
                            <form method="post" action="SendOtp" onsubmit="return validateInput();">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control " id="emailInput" 
                                           name="email" placeholder="name@example.com" value="${email}" style="height:45px">
                                    <label for="emailInput">Enter your email address</label>
                                    <div class="text-danger" id="emailError">${emailError}</div>
                                </div>
                                <div class="d-grid">
                                    <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit">${step}</button>
                                    <div class="text-center">
                                        <div>Haven't have an account?  <a href="${pageContext.request.contextPath}/Register">Sign up</a></div>
                                        <div>Already have an account? <a href="${pageContext.request.contextPath}/Login">Sign in</a></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>              
                </c:when>
                <c:otherwise>
                    <div class="main-container" style="max-height: 250px;margin-top: 8vh; margin-bottom: 8vh;min-height: fit-content">
                        <div class="login-form">
                            <h3 class="text-center" style="margin-bottom: 40px;">Forgot Your Password</h3>
                            <form method="post" action="ForgotPassWord" onsubmit="return validateInput();">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control " id="emailInput" 
                                           name="email" placeholder="name@example.com" value="${email}" style="height:45px" readonly>
                                    <label for="emailInput">Email address</label>
                                    <div class="text-danger" id="emailError"></div>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control " id="otpInput" 
                                           name="otp" placeholder="xxxxxx" style="height:45px">
                                    <label for="otpInput">OTP</label>
                                    <div class="text-danger" id="otpError"></div>
                                    <p id="countdownText"></p>
                                    <button id="sendOtpBtn" type="button" class="btn btn-link" style="background: transparent" onclick="startCountdown()">Resend OTP</button>
                                </div>  


                                <div class="d-grid">
                                    <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit">${step}</button>
                                    <div class="text-center">
                                        <div>Haven't have an account?  <a href="${pageContext.request.contextPath}/Register">Sign up</a></div>
                                        <div>Already have an account? <a href="${pageContext.request.contextPath}/Login">Sign in</a></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>              
                </c:otherwise>
            </c:choose>
        </section>                   
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
