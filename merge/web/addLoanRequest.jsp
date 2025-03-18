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
        <title>Đơn vay</title>
        <style>
            :root {
                --primary-red: #dc3545;
                --dark-red: #c82333;
                --light-red: #f8d7da;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
                color: #333;
            }

            h1 {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 30px;
                padding-bottom: 10px;
                border-bottom: 3px solid var(--primary-red);
            }

            .error-message {
                color: var(--primary-red);
                text-align: center;
                margin-bottom: 20px;
                font-weight: 500;
            }

            form {
                max-width: 600px;
                margin: 0 auto;
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
                font-weight: 500;
            }

            input, select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }
            textarea{
                width: 100%;
                padding: 10px;
                padding-bottom: 50px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                transition: border-color 0.3s;
            }

            input:focus, select:focus,textarea:focus {
                outline: none;
                border-color: var(--primary-red);
                box-shadow: 0 0 5px rgba(220,53,69,0.2);
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
                transition: background-color 0.3s;
            }

            button:hover {
                background-color: var(--dark-red);
            }

            select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
                background-repeat: no-repeat;
                background-position: right 1rem center;
                background-size: 1em;
            }
            .invalid-amount {
                color: var(--primary-red);
                font-size: 12px;
                margin-top: 5px;
                display: none;
            }


        </style>
    </head>
    <body>
        <% 
    String message = (String) request.getAttribute("message");
    if (message != null) { 
        %>
        <script>
            alert("<%= message %>");
        </script>
        <% } %>

        <div class="container">
            <h1>Biểu mẫu yêu cầu vay</h1>
            <h4 class="error-message">${requestScope.error}</h4>          
            <form action="SendLoanRequest" method="post"
                  enctype="multipart/form-data" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="Customer name">Họ và tên:</label>
                    <input type="text" name="customer_name" readonly value="${sessionScope.account.full_name}" required />
                    <input type="hidden" name="customer_id" value="${sessionScope.account.customer_id}"/>
                </div>

                <div class="form-group">
                    <label for="service_terms">Chọn kỳ hạn: </label>
                    <select id="service_terms" name="service_terms" required>
                        <option value="">Vui lòng chọn</option>
                        <c:forEach items="${requestScope.listS}" var="st">
                            <option value="${st.serviceTerm_id}"
                                    data-duration="${st.duration}"
                                    data-interest="${st.interest_rate}"
                                    data-terms="${st.contract_terms}"
                                    data-min-deposit="${st.min_deposit}">
                                ${st.term_name}                           
                            </option>

                        </c:forEach>

                    </select>
                </div>

                <div class="form-group">
                    <label for="duration">Thời gian:</label>
                    <input type="text" id="duration" name ="duration" readonly required ></input>
                </div>

                <div class="form-group">
                    <label for="interest_rate">Lãi suất:</label>
                    <input type="text" id="interest_rate" name="interest_rate" readonly required ></input>
                </div>

                <div class="form-group">
                    <label for="contract_terms">Điều khoản:</label>
                    <textarea type="text" id="contract_terms" name="contract_terms" readonly required ></textarea>
                </div>

                <div class="form-group">
                    <label for="amount">Số tiền:</label>
                    <input type="text" id="amount" name="amount"  required
                           pattern="[0-9,\.]+" />
                    <div class="invalid-amount" id="amount-feedback">
                        Vui lòng nhập số tiền hợp lệ.</div>
                </div>

                <div class="form-group">
                    <label for="loan_type">Loại vay:</label>                  
                    <select id="loan_type" name="loan_type" required>
                        <option value="secured">thế chấp</option>
                        <option value="unsecured">tín chấp</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="value_asset">Giá trị tài sản:</label>
                    <input type="text" id="value_asset" name="value_asset" required
                           pattern="[0-9,\.]+"/>
                    <div class="invalid-amount" id="asset-feedback" >
                        80% Giá trị tài sản phải bằng số tiền vay.
                    </div>
                </div>

                <div class="form-group">
                    <label for="asset_image">Hình ảnh:</label>
                    <input type="file" id="asset_image" name="asset_image" multiple accept="image/*" required ></input>
                    <div id="previewContainer"></div>
                </div>

                <div class="form-group">
                    <label for="notes">Ghi chú :</label>
                    <input type="text" id="notes" name="notes" required ></input>
                </div>

                <button type="submit">Gửi yêu cầu </button>
            </form>               
        </div>
        <script>
            document.getElementById("asset_image").addEventListener("change", function (event) {
                let previewContainer = document.getElementById("previewContainer");
                previewContainer.innerHTML = "";
                let files = event.target.files;

                for (let i = 0; i < files.length; i++) {
                    let file = files[i];

                    if (!file.type.startsWith("image/")) {
                        alert("Vui lòng chọn file ảnh!");
                        continue;
                    }

                    let reader = new FileReader();
                    reader.onload = function (e) {
                        let img = document.createElement("img");
                        img.src = e.target.result;
                        img.style.width = "500px";
                        img.style.margin = "5px";
                        previewContainer.appendChild(img);
                    };

                    reader.readAsDataURL(file); // show image 
                }
            });

            document.getElementById("service_terms").addEventListener("change", function () {
                var selectedOption = this.options[this.selectedIndex];

                var interestRate = selectedOption.getAttribute("data-interest");
                var contractTerms = selectedOption.getAttribute("data-terms");
                var duration = selectedOption.getAttribute("data-duration");
                var minPayment = selectedOption.getAttribute("data-min-deposit");

                document.getElementById("duration").value = duration + " tháng";
                document.getElementById("interest_rate").value = interestRate + " %/năm";
                document.getElementById("contract_terms").value = contractTerms
                        ? contractTerms + "\nSố tiền tối thiểu: " + minPayment + " VND"
                        : "";
            });

            document.getElementById("amount").addEventListener("input", function () {
                var selectedOption = document.getElementById("service_terms").
                        options[document.getElementById("service_terms").selectedIndex];
                var minDeposit = parseFloat(selectedOption.getAttribute("data-min-deposit"));
                
                let rawValue = this.value.replace(/\./g, "");
                let amountInput = parseFloat(rawValue);

                var errorMsg = document.getElementById("amount-error");
                if (!errorMsg) {
                    errorMsg = document.createElement("span");
                    errorMsg.id = "amount-error";
                    errorMsg.style.color = "red";
                    errorMsg.style.display = "block";
                    this.parentNode.appendChild(errorMsg);
                }

                if (amountInput < minDeposit) {
                    errorMsg.textContent = "Số tiền tối thiểu là " + minDeposit + " VND.";
                    this.setCustomValidity("Số tiền phải lớn hơn hoặc bằng " + minDeposit);
                } else {
                    errorMsg.textContent = "";
                    this.setCustomValidity("");
                }

                if (!isNaN(rawValue) && rawValue.length > 0) {
                    this.value = Number(rawValue).toLocaleString("vi-VN");
                } else {
                    this.value = "";
                }
                validateAsset();
            });

            document.getElementById('amount').addEventListener('keypress', function (e) {
                if (e.key < '0' || e.key > '9') {
                    e.preventDefault();
                }
            });

            document.getElementById("value_asset").addEventListener("input", function () {
                let rawValue = this.value.replace(/\./g, "");             
                if (!isNaN(rawValue) && rawValue.length > 0) {
                    this.value = Number(rawValue).toLocaleString("vi-VN");
                } else {
                    this.value = "";
                }
                validateAsset();
            });

            document.getElementById('value_asset').addEventListener('keypress', function (e) {
                if (e.key < '0' || e.key > '9') {
                    e.preventDefault();
                }
            });

            function validateAsset() {
                let amount = parseFloat(document.getElementById("amount").value.replace(/\./g, "").replace(/,/g, "")) || 0;
                let assetInput = document.getElementById("value_asset");
                let assetValue = parseFloat(assetInput.value.replace(/\./g, "").replace(/,/g, "")) || 0;
                let assetFeedback = document.getElementById("asset-feedback");

                if (amount > 0) {
                    let minAsset = amount / 0.8; // 80% LTV                  
                    if (assetValue < minAsset ) {
                        assetFeedback.style.display = 'block';
                    } else {
                        assetFeedback.style.display = 'none';
                    }
                } else {
                    assetFeedback.style.display = 'none';
                }
            }

            function validateForm() {
                let isValid = true;
                const amount = document.getElementById('amount');
                const amountFeedback = document.getElementById('amount-feedback');
                const assetFeedback = document.getElementById("asset-feedback");
                
                if (!amount.checkValidity()) {
                    amountFeedback.style.display = 'block';
                    isValid = false;
                } else {
                    amountFeedback.style.display = 'none';
                }
                
                if(assetFeedback.style.display === 'block'){
                    isValid = false;
                }

                return isValid;
            }

        </script>
    </body>
</html>
