package com.circle.pojo.comment;

/**
 * 订单评价实体类
 * @author Taiyuan
 *
 */
public class Comment
{
	/**
	 * id
	 */
	private int id;
	
	/**
	 * 订单id
	 */
	private int orderId;
	
	/**
	 * 商品id
	 */
	private int goodId;
	
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 用户姓名
	 * userName 
	 */
	private String userName;
	
	/**
	 * 圈子id
	 */
	private int circleId;
	
	/**
	 * 订单明细id
	 */
	private int orderDetailId;
	
	/**
	 * 评论内容
	 */
	private String commentText;
	
	
	/**
	 * 服务评论内容
	 */
	private String serverCommentText;
	
	/**
	 * 评论时间
	 */
	private String commentTime;
	
	/**
	 * 商品描述打分
	 */
	private int describeScore;
	
	/**
	 * 服务打分
	 */
	private int serverScore;
	
	/**
	 * 物流打分
	 */
	private int shipScore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	public String getServerCommentText() {
		return serverCommentText;
	}

	public void setServerCommentText(String serverCommentText) {
		this.serverCommentText = serverCommentText;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public int getDescribeScore() {
		return describeScore;
	}

	public void setDescribeScore(int describeScore) {
		this.describeScore = describeScore;
	}

	public int getServerScore() {
		return serverScore;
	}

	public void setServerScore(int serverScore) {
		this.serverScore = serverScore;
	}

	public int getShipScore() {
		return shipScore;
	}

	public void setShipScore(int shipScore) {
		this.shipScore = shipScore;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	
	
	
}
