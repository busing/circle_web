package com.circle.dao.good.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.dao.good.IGoodDao;
import com.circle.pojo.good.GoodBean;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月19日 下午10:50:59
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Repository
public class GoodDaoImpl implements IGoodDao{
	
	public final static Logger log=Logger.getLogger(GoodDaoImpl.class);
	
	
	/**
	 * 查询商品列表sql 
	 */
	public static final String QUERY_GOODLIST_SQL="SELECT good.*,count(comment.id) commonCount FROM "+CircleTable.GOODS.getTableName()+ " good left join "
			+CircleTable.GOOD_COMMENT.getTableName()+" comment on comment.good_id=good.id where good.is_putaway=1 and good.status=1  ";
	
	/**
	 * 查询当前批次商品列表sql 
	 */
	public static final String QUERY_BATCH_GOODLIST_SQL="SELECT good.*,count(comment.id) commonCount FROM "+CircleTable.GOODS.getTableName()+ " good left join "
			+CircleTable.GOOD_COMMENT.getTableName()+" comment on comment.good_id=good.id right join t_batch_sell_goods sg on sg.good_id=good.id where good.is_putaway=1 and good.status=1  ";
	
	
	/**
	 * 查询商品sql 
	 */
	public static final String QUERY_GOOD_SQL="SELECT good.*,count(comment.id) commonCount FROM "+CircleTable.GOODS.getTableName()+ " good left join "
			+CircleTable.GOOD_COMMENT.getTableName()+" comment on comment.good_id=good.id where good.is_putaway=1 and good.status=1  and good.id=:id ";
	
	
	/**
	 * 查询商品类型参数值
	 */
	public static final String QUERY_ATTR_VALUES_SQL="SELECT * FROM "+CircleTable.GOOD_ATTR_VALUE.getTableName()+ " where good_id=:good_id ";
	
	/**
	 * 查询商品类型参数值
	 */
	public static final String QUERY_ARG_VALUES_SQL="SELECT * FROM "+CircleTable.GOOD_ARG_VALUE.getTableName()+ " where good_id=:good_id ";
	
	/**
	 * 查询商品图片
	 */
	public static final String QUERY_GOOD_IMAGE_SQL="SELECT * FROM "+CircleTable.GOOD_IMAGE.getTableName()+" where good_id=:good_id order by is_default desc";
	
	/**
	 * 查询商品评论信息列表
	 */
	public static final String QUERY_GOOD_COMMENT_SQL="select c.id,c.good_id,c.comment_text,date_format(c.comment_time,'%Y-%m-%d %H:%i') comment_time,c.describe_score,u.id userid ,c.user_name username ,f.name circle_name from "
								+CircleTable.GOOD_COMMENT.getTableName()+" c,"+CircleTable.ORDER.getTableName()+" o,"
								+CircleTable.CIRCLE.getTableName()+" f,"+CircleTable.USER.getTableName()+" u where c.order_id=o.order_id and o.circle_id=f.id and "
								+" c.user_id=u.id and good_id=:good_id";
	
	
	/**
	 * 更新商品购买次数
	 * UPDATE_GOOD_BUYNUM 
	 */
	public static final String UPDATE_GOOD_BUYNUM="update "+CircleTable.GOODS.getTableName()+" set buy_num=buy_num+:buyNum where id=:goodId";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;


	/* 
	 * 查询商品列表
	 * @see com.circle.dao.good.IGoodDao#queryUserList(com.xwtec.xwserver.pojo.Page)
	 */
	@Override
	public List<Map<String, Object>> queryGoodList(Page page)  throws SPTException 
	{
		log.debug("[GoodDaoImpl.queryGoodList] start...") ;
		StringBuilder sql = new StringBuilder(QUERY_GOODLIST_SQL);
		if(page != null && page.getSearchParam() != null){
			if(page.getSearchParam().containsKey("keyWord") && !StringUtil.isEmpty(page.getSearchParam().get("keyWord"))){
				sql.append(" and (good_name LIKE '%"+page.getSearchParam().get("keyWord")+"%' ");
				sql.append(" or title LIKE '%"+page.getSearchParam().get("keyWord")+"%' ");
				sql.append(" or intro LIKE '%"+page.getSearchParam().get("keyWord")+"%') ");
//				page.getSearchParam().put("keyWord", "%"+page.getSearchParam().get("keyWord")+"%");
			}
			if(page.getSearchParam().containsKey("farm_id") && !StringUtil.isEmpty(page.getSearchParam().get("farm_id"))){
				sql.append(" and good.farm_id =:farm_id");
			}
			if(page.getSearchParam().containsKey("type_id") && !StringUtil.isEmpty(page.getSearchParam().get("type_id"))){
				sql.append(" and good.type_id = :type_id");
			}
		}
		sql.append(" group by good.id order by good.recommend desc ,good.sort_id asc");
		List<Map<String, Object>> goodList  = commonDao.queryForList(sql.toString(), page.getSearchParam(), page);
		log.debug("[GoodDaoImpl.queryGoodList] end...") ;
		return goodList;
	}
	

