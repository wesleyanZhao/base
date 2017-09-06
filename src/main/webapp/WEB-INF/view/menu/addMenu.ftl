<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<body>
<fieldset class="layui-elem-field" >
    <legend>添加菜单</legend>
<div class="layui-field-box" >
    <form class="layui-form">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">菜单名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="name" lay-verify="require" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">菜单链接</label>
                <div class="layui-input-inline">
                    <input type="text" name="url"  autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">是否使用</label>
            <div class="layui-input-block">
                <input type="radio" name="isUsed" value="y" title="是" checked>
                <input type="radio" name="isUsed" value="n" title="否" >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单级别</label>
            <div class="layui-input-block">
                <select lay-filter="menuLevel" name="menuLevel" lay-verify="required">
                    <option value="0" selected>一级</option>
                    <option value="1">二级</option>
                </select>
            </div>
        </div>
        <div id="switch" class="layui-form-item" style="display:none"  >
            <label class="layui-form-label" >关联菜单</label>
            <div class="layui-input-block">
                <select id="pId" name="pId" lay-verify="required" disabled>
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="addMenuForm">确认</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
</fieldset>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script>
    //Demo
    // 待学生自主完成
    layui.use(['form','jquery','layer'], function(){
        var $ = layui.jquery;
        var form = layui.form();
        var layer = layui.layer;
        
        form.on("select(menuLevel)",function (data) {
            // 获取一级菜单个数，初始化只有1个
            var parentMenu = $("#pId option").length;
            // 选择二级菜单并且是第一次时才会发送请求
            if(data.value == '1') {
                $("#switch").removeAttr("style");
                $("#pId").removeAttr("disabled");
                if(parentMenu < 2) {
                    $("#pId").attr("lay-verify","required");
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
            }
            form.render();
        });

        //监听提交
        form.on('submit(addMenuForm)', function(params){
            var data = $("form").serializeArray();
            $.ajax({
                type: "POST",
                url: "/menu/addMenuForm.do",  //后台程序地址
                data: data,  //需要post的数据
                success: function(data){           //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失败
                    if (data.result == 'success'){   //如果成功了, 则关闭当前layer
                        layer.msg('添加成功',{
                            icon: 1,
                            time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    },function(){//
                            //do something
                            //注册成功后，自动关闭当前注册页面
                            //先得到当前iframe层的索引
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            //parent.layer.closeAll("iframe");
                        });
                    }
                }
            });
            return false;//return false 表示不通过页面刷新方式提交表单
        });
    });
</script>
</body>
</html>