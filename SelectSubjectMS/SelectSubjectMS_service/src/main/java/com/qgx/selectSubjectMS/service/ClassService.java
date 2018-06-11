package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.SchoolClass;

/**
 * 班级业务层模块
 * @author goxcheer
 *
 */
public interface ClassService {
	/**
	 * 带条件统计班级个数
	 * @param className
	 * @param majorId
	 * @param yardId
	 * @return
	 */
	Integer countClass(String className, String majorId, Long yardId);
	/**
	 * 条件查询班级集合
	 * @param className
	 * @param majorId
	 * @param yardId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<SchoolClass> listClass(String className, String majorId,Long yardId, Integer page, Integer rows);
	/**
	 * 根据专业id查询班级集合
	 * @param majorId
	 * @return
	 */
	List<SchoolClass> listClassByMajorId(String majorId);
	/**
	 * 根据id查询班级实体
	 * @param id
	 * @return
	 */
	SchoolClass getSchoolClassById(Long id);
	/**
	 * 根据班级名称查询班级实体
	 * @param className
	 * @return
	 */
	SchoolClass getSchoolClassByClassName(String className);
	/**
	 * 添加班级
	 * @param schoolClass
	 */
	void saveClass(SchoolClass schoolClass);
	/**
	 * 更新班级
	 * @param currentSchoolClass
	 */
	void updateClass(SchoolClass currentSchoolClass);
	/**
	 * 批量删除班级信息
	 * @param ids
	 * @return
	 */
	Integer deleteClass(String[] ids);

}
