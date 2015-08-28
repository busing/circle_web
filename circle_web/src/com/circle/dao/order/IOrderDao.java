package com.circle.dao.order;

import java.util.List;
import java.util.Map;

import com.circle.pojo.order.Order;
import com.circle.pojo.order.OrderDetail;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 
 * 订单信息DAO层方法统一接口 <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月23日 15:42:10
 * <p>
 * Company: Cooper's Summer
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
public interface IOrderDao {
	
	/**
	 * 
	 * 方法描述:查询订单列表
	 * @param page 分页查询控件
	 * @return List<Order> 订单列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<Order> queryOrderList(Page page) throws SPTException;

	/**
	 * 
	 * 方法描述:订单信息查看
	 * @param id 订单id
	 * @return Order  订单信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Order queryOrderById(String id) throws SPTException;
	
	/**
	 * 
	 * 方法描述:订单详细信息查看
	 * @param id 订单id
	 * @return OrderDetail  订单详细信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<OrderDetail> queryOrdersDetailById(String id,Page page) throws SPTException;
	
	/**
	 * 方法描述:保存订单
	 * @param 
	 * @return 
	 * date:2015年1月11日
	 * @author Taiyuan
	 */
	public boolean saveOrder(Order order)  throws SPTException;
	
	/**
	 * 方法描述:保存订单明细
	 * @param 
	 * @return 
	 * date:2015年1月11日
	 * @author Taiyuan
	 */
	public boolean saveOrderDetail(OrderDetail orderDetail)  throws SPTException;
	
	/**
	 * 
	 * 方法描述:批量添加订单详细信息
	 * @param orderDetailList 订单详细信息列表
	 * @throws SPTException 
	 * date:2015-6-2
	 * @author wangfengtong
	 */
	public void batchSaveOrderDetail(List<OrderDetail> orderDetailList) throws SPTException;
	
	/**
	 * 方法描述:查询订单
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryOrderList(Page page, int userId) throws SPTException;
	
	/**
	 * 方法描述:查询订明细列表
	 * @param 
	 * @return 
	 * date:2015年1月13日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryOrderDetailList(String orderId)  throws SPTException;

	/**
	 * 方法描述:查询订单明细
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	public Map<String, Object> queryOrderDetailById(String id)  throws SPTException;
	
	/**
	 * 方法描述:设置订单付款状态
	 * @param 
	 * @return 
	 * date:2015年3月14日
	 * @author Taiyuan
	 */
	public boolean setOrderPayStatus(int payStatus, String orderId,  String tradeNo) throws SPTException;

	/**
	 * 
	 * 方法描述:订单详情信息
	 * @param orderNo 订单号
	 * @return Order  订单信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Order queryOrderByOrderNo(String orderNo) throws SPTException ;
	
	/**
	 * 方法描述:确认收货
	 * @param 
	 * @return 
	 * date:2015年4月12日
	 * @author Taiyuan
	 */
	public boolean reciveGood(String order_id) throws SPTException;
	
	
}
