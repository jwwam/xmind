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
  <title>AttentionLog</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="source/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="source/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="source/dist/css/skins/_all-skins.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<script language="JavaScript">   
	document.onreadystatechange=function() {
	    if(document.readyState=="complete"){
	    	getSyslog();
	    	//从cookie中获取上次选中，加载到页面
	    	var selectedIndex = getCookie("select_1");
	        var selectedIndexmin = getCookie("min");
	        //var ssss = getCookie("values");
	        if(selectedIndex != null) {
	            document.getElementById("select_1").selectedIndex = selectedIndex;
	        }
	        document.getElementById("min").selectedIndex = selectedIndexmin;
	    }  
	}
	
	function getSyslog(){ 
		$.ajax({  
	         url: 'Syslog/list' ,  
	         type: 'POST',  
	         async: false,  
	         cache: false,  
	         contentType: false,  
	         processData: false,  
	         success: function (returndata) {  
	        	 setHtml(returndata);//加载数据

	        	 setTimeout('myrefresh()',freshtime());//设置定时刷新数据,1000=1s，
	             //window.location.reload();
	         },  
	         error: function (returndata) {  
	             alert("获取数据失败");  
	         }  
	    });
	}   
	
	//获取并设置刷新时间
	function freshtime(){
		var myselect=document.getElementById("test");
		myselect.onchange = function(){
		    localStorage.value = this.value;
		    localStorage.index = this.selectedIndex;
		    // console.log( localStorage.index +";"+ localStorage.value );
		    myrefresh();//选中后立即刷新页面使之生效
		}
		myselect.options[ localStorage.index ].selected = true;
		var index = myselect.selectedIndex;
		var time = myselect.options[index].value;
		return time;
	}

  //获取并设置记录时间范围
	function hourset(){
    var ssss = getCookie("values");
    if(ssss != null) {
        return ssss;
    }
	}
	
	//加载数据
    function setHtml(data){
		var str = "";
		var str2 = "";
		var list1 = new Array();
   	 	var data2 = eval(data.syslogList);
   	 	 /* data2.sort(function(a,b){
           	 	return a.level-b.level;
             });  */
   	 	
   	 	for(var i in data2){
   		 /*alert(data2[i].id);*/
   		 if(deleterow(data2[i].receivedat,hourset())){//hourset()排除n小时外的数据
   			 //list1[i] = data2[i];
	   		 var level = getLevel(data2[i].level);//等级区分
	   		 var nyr = datetime(data2[i].receivedat,1);//获取年月日
	   		 var sfm = datetime(data2[i].receivedat,2);//获取时分秒
	   		 var style = minutes(data2[i].receivedat,getNminutes());//区分背景色样式
	   		 
	   		 //如果每条都需要显示年月日则删除此判断条件，放出str注释语句替换same
	   		 if(parseInt(i)>0){//判断下一条数据是否和上一条同日
	   			var same = sameDay(data2[i].receivedat,data2[parseInt(i)-1].receivedat,nyr);
	   		 }else{
	   			var same = "<li class='time-label'><span class='bg-red'>"+nyr+"</span></li>";
	   		 }
	   		 //str = str + "<li class='time-label'><span class='bg-red'>"+nyr+"</span></li>"+
	   		 if(style==1){
	   			 list1.push(data2[i]);
	   			 /* style = "style='background-color:yellow'";
	   			 str2 = str2 + same +
		   		 "<li>"+ level +"<div class='timeline-item'><span class='time'><i class='fa fa-clock-o'></i>"+sfm+"</span>"
				 +"<h3 class='timeline-header'"+style+"><a href='#'>"+data2[i].fromhost+"</a>"+
				 "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data2[i].title+"</h3>"+
				 "<div class='timeline-body'"+style+">"+data2[i].message+"</div></div></li>"; */
	   			
	   		 }else{
	   			style="style='background-color:white'";
   				str = str + same +
		   		 "<li>"+ level +"<div class='timeline-item'><span class='time'><i class='fa fa-clock-o'></i>"+sfm+"</span>"
				 +"<h3 class='timeline-header'"+style+"><a href='#'>"+data2[i].fromhost+"</a>"+
				 "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data2[i].title+"</h3>"+
				 "<div class='timeline-body'"+style+">"+data2[i].message+"</div></div></li>";
	   			document.getElementById('timeline').innerHTML = str;
	   		 }
	   		 
				 /* str = str + same +
		   		 "<li>"+ level +"<div class='timeline-item'><span class='time'><i class='fa fa-clock-o'></i>"+sfm+"</span>"
					 +"<h3 class='timeline-header'"+style+"><a href='#'>"+"host："+data2[i].fromhost+"</a>"+
					 "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;title："+data2[i].title+"</h3>"+
					 "<div class='timeline-body'"+style+">"+"message："+data2[i].message+"</div></div></li>"; */
   		 }/* else{
   			str = "<li><div class='timeline-item'><div class='timeline-body'>sorry，当前状态下无更多数据</div></div></li>";
   			document.getElementById('timeline').innerHTML = str;
   		 } */
   	 	}
             
   	    list1.sort(function(a,b){
   	 	return a.level-b.level;
     	});
   	    
   	 for(var i in list1){
   		 /*alert(data2[i].id);*/
   		 if(deleterow(list1[i].receivedat,hourset())){//hourset()排除n小时外的数据
   			 //list1[i] = list1[i];
	   		 var level = getLevel(list1[i].level);//等级区分
	   		 var nyr = datetime(list1[i].receivedat,1);//获取年月日
	   		 var sfm = datetime(list1[i].receivedat,2);//获取时分秒
	   		 var style = minutes(list1[i].receivedat,getNminutes());//区分背景色样式
	   		 
	   		 //如果每条都需要显示年月日则删除此判断条件，放出str注释语句替换same
	   		 if(parseInt(i)>0){//判断下一条数据是否和上一条同日
	   			var same = sameDay(list1[i].receivedat,list1[parseInt(i)-1].receivedat,nyr);
	   		 }else{
	   			var same = "<li class='time-label'><span class='bg-red'>"+nyr+"</span></li>";
	   		 }
	   		 //str = str + "<li class='time-label'><span class='bg-red'>"+nyr+"</span></li>"+
	   		 if(style==1){
	   			 style = "style='background-color:yellow'";
	   			 str2 = str2 + same +
		   		 "<li>"+ level +"<div class='timeline-item'><span class='time'><i class='fa fa-clock-o'></i>"+sfm+"</span>"
				 +"<h3 class='timeline-header'"+style+"><a href='#'>"+list1[i].fromhost+"</a>"+
				 "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+list1[i].title+"</h3>"+
				 "<div class='timeline-body'"+style+">"+list1[i].message+"</div></div></li>";
	   			 document.getElementById('timeline2').innerHTML = str2;
	   		 	}
   		 	}
   	 	}
   	    
	}

  //获取n分钟内新数据
  function getNminutes(){
    var n = getCookie("valmin");
    if(n != null) {
        return n;
    }
  }

	//n分钟内外颜色区分
	function minutes(data, n){
		var style = "";
		var date1= data;  //开始时间  
	    var date2 = new Date();    //结束时间  
	    var date3 = date2.getTime() - new Date(date1).getTime();   //时间差的毫秒数        
	    //计算出相差天数  
	    var days=Math.floor(date3/(24*3600*1000));  
	    //计算出小时数  
	    var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数  
	    var hours=Math.floor(leave1/(3600*1000));  
	    //计算相差分钟数  
	    var leave2=leave1%(3600*1000);       //计算小时数后剩余的毫秒数  
	    var minutes=Math.floor(leave2/(60*1000));  
	    //计算相差秒数  
	    var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数  
	    var seconds=Math.round(leave3/1000);  
	    //alert(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
	    minutes2 = days*24*60+hours*60+minutes;
	    if(minutes2 < n){
	    	return 1;
	    	//style = "style='background-color:yellow'";
	    }else{
	    	return 2;
	    	//style = "style='background-color:white'";
	    }
	    return style;
	}
	
	//删除超过n小时的数据
	function deleterow(data,n){
		var m = n;
		var date1= data;  //开始时间  
	    var date2 = new Date();    //结束时间  
	    var date3 = date2.getTime() - new Date(date1).getTime();   //时间差的毫秒数        
	    var s = date3/1000;
	    /* //计算出相差天数  
	    var days=Math.floor(date3/(24*3600*1000));  
	    //计算出小时数  
	    var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数  
	    var hours=Math.floor(leave1/(3600*1000));  
	    //计算相差分钟数  
	    var leave2=leave1%(3600*1000);       //计算小时数后剩余的毫秒数  
	    var minutes=Math.floor(leave2/(60*1000));  
	    //计算相差秒数  
	    var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数  
	    var seconds=Math.round(leave3/1000); */
	    //minutes2 = days*24*60+hours*60+minutes;
	    minutes2 = s/60;
		if(minutes2<m&&minutes2>=0){
			return true;
		}else{
			return false;
		}
	}
	
	//格式化时间nyr-1-年月日，sfm-2-时分秒
	function datetime(data,key){
		var d=new Date(data);
		//var year=d.getYear();
		var y = d.getFullYear();
		var month=d.getMonth()+1;
		var date=d.getDate();
		var hour=d.getHours();
		var minute=d.getMinutes();
		var second=d.getSeconds();
		if(key==1){
			return y+"-"+month+"-"+date;
		}else if(key==2){
			return hour+":"+minute+":"+second; 
		}
		//return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
	}    
	
    //分配等级图标
    function getLevel(data){
    	var level = "";
    	if(data==0||data==1||data==2){
    		level="<i class='fa fa-remove bg-red'></i>";
    		return level;
    	}else if(data==3||data==4){
    		level="<i class='fa fa-exclamation bg-orange-active'></i>";
    		return level;
    	}else if(data==5||data==6||data==7){
    		level="<i class='fa fa-envelope bg-blue'></i>";
    		return level;
    	}
    }
    //是否同一天
    function sameDay(data,data2,data3){
    	var d=new Date(data);
    	var d2=new Date(data2);
    	
    	var y = d.getFullYear();
		var month=d.getMonth()+1;
		var date=d.getDate();
		var d1nyr= y+"-"+month+"-"+date;
		
		var y2 = d2.getFullYear();
		var month2=d2.getMonth()+1;
		var date2=d2.getDate();
		var d2nyr= y2+"-"+month2+"-"+date2;
		
		var d1nyrs=new Date(d1nyr);
    	var d2nyrs=new Date(d2nyr);
    	
    	if(d1nyrs - d2nyrs == 0){
    		return "";
    		//return true;
    	}else{
    		return "<li class='time-label'><span class='bg-red'>"+data3+"</span></li>";	
    		//return false;
    	}
    }
    
    function myrefresh()
    {
    	window.location.reload();
    	$(".min").val(getCookie("valmin")); 
    	$(".select_1").val(getCookie("values")); 
    }

    /*function show_sub(v){     
        alert(v);     
    } */ 
