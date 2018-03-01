package com.xmind.service;

import java.util.List;

import com.xmind.entity.Time;
import com.xmind.utils.PageBean;

public interface TimeService {
	   
	int deleteByPrimaryKey(Long id);

    int insert(Time record);

    int insertSelective(Time record);

    Time selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Time record);

    int updateByPrimaryKey(Time record);
    
    List<Time> getAllTime();
    
    PageBean<Time> getPageBean(int pageNo, int pageSize);
    
    PageBean<Time> getPageBeanByHot(int pageNo, int pageSize);
    
    int findTotal();
    
    List<Time> findList(int pageNo, int pageSize);
    
    Time getLast();
    
    int findTotalByLang(String lang);
    List<Time> findListByLang(String lang, int pageNo, int pageSize);
    PageBean<Time> getPageBeanByLang(String lang, int pageNo, int pageSize);
}
