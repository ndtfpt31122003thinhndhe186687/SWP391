<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.WaterInvoice, controlller_Accountant.InvoiceDAO" %>
<html>
<head>
    <title>Quản lý Hóa Đơn Nước</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="text-center mt-4">Quản lý Hóa Đơn Nước</h2>

        <table class="table table-bordered mt-4">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nhà Cung Cấp</th>
                    <th>Khách Hàng</th>
                    <th>Chỉ số M³</th>
                    <th>Số Tiền</th>
                    <th>Ngày Hạn</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<WaterInvoice> invoices = new InvoiceDAO().getAllWaterInvoices();
                    for (WaterInvoice invoice : invoices) {
                %>
                <tr>
                    <td><%= invoice.getInvoiceId() %></td>
                    <td><%= invoice.getProviderId() %></td>
                    <td><%= invoice.getCustomerId() %></td>
                    <td><%= invoice.getConsumptionM3() %></td>
                    <td><%= invoice.getAmount() %> VNĐ</td>
                    <td><%= invoice.getDueDate() %></td>
                    <td><%= invoice.getStatus() %></td>
                    <td>
                        <form action="WaterInvoiceServlet" method="post">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="invoiceId" value="<%= invoice.getInvoiceId() %>">
                            <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
