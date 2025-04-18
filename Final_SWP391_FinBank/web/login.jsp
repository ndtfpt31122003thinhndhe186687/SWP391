<%-- 
    Document   : Login
    Created on : Jan 10, 2025, 10:24:01 PM
    Author     : Windows
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Đăng nhập</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body class="bg-gradient-primary">

        <div class="container">

            <!-- Outer Row -->
            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row">
                                <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">Chào mừng!</h1>
                                        </div>
                                        <div id="logreg-forms">
                                            <form class="form-signin" action="login" method="post">
                                                <p class="text-danger">${mess}</p>
                                                <div class="form-group">
                                                    <input name="username"  type="text"  id="inputEmail" class="form-control" placeholder="Tên đăng nhập" required="" autofocus="">
                                                </div>
                                                <div class="form-group">
                                                    <input name="pass"  type="password"  id="inputPassword" class="form-control" placeholder="Mật khẩu" required="">
                                                </div>
                                                <select name="role">
                                                    <option value="customer">Khách hàng</option>
                                                    <option value="staff">Nhân viên</option>
                                                    <option value="insurance">Bảo hiểm</option>
                                                </select>                                         
                                                <button class="btn btn-success btn-block" type="submit"><i class="fas fa-sign-in-alt"></i>Đăng nhập</button>
                                                <hr>
                                            </form>
                                            <hr>
                                            <div class="text-center">
                                                <a class="small" href="forgotpass">Quên mật khẩu?</a>
                                            </div>
                                            <div class="text-center">
                                                <a class="small" href="register">Tạo tài khoản!</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>

    </body>

</html>