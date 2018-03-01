package com.xmind.dao;

import java.util.List;

import com.xmind.entity.Thumbnails;

public interface ThumbnailsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Thumbnails record);

    int insertSelective(Thumbnails record);

    Thumbnails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Thumbnails record);

    int updateByPrimaryKeyWithBLOBs(Thumbnails record);
    
    int updateByPrimaryKey(Thumbnails record);
    
    List<Thumbnails> getAllThumbnails();
    
    int findTotal();
    
    List<Thumbnails> findList(int pageNo, int pageSize);
    
}