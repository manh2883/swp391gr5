<%-- 
    Document   : left_side_bar_public
    Created on : Feb 11, 2025, 11:56:28 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <!--        <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="description" content="">
                <meta name="author" content="">
                <title>Login | E-Shopper</title>
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
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">-->
                <link href="${pageContext.request.contextPath}/CSS/footer.css" rel="stylesheet">        
        
    </head>
                        <div class="left-sidebar" >
                            <h2>Category</h2>
                            <div class="panel-group category-products" id="accordian" >
                              
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title  active" ><a href="#">Kids</a></h4>
                                    </div>
                                </div>
                                
                            </div>

                            <div class="brands_products">
                                <h2>Brands</h2>
                                <div class="brands-name">
                                    <ul class="nav nav-pills nav-stacked">
                                        <li><a href="#"> <span class="pull-right">(50)</span>Acne</a></li>
                                    
                                    </ul>
                                </div>
                            </div>

<!--                            <div class="price-range">
                                <h2>Price Range</h2>
                                <div class="well text-center">
                                    <input type="text" class="span2" value="" data-slider-min="0" data-slider-max="600" data-slider-step="5" data-slider-value="[250,450]" id="sl2" ><br />
                                    <b class="pull-left">$ 0</b> <b class="pull-right">$ 600</b>
                                </div>
                            </div>-->

                            <div class="shipping text-center">
                                <img src="${pageContext.request.contextPath}/Images/Home/shipping.jpg" alt="" />
                            </div>
                        </div>
</html>