package com.xmind.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xmind.dao.ThumbnailsMapper;
import com.xmind.entity.Thumbnails;
import com.xmind.utils.PageBean;

@Service
public class ThumbnailsServiceImpl implements ThumbnailsService {
	
	@Resource
	private ThumbnailsMapper thumbnailsDao;    

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		thumbnailsDao.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public int insert(Thumbnails record) {
		// TODO Auto-generated method stub
		thumbnailsDao.insert(record);
		return 0;
	}

	@Override
	public int insertSelective(Thumbnails record) {
		// TODO Auto-generated method stub
		thumbnailsDao.insertSelective(record);
		return 0;
	}

	@Override
	public Thumbnails selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return thumbnailsDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Thumbnails record) {
		// TODO Auto-generated method stub
		thumbnailsDao.updateByPrimaryKeySelective(record);
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Thumbnails record) {
		// TODO Auto-generated method stub
		thumbnailsDao.updateByPrimaryKey(record);
		return 0;
	}

	@Override
	public List<Thumbnails> getAllThumbnails() {
		// TODO Auto-generated method stub
		return thumbnailsDao.getAllThumbnails();
	}

	public int findTotal() {
		int count = 0;
		return thumbnailsDao.findTotal();
	}
	
	@Override
	public PageBean<Thumbnails> getPageBean(int pageNo, int pageSize) {
		PageBean<Thumbnails> pb = new PageBean<Thumbnails>();
		try {
			int count = findTotal();
			List<Thumbnails> list = findList(pageNo, pageSize);
			pb.setPageNo(pageNo);
			pb.setPageSize(pageSize);
			pb.setTotal(count);
			pb.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return pb;
	}
	
	public List<Thumbnails> findList(int pageNo, int pageSize) {
		pageNo = (pageNo-1)*pageSize;
		return thumbnailsDao.findList(pageNo, pageSize);
	}
	
}
