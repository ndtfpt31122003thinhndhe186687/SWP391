<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Term</title>
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

            input:focus {
                outline: none;
                border-color: var(--primary-red);
                box-shadow: 0 0 5px rgba(220,53,69,0.2);
            }

            input[readonly] {
                background-color: #f8f9fa;
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
                transition: background-color 0.3s;
            }

            button:hover {
                background-color: var(--dark-red);
            }

            .error-message {
                color: red;
                text-align: center;
                margin-bottom: 10px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Update a service term</h1>

            <c:set var="s" value="${requestScope.serviceTerm}"/>
            <form action="updateServiceTerm" method="post">

                <input type="hidden" name="term_id" value="${s.term_id}" />

                <div class="form-group">
                    <label for="term_name">Term name:</label>
                    <input type="text" id="term_name" name="term_name" value="${s.term_name}" required/>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description" value="${s.description}" required/>
                </div>

                <div class="form-group">
                    <label for="contract_terms">Detailed contract terms:</label>
                    <input type="text" id="contract_terms" name="contract_terms" value="${s.contract_terms}" required/>
                </div>

                <c:if test="${s.service_id==1 || s.service_id==2}"> 
                    <div class="form-group">
                        <label for="max_term_months">Max term months:</label>
                        <input type="number" id="max_term_months" name="max_term_months" value="${s.max_term_months}" min="0" required/>
                    </div>

                    <div class="form-group">
                        <label for="early_payment_penalty">Early payment penalty:</label>
                        <input type="number" id="early_payment_penalty" name="early_payment_penalty" value="${s.early_payment_penalty}" min="0" step="0.01" required/>
                    </div>

                    <div class="form-group">
                        <label for="interest_rate">Interest rate:</label>
                        <input type="number" id="interest_rate" name="interest_rate" value="${s.interest_rate}" min="0" step="0.01" required/>
                    </div>
                </c:if>

                <c:if test="${s.service_id==2}"> 
                    <div class="form-group">
                        <label for="min_payment">Min Payment:</label>
                        <input type="number" id="min_payment" name="min_payment" value="${s.min_payment}" min="0" step="0.01" required/>
                    </div>
                </c:if>

                <c:if test="${s.service_id==1}"> 
                    <div class="form-group">
                        <label for="min_deposit">Min Deposit:</label>
                        <input type="number" id="min_deposit" name="min_deposit" value="${s.min_deposit}" min="0" step="0.01" required/>
                    </div>
                </c:if>

                <div class="form-group">
                    <label>Status:</label>
                    <select name="status">
                        <option value="active" ${s.status=="active" ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${s.status=="inactive" ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <c:if test="${not empty err}">
                    <div>
                        <p style="color: red;">${err}</p>
                    </div>
                </c:if>
                <button type="submit">Update Service Term</button>
            </form>
        </div>
    </body>
</html>
