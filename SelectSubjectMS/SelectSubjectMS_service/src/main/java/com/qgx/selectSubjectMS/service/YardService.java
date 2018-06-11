package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.Yard;

/**
 * ѧԺ���ݽӿ�
 * @author Administrator
 *
 */
public interface YardService {
	/**
	 * ͳ��Ժϵ�ĸ���
	 * @return
	 */
	int countYard();
	/**
	 * ��ҳ��ѯ����Ժϵ
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Yard> ListYard(Integer page, Integer rows);
	/**
	 * ���Ժϵ
	 * @param yard
	 * @return
	 */
	Integer saveYard(Yard yard);
	/**
	 * ɾ��ѧԺ
	 * @param ids
	 * @return
	 */
	Integer deleteYard(String[] ids);
	
	/**
	 *��ȡ����Ժϵ��Ϣ��ʼ�������� 
	 * @return
	 */
	List<Yard> ListYard();
	/**
	 * ����id��ȡѧԺ
	 * @param id
	 * @return
	 */
	Yard getYardById(String id);
	/**
	 * ����Ժϵ���ƻ�ȡѧԺ
	 * @param yardName
	 * @return
	 */
	Yard getYardByYardName(String yardName);
	/**
	 * ����ѧԺ
	 * @param yard
	 */
	void updateYard(Yard yard);

}