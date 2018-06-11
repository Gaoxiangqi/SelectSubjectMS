package com.qgx.selectSubjectMS.service;

import com.qgx.selectSubjectMS.entity.DeptHead;
import com.qgx.selectSubjectMS.entity.User;

public interface UserService {
    
	/**
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	User findUserById(Long id);
	/**
	 * 更新用户
	 * @param user
	 */
	void updateUser(User user);
    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
	User getUserByUserName(String userName);
    /**
     * 保存用户
     * @param user
     */
	void saveUser(User user);
	

}
