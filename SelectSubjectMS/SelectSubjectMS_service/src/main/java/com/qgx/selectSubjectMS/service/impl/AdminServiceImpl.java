package com.qgx.selectSubjectMS.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Admin;
import com.qgx.selectSubjectMS.service.AdminService;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	@Resource
	private BaseDao<Admin> adminDao;

	
	
	@Override
	public Admin findAdminByUserId(Long userId) {
		return adminDao.get("from Admin a where a.user.id = ? ", new Object[]{userId});
	}
	
	@Override
	public void updateAdmin(Admin currentAdmin) {
		adminDao.update(currentAdmin);

	}

}