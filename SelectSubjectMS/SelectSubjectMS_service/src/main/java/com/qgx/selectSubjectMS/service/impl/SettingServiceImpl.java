package com.qgx.selectSubjectMS.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qgx.selectSubjectMS.dao.BaseDao;
import com.qgx.selectSubjectMS.entity.Setting;
import com.qgx.selectSubjectMS.service.SettingService;
@Service("settingService")
@Transactional
public class SettingServiceImpl implements SettingService {
	
	@Resource
	private BaseDao<Setting>settingDao;

	@Override
	public Setting getSetting() {
		return settingDao.get("from Setting",new Object[]{});
	}

	@Override
	public void updateSetting(Setting setting) {
		settingDao.saveOrUpdate(setting);	
	}

	
}
