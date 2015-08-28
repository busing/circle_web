package com.circle.dao.commission.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.dao.commission.ICommissionDao;
import com.circle.pojo.commission.CommissionHistory;
import com.circle.pojo.commission.ExtractApp;
import com.circle.pojo.commission.UserCommission;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.StringUtil;

/**
 * com.circle.dao.commission.impl.ICommissionDaoImpl
 * <p> 佣金dao
 * @author ytai
 * @version 1.0.0
 * @2015-4-1 下午2:26:41
 */
@Repository
public class CommissionDaoImpl implements ICommissionDao
{
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(CommissionDaoImpl.class) ;
	
	/**
	 * ADD_USERCOMMISSION_SQL （String）
	 * <p> 添加用户佣金数据sql
	 */
	public static final String ADD_USERCOMMISSION_SQL="insert into t_user_commission (user_id,commission,last_update_time,last_history_id) values(:userId,:commission,now(),:lastHistoryId)";
	
	/**
	 * INCREASE_USERCOMMISSION_SQL （String）
	 * <p> 增加用户佣金数据sql
	 */
	public static final String INCREASE_USERCOMMISSION_SQL="update t_user_commission set commission=commission+:commission,last_update_time=now(),last_history_id=:lastHistoryId where user_id=:userId";
	
	/**
	 * INCREASE_USERCOMMISSION_SQL （String）
	 * <p> 增加用户佣金数据sql 用于佣金提现不成功退回金额
	 */
	public static final String INCREASE_USERCOMMISSION_NOHISTORY_SQL="update t_user_commission set commission=commission+:commission, last_update_time=now() where user_id=:userId";
	
	/**
	 * REDUCE_USERCOMMISSION_SQL （String）
	 * <p> 减少用户佣金数据sql
	 */
	public static final String REDUCE_USERCOMMISSION_SQL="update t_user_commission set commission=commission-:commission ,last_update_time=now() where user_id=:userId";
	
	
	/**
	 * ISEXITS_USERCOMMISSION_SQL （String）
	 * <p> 检查用户是否存在佣金数据sql
	 */
	public static final String ISEXITS_USERCOMMISSION_SQL="select count(1) from t_user_commission where user_id=:userId";
	
	/**
	 * ADD_USERCOMMISSION_SQL （String）
	 * <p> 添加用户佣金历史数据sql
	 */
	public static final String ADD_COMMISSIONHISTORY_SQL="insert into t_commission_history (user_id,from_user_id,commission,commission_type,circle_id,create_time,source_id) values(:userId,:fromUserId,:commission,:commissionType,:circleId,now(),:sourceId)";
	
	
	/**
	 * QUERY_COMMOSSIONHISTORYLIST_SQL （String）
	 * <p> 查询佣金历史数据sql
	 */
	public static final String QUERY_COMMOSSIONHISTORYLIST_SQL="select h.user_id,h.from_user_id,h.commission,h.commission_type,date_format(h.create_time,'%Y-%m-%d')create_time,u.name from t_commission_history h,t_user u where h.from_user_id=u.id and h.user_id=:userId ";
	
	/**
	 * ADD_USERCOMMISSION_SQL （String）
	 * <p> 统计佣金排行sql
	 */
	public static final String STATISTICS_COMMOSSIONHISTORY_SQL="select sum(h.commission) commission,u.id userid,u.name,u.head_image from t_commission_history h,t_user u where u.id=h.user_id ";
	
	
	/**
	 * QUERY_USERCOMMOSSION_SQL （String）
	 * <p> 查询用户佣金数据
	 */
	public static final String QUERY_USERCOMMOSSION_SQL="select * from t_user_commission where user_id=:userId";
	
	
	/**
	 * ADD_EXTRACT_APPLY_SQL （String）
	 * <p> 添加提现申请
	 */
	public static final String ADD_EXTRACT_APPLY_SQL="insert into t_extract_apply (user_id,commission_id,extract_commission,alipay_account,alipay_name,status,create_time) values(:userId,:commissionId,:extractCommission,:alipayAccount,:alipayName,:status,now())";
	
	
	/**
	 * UPDATE_EXTRACT_APPLY_STATUS_SQL （String）
	 * <p> 更新提现申请状态
	 */
	public static final String UPDATE_EXTRACT_APPLY_STATUS_SQL="update t_extract_apply set status=:status where id=:id";
	
	
	/**
	 * QUERY_EXTRACT_APPLY_BYID_SQL （String）
	 * <p> 根据id查询提现申请数据
	 */
	public static final String QUERY_EXTRACT_APPLY_BYID_SQL="select * from  t_extract_apply where id=:id";
	
	
	/**
	 * QUERY_EXTRACT_APPLY_SQL （String）
	 * <p> 查询提现申请数据列表
	 */
	public static final String QUERY_EXTRACT_APPLY_SQL="select a.*,u.name,u.mobilephone from  t_extract_apply a,t_user u where u.id=a.user_id ";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;

