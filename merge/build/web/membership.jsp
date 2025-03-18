<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Loan Request</title>
    <style>
        :root {
            --primary-red: #dc3545;
            --dark-red: #c82333;
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

        input, select, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        input:focus, select:focus, textarea:focus {
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
    </style>
    <script>
        function updateDetails() {
            var vipSelect = document.getElementById("vip_type");
            var termSelect = document.getElementById("term_name");
            var durationInput = document.getElementById("duration");
            var loanRateInput = document.getElementById("loan_rate");
            var savingsRateInput = document.getElementById("savings_rate");
            var priceInput = document.getElementById("price");

            var selectedOption = vipSelect.options[vipSelect.selectedIndex];

            if (selectedOption.value !== "") {
                termSelect.value = selectedOption.getAttribute("data-term");
                durationInput.value = selectedOption.getAttribute("data-duration");
                loanRateInput.value = selectedOption.getAttribute("data-loan-rate");
                savingsRateInput.value = selectedOption.getAttribute("data-savings-rate");
                priceInput.value = selectedOption.getAttribute("data-price");
            } else {
                termSelect.value = "";
                durationInput.value = "";
                loanRateInput.value = "";
                savingsRateInput.value = "";
                priceInput.value = "";
            }
        }
    </script>

    
</head>
<body>
    <div class="container">
        <h1>Membership</h1>

        <!-- Hiển thị lỗi nếu có -->
        <c:if test="${not empty requestScope.error}">
            <h4 class="error-message" style="color: red;">${requestScope.error}</h4>
        </c:if>

        <form action="membership" method="post">
            <div class="form-group">
                <label for="customer_name">Họ và tên:</label>
                <input type="text" name="customer_name" readonly value="${sessionScope.account.full_name}" required />
                <input type="hidden" name="customer_id" value="${sessionScope.account.customer_id}" />
            </div>

            <div class="form-group">
                <label for="vip_type">Gói hội viên: </label>
                <select id="vip_type" name="vip_type" required onchange="updateDetails()">
                    <option value="">-- Chọn gói hội viên --</option>
                    <c:forEach var="vip" items="${vipTerms}">
                        <option value="${vip.vipType}" 
                                data-term="${vip.termName}"
                                data-duration="${vip.duration}"
                                data-loan-rate="${vip.loanRate}"
                                data-savings-rate="${vip.savingsRate}"
                                data-price="${vip.price}">
                            ${vip.vipType} - ${vip.termName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="term_name">Kì hạn: </label>
                <input type="text" id="term_name" name="term_name" readonly required>
            </div>

            <div class="form-group">
                <label for="duration">Thời gian (tháng):</label>
                <input type="text" id="duration" name="duration" readonly required>
            </div>

            <div class="form-group">
                <label for="loan_rate">Ưu đãi khi vay: (%)</label>
                <input type="text" id="loan_rate" name="loan_rate" readonly required>
            </div>

            <div class="form-group">
                <label for="savings_rate">Ưu đãi khi gửi tiết kiệm: (%)</label>
                <input type="text" id="savings_rate" name="savings_rate" readonly required>
            </div>

            <div class="form-group">
                <label for="price">Giá:</label>
                <input type="text" id="price" name="price" readonly required>
            </div>

            <button type="submit">Gửi</button>
        </form>
    </div>
</body>
</html>
