package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.SelectSubject;

/**
 * 学生选题表
 * @author goxcheer
 *
 */
public interface SelectSubjectService {
    /**
     * 根据题目id查询当前选题人数
     * @param id
     * @return
     */
	Integer countSelectStudentBySubjectId(Long id);
	/**
	 * 根据题目id和学生id获取学生选题的唯一记录数
	 * @param subjectId
	 * @param studentId
	 * @return
	 */
	SelectSubject getSelectSubjectBySubjectIdAndStudentId(Long subjectId, Long studentId);
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	List<SelectSubject> listSelectSubjectByStudentId(Long studentId);
	/**
	 * 保存或更新选题志愿表
	 * @param currentSelectSubject
	 */
	void saveOrUpDateSelectSubject(SelectSubject currentSelectSubject);
	/**
	 * 根据题目id查询选择的学生
	 * @param subjectId
	 * @return
	 */
	List<SelectSubject> listSelectSubjectBySubjectId(String subjectId);
	/**
	 * 根据学生id统计学生已选题目数
	 * @param studentId
	 * @return
	 */
	Integer countSelectSubjectByStudentId(Long studentId);
	/**
	 * 删除所选志愿
	 * @param selectSubject
	 */
	void deleteSelectSubject(SelectSubject selectSubject);


}
