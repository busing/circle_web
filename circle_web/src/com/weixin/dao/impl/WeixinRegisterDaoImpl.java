package com.weixin.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.weixin.dao.IWeixinRegisterDao;
import com.weixin.pojo.WeixinRegister;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年4月23日 下午10:08:16
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Repository
public class WeixinRegisterDaoImpl implements IWeixinRegisterDao{
	
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(WeixinRegisterDaoImpl.class) ;
	
	public static final String ADD_SQL="insert into t_weixin_register (weixin_id,mobile_phone,step) values(:weixinId,:mobilePhone,:step)";
	
	public static final String UPDATE_SQL="update t_weixin_register set step=:step whsere weixin_id=:weixinID";
	
	public static final String QUERY_SQL="SELECT * FROM t_weixin_register WHERE weixin_id=:weixinId";
	
	public static final String DELETE_SQL="delete from t_weixin_register where weixin_id=:weixinId";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	

	/**
	 * 方法描述:添加微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public boolean addWeixinRegister(WeixinRegister weixinRegister)
	{
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixinId", weixinRegister.getWeixin_id());
			params.put("mobilePhone", weixinRegister.getMobile_phone());
			params.put("step", weixinRegister.getStep());
			return commonDao.update(ADD_SQL,params)>0?true:false;
		} catch (SPTException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 方法描述:更新微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public boolean updateWeixinRegister(WeixinRegister weixinRegister)
	{
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixinId", weixinRegister.getWeixin_id());
			params.put("mobilePhone", weixinRegister.getMobile_phone());
			params.put("step", weixinRegister.getStep());
			return commonDao.update(UPDATE_SQL,params)>0?true:false;
		} catch (SPTException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 方法描述:查询微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public Map<String, Object> queryWeixinRegister(String weixinId)
	{
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixinId", weixinId);
			return commonDao.queryForMap(QUERY_SQL, params);
		} catch (SPTException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 方法描述:更新微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public boolean deleteWeixinRegister(String weixinId)
	{
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixinId",weixinId);
			return commonDao.update(DELETE_SQL,params)>0?true:false;
		} catch (SPTException e) {
			e.printStackTrace();
		}
		return false;
	}
}
