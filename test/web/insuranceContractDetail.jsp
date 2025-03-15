<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Chi tiết hợp đồng</title>
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
        .contract-container {
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
        input {
            display: block;
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 18px;
            background: #f8f9fa;
            color: #333;
        }

        input:focus {
            border-color: #d9534f;
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
        }

        .btn-primary {
            background-color: #d9534f;
            color: white;
            border: none;
        }

        .btn-primary:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
    <h1>Chi tiết hợp đồng</h1>      

    <c:set var="d" value="${requestScope.contractDetail}"/>        

    <div class="contract-container">
        <label>ID hợp đồng</label>
        <input type="text" value="${d.contract_id}" readonly/><br>

        <input type="hidden" value="${d.insurance_id}" />

        <p><strong>Số tiền được nhận:</strong> 
            <span><fmt:formatNumber value="${d.coverageAmount}" pattern="#,##0.00" />VND</span></p>

       <p><strong>Số tiền cần đóng:</strong> 
           <span><fmt:formatNumber value="${d.premiumAmount}" pattern="#,##0.00" />VND</span></p>

         <p><strong>Số tiền đã nộp:</strong> 
             <span><fmt:formatNumber value="${d.paidAmount}" pattern="#,##0.00" />VND</span></p>

    </div>


</body>
</html>
