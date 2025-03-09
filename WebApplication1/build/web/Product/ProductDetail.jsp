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

            /* Ẩn modal mặc định */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%) scale(0.8);
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                text-align: center;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
                opacity: 0;
                transition: opacity 0.5s ease-in-out, transform 0.5s ease-in-out;
            }

            /* Khi modal hiển thị */
            .modal.show {
                opacity: 1;
                transform: translate(-50%, -50%) scale(1);
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
                                    <c:choose>
                                        <c:when test="${not empty tag}">
                                            <c:choose>
                                                <c:when test="${tag == 'isSale'}">
                                                    <img src="${pageContext.request.contextPath}/Images/ProductDetail/sale.png" class="newarrival" alt="">
                                                </c:when>
                                                <c:when test="${tag == 'isNew'}">
                                                    <img src="${pageContext.request.contextPath}/Images/ProductDetail/new.jpg" class="newarrival" alt="">
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>

                                    <h2>${product.name}</h2>
                                    <p>Web ID: ${product.productId}</p>

                                    <h2>
                                        <c:set var="currentPrice" value="${product.price.intValue()}" />
                                        <c:choose>
                                            <c:when test="${not empty netPrice}">

                                                <span id="priceDisplay" style="padding-right: 10px;
                                                      font-size: 30px;
                                                      color: #fe980f;"
                                                      >${product.price.intValue()}</span> 
                                                <span id ="netPrice" style="text-decoration: line-through;
                                                      padding-right: 10px;
                                                      font-size: 25px;
                                                      color: gray;
                                                      align-content: baseline">${netPrice.intValue()}</span>

                                            </c:when>
                                            <c:otherwise>
                                                <span id="priceDisplay">${product.price.intValue()}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </h2>

                                    <form action="AddToCart" method="post" id="productForm">
                                        <table>
                                            <tbody style="align-items: center;">
                                            <input readonly style="visibility: hidden" value="${product.productId}" name="idInput" id="idInput">

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

                                            </tbody>
                                        </table>
                                        <h4 style="padding-top: 20px">

                                            <p style="; height: 45px">
                                                <span id="stockMessage" style="font-weight: bold; color: red;">Out of stock</span>
                                            </p>
                                        </h4>

                                        <c:choose>
                                            <c:when test="${not empty addMessage}">
                                                <h5 style="padding-left: 50px">
                                                    <p>${addMessage}</p>
                                                </h5>
                                            </c:when>
                                        </c:choose>

                                        <span>
                                            <button type="submit" class="btn btn-fefault cart">
                                                <i class="fa fa-shopping-cart"></i> Add to cart
                                            </button>
                                            <button type="button" class="btn btn-fefault cart " onclick="submitPurchase()">
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
            function submitPurchase() {
                var form = document.getElementById("productForm");
                form.action = "PurchaseProduct"; // Đổi action
                form.submit(); // Submit form
            }
        </script>
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
            var netPrice = ${netPrice}; // Giá trị tĩnh

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
                    var price = priceData[key] || parseFloat("${product.price}"); // Giá thay đổi theo variant

                    console.log("Price:", price, "NetPrice:", netPrice, "Stock:", stock);
                    // Cập nhật giá hiển thị
                    var priceDisplay = document.getElementById("priceDisplay");
                    var netPriceDisplay = document.getElementById("netPrice");
                    priceDisplay.innerText = Math.floor(price);
                    if (netPrice > price) {
                        netPriceDisplay.innerText = Math.floor(netPrice);
                        netPriceDisplay.style.display = "inline"; // Hiện netPrice bị gạch
                    } else {
                        netPriceDisplay.style.display = "none"; // Ẩn nếu không có giảm giá
                    }

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
        <script>
            function addToCart() {
                var productId = document.getElementById("productId").value;
                var color = document.getElementById("colorInput").value;
                var size = document.getElementById("sizeInput").value;
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "AddToCartServlet", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        try {
                            console.log("Raw response:", xhr.responseText); // Kiểm tra dữ liệu trả về

                            var response = JSON.parse(xhr.responseText);
                            console.log("Parsed response:", response);
                            if (response.success) {
                                var modal = document.getElementById("successModal");
                                if (modal) {
                                    modal.style.display = "block"; // Hiển thị modal

                                    // Tự động đóng modal sau 1 giây và chuyển hướng
                                    setTimeout(function () {
                                        modal.style.display = "none";
                                        window.location.href = response.redirectUrl; // Điều hướng về trang Product Detail
                                    }, 1000);
                                } else {
                                    console.error("Không tìm thấy modal!");
                                }
                            } else {
                                console.error("Response success=false:", response);
                            }
                        } catch (e) {
                            console.error("Lỗi parse JSON:", e, xhr.responseText);
                        }
                    }
                };
                xhr.send("idInput=" + encodeURIComponent(productId) +
                        "&colorInput=" + encodeURIComponent(color) +
                        "&sizeInput=" + encodeURIComponent(size));
                return false; // Ngăn form submit mặc định
            }

        </script>

    </body>
</html>