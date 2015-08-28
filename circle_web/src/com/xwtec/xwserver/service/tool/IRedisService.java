package com.xwtec.xwserver.service.tool;

import java.util.Set;

import com.xwtec.xwserver.pojo.Page;


/**
 *数据库查询工具业务逻辑处理接口
 * <p>
 * Copyright: Copyright (c) 2013-11-15 下午03:24:01
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
public interface IRedisService {
	

	/**
	 * 方法描述:查询对应的缓存keys
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	public Set<String> getKeys(Page page);
	
	/**
	 * 方法描述: 查看一个键还有几秒过期 
	 * @param 缓存key
	 * @return 秒
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	public long Ttl(String key);
	
	 
}
