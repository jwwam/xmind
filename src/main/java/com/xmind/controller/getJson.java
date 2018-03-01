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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.xmind.entity.Thumbnails;
import com.xmind.entity.Xmind;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 直接执行
 * @author Administrator
 *
 */
public class getJson {
	
	public static void main(String[] args) {
		
		String url = "http://www.xmind.net/share/api/maps";
		//String param = "lang=ww&start=383&end=10000";
		String param = "lang=ww";
		//发送请求，获取数据
		String result = sendGet(url, param);
		//数据处理，写入对象
		getXmind(result);
		
	}
	
	
	public static String getXmind(String result){
		int i = 0;
		Xmind xmind = new Xmind();
		Thumbnails th =new Thumbnails();
		JSONObject json =JSONObject.fromObject(result);  
		
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
			
			//String dates = ob.get("created").toString();//2017-07-08T05:49:04
			//String dates2 = ob.get("formated_time").toString();//Jul 08, 2017 05:49
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.UK);
		    String dateString = formatter.format(ob.get("created").toString());
		    String dateString2 = formatter.format(ob.get("formated_time").toString());
		    Date currentTime_2 = null;
		    Date currentTime_3 = null;
			try {
				currentTime_2 = formatter.parse(dateString);
				currentTime_3 = formatter.parse(dateString2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			   
			xmind.setCreated(currentTime_2);
			xmind.setLastname(ob.get("lastName").toString());
			xmind.setDaily(ob.get("daily").toString());
			xmind.setFormatedTime(currentTime_3);
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
			}
			
			//装载缩略图
			//xmind.setThumbnails(th);
			
			int n = ++i;
			System.out.println("当前第"+ n +"条数据");
			//获取真实下载地址，慎用！频繁请求可能会被关小黑屋
			String ralurl = downloadurl(xmind.getIdname());
			if(ralurl=="0"){
				System.out.println("注意：名称为'"+ xmind.getTopic()+"'的XMind资源下载地址无效\n");
			}else{
				System.out.println("XMind名称："+ xmind.getTopic()+"\n下载地址为："+ ralurl+"\n");
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
    public static String sendGet(String url, String param) {
        String result = "";
        String urlNameString = null;
        BufferedReader in = null;
        try {
        	if(param.equals("")||param==null){
        		urlNameString = url;
        	}else{
        		urlNameString = url + "?" + param;
        	}
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
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
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
    public static String downloadurl(String id){
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
		String result = sendGet(url, param);
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
    public static Boolean testURL(String url){
        try {  
            URL u = new URL(url);  
            try {  
                HttpURLConnection uConnection = (HttpURLConnection) u.openConnection();  
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
    
}
