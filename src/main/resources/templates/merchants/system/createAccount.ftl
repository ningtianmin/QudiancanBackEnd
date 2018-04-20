<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>创建账号</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label" for="phone">手机</label>
            <div class="layui-input-inline">
                <input type="tel" id="phone" name="phone" lay-verify="required|phone" lay-verType="tips"
                       placeholder="请输入手机号" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <button type="button" class="layui-btn layui-btn-primary"
                        onclick="sendCaptcha()" id="sendCaptchaBtn" style="width: 100px;">发送验证码
                </button>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="phoneCaptcha">验证码</label>
            <div class="layui-input-inline">
                <input type="text" name="phoneCaptcha" id="phoneCaptcha" lay-verify="required" lay-verType="tips"
                       placeholder="请输入手机验证码" autocomplete="off" class="layui-input ">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="loginId">登录id</label>
            <div class="layui-input-inline">
                <input type="text" name="loginId" id="loginId" lay-verify="required|loginId" lay-verType="tips"
                       placeholder="请输入登录id" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
        >
            <label class="layui-form-label" for="email">邮箱</label>
            <div class="layui-input-inline">
                <input type="text" name="email" id="email" lay-verify="required|email" lay-verType="tips"
                       placeholder="请输入邮箱" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="name">用户名</label>
            <div class="layui-input-inline">
                <input type="text" name="name" id="name" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="password">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="password" id="password" placeholder="请输入密码" lay-verify="required|password"
                       lay-verType="tips" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="confirmPassword">确认密码</label>
            <div class="layui-input-inline">
                <input type="password" id="confirmPassword" placeholder="请确认密码" lay-verify="required|confirmPassword"
                       lay-verType="tips"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">所属门店</label>
            <div class="layui-input-block">
                <#list branches as branch>
                    <input type="checkbox" name="branchIds" value="${branch.id}" title="${branch.name}">
                </#list>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">所属角色</label>
            <div class="layui-input-block">
                 <#list roles as role>
                     <input type="checkbox" name="roleIds" value="${role.id}" title="${role.name}">
                 </#list>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <button class="layui-btn layui-bg-orange" lay-submit lay-filter="createAccount">创建账号</button>
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
            loginId: [/^[a-zA-Z][a-zA-Z0-9_-]{2,15}$/, '登录id为字母开头的3位到16位字符'],
            password: [/^[a-zA-Z0-9?.-_]{6,16}$/, '密码为6位到16位字符'],
            name: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位']
        });

        form.on('submit(createAccount)', function (data) {
            var branchIdCheckBoxes = document.getElementsByName("branchIds");
            var roleIdCheckBoxes = document.getElementsByName("roleIds");
            var branchIds = "";
            var roleIds = "";
            var temp;
            for (temp in branchIdCheckBoxes) {
                if (branchIdCheckBoxes[temp].checked) {
                    branchIds += branchIdCheckBoxes[temp].value + ",";
                }
            }
            for (temp in roleIdCheckBoxes) {
                if (roleIdCheckBoxes[temp].checked) {
                    roleIds += roleIdCheckBoxes[temp].value + ",";
                }
            }
            if (branchIds === "") {
                layer.msg('至少选择一个门店', {icon: 5});
                return;
            }
            if (roleIds === "") {
                layer.msg('至少选择一个角色', {icon: 5});
                return;
            }
            branchIds = branchIds.substring(0, branchIds.length - 1);
            roleIds = roleIds.substring(0, roleIds.length - 1);
            console.log(branchIds);
            console.log(roleIds);
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/accounts",
                data: {
                    "loginId": data.field.loginId,
                    "password": data.field.password,
                    "name": data.field.name,
                    "email": data.field.email,
                    "phone": data.field.phone,
                    "branchIds": branchIds,
                    "roleIds": roleIds,
                    "phoneCaptcha": data.field.phoneCaptcha
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    console.log(response);
                    if (response.code === 0) {
                        layer.msg("创建成功", {icon: 6});
                        setTimeout(function () {
                            parent.changeMainBody('/merchants/accountSetting');
                        }, 1500);
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
            url: "${basePath!}/shops/createAccount/send_sms_captcha",
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