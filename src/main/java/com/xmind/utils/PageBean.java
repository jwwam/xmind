package com.xmind.utils;

import java.util.List;

public class PageBean<T> {

	private int pageNo; //当前页
	private int pageSize; //每页记录数
	private int total; //总记录数
	private List<T> list; //集合
	
	/**
	 * 获取首页
	 * @return
	 */
	public int getFirst() {
		return 1;
	}
	
	/**
	 * 获取尾页
	 * @return
	 */
	public int getLast() {
		return (total+pageSize-1)/pageSize;
	}
	
	/**
	 * 获取上一页
	 * @return
	 */
	public int getPreviou() {
		if (pageNo == 1) {
			return 1;
		}
		return pageNo - 1;
	}
	
	/**
	 * 获取下一页
	 * @return
	 */
	public int getNext() {
		if (pageNo == getLast()) {
			return pageNo;
		}
		return pageNo + 1;
	}

	/**
	 * 获取当前页
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
