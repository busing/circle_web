package com.circle.pojo.commission;

/**
 * com.circle.pojo.commission.CommissionHistory
 * <p> 佣金历史pojo
 * @author ytai
 * @version 1.0.0
 * @2015-4-1 下午2:17:51
 */
public class CommissionHistory
{

	/**
	 * id （int）
	 * <p> id
	 */
	private int id;
	
	/**
	 * userId （int）
	 * <p> 收益用户id
	 */
	private int userId;
	
	/**
	 * fromUserId （int）
	 * <p> 来源用户id
	 */
	private int fromUserId;
	
	/**
	 * commission （double）
	 * <p> 佣金金额
	 */
	private double commission;
	
	/**
	 * commissionType （int）
	 * <p> 佣金类型
	 */
	private int commissionType;
	
	/**
	 * circleId （int）
	 * <p> 圈子di
	 */
	private int circleId;
	
	/**
	 * createDate （String）
	 * <p> 创建时间
	 */
	private String createDate;
	
	/**
	 * sourceId （int）
	 * <p> 来源数据id
	 */
	private int sourceId;

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

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public int getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(int commissionType) {
		this.commissionType = commissionType;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @param userId 佣金获得用户
	 * @param fromUserId 佣金来源用户
	 * @param commission 佣金额度
	 * @param commissionType 佣金类型
	 * @param circleId 圈子id
	 * @param sourceId 原始来源数据id
	 */
	public CommissionHistory(int userId, int fromUserId, double commission, int commissionType, int circleId, int sourceId)
	{
		super();
		this.userId = userId;
		this.fromUserId = fromUserId;
		this.commission = commission;
		this.commissionType = commissionType;
		this.circleId = circleId;
		this.sourceId = sourceId;
	}
	
	
	
	
}
