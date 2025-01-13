<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Change Password</title>
        <style>
             body {
                margin-top: 50px;
                background-color: #FFB6C1;
                font-family: sans-serif;
            }
            .form {
                max-width: 500px;
                margin: -20px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #020817;
                opacity: 0.9;
            }
            .form h2 {
                text-align: center;
                margin-top: 10px;
                margin-bottom: 17px;
                color: white;
                font-weight: 700;
            }
             .form-group label {
                display: block;
                margin: 14px 0px;
                color: white;
            }
             .form-group input,
            .form-group input.form-attribute{
                padding: 15px;
                border: 1px solid #58b5e8;
                border-radius: 5px;
                background-color: #020817;
                color: white;
            }
            .form-group input {
                width: 90%;
            }

            .form-group input.form-attribute{
                width: 95%;
            }
             input[type="submit"] {
                width: 100%;
                padding: 14px;
                font-size: 15px;
                color: #fff;
                border: none;
                 margin-top: 20px; /* Added margin-top here */
                border-radius: 10px;
                cursor: pointer;
                background: linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1));

            }
             input[type="submit"]:hover
            {
                background: linear-gradient(45deg,#0ABFBC, #FC354C);
            }
               p {
                margin-top: 20px;
                color: white;
                text-align: center;
            }

             a {
                color: white;
                text-decoration: none;
            }
            a:hover {
                text-decoration: none;
                color: red;
            }
        </style>
    </head>
    <body>
         <div class="form">
            <h2>Change Password</h2>
             <p style="color: red">${requestScope.error}</p>
              <p style="color: green">${requestScope.ms1}</p>
            <form action="ChangePassServlets" method="post" onsubmit="return validatePassword()">
               
                 <div class="form-group">
                    <label for="oldPass">Old Password:</label>
<input class="form-attribute" type="password" name="opass" placeholder="Enter your old password" required>
                 </div>
                 <div class="form-group">
                    <label for="newPass">New Password:</label>
                    <input class="form-attribute" type="password" name="pass" id="newPass"  placeholder="Enter your new password" required>
                 </div>
                  <div class="form-group">
                    <label for="confirmPass">Confirm New Password:</label>
                    <input class="form-attribute" type="password" name="confirmPass" id="confirmPass" placeholder="Confirm your new password" required>
                   <p style="color: red" id="confirmPassError"></p>
                 </div>
                <input type="submit" value="Change Password">
                 <p>
                    <a href="login.jsp" style="font-family: arial; font-weight: bold">Login</a>
                </p>
            </form>
        </div>
        <script>
        function validatePassword() {
            const newPass = document.getElementById("newPass").value;
            const confirmPass = document.getElementById("confirmPass").value;
             const confirmPassError = document.getElementById("confirmPassError");

            if (newPass !== confirmPass) {
                 confirmPassError.textContent= "Passwords do not match!";
                return false; // Prevent form submission
            }
             confirmPassError.textContent = "";
            return true; // Allow form submission
        }
        </script>
    </body>
</html>