<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Deposit Savings</title>
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
            <div class="header">Banking System - Savings Deposit</div>
            <h2>Saving Deposit Form</h2>
            <form action="sendSavingsApplication" method="post">
                <div class="input-group">
                    <label for="customer_id">Customer Name:</label>
                    <input type="text" id="customer_name" value="${sessionScope.account.full_name}" readonly>
                    <input type="hidden" name="customer_id" value="${sessionScope.account.customer_id}">
                </div>

                <div class="input-group">
                    <label for="service_id">Select the term you want to deposit: </label>
                    <select id="serviceTerm_id" name="serviceTerm_id" required>
                        <option value="">--All--</option>
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
                    <label for="term_id">Term:</label>
                    <input type="text" id="duration" name="duration" readonly>
                </div>

                <div class="input-group">
                    <label for="interest_rate">Interest rate(%):</label>
                    <input type="text" id="interest_rate" name="interest_rate" readonly>
                </div>

                <div class="input-group">
                    <label for="terms">Contract terms: </label>
                    <textarea id="terms" name="terms" readonly></textarea>
                </div>

                <div class="input-group">
                    <label for="amount">Deposit Amount:</label>
                    <input type="number" id="amount" name="amount" required>
                </div>

                <div class="input-group">
                    <label for="Note">Notes:</label>
                    <input type="text" id="note" name="note">
                </div>

                <button type="submit">Submit</button>
                <script>
                    document.getElementById("serviceTerm_id").addEventListener("change", function () {
                        var selectedOption = this.options[this.selectedIndex];

                        var termId = selectedOption.getAttribute("data-termid");
                        var interestRate = selectedOption.getAttribute("data-interest");
                        var contractTerms = selectedOption.getAttribute("data-terms");
                        var duration = selectedOption.getAttribute("data-duration");
                        var minDeposit = selectedOption.getAttribute("data-min-deposit");

                        document.getElementById("duration").value = duration + " months";
                        document.getElementById("interest_rate").value = interestRate + " % per year";
                        document.getElementById("terms").value = contractTerms
                                ? contractTerms + "\nMinimum Deposit: " + minDeposit + " $"
                                : "";
                    });
                    
                    
                    document.getElementById("amount").addEventListener("input", function () {
                        var selectedOption = document.getElementById("serviceTerm_id").options[document.getElementById("serviceTerm_id").selectedIndex];
                        var minDeposit = parseFloat(selectedOption.getAttribute("data-min-deposit"));
                        var amountInput = parseFloat(this.value);

                        if (amountInput < minDeposit) {
                            this.setCustomValidity("Deposit amount must be at least " + minDeposit + " $.");
                        } else {
                            this.setCustomValidity("");
                        }
                    });
                </script>
            </form>
        </div>
    </body>
</html>
