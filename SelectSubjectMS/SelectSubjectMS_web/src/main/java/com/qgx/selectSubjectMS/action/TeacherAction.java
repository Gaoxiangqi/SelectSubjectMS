package com.qgx.selectSubjectMS.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.qgx.selectSubjectMS.entity.SelectSubject;
import com.qgx.selectSubjectMS.entity.Setting;
import com.qgx.selectSubjectMS.entity.Student;
import com.qgx.selectSubjectMS.entity.Subject;
import com.qgx.selectSubjectMS.entity.Teacher;
import com.qgx.selectSubjectMS.entity.TeacherAssign;
import com.qgx.selectSubjectMS.service.SelectSubjectService;
import com.qgx.selectSubjectMS.service.SettingService;
import com.qgx.selectSubjectMS.service.StudentService;
import com.qgx.selectSubjectMS.service.SubjectService;
import com.qgx.selectSubjectMS.service.TeacherAssignService;
import com.qgx.selectSubjectMS.utils.DateUtil;
import com.qgx.selectSubjectMS.utils.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@Controller
@Scope("protoType")
public class TeacherAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	@Resource
	private SubjectService subjectService;
	@Resource
	private SelectSubjectService selectSubjectService;
	@Resource
	private TeacherAssignService teacherAssignService;
	@Resource
	private StudentService studentService;
	@Resource
	private SettingService settingService;

	private Subject subject;

	/*
	 * 查询学院的所有题目
	 */
	public void subjectListOfYard() throws Exception {
		// 获取分页查询的信息
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// 获取条件查询的信息
		String teacherName = request.getParameter("teacherName"); // 老师姓名
		String title = request.getParameter("title"); // 标题
		String direction = request.getParameter("direction"); // 方向
		String state = request.getParameter("state"); // 审核状态
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubject(teacherName, currentTeacher.getYard().getId(), title, direction, state); // 获取题目总数
		List<Subject> subjectList = subjectService.listSubject(teacherName, currentTeacher.getYard().getId(), title,
				direction, state, page, rows);

		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", s.getId());
			jsonObject.put("yardId", s.getTeacher().getYard().getId());
			jsonObject.put("yardName", s.getTeacher().getYard().getYardName());
			jsonObject.put("teacherName", s.getTeacher().getRealName());
			jsonObject.put("title", s.getTitle());
			Integer selectNum = selectSubjectService.countSelectStudentBySubjectId(s.getId());
			jsonObject.put("selectNum", selectNum);
			jsonObject.put("direction", s.getDirection());
			String stateDesc = "";
			switch (s.getState()) {
			case "1":
				stateDesc = "未审核";
				break;
			case "2":
				stateDesc = "审核通过";
				break;
			case "3":
				stateDesc = "审核未通过";
			}
			jsonObject.put("state", stateDesc);
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * 查看个人题目集合
	 */
	public void personalSubjectList() throws Exception {
		// 获取分页查询的信息
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// 获取条件查询的信息
		String title = request.getParameter("title"); // 标题
		String direction = request.getParameter("direction"); // 方向
		String state = request.getParameter("state"); // 审核状态

		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");
		int total = subjectService.countSubjectByTeacherId(currentTeacher.getId(), title, direction, state); // 获取题目总数
		List<Subject> subjectList = subjectService.listPersonalSubject(currentTeacher.getId(), title, direction, state,
				page, rows);

		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", s.getId());
			jsonObject.put("yardId", s.getTeacher().getYard().getId());
			jsonObject.put("yardName", s.getTeacher().getYard().getYardName());
			jsonObject.put("teacherName", s.getTeacher().getRealName());
			jsonObject.put("title", s.getTitle());
			Integer selectNum = selectSubjectService.countSelectStudentBySubjectId(s.getId());
			jsonObject.put("selectNum", selectNum);
			jsonObject.put("direction", s.getDirection());
			String stateDesc = "";
			switch (s.getState()) {
			case "1":
				stateDesc = "未审核";
				break;
			case "2":
				stateDesc = "审核通过";
				break;
			case "3":
				stateDesc = "审核未通过";
			}
			jsonObject.put("state", stateDesc);
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * 老师添加题目
	 */
	public void addSubject() throws Exception {
		JSONObject result = new JSONObject();
		//可以添加的前提是老师出题未超出上限
		Integer maxSetNum = Integer.parseInt(settingService.getSetting().getMaxSetNum());
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");
		Integer setNum = subjectService.countSubjectByTeacherId(currentTeacher.getId(), null, null, null);
		if (setNum == maxSetNum) {
			result.put("warn", "1");
		} else {
			// 判断该标题是否已存在
			result.put("type", "add"); // 当前是添加题目
			Subject currentSubject = subjectService.getSubjectByTitle(subject.getTitle());
			if (currentSubject != null) {
				result.put("num", "0"); // 当前标题存在，添加不成功为0个
			} else {
				subject.setTeacher(currentTeacher);
				if ("1".equals(subject.getDirection())) {
					subject.setDirection("应用方向");
				} else {
					subject.setDirection("理论方向");
				}
				subject.setState("1");
				subject.setSetTime(DateUtil.getDate());
				subjectService.saveSubject(subject);
				result.put("num", "1");
			}
		}
		ResponseUtil.write(result.toString());
	}

	/*
	 * 获取该题目的json数据
	 */
	public void getSubjectOfJSON() throws Exception {
		Long subjectId = Long.valueOf(request.getParameter("subjectId"));
		Subject currentSubject = subjectService.getSubjectOfJSONById(subjectId);

		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONObject result = JSONObject.fromObject(currentSubject, config);
		ResponseUtil.write(result.toString());
	}

	/*
	 * 老师修改题目
	 */
	public void updateSubject() throws Exception {
		JSONObject result = new JSONObject();
		result.put("type", "update");
		System.out.println(subject);
		// 根据Id查询题目实体，然后修改并保存
		String subjectId = request.getParameter("subjectId");
		Subject currentSubject = subjectService.getSubjectById(subjectId);
		currentSubject.setTitle(subject.getTitle());
		currentSubject.setKeyWords(subject.getKeyWords());
		if ("1".equals(subject.getDirection())) {
			currentSubject.setDirection("应用方向");
		} else {
			currentSubject.setDirection("理论方向");
		}
		currentSubject.setSummary(subject.getSummary());
		currentSubject.setSchedule(subject.getSchedule());
		currentSubject.setReferDoc(subject.getReferDoc());
		currentSubject.setStudentRequire(subject.getStudentRequire());
		// 将实体数据重新放入后修改
		subjectService.updateSubject(currentSubject);
		ResponseUtil.write(result.toString());

	}

	/*
	 * 删除题目
	 */
	public void deleteSubject() throws Exception {
		String ids[] = request.getParameter("delIds").split(",");
		Integer delNums = subjectService.deleteSubject(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * 老师查看可以分配的选题(分配的前提是已通过审核，并且该题目有人选)
	 */
	public void assignSubjectList() throws Exception {
		// 获取分页查询的信息
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");

		List<Subject> subjectList = subjectService.listPersonalSubject(currentTeacher.getId(), null, null, "2", page,
				rows);
		int total = subjectService.countSubjectByTeacherId(currentTeacher.getId(), null, null, "2"); // 获取题目总数

		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			Integer selectNum = selectSubjectService.countSelectStudentBySubjectId(s.getId());
			if (selectNum == 0) {
				total--; // 当题目没人选时,该记录不显示，统计的记录数需要减1
				continue; // 跳过该次遍历
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", s.getId());
			jsonObject.put("title", s.getTitle());
			jsonObject.put("selectNum", selectNum);
			// 这里可能为空，因为题目可能没分配
			TeacherAssign teacherAssign = teacherAssignService.getTeacherAssignBySubjectId(s.getId());
			if (teacherAssign == null) {
				jsonObject.put("studentId", "0");
				jsonObject.put("studentName", "");
			} else {
				jsonObject.put("studentId", teacherAssign.getStudent().getId());
				jsonObject.put("studentName", teacherAssign.getStudent().getRealName());
			}
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());

	}

	/*
	 * 根据题目id查看选该题的所有学生(前提是有学生选，这个在assignSubjectList()已保证)
	 */
	public void listStudentBySubjectId() throws Exception {	
		String subjectId = request.getParameter("subjectId");
		List<SelectSubject> selectSubjectList = selectSubjectService.listSelectSubjectBySubjectId(subjectId);
		JSONArray result = new JSONArray();
		for (SelectSubject ss : selectSubjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("studentId", ss.getStudent().getId());
			jsonObject.put("studentName", ss.getStudent().getRealName());
			jsonObject.put("studentLevel", ss.getStudentLevel());
			result.add(jsonObject);
		}
		ResponseUtil.write(result.toString());
		
	}
	/*
	 * 指派学生
	 */
	public void assignStudent() throws Exception {
		String studentName = request.getParameter("studentName");
		String subjectId = request.getParameter("subjectId");
		Student currentStudent = null;
		List<Student> studentList = studentService.listStudentByName(studentName);
		if (studentList.size() == 1) {
		     currentStudent = studentList.get(0);
		} else {
			List<SelectSubject>selectSubjectList = selectSubjectService.listSelectSubjectBySubjectId(subjectId);
			arr:for (Student s : studentList) {
				for (SelectSubject ss : selectSubjectList) {
					if (s.getId() == ss.getStudent().getId()) {
						currentStudent = s;
						break arr;
					}
				}
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		//当学生已经存在于选题指派表中，学生是不能再指派一次的
		TeacherAssign ta = teacherAssignService.getTeacherAssignByStudentId(currentStudent.getId());
		Integer flag = 1; //1代表更新
		if (ta != null) {
			jsonObject.put("isExist", "1");
		} else {
			TeacherAssign currentTeacherAssign = teacherAssignService.getUniqueTeacherAssign(Long.valueOf(subjectId));
			if (currentTeacherAssign == null) {
				flag = 0; //0代表第一次添加
				currentTeacherAssign = new TeacherAssign();
			}
			currentTeacherAssign.setSubject(subjectService.getSubjectById(subjectId));
			currentTeacherAssign.setStudent(currentStudent);
			teacherAssignService.saveOrUpdateAssign(currentTeacherAssign);
		}
		jsonObject.put("flag", flag);
		ResponseUtil.write(jsonObject.toString());
	}
	
	public void assignResult() throws Exception {
		// 获取分页查询的信息
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");
		List<Subject> subjectList = subjectService.listPersonalSubject(currentTeacher.getId(), null, null, "2", page, rows);
		Integer total = subjectService.countSubjectByTeacherId(currentTeacher.getId(), null, null, "2");
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("title", s.getTitle());
			TeacherAssign teacherAssign = teacherAssignService.getTeacherAssignBySubjectId(s.getId());
			if (teacherAssign == null) {
				jsonObject.put("studentName", "");
			} else {
				jsonObject.put("studentName", teacherAssign.getStudent().getRealName());
			}
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
