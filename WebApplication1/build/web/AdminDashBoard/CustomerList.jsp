<%-- 
    Document   : CustomerList
    Created on : Feb 27, 2025, 8:50:22 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Doctris - Doctor Appointment Booking System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="../../../index.html" />
        <meta name="Version" content="v1.2.0" />
        <!-- favicon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/Images/Doctor/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/CSS/dashboard/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- simplebar -->
        <link href="${pageContext.request.contextPath}/CSS/dashboard/simplebar.css" rel="stylesheet" type="text/css" />
        <!-- Select2 -->
        <link href="${pageContext.request.contextPath}/CSS/dashboard/select2.min.css" rel="stylesheet" />
        <!-- Date picker -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/dashboard/flatpickr.min.css">
        <link href="${pageContext.request.contextPath}/CSS/dashboard/jquery.timepicker.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons -->
        <link href="${pageContext.request.contextPath}/CSS/dashboard/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/dashboard/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/CSS/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="${pageContext.request.contextPath}/CSS/dashboard/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />

    </head>

    <body>
        <!-- Loader -->
        <div id="preloader">
            <div id="status">
                <div class="spinner">
                    <div class="double-bounce1"></div>
                    <div class="double-bounce2"></div>
                </div>
            </div>
        </div>
        <!-- Loader -->
        <header>
            <c:import url="/Template/header1.jsp" />
            <c:import url="/Template/header2.jsp" />
        </header>
        <div class="page-wrapper doctris-theme toggled">



            <!-- Start Page Content -->
            <main class="page-content bg-light">

                <div class="col-sm-3">
                    <%@ include file="/Template/left_side_bar_admin.jspf" %>
                </div>
                <div class="row">
                    <div class="col-12 mt-4">
                        <div class="table-responsive bg-white shadow rounded">
                            <table class="table mb-0 table-center">
                                <thead>
                                    <tr>
                                        <th class="border-bottom p-3" style="min-width: 50px;">#</th>
                                        <th class="border-bottom p-3" style="min-width: 180px;">Name</th>
                                        <th class="border-bottom p-3" style="min-width: 150px;">Email</th>
                                        <th class="border-bottom p-3">Age</th>
                                        <th class="border-bottom p-3">Gender</th>
                                        <th class="border-bottom p-3">Department</th>
                                        <th class="border-bottom p-3" style="min-width: 150px;">Date</th>
                                        <th class="border-bottom p-3">Time</th>
                                        <th class="border-bottom p-3" style="min-width: 220px;">Doctor</th>
                                        <th class="border-bottom p-3">Fees</th>
                                        <th class="border-bottom p-3" style="min-width: 150px;"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th class="p-3">1</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/01.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Howard Tanner</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">howard@contact.com</td>
                                        <td class="p-3">25</td>
                                        <td class="p-3">Male</td>
                                        <td class="p-3">Cardiology</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/01.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Calvin Carlo</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">2</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/02.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Wendy Filson</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">wendy@contact.com</td>
                                        <td class="p-3">28</td>
                                        <td class="p-3">Female</td>
                                        <td class="p-3">Gynecology</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/02.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Cristino Murphy</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">3</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/03.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Faye Bridger</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">faye@contact.com</td>
                                        <td class="p-3">28</td>
                                        <td class="p-3">Female</td>
                                        <td class="p-3">Psychotherapy</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/03.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Alia Reddy</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">4</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/04.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Ronald Curtis</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">ronald@contact.com</td>
                                        <td class="p-3">25</td>
                                        <td class="p-3">Male</td>
                                        <td class="p-3">Orthopedic</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/04.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Toni Kovar</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">5</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/05.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Melissa Hibner</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">melissa@contact.com</td>
                                        <td class="p-3">28</td>
                                        <td class="p-3">Female</td>
                                        <td class="p-3">Dental</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/05.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Jessica McFarlane</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">6</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/06.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Randall Case</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">randall@contact.com</td>
                                        <td class="p-3">25</td>
                                        <td class="p-3">Male</td>
                                        <td class="p-3">Orthopedic</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/04.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Toni Kovar</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">7</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/07.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Jerry Morena</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">jerry@contact.com</td>
                                        <td class="p-3">25</td>
                                        <td class="p-3">Male</td>
                                        <td class="p-3">Dentist</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/05.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Jessica McFarlane</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">8</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/08.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Lester McNally</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">jessica@contact.com</td>
                                        <td class="p-3">25</td>
                                        <td class="p-3">Male</td>
                                        <td class="p-3">Gastrology</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/06.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Elsie Sherman</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">9</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/09.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Christopher Burrell</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">christopher@contact.com</td>
                                        <td class="p-3">28</td>
                                        <td class="p-3">Female</td>
                                        <td class="p-3">Urology</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/07.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Bertha Magers</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>

                                    <tr>
                                        <th class="p-3">10</th>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/client/10.jpg" class="avatar avatar-md-sm rounded-circle shadow" alt="">
                                                    <span class="ms-2">Mary Skeens</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">mary@contact.com</td>
                                        <td class="p-3">28</td>
                                        <td class="p-3">Female</td>
                                        <td class="p-3">Neurology</td>
                                        <td class="p-3">20th Dec 2020</td>
                                        <td class="p-3">11:00AM</td>
                                        <td class="p-3">
                                            <a href="#" class="text-dark">
                                                <div class="d-flex align-items-center">
                                                    <img src="${pageContext.request.contextPath}/Images/Doctor/doctors/08.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                                    <span class="ms-2">Dr. Louis Batey</span>
                                                </div>
                                            </a>
                                        </td>
                                        <td class="p-3">$50/Patient</td>
                                        <td class="text-end p-3">
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-primary" data-bs-toggle="modal" data-bs-target="#viewappointment"><i class="uil uil-eye"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-success" data-bs-toggle="modal" data-bs-target="#acceptappointment"><i class="uil uil-check-circle"></i></a>
                                            <a href="#" class="btn btn-icon btn-pills btn-soft-danger" data-bs-toggle="modal" data-bs-target="#cancelappointment"><i class="uil uil-times-circle"></i></a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div><!--end row-->




            </main>
            <!--End page-content" -->

            <!-- page-wrapper -->

            <!-- Offcanvas Start -->
            <div class="offcanvas offcanvas-end bg-white shadow" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
                <div class="offcanvas-header p-4 border-bottom">
                    <h5 id="offcanvasRightLabel" class="mb-0">
                        <img src="${pageContext.request.contextPath}/Images/Doctor/logo-dark.png" height="24" class="light-version" alt="">
                        <img src="${pageContext.request.contextPath}/Images/Doctor/logo-light.png" height="24" class="dark-version" alt="">
                    </h5>
                    <button type="button" class="btn-close d-flex align-items-center text-dark" data-bs-dismiss="offcanvas" aria-label="Close"><i class="uil uil-times fs-4"></i></button>
                </div>
                <div class="offcanvas-body p-4 px-md-5">
                    <div class="row">
                        <div class="col-12">
                            <!-- Style switcher -->
                            <div id="style-switcher">
                                <div>
                                    <ul class="text-center list-unstyled mb-0">
                                        <li class="d-grid"><a href="javascript:void(0)" class="rtl-version t-rtl-light" onclick="setTheme('style-rtl')"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/light-dash-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                        <li class="d-grid"><a href="javascript:void(0)" class="ltr-version t-ltr-light" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                        <li class="d-grid"><a href="javascript:void(0)" class="dark-rtl-version t-rtl-dark" onclick="setTheme('style-dark-rtl')"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/dark-dash-rtl.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">RTL Version</span></a></li>
                                        <li class="d-grid"><a href="javascript:void(0)" class="dark-ltr-version t-ltr-dark" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/dark-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">LTR Version</span></a></li>
                                        <li class="d-grid"><a href="javascript:void(0)" class="dark-version t-dark mt-4" onclick="setTheme('style-dark')"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/dark-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Dark Version</span></a></li>
                                        <li class="d-grid"><a href="javascript:void(0)" class="light-version t-light mt-4" onclick="setTheme('style')"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/light-dash.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Light Version</span></a></li>
                                        <li class="d-grid"><a href="../landing/index.html" target="_blank" class="mt-4"><img src="${pageContext.request.contextPath}/Images/Doctor/layouts/landing-light.png" class="img-fluid rounded-md shadow-md d-block" alt=""><span class="text-muted mt-2 d-block">Landing Demos</span></a></li>
                                    </ul>
                                </div>
                            </div>
                            <!-- end Style switcher -->
                        </div><!--end col-->
                    </div><!--end row-->
                </div>

                <div class="offcanvas-footer p-4 border-top text-center">
                    <ul class="list-unstyled social-icon mb-0">
                        <li class="list-inline-item mb-0"><a href="https://1.envato.market/doctris-template" target="_blank" class="rounded"><i class="uil uil-shopping-cart align-middle" title="Buy Now"></i></a></li>
                        <li class="list-inline-item mb-0"><a href="https://dribbble.com/shreethemes" target="_blank" class="rounded"><i class="uil uil-dribbble align-middle" title="dribbble"></i></a></li>
                        <li class="list-inline-item mb-0"><a href="https://www.facebook.com/shreethemes" target="_blank" class="rounded"><i class="uil uil-facebook-f align-middle" title="facebook"></i></a></li>
                        <li class="list-inline-item mb-0"><a href="https://www.instagram.com/shreethemes/" target="_blank" class="rounded"><i class="uil uil-instagram align-middle" title="instagram"></i></a></li>
                        <li class="list-inline-item mb-0"><a href="https://twitter.com/shreethemes" target="_blank" class="rounded"><i class="uil uil-twitter align-middle" title="twitter"></i></a></li>
                        <li class="list-inline-item mb-0"><a href="mailto:support@shreethemes.in" class="rounded"><i class="uil uil-envelope align-middle" title="email"></i></a></li>
                        <li class="list-inline-item mb-0"><a href="../../../index.html" target="_blank" class="rounded"><i class="uil uil-globe align-middle" title="website"></i></a></li>
                    </ul><!--end icon-->
                </div>
            </div>
            <!-- comment -->

            <!-- javascript -->
            <script src="${pageContext.request.contextPath}/js/dashboard/jquery.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/dashboard/bootstrap.bundle.min.js"></script>
            <!-- simplebar -->
            <script src="${pageContext.request.contextPath}/js/dashboard/simplebar.min.js"></script>
            <!-- Select2 -->
            <script src="${pageContext.request.contextPath}/js/dashboard/select2.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/dashboard/select2.init.js"></script>
            <!-- Datepicker -->
            <script src="${pageContext.request.contextPath}/js/dashboard/flatpickr.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/dashboard/flatpickr.init.js"></script>
            <!-- Datepicker -->
            <script src="${pageContext.request.contextPath}/js/dashboard/jquery.timepicker.min.js"></script> 
            <script src="${pageContext.request.contextPath}/js/dashboard/timepicker.init.js"></script> 
            <!-- Icons -->
            <script src="${pageContext.request.contextPath}/js/dashboard/feather.min.js"></script>
            <!-- Main Js -->
            <script src="${pageContext.request.contextPath}/js/dashboard/app.js"></script>

    </body>

</html>