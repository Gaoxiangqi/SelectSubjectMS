package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Major;

/**
 * 专业业务功能
 * @author goxcheer
 *
 */
public interface MajorService {
	/**
	 * 统计专业个数
	 * @param majorName
	 * @param yardId
	 * @return
	 */
	int countMajor(String majorName, String yardId);
	/**
	 * 带条件和分页查询专业集合
	 * @param page
	 * @param rows
	 * @param majorName
	 * @param yardId
	 * @return
	 */
	List<Major> ListMajor(Integer page, Integer rows, String majorName, String yardId);
	/**
	 * 根据专业名称查找专业集合
	 * @param majorName
	 * @return
	 */
	List<Major> ListMajorByMajorName(String majorName);
	/**
	 * 保存专业
	 * @param major
	 */
	void saveMajor(Major major);
	/**
	 * 更新专业
	 * @param major
	 */
	void updateMajor(Major major);
	/**
	 * 删除专业
	 * @param ids
	 * @return
	 */
	Integer deleteMajor(String[] ids);
	/**
	 * 查询某个院系下专业集合
	 * @return
	 */
	List<Major> ListMajor(Long yardId);
	/**
	 * 模糊查询某个院系下的专业集合
	 * @param majorName
	 * @param yardId
	 * @return
	 */
	List<Major> ListMajor(String majorName, Long yardId);
	/**
	 * 根据id获取专业信息
	 * @param id
	 * @return
	 */
	Major getMajorById(Long id);
}
