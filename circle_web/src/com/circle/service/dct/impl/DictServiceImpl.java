package com.circle.service.dct.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.circle.constant.SystemDict;
import com.circle.dao.dict.IDictDao;
import com.circle.dao.msg.IMsgDao;
import com.circle.pojo.dict.DictBean;
import com.circle.pojo.goodtype.GoodTypeArg;
import com.circle.pojo.goodtype.GoodTypeAttr;
import com.circle.pojo.goodtype.GoodTypeAttrValues;
import com.circle.pojo.msg.MessageTypeBean;
import com.circle.service.dct.IDictService;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.json.JSONArray;

/**
 * 
 * 商品初始化服务层实现 <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-14 下午02:58:16
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
@Service("dictService")
public class DictServiceImpl implements IDictService {
	
	/**
	 * 注入DAO方法
	 */
	@Resource
	private IDictDao dictDao;
	
	/**
	 * 注入DAO方法
	 */
	@Resource
	private IMsgDao msgDao;
	
	/**
	 * 
	 * 方法描述:初始化字典map
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initDictMap() throws SPTException{
		List<DictBean> dictList = dictDao.queryDictList();
		List<DictBean> tempDict;
		String type_code;
		for (DictBean dictBean : dictList)
		{
			if(dictBean.getParent_id()==0)
			{
				SystemDict.PARENT_DICT_MAP.put(dictBean.getType_id(), dictBean);
			}
			else
			{
				type_code=SystemDict.PARENT_DICT_MAP.get(dictBean.getParent_id()).getType_code();
				tempDict=SystemDict.DICT_MAP.get(type_code)==null?new ArrayList<DictBean>():SystemDict.DICT_MAP.get(type_code);
				tempDict.add(dictBean);
				SystemDict.DICT_MAP.put(type_code, tempDict);
			}
			
		}
	}
	
	/**
	 * 
	 * 方法描述:初始化商品类别
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initGoodType() throws SPTException{
		SystemDict.GOOD_TYPE = dictDao.queryGoodTypeList();
	}
	
	/**
	 * 
	 * 方法描述:初始化商品类型属性信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initGoodTypeAttr() throws SPTException{
		SystemDict.GOOD_TYPE_ATTR_MAP=new HashMap<String, List<GoodTypeAttr>>();
		List<GoodTypeAttr> attrList=dictDao.queryGoodTypeAttrList();
		List<GoodTypeAttr> argTemp;
		List<GoodTypeAttrValues> attr_value_list;
		for(GoodTypeAttr attr:attrList)
		{
			String jsonString=attr.getAttr_value();
			attr_value_list=JSONArray.toList(JSONArray.fromObject(jsonString), GoodTypeAttrValues.class);
			attr.setAttr_value_list(attr_value_list);
			
			argTemp=SystemDict.GOOD_TYPE_ATTR_MAP.get(attr.getType_id()+"")==null?new ArrayList<GoodTypeAttr>():SystemDict.GOOD_TYPE_ATTR_MAP.get(attr.getType_id()+"");
			argTemp.add(attr);
			SystemDict.GOOD_TYPE_ATTR_MAP.put(attr.getType_id()+"", argTemp);
		}
	}
	
	/**
	 * 
	 * 方法描述:初始化商品类型参数信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initGoodTypeArg() throws SPTException{
		SystemDict.GOOD_TYPE_ARG_MAP=new HashMap<String, List<GoodTypeArg>>();
		List<GoodTypeArg> argList=dictDao.queryGoodTypeArgList();
		List<GoodTypeArg> argTemp;
		for(GoodTypeArg arg:argList)
		{
			argTemp=SystemDict.GOOD_TYPE_ARG_MAP.get(arg.getType_id()+"")==null?new ArrayList<GoodTypeArg>():SystemDict.GOOD_TYPE_ARG_MAP.get(arg.getType_id()+"");
			argTemp.add(arg);
			SystemDict.GOOD_TYPE_ARG_MAP.put(arg.getType_id()+"", argTemp);
		}
	}

	/* 
	 * 初始化农场数据
	 * @see com.circle.service.dct.IDictService#initFarm()
	 */
	public void initFarm() throws SPTException {
		SystemDict.FARM=dictDao.queryFarmList();
	}

	/* 
	 * 系统初始化调用
	 * 初始化消息类型map
	 */
	@Override
	public void initMessageTypeBeans()  throws SPTException
	{
		List<MessageTypeBean> messageTypeBeans=msgDao.queryMessageTypeBeans();
		for (MessageTypeBean messageTypeBean : messageTypeBeans)
		{
			SystemDict.MESSAGE_TYPE_MAP.put(messageTypeBean.getId(), messageTypeBean);
		}
	}
	
	
}
