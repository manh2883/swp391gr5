<%-- 
    Document   : Login
    Created on : Jan 19, 2025, 3:29:26 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome back</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    </head>
    <body>
        <div class="login-form" >
            <h3 class="login-heading">Welcome back!</h3>
            <form method="post" action="Login">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="userNameInput" name = "userName"placeholder="name@example.com">
                    <label for="floatingInput">Email address</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="passWordInput" name = "passWord" placeholder="Password">
                    <label for="floatingPassword">Password</label>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" value="" id="rememberPasswordCheck">
                    <label class="form-check-label" for="rememberPasswordCheck">
                        Remember password
                    </label>
                </div>          
                <div class="d-grid">
                    <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit">Sign in</button>
                    <div class="text-center">
                        <a class="small" href="${pageContext.request.contextPath}/ForgotPassword">Forgot password?</a>
                        <div>
                             Haven't have an account? Sign up 
                             <a href="${pageContext.request.contextPath}/Register">here</a><!-- comment -->
                        </div>
                         
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>

</html>
