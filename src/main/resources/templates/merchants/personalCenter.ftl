<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>个人中心</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label" for="shopId">餐厅id</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="shopId" id="shopId"
                       value="${(accountInfo.shopId)!}">
            </div>
            <label class="layui-form-label" for="loginId">登录id</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="loginId" id="loginId"
                       value="${(accountInfo.loginId)!}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="name">姓名</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="name" id="name"
                       value="${(accountInfo.name)!}">
            </div>
            <label class="layui-form-label" for="email">邮箱</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="email" id="email"
                       value="${(accountInfo.email)!}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" for="phone">手机号</label>
            <div class="layui-input-inline">
                <input disabled type="tel" class="layui-input " name="phone" id="phone"
                       value="${(accountInfo.phone)!}">
            </div>
            <label class="layui-form-label" for="createTime">创建时间</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="createTime" id="createTime"
                       value="${(accountInfo.createTime)!}">
            </div>
        </div>
        <#if accountInfo.isCreator=='是'>
            <div class="layui-form-item">
                <label class="layui-form-label" for="isCreator">是否创建者</label>
                <div class="layui-input-inline">
                    <input disabled type="text" class="layui-input" name="isCreator" id="isCreator"
                           value="${(accountInfo.isCreator)!}">
                </div>
            </div>
        <#else >
        <div class="layui-form-item">
            <label class="layui-form-label" for="branchIds">所属门店</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="branchIds" id="branchIds"
                       value="${(accountInfo.branchesString)!}">
            </div>
            <label class="layui-form-label" for="roleIds">所属角色</label>
            <div class="layui-input-inline">
                <input disabled type="text" class="layui-input" name="roleIds" id="roleIds"
                       value="${(accountInfo.rolesString)!}">
            </div>
        </div>
        </#if>
    </div>
</div>
</body>
</html>