<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Add New Password</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script>
            function validateForm(event) {
                let newPassword = document.getElementById("newPassword").value.trim();
                let rePassword = document.getElementById("rePassword").value.trim();
                let passError = document.getElementById("passError");

                // Reset lỗi trước khi kiểm tra
                passError.innerText = "";

                if (newPassword === "" || rePassword === "") {
                    passError.innerText = "Password fields cannot be empty.";
                    event.preventDefault();
                    return false;
                }
                if (newPassword.length < 8) {
                    passError.innerText = "Password must be at least 8 characters.";
                    event.preventDefault();
                    return false;
                }
                if (newPassword.includes(" ") || rePassword.includes(" ")) {
                    passError.innerText = "Password cannot contain spaces.";
                    event.preventDefault();
                    return false;
                }
                if (newPassword !== rePassword) {
                    passError.innerText = "Passwords do not match.";
                    event.preventDefault();
                    return false;
                }
                return true;
            }

            document.addEventListener("DOMContentLoaded", function () {
                document.querySelector("form").addEventListener("submit", validateForm);
            });
        </script>


    </head>
    <body>
        <c:import url="/Template/header1.jsp" />
        <section>
            <div class="main-container" style="margin-top: 8vh; margin-bottom: 8vh;">
                <div class="login-form">
                    <h3 class="text-center">Create New Password</h3>
                    <form method="post" action="CreateNewPassWord">

                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" 
                                   id="newPassword" name="newPassword"  
                                   placeholder="newPassword" style="height: 45px">
                            <label for="newPassword">New Password</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" 
                                   id="rePassword" name="rePassword" 
                                   style="height: 45px" placeholder="rePassword">
                            <label for="rePassword">Re-Enter Password</label>
                            <div class="text-danger" id="passError">${passError}</div>
                        </div>

                        <input type="hidden" class="form-control" id="emailInput" name="email" value="${email}" >

                        <div class="d-grid">
                            <button class="btn btn-primary" type="submit">Continue</button>
                            <div class="text-center"><!-- comment -->
                                <div>
                                    Haven't have an account? Sign up <a href="${pageContext.request.contextPath}/Register">here</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
