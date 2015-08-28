package com.circle.dao.order.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.constant.SystemDict;
import com.circle.dao.order.IOrderDao;
import com.circle.pojo.order.Order;
import com.circle.pojo.order.OrderDetail;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 
 * 订单DAO接口实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月23日 15:44:55
 * <p>
 * Company: Cooper's Summber
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月13日 下午8:31:19
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Repository
public class OrderDaoImpl implements IOrderDao {

	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(OrderDaoImpl.class) ;
	
	/**
	 * 查询订单列表信息
	 */
	public static final String QUERY_ORDER_LIST = "select t.order_id,t.userid,t.order_no,t.status, t.pay_status,t.ship_status,t.circle_id,t.organizer_id,t.order_time,t.issue_time,t.issue_address,t.payment_name,t.pay_no,t.goods_amount,t.order_amount,t.complete_time,t.sign_time,t.sign_name,t.ship_time,t.remark,t.admin_remark,tu.name userName,tc.name circleName from t_order t left join t_circle tc on t.circle_id = tc.id left join t_user tu on tu.id = t.userid where 1=1";

	/**
	 * 查询单条订单信息
	 */
	public static final String QUERY_ORDER_BY_ID = "select t.order_id,t.receive_name receiveName,t.receive_phone receivePhone,t.pay_type payType,t.userid,t.order_no,t.status, t.pay_status,t.ship_status,t.circle_id,t.organizer_id,t.order_time,t.issue_time,t.issue_address,t.payment_name,t.pay_no,t.goods_amount,t.order_amount,t.complete_time,t.sign_time,t.sign_name,t.ship_time,t.remark,t.admin_remark,tu.name userName,tc.name circleName,(SELECT name  from t_user t2 where t2.id = t.organizer_id) organizerName from t_order t left join t_circle tc on t.circle_id = tc.id left join t_user tu on tu.id = t.userid where 1=1 and t.order_id =:orderId ";
	
	
	/**
	 * 查询单条订单信息
	 */
	public static final String QUERY_ORDER_BY_ORDERNO = "select t.order_id,t.userid,t.order_no,t.status, t.pay_status,t.ship_status,t.circle_id,t.organizer_id,t.order_time,t.issue_time,t.issue_address,t.payment_name,t.pay_no,t.goods_amount,t.order_amount,t.complete_time,t.sign_time,t.sign_name,t.ship_time,t.remark,t.admin_remark,tu.name userName,tc.name circleName,(SELECT name  from t_user t2 where t2.id = t.organizer_id) organizerName from t_order t left join t_circle tc on t.circle_id = tc.id left join t_user tu on tu.id = t.userid where 1=1 and t.order_no =:orderNo ";

	/**
	 * 查询订单详情
	 */
	public static final String QUERY_ORDERDETAIL_BY_ORDERID  ="select t.id,t.good_id,t.order_id,t.unit_price,t.buy_num,t.total,tg.good_name from t_order_detail t LEFT JOIN t_goods tg on tg.id = t.good_id where order_id = :orderId";

	
	/**
	 * 订单明细
	 * QUERY_ORDERDETAIL_BY_ID 
	 */
	public static final String QUERY_ORDERDETAIL_BY_ID  ="select t.id,t.good_id,t.order_id,t.unit_price,tg.buy_num,t.total,t.good_name,t.good_title,t.good_unit,date_format(o.order_time,'%Y年%m月%d日') order_time,tg.image,o.circle_id from"
					+ " t_order_detail t LEFT JOIN t_goods tg on tg.id = t.good_id left join t_order o on o.order_id=t.order_id where t.id = :id";
	
	/**
	 * 保存订单
	 * SAVE_ORDER_SQL 
	 */
	public static final String SAVE_ORDER_SQL="insert into "+CircleTable.ORDER.getTableName()+" (order_id,userid,order_no,status,pay_status,ship_status,circle_id,organizer_id,"
								+" order_time,issue_address,issue_time,end_time,goods_amount,order_amount,remark,receive_name,receive_phone,ship_type,pay_type,batch_id) values(:orderId,:userId,:orderNo,"+SystemDict.ORDER_STATUS_SUCCESS+",0,"+SystemDict.SHIPPING_STATUS_PREPARE+","
								+ ":circleId,:organizerId,now(),:issueAddress,:issueTime,:endTime,:goodsAmount,:orderAmount,:remark,:receiveName,:receivePhone,:shipType,:payType,:batchId)";
	
