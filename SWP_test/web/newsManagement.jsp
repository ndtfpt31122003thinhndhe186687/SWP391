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
            .search-bar {
                margin-bottom: 20px;
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
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>Bank News</h2>
            <a href="newsManage">Dashboard</a>
            <a href="addNews">Add News</a>
            <a href="#">Statistic of news</a>
        </div>
        <div class="content">
            <h1>News Management</h1>
            <div class="search-bar">
                <input type="text" placeholder="Search news...">
                <button style="background-color: #d32f2f; color: white; border: none; padding: 5px 10px;">Search</button>
            </div>
            <table class="news-table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Created at</th>
                        <th>Updated at</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listN}" var="news">
                        <tr>
                            <td>${news.title}</td>
                            <td class="news-content">${news.content}</td>
                            <td>${news.created_at}</td>
                            <td>${news.updated_at}</td>
                            <td>${news.status}</td>  
                            <td>
                                <a href="editNews?news_id=${news.news_id}" style="background-color: #d32f2f; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px;">Edit</a>
                                <a onclick="doDelete('${news.news_id}')" href="#" style="background-color: #b71c1c; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px;" >Delete</a>
                                <c:if test="${news.status=='draft'}">
                                <a href="sendNews?news_id=${news.news_id}" style="background-color: #d32f2f; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px;">Send</a>
                                </c:if>
                                <c:if test="${news.status!='approved' && news.status!='draft'}">
                                <a href="cancelSend?news_id=${news.news_id}" style="background-color: #d32f2f; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px;">Cancel sending</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                <script type="text/javascript">
                    function doDelete(id) {
                        if (confirm("Are you sure to delete this news ?")) {
                            window.location = "deleteNews?news_id=" + id;
                        }
                    }
                </script>  

                </tbody>
            </table>
        </div>
    </body>
</html>
