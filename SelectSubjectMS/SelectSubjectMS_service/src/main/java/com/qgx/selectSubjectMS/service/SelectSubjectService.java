package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.SelectSubject;

/**
 * ѧ��ѡ���
 * @author goxcheer
 *
 */
public interface SelectSubjectService {
    /**
     * ������Ŀid��ѯ��ǰѡ������
     * @param id
     * @return
     */
	Integer countSelectStudentBySubjectId(Long id);
	/**
	 * ������Ŀid��ѧ��id��ȡѧ��ѡ���Ψһ��¼��
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
	 * ��������ѡ��־Ը��
	 * @param currentSelectSubject
	 */
	void saveOrUpDateSelectSubject(SelectSubject currentSelectSubject);
	/**
	 * ������Ŀid��ѯѡ���ѧ��
	 * @param subjectId
	 * @return
	 */
	List<SelectSubject> listSelectSubjectBySubjectId(String subjectId);
	/**
	 * ����ѧ��idͳ��ѧ����ѡ��Ŀ��
	 * @param studentId
	 * @return
	 */
	Integer countSelectSubjectByStudentId(Long studentId);
	/**
	 * ɾ����ѡ־Ը
	 * @param selectSubject
	 */
	void deleteSelectSubject(SelectSubject selectSubject);


}