	/**
	 * 查询当前售卖批次的商品列表
	 * @param page
	 * @return
	 */
	public  List<Map<String, Object>> queryCurrentBatchGoodList(Page page,int batchId) throws SPTException
	{
		if(page.getSearchParam()==null)
		{
			page.setSearchParam(new HashMap<String, String>());
		}
		log.debug("[GoodDaoImpl.queryGoodList] start...") ;
		StringBuilder sql = new StringBuilder(QUERY_BATCH_GOODLIST_SQL);
		if(page != null && page.getSearchParam() != null){
			if(page.getSearchParam().containsKey("keyWord") && !StringUtil.isEmpty(page.getSearchParam().get("keyWord"))){
				sql.append(" and (good_name LIKE '%"+page.getSearchParam().get("keyWord")+"%' ");
				sql.append(" or title LIKE '%"+page.getSearchParam().get("keyWord")+"%' ");
				sql.append(" or intro LIKE '%"+page.getSearchParam().get("keyWord")+"%') ");
//				page.getSearchParam().put("keyWord", "%"+page.getSearchParam().get("keyWord")+"%");
			}
			if(page.getSearchParam().containsKey("farm_id") && !StringUtil.isEmpty(page.getSearchParam().get("farm_id"))){
				sql.append(" and good.farm_id =:farm_id");
			}
			if(page.getSearchParam().containsKey("type_id") && !StringUtil.isEmpty(page.getSearchParam().get("type_id"))){
				sql.append(" and good.type_id = :type_id");
			}
		}
		if(batchId!=0)
		{
			sql.append(" and sg.batch_id = :batchId");
			page.getSearchParam().put("batchId", batchId+"");
		}
		sql.append(" group by good.id order by good.recommend desc ,good.sort_id asc");
		List<Map<String, Object>> goodList  = commonDao.queryForList(sql.toString(), page.getSearchParam(), page);
		log.debug("[GoodDaoImpl.queryGoodList] end...") ;
		return goodList;
	}
	
	/* 
	 * 查询商品信息
	 */
	@Override
	public Map<String, Object> queryGood(String id) throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("id", id);
		return commonDao.queryForMap(QUERY_GOOD_SQL,paramMap);
	}
	
	
	

	@Override
	public List<Map<String, Object>> queryGoodTypeArgValues(String id) throws SPTException {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("good_id", id);
		return commonDao.queryForList(QUERY_ARG_VALUES_SQL,paramMap);
	}

	@Override
	public List<Map<String, Object>> queryGoodTypeAttrValues(String id) throws SPTException {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("good_id", id);
		return commonDao.queryForList(QUERY_ATTR_VALUES_SQL,paramMap);
	}
	
	

	/**
	 * 查询商品图片列表
	 * @param id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryImageList(String id) throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("good_id", id);
		return commonDao.queryForList(QUERY_GOOD_IMAGE_SQL,paramMap);
	}
	
	

	/**  
	 * 此方法描述的是：  查询商品评论信息
	 * @author: Taiyuan  
	 * @version: 2015年1月4日 下午8:11:24  
	 */  
	@Override
	public List<Map<String, Object>> queryGoodComment(Page page) throws SPTException 
	{
		return commonDao.queryForList(QUERY_GOOD_COMMENT_SQL,page.getSearchParam(),page);
	}
	

	public boolean executeSql(String sql, Map<String, Object> paramMap)
	{
		try {
			int count=commonDao.update(sql, paramMap);
			return count>0?true:false;
		} catch (SPTException e) {
			log.error("执行sql异常");
			return false;
		}
	}
	
	/**
	 * 方法描述:更新商品购买次数
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	public void addBuyNum(String goodId, int buyNum)  throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("goodId", goodId);
		paramMap.put("buyNum", buyNum);
		commonDao.update(UPDATE_GOOD_BUYNUM, paramMap);
	}
	
	/**
	 * 
	 * 方法描述:批量修改商品购买数量
	 * @param paramListMap 商品信息
	 * @throws SPTException 
	 * date:2015-6-2
	 * @author wangfengtong
	 */
	public void batchAddBuyNum(List<Map<String,?>> paramListMap) throws SPTException{
		commonDao.batchUpdate(UPDATE_GOOD_BUYNUM, paramListMap);
	}
	
	/**
	 * 获取商品列表(不带分页)
	 * @author zhoujie
	 * @return List<GoodBean>
	 */
	public List<GoodBean> queryGoods(){
		List<GoodBean> list = new ArrayList<GoodBean>();
		try {
			list = commonDao.queryForList(QUERY_GOODLIST_SQL, GoodBean.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}