
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Product Creator</title>
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
                        <li><a href="${pageContext.request.contextPath}/ProductList">Product List</a></li>
                        <li class="active">Product Creator</li>
                    </ol>
                </div>
                <div class="row" >
                    <div class="col-sm-3">
                        <%@ include file="/Template/auto_left_side_bar.jspf" %>
                    </div>

                    <div class="col-sm-9">
                        <h2 class="title text-center">Product Creator</h2>
                        <form id="productForm" method="post" enctype="multipart/form-data">
                            <table class="col-12  " >
                                <tbody>
                                    <tr style="visibility: hidden" >
                                        <td class="col-3 text-end">
                                        </td>
                                        <td class=""></td>
                                        <td class="col-7">

                                        </td>
                                    </tr>
                                    <tr  >
                                        <td class="text-end">
                                            <label>Name:</label>
                                        </td>
                                        <td class=""></td>
                                        <td class=" text-start col-12" style="padding-bottom: 10px">
                                            <input type="text" id="name" class="form-control mt-2" name="name" required 
                                                   style="color: black;width: 100%;
                                                   height: 35px;margin-bottom: 10px;">
                                            <div class="text-danger" id="nameError"></div>
                                        </td>
                                    </tr>

                                    <tr style="padding-bottom: 20px">
                                        <td class= "text-end">
                                            <label>Description:</label>
                                        </td>
                                        <td></td>
                                        <td class=" text-start col-12" style="padding-bottom: 10px;height: max-content">
                                            <input type="text" id="description" class="form-control mt-2" name="description" style="color: black;width: 100%;
                                                   height: 35px;margin-bottom: 10px;">
                                            <div class="text-danger" id="descriptionError"></div>
                                        </td>
                                    </tr >
                                    <tr style="padding-bottom: 20px">
                                        <td class=" text-end">
                                            <label>Price:</label>
                                        </td>
                                        <td></td>
                                        <td class=" text-start col-12" style="padding-bottom: 10px">
                                            <input type="number" id="price" class="form-control mt-2"
                                                   name="price" style="color: black;width: 100%;
                                                   height: 35px;margin-bottom: 10px;" required>
                                            <div class="text-danger" id="priceError"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="visibility: hidden"> 1</td>
                                        <td> </td>
                                        <td></td>
                                    </tr>
                                    <tr style="padding-bottom: 20px">
                                        <td class=" text-end">
                                            <label>Brand:</label>
                                        </td>
                                        <td></td>
                                        <td class=" text-start">
                                            <div class="form-floating mb-10 col-12">
                                                <select name="brandInput" id="brand" 
                                                        style="height: 35px;margin-bottom: 10px;" onchange="toggleBrandInput(this)">
                                                    <c:choose>
                                                        <c:when test="${not empty brandDropList}">
                                                            <c:forEach var="brand" items="${brandDropList}">
                                                                <option value="${brand[0]}">${brand[1]}</option>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                    <option value="Other">-- Other --</option>
                                                </select>

                                                <span id="newBrandError" class="error-message"></span>

                                                <input   type="text" name="newBrand" id="newBrand" class="form-control mt-2"
                                                         style="height: 35px;margin-bottom: 10px; display:none;"
                                                         placeholder="Enter new Brand" />
                                                <!--<label for="newBrand">New Brand</label>-->
                                                <div class="text-danger" id="brandError"></div>
                                            </div>  
                                        </td>
                                    </tr>


                                    <tr>
                                        <td class=" text-end " style="align-top" >
                                            <label>Category:</label>
                                        </td>
                                        <td></td>
                                        <td>
                                            <div class="form-floating mb-10 col-12">
                                                <select name="categoryInput" id="category" 
                                                        style="height: 35px;margin-bottom: 10px;" onchange="toggleCategoryInput(this)">
                                                    <c:choose>
                                                        <c:when test="${not empty categoryDropList}">
                                                            <c:forEach var="category" items="${categoryDropList}">
                                                                <option value="${category.key}">${category.value}</option>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                    <option value="Other">-- Other --</option>
                                                </select>

                                                <span id="newCategoryError" class="error-message"></span>

                                                <input   type="text" name="newCategory" id="newCategory" class="form-control mt-2"
                                                         style="height: 35px;margin-bottom: 10px; display:none;"
                                                         placeholder="Enter new Category" />


                                                <!--<label for="newCategory">New Category</label>-->
                                                <div class="text-danger" id="categoryError"></div>
                                            </div>  

                                        </td >
                                    </tr>
                                    <tr>
                                        <td class=" text-end " >
                                            <label>Size:</label>
                                        </td>
                                        <td></td>
                                        <td>
                                            <div class="col-12">
                                                <!--<label>Loại kích thước:</label>-->
                                                <select id="sizeType" name="sizeType" onchange="updateSizeOptions()" style="height: 35px;margin-bottom: 10px;">
                                                    <option value="standard">Standard</option>
                                                    <option value="letter">Letter (S, M, L...)</option>
                                                    <option value="number">Number </option>
                                                </select>

                                                <div id="sizeRange" style="display: none;">
                                                    <table class="col-12">
                                                        <tr>
                                                            <td><label>Size bắt đầu:</label>
                                                                <select id="sizeStart" name="sizeStart" onchange="validateSizeRange()"></select></td>
                                                            <td> <label>Size kết thúc:</label>
                                                                <select id="sizeEnd" name="sizeEnd" onchange="validateSizeRange()"></select></td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <br>
                            <h2 class="title text-center">Variant List</h2>
                            <div id="variantContainer" class="col-12">

                            </div>
                            <div class="col-12" >
                                <table class="col-12" style="right:0px">
                                    <tr>
                                        <td class="w-50 text-start">
                                            <button type="button" class="btn btn-default get-manh-success" onclick="addVariant()">Add Variant</button>
                                        </td>
                                        <td class="w-50 text-end">
                                            <button type="submit" class="btn btn-default get-manh ">Create Product</button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </form> 
                    </div>
                </div>
        </section> <!--/#cart_items-->
        <br>
        <br>
        <c:import url="/Template/footer1.jsp" />
        <div id="sizeData"
             data-size-letters="${fn:join(sizeCList, ',')}"
             data-size-numbers="${fn:join(sizeNList, ',')}">
        </div>

        <script>
            document.getElementById("productForm").addEventListener("submit", function (event) {
                let isValid = true;

                // Lấy danh sách các giá trị trong dropdown
                function getDropListValues(selectId) {
                    return Array.from(document.getElementById(selectId).options).map(opt => opt.value.toLowerCase().trim());
                }

                // Kiểm tra input không được rỗng và dưới 255 ký tự
                function validateInput(inputId, errorId, fieldName) {
                    let inputElement = document.getElementById(inputId);
                    let errorElement = document.getElementById(errorId);
                    let value = inputElement.value.trim();

                    if (value === "") {
                        errorElement.textContent = fieldName + " không được để trống.";
                        isValid = false;
                    } else if (value.length > 255) {
                        errorElement.textContent = fieldName + " không được quá 255 ký tự.";
                        isValid = false;
                    } else if (inputId === "price" && value >= 100000000) {
                        errorElement.textContent = fieldName + " không được quá 100M";
                        isValid = false;
                    } else {
                        errorElement.textContent = "";
                    }
                }

                // Kiểm tra giá trị nhập vào có trùng với danh sách hay không
                function checkDuplicateInput(inputId, selectId, errorId, fieldName) {
                    let inputElement = document.getElementById(inputId);
                    let errorElement = document.getElementById(errorId);
                    let dropListValues = getDropListValues(selectId);
                    let value = inputElement.value.trim().toLowerCase();

                    if (value && dropListValues.includes(value)) {
                        errorElement.textContent = fieldName + " đã tồn tại trong danh sách.";
                        isValid = false;
                    }
                }

                // Kiểm tra các trường nhập
                validateInput("name", "nameError", "Tên sản phẩm");
                validateInput("description", "descriptionError", "Mô tả");
                validateInput("price", "priceError", "Giá");

                // Kiểm tra brand nếu nhập mới
                let brandSelect = document.getElementById("brand");
                if (brandSelect.value === "Other") {
                    validateInput("newBrand", "newBrandError", "Thương hiệu mới");
                    checkDuplicateInput("newBrand", "brand", "newBrandError", "Thương hiệu mới");
                }

                // Kiểm tra category nếu nhập mới
                let categorySelect = document.getElementById("category");
                if (categorySelect.value === "Other") {
                    validateInput("newCategory", "newCategoryError", "Danh mục mới");
                    checkDuplicateInput("newCategory", "category", "newCategoryError", "Danh mục mới");
                }

                // Nếu có lỗi, ngăn form submit
                if (!isValid) {
                    event.preventDefault();
                }
            });

            function toggleBrandInput(selectElement) {
                let newAddressInput = document.getElementById("newBrand");
                if (selectElement.value === "Other") {
                    newAddressInput.style.display = "block";
                    newAddressInput.required = true;
                } else {
                    newAddressInput.style.display = "none";
                    newAddressInput.required = false;
                }
            }

            function toggleCategoryInput(selectElement) {
                let newAddressInput = document.getElementById("newCategory");
                if (selectElement.value === "Other") {
                    newAddressInput.style.display = "block";
                    newAddressInput.required = true;
                } else {
                    newAddressInput.style.display = "none";
                    newAddressInput.required = false;
                }
            }

            function toggleColorInput(selectElement, id) {
                let newAddressInput = document.getElementById("newColor_" + id);
                if (selectElement.value === "Other") {
                    newAddressInput.style.display = "block";
                    newAddressInput.required = true;
                } else {
                    newAddressInput.style.display = "none";
                    newAddressInput.required = false;
                }
            }
            let variantIndex = 0;
            function addVariant() {
                let colorList = JSON.parse('${colorList}');
                console.log(colorList);
                variantIndex++;
                let variantHTML = '<div class ="variant-section "' +
                        ' style="margin:5px;"' +
                        ' id="variant_' + variantIndex + '">' +
                        '<table class=" col-12">' +
                        '<tr >' +
                        '<td class="col-3 text-start">' +
                        '<label></label>' +
                        '</td>' +
                        '<td class="col-9 text-end">' +
                        '<button type="button" class="remove-btn" onclick="removeVariant(' + variantIndex + ')">Delete</button>' +
                        '</td>' +
                        '</tr>' +
                        '<tr >' +
                        '<td class="">' +
                        '<label> Color: </label>' +
                        '</td >' +
                        '<td>' +
                        '<div class="form-floating mb-10">' +
                        '<select  id="color' + variantIndex + '" name="variant[' + variantIndex + '][color]"' +
                        ' style="height: 35px;margin-bottom: 10px;" onchange="toggleColorInput(this,' + variantIndex + ')"> ';
                colorList.forEach(color => {
                    variantHTML += '<option value= "' + color + '">' + color + '</option>';
                }
                );
                variantHTML +=
                        '<option value="Other">-- Other --</option>' +
                        '</select> ' +
                        '<span id="newColorError' + variantIndex + '" class="error-message"></span>' +
                        '   <input type="text" name="newColor_' + variantIndex + '" id="newColor_' + variantIndex + '" class="form-control mt-2"' +
                        '   style="height: 35px;margin-bottom: 10px; display:none;"' +
                        ' placeholder="Enter new Color" />' +
                        '<div class="text-danger" id="colorError' + variantIndex + '"></div>' +
                        '</div>' +
                        '</td>' +
                        '</tr>' +
                        '<tr> <td>' +
                        '<label>Ảnh:</label>' +
                        '</td><td>' +
                        '<input type="file" name="variant[' + variantIndex + '][images]" multiple>' +
                        '<div class="text-danger" id="imageError' + variantIndex + '"></div>' +
                        '</td></tr>' +
                        '</table>' +
                        '</div>  ';

                console.log(variantHTML);
                $('#variantContainer').append(variantHTML);
            }

            function removeVariant(index) {
                $('#variant_' + index).remove();
            }
            // Kiểm tra input color mới
            function checkDuplicateColor(variantIndex) {
                let colorSelect = document.getElementById(`color${variantIndex}`);
                let newColorInput = document.getElementById(`newColor_${variantIndex}`);
                let errorElement = document.getElementById(`colorError${variantIndex}`);

                if (colorSelect.value === "Other") {
                    let existingColors = Array.from(colorSelect.options).map(opt => opt.value.toLowerCase().trim());
                    let newColor = newColorInput.value.trim().toLowerCase();

                    if (newColor === "") {
                        errorElement.textContent = "Màu sắc mới không được để trống.";
                    } else if (newColor.length > 255) {
                        errorElement.textContent = "Màu sắc mới không được quá 255 ký tự.";
                    } else if (existingColors.includes(newColor)) {
                        errorElement.textContent = "Màu sắc đã tồn tại trong danh sách.";
                    } else {
                        errorElement.textContent = "";
                    }
                }
            }

