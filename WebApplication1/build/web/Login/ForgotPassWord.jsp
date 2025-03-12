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
            document.addEventListener("DOMContentLoaded", function () {
                let countdownTime = parseInt("${waitTime}", 10) || 0; // Chuyển thành số nguyên
                let countdownText = document.getElementById("countdownText");
                let sendOtpBtn = document.getElementById("sendOtpBtn");
                let resendForm = document.getElementById("resendForm");

                function startCountdown() {
                    if (countdownTime > 0) {
                        sendOtpBtn.disabled = true;
                        sendOtpBtn.style.visibility = "hidden";
                        countdownText.innerText = `Wait ${countdownTime} seconds to resend...`;

                        let countdownInterval = setInterval(() => {
                            countdownTime--;
                            countdownText.innerText = `Wait ${countdownTime} seconds to resend...`;

                            if (countdownTime <= 0) {
                                clearInterval(countdownInterval);
                                countdownText.innerText = "";
                                sendOtpBtn.disabled = false;
                                sendOtpBtn.style.visibility = "visible";
                            }
                        }, 1000);
                    }
                }

                function resendOTP() {
                    if (resendForm) {
                        resendForm.submit(); // Gửi form để lấy OTP mới
                    }

                    // Reset countdown về 60 giây sau khi bấm resend
                    countdownTime = 60;
                    startCountdown();
                }

                startCountdown(); // Chạy countdown ngay khi vào trang

                // Gán sự kiện khi bấm nút "Resend OTP"
                if (sendOtpBtn) {
                    sendOtpBtn.addEventListener("click", resendOTP);
                }
            });



            function validateEmail() {
                let emailInput = document.getElementById("emailInput").value.trim();
                let emailError = document.getElementById("emailError");
                let submitButton = document.getElementById("submitButton");

                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                if (!emailInput) {
                    emailError.innerText = "Email is required.";
                    if (submitButton)
                        submitButton.disabled = true;
                } else if (!emailRegex.test(emailInput)) {
                    emailError.innerText = "Invalid email format.";
                    if (submitButton)
                        submitButton.disabled = true;
                } else {
                    emailError.innerText = "";
                    if (submitButton)
                        submitButton.disabled = false;
                }
            }

        </script>

    </head>
    <body>
        <c:import url="/Template/header1.jsp" />
        <section>
            <c:choose>
                <c:when test="${availOTP}">
                    <div class="main-container" style="margin-top: 8vh; margin-bottom: 8vh;">
                        <div class="login-form">
                            <h3 class="text-center">Forgot Your Password</h3>
                            <form method="post" action="Mail">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="emailInput" name="email" placeholder="name@example.com" 
                                           style="height: 45px"
                                           value="${email}" required onkeyup="validateEmail()" onblur="validateEmail()">
                                    <label for="emailInput">Enter your email address</label>
                                    <div class="text-danger" id="emailError">${emailError}</div>
                                </div>

                                <div class="d-grid">
                                    <button id="submitButton" class="btn btn-primary" type="submit">${step}</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="main-container" style="margin-top: 8vh; margin-bottom: 8vh;">
                        <div class="login-form">
                            <h3 class="text-center">Forgot Your Password</h3>
                            <form method="post" action="ForgotPassword">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="emailInput" name="email" value="${email}" readonly style="height: 45px">
                                    <label for="emailInput">Email address</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="otpInput" name="otpInput" placeholder="xxxxxx" required style="height: 45px">
                                    <label for="otpInput">OTP</label>
                                    <div class="text-danger" id="otpError">${otpError}</div>
                                    <p id="countdownText"></p>
                                    <button id="sendOtpBtn" type="button" class="btn btn-link" onclick="resendOTP()" style="background: transparent; color: black">Resend OTP</button>
                                </div>
                                <div class="d-grid">
                                    <button class="btn btn-primary" type="submit">${step}</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <form method="post" action="Mail" id="resendForm" style="visibility: hidden">
                        <input type="hidden" name="email" value="${email}" >
                    </form>
                </c:otherwise>
            </c:choose>
        </section>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
