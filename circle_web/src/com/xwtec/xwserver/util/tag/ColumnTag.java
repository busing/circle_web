package com.xwtec.xwserver.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * jqgrid的colmodel. <br>
 * <p>
 * Copyright: Copyright (c) 2014-07-04 下午1:40:05
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author zhangyu@xwtec.cn
 * @version 1.0.0
 */
public class ColumnTag extends TagSupport{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 表格列的名称
	 */
	private String name;
	
	/**
	 * 当排序时定义排序字段名称的索引
	 */
	private String index;
	
	/**
	 * 列 名称
	 */
	private String label;
	
	/**
	 *主键标识 
	 */
	private Boolean key;
	
	/**
	 * 长度
	 */
	private String width;
	
	/**
	 * 可编辑
	 */
	private Boolean editable;
	
	/**
	 * 鼠标经过显示内容
	 * 
	 */
	private Boolean title;
	
	/**
	 * 可排序
	 */
	private Boolean sortable;
	
	/**
	 * 是否隐藏
	 */
	private Boolean hidden;
	
	/**
	 * 编辑的类型
	 * 可选值：text, textarea, select, checkbox, password, button, image and file. 
	 */
	private String edittype;
	
	/**
	 * 列对齐方式
	 */
	private String align;
	
	/**
	 * 进行编辑时设置的一些属性
	 */
	private String editoptions;
	
	/**
	 * 可编辑单元格的一些额外属性设置
	 */
	private String editrules;
	
	/**
	 * 格式化单元格
	 */
	private String formatter;
	
	/**
	 * 处于编辑状态后反格式化到以前的数据
	 */
	private String unformat;
	
	/**
	 * 用来设置对应formatter的参数
	 */
	private String formatoptions;
	
	public String getEditrules() {
		return editrules;
	}

	public void setEditrules(String editrules) {
		this.editrules = editrules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	public Boolean getKey() {
		return key;
	}

	public void setKey(Boolean key) {
		this.key = key;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getTitle() {
		return title;
	}

	public void setTitle(Boolean title) {
		this.title = title;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getEdittype() {
		return edittype;
	}

	public void setEdittype(String edittype) {
		this.edittype = edittype;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getEditoptions() {
		return editoptions;
	}

	public void setEditoptions(String editoptions) {
		this.editoptions = editoptions;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * 方法描述:重写父类方法，开始标签处理
	 * 
	 * @return 忽略标签体内内容 date:2014-07-04 add by: zhangyu@xwtec.cn
	 */
	public int doStartTag() throws JspException {
		JqGridTag jqgrid=(JqGridTag)this.getParent();
		ColumnTag column=new ColumnTag();
		column.setAlign(this.getAlign());
		column.setEditable(this.getEditable());
		column.setEditoptions(this.getEditoptions());
		column.setEditrules(this.getEditrules());
		column.setEdittype(this.getEdittype());
		column.setHidden(this.getHidden());
		column.setIndex(this.getIndex());
		column.setKey(this.getKey());
		column.setLabel(this.getLabel());
		column.setName(this.getName());
		column.setFormatter(this.getFormatter());
		column.setSortable(this.getSortable());
		column.setTitle(this.getTitle());
		column.setWidth(this.getWidth());
		column.setFormatoptions(this.getFormatoptions());
		column.setUnformat(this.getUnformat());
		jqgrid.addValue(column);
		return SKIP_BODY;
	}

	/**
	 * 方法描述:重写父类方法，结束标签处理
	 * 
	 * @return 继续处理页面其他内容 date:2014-07-04 add by: zhangyu@xwtec.cn
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatoptions(String formatoptions) {
		this.formatoptions = formatoptions;
	}

	public String getFormatoptions() {
		return formatoptions;
	}

	public void setUnformat(String unformat) {
		this.unformat = unformat;
	}

	public String getUnformat() {
		return unformat;
	}

}
