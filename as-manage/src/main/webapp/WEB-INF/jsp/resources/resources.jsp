<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

                    <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
                        <h5>资源列表</h5>
                    </div>
                    <form class="form-inline">
                        <button type="button" shiro:hasPermission="/resources/add" onclick="$('#resourcesModal').modal();" class="btn btn-info" style="float: right; margin-right: 1px;">新增</button>
                        <button type="button" shiro:hasPermission="/resources/delete" onclick="delById();" class="btn btn-info" style="float: right; margin-right: 1px;">删除</button>
                        </form>
                    <table class="table table-bordered data-table" id="datatable" >
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" class="checkall" />
                            </th>
                            <th>ID</th>
                            <th>资源名称</th>
                            <th>父资源</th>
                            <th>资源链接</th>
                            <th>资源类型</th>
                            <th>排序</th>
                        </tr>
                        </thead>
                    </table>
          

<!--弹框-->
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
<!--弹框-->


<!--添加弹框-->
<div class="modal fade" id="resourcesModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addroleLabel">添加资源</h4>
            </div>
            <div class="modal-body">
                <form id="resourcesForm">
                    <div class="form-group">
                        <label  class="control-label">资源名称:</label>
                        <input type="text" class="form-control" name="name" id="name" placeholder="请输入资源名称"/>
                    </div>
                    <div class="form-group">
                        <label  class="control-label">父资源ID:</label>
                        <input type="text" class="form-control" id="parentId" name="parentid"  placeholder="请输入父资源ID">
                    </div>
                    <div class="form-group">
                        <label  class="control-label">资源链接:</label>
                        <input type="text" class="form-control" id="resUrl" name="resurl"  placeholder="请输入资源链接">
                    </div>
                    <div class="form-group">
                        <label  class="control-label">资源类型:</label>
                        <select class="form-control" name="type" id="type" >
                            <option value="1">菜单</option>
                            <option value="2">按钮</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label">排序:</label>
                        <input type="text" class="form-control" id="sort" name="sort"  placeholder="请输入排序">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" onclick="addResources();" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
<!--/添加弹框-->



<script type="text/javascript">
    var table = $('#datatable').DataTable( {
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "searching" : false,
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "serverSide": true,//开启服务器模式，使用服务器端处理配置datatable
        "processing": true,//开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好

        "ajax": 'resources',
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
            { "data": "name" },
            { "data": "parentid" },
            { "data": "resurl" },
            { "data": "type" },
            { "data": "sort" },
        ],
        columnDefs:[
            { "orderable": false, "targets": 1 },
            { "orderable": false, "targets": 2 },
            { "orderable": false, "targets": 3 },
            { "orderable": false, "targets": 4 },
            {
                "orderable": false,
                "render": function(data, type, row) {
                    if(data==1){
                        return "菜单";
                    }else if(data==2){
                        return "按钮";
                    }else{
                        return "其他";
                    }
                },
                "targets": 5
            },
            { "orderable": false, "targets": 6 }
        ],

    } );

    $(".checkall").click(function () {
        var check = $(this).prop("checked");
        $(".checkchild").prop("checked", check);
    });
    $(function() {
    	 table.ajax.reload();
    } );

    function search(){
        table.ajax.reload();
    }

    //添加用户
    function addResources() {
        var name = $("#name").val();
        var parentId = $("#parentId").val();
        var resUrl = $("#resUrl").val();
        var sort = $("#sort").val();

        if(name == "" || name == undefined || name == null){
            return layer.msg('资源名称不能为空', function(){
                //关闭后的操作
            });
        }
        if(parentId == "" || parentId == undefined || parentId == null){
            return layer.msg('父资源id不能为空', function(){
                //关闭后的操作
            });
        }
        if(resUrl == "" || resUrl == undefined || resUrl == null){
            return layer.msg('资源链接不能为空', function(){
                //关闭后的操作
            });
        }
        if(sort == "" || sort == undefined || sort == null){
            return layer.msg('资源排序不能为空', function(){
                //关闭后的操作
            });
        }

        $.ajax({
            cache: true,
            type: "POST",
            url:'resources/add',
            data:$('#resourcesForm').serialize(),// 你的formid
            async: false,
            success: function(data) {
                if(data=="success"){
                    layer.msg('保存成功');
                    table.ajax.reload();
                    $('#resourcesModal').modal('hide');
                }else{
                    layer.msg('保存失败');
                    $('#resourcesModal').modal('hide');
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
        layer.confirm('您确定要删除该资源吗？', {
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                cache: true,
                type: "POST",
                url:'resources/delete',
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
