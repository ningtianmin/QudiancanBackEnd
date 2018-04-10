<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.override.css">
    <style>
        .laytable-cell-1-image {
            height: 100%;
            line-height: 100%;
        }

        .laytable-cell-5-image {
            height: 100%;
            line-height: 100%;
        }
    </style>
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>产品列表</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <button class="layui-btn layui-btn-radius" onclick="parent.changeMainBody('/merchants/createProduct')">新增产品</button>
    <table id="products" lay-filter="product"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use(['table', 'laytpl'], function () {
        var table = layui.table;

        table.render({
            elem: '#products',
            url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/products',
            cols: [[
                {type: 'numbers', title: '序号', width: 50, align: 'center'},
                {field: 'categoryId', title: '类目id', width: 100, align: 'center'},
                {field: 'departmentId', title: '出品部门id', width: 120, align: 'center'},
                {field: 'name', title: '名称', width: 120, align: 'center'},
                {
                    field: 'image',
                    title: '图片',
                    width: 130,
                    align: 'center',
                    templet: '#imageTemplet',
                    style: 'height: 120px;'
                },
                {field: 'unitName', title: '单位', width: 70, align: 'center'},
                {field: 'price', title: '价格', width: 70, align: 'center', sort: true},
                {field: 'description', title: '描述', width: 150, align: 'center'},
                {field: 'position', title: '类目内排序', width: 120, align: 'center'},
                {field: 'status', title: '状态', width: 120, align: 'center'},
                {title: '操作', width: 150, align: 'center', toolbar: '#operationBar'}
            ]],
            page: true,
            request: {
                pageName: 'page',
                limitName: 'size'
            },
            response: {
                statusName: 'code',
                statusCode: 0,
                msgName: 'message',
                countName: 'count',
                dataName: 'data'
            },
            limit: 10,
            limits: [10, 20, 30, 100],
            loading: true,
            text: '获取产品列表失败'
        });

        table.on('tool(product)', function (obj) {
            var data = obj.data;
            var event = obj.event;

            if (event === 'upProduct') {
                $.ajax({
                    type: 'POST',
                    url: '${basePath!}/shops/products/' + data.id + '/up',
                    complete: function (res) {
                        var response = JSON.parse(res.responseText);
                        if (response.code === 0) {
                            layer.msg('上架成功');
                            table.reload('products');
                        } else {
                            layer.msg(response.message);
                        }
                    }
                });
            } else if (event === 'downProduct') {
                $.ajax({
                    type: 'POST',
                    url: '${basePath!}/shops/products/' + data.id + '/down',
                    complete: function (res) {
                        var response = JSON.parse(res.responseText);
                        if (response.code === 0) {
                            layer.msg('下架成功');
                            table.reload('products');
                        } else {
                            layer.msg(response.message);
                        }
                    }
                });
            }
        })
    });
</script>

<!--laytpl-->
<script type="text/html" id="operationBar">
    {{# if(d.status==='NORMAL'){ }}
    <a class="layui-btn layui-btn-md layui-bg-red" lay-event="downProduct">下架</a>
    {{# } }}
    {{# if(d.status==='DOWN'){ }}
    <a class="layui-btn layui-btn-md" lay-event="upProduct">上架</a>
    {{# } }}
</script>
<script type="text/html" id="imageTemplet">
    <img style="width: 100px; height: 100px;" src="{{d.image}}">
</script>

<!--plain script-->
<script>

</script>

</html>