	/**
	 * 保存订单明细 
	 * SAVE_ORDER_DETAIL_SQL 
	 */
	public static final String SAVE_ORDER_DETAIL_SQL="insert into "+CircleTable.ORDER_DETAIL.getTableName()+" (order_id,good_name,good_title,"
								+"good_unit,good_id,unit_price,buy_num,total) values(:orderId,:goodName,:goodTitle,:goodUnit,:goodId,:unitPrice,:buyNum,:total)";
	
	/**
	 * 查询订单列表
	 * QUERY_ORDER_LIST_SQL 
	 */
	public static final String QUERY_ORDER_LIST_SQL="select o.order_id,o.order_no,o.status,o.pay_status,o.ship_status,o.comment_status,o.circle_id,o.issue_address,date_format(o.issue_time,'%Y-%m-%d %H:%i:%s') issue_time,"
						+ "c.name circle_name,date_format(o.order_time,'%Y-%m-%d') order_time,o.order_time orderTime,receive_name,receive_phone,ship_type,pay_type,"
						+" o.payment_id,o.payment_name,o.pay_no,o.goods_amount,o.order_amount,o.complete_time,o.remark,date_format(o.ship_time,'%Y-%m-%d %H:%i:%s') ship_time,u.name,u.mobilephone,"
						+" date_format(o.sign_time,'%Y-%m-%d %H:%i:%s') sign_time,o.sign_name from "+CircleTable.ORDER.getTableName()+" o,"+CircleTable.CIRCLE.getTableName()+" c,"
						+ CircleTable.USER.getTableName()+" u"
						+" where c.id=o.circle_id and c.create_userid=u.id and o.userid=:userid ";
	
