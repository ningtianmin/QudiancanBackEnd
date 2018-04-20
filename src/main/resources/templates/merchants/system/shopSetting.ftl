<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>店铺设置</title>
</head>
<body>
<div class="layui-container" style="padding: 10px; width: 90%;">
    <div class="layui-form">
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px; color: #1E9FFF;" class="layui-form-label" for="name">店铺名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required|name" lay-verType="tips"
                       placeholder="请输入店铺名称" autocomplete="off" class="layui-input" value="${shop.name!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px;" class="layui-form-label" for="holderType">主体类型</label>
            <div class="layui-input-inline">
                <select name="holderType">
                    <#list constants.shopHolderType as temp>
                        <option value="${temp.key}" <#if temp.key == shop.holderType>selected</#if>
                                disabled>${temp.value}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px;" class="layui-form-label" for="holderName">个人姓名/企业名称</label>
            <div class="layui-input-inline">
                <input type="text" name="holderName" id="holderName" lay-verify="required|holderName"
                       lay-verType="tips"
                       placeholder="请输入个人姓名/企业名称" autocomplete="off" class="layui-input" value="${shop.holderName!}"
                       disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px;" class="layui-form-label" for="holderIdentify">身份证/企业信用代码</label>
            <div class=" layui-input-inline">
                <input type="text" name="holderIdentify" id="holderIdentify" lay-verify="required|holderIdentify"
                       lay-verType="tips"
                       placeholder="请输入身份证/企业信用代码" autocomplete="off" class="layui-input"
                       value="${shop.holderIdentify!}" disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px;" class="layui-form-label" for="telephone">手机号</label>
            <div class="layui-input-inline">
                <input type="tel" class="layui-input " name="telephone" id="telephone" lay-verify="required|phone"
                       lay-verType="tips"
                       value="${shop.telephone!}" disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px; color: #1E9FFF;" class="layui-form-label" for="introduction">店铺介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="introduction" id="introduction"
                       lay-verify="required|introduction"
                       lay-verType="tips"
                       placeholder="请输入店铺介绍" autocomplete="off" class="layui-input" value="${shop.introduction!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label style="width: 160px;" class="layui-form-label" for="createTime">创建时间</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="createTime" id="createTime"
                       value="${shop.createTime!}" disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <div class="layui-form-label">
                <button type="submit" class="layui-btn layui-bg-orange" lay-submit lay-filter="updateShop">修改店铺</button>
            </div>
        </div>
    </div>
</body>

<#--layui script-->
<script>
    layui.use('form', function () {
        var form = layui.form;

        form.verify({
            name: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
            introduction: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位']
        });

        form.on('submit(updateShop)', function (data) {
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/${currentShopId}/nameIntroductionUpdate',
                data: {
                    "name": data.field.name,
                    "introduction": data.field.introduction
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg('更新成功');
                        setTimeout('location.href="${basePath!}/merchants/shopSetting"', 1500);
                    } else {
                        layer.msg(response.message);
                    }
                }
            });
        });
    });
</script>

</html>
