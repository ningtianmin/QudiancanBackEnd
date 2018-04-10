<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.override.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>类目列表</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-form">
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: left">
            <label class="layui-form-label" for="name">分类名称</label>
            <div class="layui-input-inline">
                <input type="text" placeholder="请输入类目名称" lay-verType="tips"
                       lay-verify="required|name"
                       name="name" id="name" class="layui-input">
            </div>
            <label class="layui-form-label" for="position">分类排序</label>
            <div class="layui-input-inline">
                <input type="number" placeholder="请输入类目排序" lay-verType="tips"
                       lay-verify="required|position"
                       name="position"
                       id="position" class="layui-input">
            </div>
            <button class="layui-btn layui-btn-radius layui-btn-warm" lay-submit lay-filter="newCategory">新增分类</button>
        </div>
    </div>
    <table id="categories" lay-filter="category"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use(['table', 'form', 'layer'], function () {
        var table = layui.table;
        var form = layui.form;

        table.render({
            elem: '#categories',
            url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/categories',
            page: false,
            cols: [[
                {type: 'numbers', title: '序号', width: 100, align: 'center'},
                {field: 'name', title: '分类名称', width: 150, align: 'center'},
                {field: 'position', title: '排序', width: 100, align: 'center', sort: true},
                {
                    title: '操作',
                    width: 120,
                    align: 'center',
                    templet: '#updateTool'
                }
            ]],
            response: {
                statusName: 'code',
                statusCode: 0,
                msgName: 'message',
                dataName: 'data'
            },
            text: '获取商品分类失败',
            initSort: {
                field: 'position',
                type: 'desc'
            }
        });

        table.on('tool(category)', function (obj) {
            var data = obj.data;
            var event = obj.event;
            if (event === 'update') {
                layer.prompt({
                    formType: 0,
                    value: data.name,
                    title: '类目:' + data.name + ',编辑类目名称'
                }, function (updateName, index1, elem1) {
                    if (updateName.length >= 2 && updateName.length <= 15) {
                        layer.close(index1);
                        layer.prompt({
                            formType: 0,
                            value: data.position,
                            title: '类目:' + data.name + ',编辑类目排序'
                        }, function (updatePosition, index2, elem2) {
                            var reg = new RegExp("^[1-9][0-9]{0,3}$");
                            if (reg.test(updatePosition)) {
                                layer.close(index2);
                                $.ajax({
                                    method: 'POST',
                                    url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/categories/' + data.id,
                                    data: {
                                        name: updateName,
                                        position: updatePosition
                                    },
                                    complete: function (res) {
                                        console.log(res);
                                        var response = JSON.parse(res.responseText);
                                        if (response.code === 0) {
                                            layer.msg("修改成功", {icon: 6});
                                            table.reload('categories');
                                        } else {
                                            layer.msg(response.message, {icon: 5});
                                        }
                                    }
                                });
                            } else {
                                layer.msg('类目排序为1至4位的整数', {time: 800});
                                return false;
                            }
                        })
                    } else {
                        layer.msg('类目名称两个字符到十五个字符', {time: 800});
                        return false;
                    }
                })
            }
        });

        form.verify({
            name: function (value) {
                if (!(value.length >= 2 && value.length <= 15)) {
                    return '类目名称两个字符到十五个字符';
                }
            },
            position: [/^[1-9][0-9]{0,3}$/, '类目排序为1至4位的整数']
        });

        form.on('submit(newCategory)', function (data) {
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/${currentShopId}/branches/${currentBranchId}/categories",
                data: {
                    "name": data.field.name,
                    "position": data.field.position
                },
                complete: function (res) {
                    console.log(res);
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("新增成功", {icon: 6});
                        table.reload('categories');
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            });
        });
    });
</script>

<!--laytpl-->
<script type="text/html" id="updateTool">
    <button class="layui-btn" lay-event="update">更新</button>
</script>

<!--plain script-->
<script>

</script>

</html>