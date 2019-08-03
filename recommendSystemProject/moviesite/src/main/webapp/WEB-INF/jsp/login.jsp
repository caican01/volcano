<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>发现好电影用户登录界面</title>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <!-- CSS -->
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="/assets/css/regandlogcommon.css">
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <style type="text/css">
        .login {
            box-shadow: 0 0 15px 3px rgba(51, 51, 51, 0.53);
            width: 430px;
            height: 380px;
            margin-top: 90px;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: auto;
            border-radius: 5px;
        }
        body{
            background:#CCCCCC url(/assets/img/backpost.jpg) no-repeat center center;
            background-size:cover;
            background-attachment:fixed;
            text-align: center;
        }
    </style>

</head>
<body>
<div class="login">
    <div style="padding-top: 30px;">
        <h1>
            <span style="color: white;">用户登录</span>
        </h1>
    </div>
    <div>
        <form id="logForm_mod">
            <input type="text" style="width: 300px;border: none;" name="email" id="loginEmail" placeholder="邮箱账号" required="required">
            <input type="password" style="width: 300px;border: none;" name="userPassword" id="loginPassword" placeholder="密　码" required="required">
            <button  style="width: 150px;border: none;" type="button" id="login" onclick="LOGIN.login()">登录</button>
            <button  style="width: 150px;border: none;" type="button" id="register">注册</button>
        </form>
    </div>
</div>

<!-- 登录按钮事件-->
<script type="text/javascript">
    var LOGIN = {
        checkInput: function () {

            if (!$("#loginEmail").val()) {
                alert("请输入账号");
                return false;
            }
            if ($("#loginEmail").val() && !$("#loginPassword").val()) {
                alert("请输入密码");
                return false;
            }
            return true;
        },
        doLogin: function () {
            $.post("/customer/login", {
                "email":$("#loginEmail").val(),
                "userPassword":$("#loginPassword").val(),
                "movieId":0
            }, function (data) {
                if (data.status == 200) {
                    alert("登录成功！");
                    location.href = "/"
                } else {
                    alert("登录失败，原因是：" + data.msg);
                    $("#loginEmail").val(null);
                    $("#loginPassword").val(null);
                }
            });
        },
        login: function () {
            if (this.checkInput()) {
                this.doLogin();
            }
        }
    };
</script>

<!-- 注册按钮事件-->
<script type="text/javascript">
    $('#register').click(function () {
        location.href="/page/register"
    })
</script>

</body>
</html>