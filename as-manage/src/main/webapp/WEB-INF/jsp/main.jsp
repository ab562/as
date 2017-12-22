<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/jquery.dataTables.min.css" />
    <link rel="stylesheet" href="css/matrix-style.css" />
    <link rel="stylesheet" href="css/matrix-media.css" />
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
    <title>傲胜后台管理</title>
</head>
<body>

<!--Header-part-->
<div id="header">
    <h1><a href="dashboard.html">Matrix Admin</a></h1>
</div>
<!--close-Header-part-->

<!--top -->
<div id="user-nav" class="navbar navbar-inverse" >
    <ul class="nav">
        <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">Welcome User</span><b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a href="#"><i class="icon-user"></i> My Profile</a></li>
                <li class="divider"></li>
                <li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
                <li class="divider"></li>
                <li><a  href="${pageContext.request.contextPath}/logout.do"><i class="icon-key"></i> Log Out</a></li>
            </ul>
        </li>
        <li class="dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">Messages</span> <span class="label label-important">5</span> <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a class="sAdd" title="" href="#"><i class="icon-plus"></i> new message</a></li>
                <li class="divider"></li>
                <li><a class="sInbox" title="" href="#"><i class="icon-envelope"></i> inbox</a></li>
                <li class="divider"></li>
                <li><a class="sOutbox" title="" href="#"><i class="icon-arrow-up"></i> outbox</a></li>
                <li class="divider"></li>
                <li><a class="sTrash" title="" href="#"><i class="icon-trash"></i> trash</a></li>
            </ul>
        </li>
        <li class=""><a title="" href="#"><i class="icon icon-cog"></i> <span class="text">Settings</span></a></li>
        <li class=""><a href="${pageContext.request.contextPath}/logout.do"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
    </ul>
</div>
<div id="search">
    <input type="text" placeholder="Search here..."/>
    <button type="submit" class="tip-bottom" title="Search"><i class="icon-search icon-white"></i></button>
</div>

<!-- top end -->
<!-- left -->
<input id="path" type="hidden" value="${pageContext.request.contextPath}"/>
	<div id="sidebar">
	</div>
<!-- left end -->

<div id="content">

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">

                <div class="widget-box">
                 <!--     内容 -->
                </div>
            </div>
        </div>
    </div>
</div>

<!--Footer-part-->
<div class="row-fluid">
    <div id="footer" class="span12"> 2017 &copy; yqj <a href="http://themedesigner.in/">Themedesigner.in</a> </div>
</div>
<!--end-Footer-part-->
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script src="js/bootstrap.min.js" sr></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/layer.js}"></script>
<script src="js/jquery.ba-hashchange.js"></script>
   <script type="text/javascript">
    var bak="";
 function loadhtml(u){
 	//由于页面是局部刷新导致多次加载booststrap重复绑定事件，所以在加载前 移除了
 	$(document).off();
 	$(document).unbind();
 	$(".widget-box").load(u);
 	location.hash=u;
 	bak=u;
}
 //解决前进后退问题
 $(function(){  
     $(window).hashchange( function(){  
           var hash=location.hash;
            var  url=hash.substring(1);  
           if(hash&&url!=bak){    
               loadhtml(url); //调用load方法，执行前进 后退  
           }
       })  
       $(window).hashchange();       //手动执行调用hash变化方法  
     findResourcesList();
       
 }); 
	var path=$("#path").val();
		$.ajax({cache : true,
					type : "POST",
					url : 'resources/loadMenu',
					dataType : "json",
					success : function(data) {
						var _html = '<ul >';
						$.each(data,function(i, n) {
											_html += '<li class="submenu" id=menu_'+i+'> <a href="javascript:openli('+i+')"><i class="icon"></i> <span>>>'
													+ n.name + '</span></a>';
											_html += '<ul >';
											$
													.each(
															n.childs,
															function(j, m) {
																_html += "<li><a href='javascript:loadhtml(\""+path+m.resurl+"\")'><i class='icon '></i> <span>"
																		+ m.name
																		+ "</span></a> </li>";
															});
											_html += '</ul >';
											_html += '</li>';
										});
						_html+='</ul >';
						$('#sidebar').html(_html);
					}
				});
	
		   function openli(i){
			   var menu=$('#menu_'+i);
		    	if(menu.hasClass("open")){
		    		menu.removeClass('open');
		    	}else{
		    		menu.addClass('open');
		    	}
		    }
	</script>
</body>
</html>