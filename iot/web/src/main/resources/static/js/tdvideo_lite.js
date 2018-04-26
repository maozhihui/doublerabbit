Sys_Cleanup_Cmd					=0x00000001
Sys_LocalTime_Set				=0x00000002
Sys_Restore_Cmd					=0x00000003
Sys_Reboot_Cmd					=0x00000004
Sys_AdjustLayout_Cmd			=0x00000005
Sys_FullScreen_Cmd				=0x00000006
Sys_UpgradeProgram_Cmd  =0x00000007
Sys_UpgradeWebPage_Cmd			=0x00000008
Sys_UpgradeProgress_Get			=0x00000009
Sys_SysInfo_Get					=0x0000000a
Sys_Style_Set					=0x0000000b
Sys_Backup_Cmd                  =0x0000000c

Entry_Logon_Cmd					=0x00010000
Entry_Logoff_Cmd				=0x00010001
Entry_Prop_Get					=0x00010002
Entry_NVSList_Get				=0x00010003
Entry_NVSVer_Get				=0x00010004
Entry_ObsLst_Get				=0x00010005

Obs_Prop_Get					=0x00020000
Obs_FullScreen_Cmd				=0x00020001
Obs_Add_Cmd						=0x00020002
Obs_Remove_Cmd					=0x00020003
Obs_StartRealTime_Cmd			=0x00020004
Obs_StartPlayBack_Cmd			=0x00020005
Obs_Pause_Cmd					=0x00020006
Obs_FastForward_Cmd				=0x00020007
Obs_SlowForward_Cmd				=0x00020008
Obs_Stop_Cmd					=0x00020009
Obs_ShowParam_Cmd				=0x0002000a
Obs_MicEnable_Set				=0x0002000b
Obs_SpeakerEnable_Set			=0x0002000c
Obs_Capture_Cmd					=0x0002000d
Obs_BeginRec_Cmd				=0x0002000e
Obs_EndRec_Cmd					=0x0002000f
Obs_Progress_Get				=0x00020010
Obs_ShowMsg_Cmd					=0x00020011
Obs_Volume_Set					=0x00020012
Obs_Volume_Get					=0x00020013
Obs_Monopolize_Cmd				=0x00020014
Obs_StartPlayBackOffLine_Cmd	=0x00020015
Obs_3dLocateEnable_Set			=0x00020016
Obs_3dLocateEnable_Get			=0x00020017

Dev_Type_Set					=0x00030000
Dev_Type_Get					=0x00030001
Dev_AllType_Get					=0x00030002
Dev_Controllable_Get			=0x00030003
Dev_Control_Cmd					=0x00030004
Dev_Model_Get					=0x00030005
Dev_Device_Ctrl					=0x00030006

Alarm_MDEnable_Set				=0x00040000
Alarm_MDEnable_Get				=0x00040001
Alarm_MDThreshold_Set			=0x00040002
Alarm_MDThreshold_Get			=0x00040003
Alarm_MDArea_Get				=0x00040004
Alarm_MDClean_Cmd				=0x00040005
Alarm_VCEnable_Set				=0x00040006
Alarm_VCEnable_Get				=0x00040007
Alarm_VCThreshold_Set			=0x00040008
Alarm_VCThreshold_Get			=0x00040009
Alarm_VCArea_Set				=0x0004000a
Alarm_VCArea_Get				=0x0004000b
Alarm_VCClean_Cmd				=0x0004000c
Alarm_VLEnable_Set				=0x0004000d
Alarm_VLEnable_Get				=0x0004000e
Alarm_PortCnt_Get				=0x0004000f
Alarm_InputEnable_Set			=0x00040010
Alarm_InputEnable_Get			=0x00040011
Alarm_InputMode_Set				=0x00040012
Alarm_InputMode_Get				=0x00040013
Alarm_Link_Set					=0x00040014
Alarm_Link_Get					=0x00040015
Alarm_OutputMode_Set			=0x00040016
Alarm_OutputMode_Get			=0x00040017
Alarm_EmailEnable_Set			=0x00040018
Alarm_EmailEnable_Get			=0x00040019
Alarm_EmailInfo_Set				=0x0004001a
Alarm_EmailInfo_Get				=0x0004001b
Alarm_DrawEnable_Set			=0x0004001c
Alarm_DrawEnable_Get			=0x0004001d
Alarm_Server_Set				=0x0004001e
Alarm_Server_Get				=0x0004001f
Alarm_Schedule_Set				=0x00040020
Alarm_Schedule_Get				=0x00040021
Alarm_OutPortDelay_Set			=0x00040022
Alarm_OutPortDelay_Get			=0x00040023
Alarm_LinkRec_Set				=0x00040024
Alarm_LinkRec_Get				=0x00040025
Alarm_LinkSnap_Set				=0x00040026
Alarm_LinkSnap_Get				=0x00040027
Alarm_LinkOutPort_Set			=0x00040028
Alarm_LinkOutPort_Get			=0x00040029
Alarm_LinkPTZ_Set				=0x0004002a
Alarm_LinkPTZ_Get				=0x0004002b
Alarm_InPortEnable_Set			=0x0004002c
Alarm_InPortEnable_Get			=0x0004002d
Alarm_OutPortEnable_Set			=0x0004002e
Alarm_OutPortEnable_Get			=0x0004002f
Alarm_OutPortState_Set			=0x00040030

User_MaxConn_Set				=0x00050000
User_MaxConn_Get				=0x00050001
User_UserList_Get				=0x00050002
User_AddUser_Cmd				=0x00050003
User_DelUser_Cmd				=0x00050004
User_ModifyPwd_Cmd				=0x00050005

Osd_Type_Set					=0x00060000
Osd_Type_Get					=0x00060001
Osd_Text_Set					=0x00060002
Osd_Text_Get					=0x00060003
Osd_TextEx_Set					=0x00060004
Osd_TextEx_Get					=0x00060005

Video_StreamType_Set			=0x00070000
Video_StreamType_Get			=0x00070001
Video_MaxSize_Get				=0x00070002
Video_FrameRate_Set				=0x00070003
Video_FrameRate_Get				=0x00070004
Video_Display_Set				=0x00070005
Video_Display_Get				=0x00070006
Video_Quality_Set				=0x00070007
Video_Quality_Get				=0x00070008
Video_KbitRate_Set				=0x00070009
Video_KbitRate_Get				=0x0007000a
Video_Size_Set					=0x0007000b
Video_Size_Get					=0x0007000c
Video_Encode_Set				=0x0007000d
Video_Encode_Get				=0x0007000e
Video_NP_Set					=0x0007000f
Video_NP_Get					=0x00070010
Video_Scene_Set					=0x00070011
Video_Scene_Get					=0x00070012
Video_Reverse_Set				=0x00070013
Video_Reverse_Get				=0x00070014
Video_Mirror_Set				=0x00070015
Video_Mirror_Get				=0x00070016
Video_PreferMode_Set			=0x00070017
Video_PreferMode_Get			=0x00070018
Video_DisplaySchedule_Set		=0x00070019
Video_DisplaySchedule_Get		=0x0007001a
Video_IFrameRate_Set			=0x0007001b
Video_IFrameRate_Get			=0x0007001c
Video_MaxMinorSize_Get			=0x0007001d
Video_VencType_Set				=0x0007001e
Video_VencType_Get				=0x0007001f

