<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>角色设置</title>
</head>
<body>
<div class="layui-container">
    <table class="layui-table">
        <thead class="layui-table-header">
        <tr>
            <td>角色名称</td>
            <td>角色描述</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody class="layui-table-body">
        <#list roles as temp>
        <tr>
            <td>${temp.name}</td>
            <td>${temp.description}</td>
            <td>
                <button onclick="showAuthority(${temp.id})" class="layui-btn">查看权限</button>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>

</div>
<script>
    layui.use('layer', function () {

    });

    function showAuthority(roleId) {
        $.ajax({
            type: 'GET',
            url: '${basePath!}/shops/roles/' + roleId + '/authorities',
            complete: function (res) {
                var response = JSON.parse(res.responseText);
                var authorities = response.data;
                var html = '';
                for (var i = 0, length = authorities.length; i < length; i++) {
                    var temp = authorities[i];
                    var description = temp.description == null ? '' : temp.description;
                    html += '<tr><td>' + temp.path + '</td><td>' + temp.name + '</td><td>' + description + '</td></tr>'
                }
                layer.open({
                    closeBtn: 0,
                    skin: 'layui-bg-white',
                    shadeClose: true,
                    type: 1,
                    title: false,
                    area: ['1024px', '512px'],
                    content:
                    '<table class="layui-table">' +
                    '    <thead class="layui-table-header">' +
                    '    <tr>' +
                    '        <td>path</td>' +
                    '        <td>名称</td>' +
                    '        <td>描述</td>' +
                    '    </tr>' +
                    '    </thead>' +
                    '   <tbody>' + html + '</tbody>' +
                    '</table>'
                });
            }
        });
    }
</script>
</body>
</html>