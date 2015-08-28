package com.xwtec.xwserver.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 自定义标签-分页组件. <br>
 * 封装了分页的展现、翻页的功能实现，以当前的form的POST方式，改变页码参数后请求。
 * <p>
 * Copyright: Copyright (c) 2013-11-8 下午1:56:39
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class TurnPageTag extends TagSupport {

	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(TurnPageTag.class);
	
	/**
	 * 分页对象
	 */
	private Page page;
	
	/**
	 * 翻页时请求路径
	 */
	private String requestPath;
	
	/**
	 * 分页对象名称前缀
	 */
	private String prefix;
	
	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	public Page getPage() {
		return page;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setPage(Page page) {
		// 获取分页对象的同时对当前页数与总页数进行异常处理：
		// 当前页数小于1置为1，总页数小于置为1
		if(page.getCurrentPage() < 1) {
			page.setCurrentPage(1);
		}
		if(page.getTotalPage() < 1) {
			page.setTotalPage(1);
		}
		this.page = page;
	}
	
	/**
	 * 方法描述:重写父类方法，开始标签处理
	 * @return 忽略标签体内内容
	 * date:2013-11-21
	 * add by: liu_tao@xwtec.cn
	 */
	public int doStartTag() throws JspException {
		try {
			// 标签体内容
			StringBuffer tagContent = new StringBuffer();
			
			// 脚本--start
			tagContent.append("<script>");
			tagContent.append("function changePage(pageNo) {");
			tagContent.append("if(!pageNo) {");
			tagContent.append("pageNo = $('#currentPageText').val();");
			tagContent.append("}");
			tagContent.append("var rule = /^[0-9]*[1-9][0-9]*$/;");
			tagContent.append("if(!rule.test(pageNo) || pageNo < 1) {");
			tagContent.append("pageNo = 1;");
			tagContent.append("}");
			tagContent.append("if(pageNo > " + this.page.getTotalPage() + ") {");
			tagContent.append("pageNo = " + this.page.getTotalPage() + ";");
			tagContent.append("}");
			tagContent.append("$('#currentPage').val(pageNo);");
			if(!CommonUtil.isEmpty(this.requestPath)) {
				tagContent.append("$('form').eq(0).attr('action','" + this.requestPath + "');");
			}
			tagContent.append("$('form').eq(0).submit();");
			tagContent.append("}");
			tagContent.append("</script>");
			// 脚本--end
			
			// 分页内容--start
			tagContent.append("<div class='page fix'>");
			String currentPage;
			if(CommonUtil.isEmpty(this.prefix)) {
				currentPage = "currentPage";
			} else {
				currentPage = this.prefix + ".currentPage";
			}

			tagContent.append("<input type='hidden' name='" + currentPage + "' id='currentPage' value='" + this.page.getCurrentPage() + "'/>");
			tagContent.append("共 <b>" + this.page.getTotalRow() + "</b> 条");
			if(this.page.getCurrentPage() != 1) {
				tagContent.append("<a href='###' onclick=\"changePage('1');\" class='first'>首页</a>");
				tagContent.append("<a href='###' onclick=\"changePage('" + (this.page.getCurrentPage() - 1) + "');\" class='pre'>上一页</a>");
			}
			tagContent.append("当前第<span>" + this.page.getCurrentPage() + "/" + this.page.getTotalPage() + "</span>页");
			if(this.page.getCurrentPage() != this.page.getTotalPage()) {
				tagContent.append("<a href='###' onclick=\"changePage('" + (this.page.getCurrentPage() + 1) + "')\" class='next'>下一页</a>");
				tagContent.append("<a href='###' onclick=\"changePage('" + this.page.getTotalPage() + "');\" class='last'>末页</a>");
			}
			tagContent.append("跳至 &nbsp;  <input type='text' id='currentPageText' value='" + this.page.getCurrentPage() + "' class='allInput w28' /> &nbsp; 页 &nbsp;");
			tagContent.append("<a href='###' onclick='changePage();' class='go'>GO</a>");
			tagContent.append("</div>");
			// 分页内容--end
			super.pageContext.getOut().write(tagContent.toString());
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return SKIP_BODY;
	}
	
	/**
	 * 
	 * 方法描述:重写父类方法，结束标签处理
	 * @return 继续处理页面其他内容
	 * date:2013-11-21
	 * add by: liu_tao@xwtec.cn
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}