package com.qgx.selectSubjectMS.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Student;
import com.qgx.selectSubjectMS.entity.User;

public class UserRealm extends AuthorizingRealm{
	@Resource(name="baseDao")
	private BaseDao<User>userDao;
	@Resource
	private BaseDao<Student>studentDao;
	//认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//传进来的接口向下转为实现类
		UsernamePasswordToken passwordToken=(UsernamePasswordToken) token;
		//通过实现类获取页面输入的用户名
		  String userName = passwordToken.getUsername();
		  //通过用户名查询数据库中的该用户信息
		  User user=userDao.get("from User where userName=?", new Object[]{userName});
		  //用户不存在，直接返回失败
		  if(user==null){
			  return null;
		  }	
		  //如果用户的类型是学生，还要判断毕业年份。
		  if ("Student".equals(user.getUserType())) {
			  Student student = studentDao.get("from Student s where s.user.id = ?", new Object[]{user.getId()});
			  if (! student.getGraduateYear().equals(student.getSchoolClass().getMajor().getYard().getSetting().getCurrentGrade())) {
				  return null;
			  }
		  }
		  //用过查询的用户构建简单认证对象,shiro框架帮我们进行验证
		  AuthenticationInfo info=new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}
	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//创建一个简单授权信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//从认证体中取出认证通过的用户对象
		User user = (User)principals.getPrimaryPrincipal();
		switch (user.getUserType()) {
		 case "Admin" : 
			 info.addRole("admin");
			 break;
		 case "DeptHead" :
			 info.addRole("deptHead");
			 break;
		 case "Teacher" :
			 info.addRole("teacher");
			 break;
		 case "Student" :
			 info.addRole("student");
			 break;
		}
		return info;
	}
	

	

}
