package com.qgx.selectSubjectMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.DeptHead;
import com.qgx.selectSubjectMS.service.DeptHeadService;

@Service("deptHeadService")
@Transactional
public class DeptHeadServiceImpl implements DeptHeadService {
    
	@Resource
	private BaseDao<DeptHead>deptHeadDao;
	
	
	
	@Override
	public DeptHead findDeptHeadByUserId(Long userId) {
		return deptHeadDao.get("from DeptHead d where d.user.id = ?", new Object[]{userId});
	}

	@Override
	public List<DeptHead> listDeptHead() {	
		return deptHeadDao.find("from DeptHead");
	}
	
	@Override
	public void saveDeptHead(DeptHead deptHead) {
		deptHeadDao.save(deptHead);
	}

	@Override
	public void updateDeptHead(DeptHead currentDeptHead) {
		deptHeadDao.update(currentDeptHead);	
	}

	@Override
	public List<DeptHead> ListDeptHead(Integer page, Integer rows, String deptHeadName, String yardId) {
		if (deptHeadName == null) {
			deptHeadName = "%%";
		} else {
			deptHeadName = "%" + deptHeadName + "%";
		}
		StringBuffer hql = new StringBuffer("from DeptHead d where realName like ?");
		Object []params = new Object[]{deptHeadName};
		
		if (yardId !=null && ! "0".equals(yardId)) {
			hql.append(" and d.yard.id = ?");
			params = new Object[]{deptHeadName,Long.valueOf(yardId)};
		}
		return deptHeadDao.find(hql.toString(), params, page, rows);
	}

	@Override
	public int countDeptHead(String deptHeadName, String yardId) {
		if (deptHeadName == null) {
			deptHeadName = "%%";
		} else {
			deptHeadName = "%" + deptHeadName + "%";
		}
		StringBuffer hql = new StringBuffer("select count(*) from DeptHead d where realName like ?");
		Object []params = new Object[]{deptHeadName};
		
		if (yardId !=null && ! "0".equals(yardId)) {
			hql.append(" and d.yard.id = ?");
			params = new Object[]{deptHeadName,Long.valueOf(yardId)};
		}
		return deptHeadDao.count(hql.toString(),params).intValue();
	}

	@Override
	public DeptHead getDeptHeadById(Long id) {
		return deptHeadDao.get(DeptHead.class, id);
	}

	@Override
	public Integer deleteDeptHead(String[] ids) {
		int count = 0;
		for (int i=0; i<ids.length; i++){
			deptHeadDao.delete(deptHeadDao.get(DeptHead.class, Long.valueOf(ids[i])));
			count++;
		}
		return count;
	}
	
	
}
