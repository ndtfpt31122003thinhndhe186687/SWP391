<%@ page import="model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Customer Details</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #fff5f5;
                color: #333;
                line-height: 1.6;
                margin: 0;
                padding: 20px;
            }
            h1, h2 {
                color: #c53030;
                border-bottom: 2px solid #c53030;
                padding-bottom: 10px;
            }
            .customer-info {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }
            .customer-info p {
                margin: 10px 0;
            }
            img {
                max-width: 200px;
                border-radius: 50%;
                border: 3px solid #c53030;
            }
            ul {
                list-style-type: none;
                padding: 0;
            }
            li {
                background-color: #fff;
                margin-bottom: 10px;
                padding: 10px;
                border-radius: 4px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .error {
                color: #c53030;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <fmt:setLocale value="vi_VN"/>
        <h1>Thông tin khách hàng</h1>
        <%
            Customer customer = (Customer) request.getAttribute("customer");
            String debtStatus = (String) request.getAttribute("debtStatus");
            List<String> assetStatuses = (List<String>) request.getAttribute("assetStatuses");
            List<String> serviceNames = (List<String>) request.getAttribute("serviceNames");

            if (customer != null) {
        %>
        <div class="customer-info">
            <img src="imageCustomer/<%= customer.getProfile_picture() %>" alt="Profile Picture" />
            <p><strong>ID:</strong> <%= customer.getCustomer_id() %></p>
            <p><strong>Tên:</strong> <%= customer.getFull_name() %></p>
            <p><strong>Email:</strong> <%= customer.getEmail() %></p>
            <p><strong>Tên đăng nhập:</strong> <%= customer.getUsername() %></p>
            <p><strong>Số điện thoại:</strong> <%= customer.getPhone_number() %></p>
            <p><strong>Địa chỉ:</strong> <%= customer.getAddress() %></p>
            <p><strong>Loại thẻ:</strong> <%= customer.getCard_type() %></p>
            <p><strong>Tình trạng:</strong> <%= customer.getStatus() %></p>
            <p><strong>Giới tinh:</strong> <%= customer.getGender() %></p>
            <p><strong>Số tiền:</strong> 
                <fmt:formatNumber value="<%= customer.getAmount() %>" pattern="#,###"/> VND
            </p>
            <p><strong>Giới hạn tín dụng:</strong> 
                <fmt:formatNumber value="<%= customer.getCredit_limit() %>" pattern="#,###"/> VND
            </p>
            <p><strong>Ngày sinh:</strong> 
                <fmt:formatDate value="<%= customer.getDate_of_birth() %>" pattern="dd/MM/yyyy" />
            </p>           
            <p><strong>Tình trạng nợ:</strong> <%= debtStatus != null ? debtStatus : "No debt information" %></p>
        </div>

        

        
        
        
        <%
            }
        %>
    </body>
</html>

