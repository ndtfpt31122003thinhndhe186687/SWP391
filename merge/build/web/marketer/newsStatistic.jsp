<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng tin ngân hàng</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }
            body {
                display: flex;
                height: 100vh;
            }
            .sidebar {
                width: 250px;
                background-color: #b71c1c;
                color: white;
                padding: 20px;
                display: flex;
                flex-direction: column;
                gap: 15px;
            }
            .sidebar h2 {
                text-align: center;
                margin-bottom: 20px;
            }
            .sidebar a {
                text-decoration: none;
                color: white;
                padding: 10px;
                display: block;
                background: #d32f2f;
                border-radius: 5px;
                text-align: center;
            }
            .sidebar a:hover {
                background: #c62828;
            }
            .content {
                flex: 1;
                padding: 20px;
            }

            .news-table {
                width: 100%;
                border-collapse: collapse;
            }
            .news-table th, .news-table td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: left;
            }
            .news-table th {
                background-color: #d32f2f;
                color: white;
            }
            .news-content {
                max-width: 300px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .stats-container {
                background-color: #f8d7da;
                padding: 15px;
                border-radius: 8px;
                border-left: 5px solid #b71c1c;
                margin-bottom:15px;
            }

            .stats-container p {
                font-size: 18px;
                font-weight: bold;
                color: #b71c1c;
            }

            .pagination {
                margin-top: 15px;
                text-align: center;
            }
            .pagination a {
                display: inline-block;
                padding: 8px 12px;
                margin: 0 4px;
                text-decoration: none;
                background: #d32f2f;
                color: white;
                border-radius: 4px;
            }
            .pagination a.active {
                background: #b71c1c;
            }
            .pagination a:hover {
                background: #c62828;
            }

        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>Tin tức ngân hàng</h2>
            <a href="home">Trang chủ</a>
            <a href="newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=0&status=all&sort=all&page=1&pageSize=4">Bảng điều khiển</a>
            <a href="addNews">Thêm tin tức</a>
            <a href="newsStatistic?page=1">Thống kê tin tức</a>
        </div>
        <div class="content">
            <h1>Thống kê tin tức cá nhân</h1>
            <div class="stats-container">
                <p><strong>Tổng số bài viết: </strong>${requestScope.totalArticle} </p>
                <p><strong>Tổng lượt xem: </strong>${requestScope.totalView} </p>
            </div>

            <table class="news-table">
                <thead>
                    <tr>
                        <th>Tiêu đề bài viết</th>
                        <th>Số lượt xem</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${newsView}" var="newsView">
                        <tr>
                            <td>${newsView.title}</td>
                            <td>${newsView.newsAmount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- Phân trang -->
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="newsStatistic?page=${currentPage - 1}">« Trước</a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="newsStatistic?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="newsStatistic?page=${currentPage + 1}">Tiếp »</a>
                </c:if>
            </div>

        </div>
    </body>
</html>
