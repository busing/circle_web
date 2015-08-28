package com.circle.pojo.payship;

/**
 * 支付和发货方式
 * @author Taiyuan
 *
 */
public class PayAndShip
{
	private int id;
	
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 支付方式
	 */
	private int payType;
	
	/**
	 * 发货方式
	 */
	private int shipType;

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

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getShipType() {
		return shipType;
	}

	public void setShipType(int shipType) {
		this.shipType = shipType;
	}
	
	
	
	
	
}