Audio_Chann_Set					=0x00080000
Audio_Chann_Get					=0x00080001
Audio_Encoder_Set				=0x00080002
Audio_Encoder_Get				=0x00080003

Query_Query_Cmd					=0x00090000

Store_LocalPath_Set				=0x000a0001
Store_LocalPath_Get				=0x000a0002
Store_RmtRecExt_Set				=0x000a0003
Store_RmtRecExt_Get				=0x000a0004
Store_ManualRecOnRmt_Cmd		=0x000a0005
Store_RmtRecRule_Set			=0x000a0006
Store_RmtRecRule_Get			=0x000a0007
Store_RmtAlmRecPara_Set			=0x000a0008
Store_RmtAlmRecPara_Get			=0x000a0009
Store_RmtAlmRecEnable_Set		=0x000a000a
Store_RmtAlmRecEnable_Get		=0x000a000b
Store_RmtSchRecPara_Set			=0x000a000c
Store_RmtSchRecPara_Get			=0x000a000d
Store_RmtSchRecEnable_Set		=0x000a000e
Store_RmtSchRecEnable_Get		=0x000a000f
Store_RmtRecStat_Get			=0x000a0010
Store_DLStart_Cmd				=0x000a0011
Store_DLStop_Cmd				=0x000a0012
Store_DLProgress_Get			=0x000a0013
Store_RmtIsSupport_Get			=0x000a0014
Store_RmtDiskInfo_Get			=0x000a0015
Store_RmtDiskUsage_Set			=0x000a0016
Store_RmtFormat_Cmd				=0x000a0017
Store_RmtPart_Cmd				=0x000a0018
Store_RmtRebuildIndex_Cmd		=0x000a0019
Store_RmtDelFile_Cmd			=0x000a001a
Store_RmtUsbState_Get			=0x000a001b
Store_RmtMountUSB_Cmd			=0x000a001c
Store_RmtMapDev_Get				=0x000a001d
Store_RmtMapDev_Cmd				=0x000a001e
Store_OpenLocFile_Cmd			=0x000a001f
Store_Disk_Clear_Cmd			=0x000a0020

Net_NVSAddr_Set					=0x000b0000
Net_NVSAddr_Get					=0x000b0001
Net_DomainProp_Set				=0x000b0002
Net_DomainProp_Get				=0x000b0003
Net_PPPoEProp_Set				=0x000b0004
Net_PPPoEProp_Get				=0x000b0005
Net_HttpProp_Set				=0x000b0006
Net_HttpProp_Get				=0x000b0007
Net_IPFilter_Set				=0x000b0008
Net_IPFilter_Get				=0x000b0009
Net_WifiPara_Set				=0x000b000a
Net_WifiPara_Get				=0x000b000b
Net_WifiSrch_Cmd				=0x000b000c
Net_WifiSrchRst_Get				=0x000b000d
Net_UPNPEnable_Set				=0x000b000e
Net_UPNPEnable_Get				=0x000b000f
Net_WifiDhcpEnable_Set			=0x000b0010
Net_WifiDhcpEnable_Get			=0x000b0011
Net_DDNSPara_Set				=0x000b0012
Net_DDNSPara_Get				=0x000b0013

Log_DownLoad_Cmd				=0x000c0000
Log_Read_Cmd					=0x000c0001
Log_Clear_Cmd					=0x000c0002

ZTE_ZTEInfo_Set					=0x000d0001
ZTE_ZTEInfo_Get					=0x000d0002
ZTE_OnlineState_Set				=0x000d0003
ZTE_OnlineState_Get				=0x000d0004

PU_ManagerServersInfo_Set		=0x000e0001
PU_ManagerServersInfo_Get		=0x000e0002
PU_DeviceID_Set					=0x000e0003
PU_DeviceID_Get					=0x000e0004
PU_NTP_Set						=0x000e0005
PU_NTP_Get						=0x000e0006
PU_CPUMEMAlarm_Set				=0x000e0007
PU_CPUMEMAlarm_Get				=0x000e0008
PU_OSDAlpha_Set					=0x000e0009
PU_OSDAlpha_Get					=0x000e000a
PU_PTZAUTO_Set					=0x000e000b
PU_PTZAUTO_Get					=0x000e000c
PU_MD5_Cmd                      =0x000e000d

DZ_COMMON_Set					=0x000f0001
DZ_COMMON_Get					=0x000f0002
Com_Server_Set					=0x000f0003
Com_Server_Get					=0x000f0004
G_DeviceStatus_Get				=0x000f0005
G_Dialog_Set					=0x000f0006
G_Dialog_Get					=0x000f0007
G_Message_Set					=0x000f0008
G_Message_Get					=0x000f0009

HD_Camer_Set					=0x00100001
HD_Camer_Get					=0x00100002

//----------------OCX  msg----------------------


////////////////////////////////////////////////////////
var AXCOLOR = 1;     //界面颜色:0-绿色,1-蓝色
var NVSTYPE = 1;     //服务器类型:0-T,1-S
var STARTPICNUM = 4; //启动时显示画面数
var jsLanguage = 0;      //0-中文,1-English,2-繁体
var BACKCOLOR  ;       //按钮背景颜色:#A6DB9E-绿色  0 Tiandy
//1 ,#909CC6-蓝色-OEM
var splitCode  ="\b";//
var BITRATEPOSX = 12;
var BITRATEPOSY = 40;


var	iCurLogon   =0;
var iCurObs     =0;//当前obs编号－－视频窗口的标号
var iCurLogOnObs=0;
//------------------------------
var iCurObsCount = 0;//当前生成的视频窗口数量
var iCurMonitorCount = 0;//当前画面数
var ObsInfo = new Array(25);//存放25个视频窗口信息
var CurIndex = -1;//当前选择的点在树中的序号
//------------------------------
//var iLogStream  = 0;
var iLogMode    = 1;
//var iLogChannel = 0;
var sServerHost;
var sProxyIP;
var iPort;
var iDevModel;//设备标识
//var iCurrentHostCh = -1;
var iStreamNO=0;
var sDeviceID;
var xCoord;
var yCoord;
var xOld;
var yOld;
var trackbarobj;

