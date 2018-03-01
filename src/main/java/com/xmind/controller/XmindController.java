package com.xmind.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xmind.entity.Thumbnails;
import com.xmind.entity.Xmind;
import com.xmind.service.ThumbnailsService;
import com.xmind.service.XmindService;
import com.xmind.utils.DateUtils;
import com.xmind.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/news")
public class XmindController {
	@Resource    
    private XmindService xmindService; 
	@Resource
	private ThumbnailsService thumbnailsService;
	
	public int count = 0;
	
	@RequestMapping("/getContent")
	public void getContent(){
		
		String url = "http://www.xmind.net/share/api/maps";
		System.out.println("初始请求地址为："+url);
		//String param = "lang=ww&start=383&end=10000";
		String param = "lang=ww";
		//发送请求，获取数据
		String result = sendGet(url, param);
		System.out.println("数据组装完毕！开始转换数据格式String->Json...");
		//数据处理，写入对象
		getXmind(result);
		
	}

	
	/**
	 * 按时间
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/content")
	public String releasePage(HttpServletRequest request, HttpServletResponse response, Model model){
		Date lastTime = xmindService.getLast().getFormatedTime();
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 25;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			} 
			PageBean<Xmind> xmindList = xmindService.getPageBean(pageNo, pageSize);
			model.addAttribute("xmindList", xmindList);
			model.addAttribute("createTime", lastTime);
		} catch (Exception e) {
		}
		return "xmindList";
	}
	
	@RequestMapping("/content-F")
	public String releasePageF(HttpServletRequest request, HttpServletResponse response, Model model){
		Date lastTime = xmindService.getLast().getFormatedTime();
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 100;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			} 
			PageBean<Xmind> xmindList = xmindService.getPageBean(pageNo, pageSize);
			model.addAttribute("xmindList", xmindList);
			model.addAttribute("createTime", lastTime);
		} catch (Exception e) {
		}
		return "xmindList-F";
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/find")
	public String find(@RequestParam("key") String key, HttpServletRequest request, HttpServletResponse response, Model model){
		Date lastTime = xmindService.getLast().getFormatedTime();
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 50;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			} 
			PageBean<Xmind> xmindList = xmindService.getPageBeanByKey(key, pageNo, pageSize);
			model.addAttribute("xmindList", xmindList);
			model.addAttribute("createTime", lastTime);
			model.addAttribute("key", key);
			model.addAttribute("count", xmindList.getTotal());
		} catch (Exception e) {
		}
		return "xmindListKey";
	}
	
	/**
	 * 按热度
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/hotcontent")
	public String hotcontent(HttpServletRequest request, HttpServletResponse response, Model model){
		Date lastTime = xmindService.getLast().getCreated();
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 50;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			} 
			PageBean<Xmind> xmindList = xmindService.getPageBeanByHot(pageNo, pageSize);
			model.addAttribute("xmindList", xmindList);
			model.addAttribute("createTime", lastTime);
		} catch (Exception e) {
		}
		return "xmindListHot";
	}
	
	/**
	 * 按语言
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/lang")
	public String lang(@RequestParam("lang") String lang,@RequestParam("hot") String hot, HttpServletRequest request, HttpServletResponse response, Model model){
		Date lastTime = xmindService.getLast().getFormatedTime();
		try {
			PageBean<Xmind> xmindList;
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 50;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			}
			if("-1".equals(lang)){
				if("1".equals(hot)) {
					xmindList = xmindService.getPageBean(pageNo, pageSize);
				}else {
					xmindList = xmindService.getPageBean(pageNo, pageSize);
				}
			}else{
				if("1".equals(hot)) {
					xmindList = xmindService.getPageBeanByLang(lang, pageNo, pageSize);
				}else {
					xmindList = xmindService.getPageBeanByLang(lang, pageNo, pageSize);
				}
			}
			model.addAttribute("xmindList", xmindList);
			model.addAttribute("language", lang);
			model.addAttribute("createTime", lastTime);
		} catch (Exception e) {
		}
		if("1".equals(hot)){
			return "xmindListHot";
		}else {
			return "xmindList";
		}
		
	}
	
	/**
	 * 预览
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/views")
	public String views(HttpServletRequest request, HttpServletResponse response, Model model){
		String viewurl = request.getParameter("viewsurl");
		model.addAttribute("viewurl", viewurl);
		return "viewsForm";
	}
	
	@RequestMapping("/list")
	public @ResponseBody Object List(HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Xmind> xmindList = xmindService.getAllXmind();
		model.addAttribute("xmindList", xmindList);
		map.put("message", "调取Ajax成功");
		map.put("xmindList", xmindList);
		return JSON.toJSON(map);
	}
	
	@RequestMapping("/getRealDownLoadUrl")
	public @ResponseBody Object getRealDownLoadUrl(@RequestParam("idname") String idnames, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		String ralurl = getDownloadurl(idnames);
		map.put("ralurl", ralurl);
		return JSON.toJSON(map);
	}
	
	@RequestMapping("/aboutMe")
	public String aboutMe(HttpServletRequest request, HttpServletResponse response, Model model){
		//String viewurl = request.getParameter("viewsurl");
		String why = "我叫朱利尔，首先我想说XMind真的太棒了！（重要的事就说一遍）...简单的说几句我为何做这个，其实很简单，官网的模板分享界面体验不太好，我想做一个更符合用户习惯、体验更好的，当然现在我只是重构了一个简单的界面，"
				+ "还会再改版（等我兄弟画好UI给我，maby年后了...），关于数据全部都是从官网获取的，"
				+ "官网一次提供了1000条数据，每次更新都会挤出最后的数据，现在我只是做了记录，保留每天被排除的，后面会想办法取到以前的所有数据，拭目以待！（2017/9更新：数据基本获取完毕，目前共9k+）";
		String path = "关于缩略图失效以及下载失败的问题，说明一下，由于模板都是存储在亚马逊云端，图片加载以及下载可能会遇到问题，所以建议挂VPN，体验更加流畅，后面会考虑本地化数据，总之要做的事情很多，慢慢来吧。";
		String how = "当然，如果你认为我的做法有什么不妥之处（侵权、我做的界面太丑、难用）请联系我的QQ：824247231，或者发送你的idea到我的个人邮箱：zhulier1124@gmail.com，电话就不告诉你们了，哼..."
				+ "我会参考，做出相应的修改。如果有愿意和我一起做这个事情的小伙伴，欢迎你联系我";
		String last = "接下来，我会不断更新功能、调整界面，由于是用下班时间做，肯定会很慢很慢很慢...但是我会一直坚持下去的！下面是我的规划，想做的东西比较多，完善ing~";
		model.addAttribute("why", why);
		model.addAttribute("how", how);
		model.addAttribute("last", last);
		model.addAttribute("path", path);
		return "aboutMe";
	}
	
	@RequestMapping("/timeLine")
	public String timeLine(HttpServletRequest request, HttpServletResponse response, Model model){
		//String viewurl = request.getParameter("viewsurl");
		String why = "我叫朱利尔（假的），首先我想说XMind真的太棒了！（重要的事就说一遍）...简单的说几句我为何做这个，其实很简单，官网的模板分享界面体验不太好，我想做一个更符合用户习惯、体验更好的，当然现在我只是重构了一个简单的界面，还会再改版，关于数据全部都是从官网获取的，"
				+ "官网提供了1000条数据，每次更新都会挤出最后的数据，现在我只是做了记录，保留每天被排除的，后面会想办法取到以前的所有数据，拭目以待！";
		String path = "关于缩略图失效以及下载失败的问题，说明一下，由于模板都是存储在亚马逊云端，图片加载以及下载可能会遇到问题，所以建议挂VPN，体验更加流畅，后面会考虑本地化数据，总之要做的事情很多，慢慢来吧。";
		String how = "当然，如果你认为我的做法有什么不妥之处（侵权、我做的界面太丑、难用）请联系我的QQ：824247231，或者发送你的idea到我的个人邮箱：zhulier1124@gmail.com，电话就不告诉你们了，哼..."
				+ "我会参考，做出相应的修改。如果有愿意和我一起做这个事情的小伙伴，欢迎你联系我";
		String last = "接下来，我会不断更新功能、调整界面，由于是利用下班时间做，肯定会很慢很慢很慢...但是我会一直坚持下去的！";
		model.addAttribute("why", why);
		model.addAttribute("how", how);
		model.addAttribute("last", last);
		model.addAttribute("path", path);
		return "timeLine";
	}
	
	@RequestMapping("/lists")
	public String Lists(HttpServletRequest request, HttpServletResponse response, Model model){
		List<Xmind> xmindList = xmindService.getAllXmind();
		model.addAttribute("xmindList", xmindList);
		return "list";
	}

	
	public String getXmind(String result){
		int i = 0;
		Xmind xmind = new Xmind();
		Thumbnails th =new Thumbnails();
		JSONObject json =JSONObject.fromObject(result);  
		System.out.println("转换数据格式完毕！开始写入数据...");
		StringBuffer sb = new StringBuffer();
		//获取所有导图对象
		JSONArray maps = (JSONArray) json.get("maps");
		//遍历所有导图
		Iterator<JSONObject> it = maps.iterator();
		while(it.hasNext()){
			//获取导图属性
			JSONObject ob = (JSONObject) it.next();
			xmind.setUserid(Long.parseLong(ob.get("userId").toString()));
			xmind.setTopic(ob.get("topic").toString());
			xmind.setFeatured(ob.get("featured").toString());
			xmind.setPreviewurl(ob.get("previewUrl").toString());
			xmind.setThumbnailurl(ob.get("thumbnailUrl").toString());
			xmind.setBackgroundColor(ob.get("background_color").toString());
			xmind.setIdname(ob.get("id").toString());
			xmind.setCxm(ob.get("cxm").toString());
			xmind.setDownloadable(ob.get("downloadable").toString());
			xmind.setCompleted(ob.get("completed").toString());
			xmind.setUsername(ob.get("userName").toString());
			xmind.setDescription(ob.get("description").toString());
			xmind.setViews(Integer.parseInt(ob.get("views").toString()));
			xmind.setDeleted(ob.get("deleted").toString());
			xmind.setDownloads(Integer.parseInt(ob.get("downloads").toString()));
			xmind.setAuth(ob.get("auth").toString());
			xmind.setProfilename(ob.get("profileName").toString());
			xmind.setLang(ob.get("lang").toString());
			xmind.setName(ob.get("name").toString());
			xmind.setFirstname(ob.get("firstName").toString());
			//获取创建时间
			String getCreateTime = ob.get("created").toString();//2017-07-08T05:49:04
			//修改时间
			String ct = getCreateTime.replace("T", " ");
			//格式化时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    try {
				Date createTime = formatter.parse(ct);
				xmind.setCreated(createTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			xmind.setLastname(ob.get("lastName").toString());
			xmind.setDaily(ob.get("daily").toString());
			//下面将原来的转换时间，暂时定义为入库时间
			//xmind.setFormatedTime(new Date());
			try {
				xmind.setFormatedTime(DateUtils.getNowDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			xmind.setGravatar(ob.get("gravatar").toString());
			
			//获取缩略图内容
			Iterator<JSONObject> Thum = ob.getJSONArray("thumbnails").iterator();
			while(Thum.hasNext()){
				JSONObject thum = (JSONObject) Thum.next();
				th.setThumbnailsUrl(thum.get("url").toString());
				th.setKeyName(thum.get("key_name").toString());
				th.setBucket(thum.get("bucket").toString());
				th.setMapId(Long.parseLong(thum.get("map_id").toString()));
				th.setSize(thum.get("size").toString());
				th.setIdname(xmind.getIdname());
				//System.out.println(th.toString());
				//装载缩略图
				thumbnailsService.insert(th);
			}
			
			//xmind.setThumbnails(th);
			
			int n = ++i;
			System.out.println("当前第"+ n +"条数据");
			//获取真实下载地址，慎用！频繁请求可能会被关小黑屋
			String ralurl = downloadurl(xmind.getIdname());
			if(ralurl=="0"){
				System.out.println("注意：名称为'"+ xmind.getTopic()+"'的XMind资源下载地址无效\n");
				xmindService.insert(xmind);
			}else{
				System.out.println("XMind名称："+ xmind.getTopic()+"\n下载地址为："+ ralurl+"\n");
				xmind.setBak1(ralurl);
				xmindService.insert(xmind);
			}
		}
		
		//JSONObject meta = (JSONObject) json.get("meta");
		return null;
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGet(String url, String param) {
        String result = "";
        String urlNameString = null;
        BufferedReader in = null;
        try {
        	if(param.equals("")||param==null){
        		urlNameString = url;
        	}else{
        		urlNameString = url + "?" + param;
        	}
        	System.out.println("最终请求地址为："+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            /*Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36*/
            // 建立实际的连接
            connection.connect();
            System.out.println("正在获取连接...");
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            System.out.println("连接成功！正在组装数据...");
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 获取下载地址
     * 注意频繁请求可能被关小黑屋
     * @param id
     * @return
     */
    public String downloadurl(String id){
    	Boolean testurl = null;
		String url = "https://www.xmind.net/share/api/maps/" + id + "/downloadurl";
		String param = "";
		
		//以下为失效地址，测试用
		//String uuu = "https://www.xmind.net/share/api/maps/sdDu/downloadurl";
		
		//识别地址是否有效,有效返回true，无效返回false并跳出方法返回 0 
		testurl = testURL(url);
		
		if(testurl==false){
			return "0";
		}
				
		//请求该地址，获取真实下载地址
		String result = sendGet(url);
		//返回json转换
		JSONObject json =JSONObject.fromObject(result);
		
		//获取返回json
		JSONObject links = (JSONObject) json.get("links");
		//取出链接
		String realurl = links.get("default").toString();
		
		return realurl;
	}
    
