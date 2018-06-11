package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.TeacherAssign;

/**
 * ��ʦ�����ҵ����
 * @author Administrator
 *
 */
public interface TeacherAssignService {
	/**
	 * ������Ŀid��ȡ��ǰ��Ŀ���ձ�
	 * @param subjectId
	 * @return
	 */
	TeacherAssign getTeacherAssignBySubjectId(Long subjectId);
	/**
	 * ������޸�ѡ��ָ��
	 * @param teacherAssign
	 */
	void saveOrUpdateAssign(TeacherAssign teacherAssign);
	/**
	 * ��ö�һ�޶��Ľ��
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
	 * ��ѯ�Լ�Ժϵ�µ�ѡ����
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<TeacherAssign> listTeacherAssignByYardId(Long yardId, Integer page, Integer rows);
	/**
	 * ͳ��Ժϵ�µ�����ѡ��
	 * @param yardId
	 * @return
	 */
	Integer countTeacherAssignByYardId(Long yardId);
	/**
	 * ������ҳ��ѯ�Լ�Ժϵ�µ�ѡ����
	 * @param yardId
	 * @return
	 */
	List<TeacherAssign> listTeacherAssignByYardId(Long yardId);

}
