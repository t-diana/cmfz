<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<head>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="${app}/login/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${app}/login/assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${app}/login/assets/css/form-elements.css">
    <link rel="stylesheet" href="${app}/login/assets/css/style.css">
    <link rel="shortcut icon" href="${app}/login/assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="${app}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="${app}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="${app}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${app}/login/assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script src="${app}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${app}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${app}/login/assets/js/scripts.js"></script>
    <script src="${app}/js/jquery.validate.min.js"></script>
    <script src="${app}/js/messages_zh.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#loginForm").validate({
                    message: {
                        name: {required: "请输入用户名", minlength: "最少输入四位"},
                        password: {required: "请输入密码", minlength: "最少输入四位"},
                        verifycode: {required: "请输入验证码", minlength: "最少输入四位"}
                    },
                    errorPlacement: function (error) {
                        $("#message").html(error[0]);
                    }
                }
            );
            $("#loginButtonId").click(
                function () {
                    //表单验证的状态
                    var valid = $("#loginForm").valid();
                    if (valid) {
                        $.ajax({
                                url: "${pageContext.request.contextPath}/Admin/login",
                                type: "post",
                                data: $("#loginForm").serialize(),
                                success: function (result) {
                                    if (result.msg == null)
                                        location.href = "${app}/jsp/backManager.jsp"
                                    else {
                                        $("#message").text(result.msg);
                                    }
                                },
                                datatype: "json"
                            }
                        )
                    }
                }
            )
        })
    </script>
</head>
<body>
<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box" style="padding-left:70px ">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>欢迎用户登录</h3>
                            <p id="message"></p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" required minlength="4" name="name" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" required minlength="4" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div class="form-group">
                                <img id="captchaImage" onclick="this.src=this.src+'?'+new Date().getDate()"
                                     style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/verifyCode/verify">
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="verifycode" id="form-code" required minlength="4" placeholder="输入验证码..."
                                       maxlength="4">
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</body>

</html>