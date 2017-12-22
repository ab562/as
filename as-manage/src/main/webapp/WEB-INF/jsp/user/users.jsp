       <%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
       
                  <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
                        <h5>用户列表</h5>
                    </div>
                    <form class="form-inline">
                        <div class="form-group">
                            <label >编号:</label>
                            <input type="text" class="form-control" id="id-search" placeholder="编号:"/>
                        </div>
                        <div class="form-group">
                            <label >名称:</label>
                            <input type="text" class="form-control" id="name-search" placeholder="名称"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label">状态:</label>
                            <select id="status-search"  class="form-control">
                                <option value="">全部</option>
                                <option value="1">开启</option>
                                <option value="0">关闭</option>
                            </select>
                        </div>
                        <button type="button"  onclick="search();" class="btn btn-primary">查询</button>
                        <button shiro:hasPermission="/users/add" type="button"  onclick="$('#addUser').modal();" class="btn btn-info" style="float: right; margin-right: 1px;">新增</button>
                        <button shiro:hasPermission="/users/delete" type="button"  onclick="delByID();" class="btn btn-info" style="float: right; margin-right: 1px;">删除</button>
                        <button shiro:hasPermission="/users/saveUserRoles" type="button"  onclick="allotRole();" class="btn btn-info" style="float: right; margin-right: 1px;">分配角色</button>
                    </form>

                    <div class="widget-content nopadding">
                        <table class="table table-striped table-bordered" cellspacing="0" width="100%" id="datatable" >
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" class="checkall" />
                                </th>
                                <th>ID</th>
                                <th>用户名</th>
                                <th>是否启用</th>
                            </tr>
                            </thead>
                        </table>

                    </div>
                </div>
            

<!--弹框-->
<div class="modal fade bs-example-modal-sm"  id="selectRole" tabindex="-1" role="dialog" aria-labelledby="selectRoleLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="selectRoleLabel">分配角色</h4>
            </div>
            <div class="modal-body">
                <form id="boxRoleForm" >
                    <!--<div class='checkbox'>
                    <label><input type='checkbox' id=''/>First One</label>
                </div>
                    -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" onclick="saveUserRoles();" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
<!--/弹框-->


<!--添加弹框-->
<div class="modal fade" id="addUser" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addroleLabel">添加用户</h4>
            </div>
            <div class="modal-body">
                <form id="userForm">
                    <div class="form-group">
                        <label  class="control-label">用户名:</label>
                        <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名"/>
                        <span class="btn-action single glyphicons circle_question_mark" data-toggle="tooltip" data-placement="top" data-original-title="必填"><i></i></span>
                    </div>
                    <div class="form-group">
                        <label  class="control-label">密码:</label>
                        <input type="password" class="form-control" id="password" name="password"  placeholder="请输入密码 6位以上"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" onclick="addUser();" class="btn btn-primary">Save</button>
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
        //"ajax": '${ss}/user/userList.do',
        ajax : function(data, callback, settings) {
            //封装请求参数
            var param = getQueryCondition(data);
            $.ajax({
                type: "GET",
                url: 'users',
                cache : false,  //禁用缓存
                data: param,    //传入已封装的参数
                dataType: "json",
                success: function(result) {
                    //封装返回数据  如果参数相同，可以直接返回result ，此处作为学习，先这么写了。
                    var returnData = {};
                    returnData.draw = result.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.recordsTotal;//总记录数
                    returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("查询失败");
                }
            });
        },
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
            { "data": "username" },
            { "data": "status" },
        ],
        columnDefs:[
            { "orderable": false, "targets": 1 },
            { "orderable": false, "targets": 2 },
            {
                "orderable": false,
                "render": function(data, type, row) {
                    if(data==1){
                        return "开启";
                    }else{
                        return "关闭";
                    }
                },
                "targets": 3
            },
        ],

    } );


    $(function() {
    	 table.ajax.reload();

    } );

    function search(){
        table.ajax.reload();
    }
    //封装查询参数
    function getQueryCondition(data){
        var param = {};
        //组装排序参数
        param.id = $("#id-search").val();//查询条件
        param.username = $("#name-search").val();//查询条件
        param.status = $("#status-search").val();//查询条件
        //组装分页参数
        param.start = data.start;
        param.length = data.length;
        param.draw = data.draw;
        return param;
    }
    //弹出选择角色的框
    function allotRole(){
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
        $.ajax({
            async:false,
            type : "POST",
            data:{uid:id},
            url: 'roles/rolesWithSelected',
            dataType:'json',
            success: function(data){
                $("#boxRoleForm").empty();
                var htm = "<input type='hidden' name='userid' value='"+id+"'>";
                for(var i=0;i<data.length;i++){
                    htm += "<div class='checkbox'><label><input type='checkbox' name='roleid' value='"+data[i].id+"'";
                    if(data[i].selected==1){
                        htm += " checked='checked'";
                    }
                    htm +="/>"+data[i].roledesc+"</label></div>";
                }
                $("#boxRoleForm").append(htm);
            }
        });

        $('#selectRole').modal();
    }

    //保存角色的选择
    function saveUserRoles() {
        $.ajax({
            cache: true,
            type: "POST",
            url:'users/saveUserRoles',
            data:$('#boxRoleForm').serialize(),// 你的formid
            async: false,
            success: function(data) {
                if(data=="success"){
                    layer.msg('保存成功');
                    $('#selectRole').modal('hide');
                }else{
                    layer.msg('保存失败');
                    $('#selectRole').modal('hide');
                }
            }
        })
    }


    //添加用户
    function addUser() {
        var username = $("#username").val();
        var password = $("#password").val();
        if(username == "" || username == undefined || username == null){
            return layer.msg('用户名不能为空', function(){
                //关闭后的操作
            });
        }
        if(password.length<6||password.length>=16){
            return layer.msg('密码长度为6-16', function(){
                //关闭后的操作
            });
        }

        $.ajax({
            cache: true,
            type: "POST",
            url:'users/add',
            data:$('#userForm').serialize(),// 你的formid
            async: false,
            success: function(data) {
                if(data=="success"){
                    layer.msg('保存成功');
                    table.ajax.reload();
                    $('#addUser').modal('hide');
                }else if(data="error"){
                    layer.msg('该用户已存在');
                    $('#addUser').modal('hide');
                }else{
                    layer.msg('保存失败');
                    $('#addUser').modal('hide');
                }
            }
        });
    }

    function delByID() {
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
        layer.confirm('您确定要删除该用户吗？', {
            btn: ['确认','取消'] //按钮
        }, function(){
            $.ajax({
                cache: true,
                type: "POST",
                url:'users/delete',
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

