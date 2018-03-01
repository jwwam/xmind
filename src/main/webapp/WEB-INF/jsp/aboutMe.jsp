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
            <i class="fa fa-dashboard"></i> <span>关于我</span>
              <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="<%=basePath%>news/aboutMe"><i class="fa fa-circle-o"></i>我的初衷</a></li>
            <li><a href="<%=basePath%>timeline/timeList"><i class="fa fa-circle-o"></i>版本历史</a></li>
          </ul>
        </li>
        
      </ul>
    </section>
  </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
          <h1>
            XMind模板之家
            <small>Version 0.1</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="javascript:void(0);"><i class="fa fa-dashboard"></i>首页</a></li>
            <li class="active">关于</li>
          </ol>
        </div>

        <!-- Main content -->
        <div class="content body">

			<section id="introduction">
			  <h2 class="page-header"><a href="#introduction">为什么？</a></h2>
			  <p class="lead">
			    <b>嗨，</b>${why}
			  </p>
			</section><!-- /#introduction -->
			
			
			<!-- ============================================================= -->
			
			<section id="download">
			  <h2 class="page-header"><a href="#download">接下来：</a></h2>
			  <p class="lead">
			    ${last}
			  </p>
			  
			  <div class="row">
			    <div class="col-sm-6" style="">
			      <img style="" alt=""  src="source/images/shareData.png">
			    </div>
			  </div>
			  
			  <div class="row" style="margin-top:30px;">
			    <div class="col-sm-6">
			      <div class="box box-primary">
			        <div class="box-header with-border">
			          <h3 class="box-title">联系我</h3>
			        </div><!-- /.box-header -->
			        <div class="box-body">
			          <p>${how}</p>
			        </div>
			      </div>
			    </div>
			  </div>
			  
			</section>


<!-- ============================================================= -->

<section id="download">
			  <h2 class="page-header"><a href="#download">说明：</a></h2>
			  <p class="lead">
			    ${path}
			  </p>
			</section>

		</div>
      </div>

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
