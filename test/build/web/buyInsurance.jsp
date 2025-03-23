<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Mua bảo hiểm</title>
        <style>
            /* Định dạng chung */
            body {
                font-family: "Arial", sans-serif;
                background-color: #f8f9fa;
                color: #333;
                text-align: center;
                margin: 0;
                padding: 0;
            }

            h1 {
                color: #d9534f;
                font-size: 36px;
                margin-top: 20px;
            }

            /* Khung chứa nội dung */
            .container {
                background: #ffffff;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                display: inline-block;
                text-align: left;
                width: 50%;
                margin-top: 20px;
            }

            /* Định dạng input */
            input, select {
                display: block;
                width: 100%;
                padding: 12px;
                margin: 10px 0;
                border: 2px solid #d9534f;
                border-radius: 8px;
                font-size: 18px;
                background: #fff;
                color: #333;
            }

            input:focus, select:focus {
                border-color: #c9302c;
                outline: none;
            }

            input[readonly] {
                background: #e9ecef;
                cursor: not-allowed;
            }

            /* Nút bấm */
            .btn {
                display: inline-block;
                padding: 12px 20px;
                border-radius: 8px;
                font-size: 18px;
                text-decoration: none;
                text-align: center;
                transition: 0.3s;
                border: none;
            }

            .btn-primary {
                background-color: #d9534f;
                color: white;
            }

            .btn-primary:hover {
                background-color: #c9302c;
            }

            label {
                font-size: 18px;
                font-weight: bold;
                color: #d9534f;
            }
            /* Nút "Mua" */
            button {
                background-color: #d9534f;
                color: white;
                font-size: 20px;
                font-weight: bold;
                padding: 12px 20px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                width: 100%;
                margin-top: 10px;
                transition: background-color 0.3s ease;
            }

            button:hover {
                background-color: #c9302c;
            }

            button:active {
                background-color: #a52a2a;
                transform: scale(0.98);
            }

        </style>
    </head>
    <body>

        <h1>Mua bảo hiểm</h1>
        <h4>${requestScope.error}</h4> 
        <form action="buyInsurance" method="post" enctype="multipart/form-data">

            <input type="hidden" name="insurance_id" value="${insurance_id}" />
            <div class="container">
                <label>Chọn khoản vay</label>
                <select id="loanDropdown" class="filter-dropdown" name="loan_id" onchange="updateLoanDetails()">
                    <c:if test ="${not empty listLoan}">                        
                        <c:forEach var="l" items="${requestScope.listLoan}">
                            <option value="${l.loan_id}">${l.notes}</option>  
                        </c:forEach>                
                    </c:if>
                </select>
                <label>Chọn chính sách</label>

                <select id="policyDropdown" class="filter-dropdown" name="policy_id" onchange="updatePolicyDetails()">



                    <c:if test ="${not empty listPolicy}">

                        <c:forEach var="p" items="${requestScope.listPolicy}">
                            <option value="${p.policy_id}">${p.policy_name}</option>  
                        </c:forEach>                
                    </c:if>
                </select>
                <label>Nhập tên hợp đồng</label>
                <input type="text" name="contract_name"  required  /> 

                <label>Số tiền được nhận</label>
                <input type="text" id="coverageAmount" name="coverage_amount"  required readonly /> 

                <label>Số tiền cần đóng</label>
                <input type="text" id="premiumAmount" name="premium_amount"  required readonly /> 

                <label>Nhập số tiền nộp</label>
                <input type="text" id="paidAmount" name="paid_amount" required />

                <label>Trả theo</label>
                <select name="payment_frequency">
                    <option value="monthly">Tháng</option>
                    <option value="quarterly">Quý</option>
                    <option value="annually">Năm</option>
                </select>

                <label>Ngày bắt đầu hợp đồng vay</label>
                <input type="text" id="start_date" name="start_date" required readonly />

                <label>Ngày kết thúc hợp đồng vay</label>
                <input type="text" id="end_date" name="end_date" required readonly />

                <label>Nhập thời hạn hợp đồng (Tháng)</label>
                <input type="text" name="duration" required/>
                 <div class="form-group">
                                    <label for="file">Ảnh</label>
                                    <input style="margin-bottom: 5px;margin-top: 5px;" type="file" name="file" id="file" accept="image/png, image/jpg, image/jpeg" >
                                </div>
                <button type="submit" >Mua</button>
        </form>
    </div>


    
    <script>
        var policies = {};
        <c:forEach var="p" items="${requestScope.listPolicy}">
    <c:set var="formattedCoverage" value="${p.coverage_amount}"/>
    <c:set var="formattedPremium" value="${p.premium_amount}"/>
    <fmt:formatNumber value="${formattedCoverage}" pattern="#,##0" var="formattedCoverage"/>
    <fmt:formatNumber value="${formattedPremium}" pattern="#,##0" var="formattedPremium"/>
    policies["${p.policy_id}"] = {
        coverage_amount: "${formattedCoverage}",
        premium_amount: "${formattedPremium}"
    };
</c:forEach>

        function updatePolicyDetails() {
            var selectedPolicy = document.getElementById("policyDropdown").value;
            if (selectedPolicy && policies[selectedPolicy]) {
                document.getElementById("coverageAmount").value = policies[selectedPolicy].coverage_amount;
                document.getElementById("premiumAmount").value = policies[selectedPolicy].premium_amount;
            }
        }

        var loans = {};
        <c:forEach var="l" items="${requestScope.listLoan}">
        <fmt:formatDate value="${l.start_date}" pattern="dd-MM-yyyy" var="formattedStartDate"/>
        <fmt:formatDate value="${l.end_date}" pattern="dd-MM-yyyy" var="formattedEndDate"/>
        loans["${l.loan_id}"] = {
            start_date: "${formattedStartDate}",
            end_date: "${formattedEndDate}"
        };
        </c:forEach>;

        function updateLoanDetails() {
            var selectedLoan = document.getElementById("loanDropdown").value;
            if (selectedLoan && loans[selectedLoan]) {
                document.getElementById("start_date").value = loans[selectedLoan].start_date;
                document.getElementById("end_date").value = loans[selectedLoan].end_date;
            }
        }

        window.onload = function () {
            updatePolicyDetails();
            updateLoanDetails();
        };
    </script>
    
 <script>
                document.getElementById("paidAmount").addEventListener("input", function () {               
                let rawValue = this.value.replace(/\./g, "");
                if (!isNaN(rawValue) && rawValue.length > 0) {
                    this.value = Number(rawValue).toLocaleString("vi-VN");
                } else {
                    this.value = "";
                }
            });
            </script>

</body>
</html>
