<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>注册</title>
</head>
<body>
<div class="layui-container">
    <div class="layui-row"
         style="font-size: 30px; color: #555555; font-weight: bold; line-height: 60px; text-align: center;">注册商家账号
    </div>
    <div class="layui-form">
        <div class="layui-form-item" style="display: flex;align-items:center;align-self: center;justify-content:center">
            <label class="layui-form-label" for="phone">手机</label>
            <div class="layui-input-inline">
                <input type="tel" id="phone" name="phone" lay-verify="required|phone" lay-verType="tips"
                       placeholder="请输入手机号" autocomplete="off" class="layui-input">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px;">*</p>
        </div>
        <div class="layui-form-item" style="display: flex;align-items:center;align-self: center;justify-content:center">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <button type="button" class="layui-btn layui-btn-primary" style="width: 100px;"
                        onclick="sendCaptcha()" id="sendCaptchaBtn">发送验证码
            </button>
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px;"></p>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <label class="layui-form-label" for="phoneCaptcha">验证码</label>
            <div class="layui-input-inline">
                <input type="text" name="phoneCaptcha" id="phoneCaptcha" lay-verify="required" lay-verType="tips"
                       placeholder="请输入手机验证码" autocomplete="off" class="layui-input ">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px;">*</p>
        </div>
        <div class="layui-form-item"
             style="display: flex;align-items: center;justify-content:center;justify-content:center">
            <label class="layui-form-label" for="email">邮箱</label>
            <div class="layui-input-inline">
                <input type="text" name="email" id="email" lay-verify="required|email" lay-verType="tips"
                       placeholder="请输入邮箱" autocomplete="off" class="layui-input">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px">*</p>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <label class="layui-form-label" for="name">用户名</label>
            <div class="layui-input-inline">
                <input type="text" name="name" id="name" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px"></p>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="shopId">餐厅id</label>
            <div class="layui-input-inline">
                <input type="text" name="shopId" id="shopId" lay-verify="required|shopId" lay-verType="tips"
                       placeholder="请输入餐厅id" autocomplete="off" class="layui-input">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px;">*</p>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <label class="layui-form-label" for="password">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="password" id="password" placeholder="请输入密码" lay-verify="required|password"
                       lay-verType="tips" autocomplete="off" class="layui-input">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px;">*</p>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <label class="layui-form-label" for="confirmPassword">确认密码</label>
            <div class="layui-input-inline">
                <input type="password" id="confirmPassword" placeholder="请确认密码" lay-verify="required|confirmPassword"
                       lay-verType="tips"
                       autocomplete="off" class="layui-input">
            </div>
            <p class="layui-input-inline" style="color: red;width: 5px;">*</p>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-orange" lay-submit lay-filter="register">注册账户</button>
            </div>
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-green" onclick="loginSkip()">登录账户</button>
            </div>
        </div>
    </div>
</div>

<!--layui script-->
<script>
    layui.use('form', function () {
        var form = layui.form;
        form.verify({
            confirmPassword: function (value) {
                if (value !== document.getElementById("password").value) {
                    return '确认密码与密码不一致';
                }
            },
            shopId: [/^[a-zA-Z][a-zA-Z0-9_-]{2,15}$/, '餐厅id为字母开头的3位到16位字符'],
            password: [/^[a-zA-Z0-9?.-_]{6,16}$/, '密码为6位到16位字符']
        });
        form.on('submit(register)', function (data) {
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/register",
                data: {
                    "shopId": data.field.shopId,
                    "password": data.field.password,
                    "name": data.field.name,
                    "email": data.field.email,
                    "phone": data.field.phone,
                    "phoneCaptcha": data.field.phoneCaptcha
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    console.log(response);
                    if (response.code === 0) {
                        layer.msg("注册成功", {icon: 6});
                        setTimeout('location.href="${basePath!}/merchants/login"', 2000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            });
        });
    });
</script>

<!--plain script-->
<script>
    var count = 60;
    var curCount;
    var interval;

    function sendCaptcha() {
        var phone = document.getElementById("phone").value;
        var regex = new RegExp(/^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/);
        if (!regex.test(phone)) {
            layer.msg('请输入正确的手机号', {icon: 5});
            return false;
        }

        $.ajax({
            type: "POST",
            url: "${basePath!}/shops/register/send_sms_captcha",
            data: {
                "phone": phone
            },
            complete: function (res) {
                var response = JSON.parse(res.responseText);
                if (response.code === 0) {
                    layer.msg("发送成功", {icon: 6});
                } else {
                    layer.msg(response.message, {icon: 5});
                }
            }
        });

        var sendCaptchaBtn = document.getElementById("sendCaptchaBtn");
        sendCaptchaBtn.setAttribute("class", "layui-btn layui-btn-primary layui-btn-disabled");
        sendCaptchaBtn.setAttribute("disabled", "true");
        curCount = count;
        sendCaptchaBtn.innerHTML = curCount + "s后发送";
        interval = window.setInterval("setSendCaptchaInterval()", 1000);
    }

    function setSendCaptchaInterval() {
        var sendCaptchaBtn = document.getElementById("sendCaptchaBtn");
        if (curCount === 0) {
            window.clearInterval(interval);
            sendCaptchaBtn.setAttribute("class", "layui-btn layui-btn-primary");
            sendCaptchaBtn.removeAttribute("disabled");
            sendCaptchaBtn.innerHTML = "发送验证码";
        } else {
            curCount--;
            sendCaptchaBtn.innerHTML = curCount + "s后发送";
        }
    }

    function loginSkip() {
        window.location.href = "${basePath!}/merchants/login";
    }
</script>

</body>
</html>