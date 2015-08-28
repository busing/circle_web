package com.circle.pojo.commission;

/**
 * com.circle.pojo.commission.UserCommission
 * <p> 用户佣金pojo
 * @author ytai
 * @version 1.0.0
 * @2015-4-1 下午2:21:42
 */
public class UserCommission
{
	/**
	 * id （int）
	 * <p> id
	 */
	private int id;
	
	/**
	 * userId （int）
	 * <p> 用户id 
	 */
	private int userId;
	
	/**
	 * commission （double）
	 * <p> 佣金金额
	 */
	private double commission;
	
	/**
	 * lastUpdateTime （String）
	 * <p> 最后更新时间
	 */
	private String lastUpdateTime;
	
	/**
	 * lastHistoryId （int）
	 * <p> 最后佣金变动的佣金历史id
	 */
	private int lastHistoryId;

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

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getLastHistoryId() {
		return lastHistoryId;
	}

	public void setLastHistoryId(int lastHistoryId) {
		this.lastHistoryId = lastHistoryId;
	}
		
	
}
