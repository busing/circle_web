package com.circle.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.circle.constant.SystemDict;
import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.dict.DictBean;
import com.circle.pojo.order.Order;
import com.circle.pojo.order.OrderDetail;
import com.circle.pojo.user.User;
import com.circle.service.batchsell.IBatchSellService;
import com.circle.service.circle.ICircleService;
import com.circle.service.good.IGoodService;
import com.circle.service.order.IOrderService;
import com.circle.service.receive.IReceiveInfoService;
import com.circle.service.shopcart.IShopCartService;
import com.circle.thread.JoinCircleThread;
import com.circle.utils.IDUtil;
import com.circle.utils.StringUtils;
import com.circle.utils.SystemExceptionUtil;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.StringUtil;

/**
 * <br>
 * 订单controller
 * <p>
 * Copyright: Copyright (c) 2015年1月10日 下午9:04:01
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Controller
@RequestMapping("order")
public class OrderController {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	/**
	 * 注入购物车service
	 */
	@Resource
	IShopCartService shopCartService;
	
	/**
	 * 注入圈子（农场）service
	 */
	@Resource
	ICircleService circleService;
	

	/**
	 * 注入订单service
	 */
	@Resource
	IOrderService orderService;
	
	/**
	 * 注入商品service
	 */
	@Resource
	IGoodService goodService;
	
	/**
	 * 注入收货信息和支付方式service
	 */
	@Resource
	IReceiveInfoService receiveInfoService;

	/**
	 * 注入售卖批次service
	 */
	@Resource
	IBatchSellService batchSellService;
	
	/**
	 * 方法描述:购物车结算订单
	 * @param 
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	@RequestMapping("calOrder.action")
	public ModelAndView calOrder(ModelAndView mav, HttpServletRequest request, String calCartId)
	{
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			//购物车数据
			Map<String,List<Map<String, Object>>> orderList=shopCartService.queryCalShopCartList(userId, calCartId);
			StringBuilder sb=new StringBuilder();
			for (Entry<String, List<Map<String, Object>>> e : orderList.entrySet()) 
			{
				sb.append(e.getKey()).append(",");
			}
			if(sb.length()>0)
			{
				sb.deleteCharAt(sb.length()-1);
			}
			//购物车数据过期
			if(sb.length()==0)
			{
				mav.addObject("message", "购买失败,您购买的商品不在此次特卖范围之内。");
				mav.setViewName("/usercenter/orderList.action");
				return mav;
			}
			//圈子数据
			List<Circle> circleList=circleService.queryCircleListCircleId(sb.toString());
			
			//用户第一次下单，没有收货信息，保存默认收货人信息
			if(!receiveInfoService.isExistsReceiveInfo(userId))
			{
				receiveInfoService.saveDefaultReceiveInfo(user);
			}
			//收货人信息
			List<Map<String, Object>> receiveList=receiveInfoService.queryReceiveInfo(userId);
			
			//支付方式和配送方式
			Map<String, Object> pay_ship_type=receiveInfoService.queryPayAndShipInfo(userId);
			//end of 支付方式和配送方式
			
			List<DictBean> shipType= SystemDict.getDictList(SystemDict.SHIPPING_TYPE);
			List<DictBean> payType= SystemDict.getDictList(SystemDict.PAY_TYPE);
			mav.addObject("receiveList", receiveList);
			mav.addObject("pay_ship_type", pay_ship_type);
			mav.addObject("shipType", shipType);
			mav.addObject("payType", payType);
			
			double total=shopCartService.calTotal(calCartId.split(","));
			mav.setViewName("/order/order.jsp");
			mav.addObject("shopCartList", orderList);
			mav.addObject("circleList", circleList);
			mav.addObject("calCartId", calCartId);
			mav.addObject("goodId", 0);
			mav.addObject("circleId", 0);
			mav.addObject("buyNum", 0);
			mav.addObject("total", total);
			return mav;
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	/**
	 * 方法描述:商品直接下单
	 * @param 
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	@RequestMapping("addOrder.action")
	public ModelAndView addOrder(ModelAndView mav,HttpServletRequest request,int goodId,String circleId,int buyNum)
	{
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			//判断用户是否在是改农场的成员
			if(circleService.queryCircleMemberByCircleIdAndUserId(circleId, userId+"")==0)
			{
				String msg="您不是该农场成员，请先加入该农场";
				mav.addObject("msg", msg);
				mav.setViewName("/circle/circleIndex.action?circleId="+circleId);
				return mav;
			}
			//当前售卖批次数据
			BatchSell batchSell=batchSellService.queryCurrentBatchSell();
			if(batchSell==null)
			{
				mav.addObject("message", "当前特卖已经结束，您下手晚啦");
				mav.setViewName("/usercenter/orderList.action");
				return mav;
			}
			String currentSellGoodsId=batchSellService.queryCurrentSellGoodsId(batchSell.getId());
			if(batchSell==null || currentSellGoodsId.indexOf(goodId+",")==-1 || batchSell.getRemainderSeconds()<=0)
			{
				mav.addObject("message", "购买失败,您购买的商品不在此次特卖范围之内。");
				mav.setViewName("/usercenter/orderList.action");
				return mav;
			}
			//圈子数据
			List<Circle> circleList=circleService.queryCircleListCircleId(circleId);
			Map<String,List<Map<String, Object>>> orderList=createTempOrder(goodId, circleId, buyNum,batchSell);
			
			//用户第一次下单，没有收货信息，保存默认收货人信息
			if(!receiveInfoService.isExistsReceiveInfo(userId))
			{
				receiveInfoService.saveDefaultReceiveInfo(user);
			}
			//收货人信息
			List<Map<String, Object>> receiveList=receiveInfoService.queryReceiveInfo(userId);
			
			//支付方式和配送方式
			Map<String, Object> pay_ship_type=receiveInfoService.queryPayAndShipInfo(userId);
			//end of 支付方式和配送方式
			
			List<DictBean> shipType= SystemDict.getDictList(SystemDict.SHIPPING_TYPE);
			List<DictBean> payType= SystemDict.getDictList(SystemDict.PAY_TYPE);
			mav.addObject("receiveList", receiveList);
			mav.addObject("pay_ship_type", pay_ship_type);
			mav.addObject("shipType", shipType);
			mav.addObject("payType", payType);
			
			
			//总价
			double sellPrice=Double.parseDouble(orderList.get(circleId).get(0).get("sell_price").toString());
			double total=sellPrice*buyNum;
			
			mav.setViewName("/order/order.jsp");
			mav.addObject("shopCartList", orderList);
			mav.addObject("circleList", circleList);
			mav.addObject("total", total);
			
			
			mav.addObject("goodId", goodId);
			mav.addObject("circleId", circleId);
			mav.addObject("buyNum", buyNum);
			
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	private Map<String,List<Map<String, Object>>> createTempOrder(int goodId,String circleId,int buyNum,BatchSell batchSell) throws SPTException
	{
		//购买的商品详情数据
		Map<String, Object> good = goodService.queryGood(goodId+"");
		
		//临时订单数据
		List<Map<String, Object>> order=new ArrayList<Map<String,Object>>();
		Map<String, Object> o=new HashMap<String, Object>();
		o.put("image", good.get("image"));
		o.put("title", good.get("title"));
		o.put("unit_str", good.get("unit_str"));
		o.put("unit", good.get("unit"));
		o.put("good_name", good.get("good_name"));
		o.put("good_id", good.get("id"));
		o.put("sell_price", good.get("sell_price"));
		o.put("buy_num",buyNum);
		o.put("batch_id", batchSell.getId());
		o.put("total",Double.parseDouble( good.get("sell_price").toString())*buyNum);
		order.add(o);
		
		//购物车数据
		Map<String,List<Map<String, Object>>> orderList=new HashMap<String, List<Map<String,Object>>>();
		orderList.put(circleId, order);
		
		return orderList;
	}
	
	
	/**
	 * 方法描述:确认提交订单
	 * @param 
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	@RequestMapping("submitOrder.action")
	public ModelAndView submitOrder(ModelAndView mav,HttpServletRequest request, String calCartId,String remarkInfo,String goodId,String circleId,String buyNum,Order order)
	{
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			
			//当前售卖批次数据
			BatchSell batchSell=batchSellService.queryCurrentBatchSell();
			if(batchSell==null)
			{
				mav.addObject("message", "当前特卖已经结束，您下手晚啦");
				mav.setViewName("/usercenter/orderList.action");
				return mav;
			}
			order.setBatchId(batchSell.getId());
			
			//购物车数据
			Map<String,List<Map<String, Object>>> orderList;
			if(StringUtil.isEmpty(calCartId))
			{
				
				String currentSellGoodsId=batchSellService.queryCurrentSellGoodsId(batchSell.getId());
				
				if(batchSell==null || currentSellGoodsId.indexOf(goodId+",")==-1 || batchSell.getRemainderSeconds()<=0)
				{
					mav.addObject("message", "购买失败,您购买的商品不在此次特卖范围之内。");
					mav.setViewName("/usercenter/orderList.action");
					return mav;
				}
				orderList=createTempOrder(Integer.parseInt(goodId), circleId, Integer.parseInt(buyNum),batchSell);
			}
			else
			{
				orderList=shopCartService.queryCalShopCartList(userId, calCartId);
			}
			StringBuilder circleIds=new StringBuilder();
			for (Entry<String, List<Map<String, Object>>> e : orderList.entrySet()) 
			{
				circleIds.append(e.getKey()).append(",");
			}
			if(circleIds.length()>0)
			{
				circleIds.deleteCharAt(circleIds.length()-1);
			}
			//圈子数据
			List<Circle> circleList=circleService.queryCircleListCircleId(circleIds.toString());
			JSONArray jsonRemark=JSONArray.fromObject(remarkInfo);
			Map<String, String> remarkMap=new HashMap<String, String>();
			for (Object object : jsonRemark) 
			{
				JSONObject jo=(JSONObject)object;
				remarkMap.put(jo.getString("circleId"), jo.getString("remark"));
			}
			
			//提交订单
			List<Order> orderResult=orderService.submitOrder(circleList, orderList, user, calCartId,remarkMap, order);
			StringBuilder orderNo=new StringBuilder();
			double orderTotal=0;
			String pay_type_str="";
			int pay_type=0;
			for (Order or : orderResult)
			{
				orderNo.append(or.getOrderNo()+" ");
				orderTotal+=or.getOrderAmount();
				pay_type=or.getPayType();
				pay_type_str=SystemDict.getDict(SystemDict.PAY_TYPE, or.getPayType()+"").getType_name() ;
			}
			
			mav.addObject("orderResult", orderResult);
			mav.addObject("orderTotal", orderTotal);
			mav.addObject("orderNo", orderNo.toString());
			mav.addObject("pay_type_str", pay_type_str);
			mav.addObject("pay_type", pay_type);
			
			mav.setViewName("/order/order_complete.jsp");
			return mav;
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	
	
	
	/**
	 * 方法描述:查询订单
	 * @param 
	 * @return 
	 * date:2015年1月13日
	 * @author Taiyuan
	 */
	public ModelAndView queryOrder(ModelAndView mav,Page page,HttpServletRequest request)
	{
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			List<Map<String, Object>> orderList=orderService.queryOrderList(page, userId);
			StringBuilder orderId=new StringBuilder();
			for (Map<String, Object> map : orderList)
			{
				orderId.append(map.get("order_id"));
				orderId.append(",");
			}
			orderId.deleteCharAt(orderId.length()-1);
			Map<String, List<Map<String, Object>>> orderDetailList=orderService.queryOrderDetailList(orderId.toString());
			
			mav.addObject("orderList", orderList);
			mav.addObject("orderDetailList", orderDetailList);
			mav.setViewName("/usercenter/order.jsp");
		} 
		catch (SPTException e)
		{
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	
	/**
	 * 方法描述:订单评论页面
	 * @param 
	 * @return 
	 * date:2015年1月18日
	 * @author Taiyuan
	 */
	@RequestMapping("orderComment.action")
	public ModelAndView orderComment(ModelAndView mav,OrderDetail orderDetail)
	{
		try {
			Map<String, Object> od=orderService.queryOrderDetailById(orderDetail.getId()+"");
			mav.addObject("od", od);
			mav.setViewName("/order/order_comment.jsp");
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	
	@RequestMapping("reciveGood.action")
	@ResponseBody
	public String reciveGood(String order_id,HttpServletRequest request)
	{
		JSONObject jsonResult=new JSONObject();
		boolean flag=false;
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			flag=orderService.reciveGood(order_id,user);
		} catch (NumberFormatException e) {
			flag=false;
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} catch (SPTException e) {
			flag=false;
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} 
		jsonResult.put("result", flag);
		return jsonResult.toString();
	}
	
	@ResponseBody
	@RequestMapping("morder.action")
	public JSONObject mOrder(ModelAndView mav,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Circle circle = (Circle) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.M_CIRCLE);
			System.out.println("get session circle:" + circle.toString());
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId= user.getId();
			
			//当前售卖批次数据
			BatchSell batchSell = batchSellService.queryCurrentBatchSell();
			if(batchSell==null){
				json.put("code", 0);
				json.put("message", "当前特卖已经结束，您下手晚啦");
				return json;
			}else{
				Order order = new Order();
				String orderId= IDUtil.getID();
				json.put("orderId", orderId);
				order.setOrderId(orderId);
				order.setUserId(userId);
				order.setOrderNo(StringUtils.getOrderNo());
				order.setCircleId(Integer.valueOf(circle.getId()));
				order.setOrganizerId(circle.getCreateUserId());
				order.setIssueAddress(circle.getIssueAddress());
				order.setIssueTime(circle.getIssueTime());
				order.setEndTime(circle.getEndTime());
				order.setRemark(ConstantBusiKeys.OrderKey.DEFAULT_REMARK);
				order.setReceiveName(user.getName());
				order.setReceivePhone(user.getMobilePhone());
				order.setShipType(ConstantBusiKeys.OrderKey.DEFAULT_SHIP_TYPE);
				order.setPayType(ConstantBusiKeys.OrderKey.DEFAULT_PAY_TYPE);
				order.setBatchId(batchSell.getId());
				
				double orderTotal=0;
				int goodsAmount=0;
				List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
				
				String goodIds = request.getParameter("goodIds");
				String goodNums = request.getParameter("goodNums");
				
				String[] ids = goodIds.split(",");
				String[] nums = goodNums.split(",");
				
				for (int i = 0;i < nums.length;i++) {
					if(Integer.parseInt(nums[i]) > 0){
						try {
							OrderDetail orderDetail = new OrderDetail();
							Map<String, Object> queryGood = goodService.queryGood(ids[i]);
							int buy_num=Integer.parseInt(nums[i]);
							double price = Double.parseDouble(queryGood.get("sell_price").toString());
							//订单信息
							double t=buy_num*price;
							goodsAmount+=buy_num;
							orderTotal+=t;
							
							//订单明细信息
							orderDetail=new OrderDetail();
							orderDetail.setBuyNum(buy_num);
							orderDetail.setGoodId(queryGood.get("id").toString());
							orderDetail.setGoodName(queryGood.get("good_name").toString());
							orderDetail.setGoodTitle(queryGood.get("title").toString());
							orderDetail.setGoodUnit(Integer.parseInt(queryGood.get("unit").toString()));
							orderDetail.setUnitPrice(price + "");
							orderDetail.setTotal(Double.parseDouble(orderDetail.getUnitPrice())*orderDetail.getBuyNum());
							orderDetail.setOrderId(order.getOrderId());
							orderDetailList.add(orderDetail);
							
						} catch (SPTException e) {
							logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
						}
					}
				}
				
				//订单总额
				order.setOrderAmount(orderTotal);
				//商品总数
				order.setGoodsAmount(goodsAmount);
				
				orderService.addMOrder(order, orderDetailList);
				
				json.put("code", 1);
				json.put("message", "下单成功");
				
				JoinCircleThread joinThread = new JoinCircleThread();
				joinThread.setCircleId(circle.getId());
				joinThread.setUser(user);
				System.out.println("set circleId:" + circle.getId());
				
				new Thread(joinThread).start();
			}
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			json.put("code", 0);
			json.put("message", "下单失败，请稍后再试！");
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("mqueryorder.action")
	public Map<String,Object> mQueryOrder(ModelAndView mav,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String orderId = request.getParameter("orderId");
			Order order = orderService.queryOrderById(orderId);
			List<Map<String, Object>> orderDetailList = orderService.queryMOrderDetailList("'" + orderId + "'");
			order.setOrderDetailList(orderDetailList);
			map.put("order", order);
			map.put("code", 1);
			map.put("message", "查询订单信息成功");
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			map.put("code", 0);
			map.put("message", "查询订单信息失败，请稍后再试！");
		}
		return map;
	}
}
