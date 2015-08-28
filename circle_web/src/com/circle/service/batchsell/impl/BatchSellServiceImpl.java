package com.circle.service.batchsell.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circle.constant.SystemDict;
import com.circle.dao.batchsell.IBatchSellDao;
import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.good.GoodBean;
import com.circle.service.batchsell.IBatchSellService;
import com.xwtec.xwserver.exception.SPTException;

@Service
@Transactional
public class BatchSellServiceImpl implements IBatchSellService {
	public static final Logger logger = Logger.getLogger(BatchSellServiceImpl.class);
	
	/**
	 * 注入DAO方法
	 */
	@Resource
	private IBatchSellDao batchSellDao;
	
	/**
	 * 查询当前批次
	 * @return
	 * @author ytai
	 * @2015-4-29 上午9:13:03
	 */
	@Override
	public BatchSell queryCurrentBatchSell() throws SPTException {
		return batchSellDao.queryCurrentBatchSell();
	}
	
	/**
	 * 查询当前售卖的商品id
	 * @param batchId
	 * @return String
	 * @throws SPTException
	 * @author ytai
	 * @2015-4-29 上午10:28:02
	 */
	@Override
	public String queryCurrentSellGoodsId(int batchId) throws SPTException {
		return batchSellDao.queryCurrentSellGoodsId(batchId);
	}
	
	/**
	 * 查询特卖批次详情
	 * @param id
	 * @return List<GoodBean>
	 */
	public List<GoodBean> queryGoods(Integer id) {
		List<GoodBean> goods = new ArrayList<GoodBean>();

		if(id == null || id.equals(""))
			return goods;
		
		try {
			goods = batchSellDao.queryGoods(id);
			
			GoodBean good = null;
			for (int i = 0; i < goods.size(); i++) {
				good = goods.get(i);
				good.setUnit_str(SystemDict.getDict(SystemDict.UNIT, good.getUnit()+"").getType_name());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return goods;
	}
}