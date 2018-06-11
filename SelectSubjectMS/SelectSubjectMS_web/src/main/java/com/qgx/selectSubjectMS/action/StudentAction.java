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
import com.qgx.selectSubjectMS.entity.TeacherAssign;
import com.qgx.selectSubjectMS.entity.User;
import com.qgx.selectSubjectMS.service.SelectSubjectService;
import com.qgx.selectSubjectMS.service.SettingService;
import com.qgx.selectSubjectMS.service.StudentService;
import com.qgx.selectSubjectMS.service.SubjectService;
import com.qgx.selectSubjectMS.service.TeacherAssignService;
import com.qgx.selectSubjectMS.service.UserService;
import com.qgx.selectSubjectMS.utils.ResponseUtil;
import com.qgx.selectSubjectMS.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("protoType")
public class StudentAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private Student student;
	@Resource
	private StudentService studentService;
	@Resource
	private UserService userService;
	@Resource
	private SubjectService subjectService;
	@Resource
	private SelectSubjectService selectSubjectService;
	@Resource
	private SettingService settingService;
	@Resource
	private TeacherAssignService teacherAssignService;

	public void updatePersonalInfo() throws Exception {
		// ��ȡSession�еĸ���ʵ�壬Ȼ���޸Ĳ�����
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");
		currentStudent.setRealName(student.getRealName());
		currentStudent.setPhone(currentStudent.getPhone());
		currentStudent.setEmail(student.getEmail());
		studentService.updateStudent(currentStudent); // �޸����ݿ��еĳ־û�����
		request.getSession().setAttribute("currentUser", currentStudent); // �޸�Session���������
	}

	/*
	 * �޸�����
	 */
	public String modifyPwd() throws Exception {
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");
		// ����id�������ݿ���û�
		User user = userService.findUserById(currentStudent.getUser().getId());
		JSONObject jsonObject = new JSONObject();
		// �Ƚ�ԭ�����Ƿ���ȷ
		if (!user.getPassword().equals(StringUtil.encryptMd5(password))) {
			jsonObject.put("result", "0");
			jsonObject.put("message", "ԭ�������벻��ȷ��");
		} else {
			user.setPassword(StringUtil.encryptMd5(rePassword));
			userService.updateUser(user);
			jsonObject.put("result", "1");
			jsonObject.put("message", "�޸ĳɹ�!");
		}
		ResponseUtil.write(jsonObject.toString());
		return null;
	}

	/**
	 * 
	 * ѧ���鿴��Ժ������ѡ�⣨���ͨ���ģ�
	 */
	public void subjectList() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// ��ȡ������ѯ����Ϣ
		String teacherName = request.getParameter("teacherName"); // ��ʦ����
		String title = request.getParameter("title"); // ����
		String direction = request.getParameter("direction"); // ����
		String state = "2"; // ���״̬����ѧ����˵�ǹ̶��ģ����ͨ���ſ��õ�
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubject(teacherName,
				currentStudent.getSchoolClass().getMajor().getYard().getId(), title, direction, state); // ��ȡ��Ŀ����
		List<Subject> subjectList = subjectService.listSubject(teacherName,
				currentStudent.getSchoolClass().getMajor().getYard().getId(), title, direction, state, page, rows);

		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", s.getId());
			// ������Ŀid��ѧ��id��ȡѡ��־ԸΨһ��¼
			SelectSubject currentSelectSubject = selectSubjectService.getSelectSubjectBySubjectIdAndStudentId(s.getId(),
					currentStudent.getId());
			if (currentSelectSubject != null) {
				jsonObject.put("selectSubjectId", currentSelectSubject.getId());
				jsonObject.put("studentLevel", currentSelectSubject.getStudentLevel());
			} else {
				jsonObject.put("selectSubjectId", "0");
				jsonObject.put("studentLevel", "");
			}
			jsonObject.put("teacherName", s.getTeacher().getRealName());
			jsonObject.put("title", s.getTitle());
			Integer selectNum = selectSubjectService.countSelectStudentBySubjectId(s.getId());
			jsonObject.put("selectNum", selectNum);
			jsonObject.put("direction", s.getDirection());
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * �첽��ȡ��ѡ�����Ϣ
	 */
	public void getSubjectById() throws Exception {
		String subjectId = request.getParameter("subjectId");
		JSONObject result = new JSONObject();
		Integer flag = 0; // ����̶�
		// �����ǰ���ǳ���ѡ�⣬�޸�ѡ��ʱ�����ھ���
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");
		SelectSubject selectSubject = selectSubjectService
				.getSelectSubjectBySubjectIdAndStudentId(Long.valueOf(subjectId), currentStudent.getId());
		if (selectSubject != null) {
		} else {
			Setting currentSetting = currentStudent.getSchoolClass().getMajor().getYard().getSetting();
			Integer selectNum = selectSubjectService.countSelectStudentBySubjectId(Long.valueOf(subjectId));
			if (selectNum < Integer.parseInt(currentSetting.getWarnNum())) {
				flag = 0;
			} else if (Integer.parseInt(currentSetting.getWarnNum()) <= selectNum
					&& selectNum < Integer.parseInt(currentSetting.getMaxSelectNum())) {
				flag = 1;
			} else
				flag = 2;
		}
		Subject currentSubject = subjectService.getSubjectById(subjectId);
		result.put("flag", flag);
		result.put("subjectId", subjectId);
		result.put("title", currentSubject.getTitle());
		ResponseUtil.write(result.toString());
	}

	/*
	 * �첽��ʼ��־Ը������
	 */
	public void levelComboBox() throws Exception {
		String subjectId = request.getParameter("subjectId");
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");
		// ��ȡѧ�����ѡ����Ŀ��Ϊ־Ը����
		int maxStuSelectNum = Integer
				.parseInt(currentStudent.getSchoolClass().getMajor().getYard().getSetting().getMaxStuSelectNum());
		SelectSubject currentSelectSubject = selectSubjectService
				.getSelectSubjectBySubjectIdAndStudentId(Long.valueOf(subjectId), currentStudent.getId());

		int targetLevel = 0; // ָʾ��ָʾ��ǰ״̬ 0�����ѡ־Ը�����������޸�
		if (currentSelectSubject != null) {
			targetLevel = Integer.parseInt(currentSelectSubject.getStudentLevel());
		}

		List<SelectSubject> selectSubjectList = selectSubjectService
				.listSelectSubjectByStudentId(currentStudent.getId());
		JSONArray result = new JSONArray();
		JSONObject firstObject = new JSONObject();
		firstObject.put("studentLevel", "0");
		firstObject.put("levelDesc", "��ѡ��");
		result.add(firstObject);
		for (int i = 1; i <= maxStuSelectNum; i++) {
			boolean flag = false; // false�����i־Ըδ��ѡ,true�����ѱ�ѡ
			arr: for (SelectSubject ss : selectSubjectList) {
				if (i == Integer.parseInt(ss.getStudentLevel()) & i != targetLevel) {
					flag = true;
					break arr;
				}
			}

			if (flag == false) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("studentLevel", i);
				jsonObject.put("levelDesc", "��" + i + "־Ը");
				result.add(jsonObject);
			}
		}
		ResponseUtil.write(result.toString());
	}
	/*
	 * �޸�ѧ��־Ը
	 */
	public void updateLevel() throws Exception {
		String subjectId = request.getParameter("subjectId");
		String studentLevel = request.getParameter("studentLevel");
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");
		JSONObject result = new JSONObject();
		// ѧ�����ѡ����Ŀ�����ﵽ����ѡ��
		Integer maxStuSelectNum = Integer.parseInt(currentStudent.getSchoolClass().getMajor().getYard().getSetting().getMaxStuSelectNum());
		Integer stuSelectNum = selectSubjectService.countSelectSubjectByStudentId(currentStudent.getId());
		if (stuSelectNum == maxStuSelectNum) {
			result.put("warn", "1");
		} else {
			SelectSubject currentSelectSubject = selectSubjectService
					.getSelectSubjectBySubjectIdAndStudentId(Long.valueOf(subjectId), currentStudent.getId());

			if (currentSelectSubject == null) {
				// �����ڣ�����Ҫ�½��������ָ��
				currentSelectSubject = new SelectSubject();
				currentSelectSubject.setSubject(subjectService.getSubjectById(subjectId));
				currentSelectSubject.setStudent(currentStudent);
			}
			currentSelectSubject.setStudentLevel(studentLevel);
			selectSubjectService.saveOrUpDateSelectSubject(currentSelectSubject);
		}
		ResponseUtil.write(result.toString());
	}
	/*
	 * ɾ��־Ը
	 */
	public void deleteLevel()throws Exception {
		String subjectId = request.getParameter("subjectId");
		JSONObject result = new JSONObject();
		TeacherAssign teacherAssign = teacherAssignService.getTeacherAssignBySubjectId(Long.valueOf(subjectId));
		Integer num = 0;
		//����ʦ��û�����ʱ��ɾ����Ч������ɾ����Ч
		if (teacherAssign == null) {
			Student currentStudent = (Student)request.getSession().getAttribute("currentUser");
			SelectSubject selectSubject = selectSubjectService.getSelectSubjectBySubjectIdAndStudentId(Long.valueOf(subjectId), currentStudent.getId());
			selectSubjectService.deleteSelectSubject(selectSubject);
			num++;
		}
		result.put("num", num);
		ResponseUtil.write(result.toString());
	}
	/*
	 * �鿴��ѡ��Ŀ���Ƿ����
	 */
	public void choosedSubjectList() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");

		List<SelectSubject> selectSubjectList = selectSubjectService
				.listSelectSubjectByStudentId(currentStudent.getId());
		Integer total = selectSubjectService.countSelectSubjectByStudentId(currentStudent.getId());
		JSONArray jsonArray = new JSONArray();
		for (SelectSubject ss : selectSubjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", ss.getSubject().getId());
			jsonObject.put("selectSubjectId", ss.getId());
			jsonObject.put("teacherName", ss.getSubject().getTeacher().getRealName());
			jsonObject.put("title", ss.getSubject().getTitle());
			jsonObject.put("direction", ss.getSubject().getDirection());
			jsonObject.put("studentLevel", ss.getStudentLevel());
			TeacherAssign teacherAssign = teacherAssignService.getUniqueTeacherAssign(ss.getSubject().getId());
			if (teacherAssign != null && teacherAssign.getStudent().getId() == ss.getStudent().getId()) {
				jsonObject.put("ischoosed", "��ѡ��");
			} else {
				jsonObject.put("ischoosed", "δ����");
			}
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * ���յ�ѡ����
	 */
	public void subjectResult() throws Exception {
		Student currentStudent = (Student) request.getSession().getAttribute("currentUser");
		TeacherAssign teacherAssign = teacherAssignService.getTeacherAssignByStudentId(currentStudent.getId());
		JSONArray jsonArray = new JSONArray();
		if (teacherAssign != null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", teacherAssign.getSubject().getId());
			jsonObject.put("teacherName", teacherAssign.getSubject().getTeacher().getRealName());
			jsonObject.put("direction", teacherAssign.getSubject().getDirection());
			jsonObject.put("title", teacherAssign.getSubject().getTitle());
			SelectSubject selectSubject = selectSubjectService.getSelectSubjectBySubjectIdAndStudentId(
					teacherAssign.getSubject().getId(), currentStudent.getId());
			jsonObject.put("studentLevel", selectSubject.getStudentLevel());
			jsonObject.put("ischoosed", "ѡ��");
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", "1");
		ResponseUtil.write(result.toString());
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
