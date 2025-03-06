<%-- 
    Document   : ProductListManager
    Created on : Feb 10, 2025, 8:31:22 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                                <div class="view-product">
                                    <img src="${pageContext.request.contextPath}/${imgUrl}" alt="" />
                                    <!--                                    <h3>ZOOM</h3>-->
                                </div>
                                <!--                                <div id="similar-product" class="carousel slide" data-ride="carousel">
                                
                                                                     Wrapper for slides 
                                                                    <div class="carousel-inner">
                                                                        <div class="item active">
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar1.jpg" alt=""></a>
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar2.jpg" alt=""></a>
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar3.jpg" alt=""></a>
                                                                        </div>
                                                                        <div class="item">
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar1.jpg" alt=""></a>
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar2.jpg" alt=""></a>
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar3.jpg" alt=""></a>
                                                                        </div>
                                                                        <div class="item">
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar1.jpg" alt=""></a>
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar2.jpg" alt=""></a>
                                                                            <a href=""><img src="${pageContext.request.contextPath}/Images/ProductDetail/similar3.jpg" alt=""></a>
                                                                        </div>
                                
                                                                    </div>
                                
                                                                     Controls 
                                                                    <a class="left item-control" href="#similar-product" data-slide="prev">
                                                                        <i class="fa fa-angle-left"></i>
                                                                    </a>
                                                                    <a class="right item-control" href="#similar-product" data-slide="next">
                                                                        <i class="fa fa-angle-right"></i>
                                                                    </a>
                                                                </div>-->

                            </div>
                            <div class="col-sm-7">
                                <div class="product-information"><!--/product-information-->
                                    <img src=" " class="newarrival" alt="" />
                                    <h2>${product.name}</h2>
                                    <p>Web ID: ${product.productId}</p>
