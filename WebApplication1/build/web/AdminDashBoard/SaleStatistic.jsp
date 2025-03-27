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

            tr:hover {
                background-color: #f5f5f5;
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
                        <li><a href="${pageContext.request.contextPath}">Home</a></li>
                        <li class="active">Sale Statistic</li>
                    </ol>
                </div>

                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <h2 class="title text-center">Sale Statistic</h2>
                        <form action="SaleStatistic" method="get">
                            <table>
                                <tbody>
                                    <tr>
                                        <td><label for="startDate">Start Date:</label></td>
                                        <td><input type="date" id="startDate" name="startDate" value="${startDate}" style="color: black;width: 100%;
                                                   height: 35px;margin-bottom: 10px;">
                                        </td>
                                        <td><label for="endDate">End Date:</label></td>
                                        <td><input type="date" id="endDate" name="endDate" value="${endDate}" style="color: black;width: 100%;
                                                   height: 35px;margin-bottom: 10px;">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-default get-manh" style="margin-bottom: 10px">Load Data</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>



                        </form>
                        <canvas id="orderChart"></canvas>
                        <br>
                        <br>
                        <h2 class="title text-center">Top Product</h2>
                        <form action="SaleStatistic" method="get">
                            <input type="hidden" name="startDate" value="${startDate}">
                            <input type="hidden" name="endDate" value="${endDate}">
                            <table>
                                <tbody>
                                    <tr>
                                        <td>
                                            <label for="sortBy">Sort by:</label>
                                        </td>
                                        <td>
                                            <select name="sortBy" id="sortBy" style="height: 35px;margin-bottom: 10px;">
                                                <option value="revenue" ${sortBy == 'revenue' ? 'selected' : ''}>Revenue</option>
                                                <option value="quantity_sold" ${sortBy == 'quantity_sold' ? 'selected' : ''}>Quantity Sold</option>
                                            </select>
                                        </td>
                                        <td>
                                            <label for="order">Order:</label>
                                        </td>
                                        <td>
                                            <select name="order" id="order" style="height: 35px;margin-bottom: 10px;">
                                                <option value="desc" ${order == 'desc' ? 'selected' : ''}>Descending</option>
                                                <option value="asc" ${order == 'asc' ? 'selected' : ''}>Ascending</option>
                                            </select>
                                        </td>
                                        <td>
                                            <label for="top">Limit:</label>
                                        </td>
                                        <td>
                                            <select name="top" id="top" style="height: 35px;margin-bottom: 10px;">
                                                <option value="3" ${top == 3 ? 'selected' : ''}>Top 3</option>
                                                <option value="5" ${top == 5 ? 'selected' : ''}>Top 5</option>
                                                <option value="7" ${top == 10 ? 'selected' : ''}>Top 10</option>
                                            </select>
                                        </td>
                                        <td><button type="submit" class="btn btn-default get-manh" style="margin-bottom: 10px">Filter</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                        <div class="table-responsive cart_info">
                            <table class="table table-condensed">
                                <thead>
                                    <tr>
                                        <th>Product ID</th>
                                        <th>Product Name</th>
                                        <th>Quantity Sold</th>
                                        <th>Revenue</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="product" items="${topProducts}">
                                        <tr style="cursor: pointer;" 
                                            onclick="window.location = '${pageContext.request.contextPath}/MarketingDashBoard?product_id=${product.product_id}'">
                                            <td>${product.product_id}</td>
                                            <td>${product.product_name}</td>
                                            <td>${product.quantity_sold}</td>
                                            <td>${product.revenue}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </section> <!--/#cart_items-->
        <br>
        <br>
        <c:import url="/Template/footer1.jsp" />
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const ctx = document.getElementById('orderChart').getContext('2d');
                const labels = [];
                const totalOrders = [];
                const successOrders = [];

            <c:forEach var="stat" items="${orderStats}">
                labels.push("${stat.order_date}");
                totalOrders.push(${stat.total_orders});
                successOrders.push(${stat.success_orders});
            </c:forEach>

                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: 'Total Orders',
                                data: totalOrders,
                                backgroundColor: 'rgba(54, 162, 235, 0.6)'
                            },
                            {
                                label: 'Successful Orders',
                                data: successOrders,
                                backgroundColor: 'rgba(75, 192, 192, 0.6)'
                            }
                        ]
                    }
                });
            });
        </script>
    </body>
</html>
