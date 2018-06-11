package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Yard;
import com.qgx.selectSubjectMS.service.YardService;

@Service("yardService")
@Transactional
public class YardServiceImpl implements YardService{
	
	@Resource
	private BaseDao<Yard>yardDao;

	@Override
	public int countYard() {
		return yardDao.count("select count(*) from Yard").intValue();
	}

	@Override
	public List<Yard> ListYard(Integer page, Integer rows) {
		return yardDao.find("from Yard", page, rows);
	}

	@Override
	public Integer saveYard(Yard yard) {
		int count = 0;
		List<Yard>yardList = yardDao.find("from Yard where yardName = ?",new Object[]{yard.getYardName()});
		if (yardList.isEmpty()) {
			yardDao.save(yard);
			count++;
		}
		return count;
	}

	@Override
	public Integer deleteYard(String[] ids) {
		int count = 0;
		for (int i = 0; i<ids.length; i++) {
			yardDao.delete(yardDao.get(Yard.class, Long.valueOf(ids[i])));
			count++;
		}
		return count;
	}

	@Override
	public List<Yard> ListYard() {
		return yardDao.find("from Yard");
	}

	@Override
	public Yard getYardById(String id) {
		return yardDao.get(Yard.class, Long.valueOf(id));
	}

	@Override
	public Yard getYardByYardName(String yardName) {
		return yardDao.get("from Yard where yardName = ?", new Object[]{yardName});
	}

	@Override
	public void updateYard(Yard yard) {
		yardDao.update(yard);
	}	
	
	
	
}
