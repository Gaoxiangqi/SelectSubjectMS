package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Teacher;
import com.qgx.selectSubjectMS.service.TeacherService;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {

	@Resource
	private BaseDao<Teacher> teacherDao;

	@Override
	public Teacher findTeacherByUserId(Long userId) {
		return teacherDao.get("from Teacher t where t.user.id = ?", new Object[] { userId });
	}

	@Override
	public List<Teacher> listTeacher(String teacherName, Long yardId, Integer page, Integer rows) {
		if (teacherName == null) {
			teacherName = "%%";
		} else {
			teacherName = "%" + teacherName + "%";
		}
		return teacherDao.find("from Teacher t where realName like ? and t.yard.id = ?",
				new Object[] { teacherName, yardId }, page, rows);
	}

	@Override
	public int countTeacher(String teacherName, Long yardId) {
		if (teacherName == null) {
			teacherName = "%%";
		} else {
			teacherName = "%" + teacherName + "%";
		}
		return teacherDao.count("select count(*) from Teacher t  where realName like ? and t.yard.id = ?",
				new Object[] {teacherName, yardId}).intValue();
	}

	@Override
	public void saveTeacher(Teacher teacher) {
		teacherDao.save(teacher);	
	}

	@Override
	public Teacher getTeacherById(Long id) {
		return teacherDao.get(Teacher.class, id);
	}

	@Override
	public void updateTeacher(Teacher currentTeacher) {
		teacherDao.update(currentTeacher);
	}

	@Override
	public Integer deleteTeacher(String[] ids) {
		int count =0;
		for (int i = 0; i<ids.length; i++) {
			teacherDao.delete(teacherDao.get(Teacher.class, Long.valueOf(ids[i])));
			count++;
		}
		return count;
	}

	@Override
	public List<Teacher> listTeacherByYardId(Long yardId) {
		return teacherDao.find("from Teacher t where t.yard.id = ?", new Object[]{yardId});
	}
	
	
}
