<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>堂点</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-col-md3" style="padding: 5px;" id="leftContent">
        <div id="extraInfo" class="layui-form">
            <div class="layui-form-item">
                <label class="layui-form-label">当前桌台</label>
                <div class="layui-input-inline">
                    <input class="layui-input" style="color: #FFB800;" id="branchTableName"
                           disabled>
                    <input hidden name="branchTableId" id="branchTableId">
                    <input hidden name="orderId" id="orderId">
                    <input hidden name="orderNumber" id="orderNumber">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" for="customerNum">客人数</label>
                <div class="layui-input-inline">
                    <input type="number" class="layui-input" style="color: #FFB800;" name="customerNum"
                           id="customerNum">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" for="note">备注</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" style="color: #FFB800;" name="note" id="note">
                </div>
            </div>
            <div id="newOrderOperation" style="display: none;">
                <button class="layui-btn layui-btn-lg layui-bg-gray layui-disabled" disabled
                        style="width: 100%; margin-bottom: 10px;"
                        lay-submit lay-filter="placeOrder" id="placeOrder">下单
                </button>
            </div>
            <div id="oldOrderOperation" style="display: none">
                <button class="layui-btn layui-btn-lg layui-bg-gray layui-disabled" disabled
                        style="width: 45%; margin-bottom: 10px;"
                        lay-submit lay-filter="superaddition" id="superaddition">追加产品
                </button>
                <button class="layui-btn layui-btn-lg layui-bg-red"
                        style="width: 45%; margin-bottom: 10px;"
                        lay-submit lay-filter="cashPayOrder" id="cashPayOrder">现金支付
                </button>
            </div>
        </div>
        <div id="tableOrder" style="display: none;">
        </div>
        <div id="cartTot" style="display: none;">
            <i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe698;</i>
            <span style="font-size: 20px;">购物车合计：<span
                    id="totalSum">0</span></span>
        </div>
        <div id="cartContainer" style="display: none; margin-top: 10px;">

        </div>
    </div>
    <div class="layui-col-md9" style="padding: 5px;" id="rightContent">
        <div id="tables">
            <#list tables as temp>
        <#if temp.status=='OCCUPIED'>
            <button class="layui-btn layui-btn-lg layui-bg-red"
                    style="width: 145px; height: 75px; margin-bottom: 10px; margin-left: 10px;"
                    onclick="showTableOrder('${temp.id}')">
                ${temp.name}
            </button>
        <#elseif temp.status=='LEISURE'>
            <button class="layui-btn layui-btn-lg layui-bg-green"
                    style="width: 145px; height: 75px; margin-bottom: 10px; margin-left: 10px;"
                    onclick="startOrder('${temp.id}','${temp.name}')">
                ${temp.name}
            </button>
        </#if>
            </#list>
        </div>
        <div id="products" style="display: none;">
            <#list products as temp>
                <span class="layui-show-md-block"
                      style="margin-bottom: 10px; margin-left: 10px; font-weight: bold; color: #40AFFE;">${temp.categoryName!}</span>
                <#list temp.products as temp2>
                <button class="layui-btn layui-btn-lg layui-bg-orange"
                        style="width: 145px; height: 50px; margin-bottom: 10px; margin-left: 10px;"
                        onclick="addCartProduct('${temp2.id}','${temp2.name}','${temp2.price}')">
                    ${temp2.name!}&nbsp;|&nbsp;<span style="color: darkred;">${temp2.price!}</span>
                </button>
                </#list>
            </#list>
        </div>
    </div>
