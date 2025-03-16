<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Mini Finance - Settings</title>

        <!-- CSS FILES -->      
        <link rel="preconnect" href="https://fonts.googleapis.com">

        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

        <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">

        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="css/bootstrap-icons.css" rel="stylesheet">

        <link href="css/tooplate-mini-finance.css" rel="stylesheet">
        <style>
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
            .table {
                border-collapse: collapse;
                width: 100%;
                background-color: #fff;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }

            .table thead {
                background-color: #007bff;
                color: white;
            }

            .table thead th {
                padding: 12px;
                text-align: left;
                font-weight: bold;
            }

            .table tbody tr {
                transition: background 0.3s;
            }

            .table tbody tr:hover {
                background-color: #f1f1f1;
            }

            .table td, .table th {
                padding: 10px;
                border-bottom: 1px solid #ddd;
            }

            .badge {
                font-size: 14px;
                padding: 5px 10px;
                border-radius: 12px;
            }

            .bg-success {
                background-color: #28a745 !important;
            }

            .bg-danger {
                background-color: #dc3545 !important;
            }

            .btn {
                padding: 6px 12px;
                font-size: 14px;
                border-radius: 5px;
                transition: 0.3s;
            }

            .btn:hover {
                opacity: 0.8;
            }

            .pagination {
                margin-top: 20px;
                display: flex;
                justify-content: center;
                gap: 8px;
            }

            .pagination a {
                text-decoration: none;
                padding: 6px 12px;
                border: 1px solid #007bff;
                border-radius: 5px;
                color: #007bff;
                transition: 0.3s;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: white;
            }

            .current-page {
                padding: 6px 12px;
                border-radius: 5px;
                background-color: #007bff;
                color: white;
                font-weight: bold;
            }
            .news-content {
                max-width: 300px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .news-image {
                width: 100px;
                height: 80px;
                object-fit: cover;
                border-radius: 5px;
                border: 1px solid #ddd;
            }
            .table-actions {
                white-space: nowrap;
            }

            .table-actions button {
                margin-right: 5px; /* Khoảng cách giữa hai nút */
            }

        </style>
    </head>
    <body>
        <header class="navbar sticky-top flex-md-nowrap bg-danger">
            <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
                <a class="navbar-brand text-white" href="">
                    <i class="bi-box"></i>
                    Mini Finance
                </a>
            </div>

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="" method="get" role="form">
                <input class="form-control bg-white text-dark" name="search" type="text" placeholder="Search" aria-label="Search">
            </form>

            <div class="navbar-nav me-lg-2">
                <div class="nav-item text-nowrap d-flex align-items-center">
                    <div class="dropdown ps-3">
                        <a class="nav-link dropdown-toggle text-center text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="navbarLightDropdownMenuLink">
                            <i class="bi-bell"></i>
                            <span class="position-absolute start-100 translate-middle p-1 bg-white border border-danger rounded-circle">
                                <span class="visually-hidden">New alerts</span>
                            </span>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-lg-end notifications-block-wrap bg-white text-danger shadow" aria-labelledby="navbarLightDropdownMenuLink">
                            <small class="text-danger">Notifications</small>

                            <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                                <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                    <div class="notifications-icon-wrap bg-danger text-white">
                                        <i class="notifications-icon bi-check-circle-fill"></i>
                                    </div>
                                    <div>
                                        <span>Your account has been created successfully.</span>
                                        <p>12 days ago</p>
                                    </div>
                                </a>
                            </li>

                            <li class="notifications-block border-bottom border-danger pb-2 mb-2">
                                <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                    <div class="notifications-icon-wrap bg-danger text-white">
                                        <i class="notifications-icon bi-folder"></i>
                                    </div>
                                    <div>
                                        <span>Please check. We have sent a Daily report.</span>
                                        <p>10 days ago</p>
                                    </div>
                                </a>
                            </li>

                            <li class="notifications-block">
                                <a class="dropdown-item d-flex align-items-center text-danger" href="#">
                                    <div class="notifications-icon-wrap bg-danger text-white">
                                        <i class="notifications-icon bi-question-circle"></i>
                                    </div>
                                    <div>
                                        <span>Account verification failed.</span>
                                        <p>1 hour ago</p>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="dropdown px-3">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid" alt="">
                </a>
                <ul class="dropdown-menu bg-white shadow">
                    <li>
                        <div class="dropdown-menu-profile-thumb d-flex">
                            <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid me-3" alt="">

                            <div class="d-flex flex-column">
                                <c:if test="${sessionScope.account.role_id==6}">
                                    <small>${sessionScope.account.customer_id}</small>
                                    <small>${sessionScope.account.email}</small>
                                </c:if>
                                <c:if test="${sessionScope.account.role_id==1}">
                                    <small>${sessionScope.account.staff_id}</small>
                                    <small>${sessionScope.account.email}</small>
                                </c:if> 
                            </div>
                        </div>
                    </li>
                    <li>
                        <a class="dropdown-item" href="profile.html">
                            <i class="bi-person me-2"></i>
                            Profile
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="setting.html">
                            <i class="bi-gear me-2"></i>
                            Settings
                        </a>
                    </li>
                    <li class="border-top mt-3 pt-2 mx-4">
                        <a class="dropdown-item ms-0 me-0" href="logout">
                            <i class="bi-box-arrow-left me-2"></i>
                            Logout
                        </a>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</header>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
            <div class="position-sticky py-4 px-3 sidebar-sticky">
                <ul class="nav flex-column h-100">
                    <li class="nav-item">
                        <a class="nav-link " aria-current="page" href="staff_management?status=all&sort=full_name&type=&page=1&pageSize=2">
                            <i class="me-2"></i>
                            Staff Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="service_management?type=services">
                            <i class="me-2"></i>
                            Service Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="transaction_management">
                            <i class=" me-2"></i>
                            Transaction Management
                        </a>
                    </li>                   

                    <li class="nav-item">
                        <a class="nav-link " href="statistic_management">
                            <i class="me-2"></i>
                            Statistic Management
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link " href="serviceTermManagement?serviceName=all&sort=all&page=1&pageSize=4">
                            <i class="me-2"></i>
                            Quản lí dịch vụ
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active" href="newsResponse?categoryId=0&sort=title&page=1&pageSize=4">
                            <i class="me-2"></i>
                            Kiểm duyệt tin tức
                        </a>
                    </li>


                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Kiểm duyệt tin tức</h1>
            </div>

            <input type="hidden" name ="page" value="1"> 

            <div class="filter-sort-bar">
                <label for="filterCategory">Lọc theo Danh mục</label>
                <select id="filterCategory" class="filter-dropdown" onchange="filterCategory()">
                    <option value="0" ${requestScope.categoryId == 0 ? 'selected' : ''}}>All </option>        
                    <c:forEach var="n" items="${requestScope.listNc}">
                        <option value="${n.category_id}" ${requestScope.categoryId == n.category_id ? 'selected':''}>${n.category_name}</option>        
                    </c:forEach>
                </select>

                <label for="sortNews">Sắp xếp theo:</label>
                <select id="sortNews" class="filter-dropdown" onchange="sortNews()">
                    <option value="all" ${requestScope.sort == 'all' ? 'selected' : ''}>All</option>
                    <option value="title" ${requestScope.sort == 'title' ? 'selected' : ''}>Tiêu đề</option>
                    <option value="content" ${requestScope.sort == 'content' ? 'selected' : ''}>Nội dung</option>
                </select>

                <label for="pageSize">Số bài mỗi trang:</label>
                <select id="pageSize" class="filter-dropdown" onchange="changePageSize()">
                    <option value="4" ${requestScope.pageSize == 4 ? 'selected' : ''}>4</option>
                    <option value="8" ${requestScope.pageSize == 8 ? 'selected' : ''}>8</option>
                    <option value="12" ${requestScope.pageSize == 12 ? 'selected' : ''}>12</option>
                </select>

            </div>  

            <div class="search-bar">
                <form action="searchNews">
                    <input type="text" placeholder="Search" name="searchName" id="searchName" value="${param.searchName}"> 
                    <input type="hidden" name="page" value="${page}">
                    <input type="hidden" name="pageSize" value="${pageSize}">
                    <button style="background-color: #d32f2f; color: white; border: none; padding: 5px 10px;">Search</button>
                </form>
            </div>    

            <div class="mt-3">
                <table class="table table-bordered">
                    <thead>
                        <tr>                      
                            <th>Thể loại Tin tức</th>
                            <th>Tiêu đề</th>
                            <th>Nội dung</th>
                            <th>Hình ảnh</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <c:forEach items="${requestScope.listN}" var="news">
                        <tr>
                            <td>${news.category_name}</td>
                            <td>
                                <a href="#" class="news-title news-content" 
                                   onclick="showNewsModal('${news.category_name}',
                                                   '${fn:escapeXml(news.title)}',
                                                   `${fn:escapeXml(news.content)}`,
                                                   '${news.staff_name}',
                                                   '${news.created_at}',
                                                   '${news.updated_at}',
                                                   '${news.picture}')">
                                    ${news.getTitle()}
                                </a>
                            </td>
                            <td class="news-content">${news.content}</td>
                            <td>
                                <img src="<%= request.getContextPath() %>/imageNews/${news.picture}" alt="News Image" class="news-image">
                            </td>
                            <td class="table-actions">
                                <form action="newsApproval" method="post">
                                    <input type="hidden" name="news_id" value="${news.news_id}" />
                                    <button type="submit" name="action" value="approved" class="btn btn-success">Approve</button>
                                    <button type="submit" name="action" value="rejected" class="btn btn-danger">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                    <div id="newsModal" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);
                         width: 50%; max-height: 80vh; overflow-y: auto; background: white; padding: 20px; border: 2px solid black;
                         box-shadow: 0px 0px 10px gray;">
                        <p><strong>Thể loại:</strong> <span id="modalCategory"></span></p>
                        <p><strong>Tiêu đề:</strong> <span id="modalTitle"></span></p>
                        <p><strong>Nội dung:</strong></p>
                        <div id="modalContent" style="max-height: 50vh; overflow-y: auto; border: 1px solid #ddd; padding: 10px;"></div>
                        <p><strong>Người viết: </strong> <span id="modalStaff"></span></p>
                        <p><strong>Được tạo lúc:</strong> <span id="modalCreatedAt"></span></p>
                        <p><strong>Được cập nhật lúc:</strong> <span id="modalUpdatedAt"></span></p>
                        <p><strong>Hình ảnh:</strong></p>
                        <img id="modalImage" src="" style="max-width: 100%; display: none;" />
                        <br>
                        <button onclick="closeModal()" style="margin-top: 10px; padding: 5px 10px; background-color: #d32f2f;
                                color: white; border: none; border-radius: 5px;">Close</button>
                    </div>
                </table>
            </div>

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
                                <a href="newsResponse?categoryId=${categoryId}&status=${status}&sort=${sort}&page=${i}&pageSize=${pageSize}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </div>
        </main>

        <script type="text/javascript">
            function filterCategory() {
                var categoryId = document.getElementById("filterCategory").value;
                var sort = document.getElementById("sortNews").value;
                var pageSize = document.getElementById("pageSize").value;
                window.location.href = "newsResponse?categoryId=" + categoryId + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
            }
            function sortNews() {
                var categoryId = document.getElementById("filterCategory").value;
                var sort = document.getElementById("sortNews").value;
                var pageSize = document.getElementById("pageSize").value;
                window.location.href = "newsResponse?categoryId=" + categoryId + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
            }
            function changePageSize() {
                var categoryId = document.getElementById("filterCategory").value;
                var pageSize = document.getElementById("pageSize").value;
                var sort = document.getElementById("sortNews").value;
                var searchInput = document.getElementById("searchName");

                var searchName = searchInput ? searchInput.value.trim().replace(/\s+/g, " ") : "";

                if (searchName !== "") {
                    window.location.href = "searchNews?searchName=" + encodeURIComponent(searchName) + "&page=1&pageSize=" + pageSize;
                } else {
                    window.location.href = "newsResponse?categoryId=" + categoryId + "&sort=" + sort + "&page=1&pageSize=" + pageSize;
                }
            }
            function showNewsModal(category, title, content, staff, createdAt, updatedAt, image) {
                function formatDate(dateString) {
                    let date = new Date(dateString);
                    return date.toLocaleDateString("vi-VN");
                }
                document.getElementById("modalTitle").innerText = title;
                document.getElementById("modalCategory").innerText = category;
                document.getElementById("modalContent").innerHTML = content;
                document.getElementById("modalStaff").innerText = staff;
                document.getElementById("modalCreatedAt").innerText = formatDate(createdAt);
                document.getElementById("modalUpdatedAt").innerText = formatDate(updatedAt);
                let imgElement = document.getElementById("modalImage");
                if (image && image !== "null") {
                    imgElement.src = "<%= request.getContextPath() %>/imageNews/" + image;
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
    </div>
</div>

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>