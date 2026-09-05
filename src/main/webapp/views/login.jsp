<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập hệ thống</title>
    <!-- Bootstrap & FontAwesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body { background-color: #f8f9fa; font-family: Arial, sans-serif; }
        .login-card {
            max-width: 450px;
            margin: 60px auto;
            padding: 30px;
            background: #ffffff;
            border: 1px solid #e1e8ed;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            border-radius: 4px;
        }
        .login-title {
            text-align: center;
            color: #777777;
            font-size: 20px;
            margin-bottom: 25px;
            font-weight: normal;
        }
        .input-group-addon { background-color: #fff; color: #ccc; border-right: none; }
        .form-control { border-left: none; background-color: #e8f0fe; }
        .form-control:focus { box-shadow: none; border-color: #ccc; }
        .btn-login {
            background-color: #0099e6;
            color: white;
            font-weight: bold;
            width: 100%;
            border-radius: 0;
            padding: 10px;
            border: none;
            margin-top: 15px;
        }
        .btn-login:hover { background-color: #0088cc; color: white; }
        .remember-forgot { margin-top: 10px; font-size: 13px; color: #666; }
        .register-link { text-align: center; margin-top: 25px; color: #888; font-size: 13px; }
    </style>
</head>
<body>

<div class="login-card">
    <h2 class="login-title">Đăng Nhập Vào Hệ Thống</h2>

    <c:if test="${alert != null}">
        <div class="alert alert-danger" style="padding: 8px; font-size: 13px;">${alert}</div>
    </c:if>

    <form action="login" method="post">
        <!-- Username -->
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <input type="text" placeholder="Tài khoản" name="username" class="form-control" required>
            </div>
        </div>

        <!-- Password -->
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <input type="password" placeholder="Mật khẩu" name="password" class="form-control" required>
            </div>
        </div>

        <!-- Checkbox & Link -->
        <div class="row remember-forgot">
            <div class="col-xs-6">
                <label style="font-weight: normal; cursor: pointer;">
                    <input type="checkbox" name="remember" style="margin-top: 2px;"> Nhớ tôi
                </label>
            </div>
            <div class="col-xs-6 text-right">
                <a href="#" style="color: #666; text-decoration: underline;">Quên mật khẩu?</a>
            </div>
        </div>

        <!-- Nút Submit -->
        <button type="submit" class="btn btn-login">Đăng nhập</button>
    </form>
</div>

<div class="register-link">
    Nếu bạn chưa có tài khoản trên hệ thống, thì hãy <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
</div>

</body>
</html>