var iMaxConUser;
var sCurUserName;
var sCurPassword;
var sChannelNumMax;
var iSelectedChannel;//在channel下拉框中选择的通道号
var iCurAuthority;
var iChannelNum;
var iLogonStatus;//当前登录状态

//var iStreamType;// = new Array(4);
var sOsdText;// = new Array(4);
var iOsdTextColor;// = new Array(4);
var iCurUserNum;
var iIFrameRate;// = new Array(4);

var ChannelInfo = new Array(25);//存放通道信息的数组
for(var i = 0;i<25;i++)
{
    ChannelInfo[i] = new Array(5);
    ChannelInfo[i] = null;
    ChannelInfo[i]  =   new Object;
    ChannelInfo[i]['iCurObsID'] = 0;
    ChannelInfo[i]['bTalk'] = false;
    ChannelInfo[i]['bAudio'] = false;
    ChannelInfo[i]['bRec'] = false;
    ChannelInfo[i]['bShowBitRate'] = false;
    ChannelInfo[i]['bAuto'] = false;
}

var iCurObsIndex = 0;//当前iCurObs对应的obs编号(0-15)

var iBrightness;
var iSaturation;
var iContrast;
var iHue;
var iVideoQuality;
var iFrameRate;
var iMediaType;
var sChannelTitle;
var iOSDType;
var iOsdDTP;
var iOsdCTP;
var iMotionDetectThreshold;
var iVideoCoverThreshold;
//var iMotionDetectAlarming = new Array(4);
//var iVideoLostAlarming = new Array(4);
//var iVideoCoverAlarming = new Array(4);
var iMotionDetectAlarmCk;
var iVideoLostAlarmCk;
var iVideoCoverAlarmCk;
var iAlarmInPortNum;
var iAlarmOutPortNum;
var iAlarmInMode1;//
var iAlarmInMode2;//
var iAlarmInMode = new Array(4);
var iAlarmOutMode;
var iAudioCh;


var iDelayNum=1;
var iInitializtion = 0;

//zc add

/*var iSelected = 0;//当前播放通道(Panel)
var iLogStream = 0;
var iLogMode = 1;
var iLogChannel = 0;*/

//for advanced options
var sIPChange;
var sMask;
var sGateway;
var sDNS;
var sSDKVer;
var sSvrVer;
var sAxVer;
var iBitRate;
var iVideoSize;
var iEncodeMode;
var iPPPEnabled;
var sPPPUser;
var sPPPPwd;
var sRegCenter;
//var sCoverArea;
var iDHCP;//DHCP使能
var arrayVideoSize = new Array("Quarter Cif","Half Cif","Full Cif","Half D1","Full D1");
var iMaxVideoSize;
var iMaxMinorSize;
//for QueryFiles
iCurQFPage  =   0;
iTotalQFPage  =   0;
iQFPageSize =   5;

var arrSchedule = new Array(4);
for(i=0;i<4;i++)
{
    arrSchedule[i] = new Array(4);
}

var BMPNUM = 0;
//var bDevAuto = false;
var bDevLight = false;
var bDevPower = false;
var iDomeSpeed = 50;
var iDevAddress = 0;

//define Main Menu
var TAB_LOGON    = 1;
var TAB_DEVICE   = 2;
var TAB_VIDEO    = 3;
var TAB_ALARM    = 4;
var TAB_NETWORK  = 5;
var TAB_ADVANCED = 6;
var TAB_OSDParam = 7;
var TAB_MemorySet= 8;
var iCurrentTAB = TAB_LOGON;

//Action
var bUp        = false;
var bDown      = false;
var bLeft      = false;
var bRight     = false;
var bUpLeft    = false;
var bUpRight   = false;
var bDownLeft  = false;
var bDownRight = false;

var IsFocusFar  = false;
var IsFocusNear = false;
var IsZoomBig   = false;
var IsZoomSmall = false;
var IsIrisOpen  = false;
var IsIrisClose = false;
var IsRain      = false;
//下载
var pbDownStatus = 0;//当前SD卡文件下载状态
var strLastDownFileName;//当前正在下载的文件名
var pbDownloadID = 0;
var pbDownInterval = 0;
var playBackStatus = 0;//当前播放状态
//---------------------------------------------------------------------------
//	Message(Chinese/English)
//---------------------------------------------------------------------------
var JS_cMsg0  = "请选择控制协议";
var JS_cMsg1  = "注册中心IP地址";
var JS_cMsg2  = "端口号";
var JS_cMsg3  = "不限码流";
var JS_cMsg4  = "请先打开区域设置再进行区域清除！";
var JS_cMsg5  = "没有登录！\n请选择已登录的视频再进行操作。";
var JS_cMsg6  = "没有登录，无法抓拍！";
var JS_cMsg7  = "输入含有非法字符！";
var JS_cMsg8  = "请输入一个整数值！";
var JS_cMsg9  = "请输入32~8000之间的值！";
var JS_cMsg10 = "无法删除当前活动用户！";
var JS_cMsg11 = "确定恢复默认设置？";
var JS_cMsg12 = "确定重新启动服务器？";
var JS_cMsg13 = "磁盘已满，录像和下载已停止！";
var JS_cMsg14 = "抓拍失败，请检查磁盘！";
var JS_cMsg15 = "系统中已经运行了一个客户端，请检查后重新下载本页面！";
var JS_cMsg16 = "未安装控件！请尝试重新下载。";
var JS_cMsg17 = "您的IE版本太低，不能正常浏览该网页！请将IE升级到6.0及以上版本！";
var JS_cMsg18 = "Admin为保留用户，不允许删除！";
var JS_cMsg19 = "两次输入密码不一致！";
var JS_cMsg20 = "有效地址范围为：1~254";
var JS_cMsg21 = "有效地址范围为：1~255";
var JS_cMsg22 = "有效预置点范围为：1~255";
var JS_cMsg23 = "无效IP地址";
var JS_cMsg24 = "无效网关或DNS";
var JS_cMsg25 = "无效的子网掩码";
var JS_cMsg26 = "两次输入密码不一致！";
var JS_cMsg27 = "起始时间必须小于结束时间";
var JS_cMsg28 = "不是有效的升级文件";
var JS_cMsg29 = "校验码不正确";
var JS_cMsg30 = "端口号无效";
var JS_cMsg31 = "登录失败，用户名密码错误";
var JS_cMsg32 =	"您的权限不足，不能够进行此操作";
var JS_cMsg33 = "确认用当前系统时间校准设备时间？";
var JS_cMsg34 =	"用户名不能为空";
var JS_cMsg35 = "确认退出？";
var JS_cMsg36 = "用户不存在";
var JS_cMsg37 = "用户已存在";
var JS_cMsg38 = "密码不能为空";
var JS_cMsg39 =" 只能由英文字母a～z(区分大小写)、数字0～9、特殊键字符组成";
var JS_cMsg40 = "您输入的字符超出了字长范围127，汉字按照2个字长计算。"
var JS_cMsg41 =" 请输入长度为:  ";
var JS_cMsg42 = " 个字符";
var JS_cMsg43 =" 请输入值为: ";
var JS_cMsg44 = " 的数值";
var JS_cMsg45 = "无效的Email地址";
var JS_cMsg46 = "设置成功，20秒后系统会重新登录";
var JS_cMsg47 = "IP 和网关必须在同一个网段内";
var JS_cMsg48 = "您输入了一个无效的子网掩码，子网掩码必须是相邻的。";
var JS_cMsg49 = "设置失败";
var JS_cMsg50 = "设置成功";
var JS_cMsg51  = "没有登录，无法打开音频！";
var JS_cMsg52  = "没有登录，无法进行录像！";
var JS_cMsg53  = "没有登录，无法进行对讲！";
var JS_cMsg54  = "未选择设置移动报警区域";
var JS_cMsg55  =  "开始时间必须小于结束时间!";
var JS_cMsg56  =  "播放完毕!";
var JS_cMsg57  =  "本地录像文件不需下载!";
var JS_cMsg58  =  "请在右侧文件列表中选择您要下载的文件!";
var JS_cMsg59  = "同时只能下载一个文件!";
var JS_cMsg60  = "下载超过最大限制!";
var JS_cMsg61  = "下载完毕!文件保存到：";
var JS_cMsg62  = "两次输入密码不一致!";
var JS_cMsg63  = "正在升级中，请等待……";
var JS_cMsg64  = "此用户名不在用户名列表中！";
var JS_cMsg65  = "旧密码输入错误！";
var JS_cMsg66  = "有线IP不能与无线IP相同！";
var JS_cMsg67  ="请选择文件夹";
var JS_cMsg68  ="叠加字符长度不能超过32个字符(一个汉字占两个字符)";
var JS_cMsg69  ="设置成功，请重启设备！";
var JS_cMsg70  ="IP修改成功，请重新登录设备！";
var JS_cMsg71  ="超出最大可添加用户数量！";
var JS_cMsg72  ="添加用户失败！";
var JS_cMsg73  ="添加用户名重复！";
var JS_cMsg74  ="路径名输入错误或正在使用原路径";
var JS_cMsg75  ="输入值错误！";
var JS_cMsg76  ="用户名和密码不能设为空或含有空格";
var JS_cMsg77  ="对讲失败！";
var JS_cMsg78  ="密码长度不符合要求，请重新输入！";
var JS_cMsg79 = "用户名长度超出范围1-15个字符（一个汉字占两个字符）";
var JS_cMsg80 = "升级时将关闭录像、音频和对讲!";
var JS_cMsg81 = "设备端TCP连接数超过限制!";
var JS_cMsg82 = "此参数设置成功后，会自动重启设备";
var JS_cMsg83 = "是否确认断开无线连接？确认后将重启设备。";







