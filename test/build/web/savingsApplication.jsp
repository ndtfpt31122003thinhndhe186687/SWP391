<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gửi Tiết Kiệm</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f8f8;
                text-align: center;
                background: url('https://www.techcombank.com.vn/banner.jpg') no-repeat center center/cover;
            }
            .container {
                background: white;
                max-width: 500px;
                margin: 50px auto;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                position: relative;
            }
            h2 {
                color: #d61c1c;
            }
            .header {
                background-color: #d61c1c;
                color: white;
                padding: 15px;
                font-size: 20px;
                text-align: center;
                border-radius: 8px 8px 0 0;
            }
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
                color: #d61c1c;
                text-align: left;
            }
            input, select, textarea {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .input-group {
                text-align: left;
                margin-bottom: 15px;
            }
            button {
                background-color: #d61c1c;
                color: white;
                border: none;
                padding: 12px;
                width: 100%;
                margin-top: 15px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                transition: 0.3s;
            }
            button:hover {
                background-color: #b51717;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">Hệ Thống Ngân Hàng - Gửi Tiết Kiệm</div>
            <h2>Biểu Mẫu Gửi Tiết Kiệm</h2>
            <form action="sendSavingsApplication" method="post">
                <div class="input-group">
                    <label for="customer_id">Tên Khách Hàng:</label>
                    <input type="text" id="customer_name" value="${sessionScope.account.full_name}" readonly>
                    <input type="hidden" name="customer_id" value="${sessionScope.account.customer_id}">
                </div>

                <div class="input-group">
                    <label for="service_id">Chọn kỳ hạn gửi:</label>
                    <select id="serviceTerm_id" name="serviceTerm_id" required>
                        <option value="">--Chọn--</option>
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

                <div class="input-group">
                    <label for="term_id">Thời Gian Gửi:</label>
                    <input type="text" id="duration" name="duration" readonly>
                </div>

                <div class="input-group">
                    <label for="interest_rate">Lãi Suất (%):</label>
                    <input type="text" id="interest_rate" name="interest_rate" readonly>
                </div>

                <div class="input-group">
                    <label for="terms">Điều Khoản Hợp Đồng:</label>
                    <textarea id="terms" name="terms" readonly></textarea>
                </div>

                <div class="input-group">
                    <label for="amount">Số Tiền Gửi:</label>
                    <input type="text" id="amount" name="amount" required pattern="[0-9,\.]+" />
                    <input type="hidden" id="availableAmount" value="${sessionScope.account.amount}">
                </div>

                <div class="input-group">
                    <label for="Note">Ghi Chú:</label>
                    <input type="text" id="note" name="note">
                </div>

                <button type="submit">Gửi Đăng Ký</button>
                <script>
                    document.getElementById("serviceTerm_id").addEventListener("change", function () {
                        var selectedOption = this.options[this.selectedIndex];
                        var interestRate = selectedOption.getAttribute("data-interest");
                        var contractTerms = selectedOption.getAttribute("data-terms");
                        var duration = selectedOption.getAttribute("data-duration");
                        var minDeposit = selectedOption.getAttribute("data-min-deposit");

                        document.getElementById("duration").value = duration + " tháng";
                        document.getElementById("interest_rate").value = interestRate + " % mỗi năm";
                        document.getElementById("terms").value = contractTerms
                                ? contractTerms +  "\nSố tiền gửi tối thiểu: " + parseFloat(minDeposit)
                                    .toLocaleString("vi-VN")
                                    .replace(/\./g, " ") + " VNĐ": "";
                                
                    });

                    document.getElementById("amount").addEventListener("input", function () {
                        var selectedOption = document.getElementById("serviceTerm_id").options[document.getElementById("serviceTerm_id").selectedIndex];
                        var minDeposit = parseFloat(selectedOption.getAttribute("data-min-deposit"));
                        var availableAmount = parseFloat(document.getElementById("availableAmount").value);
                        var rawValue = this.value.replace(/\./g, "");
                        var amountInput = parseFloat(rawValue);

                        if (amountInput < minDeposit) {
                            this.setCustomValidity("Số tiền gửi tối thiểu là " + minDeposit + " VNĐ.");
                        } else if (amountInput > availableAmount) {
                            this.setCustomValidity("Số dư tài khoản không đủ. Vui lòng nhập lại!");
                        } else {
                            this.setCustomValidity("");
                        }
                        if (!isNaN(rawValue) && rawValue.length > 0) {
                            this.value = amountInput.toLocaleString("vi-VN").replace(/,/g, ".");
                        } else {
                            this.value = "";
                        }
                    });

                    document.querySelector("form").addEventListener("submit", function () {
                        var amountInput = document.getElementById("amount");
                        amountInput.value = amountInput.value.replace(/\./g, "");
                        localStorage.removeItem("selectedTerm");
                    });

                    window.onload = function () {
                        let selectedTerm = localStorage.getItem("selectedTerm");
                        if (selectedTerm) {
                            selectedTerm = JSON.parse(selectedTerm);
                            document.getElementById("serviceTerm_id").value = selectedTerm.serviceTermId;
                            document.getElementById("duration").value = selectedTerm.duration + " tháng";
                            document.getElementById("interest_rate").value = selectedTerm.interestRate + " % mỗi năm";
                            document.getElementById("terms").value = selectedTerm.contractTerms +
                                    "\nSố tiền gửi tối thiểu: " + parseFloat(selectedTerm.minDeposit)
                                    .toLocaleString("vi-VN")
                                    .replace(/\./g, " ") + " VNĐ";


                            document.getElementById("serviceTerm_id").dispatchEvent(new Event("change"));
                        }
                    };

                    window.addEventListener("unload", function () {
                        localStorage.removeItem("selectedTerm");
                    });
                </script>
            </form>
        </div>
    </body>
</html>
