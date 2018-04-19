<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>账户列表</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <h2 class="layui-show">当前门店的账户</h2>
    <table class="layui-table">
        <thead class="layui-table-header">
        <tr>
            <td>店铺id</td>
            <td>登录id</td>
            <td>名称</td>
            <td>邮箱</td>
            <td>手机号</td>
            <td>所属门店</td>
            <td>所属角色</td>
            <td>是否店铺创建者</td>
            <td>创建时间</td>
        </tr>
        </thead>
        <tbody class="layui-table-body">
        <#list accounts as account>
        <tr>
            <td>${account.shopId}</td>
            <td>${account.loginId}</td>
            <td>${account.name}</td>
            <td>${account.email}</td>
            <td>${account.phone}</td>
            <td>${account.branchesString}</td>
            <td>${account.rolesString}</td>
            <td>${account.isCreator}</td>
            <td>${account.createTime}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<script></script>
</body>
</html>