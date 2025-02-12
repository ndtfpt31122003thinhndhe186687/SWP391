<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bank News Dashboard</title>
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

        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>Bank News</h2>
            <a href="home">Back Home</a>
            <a href="newsManage?staff_id=${sessionScope.account.staff_id}">Dashboard</a>
            <a href="addNews">Add News</a>
            <a href="newsStatistic">Statistic of news</a>
        </div>
        <div class="content">
            <h1>News Statistic</h1>
            <div class="stats-container">
                <p><strong>Total Articles: </strong>${requestScope.totalArticle} </p>
                <p><strong>Total Views: </strong>${requestScope.totalView} </p>
            </div>

            <table class="news-table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Number of views</th>
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
        </div>
    </body>
</html>
