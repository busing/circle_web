package com.circle.service.commossion;

import java.util.List;
import java.util.Map;

import com.circle.pojo.commission.CommissionHistory;
import com.circle.pojo.commission.ExtractApp;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

public interface ICommissionService
{
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 增加用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:37:52
	 */
	public boolean increaseUserCommission(CommissionHistory commissionHistory);
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 减少用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:38:00
	 */
	public boolean reduceUserCommission(int userId, double commission);
	
	
	/**
	 * @param page 分页对象
	 * @return 佣金历史数据
	 * <p> 查询佣金历史
	 * @author ytai
	 * @2015-4-1 下午2:44:25
	 */
	public List<Map<String, Object>> queryCommossionHistoryList(Page page,int userId)  throws SPTException;
	
	
	/**
	 * @param startDate 统计开始时间
	 * @param endDate 统计结束时间
	 * @return 
	 * <p> 统计佣金排行
	 * @author ytai
	 * @2015-4-1 下午2:47:49
	 */
	public List<Map<String, Object>> statisticsCommossionHistory(String startDate,String endDate, int userId, String commissionType)  throws SPTException;
	
	/**
	 * @param userId
	 * @return
	 * @throws SPTException
	 * <p> 查询用户当前佣金数据
	 * @author ytai
	 * @2015-4-2 下午2:19:08
	 */
	public Map<String, Object> queryUserCommossion(int userId)  throws SPTException;
	
	
	/**
	 * @param extractApp
	 * @return
	 * <p> 提交提现申请
	 * @author ytai
	 * @2015-4-8 下午2:17:04
	 */
	public boolean  addExtractApp(ExtractApp extractApp) ;
	
	/**
	 * @param id
	 * @param status
	 * @return
	 * <p> 处理提现申请
	 * @author ytai
	 * @2015-4-8 下午2:17:12
	 */
	public boolean dealExtractApp(int id, int status);
	
	/**
	 * @param page
	 * @return
	 * @throws SPTException
	 * <p> 查询提现申请列表
	 * @author ytai
	 * @2015-4-8 下午2:12:21
	 */
	public List<Map<String, Object>> queryExtractApplyList(Page page)  throws SPTException;
}
