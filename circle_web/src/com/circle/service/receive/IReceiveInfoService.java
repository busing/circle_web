package com.circle.service.receive;

import java.util.List;
import java.util.Map;


import com.circle.pojo.payship.PayAndShip;
import com.circle.pojo.receive.ReceiveInfo;
import com.circle.pojo.user.User;

public interface IReceiveInfoService
{
	/**
	 * 保存收货信息
	 * @param receiveInfo
	 * @return
	 */
	public boolean saveReceiveInfo(ReceiveInfo receiveInfo);
	
	/**
	 * 删除收货信息
	 * @param id
	 * @return
	 */
	public boolean deleteReceiveInfo(int id);
	
	/**
	 * 设置默认收货信息
	 * @param id
	 * @return
	 */
	public boolean defaultReceiveInfo(int id,int userId);
	
	
	/**
	 * 保存支付方式和发货信息
	 * @param payAndShip
	 * @return
	 */
	public boolean savePayAndShipInfo(PayAndShip payAndShip);
	
	/**
	 * 查询收货信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryReceiveInfo(int userId);
	
	/**
	 * 是否存在收货信息
	 * @param userId
	 * @return
	 */
	public boolean isExistsReceiveInfo(int userId);
	
	
	/**
	 * 方法描述:保存默认的收货信息
	 * @param 
	 * @return 
	 * date:2015年2月16日
	 * @author Taiyuan
	 */
	public boolean saveDefaultReceiveInfo(User user);
	
	/**
	 * 查询支付方式
	 * @param userId
	 * @return
	 */
	public Map<String, Object> queryPayAndShipInfo(int userId);
}
