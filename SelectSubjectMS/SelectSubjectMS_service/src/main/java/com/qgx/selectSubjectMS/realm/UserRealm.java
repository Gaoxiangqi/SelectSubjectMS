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
	//��֤
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//�������Ľӿ�����תΪʵ����
		UsernamePasswordToken passwordToken=(UsernamePasswordToken) token;
		//ͨ��ʵ�����ȡҳ��������û���
		  String userName = passwordToken.getUsername();
		  //ͨ���û�����ѯ���ݿ��еĸ��û���Ϣ
		  User user=userDao.get("from User where userName=?", new Object[]{userName});
		  //�û������ڣ�ֱ�ӷ���ʧ��
		  if(user==null){
			  return null;
		  }	
		  //����û���������ѧ������Ҫ�жϱ�ҵ��ݡ�
		  if ("Student".equals(user.getUserType())) {
			  Student student = studentDao.get("from Student s where s.user.id = ?", new Object[]{user.getId()});
			  if (! student.getGraduateYear().equals(student.getSchoolClass().getMajor().getYard().getSetting().getCurrentGrade())) {
				  return null;
			  }
		  }
		  //�ù���ѯ���û���������֤����,shiro��ܰ����ǽ�����֤
		  AuthenticationInfo info=new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}
	//��Ȩ
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//����һ������Ȩ��Ϣ
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//����֤����ȡ����֤ͨ�����û�����
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
