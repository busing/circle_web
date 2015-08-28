package com.circle.dao.dict;

import java.util.List;

import com.circle.pojo.dict.DictBean;
import com.circle.pojo.farm.Farm;
import com.circle.pojo.goodtype.GoodType;
import com.circle.pojo.goodtype.GoodTypeArg;
import com.circle.pojo.goodtype.GoodTypeAttr;
import com.xwtec.xwserver.exception.SPTException;


/**
 * 
 * 商品初始化DAO层 <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-14 下午02:43:53
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
public interface IDictDao {
	
	/**
	 * 
	 * 方法描述:查询所有字典信息
	 * @return 字典信息列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<DictBean> queryDictList() throws SPTException;
	
	/**
	 * 
	 * 方法描述:查询所有商品类别列表
	 * @return 商品类别 信息列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<GoodType> queryGoodTypeList() throws SPTException;
	
	/**
	 * 
	 * 方法描述:查询所有商品类型属性信息
	 * @return 商品类型属性信息列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<GoodTypeAttr> queryGoodTypeAttrList() throws SPTException;
	
	/**
	 * 
	 * 方法描述:查询商品类型参数信息
	 * @return 商品类型参数列表
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<GoodTypeArg> queryGoodTypeArgList() throws SPTException;
	
	/**
	 * 查询农场信息
	 * @return
	 * @throws SPTException
	 */
	public List<Farm> queryFarmList()  throws SPTException;
}
