<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>订单统计</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
            <li class="layui-this">订单统计（时）</li>
            <li>订单统计（日）</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show" id="dayItem">
                <div style="font-size: 15px; color: #555555; font-weight: bold; line-height: 30px;">选择日期</div>
                <input type="text" class="layui-input" id="day" style="width: 10%; margin-bottom: 10px;">
                <button class="layui-btn" onclick="orderStatisticsHour()">统计</button>
            </div>
            <div class="layui-tab-item" id="monthItem">
                <div style="font-size: 15px; color: #555555; font-weight: bold; line-height: 30px;">选择月份</div>
                <input type="text" class="layui-input" id="month" style="width: 10%; margin-bottom: 10px;">
                <button class="layui-btn" onclick="orderStatisticsDay()">统计</button>
            </div>
        </div>
        <iframe id="content" style="width: 100%; height: 100%; border: none;"></iframe>
    </div>
</div>
<script>
    layui.use(['element', 'laydate', 'layer'], function () {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#day',
            min: '2018-04-01',
            max: '2020-01-01'
        });

        laydate.render({
            elem: '#month',
            type: 'month',
            min: '2018-04-01',
            max: '2020-01-01'
        });
    });

    function orderStatisticsHour() {
        var $ = layui.$;
        var day = $('#day').val();
        if (day === "") {
            layer.msg('请选择日期');
            return;
        }
        $('#content').attr('src', "${basePath!}/merchants/orderStatisticsHour?branchId=${currentBranchId}&date=" + day);
    }

    function orderStatisticsDay() {
        var $ = layui.$;
        var month = $('#month').val();
        if (month === "") {
            layer.msg('请选择月份');
            return;
        }
        $('#content').attr('src', "${basePath!}/merchants/orderStatisticsDay?branchId=${currentBranchId}&date=" + month);
    }
</script>
</body>
</html>