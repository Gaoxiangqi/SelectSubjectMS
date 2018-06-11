package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.SchoolClass;
import com.qgx.selectSubjectMS.service.ClassService;

@Service("classService")
@Transactional
public class ClassServiceImpl implements ClassService {

	@Resource
	private BaseDao<SchoolClass> classDao;

	@Override
	public Integer countClass(String className, String majorId, Long yardId) {
		if (className == null) {
			className = "%%";
		} else {
			className = "%" + className + "%";
		}
		StringBuffer hql = new StringBuffer(
				"select count(*) from SchoolClass s where className like ? and s.major.yard.id = ? ");
		Object[] params = new Object[] { className, yardId};
		if (majorId != null && !"0".equals(majorId)) {
			hql.append(" and s.major.id = ?");
			params = new Object[] { className, yardId, Long.valueOf(majorId)};
		}
		return classDao.count(hql.toString(), params).intValue();
	}

	@Override
	public List<SchoolClass> listClass(String className, String majorId,Long yardId, Integer page, Integer rows) {
		if (className == null) {
			className = "%%";
		} else {
			className = "%" + className + "%";
		}
		StringBuffer hql = new StringBuffer("from SchoolClass s where className like ? and s.major.yard.id = ?");
		Object[] params = new Object[] { className,yardId};
		if (majorId != null && !"0".equals(majorId)) {
			hql.append(" and s.major.id = ?");
			params = new Object[] { className,yardId,Long.valueOf(majorId) };
		}
		return classDao.find(hql.toString(), params, page, rows);
	}

	@Override
	public List<SchoolClass> listClassByMajorId(String majorId) {	
		return classDao.find("from SchoolClass c where c.major.id = ?", new Object[]{Long.valueOf(majorId)});
	}

	@Override
	public SchoolClass getSchoolClassById(Long id) {
		return classDao.get(SchoolClass.class, id);
	}

	@Override
	public SchoolClass getSchoolClassByClassName(String className) {
		return classDao.get("from SchoolClass where className = ?", new Object[]{className});
	}

	@Override
	public void saveClass(SchoolClass schoolClass) {
			classDao.save(schoolClass);
	}

	@Override
	public void updateClass(SchoolClass currentSchoolClass) {
		classDao.update(currentSchoolClass);
	}

	@Override
	public Integer deleteClass(String[] ids) {
		int count = 0;
		for (int i = 0; i<ids.length; i++) {
			classDao.delete(classDao.get(SchoolClass.class, Long.valueOf(ids[i])));
			count++;
		}
		return count;
	}
	
	
}
