package com.circle.pojo.msg;

/**
 * 消息类型bean
 * @author Taiyuan
 *
 */
public class MessageTypeBean
{
	/**
	 * id
	 */
	public String id;
	
	/**
	 * 消息类型名称
	 */
	private String typeName;
	
	/**
	 * 第一个按钮回调对应的类路径
	 */
	private String classpath;
	
	/**
	 * 第一个按钮名称
	 */
	private String button1;
	
	/**
	 * 第二个按钮名称
	 */
	private String button2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

	public String getButton1() {
		return button1;
	}

	public void setButton1(String button1) {
		this.button1 = button1;
	}

	public String getButton2() {
		return button2;
	}

	public void setButton2(String button2) {
		this.button2 = button2;
	}
	
	
}
