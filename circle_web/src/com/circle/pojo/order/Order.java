package com.circle.pojo.order;

import java.util.List;
import java.util.Map;

/**
 * 
 * 订单实体信息. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月22日 16:32:48
 * <p>
 * <p>
 * 
 * @author Cooper
 * @version 1.0.0
 */
public class Order implements Cloneable {
	// 主键
	private String orderId;
	// 用户id
	private int userId;
	// 下单用户名
	private String userName;
	// 订单号
	private String orderNo;
	// 订单状态
	private int status;
	// 付款状态
	private int payStatus;
	// 货运状态
	private int shipStatus;
	// 圈子id
	private int circleId;
	// 圈子名称
	private String circleName;
	// 圈子组织者id
	private int organizerId;
	// 圈子组织者
	private String organizerName;
	// 下单时间
	private String orderTime;
	// 发放货物地点
	private String issueAddress;
	// 发放货物时间
	private String issueTime;

	/**
	 * 批次id batchId
	 */
	private int batchId;

	/**
	 * 收货人姓名 receiveName
	 */
	private String receiveName;

	/**
	 * 收货人电话 receivePhone
	 */
	private String receivePhone;

	/**
	 * 配送方式 shipType
	 */
	private int shipType;

	/**
	 * 支付方式 payType
	 */
	private int payType;

	/**
	 * 订单结束时间 endTime
	 */
	private String endTime;

	// 支付方式id
	private int paymentId;
	// 支付方式名
	private String paymentName;
	// 支付单号
	private String payNo;
	// 商品总数
	private int goodsAmount;
	// 订单总额
	private double orderAmount;
	// 完成时间
	private String completeTime;
	// 签收人
	private String signName;
	// 签收时间
	private String signTime;
	// 发货时间
	private String shipTime;
	// 订单备注
	private String remark;
	// 管理员备注
	private String adminRemark;

	/**
	 * 订单详细
	 */
	public List<Map<String, Object>> orderDetailList;

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(int shipStatus) {
		this.shipStatus = shipStatus;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public int getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(int organizerId) {
		this.organizerId = organizerId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getIssueAddress() {
		return issueAddress;
	}

	public void setIssueAddress(String issueAddress) {
		this.issueAddress = issueAddress;
	}

	public String getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public int getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(int goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getShipTime() {
		return shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdminRemark() {
		return adminRemark;
	}

	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public int getShipType() {
		return shipType;
	}

	public void setShipType(int shipType) {
		this.shipType = shipType;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public List<Map<String, Object>> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<Map<String, Object>> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
