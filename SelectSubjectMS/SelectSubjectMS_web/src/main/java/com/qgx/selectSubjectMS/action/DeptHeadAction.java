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
	 * 修改个人信息
	 */
	public void updatePersonalInfo() throws Exception {
		// 获取Session中的admin用户信息
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// 修改信息
		currentDeptHead.setRealName(deptHead.getRealName());
		currentDeptHead.setPhone(deptHead.getPhone());
		currentDeptHead.setEmail(deptHead.getEmail());
		// 更新数据库的信息
		deptHeadService.updateDeptHead(currentDeptHead);
		// 更新Session中的信息
		request.getSession().setAttribute("currentUser", currentDeptHead);
	}

	public void modifyPwd() throws Exception {
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// 根据id查找数据库的用户
		User user = userService.findUserById(currentDeptHead.getUser().getId());
		JSONObject jsonObject = new JSONObject();
		// 比较原密码是否正确
		if (!user.getPassword().equals(StringUtil.encryptMd5(password))) {
			jsonObject.put("result", "0");
			jsonObject.put("message", "原密码输入不正确！");
		} else {
			user.setPassword(StringUtil.encryptMd5(rePassword));
			userService.updateUser(user);
			jsonObject.put("result", "1");
			jsonObject.put("message", "修改成功!");
		}
		ResponseUtil.write(jsonObject.toString());
	}

	/*
	 * 更新时间设置
	 */
	public String updateSetting() throws Exception {
		// session获取当前院系设置信息
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Setting currentSetting = currentDeptHead.getYard().getSetting();
		// 修改更改
		currentSetting.setTeacherSetStartTime(setting.getTeacherSetStartTime());
		currentSetting.setTeacherSetEndTime(setting.getTeacherSetEndTime());
		currentSetting.setStudentSelectStartTime(setting.getStudentSelectStartTime());
		currentSetting.setStudentSelectEndTime(setting.getStudentSelectEndTime());
		currentSetting.setTeacherSelectStartTime(setting.getTeacherSelectStartTime());
		currentSetting.setTeacherSelectEndTime(setting.getTeacherSelectEndTime());
		currentSetting.setDeptHeadAdjustStartTime(setting.getDeptHeadAdjustStartTime());
		currentSetting.setDeptHeadAdjustEndTime(setting.getDeptHeadAdjustEndTime());
		// 更新数据库持久化数据
		settingService.updateSetting(currentSetting);
		// 还要更新Session缓存中的
		currentDeptHead.getYard().setSetting(currentSetting);
		request.getSession().setAttribute("currentUser", currentDeptHead);
		request.setAttribute("flag", "update");
		return "selectTime";
	}

	/*
	 * 更新限制设置
	 */
	public void updateLimit() throws Exception {
		// session获取当前院系设置信息
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
		// 还要更新Session缓存中的
		currentDeptHead.getYard().setSetting(currentSetting);
		request.getSession().setAttribute("currentUser", currentDeptHead);

	}

	/*
	 * 系主任查看该学院下所有老师(可能带条件查询)
	 * 
	 */
	public void teacherList() throws Exception {
		// 获取页面的查询条件
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String teacherName = request.getParameter("teacherName");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// 条件查询集合
		List<Teacher> teacherList = teacherService.listTeacher(teacherName, currentDeptHead.getYard().getId(), page,
				rows);
		// 条件查询个数
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

	// 添加教师
	public void addTeacher() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		User currentUser = userService.getUserByUserName(user.getUserName());
		Integer flag = 0;
		JSONObject result = new JSONObject();
		if (currentUser == null) {
			// 用户为空,表示不存在可以添加,先添加用户
			user.setUserType("Teacher");
			user.setPassword(StringUtil.encryptMd5(user.getPassword()));
			userService.saveUser(user);
			// 用户添加后需要获取数据库持久层的该用户，并放入系主任中(id相对应)
			currentUser = userService.getUserByUserName(user.getUserName());
			teacher.setUser(currentUser);
			teacher.setYard(currentDeptHead.getYard()); // 完整对象放入系主任
			teacherService.saveTeacher(teacher);
			flag++;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// 修改老师
	public void updateTeacher() throws Exception {
		// 根据id查询数据库的当前对象
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
	 * 删除教师
	 */
	public void deleteTeacher() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = teacherService.deleteTeacher(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * 导出教师(该院系下的)
	 */
	public void exportTeachers() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// 查询所有老师(该院系)
		List<Teacher> teacherList = teacherService.listTeacherByYardId(currentDeptHead.getYard().getId());
		// 创建工作簿
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "序号", "账号", "姓名", "学院", "性别", "电话", "邮箱" };
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // 创建一个行索引
		Row row = sheet.createRow(rowIndex++); // 创建第一行
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
	 * 导入教师
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
					// 添加的老师必定是当前院系
					DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
					teacher.setYard(currentDeptHead.getYard());
					// 持久化老师及用户
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
	 * 系主任条件查询学生集合（默认为该院系下）
	 */
	public void studentList() throws Exception {
		// 获取页面的查询条件
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String studentName = request.getParameter("studentName");
		String majorId = request.getParameter("majorId");
		String classId = request.getParameter("classId");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// 条件查询集合
		List<Student> studentList = studentService.listStudent(studentName, currentDeptHead.getYard().getId(), majorId,
				classId, page, rows);
		// 条件查询个数
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
	 * 专业下拉框
	 */
	public void majorComboBox() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		List<Major> majorList = majorService.ListMajor(currentDeptHead.getYard().getId());
		JSONArray result = new JSONArray();
		// 将请选择放进来
		JSONObject firstObject = new JSONObject();
		firstObject.put("majorId", "0");
		firstObject.put("majorName", "请选择");
		result.add(firstObject);
		// 将院系放进去
		for (Major m : majorList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("majorId", m.getId());
			jsonObject.put("majorName", m.getMajorName());
			result.add(jsonObject);
		}
		ResponseUtil.write(result.toString());
	}

	/*
	 * 班级下拉框二级联动
	 */
	public void classCombobox() throws Exception {
		String majorId = request.getParameter("majorId");
		List<SchoolClass> classList = classService.listClassByMajorId(majorId);
		JSONArray result = new JSONArray();
		JSONObject firstObject = new JSONObject();
		firstObject.put("classId", "0");
		firstObject.put("className", "请选择");
		result.add(firstObject);
		for (SchoolClass c : classList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("classId", c.getId());
			jsonObject.put("className", c.getClassName());
			result.add(jsonObject);
		}
		ResponseUtil.write(result.toString());
	}

	// 添加学生
	public void addStudent() throws Exception {
		String classId = request.getParameter("classId");
		User currentUser = userService.getUserByUserName(user.getUserName());
		Integer flag = 0;
		JSONObject result = new JSONObject();
		if (currentUser == null) {
			// 用户为空,表示不存在可以添加,先添加用户
			user.setUserType("Student");
			user.setPassword(StringUtil.encryptMd5(user.getPassword()));
			userService.saveUser(user);
			// 用户添加后需要获取数据库持久层的该用户，并放入系主任中(id相对应)
			currentUser = userService.getUserByUserName(user.getUserName());
			student.setUser(currentUser);
			SchoolClass currentClass = classService.getSchoolClassById(Long.valueOf(classId));
			student.setSchoolClass(currentClass); // 完整对象放入学生
			studentService.saveStudent(student);
			flag++;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// 修改学生
	public void updateStudent() throws Exception {
		String classId = request.getParameter("classId");
		// 根据id查询数据库的当前对象
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
	 * 删除学生
	 */
	public void deleteStudent() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = studentService.deleteStudent(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * 导出学生(该院系下的)
	 */
	public void exportStudents() throws Exception {
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// 查询所有学生(该院系)
		List<Student> studentList = studentService.listStudentByYardId(currentDeptHead.getYard().getId());
		// 创建工作簿
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "序号", "账号", "姓名", "学院", "专业", "班级", "性别", "电话", "邮箱" };
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // 创建一个行索引
		Row row = sheet.createRow(rowIndex++); // 创建第一行
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
	 * 导入学生
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
					// 判断专业班级是否匹配(匹配才允许插入)
					SchoolClass currentClass = classService
							.getSchoolClassByClassName(ExcelUtil.formatCell(row.getCell(5)));
					if (currentClass.getMajor().getMajorName().equals(ExcelUtil.formatCell(row.getCell(4)))) {
						// 专业和班级均匹配才可添加
						// 持久化老师及用户
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
	 * 带条件查询专业集合
	 */
	public void majorList() throws Exception {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String majorName = request.getParameter("majorName");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// 查询是在该院系为基础下
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

	// 添加专业
	public void addMajor() throws Exception {
		// 专业相同时专业名相同并且所在院系也相同，此时不能填加
		// 根据名称查询时，可能不止一个
		List<Major> majorList = majorService.ListMajorByMajorName(major.getMajorName());
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Integer flag = 0;
		boolean isExist = false;
		JSONObject result = new JSONObject();
		for (Major m : majorList) {
			if (m.getYard().getId() == currentDeptHead.getYard().getId()) {
				flag = 0;
				isExist = true; // 存在标识
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

	// 修改专业
	public void updateMajor() throws Exception {
		// 专业相同时专业名相同并且所在院系也相同，此时不能修改
		// 根据名称查询时，可能不止一个
		List<Major> majorList = majorService.ListMajorByMajorName(major.getMajorName());
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		Major currentMajor = majorService.getMajorById(major.getId());
		Integer flag = 0;
		boolean isExist = false;
		JSONObject result = new JSONObject();
		// 当专业未改变时，即使一样也无所谓
		if (currentMajor.getMajorName().equals(major.getMajorName())) {
			currentMajor.setMajorName(major.getMajorName());
			currentMajor.setMajorDesc(major.getMajorDesc());
			majorService.updateMajor(currentMajor);
			flag = 1;
		} else {
			for (Major m : majorList) {
				if (m.getYard().getId() == currentDeptHead.getYard().getId()) {
					flag = 0;
					isExist = true; // 存在标识
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
	 * 删除专业
	 */
	public void deleteMajor() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = majorService.deleteMajor(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * 带条件查询班级集合
	 */
	public void classList() throws Exception {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String className = request.getParameter("className");
		String majorId = request.getParameter("majorId");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");
		// 查询是在该院系为基础下
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

	// 添加班级
	public void addClass() throws Exception {
		// 班级名称算的上唯一，不存在即可添加
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

	// 修改班级
	public void updateClass() throws Exception {
		// 班级修改后的信息存在，则不能修改
		String className = request.getParameter("className");
		String majorId = request.getParameter("majorId");
		String classId = request.getParameter("classId");

		SchoolClass currentSchoolClass = classService.getSchoolClassById(Long.valueOf(classId));
		SchoolClass validateSC = classService.getSchoolClassByClassName(className);
		Integer flag = 0;
		// 有，并且不是原来的才不允许更新
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
	 * 删除班级
	 */
	public void deleteClass() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = classService.deleteClass(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	 * 系主任查看所有题目
	 */
	public void subjectList() throws Exception {
		// 获取分页查询的信息
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// 获取条件查询的信息
		String teacherName = request.getParameter("teacherName"); // 老师姓名
		String title = request.getParameter("title"); // 标题
		String direction = request.getParameter("direction"); // 方向
		String state = request.getParameter("state"); // 审核状态
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubject(teacherName, currentDeptHead.getYard().getId(), title, direction,
				state); // 获取题目总数
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
				stateDesc = "未审核";
				jsonObject.put("examine", "进行审核");
				break;
			case "2":
				stateDesc = "审核通过";
				jsonObject.put("examine", "修改审核");
				break;
			case "3":
				stateDesc = "审核未通过";
				jsonObject.put("examine", "修改审核");
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
	 * 根据id异步获取当前选题的信息
	 */
	public void getSubjectById() throws Exception {
		String subjectId = request.getParameter("subjectId");
		Subject currentSubject = subjectService.getSubjectById(subjectId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "teacher" });
		ResponseUtil.write(JSONObject.fromObject(currentSubject, jsonConfig).toString());
	}

	/*
	 * 修改选题状态
	 */
	public void updateState() throws Exception {
		String subjectId = request.getParameter("subjectId");
		String state = request.getParameter("state");

		Subject currentSubject = subjectService.getSubjectById(subjectId);
		currentSubject.setState(state);

		subjectService.updateSubject(currentSubject);
	}

	/*
	 * 未指派学生的题目
	 */
	public void unChooseSubjectList() throws Exception {
		// 获取分页查询的信息
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// 获取条件查询的信息
		String teacherName = request.getParameter("teacherName"); // 老师姓名
		String title = request.getParameter("title"); // 标题
		String direction = request.getParameter("direction"); // 方向
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		int total = subjectService.countSubjectOfUnChoose(teacherName, currentDeptHead.getYard().getId(), title,
				direction, "2"); // 获取题目总数
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
	 * 未分配的学生
	 */
	public void unAssignStudentList() throws Exception {
		// 获取页面的查询条件
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String studentName = request.getParameter("studentName");
		DeptHead currentDeptHead = (DeptHead) request.getSession().getAttribute("currentUser");

		// 条件查询集合
		List<Student> studentList = studentService.listStudentOfUnAssign(studentName, currentDeptHead.getYard().getId(),
				page, rows);
		// 条件查询个数
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
	 * 系主任落选指派
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
	 * 最终结果
	 */
	public void finalResult() throws Exception {
		// 获取页面的查询条件
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		// 最终结果也是基于本院系下的
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
		// 查询所有结果（该院下）
		List<TeacherAssign> taList = teacherAssignService.listTeacherAssignByYardId(currentDeptHead.getYard().getId());
		// 创建工作簿
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "序号", "专业", "班级", "学生姓名", "题目名称", "方向"};
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // 创建一个行索引
		Row row = sheet.createRow(rowIndex++); // 创建第一行
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
