package com.zimi.zimixing.configs;


/**
 * 公共常量
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class Constant {

	// ******************************语言类型****************************//
    /**
     * 标识true
     */
    public static final String TRUE = "1";
    /**
     * 标识false
     */
    public static final String FALSE = "0";

	// ******************************语言类型****************************//
	/**
	 * 系统语言
	 */
	public static final int LANGUAGE_SYSTEM = 0;
	/**
	 * 中文
	 */
	public static final int LANGUAGE_CHINESE = 1;
	/**
	 * 英语
	 */
	public static final int LANGUAGE_ENGLISH = 2;

	// ******************************性别标识标识****************************//
	/**
	 * 标识男
	 */
	public static final String SEX_MAN = "1";
	/**
	 * 标识女
	 */
	public static final String SEX_WOMAN = "0";
	/**
	 * 标识未知
	 */
	public static final String SEX_UNKNOWN = "-1";
	/**
	 * 显示gif动画的最低内存要求1800Mb（无限大目前不让播放gif 待优化后放出此功能）
	 */
	public static final long MEMORY_MAX = 1800;

	// ******************************压缩****************************//
	/**
	 * 普通图片压缩裁剪后的后缀
	 */
	public static final String IMAGE_LOGO_COMPRESS = "_compress+new.";
	/**
	 * 黑白图片后缀
	 */
	public static final String IMAGE_LOGO_COMPRESS_GRAY = "_compress+new_gray.";


	// ******************************注册登录****************************//
	/** 注册*/
	public static final int TYPE_REGISTER = 1;
	/** 找回密码*/
	public static final int TYPE_FIND_PASSWORD = 2;

	// ******************************金真估****************************//
	public static final String OPERATE_VIN = "VIN";//VIN解析
	public static final String OPERATE_MAKE = "Make";//获取品牌
	public static final String OPERATE_MODEL = "Model";//根据品牌ID获取车系信息
	public static final String OPERATE_STYLE = "Style";//根据车系Id获取车型信息
	public static final String OPERATE_PROVINCE = "Province";//获取省份信息
	public static final String OPERATE_CITY = "City";//根据省份ID获取城市信息
	public static final String OPERATE_ESTIMATE = "Estimate";//估值方法
	public static final String OPERATE_LICENCEPLATECITY = "";//车牌号码解析城市

	// ******************************任务列表****************************//
	//待办是0 已办是1
	public static final int ALREADY_DONE = 1;
	public static final int WAIT_DO = 0;

	public static final String agencyTaskTab = "agencyTaskTab"; //待办
	public static final String processedTaskTab = "processedTaskTab"; //待办

	public static final int CAR_ASSESS = 3; //车辆评估
	public static final int GPS_INSTALL = 4; //gps安装
	public static final int CAR_MORTGAGE = 5; //车辆抵押

	public static final String usertask_assess = "usertask_assess"; //评估任务
	public static final String usertask_gps = "usertask_gps"; //安装任务
	public static final String usertask_mortgage = "usertask_mortgage"; //抵押任务

	// ******************************图片****************************//
	/** 图片相关 */
	public static final int positionImages =1;// gps安装 装车位置
	public static final int materialsImages =2;//
	public static final int contractImages =3;// 车辆抵押
	public static final int carImages =4;//  车辆评估
	public static final int vehicleUnbindMortgage = 5; // 车辆解押

	/** groupId */
	public static final String GROUPID_POSITIONIMAGES = "positionImages"; // gps安装  装车位置
	public static final String GROUPID_MATERIALSIMAGES = "materialsImages"; //
	public static final String GROUPID_CONTRACTIMAGES = "hengxinContractInformation"; // 车辆抵押
	public static final String GROUPID_CARIMAGES = "carImages"; // 车辆评估
	public static final String GROUPID_VEHICLEUNBINDMORTGAGE = "VehicleUnbindMortgage"; //车辆解押
}