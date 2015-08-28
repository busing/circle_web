package com.weixin.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.weixin.dao.IWeixinRegisterDao;
import com.weixin.pojo.WeixinRegister;
import com.weixin.service.IWeixinRegisterService;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年4月23日 下午10:24:54
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Service
public class WeixinRegisterServiceImpl implements IWeixinRegisterService {
	
	@Resource
	private IWeixinRegisterDao weixinRegisterDao;
	

	/**
	 * 方法描述:
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	@Override
	public boolean addWeixinRegister(WeixinRegister weixinRegister) {
		return weixinRegisterDao.addWeixinRegister(weixinRegister);
	}

	/**
	 * 方法描述:
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	@Override
	public boolean updateWeixinRegister(WeixinRegister weixinRegister) {
		return weixinRegisterDao.updateWeixinRegister(weixinRegister);
	}

	/**
	 * 方法描述:
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	@Override
	public Map<String, Object> queryWeixinRegister(String weixinId) {
		return weixinRegisterDao.queryWeixinRegister(weixinId);
	}

	/**
	 * 方法描述:
	 * @param 
	 * @return 
	 * date:2015年4月23日
	 * @author Taiyuan
	 */
	@Override
	public boolean deleteWeixinRegister(String weixinId) {
		return weixinRegisterDao.deleteWeixinRegister(weixinId);
	}

}
