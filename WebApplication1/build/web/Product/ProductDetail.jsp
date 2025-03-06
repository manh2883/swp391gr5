<%-- 
    Document   : ProductListManager
    Created on : Feb 10, 2025, 8:31:22 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>${product.name}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/Images/tpfv1_reverse.ico" type="image/x-icon">

        <link href="${pageContext.request.contextPath}/CSS/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


        <style>

            .productinfo .btn {
                display: inline-block; /* Đảm bảo các nút được xếp thành dòng ngang */

            }

            .product-img {
                width: 84px;
                height: 84px;
                object-fit: cover; /* Giữ nguyên tỷ lệ, có thể có khoảng trắng */
                background-color: #f8f8f8; /* Màu nền cho khoảng trống */
            }
        </style>
    </head>
    <body>
        <header>
            <c:import url="/Template/header1.jsp" />
            <c:import url="/Template/header2.jsp" />
        </header>
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <%@ include file="/Template/left_side_bar_public.jspf" %>
                    </div><!--
                    -->

                    <div class="col-sm-9 padding-right">
                        <div class="product-details"><!--product-details-->
                            <div class="col-sm-5">
                                <c:choose>
                                    <c:when test="${not empty imgList}">
                                        <div class="view-product">
                                            <img id="main-product-img" src="${pageContext.request.contextPath}/${imgList[0][2]}" alt="${imgList[0][1]}" />
                                        </div>
                                        <div id="similar-product" class="carousel slide" data-ride="carousel">
                                            <div class="carousel-inner">

                                                <c:set var="listSize" value="${fn:length(imgList)}" />

                                                <c:forEach var="i" begin="0" end="${listSize - 1}" step="1">
                                                    <div class="item ${i == 0 ? 'active' : ''}">
                                                        <c:forEach var="j" begin="0" end="2">
                                                            <c:set var="imgIndex" value="${(i + j) % listSize}" /> 
                                                            <a href="#">
                                                                <img class="product-img" src="${pageContext.request.contextPath}/${imgList[imgIndex][2]}" 
                                                                     alt="${imgList[imgIndex][1]}">
                                                            </a>
                                                        </c:forEach>
                                                    </div>
                                                </c:forEach>

                                            </div>

                                            <a class="left item-control" href="#similar-product" data-slide="prev">
                                                <i class="fa fa-angle-left"></i>
                                            </a>
                                            <a class="right item-control" href="#similar-product" data-slide="next">
                                                <i class="fa fa-angle-right"></i>
                                            </a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="view-product">
                                            <img id="main-product-img" src="${pageContext.request.contextPath}/Images/RUN.jpg" alt="${product.name}" />
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="col-sm-7">
                                <div class="product-information">
                                    <img src=" " class="newarrival" alt="" />
                                    <h2>${product.name}</h2>
                                    <p>Web ID: ${product.productId}</p>

                                    <span>
                                        <span id="priceDisplay">${product.price.intValue()}</span>
                                    </span>

                                    <form action="AddToCart" method="post">
                                        <table>
                                            <tbody style="align-items: center;">
                                            <input readonly style="visibility: hidden" value="${product.productId}" name="idInput">

                                            <!-- Lựa chọn màu sắc -->
                                            <c:choose>
                                                <c:when test="${not empty colorList}">
                                                    <tr style="padding-top: 20px">
                                                        <td>Color:</td>
                                                        <td style="padding-left: 50px; height: 45px">
                                                            <select id="colorInput" name="colorInput">
                                                                <c:forEach var="colorItem" items="${colorList}">
                                                                    <option value="${colorItem}">${colorItem}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                            </c:choose>

                                            <!-- Lựa chọn kích thước -->
                                            <c:choose>
                                                <c:when test="${not empty sizeList}">
                                                    <tr style="padding-top: 20px">
                                                        <td>Size:</td>
                                                        <td style="padding-left: 50px; height: 45px">
                                                            <select id="sizeInput" name="sizeInput">
                                                                <c:forEach var="sizeItem" items="${sizeList}">
                                                                    <option value="${sizeItem}">${sizeItem}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                            </c:choose>

                                            <!-- Hiển thị stock -->
                                            <tr style="padding-top: 20px">

                                                <td style="padding-left: 50px; height: 45px">
                                                    <span id="stockMessage" style="font-weight: bold; color: red;">Out of stock</span>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>

                                        <span>
                                            <button type="submit" class="btn btn-fefault cart">
                                                <i class="fa fa-shopping-cart"></i> Add to cart
                                            </button>
                                            <button type="button" class="btn btn-fefault cart">
                                                <i class="fa fa-shopping-cart"></i> Purchase
                                            </button>
                                        </span>
                                    </form> 
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </div>

        </section>
        <c:import url="/Template/footer1.jsp" />
        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <script>

            document.addEventListener("DOMContentLoaded", function () {
                const carousel = document.getElementById("similar-product");
                const items = carousel.querySelectorAll(".carousel-inner .item");
                let currentIndex = 0;

                function showSlide(index) {
                    items.forEach((item, i) => {
                        item.classList.remove("active");
                        if (i === index) {
                            item.classList.add("active");
                        }
                    });
                }

                document.querySelector(".left.item-control").addEventListener("click", function (e) {
                    e.preventDefault();
                    currentIndex = (currentIndex - 1 + items.length) % items.length;
                    showSlide(currentIndex);
                });

                document.querySelector(".right.item-control").addEventListener("click", function (e) {
                    e.preventDefault();
                    currentIndex = (currentIndex + 1) % items.length;
                    showSlide(currentIndex);
                });
            });


            document.addEventListener("DOMContentLoaded", function () {
                const carousel = document.getElementById("similar-product");
                const items = carousel.querySelectorAll(".carousel-inner .item");
                const mainImg = document.getElementById("main-product-img");
                let currentIndex = 0;

                function showSlide(index) {
                    items.forEach((item, i) => {
                        item.classList.remove("active");
                        if (i === index) {
                            item.classList.add("active");
                            // Lấy ảnh đầu tiên của nhóm và cập nhật ảnh lớn
                            const firstImg = item.querySelector("img");
                            if (firstImg) {
                                mainImg.src = firstImg.src;
                                mainImg.alt = firstImg.alt;
                            }
                        }
                    });
                }

                document.querySelector(".left.item-control").addEventListener("click", function (e) {
                    e.preventDefault();
                    currentIndex = (currentIndex - 1 + items.length) % items.length;
                    showSlide(currentIndex);
                });

                document.querySelector(".right.item-control").addEventListener("click", function (e) {
                    e.preventDefault();
                    currentIndex = (currentIndex + 1) % items.length;
                    showSlide(currentIndex);
                });
            });


        </script>

        <script>
            var variantData = {};
            var priceData = {};

            <c:forEach var="variant" items="${variantList}">
            var key = "${variant[1]}-${variant[2]}";
                variantData[key] = ${variant[3]}; // Stock
                priceData[key] = ${variant[4]}; // Giá theo variant
            </c:forEach>

                function updateStockAndPrice() {
                    var color = document.getElementById("colorInput").value;
                    var size = document.getElementById("sizeInput").value;
                    var key = color + "-" + size;
                    var stock = variantData[key] || 0;
                    var price = priceData[key] || "${product.price}";


                    // Cập nhật giá
                    document.getElementById("priceDisplay").innerText = Math.floor(price);

                    // Cập nhật stock message
                    var stockMessage = document.getElementById("stockMessage");
                    var addToCartBtn = document.querySelector("button[type='submit']");
                    var purchaseBtn = document.querySelector("button[type='button']");

                    if (stock > 0) {
                        stockMessage.innerText = "In stock: " + stock + " items";
                        stockMessage.style.color = "green";
                        addToCartBtn.disabled = false;
                        purchaseBtn.disabled = false;
                    } else {
                        stockMessage.innerText = "Out of stock";
                        stockMessage.style.color = "red";
                        addToCartBtn.disabled = true;
                        purchaseBtn.disabled = true;
                    }
                }

                // Gán sự kiện thay đổi khi chọn size hoặc color
                document.getElementById("colorInput").addEventListener("change", updateStockAndPrice);
                document.getElementById("sizeInput").addEventListener("change", updateStockAndPrice);

                // Cập nhật lần đầu khi trang load
                updateStockAndPrice();
        </script>


    </body>
</html>