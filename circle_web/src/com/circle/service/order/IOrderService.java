package com.circle.service.order;

import java.util.List;
import java.util.Map;

import com.circle.pojo.circle.Circle;
import com.circle.pojo.order.Order;
import com.circle.pojo.order.OrderDetail;
import com.circle.pojo.user.User;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 
 * 订单信息业务逻辑层接口类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 11:08:35
 * <p>
 * Company: Cooper's Summer’
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
public interface IOrderService {
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
	 * 方法描述:订单信息查询
	 * @param id 订单id
	 * @return Order  订单信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Order queryOrderByOrderNo(String orderNo) throws SPTException;
	
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
	 * 
	 * 方法描述:订单详细信息查看
	 * @param id 订单id
	 * @return OrderDetail  订单详细信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Map<String, Object> queryOrderDetailById(String id) throws SPTException;

	
	/**
	 * 方法描述:提交订单
	 * @param 
	 * @return 
	 * date:2015年1月11日
	 * @author Taiyuan
	 */
	public List<Order> submitOrder(List<Circle> circleList,Map<String,List<Map<String, Object>>> orderList,User user,String cartIds,Map<String, String> remarkMap,Order order);
	
	/**
	 * 方法描述:查询订单
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryOrderList(Page page, int userId) throws SPTException;
	
	
	/**
	 * 方法描述:查询订单
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public Map<String,List<Map<String, Object>>> queryOrderDetailList( String orderId) throws SPTException;
	
	
	/**
	 * 方法描述:设置订单付款状态
	 * @param 
	 * @return 
	 * date:2015年3月14日
	 * @author Taiyuan
	 */
	public boolean setOrderPayStatus(int payStatus, String orderId, String tradeNo);
	
	/**
	 * 方法描述:确认收货
	 * @param 
	 * @return 
	 * date:2015年4月12日
	 * @author Taiyuan
	 */
	public boolean reciveGood(String order_id,User user)  throws SPTException;
	
	/**
	 * 
	 * 方法描述:添加手机客户端订单
	 * @param order 订单信息
	 * @param orderDetailList 订单详细信息列表
	 * @throws SPTException
	 * date:2015-6-2
	 * @author wangfengtong
	 */
	public void addMOrder(Order order,List<OrderDetail> orderDetailList) throws SPTException;
	
	/**
	 * 
	 * 方法描述:根据订单id查询订单
	 * @param orderId 订单id
	 * @return 订单详细信息列表
	 * @throws SPTException
	 * date:2015-6-3
	 * @author wangfengtong
	 */
	public List<Map<String, Object>> queryMOrderDetailList(String orderId) throws SPTException;
	
}
