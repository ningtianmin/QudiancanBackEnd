<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>修改密码</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 30px 40px 0;">
    <div class="layui-form">
        <div style="font-size: 15px; color: #555555; font-weight: bold; line-height: 30px;">原密码</div>
        <div class="layui-input-inline" style="width: 100%; height: 40px;">
            <input type="password" name="oldPassword" id="oldPassword" placeholder="输入原密码"
                   lay-verify="required|password"
                   lay-verType="tips" autocomplete="off" class="layui-input">
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
            <button class="layui-btn" lay-submit lay-filter="updatePassword"
                    style="width: 122px; height: 40px; background: #FF6700;">确定
            </button>
            <button class="layui-btn" style="width: 122px; height: 40px; background: #FFFFFF; color: #000000;"
                    onclick="closeUpdatePassword()">取消
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

        form.on('submit(updatePassword)', function (data) {
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/updatePassword',
                data: {
                    "oldPassword": data.field.oldPassword,
                    "newPassword": data.field.newPassword
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg('修改成功');
                        setTimeout(function () {
                            var pathname = window.location.pathname;
                            var contextPath = pathname.substring(0, pathname.substring(1).indexOf("/") + 1);
                            parent.location.replace(contextPath + "/merchants/login");
                        }, 2000);
                    } else {
                        layer.msg(response.message);
                    }
                }
            });
        });
    });

    function closeUpdatePassword() {
        parent.layer.closeAll();
    }
</script>

<#--plain script-->
<script>

</script>
</body>
</html>