package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.SelectSubject;
import com.qgx.selectSubjectMS.service.SelectSubjectService;
@Service("selectSubjectService")
@Transactional
public class SelectSubjectServiceImpl implements SelectSubjectService {
	
    @Resource
	private BaseDao<SelectSubject>selectSubjectDao;

	@Override
	public Integer countSelectStudentBySubjectId(Long id) {
		return selectSubjectDao.count("select count (*) from SelectSubject where subjectId = ?",new Object[]{id}).intValue();
	}

	@Override
	public SelectSubject getSelectSubjectBySubjectIdAndStudentId(Long subjectId, Long studentId) {
		
		return selectSubjectDao.get("from SelectSubject ss where ss.subject.id = ? and ss.student.id = ?", new Object[]{subjectId, studentId});
	}

	@Override
	public List<SelectSubject> listSelectSubjectByStudentId(Long studentId) {
		return selectSubjectDao.find("from SelectSubject ss where ss.student.id = ?  order by studentLevel ", new Object[]{studentId});
	}

	@Override
	public void saveOrUpDateSelectSubject(SelectSubject currentSelectSubject) {
		 selectSubjectDao.saveOrUpdate(currentSelectSubject);	
	}

	@Override
	public List<SelectSubject> listSelectSubjectBySubjectId(String subjectId) {
		return selectSubjectDao.find("from SelectSubject ss where ss.subject.id = ? order by studentLevel", new Object[]{Long.valueOf(subjectId)});
	}

	@Override
	public Integer countSelectSubjectByStudentId(Long studentId) {
		return selectSubjectDao.count("select count(*) from SelectSubject ss where ss.student.id = ?",new Object[]{studentId}).intValue();
	}

	@Override
	public void deleteSelectSubject(SelectSubject selectSubject) {
		selectSubjectDao.delete(selectSubject);
		
	}
    
    
}
