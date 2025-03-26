<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<c:if test="${empty sessionScope.account}">
    <c:redirect url="/Home/Error404.jsp" />
</c:if>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>My Profile</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="../../../index.html" />
        <meta name="Version" content="v1.2.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/Images/tpfv1_reverse.ico" type="image/x-icon">
        <link href="${pageContext.request.contextPath}/CSS/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/responsive.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                height: 20%;
                width: 30%;
                background-color: rgba(0,0,0,0.4);
            }
            .modal-content {
                background-color: white;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 20%;
                text-align: center;
            }
        </style>
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
                        <li class="active">My Profile</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <h2 class="title text-center">My Profile</h2>

                        <p class="text-center" style="color: green">${message}</p>
                        <input name="email" id="email" value="${email}" type="hidden">
                        <form method="post" action="MyProfile" id="profileForm">
                            <table style="width: 60%; table-layout: fixed; margin: auto">
                                <tbody>
                                    <tr style="visibility: hidden">
                                        <td style="width: 30%">
                                            1
                                        </td>
                                        <td class=" text-start" style="width: 70%">
                                            1
                                        </td>
                                    </tr>
                                    <tr >
                                        <td class="text-end">
                                            <label>User Name:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="text" id="userName" class="form-control" name="userName" required
                                                   minlength="8" maxlength="20" pattern="[a-zA-Z0-9]+" value="${userName}" readonly
                                                   style="margin-bottom: 10px;height: 35px;">
                                            <div class="text-danger" id="userNameError"></div>
                                        </td>
                                    </tr>
                                    <tr style="margin-bottom: 10px;">
                                        <td class="text-end">
                                            <label>First Name:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="text" id="firstName" class="form-control" name="firstName" required
                                                   minlength="1" maxlength="20" style="margin-bottom: 10px; height: 35px;"
                                                   pattern="[a-zA-Z]+" value="${firstName}" readonly>
                                            <div class="text-danger" id="firstNameError"></div>
                                        </td>
                                    </tr>
                                    <tr >
                                        <td class="text-end">
                                            <label>Last Name:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="text" id="lastName" class="form-control" name="lastName" required
                                                   minlength="1" maxlength="20" pattern="[a-zA-Z]+" value="${lastName}" 
                                                   style="margin-bottom: 10px;height: 35px;" readonly>
                                            <div class="text-danger" id="lastNameError"></div>
                                        </td>
                                    </tr>
                                    <tr >
                                        <td class="text-end">
                                            <label>Gender:</label>
                                        </td>
                                        <td class="text-start">
                                            <select class="form-select" id="gender" name="gender" style="margin-bottom: 10px;height: 35px; display: none;  font-family: 'roboto';
                                                    font-size: 12px;
                                                    font-weight: 300;">
                                                <option value="1" ${gender != null && gender == 1 ? "selected" : ""}>Male</option>
                                                <option value="0" ${gender != null && gender == 0 ? "selected" : ""}>Female</option>
                                                <option value="2" ${gender != null && gender == 2 ? "selected" : ""}>Other</option>
                                            </select>
                                            <input type="text" id="genderShow" class="form-control"  required
                                                   value="${genderString}" 
                                                   style="margin-bottom: 10px;height: 35px; " readonly>

                                        </td>
                                    </tr>
                                    <tr style="margin-bottom: 10px;">
                                        <td class="text-end">
                                            <label>DoB:</label>
                                        </td>
                                        <td class="text-start">
                                            <input type="date" id="dob" class="form-control" name="dob" 
                                                   required value="${dob}" readonly style="margin-bottom: 10px; height: 35px;">
                                            <div class="text-danger" id="dobError"></div>
                                        </td>
                                    </tr>
                                    <tr style="margin-bottom: 10px;">
                                        <td></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/MyProfile"> 
                                                <button type="button" id="cancelButton" 
                                                        class="btn btn-default get-manh" style="display: none;background-color: crimson">Cancel</button></a>
                                            <button type="submit" id="submitButton" class="btn btn-default get-manh" style="display: none;">Submit</button>
                                            <button type="button" id="editButton" class="btn btn-default get-manh" style="background-color: green">Edit</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                        <br><br><br>
                        <!-- Modal -->                      
                        <div id="confirmModal" class="modal" style="display: none;">
                            <div class="modal-content" style="width: 30%;">
                                <h2 class="title text-center" style="color: black">Edit your profile?</h2>
                                <br>
                                <table>
                                    <tr>
                                        <td class="w-50">
                                            <button id="confirmSubmit" class="btn btn-default get-manh" style="padding-left: 15px;padding-right: 15px;">Yes</button>
                                        </td>
                                        <td class="w-50">
                                            <button id="cancelSubmit" class="btn btn-default get-manh" style="background-color: crimson;padding-left: 15px;padding-right: 15px;">No</button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <br><!-- comment -->
                    <br>
                    <br>
                </div>

        </section>
        <c:import url="/Template/footer1.jsp" />
    </body>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const editButton = document.getElementById("editButton");
            const submitButton = document.getElementById("submitButton");
            const cancelButton = document.getElementById("cancelButton");
            const formInputs = document.querySelectorAll("input, select");
            const form = document.getElementById("profileForm");
            const confirmModal = document.getElementById("confirmModal");
            const confirmSubmit = document.getElementById("confirmSubmit");
            const cancelSubmit = document.getElementById("cancelSubmit");
            const genderShow = document.getElementById("genderShow");
            const gender = document.getElementById("gender");

            const dobInput = document.getElementById("dob");
            const dobError = document.getElementById("dobError");

            submitButton.style.display = "none";
            cancelButton.style.display = "none";

            editButton.addEventListener("click", function () {
                submitButton.style.display = "inline-block";
                cancelButton.style.display = "inline-block";
                editButton.style.display = "none";
                genderShow.style.display = "none";
                gender.style.display = "inline-block";
                formInputs.forEach(input => {
                    if (input.id !== "userName")
                        input.removeAttribute("readonly");
                });
            });

            form.addEventListener("submit", function (event) {
                event.preventDefault();
                confirmModal.style.display = "block";
            });

            confirmSubmit.addEventListener("click", function () {
                form.submit();
            });

            cancelSubmit.addEventListener("click", function () {
                confirmModal.style.display = "none";
            });
            form.addEventListener("submit", function (event) {
                event.preventDefault();
                if (!validateAge()) {
                    confirmModal.style.display = "none";
                    return;
                }

            });

            function validateAge() {
                const dobValue = dobInput.value;
                if (!dobValue)
                    return false;
                const dobDate = new Date(dobValue);
                const today = new Date();
                const age = today.getFullYear() - dobDate.getFullYear();
                const monthDiff = today.getMonth() - dobDate.getMonth();
                const dayDiff = today.getDate() - dobDate.getDate();

                if (age < 13 || (age === 13 && (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)))) {
                    dobError.textContent = "You must be at least 13 years old.";
                    return false;
                }
                dobError.textContent = "";
                return true;
            }
        });
    </script>
</html>
