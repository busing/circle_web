package com.circle.utils.commission.compute;

/**
 * com.circle.utils.commission.compute.SellerCommissionCompute
 * <p> 农场主售卖佣金
 * @author ytai
 * @version 1.0.0
 * @2015-4-2 下午2:49:31
 */
public class SellerCommissionCompute extends ACommissionCompute
{

	@Override
	public double compute(int incomeUserId, int fromUserId, double sourceMoney) {
		return sourceMoney*0.05;
	}

}
