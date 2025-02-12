
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Checkout</title>
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
        </style>
    </head>

    <body>
        <c:import url="/Template/header1.jsp" />
        <c:import url="/Template/header2.jsp" />
        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">Check out</li>
                    </ol>
                </div><!--/breadcrums-->

                <div class="checkout-informations">
                    <div class="row">
                        <div class="col-sm-7 clearfix">
                            <div class="bill-to">
                                <p>Bill To</p>
                                <div class="form-one">
                                    <form>
                                        <input type="text" placeholder="Email*">
                                        <input type="text" placeholder="Name *">
                                        <input type="text" placeholder="Address 1 *">
                                        <input type="text" placeholder="Address 2">
                                    </form>
                                </div>
                                <div class="form-two">
                                    <form>
                                        <input type="text" placeholder="Zip / Postal Code *">
                                        <select>
                                            <option>-- Address --</option>
                                            <option>United States</option>
                                            <option>Bangladesh</option>
                                            <option>UK</option>
                                            <option>India</option>
                                            <option>Pakistan</option>
                                            <option>Ucrane</option>
                                            <option>Canada</option>
                                            <option>Dubai</option>
                                        </select>
                                        <input type="text" placeholder="Phone *">
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div class="order-message">
                                <p>Shipping Order</p>
                                <textarea name="message"  placeholder="Notes about your order, Special Notes for Delivery" rows="16"></textarea>
                                <label><input type="checkbox"> Shipping to bill address</label>
                            </div>	
                        </div>					
                    </div>
                </div>
                <div class="review-payment">
                    <h2>Review & Payment</h2>
                </div>

                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Item</td>
                                <td class="description"></td>
                                <td class="price">Price</td>
                                <td class="quantity">Quantity</td>
                                <td class="total">Total</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="cart_product">
                                    <a href=""><img src="images/cart/one.png" alt=""></a>
                                </td>
                                <td class="cart_description">
                                    <h4><a href="">Colorblock Scuba</a></h4>
                                    <p>Web ID: 1089772</p>
                                </td>
                                <td class="cart_price">
                                    <p>$59</p>
                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <a class="cart_quantity_up" href=""> + </a>
                                        <input class="cart_quantity_input" type="text" name="quantity" value="1" autocomplete="off" size="2">
                                        <a class="cart_quantity_down" href=""> - </a>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p class="cart_total_price">$59</p>
                                </td>
                                <td class="cart_delete">
                                    <a class="cart_quantity_delete" href=""><i class="fa fa-times"></i></a>
                                </td>
                            </tr>

                            <tr>
                                <td class="cart_product">
                                    <a href=""><img src="images/cart/two.png" alt=""></a>
                                </td>
                                <td class="cart_description">
                                    <h4><a href="">Colorblock Scuba</a></h4>
                                    <p>Web ID: 1089772</p>
                                </td>
                                <td class="cart_price">
                                    <p>$59</p>
                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <a class="cart_quantity_up" href=""> + </a>
                                        <input class="cart_quantity_input" type="text" name="quantity" value="1" autocomplete="off" size="2">
                                        <a class="cart_quantity_down" href=""> - </a>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p class="cart_total_price">$59</p>
                                </td>
                                <td class="cart_delete">
                                    <a class="cart_quantity_delete" href=""><i class="fa fa-times"></i></a>
                                </td>
                            </tr>
                            <tr>
                                <td class="cart_product">
                                    <a href=""><img src="images/cart/three.png" alt=""></a>
                                </td>
                                <td class="cart_description">
                                    <h4><a href="">Colorblock Scuba</a></h4>
                                    <p>Web ID: 1089772</p>
                                </td>
                                <td class="cart_price">
                                    <p>$59</p>
                                </td>
                                <td class="cart_quantity">
                                    <div class="cart_quantity_button">
                                        <a class="cart_quantity_up" href=""> + </a>
                                        <input class="cart_quantity_input" type="text" name="quantity" value="1" autocomplete="off" size="2">
                                        <a class="cart_quantity_down" href=""> - </a>
                                    </div>
                                </td>
                                <td class="cart_total">
                                    <p class="cart_total_price">$59</p>
                                </td>
                                <td class="cart_delete">
                                    <a class="cart_quantity_delete" href=""><i class="fa fa-times"></i></a>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">&nbsp;</td>
                                <td colspan="2">
                                    <table class="table table-condensed total-result">
                                        <tr>
                                            <td>Cart Sub Total</td>
                                            <td>$59</td>
                                        </tr>
                                        <tr>
                                            <td>Exo Tax</td>
                                            <td>$2</td>
                                        </tr>
                                        <tr class="shipping-cost">
                                            <td>Shipping Cost</td>
                                            <td>Free</td>										
                                        </tr>
                                        <tr>
                                            <td>Total</td>
                                            <td><span>$61</span></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="payment-options">
                    <span>
                        <label><input type="checkbox"> Direct Bank Transfer</label>
                    </span>
                    <span>
                        <label><input type="checkbox"> Check Payment</label>
                    </span>
                    <span>
                        <label><input type="checkbox"> Paypal</label>
                    </span>
                </div>
            </div>
        </section> <!--/#cart_items-->
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
        <c:import url="/Template/footer1.jsp" />
    </body>
</html>
