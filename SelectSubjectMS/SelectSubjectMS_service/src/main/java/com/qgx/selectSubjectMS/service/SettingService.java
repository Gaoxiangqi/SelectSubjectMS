package com.qgx.selectSubjectMS.service;

import com.qgx.selectSubjectMS.entity.Setting;

/**
 * 通用设置业务层
 * @author goxcheer
 *
 */
public interface SettingService {
	/**
	 * 获取数据库的设置信息
	 * @return
	 */
	Setting getSetting();
	/**
	 * 更新设置
	 * @param setting
	 */
	void updateSetting(Setting setting);
	

}
