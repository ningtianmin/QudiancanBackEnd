<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>门店设置</title>
</head>
<body>
<div class="layui-container" style="padding: 10px; width: 100%;">
    <div class="layui-form">
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchName">门店名称</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchName" id="branchName" lay-verify="required|branchName"
                       lay-verType="tips"
                       placeholder="请输入门店名称" autocomplete="off" class="layui-input" value="${branch.name!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchNotice">门店公告</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchNotice" id="branchNotice"
                       lay-verify="required|branchNotice"
                       lay-verType="tips"
                       placeholder="请输入门店公告" autocomplete="off" class="layui-input" value="${branch.notice!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchPhone">门店联系手机</label>
            <div class=" layui-input-inline">
                <input type="tel" name="branchPhone" id="branchPhone" lay-verify="required|phone"
                       lay-verType="tips"
                       placeholder="请输入门店联系手机" autocomplete="off" class="layui-input" value="${branch.phone!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchAddress">门店地址</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchAddress" id="branchAddress"
                       lay-verify="required|branchAddress"
                       lay-verType="tips"
                       placeholder="请输入门店地址" autocomplete="off" class="layui-input" value="${branch.address!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchLongitude">门店地址经度</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchLongitude" id="branchLongitude"
                       lay-verify="required|branchLongitude"
                       lay-verType="tips"
                       placeholder="请输入门店地址经度" autocomplete="off" class="layui-input" value="${branch.longitude!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchLatitude">门店地址纬度</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchLatitude" id="branchLatitude"
                       lay-verify="required|branchLatitude"
                       lay-verType="tips"
                       placeholder="请输入门店地址纬度" autocomplete="off" class="layui-input" value="${branch.latitude!}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label style="width: 120px; color: #1E9FFF;" class="layui-form-label" for="branchIntroduction">门店介绍</label>
            <div class=" layui-input-inline">
                <input type="text" name="branchIntroduction" id="branchIntroduction"
                       lay-verify="required|branchIntroduction"
                       lay-verType="tips"
                       placeholder="请输入门店介绍" autocomplete="off" class="layui-input" value="${branch.introduction!}">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: center">
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-orange" lay-submit lay-filter="updateBranch">更新门店</button>
            </div>
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-green"
                        onclick="parent.changeMainBody('/merchants/createBranch')">创建门店
                </button>
            </div>
        </div>
    </div>
</body>

<#--layui script-->
<script>
    layui.use('form', function () {
        var form = layui.form;

        form.verify({
            branchName: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,16}$/, '2位到16位'],
            branchNotice: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位'],
            branchAddress: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位'],
            branchLongitude: [/^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,10})?)|180(([.][0]{1,10})?))$/, '请输入正确的经度'],
            branchLatitude: [/^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,10})?)|90(([.][0]{1,10})?))$/, '请输入正确的纬度'],
            branchIntroduction: [/^[0-9a-zA-Z\u4E00-\u9FA5]{2,50}$/, '2位到50位']
        });

        form.on('submit(updateBranch)', function (data) {
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/${currentShopId}/branches/${currentBranchId}",
                data: {
                    "name": data.field.branchName,
                    "notice": data.field.branchNotice,
                    "phone": data.field.branchPhone,
                    "address": data.field.branchAddress,
                    "longitude": data.field.branchLongitude,
                    "latitude": data.field.branchLatitude,
                    "introduction": data.field.branchIntroduction
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("更新成功", {icon: 6});
                        setTimeout('parent.location.replace(parent.location.href)', 2000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            })
        });
    });
</script>

</html>
