package com.zimi.zimixing.interf;

/**
 * 监听线程回调接口
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public interface OnDownLoadCallBack {

	/**
	 * 任务正在执行的时候调用
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-5,下午4:01:37
	 * <br> UpdateTime: 2016-1-5,下午4:01:37
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param press
	 *            0-100
	 * @param fileSize
	 *            文件总大小
	 * @param downLoadSize
	 *            已下载的大小
	 */
	void ResultProgress(int press,int fileSize,int downLoadSize);
}