	/**
	 * 查询订单明细列表
	 * QUERY_ORDERDETAIL_LIST_SQL 
	 */
	public static final String QUERY_ORDERDETAIL_LIST_SQL="select o.id,o.order_id,o.good_name,g.image,o.good_title,o.good_unit,o.good_id,o.unit_price,o.buy_num,o.total from "+CircleTable.ORDER_DETAIL.getTableName()
						+" o,"+CircleTable.GOODS.getTableName()+" g"
						+"  where g.id=o.good_id ";
	
	
	/**
	 * 更新货物付款状态
	 * UPDATE_ORDER_PAY_STATUS 
	 */
	public static final String UPDATE_ORDER_PAY_STATUS="update "+CircleTable.ORDER.getTableName()+" set pay_status=:payStatus , pay_no=:payNo where order_no=:orderNo";
	
	
	/**
	 * 确认收货sql
	 * RECIVEGOOD＿SQL 
	 */
	public static final String RECIVEGOOD＿SQL="update t_order set status=:status,ship_status=:shipStatus where order_id=:orderId";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	/**
	 * 
	 * 方法描述:查询订单列表
	 * @param page 分页查询控件
	 * @return List<Order> 订单列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<Order> queryOrderList(Page page) throws SPTException {
		logger.debug("[OderDaoImpl.queryOrderList] start...") ;
		StringBuilder sql = new StringBuilder(QUERY_ORDER_LIST);
		if(page != null && page.getSearchParam() != null){
			if(page.getSearchParam().containsKey("orderNo") && !StringUtil.isEmpty(page.getSearchParam().get("orderNo"))){
				sql.append(" and t.status = :orderNo");
			}
			if(page.getSearchParam().containsKey("circleName") && !StringUtil.isEmpty(page.getSearchParam().get("circleName"))){
				sql.append(" and  tc.name like '%" + page.getSearchParam().get("circleName") + "%'");
			}
			if(page.getSearchParam().containsKey("userName") && !StringUtil.isEmpty(page.getSearchParam().get("userName"))){
				sql.append(" and  tu.name like '%" + page.getSearchParam().get("userName") + "%'");
			}
			if(page.getSearchParam().containsKey("status") && !StringUtil.isEmpty(page.getSearchParam().get("status"))){
				sql.append(" and t.status = :status");
			}
			if(page.getSearchParam().containsKey("beginTime") && !StringUtil.isEmpty(page.getSearchParam().get("beginTime"))){
				sql.append(" and t.order_time >= :beginTime");
			}
			if(page.getSearchParam().containsKey("endTime") && !StringUtil.isEmpty(page.getSearchParam().get("endTime"))){
				sql.append(" and t.order_time <= :endTime");
			}
		}
		List<Order> orderList  = commonDao.queryForList(sql.toString(), page.getSearchParam(), page, Order.class);
		logger.debug("[OderDaoImpl.queryOrderList] end...") ;
		return orderList;
	}

	/**
	 * 
	 * 方法描述:订单信息查看
	 * @param id 订单id
	 * @return Order  订单信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public Order queryOrderById(String id) throws SPTException {
		logger.debug("[OderDaoImpl.queryOrderById] start...") ;
		
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("orderId", id) ;
		Order order = commonDao.queryForObject(QUERY_ORDER_BY_ID, paramMap, Order.class) ;
		logger.debug("[OderDaoImpl.queryOrderById] end...") ;
		
		return order;
	}
	
	
	
	
	
	@Override
	public Order queryOrderByOrderNo(String orderNo)  throws SPTException 
	{
		logger.debug("[OderDaoImpl.queryOrderById] start...") ;
		
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("orderNo", orderNo) ;
		Order order = commonDao.queryForObject(QUERY_ORDER_BY_ORDERNO, paramMap, Order.class) ;
		logger.debug("[OderDaoImpl.queryOrderById] end...") ;
		return order;
	}

	/**
	 * 
	 * 方法描述:订单详细信息查看
	 * @param id 订单id
	 * @return OrderDetail  订单详细信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<OrderDetail> queryOrdersDetailById(String id,Page page) throws SPTException {
		logger.debug("[OderDaoImpl.queryOrderDetailById] start...") ;
		
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("orderId", id) ;
		page.setSearchParam(paramMap);
		
		List<OrderDetail> orderDetailList  = commonDao.queryForList(QUERY_ORDERDETAIL_BY_ORDERID, page.getSearchParam(),page,OrderDetail.class);
		logger.debug("[OderDaoImpl.queryOrdersDetailById] end...") ;
		
		return orderDetailList;
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
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("id", id);
		return commonDao.queryForMap(QUERY_ORDERDETAIL_BY_ID, paramMap);
	}

	/**
	 * 方法描述:保存订单
	 * @param 
	 * @return 
	 * date:2015年1月11日
	 * @author Taiyuan
	 */
	@Override
	public boolean saveOrder(Order order) throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("orderId", order.getOrderId());
		paramMap.put("circleId", order.getCircleId());
		paramMap.put("organizerId", order.getOrganizerId());
		paramMap.put("issueAddress", order.getIssueAddress());
		paramMap.put("issueTime", order.getIssueTime());
		paramMap.put("endTime", order.getEndTime());
		paramMap.put("goodsAmount", order.getGoodsAmount());
		paramMap.put("orderAmount", order.getOrderAmount());
		paramMap.put("remark", order.getRemark());
		paramMap.put("userId", order.getUserId());
		paramMap.put("orderNo", order.getOrderNo());
		paramMap.put("receiveName", order.getReceiveName());
		paramMap.put("receivePhone", order.getReceivePhone());
		paramMap.put("shipType", order.getShipType());
		paramMap.put("payType", order.getPayType());
		paramMap.put("batchId", order.getBatchId());
		boolean flag= commonDao.update(SAVE_ORDER_SQL, paramMap)>0?true:false;
		return flag;
	}
	
	

	/**
	 * 方法描述:保存订单明细
	 * @param 
	 * @return 
	 * date:2015年1月11日
	 * @author Taiyuan
	 */
	@Override
	public boolean saveOrderDetail(OrderDetail orderDetail)  throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("orderId", orderDetail.getOrderId());
		paramMap.put("goodName", orderDetail.getGoodName());
		paramMap.put("goodTitle", orderDetail.getGoodTitle());
		paramMap.put("goodUnit", orderDetail.getGoodUnit());
		paramMap.put("goodId", orderDetail.getGoodId());
		paramMap.put("unitPrice", orderDetail.getUnitPrice());
		paramMap.put("buyNum", orderDetail.getBuyNum());
		paramMap.put("total", orderDetail.getTotal());
		boolean flag= commonDao.update(SAVE_ORDER_DETAIL_SQL, paramMap)>0?true:false;
