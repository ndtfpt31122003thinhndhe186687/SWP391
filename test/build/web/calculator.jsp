<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ServiceTerms" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Loan and Savings Interest Calculator</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f8f9fa;
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .calculator {
                background: #fff;
                border: 1px solid #ccc;
                padding: 15px 20px;
                margin: 20px auto;
                max-width: 500px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .calculator h2 {
                margin-top: 0;
                color: #007BFF;
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            .form-group input, .form-group select {
                width: 100%;
                padding: 8px 10px;
                font-size: 1em;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }
            button {
                background-color: #007BFF;
                color: #fff;
                border: none;
                padding: 10px 15px;
                font-size: 1em;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
            .result {
                margin-top: 15px;
                background: #e9ecef;
                padding: 10px;
                border-radius: 4px;
            }
            .error {
                color: red;
            }
        </style>
    </head>
    <body>
        <h1>Loan and Savings Interest Calculator</h1>

        <!-- Form tính lãi suất cho vay -->
        <div class="calculator">
            <h2>Loan Terms</h2>
            <form action="calculate" method="post">
                <div class="form-group">
                    <label for="loanTermId">Choose loan Terms:</label>
                    <select name="loanTermId" id="loanTermId">
                        <%
                            List<ServiceTerms> loanTerms = (List<ServiceTerms>) request.getAttribute("loanTerms");
                            if (loanTerms != null) {
                                for (ServiceTerms term : loanTerms) {
                        %>
                        <option value="<%= term.getTerm_id() %>">
                            <%= term.getTerm_name() %> - Interest rate: <%= term.getInterest_rate() %>% - Min payment: <%= String.format("%,.0f", term.getMin_payment()) %> $
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="loanAmount">Loan amount ($):</label>
                    <input type="number" name="loanAmount" id="loanAmount" placeholder="Enter loan amount">
                </div>
                <div class="form-group">
                    <label for="loanDuration">Loan term (months):</label>
                    <input type="number" name="loanDuration" id="loanDuration" placeholder="Enter months">
                </div>
                <button type="submit" name="action" value="calculateLoan">Loan Interest Calculation</button>
            </form>
            <%
                String loanError = (String) request.getAttribute("loanError");
                if (loanError != null) {
            %>
            <div class="result error">
                <p><%= loanError %></p>
            </div>
            <%
                }
                Double monthlyPayment = (Double) request.getAttribute("loanMonthlyPayment");
                Double totalPayment = (Double) request.getAttribute("loanTotalPayment");
                Double totalInterest = (Double) request.getAttribute("loanTotalInterest");
                if (monthlyPayment != null && totalPayment != null && totalInterest != null) {
            %>
            <div class="result">
                <p>Monthly payment: <strong><%= String.format("%,.0f", monthlyPayment) %> $</strong></p>
                <p>Total amount payable: <strong><%= String.format("%,.0f", totalPayment) %> $</strong></p>
                <p>Total interest payable: <strong><%= String.format("%,.0f", totalInterest) %> $</strong></p>
            </div>
            <%
                }
            %>
        </div>

        <!-- Form tính lãi suất tiết kiệm -->
        <div class="calculator">
            <h2>Savings Terms</h2>
            <form action="calculate" method="post">
                <div class="form-group">
                    <label for="savingTermId">Choose Savings Terms:</label>
                    <select name="savingTermId" id="savingTermId">
                        <%
                            List<ServiceTerms> savingTerms = (List<ServiceTerms>) request.getAttribute("savingTerms");
                            if (savingTerms != null) {
                                for (ServiceTerms term : savingTerms) {
                        %>
                        <option value="<%= term.getTerm_id() %>">
                            <%= term.getTerm_name() %> - Interest rate: <%= term.getInterest_rate() %>% - Min payment: <%= String.format("%,.0f", term.getMin_deposit()) %> $
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="savingAmount">Deposit amount ($):</label>
                    <input type="number" name="savingAmount" id="savingAmount" placeholder="Enter deposit amount">
                </div>
                <div class="form-group">
                    <label for="savingDuration">Deposit period (month):</label>
                    <input type="number" name="savingDuration" id="savingDuration" placeholder="Enter months">
                </div>
                <button type="submit" name="action" value="calculateSaving">Calculate Savings Interest Rate</button>
            </form>
            <%
                String savingError = (String) request.getAttribute("savingError");
                if (savingError != null) {
            %>
            <div class="result error">
                <p><%= savingError %></p>
            </div>
            <%
                }
                Double savingInterestEarned = (Double) request.getAttribute("savingInterestEarned");
                Double savingFinalAmount = (Double) request.getAttribute("savingFinalAmount");
                if (savingInterestEarned != null && savingFinalAmount != null) {
            %>
            <div class="result">
                <p>Amount of interest received: <strong><%= String.format("%,.0f", savingInterestEarned) %> $</strong></p>
                <p>Ending amount: <strong><%= String.format("%,.0f", savingFinalAmount) %> $</strong></p>
            </div>
            <%
                }
            %>
        </div>

    </body>
</html>
