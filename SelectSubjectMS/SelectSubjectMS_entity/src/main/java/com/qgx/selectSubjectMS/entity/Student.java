package com.qgx.selectSubjectMS.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ѧ��ʵ��
 * @author goxcheer
 *
 */
@Entity
@Table(name="t_student")
public class Student implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private User user; //�û�����
    private String realName; //��ʵ����
    private SchoolClass schoolClass; //�༶����
    private String sex; //�Ա�
    private String phone; //�绰
    private String email; //����
    private String graduateYear; //��ҵ���
    @Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToOne(cascade={CascadeType.REMOVE})
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "realName")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	@ManyToOne
	@JoinColumn(name="classId")
	public SchoolClass getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="graduateYear")
	public String getGraduateYear() {
		return graduateYear;
	}
	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}
    
}
