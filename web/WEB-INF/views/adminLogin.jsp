<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>童心堂后台登陆</title>
    <meta name="description" content="description">
    <meta name="author" content="Evgeniya">
    <meta name="keyword" content="keywords">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/plugins/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.css" rel="stylesheet">
    <link href="/css/style_v2.css" rel="stylesheet">
    <script src="/plugins/jquery/jquery.min.js"></script>
    <script src="/js/md5.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
            <script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>
            <script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container-fluid">
    <div id="page-login" class="row">
        <div class="col-xs-12 col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
            <div class="box">
                <div class="box-content">
                    <div class="text-center">
                        <h3 class="page-header">童心堂后台登陆</h3>
                    </div>
                    <div class="form-group">
                        <label class="control-label">用户名</label>
                        <input type="text" id="username" class="form-control" name="username"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">密码</label>
                        <input type="password" id="password" class="form-control" name="password"/>
                    </div>
                    <div class="text-center">
                        <input type="button" onclick="login();" value="登陆"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function login() {
        var username = $("#username").val();
        var password = $("#password").val();
        if (username.replace(/\s/g, '') == '') {
            alert("用户名不能为空");
            return;
        }
        if (password.replace(/\s/g, '') == '') {
            alert("密码不能为空");
            return;
        }
        var passwordCode = hex_md5(password);
        $.ajax({
            cache: true,
            type: "POST",
            url: "/adminLogin.do",
            data: {"username": username, "password": passwordCode},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
//                alert(data);
                if (data.success) {
                    window.location.href = "/main.do";
                } else {
                    var _case = {
                        1: "用户名不能为空",
                        2: "密码不能为空",
                        3: "该用户不存在",
                        4: "密码不正确",
                        5: "登陆失败",
                        6: "您的账号没有权限，请联系良讯网管理员"
                    };
                    alert(_case[data.code])
                }
            }
        });
    }
</script>
</body>
</html>
