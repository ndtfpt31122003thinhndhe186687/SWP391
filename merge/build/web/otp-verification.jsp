<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận OTP</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <style>
        .otp-container {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-bottom: 15px;
        }
        .otp-box {
            width: 50px;
            height: 50px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            border: 2px solid #dc3545;
            border-radius: 5px;
            background-color: #f8f9fa;
            line-height: 50px;
        }
        #otp-input {
            position: absolute;
            opacity: 0;
            width: 1px;
            height: 1px;
        }
    </style>
    <script>
        function updateOtpDisplay() {
            let otpInput = document.getElementById("otp-input");
            let otpValue = otpInput.value.replace(/\D/g, '').slice(0, 6); // Chỉ lấy tối đa 6 số
            otpInput.value = otpValue;

            let boxes = document.querySelectorAll(".otp-box");
            for (let i = 0; i < 6; i++) {
                boxes[i].textContent = otpValue[i] || ""; // Hiển thị số hoặc rỗng
            }
        }

        function focusOtpInput() {
            document.getElementById("otp-input").focus();
        }
    </script>
</head>
<body>
    <div class="container text-center">
        <h2 class="text-danger mt-5">Xác nhận OTP</h2>
        <p>Nhập mã OTP gồm 6 chữ số đã gửi đến email của bạn:</p>

        <form action="${sessionScope.account.role_id == 6 ? 'verifyOtp' : 'verifyOtpStaff'}" method="post">
            <input type="text" id="otp-input" name="otp" inputmode="numeric" 
                   maxlength="6" oninput="updateOtpDisplay()" autofocus>

            <div class="otp-container" onclick="focusOtpInput()">
                <div class="otp-box"></div>
                <div class="otp-box"></div>
                <div class="otp-box"></div>
                <div class="otp-box"></div>
                <div class="otp-box"></div>
                <div class="otp-box"></div>
            </div>

            <button type="submit" class="btn btn-danger">Xác nhận</button>
        </form>

        <c:if test="${not empty errorMessage}">
            <div style="color: red; font-weight: bold; margin-top: 10px;">
                ${errorMessage}
            </div>
        </c:if>
    </div>
</body>
</html>
