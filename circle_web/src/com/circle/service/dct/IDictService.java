package com.circle.service.dct;

import com.xwtec.xwserver.exception.SPTException;


/**
 * 
 * 商品初始化Service接口. <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-14 下午02:57:38
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
public interface IDictService {
	
	/**
	 * 
	 * 方法描述:初始化字典map
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initDictMap() throws SPTException;
	
	/**
	 * 
	 * 方法描述:初始化商品类别
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initGoodType() throws SPTException;
	
	/**
	 * 
	 * 方法描述:初始化商品类型属性信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initGoodTypeAttr() throws SPTException;
	
	/**
	 * 
	 * 方法描述:初始化商品类型参数信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public void initGoodTypeArg() throws SPTException;
	
	/**
	 * 初始化农场数据
	 * @throws SPTException
	 */
	public void initFarm() throws SPTException;
	
	/* 
	 * 系统初始化调用
	 * 初始化消息类型map
	 */
	public void initMessageTypeBeans() throws SPTException;
}
