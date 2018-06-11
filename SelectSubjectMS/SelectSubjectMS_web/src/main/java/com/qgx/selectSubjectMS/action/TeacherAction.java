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
	 * ��ѯѧԺ��������Ŀ
	 */
	public void subjectListOfYard() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// ��ȡ������ѯ����Ϣ
		String teacherName = request.getParameter("teacherName"); // ��ʦ����
		String title = request.getParameter("title"); // ����
		String direction = request.getParameter("direction"); // ����
		String state = request.getParameter("state"); // ���״̬
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubject(teacherName, currentTeacher.getYard().getId(), title, direction, state); // ��ȡ��Ŀ����
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
				stateDesc = "δ���";
				break;
			case "2":
				stateDesc = "���ͨ��";
				break;
			case "3":
				stateDesc = "���δͨ��";
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
	 * �鿴������Ŀ����
	 */
	public void personalSubjectList() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// ��ȡ������ѯ����Ϣ
		String title = request.getParameter("title"); // ����
		String direction = request.getParameter("direction"); // ����
		String state = request.getParameter("state"); // ���״̬

		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");
		int total = subjectService.countSubjectByTeacherId(currentTeacher.getId(), title, direction, state); // ��ȡ��Ŀ����
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
				stateDesc = "δ���";
				break;
			case "2":
				stateDesc = "���ͨ��";
				break;
			case "3":
				stateDesc = "���δͨ��";
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
	 * ��ʦ�����Ŀ
	 */
	public void addSubject() throws Exception {
		JSONObject result = new JSONObject();
		//������ӵ�ǰ������ʦ����δ��������
		Integer maxSetNum = Integer.parseInt(settingService.getSetting().getMaxSetNum());
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");
		Integer setNum = subjectService.countSubjectByTeacherId(currentTeacher.getId(), null, null, null);
		if (setNum == maxSetNum) {
			result.put("warn", "1");
		} else {
			// �жϸñ����Ƿ��Ѵ���
			result.put("type", "add"); // ��ǰ�������Ŀ
			Subject currentSubject = subjectService.getSubjectByTitle(subject.getTitle());
			if (currentSubject != null) {
				result.put("num", "0"); // ��ǰ������ڣ���Ӳ��ɹ�Ϊ0��
			} else {
				subject.setTeacher(currentTeacher);
				if ("1".equals(subject.getDirection())) {
					subject.setDirection("Ӧ�÷���");
				} else {
					subject.setDirection("���۷���");
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
	 * ��ȡ����Ŀ��json����
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
	 * ��ʦ�޸���Ŀ
	 */
	public void updateSubject() throws Exception {
		JSONObject result = new JSONObject();
		result.put("type", "update");
		System.out.println(subject);
		// ����Id��ѯ��Ŀʵ�壬Ȼ���޸Ĳ�����
		String subjectId = request.getParameter("subjectId");
		Subject currentSubject = subjectService.getSubjectById(subjectId);
		currentSubject.setTitle(subject.getTitle());
		currentSubject.setKeyWords(subject.getKeyWords());
		if ("1".equals(subject.getDirection())) {
			currentSubject.setDirection("Ӧ�÷���");
		} else {
			currentSubject.setDirection("���۷���");
		}
		currentSubject.setSummary(subject.getSummary());
		currentSubject.setSchedule(subject.getSchedule());
		currentSubject.setReferDoc(subject.getReferDoc());
		currentSubject.setStudentRequire(subject.getStudentRequire());
		// ��ʵ���������·�����޸�
		subjectService.updateSubject(currentSubject);
		ResponseUtil.write(result.toString());

	}

	/*
	 * ɾ����Ŀ
	 */
	public void deleteSubject() throws Exception {
		String ids[] = request.getParameter("delIds").split(",");
		Integer delNums = subjectService.deleteSubject(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * ��ʦ�鿴���Է����ѡ��(�����ǰ������ͨ����ˣ����Ҹ���Ŀ����ѡ)
	 */
	public void assignSubjectList() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		Teacher currentTeacher = (Teacher) request.getSession().getAttribute("currentUser");

		List<Subject> subjectList = subjectService.listPersonalSubject(currentTeacher.getId(), null, null, "2", page,
				rows);
		int total = subjectService.countSubjectByTeacherId(currentTeacher.getId(), null, null, "2"); // ��ȡ��Ŀ����

		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			Integer selectNum = selectSubjectService.countSelectStudentBySubjectId(s.getId());
			if (selectNum == 0) {
				total--; // ����Ŀû��ѡʱ,�ü�¼����ʾ��ͳ�Ƶļ�¼����Ҫ��1
				continue; // �����ôα���
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", s.getId());
			jsonObject.put("title", s.getTitle());
			jsonObject.put("selectNum", selectNum);
			// �������Ϊ�գ���Ϊ��Ŀ����û����
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
	 * ������Ŀid�鿴ѡ���������ѧ��(ǰ������ѧ��ѡ�������assignSubjectList()�ѱ�֤)
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
	 * ָ��ѧ��
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
		//��ѧ���Ѿ�������ѡ��ָ�ɱ��У�ѧ���ǲ�����ָ��һ�ε�
		TeacherAssign ta = teacherAssignService.getTeacherAssignByStudentId(currentStudent.getId());
		Integer flag = 1; //1�������
		if (ta != null) {
			jsonObject.put("isExist", "1");
		} else {
			TeacherAssign currentTeacherAssign = teacherAssignService.getUniqueTeacherAssign(Long.valueOf(subjectId));
			if (currentTeacherAssign == null) {
				flag = 0; //0�����һ�����
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
		// ��ȡ��ҳ��ѯ����Ϣ
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
