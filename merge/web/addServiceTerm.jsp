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
        <title>Add Service Term</title>
        <script src="https://cdn.ckeditor.com/4.16.2/full/ckeditor.js"></script>
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

            input:focus, select:focus {
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
    </head>
    <body>
        <div class="container">
            <h1>Add Service Term</h1>
            <form action="addServiceTerm" method="post">
                <div class="form-group">
                    <label for="service_id">Service name:</label>
                    <select name="service_id" id="service_id" onchange="toggleFields()" required>
                        <option value="">-- Select Service --</option>
                        <c:forEach var="service" items="${requestScope.listS}">
                            <option value="${service.service_id}">${service.service_name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="term_name">Term name:</label>
                    <input type="text" id="term_name" name="term_name" required/>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description" required/>
                </div>

                <div class="form-group">
                    <label for="contract_terms">Detailed contract terms</label>
                    <input type="text" id="contract_terms" name="contract_terms" required/>
                </div>

                <div class="form-group">
                    <label for="max_term_months">Max term months:</label>
                    <select name="term_id" id="max_term_months">
                        <option value="">-- Select Term --</option>
                        <c:forEach var="term" items="${requestScope.listTerms}">
                            <option value="${term.term_id}">${term.duration} months</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="early_payment_penalty">Early payment penalty:</label>
                    <input type="number" id="early_payment_penalty" name="early_payment_penalty" min="0" step="0.01" />
                </div>

                <div class="form-group">
                    <label for="interest_rate">Interest rate:</label>
                    <input type="number" id="interest_rate" name="interest_rate" min="0" step="0.01" />
                </div>

                <div class="form-group">
                    <label for="min_payment">Min Payment:</label>
                    <input type="number" id="min_payment" name="min_payment" min="0" step="0.01" />
                </div>

                <div class="form-group">
                    <label for="min_deposit">Min Deposit:</label>
                    <input type="number" id="min_deposit" name="min_deposit" min="0" step="0.01" />
                </div>
                
                <c:if test="${not empty err}">
                    <div>
                        <p style="color: red;">${err}</p>
                    </div>
                </c:if>
                
                <button type="submit">Add New Service Term</button>
            </form>
        </div>
    </body>
</html>

 <script>
                function toggleFields() {
                    var serviceType = document.getElementById("service_id").value;


                    var max_term_months = document.getElementById("max_term_months");
                    var early_payment_penalty = document.getElementById("early_payment_penalty");
                    var interest_rate = document.getElementById("interest_rate");
                    var minPayment = document.getElementById("min_payment");
                    var minDeposit = document.getElementById("min_deposit");

                    max_term_months.parentElement.style.display = "block";
                    early_payment_penalty.parentElement.style.display = "block";
                    interest_rate.parentElement.style.display = "block";
                    minPayment.parentElement.style.display = "block";
                    minDeposit.parentElement.style.display = "block";

                    if (serviceType == 2) {
                        minDeposit.parentElement.style.display = "none";
                    } else if (serviceType == 1) {
                        minPayment.parentElement.style.display = "none";
                    } else if (serviceType == 3 || serviceType == 4) {
                        max_term_months.parentElement.style.display = "none";
                        early_payment_penalty.parentElement.style.display = "none";
                        interest_rate.parentElement.style.display = "none";
                        minPayment.parentElement.style.display = "none";
                        minDeposit.parentElement.style.display = "none";
                    }
                }

                // Gọi khi trang load để ẩn các trường ban đầu
                window.onload = toggleFields;
            </script>
