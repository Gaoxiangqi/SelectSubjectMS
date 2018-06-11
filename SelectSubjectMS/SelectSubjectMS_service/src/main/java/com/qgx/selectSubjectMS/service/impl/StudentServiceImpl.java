package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.SchoolClass;
import com.qgx.selectSubjectMS.entity.Student;
import com.qgx.selectSubjectMS.entity.Teacher;
import com.qgx.selectSubjectMS.entity.TeacherAssign;
import com.qgx.selectSubjectMS.service.StudentService;

@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

	@Resource
	private BaseDao<Student> studentDao;
	@Resource 
	private BaseDao<TeacherAssign> teacherAssignDao;

	@Override
	public Student findStudentByUserId(Long userId) {
		return studentDao.get("from Student s where s.user.id = ?", new Object[] { userId });
	}

	@Override
	public void updateStudent(Student currentStudent) {
		studentDao.save(currentStudent);
	}

	@Override
	public Integer countStudentByClassId(Long classId) {
		return studentDao.count("select count(*) from Student s where s.schoolClass.id = ?", new Object[] { classId })
				.intValue();
	}

	@Override
	public List<Student> listStudentByName(String studentName) {
		return studentDao.find("from Student where realName = ?", new Object[] { studentName });
	}

	@Override
	public List<Student> listStudent(String studentName, Long yardId, String majorId, String classId, Integer page,
			Integer rows) {
		if (studentName != null) {
			studentName = "%" + studentName + "%";
		} else
			studentName = "%%";

		StringBuffer hql = new StringBuffer("from Student s where realName like ? and s.schoolClass.major.yard.id = ?");
		Object[] params = new Object[] { studentName, yardId };
		// 如何班级条件存在，则专业条件无作用
		if (classId != null && !"0".equals(classId)) {
			hql.append(" and s.schoolClass.id = ?");
			params = new Object[] { studentName, yardId, Long.valueOf(classId) };
		} else if (majorId != null && !"0".equals(majorId)) {
			hql.append(" and s.schoolClass.major.id = ?");
			params = new Object[] { studentName, yardId, Long.valueOf(majorId) };
		}
		hql.append(" order by s.schoolClass.className");
		return studentDao.find(hql.toString(), params, page, rows);
	}

	@Override
	public int countStudent(String studentName, Long yardId, String majorId, String classId) {
		if (studentName != null) {
			studentName = "%" + studentName + "%";
		} else
			studentName = "%%";

		StringBuffer hql = new StringBuffer(
				"select count(*) from Student s where realName like ? and s.schoolClass.major.yard.id = ?");
		Object[] params = new Object[] { studentName, yardId };
		// 如何班级条件存在，则专业条件无作用
		if (classId != null && !"0".equals(classId)) {
			hql.append(" and s.schoolClass.id = ?");
			params = new Object[] { studentName, yardId, Long.valueOf(classId) };
		} else if (majorId != null && !"0".equals(majorId)) {
			hql.append(" and s.schoolClass.major.id = ?");
			params = new Object[] { studentName, yardId, Long.valueOf(majorId) };
		}
		return studentDao.count(hql.toString(), params).intValue();
	}

	@Override
	public void saveStudent(Student student) {
		studentDao.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentDao.get(Student.class, id);
	}

	@Override
	public Integer deleteStudent(String[] ids) {
		int count = 0;
		for (int i = 0; i < ids.length; i++) {
			studentDao.delete(studentDao.get(Student.class, Long.valueOf(ids[i])));
			count++;
		}
		return count;
	}

	@Override
	public List<Student> listStudentByYardId(Long yardId) {
		return studentDao.find("from Student s where s.schoolClass.major.yard.id = ? order by s.schoolClass.className",
				new Object[] { yardId });
	}

	@Override
	public List<Student> listStudentOfUnAssign(String studentName, Long yardId, Integer page, Integer rows) {
		List<TeacherAssign> taList = teacherAssignDao.find("from TeacherAssign");
		String ids = "";
		if ( ! taList.isEmpty()) {
			ids = "(";
			for (int i = 0 ; i<taList.size(); i++) {
				ids = ids + taList.get(i).getStudent().getId().toString() + ",";
			}
			ids = ids.substring(0, ids.length()-1) + ")";	
		}
		
		if (studentName != null) {
			studentName = "%" + studentName + "%";
		} else {
			studentName = "%%";
		}

		StringBuffer hql = new StringBuffer(
				" from Student s where s.realName like  ? and s.schoolClass.major.yard.id = ?");
		Object[] object = new Object[] { studentName, yardId};
		if (! taList.isEmpty()) {
			hql.append(" and s.id not in "+ids + " order by s.schoolClass.className");
		}
		return studentDao.find(hql.toString(), object, page, rows);
	}

	@Override
	public int countStudentOfUnAssign(String studentName, Long yardId) {
		List<TeacherAssign> taList = teacherAssignDao.find("from TeacherAssign");
		String ids = "";
		if ( ! taList.isEmpty()) {
			ids = "(";
			for (int i = 0 ; i<taList.size(); i++) {
				ids = ids + taList.get(i).getStudent().getId().toString() + ",";
			}
			ids = ids.substring(0, ids.length()-1) + ")";	
		}
		
		if (studentName != null) {
			studentName = "%" + studentName + "%";
		} else {
			studentName = "%%";
		}

		StringBuffer hql = new StringBuffer(
				"select count(*) from Student s where s.realName like  ? and s.schoolClass.major.yard.id = ?");
		Object[] object = new Object[] { studentName, yardId};
		if (! taList.isEmpty()) {
			hql.append(" and s.id not in "+ids);
		}
		return studentDao.count(hql.toString(), object).intValue();
	}
	
	
}
