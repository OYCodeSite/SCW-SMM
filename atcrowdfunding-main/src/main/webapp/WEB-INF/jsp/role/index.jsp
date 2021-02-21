<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2021/1/27
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@ include file="/WEB-INF/jsp/common/css.jsp"%>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

    <%@ include file="/WEB-INF/jsp/common/top.jsp"%>

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline" role="form" style="float:left;">
                            <div class="form-group has-feedback">
                                <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                    <input id="condition" class="form-control has-success" type="text" placeholder="请输入查询条件">
                                </div>
                            </div>
                            <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                        <security:authorize access="hasRole('PM - 项目经理')">
                            <button type="button" id="addBtn" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        </security:authorize>
                        <br>
                        <hr style="clear:both;">
                        <div class="table-responsive">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input type="checkbox"></th>
                                    <th>名称</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                                <tfoot>
                                <tr >
                                    <td colspan="6" align="center">
                                        <ul class="pagination">

                                        </ul>
                                    </td>
                                </tr>

                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <!-- 添加数据 模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">添加角色</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="exampleInputPassword1">角色名称</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名称">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="saveBtn" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 修改数据 模态框 -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改角色</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="exampleInputPassword1">角色名称</label>
                        <input type="hidden" name="id">
                        <input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名称">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="updateBtn" type="button" class="btn btn-primary">修改</button>
                </div>
            </div>
        </div>
    </div>



    <!-- 角色分配 模态框 -->
    <div class="modal fade" id="assignModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">给角色分配权限</h4>
                </div>
                <div class="modal-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="assignBtn" type="button" class="btn btn-primary">分配</button>
                </div>
            </div>
        </div>
    </div>





    <%@ include file="/WEB-INF/jsp/common/js.jsp"%>
    <script type="text/javascript">
        $(function () {
            $(".list-group-item").click(function(){
                if ( $(this).find("ul") ) {
                    $(this).toggleClass("tree-closed");
                    if ( $(this).hasClass("tree-closed") ) {
                        $("ul", this).hide("fast");
                    } else {
                        $("ul", this).show("fast");
                    }
                }
            });

            initData(1);
        });

        //======分页查询 开始================================================================

        var json = {
            pageNum: 1,
            pageSize: 2
        }


        function initData(pageNum){

            // 1. 发起ajax请求，获取分页数据

            json.pageNum = pageNum;

            var index = -1;

            $.ajax({
                type:'post',
                url:"${PATH}/role/loadData",
                data:json,
                beforeSend:function(){
                    index = layer.load(0,{time:10*1000});
                    return true ;
                },
                success:function(result){

                    console.log(result);
                    layer.close(index);

                    initShow(result);

                    initNavg(result);
                }
            });
        }

        // 展示数据
        function initShow(result) {
            // alert("initShow", result);

            $('tbody').empty();
                var list = result.list;

                // 方式一
                // var context = '';
                //
                // $.each(list, function(i,e){
                //     context +='<tr>';
                //     context +='	<td>'+(i+1)+'</td>';
                //     context +='	<td><input type="checkbox"></td>';
                //     context +='	<td>'+e.name+'</td>';
                //     context +='	<td>';
                //     context +='		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                //     context +='		<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                //     context +='		<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                //     context +='	</td>';
                //     context +='</tr>';
                // });
                //
                // $("tbody").html(context);

            // 方式二
            $.each(list, function (i,e){
                var tr = $('<tr></tr>');

                tr.append('<tr>'+(i+1)+'</tr>');
                tr.append('<td><input type="checkbox"></td>');
                tr.append('	<td>'+e.name+'</td>');

                var td = $('<td></td>');
                td.append('<button type="button" roleId="'+e.id+'" class="assignPermissionClass btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>')
                td.append('<button type="button" roleId="'+e.id+'" class="updateClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>')
                td.append('<button type="button" roleId="'+e.id+'" class="deleteClass btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>')

                tr.append(td);

                tr.appendTo($('tbody'));
            });
        }

            // 3.展示分页条
        function initNavg(result){
            //console.log(result);

           $('.pagination').empty();


           var navigatepageNums = result.navigatepageNums;

           if(result.isFirstPage){
               $('.pagination').append($('<li class="disabled"><a href="#">上一页</a></li>'));
           }else{
               $('.pagination').append($('<li><a onclick="initData('+(result.pageNum-1)+')">上一页</a></li>'))
           }

           $.each(navigatepageNums, function (i, num){
               if(num == result.pageNum){
                   $('.pagination').append($('<li class="active"><a href="#">'+num+' <span class="sr-only">(current)</span></a></li>'));
               }else{
                   $('.pagination').append($('<li><a onclick="initData('+num+')">'+num+'</a></li>'))
               }
           });


            if(result.isLastPage){
                $('.pagination').append($('<li class="disabled"><a href="#">下一页</a></li>'));
            }else{
                $('.pagination').append($('<li><a onclick="initData('+(result.pageNum+1)+')">下一页</a></li>'))
            }


            // ===分页查询=========================================

            $("#queryBtn").click(function (){
                var condition = $("#condition").val();

                json.condition = condition;

                initData(1)
            });


            // ===添加 开始=========================================

            $("#addBtn").click(function(){
                $("#addModal").modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });
            });

            $('#saveBtn').click(function(){
                var name = $("#addModal input[name='name']").val();

               //alert(name);
                $.ajax({
                    type: "post",
                    url: "${PATH}/role/doAdd",
                    data: {
                        name: name
                    },
                    beforeSend: function(){
                        return true;
                    },
                    success: function (result){
                        if(result == 'ok'){
                           layer.msg("保存成功",{time: 1000}, function (){
                               $('#addModal').modal('hide');
                               $("#addModal input[name='name]").val("");
                               // 添加后初始化第一页，倒叙排序
                               initData(1);
                           });
                        }else{
                            layer.msg("保存失败", function (){
                                $('#addModal').modal('hide');
                            });

                        }
                    }
                });
            });

            // ===添加 结束=========================================


            // ===修改 开始=========================================

            $('tbody').on('click', '.updateClass', function (){

                var roleId = $(this).attr("roleId");

                $.get("${PATH}/role/getRoleById",{id: roleId}, function (result){
                    console.log(result);

                    $("#updateModal").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });

                    $("#updateModal input[name = 'name']").val(result.name);
                    $("#updateModal input[name = 'id']").val(result.id);
                });
            });

            $("#updateBtn").click(function(){
                var name = $("#updateModal input[name = 'name']").val();
                var id = $("#updateModal input[name = 'id']").val();

                $.post("${PATH}/role/doUpdate", {id:id, name:name}, function (){
                    if("ok" == result){
                        layer.msg("修改成功", {time: 1000}, function (){
                            $("#updateModal").modal('hide');
                            // 初始化当前页
                            initData(json.pageNum);
                        });
                    }else{
                        layer.msg("修改成功");
                    }
                });
            });

            // ===修改 结束=========================================

            //===删除 开始==============================================================

            $("tbody").on('click', '.deleteClass', function (){
                var id = $(this).attr("roleId");

                layer.confirm("您确定要删除吗？",{bth:['确定','取消']}, function (index){
                    $.post("${PATH}/role/doDelete",{id:id}, function (result){
                        if(result == "ok"){
                            layer.msg("删除成功", {time: 1000}, function (){
                                initData(json.pageNum);
                            });
                        }else{
                            layer.msg("删除失败");
                        }
                    });
                    layer.close(index)
                }, function (index){
                    layer.close(index);
                })
            })
            //===删除 开始==============================================================
        }


        // =====给角色分配权限 开始=======================================================

        var roleId='';

        $("tbody").on("click",".assignPermissionClass",function (){
            $("#assignModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });

            roleId = $(this).attr("roleId");

            initTree();
        });


        function initTree(){
            var setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: "pid"
                    },
                    key: {
                        url: "xxx",
                        name:"title"
                    },
                },
                view: {
                    addDiyDom: function(treeId,treeNode){
                        $("#"+treeNode.tId+"_ico").removeClass();
                        $("#"+treeNode.tId+"_span").before('<span class="'+treeNode.icon+'"></span>');
                    }
                }
            }

            // 多个异步请求，执行先后顺序问题

            // 1. 加载数据
            $.get("${PATH}/permission/listAllPermissionTree", function(data){

                $.fn.zTree.init($("#treeDemo"), setting, data);
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.expandAll(true);

                // 2.回显以分配许可
                $.get("${PATH}/role/listPermissionIdByRoleId",{roleId:roleId},function(data){
                    $.each(data,function(i,e){
                        var permissionId = e;
                        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                        var node = treeObj.getNodeByParam("id",permissionId,null);
                        treeObj.checkNode(node,true,false, false);
                    });
                });
            });


        }

        $("#assignBtn").click(function(){

            var json = {
                roleId: roleId
            };

            console.log(roleId);
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var node = treeObj.getCheckedNodes(true);
            $.each(node, function(i,e){
                var permissionId = e.id;
                console.log(permissionId);
                json['ids['+i+']']= permissionId;

                // 多条数据提交和接收
                //json['userList[i].name'] = 'xxx';
                //json['userList[i].age'] = 23;
            })

            $.ajax({
                type: "post",
                url: "${PATH}/role/doAssignPermissionToRole",
                data: json,
                success: function(result){
                    if("ok" == result){
                        layer.msg("分配成功",{time:1000}, function(){
                            $("#assignModal").modal('hide');
                        });
                    }else{
                        layer.msg("分配失败");
                    }
                }
            });
        });



        // =====给角色分配权限 结束========================================================
    </script>
</body>
</html>
