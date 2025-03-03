<%-- 
    Document   : new
    Created on : Sep 26, 2024, 3:49:32 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            input, select {
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
        <h1>Update a Policy</h1>
        <h4>${requestScope.error}</h4>
        <c:set var="p" value="${requestScope.policy}"/>
        <form action="updatePolicy" method="post" enctype="multipart/form-data">
            <input type="hidden" name="policy_id" value="${p.policy_id}">

            Enter Policy Name  
            <textarea name="policy_name" id="editor1">${p.policy_name}</textarea><br>

            Enter Description  
            <textarea name="description" id="editor2">${p.description}</textarea><br>

            Enter Coverage Amount  
            <input type="text" name="coverage_amount" class="format-number" value="${p.coverage_amount}" /><br>

            Enter Premium Amount  
            <input type="text" name="premium_amount" class="format-number" value="${p.premium_amount}" /><br>

            Enter Status
            <select class="filter-dropdown" name="status">
                <option value="active" ${p.status == 'active' ? 'selected' : ''}>Active</option>
                <option value="inactive" ${p.status == 'inactive' ? 'selected' : ''}>Inactive</option>
            </select>
            </br>
            <div class="form-group">
    <label for="file">Current Image</label><br>
    <c:if test="${not empty p.image}">
        <img src="${p.image}" alt="Current Image" width="150" height="150" style="border-radius: 5px; object-fit: cover;"><br>
    </c:if>
    <label for="file">Upload New Image</label>
    <input type="file" name="file" id="file" accept="image/png, image/jpg, image/jpeg">
</div>

            <button type="submit">Update</button>
        </form>

        <main>
            <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
            <script>
                CKEDITOR.replace('editor1');
                CKEDITOR.replace('editor2');
            </script>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    let formatNumbers = document.querySelectorAll(".format-number");
                    formatNumbers.forEach(function (el) {
                        let num = parseFloat(el.value.replace(/,/g, ''));
                        if (!isNaN(num)) {
                            el.value = num.toLocaleString("en-US", {minimumFractionDigits: 2, maximumFractionDigits: 2});
                        }
                    });

                    formatNumbers.forEach(function (el) {
                        el.addEventListener("input", function () {
                            let cursorPosition = this.selectionStart;

                            let rawValue = this.value.replace(/,/g, "").replace(/[^0-9.]/g, '');

                            if (!isNaN(rawValue) && rawValue !== "") {
                                let formattedValue = Number(rawValue).toLocaleString("en-US", {
                                    minimumFractionDigits: 2,
                                    maximumFractionDigits: 2
                                });

                                this.value = formattedValue;


                                setTimeout(() => {
                                    this.selectionStart = this.selectionEnd = cursorPosition;
                                }, 0);
                            }
                        });
                    });
                });
            </script>


        </main>
    </body>

</html>
