<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>重置密码</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 30px 40px 0;">
    <div class="layui-form">
        <div style="font-size: 15px; color: #555555; font-weight: bold; line-height: 30px;">手机验证</div>
        <div class="layui-input-inline" style="width: 100%; height: 40px; margin-bottom: 5px;">
            <input type="tel" id="phone" name="phone" lay-verify="required|phone" lay-verType="tips"
                   placeholder="请输入手机号" autocomplete="off" class="layui-input">
        </div>
        <button type="button" class="layui-btn layui-btn-primary" style="100px; height: 40px; margin-bottom: 5px;"
                onclick="sendCaptcha()" id="sendCaptchaBtn">发送验证码
        </button>
        <div class="layui-input-inline" style="width: 100%; height: 40px; margin-bottom: 5px;">
            <input type="text" name="phoneCaptcha" id="phoneCaptcha" lay-verify="required" lay-verType="tips"
                   placeholder="请输入手机验证码" autocomplete="off" class="layui-input ">
        </div>
        <div style="font-size: 15px; color: #555555; font-weight: bold; line-height: 30px;">新密码</div>
        <div class="layui-input-inline" style="width: 100%; height: 40px; margin-bottom: 5px;">
            <input type="password" name="newPassword" id="newPassword" placeholder="输入新密码"
                   lay-verify="required|password"
                   lay-verType="tips" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-input-inline" style="width: 100%; height: 40px; margin-bottom: 5px;">
            <input type="password" name="confirmPassword" id="confirmPassword" placeholder="重复新密码"
                   lay-verify="required|confirmPassword"
                   lay-verType="tips" autocomplete="off" class="layui-input">
        </div>
        <div style="margin-bottom: 35px;">密码长度6至16位</div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <button class="layui-btn" lay-submit lay-filter="resetPassword"
                    style="width: 122px; height: 40px; background: #FF6700;">确定
            </button>
            <button class="layui-btn" style="width: 122px; height: 40px; background: #FFFFFF; color: #000000;"
                    onclick="closeResetPassword()">取消
            </button>
        </div>
    </div>
</div>

<!--layui script-->
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form;

        form.verify({
            confirmPassword: function (value) {
                if (value !== document.getElementById("newPassword").value) {
                    return '确认密码与密码不一致';
                }
            },
            password: [/^[a-zA-Z0-9?.-_]{6,16}$/, '密码为6位到16位字符']
        });

        form.on('submit(resetPassword)', function (data) {
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/resetPassword',
                data: {
                    "phone": data.field.phone,
                    "phoneCaptcha": data.field.phoneCaptcha,
                    "newPassword": data.field.newPassword
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg('重置成功');
                    } else {
                        layer.msg(response.message);
                    }
                    setTimeout(function () {
                        parent.layer.closeAll();
                    }, 1500);
                }
            });
        });
    });

    function closeResetPassword() {
        parent.layer.closeAll();
    }

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
            url: "${basePath!}/shops/resetPassword/send_sms_captcha",
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
</script>

</body>
</html>