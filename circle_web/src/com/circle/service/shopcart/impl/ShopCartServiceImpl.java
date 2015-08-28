package com.circle.service.shopcart.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circle.constant.SystemDict;
import com.circle.dao.shopcart.IShopCartDAO;
import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.shopcart.ShopCart;
import com.circle.service.batchsell.IBatchSellService;
import com.circle.service.shopcart.IShopCartService;
import com.circle.utils.NumUtils;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.ProUtil;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月5日 下午9:26:43
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Service
@Transactional
public class ShopCartServiceImpl implements IShopCartService {

	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(ShopCartServiceImpl.class) ;
	
	/**
	 * 购物车dao
	 * shopCartDao 
	 */
	@Resource
	IShopCartDAO shopCartDao;
	
	/**
	 * 注入售卖批次service
	 */
	@Resource
	IBatchSellService batchSellService;
	
	/**
	 * 方法描述:加入购物车
	 * @param 
	 * @return 
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	@Override
	public boolean addShopCart(ShopCart shopCart) {
		try {
			boolean flag=false;
			
			int cart_id=shopCartDao.queryShopCartGood(shopCart);
			if(cart_id!=0)
			{
				shopCart.setCartId(cart_id);
				flag=shopCartDao.addGoodNum(shopCart);
			}
			else
			{
				flag=shopCartDao.addShopCart(shopCart);
			}
			return flag;
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return false;
		}
	}

	/**
	 * 方法描述:查询购物车总数
	 * @param 
	 * @return 
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	@Override
	public int countShopCart(int userId) throws SPTException
	{
		return shopCartDao.countShopCart(userId);
	}
	
	/**
	 * 方法描述:查询商品购物车列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryShopCartList(Page page,int userId) throws SPTException
	{
		List<Map<String, Object>> cartList= shopCartDao.queryShopCartList(page,userId);
		convertShopCartData(cartList);
		return cartList;
	}
	
	/**
	 * 方法描述:查询勾选购物车的列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public Map<String, List<Map<String, Object>>> queryCalShopCartList(int userId,String calCartId) throws SPTException
	{
		//当前售卖批次数据
		BatchSell batchSell=batchSellService.queryCurrentBatchSell();
		Map<String, List<Map<String, Object>>> data=new HashMap<String, List<Map<String,Object>>>();
		List<Map<String, Object>> cartList= shopCartDao.queryCalShopCartList(userId,calCartId);
		convertShopCartData(cartList);
		
		String circle_id;
		List<Map<String, Object>> tempList;
		for (Map<String, Object> map : cartList) 
		{
			//去掉当前批次不售卖的商品数据
			if(batchSell==null || !(batchSell.getId()+"").equals(map.get("batch_id")+""))
			{
				continue;
			}
			circle_id=map.get("circle_id").toString();
			tempList=data.get(circle_id)==null?new ArrayList<Map<String,Object>>():data.get(circle_id);
			tempList.add(map);
			data.put(circle_id, tempList);
		}
		return data;
	}

	/**
	 * 方法描述:转化购物车数据
	 * @param 
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	private void convertShopCartData(List<Map<String, Object>> cartList) 
	{
		String sell_price;
		String total;
		for (Map<String, Object> map : cartList)
		{
			sell_price=map.get("sell_price").toString();
			total=map.get("total").toString();
			map.put("image",ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+map.get("image"));
			map.put("unit_str",SystemDict.getDict(SystemDict.UNIT, map.get("unit").toString()).getType_name());
			map.put("sell_price",NumUtils.subZeroAndDot(sell_price));
			map.put("total",NumUtils.subZeroAndDot(total));
		}
	}
	
	
	/**
	 * 方法描述:删除购物车商品
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public boolean deleteShopCart(int cartId) throws SPTException
	{
		return shopCartDao.deleteShopCart(cartId+"");
	}
	
	
	
	/**
	 * 方法描述:计算所选购物车商品总价
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public double calTotal(String[] cartId) throws SPTException
	{
		return shopCartDao.calTotal(cartId);
	}

	@Override
	public boolean updateGoodNum(ShopCart shopCart) throws SPTException {
		return shopCartDao.updateGoodNum(shopCart);
	}

	
}
