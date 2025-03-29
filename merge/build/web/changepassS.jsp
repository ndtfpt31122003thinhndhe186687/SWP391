<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mini Finance - Đổi mật khẩu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- CSS FILES -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;700&display=swap" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-icons.css" rel="stylesheet">
    <link href="css/tooplate-mini-finance.css" rel="stylesheet">
</head>
<body>

<header class="navbar sticky-top flex-md-nowrap bg-danger">
    <div class="col-md-3 col-lg-3 me-0 px-3 fs-6">
        <a class="navbar-brand text-white" href="home">
            <i class="bi-box"></i>
            Finbank
        </a>
    </div>
</header>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-3 d-md-block sidebar collapse">
            <div class="position-sticky py-4 px-3 sidebar-sticky">
                <ul class="nav flex-column h-100">
                    <li class="nav-item">
                        <a class="nav-link" href="viewInforStaff">
                            <i class="bi-person me-2"></i> Hồ sơ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="changeInforStaff">
                            <i class="bi-gear me-2"></i> Chỉnh sửa thông tin
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="changepassstaff">
                            <i class="bi-key me-2"></i> Đổi mật khẩu
                        </a>
                    </li>
                    <li class="nav-item border-top mt-auto pt-2">
                        <a class="nav-link" href="logout">
                            <i class="bi-box-arrow-left me-2"></i> Đăng xuất
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Đổi mật khẩu</h1>
            </div>

            <div class="row my-4">
                <div class="col-lg-7 col-12">
                    <div class="custom-block bg-white p-4 rounded shadow-sm">
                        <h6 class="mb-4 text-danger">Thông tin mật khẩu</h6>

                        <form action="changepassstaff" method="post">
                            <div class="mb-3">
                                <label class="form-label">Mật khẩu cũ</label>
                                <input type="password" class="form-control" name="opass" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mật khẩu mới</label>
                                <input type="password" class="form-control" name="newpass" required>
                                <small class="text-muted">Ít nhất 6 ký tự, bao gồm chữ hoa và thường.</small>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Xác nhận mật khẩu mới</label>
                                <input type="password" class="form-control" name="confirmpass" required>
                            </div>

                            <button type="submit" class="btn btn-danger">Cập nhật mật khẩu</button>

                            <c:if test="${not empty error}">
                                <div class="mt-3 text-danger fw-bold">${error}</div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>

            <footer class="site-footer">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12 col-12">
                            <p class="copyright-text">Copyright © Mini Finance 2048 
                                - Design: <a rel="sponsored" href="https://www.tooplate.com" target="_blank">Tooplate</a></p>
                        </div>
                    </div>
                </div>
            </footer>
        </main>
    </div>
</div>

<script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>
