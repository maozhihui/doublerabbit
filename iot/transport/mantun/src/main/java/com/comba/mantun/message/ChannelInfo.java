package com.comba.mantun.message;

import lombok.Data;

/**
 * 线路信息类
 * @author maozhihui
 * @date 2017年12月15日 下午4:22:27
 */
@Data
public class ChannelInfo {

	// 线路地址
	private long addr;
	// 线路名称
	private String title;
	// 线路状态(true表示开,false表示关)
	private boolean oc;
	// 功率
	private float aW;
	// 电压
	private float aV;
	// 电流
	private float aA;
	// 温度
	private float aT;
	// 电量（累计到现在的总电量）
	private float power;
	// 更新时间
	private String updateTime;
	// 是否在线(true表示设备在线,false表示设备不在线)
	private boolean online;
	// 是否手动关闭(true表示否,false表示是)
	private boolean enableNetCtrl;
}
