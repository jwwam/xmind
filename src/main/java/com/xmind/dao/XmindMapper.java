package com.xmind.dao;

import java.util.List;

import com.xmind.entity.Xmind;

public interface XmindMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(Xmind record);

    int insertSelective(Xmind record);

    Xmind selectByPrimaryKey(Long id);
    
    Xmind selectByIdName(String idName);

    int updateByPrimaryKeySelective(Xmind record);

    int updateByPrimaryKeyWithBLOBs(Xmind record);
    
    int updateByPrimaryKey(Xmind record);
    
    List<Xmind> getAllXmind();
    
    int findTotal();
    
    List<Xmind> findList(int pageNo, int pageSize);
    
    /**
     * 按热度查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Xmind> findListByHot(int pageNo, int pageSize);
    
    /**
     * 获取最后一条记录
     * @return
     */
    Xmind getLast();
    
    /**
     * 根据语言查询
     * @param lang
     * @return
     */
    int findTotalByLang(String lang);
    List<Xmind> findListByLang(String lang, int pageNo, int pageSize);
    List<Xmind> getPageBeanByLang(String lang, int pageNo, int pageSize);
    
    /**
     * 根据关键字查询
     * @param key
     * @return
     */
    int findTotalByKey(String key);
    List<Xmind> findListByKey(String key, int pageNo, int pageSize);
    List<Xmind> getPageBeanByKey(String key, int pageNo, int pageSize);
    
    /**
     * 根据发布者查询
     * @param uerName
     * @return
     */
    int findTotalByAuthor(String uerName);
    List<Xmind> findListByAuthor(String uerName, int pageNo, int pageSize);
    List<Xmind> getPageBeanByAuthor(String uerName, int pageNo, int pageSize);
    
}