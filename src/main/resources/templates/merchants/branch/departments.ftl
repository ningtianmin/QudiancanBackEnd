<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.override.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>出品部门列表</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-form">
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: left">
            <label class="layui-form-label" for="name">名称</label>
            <div class="layui-input-inline">
                <input type="text" placeholder="请输入出品部门名称" lay-verType="tips"
                       lay-verify="required|name"
                       name="name" id="name" class="layui-input">
            </div>
            <button class="layui-btn layui-btn-radius layui-btn-warm" lay-submit lay-filter="newDepartment"
                    style="margin-left: 35px;">新增出品部门
            </button>
        </div>
        <div class="layui-form-item" style="display: flex;align-items: center; justify-content: left">
        <#--<label class="layui-form-label" for="description">描述</label>-->
            <div class="layui-input-block">
                <textarea placeholder="请输入出品部门描述" lay-verType="tips" style="width: 190px;"
                          lay-verify="required|description"
                          name="description"
                          id="description" class="layui-textarea"></textarea>
            </div>
        </div>

    </div>
    <table id="departments" lay-filter="department" class="layui-table"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use(['table', 'form', 'layer'], function () {
        var table = layui.table;
        var form = layui.form;

        table.render({
            elem: '#departments',
            url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/departments',
            page: false,
            cols: [[
                {type: 'numbers', title: '序号', width: 100, align: 'center'},
                {field: 'name', title: '出品部门名称', width: 200, align: 'center'},
                {field: 'description', title: '出品部门描述', width: 300, align: 'center'},
                {
                    title: '操作',
                    width: 120,
                    align: 'center',
                    templet: '#updateTool',
                    event: 'update'
                }
            ]],
            response: {
                statusName: 'code',
                statusCode: 0,
                msgName: 'message',
                dataName: 'data'
            },
            text: '获取出品部门失败'
        });

        table.on('tool(department)', function (obj) {
            var data = obj.data;
            var event = obj.event;
            if (event === 'update') {
                layer.prompt({
                    formType: 0,
                    value: data.name,
                    title: '出品部门:' + data.name + ',编辑出品部门名称'
                }, function (updateName, index1, elem1) {
                    if (updateName.length >= 2 && updateName.length <= 15) {
                        layer.close(index1);
                        layer.prompt({
                            formType: 2,
                            value: data.description,
                            title: '出品部门:' + data.name + ',编辑出品部门描述'
                        }, function (updateDescription, index2, elem2) {
                            if (updateDescription.length >= 2 && updateDescription.length <= 50) {
                                layer.close(index2);
                                $.ajax({
                                    method: 'POST',
                                    url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/departments/' + data.id,
                                    data: {
                                        name: updateName,
                                        description: updateDescription
                                    },
                                    complete: function (res) {
                                        console.log(res);
                                        var response = JSON.parse(res.responseText);
                                        if (response.code === 0) {
                                            layer.msg("修改成功", {icon: 6});
                                            table.reload('departments');
                                        } else {
                                            layer.msg(response.message, {icon: 5});
                                        }
                                    }
                                })
                            } else {
                                layer.msg('出品部门描述两个字符到五十个字符', {time: 800});
                                return false;
                            }
                        })
                    }
                    else {
                        layer.msg('出品部门名称两个字符到十五个字符', {time: 800});
                        return false;
                    }
                })
            }
        });

        form.verify({
            name: function (value) {
                if (!(value.length >= 2 && value.length <= 15)) {
                    return '两个字符到十五个字符';
                }
            },
            description: function (value) {
                if (!(value.length >= 2 && value.length <= 50)) {
                    return '两个字符到五十个字符';
                }
            }
        });

        form.on('submit(newDepartment)', function (data) {
            $.ajax({
                type: "POST",
                url: "${basePath!}/shops/${currentShopId}/branches/${currentBranchId}/departments",
                data: {
                    "name": data.field.name,
                    "description": data.field.description
                },
                complete: function (res) {
                    console.log(res);
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("新增成功", {icon: 6});
                        table.reload('departments');
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
    <button class="layui-btn">更新</button>
</script>

<!--plain script-->
<script>

</script>

</html>