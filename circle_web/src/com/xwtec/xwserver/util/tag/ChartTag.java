package com.xwtec.xwserver.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.xwtec.xwserver.constant.ConstantKeys;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * Fusioncharts图表自定义标签. <br>
 * <p>
 * Copyright: Copyright (c) 2014-2-12 上午10:52:48
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ChartTag extends TagSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ChartTag.class);

	/**
	 * 容器ID
	 */
	private String id;

	/**
	 * 初始化图表的数据
	 */
	private String data;

	/**
	 * 图表的类型，包括： Doughnut : 环状图 Area : 面积图 Column : 柱状图 MSColumn : 多系列柱状图
	 * ColLine : 柱状+折线图
	 */
	private String type;

	/**
	 * 图表ID
	 */
	private String chartId;

	/**
	 * 图表宽度
	 */
	private String width;

	/**
	 * 图表高度
	 */
	private String height;

	/**
	 * 方法描述:重写父类方法，开始标签处理
	 * @return 忽略标签体内内容
	 * date:2014-2-12
	 * add by: liu_tao@xwtec.cn
	 */
	public int doStartTag() throws JspException {
		try {
			// 标签体内容
			StringBuilder tagContent = new StringBuilder();
			tagContent.append("<div id='" + this.id + "'></div>");
			tagContent.append("<script>");
			tagContent.append("load" + this.type + "({");
			tagContent.append("basePath : '"
					+ super.pageContext.getRequest().getAttribute(ConstantKeys.BASE_PATH_KEY)
					+ "'");
			tagContent.append(",id : '" + this.id + "'");
			tagContent.append(",data : '" + this.data + "'");
			if (!CommonUtil.isEmpty(this.chartId)) {
				tagContent.append(",chartId : '" + this.chartId + "'");
			}
			if (!CommonUtil.isEmpty(this.width)) {
				tagContent.append(",width : " + this.width);
			}
			if (!CommonUtil.isEmpty(this.height)) {
				tagContent.append(",height : '" + this.height + "'");
			}
			tagContent.append("});");
			tagContent.append("</script>");
			super.pageContext.getOut().write(tagContent.toString());
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return SKIP_BODY;
	}

	/**
	 * 方法描述:重写父类方法，结束标签处理
	 * @return 继续处理页面其他内容
	 * date:2014-2-12
	 * add by: liu_tao@xwtec.cn
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChartId() {
		return chartId;
	}

	public void setChartId(String chartId) {
		this.chartId = chartId;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
