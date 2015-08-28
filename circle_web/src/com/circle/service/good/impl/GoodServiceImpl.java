package com.circle.service.good.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circle.constant.SystemDict;
import com.circle.dao.good.IGoodDao;
import com.circle.service.good.IGoodService;
import com.circle.utils.NumUtils;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.ProUtil;

@Service
@Transactional
public class GoodServiceImpl implements IGoodService {
	
	@Resource
	private IGoodDao goodDao;

	public final static Logger log=Logger.getLogger(GoodServiceImpl.class);
	

	/* 
	 * 查询商品列表
	 */
	@Override
	public List<Map<String, Object>> queryGoodList(Page page) throws SPTException  {
		List<Map<String, Object>> goodList=goodDao.queryGoodList(page);
		for (Map<String, Object> map : goodList) {
			converGoodInfo(map);
		}
		return goodList;
	}
	

	/**
	 * 查询当前售卖批次的商品列表
	 * @param page
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryCurrentBatchGoodList(Page page,int batchId) throws SPTException {
		List<Map<String, Object>> goodList=goodDao.queryCurrentBatchGoodList(page,batchId);
		for (Map<String, Object> map : goodList) {
			converGoodInfo(map);
		}
		return goodList;
	}





	/**  
	 * 此方法描述的是：  转化商品一些文字描述信息
	 * @author: Taiyuan  
	 * @version: 2015年1月1日 下午11:32:10  
	 */  
	    
	private void converGoodInfo(Map<String, Object> good) 
	{
		String sell_price;
		String original_price;
		String cost_price;
		if(good.get("id")==null)
		{
			return;
		}
		sell_price=good.get("sell_price").toString();
		original_price=good.get("original_price").toString();
		cost_price=good.get("cost_price").toString();
		good.put("type_str",SystemDict.getGoodType(good.get("type_id")+"").getName());
		good.put("image",ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+good.get("image"));
		good.put("unit_str",SystemDict.getDict(SystemDict.UNIT, good.get("unit").toString()).getType_name());
		good.put("farm_str",SystemDict.getFarm(good.get("farm_id").toString()).getFarm_name());
		good.put("sell_price",NumUtils.subZeroAndDot(sell_price));
		good.put("sell_price",NumUtils.subZeroAndDot(sell_price));
		good.put("original_price",NumUtils.subZeroAndDot(original_price));
		good.put("cost_price",NumUtils.subZeroAndDot(cost_price));
		good.put("buy_num",good.get("buy_num")==null?0:good.get("buy_num"));
	}

	/* 查询商品
	 */
	@Override
	public Map<String, Object> queryGood(String id) throws SPTException
	{
		Map<String,Object> good= goodDao.queryGood(id);
		converGoodInfo(good);
		return good;
	}

	/**
	 * 查询商品类型参数值
	 * @param id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryGoodTypeArgValues(String id) throws SPTException
	{
		return goodDao.queryGoodTypeArgValues(id);
	}

	/**
	 * 查询商品类型属性参数值
	 * @param id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryGoodTypeAttrValues(String id) throws SPTException
	{
		return goodDao.queryGoodTypeAttrValues(id);
	}

	/**
	 * 查询商品图片列表
	 * @param id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryImageList(String id)  throws SPTException
	{
		 List<Map<String, Object>> imageList= goodDao.queryImageList(id);
		 for (Map<String, Object> map : imageList) 
		 {
			 map.put("path", ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+map.get("img_path"));
		 }
		 return imageList;
	}
	
	
	
	/**  
	 * 此方法描述的是：  查询商品评论信息
	 * @author: Taiyuan  
	 * @version: 2015年1月4日 下午8:11:24  
	 */  
    @Override
	public List<Map<String, Object>> queryGoodComment(Page page)  throws SPTException {
		return goodDao.queryGoodComment(page);
	}

	
}
