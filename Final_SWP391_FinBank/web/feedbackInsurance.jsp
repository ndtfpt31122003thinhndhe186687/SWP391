<%-- 
    Document   : feedbackInsurance
    Created on : Mar 13, 2025, 1:47:34 PM
    Author     : Windows
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Phản hồi</title>
        <style>
            /* Căn chỉnh tổng thể */
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
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 900px;
                margin: auto;
                border: 2px solid #e74c3c; /* Viền đỏ */
            }

            /* Nhãn và các input */
            label {
                font-weight: bold;
                display: block;
                margin-top: 15px;
                color: #e74c3c; /* Màu chữ đỏ */
            }

            textarea {
                width: 100%;
                height: 100px;
                padding: 8px;
                border: 1px solid #e74c3c;
                border-radius: 5px;
                resize: none;
            }

            /* CSS cho dropdown chọn chính sách */
            select {
                width: 100%;
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #e74c3c;
                margin-top: 5px;
            }

            /* Nút gửi */
            button {
                display: block;
                width: 100%;
                background: #e74c3c;
                color: white;
                padding: 10px;
                margin-top: 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s;
                font-size: 16px;
            }

            button:hover {
                background: #c0392b;
            }

            /* CSS cho đánh giá sao */
            .rating {
                display: flex;
                flex-direction: row-reverse;
                justify-content: center;
                gap: 5px;
                font-size: 30px;
                margin-top: 10px;
            }

            .rating input {
                display: none;
            }

            .rating label {
                cursor: pointer;
                color: black; /* Màu mặc định */
                transition: color 0.3s;
            }

            .rating input:checked ~ label,
            .rating label:hover,
            .rating label:hover ~ label {
                color: gold;
            }
.feedback-content img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
    margin-top: 10px;
}
        </style>
    </head>
    <body>
        <h1>Gửi phản hồi bảo hiểm</h1>
        <h3 style="color:red">${requestScope.error}</h3>
       <form action="feedbackInsurance" method="post">
    <input type="hidden" name="insurance_id" value="${param.insurance_id}">

    <label>Chọn chính sách</label>  
    <select name="policy_id">
        <c:forEach var="P" items="${listP}">
            <option value="${P.policy_id}" ${P.policy_id == param.policy_id ? 'selected' : ''}>${P.policy_name}</option>
        </c:forEach>
    </select>

    <label>Nhập nội dung phản hồi</label>
    <div id="toolbar-container"></div> <!-- Thanh công cụ CKEditor -->
    <div id="editor" class="feedback-content">${param.feedback_content}</div>
    <input type="hidden" name="feedback_content" id="hiddenDescription" value="${param.feedback_content}">

    <label>Đánh giá</label>
    <div class="rating">
        <input type="radio" id="star5" name="feedback_rate" value="5" ${param.feedback_rate == '5' ? 'checked' : ''}><label for="star5">★</label>
        <input type="radio" id="star4" name="feedback_rate" value="4" ${param.feedback_rate == '4' ? 'checked' : ''}><label for="star4">★</label>
        <input type="radio" id="star3" name="feedback_rate" value="3" ${param.feedback_rate == '3' ? 'checked' : ''}><label for="star3">★</label>
        <input type="radio" id="star2" name="feedback_rate" value="2" ${param.feedback_rate == '2' ? 'checked' : ''}><label for="star2">★</label>
        <input type="radio" id="star1" name="feedback_rate" value="1" ${param.feedback_rate == '1' ? 'checked' : ''}><label for="star1">★</label>
    </div>

    <button type="submit">Gửi phản hồi</button>
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
            let descriptionValue = editor.getData().trim();
            document.getElementById("hiddenDescription").value = descriptionValue;
        });

                    })
                    .catch(error => {
                        console.error('CKEditor lỗi:', error);
                    });

        </script>
</body>
</html>
