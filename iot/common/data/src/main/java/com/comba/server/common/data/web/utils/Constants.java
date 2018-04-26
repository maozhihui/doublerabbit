package com.comba.server.common.data.web.utils;

/**
 * 常量
 * @author wengzhonghui
 *
 */
public final class Constants {
	
	
	//系统编码
	public final static String SYSTEM_CHAR_CODE = "UTF-8";
	
	//
	public final static String AOP_LOG_DESCRIPTION = "AOP_LOG_DESCRIPTION";
	//标志状态位
	public final static Integer STATUS_VALID = 1; //有效
	public final static Integer STATUS_NO_VALID  = 2; //无效
	public final static Integer DELETE_STATE = 1; //  删除
	public final static Integer UN_DELETE_STATE = 2; //  没删除
	
	public final static int RESULT_CODE_SUCCESS = 0;
	public final static int RESULT_CODE_ERROR = 1;
	public final static int RESULT_CODE_FAILED = 2;
	public final static int RESULT_CODE_UPDATE = 2;
	
	public final static String LOCK_SCREEN_LAST_TIME = "LOCK_SCREEN_LAST_TIME";//记录锁屏最后操作时间
	
	//web中常用向前端发送的成功失败信息
	public final static String SUCCESS = "success";
	public final static String FAIL = "fail";
	
	//当前选择产品ID对应的Key
	public final static String CUR_PRODUCT_ID = "CUR_PRODUCT_ID";

	//短信验证码
	public final static String VALISATE_CODE = "validateCode";

	//用户输入的验证码
	public final static String USER_INPUT_CODE = "msCaption";

	//短信验证码创建时间
	public final static String VALIDATE_TIME = "validateTime";


    /**
     * 网关
     */
    public final static String IS_GATEWAY = "是";
    /**
     * 非网关
     */
    public final static String IS_NOT_GATEWAY = "否";

    public final static String ONLINE = "在线";

    public final static String OFFLINE = "离线";

    public final static String IS_LORA = "是";

    public final static String IS_NOT_LORA = "否";

    public final static String NUMBER_AND_WORD = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";

    /*短信发送模板*/
    public final static String SMS_HEADER = "您申请的手机验证码是:";
    public final static String SMS_FOOT = ",请输入后进行验证，谢谢！";

    /**
     * 摄像头的类别名称
     */
    public final static String CAMERA_CATEGORY_NAME = "摄像头";

}