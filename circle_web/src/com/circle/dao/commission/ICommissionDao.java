package com.circle.dao.commission;

import java.util.List;
import java.util.Map;
import com.circle.pojo.commission.CommissionHistory;
import com.circle.pojo.commission.ExtractApp;
import com.circle.pojo.commission.UserCommission;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * com.circle.dao.commission.ICommissionDao
 * <p> 佣金dao
 * @author ytai
 * @version 1.0.0
 * @2015-4-1 下午2:26:41
 */
public interface ICommissionDao
{
	
	/**
	 * @param userCommission 
	 * @return 是否新增成功
	 * <p> 添加用户佣金数据
	 * @author ytai
	 * @2015-4-1 下午2:33:46
	 */
	public boolean addUserCommission(UserCommission userCommission)  throws SPTException;
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 增加用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:37:52
	 */
	public boolean increaseUserCommission(CommissionHistory commissionHistory)  throws SPTException;
	
	/**
	 * @param userId
	 * @param commission
	 * @return
	 * <p> 增加用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:38:00
	 */
	public boolean increaseUserCommission(int userId, double commission)  throws SPTException;
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 减少用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:38:00
	 */
	public boolean reduceUserCommission(int userId, double commission)  throws SPTException;
	
	/**
	 * @param userId
	 * @return
	 * <p> 检查用户是否已经有佣金数据
	 * @author ytai
	 * @2015-4-1 下午2:38:08
	 */
	public boolean isExitsUserCommission(int userId)  throws SPTException;
	
	/**
	 * @param history 佣金历史对象
	 * @return 是否添加成功
	 * <p> 添加佣金历史
	 * @author ytai
	 * @2015-4-1 下午2:29:10
	 */
	public boolean addCommissionHistory(CommissionHistory history)  throws SPTException;
	
	
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
	 * @throws SPTException
	 * <p> 添加提现申请
	 * @author ytai
	 * @2015-4-8 下午2:00:09
	 */
	public boolean addExtractApp(ExtractApp extractApp) throws SPTException;
	
	/**
	 * @param id
	 * @param status
	 * @return
	 * @throws SPTException
	 * <p> 更新提现申请状态
	 * @author ytai
	 * @2015-4-8 下午2:00:22
	 */
	public boolean updateExtractAppStatus(int id , int status) throws SPTException;
	
	/**
	 * @param id
	 * @return
	 * @throws SPTException
	 * <p> 查询提现申请
	 * @author ytai
	 * @2015-4-8 下午2:00:31
	 */
	public Map<String, Object> queryExtractAppById(int id ) throws SPTException;
	
	
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
