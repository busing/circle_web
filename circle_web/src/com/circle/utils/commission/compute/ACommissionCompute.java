package com.circle.utils.commission.compute;

/**
 * com.circle.utils.commission.compute.ACommissionCompute
 * <p> 佣金计算抽象类
 * @author ytai
 * @version 1.0.0
 * @2015-4-2 下午2:50:37
 */
public abstract class ACommissionCompute
{
	/**
	 * @param incomeUserId 收益用户
	 * @param fromUserId 来源用户
	 * @param sourceMoney 原始金额
	 * @return 计算结果佣金
	 * <p> 计算佣金
	 * @author ytai
	 * @2015-4-2 下午2:52:10
	 */
	public abstract double compute(int incomeUserId, int fromUserId, double sourceMoney);
	
}
