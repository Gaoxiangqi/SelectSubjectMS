package com.qgx.selectSubjectMS.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Ժϵʵ��
 * @author goxcheer
 *
 */
@Entity
@Table(name="t_yard")
public class Yard implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String yardName; //ѧԺ����
	private Setting setting; //ѡ��ͨ������
	
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
	@Column(name="yardName")
	public String getYardName() {
		return yardName;
	}
	public void setYardName(String yardName) {
		this.yardName = yardName;
	}
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="setId")
	public Setting getSetting() {
		return setting;
	}
	public void setSetting(Setting setting) {
		this.setting = setting;
	}	
	
}
