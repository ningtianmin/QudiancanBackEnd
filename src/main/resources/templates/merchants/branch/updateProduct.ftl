<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${basePath!}/static/layui/css/layui.css">
    <script src="${basePath!}/static/jquery-3.3.1.min.js"></script>
    <script src="${basePath!}/static/layui/layui.js"></script>
    <title>更新产品</title>
</head>
<body>
<div class="layui-container" style="width: 100%; padding: 10px;">
    <div class="layui-row"
         style="font-size: 30px; color: #555555; font-weight: bold; line-height: 60px; text-align: center;">更新产品
    </div>
    <div class="layui-form">
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label">产品类目</label>
            <div class="layui-input-inline">
                <select name="categoryId" lay-verify="required">
                <#list productCategories as item>
                    <option value="${item.key}"
                            <#if item.key == product.categoryId+"">selected</#if>>${item.value}</option>
                </#list>
                </select>
            </div>
            <label class="layui-form-label">出品部门</label>
            <div class="layui-input-inline">
                <select name="departmentId" lay-verify="required" style="width: 190px;">
                <#list branchDepartments as item>
                    <option value="${item.key}"
                            <#if item.key == product.departmentId+"">selected</#if>>${item.value}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="name">产品名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required|name" lay-verType="tips"
                       placeholder="请输入产品名称" autocomplete="off" class="layui-input" value="${product.name}">
            </div>
            <label class="layui-form-label" for="unitName">单位名称</label>
            <div class="layui-input-inline">
                <input type="text" id="unitName" name="unitName" lay-verify="required|unitName" lay-verType="tips"
                       placeholder="请输入单位名称(份/个...)" autocomplete="off" class="layui-input" value="${product.unitName}">
            </div>
        </div>
        <div class="layui-form-item"
             style="display: flex; align-items: center; align-self: center; justify-content: center">
            <label class="layui-form-label" for="price">产品价格</label>
            <div class="layui-input-inline">
                <input type="text" id="price" name="price" lay-verify="required|price" lay-verType="tips"
                       placeholder="请输入产品价格" autocomplete="off" class="layui-input" value="${product.price}">
            </div>
            <label class="layui-form-label" for="position">类目内排序</label>
            <div class="layui-input-inline">
                <input type="number" id="position" name="position" lay-verify="required|position" lay-verType="tips"
                       placeholder="请输入类目内排序" autocomplete="off" class="layui-input" value="${product.position}">
            </div>
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <label class="layui-form-label" for="description">产品描述</label>
            <div class="layui-input-inline">
                <textarea placeholder="请输入产品描述" lay-verType="tips" style="width: 190px; height: 154px; resize: none;"
                          lay-verify="required|description"
                          name="description"
                          id="description" class="layui-textarea">${product.description}</textarea>
            </div>
            <label class="layui-form-label">产品图片</label>
            <div class="layui-input-inline">
                <div class="layui-upload-drag" id="imageUpload">
                    <i class="layui-icon"></i>
                    <p>点击上传产品图片，或将图片拖拽到此处</p>
                </div>
            </div>
            <input hidden name="image" id="image" value="${product.image}" lay-verify="image">
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <img id="imagePreview" style="width: 200px; height: 200px;" src="${product.image}">
        </div>
        <div class="layui-form-item" style="display: flex; align-items: center; justify-content: center">
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-orange" lay-submit lay-filter="updateProduct">更新产品</button>
            </div>
            <div class="layui-form-label">
                <button class="layui-btn layui-bg-green" onclick="parent.changeMainBody('/merchants/products')">取消更新
                </button>
            </div>
        </div>
    </div>
</body>

<#--layui script-->
<script>
    layui.use(['form', 'upload', 'layer'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var upload = layui.upload;

        form.verify({
            name: function (value) {
                if (value.length < 1 || value.length > 15) {
                    return '产品名称一个字符到15个字符';
                }
            },
            unitName: function (value) {
                if (value.length < 1 || value.length > 5) {
                    return '单位名称一个字符到五个字符';
                }
            },
            price: [/^(0|[1-9][0-9]{0,3})(\.[0-9]{1,2})?$/, '价格0至9999.99'],
            position: [/^[1-9][0-9]{0,3}$/, '类目内排序为1至4位的整数'],
            description: function (value) {
                if (!(value.length >= 2 && value.length <= 50)) {
                    return '产品描述两个字符到五十个字符';
                }
            },
            image: function (value) {
                if (value === "") {
                    return '请上传产品图片';
                }
            }
        });

        form.on('submit(updateProduct)', function (data) {
            if (data.field.categoryId === "") {
                layer.msg('请选择产品类目或前去创建');
            }
            if (data.field.departmentId === "") {
                layer.msg('请选择出品部门或前去创建');
            }
            $.ajax({
                type: 'POST',
                url: '${basePath!}/shops/${currentShopId}/branches/${currentBranchId}/products/${product.id}',
                data: {
                    "categoryId": data.field.categoryId,
                    "departmentId": data.field.departmentId,
                    "name": data.field.name,
                    "image": data.field.image,
                    "unitName": data.field.unitName,
                    "price": data.field.price,
                    "description": data.field.description,
                    "position": data.field.position
                },
                complete: function (res) {
                    var response = JSON.parse(res.responseText);
                    if (response.code === 0) {
                        layer.msg("更新成功", {icon: 6});
                    } else {
                        layer.msg(response.message, {icon: 5});
                    }
                    setTimeout(function () {
                        parent.changeMainBody('/merchants/products');
                    }, 1500);
                }
            });
        });

        upload.render({
            elem: '#imageUpload',
            method: 'POST',
            url: '${basePath!}/shops/uploadImage',
            accept: 'images',
            acceptMime: 'image/*',
            auto: true,
            size: 10240,
            drag: true,
            before: function (obj) {
                obj.preview(function (index, file, result) {
                    var imagePreview = $('#imagePreview');
                    imagePreview.attr('src', result);
                });
                layer.load();
            },
            done: function (res, index, upload) {
                // res为responseBody对象
                if (res.code === 0) {
                    layer.msg('上传成功');
                    $('#image').val(res.data);
                } else {
                    layer.msg(res.message);
                }
                layer.closeAll('loading');
            },
            error: function (index, upload) {
                layer.closeAll('loading');
            }
        });
    });
</script>

</html>
