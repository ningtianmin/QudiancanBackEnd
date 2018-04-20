<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>更新桌台</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-form">
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: left">
            <label class="layui-form-label">桌台类目</label>
            <div class="layui-input-inline">
                <select name="categoryId" lay-verify="required">
                <#list tableCategories as item>
                    <option value="${item.key}"
                            <#if item.key == branchTable.categoryId+"">selected</#if>>${item.value}</option>
                </#list>
                </select>
            </div>
            <label class="layui-form-label" for="name">桌台名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required|name" lay-verType="tips"
                       placeholder="请输入桌台名称" value="${branchTable.name}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: left">
            <label class="layui-form-label" for="capacity">桌台容量</label>
            <div class="layui-input-inline">
                <input type="number" id="capacity" name="capacity" lay-verify="required|capacity" lay-verType="tips"
                       placeholder="请输入桌台容量" value="${branchTable.capacity}" autocomplete="off" class="layui-input">
            </div>
            <label class="layui-form-label" for="position">类目内排序</label>
            <div class="layui-input-inline">
                <input type="number" id="position" name="position" lay-verify="required|position" lay-verType="tips"
                       placeholder="请输入桌台类目内排序" value="${branchTable.position}" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: left">
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-orange layui-btn-radius" lay-submit lay-filter="updateTable"
                        style="margin-left: 20px;">更新桌台
                </button>
            </div>
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-green layui-btn-radius"
                        onclick="parent.changeMainBody('/merchants/tables')" style="margin-left: 20px;">取消更新
                </button>
            </div>
        </div>
    </div>
</div>
</body>

<!--layui script-->
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form;

        form.verify({
            name: function (value) {
                if (value === "" || value.length > 10) {
                    return '桌台名称不超过10个字符';
                }
            },
            capacity: function (value) {
                if (value.indexOf('-') >= 0 || value.indexOf('.') >= 0) {
                    return '请输入正整数';
                }
                if (value === "" || value < 1 || value > 12) {
                    return '桌台容量1-12';
                }
            },
            position: [/^[1-9][0-9]{0,3}$/, '桌台类目内排序为1至4位的整数']
        });

        form.on('submit(updateTable)', function (data) {
            if (data.field.categoryId === "") {
                layer.msg('请选择桌台类目或前去创建');
            }
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/${currentShopId}/branches/${currentBranchId}/tables/${branchTable.id}',
                data: {
                    "categoryId": data.field.categoryId,
                    "name": data.field.name,
                    "capacity": data.field.capacity,
                    "position": data.field.position
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("更新成功", {icon: 6});
                        setTimeout(function () {
                            parent.changeMainBody('/merchants/tables');
                        }, 1000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            });
        });
    });
</script>
</html>