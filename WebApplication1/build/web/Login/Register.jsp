<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign up</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
                    document.getElementById('firstNameError').innerText = 'Names cannot contain numbers or invalid symbols.';
                    isValid = false;
                }


                // Kiểm tra Username
                const username = document.getElementById('userNameInput').value.trim();
                if (username === '') {
                    document.getElementById('userNameError').innerText = 'Username is required.';
                    isValid = false;
                } else if (username.length < 4) {
                    document.getElementById('userNameError').innerText = 'Username must be at least 4 characters.';
                    isValid = false;
                }

                // Kiểm tra Email hoặc Số điện thoại
                const contact = document.getElementById('contactInput').value.trim();
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                const phoneRegex = /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/;
                if (contact === '') {
                    document.getElementById('contactError').innerText = 'Email or Phone Number is required.';
                    isValid = false;
                } else if (!emailRegex.test(contact) && !phoneRegex.test(contact)) {
                    document.getElementById('contactError').innerText = 'Invalid Email or Phone Number format.';
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
        <section style="padding-bottom: 60px; padding-top: 60px">
            <div class="main-container">
                <div class="login-form">
                    <h3 class="text-center" style="margin-bottom: 40px;">Sign Up</h3>
                    <form method="post" action="Register" onsubmit="return validateInput();">
                        <!-- First Name & Last Name -->
                        <div class="row mb-3">

                            <div class="col">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="firstNameInput" name="firstNameInput" 
                                           placeholder="firstNameInput" style="height:45px">
                                    <label for="firstNameInput">First Name</label>

                                </div>
                            </div>

                            <div class="col">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="lastNameInput" name="lastNameInput" 
                                           placeholder="lastNameInput" style="height:45px">
                                    <label for="lastNameInput">Last Name</label>

                                </div>
                            </div>
                            <div class="text-danger" id="firstNameError"></div>
                        </div>

                        <!-- Username -->
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="userNameInput" name="userName" 
                                   placeholder="Username" style="height:45px">
                            <label for="userNameInput">Username</label>
                            <div class="text-danger" id="userNameError"></div>
                        </div>

                        <!-- Email or Phone Number -->
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="contactInput" name="contact" placeholder="Email or Phone Number" style="height:45px">
                            <label for="contactInput">Email or Phone Number</label>
                            <div class="text-danger" id="contactError"></div>
                        </div>

                        <!-- Gender Dropdown -->
                        <div class="form-floating mb-3">
                            <select class="form-select form-control" id="gender" name="gender" style="height:45px" >

                                <option value="1">Male</option>
                                <option value="0">Female</option>
                                <option value="2">Other</option>
                            </select>
                            <label for="gender">Gender</label>
                            <div class="text-danger" id="genderError"></div>
                        </div>

                        <!-- Date of Birth -->
                        <div class="row mb-3">
                            <div class="col">
                                <select class="form-select form-control" id="dobDay" name="dobDay" style="height:45px">
                                    <option value="">Day</option>
                                    <% for (int i = 1; i <= 31; i++) { %>
                                    <option value="<%=i%>"><%=i%></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="col">
                                <select class="form-select form-control" id="dobMonth" name="dobMonth" style="height:45px">
                                    <option value="">Month</option>
                                    <% String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                       for (int i = 0; i < months.length; i++) { %>
                                    <option value="<%=i+1%>"><%=months[i]%></option>
                                    <% } %>
                                </select>
                            </div> 
                            <div class="col">
                                <select class="form-select form-control" id="dobYear" name="dobYear" style="height:45px">
                                    <option value="">Year</option>
                                    <% for (int i = 2025; i >= 1925; i--) { %>
                                    <option value="<%=i%>"><%=i%></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="text-danger" id="dobError"></div> <!-- Thông báo lỗi cho DoB -->
                        </div>

                        <!-- Password -->
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="passWordInput" name="passWord" placeholder="Password" style="height:45px">
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
        <c:import url="/Template/footer1.jsp" />
    </body>

</html>