<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Sale Statistic</title>
<!--<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/login_css.css"/>-->
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <style>
            .form-control{
                height:50px;
            }

            .variant-section {
                border: 1px solid #ddd;
                padding: 10px;
                position: relative;
            }
            .remove-btn {
                /*position: absolute;*/
                cursor: pointer;
                background-color: crimson;
                color: white;
                border: none;
                padding: 5px;
                margin-bottom:  10px;
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
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
                        <li><a href="#">Home</a></li>
                        <li class="active">${breadcrumbs}</li>
                    </ol>
                </div>

                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>
                    <div class="col-sm-9">
                        <h2 class="title text-center">Marketing - Product Price Management</h2>
                        <form action="MarketingDashBoard" method="get">
                            <label>Product ID:</label>
                            <input type="text" name="product_id" value="${productId}" required>
                            <button type="submit" class="btn btn-default get-manh" style="margin-bottom: 10px">Search</button>
                        </form>

                        <br>
                        <br>
                        <c:if test="${not empty productList}">
                            <h3 class="text-center">Sales Stats (Last 7 Days)</h3>
                            <canvas id="salesChart" width="800" height="400"></canvas>
                            <table>
                                <tr><th>Product ID</th>
                                    <th>Variant ID</th>
                                    <th>Color</th>
                                    <th>Size</th>
                                    <th>Current Price</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                </tr>

                                <c:forEach var="product" items="${productList}">
                                    <tr>
                                    <form action="MarketingDashBoard" method="post">
                                        <td>
                                            <input type="hidden" name="product_id" value="${product.product_id}">
                                            ${product.product_id}
                                        </td>
                                        <td>
                                            <input type="hidden" name="variant_id" value="${product.variant_id}">
                                            ${product.variant_id}
                                        </td>
                                        <td>${product.color}</td>
                                        <td>${product.size}</td>
                                        <td>
                                            <input type="number" name="new_price" value="${product.price}">
                                        </td>
                                        <td>
                                            <input type="date" name="start_date" value="${product.start_price_date}">
                                        </td>
                                        <td>
                                            <input type="date" name="end_date" value="${product.end_price_date}">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-default get-manh" style="margin-bottom: 10px">Update</button>
                                        </td>
                                    </form>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
        </section> <!--/#cart_items-->
        <br>
        <br>
        <c:import url="/Template/footer1.jsp" />
    </body>
    <script>
        const labels = [
        <c:forEach var="item" items="${stats}" varStatus="loop">
            "${item[0]}",
        </c:forEach>
        ];

        const quantities = [
        <c:forEach var="item" items="${stats}">
            ${item[1]},
        </c:forEach>
        ];

        const revenues = [
        <c:forEach var="item" items="${stats}">
            ${item[2]},
        </c:forEach>
        ];

        const ctx = document.getElementById('salesChart').getContext('2d');
        const maxRevenue = Math.max(...revenues); // Tìm giá trị lớn nhất
        const yRevenueMax = Math.ceil(maxRevenue * 1.2); // Làm tròn cho đẹp (nếu cần)

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        type: 'bar',
                        label: 'Revenue (VND)',
                        data: revenues,
                        backgroundColor: 'rgba(75, 192, 192, 0.7)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        yAxisID: 'yRevenue'
                    },
                    {
                        type: 'line',
                        label: 'Quantity Sold',
                        data: quantities,
                        borderColor: 'rgba(255, 99, 132, 1)',
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderWidth: 2,
                        fill: false,
                        yAxisID: 'yQuantity',
                        tension: 0.3
                    }
                ]
            },
            options: {
                responsive: true,
                scales: {
                    yRevenue: {
                        type: 'linear',
                        position: 'left',
                        title: {
                            display: true,
                            text: 'Revenue (VND)'
                        },
                        beginAtZero: true,
                        max: yRevenueMax, // gán max theo biến tính được
                        ticks: {
                            callback: function (value) {
                                return new Intl.NumberFormat('vi-VN').format(value) + ' VNĐ';
                            }
                        }
                    },
                    yQuantity: {
                        type: 'linear',
                        position: 'right',
                        title: {
                            display: true,
                            text: 'Quantity Sold'
                        },
                        ticks: {
                            callback: function (value) {
                                return value + ' sp';
                            },
                            stepSize: 5 // tuỳ chọn: hiển thị mỗi 5 đơn vị
                        },
                        min: 0,
                        max: 25,
                        grid: {
                            drawOnChartArea: false
                        }
                    }
                }
            }
        });
    </script>
</html>
