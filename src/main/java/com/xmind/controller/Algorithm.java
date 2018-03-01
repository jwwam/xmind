package com.xmind.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmind.entity.Thumbnails;
import com.xmind.entity.Xmind;
import com.xmind.service.ThumbnailsService;
import com.xmind.service.XmindService;
import com.xmind.utils.DateUtils;
import com.xmind.utils.GetHttpJson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/set")
public class Algorithm {
	
	@Resource    
    private XmindService xmindService; 
	@Resource
	private ThumbnailsService thumbnailsService;
	
	public int count = 0;
	public int temp = 0;
	public int sum = 0;
	public int faild = 0;
	public int exception = 0;
	private List<String> faildList = new ArrayList<String>();
	private List<String> exceptionList = new ArrayList<String>();
	private Xmind xmind = new Xmind();
	private Thumbnails th = new Thumbnails();
	public Date lastTime;
	
	@RequestMapping("/getNews")
	public String getNews(){
		lastTime = getLast().getCreated();
		int s = 0;
		int e = 900;
		StringBuffer sb = new StringBuffer();
		sb.append("lang=ww");
		sb.append("&start=" + s);
		sb.append("&end=" + e);
		String result = "";
		JSONArray maps = new JSONArray();
		//String param = "lang=ww&start=383&end=10000";
		String url = "https://www.xmind.net/share/api/maps";
		try {
			result = GetHttpJson.sendGet(url, sb.toString());
			//获取所有导图对象
			maps = (JSONArray) getNewsToJson(result).get("maps");
		}catch(Exception Jsone){
			System.out.println("本次返回数据异常!");
			Jsone.printStackTrace();
		}
		//遍历所有导图
		Iterator<JSONObject> it = maps.iterator();
		while(it.hasNext()){
			//获取导图属性
			JSONObject ob = (JSONObject) it.next();
			Date time = formateCreated(ob.get("created").toString());
			System.out.println("新数据发布时间："+time);
			System.out.println("本次入库前库中最后一条数据发布时间："+lastTime);
			if(time.getTime() > lastTime.getTime()){
				setValue(ob);
				setThumbnails(ob);
				//xmind.set...
			}else{ continue; }
			
			/*根据时间差计算多久的数据没更新,还没做完，以后再做吧
			 * Date time = isNew(ob.get("created").toString());//2017-07-08T05:49:04
			if(time>getLast().getCreated()){
				xmind.set...
			}else{ continue; }*/
		}
		
		return null;
	}
	
	
	@RequestMapping("/getNewsByIdName")
	public String getNewsByIdName(){
		String[] idNameList = GetHttpJson.getIidNameList();
		String[] idNameList2 = GetHttpJson.getIidNameList2();
		String[] idNameList3 = GetHttpJson.getIidNameList3();
		String[] first = (String[]) ArrayUtils.addAll(idNameList, idNameList2);
		String[] bothIdNameList = (String[]) ArrayUtils.addAll(first, idNameList3);
			for(int i = 0;bothIdNameList.length>i; i++) {
				try {
					String result = GetHttpJson.sendIdToGetXmind(bothIdNameList[i]);
					//获取导图对象
					
					JSONObject maps = (JSONObject)getNewsToJson(result).get("maps");
					Xmind xmindIdName = xmindService.selectByIdName(maps.get("id").toString());
					if(xmindIdName!=null) {
						//do nothing
						System.out.println("已存在，将跳过此记录");
						temp++;
						continue;
					}else {
						Date time = formateCreated(maps.get("created").toString());
						System.out.println("获取的数据发布时间："+time);
						setValue(maps);
						setThumbnails(maps);
						sum++;
					}
				} catch (Exception e) {
					System.out.println("出现异常，将跳过继续获取下一条");
					exceptionList.add(bothIdNameList[i]);
					exception++;
					continue;
					// TODO: handle exception
				}
				System.out.println("添加结束，共跳过了"+temp+"条数据，插入了"+ sum +"条新数据，其中下载地址无效的有"+faild+"条，无效数据idName列表为"+faildList.toString());
				System.out.println("出现异常的数据共"+exception+"条，异常数据idName列表为"+exceptionList.toString());
			}
			System.out.println("添加结束，共跳过了"+temp+"条数据，插入了"+ sum +"条新数据，其中下载地址无效的有"+faild+"条，无效数据idName列表为"+faildList.toString());
			System.out.println("出现异常的数据共"+exception+"条，异常数据idName列表为"+exceptionList.toString());
		return null;
	}
	
	public Date formateCreated(String getCreateTime){
		//修改时间
		String ct = getCreateTime.replace("T", " ");
		//格式化时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
			Date createTime = formatter.parse(ct);
			return createTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	/**
	 * 判断两个日期差多少，
	 * 貌似写反了，以后再看，现在没用到
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	 public static int compare_date(String DATE1, String DATE2) {
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
         try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }
	     return 0;
	    }
	
	/**
	 * 获取最后一条
	 * @return
	 */
	public Xmind getLast(){
		xmind = xmindService.getLast();
		return xmind;
	}
	
	/**
	 * String转JsonObject
	 * @param result
	 * @return
	 */
	public JSONObject getNewsToJson(String result){
		JSONObject json =JSONObject.fromObject(result);
		System.out.println("String转换Json成功！");
		return json;
	}
	
	public void setValue(JSONObject ob){
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
		//获取真实下载地址，慎用！频繁请求可能会被关小黑屋
		String ralurl = downloadurl(xmind.getIdname());
		if(ralurl=="0"){
			System.out.println("注意：名称为'"+ xmind.getTopic()+"'的XMind资源下载地址无效\n");
			faild++;
			faildList.add(xmind.getTopic());
			xmindService.insert(xmind);
		}else{
			System.out.println("XMind名称："+ xmind.getTopic()+"\n下载地址为："+ ralurl+"\n");
			xmind.setBak1(ralurl);
			xmindService.insert(xmind);
		}
	}
	
	public void setThumbnails(JSONObject ob){
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
		//识别地址是否有效,有效返回true，无效返回false并跳出方法返回 0 
		testurl = testURL(url);
		if(testurl==false){
			return "0";
		}
		//请求该地址，获取真实下载地址
		String result = sendGet(url);
		if(result == null){
			return "0";
		}
		//返回json转换
		JSONObject json =JSONObject.fromObject(result);
		
		//获取返回json
		//JSONObject links = (JSONObject) json.get("links");
		//JSONObject links = (JSONObject) json.get("downloadUrl");
		//取出链接
		//String realurl = links.get("default").toString();
		String realurl = json.get("downloadUrl").toString();

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
     * 发送请求，获取数据
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
            return null;
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
    
    
}