//---------------------------------------------------------------------------
//	所有显示信息(Chinese/English)
//---------------------------------------------------------------------------
var JS_cShow1 = "服务器IP：";
var JS_cShow2 = "用户名：";
var JS_cShow3 = "密码：";
var JS_cShow4 = "[登录]";
var JS_cShow5 = "[断开]";
var JS_cShow6 = "代理IP：";
var JS_cShow7 = "端口：";
var JS_cShow8 = "通道：";
var JS_cShow9 = "码流：";
var JS_cShow10 = "主码流";
var JS_cShow11 = "副码流";
var JS_cShow12 = "协议：";
var JS_cShow13 = "[连接视频]";
var JS_cShow14 = "[断开视频]";
var JS_cShow15 = "注册中心：";
var JS_cShow16 = "服务器名：";
var JS_cShow17 = "子网掩码：";
var JS_cShow18 = "网关：";
var JS_cShow19 = "[无线设置]";
var JS_cShow20 = "[设置]";
var JS_cShow21 = "使用PPPoE拨号连接：";
var JS_cShow22 = "帐号：";
var JS_cShow23 = "Web端口：";
var JS_cShow24 = "请选择要升级的文件(*.bin/*.box)";
var JS_cShow25 = "[升级]";
var JS_cShow26 = "[清除]";
var JS_cShow27 = "[恢复默认]";
var JS_cShow28 = "[重启设备]";
var JS_cShow29 = "[设置时间]";
var JS_cShow30 = "用户管理";
var JS_cShow31 = "选择用户：";
var JS_cShow32 = "旧密码：";
var JS_cShow33 = "新密码：";
var JS_cShow34 = "权&nbsp;&nbsp; 限：";
var JS_cShow35 = "浏览";
var JS_cShow36 = "浏览+控制";
var JS_cShow37 = "浏览+控制+设置";
var JS_cShow38 = "管理员";

var JS_cShow39 = "[添加]";
var JS_cShow40 = "[删除]";
var JS_cShow41 = "[修改密码]";
var JS_cShow42 = "系统信息";
var JS_cShow43 = "SDK版本：";
var JS_cShow44 = "服务器版本：";
var JS_cShow45 = "ActiveX版本：";
var JS_cShow46 = "[预置]";
var JS_cShow47 = "[调用]";
var JS_cShow48 = "启用3D定位";
var JS_cShow49 = "变倍大";
var JS_cShow50 = "变倍小";
var JS_cShow51 = "聚焦远";
var JS_cShow52 = "聚焦近";
var JS_cShow53 = "光圈开";
var JS_cShow54 = "光圈关";
var JS_cShow55 = "灯光";
var JS_cShow56 = "电源";
var JS_cShow57 = "雨刷";
var JS_cShow58 = "速度：";
var JS_cShow59 = "控制协议：";
var JS_cShow60 = "协议状态：";
var JS_cShow61 = "设备地址：";

