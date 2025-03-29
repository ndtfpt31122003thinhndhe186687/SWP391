<%-- 
    Document   : customerViewFeedback
    Created on : Mar 25, 2025, 12:36:17 AM
    Author     : Windows
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Phản hồi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    </head>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .feedback-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 15px;
        }
        .star-rating .fa-star {
            color: gold;
        }
    </style>
    <body>
        <div class="container mt-5">
            <h1 class="mb-4 text-center text-primary">Phản hồi về dịch vụ: <strong>${service_name}</strong></h1>

            <c:forEach items="${listF}" var="f">
                <div class="feedback-card p-3">
                    <h4><i class="bi bi-person-circle"></i> ${f.full_name}</h4>
                    <p><strong>Nội dung phản hồi:</strong> ${f.feedback_content}</p>
                    <div class="star-rating">
                        <c:if test="${star > 0}">
                            <c:forEach begin="1" end="${star}">
                                <i class="fa-solid fa-star" style="color: gold;"></i>
                            </c:forEach>
                        </c:if>
                       
                    </div>
                </div>
            </c:forEach>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
