package com.circle.service.order.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circle.constant.SystemDict;
import com.circle.dao.good.IGoodDao;
import com.circle.dao.order.IOrderDao;
import com.circle.dao.shopcart.IShopCartDAO;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.commission.CommissionHistory;
import com.circle.pojo.order.Order;
import com.circle.pojo.order.OrderDetail;
import com.circle.pojo.user.User;
import com.circle.service.circle.ICircleService;
import com.circle.service.commossion.ICommissionService;
import com.circle.service.order.IOrderService;
import com.circle.utils.IDUtil;
import com.circle.utils.NumUtils;
import com.circle.utils.StringUtils;
import com.circle.utils.commission.CommissionComputeFactory;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.ProUtil;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 
 * 订单信息业务逻辑层实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 11:11:37
 * <p>
 * Company: Cooper's Summer
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
@Transactional
@Service
public class OrderServiceImpl implements IOrderService {
	
	public static final Logger logger=Logger.getLogger(OrderServiceImpl.class);

	/**
	 * 注入DAO方法
	 */
	@Resource
	private IOrderDao orderDao;
	
	/**
	 * 注入DAO方法
	 */
	@Resource
	private IGoodDao goodDao;
	
	/**
	 * 注入DAO方法
	 */
	@Resource
	private IShopCartDAO shopCartDAO;
	
	/**
	 * 注入佣金service
	 */
	@Resource
	ICommissionService commissionService;
	
	/**
	 * 注入圈子（农场）service
	 */
	@Resource
	ICircleService circleService;
	
