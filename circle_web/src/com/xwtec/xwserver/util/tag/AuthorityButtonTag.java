package com.xwtec.xwserver.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 权限按钮. <br>
 * 根据Session中存放的权限与按钮的权限地址属性比对，来决定按钮是否显示.
 * 本功能暂未实现，当出现需要用权限控制实现的button再完成本类方法，暂时只有权限控制的a标签。
 * <p>
 * Copyright: Copyright (c) 2013-12-3 下午2:35:05
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class AuthorityButtonTag extends TagSupport {
	
	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 方法描述:重写父类方法，开始标签处理
	 * @return 忽略标签体
	 * date:2013-12-3
	 * add by: liu_tao@xwtec.cn
	 */
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	/**
	 * 方法描述:重写父类方法，结束标签处理
	 * @return 继续处理页面其他内容
	 * date:2013-12-3
	 * add by: liu_tao@xwtec.cn
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}