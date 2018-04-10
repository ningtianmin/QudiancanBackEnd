<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>去点餐后台</title>
</head>
<body>
<#if currentBranchId??>
<div class="layui-container" style="width: 90%;">
    <div class="layui-row">
        <ul class="layui-nav" style="display: flex; flex-direction: row-reverse; align-items: center">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon" style="color: #FFB800; font-size: 20px;">&#xe612;</i>&nbsp;&nbsp;${currentAccountName!}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">个人中心</a></dd>
                    <dd><a href="javascript:;">修改密码</a></dd>
                    <dd><a href="javascript:;" onclick="logout()">注销</a></dd>
                </dl>
            </li>
        </ul>
    </div>
    <div class="layui-row">
        <div class="layui-col-md1">
            <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="sideBar" style="width: 100%;">
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="javascript:;">门店中心</a>
                    <dl class="layui-nav-child">
                        <dd style="text-align: center"><a href="javascript:;"
                                                          onclick="changeMainBody('/merchants/categories')">商品分类</a>
                        <dd style="text-align: center"><a href="javascript:;"
                                                          onclick="changeMainBody('/merchants/departments')">出品部门</a>
                        </dd>
                        <dd style="text-align: center"><a href="javascript:;"
                                                          onclick="changeMainBody('/merchants/products')">产品管理</a></dd>
                        </dd>
                        <dd style="text-align: center"><a href="javascript:;">桌台分类</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">桌台管理</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">数据中心</a>
                    <dl class="layui-nav-child">
                        <dd style="text-align: center"><a href="javascript:;">订单分析</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">菜品分析</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">桌台分析</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">用户分析</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">营业分析</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">系统中心</a>
                    <dl class="layui-nav-child">
                        <dd style="text-align: center"><a href="javascript:;">系统设置</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">角色设置</a></dd>
                        <dd style="text-align: center"><a href="javascript:;">账号设置</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
        <div class="layui-col-md11">
            <iframe id="frame" style="width: 100%; height: 100%; border: none;"></iframe>
        </div>
    </div>
</div>
<#else>
<button class="layui-btn layui-bg-green" onclick="perfectShopSkip()">前去完善店铺信息</button>
</#if>

<!--layui script-->
<script>
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
        element.on('nav(sideBar)', function (elem) {
            layer.msg(elem.text());
        });
    });
</script>

<!--plain script-->
<script>
    function logout() {
        $.ajax({
            type: "POST",
            url: "${basePath!}/shops/logout",
            complete: function (res) {
                var response = JSON.parse(res.responseText);
                if (response.code === 0) {
                    layer.msg("注销成功", {icon: 6});
                } else {
                    layer.msg(response.message, {icon: 5});
                }
                setTimeout('location.href="${basePath!}/merchants/login"', 2000);
            }
        });
    }

    function perfectShopSkip() {
        window.location.href = "${basePath!}/merchants/perfectShop";
    }

    function changeMainBody(url) {
        document.getElementById("frame").src = '${basePath!}' + url;
    }
</script>

</body>
</html>