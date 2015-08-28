package com.xwtec.xwserver.pojo.util;

/**
 * 与下拉复选框对应的类. <br>
 * 存储的是选项的文字与提交时的id
 * <p>
 * Copyright: Copyright (c) 2014-3-3 下午3:21:15
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class Combobox {
	/**
	 * 提交时的值
	 */
	private String id;
	
	/**
	 * 显示的文字
	 */
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}