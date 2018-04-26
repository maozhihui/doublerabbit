package com.comba.web.common.export;

import java.util.Map;

/**
 * 导出列描述
 * @author wengzhonghui
 *
 */
public class ExportColumn {

	private String field;
	private String title;
	private String width;
	private String exportFormatter;
	private String isHide;
	
	/*导出参数属性*/
	
	
	public ExportColumn(int index,Map<String, Object> parameterMap) {
		String paramIndex = "columns["+index+"]";
		this.field = getKeyVal(parameterMap, paramIndex + "[field]");
		this.title = getKeyVal(parameterMap, paramIndex + "[title]");
		this.width = getKeyVal(parameterMap, paramIndex + "[width]");
		this.exportFormatter = getKeyVal(parameterMap, paramIndex + "[exportFormatter]");
		this.isHide = getKeyVal(parameterMap, paramIndex + "[isHide]");
	}
	
	public static String getKeyVal(Map<String, Object> parameterMap,String key){
		if(parameterMap.containsKey(key) && parameterMap.get(key)!=null){
			return parameterMap.get(key).toString();
		}else{
			return null;
		}
	}
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getExportFormatter() {
		return exportFormatter;
	}
	public void setExportFormatter(String exportFormatter) {
		this.exportFormatter = exportFormatter;
	}
	public String getIsHide() {
		return isHide;
	}
	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	
	
	
}
