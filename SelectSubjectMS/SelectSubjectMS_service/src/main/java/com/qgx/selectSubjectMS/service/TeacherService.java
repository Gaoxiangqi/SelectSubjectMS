package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Teacher;

/**
 * 老师业务层代码
 * @author goxcheer
 *
 */
public interface TeacherService {
	/**
	 * 根据用户id获取老师
	 * @param userId
	 * @return
	 */
	Teacher findTeacherByUserId(Long userId);
	/**
	 * 带条件查询教师集合
	 * @param teacherName
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Teacher> listTeacher(String teacherName, Long yardId, Integer page, Integer rows);
	/**
	 * 条件统计个数
	 * @param teacherName
	 * @param yardId
	 * @return
	 */
	int countTeacher(String teacherName, Long yardId);
	/**
	 * 添加教师
	 * @param teacher
	 */
	void saveTeacher(Teacher teacher);
	/**
	 * 根据id获取老师
	 * @param id
	 * @return
	 */
	Teacher getTeacherById(Long id);
	/**
	 * 更新老师信息
	 * @param currentTeacher
	 */
	void updateTeacher(Teacher currentTeacher);
	/**
	 * 删除老师
	 * @param ids
	 * @return
	 */
	Integer deleteTeacher(String[] ids);
	/**
	 * 根据学院id查询该院系下的老师
	 * @param yardId
	 * @return
	 */
	List<Teacher> listTeacherByYardId(Long yardId);

}
