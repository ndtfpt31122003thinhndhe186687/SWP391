<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.ElectricityInvoice, controlller_Accountant.InvoiceDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý Hóa Đơn Điện</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="text-center mt-4">Quản lý Hóa Đơn Điện</h2>

        <!-- Form Thêm Hóa Đơn -->
        <form action="ElectricityInvoiceServlet" method="post">
            <input type="hidden" name="action" value="create">
            <div class="mb-3">
                <label>Provider ID:</label>
                <input type="number" name="providerId" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Customer ID:</label>
                <input type="number" name="customerId" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Chỉ số tiêu thụ (KWh):</label>
                <input type="number" step="0.01" name="consumptionKWh" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Số tiền (VNĐ):</label>
                <input type="number" step="0.01" name="amount" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Ngày đến hạn:</label>
                <input type="date" name="dueDate" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-success">Thêm Hóa Đơn</button>
        </form>

        <!-- Danh Sách Hóa Đơn -->
        <table class="table table-bordered mt-4">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nhà Cung Cấp</th>
                    <th>Khách Hàng</th>
                    <th>Chỉ số KWh</th>
                    <th>Số Tiền</th>
                    <th>Ngày Hạn</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <%
                    InvoiceDAO dao = new InvoiceDAO();
                    List<ElectricityInvoice> invoices = dao.getAllElectricityInvoices();
                    for (ElectricityInvoice invoice : invoices) {
                %>
                <tr>
                    <td><%= invoice.getInvoiceId() %></td>
                    <td><%= invoice.getProviderId() %></td>
                    <td><%= invoice.getCustomerId() %></td>
                    <td><%= invoice.getConsumptionKWh() %></td>
                    <td><%= invoice.getAmount() %> VNĐ</td>
                    <td><%= invoice.getDueDate() %></td>
                    <td><%= invoice.getStatus() %></td>
                    <td>
                        <form action="ElectricityInvoiceServlet" method="post" style="display:inline;">
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
