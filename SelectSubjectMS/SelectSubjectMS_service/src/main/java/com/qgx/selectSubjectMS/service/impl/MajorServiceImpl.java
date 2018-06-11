package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Major;
import com.qgx.selectSubjectMS.service.MajorService;

@Service("majorService")
@Transactional
public class MajorServiceImpl implements MajorService {

	@Resource
	private BaseDao<Major> majorDao;

	@Override
	public int countMajor(String majorName, String yardId) {
		if (majorName == null) {
			majorName = "%%";
		} else {
			majorName = "%" + majorName + "%";
		}
		StringBuffer hql = new StringBuffer("select count (*) from Major where majorName like '" + majorName + "'");
		if (yardId != null && !"0".equals(yardId)) {
			hql.append(" and yardId = " + yardId);
		}
		return majorDao.count(hql.toString()).intValue();
	}

	@Override
	public List<Major> ListMajor(Integer page, Integer rows, String majorName, String yardId) {
		if (majorName == null) {
			majorName = "%%";
		} else {
			majorName = "%" + majorName + "%";
		}
		StringBuffer hql = new StringBuffer("from Major where majorName like '" + majorName + "'");
		if (yardId != null && !"0".equals(yardId)) {
			hql.append(" and yardId = " + yardId);
		}
		return majorDao.find(hql.toString(), page, rows);
	}

	@Override
	public List<Major> ListMajorByMajorName(String majorName) {
		return majorDao.find("from Major where majorName = ?", new Object[] { majorName });
	}

	@Override
	public void saveMajor(Major major) {
		majorDao.save(major);
	}

	@Override
	public void updateMajor(Major major) {
		majorDao.update(major);

	}

	@Override
	public Integer deleteMajor(String[] ids) {
		int count = 0;
		for (int i = 0; i < ids.length; i++) {
			majorDao.delete(majorDao.get(Major.class, Long.valueOf(ids[i])));		
			count++;
		}
		return count;
	}

	@Override
	public List<Major> ListMajor(Long yardId) {
		return majorDao.find("from Major m where m.yard.id = ?", new Object[]{yardId});
	}

	@Override
	public List<Major> ListMajor(String majorName, Long yardId) {
		if (majorName != null) {
			majorName = "%" + majorName + "%";
		} else majorName = "%%";
		return majorDao.find("from Major m where majorName like ? and  m.yard.id = ?", new Object[]{majorName, yardId});
	}

	@Override
	public Major getMajorById(Long id) {
		return majorDao.get(Major.class, id);
	}
	
	
}
