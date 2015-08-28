package com.circle.dao.receive.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.dao.receive.IReceiveInfoDao;
import com.circle.pojo.payship.PayAndShip;
import com.circle.pojo.receive.ReceiveInfo;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 下单收货信息 和 支付以及发货方式dao
 * @author Taiyuan
 *
 */
@Repository
public class ReceiveInfoDaoImpl implements IReceiveInfoDao
{
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(ReceiveInfoDaoImpl.class) ;
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	
	/**
	 * 添加收货信息sql
	 */
	public static final String ADD_RECEIVE_INFO = "insert into t_receive_info (userid,receive_name,receive_phone,receive_address,is_default) values(:userId,:receiveName,:receivePhone,:receiveAddress,:isDefault)";
	
	/**
	 * 修改收货信息sql
	 */
	public static final String UPDATE_RECEIVE_INFO = "update t_receive_info set receive_name=:receiveName,receive_phone=:receivePhone,receive_address=:receiveAddress where id=:id";
	
	/**
	 * 删除收货信息sql
	 */
	public static final String DELETE_RECEIVE_INFO = "delete from t_receive_info where id=:id";
	
	/**
	 * 是否存在收货信息
	 */
	public static final String CHECK_RECEIVE_INFO = "select count(1) from t_receive_info where userid=:userId";
	
	/**
	 * 去掉默认收货信息
	 */
	public static final String REMOVE_DEFALUT_RECEIVE_INFO = "update t_receive_info set is_default=0 where userid=:userId and is_default=1";
	
	/**
	 * 更新默认收货信息
	 */
	public static final String SET_DEFALUT_RECEIVE_INFO = "update t_receive_info set is_default=1 where id=:id";
	
	/**
	 * 添加支付方式信息sql
	 */
	public static final String ADD_PAY_SHIP_TYPE = "insert into t_pay_ship_type (userid,pay_type,ship_type) values(:userId,:payType,:shipType)";
	
	/**
	 * 添加支付方式信息sql
	 */
	public static final String UPDATE_PAY_SHIP_TYPE = "update t_pay_ship_type set pay_type=:payType,ship_type=:shipType where userid=:userId";
	
	
	/**
	 * 是否存在支付方式
	 */
	public static final String CHECK_PAY_SHIP_TYPE = "select count(1) from t_pay_ship_type where userid=:userId";
	
	
	/**
	 * 查询收货人信息
	 */
	public static final String QUERY_RECEIVE_INFO="select * from t_receive_info where userid=:userId order by is_default desc ,id ";
	
	/**
	 * 查询收货人信息
	 */
	public static final String QUERY_PAY_SHIP_TYPE_INFO="select * from t_pay_ship_type where userid=:userId";
	
	
	/**
	 * 添加收货信息
	 * @param receiveInfo
	 * @return
	 */
	public boolean addReceiveInfo(ReceiveInfo receiveInfo) throws SPTException
	{
		Map<String, Object> paramsMap = getReceiveParams(receiveInfo);
		return commonDao.update(ADD_RECEIVE_INFO, paramsMap)>0?true:false;
	}

	private Map<String, Object> getReceiveParams(ReceiveInfo receiveInfo) {
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("receiveAddress", receiveInfo.getReceiveAddress());
		paramsMap.put("id", receiveInfo.getId());
		paramsMap.put("receiveName", receiveInfo.getReceiveName());
		paramsMap.put("receivePhone", receiveInfo.getReceivePhone());
		paramsMap.put("userId", receiveInfo.getUserId());
		paramsMap.put("isDefault", receiveInfo.getIsDefault());
		return paramsMap;
	}
	
	/**
	 * 更新收货信息
	 * @param receiveInfo
	 * @return
	 */
	public boolean updateReceiveInfo(ReceiveInfo receiveInfo) throws SPTException
	{
		Map<String, Object> paramsMap = getReceiveParams(receiveInfo);
		return commonDao.update(UPDATE_RECEIVE_INFO, paramsMap)>0?true:false;
	}
	
	/**
	 * 删除收货信息
	 * @param id
	 * @return
	 */
	public boolean deleteReceiveInfo(int id) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("id", id);
		return commonDao.update(DELETE_RECEIVE_INFO, paramsMap)>0?true:false;
	}
	
	/**
	 * 是否存在收货信息
	 * @param userId
	 * @return
	 */
	public boolean isExistsReceiveInfo(int userId) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		return commonDao.queryForInt(CHECK_RECEIVE_INFO, paramsMap)>0?true:false;
	}
	
	/**
	 * 设置默认收货信息
	 * @param id
	 * @return
	 */
	public boolean defaultReceiveInfo(int id,int userId) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("id", id);
		commonDao.update(REMOVE_DEFALUT_RECEIVE_INFO, paramsMap);
		return commonDao.update(SET_DEFALUT_RECEIVE_INFO, paramsMap)>0?true:false;
		
	}
	
	
	private Map<String, Object> getPayAndShipInfoParams(PayAndShip payAndShip) {
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("payType", payAndShip.getPayType());
		paramsMap.put("shipType", payAndShip.getShipType());
		paramsMap.put("userId", payAndShip.getUserId());
		return paramsMap;
	}
	
	
	/**
	 * 添加支付方式和发货信息
	 * @param payAndShip
	 * @return
	 */
	public boolean addPayAndShipInfo(PayAndShip payAndShip) throws SPTException
	{
		Map<String, Object> paramsMap=getPayAndShipInfoParams(payAndShip);
		return commonDao.update(ADD_PAY_SHIP_TYPE,paramsMap)>0?true:false;
	}
	
	/**
	 * 更新支付方式和发货信息
	 * @param payAndShip
	 * @return
	 */
	public boolean updatePayAndShipInfo(PayAndShip payAndShip) throws SPTException
	{
		Map<String, Object> paramsMap=getPayAndShipInfoParams(payAndShip);
		return commonDao.update(UPDATE_PAY_SHIP_TYPE,paramsMap)>0?true:false;
	}
	
	/**
	 * 是否存在支付方式和发货信息
	 * @param userId
	 * @return
	 */
	public boolean isExistsPayAndShipInfo(int userId) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId",userId);
		return commonDao.queryForInt(CHECK_PAY_SHIP_TYPE,paramsMap)>0?true:false;
	}
	
	/**
	 * 查询收货信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryReceiveInfo(int userId)  throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId",userId);
		return commonDao.queryForList(QUERY_RECEIVE_INFO, paramsMap);
	}
	
	/**
	 * 查询支付方式
	 * @param userId
	 * @return
	 */
	public Map<String, Object> queryPayAndShipInfo(int userId) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId",userId);
		List<Map<String, Object>> data= commonDao.queryForList(QUERY_PAY_SHIP_TYPE_INFO, paramsMap);
		if(data!=null && data.size()>0)
		{
			return data.get(0);
		}
		else
		{
			return null;
		}
	}
	
}
