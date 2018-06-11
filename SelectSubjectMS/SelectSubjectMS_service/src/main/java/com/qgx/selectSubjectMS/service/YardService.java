package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Yard;

/**
 * 学院数据接口
 * @author Administrator
 *
 */
public interface YardService {
	/**
	 * 统计院系的个数
	 * @return
	 */
	int countYard();
	/**
	 * 分页查询所有院系
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Yard> ListYard(Integer page, Integer rows);
	/**
	 * 添加院系
	 * @param yard
	 * @return
	 */
	Integer saveYard(Yard yard);
	/**
	 * 删除学院
	 * @param ids
	 * @return
	 */
	Integer deleteYard(String[] ids);
	
	/**
	 *获取所有院系信息初始化下拉框 
	 * @return
	 */
	List<Yard> ListYard();
	/**
	 * 根据id获取学院
	 * @param id
	 * @return
	 */
	Yard getYardById(String id);
	/**
	 * 根据院系名称获取学院
	 * @param yardName
	 * @return
	 */
	Yard getYardByYardName(String yardName);
	/**
	 * 更新学院
	 * @param yard
	 */
	void updateYard(Yard yard);

}