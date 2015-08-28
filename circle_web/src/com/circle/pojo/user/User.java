package com.circle.pojo.user;
/**
 * 
 * 用户实体信息. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 10:30:26
 * <p>
 * <p>
 * 
 * @author Cooper
 * @version 1.0.0
 */
public class User {
	// 主键ID
	private int id;
	
	//电话
	private String mobilePhone;
	
	//用户名
	private String name;

	//用户名
	private String nickname;
	
	//密码
	private String password;
	
	//邮箱
	private String email;
	
	//头像路径
	private String headImage;
	
	//状态
	private int status;
	
	//注册时间
	private String registerTime;
	
	//最后登录时间
	private String lastLoginTime;
	
	//原始的图片路径(zj)
	private String imagePath;
	
	//自己的邀请码
	private String inviteCode;
	
	//邀请人id
	private Integer inviteUserId;
	
	/**
	 * 微信二维码
	 * weixinImage 
	 */
	private String weixinImage;
	
	/**
	 * 微信名称
	 * weixinName 
	 */
	private String weixinName;
	
	/**
	 * 每个微信用户对应订阅号有唯一一个openid
	 */
	private String weixinOpenid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Integer inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getWeixinImage() {
		return weixinImage;
	}

	public void setWeixinImage(String weixinImage) {
		this.weixinImage = weixinImage;
	}

	public String getWeixinName() {
		return weixinName;
	}

	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	public String getWeixinOpenid() {
		return weixinOpenid;
	}

	public void setWeixinOpenid(String weixinOpenid) {
		this.weixinOpenid = weixinOpenid;
	}
	
	
	
	
}