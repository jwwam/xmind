<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<base href="<%=basePath%>">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <title>XMind模板之家</title>
  <link rel="stylesheet" href="source/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="source/dist/css/AdminLTE.min.css">
  <link rel="stylesheet" href="source/dist/css/skins/_all-skins.min.css">
  <script src="source/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="source/bootstrap/js/bootstrap.min.js"></script>
  <script src="source/plugins/fastclick/fastclick.js"></script>
  <script src="source/dist/js/app.min.js"></script>
  <script src="source/dist/js/demo.js"></script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">

      <header class="main-header">
    <!-- Logo -->
    <a href="<%=basePath%>" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>Z</b>YQ</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>XMind</b>模板之家</span>
    </a>
    
 <%@ include file="header.jsp" %>
 
  </header>
  
     <%--  <%@ include file="aside.jsp" %> --%>
 	<aside class="main-sidebar">
    <section class="sidebar">
    	<div class="user-panel">
	        <div class="pull-left image">
	          <img src="source/dist/img/xmindico.png" class="img-circle" alt="User Image">
	        </div>
	        <div class="pull-left info">
	          <p>思如泉涌，成竹在图。</p>
	          <a href="#"><i class="fa fa-circle text-success"></i>让思维清晰可见。</a>
	        </div>
      	</div>
      
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="搜索...">
              <span class="input-group-btn">
                <!-- <button type="submit" name="search" id="search-btn" class="btn btn-flat"> -->
                
                <button type="button" onclick="searchs()" name="search" id="search-btn" class="btn btn-flat">
                <i class="fa fa-search"></i>
                <script>
                	function searchs(){
                		alert("暂未开放，敬请期待");
                		//showAlert("暂未开放，敬请期待");
                	}
                </script>
                </button>
              </span>
        </div>
      </form>
      
      <ul class="sidebar-menu">
      <li class="header">菜单栏</li>
      
      	<li class="treeview">
          <a href="javascript:void(0);">
            <i class="fa fa-table"></i> <span>模板列表</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=basePath%>news/content"><i class="fa fa-circle-o"></i>所有模板</a></li>
            <li><a href="javascript:;" onclick="return confirm('暂未开放');"><i class="fa fa-circle-o"></i>模板分类</a></li>
          </ul>
        </li>
        <li class="treeview active">
          <a href="javascript:void(0);">
              <i class="fa fa-dashboard"></i> 
              	<span>关于我</span>
              <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li>
	            <a href="<%=basePath%>news/aboutMe">
	            <i class="fa fa-circle-o"></i>
	            	我的初衷
	            </a>
            </li>
            <li class="active">
	           	<a href="<%=basePath%>timeline/timeList">
	           	<i class="fa fa-circle-o"></i>
	           		版本历史
	           	</a>
           	</li>
          </ul>
        </li>
        
      </ul>
    </section>
  </aside>

	    <!-- Content Wrapper. Contains page content -->
		  <div class="content-wrapper">
		    <!-- Content Header (Page header) -->
		    <section class="content-header">
		      <h1>
		        	版本更新历史
		        <small>历史记录</small>
		      </h1>
		      <ol class="breadcrumb">
		        <li><a href="javascript:void(0);"><i class="fa fa-dashboard"></i>主页</a></li>
		        <li><a href="javascript:void(0);">关于我</a></li>
		        <li class="active">版本历史</li>
		      </ol>
		    </section>
		
		    <!-- Main content -->
		    <section class="content">
		
		      <!-- row -->
		      <div class="row">
		        <div class="col-md-12">
		          <!-- The time line -->
		          <ul class="timeline">
		          <c:forEach var="time" items="${timeList.list}">
		            <li class="time-label">
	                  <span class="bg-red"><fmt:formatDate value="${time.time }" pattern="yyyy-MM-dd"/></span>
		            </li>
		            <li>
		              <i class="fa fa-envelope bg-blue"></i>
		              <div class="timeline-item">
		                <span class="time"><i class="fa fa-clock-o"></i>
		                <fmt:formatDate value="${time.time }" pattern="HH:mm:ss"/></span>
		                <h3 class="timeline-header"><a href="#">${time.title }</a><!-- 内容 --></h3>
		
		                <div class="timeline-body">${time.content }</div>
		                <!-- <div class="timeline-footer">
		                  <a class="btn btn-primary btn-xs">Read more</a>
		                  <a class="btn btn-danger btn-xs">Delete</a>
		                </div> -->
		              </div>
		            </li>
		          </c:forEach>
		            
		            <!-- <li>
		              <i class="fa fa-user bg-aqua"></i>
		
		              <div class="timeline-item">
		                <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
		
		                <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request</h3>
		              </div>
		            </li>
		            <li>
		              <i class="fa fa-comments bg-yellow"></i>
		
		              <div class="timeline-item">
		                <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>
		
		                <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>
		
		                <div class="timeline-body">
		                  Take me to your leader!
		                  Switzerland is small and neutral!
		                  We are more like Germany, ambitious and misunderstood!
		                </div>
		                <div class="timeline-footer">
		                  <a class="btn btn-warning btn-flat btn-xs">View comment</a>
		                </div>
		              </div>
		            </li>
		            <li class="time-label">
		                  <span class="bg-green">
		                    3 Jan. 2014
		                  </span>
		            </li>
		            <li>
		              <i class="fa fa-camera bg-purple"></i>
		
		              <div class="timeline-item">
		                <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>
		
		                <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>
		
		                <div class="timeline-body">
		                  <img src="http://placehold.it/150x100" alt="..." class="margin">
		                  <img src="http://placehold.it/150x100" alt="..." class="margin">
		                  <img src="http://placehold.it/150x100" alt="..." class="margin">
		                  <img src="http://placehold.it/150x100" alt="..." class="margin">
		                </div>
		              </div>
		            </li>
		            <li>
		              <i class="fa fa-video-camera bg-maroon"></i>
		
		              <div class="timeline-item">
		                <span class="time"><i class="fa fa-clock-o"></i> 5 days ago</span>
		
		                <h3 class="timeline-header"><a href="#">Mr. Doe</a> shared a video</h3>
		
		                <div class="timeline-body">
		                  <div class="embed-responsive embed-responsive-16by9">
		                    <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/tMWkeBIohBs"
		                            frameborder="0" allowfullscreen></iframe>
		                  </div>
		                </div>
		                <div class="timeline-footer">
		                  <a href="#" class="btn btn-xs bg-maroon">See comments</a>
		                </div>
		              </div>
		            </li>
		            <li>
		              <i class="fa fa-clock-o bg-gray"></i>
		            </li> -->
		            
		            
		          </ul>
		          
		        </div>
		        <!-- /.col -->
		      </div>
		      <!-- /.row -->
		
		      
		
		    </section>
		    <!-- /.content -->
		  </div>
		  <!-- /.content-wrapper -->      

      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b>0.1
        </div>
        <strong>Copyright &copy; 2017-2099 <a href="<%=basePath%>news/content">朱利尔</a>.</strong> All rights reserved.
      </footer>

      <!-- Control Sidebar -->
      <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <div class="pad">
          This is an example of the control sidebar.
        </div>
      </aside><!-- /.control-sidebar -->
      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>

    </div><!-- ./wrapper -->

    <!-- jQuery 2.2.3 -->
    <script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="../plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="../dist/js/app.min.js"></script>
    <!-- SlimScroll 1.3.0 -->
    <script src="../plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
    <script src="docs.js"></script>
  </body>
</html>
