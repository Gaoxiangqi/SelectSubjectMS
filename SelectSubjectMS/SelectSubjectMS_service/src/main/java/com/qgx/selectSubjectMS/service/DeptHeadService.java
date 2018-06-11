package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.DeptHead;

/**
 * 系主任功能接口
 * @author goxcheer
 *
 */
public interface DeptHeadService {
	/**
	 * 根据用户Id获取系主任
	 * @param userId
	 * @return
	 */
	DeptHead findDeptHeadByUserId(Long userId);
    /**
     * 查询系主任集合
     * @return
     */
	List<DeptHead> listDeptHead();
    /**
     * 插入保存系主任
     * @param deptHead
     */
	void saveDeptHead(DeptHead deptHead);
	/**
	 * 更新系主任
	 * @param currentDeptHead
	 */
	void updateDeptHead(DeptHead currentDeptHead);
	/**
	 * 带条件和分页查询系主任集合
	 * @param page
	 * @param rows
	 * @param deptHeadName
	 * @param yardId
	 * @return
	 */
	List<DeptHead> ListDeptHead(Integer page, Integer rows, String deptHeadName, String yardId);
	/**
	 * 带条件统计系主任人数
	 * @param deptHeadName
	 * @param yardId
	 * @return
	 */
	int countDeptHead(String deptHeadName, String yardId);
	/**
	 * 根据系主任id获取系主任
	 * @param id
	 * @return
	 */
	DeptHead getDeptHeadById(Long id);
	/**
	 * 删除系主任
	 * @param ids
	 * @return
	 */
	Integer deleteDeptHead(String[] ids);
	
    
}
