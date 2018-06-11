package com.qgx.selectSubjectMS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * �༶ʵ��
 * @author goxcheer
 *
 */
@Entity
@Table(name="t_class")
public class SchoolClass implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id; 
	private String className; //�༶����
	private Major major; //����רҵ
	@Id
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="className")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@ManyToOne
	@JoinColumn(name="majorId")
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	
	
}
