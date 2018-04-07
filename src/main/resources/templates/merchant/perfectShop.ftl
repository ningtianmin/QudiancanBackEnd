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
                <input type="text" id="shopName" name="shopName" lay-verify="required | shopName" lay-verType="tips"
                       placeholder="请输入店铺名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex;align-items: center;justify-content:center;justify-content:center">
            <label class="layui-form-label" for="holderType">主体类型</label>
            <div class="layui-input-inline">
                <input type="text" name="holderType" id="holderType" lay-verify="required | holderType"
                       lay-verType="tips"
                       placeholder="请输入主体类型" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <label class="layui-form-label" for="holderName">主体名称</label>
            <div class="layui-input-inline">
                <input type="text" name="holderName" id="holderName" lay-verify="required | holderName"
                       lay-verType="tips"
                       placeholder="请输入主体名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="holderIdentity">个人身份证或企业统一社会信用代码</label>
            <div class=" layui-input-inline">
                <input type="text" name="holderIdentity" id="holderIdentity" lay-verify="required | holderIdentity"
                       lay-verType="tips"
                       placeholder="请输入个人身份证或企业统一社会信用代码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="shopPhone">店铺主体手机</label>
            <div class=" layui-input-inline">
                <input type="tel" name="shopPhone" id="shopPhone" lay-verify="required" lay-verType="tips"
                       placeholder="请输入店铺主体手机" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="shopIntroduction">店铺介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="shopIntroduction" id="shopIntroduction"
                       lay-verify="required | shopIntroduction"
                       lay-verType="tips"
                       placeholder="请输入店铺介绍" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchName">门店名称</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchName" id="branchName" lay-verify="required | branchName"
                       lay-verType="tips"
                       placeholder="请输入门店名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchNotice">门店公告</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchNotice" id="branchNotice"
                       lay-verify="required | branchNotice"
                       lay-verType="tips"
                       placeholder="请输入门店公告" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchPhone">门店联系手机</label>
            <div class=" layui-input-inline">
                <input type="tel" name="branchPhone" id="branchPhone" lay-verify="required"
                       lay-verType="tips"
                       placeholder="请输入门店联系手机" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchAddress">门店地址</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchAddress" id="branchAddress"
                       lay-verify="required | branchAddress"
                       lay-verType="tips"
                       placeholder="请输入门店地址" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchLongitude">门店地址经度</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchLongitude" id="branchLongitude"
                       lay-verify="required | branchLongitude"
                       lay-verType="tips"
                       placeholder="请输入门店地址经度" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchLatitude">门店地址纬度</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchLatitude" id="branchLatitude"
                       lay-verify="required | branchLatitude"
                       lay-verType="tips"
                       placeholder="请输入门店地址纬度" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="branchIntroduction">门店介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchIntroduction" id="branchIntroduction"
                       lay-verify="required | branchIntroduction"
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
</html>
