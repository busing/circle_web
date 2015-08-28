package com.xwtec.xwserver.exception;

import com.xwtec.xwserver.util.CommonUtil;


/**
 * 省平台后台管理自定义异常<br>
 * <p>
 * Copyright: Copyright (c) Nov 15, 2013 2:34:51 PM
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author houxu@xwtec.cn
 * @version 1.0.0
 */
public class SPTException extends Exception {

	/**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    
    private String message;
    
    /**
	 * 方法描述:重写父类带参构造方法
	 * @param message:创建异常时指定的异常信息
	 * date:2013-12-31
	 * add by: liu_tao@xwtec.cn
	 */
	public SPTException(String message) {
		super(message);
		this.message = message;
    }

	/**
	 * 方法描述:重写父类无参构造方法
	 * date:2013-12-31
	 * add by: liu_tao@xwtec.cn
	 */
//	public SPTException() {
//		super();
//    }
	
	/**
	 * 方法描述:重写父类带参构造方法
	 * @param throwable:异常对象
	 * date:2013-12-31
	 * add by: liu_tao@xwtec.cn
	 */
	public SPTException(Throwable throwable) {
		super(throwable);
		this.message = throwable.getMessage();
	}
	
	/**
	 * 方法描述:重写父类带参构造方法
	 * @param message:创建异常时指定的异常信息
	 * @param throwable:异常对象
	 * date:2013-12-31
	 * add by: liu_tao@xwtec.cn
	 */
	public SPTException(String message,Throwable throwable) {
		super(message,throwable);
		if(!CommonUtil.isEmpty(message)) {
			this.message = message;
		} else {
			this.message = throwable.getMessage();
		}
	}
	
	/**
	 * 方法描述:重写父类取消息方法
	 * @return 错误消息
	 * date:2013-12-31
	 * add by: liu_tao@xwtec.cn
	 */
	public String getMessage() {
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(this.message + "\n");
		StackTraceElement[] stacks = super.getStackTrace();
		if(stacks != null) {
			for(StackTraceElement stack : stacks) {
				errorMessage.append("\t" + stack.toString() + "\n");
			}
		}
		return errorMessage.toString();
	}
}
