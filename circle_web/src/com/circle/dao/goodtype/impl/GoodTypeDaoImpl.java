package com.circle.dao.goodtype.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.dao.goodtype.IGoodTypeDao;
import com.circle.pojo.goodtype.GoodType;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

@Repository
public class GoodTypeDaoImpl implements IGoodTypeDao{

	public final static Logger log=Logger.getLogger(GoodTypeDaoImpl.class);
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	
	/**
	 * 查询商品类型sql
	 */
	public static final String QUERY_GOODTYPES_SQL="select * from "+CircleTable.GOOD_TYPE.getTableName()+" order by sort_id";
	
	/**
	 * 查询商品类型sql
	 */
	public static final String QUERY_GOODTYPE_SQL="select * from "+CircleTable.GOOD_TYPE.getTableName()+" where id=:id";
	
	/**
	 * 查询商品类型参数配置sql
	 */
	public static final String QUERY_GOODTYPEARG_SQL="select * from "+CircleTable.GOOD_TYPE_ARG.getTableName()+" where type_id=:type_id";
	
	/**
	 * 查询商品类型属性配置sql
	 */
	public static final String QUERY_GOODTYPEATTR_SQL="select * from "+CircleTable.GOOD_TYPE_ATTR.getTableName()+" where type_id=:type_id";
	
	/**
	 * 添加商品类型sql
	 */
	public static final String INSERT_GOODTYPE_SQL="insert into "+CircleTable.GOOD_TYPE.getTableName()+"(name,parent_id,sort_id) values(:name,:parent_id,:sort_id)";
	
	/**
	 * 更新商品类型sql
	 */
	public static final String UPDATE_GOODTYPE_SQL="update "+CircleTable.GOOD_TYPE.getTableName()+" set name=:name,parent_id=:parent_id,sort_id=:sort_id where id=:id";
	
	/**
	 * 删除商品类型参数配置sql
	 */
	public static final String DELETE_GOODTYPEARG_SQL="delete from "+CircleTable.GOOD_TYPE_ARG.getTableName()+" where type_id=:type_id";
	
	/**
	 * 删除商品类型属性配置sql
	 */
	public static final String DELETE_GOODTYPEATTR_SQL="delete from "+CircleTable.GOOD_TYPE_ATTR.getTableName()+" where type_id=:type_id";
	
	/**
	 * 添加商品类型参数配置sql
	 */
	public static final String INSERT_GOODTYPEARG_SQL="insert into "+CircleTable.GOOD_TYPE_ARG.getTableName()+" (english_name,name,type_id) values(:english_name,:name,:type_id)";
	
	/**
	 * 添加商品类型属性配置sql
	 */
	public static final String INSERT_GOODTYPEATTR_SQL="insert into "+CircleTable.GOOD_TYPE_ATTR.getTableName()+" (english_name,name,attr_value,type_id) values(:english_name,:name,:attr_value,:type_id)";;
	
	/* 
	 * 查询商品类型
	 * @see com.circle.dao.goodtype.IGoodTypeDao#queryGoodTypes()
	 */
	@Override
	public List<Map<String, Object>> queryGoodTypes() throws SPTException 
	{
		return commonDao.queryForList(QUERY_GOODTYPES_SQL);
	}

	
	/**
	 * 查询商品类型
	 * @param id
	 * @return
	 */
	@Override
	public GoodType getGoodType(String id)  throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("id", id);
		GoodType goodType=commonDao.queryForObject(QUERY_GOODTYPE_SQL,paramsMap, GoodType.class);
		return goodType;
	}
	
	
	/**
	 * 保存商品类型
	 * @param id
	 * @return
	 */
	@Override
	public boolean save(GoodType goodType)  throws SPTException
	{
		Map<String, Object> paramMap = getGoodTypeParams(goodType);
		int i=commonDao.update(INSERT_GOODTYPE_SQL, paramMap);
		boolean flag=i>0?true:false;
		if(flag)
		{
			goodType.setId(commonDao.getLastId(CircleTable.GOOD_TYPE.getTableName(), "id"));
		}
		return flag;
	}

	/**
	 * 更新商品类型
	 * @return
	 */
	@Override
	public boolean update(GoodType goodType) throws SPTException
	{
		Map<String, Object> paramMap = getGoodTypeParams(goodType);
		int i=commonDao.update(UPDATE_GOODTYPE_SQL, paramMap);
		boolean flag=i>0?true:false;
		return flag;
	}
	

	/**
	 * 商品类型参数转化
	 * @param goodType
	 * @return
	 */
	private Map<String, Object> getGoodTypeParams(GoodType goodType) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("name", goodType.getName());
		paramMap.put("parent_id", goodType.getParent_id());
		paramMap.put("sort_id", goodType.getSort_id());
		paramMap.put("id", goodType.getId());
		return paramMap;
	}

	
	/**
	 * 查询商品类型对应的参数配置
	 * @return
	 */
	@Override
	public void deleteArg(int typeId)  throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("type_id", typeId);
		commonDao.update(DELETE_GOODTYPEARG_SQL, paramMap);
	}

	/**
	 * 查询商品类型对应的属性配置
	 * @return
	 */
	@Override
	public void deleteAttr(int typeId) throws SPTException 
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("type_id", typeId);
		commonDao.update(DELETE_GOODTYPEATTR_SQL, paramMap);
	}

	/**
	 * 保存商品类型对应的参数配置
	 * @return
	 */
	@Override
	public void saveArg(String name, String english_name, int typeId)  throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("english_name", english_name);
		paramMap.put("type_id", typeId);
		commonDao.update(INSERT_GOODTYPEARG_SQL, paramMap);
	}

	/**
	 * 保存商品类型对应的参数配置
	 * @return
	 */
	@Override
	public void saveAttr(String name, String english_name, String attrValues,int typeId)  throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("english_name", english_name);
		paramMap.put("attr_value", attrValues);
		paramMap.put("type_id", typeId);
		commonDao.update(INSERT_GOODTYPEATTR_SQL, paramMap);
	}


	/**
	 * 查询商品类型参数配置列表
	 * @param type_id
	 * @return
	 * @throws SPTException 
	 */
	@Override
	public List<Map<String, Object>> queryGoodTypeArgList(String type_id) throws SPTException 
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("type_id", type_id);
		return commonDao.queryForList(QUERY_GOODTYPEARG_SQL,paramMap);
	}

	
	/**
	 * 查询商品类型属性配置列表
	 * @param type_id
	 * @return
	 * @throws SPTException 
	 */
	@Override
	public List<Map<String, Object>> queryGoodTypeAttrList(String type_id) throws SPTException
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("type_id", type_id);
		return commonDao.queryForList(QUERY_GOODTYPEATTR_SQL,paramMap);
	}
	
	
	
	
}