var JS_cShow62 = "输入端口有效";
var JS_cShow63 = "输入端口：";
var JS_cShow64 = "输出端口：";
var JS_cShow65 = "输入端口：";
var JS_cShow66 = "输出端口：";
var JS_cShow67 = "联动设置";
var JS_cShow68 = "模式设置";
var JS_cShow69 = "[清除区域]";
var JS_cShow70 = "设置移动报警区域";
var JS_cShow71 = "设置视频遮挡区域";
var JS_cShow72 = "视频移动报警灵敏度";
var JS_cShow73 = "视频移动报警";
var JS_cShow74 = "视频遮挡报警";
var JS_cShow75 = "视频丢失报警";
var JS_cShow76 = "闭路报警";
var JS_cShow77 = "开路报警";
var JS_cShow78 = "不叠加";
var JS_cShow79 = "日期时间";
var JS_cShow80 = "通道名称：";
var JS_cShow81 = "日期时间＋通道名称";
var JS_cShow82 = "字符叠加：";
var JS_cShow83 = "通道名称";
var JS_cShow84 = "通道名位置：";
var JS_cShow85 = "日期位置：";
var JS_cShow86 = "附加字符叠加：";
var JS_cShow87 = "X坐标：";
var JS_cShow88 = "Y坐标：";
var JS_cShow89 = "USB/SD设备";
var JS_cShow90 = "加载状态";
var JS_cShow91 = "未加载";
var JS_cShow92 = "已加载";
var JS_cShow93 = "磁盘空间(M)";
var JS_cShow94 = "剩余空间(M)";
var JS_cShow95 = "SATA设备";
var JS_cShow96 = "磁盘容量(M)";
var JS_cShow97 = "NFS 远程映射存储设备";
var JS_cShow98 = "映射状态";
var JS_cShow99 = "未映射";
var JS_cShow100 = "已映射";
var JS_cShow101 = "IP地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
var JS_cShow102 = "映射路径";
var JS_cShow103 = "[磁盘信息]";
var JS_cShow104 = "[应用到每一天]";
var JS_cShow105 = "最大录像时间 单位分钟(10-120)";
var JS_cShow106 = "剩余磁盘空间 单位MB(≥200)";
var JS_cShow107 = "保存策略：";
var JS_cShow108 = "停止录像";
var JS_cShow109 = "循环删除";
var JS_cShow110 = "循环删除(除报警文件)";
var JS_cShow111 = "定时录像";
var JS_cShow112 = "报警录像";
var JS_cShow113 = "停止";
var JS_cShow114 = "手动录像";
var JS_cShow115 = "录像状态：";
var JS_cShow116 = "周日";
var JS_cShow117 = "周一";
var JS_cShow118 = "周二";
var JS_cShow119 = "周三";
var JS_cShow120 = "周四";
var JS_cShow121 = "周五";
var JS_cShow122 = "周六";
var JS_cShow123 = "日期选择：";
var JS_cShow124 = "报警预录的时间，单位秒(5-60) ";
var JS_cShow125 = "报警录像延时，单位秒(10-60)";
var JS_cShow126 = "[前端抓拍]";
var JS_cShow127 = "报警预录：";
var JS_cShow128 = "本地文件存储路径:";
var JS_cShow129 = "服务器列表：";
var JS_cShow130 = "远程";
var JS_cShow131 = "文件类型：";
var JS_cShow132 = "录像";
var JS_cShow133 = "图片"
var JS_cShow134 = "开始时间"
var JS_cShow135 = "结束时间"
var JS_cShow136 = "录像类型："
var JS_cShow137 = "所有"
var JS_cShow138 = "手动"
var JS_cShow139 = "定时"
var JS_cShow140 = "报警"
var JS_cShow141 = "通道："
var JS_cShow142 = "[搜索]";
var JS_cShow143 = "[播放]"
var JS_cShow144 = "[暂停]";
var JS_cShow145 = "[停止]";
var JS_cShow146 = "[快进]";
var JS_cShow147 = "[慢进]";
var JS_cShow148 = "[下载]";
var JS_cShow149 = "[停止下载]";
var JS_cShow150 = "[首页]";
var JS_cShow151 = "[上一页]";
var JS_cShow152 = "[下一页]";

var JS_cShow153 = "[末页]";
var JS_cShow154 = "本地回放：";
var JS_cShow155 = "[返回]";
var JS_cShow156 = "亮度：";
var JS_cShow157 = "饱和度：";
var JS_cShow158 = "色调：";
var JS_cShow159 = "对比度：";
var JS_cShow160 = "最好";
var JS_cShow161 = "较好";
var JS_cShow162 = "好";
var JS_cShow163 = "一般";
var JS_cShow164 = "较差";
var JS_cShow165 = "视频质量：";
var JS_cShow166 = "纯视频";
var JS_cShow167 = "音视频";
var JS_cShow168 = "流类型：";
var JS_cShow169 = "质量优先";
var JS_cShow170 = "帧率优先";
var JS_cShow171 = "优先模式：";
var JS_cShow172 = "帧率：";
var JS_cShow173 = "码率：";
var JS_cShow174 = "视频大小：";
var JS_cShow175 = "编码模式：";
var JS_cShow176 = "制式：";
var JS_cShow177 = "显示帧率及码流信息";

var JS_cShow178 = "可用网络列表：";
var JS_cShow179 = "无线网卡";
var JS_cShow180 = "无";
var JS_cShow181 = "有";
var JS_cShow182 = "无线状态";
var JS_cShow183 = "不在线";
var JS_cShow184 = "在线";
var JS_cShow185 = "自动获取IP地址";
var JS_cShow186 = "无线IP：";
var JS_cShow187 = "无线子网掩码：";
var JS_cShow188 = "无线网关：";
var JS_cShow189 = "无线DNS：";
var JS_cShow190 = "安全类型：";
var JS_cShow191 = "密钥格式选择：";
var JS_cShow192 = "密钥类型：";
var JS_cShow193 = "禁用";
var JS_cShow194 = "64位";
var JS_cShow195 = "128位";
var JS_cShow196 = "密钥：";
var JS_cShow197 = "密码长度说明：选择64位密钥需输入16进制数字符10个，或者ASCII码字符5个。选择128位密钥需输入16进制数字符26个，或者ASCII码字符13个。";


var JS_cShow209 = "登录成功";
var JS_cShow210 = "登录成功,通道号错误，请重新选择通道显示视频";
var JS_cShow211 = "登录超时";

var JS_cShow225 = "抓拍";


var JS_cShow235 = "动态域名解析使能";
var JS_cShow236 = "服务器域名：";
var JS_cShow237 = "域名解析服务器：";
var JS_cShow238 = "密码确认：";
var JS_cShow245 = "此设备不支持副码流，请重新选择码流类型！";
var JS_cShow246 = "无人值守";
var JS_cShow247 = "密码确认：";
var JS_cShow248 = "重复连接";
var JS_cShow249 = "通道已经占用";
var JS_cShow250 = "同一个远程文件不能同时本地下载和预览";
var JS_cShow251 = "帐户";

var JS_cShow286 = "磁盘编号：";
var JS_cShow287 = "录像";
var JS_cShow288 = "备份";
var JS_cShow289 = "用途：";
var JS_cShow290 = "分区数：";
var JS_cShow291 = "无磁盘";
var JS_cShow292 = "未格式化";
var JS_cShow293 = "已格式化";
var JS_cShow294 = "已挂载";
var JS_cShow295 = "读写中";
var JS_cShow296 = "[清除磁盘]";
var JS_cShow297 = "[分区格式化]";


