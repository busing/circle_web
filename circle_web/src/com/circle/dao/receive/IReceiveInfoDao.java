package com.circle.dao.receive;

import java.util.List;
import java.util.Map;

import com.circle.pojo.payship.PayAndShip;
import com.circle.pojo.receive.ReceiveInfo;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 下单收货信息 和 支付以及发货方式dao
 * @author Taiyuan
 *
 */
public interface IReceiveInfoDao
{
	
	/**
	 * 添加收货信息
	 * @param receiveInfo
	 * @return
	 */
	public boolean addReceiveInfo(ReceiveInfo receiveInfo) throws SPTException;
	
	/**
	 * 更新收货信息
	 * @param receiveInfo
	 * @return
	 */
	public boolean updateReceiveInfo(ReceiveInfo receiveInfo) throws SPTException;
	
	/**
	 * 删除收货信息
	 * @param id
	 * @return
	 */
	public boolean deleteReceiveInfo(int id) throws SPTException;
	
	/**
	 * 是否存在收货信息
	 * @param userId
	 * @return
	 */
	public boolean isExistsReceiveInfo(int userId) throws SPTException;
	
	/**
	 * 设置默认收货信息
	 * @param id
	 * @return
	 */
	public boolean defaultReceiveInfo(int id,int userId) throws SPTException;
	
	
	/**
	 * 添加支付方式和发货信息
	 * @param payAndShip
	 * @return
	 */
	public boolean addPayAndShipInfo(PayAndShip payAndShip) throws SPTException;
	
	/**
	 * 更新支付方式和发货信息
	 * @param payAndShip
	 * @return
	 */
	public boolean updatePayAndShipInfo(PayAndShip payAndShip) throws SPTException;
	
	/**
	 * 是否存在支付方式和发货信息
	 * @param userId
	 * @return
	 */
	public boolean isExistsPayAndShipInfo(int userId) throws SPTException;
	
	/**
	 * 查询收货信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryReceiveInfo(int userId) throws SPTException;
	
	/**
	 * 查询支付方式
	 * @param userId
	 * @return
	 */
	public Map<String, Object> queryPayAndShipInfo(int userId) throws SPTException;
	
	

}
