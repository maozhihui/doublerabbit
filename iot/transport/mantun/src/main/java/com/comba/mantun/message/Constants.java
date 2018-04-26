package com.comba.mantun.message;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.comba.mantun.service.AuthorizeConfig;
import com.google.gson.Gson;

/**
 * 维护常量
 * @author maozhihui
 * @date 2017年12月15日 下午2:09:05
 */
public class Constants {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static final Gson GSON = new Gson();
	public static final String ACCESS_TOKEN_TYPE = "authorization_code";
	public static final String REFRESH_TOKEN_TYPE = "refresh_token";
	
	// 定义变量名
	public static final String CLIENT_ID_VAR = "client_id";
	public static final String METHOD_VAR = "method";
	public static final String ACCESS_TOKEN_VAR = "access_token";
	public static final String TIMESTAMP_VAR = "timestamp";
	public static final String SIGN_VAR = "sign";
	public static final String PROJECTCODE_VAR = "projectCode";
	public static final String MAC_VAR = "mac";
	public static final String CMD_VAR = "cmd";
	public static final String VALUE1_VAR = "value1";
	public static final String VALUE2_VAR = "value2";

	public static volatile String token;
	public static AuthorizeConfig configuration;
	public static Map<String, BoxBaseInfo> boxMapInfo = new ConcurrentHashMap<>();
	public static Map<String, List<ChannelInfo>> channelMapInfo = new ConcurrentHashMap<>();

	// 接口方法定义
	// 获取所有电箱
	public static final String GET_BOXES_METHOD = "GET_BOXES";
	// 获取电箱实时信息
	public static final String GET_BOXES_REALTIME_METHOD = "GET_BOX_CHANNELS_REALTIME";
	// 电箱线路控制
	public static final String PUT_BOX_CONTROL_METHOD = "PUT_BOX_CONTROL";
	
	public static String getCurrentDate(){
		return DATE_FORMAT.format(System.currentTimeMillis());
	}

	// 属性参数名称定义
	// 开关状态
	public static final String SWITCH_ATTR = "switch";
	// 功率
	public static final String WATT_ATTR = "watt";
	// 电压
	public static final String VOLTAGE_ATTR = "voltage";
	// 电流
	public static final String ELECTRICITY_ATTR = "electricity";
	// 温度
	public static final String TEMPERATURE_ATTR = "temperature";
	// 累计到当前的总电量
	public static final String POWER_ATTR = "power";
}
