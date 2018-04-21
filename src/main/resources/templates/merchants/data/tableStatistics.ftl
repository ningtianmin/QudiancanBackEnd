<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>桌台统计</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
            <li class="layui-this">桌台统计</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <div style="font-size: 15px; color: #555555; font-weight: bold; line-height: 30px;">时间段</div>
                <input type="text" class="layui-input" id="datePeriod" style="width: 15%; margin-bottom: 10px;">
                <button class="layui-btn" onclick="tableStatistics()">统计</button>
            </div>
        </div>
        <iframe id="content" style="width: 100%; height: 100%; border: none;"></iframe>
    </div>
</div>
<script>
    layui.use(['element', 'laydate', 'layer'], function () {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#datePeriod',
            range: true,
            min: '2018-04-01',
            max: '2020-01-01'
        });
    });

    function tableStatistics() {
        var $ = layui.$;
        var datePeriod = $('#datePeriod').val();
        if (datePeriod === "") {
            layer.msg('请选择时间段');
            return;
        }
        $('#content').attr('src', "${basePath!}/merchants/tableStatisticsPeriod?branchId=${currentBranchId}&datePeriod=" + datePeriod);
    }
</script>
</body>
</html>