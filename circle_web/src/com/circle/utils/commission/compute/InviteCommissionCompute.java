package com.circle.utils.commission.compute;

/**
 * com.circle.utils.commission.compute.InviteCommissionCompute
 * <p> 邀请注册佣金
 * @author ytai
 * @version 1.0.0
 * @2015-4-2 下午2:47:57
 */
public class InviteCommissionCompute extends ACommissionCompute
{
	
	/**
	 * 方法描述:邀请一个用户0.5元
	 * @param 
	 * @return 
	 * date:2015年4月3日
	 * @author Taiyuan
	 */
	@Override
	public double compute(int incomeUserId, int fromUserId, double sourceMoney) {
		return 0.5;
	}

		
	
}
