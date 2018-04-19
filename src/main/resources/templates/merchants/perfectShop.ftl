<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>完善店铺</title>
</head>
<body>
<div class="layui-container">
    <div class="layui-row layui-bg-orange"><h1 style="text-align: center;">完善店铺</h1></div>
    <div class="layui-form">
        <div class="layui-form-item" style="display: flex;align-items:center;align-self: center;justify-content:center">
            <label class="layui-form-label" for="shopName">店铺名称</label>
            <div class="layui-input-inline">
                <input type="text" id="shopName" name="shopName" lay-verify="required|shopName" lay-verType="tips"
                       placeholder="请输入店铺名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center;">
            <label class="layui-form-label">主体类型</label>
            <div class="layui-input-inline">
                <select name="shopHolderType">
                    <#list constants.shopHolderType as temp>
                        <option value="${temp.key}">${temp.value}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <label class="layui-form-label" for="holderName">个人姓名/企业名称</label>
            <div class="layui-input-inline">
                <input type="text" name="holderName" id="holderName" lay-verify="required|holderName"
                       lay-verType="tips"
                       placeholder="请输入个人姓名/企业名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="holderIdentify">身份证/企业信用代码</label>
            <div class=" layui-input-inline">
                <input type="text" name="holderIdentify" id="holderIdentify" lay-verify="required|holderIdentify"
                       lay-verType="tips"
                       placeholder="请输入身份证/企业信用代码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="shopIntroduction">店铺介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="shopIntroduction" id="shopIntroduction"
                       lay-verify="required|shopIntroduction"
                       lay-verType="tips"
                       placeholder="请输入店铺介绍" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchName">门店名称</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchName" id="branchName" lay-verify="required|branchName"
                       lay-verType="tips"
                       placeholder="请输入门店名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchNotice">门店公告</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchNotice" id="branchNotice"
                       lay-verify="required|branchNotice"
                       lay-verType="tips"
                       placeholder="请输入门店公告" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchPhone">门店联系手机</label>
            <div class=" layui-input-inline">
                <input type="tel" name="branchPhone" id="branchPhone" lay-verify="required|phone"
                       lay-verType="tips"
                       placeholder="请输入门店联系手机" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchAddress">门店地址</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchAddress" id="branchAddress"
                       lay-verify="required|branchAddress"
                       lay-verType="tips"
                       placeholder="请输入门店地址" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchLongitude">门店地址经度</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchLongitude" id="branchLongitude"
                       lay-verify="required|branchLongitude"
                       lay-verType="tips"
                       placeholder="请输入门店地址经度" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchLatitude">门店地址纬度</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchLatitude" id="branchLatitude"
                       lay-verify="required|branchLatitude"
                       lay-verType="tips"
                       placeholder="请输入门店地址纬度" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchIntroduction">门店介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchIntroduction" id="branchIntroduction"
                       lay-verify="required|branchIntroduction"
                       lay-verType="tips"
                       placeholder="请输入门店介绍" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-orange" lay-submit lay-filter="perfect">完善店铺</button>
            </div>
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-green"><a href="${basePath!}/merchants/login">登录账户</a></button>
            </div>
        </div>
    </div>
</body>

<#--layui script-->
<script>
    layui.use('form', function () {
        var form = layui.form;
        form.verify({
            shopName: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
            holderName: [/^[a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
            holderIdentify: [/^(([1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx])|([1-9A-GY][1239][1-5][0-9]{5}[0-9A-Z]{10}))$/
                , '请输入正确的身份证/企业信用代码'],
            shopIntroduction: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位'],
            branchName: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
            branchNotice: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位'],
            branchAddress: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位'],
            branchLongitude: [/^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,10})?)|180(([.][0]{1,10})?))$/, '请输入正确的经度'],
            branchLatitude: [/^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,10})?)|90(([.][0]{1,10})?))$/, '请输入正确的纬度'],
            branchIntroduction: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位']
        });
        form.on('submit(perfect)', function (data) {
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/perfect",
                data: {
                    "shopName": data.field.shopName,
                    "shopHolderType": data.field.shopHolderType,
                    "holderName": data.field.holderName,
                    "holderIdentify": data.field.holderIdentify,
                    "shopIntroduction": data.field.shopIntroduction,
                    "branchName": data.field.branchName,
                    "branchNotice": data.field.branchNotice,
                    "branchPhone": data.field.branchPhone,
                    "branchAddress": data.field.branchAddress,
                    "branchLongitude": data.field.branchLongitude,
                    "branchLatitude": data.field.branchLatitude,
                    "branchIntroduction": data.field.branchIntroduction
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("完善成功", {icon: 6});
                        setTimeout('location.href="${basePath!}/merchants/index"', 2000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            })
        });
    });
</script>

</html>
