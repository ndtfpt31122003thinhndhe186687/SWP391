<%-- 
    Document   : new
    Created on : Sep 26, 2024, 3:49:32 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <style>
            :root {
                --primary-red: #dc3545;
                --dark-red: #a71d2a;
                --light-red: #f8d7da;
                --background-light: #fff5f5;
                --text-dark: #333;
                --border-light: #faa2a2;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: var(--background-light);
                margin: 0;
                padding: 20px;
                color: var(--text-dark);
            }

            h1 {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 30px;
                padding-bottom: 10px;
                border-bottom: 4px solid var(--primary-red);
            }

            h4 {
                color: var(--dark-red);
                text-align: center;
            }

            form {
                max-width: 600px;
                margin: 0 auto;
                background: white;
                padding: 30px;
                border-radius: 10px;
                border: 2px solid var(--border-light);
                box-shadow: 0 0 20px rgba(220, 53, 69, 0.3);
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: var(--primary-red);
                font-weight: 600;
            }

            input, select, textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid var(--border-light);
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s, box-shadow 0.3s;
            }

            input:focus, select:focus, textarea:focus {
                outline: none;
                border-color: var(--primary-red);
                box-shadow: 0 0 8px rgba(220, 53, 69, 0.4);
            }

            input[readonly] {
                background-color: #f8d7da;
                cursor: not-allowed;
            }

            button {
                background-color: var(--primary-red);
                color: white;
                padding: 12px 30px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 600;
                width: 100%;
                margin-top: 20px;
                transition: background-color 0.3s, transform 0.2s;
            }

            button:hover {
                background-color: var(--dark-red);
                transform: scale(1.02);
            }

            select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background-color: #fff;
                border: 1px solid var(--border-light);
                background-image: linear-gradient(45deg, transparent 50%, var(--primary-red) 50%),
                    linear-gradient(135deg, var(--primary-red) 50%, transparent 50%);
                background-position: calc(100% - 10px) center, calc(100% - 5px) center;
                background-size: 5px 5px, 5px 5px;
                background-repeat: no-repeat;
                padding-right: 30px;
            }

            .invalid-feedback {
                color: var(--primary-red);
                font-size: 12px;
                margin-top: 5px;
                display: none;
            }
.term-description img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
    margin-top: 10px;
}
        </style>
    </head>
    <body>
        <h1>Cập nhật điều khoản bảo hiểm</h1>
        <h4>${requestScope.error}</h4>
        <c:set var="t" value="${requestScope.term}"/>
        <form action="updateInsuranceTerm" method="post">            
            <input type="hidden" name="term_id" value="${t.term_id}">
            <label>Chọn tên chính sách</label>
            <select class="filter-dropdown" name="policy_id" >
                <c:if test ="${not empty listPolicy}">
                    <c:forEach var="p" items="${requestScope.listPolicy}">
                        <option value="${p.policy_id}" ${p.policy_id == t.policy_id ? 'selected' : ''}>${p.policy_name}</option>  
                    </c:forEach>                
                </c:if>
            </select>
            <br>
            <br>
            <label>Nhập tên</label>
            <textarea name="term_name" required>${t.term_name}</textarea>
            <br>
            <label>Nhận mô tả</label>
             <div id="toolbar-container"></div> <!-- Thanh công c? CKEditor -->
             <div id="editor" class="term-description">${t.term_description != null ? t.term_description : ""}</div>
                                <input type="hidden" name="term_description" id="hiddenDescription">
            <br>
            <label>Nhập ngày bắt đầu</label>
            <input type="date" name="start_date" value="${t.start_date}" required/>
            <br>
            <label>Nhận ngày kết thúc</label>
            <input type="date" name="end_date" value="${t.end_date}" required/>
            <br>
            <label>Chọn trạng thái</label>
            <select class="filter-dropdown" name="status">                
                <option value="active" ${t.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${t.status == 'inactive' ? 'selected' : ''}>Ngừng hoạt động</option>
            </select>


            <br>
            <br>
            <button type="submit">Sửa</button>
        </form>
 
<script src="https://cdn.ckeditor.com/ckeditor5/39.0.1/decoupled-document/ckeditor.js"></script>

<script>
            // Hàm upload ?nh
            class MyUploadAdapter {
                constructor(loader) {
                    this.loader = loader;
                }

                upload() {
                    return this.loader.file
                            .then(file => new Promise((resolve, reject) => {
                                    const formData = new FormData();
                                    formData.append('upload', file); // Ph?i trùng v?i request.getPart("upload") trong Servlet

                                    fetch('http://localhost:9999/merge/uploadImgPolicy', {// URL servlet upload
                                        method: 'POST',
                                        body: formData
                                    })
                                            .then(response => {
                                                if (!response.ok) {
                                                    throw new Error(`Lỗii HTTP! Mã trạng thái: ${response.status}`);
                                                }
                                                return response.json();
                                            })
                                            .then(result => {
                                                if (!result || !result.url) {
                                                    return reject('Upload ảnh thấtt bại!');
                                                }
                                                resolve({
                                                    default: result.url  // ???ng d?n ?nh tr? v? t? server
                                                });
                                            })
                                            .catch(error => {
                                                console.error('Lỗi upload ảnh:', error);
                                                reject('Không thể upload ảnh!');
                                            });
                                }));
                }
            }

// Gán plugin upload ?nh vào CKEditor
            function MyCustomUploadAdapterPlugin(editor) {
                editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
                    return new MyUploadAdapter(loader);
                };
            }

// Kh?i t?o CKEditor
            DecoupledEditor
                    .create(document.querySelector('#editor'), {
                        extraPlugins: [MyCustomUploadAdapterPlugin]
                    })
                    .then(editor => {
                        const toolbarContainer = document.querySelector('#toolbar-container');
                        toolbarContainer.appendChild(editor.ui.view.toolbar.element);

                        // L?u n?i dung vào input ?n khi submit form
                        document.querySelector("form").addEventListener("submit", function () {
 let descriptionValue = editor.getData();
    document.querySelector("#hiddenDescription").value = descriptionValue;
    console.log("Mô tả gửi đi:", descriptionValue);
                        });

                    })
                    .catch(error => {
                        console.error('CKEditor lỗi:', error);
                    });

        </script>
    </body>
</html>
