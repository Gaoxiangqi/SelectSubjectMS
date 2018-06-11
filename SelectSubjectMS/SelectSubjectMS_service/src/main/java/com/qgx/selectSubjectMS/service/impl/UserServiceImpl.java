package com.qgx.selectSubjectMS.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Admin;
import com.qgx.selectSubjectMS.entity.DeptHead;
import com.qgx.selectSubjectMS.entity.Student;
import com.qgx.selectSubjectMS.entity.Teacher;
import com.qgx.selectSubjectMS.entity.User;
import com.qgx.selectSubjectMS.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Resource	
	private BaseDao<Admin>adminDao;
	@Resource	
	private BaseDao<DeptHead>deptHeadDao;
	@Resource	
	private BaseDao<Teacher>teacherDao;
	@Resource	
	private BaseDao<Student>studentDao;
	@Resource
	private BaseDao<User>userDao;
	
	
	@Override
	public User findUserById(Long id) {
		return userDao.get(User.class, id);	
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDao.get("from User where userName=?", new Object[]{userName});
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

}
