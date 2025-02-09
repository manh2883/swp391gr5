<%-- 
    Document   : Home
    Created on : Jan 19, 2025, 2:28:48 PM
    Author     : Acer
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign up</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
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
        <script>
            function validateInput() {

                let isValid = true;

                // Xóa các thông báo lỗi cũ
                document.querySelectorAll('.text-danger').forEach(el => el.innerText = '');

                // Kiểm tra First Name và Last Name
                const firstName = document.getElementById('firstNameInput').value.trim();
                const lastName = document.getElementById('lastNameInput').value.trim();
                if (firstName === '' || lastName === '') {
                    document.getElementById('firstNameError').innerText = 'First and Last Name are required.';
                    isValid = false;
                } else if (!/^[\p{L}\p{M}\s'-]+$/u.test(firstName) || !/^[\p{L}\p{M}\s'-]+$/u.test(lastName)) {
                    if (/\s/.test(firstName) || /\s/.test(lastName)) {
                        document.getElementById('firstNameError').innerText = 'Names cannot contain space.';
                    } else
                        document.getElementById('firstNameError').innerText = 'Names cannot contain numbers or invalid symbols.';
                    isValid = false;
                }


                // Kiểm tra Username
                const username = document.getElementById('userNameInput').value.trim();
                const usernameRegex = /^[A-Za-z0-9]+$/; // Chỉ cho phép chữ cái và số

                if (username === '') {
                    document.getElementById('userNameError').innerText = 'Username is required.';
                    isValid = false;
                } else if (username.length < 4) {
                    document.getElementById('userNameError').innerText = 'Username must be at least 4 characters.';
                    isValid = false;
                } else if (!usernameRegex.test(username)) {
                    document.getElementById('userNameError').innerText = 'Username can only contain letters and numbers.';
                    isValid = false;
                }


                // Kiểm tra Email hoặc Số điện thoại
                const email = document.getElementById('emailInput').value.trim();
                const phone = document.getElementById('phoneInput').value.trim();
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                const phoneRegex = /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/;

                if (email === '') {
                    document.getElementById('emailError').innerText = 'Email is required.';
                    isValid = false;
                } else if (!emailRegex.test(email)) {
                    document.getElementById('emailError').innerText = 'Invalid Email format.';
                    isValid = false;
                }

                if (phone === '') {
                    document.getElementById('phoneError').innerText = 'Phone is required.';
                    isValid = false;
                } else if (!phoneRegex.test(phone)) {
                    document.getElementById('phoneError').innerText = 'Invalid phone format.';
                    isValid = false;
                }

                // Kiểm tra Giới tính
                const gender = document.getElementById('gender').value;
                if (gender === '') {
                    document.getElementById('genderError').innerText = 'Please select your gender.';
                    isValid = false;
                }

                // Kiểm tra Ngày sinh
                const day = parseInt(document.getElementById('dobDay').value);
                const month = parseInt(document.getElementById('dobMonth').value);
                const year = parseInt(document.getElementById('dobYear').value);
                const dobErrorElement = document.getElementById('dobError');

                if (!day || !month || !year) {
                    dobErrorElement.innerText = 'Please select a valid date of birth.';
                    isValid = false;
                } else {
                    const dob = new Date(year, month - 1, day);
                    const currentDate = new Date();
                    const minAge = 13; // Giới hạn tuổi tối thiểu

                    if (
                            dob.getDate() !== day ||
                            dob.getMonth() !== (month - 1) ||
                            dob.getFullYear() !== year
                            ) {
                        dobErrorElement.innerText = 'Invalid date of birth.';
                        isValid = false;
                    } else if (currentDate.getFullYear() - year < minAge || dob > currentDate) {
                        dobErrorElement.innerText = `You must be at least ${minAge} years old.`;
                        isValid = false;
                    }
                }

                // Kiểm tra Mật khẩu
                const password = document.getElementById('passWordInput').value;
                if (password.length < 8) {
                    document.getElementById('passError').innerText = 'Password must be at least 8 characters.';
                    isValid = false;
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
                    <h3 class="text-center" style="margin-bottom: 40px;">Sign Up</h3>
                    <form method="post" action="Register" onsubmit="return validateInput();">
                        <!-- First Name & Last Name -->
                        <div class="row mb-3">

                            <div class="col">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="firstNameInput" name="firstNameInput" 
                                           placeholder="firstNameInput" style="height:45px" value="${firstName}">
                                    <label for="firstNameInput">First Name</label>

                                </div>
                            </div>

                            <div class="col">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="lastNameInput" name="lastNameInput" 
                                           placeholder="lastNameInput" style="height:45px" value="${lastName}">
                                    <label for="lastNameInput">Last Name</label>

                                </div>
                            </div>
                            <div class="text-danger" id="firstNameError"></div>
                        </div>

                        <!-- Username -->
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="userNameInput" name="userNameInput" 
                                   placeholder="Username" style="height:45px" value="${userName}">
                            <label for="userNameInput">Username</label>
                            <div class="text-danger" id="userNameError">${userNameError}</div>
                        </div>

                        <!-- Email or Phone Number -->
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="emailInput" name="emailInput" 
                                   placeholder="Email" style="height:45px" value="${email}">
                            <label for="emailInput">Email</label>
                            <div class="text-danger" id="emailError">${emailError}</div>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="phoneInput" name="phoneInput" 
                                   placeholder="phone" style="height:45px" value="${phone}">
                            <label for="phoneInput">Phone Number</label>
                            <div class="text-danger" id="phoneError">${phoneError}</div>
                        </div>
                        <!-- Gender Dropdown -->
                        <div class="form-floating mb-3">
                            <select class="form-select form-control" id="gender" name="gender" style="height:45px">
                                <option value="1" ${gender != null && gender == 1 ? "selected" : ""}>Male</option>
                                <option value="0" ${gender != null && gender == 0 ? "selected" : ""}>Female</option>
                                <option value="2" ${gender != null && gender == 2 ? "selected" : ""}>Other</option>
                            </select>
                            <label for="gender">Gender</label>
                            <div class="text-danger" id="genderError"></div>
                        </div>



                        <!-- Date of Birth -->
                        <div class="row mb-3">
                            <!-- Ngày -->
                            <div class="col">
                                <select class="form-select form-control" id="dobDay" name="dobDay" style="height:45px">
                                    <option value="">Day</option>
                                    <c:forEach var="i" begin="1" end="31">
                                        <option value="${i}" ${not empty dobDay and dobDay == i ? 'selected' : ''}>${i}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Tháng -->
                            <div class="col">
                                <select class="form-select form-control" id="dobMonth" name="dobMonth" style="height:45px">
                                    <option value="">Month</option>
                                    <c:forEach var="i" begin="1" end="12">
                                        <c:set var="monthNames" value="January,February,March,April,May,June,July,August,September,October,November,December"/>
                                        <c:set var="monthName" value="${fn:split(monthNames, ',')[i-1]}" />
                                        <option value="${i}" ${not empty dobMonth and dobMonth == i ? 'selected' : ''}>${monthName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Năm -->
                            <div class="col">
                                <select class="form-select form-control" id="dobYear" name="dobYear" style="height:45px">
                                    <option value="">Year</option>
                                    <c:forEach var="i" begin="1950" end="2025">
                                        <option value="${2025 - i + 1950}" ${not empty dobYear and dobYear == (2025 - i + 1950) ? 'selected' : ''}>${2025 - i + 1950}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="text-danger" id="dobError"></div>
                        </div>

                        <!-- Password -->
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="passWordInput" name="passWordInput" placeholder="Password" style="height:45px">
                            <label for="passWordInput">Password</label>
                            <div class="text-danger" id="passError"></div>
                        </div>

                        <!-- Submit Button -->
                        <div class="d-grid">
                            <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit">Sign Up</button>
                            <div class="text-center">
                                <div>Already have an account? <a href="${pageContext.request.contextPath}/Login">Sign in here</a></div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </section>
        <div>${dobDay}, ${dobMonth}, ${dobYear}</div>
        <c:import url="/Template/footer1.jsp" />
    </body>

</html>