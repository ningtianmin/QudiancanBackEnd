<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>完善店铺</title>
</head>
<body>
<div class="layui-container" style="padding: 10px; width: 90%;">
    <div class="layui-form">
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="name">店铺名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required|name" lay-verType="tips"
                       placeholder="请输入店铺名称" autocomplete="off" class="layui-input" value="${shop.name!}" disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="holderType">主体类型</label>
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
            <label class="layui-form-label" for="holderName">个人姓名/企业名称</label>
            <div class="layui-input-inline">
                <input type="text" name="holderName" id="holderName" lay-verify="required|holderName"
                       lay-verType="tips"
                       placeholder="请输入个人姓名/企业名称" autocomplete="off" class="layui-input" value="${shop.holderName!}"
                       disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="holderIdentify">身份证/企业信用代码</label>
            <div class=" layui-input-inline">
                <input type="text" name="holderIdentify" id="holderIdentify" lay-verify="required|holderIdentify"
                       lay-verType="tips"
                       placeholder="请输入身份证/企业信用代码" autocomplete="off" class="layui-input"
                       value="${shop.holderIdentify!}" disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="telephone">手机号</label>
            <div class="layui-input-inline">
                <input type="tel" class="layui-input " name="telephone" id="telephone" lay-verify="required|phone"
                       lay-verType="tips"
                       value="${shop.telephone!}" disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="introduction">店铺介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="introduction" id="introduction"
                       lay-verify="required|introduction"
                       lay-verType="tips"
                       placeholder="请输入店铺介绍" autocomplete="off" class="layui-input" value="${shop.introduction!}"
                       disabled>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="createTime">创建时间</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="createTime" id="createTime"
                       value="${shop.createTime!}" disabled>
            </div>
        </div>
    </div>
</body>

<#--layui script-->
<script>
    // layui.use('form', function () {
    //     var form = layui.form;
    //
    //     form.verify({
    //         name: [/^[a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
    //         holderName: [/^[a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
    //         holderIdentify: [/^(([1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx])|([1-9A-GY][1239][1-5][0-9]{5}[0-9A-Z]{10}))$/
    //             , '请输入正确的身份证/企业信用代码'],
    //         introduction: [/^[a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位']
    //     });
    // });
</script>

</html>