var JS_cShow298 = "报警服务器：";
var JS_cShow299 = "串口服务器：";
var JS_cShow300 = "报警高级";

var JS_cShow330 = "电子云台";
var JS_cShow331 = "自动光圈";
var JS_cShow332 = "背光补偿";
var JS_cShow333 = "视频模式：";
var JS_cShow334 = "[翻转]";
var JS_cShow335 = "[断开]";
var JS_cShow400 = "ESSID：";

var ObsTempNum = new Object;

//---------------------------------------------------------------------------
//	Load Data From Activex And Init Html
//---------------------------------------------------------------------------
function aXActivate()
{
}

//显示错误
function OnError(iStatus,iID)
{
    if (iStatus==3)//登录超过连接
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd,iCurObs ,true,JS_cMsg81,3000,0,0,0,0,0);
    }
    else if(iStatus == 6)//磁盘已达到阈值
    {
        for(i=0;i<16;i++)
        {
            lyRecorder.style.visibility = "visible";
            lyRecorderMD.style.visibility = "hidden";
            ChannelInfo[i]['bRec'] = false;
        }

        if(pbDownStatus)
        {
            PB_StopDown();
        }
        if(IsRemode)
        {
            PlayBack_Stop();
        }
        //alert(JS_cMsg13);
        TiandyVideo.Commander(Obs_ShowMsg_Cmd,iCurObs ,true,JS_cMsg13,3000,0,0,0,0,0);
    }
}

function GetLogonStatus()//获取该设备的登录状态
{
    var sTemp = TiandyVideo.Commander(Obs_Prop_Get,iCurObs,0,0,0,0,0,0,0,0);
    if (sTemp.length>10)
    {
        var arrStatus=sTemp.split('\n')[1].split(splitCode);
    }
    else
    {
        return false;
    }
    iLogonStatus = parseInt(arrStatus[0]);
    if(iLogonStatus == -1)
    {
        return false;}
    else
    {
        return true;}
}


function ShowWarningMsg()
{
    alert(JS_cMsg5);
}


function checkwhere(e)
{
    if (document.layers)
    {
        xCoord = e.x;
        yCoord = e.y;
    }
    else if (document.all)
    {
        xCoord = event.clientX;
        yCoord = event.clientY;
    }
    else if (document.getElementById)
    {
        xCoord = e.clientX;
        yCoord = e.clientY;
    }
    var xMove = xCoord  - xOld;
    var yMove = yCoord - yOld;
    if((Ismousedown == true)&&(xMove != 0))
    {
        var xStart = Math.floor((trackbarobj.style.left).substring(0,(trackbarobj.style.left).length-2));
        var xPos = xStart + xMove;
        if(xPos<0) xPos = 0;
        if(xPos>100) xPos = 100;


        trackbarobj.style.left = xPos + "px";
        if(trackbarobj == lyBrightnessBut)
        {
            iBrightness = Math.floor(xPos)+100;
            labBrightnessValue.innerText = xPos;
        }
        else if(trackbarobj == lyContrastBut)
        {
            iContrast = Math.floor(xPos)+100;
            labContrastValue.innerText = xPos;
        }
        else if(trackbarobj == lySaturationBut)
        {
            iSaturation = Math.floor(xPos)+100;
            labSaturationValue.innerText =  xPos;
        }
        else if(trackbarobj == lyHueBut)
        {
            iHue = Math.floor(xPos)+100;
            labHueValue.innerText =  xPos;
        }
        else if(trackbarobj == lyMotionDetectBut)

        {
            iMotionDetectThreshold = Math.floor(xPos*255/100);
            labMotionDetectValue.innerText = xPos;
        }
        else if(trackbarobj == lySpeedBut)
        {
            iDomeSpeed = xPos;
            labSpeedValue.innerText = xPos;
        }

        xOld = xCoord;
        yOld = yCoord;
    }
}


//---------------------------------------------------------------------------
//	Activex Event
//---------------------------------------------------------------------------
/*单击Panel事件：获取通道参数*/
function DHT_Ch_Change(_ulObsID)
{

}

/*双击Panel事件：获取屏幕大小*/
function DHT_Panel_DblClick()
{
    var iFullSrc = TiandyVideo.iFullSize;
    //四画面
    if(iFullSrc == 0)
    {
        ly1pic.style.visibility = "visible";
        ly1picMD.style.visibility = "hidden";
        ly4pic.style.visibility = "hidden";
        ly4picMD.style.visibility = "visible";
    }
    //一画面
    if(iFullSrc == 1)
    {
        ly1pic.style.visibility = "hidden";
        ly1picMD.style.visibility = "visible";
        ly4pic.style.visibility = "visible";
        ly4picMD.style.visibility = "hidden";
    }
    //全屏
    if(iFullSrc == 2)
    {
        ly1pic.style.visibility = "visible";
        ly1picMD.style.visibility = "hidden";
        ly4pic.style.visibility = "hidden";
        ly4picMD.style.visibility = "visible";
    }
}


//---------------------------------------------------------------------------
var Ismousedown = false;
function trackbar_mousedown(obj)
{
    Ismousedown = true;
    xOld = xCoord;
    yOld = yCoord;
    trackbarobj = obj
}

function trackbar_mouseup(obj)
{
    Ismousedown = false;
}

function trackbar_mousemove(obj)
{
}


function doc_mouseup()
{
    Ismousedown = false;
}
document.onmousemove = checkwhere;
document.onmouseup = doc_mouseup;
if(document.captureEvents) {document.captureEvents(Event.MOUSEMOVE);}


function writeCookie(name, value, hours)
{
    var expire = "";
    if(hours != null)
    {
        expire = new Date((new Date()).getTime() + hours * 3600000);
        expire = "; expires=" + expire.toGMTString();
    }
    document.cookie = name + "=" + escape(value) + expire;
}


function OutPrint(vText)
{
    document.write(vText);
}

function DHT_LogOn(ip,port,username,pwd)
{
    try
    {
        if(TiandyVideo.iInitializtion == 1)
        {
            iCurObs = TiandyVideo.CurObs;//获取当前播放索引
            iCurLogOnObs=iCurObs;
            writeCookie('UserName',username,12);
            var a = TiandyVideo.commander(Entry_Logon_Cmd,"",ip,username,pwd,"",parseInt(port),0,0,0);
        }
        else if(TiandyVideo.iInitializtion == -1)
        {
            alert(JS_cMsg15);
            window.close();
        }
        else
        {
            alert(JS_cMsg16);
            window.close();
        }
    }catch(e){}
}

