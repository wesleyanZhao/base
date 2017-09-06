<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<body>
<fieldset class="layui-elem-field" >
    <legend>个人信息</legend>
<div class="layui-field-box" >
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">昵称</label>
            <div class="layui-input-block">
                <input type="text" id="nickname" value="${result.nickname! ''}" name="nickname" required  lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">生日</label>
            <div class="layui-inline">
                <input name="birthday" value="${result.birthday! ''}" class="layui-input" placeholder="生日" onclick="layui.laydate({elem: this})">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">签名</label>
            <div class="layui-input-block">
                <textarea name="sign" placeholder="请输入内容" class="layui-textarea">${result.sign! ''}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">头像</label>
            <div class="layui-input-block">
                <input type="file" name="file" class="layui-upload-file">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="userInfoForm">确定</button>
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
    layui.use(['form','jquery','layer','laydate','upload'], function(){
        var $ = layui.jquery;
        var form = layui.form();
        var layer = layui.layer;
        var laydate = layui.laydate;

        //监听提交
        form.on('submit(userInfoForm)', function(params){
            var data = $("form").serializeArray();

            $.ajax({
                type: "POST",
                url: "/user/userInfoForm.do",  //后台程序地址
                data: data,  //需要post的数据
                success: function(data){           //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失败
                    if (data.result == 'success'){   //如果成功了, 则关闭当前layer
                        layer.msg('成功',{
                            icon: 1,
                            time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    },function(){//
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        });
                    }
                }
            });
            return false;//return false 表示不通过页面刷新方式提交表单
        });

        //头像上传
        layui.upload({
            url: '/user/upload.do'
            ,method: 'post' //上传接口的http类型
            ,success: function(res){
                layer.msg("上传成功");
                //LAY_demo_upload.src = res.url;
            }
        });
    });
</script>
</body>
</html>