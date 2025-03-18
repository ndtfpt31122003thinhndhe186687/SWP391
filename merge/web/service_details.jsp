<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thông tin về dịch vụ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            :root {
                --primary-color: #dc3545;
            }

            body {
                background-color: #f8f9fa;
            }

            .service-header {
                background: linear-gradient(135deg, #dc3545, #c82333);
                color: white;
                padding: 3rem 0;
                margin-bottom: 2rem;
            }

            .detail-card {
                background: white;
                border: none;
                box-shadow: 0 2px 15px rgba(0,0,0,0.1);
                border-radius: 12px;
                overflow: hidden;
            }

            .detail-header {
                background-color: #f8f9fa;
                padding: 1.5rem;
                border-bottom: 1px solid #dee2e6;
            }

            .detail-body {
                padding: 2rem;
            }

            .detail-icon {
                font-size: 4rem;
                color: var(--primary-color);
                margin-bottom: 1.5rem;
            }

            .service-type-badge {
                display: inline-block;
                padding: 0.5rem 1.5rem;
                border-radius: 25px;
                font-size: 1rem;
                font-weight: 500;
                margin: 1rem 0;
            }

            .service-type-badge.saving {
                background-color: #fff5f5;
                color: #dc3545;
            }

            .service-type-badge.loan {
                background-color: #fce8e6;
                color: #c82333;
            }

            .service-type-badge.deposit {
                background-color: #ffe6e6;
                color: #bd2130;
            }

            .service-type-badge.withdrawal {
                background-color: #ffecec;
                color: #a71d2a;
            }

            .detail-label {
                color: #5f6368;
                font-weight: 500;
                margin-bottom: 0.5rem;
            }

            .detail-value {
                font-size: 1.1rem;
                margin-bottom: 1.5rem;
            }

            .btn-apply {
                background-color: var(--primary-color);
                color: white;
                border: none;
                padding: 0.75rem 2rem;
                border-radius: 25px;
                font-weight: 500;
                transition: background-color 0.3s;
            }

            .btn-apply:hover {
                background-color: #c82333;
                color: white;
            }

            .btn-back {
                color: var(--primary-color);
                background-color: transparent;
                border: 2px solid var(--primary-color);
                padding: 0.75rem 2rem;
                border-radius: 25px;
                font-weight: 500;
                transition: all 0.3s;
            }

            .btn-back:hover {
                background-color: var(--primary-color);
                color: white;
            }
        </style>
    </head>
    <body>
        <!-- Header Section -->
        <div class="service-header">
            <div class="container">
                <h1 class="display-4">
                    <i class="bi bi-info-circle"></i> Thông tin chi tiết
                </h1>
                <p class="lead">Thông tin về ${service.service_name}</p>
            </div>
        </div>

        <!-- Main Content -->
        <div class="container">
            <div class="detail-card">
                <div class="detail-header">
                    <h2 class="mb-0">Thông tin dịch vụ</h2>
                </div>
                <div class="detail-body">
                    <div class="text-center">
                        <div class="detail-icon">
                            <c:choose>
                                <c:when test="${service.service_type == 'Saving'}">
                                    <i class="bi bi-piggy-bank"></i>
                                </c:when>
                                <c:when test="${service.service_type == 'Loan'}">
                                    <i class="bi bi-cash-coin"></i>
                                </c:when>
                                <c:when test="${service.service_type == 'Deposit'}">
                                    <i class="bi bi-safe"></i>
                                </c:when>
                                <c:when test="${service.service_type == 'Withdrawal'}">
                                    <i class="bi bi-cash-stack"></i>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="service-type-badge ${service.service_type.toLowerCase()}">
                            <c:choose>
                                <c:when test="${service.service_type == 'Saving'}">
                                    Savings
                                </c:when>
                                <c:when test="${service.service_type == 'Loan'}">
                                    Loans
                                </c:when>
                                <c:when test="${service.service_type == 'Deposit'}">
                                    Deposits
                                </c:when>
                                <c:when test="${service.service_type == 'Withdrawal'}">
                                    Withdrawals
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                    <div class="row mt-4">
                        <div class="col-md-6">
                            <div class="detail-label">ID dịch vụ</div>
                            <div class="detail-value">#${service.service_id}</div>

                            <div class="detail-label">Tên dịch vụ</div>
                            <div class="detail-value">${service.service_name}</div>
                        </div>
                        <div class="col-md-6">
                            <div class="detail-label">Loại dịch vụ</div>
                            <div class="detail-value">${service.service_type}</div>

                            <div class="detail-label">Trạng thái</div>
                            <div class="detail-value">
                                <span class="badge ${service.status == 'active' ? 'bg-success' : 'bg-danger'}">
                                    ${service.status == 'active' ? 'Active' : 'Inactive'}
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="mt-4">
                        <div class="detail-label">Mô tả</div>
                        <div class="detail-value">
                            ${service.description}
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <c:if test="${service.status == 'active'}">
                            <c:choose>
                                <c:when test="${service.service_type == 'saving'}">
                                    <a href="sendSavingsApplication" class="btn btn-apply me-3" onclick="showAlert(event)">
                                        <i class="bi bi-check-circle"></i> Sử dụng
                                    </a>
                                </c:when>
                                <c:when test="${service.service_type == 'loan'}">
                                    <a href="SendLoanRequest" class="btn btn-apply me-3">
                                        <i class="bi bi-check-circle"></i> Sử dụng
                                    </a>
                                </c:when>
                                <c:when test="${service.service_type == 'deposit'}">
                                    <a href="" class="btn btn-apply me-3">
                                        <i class="bi bi-check-circle"></i> Sử dụng
                                    </a>
                                </c:when>
                                <c:when test="${service.service_type == 'withdrawal'}">
                                    <a href="" class="btn btn-apply me-3">
                                        <i class="bi bi-check-circle"></i> Sử dụng
                                    </a>
                                </c:when>
                            </c:choose>
                        </c:if>
                        <script>
                            function showAlert(event) {
                                var cardType = "<c:out value='${sessionScope.account.card_type}' />";
                                if (cardType === "credit") {
                                    event.preventDefault(); // Ngăn chuyển trang
                                    alert("Bạn không thể sử dụng dịch vụ này khi thẻ của bạn là thẻ credit.");
                                }
                            }
                        </script>

                        <a href="Service" class="btn btn-back">
                            <i class="bi bi-arrow-left"></i> Quay lại
                        </a>

                    </div>

                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
