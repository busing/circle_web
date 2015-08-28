package com.circle.utils.commission.compute;

/**
 * com.circle.utils.commission.compute.OrderCommissionCompute
 * <p> 新用户注册一个月下单提成佣金
 * @author ytai
 * @version 1.0.0
 * @2015-4-2 下午2:50:03
 */
public class OrderCommissionCompute extends ACommissionCompute
{

	@Override
	public double compute(int incomeUserId, int fromUserId, double sourceMoney)
	{
		return sourceMoney*0.05;
	}

}
