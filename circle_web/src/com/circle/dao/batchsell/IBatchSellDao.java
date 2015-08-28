package com.circle.dao.batchsell;

import java.util.List;

import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.batchsell.BatchSellGood;
import com.circle.pojo.good.GoodBean;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 批次销售
 * @author ytai
 * @version 1.0.0
 * @2015-4-29 上午9:05:56
 */
public interface IBatchSellDao {
	/**
	 * 查询当前批次
	 * @return BatchSell
	 * @author ytai
	 * @2015-4-29 上午9:13:03
	 */
	public BatchSell queryCurrentBatchSell() throws SPTException;
	
	/**
	 * 查询当前售卖的商品id
	 * @param batchId
	 * @return String
	 * @throws SPTException
	 * @author ytai
	 * @2015-4-29 上午10:28:02
	 */
	public String queryCurrentSellGoodsId(int batchId) throws SPTException;
	
	/**
	 * 查询特卖批次详情
	 * @param id
	 * @return BatchSell
	 */
	public BatchSell queryBatchSell(Integer id);

	/**
	 * 查询特卖批次对应的商品列表
	 * @param batchId
	 * @return List<BatchSellGood>
	 */
	public List<BatchSellGood> queryBatchSellGoods(Integer batchId);
	
	/**
	 * 查询特卖批次对应的商品列表
	 * @param batchId
	 * @return List<GoodBean>
	 */
	public List<GoodBean> queryGoods(Integer batchId);
}