<!--                                    <img src="${pageContext.request.contextPath}/Images/ProductDetail/rating.png" alt="" />-->
                                    <span>
                                        <span>${product.price}</span>
                                    </span>

                                    <form action="AddToCart" method="post">
                                        <table>
                                            <tbody style="align-items: center;">
                                                <!-- Lựa chọn màu sắc -->
                                            <input readonly style ="visibility: hidden " value ="${product.productId}" name = "idInput"> 
                                            <c:choose>
                                                <c:when test="${not empty colorList}">
                                                    <tr style="padding-top: 20px">
                                                        <td>Color:</td>
                                                        <td style="padding-left: 50px; height: 45px">
                                                            <select name = "colorInput">
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
                                                            <select name = "sizeInput">
                                                                <c:forEach var="sizeItem" items="${sizeList}">
                                                                    <option value="${sizeItem}">${sizeItem}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                            </c:choose>
                                            </tbody>
                                        </table>

                                        <span>
                                            <!--<input type="text" value="1" />-->
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

                        <!--                        <div class="category-tab shop-details-tab">category-tab
                                                    <div class="col-sm-12">
                                                        <ul class="nav nav-tabs">
                                                            <li><a href="#details" data-toggle="tab">Details</a></li>
                                                            <li><a href="#companyprofile" data-toggle="tab">Company Profile</a></li>
                                                            <li><a href="#tag" data-toggle="tab">Tag</a></li>
                                                            <li class="active"><a href="#reviews" data-toggle="tab">Reviews (5)</a></li>
                                                        </ul>
                                                    </div>
                                                    <div class="tab-content">
                                                        <div class="tab-pane fade" id="details" >
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery1.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery2.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery3.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery4.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                        
                                                        <div class="tab-pane fade" id="companyprofile" >
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery1.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery3.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery2.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery4.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                        
                                                        <div class="tab-pane fade" id="tag" >
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery1.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery2.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery3.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <div class="product-image-wrapper">
                                                                    <div class="single-products">
                                                                        <div class="productinfo text-center">
                                                                            <img src="${pageContext.request.contextPath}/Images/Home/gallery4.jpg" alt="" />
                                                                            <h2>$56</h2>
                                                                            <p>Easy Polo Black Edition</p>
                                                                            <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                        
                                                        <div class="tab-pane fade active in" id="reviews" >
                                                            <div class="col-sm-12">
                                                                <ul>
                                                                    <li><a href=""><i class="fa fa-user"></i>EUGEN</a></li>
                                                                    <li><a href=""><i class="fa fa-clock-o"></i>12:41 PM</a></li>
                                                                    <li><a href=""><i class="fa fa-calendar-o"></i>31 DEC 2014</a></li>
                                                                </ul>
                                                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                                                                <p><b>Write Your Review</b></p>
                        
                                                                <form action="#">
                                                                    <span>
                                                                        <input type="text" placeholder="Your Name"/>
                                                                        <input type="email" placeholder="Email Address"/>
                                                                    </span>
                                                                    <textarea name="" ></textarea>
                                                                    <b>Rating: </b> <img src="${pageContext.request.contextPath}/Images/ProductDetail/rating.png" alt="" />
                                                                    <button type="button" class="btn btn-default pull-right">
                                                                        Submit
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </div>
                        
                                                    </div>
                                                </div>-->
                        <!--/category-tab-->
                        <!--
                                                <div class="recommended_items">recommended_items
                                                    <h2 class="title text-center">recommended items</h2>
                        
                                                    <div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
                                                        <div class="carousel-inner">
                                                            <div class="item active">	
                                                                <div class="col-sm-4">
                                                                    <div class="product-image-wrapper">
                                                                        <div class="single-products">
                                                                            <div class="productinfo text-center">
                                                                                <img src="${pageContext.request.contextPath}/Images/Home/recommend1.jpg" alt="" />
                                                                                <h2>$56</h2>
                                                                                <p>Easy Polo Black Edition</p>
                                                                                <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-4">
                                                                    <div class="product-image-wrapper">
                                                                        <div class="single-products">
                                                                            <div class="productinfo text-center">
                                                                                <img src="${pageContext.request.contextPath}/Images/Home/recommend2.jpg" alt="" />
                                                                                <h2>$56</h2>
                                                                                <p>Easy Polo Black Edition</p>
                                                                                <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-4">
                                                                    <div class="product-image-wrapper">
                                                                        <div class="single-products">
                                                                            <div class="productinfo text-center">
                                                                                <img src="${pageContext.request.contextPath}/Images/Home/recommend3.jpg" alt="" />
                                                                                <h2>$56</h2>
                                                                                <p>Easy Polo Black Edition</p>
                                                                                <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="item">	
                                                                <div class="col-sm-4">
                                                                    <div class="product-image-wrapper">
                                                                        <div class="single-products">
                                                                            <div class="productinfo text-center">
                                                                                <img src="${pageContext.request.contextPath}/Images/Home/recommend1.jpg" alt="" />
                                                                                <h2>$56</h2>
                                                                                <p>Easy Polo Black Edition</p>
                                                                                <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-4">
                                                                    <div class="product-image-wrapper">
                                                                        <div class="single-products">
                                                                            <div class="productinfo text-center">
                                                                                <img src="${pageContext.request.contextPath}/Images/Home/recommend2.jpg" alt="" />
                                                                                <h2>$56</h2>
                                                                                <p>Easy Polo Black Edition</p>
                                                                                <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-4">
                                                                    <div class="product-image-wrapper">
                                                                        <div class="single-products">
                                                                            <div class="productinfo text-center">
                                                                                <img src="${pageContext.request.contextPath}/Images/Home/recommend3.jpg" alt="" />
                                                                                <h2>$56</h2>
                                                                                <p>Easy Polo Black Edition</p>
                                                                                <button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
                                                            <i class="fa fa-angle-left"></i>
                                                        </a>
                                                        <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
                                                            <i class="fa fa-angle-right"></i>
                                                        </a>			
                                                    </div>
                                                </div>/recommended_items-->

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
    </body>
</html>