function DHT_Install(iLogStatus,iLogonID)
{

    if(iLogStatus == 1)
    {
        iCurLogon = iLogonID;

        TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cShow209,3000,0,0,0,0,0);
        var arrResult    =   TiandyVideo.Commander(Entry_Prop_Get,iCurLogon ,0,0,0,0,0,0,0,0).split('\n')[1].split(splitCode);
        sChannelNumMax   =   arrResult[5];
        iDevModel      =     TiandyVideo.Commander(Dev_Model_Get,iCurLogon,0,0,0,0,0,0,0,0).split('\n')[1].split(splitCode)[1];

        TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cShow209,3000,0,0,0,0,0);
        if (iCurObs!=iCurLogOnObs)
        {
            return;
        }
        PlayAV();
        aXActivate();
        //loadInfoFromaX(1);
        //initHtmlInfo();

    }
    if (iLogStatus == 2)
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cShow211,3000,0,0,0,0,0);
    }
    if (iLogStatus == 3)
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cMsg31,3000,0,0,0,0,0);
    }
    if(iLogStatus == 5)
    {
        PlayAV();
    }

}


function PlayAV()
{

    if(GetLogonStatus())//重复连接提示
    {
        //TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cShow249,3000,0,0,0,0,0);
        return;
    }
    if(iStreamNO==1&&!(iDevModel&0x0008))
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd, iCurObs ,true,JS_cShow245,3000,0,0,0,0,0);
        return;
    }
    var iMode		= 1;
    iChannelNum     =0;

    for(var i = 0;i<4;i++){
        var windowObj = ObsTempNum[i];
        TiandyVideo.Commander(Obs_StartRealTime_Cmd,windowObj,iCurLogon,iChannelNum,iStreamNO,10*iDelayNum,iDelayNum,iMode,0,0);//iBufferCount:50;iDelayNum:10;iMode:2.
        iChannelNum++;
    }

    iCurLogOnObs=0;
    iCurObs =   TiandyVideo.CurObs;
}


function AddObs()
{
    var ret = -1;
    cmdResult = TiandyVideo.Commander(Obs_Add_Cmd,0,0,0,0,0,0,0,0,0).split('\n');
    if(cmdResult[0]==0)
    {
        ret = parseInt(cmdResult[1]);
    }
    return ret;
}

function   DHT_StartObs()
{
    if(GetLogonStatus())//重复连接提示
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cShow249,3000,0,0,0,0,0);
        return;
    }

    iSelectedChannel     =selChannel.selectedIndex;
    iChannelNum     =selChannel.selectedIndex;
    if (iSelectedChannel+1 > sChannelNumMax)
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd,    iCurObs ,true,JS_cShow210,3000,0,0,0,0,0);
        return;
    }
    iStreamNO = parseInt(selStreamType.options[selStreamType.selectedIndex].value);
    var iMode		= parseInt(selNetMode.options[selNetMode.selectedIndex].value);
    if(iStreamNO==1&&!(iDevModel&0x0008)) //此设备不支持副码流
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd, iCurObs ,true,JS_cShow245,3000,0,0,0,0,0);
        return;
    }
    var a = TiandyVideo.Commander(Obs_StartRealTime_Cmd,iCurObs,iCurLogon,iChannelNum,iStreamNO,10*iDelayNum,iDelayNum,iMode,0,0);//iBufferCount:50;iDelayNum:10;iMode:2.
    if (a=="-536739835")
    {
        TiandyVideo.Commander(Obs_ShowMsg_Cmd, iCurObs ,true,JS_cShow248,3000,0,0,0,0,0);
    }
    iCurObs =   TiandyVideo.CurObs;
    iCurLogOnObs=0;
    if(iCurLogon == iTalklogonID)
    {ChannelInfo[iCurObsIndex]['bTalk'] = true;}
    UpdateStatusMenu();//更新录像、音频、对讲、显示码流状态
}

function    DHT_StopObs()
{
    TiandyVideo.Commander(Obs_ShowParam_Cmd,iCurObs,BITRATEPOSX,BITRATEPOSY,0,0,0,0,0,0);
    ChannelInfo[iCurObsIndex]['bShowBitRate'] = false;
    ChannelInfo[iCurObsIndex]['bAuto'] = false;
    DHT_RecStop();
    DHT_AudioStop();
    Menu_TalkStop();
    TiandyVideo.Commander(Obs_Stop_Cmd,    iCurObs,0,0,0,0,0,0,0,0);
}

function DHT_LogOff()
{
    try
    {
        var IsCur=0;
        var IsCurObs=0;
        for(var i = 0;i<selNVS.length;i++)
        {
            var	strUnion	=	selNVS.options[i].text;
            var arrUnion	=	strUnion.split(splitCode);
            if(arrUnion[1]=="0.0.0.0")arrUnion[1]="";
            if ((arrUnion[2] == txHostIp.value)&&(txAgentIp.value==arrUnion[1]))
            {
                selNVS.selectedIndex = i;
                selNVSIP.selectedIndex = i;
                if (iCurLogon ==parseInt(arrUnion[5]))
                {IsCurObs=1;
                }else
                {iCurLogon =parseInt(arrUnion[5])}
                IsOff=1;
                break;
            }
        }

//		if(!GetLogonStatus())
//        {return;}
        if (IsOff==0)return;
        //TiandyVideo.Commander(Entry_Logoff_Cmd,iCurLogon,0,0,0,0,0,0,0,0);
        //TiandyVideo.Commander(Obs_ShowParam_Cmd,iCurObs,BITRATEPOSX,BITRATEPOSY,0,0,0,0,0,0);
        if (IsCurObs==1)
        {
            ChannelInfo[iCurObsIndex]['bShowBitRate'] = false;
            ChannelInfo[iCurObsIndex]['bAuto'] = false;

            Menu_TalkStop();
            LogOffAll(iCurLogon);//删除logOn对应的obs
            //TiandyVideo.Commander(Obs_Stop_Cmd,iCurObs,0,0,0,0,0,0,0,0);
        }

        //ChannelInfo[iCurObsIndex]['iCurObsID'] = 0;
        //
        //var	strUnion	=	selNVS.options[selNVSIP.selectedIndex].text;
        //var arrUnion	=	strUnion.split(splitCode);
        TiandyVideo.Commander(Entry_Logoff_Cmd,iCurLogon,0,0,0,0,0,0,0,0);

        var lstNVS			=	TiandyVideo.Commander(Entry_NVSList_Get,0,0,0,0,0,0,0,0,0).split('\n');
        selNVS.options.length	=	0;
        selNVSIP.options.length	=	0;
        var varItem;
        var varItemIP;
        for (i=1;	i<lstNVS.length;	i++)
        {
            varItem	=	new Option(lstNVS[i]);
            selNVS.options.add(varItem);
            var arrIP = lstNVS[i].split(splitCode);
            varItemIP = new Option(arrIP[2]);
            selNVSIP.options.add(varItemIP);
        }

    }
    catch(e){}
}