// Kiểm tra định dạng file ảnh
            function validateImage(variantIndex) {
                let fileInput = document.getElementById(`imageUpload${variantIndex}`);
                let errorElement = document.getElementById(`imageError${variantIndex}`);
                let allowedExtensions = ["jpg", "jpeg", "png", "gif"];

                for (let file of fileInput.files) {
                    let fileExtension = file.name.split('.').pop().toLowerCase();
                    if (!allowedExtensions.includes(fileExtension)) {
                        errorElement.textContent = "Chỉ chấp nhận file ảnh (.jpg, .jpeg, .png, .gif)";
                        fileInput.value = ""; // Xóa file không hợp lệ
                        return;
                    }
                }
                errorElement.textContent = "";
            }
            function updateSizeOptions() {
                let sizeType = document.getElementById("sizeType").value;
                let sizeRange = document.getElementById("sizeRange");
                let sizeStart = document.getElementById("sizeStart");
                let sizeEnd = document.getElementById("sizeEnd");

                // Xóa options cũ
                sizeStart.innerHTML = "";
                sizeEnd.innerHTML = "";

                // Lấy danh sách size từ `data-attribute`
                let sizeData = document.getElementById("sizeData");
                let sizeLetters = sizeData.getAttribute("data-size-letters").split(",");
                let sizeNumbers = sizeData.getAttribute("data-size-numbers").split(",");

                let sizes = [];

                if (sizeType === "letter") {
                    sizes = sizeLetters;
                } else if (sizeType === "number") {
                    sizes = sizeNumbers;
                }

                if (sizes.length > 0 && sizes[0] !== "") {
                    sizes.forEach(size => {
                        let option1 = new Option(size, size);
                        let option2 = new Option(size, size);
                        sizeStart.appendChild(option1);
                        sizeEnd.appendChild(option2);
                    });
                    sizeRange.style.display = "block";
                } else {
                    sizeRange.style.display = "none";
                }
            }

            // Gọi khi trang load
            document.addEventListener("DOMContentLoaded", updateSizeOptions);
            function validateSizeRange() {
                let sizeStart = document.getElementById("sizeStart").value;
                let sizeEnd = document.getElementById("sizeEnd").value;
                let sizeType = document.getElementById("sizeType").value;
                let sizeLetters = sizeData.getAttribute("data-size-letters").split(",");
                let sizeNumbers = sizeData.getAttribute("data-size-numbers").split(",");

                let sizes = sizeType === "number"
                        ? sizeNumbers
                        : sizeLetters;

                if (sizes.indexOf(sizeStart) > sizes.indexOf(sizeEnd)) {
                    alert("Size bắt đầu không được lớn hơn size kết thúc");
                    document.getElementById("sizeEnd").value = sizeStart;
                }
            }
            // Đảm bảo gọi khi trang load
            document.addEventListener("DOMContentLoaded", updateSizeOptions);
        </script>
    </body>
</html>
