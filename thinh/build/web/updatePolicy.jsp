<%-- 
    Document   : new
    Created on : Sep 26, 2024, 3:49:32 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #ffe6e6;
                color: #990000;
                text-align: center;
                font-size: 18px;
            }
            h1 {
                color: #cc0000;
                font-size: 32px;
            }
            form {
                background: #ffcccc;
                padding: 30px;
                border-radius: 15px;
                display: inline-block;
                text-align: left;
                width: 50%;
            }
            input, select,textarea {
                display: block;
                margin: 15px 0;
                padding: 10px;
                width: 100%;
                border: 2px solid #cc0000;
                border-radius: 8px;
                font-size: 18px;
            }
            input[type="hidden"] {
                display: none;
            }
            button {
                background-color: #cc0000;
                color: white;
                border: none;
                padding: 15px 25px;
                border-radius: 8px;
                cursor: pointer;
                font-size: 18px;
                width: 100%;
            }
            button:hover {
                background-color: #990000;
            }
            a {
                display: inline-block;
                text-decoration: none;
                background-color: #cc0000;
                color: white;
                padding: 12px 25px;
                border-radius: 8px;
                font-size: 18px;
            }

        </style>
    </head>
    <body>
        <h1>Cập nhật chính sách</h1>
        <h4>${requestScope.error}</h4>
        <c:set var="p" value="${requestScope.policy}"/>
        <form action="updatePolicy" method="post" enctype="multipart/form-data">
            <input type="hidden" name="policy_id" value="${p.policy_id}">

            Nhập tên
            <textarea name="policy_name" >${p.policy_name}</textarea><br>

            Nhập mô tả  
            <textarea name="description" id="editor2">${p.description}</textarea><br>

           <fmt:formatNumber value="${p.coverage_amount}" pattern="#,##0" var="formattedCoverage"/>
<fmt:formatNumber value="${p.premium_amount}" pattern="#,##0" var="formattedPremium"/>

Nhập số tiền được nhận 
<input type="text" id="coverage_amount" name="coverage_amount" value="${formattedCoverage}" /><br>

Nhập số tiền cần đóng 
<input type="text" id="premium_amount" name="premium_amount" value="${formattedPremium}" /><br>

            Chọn trạng thái
            <select class="filter-dropdown" name="status">
                <option value="active" ${p.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${p.status == 'inactive' ? 'selected' : ''}>Ngừng hoạt động</option>
            </select>
            </br>
            <div class="form-group">
                <label for="file">Ảnh hiện tại</label><br>
                <c:if test="${not empty p.image}">
                    <img src="InsurancePolicy/${p.image}" alt="Current Image" width="150" height="150" style="border-radius: 5px; object-fit: cover;"><br>
                </c:if>
                <label for="file">Tải ảnh mới</label>
                <input type="file" name="file" id="file" accept="image/png, image/jpg, image/jpeg">
            </div>

            <button type="submit">Cập nhật</button>
        </form>

        <main>
            <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
            <script>

                CKEDITOR.replace('editor2');
            </script>
          

            <script>
                function formatCurrencyInput(element) {
                    let rawValue = element.value.replace(/\D/g, ""); // Chỉ giữ lại số
                    if (rawValue.length > 0) {
                        element.value = Number(rawValue).toLocaleString("vi-VN");
                    } else {
                        element.value = "";
                    }
                }

                document.getElementById("coverage_amount").addEventListener("input", function () {
                    formatCurrencyInput(this);
                });

                document.getElementById("premium_amount").addEventListener("input", function () {
                    formatCurrencyInput(this);
                });
            </script>  



        </main>
    </body>

</html>
