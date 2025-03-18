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
        function updateDurationAndInterest() {
            var select = document.getElementById("service_terms");
            var durationInput = document.getElementById("duration");
            var interestRateInput = document.getElementById("interest_rate");
            var selectedOption = select.options[select.selectedIndex];

            if (selectedOption.value !== "") {
                durationInput.value = selectedOption.getAttribute("data-duration");
                interestRateInput.value = selectedOption.getAttribute("data-interest");
            } else {
                durationInput.value = "";
                interestRateInput.value = "";
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Loan request form</h1>

        <!-- Hiển thị lỗi nếu có -->
        <c:if test="${not empty requestScope.error}">
            <h4 class="error-message" style="color: red;">${requestScope.error}</h4>
        </c:if>

        <form action="SendLoanReq" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="customer_name">Name:</label>
                <input type="text" name="customer_name" readonly value="${sessionScope.account.full_name}" required />
                <input type="hidden" name="customer_id" value="${sessionScope.account.customer_id}" />
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" name="email" readonly value="${sessionScope.account.email}" required />
            </div>

            <div class="form-group">
                <label for="phone_number">Phone Number:</label>
                <input type="text" name="phone_number" readonly value="${sessionScope.account.phone_number}" required />
            </div>

            <!-- Dropdown lấy từ SendLoanRequestServlet -->
            <div class="form-group">
                <label for="service_terms">Term: </label>
                <select id="service_terms" name="serviceTerm_id" required onchange="updateDurationAndInterest()">
                    <option value="">-- Select --</option>
                    <c:forEach var="term" items="${loanTerms}">
                        <option value="${term.serviceTerm_id}" data-duration="${term.duration}" data-interest="${term.interest_rate}">
                            ${term.term_name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="duration">Duration (month):</label>
                <input type="text" id="duration" name="duration" readonly required>
            </div>

            <div class="form-group">
                <label for="interest_rate">Interest rate (%):</label>
                <input type="text" id="interest_rate" name="interest_rate" readonly required>
            </div>

            <div class="form-group">
                <label for="salary">Salary:</label>
                <input type="text" id="salary" name="value" required>
            </div>
            
            <div class="form-group">
                <label for="salary">Amount:</label>
                <input type="text" id="amount" name="amount" required>
            </div>
            
            <div class="form-group">
                <label for="pdfFile">PDF:</label>
                <input type="file" id="pdfFile" name="pdfFile" accept=".pdf" required>
            </div>

            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
