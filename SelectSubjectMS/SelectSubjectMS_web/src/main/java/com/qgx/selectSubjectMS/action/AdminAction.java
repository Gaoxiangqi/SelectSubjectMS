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
	 * �޸Ĺ���Ա��Ϣ
	 */
	public String updatePersonalInfo() throws Exception {
		// ��ȡSession�е�admin�û���Ϣ
		Admin currentAdmin = (Admin) request.getSession().getAttribute("currentUser");
		// �޸���Ϣ
		currentAdmin.setRealName(admin.getRealName());
		currentAdmin.setPhone(admin.getPhone());
		currentAdmin.setEmail(admin.getEmail());
		// �������ݿ����Ϣ
		adminService.updateAdmin(currentAdmin);
		// ����Session�е���Ϣ
		request.getSession().setAttribute("currentUser", currentAdmin);
		return null;
	}

	/*
	 * �޸�����
	 */
	public String modifyPwd() throws Exception {
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		Admin admin = (Admin) request.getSession().getAttribute("currentUser");
		// ����id�������ݿ���û�
		User user = userService.findUserById(admin.getUser().getId());
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

	/*
	 * ��ȡѧԺ����Ϣ
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
	 * ���ѧԺ
	 */
	public String addYard() throws Exception {
		Setting setting = new Setting(); //ѧԺ����ʱ��Ҫ��ѡ������һ����ʼֵ
		setting.setWarnNum("3");//־Ը��������,Ĭ��Ϊ3
		setting.setMaxSelectNum("4"); //ÿ��������ѡ������,Ĭ����4
		setting.setMaxSetNum("6"); //��ʦ����������,Ĭ����6
		setting.setMaxStuSelectNum("6"); //ѧ�������ѡ�����,Ĭ����6
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
	 * �޸�ѧԺ
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
	 * ɾ��ѧԺ
	 */
	public String deleteYard() throws Exception {
		String ids[] = request.getParameter("delIds").split(",");
		Integer delNums = yardService.deleteYard(ids);
		ResponseUtil.write(delNums.toString());
		return null;
	}

	/*
	 * ��ȡרҵ����Ϣ
	 */
	public String majorList() throws Exception {
		//��ȡҳ��Ĳ�ѯ����
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
	 * ��ȡѧԺ��Ϣ��̬ʵ����Ͽ�
	 */
	public String yardComboBox() throws Exception {
		List<Yard> yardList = yardService.ListYard();
		JSONArray result = new JSONArray();
		
		//����ѡ��Ž���
		JSONObject firstObject = new JSONObject();
		firstObject.put("yardId", "0");
		firstObject.put("yardName", "��ѡ��");
		result.add(firstObject);
		//��Ժϵ�Ž�ȥ
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
	 * ���רҵ
	 */
	public void addMajor() throws Exception {
		String yardId = request.getParameter("yardId");
		JSONObject result = new JSONObject();
		Yard yard = yardService.getYardById(yardId);
		List<Major>majorList = majorService.ListMajorByMajorName(major.getMajorName());
		int count = 0;
		//������רҵ������ͬ��������Ժϵ��ͬʱ���Ų������
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
	 * �޸�רҵ
	 * @throws Exception
	 */
	public void updateMajor() throws Exception {
		String yardId = request.getParameter("yardId");
		Yard yard = yardService.getYardById(yardId);
		JSONObject result = new JSONObject();
		int count = 0;
		List<Major>majorList = majorService.ListMajorByMajorName(major.getMajorName());
		//������רҵ������ͬ��������Ժϵ��ͬʱ���Ų����޸�
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
	 * ɾ��רҵ
	 */
	public void deleteMajor() throws Exception {
		String ids[] = request.getParameter("delIds").split(",");
		Integer delNums = majorService.deleteMajor(ids);
		ResponseUtil.write(delNums.toString());
	}

	/*
	  *�ɹ���Ա�鿴ϵ������Ϣ(���ܰ�����ѯ����)
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
	// ���ϵ����
	public void addDeptHead() throws Exception {
		String yardId = request.getParameter("yardId");
		
		User currentUser = userService.getUserByUserName(user.getUserName());
		Integer flag = 0;
		JSONObject result = new JSONObject();
		if (currentUser == null) {
			//�û�Ϊ��,��ʾ�����ڿ������,������û�
			user.setUserType("DeptHead");
			user.setPassword(StringUtil.encryptMd5(user.getPassword()));
			userService.saveUser(user);
			//�û���Ӻ���Ҫ��ȡ���ݿ�־ò�ĸ��û���������ϵ������(id���Ӧ)
			currentUser = userService.getUserByUserName(user.getUserName());
			deptHead.setUser(currentUser);
			Yard yard = yardService.getYardById(yardId);	//��ѯ������Yard����
			deptHead.setYard(yard); 	//�����������ϵ����
			deptHeadService.saveDeptHead(deptHead);
			flag++;
		}
		result.put("num", flag);
		result.put("type", "add");
		ResponseUtil.write(result.toString());
	}

	// �޸�ϵ����
	public void updateDeptHead() throws Exception {
		String yardId = request.getParameter("yardId");
		
		// ��ѯ������Yard
		Yard yard = yardService.getYardById(yardId);
		// ����id��ѯ���ݿ�ĵ�ǰ����
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
	 * ɾ��ϵ����
	 */
	public void deleteDeptHead() throws Exception {
		String[] ids = request.getParameter("delIds").split(",");
		Integer delNums = deptHeadService.deleteDeptHead(ids);
		ResponseUtil.write(delNums.toString());	
	}
	/*
	 * ����ϵ����
	 */
	public void exportDeptHeads() throws Exception {
		// ��ѯ����ϵ����
		List<DeptHead> deptHeadList = deptHeadService.listDeptHead();
		// ����������
		Workbook wb = new HSSFWorkbook();
		String heads[] = { "���", "�˺�", "����", "ѧԺ", "�Ա�", "�绰", "����" };
		Sheet sheet = wb.createSheet();
		int rowIndex = 0; // ����һ��������
		Row row = sheet.createRow(rowIndex++); // ������һ��
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
	 * ����ϵ����
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
					//����Ժϵ���Ʋ�ѯԺϵʵ��(����ϵ���ڶ�������Բ�����Ǽ���)
					Yard yard = yardService.getYardByYardName(ExcelUtil.formatCell(row.getCell(3)));					
				  } else continue;
				if (yard!=null) {
					//ֻ��ϵ���ڵ�����£����ܳ־û�ϵ���μ��û�
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
