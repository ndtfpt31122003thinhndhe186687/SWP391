<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi Tiết Bảo Hiểm</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            :root {
                --primary-color: #dc3545;
            }

            body {
                background-color: #f8f9fa;
            }

            .insurance-header {
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

            .insurance-type-badge {
                display: inline-block;
                padding: 0.5rem 1.5rem;
                border-radius: 25px;
                font-size: 1rem;
                font-weight: 500;
                margin: 1rem 0;
            }

            .insurance-type-badge.saving {
                background-color: #fff5f5;
                color: #dc3545;
            }

            .insurance-type-badge.loan {
                background-color: #fce8e6;
                color: #c82333;
            }

            .insurance-type-badge.deposit {
                background-color: #ffe6e6;
                color: #bd2130;
            }

            .insurance-type-badge.withdrawal {
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
        <div class="insurance-header">
            <div class="container">
                <h1 class="display-4">
                    <i class="bi bi-info-circle"></i> Chi tiết Bảo Hiểm
                </h1>
                <p class="lead">Chi tiết về thông tin bảo hiểm ${insurance.insurance_name}</p>
            </div>
        </div>

        <!-- Main Content -->
        <div class="container">
            <div class="detail-card">
                <div class="detail-header">
                    <h2 class="mb-0">Thông tin bảo hiểm</h2>
                </div>
                <div class="detail-body">
                    <div class="text-center">
                        <div class="row mt-4">
                            <div class="col-md-6">
                                <div class="detail-label">ID Bảo Hiểm</div>
                                <div class="detail-value">#${insurance.insurance_id}</div>

                                <div class="detail-label">Tên Bảo Hiểm</div>
                                <div class="detail-value">${insurance.insurance_name}</div>
                                <div class="detail-label">Đánh giá</div>
                                <div class="detail-value">
                                    <c:if test="${star > 0}">
                                    <c:forEach begin="1" end="${star}">
                                        <i class="fa-solid fa-star" style="color: gold;"></i>
                                    </c:forEach>
                                    </c:if>
                                        <c:if test="${star == 0}">
                                            <label>Chưa có đánh giá</label>
                                        </c:if>
                                            <div class="detail-label">
                                 <a href="customerViewFeedbackInsurance?insurance_id=${insurance.insurance_id} "class="detail-label">Xem đánh giá</a>
                             </div>
                                </div>
                                <div class="detail-label">Chính sách</div>
                                <a class="detail-label" href="InsurancePolicyDetails?insurance_id=${insurance.insurance_id}" >Xem thêm</a>
                                <div class="detail-label">Điều khoản</div>
                                <a class="detail-label" href="InsuranceTermDetails?insurance_id=${insurance.insurance_id}">Xem thêm</a>
                            </div>
                            <div class="col-md-6">
                                <div class="detail-label">Email</div>
                                <div class="detail-value">${insurance.email}</div>
                                <div class="detail-label">Số Điện Thoại</div>
                                <div class="detail-value">${insurance.phone_number}</div>
                                <div class="detail-label">Địa Chỉ</div>
                                <div class="detail-value">${insurance.address}</div>

                                <div class="detail-label">Trạng Thái</div>
                                <div class="detail-value">
                                    <span class="badge ${insurance.status == 'active' ? 'bg-success' : 'bg-danger'}">
                                        ${insurance.status == 'active' ? 'Hoạt Động' : 'Ngưng Hoạt Động'}
                                    </span>
                                </div>
                            </div>
                        </div>



                        <div class="text-center mt-4">
                            <c:if test="${sessionScope.account != null}">
                                <c:if test="${insurance.status == 'active'}">
                                    <c:if test="${sessionScope.account.role_id == 6}">
                                        <c:forEach var="C" items="${listC}">
                                            <c:if test="${C.customer_id == sessionScope.account.customer_id}">
                                                <a href="feedbackInsurance?insurance_id=${insurance.insurance_id}" class="btn btn-apply me-3">
                                                    <i class="bi bi-check-circle"></i> Phản hồi 
                                                </a>
                                                <a href="chooseInsurancePolicy?insurance_id=${insurance.insurance_id}" class="btn btn-apply me-3">
                                                    <i class="bi bi-check-circle"></i> Nộp tiền bảo hiểm
                                                </a>
                                            </c:if>
                                        </c:forEach>

                                        <a href="buyInsurance?insurance_id=${insurance.insurance_id}" class="btn btn-apply me-3">
                                            <i class="bi bi-check-circle"></i> Mua bảo hiểm
                                        </a>
                                    </c:if>
                                </c:if>
                            </c:if>

                            <a href="Insurance" class="btn btn-back">
                                <i class="bi bi-arrow-left"></i> Trở về trang bảo hiểm
                            </a>
                        </div>

                    </div>
                </div>
            </div>

            <!-- Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
