package com.weixin.service;

import java.util.Map;

import com.weixin.pojo.WeixinRegister;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年4月23日 下午10:07:46
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public interface IWeixinRegisterService 
{
	/**
	 * 方法描述:添加微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public boolean addWeixinRegister(WeixinRegister weixinRegister);
	
	/**
	 * 方法描述:更新微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public boolean updateWeixinRegister(WeixinRegister weixinRegister);
	
	/**
	 * 方法描述:查询微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public Map<String, Object>  queryWeixinRegister(String weixinId);
	

	/**
	 * 方法描述:更新微信注册记录数据
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	public boolean deleteWeixinRegister(String weixinId);
	
}
