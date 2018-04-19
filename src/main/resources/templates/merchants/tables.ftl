<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.override.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>桌台列表</title>
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
                    <option value="${item.key}">${item.value}</option>
                </#list>
                </select>
            </div>
            <label class="layui-form-label" for="name">桌台名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required|name" lay-verType="tips"
                       placeholder="请输入桌台名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: left">
            <label class="layui-form-label" for="capacity">桌台容量</label>
            <div class="layui-input-inline">
                <input type="number" id="capacity" name="capacity" lay-verify="required|capacity" lay-verType="tips"
                       placeholder="请输入桌台容量" autocomplete="off" class="layui-input">
            </div>
            <label class="layui-form-label" for="position">类目内排序</label>
            <div class="layui-input-inline">
                <input type="number" id="position" name="position" lay-verify="required|position" lay-verType="tips"
                       placeholder="请输入桌台类目内排序" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: left">
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-orange layui-btn-radius" lay-submit lay-filter="createTable"
                        style="margin-left: 20px;">新增桌台
                </button>
            </div>
        </div>
    </div>
    <table id="tables" lay-filter="table" class="layui-table"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use(['table', 'laytpl', 'form', 'layer'], function () {
        var table = layui.table;
        var form = layui.form;

        table.render({
            elem: '#tables',
            url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/tables',
            cols: [[
                {type: 'numbers', title: '序号', width: 50, align: 'center'},
                {field: 'categoryId', title: '桌台类目id', width: 100, align: 'center'},
                {field: 'name', title: '名称', width: 120, align: 'center'},
                {field: 'orderId', title: '该桌台当前订单', width: 130, align: 'center'},
                {field: 'capacity', title: '可供就餐人数', width: 120, align: 'center'},
                {field: 'position', title: '桌台类目内排序', width: 150, align: 'center'},
                {field: 'status', title: '状态', width: 120, align: 'center'},
                {title: '操作', width: 250, align: 'center', toolbar: '#operationBar'}

            ]],
            page: false,
            response: {
                statusName: 'code',
                statusCode: 0,
                msgName: 'message',
                dataName: 'data'
            },
            loading: true,
            text: '获取桌台列表失败'
        });

        table.on('tool(table)', function (obj) {
            var data = obj.data;
            var event = obj.event;
            if (event === 'update') {
                alert('update待做');
            } else if (event === 'showQrcode') {
                layer.open({
                    type: 1,
                    title: data.name + '二维码',
                    closeBtn: 0,
                    area: ['520px', '520px'],
                    skin: 'layui-bg-white',
                    shadeClose: true,
                    content: '<img style="display: block; margin: auto;" src="' + data.qrcode + '">'
                });
            }
        });

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

        form.on('submit(createTable)', function (data) {
            if (data.field.categoryId === "") {
                layer.msg('请选择桌台类目或前去创建');
            }
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/${currentShopId}/branches/${currentBranchId}/tables',
                data: {
                    "categoryId": data.field.categoryId,
                    "name": data.field.name,
                    "capacity": data.field.capacity,
                    "position": data.field.position
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("新增成功", {icon: 6});
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                    table.reload('tables');
                }
            });
        });
    });
</script>

<!--laytpl-->
<script type="text/html" id="operationBar">
    <button class="layui-btn layui-bg-green" lay-event="update">更新</button>
    <button class="layui-btn layui-bg-green" lay-event="showQrcode">查看二维码</button>
</script>

<!--plain script-->
<script>

</script>

</html>