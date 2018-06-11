package com.qgx.selectSubjectMS.service;

import com.qgx.selectSubjectMS.entity.Admin;

public interface AdminService {
	/**
	 * 根据用户id获取对应的管理员
	 * @param userId
	 * @return
	 */
	Admin findAdminByUserId(Long userId);
	/**
	 * 更新管理员信息
	 * @param currentAdmin
	 */
	void updateAdmin(Admin currentAdmin);
	

	

	

	

	

}
