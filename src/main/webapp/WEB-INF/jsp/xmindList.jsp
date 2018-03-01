<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.common.controller.VisitorCount"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	//訪問量
    String count = VisitorCount.readCount("C:/visitorCount.txt");
	String todayCount = VisitorCount.readTodayCount("C:/visitorCount.txt");
	if (session.getAttribute("visit") == null) {
		session.setAttribute("visit", "y");//将未访问设置为访问
		session.setMaxInactiveInterval(60 * 60 * 24);//设置最大时效    单位是秒

		int count1 = Integer.parseInt(count);
		int count2 = Integer.parseInt(todayCount);
		count1 = count1 + 1;
		count2 = count2 + 1;
		count = String.valueOf(count1).toString();
		todayCount = String.valueOf(count2).toString();
		VisitorCount.writeCount("C:/visitorCount.txt", count, todayCount);
	}
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
  <link rel="stylesheet" href="source/plugins/select2/select2.min.css">
  <script src="source/plugins/select2/select2.full.min.js"></script>
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
	          <a href="javascript:void(0);"><i class="fa fa-circle text-success"></i>让思维清晰可见。</a>
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
      
      	<li class="treeview active">
          <a href="javascript:void(0);">
            <i class="fa fa-table"></i> <span>模板列表</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="<%=basePath%>news/content"><i class="fa fa-circle-o"></i>所有模板</a></li>
            <li><a href="javascript:;" onclick="return confirm('暂未开放');"><i class="fa fa-circle-o"></i>模板分类</a></li>
          </ul>
        </li>
        <li class="treeview">
          <a href="javascript:void(0);">
            <i class="fa fa-dashboard"></i> <span>关于我</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=basePath%>news/aboutMe"><i class="fa fa-circle-o"></i>我的初衷</a></li>
            <li><a href="<%=basePath%>timeline/timeList"><i class="fa fa-circle-o"></i>版本历史</a></li>
          </ul>
        </li>
        
      </ul>
    </section>
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>模板列表<small>数据全部来自<a href="https://www.xmind.net/share" target="_blank">官网</a></small></h1>
      <ol class="breadcrumb">
        <li><a href="javascript:void(0);"><i class="fa fa-dashboard"></i>主页</a></li>
        <li><a href="javascript:void(0);">列表</a></li>
        <li class="active">模板</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
    
    	<div class="row">
        <div class="col-xs-12">
          <div class="box">
          
          
            <div class="box-header">
              <h3 class="box-title">
              <input type="button" value="按热度" class="btn btn-warning" style="line-height: 1.0;" onclick="hot()"/>
			  </h3>

              <div class="box-tools">
              	<form id="" name="" action="news/find" method="get" >
	                <div class="input-group input-group-sm" style="width: 180px;float:right;">
	                  <input type="text" name="key" class="form-control pull-right" placeholder="查询" style="height:34px;">
	                  <div class="input-group-btn">
	                    <button type="submit" class="btn btn-default" style="height:34px;"><i class="fa fa-search"></i></button>
	                  </div>
	                </div>
                
                <div class="input-group input-group-sm" style="width: 150px;float:right;margin-right:20px;">
	                <div style='display: inline-block'>
               			<input type='hidden' id='language' name='language' value='${language}'/>
               			<select id='lang' name='lang' class='form-control select2' style='width: 150px;color:#999;'>
               			
	               			<%-- <c:if test='${not empty language}'>
					        	<option value='${lang}'>${language}</option>
					        </c:if>
					        <c:if test='${empty language}'>
					        	<option value='-1'>选择语言</option>
					        </c:if> --%>
					        
                        </select>
	               </div>
                           <script>
                           	var langs = $('#lang');
                           	var languages = "${language}";
                           	$.getJSON('${ROOT}/xmind/news/getLang', function(data) {
                           		//alert(data);
                           		//console.log(data);
                           		//console.log("language==="+$('#language').val());
                           		langs.empty();
                           		langs.append(" <option value = '-1'>选择语言</option> ");
                               	$.each(data.langList, function(i, item) {
                               		if(i==0){
                               			var lname = 'zh';
                               		}else if(i==1){
                               			var lname = 'en';
                               		}else if(i==2){
                               			var lname = 'es';
                               		}else if(i==3){
                               			var lname = 'de';
                               		}else if(i==4){
                               			var lname = 'fr';
                               		}else{
                               			var lname = 'ja';
                               		}
                                	langs.append("<option value = " + lname + "> " + item + "</option>");
                               	});
                               	if(languages==""){
                               		$('#lang').val("-1");
                               	}else{
                               		$('#lang').val(languages);
                               	}
                               	
                               	//console.log("languages="+languages);
                               });
                           	
                           	$('#lang').on('change', function() {
                                   var text=$('#lang option:selected').text();
                                   if(text){
                                   	$('#language').val(text);
                                   	window.location.href="news/lang?lang="+lang.value+"&hot=0";
                                   }
                               });
                           </script>
              </div>
              </form>
            </div>
            </div>
            
            <div class="box-body table-responsive no-padding">
            
            	<div class="listlo" style="margin-top:15px;margin-left:10px;">
	            	<div class="row">
					      
				    </div>
            		<form id="fmSearch" name="fmSearch" method="get" action="" class="form-inline">
                		<!-- 标题:<input id="remark" name="remark" value="" class="form-control" style="width:130px;height: 28px;"/>
							<input type="submit" value="查询" class="btn btn-warning" style="line-height: 1.0;"/> -->
                			<span style="font-size:18px;">数据最后更新时间为：</span><span style="color:green;font-size:18px;"><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                			
            		</form>
        		</div>
        		
              <table class="table table-hover" style="width:100%;margin-top:15px;">
                <tr>
                  <th style="text-align:center;">名称</th>
                  <th style="text-align:center;">发布者</th>
                  <th style="text-align:center;">发布时间(格林尼治)</th>
                  <th style="text-align:center;">预览次数</th>
                  <th style="text-align:center;">下载次数</th>
                  <th style="text-align:center;">热度</th>
                  <th style="text-align:center;">缩略图</th>
                  <th style="text-align:center;">操作</th>
                </tr>
                <c:forEach var="xmind" items="${xmindList.list}" varStatus="idx">
	                <tr>
	                  <td width="7%">${xmind.topic }</td>
	                  <td width="5%" align="center">
	                  	<a href="javascript:;" title="查看详情" onclick="javascript:author('${xmind.username }')">${xmind.username }</a>
	                  </td>
	                  <td width="12%" align="center"><fmt:formatDate value="${xmind.created }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                  <td width="7%" align="center"><span class="label label-success">${xmind.views }</span></td>
	                  <td width="7%" align="center">
	           			<span class="pull-center badge bg-red">${xmind.downloads }</span>
	                  </td>
	                  <td width="8%" align="center">
	                    <div class="progress progress-xs">
	                      <div class="progress-bar progress-bar-danger" style="width: ${(xmind.downloads/1500)*100 }%"></div>
	                    </div>
	                  </td>
	                  <td width="8%" align="center">
	                  	<img alt="" src="${xmind.thumbnailurl}" width="100%" height="30px;">
	                  </td>
	                  <td width="10%" align="center">
	                        <!-- <a href="javascript:modify('')">下载</a> -->
	                        <input type="button" value="预览" class="btn btn-warning" style="line-height: 1.0;" onclick="javascript:views('${xmind.previewurl }')"/>
	                        <input type="button" value="下载" class="btn btn-danger" style="line-height: 1.0;" onclick="javascript:download('${xmind.idname }')"/>
	                  </td>
	                </tr>
	            </c:forEach>
              </table>
              <div style="float:left;margin-top: 5px;">
				每页<i class="blue">${xmindList.pageSize}</i>条记录&nbsp;共<i class="blue">${xmindList.total}</i>条记录，当前显示第&nbsp;<i class="blue">${xmindList.pageNo}&nbsp;</i>页
		  </div>
		  <div class="pagelist" style="float:right;margin-top: 5px;">
			<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>news/content?pageNo=1&lang=${language}'">首页</a>
			<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>news/content?pageNo=${xmindList.previou}&lang=${language}'">上一页</a>
			<a class="btn-inline" style="border-bottom: none;display: ;" >当前显示第&nbsp;<i class="blue">${xmindList.pageNo}&nbsp;</i>页</a>
			<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>news/content?pageNo=${xmindList.next}&lang=${language}'">下一页</a>
			<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>news/content?pageNo=${xmindList.last}&lang=${language}'">尾页</a>
		  </div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
      </div>
      
      <div class='modal fade' id='addresMapNodeTypeModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
            <div class='modal-dialog' id='addresMapNodeTypeModalWidth' style=''>
                <div class='modal-content' style="">
                    <div class='modal-header'>
                        <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>
                            &times;
                        </button>
                        <h4 class='modal-title' id='myModalLabel'>预览</h4>
                    </div>
                    <div class='modal-body' id='addresMapNodeTypeModalHeight' style=''>
                        <iframe src='' id='formFrame' name='formFrame' style='border-width: 0px;width: 100%;height: 100%;'>
                        	<img alt="" src="${xmind.thumbnailurl }">
                        </iframe>
                    </div>
                    <div class='modal-footer'>
                        <button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>
                        <!-- <button type='button' class='btn btn-primary' onclick='submitData()'>提交</button> -->
                        <span id='tip'> </span>
                    </div>
                </div>
            </div>
        </div>
        
        <div class='modal fade' id='addresMapNodeTypeModalAuthor' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
            <div class='modal-dialog' id='addresMapNodeTypeModalWidthAuthor' style=''>
                <div class='modal-content' style="">
                    <div class='modal-header'>
                        <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>
                            &times;
                        </button>
                        <h4 class='modal-title' id='myModalLabel'>发布者信息</h4>
                    </div>
                    <div class='modal-body' id='addresMapNodeTypeModalHeightAuthor' style=''>
                        <iframe src='' id='formFrameAuthor' name='formFrameAuthor' style='border-width: 0px;width: 100%;height: 100%;'></iframe>
                    </div>
                    <div class='modal-footer'>
                        <button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>
                        <!-- <button type='button' class='btn btn-primary' onclick='submitData()'>提交</button> -->
                        <span id='tip'> </span>
                    </div>
                </div>
            </div>
        </div>
      
      
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b>0.1
    </div>
    <strong>Copyright &copy; 2017-2099 <a href="<%=basePath%>news/content">朱利尔</a>.</strong> All rights reserved.今日访问量<%=todayCount %>,历史访问总量<%=count %>
  </footer>

  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<script type="text/javascript">
