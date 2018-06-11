package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Student;
/**
 * 学生业务功能
 * @author goxcheer
 *
 */
public interface StudentService {
	
	/**
	 *根据用户id获取学生 
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
	 * 根据学生姓名查找学生集合
	 * @param studentName
	 * @return
	 */
	List<Student> listStudentByName(String studentName);
	/**
	 * 条件查询学生集合
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
	 * 条件统计学生个数
	 * @param studentName
	 * @param yardId
	 * @param majorId
	 * @param classId
	 * @return
	 */
	int countStudent(String studentName, Long yardId, String majorId, String classId);
	/**
	 * 保存学生信息
	 * @param student
	 */
	void saveStudent(Student student);
	/**
	 * 根据id查询学生信息
	 * @param id
	 * @return
	 */
	Student getStudentById(Long id);
	/**
	 * 批量删除学生
	 * @param ids
	 * @return
	 */
	Integer deleteStudent(String[] ids);
	/**
	 * 查询学院下的学生集合(默认以班级序号排序)
	 * @param yardId
	 * @return
	 */
	List<Student> listStudentByYardId(Long yardId);
	/**
	 * 查询未分配题目的学生
	 * @param studentName
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Student> listStudentOfUnAssign(String studentName, Long yardId, Integer page, Integer rows);
	/**
	 * 统计未分配题目学生数
	 * @param studentName
	 * @param yardId
	 * @return
	 */
	int countStudentOfUnAssign(String studentName, Long yardId);


	

}
