package com.circle.pojo.receive;

/**
 * 收货人信息
 * @author Taiyuan
 *
 */
public class ReceiveInfo
{
	private int id;
	
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 用户姓名
	 */
	private String receiveName;
	
	/**
	 * 联系电话
	 */
	private String receivePhone;
	
	/**
	 * 收货地址
	 */
	private String receiveAddress;
	
	/**
	 * 是否默认
	 */
	private int isDefault;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	
	
}
