<%-- 
    Document   : payInsurancePolicy
    Created on : Mar 4, 2025, 1:41:09 AM
    Author     : Windows
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanh Toán</title>
        <style>
    /* Toàn bộ trang với tone màu đỏ chủ đạo */
    body,a {
        font-family: Arial, sans-serif;
        background-color: #fff5f5; /* Nền màu đỏ nhạt */
        color: #D32F2F; /* Màu chữ chính */
        text-align: center;
        margin: 40px;
    }

    h1 {
        color: #B71C1C; /* Màu đỏ đậm hơn cho tiêu đề */
        font-size: 24px;
        font-weight: bold;
    }

    /* Khung chứa thông tin */
    .container {
        display: inline-block;
        padding: 20px;
        border: 3px solid #D32F2F;
        border-radius: 10px;
        background: white;
        box-shadow: 2px 2px 10px rgba(211, 47, 47, 0.3);
        max-width: 400px;
        margin: auto;
    }

    /* Số tiền cần trả */
    .money {
        font-size: 28px;
        color: #E53935; /* Màu đỏ tươi hơn */
        font-weight: bold;
        padding: 10px;
        border-radius: 5px;
        background: #FFEBEE; /* Nền đỏ nhạt */
        display: inline-block;
        margin-top: 10px;
    }

    /* Nút quay lại */
    .btn-back {
        display: inline-block;
        margin-top: 20px;
        padding: 10px 20px;
        font-size: 16px;
        border-radius: 5px;
        background-color: #D32F2F;
        color: white;
        text-decoration: none;
        font-weight: bold;
        transition: background 0.3s ease-in-out;
    }

    .btn-back:hover {
        background-color: #B71C1C; /* Hiệu ứng hover */
    }
</style>

    </head>
    <body>
        <fmt:setLocale value="vi_VN"/>
       <div class="container">
        <h2>Thanh Toán Hợp Đồng Bảo Hiểm</h2>
        <h4>${requestScope.error}</h4>
<c:choose>
    <c:when test="${hasPaid}">
        <p>Bạn đã thanh toán. Không cần nộp thêm.</p>
    </c:when>
    <c:otherwise>
        <p>Số tiền cần thanh toán: <fmt:formatNumber value="${money}" pattern="#,###"/> VND</p>
        
        <form action="calculatorInsurance" method="post">
            <input type="hidden" name="contract_id" value="${contract_id}">
            <input type="hidden" name="insurance_id" value="${insurance_id}">
            <input type="hidden" name="policy_id" value="${policy_id}">
            <input type="hidden" name="amount" value="${money}">
            <button type="submit">Thanh toán</button>
        </form>
    </c:otherwise>
</c:choose>
            <br>
            <a href="home.jsp">Trở về trang chủ</a>
    </div>
    </body>
</html>
