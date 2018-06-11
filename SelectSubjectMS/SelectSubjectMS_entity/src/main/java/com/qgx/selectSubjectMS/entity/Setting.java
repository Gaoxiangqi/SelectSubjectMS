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
 * Ժϵͨ������
 * @author goxcheer
 *
 */
@Entity
@Table(name="t_setting")
public class Setting implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String warnNum;  //־Ը��������,Ĭ��Ϊ3
	private String maxSelectNum; //ÿ��������ѡ������
	private String maxStuSelectNum; //ѧ�������ѡ�����
	private String maxSetNum; //��ʦ��������,Ĭ��Ϊ6
	private String currentGrade; //���õ�ǰ�꼶
	private String teacherSetStartTime; //��ʦ���⿪ʼʱ��
	private String teacherSetEndTime; //��ʦ�������ʱ��
	private String studentSelectStartTime; //ѧ��ѡ��ʼʱ��
	private String studentSelectEndTime; //ѧ��ѡ�����ʱ��
	private String teacherSelectStartTime; //��ʦѡ��ʼʱ��
	private String teacherSelectEndTime;  //��ʦѡ�����ʱ ��
	private String deptHeadAdjustStartTime; //ϵ���ε�����ʼʱ��
	private String deptHeadAdjustEndTime;  //ϵ���ε�������ʱ��
	private Yard yard;  //�����������ĸ�ѧԺ
	@Id
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="warnNum")
	public String getWarnNum() {
		return warnNum;
	}
	public void setWarnNum(String warnNum) {
		this.warnNum = warnNum;
	}
	@Column(name="maxSelectNum")
	public String getMaxSelectNum() {
		return maxSelectNum;
	}
	public void setMaxSelectNum(String maxSelectNum) {
		this.maxSelectNum = maxSelectNum;
	}
	@Column(name="maxStuSelectNum")
	public String getMaxStuSelectNum() {
		return maxStuSelectNum;
	}
	public void setMaxStuSelectNum(String maxStuSelectNum) {
		this.maxStuSelectNum = maxStuSelectNum;
	}
	@Column(name="maxSetNum")
	public String getMaxSetNum() {
		return maxSetNum;
	}
	public void setMaxSetNum(String maxSetNum) {
		this.maxSetNum = maxSetNum;
	}
	@Column(name="currentGrade")
	public String getCurrentGrade() {
		return currentGrade;
	}
	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade;
	}
	@Column(name="teacherSetStartTime")
	public String getTeacherSetStartTime() {
		return teacherSetStartTime;
	}
	public void setTeacherSetStartTime(String teacherSetStartTime) {
		this.teacherSetStartTime = teacherSetStartTime;
	}
	@Column(name="teacherSetEndTime")
	public String getTeacherSetEndTime() {
		return teacherSetEndTime;
	}
	public void setTeacherSetEndTime(String teacherSetEndTime) {
		this.teacherSetEndTime = teacherSetEndTime;
	}
	@Column(name="studentSelectStartTime")
	public String getStudentSelectStartTime() {
		return studentSelectStartTime;
	}
	public void setStudentSelectStartTime(String studentSelectStartTime) {
		this.studentSelectStartTime = studentSelectStartTime;
	}
	@Column(name="studentSelectEndTime")
	public String getStudentSelectEndTime() {
		return studentSelectEndTime;
	}
	public void setStudentSelectEndTime(String studentSelectEndTime) {
		this.studentSelectEndTime = studentSelectEndTime;
	}
	@Column(name="teacherSelectStartTime")
	public String getTeacherSelectStartTime() {
		return teacherSelectStartTime;
	}
	public void setTeacherSelectStartTime(String teacherSelectStartTime) {
		this.teacherSelectStartTime = teacherSelectStartTime;
	}
	@Column(name="teacherSelectEndTime")
	public String getTeacherSelectEndTime() {
		return teacherSelectEndTime;
	}
	public void setTeacherSelectEndTime(String teacherSelectEndTime) {
		this.teacherSelectEndTime = teacherSelectEndTime;
	}
	@Column(name="deptHeadAdjustStartTime")
	public String getDeptHeadAdjustStartTime() {
		return deptHeadAdjustStartTime;
	}
	public void setDeptHeadAdjustStartTime(String deptHeadAdjustStartTime) {
		this.deptHeadAdjustStartTime = deptHeadAdjustStartTime;
	}
	@Column(name="deptHeadAdjustEndTime")
	public String getDeptHeadAdjustEndTime() {
		return deptHeadAdjustEndTime;
	}
	public void setDeptHeadAdjustEndTime(String deptHeadAdjustEndTime) {
		this.deptHeadAdjustEndTime = deptHeadAdjustEndTime;
	}
	
	@OneToOne
	@JoinColumn(name="yardId")
	public Yard getYard() {
		return yard;
	}
	public void setYard(Yard yard) {
		this.yard = yard;
	}
	
	
}
