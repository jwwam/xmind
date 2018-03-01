package com.xmind.dao;

import java.util.List;

import com.xmind.entity.Time;

public interface TimeMapper {
	
	int deleteByPrimaryKey(Long id);

    int insert(Time record);

    int insertSelective(Time record);

    Time selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Time record);

    int updateByPrimaryKeyWithBLOBs(Time record);
    
    int updateByPrimaryKey(Time record);
    
    List<Time> getAllTime();
    
    int findTotal();
    
    List<Time> findList(int pageNo, int pageSize);
    
    List<Time> findListByHot(int pageNo, int pageSize);
    
    /**
     * 获取最后一条记录
     * @return
     */
    Time getLast();
    
    int findTotalByLang(String lang);
    List<Time> findListByLang(String lang, int pageNo, int pageSize);
    List<Time> getPageBeanByLang(String lang, int pageNo, int pageSize);
}