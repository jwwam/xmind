package com.xmind.service;

import java.util.List;

import com.xmind.entity.Xmind;
import com.xmind.utils.PageBean;

public interface XmindService {
	   
	int deleteByPrimaryKey(Long id);

    int insert(Xmind record);

    int insertSelective(Xmind record);

    Xmind selectByPrimaryKey(Long id);
    
    Xmind selectByIdName(String idName);

    int updateByPrimaryKeySelective(Xmind record);

    int updateByPrimaryKey(Xmind record);
    
    List<Xmind> getAllXmind();
    
    PageBean<Xmind> getPageBean(int pageNo, int pageSize);
    
    PageBean<Xmind> getPageBeanByHot(int pageNo, int pageSize);
    
    int findTotal();
    
    List<Xmind> findList(int pageNo, int pageSize);
    
    Xmind getLast();
    
    int findTotalByLang(String lang);
    List<Xmind> findListByLang(String lang, int pageNo, int pageSize);
    PageBean<Xmind> getPageBeanByLang(String lang, int pageNo, int pageSize);
    
    int findTotalByKey(String key);
    List<Xmind> findListByKey(String key, int pageNo, int pageSize);
    PageBean<Xmind> getPageBeanByKey(String key, int pageNo, int pageSize);
    
    int findTotalByAuthor(String uerName);
    List<Xmind> findListByAuthor(String uerName, int pageNo, int pageSize);
    PageBean<Xmind> getPageBeanByAuthor(String uerName, int pageNo, int pageSize);

}
