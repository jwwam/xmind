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
@RequestMapping("/author")
public class Author {
	@Resource    
    private XmindService xmindService; 
	@Resource
	private ThumbnailsService thumbnailsService;
	
	public int count = 0;
	
	
	@RequestMapping("/getAuthorInfo")
	public String getAuthorInfo(@RequestParam("authorName") String authorName, HttpServletRequest request, HttpServletResponse response, Model model){
		//发布数
		
		//发布列表
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 25;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			} 
			PageBean<Xmind> authorXmindList = xmindService.getPageBeanByAuthor(authorName, pageNo, pageSize);
			model.addAttribute("authorXmindList", authorXmindList);
		} catch (Exception e) {
		}
		return "authorInfo";
	}

    
}