	/**
	 * 
	 * 方法描述:查询订单列表
	 * @param page 分页查询控件
	 * @return List<Order> 订单列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<Order> queryOrderList(Page page) throws SPTException{
		return orderDao.queryOrderList(page);
	}
	
	/**
	 * 
	 * 方法描述:订单信息查看
	 * @param id 订单id
	 * @return Order  订单信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Order queryOrderById(String id) throws SPTException{
		return orderDao.queryOrderById(id);
	}
	
	
	/**
	 * 
	 * 方法描述:订单信息查询
	 * @param id 订单id
	 * @return Order  订单信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Order queryOrderByOrderNo(String orderNo) throws SPTException
	{
		return orderDao.queryOrderByOrderNo(orderNo);
	}
	
	
	
	/**
	 * 
	 * 方法描述:订单详细信息查看
	 * @param id 订单id
	 * @return OrderDetail  订单详细信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<OrderDetail> queryOrdersDetailById(String id,Page page) throws SPTException{
		return orderDao.queryOrdersDetailById(id,page);
	}
	
	
	


	/**
	 * 方法描述:查询订单明细
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	@Override
	public Map<String, Object> queryOrderDetailById(String id) throws SPTException 
	{
		
		Map<String, Object> orderDetail= orderDao.queryOrderDetailById(id);
		orderDetail.put("good_unit_str", SystemDict.getDict(SystemDict.UNIT,orderDetail.get("good_unit").toString()).getType_name());
		orderDetail.put("image", ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+orderDetail.get("image"));
		orderDetail.put("unit_price", NumUtils.subZeroAndDot(orderDetail.get("unit_price").toString()));
		return orderDetail;
	}
	
	
	

	/**
	 * 方法描述:提交订单
	 * @param 
	 * @return 
	 * date:2015年1月11日
	 * @author Taiyuan
	 */
	@Override
	public List<Order> submitOrder(List<Circle> circleList, Map<String,List<Map<String, Object>>> shopCartList,User user,String cartIds,Map<String, String> remarkMap,Order o) {
		List<Order> orderResult=new ArrayList<Order>();
		boolean flag = false;
		try {
			List<Map<String, Object>> shopList=null;
			Order order;
			List<OrderDetail> orderDetailList;
			OrderDetail orderDetail;
			//遍历订单所在的圈子
			for (Circle circle : circleList) 
			{
				orderDetailList=new ArrayList<OrderDetail>();
				//创建order对象
				order=(Order)o.clone();
				order.setOrderId(IDUtil.getID());
				order.setCircleId(Integer.parseInt(circle.getId()));
				order.setRemark(remarkMap.get(circle.getId()));
				order.setUserId(user.getId());
				order.setOrganizerId(circle.getCreateUserId());
				order.setIssueAddress(circle.getIssueAddress());
				order.setIssueTime(circle.getIssueTime());
				order.setEndTime(circle.getEndTime());
				order.setOrderNo(StringUtils.getOrderNo());
				//计算订单总价
				shopList=shopCartList.get(circle.getId());
				double orderTotal=0;
				int goodsAmount=0;
				for (Map<String, Object> map : shopList) 
				{
					int buy_num=Integer.parseInt(map.get("buy_num").toString());
					//订单信息
					double t=Double.parseDouble(map.get("total").toString());
					goodsAmount+=buy_num;
					orderTotal+=t;
					
					//订单明细信息
					orderDetail=new OrderDetail();
					orderDetail.setBuyNum(buy_num);
					orderDetail.setGoodId(map.get("good_id").toString());
					orderDetail.setGoodName(map.get("good_name").toString());
					orderDetail.setGoodTitle(map.get("title").toString());
					orderDetail.setGoodUnit(Integer.parseInt(map.get("unit").toString()));
					orderDetail.setUnitPrice(map.get("sell_price").toString());
					orderDetail.setTotal(Double.parseDouble(orderDetail.getUnitPrice())*orderDetail.getBuyNum());
					orderDetailList.add(orderDetail);
				}
				//订单总额
				order.setOrderAmount(orderTotal);
				//商品总数
				order.setGoodsAmount(goodsAmount);
				
				//保存订单
				flag=orderDao.saveOrder(order);
				if(flag)
				{
					//保存订单详情
					for(OrderDetail od:orderDetailList)
					{
						od.setOrderId(order.getOrderId());
						orderDao.saveOrderDetail(od);
						//更新商品购买次数
						goodDao.addBuyNum(od.getGoodId(),od.getBuyNum());
					}
				}
				orderResult.add(order);
			}
			
			//订单信息和订单详情信息保存结束, 删除购物车对应的数据
			if(!StringUtil.isEmpty(cartIds))
			{
				shopCartDAO.deleteShopCart(cartIds);
			}
			
		} catch (Exception e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return orderResult;
	}
	
	
	/**
	 * 方法描述:查询订单
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryOrderList(Page page, int userId) throws SPTException
	{
		List<Map<String, Object>> orderDetailList=orderDao.queryOrderList(page, userId);
		for (Map<String, Object> map : orderDetailList) 
		{
			map.put("ship_status_str", SystemDict.getDict(SystemDict.SHIPPING_STATUS, map.get("ship_status").toString()).getType_name());
			map.put("status_str", SystemDict.getDict(SystemDict.ORDER_STATUS, map.get("status").toString()).getType_name());
			map.put("pay_type_str", SystemDict.getDict(SystemDict.PAY_TYPE, map.get("pay_type").toString()).getType_name());
		}
		return orderDetailList;
	}
	
	
	/**
	 * 方法描述:查询订单
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public Map<String,List<Map<String, Object>>> queryOrderDetailList(String orderId) throws SPTException
	{
		List<Map<String, Object>> orderDetailList=orderDao.queryOrderDetailList(orderId);
		Map<String, List<Map<String, Object>>> orderDetailMap=new HashMap<String, List<Map<String,Object>>>();
		if(orderDetailList==null)
		{
			return orderDetailMap;
		}
		List<Map<String, Object>> tempList;
		String order_id;
		for (Map<String, Object> od : orderDetailList) 
		{
			order_id=od.get("order_id").toString();
			od.put("good_unit_str", SystemDict.getDict(SystemDict.UNIT, od.get("good_unit").toString()).getType_name());
			od.put("unit_price", NumUtils.subZeroAndDot(od.get("unit_price").toString()));
			od.put("total", NumUtils.subZeroAndDot(od.get("total").toString()));
			od.put("image", ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+od.get("image"));
			tempList=orderDetailMap.get(order_id)==null?new ArrayList<Map<String, Object>>():orderDetailMap.get(order_id);
			tempList.add(od);
			orderDetailMap.put(order_id, tempList);
		}
		return orderDetailMap;
	}

	/**
	 * 方法描述:设置付款状态
	 * @param 
	 * @return 
	 * date:2015年3月14日
	 * @author Taiyuan
	 */
	@Override
	public boolean setOrderPayStatus(int payStatus, String orderId,  String tradeNo) {
		try {
			return orderDao.setOrderPayStatus(payStatus, orderId, tradeNo);
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return false;
		}
	}
	
	/**
	 * 方法描述:确认收货
	 * @param 
	 * @return 
	 * date:2015年4月12日
	 * @author Taiyuan
	 */
	@Transactional
	public boolean reciveGood(String order_id,User user) throws SPTException
	{
		Order order=queryOrderById(order_id);
		boolean flag=false;
		//验证条件 发货状态：已发货 订单状态：交易中
		try {
			if(order.getShipStatus()==Integer.parseInt(SystemDict.SHIPPING_STATUS_YES) && order.getStatus()==Integer.parseInt(SystemDict.ORDER_STATUS_DEAL))
			{
				 flag= orderDao.reciveGood(order_id);
				//确认收货成功  发放农场主佣金和邀请用户提成
				if(flag)
				{
					double money=0;
					//发放给农场主
					CommissionHistory commissionHistory;
					try {
						//农场售卖佣金
						Circle cirlce=circleService.queryCircleByCircleId(order.getCircleId()+"");
						money = CommissionComputeFactory.getCommissionCompute(SystemDict.COMMISSION_TYPE_SELLER).compute(cirlce.getCreateUserId(), user.getId(), order.getOrderAmount());
						commissionHistory = new CommissionHistory(cirlce.getCreateUserId(), user.getId(), money, Integer.parseInt(SystemDict.COMMISSION_TYPE_SELLER), order.getCircleId(),  user.getId());
						commissionService.increaseUserCommission(commissionHistory);
					} catch (Exception e) {
						logger.error("异常,发放农场主佣金异常:"+money,e);
					}
					
					//30天内下单 邀请用户下单提成
					String orderTime=order.getOrderTime();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long orderMill=df.parse(orderTime).getTime();
					long nowMill=Calendar.getInstance().getTimeInMillis();
					long days = (nowMill-orderMill) / (1000 * 60 * 60 * 24);
					if(days<=30 && user.getInviteUserId()!=null && user.getInviteUserId()!=0)
					{
						try {
							money=CommissionComputeFactory.getCommissionCompute(SystemDict.COMMISSION_TYPE_ORDER).compute(user.getInviteUserId(), user.getId(), order.getOrderAmount());
							commissionHistory=new CommissionHistory(user.getInviteUserId(), user.getId(), money, Integer.parseInt(SystemDict.COMMISSION_TYPE_ORDER), order.getCircleId(),  user.getId());
							commissionService.increaseUserCommission(commissionHistory);
						} catch (Exception e) {
							logger.error("异常,发放邀请用户下单提成:"+money,e);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			flag=false;
		} 
		return flag;
		
	}
	
	/**
	 * 
	 * 方法描述:添加手机客户端订单
	 * @param order 订单信息
	 * @param orderDetailList 订单详细信息列表
	 * @throws SPTException
	 * date:2015-6-2
	 * @author wangfengtong
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addMOrder(Order order,List<OrderDetail> orderDetailList) throws SPTException{
		//保存订单
		boolean flag = orderDao.saveOrder(order);
		if(flag){
			List<Map<String,?>> paramListMap = new ArrayList<Map<String,?>>();
			for(OrderDetail od:orderDetailList){
				od.setOrderId(order.getOrderId());
				Map<String, Object> paramMap=new HashMap<String, Object>();
				paramMap.put("goodId", od.getGoodId());
				paramMap.put("buyNum", od.getBuyNum());
				paramListMap.add(paramMap);
			}
			
			//保存订单详情
			orderDao.batchSaveOrderDetail(orderDetailList);
			
			//更新商品购买次数
			goodDao.batchAddBuyNum(paramListMap);
		}
	}
	
	/**
	 * 
	 * 方法描述:根据订单id查询订单
	 * @param orderId 订单id
	 * @return 订单详细信息列表
	 * @throws SPTException
	 * date:2015-6-3
	 * @author wangfengtong
	 */
	public List<Map<String, Object>> queryMOrderDetailList(String orderId) throws SPTException{
		return orderDao.queryOrderDetailList(orderId);
	}
}
