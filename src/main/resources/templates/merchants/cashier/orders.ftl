<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>订单管理</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <table id="orders" lay-filter="order" class="layui-table"></table>
</div>
</body>

<!--layui script-->
<script>
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#orders',
            url: '${basePath!}/shops/branches/${currentBranchId}/orders',
            cols: [[
                {type: 'numbers', title: '序号', width: 50, align: 'center'},
                {field: 'orderNumber', title: '订单编号', width: 185, align: 'center'},
                {field: 'branchName', title: '门店名称', width: 185, align: 'center'},
                {field: 'tableName', title: '桌台名称', width: 100, align: 'center'},
                {field: 'totalSum', title: '订单金额', width: 90, align: 'center'},
                {field: 'discountSum', title: '折扣金额', width: 90, align: 'center'},
                {field: 'wipeSum', title: '抹零金额', width: 90, align: 'center'},
                {field: 'chargeSum', title: '实收金额', width: 90, align: 'center'},
                {field: 'note', title: '订单备注', width: 100, align: 'center'},
                {field: 'payMethod', title: '支付方式', width: 90, align: 'center'},
                {field: 'payStatus', title: '支付状态', width: 90, align: 'center'},
                {field: 'orderStatus', title: '订单状态', width: 90, align: 'center'},
                {field: 'customerNum', title: '客人数量', width: 90, align: 'center'},
                {field: 'createTime', title: '创建时间', width: 166, align: 'center'}
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
            text: '获取订单列表失败'
        });
    });
</script>
</html>