    /**
     * 判断地址是否有效
     * @param url
     * @return
     */
    public Boolean testURL(String url){
        try {  
            URL u = new URL(url);  
            try {  
                HttpURLConnection uConnection = (HttpURLConnection) u.openConnection(); 
                uConnection.setConnectTimeout(300000);
                uConnection.setReadTimeout(300000);
                try {  
                    uConnection.connect();  
                    int code = uConnection.getResponseCode();  
                    if(code==404||code==403||code==500||code==400||code==401){
                    	return false;
                    }else{
                    	return true;
                    }
                } catch (Exception e) { 
                    e.printStackTrace();  
                    System.out.println("connect failed");  
                }  
                  
            } catch (IOException e) {  
                System.out.println("build failed");  
                e.printStackTrace();  
            }  
              
        } catch (MalformedURLException e) {  
            System.out.println("build url failed");  
            e.printStackTrace();  
        }  
        return false;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGet(String url) {
    	++count;
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            /*Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36*/
            //360:
            //Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36
            
            Timer timer = new Timer();// 实例化Timer类  
            timer.schedule(new TimerTask() {
                public void run() {  
                    System.out.println("已退出延时");  
                    this.cancel();  
                }  
            }, getTimer(count));// 这里毫秒  
            System.out.println("进入延时等待...时间为:" + getTimer(count)/1000+"s");
            
            // 建立实际的连接
            connection.connect();
            System.out.println("正在获取真实下载地址...");
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            System.out.println("获取成功！正在组装下载地址...");
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 设置延时时间
     * 不知随机好还是固定好
     * 这里先做固定
     */
    public int getTimer(int count){
    	int timer = 0;
    	//请求次数大于100次
    	if(count < 100 && count > 0){
    		timer = 3000;//延时2s
    	}else if(count >= 100 && count < 299){
    		timer = 5000;//延时6s
    	}else if(count >= 300 && count < 599){
    		timer = 5000;//延时6s
    	}else if(count >= 600 && count < 999){
    		timer = 6000;//延时6s
    	}else if(count >= 1000){
    		timer = 6000;//延时6s
    	}else{
    		timer = 1;
    	}
    	return timer;
    }
    
    
    public String getDownloadurl(String id){
		String url = "https://www.xmind.net/share/api/maps/" + id + "/downloadurl";
		//请求该地址，获取真实下载地址
		String result = sendGetDownload(url);
		//返回json转换
		JSONObject json =JSONObject.fromObject(result);
		//获取返回json
		JSONObject links = (JSONObject) json.get("links");
		//取出链接
		String realurl = links.get("default").toString();
		return realurl;
	}
    
    
    public String sendGetDownload(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            //connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            /*Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36*/
            //360:
            //Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36
            
            // 建立实际的连接
            connection.connect();
            System.out.println("正在获取真实下载地址...");
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            System.out.println("获取成功！正在组装下载地址...");
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    @RequestMapping("/getLang")
    public @ResponseBody Object getLang(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<String> list = new ArrayList<String>();  
        list.add("中文");  
        list.add("English");  
        list.add("Español");  
        list.add("Deutsch");  
        list.add("Français"); 
        list.add("日本語"); 
        map.put("message","添加成功");
        map.put("langList", list);
		return JSON.toJSON(map);
    }
    
}
