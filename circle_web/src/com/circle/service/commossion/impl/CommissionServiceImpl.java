package com.circle.service.commossion.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circle.constant.ConstantBusiKeys;
import com.circle.constant.SystemDict;
import com.circle.dao.commission.ICommissionDao;
import com.circle.pojo.commission.CommissionHistory;
import com.circle.pojo.commission.ExtractApp;
import com.circle.pojo.commission.UserCommission;
import com.circle.service.commossion.ICommissionService;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.ProUtil;

@Transactional
@Service
public class CommissionServiceImpl implements ICommissionService
{

	public static final Logger logger=Logger.getLogger(CommissionServiceImpl.class);

	/**
	 * 注入DAO方法
	 */
	@Resource
	private ICommissionDao commissionDao;
	
	/**
	 * @param userId 佣金获得用户
	 * @param fromUserId 佣金来源用户
	 * @param commission 佣金金额
	 * @param commissionType 佣金类型
	 * @param circleId 圈子id
	 * @param sourceId 源数据id
	 * @return
	 * <p> 
	 * @author ytai
	 * @2015-4-2 下午1:43:26
	 */
	public boolean increaseUserCommission(int userId, int fromUserId, double commission, int commissionType, int circleId, int sourceId)
	{
		CommissionHistory commissionHistory=new CommissionHistory(userId, fromUserId, commission, commissionType, circleId, sourceId);
		return increaseUserCommission(commissionHistory);
	}
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 增加用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:37:52
	 */
	public boolean increaseUserCommission(CommissionHistory commissionHistory)
	{
		try
		{
			boolean isExists=commissionDao.isExitsUserCommission(commissionHistory.getUserId());
			if(!isExists)
			{
				UserCommission userCommission=new UserCommission();
				userCommission.setCommission(0);
				userCommission.setUserId(commissionHistory.getUserId());
				userCommission.setLastHistoryId(0);
				commissionDao.addUserCommission(userCommission);
			}
			commissionDao.addCommissionHistory(commissionHistory);
			commissionDao.increaseUserCommission(commissionHistory);
		}
		catch (SPTException e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return false;
		}
		return true;
	}
	
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 减少用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:38:00
	 */
	public boolean reduceUserCommission(int userId, double commission)
	{
		try
		{
			boolean isExists=commissionDao.isExitsUserCommission(userId);
			if(isExists)
			{
				commissionDao.reduceUserCommission(userId,commission);
			}
		}
		catch (SPTException e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return true;
	}
	
	
	/**
	 * @param page 分页对象
	 * @return 佣金历史数据
	 * <p> 查询佣金历史
	 * @author ytai
	 * @2015-4-1 下午2:44:25
	 */
	public List<Map<String, Object>> queryCommossionHistoryList(Page page,int userId)  throws SPTException
	{
		List<Map<String, Object>> commissionList= commissionDao.queryCommossionHistoryList(page, userId);
		for (Map<String, Object> map : commissionList)
		{
			map.put("commission_type_str", SystemDict.getDict(SystemDict.COMMISSION_TYPE, map.get("commission_type").toString()).getType_name() );
		}
		return commissionList;
	}
	
	
	/**
	 * @param startDate 统计开始时间
	 * @param endDate 统计结束时间
	 * @return 
	 * <p> 统计佣金排行
	 * @author ytai
	 * @2015-4-1 下午2:47:49
	 */
	public List<Map<String, Object>> statisticsCommossionHistory(String startDate,String endDate,int userId, String commissionType)  throws SPTException
	{
		List<Map<String, Object>> commissionLIst= commissionDao.statisticsCommossionHistory(startDate, endDate,userId,commissionType );
		for (Map<String, Object> map : commissionLIst) 
		{
			map.put("head_image", ProUtil.get(ConstantBusiKeys.PropertiesKey.DOMAIN)+map.get("head_image"));
		}
		return commissionLIst;
	}
	
	
	/**
	 * @param userId
	 * @return
	 * @throws SPTException
	 * <p> 查询用户当前佣金数据
	 * @author ytai
	 * @2015-4-2 下午2:19:08
	 */
	public Map<String, Object> queryUserCommossion(int userId)  throws SPTException
	{
		return commissionDao.queryUserCommossion(userId);
	}
	
	

	/**
	 * @param extractApp
	 * @return
	 * <p> 提交提现申请
	 * @author ytai
	 * @2015-4-8 下午2:17:04
	 */
	public boolean addExtractApp(ExtractApp extractApp) 
	{
		boolean flag=false;
		try
		{
			flag=commissionDao.reduceUserCommission(extractApp.getUserId(),extractApp.getExtractCommission());
			if(flag)
			{
				flag=commissionDao.addExtractApp(extractApp);
			}
		}
		catch (SPTException e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag;
	}
	
	/**
	 * @param id
	 * @param status
	 * @return
	 * <p> 处理提现申请
	 * @author ytai
	 * @2015-4-8 下午2:17:12
	 */
	public boolean dealExtractApp(int id, int status)
	{
		boolean flag=true;
		try
		{
			//更新申请状态
			flag=commissionDao.updateExtractAppStatus(id, status);
			if(flag)
			{
				//提现成功
				if(status==2)
				{
					//flag=commissionDao.updateExtractAppStatus(id, status);
				}
				//提现失败 退回钱
				else
				{
					//查询提现申请
					Map<String, Object> extractApp=commissionDao.queryExtractAppById(id);
					double extractCommission=Double.parseDouble(extractApp.get("extract_commission").toString());
					int userId=Integer.parseInt(extractApp.get("user_id").toString());
					
					//增加佣金（还回对应的金额）
					flag=commissionDao.increaseUserCommission(userId, extractCommission);
				}
			}
			
		}
		catch (SPTException e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			flag=false;
		}
		return flag;
		
	}
	
	/**
	 * @param page
	 * @return
	 * @throws SPTException
	 * <p> 查询提现申请列表
	 * @author ytai
	 * @2015-4-8 下午2:12:21
	 */
	public List<Map<String, Object>> queryExtractApplyList(Page page)  throws SPTException
	{
		return commissionDao.queryExtractApplyList(page);
	}

}
