package com.qgx.selectSubjectMS.service;

import com.qgx.selectSubjectMS.entity.DeptHead;
import com.qgx.selectSubjectMS.entity.User;

public interface UserService {
    
	/**
	 * ����id��ȡ�û�
	 * @param id
	 * @return
	 */
	User findUserById(Long id);
	/**
	 * �����û�
	 * @param user
	 */
	void updateUser(User user);
    /**
     * �����û�����ȡ�û�
     * @param userName
     * @return
     */
	User getUserByUserName(String userName);
    /**
     * �����û�
     * @param user
     */
	void saveUser(User user);
	

}
