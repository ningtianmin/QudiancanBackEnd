<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>订单统计（时）</title>
</head>
<body>
<div class="layui-container" style="width: 100%;">
    <table class="layui-table" lay-even>
        <thead class="layui-table-header">
        <tr style="font-weight: bold;">
            <td>时段</td>
            <td>订单金额</td>
            <td>折扣金额</td>
            <td>抹零金额</td>
            <td>实收金额</td>
            <td>订单数</td>
            <td>客人数</td>
            <td>单均</td>
            <td>人均</td>
        </tr>
        </thead>
        <tbody class="layui-table-body">
            <#list orderStatisticsList as temp>
            <tr>
                <td>${temp.period}</td>
                <td>${temp.totalSum}</td>
                <td>${temp.discountSum}</td>
                <td>${temp.wipeSum}</td>
                <td>${temp.chargeSum}</td>
                <td>${temp.orderNum}</td>
                <td>${temp.customerNum}</td>
                <td>${temp.averageOrder}</td>
                <td>${temp.averageCustomer}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
</body>
</html>