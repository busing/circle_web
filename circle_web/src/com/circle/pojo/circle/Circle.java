package com.circle.pojo.circle;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 圈子实体信息. <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-13 下午09:20:52
 * <p>
 * <p>
 * 
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
public class Circle {
	// 主键ID
	private String id;

	// 圈子名称
	private String name;

	// 圈子描述
	private String description;

	// 圈子头像路劲
	private String headPath;

	// 加入方式
	private String joinType;

	// 本次发货时间
	private String issueTime;

	// 本次发货地址
	private String issueAddress;
	
	// 订单截止时间
	private String endTime;

	// 创建时间
	private String createTime;

	// 创建人
	private String createUser;
	
	/**
	 * 微信名称
	 * weixinName 
	 */
	private String weixinName;
	
	/**
	 * 微信二维码
	 * weixinImage 
	 */
	private String weixinImage;
	
	/**
	 * 创建人id 
	 */
	private int createUserId;
	
	//圈主电话
	private String mobilePhone;

	// 圈子地址
	private String address;

	// 圈子所在省
	private String province;

	// 圈子所在城市
	private String city;

	// 经度
	private String longitude;

	// 纬度
	private String latitude;

	// 圈子状态：0、删除状态 1、审核状态 2、审核未通过状态 3、审核通过状态
	private String status;

	// 审核意见
	private String auditMsg;
	
	//公告
	private String notice;
	
	//是否自己创建
	private Integer isSelfCreate;
	
	//圈子图片全路径
	private String imagePath;
	
	private String distance;
	
	/**
	 * 投递时间（每周几）
	 * postWeek 
	 */
	private int postWeek;
	
	/**
	 * 投递时间（上午：1 下午：2）
	 * postWeek 
	 */
	private int postAmPm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}

	public String getIssueAddress() {
		return issueAddress;
	}

	public void setIssueAddress(String issueAddress) {
		this.issueAddress = issueAddress;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getIsSelfCreate() {
		return isSelfCreate;
	}

	public void setIsSelfCreate(Integer isSelfCreate) {
		this.isSelfCreate = isSelfCreate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getPostWeek() {
		return postWeek;
	}

	public void setPostWeek(int postWeek) {
		this.postWeek = postWeek;
	}

	public int getPostAmPm() {
		return postAmPm;
	}

	public void setPostAmPm(int postAmPm) {
		this.postAmPm = postAmPm;
	}
	
	public String getWeixinName() {
		return weixinName;
	}

	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	public String getWeixinImage() {
		return weixinImage;
	}

	public void setWeixinImage(String weixinImage) {
		this.weixinImage = weixinImage;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public static void main(String[] args) {
		try {
			String pwd="123456";
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(pwd.getBytes()); 
			String newPwd = new BigInteger(1, md.digest()).toString(16);   
			System.out.println(newPwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	}

}