</div>
<script>
    layui.use('form', function () {
        var form = layui.form;

        form.on('submit(placeOrder)', function (data) {
            var cartProductArray = [];
            for (var i = 0, size = cartData.length; i < size; i++) {
                var temp = cartData[i];
                cartProductArray.push({
                    "productId": temp.productId,
                    "productNum": temp.productNum
                });
            }
            var cartJson = JSON.stringify(cartProductArray);
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/orders',
                data: {
                    "branchId": ${currentBranchId!},
                    "branchTableId": data.field.branchTableId,
                    "note": data.field.note,
                    "customerNum": data.field.customerNum,
                    "cartJson": cartJson
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg('下单成功', {icon: 6});
                        setTimeout(function () {
                            parent.changeMainBody('/merchants/eatin?branchId=${currentBranchId}');
                        }, 1000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            });
        });

        form.on('submit(superaddition)', function (data) {
            var cartProductArray = [];
            for (var i = 0, size = cartData.length; i < size; i++) {
                var temp = cartData[i];
                cartProductArray.push({
                    "productId": temp.productId,
                    "productNum": temp.productNum
                });
            }
            var cartJson = JSON.stringify(cartProductArray);
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/orders/addProducts',
                data: {
                    "orderId": data.field.orderId,
                    "note": data.field.note,
                    "cartJson": cartJson
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg('追加产品成功', {icon: 6});
                        setTimeout(function () {
                            parent.changeMainBody('/merchants/eatin?branchId=${currentBranchId}');
                        }, 1000);
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                }
            });
        });

        form.on('submit(cashPayOrder)', function (data) {
            layer.msg('现金支付确认', {
                time: 0,
                btn: ['确认', '取消'],
                yes: function (index) {
                    $.ajax({
                        type: 'POST',
                        url: '${basePath!}/shops/orders/cashPay',
                        data: {
                            "orderNumber": data.field.orderNumber,
                            "branchId": ${currentBranchId}
                        },
                        complete: function (res) {
                            layer.close(index);
                            console.log(res);
                            var response = JSON.parse(res.responseText);
                            if (response.code === 0) {
                                layer.msg('现金支付成功', {icon: 6});
                                setTimeout(function () {
                                    parent.changeMainBody('/merchants/eatin?branchId=${currentBranchId}');
                                }, 1000);
                            } else {
                                layer.msg(response.message, {icon: 5});
                            }
                        }
                    });

                }
            });
        });
    });
