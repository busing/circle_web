package com.circle.pojo.commission;

/**
 * com.circle.pojo.commission.ExtractApp
 * <p> 提现申请
 * @author ytai
 * @version 1.0.0
 * @2015-4-8 下午1:57:21
 */
public class ExtractApp
{
	/**
	 * id （int）
	 * <p> 
	 */
	private int id;
	
	/**
	 * userId （int）
	 * <p> 申请用户id
	 */
	private int userId;
	
	/**
	 * commissionId （int）
	 * <p> 佣金id
	 */
	private int commissionId;
	
	/**
	 * extractCommission （double）
	 * <p> 提取佣金额度
	 */
	private double extractCommission;
	
	/**
	 * alipayAccount （String）
	 * <p> 支付宝账户
	 */
	private String alipayAccount;
	
	/**
	 * alipayName （String）
	 * <p> 支付宝姓名
	 */
	private String alipayName;
	
	/**
	 * status （int）
	 * <p> 状态
	 */
	private int status;
	
	private String createTime;

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

	public int getCommissionId() {
		return commissionId;
	}

	public void setCommissionId(int commissionId) {
		this.commissionId = commissionId;
	}

	public double getExtractCommission() {
		return extractCommission;
	}

	public void setExtractCommission(double extractCommission) {
		this.extractCommission = extractCommission;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
