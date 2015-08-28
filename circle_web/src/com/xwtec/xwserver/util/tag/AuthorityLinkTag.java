package com.xwtec.xwserver.util.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.xwtec.xwserver.util.CommonUtil;

/**
 * 权限超链接(a标签). <br>
 * 根据Session中存放的权限与a标签中的权限地址属性比对，来决定a标签是否显示.
 * <p>
 * Copyright: Copyright (c) 2013-12-3 下午2:35:05
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class AuthorityLinkTag extends BodyTagSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AuthorityLinkTag.class);

	/**
	 * 用来判断当前登录用户是否拥有该链接权限的地址
	 */
	private String authority;

	/**
	 * a标签的href
	 */
	private String href;

	/**
	 * a标签的onclick
	 */
	private String onclick;

	/**
	 * a标签的class
	 */
	private String cssClass;

	/**
	 * 是否拥有本标签操作权限
	 */
	private boolean hasAuthority = false;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public boolean isHasAuthority() {
		return hasAuthority;
	}

	public void setHasAuthority(boolean hasAuthority) {
		this.hasAuthority = hasAuthority;
	}

	/**
	 * 方法描述:重写父类方法，开始标签处理
	 * 
	 * @return 是否忽略标签体中内容，EVAL_BODY_INCLUDE：包含标签内容，SKIP_BODY：忽略标签体内容
	 *         date:2013-12-3 add by: liu_tao@xwtec.cn
	 */
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try {
				this.hasAuthority = true;
				// 开始标签内容
				StringBuffer tagContent = new StringBuffer();
				tagContent.append("<a");
				if (!CommonUtil.isEmpty(this.href)) {
					tagContent.append(" href='" + this.href + "'");
				}
				if (!CommonUtil.isEmpty(this.onclick)) {
					// 判断onclick事件中方法的参数用是的单引号还是双引号
					// 来决定onclick事件的最外层是用双引号还是单引号
					String quotes = "\"";
					if (this.onclick.indexOf("\"") > 0) {
						quotes = "'";
					}
					tagContent.append(" onclick=" + quotes + this.onclick
							+ quotes);
				}
				if (!CommonUtil.isEmpty(this.cssClass)) {
					tagContent.append(" class='" + this.cssClass + "'");
				}
				tagContent.append(">");

				super.pageContext.getOut().write(tagContent.toString());

				return EVAL_BODY_INCLUDE;
		} catch (Exception e) {
			this.hasAuthority = false;
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return SKIP_BODY;
		}
	}

	/**
	 * 方法描述:重写父类方法，结束标签处理
	 * 
	 * @return 继续处理页面其他内容 date:2013-12-3 add by: liu_tao@xwtec.cn
	 */
	public int doEndTag() throws JspException {
		if (this.hasAuthority) {
			// 结束标签内容
			StringBuffer tagContent = new StringBuffer();
			tagContent.append("</a>");
			try {
				super.pageContext.getOut().write(tagContent.toString());
			} catch (IOException e) {
				log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			}
		}
		return EVAL_PAGE;
	}
}