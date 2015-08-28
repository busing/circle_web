package com.circle.dao.good;

import java.util.List;
import java.util.Map;

import com.circle.pojo.good.GoodBean;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

public interface IGoodDao {
	/**
	 * 查询商品列表
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> queryGoodList(Page page)  throws SPTException ;
	
	/**
	 * 查询当前售卖批次的商品列表
	 * @param page
	 * @return
	 */
	public  List<Map<String, Object>> queryCurrentBatchGoodList(Page page,int batchId) throws SPTException;
	
	/**
	 * 查询商品
	 * @param id
	 * @return
	 */
	public Map<String, Object> queryGood(String id) throws SPTException;
	
	/**
	 * 查询商品类型参数值
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryGoodTypeArgValues(String id)  throws SPTException;
	
	/**
	 * 查询商品类型属性参数值
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryGoodTypeAttrValues(String id)  throws SPTException;
	
	/**
	 * 查询商品图片列表
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryImageList(String id)  throws SPTException;
	
	/**  
	 * 此方法描述的是：  查询商品评论信息
	 * @author: Taiyuan  
	 * @version: 2015年1月4日 下午8:11:24  
	 */  
	public List<Map<String, Object>> queryGoodComment(Page page) throws SPTException;
	
	/**
	 * 方法描述:更新商品购买次数
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	public void addBuyNum(String goodId, int buyNum)  throws SPTException;
	
	/**
	 * 
	 * 方法描述:批量修改商品购买数量
	 * @param paramListMap 商品信息
	 * @throws SPTException 
	 * date:2015-6-2
	 * @author wangfengtong
	 */
	public void batchAddBuyNum(List<Map<String,?>> paramListMap) throws SPTException;
	
	/**
	 * 获取商品列表(不带分页)
	 * @author zhoujie
	 * @return List<GoodBean>
	 */
	public List<GoodBean> queryGoods();
}