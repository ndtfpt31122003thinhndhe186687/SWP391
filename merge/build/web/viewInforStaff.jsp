<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Mini Finance - Profile Page</title>
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

            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <form class="custom-form header-form ms-lg-3 ms-md-3 me-lg-auto me-md-auto order-2 order-lg-0 order-md-0" action="" method="get" role="form">
                <input class="form-control bg-white text-dark" name="search" type="text" placeholder="Search" aria-label="Search">
            </form>

            <div class="dropdown px-3">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="images/medium-shot-happy-man-smiling.jpg" class="profile-image img-fluid" alt="">
                </a>
                <ul class="dropdown-menu bg-white shadow">
                    <li>
                        <div class="dropdown-menu-profile-thumb d-flex">
                            <div class="d-flex flex-column">
                                <small>${sessionScope.account.full_name}</small>
                                <small>${sessionScope.account.email}</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a class="dropdown-item" href="viewInforStaff">
                            <i class="bi-person me-2"></i>
                             Xem hồ sơ
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
                        <a class="nav-link active" href="viewInforStaff">
                            <i class="bi-person me-2"></i>
                            Hồ sơ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="changeInforStaff">
                            <i class="bi-gear me-2"></i>
                            Chỉnh sửa thông tin
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="changepassstaff">
                            <i class="bi-key me-2"></i>
                            Đổi mật khẩu
                        </a>
                    </li>
                    <li class="nav-item border-top mt-auto pt-2">
                        <a class="nav-link" href="logout">
                            <i class="bi-box-arrow-left me-2"></i>
                            Đăng xuất
                        </a>
                    </li>
                </ul>

            </div>
        </nav>                

        <main class="main-wrapper col-md-9 ms-sm-auto py-4 col-lg-9 px-md-4 border-start">
            <div class="title-group mb-3">
                <h1 class="h2 mb-0 text-danger">Profile</h1>
            </div>
            <div class="row my-4">
                <div class="col-lg-7 col-12 mx-auto">
                    <div class="custom-block custom-block-profile p-4 shadow rounded">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex align-items-center">
                                <i class="bi bi-person-circle me-2 text-danger"></i>
                                <strong class="me-2">Full Name:</strong>
                                <span>${staff.full_name}</span>
                            </li>
                            <li class="list-group-item d-flex align-items-center">
                                <i class="bi bi-envelope me-2 text-danger"></i>
                                <strong class="me-2">Email:</strong>
                                <a href="mailto:${staff.email}" class="text-decoration-none">${staff.email}</a>
                            </li>
                            <li class="list-group-item d-flex align-items-center">
                                <i class="bi bi-telephone me-2 text-danger"></i>
                                <strong class="me-2">Phone:</strong>
                                <a href="tel:${staff.phone_number}" class="text-decoration-none">${staff.phone_number}</a>
                            </li>
                            <li class="list-group-item d-flex align-items-center">
                                <i class="bi bi-calendar3 me-2 text-danger"></i>
                                <strong class="me-2">Date of Birth:</strong>
                                <span><fmt:formatDate value="${staff.date_of_birth}" pattern="dd/MM/yyyy" /></span>
                            </li>
                            <li class="list-group-item d-flex align-items-center">
                                <i class="bi bi-gender-ambiguous me-2 text-danger"></i>
                                <strong class="me-2">Gender:</strong>
                                <span>${staff.gender}</span>
                            </li>
                            <li class="list-group-item d-flex align-items-center">
                                <i class="bi bi-geo-alt me-2 text-danger"></i>
                                <strong class="me-2">Address:</strong>
                                <span>${staff.address}</span>
                            </li>
                        </ul>
                    </div>
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

<!-- JAVASCRIPT FILES -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/custom.js"></script>
</body>
</html>