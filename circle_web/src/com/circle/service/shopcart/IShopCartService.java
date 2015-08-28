package com.circle.service.shopcart;

import java.util.List;
import java.util.Map;

import com.circle.pojo.shopcart.ShopCart;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 购物车接口服务. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月5日 下午9:20:36
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public interface IShopCartService {
	
	/**
	 * 方法描述:加入购物车
	 * @param shopCart 购物车对象
	 * @return 是否添加成功
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	public boolean addShopCart(ShopCart shopCart);
	
	/**
	 * 方法描述:查询购物车商品数量
	 * @param 
	 * @return 
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	public int countShopCart(int userId) throws SPTException; 
	
	/**
	 * 方法描述:查询商品购物车列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryShopCartList(Page page,int userId ) throws SPTException;
	
	
	/**
	 * 方法描述:查询勾选购物车的列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public Map<String, List<Map<String, Object>>> queryCalShopCartList(int userId,String calCartId) throws SPTException;
	
	
	/**
	 * 方法描述:删除购物车商品
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public boolean deleteShopCart(int cartId) throws SPTException;
	
	
	
	/**
	 * 方法描述:计算所选购物车商品总价
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public double calTotal(String[] cartId) throws SPTException;
	
	
	/**
	 * 方法描述:更新购物车数量
	 * @param 
	 * @return 
	 * date:2015年1月9日
	 * @author Taiyuan
	 */
	public boolean updateGoodNum(ShopCart shopCart) throws SPTException;
}
