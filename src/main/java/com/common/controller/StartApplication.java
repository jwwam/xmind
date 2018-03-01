package com.common.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class StartApplication {
	
	/**
	 * @Scheduled(fixedRate = 5 * 60 * 1000)
	 * 五分钟执行一次
	 */
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void startApp() {
		System.out.println("执行当前类：" + this.getClass().getName() + "中的方法："+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void startApp2() {
		System.out.println("执行当前类：" + this.getClass().getName() + "中的方法："+Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
}
