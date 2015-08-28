package com.circle.utils.commission;

import com.circle.constant.SystemDict;
import com.circle.utils.commission.compute.ACommissionCompute;
import com.circle.utils.commission.compute.InviteCommissionCompute;
import com.circle.utils.commission.compute.OrderCommissionCompute;
import com.circle.utils.commission.compute.SellerCommissionCompute;

public class CommissionComputeFactory
{
	private CommissionComputeFactory(){}
	
	public static ACommissionCompute getCommissionCompute(String commissionType)
	{
		ACommissionCompute commissionCompute=null;
		if(commissionType.equals(SystemDict.COMMISSION_TYPE_INVITE))
		{
			commissionCompute=new InviteCommissionCompute();
		}
		else if(commissionType.equals(SystemDict.COMMISSION_TYPE_ORDER))
		{
			commissionCompute=new OrderCommissionCompute();
		}
		else if(commissionType.equals(SystemDict.COMMISSION_TYPE_SELLER))
		{
			commissionCompute=new SellerCommissionCompute();
		}
		return commissionCompute;
	}
}