	/**
	 * @param userCommission 
	 * @return 是否新增成功
	 * <p> 添加用户佣金数据
	 * @author ytai
	 * @2015-4-1 下午2:33:46
	 */
	public boolean addUserCommission(UserCommission userCommission) throws SPTException
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userCommission.getUserId());
		params.put("commission", userCommission.getCommission());
		params.put("lastHistoryId", userCommission.getLastHistoryId());
		boolean flag= commonDao.update(ADD_USERCOMMISSION_SQL, params)>0?true:false;
		if(flag)
		{
			int id=commonDao.getLastId("t_user_commission", "id");
			userCommission.setId(id);
		}
		return flag;
	}
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 增加用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:37:52
	 */
	public boolean increaseUserCommission(CommissionHistory commissionHistory) throws SPTException
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", commissionHistory.getUserId());
		params.put("commission", commissionHistory.getCommission());
		params.put("lastHistoryId", commissionHistory.getId());
		return commonDao.update(INCREASE_USERCOMMISSION_SQL, params)>0?true:false;
	}
	
	/**
	 * @param userCommission
	 * @return
	 * <p> 减少用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:38:00
	 */
	public boolean reduceUserCommission(int userId, double commission) throws SPTException
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("commission", commission);
		return commonDao.update(REDUCE_USERCOMMISSION_SQL, params)>0?true:false;
	}
	
	/**
	 * @param userId
	 * @param commission
	 * @return
	 * <p> 增加用户佣金
	 * @author ytai
	 * @2015-4-1 下午2:38:00
	 */
	public boolean increaseUserCommission(int userId, double commission)  throws SPTException
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("commission", commission);
		return commonDao.update(INCREASE_USERCOMMISSION_NOHISTORY_SQL, params)>0?true:false;
	}
	
	/**
	 * @param userId
	 * @return
	 * <p> 检查用户是否已经有佣金数据
	 * @author ytai
	 * @2015-4-1 下午2:38:08
	 */
	public boolean isExitsUserCommission(int userId) throws SPTException
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return commonDao.queryForInt(ISEXITS_USERCOMMISSION_SQL, params)>0?true:false;
	}
	
	/**
	 * @param history 佣金历史对象
	 * @return 是否添加成功
	 * <p> 添加佣金历史
	 * @author ytai
	 * @2015-4-1 下午2:29:10
	 */
	public boolean addCommissionHistory(CommissionHistory history) throws SPTException
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", history.getUserId());
		params.put("fromUserId", history.getFromUserId());
		params.put("commission", history.getCommission());
		params.put("commissionType", history.getCommissionType());
		params.put("circleId", history.getCircleId());
		params.put("sourceId", history.getSourceId());
		boolean flag= commonDao.update(ADD_COMMISSIONHISTORY_SQL, params)>0?true:false;
		if(flag)
		{
			int id=commonDao.getLastId("t_commission_history", "id");
			history.setId(id);
		}
		return flag; 
	}
	
	
	/**
	 * @param page 分页对象
	 * @return 佣金历史数据
	 * <p> 查询佣金历史
	 * @author ytai
	 * @2015-4-1 下午2:44:25
	 */
	public List<Map<String, Object>> queryCommossionHistoryList(Page page,int userId) throws SPTException
	{
		StringBuilder sb=new StringBuilder(QUERY_COMMOSSIONHISTORYLIST_SQL);
		if(page.getSearchParam()==null)
		{
			page.setSearchParam(new HashMap<String, String>());
		}
		page.getSearchParam().put("userId", userId+"");
		if(page != null && page.getSearchParam() != null)
		{
			if(page.getSearchParam().containsKey("commission_type") && !StringUtil.isEmpty(page.getSearchParam().get("commission_type")))
			{
				sb.append(" and h.commission_type in ("+page.getSearchParam().get("commission_type")+" )");
			}
			if(page.getSearchParam().containsKey("startDate") && !StringUtil.isEmpty(page.getSearchParam().get("startDate"))
				&&page.getSearchParam().containsKey("endDate") && !StringUtil.isEmpty(page.getSearchParam().get("endDate")))
			{
				page.getSearchParam().put("startTime",page.getSearchParam().get("startDate")+" 00:00:00");
				page.getSearchParam().put("endTime",page.getSearchParam().get("endDate")+" 23:59:59");
				sb.append(" and h.create_time between date_format(:startTime,'%Y-%m-%d %H:%i:%s') and date_format(:endTime,'%Y-%m-%d %H:%i:%s')");
			}
			
		}
		sb.append(" order by h.create_time desc ");
		return commonDao.queryForList(sb.toString(), page.getSearchParam(),page);
	}
	
	
	/**
	 * @param startDate 统计开始时间
	 * @param endDate 统计结束时间
	 * @return 
	 * <p> 统计佣金排行
	 * @author ytai
	 * @2015-4-1 下午2:47:49
	 */
	public List<Map<String, Object>> statisticsCommossionHistory(String startDate,String endDate, int userId, String commissionType) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		StringBuilder sb=new StringBuilder(STATISTICS_COMMOSSIONHISTORY_SQL);
		if(!StringUtil.isEmpty(startDate) && !StringUtil.isEmpty(endDate))
		{
			startDate+=" 00:00:00";
			endDate+=" 23:59:59";
			sb.append(" and h.create_time between date_format(:startDate,'%Y-%m-%d %H:%i:%s') and ate_format(:endDate,'%Y-%m-%d %H:%i:%s')");
		}
		if(userId!=0)
		{
			sb.append(" and user_id=:userId");
			paramsMap.put("userId", userId);
		}
		if(!StringUtil.isEmpty(commissionType))
		{
			sb.append(" and commission_type in ("+commissionType+")");
		}
		
		
		if(userId==0)
		{
			sb.append(" group by h.user_id ");
		}
		
