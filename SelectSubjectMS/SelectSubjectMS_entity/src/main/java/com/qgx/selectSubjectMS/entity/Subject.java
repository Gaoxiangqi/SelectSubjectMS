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
 * ��Ŀʵ����
 * @author goxcheer
 *
 */
@Entity
@Table(name="t_subject")
public class Subject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;  //����
	private Teacher teacher; //������
	private String direction; //����
	private String keyWords; //�ؼ���
	private String summary;  //��Ŀ���
	private String schedule; //���Ȱ���
	private String referDoc; //�ο��ĵ�
	private String studentRequire; //ѧ��Ҫ��
	private String setTime; //����ʱ��
	private String state; //��Ŀ���״̬
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
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@ManyToOne
	@JoinColumn(name="teacherId")
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	@Column(name="direction")
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	@Column(name="keyWords")
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	@Column(name="summary")
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@Column(name="schedule")
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	@Column(name="referDoc")
	public String getReferDoc() {
		return referDoc;
	}
	public void setReferDoc(String referDoc) {
		this.referDoc = referDoc;
	}
	@Column(name="studentRequire")
	public String getStudentRequire() {
		return studentRequire;
	}
	public void setStudentRequire(String studentRequire) {
		this.studentRequire = studentRequire;
	}
	@Column(name="setTime")
	public String getSetTime() {
		return setTime;
	}
	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}
	@Column(name="state")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Subject [id=" + id + ", title=" + title + ", teacher=" + teacher + ", direction=" + direction
				+ ", keyWords=" + keyWords + ", summary=" + summary + ", schedule=" + schedule + ", referDoc="
				+ referDoc + ", studentRequire=" + studentRequire + ", setTime=" + setTime + ", state=" + state + "]";
	}	
}
