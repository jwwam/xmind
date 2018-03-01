package com.xmind.service;

import java.util.List;

import com.xmind.entity.Thumbnails;
import com.xmind.utils.PageBean;


public interface ThumbnailsService {
	   
	int deleteByPrimaryKey(Long id);

    int insert(Thumbnails record);

    int insertSelective(Thumbnails record);

    Thumbnails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Thumbnails record);

    int updateByPrimaryKey(Thumbnails record);
    
    List<Thumbnails> getAllThumbnails();
    
    PageBean<Thumbnails> getPageBean(int pageNo, int pageSize);
    
    int findTotal();
    
    List<Thumbnails> findList(int pageNo, int pageSize);
    
}
