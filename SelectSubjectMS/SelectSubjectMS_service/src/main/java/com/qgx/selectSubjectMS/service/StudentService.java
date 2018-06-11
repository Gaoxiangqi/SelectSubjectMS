package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Student;
/**
 * ѧ��ҵ����
 * @author goxcheer
 *
 */
public interface StudentService {
	
	/**
	 *�����û�id��ȡѧ�� 
	 * @param userId
	 * @return
	 */
	Student findStudentByUserId(Long userId);
	/**
	 * 
	 * @param student
	 */
	void updateStudent(Student currentStudent);
	/**
	 * 
	 * @param classId
	 * @return
	 */
	Integer countStudentByClassId(Long classId);
	/**
	 * ����ѧ����������ѧ������
	 * @param studentName
	 * @return
	 */
	List<Student> listStudentByName(String studentName);
	/**
	 * ������ѯѧ������
	 * @param studentName
	 * @param yardId
	 * @param majorId
	 * @param classId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Student> listStudent(String studentName, Long yardId, String majorId, String classId, Integer page, Integer rows);
	/**
	 * ����ͳ��ѧ������
	 * @param studentName
	 * @param yardId
	 * @param majorId
	 * @param classId
	 * @return
	 */
	int countStudent(String studentName, Long yardId, String majorId, String classId);
	/**
	 * ����ѧ����Ϣ
	 * @param student
	 */
	void saveStudent(Student student);
	/**
	 * ����id��ѯѧ����Ϣ
	 * @param id
	 * @return
	 */
	Student getStudentById(Long id);
	/**
	 * ����ɾ��ѧ��
	 * @param ids
	 * @return
	 */
	Integer deleteStudent(String[] ids);
	/**
	 * ��ѯѧԺ�µ�ѧ������(Ĭ���԰༶�������)
	 * @param yardId
	 * @return
	 */
	List<Student> listStudentByYardId(Long yardId);
	/**
	 * ��ѯδ������Ŀ��ѧ��
	 * @param studentName
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Student> listStudentOfUnAssign(String studentName, Long yardId, Integer page, Integer rows);
	/**
	 * ͳ��δ������Ŀѧ����
	 * @param studentName
	 * @param yardId
	 * @return
	 */
	int countStudentOfUnAssign(String studentName, Long yardId);


	

}
