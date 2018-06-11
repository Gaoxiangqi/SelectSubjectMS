package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Subject;

/**
 * 题目业务功能
 * 
 * @author goxcheer
 *
 */
public interface SubjectService {
	/**
	 * 带条件统计题目个数
	 * @param teacherName
	 * @param yardId
	 * @param title
	 * @param direction
	 * @param state
	 * @return
	 */
	int countSubject(String teacherName,Long yardId, String title, String direction, String state);
    /**
     * 带条件和分页查询所有题目集合
     * @param teacherName
     * @param yardId
     * @param title
     * @param direction
     * @param state
     * @param page
     * @param rows
     * @return
     */
	List<Subject> listSubject(String teacherName,Long yardId, String title, String direction, String state, Integer page,
			Integer rows);
	/**
	 * 根据id查询题目
	 * @param subjectId
	 * @return
	 */
	Subject getSubjectById(String subjectId);
	/**
	 * 条件统计题目总数
	 * @param teacherId
	 * @param title
	 * @param direction
	 * @param state
	 * @return
	 */
	int countSubjectByTeacherId(Long teacherId, String title, String direction, String state);
	
	/**
	 * 查询个人所有出题
	 * @param title
	 * @param direction
	 * @param state
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Subject> listPersonalSubject(Long teacherId, String title, String direction, String state, Integer page,
			Integer rows);
	/**
	 * 根据标题查询题目
	 * @param title
	 * @return
	 */
	Subject getSubjectByTitle(String title);
	/**
	 * 添加题目
	 * @param subject
	 */
	void saveSubject(Subject subject);
	/**
	 * 获取题目的json数据
	 * @param subjectId
	 * @return
	 */
	Subject getSubjectOfJSONById(Long subjectId);
	/**
	 * 根据实体修改实体
	 * @param currentSubject
	 */
	void updateSubject(Subject currentSubject);
	/**
	 * 根据id(可以多个)值删除题目
	 * @param ids
	 * @return
	 */
	Integer deleteSubject(String[] ids);
	/**
	 * 统计未被选择的题目
	 * @param teacherName
	 * @param yardId
	 * @param title
	 * @param direction
	 * @param state
	 * @return
	 */
	int countSubjectOfUnChoose(String teacherName, Long yardId, String title, String direction, String state);
	/**
	 * 条件统计未被选择的题目
	 * @param teacherName
	 * @param yardId
	 * @param title
	 * @param direction
	 * @param state
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Subject> listSubjectOfUnChoose(String teacherName, Long yardId, String title, String direction, String state,
			Integer page, Integer rows);

}
