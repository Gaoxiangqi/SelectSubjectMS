package com.qgx.selectSubjectMS.service;

import com.qgx.selectSubjectMS.entity.Setting;

/**
 * ͨ������ҵ���
 * @author goxcheer
 *
 */
public interface SettingService {
	/**
	 * ��ȡ���ݿ��������Ϣ
	 * @return
	 */
	Setting getSetting();
	/**
	 * ��������
	 * @param setting
	 */
	void updateSetting(Setting setting);
	

}
