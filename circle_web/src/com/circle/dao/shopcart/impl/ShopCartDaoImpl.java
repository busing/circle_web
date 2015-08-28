package com.circle.dao.shopcart.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.dao.shopcart.IShopCartDAO;
import com.circle.pojo.shopcart.ShopCart;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 购物车daoImpl
 * @author Taiyuan
 */
@Repository
public class ShopCartDaoImpl implements IShopCartDAO {
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	
	/**
	 * 加入购物车sql
	 * SAVE_SHOPCART_SQL 
	 */
	public static String SAVE_SHOPCART_SQL="INSERT INTO "+CircleTable.SHOPCART.getTableName()
						+" (good_id,circle_id,buy_num,unit_id,session_id,user_id,add_time,over_time,batch_id) values"
						+" (:good_id,:circle_id,:buy_num,:unit_id,:session_id,:user_id,now(),DATE_ADD(now(),INTERVAL 5 DAY),:batchId)";
	
	public static String QUERY_SHOPCART_GOOD_SQL="select cart_id from "+CircleTable.SHOPCART.getTableName()+" where circle_id=:circle_id and good_id=:good_id and user_id=:user_id";
	
	
	/**
	 * 添加购物车商品数量
	 * ADD_GOODNUM_SQL 
	 */
	public static String ADD_GOODNUM_SQL="update "+CircleTable.SHOPCART.getTableName()+" set buy_num=buy_num+:buy_num where cart_id=:cart_id";
	
	
	/**
	 * 更新购物车商品数量
	 * UPDATE_GOODNUM_SQL 
	 */
	public static String UPDATE_GOODNUM_SQL="update "+CircleTable.SHOPCART.getTableName()+" set buy_num=:buy_num where cart_id=:cart_id";
	
	/**
	 * 查询购物车商品总数
	 * QUERY_COUNT_SHOPCART_SQL 
	 */
	public static String QUERY_COUNT_SHOPCART_SQL="select count(1) from "+CircleTable.SHOPCART.getTableName()+" where user_id=:userId and over_time>now()";

	
	/**
	 * 删除购物车商品
	 * DELETE_SHOPCART_SQL 
	 */
	public static String DELETE_SHOPCART_SQL="delete from "+CircleTable.SHOPCART.getTableName();
	
	
	
	/**
	 * 查询购物车列表
	 * QUERY_SHOPCART_LIST_SQL 
	 */
	public static String QUERY_SHOPCART_LIST_SQL="select s.cart_id,s.batch_id,c.name,c.id circle_id,g.good_name,g.title,g.image,g.unit,g.id good_id,g.sell_price, s.buy_num, g.sell_price*s.buy_num total from "
					+CircleTable.SHOPCART.getTableName()+" s ,"+CircleTable.CIRCLE.getTableName()+" c,"+CircleTable.GOODS.getTableName()+" g"
					+" where s.good_id=g.id and c.id=s.circle_id and g.status=1 and g.is_putaway=1 and s.user_id=:userId order by c.id ";
	
	
	/**
	 * 查询结算购物车列表
	 * QUERY_CAL_SHOPCART_LIST_SQL 
	 */
	public static String QUERY_CAL_SHOPCART_LIST_SQL="select s.cart_id,g.good_name,g.title,g.image,g.unit,g.id good_id,g.sell_price,s.circle_id, s.buy_num, g.sell_price*s.buy_num total,s.batch_id from "
					+CircleTable.SHOPCART.getTableName()+" s ,"+CircleTable.GOODS.getTableName()+" g"
					+" where s.good_id=g.id and g.status=1 and g.is_putaway=1 and s.user_id=:userId ";
	
	/**
	 * 计算购物车总价
	 * TOTAL_SHOPCART 
	 */
	public static String TOTAL_SHOPCART="select sum(s.buy_num*g.sell_price) total from "+CircleTable.SHOPCART.getTableName()+" s,"
					+CircleTable.GOODS.getTableName()+" g "+ "where g.id=s.good_id ";
	
