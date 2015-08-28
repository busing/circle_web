package com.circle.dao.shopcart;

import java.util.List;
import java.util.Map;

import com.circle.pojo.shopcart.ShopCart;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;


/**
 * 购物车dao
 * @author Taiyuan
 */
public interface IShopCartDAO {
	
	/**
	 * 方法描述:加入购物车
	 * @param shopCart 购物车对象
	 * @return 是否添加成功
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	public boolean addShopCart(ShopCart shopCart)  throws SPTException;
	
	/**
	 * 方法描述:查询购物车商品数量
	 * @param 
	 * @return 
	 * date:2015年1月5日
	 * @author Taiyuan
	 */
	public int countShopCart(int userId) throws SPTException; 
	
	/**
	 * 方法描述:查询商品是否已经存在购物车中
	 * @param 
	 * @return 
	 * date:2015年1月6日
	 * @author Taiyuan
	 */
	public int queryShopCartGood(ShopCart shopCart) throws SPTException;
	
	/**
	 * 方法描述:更新购物车的商品数量
	 * @param 
	 * @return 
	 * date:2015年1月6日
	 * @author Taiyuan
	 */
	public boolean updateGoodNum(ShopCart shopCart) throws SPTException;
	
	
	/**
	 * 方法描述:添加购物车的商品数量
	 * @param 
	 * @return 
	 * date:2015年1月6日
	 * @author Taiyuan
	 */
	public boolean addGoodNum(ShopCart shopCart) throws SPTException;
	
	
	
	/**
	 * 方法描述:查询商品购物车列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryShopCartList(Page page, int userId) throws SPTException;
	
	
	/**
	 * 方法描述:查询勾选购物车的列表
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> queryCalShopCartList(int userId,String calCartId) throws SPTException;
	
	
	/**
	 * 方法描述:删除购物车商品
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public boolean deleteShopCart(String cartId) throws SPTException;
	
	
	
	
	/**
	 * 方法描述:计算所选购物车商品总价
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	public double calTotal(String[] cartId) throws SPTException;
	
	
	
}
