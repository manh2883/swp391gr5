

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script>
            function validateInput() {
                let userName = document.getElementById("userNameInput");
                let passWord = document.getElementById("passWordInput");
                let userError = document.getElementById("userError");
                let passError = document.getElementById("passError");
                let isValid = true;

                if (userName.value.trim() === "") {
                    userError.textContent = "Username cannot be empty.";
                    isValid = false;
                } else if (userName.value.length > 255) {
                    userError.textContent = "Username cannot exceed 255 characters.";
                    isValid = false;
                } else {
                    userError.textContent = "";
                }

                if (passWord.value.trim() === "") {
                    passError.textContent = "Password cannot be empty.";
                    isValid = false;
                } else if (passWord.value.length > 255) {
                    passError.textContent = "Password cannot exceed 255 characters.";
                    isValid = false;
                } else {
                    passError.textContent = "";
                }

                return isValid;
            }
        </script>
        <style>
            .form-control{
                height:50px;
            }

        </style>
    </head>
    <body >
        <c:import url="/Template/header1.jsp" />
        <section>
            <div class="main-container">
                <div class="login-form">
                    <h3 class="text-center" style="margin-bottom: 40px;">Welcome back!</h3>
                    <form method="post" action="Login" onsubmit="return validateInput();">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control " id="userNameInput" 
                                   name="userName" placeholder="name@example.com" value="${userName}" style="height:45px">
                            <label for="userNameInput">Email address</label>
                            <div class="text-danger" id="userError"></div>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control " id="passWordInput" 
                                   name="passWord" placeholder="Password" style="height:45px">
                            <label for="passWordInput">Password</label>
                            <div class="text-danger" id="passError">${passError}</div>
                        </div>
                        <div class="d-grid">
                            <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit">Sign in</button>
                            <div class="text-center">
                                <a class="small" href="${pageContext.request.contextPath}/ForgotPassword">Forgot password?</a>
                                <div>Haven't have an account? Sign up <a href="${pageContext.request.contextPath}/Register">here</a></div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:import url="/Template/footer1.jsp" />
    </body>
</html>
