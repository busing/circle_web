package com.circle.service.batchsell;

import java.util.List;

import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.good.GoodBean;
import com.xwtec.xwserver.exception.SPTException;

public interface IBatchSellService {
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
	 * @return List<GoodBean>
	 */
	public List<GoodBean> queryGoods(Integer id);
}