<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng điều khiển Tin tức Ngân hàng</title>
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
            .filter-sort-bar {
                margin-bottom: 20px;
                display: flex;
                gap: 20px;
                align-items: center;
            }
            .filter-sort-bar label {
                font-weight: bold;
                color: #333;
            }
            .filter-dropdown {
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #ddd;
                background-color: #fff;
                font-size: 14px;
            }
            .filter-dropdown:focus {
                border-color: #d32f2f;
                outline: none;
            }
            .filter-dropdown:hover {
                background-color: #f0f0f0;
            }
            .filter-dropdown option:hover {
                background-color: #d32f2f;
                color: white;
            }
            .news-table {
                width: 100%;
                border-collapse: collapse;
            }
            .search-bar{
                margin-bottom: 15px;
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
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 20px;
                padding: 10px;
                width: 100%;
            }

            .pagination a, .pagination span {
                padding: 8px 12px;
                margin: 0 5px;
                text-decoration: none;
                border: 1px solid #ddd;
                color: #007bff;
                background-color: #fff;
                border-radius: 5px;
                display: inline-block;
                text-align: center;
            }

            .pagination a:hover {
                background-color: red;
                color: white;
            }

            .pagination .current-page {
                background-color: red;
                color: white;
                font-weight: bold;
                border-radius: 5px;
            }
            .news-image {
                width: 100px;
                height: 80px;
                object-fit: cover;
                border-radius: 5px;
                border: 1px solid #ddd;
            }
            .news-title {
                text-decoration: none;
                color: black;
            }
            .news-title:hover {
                color: #d32f2f;
                text-decoration: none;
            }
            th, td {
                white-space: nowrap;
            }
            th:last-child, td:last-child {
                width: 120px; /* Điều chỉnh tùy ý */
                text-align: center;
            }

        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>Tin tức Ngân hàng</h2>      
            <a href="home">Trở về Trang chủ</a>
            <a href="newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=0&status=all&sort=all&page=1&pageSize=4">Bảng điều khiển</a>
            <a href="addNews">Thêm Tin tức</a>
            <a href="newsStatistic">Thống kê tin tức</a>
        </div>
        <div class="content">
            <h1>Quản lý Tin tức</h1>

            <input type="hidden" name="page" value="1">
            <div class="filter-sort-bar">
                <label for="filterCategory">Lọc theo Danh mục</label>
                <select id="filterCategory" class="filter-dropdown" onchange="filterCategory()">
                    <option value="0" ${requestScope.categoryId == 0 ? 'selected' : ''}}>All </option>        
                    <c:forEach var="n" items="${requestScope.listNc}">
                        <option value="${n.category_id}" ${requestScope.categoryId == n.category_id ? 'selected':''}>${n.category_name}</option>        
                    </c:forEach>
                </select>

                <label for="filterStatus">Lọc theo trạng thái:</label>
                <select id="filterStatus" class="filter-dropdown" onchange="filterNews()">
                    <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>All</option>
                    <option value="draft" ${requestScope.status == 'draft' ? 'selected' : ''}>Draft</option>
                    <option value="approved" ${requestScope.status == 'approved' ? 'selected' : ''}>Approved</option>
                    <option value="pending" ${requestScope.status == 'pending' ? 'selected' : ''}>Pending</option>
                </select>

                <label for="sortNews">Sắp xếp theo:</label>
                <select id="sortNews" class="filter-dropdown" onchange="sortNews()">
                    <option value="all" ${requestScope.sort == 'all' ? 'selected' : ''}>All</option>
                    <option value="created_at" ${requestScope.sort == 'created_at' ? 'selected' : ''}>Created Date</option>
                    <option value="title" ${requestScope.sort == 'title' ? 'selected' : ''}>Title</option>
                </select>

                <label for="pageSize">Số bài mỗi trang:</label>
                <select id="pageSize" class="filter-dropdown" onchange="changePageSize()">
                    <option value="4" ${requestScope.pageSize == 4 ? 'selected' : ''}>4</option>
                    <option value="8" ${requestScope.pageSize == 8 ? 'selected' : ''}>8</option>
                    <option value="12" ${requestScope.pageSize == 12 ? 'selected' : ''}>12</option>
                </select>
            </div>

            <div class="search-bar">
                <form action="searchNews" method="get">
                    <input type="text" placeholder="Tìm kiếm tin tức theo tiêu đề" name="searchName" id="searchName" value="${param.searchName}">
                    <input type="hidden" name="page" value="${page}">
                    <input type="hidden" name="pageSize" value="${pageSize}">
                    <button type="submit" style="background-color: #d32f2f;
                            color: white;
                            border: none;
                            padding: 5px 10px;">Search</button>
                </form>
            </div>
            <table class="news-table">
                <thead>
                    <tr>                      
                        <th>Thể loại Tin tức</th>
                        <th>Tiêu đề</th>
                        <th>Nội dung</th>
                        <th>Được tạo vào lúc</th>
                        <th>Trạng thái</th>
                        <th>Hình ảnh</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listN}" var="news">
                        <tr>
                            <td>${news.category_name}</td>
                            <td>
                                <a href="#" class="news-title news-content" 
                                   onclick="showNewsModal('${news.category_name}',
                                                   '${news.title}',
                                                   `${news.content}`,
                                                   '${news.created_at}',
                                                   '${news.updated_at}',
                                                   '${news.picture}')">
                                    ${news.getTitle()}
                                </a>
                            </td>                          
                            <td class="news-content">${news.content}</td>
                            <td>
                                <fmt:formatDate value="${news.created_at}" pattern="dd-MM-yyyy" />
                            </td>                           
                            <td>${news.status}</td>  
                            <td>
                                <img src="imageNews/${news.picture}" alt="News Image" class="news-image">
                            </td>
                            <td>
                                <a href="editNews?news_id=${news.news_id}&category_id=${news.category_id}" style="background-color: #d32f2f;
                                   color: white;
                                   padding: 5px 10px;
                                   text-decoration: none;
                                   border-radius: 5px;">✏️</a>
                                <a onclick="doDelete('${news.news_id}', '${news.category_id}')" href="#" style="background-color: #b71c1c;
                                   color: white;
                                   padding: 5px 10px;
                                   text-decoration: none;
                                   border-radius: 5px;" >🗑️</a>
                                <c:if test="${news.status=='draft'}">
                                    <a href="sendNews?news_id=${news.news_id}&categoryId=${categoryId}&status=${status}&sort=${sort}&page=${page}&pageSize=${pageSize}" 
                                       style="background-color: #d32f2f; color: white; padding: 5px 10px; text-decoration: none; border-radius: 5px;">
                                        📤
                                    </a>

                                </c:if>
                                <c:if test="${news.status!='approved' && news.status!='draft'}">
                                    <a href="cancelSend?news_id=${news.news_id}&categoryId=${categoryId}&status=${status}&sort=${sort}&page=${page}&pageSize=${pageSize}" style="background-color: #d32f2f;
                                       color: white;
                                       padding: 5px 10px;
                                       text-decoration: none;
                                       border-radius: 5px;">❌</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <!-- Modal -->
                <div id="newsModal" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);
                     width: 50%; max-height: 80vh; overflow-y: auto; background: white; padding: 20px; border: 2px solid black;
                     box-shadow: 0px 0px 10px gray;">
                    <p><strong>Thể loại:</strong> <span id="modalCategory"></span></p>
                    <p><strong>Tiêu đề:</strong> <span id="modalTitle"></span></p>
                    <p><strong>Nội dung:</strong></p>
                    <div id="modalContent" style="max-height: 50vh; overflow-y: auto; border: 1px solid #ddd; padding: 10px;"></div>
                    <p><strong>Được tạo lúc:</strong> <span id="modalCreatedAt"></span></p>
                    <p><strong>Được cập nhật lúc:</strong> <span id="modalUpdatedAt"></span></p>
                    <p><strong>Hình ảnh:</strong></p>
                    <img id="modalImage" src="" style="max-width: 100%; display: none;" />
                    <br>
                    <button onclick="closeModal()" style="margin-top: 10px; padding: 5px 10px; background-color: #d32f2f;
                            color: white; border: none; border-radius: 5px;">Close</button>
                </div>

                <script type="text/javascript">
                    function doDelete(id, cateId) {
                        if (confirm("Are you sure to delete this news ?")) {
                            window.location = "deleteNews?news_id=" + id + "&categoryId=" + cateId;
                        }
                    }
                    function filterCategory() {
                        var categoryId = document.getElementById("filterCategory").value;
                        var status = document.getElementById("filterStatus").value;
                        var sort = document.getElementById("sortNews").value;
                        var pageSize = document.getElementById("pageSize").value;
                        window.location.href = "newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=" + categoryId + "&status=" + status + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
                    }

                    function filterNews() {
                        var categoryId = document.getElementById("filterCategory").value;
                        var status = document.getElementById("filterStatus").value;
                        var sort = document.getElementById("sortNews").value;
                        var pageSize = document.getElementById("pageSize").value;
                        window.location.href = "newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=" + categoryId + "&status=" + status + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
                    }
                    function sortNews() {
                        var categoryId = document.getElementById("filterCategory").value;
                        var sort = document.getElementById("sortNews").value;
                        var status = document.getElementById("filterStatus").value;
                        var pageSize = document.getElementById("pageSize").value;
                        window.location.href = "newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=" + categoryId + "&status=" + status + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
                    }
                    function changePageSize() {
                        var categoryId = document.getElementById("filterCategory").value;
                        var pageSize = document.getElementById("pageSize").value;
                        var status = document.getElementById("filterStatus").value;
                        var sort = document.getElementById("sortNews").value;
                        var searchInput = document.getElementById("searchName");

                        var searchName = searchInput ? searchInput.value.trim().replace(/\s+/g, " ") : "";

                        if (searchName !== "") {
                            window.location.href = "searchNews?searchName=" + encodeURIComponent(searchName) + "&page=1&pageSize=" + pageSize;
                        } else {
                            window.location.href = "newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=" + categoryId + "&status=" + status + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
                        }
                    }

                    function showNewsModal(category, title, content, createdAt, updatedAt, image) {
                        function formatDate(dateString) {
                            let date = new Date(dateString);
                            return date.toLocaleDateString("vi-VN");
                        }
                        document.getElementById("modalTitle").innerText = title;
                        document.getElementById("modalCategory").innerText = category;
                        document.getElementById("modalContent").innerHTML = content;
                        document.getElementById("modalCreatedAt").innerText = formatDate(createdAt);
                        document.getElementById("modalUpdatedAt").innerText = formatDate(updatedAt);
                        let imgElement = document.getElementById("modalImage");
                        if (image && image !== "null") {
                            imgElement.src = "imageNews/" + image;
                            imgElement.style.display = "block";
                        } else {
                            imgElement.style.display = "none";
                        }

                        document.getElementById("newsModal").style.display = "block";
                    }

                    function closeModal() {
                        document.getElementById("newsModal").style.display = "none";
                    }

                </script>  
                </tbody>
            </table>
            <div class="pagination">
                <c:if test="${not empty param.searchName}">
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <c:choose>
                            <c:when test="${i == page}">
                                <span class="current-page">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="searchNews?searchName=${param.searchName}&page=${i}&pageSize=${pageSize}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
                <c:if test="${empty param.searchName}">      
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <c:choose>
                            <c:when test="${i == page}">
                                <span class="current-page">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="newsManage?staff_id=${sessionScope.account.staff_id}&categoryId=${categoryId}&status=${status}&sort=${sort}&page=${i}&pageSize=${pageSize}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </div>
        </div>


    </body>
</html>
