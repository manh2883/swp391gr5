<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Forgot Password</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script>
//          


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

            document.addEventListener("DOMContentLoaded", function () {
                startOtpTimerOnLoad();
            });

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
                            let expiryTime = Date.now() + 60000; // 60 giây hết hạn

                            // Lưu cả vào localStorage và sessionStorage
                            localStorage.setItem("otpExpiry", expiryTime);
                            sessionStorage.setItem("otpExpiry", expiryTime);

                            startOtpTimer();
                        })
                        .catch(error => console.error("Error sending OTP:", error));
            }

            function startOtpTimer() {
                let button = document.getElementById("sendOtpBtn");
                button.disabled = true;

                function updateTimer() {
                    let expiryTime = getOtpExpiry();
                    let remainingTime = Math.floor((expiryTime - Date.now()) / 1000);

                    if (remainingTime > 0) {
                        button.innerText = "Wait " + remainingTime + "s to send again";
                        setTimeout(updateTimer, 1000);
                    } else {
                        clearOtpExpiry();
                        button.disabled = false;
                        button.innerText = "Get OTP";
                    }
                }
                updateTimer();
            }

            function startOtpTimerOnLoad() {
                let expiryTime = getOtpExpiry();

                if (expiryTime && Date.now() < expiryTime) {
                    startOtpTimer();
                } else {
                    console.log("No active OTP timer found.");
                }
            }

            function getOtpExpiry() {
                // Ưu tiên lấy từ sessionStorage nếu có, nếu không thì lấy từ localStorage
                return sessionStorage.getItem("otpExpiry") || localStorage.getItem("otpExpiry") || ${otpExpiry};
            }

            function clearOtpExpiry() {
                localStorage.removeItem("otpExpiry");
                sessionStorage.removeItem("otpExpiry");
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
                                    <div class="text-center">
                                        <div>Haven't have an account? Sign up <a href="${pageContext.request.contextPath}/Register">here</a></div>
                                    </div>

                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="main-container" style="margin-top: 8vh; margin-bottom: 8vh;">
                        <div class="login-form">
                            <h3 class="text-center">Forgot Your Password</h3>
                            <input type="hidden" class="form-control" id="email" name="email" value="${email}">
                            <form method="post" action="ForgotPassword">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="emailInput" name="email" value="${email}" readonly style="height: 45px">
                                    <label for="emailInput">Email address</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="otpInput" name="otpInput" placeholder="xxxxxx" required style="height: 45px">
                                    <label for="otpInput">OTP</label>
                                    <div class="text-danger" id="otpError">${otpError}</div>
                                    <div>
                                        <a id="sendOtpBtn" class="btn-link" 
                                           onclick="sendOtpRequest()" style="background: transparent;
                                           color: black;">Resend OTP</a>
                                    </div>

                                </div>

                                <div class="d-grid mb-3">
                                    <button class="btn btn-primary" type="submit">Continue</button>

                                    <div class="text-center">
                                        <div>Already have an account? <a href="${pageContext.request.contextPath}/Login">Sign in here</a></div>
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