//		int orderId=commonDao.getLastId(CircleTable.ORDER.getTableName(), "order_id");
//		orderDetail.setOrderId(orderId);
		return flag;
	}
	
	/**
	 * 
	 * 方法描述:批量添加订单详细信息
	 * @param orderDetailList 订单详细信息列表
	 * @throws SPTException 
	 * date:2015-6-2
	 * @author wangfengtong
	 */
	public void batchSaveOrderDetail(List<OrderDetail> orderDetailList) throws SPTException{
		List<Map<String,?>> paramListMap = new ArrayList<Map<String,?>>();
		
		for (OrderDetail orderDetail : orderDetailList) {
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("orderId", orderDetail.getOrderId());
			paramMap.put("goodName", orderDetail.getGoodName());
			paramMap.put("goodTitle", orderDetail.getGoodTitle());
			paramMap.put("goodUnit", orderDetail.getGoodUnit());
			paramMap.put("goodId", orderDetail.getGoodId());
			paramMap.put("unitPrice", orderDetail.getUnitPrice());
			paramMap.put("buyNum", orderDetail.getBuyNum());
			paramMap.put("total", orderDetail.getTotal());
			paramListMap.add(paramMap);
		}
		commonDao.batchUpdate(SAVE_ORDER_DETAIL_SQL, paramListMap);
	}

	/**
	 * 方法描述:查询订单列表
	 * @param 
	 * @return 
	 * date:2015年1月13日
	 * @author Taiyuan
	 */
	@Override
	public List<Map<String, Object>> queryOrderList(Page page, int userId) throws SPTException 
	{
		StringBuilder sb=new StringBuilder(QUERY_ORDER_LIST_SQL);
		page.getSearchParam().put("userid", userId+"");
		if(page != null && page.getSearchParam() != null)
		{
			if(page.getSearchParam().containsKey("ship_status") && !StringUtil.isEmpty(page.getSearchParam().get("ship_status")))
			{
				sb.append(" and o.ship_status=:ship_status ");
			}
			if(page.getSearchParam().containsKey("status") && !StringUtil.isEmpty(page.getSearchParam().get("status")))
			{
				sb.append(" and o.status=:status ");
			}
			if(page.getSearchParam().containsKey("commentStatus") && !StringUtil.isEmpty(page.getSearchParam().get("commentStatus")))
			{
				if(page.getSearchParam().get("commentStatus").equals("0"))
				{
					sb.append(" and o.comment_status is null ");
				}
				else
				{
					sb.append(" and o.comment_status=:commentStatus ");
				}
			}
		}
		sb.append(" order by orderTime desc ");
		return commonDao.queryForList(sb.toString(), page.getSearchParam(),page);
	}
	
	/**
	 * 方法描述:查询订明细列表
	 * @param 
	 * @return 
	 * date:2015年1月13日
	 * @author Taiyuan
	 */
	@Override
	public List<Map<String, Object>> queryOrderDetailList( String orderId) throws SPTException 
	{
		if(StringUtil.isEmpty(orderId))
		{
			return null;
		}
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("orderId", orderId);
		StringBuilder sb=new StringBuilder(QUERY_ORDERDETAIL_LIST_SQL);
		sb.append(" and o.order_id in("+orderId+")");
		return commonDao.queryForList(sb.toString());
	}

	/**
	 * 方法描述:设置订单付款状态
	 * @param 
	 * @return 
	 * date:2015年3月14日
	 * @author Taiyuan
	 */
	@Override
	public boolean setOrderPayStatus(int payStatus,String orderId, String tradeNo) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("payStatus", payStatus);
		paramsMap.put("payNo", tradeNo);
		paramsMap.put("orderNo", orderId);
		return commonDao.update(UPDATE_ORDER_PAY_STATUS, paramsMap )>0?true:false;
	}
	
	
	/**
	 * 方法描述:确认收货
	 * @param 
	 * @return 
	 * date:2015年4月12日
	 * @author Taiyuan
	 */
	public boolean reciveGood(String order_id) throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("shipStatus", SystemDict.SHIPPING_STATUS_SIGN);
		paramMap.put("status", SystemDict.ORDER_STATUS_COMPLETE);
		paramMap.put("orderId", order_id);
		return commonDao.update(RECIVEGOOD＿SQL, paramMap)>0?true:false;
	}
	
	
	
}
