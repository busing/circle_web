package com.circle.pojo.circle;

/**
 * 
 * 圈子成员实体信息. <br>
 * <p>
 * Copyright: Copyright (c) 2015-01-19
 * <p>
 * <p>
 * 
 * @author zhoujie
 * @version 1.0.0
 */
public class CircleMember {
	private Integer circleId;
	private Integer userId;
	private Integer identityType;
	private String joinTime;
	
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIdentityType() {
		return identityType;
	}
	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}
	public String getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
}