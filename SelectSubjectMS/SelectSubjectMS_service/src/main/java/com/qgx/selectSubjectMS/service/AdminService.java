package com.qgx.selectSubjectMS.service;

import com.qgx.selectSubjectMS.entity.Admin;

public interface AdminService {
	/**
	 * �����û�id��ȡ��Ӧ�Ĺ���Ա
	 * @param userId
	 * @return
	 */
	Admin findAdminByUserId(Long userId);
	/**
	 * ���¹���Ա��Ϣ
	 * @param currentAdmin
	 */
	void updateAdmin(Admin currentAdmin);
	

	

	

	

	

}
