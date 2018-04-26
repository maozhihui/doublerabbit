package com.comba.web.utils;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * 系统基础配置类
 * @author wengzhonghui
 *
 */
@Component
public class CommonProperties {

	private static Logger logger =  Logger.getLogger(CommonProperties.class);
	/** 国际化配置，1：中文，2：英文 */
	public static final String localeLanguage_zhcn = "zhCN";
	public static final String localeLanguage_enus = "enUS";
	public static String localeLanguage = localeLanguage_zhcn;
	@Value("${i18n.locale}")
	public void setI18n(String i18nLocale) {
		localeLanguage = ("2".equals(i18nLocale)||"en_US".equals(i18nLocale)) 
			? localeLanguage_enus : localeLanguage_zhcn;
		logger.info("Locale language set [" + localeLanguage + "]");
	}
	
	/*导出文件*/
	public static String downloadExcelPath = "";
	@Value("${download.excel.path}")
	public void setTaskFileViewPath(String downloadExcelPath) {
		CommonProperties.downloadExcelPath = downloadExcelPath;
	}
	
	/*最大支持导出数据条数设置*/
	public static int maxAllowExportNum = 1000;
	@Value("${max.allow.export.num}")
	public void setMaxAllowExportNum(Integer maxAllowExportNum) {
		CommonProperties.maxAllowExportNum = maxAllowExportNum;
	}
	
	/*插件类型设置*/
	public static Map<String,String> PLUGIN_TYPES = new HashMap<String,String>();
	@Value("${plugin.types}")
	public void setPluginTypes(String pluginTypes) {
		if(pluginTypes!=null && pluginTypes.length()>0){
			String[] types = pluginTypes.split(",");
			for(String type : types){
				if(type!=null){
					String[] typeTemp = type.split("=");
					if(typeTemp.length>1){
						PLUGIN_TYPES.put(typeTemp[0], typeTemp[1]);
					}
				}
			}
			
		}
	}
	
	/*过滤器类型设置*/
	public static Map<String,String> FILTER_TYPES = new HashMap<String,String>();
	@Value("${filter.types}")
	public void setFilterTypes(String filterTypes) {
		if(filterTypes!=null && filterTypes.length()>0){
			String[] types = filterTypes.split(",");
			for(String type : types){
				if(type!=null){
					String[] typeTemp = type.split("=");
					if(typeTemp.length>1){
						FILTER_TYPES.put(typeTemp[0], typeTemp[1]);
					}
				}
			}
			
		}
	}
	
	/*处理器类型设置*/
	public static Map<String,String> PROCCESSOR_TYPES = new HashMap<String,String>();
	@Value("${process.types}")
	public void setProccessorTypes(String processTypes) {
		if(processTypes!=null && processTypes.length()>0){
			String[] types = processTypes.split(",");
			for(String type : types){
				if(type!=null){
					String[] typeTemp = type.split("=");
					if(typeTemp.length>1){
						PROCCESSOR_TYPES.put(typeTemp[0], typeTemp[1]);
					}
				}
			}
			
		}
	}


    //ftp 用户名
    public static String FTP_USER_NAME = "";
    @Value("${upgrade.user}")
    public  String getFtpUserName(final String ftpUserName) {
        return FTP_USER_NAME = ftpUserName;
    }

    //ftp密码
    public static String FTP_PASSWD = "";
    @Value("${upgrade.password}")
    public  String getFtpPassword(final String ftpPassword) {
        return FTP_PASSWD = ftpPassword;
    }


    public static String FTP_UPGRADE="";
    @Value("${upgrade.upgrade}")
    public  String getFtpUpgrade(final String ftpUpgrade) {
        return FTP_UPGRADE = ftpUpgrade;
    }

    public static String FTP_IP = ""; //ip
    @Value("${upgrade.host}")
    public  String getFtpIp(final String ftpIp) {
        return FTP_IP = ftpIp;
    }

    public static String FTP_PROTOCOL = "";
    @Value("${upgrade.protocol}")
    public  String getFtpProtocol(final String ftpProtocol) {
        return FTP_PROTOCOL = ftpProtocol;
    }

    public  static Integer FTP_PORT = 0;//端口号
    @Value("${upgrade.port}")
    public  Integer getFtpPort(final Integer ftpPort) {
        return FTP_PORT = ftpPort;
    }

    public static String FTP_PATH= "";
    @Value("${upgrade.path}")
    public  String getFtpPath(final String ftpPath) {
        return FTP_PATH = ftpPath;
    }












}