function views(url) {
    var viewurl = "<%=basePath%>news/views";
    var realurl = viewurl + "?viewsurl=" + url;
    $('#formFrame', window.document.body).attr('src', realurl);
    
    var mainHeight =  screen.height - 320;
    $("#addresMapNodeTypeModalHeight").height(mainHeight);
    var mainWidth = $(document.body).width() - 100;
    $("#addresMapNodeTypeModalWidth").width(mainWidth);
    
    $('#addresMapNodeTypeModal').modal('show');
    
}

function download(idname){
    $.ajax({  
         url: 'news/getRealDownLoadUrl' ,  
         type: 'POST',  
         data: 'idname='+idname,
         async: false,  
         cache: false,  
         processData: false,  
         datatype: 'text',
         contentType:'application/x-www-form-urlencoded',
         success: function (returndata) {
             window.location.href=returndata.ralurl;
         },  
         error: function (returndata) {  
             alert("下载失败");
         }  
    });
}

function hot(){
	window.location.href='<%=basePath%>news/hotcontent';
}

function author(name) {
    var authorurl = "<%=basePath%>author/getAuthorInfo";
    var realurl = authorurl + "?authorName=" + name;
    $('#formFrameAuthor', window.document.body).attr('src', realurl);
    
    var mainHeight =  screen.height - 420;
    $("#addresMapNodeTypeModalHeightAuthor").height(mainHeight);
    var mainWidth = $(document.body).width() - 450;
    $("#addresMapNodeTypeModalWidthAuthor").width(mainWidth);
    
    $('#addresMapNodeTypeModalAuthor').modal('show');
    
}


</script>
</body>
</html>