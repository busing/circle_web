package com.xwtec.xwserver.util.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.xwtec.xwserver.pojo.util.Combobox;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 下拉组合框（目前实现的是下拉多选框）. <br>
 * <p>
 * Copyright: Copyright (c) 2014-3-3 上午11:12:40
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ComboboxTag extends TagSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ComboboxTag.class);
	
	/**
	 * 组件ID
	 */
	private String id;
	
	/**
	 * 能与后台对应上的，用来提交组件值的名称
	 */
	private String name;
	
	/**
	 * 选中的值
	 */
	private String value;
	
	/**
	 * 下拉选项
	 */
	private List<Combobox> list;
	
	/**
	 * 是否可以多选：true：多选，false：单选
	 */
	private boolean isMultiSelect = true;
	
	/**
	 * 联动下拉框功能--指定联动时调用的远程URL
	 */
	private String linkageUrl;
	
	/**
	 * 联动下拉框功能--指定联动的目标下拉框ID
	 */
	private String linkageTargetId;
	
	/**
	 * 是否需要全选功能
	 */
	private boolean hasCheckAll = false;
	
	/**
	 * 方法描述:重写父类方法，开始标签处理
	 * @return 忽略标签体内内容
	 * date:2014-3-3
	 * add by: liu_tao@xwtec.cn
	 */
	public int doStartTag() throws JspException {
		try {
			// 标签体内容
			StringBuilder tagContent = new StringBuilder();
			// 多选下拉框的载体
			tagContent.append("<input type='text' id='" + this.id + "'/>");
			// 用JS初始化多选下拉框
			tagContent.append("<script>");
			if(this.hasCheckAll && this.isMultiSelect) {
				// 用标记来记录当前动作是：取消选中：0,全部选中：1，其他选项选中：2,全部取消：3
				tagContent.append("var " + this.id + "IsSelect = 0;");
			}
			// 采用一个第三方组件ligerUI的多选下拉框
			tagContent.append("$('#" + this.id + "').ligerComboBox({");
			// 修改选择多个选项值之间的分隔符，原为";"改为","
			tagContent.append("split:','");
			// 初始化下拉选项数据
			tagContent.append(",data:[");
			// 全选功能
			if(this.hasCheckAll && this.isMultiSelect) {
				tagContent.append("{text:'全选'}");
			}
			if(list != null) {
				for(int i = 0; i < this.list.size(); i++) {
					if(i != 0 || (this.hasCheckAll && this.isMultiSelect)) {
						tagContent.append(",");
					}
					tagContent.append("{id:'" + this.list.get(i).getId() + "',text:'" + this.list.get(i).getText() + "'}");
				}
			}
			tagContent.append("]");
			// 是否支持多选
			tagContent.append(",isMultiSelect:" + this.isMultiSelect);
			// 当有全选功能时，实现combobox的onBeforeSelect事件
			onBeforeSelectForCheckAll(tagContent);
			// 选中事件
			tagContent.append(",onSelected:function(value,text) {");
			// 当有全选功能时，实现combobox的onSelected事件来完成取消全选动作，以及对其他选项的动作进行调整
			onSelectedForCheckAll(tagContent);
			// 当有联动功能时，实现combobox的onSelected事件来完成联动功能
			onSelectedForLinkage(tagContent);
			tagContent.append("}");
			tagContent.append("});");
			// 根据value默认选中指定的选项
			if(!CommonUtil.isEmpty(this.value)) {
				tagContent.append("$('#" + this.id + "').ligerComboBox('selectValue','" + this.value + "');");
			}
			// 多选下拉框初始化完毕后，根据ligerUI的规则找到用来提交选项值的hidden，将提交的name修改为开发人员指定的name
			tagContent.append("$('#" + this.id + "_val').attr('name','" + this.name + "');");
			tagContent.append("</script>");
			super.pageContext.getOut().write(tagContent.toString());
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return SKIP_BODY;
	}
	
	/**
	 * 方法描述:当有全选功能时，实现combobox的onBeforeSelect事件来完成全选动作
	 * @param tagContent 用来拼接标签体内容的字符串对象
	 * date:2014-3-13
	 * add by: liu_tao@xwtec.cn
	 */
	private void onBeforeSelectForCheckAll(StringBuilder tagContent) {
		if(this.hasCheckAll && this.isMultiSelect) {
			// 选中前事件
			tagContent.append(",onBeforeSelect:function(value,text){");
			// 如果当前选中的是"全选"
			tagContent.append("if(value == 'undefined' && text == '全选'){");
			// 当前动作都是"全部选中"
			tagContent.append(this.id + "IsSelect = 1;");
			tagContent.append("} else {");
			tagContent.append(this.id + "IsSelect = 2;");
			tagContent.append("}");
			tagContent.append("\r\n");
			tagContent.append("}");
			tagContent.append("\r\n");
		}
	}
	
	/**
	 * 方法描述:当有联动功能时，实现combobox的onSelected事件来完成联动功能
	 * @param tagContent 用来拼接标签体内容的字符串对象
	 * date:2014-3-13
	 * add by: liu_tao@xwtec.cn
	 */
	private void onSelectedForLinkage(StringBuilder tagContent) {
		if(!CommonUtil.isEmpty(this.linkageUrl)) {
			// 按指定的地址发起ajax请求，来获取子下拉框的选项
			tagContent.append("ajax('" + this.linkageUrl + "',function (data) {");
			// 为子下拉框选项赋值
			tagContent.append("$('#" + this.linkageTargetId + "').ligerComboBox('setData',data);");
			tagContent.append("},{id:value});");
		}
	}
	
	/**
	 * 方法描述:当有全选功能时，实现combobox的onSelected事件来完成取消全选动作，以及对其他选项的动作进行调整
	 * @param tagContent 用来拼接标签体内容的字符串对象
	 * date:2014-3-13
	 * add by: liu_tao@xwtec.cn
	 */
	private void onSelectedForCheckAll(StringBuilder tagContent) {
		if(this.hasCheckAll && this.isMultiSelect) {
			tagContent.append("var valueArr = value.split(',');");
			tagContent.append("\r\n");
			tagContent.append("var dataTemp;");
			tagContent.append("if($('#" + this.id + "').ligerComboBox('getOptions')) {");
			// 获取下拉框所有选项
			tagContent.append("dataTemp = $('#" + this.id + "').ligerComboBox('getOptions').data;");
			tagContent.append("}");
			tagContent.append("\r\n");
			// 如果当前是“全部选中”动作或“其他选项选中”动作把其他选项全部选中了
			tagContent.append("if(" + this.id + "IsSelect === 1 || (" + this.id + "IsSelect === 2 && valueArr.length == (dataTemp.length-1))) {");
			tagContent.append(this.id + "IsSelect = 0;");
			tagContent.append("\r\n");
			// hidden里的值
			tagContent.append("var values = '';");
			tagContent.append("\r\n");
			// 全部的值，包括"全选"这个选项的值
			tagContent.append("var allValues = '';");
			tagContent.append("\r\n");
			// 文本框里的文字
			tagContent.append("var names = '';");
			tagContent.append("\r\n");
			// 遍历所有选项，为全选准备
			tagContent.append("for(var i = 0; i < dataTemp.length; i++) {");
			tagContent.append("\r\n");
			tagContent.append("if(values) {");
			tagContent.append("\r\n");
			tagContent.append("values += ',';");
			tagContent.append("\r\n");
			tagContent.append("names += ',';");
			tagContent.append("\r\n");
			tagContent.append("}");
			tagContent.append("\r\n");
			tagContent.append("if(i != 0) {");
			tagContent.append("\r\n");
			tagContent.append("allValues += ',';");
			tagContent.append("\r\n");
			tagContent.append("}");
			tagContent.append("\r\n");
			tagContent.append("allValues += dataTemp[i].id;");
			tagContent.append("\r\n");
			tagContent.append("if(dataTemp[i].id) {");
			tagContent.append("\r\n");
			tagContent.append("values += dataTemp[i].id;");
			tagContent.append("\r\n");
			tagContent.append("names += dataTemp[i].text;");
			tagContent.append("\r\n");
			tagContent.append("}");
			tagContent.append("\r\n");
			tagContent.append("}");
			tagContent.append("\r\n");
			// 全选所有，包括"全选"这个选项，是为了"全选"也被勾中
			tagContent.append("$('#" + this.id + "').ligerComboBox('selectValue',allValues);");
			tagContent.append("\r\n");
			// 为文本框赋值，不包括"全选"字样
			tagContent.append("$('#" + this.id + "').val(names);");
			tagContent.append("\r\n");
			// 为hidden赋值，不包括"全选"这个选项的值
			tagContent.append("$('#" + this.id + "_val').val(values);");
			tagContent.append("\r\n");
			// 如果当前是“取消选中”动作
			tagContent.append("} else if(" + this.id + "IsSelect === 0) {");
			// 如果当前“取消选中”动作执行后，选项中有“全选”选项，说明取消的是其他选项，此时要把“全选”选项取消选中
			tagContent.append("if(value.indexOf('undefined') != -1 && text.indexOf('全选') != -1) {");
			tagContent.append("$('#" + this.id + "').ligerComboBox('selectValue',value.replace('undefined,',''));");
			// 如果当前“取消选中”动作执行后，选项中没有“全选”选项，并且此时其他选项全部是选中状态，说明取消的是“全选”，此时要把所有选项都取消选中
			tagContent.append("} else if(dataTemp && (valueArr.length == (dataTemp.length-1))){");
			tagContent.append("$('#" + this.id + "').ligerComboBox('selectValue','');");
			tagContent.append("}");
			tagContent.append("}");
			tagContent.append(this.id + "IsSelect = 0;");
			tagContent.append("\r\n");
		}
	}
	
	/**
	 * 方法描述:重写父类方法，结束标签处理
	 * @return 继续处理页面其他内容
	 * date:2014-3-3
	 * add by: liu_tao@xwtec.cn
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Combobox> getList() {
		return list;
	}

	public void setList(List<Combobox> list) {
		this.list = list;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsMultiSelect() {
		return isMultiSelect;
	}

	public void setIsMultiSelect(boolean isMultiSelect) {
		this.isMultiSelect = isMultiSelect;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLinkageUrl() {
		return linkageUrl;
	}

	public void setLinkageUrl(String linkageUrl) {
		this.linkageUrl = linkageUrl;
	}

	public String getLinkageTargetId() {
		return linkageTargetId;
	}

	public void setLinkageTargetId(String linkageTargetId) {
		this.linkageTargetId = linkageTargetId;
	}

	public boolean getHasCheckAll() {
		return hasCheckAll;
	}

	public void setHasCheckAll(boolean hasCheckAll) {
		this.hasCheckAll = hasCheckAll;
	}
}
