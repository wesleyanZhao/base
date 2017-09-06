<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>商品管理系统 惠买ivalue后台管理</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>

<body>

<form id="pageSubmit" class="layui-form">
    <input type="hidden" id="currentPage" name="currentPage" >

    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" value="${params.name!''}" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">菜单链接</label>
        <div class="layui-input-inline">
            <input type="text" name="url"  value="${params.url!''}" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否使用</label>
        <div class="layui-input-inline">
            <select id="isUsed" name="isUsed" >
                <option value="">请选择</option>
                <option value="y">使用</option>
                <option value="n">禁用</option>
            </select>
        </div>

        <label class="layui-form-label">菜单级别</label>
        <div class="layui-input-inline">
            <select id="pId" name="pId" >
                <option value="">请选择</option>
                <option value="0">一级</option>
                <option value="1">二级</option>
            </select>
        </div>

        <div class="layui-input-inline">
            <button class="layui-btn" lay-submit >确认</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>菜单管理</legend>
</fieldset>

<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="50">
            <col width="100">
            <col width="100">
            <col width="200">
            <col width="50">
            <col width="50">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>ID</th>
            <th>级别</th>
            <th>名称</th>
            <th>链接</th>
            <th>是否使用</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#list list as item>
            <tr>
                <td>${item.id}</td>
                <td>
                    <#if item.pId==0>一级菜单
                    <#else>二级菜单
                    </#if>
                </td>
                <td>
                ${item.name}
                </td>
                <td>${item.url}</td>
                <td>
                    <#if item.isUsed=='y'>使用
                    <#else>禁用
                    </#if>
                </td>
                <td>
                    <#--<a class="manageMenu" val="${item.id}">管理菜单</a>-->
                    <button val="${item.id}" class="manageMenu layui-btn layui-btn-normal layui-btn-small"><i class="layui-icon"></i></button>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<div class="layui-form">
    <span id="form_page"></span>&nbsp;共${page.total}条数据
</div>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script type="text/javascript">
    layui.define([ 'element', 'form', 'layer', 'laypage'], function(exports) {
        var element = layui.element();
        var form = layui.form();
        var layer = layui.layer;
        var laypage = layui.laypage;
        var $ = layui.jquery;

        var pindex = "${page.pageNum}";// 当前页
        var ptotalpages = "${page.pages}";// 总页数
        var pcount = "${page.total}";// 数据总数

        // 分页
        laypage({
            cont : 'form_page', // 页面上的id
            pages : ptotalpages,//总页数
            curr : pindex,//当前页。
            skip : true,
            jump : function(obj, first) {
                $("#currentPage").val(obj.curr);//设置当前页
                //$("#size").val(psize);
                //防止无限刷新,
                //只有监听到的页面index 和当前页不一样是才出发分页查询
                if (obj.curr != pindex ) {
                    $("#pageSubmit").submit();
                }
            }
        });

        //监听修改事件
        $(".manageMenu").on("click",function(){
            var id = $(this).attr("val");
            layer.open({
                title: '管理菜单 - 惠买ivalue管理系统'
                ,area: ['400px', '500px']
                ,type: 2 //content内容为一个连接
                ,content: '/menu/updateMenu.do?id=' + id
            });
        });

        //带回页面的select参数进行动态赋值
        var pId = "${params.pId!''}";
        var isUsed= "${params.isUsed!''}";

        // 菜单级别动态赋值
        if(pId == '0') {
            $("#pId").find("option[value = '0']").attr("selected","selected");
        } else if(pId == '1') {
            $("#pId").find("option[value = '1']").attr("selected","selected");
        }

        // 是否使用动态赋值
        if(isUsed == 'y') {
            $("#isUsed").find("option[value = 'y']").attr("selected","selected");
        } else if(isUsed == 'n') {
            $("#isUsed").find("option[value = 'n']").attr("selected","selected");
        }
        // 重新渲染页面
        form.render();
    });
</script>
</body>
</html>