</script> 
<script> 
    //获取并设置时间范围（放在cookie中），防止刷新失去选中
    function setCookie(name, value, data) {
        var exp = new Date();
        exp.setTime(exp.getTime() + 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        if (name=='min') {
          document.cookie = "valmin=" + escape(data) + ";expires=" + exp.toGMTString();
        }else{
          document.cookie = "values=" + escape(data) + ";expires=" + exp.toGMTString();
        }
        myrefresh();
    }

    //获取选中时间（从cookie中）
    function getCookie(name)
    {
        var regExp = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        var arr = document.cookie.match(regExp);
        if (arr == null) {
            return null;
        }
        return unescape(arr[2]);
    }
</script>  

<script type="text/javascript">
    //从cookie中获取上次选中，加载到页面//chrome失效
    var selectedIndex = getCookie("select_1");
    var selectedIndexmin = getCookie("min");
    //var ssss = getCookie("values");
    if(selectedIndex != null) {
        document.getElementById("select_1").selectedIndex = selectedIndex;
    }
    document.getElementById("min").selectedIndex = selectedIndexmin;
</script>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- header -->
  <!-- aside -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper" style="margin: 0 auto;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        AttentionLog
        <small>syslog</small>
      </h1>
      
    </section>

    <!-- Main content -->
    <section class="content">

      <!-- row -->
      <div class="row">
      <div style="float:right;margin-right:40px;">
        <label>设置</label>
            <select id="min" name="" onchange="setCookie('min',this.selectedIndex,this.value)"> 
              <option value="1">1分钟</option> 
              <option value="5">5分钟</option> 
              <option value="10">10分钟</option>
              <option value="30">30分钟</option>  
              <!-- <option value="60">1小时</option> 
              <option value="2040">一天</option>
              <option value="14280">一周</option> -->  
          </select>
        <label>为新数据&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>   

				<label>获取</label>
			      <select id="select_1" name="" onchange="setCookie('select_1',this.selectedIndex,this.value)"> 
  			      <!-- <option value="1">1分钟</option> 
  			      <option value="10">10分钟</option>
				  <option value="30">30分钟</option> -->  
				  <option value="60">1小时</option>
				  <option value="720">12小时</option>
				  <option value="2040">24小时</option> 
  					  <!-- <option value="14280">一周</option> -->
					  </select>
        <label>内的记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>     

        <label>隔</label>
		      <select id="test" name="" >
          <!-- <select id="test" name="" onchange="show_sub(this.options[this.options.selectedIndex].value)"> -->
  		      <option value="6000">6秒</option>
  				  <option value="30000">30秒</option>  
  				  <option value="60000">1分钟</option>
  				  <option value="300000">5分钟</option>  
  				  <option value="1800000">30分钟</option>
				</select>
        <label>刷新一次</label>
			</div>
        <div class="col-md-12">
          <!-- The time line -->
          <ul class="timeline" id="timeline2">
            <!-- timeline time label -->
            
          </ul>
          <ul class="timeline" id="timeline">
            <!-- timeline time label -->
            
          </ul>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- <footer class="main-footer">
  </footer> -->

  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="source/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="source/bootstrap/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="source/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="source/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="source/dist/js/demo.js"></script>
</body>
</html>
