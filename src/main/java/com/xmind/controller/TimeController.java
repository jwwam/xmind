package com.xmind.controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xmind.entity.Time;
import com.xmind.entity.Xmind;
import com.xmind.service.TimeService;
import com.xmind.service.XmindService;
import com.xmind.utils.PageBean;

@Controller
@RequestMapping("/timeline")
public class TimeController {
	@Resource    
    private XmindService xmindService;
	@Resource    
	private TimeService timeService;
	
	public int count = 0;

	
	/**
	 * 按时间
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/timeList")
	public String releasePage(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 25;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			} 
			PageBean<Time> timeList = timeService.getPageBean(pageNo, pageSize);
			model.addAttribute("timeList", timeList);
		} catch (Exception e) {
		}
		return "timeLine";
	}
	
	
	/**
	 * 按语言
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/lang")
	public String lang(@RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String sPageNo = request.getParameter("pageNo");
			int pageNo = 1;
			int pageSize = 50;
			if (sPageNo != null) {
				pageNo = Integer.parseInt(sPageNo);
			}
			PageBean<Xmind> xmindList = xmindService.getPageBeanByLang(lang, pageNo, pageSize);
			model.addAttribute("xmindList", xmindList);
			model.addAttribute("language", lang);
		} catch (Exception e) {
		}
		return "xmindList";
	}

	
	@RequestMapping("/getRealDownLoadUrl")
	public @ResponseBody Object getRealDownLoadUrl(@RequestParam("idname") String idnames, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("ralurl", ralurl);
		return JSON.toJSON(map);
	}
		
	
	@RequestMapping("/timeLine")
	public String timeLine(HttpServletRequest request, HttpServletResponse response, Model model){
		
		return "timeLine";
	}
	

    
}
