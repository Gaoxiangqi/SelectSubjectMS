package com.qgx.selectSubjectMS.utils;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/**
 * ����Excel�Ĺ�����
 * 
 * @author goxcheer
 *
 */
public class ExcelUtil {
    /**
     * ��cellת��Ϊ�ַ���
     * @param hssfCell
     * @return
     */
	public static String formatCell(Cell cell) {
		if (cell == null) {
			return "";
		} else {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue()).trim();
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				DecimalFormat df = new DecimalFormat("0");
				return df.format(cell.getNumericCellValue()).trim();
			} else {
				return String.valueOf(cell.getStringCellValue()).trim();
			}
		}
	}
}
