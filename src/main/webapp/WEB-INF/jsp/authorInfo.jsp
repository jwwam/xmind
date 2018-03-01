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
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>发布者信息</title>
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
	<div class="box-body table-responsive no-padding">
           	<div class="listlo" style="margin-top:15px;margin-left:10px;">
	           	<c:forEach var="xmind" items="${authorXmindList.list}" varStatus="idx">
	                <c:if test="${idx.index == 0}">
		                <table>
			                <tr>
			                	<td style="padding:10px;" rowspan="3">
			                		<img alt="" src="${xmind.gravatar }">
			                	</td>
			                	<td style="padding:10px;">用户名：&nbsp;&nbsp;&nbsp;${xmind.username }</td>
			                	<td></td>
			                </tr>
			                <tr>
			                	<td style="padding:10px;">FirstName:&nbsp;&nbsp;&nbsp;${xmind.firstname }</td>
			                	<td></td>
			                </tr>
			                <tr>
			                	<td style="padding:10px;">LastName:&nbsp;&nbsp;&nbsp;${xmind.lastname }</td>
			                	<td>
			                		<span style="font-size:18px;">他共发布了<span style="color:red;font-size:24px;">${fn:length(authorXmindList.list)} </span>个模板</span>
			                	</td>
			                </tr>
		                </table>
					</c:if>
	            </c:forEach>
       		</div>
            <div class="col-md-12">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#list" data-toggle="tab">发布列表</a></li>
                        <li><a href="#charts" data-toggle="tab">趋势图</a></li>
                    </ul>
                    <div class="tab-content">
	                    <div class="active tab-pane" id="list">
				            <div class="post">
				                <div class="box-body">
				                    <div class="row">
				                        <div class="col-md-12">
				                            <div class="box-body">
				                                <div class="row">
				                                	<table class="table table-hover" style="width:100%;margin-top:15px;">
										              <tr>
										                <th style="text-align:center;">名称</th>
										                <th style="text-align:center;">发布时间(格林尼治)</th>
										                <th style="text-align:center;">预览次数</th>
										                <th style="text-align:center;">下载次数</th>
										                <th style="text-align:center;">热度</th>
										                <th style="text-align:center;">缩略图</th>
										                <th style="text-align:center;">操作</th>
										              </tr>
										              <c:forEach var="xmind" items="${authorXmindList.list}" varStatus="idx">
										               <tr>
										                 <td width="7%">${xmind.topic }</td>
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
														每页<i class="blue">${authorXmindList.pageSize}</i>条记录&nbsp;共<i class="blue">${authorXmindList.total}</i>条记录，当前显示第&nbsp;<i class="blue">${authorXmindList.pageNo}&nbsp;</i>页
												   </div>
												   <div class="pagelist" style="float:right;margin-top: 5px;">
														<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>author/getAuthorInfo?pageNo=1&lang=${language}'">首页</a>
														<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>author/getAuthorInfo?pageNo=${authorXmindList.previou}&lang=${language}'">上一页</a>
														<a class="btn-inline" style="border-bottom: none;display: ;" >当前显示第&nbsp;<i class="blue">${authorXmindList.pageNo}&nbsp;</i>页</a>
														<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>author/getAuthorInfo?pageNo=${authorXmindList.next}&lang=${language}'">下一页</a>
														<a class="btn-inline" style="border-bottom: none;display: ;" href="javascript:window.location.href='<%=basePath%>author/getAuthorInfo?pageNo=${authorXmindList.last}&lang=${language}'">尾页</a>
												   </div>
				                                </div>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </div>
			            </div>
			            
	                    <div class="tab-pane" id="charts">
				            <div class="post">
				                <div class="box-body">
				                    <div class="row">
				                        <div class="col-md-12">
				                            <div class="box-body">
				                                <div class="row">
					                                <script src="source/charts/echarts.js"></script>
							                        <script src="source/charts/highcharts.js"></script>
							                        <script src="source/charts/exporting.js"></script>
							                        <script src="source/charts/highcharts-zh_CN.js"></script>
							                        <div id="main" style="width: 1000px;height:450px;margin:0 auto;/*margin-top: 50px;*/"></div>
						                        		<script type="text/javascript">
													        // 基于准备好的dom，初始化echarts实例
													        var myChart = echarts.init(document.getElementById('main'));
													        /* var url = '${ROOT}/resCenter/resMonitorValueMin/getLastValueByItemIdToJson.do';
														    $.ajax(
														    	{
														    	type: "POST",
														    	url: url,
														    	async : false,
														    	data: dataForm,
														    	success: function(j){
					                                                if (j.code == 1) {
					                                                    var rs = j.result;
					                                                    for (var i = 0; i < rs.length; i++) {
					                                                        var realTime = rs[i].createTime;
					                                                        myChart.setOption(option_inter);
					                                                    }
					                                                }
					                                            },
					                                            dataType: 'json'
					                                        }); */
													        
													        // 指定图表的配置项和数据
													        
													        /* var option = {
													            title: {
													                text: 'ECharts 入门示例'
													            },
													            tooltip: {},
													            legend: {
													                data:['销量']
													            },
													            xAxis: {
													                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
													            },
													            yAxis: {},
													            series: [{
													                name: '销量',
													                type: 'bar',
													                data: [5, 20, 36, 10, 10, 20]
													            }]
													        };
													
													        // 使用刚指定的配置项和数据显示图表。
													        myChart.setOption(option); */
													        
													        
												    </script>
				                                </div>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </div>
			            </div>
			            
                    </div>
                </div>
            </div>
	</div>
<div style="height:50px;"></div>

</body>
</html>
