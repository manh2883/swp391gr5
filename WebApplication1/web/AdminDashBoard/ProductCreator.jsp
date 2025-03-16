<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tạo Sản Phẩm</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .variant-section {
                border: 1px solid #ddd;
                padding: 10px;
                margin-top: 10px;
                position: relative;
            }
            .remove-btn {
                position: absolute;
                top: 5px;
                right: 5px;
                cursor: pointer;
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.4);
            }
            .modal-content {
                background-color: white;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 50%;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h2>Tạo Sản Phẩm</h2>
        <form id="productForm" method="post" enctype="multipart/form-data">
            <label>Mã sản phẩm:</label>
            <input type="text" id="product_id" name="product_id" readonly><br>

            <label>Tên sản phẩm:</label>
            <input type="text" id="name" name="name" required><br>

            <label>Mô tả:</label>
            <textarea id="description" name="description"></textarea><br>

            <label>Thương hiệu:</label>
            <select id="brand" name="brand_id">
                <option value="1">Nike</option>
                <option value="2">Adidas</option>
                <option value="3">Puma</option>
            </select>
            <button type="button" onclick="addNewBrand()">Other</button><br>

            <label>Danh mục:</label>
            <select id="category" name="category_id">
                <option value="1">Áo</option>
                <option value="2">Quần</option>
                <option value="3">Giày</option>
            </select>
            <button type="button" onclick="addNewCategory()">Other</button><br>

            <label>Giá:</label>
            <input type="number" id="price" name="price" required><br>

            <label>Loại kích thước:</label>
            <select id="sizeType" name="sizeType" onchange="updateSizeOptions()">
                <option value="standard">Tiêu chuẩn</option>
                <option value="letter">Chữ (S, M, L...)</option>
                <option value="number">Số (1-50)</option>
            </select>

            <div id="sizeRange" style="display: none;">
                <label>Size bắt đầu:</label>
                <select id="sizeStart" name="sizeStart" onchange="validateSizeRange()"></select>
                <label>Size kết thúc:</label>
                <select id="sizeEnd" name="sizeEnd" onchange="validateSizeRange()"></select>
            </div>

            <br>

            <h3>Danh sách biến thể</h3>
            <div id="variantContainer"></div>
            <button type="button" onclick="addVariant()">Thêm Biến Thể</button>

            <br><br>
            <button type="submit">Tạo Sản Phẩm</button>
        </form>

        <div id="sizeData"
             data-size-letters="${fn:join(sizeCList, ',')}"
             data-size-numbers="${fn:join(sizeNList, ',')}">
        </div>


        <script>
            let variantIndex = 0;

            document.addEventListener("DOMContentLoaded", updateSizeOptions);

            function addVariant() {
                variantIndex++;
                let variantHTML = `<div class='variant-section' id='variant_${variantIndex}'>
              <button type='button' class='remove-btn' onclick='removeVariant(${variantIndex})'>X</button>
              <label>Màu sắc:</label>
              <input type='text' name='variant[${variantIndex}][color]' required><br>
              <label>Ảnh:</label>
              <input type='file' name='variant[${variantIndex}][images]' multiple><br>
          </div>`;

                $('#variantContainer').append(variantHTML);
            }

            function removeVariant(index) {
                $(`#variant_${index}`).remove();
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

                let sizes = sizeType === "number"
                        ? Array.from({length: 50}, (_, i) => (i + 1).toString())
                        : ["S", "M", "L", "XL", "XXL"];

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
