package com.comba.web.common.export;


/**
 * 格式化导出表格值
 * @author wengzhonghui
 *
 */
public class CellFormatter {

	
	/**
	 * 根据格式格式化值
	 * @param formatterName
	 * @param value
	 * @return
	 */
	public static String formatterVal(String formatterName,String value){
		if("isOrNotFormatter".equalsIgnoreCase(formatterName)){
			return isOrNotFormatter(value);
		}else if("sexFormatter".equalsIgnoreCase(formatterName)){
			return sexFormatter(value);
		}
		return "";
	}
	/**
	 * 是或否格式化，1或true返回是，其他返回否
	 * @return
	 */
	public static String isOrNotFormatter(String value){
		if(value!=null&&("1".equals(value) || "true".equalsIgnoreCase(value))){
			return "是";
		}else {
			return "否";
		}
	}
	
	/**
	 * 性别格式化
	 * @param value
	 * @return
	 */
	public static String sexFormatter(String value){
		if(value!=null&&("1".equals(value))){
			return "男";
		}else {
			return "女";
		}
	}
	
	
	
}
