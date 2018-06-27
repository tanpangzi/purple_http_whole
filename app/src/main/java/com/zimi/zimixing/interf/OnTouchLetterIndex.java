package com.zimi.zimixing.interf;

/**
 * 触碰字母索引接口
 * <p>
 * <br> Author: 叶青
 * <br> Version: 6.0.0
 * <br> Date: 2017/9/2
 */
public interface OnTouchLetterIndex {

	/**
	 * 触摸字母空间接口.
	 */
	public void touchLetterWitch(String letter);

	/**
	 * 结束查询
	 */
	public void touchFinish();
	
}
