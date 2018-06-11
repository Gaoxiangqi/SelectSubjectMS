package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.TeacherAssign;
import com.qgx.selectSubjectMS.service.TeacherAssignService;

@Service("teacherAssginService")
@Transactional
public class TeacherAssignServiceImpl implements TeacherAssignService {
	
	@Resource
	private BaseDao<TeacherAssign>teacherAssignDao;

	@Override
	public TeacherAssign getTeacherAssignBySubjectId(Long subjectId) {
		
		return teacherAssignDao.get("from TeacherAssign ta where ta.subject.id = ?", new Object[]{subjectId});
	}

	@Override
	public void saveOrUpdateAssign(TeacherAssign teacherAssign) {
		teacherAssignDao.saveOrUpdate(teacherAssign);
	}

	@Override
	public TeacherAssign getUniqueTeacherAssign(Long subjectId) {
		return teacherAssignDao.get("from TeacherAssign ta where ta.subject.id = ?", new Object[]{subjectId});
	}

	@Override
	public TeacherAssign getTeacherAssignByStudentId(Long studentId) {
		return teacherAssignDao.get("from TeacherAssign ta where ta.student.id = ?", new Object[]{studentId});
	}

	@Override
	public List<TeacherAssign> listTeacherAssignByYardId(Long yardId, Integer page, Integer rows) {
		return teacherAssignDao.find("from TeacherAssign ta where ta.student.schoolClass.major.yard.id = ? order by ta.student.schoolClass.className",new Object[]{yardId} , page, rows);
	}

	@Override
	public Integer countTeacherAssignByYardId(Long yardId) {
		return teacherAssignDao.count("select count(*) from TeacherAssign ta where ta.student.schoolClass.major.yard.id = ?",new Object[]{yardId}).intValue();
	}

	@Override
	public List<TeacherAssign> listTeacherAssignByYardId(Long yardId) {
		return teacherAssignDao.find("from TeacherAssign ta where ta.student.schoolClass.major.yard.id = ? order by ta.student.schoolClass.className",new Object[]{yardId});
	}
	
	
}
