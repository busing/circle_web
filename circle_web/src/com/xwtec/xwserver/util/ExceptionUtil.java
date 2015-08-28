package com.xwtec.xwserver.util;


/**
 * exception操作类
 * <p>
 * Copyright: Copyright (c) 2014-1-6 下午06:28:05
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
public class ExceptionUtil {
	
	/**
	 * 把错误信息打印成固定格式
	 * @param Exception 错误
	 * @return 拼接后的错误信息
	 * date:2014-1-6
	 * add by: liuwenbing@xwtec.cn
	 */
	public static String ExceptionToString(Exception exception){
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(exception.toString() + "\n");
		StackTraceElement[] stacks = exception.getStackTrace();
		if(stacks != null) {
			for(StackTraceElement stack : stacks) {
				errorMessage.append("\t" + stack.toString() + "\n");
			}
		}
		return errorMessage.toString();
	}
}