</script>
<script>
    var cartData = [];

    function startOrder(branchTableId, branchTableName) {
        console.log('branchTableId:' + branchTableId + ',branchTableName:' + branchTableName);
        $('#newOrderOperation').css('display', 'block');
        $('#oldOrderOperation').css('display', 'none');
        $('#tableOrder').css('display', 'none');
        $('#cartTot').css('display', 'block');
        $('#tables').css('display', 'none');
        $('#products').css('display', 'block');
        var customerNum = $('#customerNum');
        customerNum.attr('disabled', false);
        var cartContainer = $('#cartContainer');
        cartContainer.empty();
        cartContainer.css('display', 'block');

        // 初始化数据
        branchTableId = parseInt(branchTableId);
        $('#branchTableName').attr('value', branchTableName);
        customerNum.attr('value', 2);
        $('#note').attr('value', '');
        $('#branchTableId').attr('value', branchTableId);
    }

    function addCartProduct(productId, productName, productPrice) {
        productId = parseInt(productId);
        productPrice = parseFloat(productPrice);
        for (var i = 0, size = cartData.length; i < size; i++) {
            var obj = cartData[i];
            if (obj.productId === productId) {
                obj.productNum++;
                $('#productNum' + productId).attr('value', obj.productNum);
                computeTotalSum();
                return;
            }
        }
        var temp = {
            "productId": productId,
            "productNum": 1,
            "productName": productName,
            "productPrice": productPrice
        };
        cartData.push(temp);
        console.log(cartData);
        $('#cartContainer').append(
                '<div class="layui-form-item" id="cartItem' + productId + '">\n' +
                '    <label class="layui-form-label">' + productName + '</label>\n' +
                '    <div class="layui-input-inline" style="width: 100px;">\n' +
                '       <input type="number" class="layui-input" style="color: #FFB800;" id="productNum' + productId + '" disabled value="1">\n' +
                '    </div>' +
                '    <div class="layui-input-inline" style="width: 100px;">' +
                '       <i class="layui-icon" style="font-size: 30px; color: #1E9FFF; margin-right: 20px; cursor: pointer;" onclick="addCartProduct(\'' + productId + '\')">&#xe619;</i>' +
                '       <i class="layui-icon" style="font-size: 30px; color: #1E9FFF; cursor: pointer;" onclick="subCartProduct(\'' + productId + '\')">&#xe61a;</i>' +
                '    </div>' +
                '</div>'
        );
        computeTotalSum();
    }

    function subCartProduct(productId) {
        var cartDataTemp = [];
        productId = parseInt(productId);
        for (var i = 0, size = cartData.length; i < size; i++) {
            var obj = cartData[i];
            if (obj.productId === productId) {
                if (obj.productNum !== 1) {
                    obj.productNum--;
                    $('#productNum' + productId).attr('value', obj.productNum);
                } else {
                    $('#cartItem' + productId).remove();
                    continue;
                }
            }
            cartDataTemp.push({
                "productId": obj.productId,
                "productNum": obj.productNum,
                "productName": obj.productName,
                "productPrice": obj.productPrice
            });
        }
        cartData = cartDataTemp;
        computeTotalSum();
    }

    function computeTotalSum() {
        var totalSum = 0;
        for (var i = 0, size = cartData.length; i < size; i++) {
            totalSum += parseFloat((cartData[i].productPrice * cartData[i].productNum).toFixed(2));
        }
        $('#totalSum').text(totalSum);
        var placeOrder = $('#placeOrder');
        var superaddition = $('#superaddition');
        if (totalSum === 0) {
            placeOrder.attr('disabled', true);
            placeOrder.attr('class', 'layui-btn layui-btn-lg layui-bg-gray layui-disabled');
            superaddition.attr('disabled', true);
            superaddition.attr('class', 'layui-btn layui-btn-lg layui-bg-gray layui-disabled');
        } else {
            placeOrder.attr('disabled', false);
            placeOrder.attr('class', 'layui-btn layui-btn-lg layui-bg-red');
            superaddition.attr('disabled', false);
            superaddition.attr('class', 'layui-btn layui-btn-lg layui-bg-red');
        }
    }

    function showTableOrder(branchTableId) {
        branchTableId = parseInt(branchTableId);
        $.ajax({
            type: 'GET',
            url: '${basePath!}/shops/tables/' + branchTableId + '/tableOrder',
            complete: function (res) {
                console.log(res);
                var response = JSON.parse(res.responseText);
                if (response.code === 0) {
                    $('#newOrderOperation').css('display', 'none');
                    $('#oldOrderOperation').css('display', 'block');
                    var tableOrder = $('#tableOrder');
                    tableOrder.css('display', 'block');
                    $('#cartTot').css('display', 'block');
                    $('#tables').css('display', 'none');
                    $('#products').css('display', 'block');
                    $('#customerNum').attr('disabled', true);
                    var cartContainer = $('#cartContainer');
                    cartContainer.empty();
                    cartContainer.css('display', 'block');

                    var table = response.data.table;
                    var order = response.data.order;
                    // 初始化数据
                    $('#branchTableName').attr('value', table.name);
                    $('#customerNum').attr('value', order.customerNum);
                    $('#note').attr('value', '');
                    $('#branchTableId').attr('value', branchTableId);
                    $('#orderId').attr('value', order.id);
                    $('#orderNumber').attr('value', order.orderNumber);
                    // 订单信息
                    tableOrder.append(
                            '<div style="padding:0 10px; font-size: 20px;">\n' +
                            '    <div class="layui-show-lg-block" style="color: red;">该桌台已下单，可以追加产品！</div>\n' +
                            '    <div class="layui-show-lg-block">订单编号：' + order.orderNumber + '</div>\n' +
                            '    <div class="layui-show-lg-block">订单金额：' + order.totalSum + '</div>\n' +
                            '    <div class="layui-show-lg-block">订单备注：' + order.note + '</div>\n' +
                            '    <div class="layui-show-lg-block">订单状态：' + order.orderStatus + '</div>\n' +
                            '    <div class="layui-show-lg-block">支付状态：' + order.payStatus + '</div>\n' +
                            '    <div class="layui-show-lg-block">创建时间：' + order.createTime + '</div>\n' +
                            '    <hr>\n' +
                            '</div>'
                    );
                    // 桌台已下单产品遍历
                    var orderProducts = order.orderProducts;
                    var element =
                            '<div class="layui-form-item">\n' +
                            '    <label class="layui-form-label" style="text-align: center;">产品名</label>\n' +
                            '    <label class="layui-form-label" style="text-align: center;">数量</label>\n' +
                            '    <label class="layui-form-label" style="text-align: center;">小计</label>\n' +
                            '</div>';
                    for (var i = 0, size = orderProducts.length; i < size; i++) {
                        var product = orderProducts[i];
                        element += '<div class="layui-form-item">\n' +
                                '    <label class="layui-form-label">' + product.name + '</label>\n' +
                                '    <div class="layui-input-inline" style="width: 100px;">\n' +
                                '       <input class="layui-input" style="color: #FFB800;" disabled value="' + product.num + '">\n' +
                                '    </div>' +
                                '    <div class="layui-input-inline" style="width: 100px;">\n' +
                                '       <input class="layui-input" style="color: #FFB800;" disabled value="' + product.totalSum + '">\n' +
                                '    </div>' +
                                '</div>';
                    }
                    tableOrder.append(element);
                } else {
                    layer.msg(response.message, {icon: 5});
                }
            }
        });
    }
</script>
</body>
</html>