	/**
	 * 方法描述:加入购物车
	 * @param shopCart 购物车对象
	 * @return 是否添加成功
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	public boolean addShopCart(ShopCart shopCart)  throws SPTException
	{
		Map<String, Object> paramMap = consShopParam(shopCart);
		return commonDao.update(SAVE_SHOPCART_SQL, paramMap)>0?true:false;
	}


	private Map<String, Object> consShopParam(ShopCart shopCart) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("good_id", shopCart.getGoodId());
		paramMap.put("circle_id", shopCart.getCartId());
		paramMap.put("buy_num", shopCart.getBuyNum());
		paramMap.put("unit_id", shopCart.getUnitId());
		paramMap.put("session_id", shopCart.getSessionId());
		paramMap.put("user_id", shopCart.getUserId());
		paramMap.put("circle_id", shopCart.getCircleId());
		paramMap.put("cart_id", shopCart.getCartId());
		paramMap.put("batchId", shopCart.getBatchId());
		return paramMap;
	}
	
	
	/**
	 * 方法描述:查询商品是否已经存在购物车中
	 * @param 
	 * @return 
	 * date:2015年1月6日
	 * @author Taiyuan
	 */
	public int queryShopCartGood(ShopCart shopCart) throws SPTException
	{
		Map<String, Object> paramMap = consShopParam(shopCart);
		Map<String, Object> result= commonDao.queryForMap(QUERY_SHOPCART_GOOD_SQL, paramMap);
		return result==null?0:Integer.parseInt(result.get("cart_id").toString());
	}
	
	/**
	 * 方法描述:更新购物车的商品数量
	 * @param 
	 * @return 
	 * date:2015年1月6日
	 * @author Taiyuan
	 */
	public boolean updateGoodNum(ShopCart shopCart) throws SPTException
	{
		Map<String, Object> paramMap = consShopParam(shopCart);
		return commonDao.update(UPDATE_GOODNUM_SQL, paramMap)==1?true:false;
	}

	
	
	
	@Override
	public boolean addGoodNum(ShopCart shopCart) throws SPTException {
		Map<String, Object> paramMap = consShopParam(shopCart);
		return commonDao.update(ADD_GOODNUM_SQL, paramMap)==1?true:false;
	}


	/**
	 * 方法描述:查询购物车商品数量
	 * @param 
	 * @return 
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	@Override
	public int countShopCart(int userId) throws SPTException 
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("userId", userId);
		return commonDao.queryForInt(QUERY_COUNT_SHOPCART_SQL, paramMap);
	}


	/**
	 * 方法描述:查询商品购物车列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@Override
	public List<Map<String, Object>> queryShopCartList(Page page,int userId) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		return commonDao.queryForList(QUERY_SHOPCART_LIST_SQL, paramsMap, page);
	}
	
	/**
	 * 方法描述:查询勾选结算的商品购物车列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@Override
	public List<Map<String, Object>> queryCalShopCartList(int userId,String calCartId) throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("calCartId", calCartId);
		StringBuilder sb=new StringBuilder(QUERY_CAL_SHOPCART_LIST_SQL);
		sb.append("and s.cart_id in (");
		sb.append(calCartId);
		sb.append(") order by s.circle_id ");
		return commonDao.queryForList(sb.toString(),paramsMap);
	}
	
	


	/**
	 * 方法描述:删除购物车商品
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@Override
	public boolean deleteShopCart(String cartId) throws SPTException {
		StringBuilder sb=new StringBuilder(DELETE_SHOPCART_SQL);
		sb.append(" where cart_id in("+cartId+")");
		return commonDao.update(sb.toString(),null)>0?true:false;
	}
	


	/**
	 * 方法描述:计算所选购物车商品总价
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@Override
	public double calTotal(String[] cartId) throws SPTException 
	{
		String cartIdStr=joinString(cartId, ",");
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("cartId", cartIdStr);
		StringBuilder sb=new StringBuilder(TOTAL_SHOPCART);
		sb.append(" and s.cart_id in ("+cartIdStr+")");
		Map<String, Object> result= commonDao.queryForMap(sb.toString(), paramMap);
		if(result==null)
		{
			return 0;
		}
		double d=Double.parseDouble(result.get("total").toString());
		return d;
	}
	
	
	
	
	private String joinString(String[] str,String joinStr)
	{
		StringBuilder sb=new StringBuilder();
		for (String string : str) 
		{
			sb.append(string);
			sb.append(joinStr);
		}
		if(sb.length()>0)
		{
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}


	
	
}
