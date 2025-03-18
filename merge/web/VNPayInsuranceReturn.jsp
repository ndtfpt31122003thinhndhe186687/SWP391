<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="javax.crypto.Mac" %>
<%@ page import="javax.crypto.spec.SecretKeySpec" %>

<%
    //Begin process return from VNPAY
    Map fields = new HashMap();
    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
    String fieldName = (String) params.nextElement();
    String fieldValue = request.getParameter(fieldName);
    if ((fieldValue != null) && (fieldValue.length() > 0)) {
        fields.put(fieldName, fieldValue);
    }
    }
    
    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    if (fields.containsKey("vnp_SecureHashType")) {
    fields.remove("vnp_SecureHashType");
    }
    if (fields.containsKey("vnp_SecureHash")) {
    fields.remove("vnp_SecureHash");
    }
    String signValue = Config.hashAllFields(fields);
    
    %>
    <%
    if (signValue.equals(vnp_SecureHash)) {
        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
            out.print("GD Thanh cong");
        } else {
            out.print("GD Khong thanh cong");
        }
    
    } else {
        out.print("Chu ky khong hop le");
    }
    %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Thanh Toán</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        .container { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); }
        h2 { color: #28a745; }
        .error { color: red; }
        .info { font-size: 18px; margin-top: 20px; }
    </style>
</head>
<body>

<div class="container">
    <% if (isValidTransaction) { %>
        <h2>Giao dịch thành công!</h2>
        <p class="info">Mã đơn hàng: <strong><%= vnp_Params.get("vnp_TxnRef") %></strong></p>
        <p class="info">Số tiền thanh toán: <strong><%= Long.parseLong(vnp_Params.get("vnp_Amount")) / 100 %> VND</strong></p>
        <p class="info">Thời gian giao dịch: <strong><%= vnp_Params.get("vnp_PayDate") %></strong></p>
    <% } else { %>
        <h2 class="error">Giao dịch thất bại hoặc không hợp lệ!</h2>
        <p class="info">Vui lòng kiểm tra lại thông tin thanh toán.</p>
    <% } %>
    <br>
    <a href="home">Quay về trang chủ</a>
</div>

</body>
</html>

<%! 
    // Hàm tạo mã HMAC SHA-512
    public String hmacSHA512(String key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder hash = new StringBuilder();
        for (byte b : hmacBytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }
%>

<%
    out.println("<p><strong>Chuỗi hashData:</strong> " + hashData.toString() + "</p>");
    out.println("<p><strong>SecureHash từ VNPay:</strong> " + vnp_SecureHash + "</p>");
    out.println("<p><strong>SecureHash tạo ra:</strong> " + secureHash + "</p>");
%>

