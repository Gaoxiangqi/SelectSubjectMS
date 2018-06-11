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
 * 专业
 * @author Administrator
 *
 */
@Entity
@Table(name="t_major")
public class Major implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String majorName; //专业名称
	private String majorDesc; //专业描述
	private Yard yard; //所属院系
	
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
	
	@Column(name="majorName")
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	
	@Column(name="majorDesc")
	public String getMajorDesc() {
		return majorDesc;
	}
	public void setMajorDesc(String majorDesc) {
		this.majorDesc = majorDesc;
	}
	
	@ManyToOne
	@JoinColumn(name="yardId")
	public Yard getYard() {
		return yard;
	}
	public void setYard(Yard yard) {
		this.yard = yard;
	}
	
	

}
