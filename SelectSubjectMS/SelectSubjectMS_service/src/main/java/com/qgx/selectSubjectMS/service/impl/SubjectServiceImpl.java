package com.qgx.selectSubjectMS.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Subject;
import com.qgx.selectSubjectMS.entity.TeacherAssign;
import com.qgx.selectSubjectMS.service.SubjectService;

@Service("subjectService")
@Transactional
public class SubjectServiceImpl implements SubjectService {

	@Resource
	private BaseDao<Subject> subjectDao;
	@Resource
	private BaseDao<TeacherAssign> teacherAssignDao;

	@Override
	public int countSubject(String teacherName, Long yardId, String title, String direction, String state) {
		if (teacherName != null) {
			teacherName = "%" + teacherName + "%";
		} else {
			teacherName = "%%";
		}

		if (title != null) {
			title = "%" + title + "%";
		} else {
			title = "%%";
		}

		StringBuffer hql = new StringBuffer(
				"select count(*) from Subject s where s.teacher.realName like  ? and title like ? and s.teacher.yard.id = ?");
		Object[] object = new Object[] { teacherName, title, yardId };
		if ("1".equals(direction)) {
			hql.append(" and direction ='应用方向'");
		}
		if ("2".equals(direction)) {
			hql.append(" and direction ='理论方向'");
		}

		if (state != null && !"0".equals(state)) {
			hql.append(" and state = ?");
			object = new Object[] { teacherName, title, yardId, state };
		}
		return subjectDao.count(hql.toString(), object).intValue();
	}

	@Override
	public List<Subject> listSubject(String teacherName, Long yardId, String title, String direction, String state,
			Integer page, Integer rows) {
		if (teacherName != null) {
			teacherName = "%" + teacherName + "%";
		} else {
			teacherName = "%%";
		}

		if (title != null) {
			title = "%" + title + "%";
		} else {
			title = "%%";
		}

		StringBuffer hql = new StringBuffer("from Subject s where s.teacher.realName like  ? and title like ? and s.teacher.yard.id = ?");
		Object[] object = new Object[] { teacherName, title,yardId };
		if ("1".equals(direction)) {
			hql.append(" and direction ='应用方向'");
		}
		if ("2".equals(direction)) {
			hql.append(" and direction ='理论方向'");
		}

		if (state != null && !"0".equals(state)) {
			hql.append(" and state = ?");
			object = new Object[] { teacherName, title, yardId, state };
		}

		return subjectDao.find(hql.toString(), object, page, rows);
	}

	@Override
	public Subject getSubjectById(String subjectId) {
		return subjectDao.get(Subject.class, Long.valueOf(subjectId));
	}

	@Override
	public int countSubjectByTeacherId(Long teacherId, String title, String direction, String state) {
		if (title != null) {
			title = "%" + title + "%";
		} else {
			title = "%%";
		}

		StringBuffer hql = new StringBuffer("select count(*) from Subject where title like ? and teacherId = ?");
		Object[] object = new Object[] { title, teacherId };
		if ("1".equals(direction)) {
			hql.append(" and direction ='应用方向'");
		}
		if ("2".equals(direction)) {
			hql.append(" and direction ='理论方向'");
		}

		if (state != null && !"0".equals(state)) {
			hql.append(" and state = ?");
			object = new Object[] { title, teacherId, state };
		}
		return subjectDao.count(hql.toString(),object).intValue();
	}

	@Override
	public List<Subject> listPersonalSubject(Long teacherId, String title, String direction, String state, Integer page,
			Integer rows) {
		if (title != null) {
			title = "%" + title + "%";
		} else {
			title = "%%";
		}

		StringBuffer hql = new StringBuffer("from Subject where title like ? and teacherId = ?");
		Object[] object = new Object[] { title, teacherId };
		if ("1".equals(direction)) {
			hql.append(" and direction ='应用方向'");
		}
		if ("2".equals(direction)) {
			hql.append(" and direction ='理论方向'");
		}

		if (state != null && !"0".equals(state)) {
			hql.append(" and state = ?");
			object = new Object[] { title, teacherId, state };
		}

		return subjectDao.find(hql.toString(), object, page, rows);
	}

	@Override
	public Subject getSubjectByTitle(String title) {
		return subjectDao.get("from Subject where title = ?", new Object[] { title });
	}

	@Override
	public void saveSubject(Subject subject) {
		subjectDao.save(subject);
	}

	@Override
	public Subject getSubjectOfJSONById(Long subjectId) {
		return subjectDao.get(Subject.class, subjectId);
	}

	@Override
	public void updateSubject(Subject currentSubject) {
		subjectDao.update(currentSubject);
	}

	@Override
	public Integer deleteSubject(String[] ids) {
		Integer count = 0;
		for (int i = 0; i < ids.length; i++) {
			subjectDao.delete(subjectDao.get(Subject.class, Long.valueOf(ids[i])));
			count++;
		}
		return count;
	}

	@Override
	public int countSubjectOfUnChoose(String teacherName, Long yardId, String title, String direction, String state) {
		List<TeacherAssign> taList = teacherAssignDao.find("from TeacherAssign");
		String ids = "";
		if ( ! taList.isEmpty()) {
			ids = "(";
			for (int i = 0 ; i<taList.size(); i++) {
				ids = ids + taList.get(i).getSubject().getId().toString() + ",";
			}
			ids = ids.substring(0, ids.length()-1) + ")";	
		}
		
		if (teacherName != null) {
			teacherName = "%" + teacherName + "%";
		} else {
			teacherName = "%%";
		}

		if (title != null) {
			title = "%" + title + "%";
		} else {
			title = "%%";
		}

		StringBuffer hql = new StringBuffer(
				"select count(*) from Subject s where s.teacher.realName like  ? and title like ? and s.teacher.yard.id = ?");
		Object[] object = new Object[] { teacherName, title, yardId, state};
		if ("1".equals(direction)) {
			hql.append(" and direction ='应用方向'");
		}
		if ("2".equals(direction)) {
			hql.append(" and direction ='理论方向'");
		}
		hql.append(" and state = ?");
		if (! taList.isEmpty()) {
			hql.append(" and s.id not in "+ids);
		}
		return subjectDao.count(hql.toString(), object).intValue();
	}

	@Override
	public List<Subject> listSubjectOfUnChoose(String teacherName, Long yardId, String title, String direction,
			String state, Integer page, Integer rows) {
		List<TeacherAssign> taList = teacherAssignDao.find("from TeacherAssign");
		String ids = "";
		if ( ! taList.isEmpty()) {
			ids = "(";
			for (int i = 0 ; i<taList.size(); i++) {
				ids = ids + taList.get(i).getSubject().getId().toString() + ",";
			}
			ids = ids.substring(0, ids.length()-1) + ")";	
		}
		
		if (teacherName != null) {
			teacherName = "%" + teacherName + "%";
		} else {
			teacherName = "%%";
		}

		if (title != null) {
			title = "%" + title + "%";
		} else {
			title = "%%";
		}

		StringBuffer hql = new StringBuffer(
				" from Subject s where s.teacher.realName like  ? and title like ? and s.teacher.yard.id = ?");
		Object[] object = new Object[] { teacherName, title, yardId, state};
		if ("1".equals(direction)) {
			hql.append(" and direction ='应用方向'");
		}
		if ("2".equals(direction)) {
			hql.append(" and direction ='理论方向'");
		}
		hql.append(" and state = ?");
		if (! taList.isEmpty()) {
			hql.append(" and s.id not in "+ids);
		}
		return subjectDao.find(hql.toString(), object, page, rows);
	}
	
	
}
