<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <form action="buyInsurance" method="post">

            <input type="hidden" name="insurance_id" value="${insurance_id}" />
            <div class="container">
                <label>Chọn ID khoản vay</label>
                <select class="filter-dropdown" name="loan_id">
                    <c:if test ="${not empty listLoan}">                        
                        <c:forEach var="l" items="${requestScope.listLoan}">
                            <option value="${l.loan_id}">${l.loan_id}</option>  
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

                <label>Số tiền được nhận</label>
                <input type="text" id="coverageAmount" name="coverage_amount" required readonly />

                <label>Số tiền cần đóng</label>
                <input type="text" id="premiumAmount" name="premium_amount" required readonly />

                <label>Nhập số tiền nộp</label>
                <input type="text" name="paid_amount" required />

                <label>Trả theo</label>
                <select name="payment_frequency">
                    <option value="monthly">Tháng</option>
                    <option value="quarterly">Quý</option>
                    <option value="annually">Năm</option>
                </select>

                <label>Nhập thời hạn hợp đồng (Tháng)</label>
                <input type="text" name="duration" required/>

                <button type="submit" >Mua</button>
        </form>
    </div>


    <script>
// Lưu danh sách policy vào JavaScript object
        var policies = {};
        <c:forEach var="p" items="${requestScope.listPolicy}">
        policies["${p.policy_id}"] = {
            coverage_amount: "${p.coverage_amount}",
            premium_amount: "${p.premium_amount}"
        };
        </c:forEach>;

        function updatePolicyDetails() {
            var selectedPolicy = document.getElementById("policyDropdown").value;
            if (selectedPolicy && policies[selectedPolicy]) {
                document.getElementById("coverageAmount").value = policies[selectedPolicy].coverage_amount;
                document.getElementById("premiumAmount").value = policies[selectedPolicy].premium_amount;
            }
        }

// Khi trang vừa tải xong, tự động cập nhật thông tin policy đầu tiên
        window.onload = function () {
            updatePolicyDetails();
        };

    </script>

</body>
</html>
