package com.circle.pojo.shopcart;

/**
 * 购物车实体类. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月5日 下午9:11:04
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public class ShopCart {
	
	/**
	 * 购物车id
	 * cartId 
	 */
	private int cartId;
	
	/**
	 * 商品id
	 * goodId 
	 */
	private int goodId;
	
	/**
	 * 圈子id
	 * circleId 
	 */
	private int circleId;
	
	/**
	 * 购买数量
	 * buyNum 
	 */
	private int buyNum;
	
	/**
	 * 单位id
	 * unitId 
	 */
	private int unitId;
	
	/**
	 * 购物车sessionid
	 * sessionId 
	 */
	private String sessionId;
	
	/**
	 * 用户id
	 * userId 
	 */
	private int userId;
	
	/**
	 * 商品单价
	 * price 
	 */
	private double price;
	
	/**
	 * 总价
	 * total 
	 */
	private double total;
	
	/**
	 * 加入购物车时间
	 * addTime 
	 */
	private String addTime;
	
	/**
	 * 过期时间
	 * overTime 
	 */
	private String overTime;

	/**
	 * 批次id
	 * batchId 
	 */
	private int batchId;
	
	
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	
}
