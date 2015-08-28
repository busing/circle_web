package com.circle.dao.dict.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.circle.dao.dict.IDictDao;
import com.circle.pojo.dict.DictBean;
import com.circle.pojo.farm.Farm;
import com.circle.pojo.goodtype.GoodType;
import com.circle.pojo.goodtype.GoodTypeArg;
import com.circle.pojo.goodtype.GoodTypeAttr;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 
 * 圈子DAO接口实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-12 下午10:09:25
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class DictDaoImpl implements IDictDao{
	
	/**
	 * 查询字典信息
	 */
	public static final String QUERY_DICT_LIST = "select * from t_dict where status=1 order by parent_id, type_id" ;
	
	/**
	 * 查询商品信息
	 */
	public static final String QUERY_GOOD_TYPE_LIST = "select * from t_good_type";
	
	/**
	 * 查询商品类型属性信息
	 */
	public static final String QUERY_GOODTYPE_ATTR_LIST = "select * from t_goodtype_attr";
	
	/**
	 * 查询商品类型参数表
	 */
	public static final String QUERY_GOODTYPE_ARG_LIST = "select * from t_goodtype_arg";
	
	/**
	 * 查询农场信息
	 */
	public static final String QUERY_FARM_LIST = "select * from t_farm where status=1";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;

	/**
	 * 
	 * 方法描述:查询所有字典信息
	 * @return 字典信息列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<DictBean> queryDictList() throws SPTException{
		return commonDao.queryForList(QUERY_DICT_LIST, DictBean.class);
			
	}
	
	/**
	 * 
	 * 方法描述:查询所有商品类别列表
	 * @return 商品类别 信息列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<GoodType> queryGoodTypeList() throws SPTException{
		return commonDao.queryForList(QUERY_GOOD_TYPE_LIST,GoodType.class);
	}
	
	/**
	 * 
	 * 方法描述:查询所有商品类型属性信息
	 * @return 商品类型属性信息列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<GoodTypeAttr> queryGoodTypeAttrList() throws SPTException{
		return commonDao.queryForList(QUERY_GOODTYPE_ATTR_LIST, GoodTypeAttr.class);
			
	}
	
	/**
	 * 
	 * 方法描述:查询商品类型参数信息
	 * @return 商品类型参数列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<GoodTypeArg> queryGoodTypeArgList() throws SPTException{
		return commonDao.queryForList(QUERY_GOODTYPE_ARG_LIST, GoodTypeArg.class);
	}

	/**
	 * 
	 * 方法描述:查询农场信息
	 * @return 农场信息
	 */
	public List<Farm> queryFarmList() throws SPTException {
		return commonDao.queryForList(QUERY_FARM_LIST, Farm.class);
	}
	
	
}
