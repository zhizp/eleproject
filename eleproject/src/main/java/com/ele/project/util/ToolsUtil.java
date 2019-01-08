package com.ele.project.util;

/**
 * 常用工具类
 * @author mqr
 *
 */
public class ToolsUtil {

	/**
	 * 检测字符串是否为空
	 * @param str
	 * @return
	 */
	public static  boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()) || str.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检测字符串是否不为空
	 * @param str
	 * @return
	 */
	public static  boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim()) && str.trim().length() > 0) {
			return true;
		}
		return false;
	}
}
