package com.qgx.selectSubjectMS.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.qgx.selectSubjectMS.entity.DeptHead;
import com.qgx.selectSubjectMS.entity.Major;
import com.qgx.selectSubjectMS.entity.SchoolClass;
import com.qgx.selectSubjectMS.entity.Setting;
import com.qgx.selectSubjectMS.entity.Student;
import com.qgx.selectSubjectMS.entity.Subject;
import com.qgx.selectSubjectMS.entity.Teacher;
import com.qgx.selectSubjectMS.entity.TeacherAssign;
import com.qgx.selectSubjectMS.entity.User;
import com.qgx.selectSubjectMS.entity.Yard;
import com.qgx.selectSubjectMS.service.ClassService;
import com.qgx.selectSubjectMS.service.DeptHeadService;
import com.qgx.selectSubjectMS.service.MajorService;
import com.qgx.selectSubjectMS.service.SelectSubjectService;
import com.qgx.selectSubjectMS.service.SettingService;
import com.qgx.selectSubjectMS.service.StudentService;
import com.qgx.selectSubjectMS.service.SubjectService;
import com.qgx.selectSubjectMS.service.TeacherAssignService;
import com.qgx.selectSubjectMS.service.TeacherService;
import com.qgx.selectSubjectMS.service.UserService;
import com.qgx.selectSubjectMS.utils.ExcelUtil;
import com.qgx.selectSubjectMS.utils.ResponseUtil;
import com.qgx.selectSubjectMS.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("protoType")
public class DeptHeadAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private DeptHead deptHead;

	@Resource
	private DeptHeadService deptHeadService;
	@Resource
	private UserService userService;
	@Resource
	private SettingService settingService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentService studentService;
	@Resource
	private SubjectService subjectService;
	@Resource
	private MajorService majorService;
	@Resource
	private ClassService classService;
	@Resource
	private SelectSubjectService selectSubjectService;
	@Resource
	private TeacherAssignService teacherAssignService;

	private Setting setting;

	private User user;

	private Teacher teacher;

	private Student student;

	private File teacherUploadFile;

	private File studentUploadFile;

	private Major major;

	/*
	 * �޸ĸ�����Ϣ
	 */
	public void updatePersonalInfo() throws Exception {
		// ��ȡSession�е�admin�û���Ϣ
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// �޸���Ϣ
		currentDeptHead.setRealName(deptHead.getRealName());
		currentDeptHead.setPhone(deptHead.getPhone());
		currentDeptHead.setEmail(deptHead.getEmail());
		// �������ݿ����Ϣ
		deptHeadService.updateDeptHead(currentDeptHead);
		// ����Session�е���Ϣ
		request.getSession().setAttribute("currentUser", currentDeptHead);
	}

	public void modifyPwd() throws Exception {
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// ����id�������ݿ���û�
		User user = userService.findUserById(currentDeptHead.getUser().getId());
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
	}

	/*
	 * ����ʱ������
	 */
	public String updateSetting() throws Exception {
		// session��ȡ��ǰԺϵ������Ϣ
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Setting currentSetting = currentDeptHead.getYard().getSetting();
		// �޸ĸ���
		currentSetting.setTeacherSetStartTime(setting.getTeacherSetStartTime());
		currentSetting.setTeacherSetEndTime(setting.getTeacherSetEndTime());
		currentSetting.setStudentSelectStartTime(setting.getStudentSelectStartTime());
		currentSetting.setStudentSelectEndTime(setting.getStudentSelectEndTime());
		currentSetting.setTeacherSelectStartTime(setting.getTeacherSelectStartTime());
		currentSetting.setTeacherSelectEndTime(setting.getTeacherSelectEndTime());
		currentSetting.setDeptHeadAdjustStartTime(setting.getDeptHeadAdjustStartTime());
		currentSetting.setDeptHeadAdjustEndTime(setting.getDeptHeadAdjustEndTime());
		// �������ݿ�־û�����
		settingService.updateSetting(currentSetting);
		// ��Ҫ����Session�����е�
		currentDeptHead.getYard().setSetting(currentSetting);
		request.getSession().setAttribute("currentUser", currentDeptHead);
		request.setAttribute("flag", "update");
		return "selectTime";
	}

	/*
	 * ������������
	 */
	public void updateLimit() throws Exception {
		// session��ȡ��ǰԺϵ������Ϣ
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Setting currentSetting = currentDeptHead.getYard().getSetting();

		if (setting.getWarnNum() != null) {
			currentSetting.setWarnNum(setting.getWarnNum());
		}
		if (setting.getMaxSelectNum() != null) {
			currentSetting.setMaxSelectNum(setting.getMaxSelectNum());
		}
		if (setting.getMaxStuSelectNum() != null) {
			currentSetting.setMaxStuSelectNum(setting.getMaxStuSelectNum());
		}
		if (setting.getMaxSetNum() != null) {
			currentSetting.setMaxSetNum(setting.getMaxSetNum());
		}
		if (setting.getCurrentGrade() != null) {
			currentSetting.setCurrentGrade(setting.getCurrentGrade());
		}
		settingService.updateSetting(currentSetting);
		// ��Ҫ����Session�����е�
		currentDeptHead.getYard().setSetting(currentSetting);
		request.getSession().setAttribute("currentUser", currentDeptHead);

	}

	/*
	 * ϵ���β鿴��ѧԺ��������ʦ(���ܴ�������ѯ)
	 * 
	 */
	public void teacherList() throws Exception {
		// ��ȡҳ��Ĳ�ѯ����
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String teacherName = request.getParameter("teacherName");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// ������ѯ����
		List<Teacher> teacherList = teacherService.listTeacher(teacherName, currentDeptHead.getYard().getId(), page,
				rows);
		// ������ѯ����
		int total = teacherService.countTeacher(teacherName, currentDeptHead.getYard().getId());
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Teacher t : teacherList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("teacherId", t.getId());
			jsonObject.put("yardId", t.getYard().getId());
			jsonObject.put("yardName", t.getYard().getYardName());
			jsonObject.put("realName", t.getRealName());
			jsonObject.put("userName", t.getUser().getUserName());
			jsonObject.put("password", t.getUser().getPassword());
			jsonObject.put("sex", t.getSex());
			jsonObject.put("phone", t.getPhone());
			jsonObject.put("email", t.getEmail());
			Integer setNum = subjectService.countSubjectByTeacherId(t.getId(), null, null, null);
			jsonObject.put("setNum", setNum);
			jsonObject.put("maxSetNum", currentDeptHead.getYard().getSetting().getMaxSetNum());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	// ��ӽ�ʦ
	public void addTeacher() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		User currentUser = userService.getUserByUserName(user.getUserName());
		Integer flag = 0;
		JSONObject result = new JSONObject();
		if (currentUser == null) {
			// �û�Ϊ��,��ʾ�����ڿ������,������û�
			user.setUserType("Teacher");
			user.setPassword(StringUtil.encryptMd5(user.getPassword()));
			userService.saveUser(user);
			// �û���Ӻ���Ҫ��ȡ���ݿ�־ò�ĸ��û���������ϵ������(id���Ӧ)
			currentUser = userService.getUserByUserName(user.getUserName());
			teacher.setUser(currentUser);
			teacher.setYard(currentDeptHead.getYard()); // �����������ϵ����
			teacherService.saveTeacher(teacher);
			flag++;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// �޸���ʦ
	public void updateTeacher() throws Exception {
		// ����id��ѯ���ݿ�ĵ�ǰ����
		Teacher currentTeacher = teacherService.getTeacherById(teacher.getId());
		currentTeacher.setRealName(teacher.getRealName());
		currentTeacher.setSex(teacher.getSex());
		currentTeacher.setPhone(teacher.getPhone());
		currentTeacher.setEmail(teacher.getEmail());
		if (!currentTeacher.getUser().getPassword().equals(user.getPassword())) {
			currentTeacher.getUser().setPassword(StringUtil.encryptMd5(user.getPassword()));
		}
		teacherService.updateTeacher(currentTeacher);
		JSONObject result = new JSONObject();
		result.put("type", "update");
		ResponseUtil.write(result.toString());
	}

	/*
	 * ɾ����ʦ
	 */
	public void deleteTeacher() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = teacherService.deleteTeacher(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * ������ʦ(��Ժϵ�µ�)
	 */
	public void exportTeachers() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// ��ѯ������ʦ(��Ժϵ)
		List<Teacher> teacherList = teacherService.listTeacherByYardId(currentDeptHead.getYard().getId());
		// ����������
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "���", "�˺�", "����", "ѧԺ", "�Ա�", "�绰", "����" };
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // ����һ��������
		Row row = sheet.createRow(rowIndex++); // ������һ��
		for (int i = 0; i < heads.length; i++) {
			row.createCell(i).setCellValue(heads[i]);
		}

		for (Teacher t : teacherList) {
			row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(rowIndex);
			row.createCell(1).setCellValue(t.getUser().getUserName());
			row.createCell(2).setCellValue(t.getRealName());
			row.createCell(3).setCellValue(t.getYard().getYardName());
			row.createCell(4).setCellValue(t.getSex());
			row.createCell(5).setCellValue(t.getPhone());
			row.createCell(6).setCellValue(t.getEmail());
		}

		ResponseUtil.export(wb, "teachers.xls");
	}

	/*
	 * �����ʦ
	 */
	public void importTeachers() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(teacherUploadFile));
		Sheet sheet = wb.getSheetAt(0);
		int count = 0;
		if (sheet != null) {
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				User user = new User();
				user.setUserName(ExcelUtil.formatCell(row.getCell(0)));
				User currentUser = userService.getUserByUserName(user.getUserName());
				if (currentUser == null) {
					user.setPassword(StringUtil.encryptMd5(ExcelUtil.formatCell(row.getCell(1))));
					user.setUserType("Teacher");
					Teacher teacher = new Teacher();
					teacher.setRealName(ExcelUtil.formatCell(row.getCell(2)));
					// ��ӵ���ʦ�ض��ǵ�ǰԺϵ
					DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
					teacher.setYard(currentDeptHead.getYard());
					// �־û���ʦ���û�
					userService.saveUser(user);
					User savedUser = userService.getUserByUserName(user.getUserName());
					teacher.setUser(savedUser);
					teacher.setSex(ExcelUtil.formatCell(row.getCell(3)));
					teacher.setPhone(ExcelUtil.formatCell(row.getCell(4)));
					teacher.setEmail(ExcelUtil.formatCell(row.getCell(5)));
					teacherService.saveTeacher(teacher);
					count++;
				} else
					continue;
			}
		}
		JSONObject result = new JSONObject();
		result.put("num", count);
		ResponseUtil.write(result.toString());
	}

	/*
	 * ϵ����������ѯѧ�����ϣ�Ĭ��Ϊ��Ժϵ�£�
	 */
	public void studentList() throws Exception {
		// ��ȡҳ��Ĳ�ѯ����
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String studentName = request.getParameter("studentName");
		String majorId = request.getParameter("majorId");
		String classId = request.getParameter("classId");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// ������ѯ����
		List<Student> studentList = studentService.listStudent(studentName, currentDeptHead.getYard().getId(), majorId,
				classId, page, rows);
		// ������ѯ����
		int total = studentService.countStudent(studentName, currentDeptHead.getYard().getId(), majorId, classId);

		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Student s : studentList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("studentId", s.getId());
			jsonObject.put("yardName", s.getSchoolClass().getMajor().getYard().getYardName());
			jsonObject.put("majorId", s.getSchoolClass().getMajor().getId());
			jsonObject.put("majorName", s.getSchoolClass().getMajor().getMajorName());
			jsonObject.put("classId", s.getSchoolClass().getId());
			jsonObject.put("className", s.getSchoolClass().getClassName());
			jsonObject.put("realName", s.getRealName());
			jsonObject.put("userName", s.getUser().getUserName());
			jsonObject.put("password", s.getUser().getPassword());
			jsonObject.put("sex", s.getSex());
			jsonObject.put("phone", s.getPhone());
			jsonObject.put("email", s.getEmail());
			Integer selectSubjectNum = selectSubjectService.countSelectSubjectByStudentId(s.getId());
			jsonObject.put("selectSubjectNum", selectSubjectNum);
			jsonObject.put("graduateYear", s.getGraduateYear());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());

	}

	/*
	 * רҵ������
	 */
	public void majorComboBox() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		List<Major> majorList = majorService.ListMajor(currentDeptHead.getYard().getId());
		JSONArray result = new JSONArray();
		// ����ѡ��Ž���
		JSONObject firstObject = new JSONObject();
		firstObject.put("majorId", "0");
		firstObject.put("majorName", "��ѡ��");
		result.add(firstObject);
		// ��Ժϵ�Ž�ȥ
		for (Major m : majorList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("majorId", m.getId());
			jsonObject.put("majorName", m.getMajorName());
			result.add(jsonObject);
		}
		ResponseUtil.write(result.toString());
	}

	/*
	 * �༶�������������
	 */
	public void classCombobox() throws Exception {
		String majorId = request.getParameter("majorId");
		List<SchoolClass> classList = classService.listClassByMajorId(majorId);
		JSONArray result = new JSONArray();
		JSONObject firstObject = new JSONObject();
		firstObject.put("classId", "0");
		firstObject.put("className", "��ѡ��");
		result.add(firstObject);
		for (SchoolClass c : classList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("classId", c.getId());
			jsonObject.put("className", c.getClassName());
			result.add(jsonObject);
		}
		ResponseUtil.write(result.toString());
	}

	// ���ѧ��
	public void addStudent() throws Exception {
		String classId = request.getParameter("classId");
		User currentUser = userService.getUserByUserName(user.getUserName());
		Integer flag = 0;
		JSONObject result = new JSONObject();
		if (currentUser == null) {
			// �û�Ϊ��,��ʾ�����ڿ������,������û�
			user.setUserType("Student");
			user.setPassword(StringUtil.encryptMd5(user.getPassword()));
			userService.saveUser(user);
			// �û���Ӻ���Ҫ��ȡ���ݿ�־ò�ĸ��û���������ϵ������(id���Ӧ)
			currentUser = userService.getUserByUserName(user.getUserName());
			student.setUser(currentUser);
			SchoolClass currentClass = classService.getSchoolClassById(Long.valueOf(classId));
			student.setSchoolClass(currentClass); // �����������ѧ��
			studentService.saveStudent(student);
			flag++;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// �޸�ѧ��
	public void updateStudent() throws Exception {
		String classId = request.getParameter("classId");
		// ����id��ѯ���ݿ�ĵ�ǰ����
		Student currentStudent = studentService.getStudentById(student.getId());
		currentStudent.setRealName(student.getRealName());
		currentStudent.setSex(student.getSex());
		currentStudent.setPhone(student.getPhone());
		currentStudent.setEmail(student.getEmail());
		currentStudent.setGraduateYear(student.getGraduateYear());
		if (!currentStudent.getUser().getPassword().equals(user.getPassword())) {
			currentStudent.getUser().setPassword(StringUtil.encryptMd5(user.getPassword()));
		}
		SchoolClass currentClass = classService.getSchoolClassById(Long.valueOf(classId));
		currentStudent.setSchoolClass(currentClass);
		studentService.updateStudent(currentStudent);
		JSONObject result = new JSONObject();
		result.put("type", "update");
		ResponseUtil.write(result.toString());
	}

	/*
	 * ɾ��ѧ��
	 */
	public void deleteStudent() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = studentService.deleteStudent(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * ����ѧ��(��Ժϵ�µ�)
	 */
	public void exportStudents() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// ��ѯ����ѧ��(��Ժϵ)
		List<Student> studentList = studentService.listStudentByYardId(currentDeptHead.getYard().getId());
		// ����������
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "���", "�˺�", "����", "ѧԺ", "רҵ", "�༶", "�Ա�", "�绰", "����" };
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // ����һ��������
		Row row = sheet.createRow(rowIndex++); // ������һ��
		for (int i = 0; i < heads.length; i++) {
			row.createCell(i).setCellValue(heads[i]);
		}

		for (Student s : studentList) {
			row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(rowIndex - 1);
			row.createCell(1).setCellValue(s.getUser().getUserName());
			row.createCell(2).setCellValue(s.getRealName());
			row.createCell(3).setCellValue(s.getSchoolClass().getMajor().getYard().getYardName());
			row.createCell(4).setCellValue(s.getSchoolClass().getMajor().getMajorName());
			row.createCell(5).setCellValue(s.getSchoolClass().getClassName());
			row.createCell(6).setCellValue(s.getSex());
			row.createCell(7).setCellValue(s.getPhone());
			row.createCell(8).setCellValue(s.getEmail());
		}

		ResponseUtil.export(wb, "students.xls");
	}

	/*
	 * ����ѧ��
	 */
	public void importStudents() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(studentUploadFile));
		Sheet sheet = wb.getSheetAt(0);
		int count = 0;
		if (sheet != null) {
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				User user = new User();
				user.setUserName(ExcelUtil.formatCell(row.getCell(0)));
				User currentUser = userService.getUserByUserName(user.getUserName());
				if (currentUser == null) {
					user.setPassword(StringUtil.encryptMd5(ExcelUtil.formatCell(row.getCell(1))));
					user.setUserType("Student");
					Student student = new Student();
					student.setRealName(ExcelUtil.formatCell(row.getCell(2)));
					// �ж�רҵ�༶�Ƿ�ƥ��(ƥ����������)
					SchoolClass currentClass = classService
							.getSchoolClassByClassName(ExcelUtil.formatCell(row.getCell(5)));
					if (currentClass.getMajor().getMajorName().equals(ExcelUtil.formatCell(row.getCell(4)))) {
						// רҵ�Ͱ༶��ƥ��ſ����
						// �־û���ʦ���û�
						userService.saveUser(user);
						User savedUser = userService.getUserByUserName(user.getUserName());
						student.setUser(savedUser);
						student.setSchoolClass(currentClass);
						student.setSex(ExcelUtil.formatCell(row.getCell(3)));
						student.setPhone(ExcelUtil.formatCell(row.getCell(6)));
						student.setEmail(ExcelUtil.formatCell(row.getCell(7)));
						student.setGraduateYear(ExcelUtil.formatCell(row.getCell(8)));
						studentService.saveStudent(student);
						count++;
					}
				} else
					continue;
			}
		}
		JSONObject result = new JSONObject();
		result.put("num", count);
		ResponseUtil.write(result.toString());
	}

	/*
	 * ��������ѯרҵ����
	 */
	public void majorList() throws Exception {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String majorName = request.getParameter("majorName");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// ��ѯ���ڸ�ԺϵΪ������
		Integer total = majorService.countMajor(majorName, currentDeptHead.getYard().getId().toString());
		List<Major> majorList = majorService.ListMajor(majorName, currentDeptHead.getYard().getId());
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Major m : majorList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("majorId", m.getId());
			jsonObject.put("yardName", m.getYard().getYardName());
			jsonObject.put("majorName", m.getMajorName());
			jsonObject.put("majorDesc", m.getMajorDesc());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	// ���רҵ
	public void addMajor() throws Exception {
		// רҵ��ͬʱרҵ����ͬ��������ԺϵҲ��ͬ����ʱ�������
		// �������Ʋ�ѯʱ�����ܲ�ֹһ��
		List<Major> majorList = majorService.ListMajorByMajorName(major.getMajorName());
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Integer flag = 0;
		boolean isExist = false;
		JSONObject result = new JSONObject();
		for (Major m : majorList) {
			if (m.getYard().getId() == currentDeptHead.getYard().getId()) {
				flag = 0;
				isExist = true; // ���ڱ�ʶ
			}
		}
		if (!isExist) {
			major.setYard(currentDeptHead.getYard());
			majorService.saveMajor(major);
			flag = 1;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// �޸�רҵ
	public void updateMajor() throws Exception {
		// רҵ��ͬʱרҵ����ͬ��������ԺϵҲ��ͬ����ʱ�����޸�
		// �������Ʋ�ѯʱ�����ܲ�ֹһ��
		List<Major> majorList = majorService.ListMajorByMajorName(major.getMajorName());
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Major currentMajor = majorService.getMajorById(major.getId());
		Integer flag = 0;
		boolean isExist = false;
		JSONObject result = new JSONObject();
		// ��רҵδ�ı�ʱ����ʹһ��Ҳ����ν
		if (currentMajor.getMajorName().equals(major.getMajorName())) {
			currentMajor.setMajorName(major.getMajorName());
			currentMajor.setMajorDesc(major.getMajorDesc());
			majorService.updateMajor(currentMajor);
			flag = 1;
		} else {
			for (Major m : majorList) {
				if (m.getYard().getId() == currentDeptHead.getYard().getId()) {
					flag = 0;
					isExist = true; // ���ڱ�ʶ
				}
			}
			if (!isExist) {
				currentMajor.setMajorName(major.getMajorName());
				currentMajor.setMajorDesc(major.getMajorDesc());
				majorService.updateMajor(currentMajor);
				flag = 1;
			}
		}
		result.put("num", flag);
		result.put("type", "update");
		ResponseUtil.write(result.toString());
	}

	/*
	 * ɾ��רҵ
	 */
	public void deleteMajor() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = majorService.deleteMajor(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * ��������ѯ�༶����
	 */
	public void classList() throws Exception {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String className = request.getParameter("className");
		String majorId = request.getParameter("majorId");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// ��ѯ���ڸ�ԺϵΪ������
		Integer total = classService.countClass(className, majorId, currentDeptHead.getYard().getId());
		List<SchoolClass> classList = classService.listClass(className, majorId, currentDeptHead.getYard().getId(),
				page, rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (SchoolClass s : classList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("classId", s.getId());
			jsonObject.put("className", s.getClassName());
			jsonObject.put("majorId", s.getMajor().getId());
			jsonObject.put("majorName", s.getMajor().getMajorName());
			Integer studentNum = studentService.countStudentByClassId(s.getId());
			jsonObject.put("studentNum", studentNum);
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	// ��Ӱ༶
	public void addClass() throws Exception {
		// �༶���������Ψһ�������ڼ������
		String className = request.getParameter("className");
		String majorId = request.getParameter("majorId");

		Integer flag = 0;
		SchoolClass schoolClass = classService.getSchoolClassByClassName(className);
		if (schoolClass == null) {
			Major currentMajor = majorService.getMajorById(Long.valueOf(majorId));
			schoolClass = new SchoolClass();
			schoolClass.setMajor(currentMajor);
			schoolClass.setClassName(className);
			classService.saveClass(schoolClass);
			flag = 1;
		}
		JSONObject result = new JSONObject();
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// �޸İ༶
	public void updateClass() throws Exception {
		// �༶�޸ĺ����Ϣ���ڣ������޸�
		String className = request.getParameter("className");
		String majorId = request.getParameter("majorId");
		String classId = request.getParameter("classId");

		SchoolClass currentSchoolClass = classService.getSchoolClassById(Long.valueOf(classId));
		SchoolClass validateSC = classService.getSchoolClassByClassName(className);
		Integer flag = 0;
		// �У����Ҳ���ԭ���ĲŲ��������
		if (validateSC != null && validateSC.getId() != currentSchoolClass.getId()) {

		} else {
			currentSchoolClass.setClassName(className);
			classService.updateClass(currentSchoolClass);
			flag = 1;
		}
		JSONObject result = new JSONObject();
		result.put("num", flag);
		result.put("type", "update");
		ResponseUtil.write(result.toString());
	}

	/*
	 * ɾ���༶
	 */
	public void deleteClass() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = classService.deleteClass(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * ϵ���β鿴������Ŀ
	 */
	public void subjectList() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// ��ȡ������ѯ����Ϣ
		String teacherName = request.getParameter("teacherName"); // ��ʦ����
		String title = request.getParameter("title"); // ����
		String direction = request.getParameter("direction"); // ����
		String state = request.getParameter("state"); // ���״̬
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubject(teacherName, currentDeptHead.getYard().getId(), title, direction,
				state); // ��ȡ��Ŀ����
		List<Subject> subjectList = subjectService.listSubject(teacherName, currentDeptHead.getYard().getId(), title,
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
			jsonObject.put("state", s.getState());
			String stateDesc = "";
			switch (s.getState()) {
			case "1":
				stateDesc = "δ���";
				jsonObject.put("examine", "�������");
				break;
			case "2":
				stateDesc = "���ͨ��";
				jsonObject.put("examine", "�޸����");
				break;
			case "3":
				stateDesc = "���δͨ��";
				jsonObject.put("examine", "�޸����");
			}
			jsonObject.put("stateDesc", stateDesc);
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * ����id�첽��ȡ��ǰѡ�����Ϣ
	 */
	public void getSubjectById() throws Exception {
		String subjectId = request.getParameter("subjectId");
		Subject currentSubject = subjectService.getSubjectById(subjectId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "teacher" });
		ResponseUtil.write(JSONObject.fromObject(currentSubject, jsonConfig).toString());
	}

	/*
	 * �޸�ѡ��״̬
	 */
	public void updateState() throws Exception {
		String subjectId = request.getParameter("subjectId");
		String state = request.getParameter("state");

		Subject currentSubject = subjectService.getSubjectById(subjectId);
		currentSubject.setState(state);

		subjectService.updateSubject(currentSubject);
	}

	/*
	 * δָ��ѧ������Ŀ
	 */
	public void unChooseSubjectList() throws Exception {
		// ��ȡ��ҳ��ѯ����Ϣ
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// ��ȡ������ѯ����Ϣ
		String teacherName = request.getParameter("teacherName"); // ��ʦ����
		String title = request.getParameter("title"); // ����
		String direction = request.getParameter("direction"); // ����
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubjectOfUnChoose(teacherName, currentDeptHead.getYard().getId(), title,
				direction, "2"); // ��ȡ��Ŀ����
		List<Subject> subjectList = subjectService.listSubjectOfUnChoose(teacherName, currentDeptHead.getYard().getId(),
				title, direction, "2", page, rows);

		JSONArray jsonArray = new JSONArray();
		for (Subject s : subjectList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subjectId", s.getId());
			jsonObject.put("yardId", s.getTeacher().getYard().getId());
			jsonObject.put("yardName", s.getTeacher().getYard().getYardName());
			jsonObject.put("teacherName", s.getTeacher().getRealName());
			jsonObject.put("title", s.getTitle());
			jsonObject.put("direction", s.getDirection());
			jsonArray.add(jsonObject);
		}
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * δ�����ѧ��
	 */
	public void unAssignStudentList() throws Exception {
		// ��ȡҳ��Ĳ�ѯ����
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String studentName = request.getParameter("studentName");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// ������ѯ����
		List<Student> studentList = studentService.listStudentOfUnAssign(studentName, currentDeptHead.getYard().getId(),
				page, rows);
		// ������ѯ����
		int total = studentService.countStudentOfUnAssign(studentName, currentDeptHead.getYard().getId());

		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Student s : studentList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("studentId", s.getId());
			jsonObject.put("yardName", s.getSchoolClass().getMajor().getYard().getYardName());
			jsonObject.put("majorId", s.getSchoolClass().getMajor().getId());
			jsonObject.put("majorName", s.getSchoolClass().getMajor().getMajorName());
			jsonObject.put("classId", s.getSchoolClass().getId());
			jsonObject.put("className", s.getSchoolClass().getClassName());
			jsonObject.put("realName", s.getRealName());
			jsonObject.put("userName", s.getUser().getUserName());
			jsonObject.put("sex", s.getSex());
			jsonObject.put("phone", s.getPhone());
			jsonObject.put("email", s.getEmail());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	/*
	 * ϵ������ѡָ��
	 */
	public void assign() throws Exception {
		String studentId = request.getParameter("studentId");
		String subjectId = request.getParameter("subjectId");

		TeacherAssign ta = new TeacherAssign();
		ta.setStudent(studentService.getStudentById(Long.valueOf(studentId)));
		ta.setSubject(subjectService.getSubjectById(subjectId));
		teacherAssignService.saveOrUpdateAssign(ta);
		JSONObject result = new JSONObject();
		result.put("num", "1");
		ResponseUtil.write(result.toString());
	}

	/*
	 * ���ս��
	 */
	public void finalResult() throws Exception {
		// ��ȡҳ��Ĳ�ѯ����
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// ���ս��Ҳ�ǻ��ڱ�Ժϵ�µ�
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		List<TeacherAssign> taList = teacherAssignService.listTeacherAssignByYardId(currentDeptHead.getYard().getId(),
				page, rows);
		Integer total = teacherAssignService.countTeacherAssignByYardId(currentDeptHead.getYard().getId());

		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (TeacherAssign ta : taList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("majorName", ta.getStudent().getSchoolClass().getMajor().getMajorName());
			jsonObject.put("className", ta.getStudent().getSchoolClass().getClassName());
			jsonObject.put("realName", ta.getStudent().getRealName());
			jsonObject.put("title", ta.getSubject().getTitle());
			jsonObject.put("direction", ta.getSubject().getDirection());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}

	public void exportResult() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// ��ѯ���н������Ժ�£�
		List<TeacherAssign> taList = teacherAssignService.listTeacherAssignByYardId(currentDeptHead.getYard().getId());
		// ����������
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "���", "רҵ", "�༶", "ѧ������", "��Ŀ����", "����"};
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // ����һ��������
		Row row = sheet.createRow(rowIndex++); // ������һ��
		for (int i = 0; i < heads.length; i++) {
			row.createCell(i).setCellValue(heads[i]);
		}

		for (TeacherAssign  ta: taList) {
			row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(rowIndex - 1);
			row.createCell(1).setCellValue(ta.getStudent().getSchoolClass().getMajor().getMajorName());
			row.createCell(2).setCellValue(ta.getStudent().getSchoolClass().getClassName());
			row.createCell(3).setCellValue(ta.getStudent().getRealName());
			row.createCell(4).setCellValue(ta.getSubject().getTitle());
			row.createCell(5).setCellValue(ta.getSubject().getDirection());
		}

		ResponseUtil.export(wb, "selectResult.xls");
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public DeptHead getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(DeptHead deptHead) {
		this.deptHead = deptHead;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public File getTeacherUploadFile() {
		return teacherUploadFile;
	}

	public void setTeacherUploadFile(File teacherUploadFile) {
		this.teacherUploadFile = teacherUploadFile;
	}

	public File getStudentUploadFile() {
		return studentUploadFile;
	}

	public void setStudentUploadFile(File studentUploadFile) {
		this.studentUploadFile = studentUploadFile;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
