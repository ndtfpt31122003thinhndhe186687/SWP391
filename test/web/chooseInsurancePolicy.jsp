<%-- 
    Document   : payInsurance
    Created on : Mar 3, 2025, 11:38:43 PM
    Author     : Windows
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Chọn Bảo Hiểm</title>
    <style>
    /* Toàn bộ trang với tone màu đỏ chủ đạo */
    body {
        font-family: Arial, sans-serif;
        background-color: #fff5f5; /* Nền màu đỏ nhạt */
        color: #D32F2F; /* Màu chữ chính */
        text-align: center;
        margin: 40px;
    }

    h1 {
        color: #B71C1C; /* Màu đỏ đậm hơn cho tiêu đề */
    }

    /* Style cho dropdown và button */
    select, button {
        width: 300px;
        padding: 10px;
        font-size: 16px;
        border-radius: 5px;
        border: 2px solid #D32F2F;
        margin: 10px 0;
    }

    /* Dropdown */
    select {
        background-color: white;
        color: #D32F2F;
    }

    select:hover {
        border-color: #E53935; /* Hiệu ứng hover */
    }

    /* Button chọn */
    button {
        background-color: #D32F2F;
        color: white;
        font-weight: bold;
        cursor: pointer;
        transition: background 0.3s ease-in-out;
    }

    button:hover {
        background-color: #B71C1C; /* Hiệu ứng hover */
    }

    button:active {
        background-color: #E53935;
    }

    /* Container */
    form {
        display: inline-block;
        padding: 20px;
        border: 2px solid #D32F2F;
        border-radius: 10px;
        background: white;
        box-shadow: 2px 2px 10px rgba(211, 47, 47, 0.3);
    }

    label {
        font-weight: bold;
    }
</style>
</head>
<body>
    <h1>Chọn bảo hiểm bạn muốn thanh toán:</h1>
<form action="calculatorInsurance" method="post">
        <label>Chọn tên chính sách</label>    
        <select class="filter-dropdown" name="policy_id" id="policySelect" onchange="filterContracts()">
            <c:set var="uniquePolicies" value="" />
            <c:forEach var="c" items="${requestScope.listCD}">
                <c:if test="${not fn:contains(uniquePolicies, c.policy_id)}">
                    <option value="${c.policy_id}">${c.policy_name}</option>  
                    <c:set var="uniquePolicies" value="${uniquePolicies},${c.policy_id}" />
                </c:if>
            </c:forEach>
        </select>

        <label>Chọn ID hợp đồng</label>
        <select class="filter-dropdown" name="contract_id" id="contractSelect">
            <!-- Hợp đồng sẽ được lọc bằng JavaScript -->
        </select>

        <!-- Ẩn toàn bộ danh sách hợp đồng vào đây -->
        <div style="display: none;">
            <c:forEach var="c" items="${requestScope.listCD}">                  
                <option class="contractOption" data-policy-id="${c.policy_id}" value="${c.contract_id}">
                    ${c.contract_id}
                </option>
            </c:forEach>
        </div>

        <c:forEach var="c" items="${requestScope.listCD}">                  
            <input type="hidden" name="insurance_id" value="${c.insurance_id}">
        </c:forEach>

        <button type="submit">Chọn</button>
    </form>



        <script>
    function filterContracts() {
        var policySelect = document.getElementById("policySelect");
        var contractSelect = document.getElementById("contractSelect");

        // Lấy ID chính sách đang được chọn
        var policyId = policySelect.value;

        // Xóa tất cả các option cũ trong danh sách hợp đồng
        contractSelect.innerHTML = "";

        // Lặp qua danh sách hợp đồng và thêm hợp đồng phù hợp
        var contracts = document.querySelectorAll(".contractOption");
        var hasValidContract = false;

        contracts.forEach(function(option) {
            if (option.getAttribute("data-policy-id") === policyId) {
                var newOption = document.createElement("option");
                newOption.value = option.value;
                newOption.text = option.textContent;
                contractSelect.appendChild(newOption);
                hasValidContract = true;
            }
        });

        // Nếu có hợp đồng hợp lệ, chọn hợp đồng đầu tiên
        if (hasValidContract) {
            contractSelect.selectedIndex = 0;
        }
    }

    // Khi trang tải, tự động gọi filterContracts để hiển thị hợp đồng đầu tiên
    window.onload = function() {
        filterContracts();
    };
</script>

</body>
</html>