//		if(StringUtil.isEmpty(commissionType))
//		{
//			sb.append(" group by h.commission_type ");
//		}
		
		sb.append(" order by commission desc  limit 10");
		return commonDao.queryForList(sb.toString(),paramsMap);
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
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return commonDao.queryForMap(QUERY_USERCOMMOSSION_SQL, params);
	}

	/**
	 * @param extractApp
	 * @return
	 * @throws SPTException
	 * <p> 添加提现申请
	 * @author ytai
	 * @2015-4-8 下午2:00:09
	 */
	public boolean addExtractApp(ExtractApp extractApp) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", extractApp.getUserId());
		paramsMap.put("status", extractApp.getStatus());
		paramsMap.put("extractCommission", extractApp.getExtractCommission());
		paramsMap.put("commissionId", extractApp.getCommissionId());
		paramsMap.put("alipayName", extractApp.getAlipayName());
		paramsMap.put("alipayAccount", extractApp.getAlipayAccount());
		return commonDao.update(ADD_EXTRACT_APPLY_SQL, paramsMap)>0?true:false;
	}
	
	/**
	 * @param id
	 * @param status
	 * @return
	 * @throws SPTException
	 * <p> 更新提现申请状态
	 * @author ytai
	 * @2015-4-8 下午2:00:22
	 */
	public boolean updateExtractAppStatus(int id , int status) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("id", id);
		paramsMap.put("status", status);
		return commonDao.update(UPDATE_EXTRACT_APPLY_STATUS_SQL, paramsMap)>0?true:false;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws SPTException
	 * <p> 查询提现申请
	 * @author ytai
	 * @2015-4-8 下午2:00:31
	 */
	public Map<String, Object> queryExtractAppById(int id ) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("id", id);
		return commonDao.queryForMap(QUERY_EXTRACT_APPLY_BYID_SQL, paramsMap);
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
		if(page.getSearchParam()==null)
		{
			page.setSearchParam(new HashMap<String, String>());
		}
		return commonDao.queryForList(QUERY_EXTRACT_APPLY_SQL, page.getSearchParam(), page);
	}
	

}
