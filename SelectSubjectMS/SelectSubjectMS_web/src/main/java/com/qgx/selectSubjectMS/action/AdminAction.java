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
import com.qgx.selectSubjectMS.entity.Admin;
import com.qgx.selectSubjectMS.entity.DeptHead;
import com.qgx.selectSubjectMS.entity.Major;
import com.qgx.selectSubjectMS.entity.Setting;
import com.qgx.selectSubjectMS.entity.User;
import com.qgx.selectSubjectMS.entity.Yard;
import com.qgx.selectSubjectMS.service.AdminService;
import com.qgx.selectSubjectMS.service.DeptHeadService;
import com.qgx.selectSubjectMS.service.MajorService;
import com.qgx.selectSubjectMS.service.UserService;
import com.qgx.selectSubjectMS.service.YardService;
import com.qgx.selectSubjectMS.utils.ExcelUtil;
import com.qgx.selectSubjectMS.utils.ResponseUtil;
import com.qgx.selectSubjectMS.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("protoType")
public class AdminAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private Admin admin;
	private Yard yard;
	private Major major;

	private DeptHead deptHead;
	private User user;
	@Resource
	private AdminService adminService;
	@Resource
	private UserService userService;
	@Resource
	private DeptHeadService deptHeadService;
	@Resource
	private YardService yardService;
	@Resource
	private MajorService majorService;
	
	private File deptHeadUploadFile;
	
	

	/*
	 * 修改管理员信息
	 */
	public String updatePersonalInfo() throws Exception {
		// 获取Session中的admin用户信息
		Admin currentAdmin = (Admin) request.getSession().getAttribute("currentUser");
		// 修改信息
		currentAdmin.setRealName(admin.getRealName());
		currentAdmin.setPhone(admin.getPhone());
		currentAdmin.setEmail(admin.getEmail());
		// 更新数据库的信息
		adminService.updateAdmin(currentAdmin);
		// 更新Session中的信息
		request.getSession().setAttribute("currentUser", currentAdmin);
		return null;
	}

	/*
	 * 修改密码
	 */
	public String modifyPwd() throws Exception {
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		Admin admin = (Admin) request.getSession().getAttribute("currentUser");
		// 根据id查找数据库的用户
		User user = userService.findUserById(admin.getUser().getId());
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
		return null;
	}

	/*
	 * 获取学院的信息
	 */
	public String yardList() throws Exception {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		int total = yardService.countYard();
		List<Yard> yardList = yardService.ListYard(page, rows);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Yard y : yardList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("yardId", y.getId());
			jsonObject.put("yardName", y.getYardName());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
		return null;
	}

	/*
	 * 添加学院
	 */
	public String addYard() throws Exception {
		Setting setting = new Setting(); //学院存在时需要给选题设置一个初始值
		setting.setWarnNum("3");//志愿扎堆提醒,默认为3
		setting.setMaxSelectNum("4"); //每道题的最大选择人数,默认是4
		setting.setMaxSetNum("6"); //老师的最大出题数,默认是6
		setting.setMaxStuSelectNum("6"); //学生的最多选题个数,默认是6
		setting.setYard(yard);
		yard.setSetting(setting);
		Integer num = yardService.saveYard(yard);
		JSONObject result = new JSONObject();
		result.put("num", num);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
		return null;
	}
	
	/*
	 * 修改学院
	 */
	public String updateYard() throws Exception {
		Yard currentYard = yardService.getYardById(String.valueOf(yard.getId()));
		Yard namedYard = yardService.getYardByYardName(yard.getYardName());
		JSONObject result = new JSONObject();
		Integer num = 0;
		if (namedYard == null) {
			currentYard.setYardName(yard.getYardName());
			 yardService.updateYard(currentYard);
			 num = 1;
		}
		result.put("num", num);
		result.put("type", "update");
		ResponseUtil.write(result.toString());
		return null;
	}
	
	/*
	 * 删除学院
	 */
	public String deleteYard() throws Exception {
		String ids[] = request.getParameter("delIds").split(",");
		Integer delNums = yardService.deleteYard(ids);
		ResponseUtil.write(delNums.toString());
		return null;
	}

	/*
	 * 获取专业的信息
	 */
	public String majorList() throws Exception {
		//获取页面的查询条件
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		String majorName = request.getParameter("majorName");
		String yardId = request.getParameter("yardId");
		int total = majorService.countMajor(majorName,yardId);
		List<Major>majorList = majorService.ListMajor(page, rows, majorName, yardId);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Major m : majorList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("majorId", m.getId());
			jsonObject.put("yardId", m.getYard().getId());
			jsonObject.put("yardName", m.getYard().getYardName());
			jsonObject.put("majorName", m.getMajorName());
			jsonObject.put("majorDesc", m.getMajorDesc());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
		return null;
	}

	/*
	 * 获取学院信息动态实现组合框
	 */
	public String yardComboBox() throws Exception {
		List<Yard> yardList = yardService.ListYard();
		JSONArray result = new JSONArray();
		
		//将请选择放进来
		JSONObject firstObject = new JSONObject();
		firstObject.put("yardId", "0");
		firstObject.put("yardName", "请选择");
		result.add(firstObject);
		//将院系放进去
		for (Yard y : yardList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("yardId", y.getId());
			jsonObject.put("yardName", y.getYardName());
			result.add(jsonObject);
		}
		ResponseUtil.write(result.toString());
		return null;
	}

	/*
	 * 添加专业
	 */
	public void addMajor() throws Exception {
		String yardId = request.getParameter("yardId");
		JSONObject result = new JSONObject();
		Yard yard = yardService.getYardById(yardId);
		List<Major>majorList = majorService.ListMajorByMajorName(major.getMajorName());
		int count = 0;
		//当存在专业名称相同并且所在院系相同时，才不能添加
		if (! majorList.isEmpty()) {
			for (Major m : majorList) {
				if (m.getYard().getYardName().equals(yard.getYardName())) {
					result.put("num", count);
					ResponseUtil.write(result.toString());
					return ;
				}
			}
		} 	
		major.setYard(yard);
		majorService.saveMajor(major);
		count++;
		result.put("num", count);
		ResponseUtil.write(result.toString());
	}
	/**
	 * 修改专业
	 * @throws Exception
	 */
	public void updateMajor() throws Exception {
		String yardId = request.getParameter("yardId");
		Yard yard = yardService.getYardById(yardId);
		JSONObject result = new JSONObject();
		int count = 0;
		List<Major>majorList = majorService.ListMajorByMajorName(major.getMajorName());
		//当存在专业名称相同并且所在院系相同时，才不能修改
				if (! majorList.isEmpty()) {
					for (Major m : majorList) {
						if (m.getYard().getYardName().equals(yard.getYardName())) {
							result.put("num", count);
							ResponseUtil.write(result.toString());
							return ;
						}
					}
				}
		major.setYard(yard);
		majorService.updateMajor(major);
		count++;
		result.put("num", count);
		ResponseUtil.write(result.toString());
		
	}
	/*
	 * 删除专业
	 */
	public void deleteMajor() throws Exception {
		String ids[] = request.getParameter("delIds").split(",");
		Integer delNums = majorService.deleteMajor(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	  *由管理员查看系主任信息(可能包含查询条件)
	 */
	public void deptHeadList() throws Exception {
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		
		String deptHeadName = request.getParameter("deptHeadName");
		String yardId = request.getParameter("yardId");

		List<DeptHead> deptHeadList = deptHeadService.ListDeptHead(page, rows, deptHeadName, yardId);
		int total = deptHeadService.countDeptHead(deptHeadName, yardId);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (DeptHead deptHead : deptHeadList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deptHeadId", deptHead.getId());
			jsonObject.put("yardId", deptHead.getYard().getId());
			jsonObject.put("yardName", deptHead.getYard().getYardName());
			jsonObject.put("realName", deptHead.getRealName());
			jsonObject.put("userName", deptHead.getUser().getUserName());
			jsonObject.put("password", deptHead.getUser().getPassword());
			jsonObject.put("sex", deptHead.getSex());
			jsonObject.put("phone", deptHead.getPhone());
			jsonObject.put("email", deptHead.getEmail());
			jsonArray.add(jsonObject);
		}
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(result.toString());
	}
	// 添加系主任
	public void addDeptHead() throws Exception {
		String yardId = request.getParameter("yardId");
		
		User currentUser = userService.getUserByUserName(user.getUserName());
		Integer flag = 0;
		JSONObject result = new JSONObject();
		if (currentUser == null) {
			//用户为空,表示不存在可以添加,先添加用户
			user.setUserType("DeptHead");
			user.setPassword(StringUtil.encryptMd5(user.getPassword()));
			userService.saveUser(user);
			//用户添加后需要获取数据库持久层的该用户，并放入系主任中(id相对应)
			currentUser = userService.getUserByUserName(user.getUserName());
			deptHead.setUser(currentUser);
			Yard yard = yardService.getYardById(yardId);	//查询完整的Yard对象
			deptHead.setYard(yard); 	//完整对象放入系主任
			deptHeadService.saveDeptHead(deptHead);
			flag++;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// 修改系主任
	public void updateDeptHead() throws Exception {
		String yardId = request.getParameter("yardId");
		
		// 查询完整的Yard
		Yard yard = yardService.getYardById(yardId);
		// 根据id查询数据库的当前对象
		DeptHead currentDeptHead = deptHeadService.getDeptHeadById(deptHead.getId());
		currentDeptHead.setYard(yard);
		currentDeptHead.setRealName(deptHead.getRealName());
		currentDeptHead.setEmail(deptHead.getEmail());
		currentDeptHead.setPhone(deptHead.getPhone());
		currentDeptHead.setSex(deptHead.getSex());
		if (! currentDeptHead.getUser().getPassword().equals(user.getPassword())) {
			currentDeptHead.getUser().setPassword(StringUtil.encryptMd5(user.getPassword()));
		}
		deptHeadService.updateDeptHead(currentDeptHead);
		JSONObject result = new JSONObject();
		result.put("type", "update");
		ResponseUtil.write(result.toString());
	}
	
	/*
	 * 删除系主任
	 */
	public void deleteDeptHead() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = deptHeadService.deleteDeptHead(ids);
		ResponseUtil.write(delNums.toString());	
	}
	/*
	 * 导出系主任
	 */
	public void exportDeptHeads() throws Exception {
		// 查询所有系主任
		List<DeptHead> deptHeadList = deptHeadService.listDeptHead();
		// 创建工作簿
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "序号", "账号", "姓名", "学院", "性别", "电话", "邮箱" };
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // 创建一个行索引
		Row row = sheet.createRow(rowIndex++); // 创建第一行
		for (int i = 0; i < heads.length; i++) {
			row.createCell(i).setCellValue(heads[i]);
		}

		for (DeptHead d : deptHeadList) {
			row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(rowIndex-1);
			row.createCell(1).setCellValue(d.getUser().getUserName());
			row.createCell(2).setCellValue(d.getRealName());
			row.createCell(3).setCellValue(d.getYard().getYardName());
			row.createCell(4).setCellValue(d.getSex());
			row.createCell(5).setCellValue(d.getPhone());
			row.createCell(6).setCellValue(d.getEmail());
		}
		
		ResponseUtil.export(wb, "deptHead.xls");
	}
    
	/*
	 * 导入系主任
	 */
	public void importDeptHeads() throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(deptHeadUploadFile));
		Sheet sheet = wb.getSheetAt(0);
		int count = 0;
		if (sheet!=null) {
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if(row == null){
					continue;
				}
				User user =new User();
				user.setUserName(ExcelUtil.formatCell(row.getCell(0)));
				User currentUser = userService.getUserByUserName(user.getUserName());
				if (currentUser == null) {
					user.setPassword(StringUtil.encryptMd5(ExcelUtil.formatCell(row.getCell(1))));
					user.setUserType("DeptHead");
					DeptHead deptHead = new DeptHead();
					deptHead.setRealName(ExcelUtil.formatCell(row.getCell(2)));
					//根据院系名称查询院系实体(可能系存在多个，所以查出来是集合)
					Yard yard = yardService.getYardByYardName(ExcelUtil.formatCell(row.getCell(3)));					
				  } else continue;
				if (yard!=null) {
					//只有系存在的情况下，才能持久化系主任及用户
					userService.saveUser(user);
					User savedUser = userService.getUserByUserName(user.getUserName());
					deptHead.setUser(savedUser);
					deptHead.setYard(yard);
					deptHead.setSex(ExcelUtil.formatCell(row.getCell(5)));
					deptHead.setPhone(ExcelUtil.formatCell(row.getCell(6)));
					deptHead.setEmail(ExcelUtil.formatCell(row.getCell(7)));
					deptHeadService.saveDeptHead(deptHead);
					count++;	
				}
		}
		}
		JSONObject result = new JSONObject();
		result.put("num", count);
		ResponseUtil.write(result.toString());
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Yard getYard() {
		return yard;
	}

	public void setYard(Yard yard) {
		this.yard = yard;
	}
	
	public DeptHead getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(DeptHead deptHead) {
		this.deptHead = deptHead;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public File getDeptHeadUploadFile() {
		return deptHeadUploadFile;
	}

	public void setDeptHeadUploadFile(File deptHeadUploadFile) {
		this.deptHeadUploadFile = deptHeadUploadFile;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
	
	
}
