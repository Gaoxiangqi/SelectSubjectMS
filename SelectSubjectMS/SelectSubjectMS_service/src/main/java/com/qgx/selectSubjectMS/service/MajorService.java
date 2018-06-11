package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Major;

/**
 * רҵҵ����
 * @author goxcheer
 *
 */
public interface MajorService {
	/**
	 * ͳ��רҵ����
	 * @param majorName
	 * @param yardId
	 * @return
	 */
	int countMajor(String majorName, String yardId);
	/**
	 * �������ͷ�ҳ��ѯרҵ����
	 * @param page
	 * @param rows
	 * @param majorName
	 * @param yardId
	 * @return
	 */
	List<Major> ListMajor(Integer page, Integer rows, String majorName, String yardId);
	/**
	 * ����רҵ���Ʋ���רҵ����
	 * @param majorName
	 * @return
	 */
	List<Major> ListMajorByMajorName(String majorName);
	/**
	 * ����רҵ
	 * @param major
	 */
	void saveMajor(Major major);
	/**
	 * ����רҵ
	 * @param major
	 */
	void updateMajor(Major major);
	/**
	 * ɾ��רҵ
	 * @param ids
	 * @return
	 */
	Integer deleteMajor(String[] ids);
	/**
	 * ��ѯĳ��Ժϵ��רҵ����
	 * @return
	 */
	List<Major> ListMajor(Long yardId);
	/**
	 * ģ����ѯĳ��Ժϵ�µ�רҵ����
	 * @param majorName
	 * @param yardId
	 * @return
	 */
	List<Major> ListMajor(String majorName, Long yardId);
	/**
	 * ����id��ȡרҵ��Ϣ
	 * @param id
	 * @return
	 */
	Major getMajorById(Long id);
}
