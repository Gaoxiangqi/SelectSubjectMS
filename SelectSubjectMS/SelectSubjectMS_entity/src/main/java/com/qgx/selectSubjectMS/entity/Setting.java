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
 * 院系通用设置
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
	private String warnNum;  //志愿扎堆提醒,默认为3
	private String maxSelectNum; //每道题的最大选择人数
	private String maxStuSelectNum; //学生的最多选题个数
	private String maxSetNum; //老师最大出题数,默认为6
	private String currentGrade; //设置当前年级
	private String teacherSetStartTime; //老师出题开始时间
	private String teacherSetEndTime; //老师出题结束时间
	private String studentSelectStartTime; //学生选择开始时间
	private String studentSelectEndTime; //学生选择结束时间
	private String teacherSelectStartTime; //老师选择开始时间
	private String teacherSelectEndTime;  //老师选择结束时 间
	private String deptHeadAdjustStartTime; //系主任调整开始时间
	private String deptHeadAdjustEndTime;  //系主任调整结束时间
	private Yard yard;  //该设置属于哪个学院
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
