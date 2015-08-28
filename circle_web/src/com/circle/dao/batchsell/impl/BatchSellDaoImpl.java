package com.circle.dao.batchsell.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.circle.dao.batchsell.IBatchSellDao;
import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.batchsell.BatchSellGood;
import com.circle.pojo.good.GoodBean;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

@Repository
public class BatchSellDaoImpl implements IBatchSellDao {
	/**
	 * QUERY_BATCHSELL_LIST_SQL （String）
	 * <p>
	 * 查询批次列表
	 */
	public static String QUERY_BATCHSELL_LIST_SQL = "select id,date_format(start_time,'%Y-%m-%d %H:%i:%s') startTime,date_format(end_time,'%Y-%m-%d %H:%i:%s') endTime,date_format(ship_time,'%Y-%m-%d %H:%i:%s') shipTime,batch_name batchName,status from t_batch_sell";
	
	/**
	 * QUERY_CURRENT_BATCHSELL_SQL （String）
	 * <p>
	 * 查询当前批次
	 */
	public static String QUERY_CURRENT_BATCHSELL_SQL = "select id,date_format(start_time,'%Y-%m-%d %H:%i:%s') startTime,date_format(end_time,'%Y-%m-%d %H:%i:%s') endTime,date_format(ship_time,'%Y-%m-%d %H:%i:%s') shipTime,batch_name batchName,status from t_batch_sell"
		+ " where status = 1 and now() between start_time and end_time  ORDER BY status DESC,startTime LIMIT 1";
	
	/**
	 * 查询当前批次售卖的商品数据id QUERY_CUURENT_SELL_GOODSID （String）
	 * <p>
	 */
	public static String QUERY_CUURENT_SELL_GOODSID = "select good_id from t_batch_sell_goods where batch_id=:batchId";

	//查询特卖批次详情
	private final String QUERY_BATCHSELL = "SELECT id,DATE_FORMAT(START_TIME,'%Y-%m-%d %H:%i:%s') startTime,DATE_FORMAT(END_TIME,'%Y-%m-%d %H:%i:%s') endTime,DATE_FORMAT(SHIP_TIME,'%Y-%m-%d %H:%i:%s') shipTime,BATCH_NAME batchName,CASE WHEN END_TIME > NOW() THEN 1 ELSE 0 END state FROM t_batch_sell WHERE STATUS = 1 AND ID = :id";

	//查询批次对应的商品
	private final String QUERY_BATCHSELL_GOODS = "SELECT * FROM t_batch_sell_goods BSG,t_goods GOOD WHERE BSG.GOOD_ID = GOOD.id AND BSG.BATCH_ID = :batchId";
	
	/**
	 * 注入共通DAO方法类 commonDao
	 */
	@Resource
	ICommonDao commonDao;
	
	/**
	 * @return
	 * <p>
	 * 查询当前批次
	 * @author ytai
	 * @2015-4-29 上午9:13:03
	 */
	@Override
	public BatchSell queryCurrentBatchSell() throws SPTException {
		return commonDao.queryForObject(QUERY_CURRENT_BATCHSELL_SQL,
				BatchSell.class);
	}
	
	/**
	 * 查询当前售卖的商品id
	 * @param batchId
	 * @return String
	 * @throws SPTException
	 * @author ytai
	 * @2015-4-29 上午10:28:02
	 */
	public String queryCurrentSellGoodsId(int batchId) throws SPTException {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("batchId", batchId);
		List<Map<String, Object>> cuurentSellGoodsId = commonDao.queryForList(QUERY_CUURENT_SELL_GOODSID, paramsMap);
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> map : cuurentSellGoodsId) {
			sb.append(map.get("good_id").toString()).append(",");
		}
		return sb.toString();
	}
	
	/**
	 * 查询特卖批次详情
	 * @param id
	 * @return BatchSell
	 */
	public BatchSell queryBatchSell(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id == null || id.equals("") ? 0 : id);
		BatchSell batchSell = null;
		try {
			batchSell = commonDao.queryForObject(QUERY_BATCHSELL, params, BatchSell.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return batchSell == null ? new BatchSell() : batchSell;
	}
	
	/**
	 * 查询特卖批次对应的商品列表
	 * @param batchId
	 * @return List<BatchSellGood>
	 */
	public List<BatchSellGood> queryBatchSellGoods(Integer batchId) {
		List<BatchSellGood> list = new ArrayList<BatchSellGood>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchId", batchId);
		
		try {
			list = commonDao.queryForList(QUERY_BATCHSELL_GOODS, params, BatchSellGood.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询特卖批次对应的商品列表
	 * @param batchId
	 * @return List<GoodBean>
	 */
	public List<GoodBean> queryGoods(Integer batchId) {
		List<GoodBean> list = new ArrayList<GoodBean>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchId", batchId);
		
		try {
			list = commonDao.queryForList(QUERY_BATCHSELL_GOODS, params, GoodBean.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}