//---------------------------------------------------------------------------
/*
	Page OnLoad/UnLoad
*/
function LoadHtml()
{
    if(jsLanguage == 1)
    {TiandyVideo.iLanguage = 1;}
    else if(jsLanguage == 2)
    {TiandyVideo.iLanguage = 2;}
    if(AXCOLOR == 0)
    {
        BACKCOLOR = "#A6DB9E";
    }
    else
    {
        BACKCOLOR = "#909CC6";
    }

    var xVersion = navigator.appVersion.indexOf("MSIE")+5;
    xVersion = parseInt(navigator.appVersion.substring(xVersion, xVersion+3));
    if( xVersion < 6 )
    {
        alert(JS_cMsg17);
    }

    if(TiandyVideo.iInitializtion == -1)
    {
        alert(JS_cMsg15);
        window.close();
    }
    if (AXCOLOR==1)
    {
        arrResult = TiandyVideo.Commander(Sys_Style_Set,0x00ffffff,0x00ab7a69,0,0,0,0,0,0,0);
    }
    //TiandyVideo.iLanguage = jsLanguage;

    IniMonitorNum(17);
    ChangeMonitorNum(4);
}

function UnLoadHtml()
{

    try
    {
        TiandyVideo.Commander(Sys_Cleanup_Cmd,0,0,0,0,0,0,0,0,0);
    }
    catch(e){}

}
var ObsTemp = new Object;
function ChangeMonitorNum(num)
{
    if(num==17)
    {
        ObsTempNum[0] = ObsTemp[16];

    }
    else
    {
        for(var i=0;i<num;i++)
        {
            if(num == 1)
            {
                //alert(iCurObsIndex);
                if (iCurObs==0)
                {
                    ObsTempNum[0] = parseInt( ChannelInfo[iCurObsIndex]['iCurObsID']);
                }else
                {
                    if(iCurObs==ObsTemp[16])
                    {
                        ObsTempNum[0]= parseInt( ChannelInfo[iCurObsIndex]['iCurObsID']);
                    }else
                    {
                        ObsTempNum[0]=iCurObs;
                    }
                }

                //alert(ObsTempNum[0]);
            }
            else
            {
                ObsTempNum[i] = ObsTemp[i];
            }

        }
    }


    if(SetObsShow(ObsTempNum) == true)
    {

        if (num == 17) {
            //iCurMonitorCount = 1;
        }
        else
        {
            iCurMonitorCount = num;
        }
    }
    //alert(iCurMonitorCount);
}
function IniMonitorNum(num)
{

    if(num>iCurObsCount)//增多画面
    {
        ChangeObsNum(num);//当视频窗口生成数不足时补齐
    }

    for(var i= 0;i<num;i++)
    {

        ObsTemp[i] = parseInt( ChannelInfo[i]['iCurObsID']);
    }



}
function ChangeObsNum(num)
{
    if(num>iCurObsCount)//增多画面
    {
        for(var i= iCurObsCount;i<num;i++)
        {
            ChannelInfo[i]['iCurObsID']= AddObs();
            iCurObsCount++;
        }
        iCurObsCount = iCurObsCount-1;
    }

}

/*
 *	设置视频窗口显示哪些，参数为Obs数组
 */
function SetObsShow(_ObsArr)
{
    try
    {
        if(_ObsArr.length<=0)
        {
            return false;
        }
        var ret = TiandyVideo.Commander(Obs_Monopolize_Cmd,_ObsArr,0,0,0,0,0,0,0,0);
        TiandyVideo.CurObs = _ObsArr[0];
        iCurObs = TiandyVideo.CurObs;

        if (ChannelInfo[16]['iCurObsID'] != iCurObs)
        {
            for(var i = 0;i<25;i++)
            {
                if(ChannelInfo[i]['iCurObsID'] == iCurObs)
                {
                    iCurObsIndex = i;
                    break;
                }
            }
        }
        if(ret == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    catch(e){}

}

/*端口所有链接*/
function LogOffAll(logonID)
{

    var b = TiandyVideo.Commander(Entry_ObsLst_Get,logonID,0,0,0,0,0,0,0,0).split("\n");
    if (b[0]=="0")
    {
        for (var i=1;i<=b.length-1;i++)
        {
            var arrResult   =   TiandyVideo.Commander(Obs_Prop_Get, parseInt(b[i]),0,0,0,0,0,0,0,0).split('\n')[1].split(splitCode);

            for(var j = 0;j<25;j++)
            {
                if(ChannelInfo[j]['iCurObsID'] ==b[i])
                {
                    //iCurObsIndex = j;

                    ChannelInfo[j]['bShowBitRate'] = false;
                    ChannelInfo[j]['bAuto'] = false;
                    //DHT_RecStop();
                    if (iCurObs==b[i])
                    {
                        lyRecorder.style.visibility = "visible";
                        lyRecorderMD.style.visibility = "hidden";
                        lyAudio.style.visibility = "visible";
                        lyAudioMD.style.visibility = "hidden";
                        // lyTalk.style.visibility = "visible";
                        // lyTalkMD.style.visibility = "hidden";
                    }
                    TiandyVideo.Commander(Obs_EndRec_Cmd,parseInt(b[i]),0,0,0,0,0,0,0,0);
                    ChannelInfo[j]['bRec'] = false;

                    //DHT_AudioStop();
                    TiandyVideo.Commander(Obs_SpeakerEnable_Set,parseInt(b[i]),0,0,0,0,0,0,0,0);
                    ChannelInfo[j]['bAudio'] = false;


                    //Menu_TalkStop();
                    //TiandyVideo.Commander(Obs_MicEnable_Set,parseInt(temp[0]),0,0,0,0,0,0,0,0);
                    //ChannelInfo[j]['bTalk'] = false;


                    TiandyVideo.Commander(Obs_Stop_Cmd, parseInt(b[i]),0,0,0,0,0,0,0,0);

                }
            }


        }

    }
}

(function () {
    var ie = !!(window.attachEvent && !window.opera);
    var wk = /webkit\/(\d+)/i.test(navigator.userAgent) && (RegExp.$1 < 525);
    var fn = [];
    var run = function () { for (var i = 0; i < fn.length; i++) fn[i](); };
    var d = document;

    d.ready = function (f) {
        if (!ie && !wk && d.addEventListener)
            return d.addEventListener('DOMContentLoaded', f, false);
        if (fn.push(f) > 1) return;
        if (ie)
            (function () {
                try { d.documentElement.doScroll('left'); run(); }
                catch (err) { setTimeout(arguments.callee, 0); }
            })();
        else if (wk)
            var t = setInterval(function () {
                if (/^(loaded|complete)$/.test(d.readyState))
                    clearInterval(t), run();
            }, 0);
    };
})();