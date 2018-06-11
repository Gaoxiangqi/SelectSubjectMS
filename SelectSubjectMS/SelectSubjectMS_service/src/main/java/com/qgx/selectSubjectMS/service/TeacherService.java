package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Teacher;

/**
 * ��ʦҵ������
 * @author goxcheer
 *
 */
public interface TeacherService {
	/**
	 * �����û�id��ȡ��ʦ
	 * @param userId
	 * @return
	 */
	Teacher findTeacherByUserId(Long userId);
	/**
	 * ��������ѯ��ʦ����
	 * @param teacherName
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Teacher> listTeacher(String teacherName, Long yardId, Integer page, Integer rows);
	/**
	 * ����ͳ�Ƹ���
	 * @param teacherName
	 * @param yardId
	 * @return
	 */
	int countTeacher(String teacherName, Long yardId);
	/**
	 * ��ӽ�ʦ
	 * @param teacher
	 */
	void saveTeacher(Teacher teacher);
	/**
	 * ����id��ȡ��ʦ
	 * @param id
	 * @return
	 */
	Teacher getTeacherById(Long id);
	/**
	 * ������ʦ��Ϣ
	 * @param currentTeacher
	 */
	void updateTeacher(Teacher currentTeacher);
	/**
	 * ɾ����ʦ
	 * @param ids
	 * @return
	 */
	Integer deleteTeacher(String[] ids);
	/**
	 * ����ѧԺid��ѯ��Ժϵ�µ���ʦ
	 * @param yardId
	 * @return
	 */
	List<Teacher> listTeacherByYardId(Long yardId);

}
