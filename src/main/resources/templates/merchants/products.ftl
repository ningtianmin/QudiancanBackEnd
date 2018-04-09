<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>产品列表</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 0 10px 0 10px;">
    <table id="products" lay-filter="product"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#products',
            url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/products',
            cols: [[
                {type: 'numbers', title: '序号', width: 50, align: 'center'},
                {field: 'categoryId', title: '类目id', width: 100, align: 'center'},
                {field: 'departmentId', title: '出品部门id', width: 120, align: 'center'},
                {field: 'name', title: '名称', width: 120, align: 'center', edit: true},
                {field: 'image', title: '图片', width: 300, align: 'center'},
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
        })
        ;
    });
</script>

<script type="text/html" id="operationBar">
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<!--laytpl-->

<!--plain script-->
<script>

</script>
</html>