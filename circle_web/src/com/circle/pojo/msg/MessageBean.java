package com.circle.pojo.msg;

/**
 * 
 * 消息实体类. <br>
 * <p>
 * Copyright: Copyright (c) 2015-1-13 上午10:36:27
 * <p>
 * 
 * @version 1.0.0
 */
public class MessageBean {
	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 消息类型
	 */
	private String typeId;

	/**
	 * 消息内容
	 */
	private String msgContent;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 消息状态（0：未读，1：已读，-1：已删除）
	 */
	private String status;

	/**
	 * 发送人
	 */
	private String sendUserId;

	/**
	 * 接收人
	 */
	private String reciveUserId;

	/**
	 * 消息参数
	 */
	private String params;

	/**
	 * 无参构造函数
	 */
	public MessageBean() {
	}

	/**
	 * 部分参数构造函数
	 */
	public MessageBean(String typeId, String msgContent, String sendUserId,
			String reciveUserId, String params) {
		this.typeId = typeId;
		this.msgContent = msgContent;
		this.sendUserId = sendUserId;
		this.reciveUserId = reciveUserId;
		this.params = params;
	}

	/**
	 * 全参构造函数
	 */
	public MessageBean(String id, String typeId, String msgContent,
			String createTime, String status, String sendUserId, String params) {
		this.id = id;
		this.typeId = typeId;
		this.msgContent = msgContent;
		this.createTime = createTime;
		this.status = status;
		this.sendUserId = sendUserId;
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getReciveUserId() {
		return reciveUserId;
	}

	public void setReciveUserId(String reciveUserId) {
		this.reciveUserId = reciveUserId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
