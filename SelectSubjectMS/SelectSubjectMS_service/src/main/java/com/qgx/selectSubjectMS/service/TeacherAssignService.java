package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.TeacherAssign;

/**
 * 老师分配表业务功能
 * @author Administrator
 *
 */
public interface TeacherAssignService {
	/**
	 * 根据题目id获取当前题目的终表
	 * @param subjectId
	 * @return
	 */
	TeacherAssign getTeacherAssignBySubjectId(Long subjectId);
	/**
	 * 保存或修改选题指派
	 * @param teacherAssign
	 */
	void saveOrUpdateAssign(TeacherAssign teacherAssign);
	/**
	 * 获得独一无二的结果
	 * @param subjectId
	 * @return
	 */
	TeacherAssign getUniqueTeacherAssign(Long subjectId);
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	TeacherAssign getTeacherAssignByStudentId(Long studentId);
	/**
	 * 查询自己院系下的选题结果
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<TeacherAssign> listTeacherAssignByYardId(Long yardId, Integer page, Integer rows);
	/**
	 * 统计院系下的所有选题
	 * @param yardId
	 * @return
	 */
	Integer countTeacherAssignByYardId(Long yardId);
	/**
	 * 不带分页查询自己院系下的选题结果
	 * @param yardId
	 * @return
	 */
	List<TeacherAssign> listTeacherAssignByYardId(Long yardId);

}
