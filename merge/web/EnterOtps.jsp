<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Verify OTP</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

    <style type="text/css">
        .form-gap {
            padding-top: 70px;
        }
        
        .btn-danger {
            background-color: #d9534f;
            border-color: #d43f3a;
        }
        
        .btn-danger:hover, .btn-danger:focus, .btn-danger:active {
            background-color: #c9302c;
            border-color: #ac2925;
        }
        
        .color-red {
            color: #d9534f;
        }
        
        .panel-default {
            border-color: #ddd;
        }
        
        .panel-default > .panel-heading {
            background-color: #f5f5f5;
            border-color: #ddd;
        }
        
        .input-group-addon {
            background-color: #f5f5f5;
        }
        
        .fa-lock {
            color: #d9534f;
        }
        
        .text-danger {
            color: #d9534f;
        }
    </style>
</head>

<body>
    <div class="form-gap"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="text-center">
                            <h3>
                                <i class="fa fa-lock fa-4x"></i>
                            </h3>
                            <h2 class="text-center">Enter OTP</h2>
                            
                            <c:if test="${not empty mess}">
                                <p class="text-danger ml-1">${mess}</p>
                            </c:if>
                            
                            <div class="panel-body">
                                <form id="register-form" action="VerifyOtpsServlet" role="form" autocomplete="off"
                                    class="form" method="post">

                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="glyphicon glyphicon-envelope color-red"></i>
                                            </span> 
                                            <input id="otp" name="otp" placeholder="Enter OTP"
                                                class="form-control" type="text" required="required">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input name="verify-submit"
                                            class="btn btn-lg btn-danger btn-block"
                                            value="Verify" type="submit">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

