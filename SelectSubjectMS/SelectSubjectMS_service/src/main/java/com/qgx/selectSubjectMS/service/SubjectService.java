package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Subject;

/**
 * ��Ŀҵ����
 * 
 * @author goxcheer
 *
 */
public interface SubjectService {
	/**
	 * ������ͳ����Ŀ����
	 * @param teacherName
	 * @param yardId
	 * @param title
	 * @param direction
	 * @param state
	 * @return
	 */
	int countSubject(String teacherName,Long yardId, String title, String direction, String state);
    /**
     * �������ͷ�ҳ��ѯ������Ŀ����
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
	 * ����id��ѯ��Ŀ
	 * @param subjectId
	 * @return
	 */
	Subject getSubjectById(String subjectId);
	/**
	 * ����ͳ����Ŀ����
	 * @param teacherId
	 * @param title
	 * @param direction
	 * @param state
	 * @return
	 */
	int countSubjectByTeacherId(Long teacherId, String title, String direction, String state);
	
	/**
	 * ��ѯ�������г���
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
	 * ���ݱ����ѯ��Ŀ
	 * @param title
	 * @return
	 */
	Subject getSubjectByTitle(String title);
	/**
	 * �����Ŀ
	 * @param subject
	 */
	void saveSubject(Subject subject);
	/**
	 * ��ȡ��Ŀ��json����
	 * @param subjectId
	 * @return
	 */
	Subject getSubjectOfJSONById(Long subjectId);
	/**
	 * ����ʵ���޸�ʵ��
	 * @param currentSubject
	 */
	void updateSubject(Subject currentSubject);
	/**
	 * ����id(���Զ��)ֵɾ����Ŀ
	 * @param ids
	 * @return
	 */
	Integer deleteSubject(String[] ids);
	/**
	 * ͳ��δ��ѡ�����Ŀ
	 * @param teacherName
	 * @param yardId
	 * @param title
	 * @param direction
	 * @param state
	 * @return
	 */
	int countSubjectOfUnChoose(String teacherName, Long yardId, String title, String direction, String state);
	/**
	 * ����ͳ��δ��ѡ�����Ŀ
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
