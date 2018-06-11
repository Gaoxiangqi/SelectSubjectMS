package com.qgx.selectSubjectMS.service;

import java.util.List;

import com.qgx.selectSubjectMS.entity.DeptHead;

/**
 * ϵ���ι��ܽӿ�
 * @author goxcheer
 *
 */
public interface DeptHeadService {
	/**
	 * �����û�Id��ȡϵ����
	 * @param userId
	 * @return
	 */
	DeptHead findDeptHeadByUserId(Long userId);
    /**
     * ��ѯϵ���μ���
     * @return
     */
	List<DeptHead> listDeptHead();
    /**
     * ���뱣��ϵ����
     * @param deptHead
     */
	void saveDeptHead(DeptHead deptHead);
	/**
	 * ����ϵ����
	 * @param currentDeptHead
	 */
	void updateDeptHead(DeptHead currentDeptHead);
	/**
	 * �������ͷ�ҳ��ѯϵ���μ���
	 * @param page
	 * @param rows
	 * @param deptHeadName
	 * @param yardId
	 * @return
	 */
	List<DeptHead> ListDeptHead(Integer page, Integer rows, String deptHeadName, String yardId);
	/**
	 * ������ͳ��ϵ��������
	 * @param deptHeadName
	 * @param yardId
	 * @return
	 */
	int countDeptHead(String deptHeadName, String yardId);
	/**
	 * ����ϵ����id��ȡϵ����
	 * @param id
	 * @return
	 */
	DeptHead getDeptHeadById(Long id);
	/**
	 * ɾ��ϵ����
	 * @param ids
	 * @return
	 */
	Integer deleteDeptHead(String[] ids);
	
    
}
