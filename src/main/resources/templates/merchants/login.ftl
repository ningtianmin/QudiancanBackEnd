<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>登录</title>
</head>
<body>
<div class="layui-container">
    <div class="layui-row layui-bg-orange"><h1 style="text-align: center;">登录商家账号</h1></div>
    <div class="layui-form">
        <div class="layui-form-item" style="display: flex; justify-content: center">
            <label class="layui-form-label" for="shopId">店铺id</label>
            <div class="layui-input-inline">
                <input type="text" name="shopId" id="shopId" lay-verify="required|shopId" lay-verType="tips"
                       placeholder="请输入店铺id" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; justify-content: center">
            <label class="layui-form-label" for="loginId">登录id</label>
            <div class="layui-input-inline">
                <input type="text" name="loginId" id="loginId" lay-verify="required|loginId" lay-verType="tips"
                       placeholder="请输入登录id"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; justify-content: center">
            <label class="layui-form-label" for="password">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="password" id="password" placeholder="请输入密码" lay-verify="required|password"
                       lay-verType="tips" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; justify-content: center">
            <div class="layui-form-label">
                <button type="submit" class="layui-btn layui-bg-orange" lay-submit lay-filter="login">登录账户</button>
            </div>
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-green" onclick="registerSkip()">注册账户</button>
            </div>
        </div>
    </div>
</div>
</body>

<!--layui script-->
<script>
    layui.use('form', function () {
        var form = layui.form;
        form.verify({
            loginId: [/^[a-zA-Z][a-zA-Z0-9_-]{2,15}$/, '登录id为字母开头的3位到16位字符'],
            shopId: [/^[a-zA-Z][a-zA-Z0-9_-]{2,15}$/, '店铺id为字母开头的3位到16位字符'],
            password: [/^[a-zA-Z0-9?.-_]{6,16}$/, '密码为6位到16位字符']
        });
        form.on('submit(login)', function (data) {
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/login",
                data: {
                    "shopId": data.field.shopId,
                    "loginId": data.field.loginId,
                    "password": data.field.password
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("登录成功", {icon: 6});
                        setTimeout('location.href="${basePath!}/merchants/index"', 2000);
                    } else if (response.code === 1000) {
                        layer.msg(response.message, {icon: 5});
                        setTimeout('location.href="${basePath!}/merchants/perfectShop"', 2000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            })
        });
    });
</script>

<!--plain script-->
<script>
    function registerSkip() {
        window.location.href = "${basePath!}/merchants/register";
    }
</script>

</html>