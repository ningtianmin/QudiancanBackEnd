<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>类目列表</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 0 10px 0 10px;">
    <table id="categories" lay-filter="category"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#categories',
            url: '${basePath}/shops/${currentShopId}/branches/${currentBranchId}/categories',
            page: false,
            cols: [[
                {type: 'numbers', title: '序号', width: 100, align: 'center'},
                {field: 'name', title: '分类名称', width: 150, align: 'center', edit: true},
                {field: 'position', title: '排序', width: 100, align: 'center', sort: true, edit: true}
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
    });
</script>

<!--laytpl-->

<!--plain script-->
<script>

</script>
</html>