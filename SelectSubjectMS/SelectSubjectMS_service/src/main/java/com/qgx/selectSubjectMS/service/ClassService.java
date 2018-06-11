package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.SchoolClass;

/**
 * �༶ҵ���ģ��
 * @author goxcheer
 *
 */
public interface ClassService {
	/**
	 * ������ͳ�ư༶����
	 * @param className
	 * @param majorId
	 * @param yardId
	 * @return
	 */
	Integer countClass(String className, String majorId, Long yardId);
	/**
	 * ������ѯ�༶����
	 * @param className
	 * @param majorId
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<SchoolClass> listClass(String className, String majorId,Long yardId, Integer page, Integer rows);
	/**
	 * ����רҵid��ѯ�༶����
	 * @param majorId
	 * @return
	 */
	List<SchoolClass> listClassByMajorId(String majorId);
	/**
	 * ����id��ѯ�༶ʵ��
	 * @param id
	 * @return
	 */
	SchoolClass getSchoolClassById(Long id);
	/**
	 * ���ݰ༶���Ʋ�ѯ�༶ʵ��
	 * @param className
	 * @return
	 */
	SchoolClass getSchoolClassByClassName(String className);
	/**
	 * ��Ӱ༶
	 * @param schoolClass
	 */
	void saveClass(SchoolClass schoolClass);
	/**
	 * ���°༶
	 * @param currentSchoolClass
	 */
	void updateClass(SchoolClass currentSchoolClass);
	/**
	 * ����ɾ���༶��Ϣ
	 * @param ids
	 * @return
	 */
	Integer deleteClass(String[] ids);

}
