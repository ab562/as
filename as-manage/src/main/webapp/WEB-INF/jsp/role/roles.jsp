<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

                    <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
                        <h5>角色列表</h5>
                    </div>
                    <form class="form-inline">
                        <button type="button" shiro:hasPermission="/roles/add" onclick="$('#addRole').modal();" class="btn btn-info" style="float: right; margin-right: 1px;">新增</button>
                        <button type="button" shiro:hasPermission="/roles/delete" onclick="delById();" class="btn btn-info" style="float: right; margin-right: 1px;">删除</button>
                        <button type="button" shiro:hasPermission="/roles/saveRoleResources" onclick="allotResources();" class="btn btn-info" style="float: right; margin-right: 1px;">分配权限</button>
                    </form>
                    <div class="widget-content nopadding">
                        <table class="table table-bordered data-table" id="datatable" >
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" class="checkall" />
                                </th>
                                <th>ID</th>
                                <th>角色名称</th>
                            </tr>
                            </thead>
                        </table>
                    </div>

<!-- 弹框 -->
<div class="modal fade bs-example-modal-sm"  id="selectResources" tabindex="-1" role="dialog" aria-labelledby="selectRoleLabel">
    <div class="modal-dialog modal-sm" role="document" style="height: 600px; "  >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="selectRoleLabel">分配权限</h4>
            </div>
            <div class="modal-body">
                <form id="boxRoleForm" >
                    <ul id="treeDemo" class="ztree"></ul>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" onclick="saveRoleResources();" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
<!-- 弹框 -->


<!--添加弹框-->
<div class="modal fade" id="addRole" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addroleLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                <form id="roleForm">
                    <div class="form-group">
                        <label class="control-label">角色名称:</label>
                        <input type="text" class="form-control" name="roledesc" id="roleDesc" placeholder="请输入角色名称"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" onclick="addRole();" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
<!--/添加弹框-->



<script type="text/javascript">
    $(".checkall").click(function () {
        var check = $(this).prop("checked");
        $(".checkchild").prop("checked", check);
    });
    var table = $('#datatable').DataTable( {
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "searching" : false,
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "serverSide": true,//开启服务器模式，使用服务器端处理配置datatable
        "processing": true,//开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好

        "ajax": 'roles',
        "columns": [
            {
                "sClass": "text-center",
                "data": "id",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                },
                "bSortable": false
            },
            { "data": "id" },
            { "data": "roledesc" },
        ],
        columnDefs:[
            { "orderable": false, "targets": 1 },
            { "orderable": false, "targets": 2 },
        ],

    } );


    $(function() {
    	table.ajax.reload();
    } );

    function search(){
        table.ajax.reload();
    }
    //弹出选择角色的框
    var roleId;
    function allotResources(){
        var rid = $(".checkchild:checked").val();
        if ($(".checkchild:checked").length < 1)
        {
            layer.msg('请选择一条数据');
            return;
        }
        if ($(".checkchild:checked").length > 1)
        {
            layer.msg('一次只能修改一条数据');
            return;
        }
        roleId = rid;
        var setting = {
            check: {
                enable: true,
                chkboxType:  { "Y" : "p", "N" : "s" }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentid",
                }
            }
        };

        $.ajax({
            async:false,
            type : "POST",
            data:{rid:rid},
            url: "resources/resourcesWithSelected",
            dataType:'json',
            success: function(data){

                $.fn.zTree.init($("#treeDemo"), setting, data);
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.expandAll(true);
                $('#selectResources').modal();
            }
        });

    }

    //保存权限的选择
    function saveRoleResources() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                checkNode= zTree.getCheckedNodes(true);
        var ids = new Array();
        for(var i=0;i<checkNode.length;i++){
            ids.push(checkNode[i].id);
        }
        $.ajax({
            async:false,
            type : "POST",
            data:{roleid:roleId,resourcesid:ids.join(",")},
            url: "roles/saveRoleResources",
            success: function(data){
                if(data=="success"){
                    layer.msg('保存成功');
                    $('#selectResources').modal('hide');
                }else{
                    layer.msg('保存失败');
                    $('#selectResources').modal('hide');
                }
            }
        });
    }
    //添加用户
    function addRole() {
        var roleDesc = $("#roleDesc").val();
        if(roleDesc == "" || roleDesc == undefined || roleDesc == null){
            return layer.msg('角色名称不能为空', function(){
                //关闭后的操作
            });
        }

        $.ajax({
            cache: true,
            type: "POST",
            url:'roles/add',
            data:$('#roleForm').serialize(),// 你的formid
            async: false,
            success: function(data) {
                if(data=="success"){
                    layer.msg('保存成功');
                    table.ajax.reload();
                    $('#addRole').modal('hide');
                }else{
                    layer.msg('保存失败');
                    $('#addRole').modal('hide');
                }
            }
        });
    }


    function delById() {
        var id = $(".checkchild:checked").val();
        if ($(".checkchild:checked").length < 1)
        {
            layer.msg('请选择一条数据');
            return;
        }
        if ($(".checkchild:checked").length > 1)
        {
            layer.msg('一次只能修改一条数据');
            return;
        }
        layer.confirm('您确定要删除该角色吗？', {
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                cache: true,
                type: "POST",
                url:'roles/delete',
                data:{id:id},
                async: false,
                success: function(data) {
                    if(data=="success"){
                        layer.msg('删除成功');
                        table.ajax.reload();
                    }else{
                        layer.msg('删除失败');
                    }
                }
            });
        });


    }
</script>
