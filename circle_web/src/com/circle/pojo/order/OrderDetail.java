package com.circle.pojo.order;

/**
 * 
 * 订单详细实体信息. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月22日 16:32:48
 * <p>
 * <p>
 * 
 * @author Cooper
 * @version 1.0.0
 */
public class OrderDetail {
	//主键
	private int id;
	//订单id
	private String orderId;
	
	/**
	 * 商品名称
	 * goodName 
	 */
	private String goodName;
	
	/**
	 * 商品标题
	 * goodTitle 
	 */
	private String goodTitle;
	
	/**
	 * 商品单位
	 * goodUnit 
	 */
	private int goodUnit;
	//商品id
	private String goodId;
	//单价
	private String unitPrice;
	//购买数量
	private int buyNum;
	//总计
	private double total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodTitle() {
		return goodTitle;
	}
	public void setGoodTitle(String goodTitle) {
		this.goodTitle = goodTitle;
	}
	public int getGoodUnit() {
		return goodUnit;
	}
	public void setGoodUnit(int goodUnit) {
		this.goodUnit = goodUnit;
	}
	
	
}
