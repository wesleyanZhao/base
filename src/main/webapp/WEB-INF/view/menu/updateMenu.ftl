<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<body>
<fieldset class="layui-elem-field" >
    <legend>菜单修改</legend>
<div class="layui-field-box" >
    <form class="layui-form">
        <!-- id作为隐藏域-->
        <input type="hidden" name="id" value="${result.id}">
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-block">
                <input type="text" id="name" name="name" value="${result.name}" required  lay-verify="required" placeholder="请输入菜单名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单链接</label>
            <div class="layui-input-inline">
                <input type="text" id="url" name="url" value="${result.url}" required lay-verify="required" placeholder="请输入菜单链接" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否使用</label>
            <div class="layui-input-block">
                <input type="radio" name="isUsed" value="y" title="启用" checked>
                <input type="radio" name="isUsed" value="n" title="禁用" >
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">菜单级别</label>
            <div class="layui-input-block">
                <select lay-filter="menuLevel" id="menuLevel" name="menuLevel" >
                    <option value="" selected>请选择</option>
                    <option value="0" >一级</option>
                    <option value="1" >二级</option>
                </select>
            </div>
        </div>

        <div id="switch" class="layui-form-item" style="display:none"  >
            <label class="layui-form-label">一级菜单</label>
            <div class="layui-input-block">
                <select id="pId" name="pId"  disabled>
                    <option value="" selected>请选择</option>
                    <#list result.list as item>
                        <#if item.id == result.pId><option value="${item.id}" selected>${item.name}</option>
                            <#else ><option value="${item.id}" >${item.name}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="updateMenuForm">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
</fieldset>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script>
    layui.use(['form','jquery','layer'], function(){
        var $ = layui.jquery;
        var form = layui.form();
        var layer = layui.layer;

        // 监听select事件
        form.on("select(menuLevel)",function (data) {
            // 获取一级菜单个数，初始化只有1个
            var firstMenuSize = $("#pId option").length;
            // 选择二级菜单并且是第一次时才会发送请求
            if(data.value == '1') {
                // 控制显示select
                $("#switch").removeAttr("style");
                // 设置disabled属性为了提交form的时候不再将这个select数据传输到后台
                $("#pId").removeAttr("disabled");
                // 添加必填验证
                $("#pId").attr("lay-verify","required");
                form.render();
                if(firstMenuSize < 2) {
                    $.ajax({
                        type: "POST",
                        url: "/menu/getAllParentNodeInfo.do",  //后台程序地址
                        //data: data,  //需要post的数据
                        success: function(result){           //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失败
                            for(var i=0; i<result.length;i++){
                                if(i == 0) {
                                    $("#pId").append("<option value=\""+result[i].id+"\" selected>"+result[i].name+"</option>");
                                } else {
                                    $("#pId").append("<option value=\""+result[i].id+"\">"+result[i].name+"</option>");

                                }
                            }
                            form.render();
                        }
                    });
                }
            } else {
                $("#pId").removeAttr("lay-verify");
                $("#pId").attr("disabled","disabled");
                $("#switch").attr("style","display:none");
                form.render();
            }
        });

        //监听提交
        form.on('submit(updateMenuForm)', function(params){
            var data = $("form").serializeArray();
            $.ajax({
                type: "POST",
                url: "/menu/updateMenuForm.do",  //后台程序地址
                data: data,  //需要post的数据
                success: function(data){           //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失败
                    if (data.result == 'success'){   //如果成功了, 则关闭当前layer
                        layer.msg('修改成功',{
                            icon: 1,
                            time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    },function(){//
                            //注册成功后，自动关闭当前注册页面
                            //先得到当前iframe层的索引
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        });
                    }
                }
            });
            return false;//return false 表示不通过页面刷新方式提交表单
        });

        //带回页面的select参数进行动态赋值
        var pId = "${result.pId!''}";
        var isUsed= "${result.isUsed!''}";

        // 菜单级别动态赋值
        if(pId == '0') {
            $("#menuLevel").find("option[value = '0']").attr("selected","selected");

            $("#menuLevel").attr("disabled","disabled");
        } else if(pId != '0') {
            $("#menuLevel").find("option[value = '1']").attr("selected","selected");
            // 显示一级菜单下拉框div
            $("#switch").removeAttr("style");
            // 使可用
            $("#pId").removeAttr("disabled");

        }

        // 是否使用动态赋值
        if(isUsed == 'y') {
            $("input[name='isUsed']:eq(0)").attr("checked",'checked');
        } else if(isUsed == 'n') {
            $("input[name='isUsed']:eq(1)").attr("checked",'checked');
        }
        // 重新渲染页面
        form.render();
    });
</script>
</body>
</html>