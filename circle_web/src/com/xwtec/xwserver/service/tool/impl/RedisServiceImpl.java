package com.xwtec.xwserver.service.tool.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.service.tool.IRedisService;

/**
 * 查询数据库工具业务逻辑处理类 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-11-14 下午06:16:29
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
@Service
public class RedisServiceImpl implements IRedisService {

	/**
	 * 日志打印对象
	 */
	private static final Logger logger = Logger.getLogger(RedisServiceImpl.class);
	
	@Resource
	JedisPool jedisPool;

	/**
	 * 方法描述:查询对应的缓存keys
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	public Set<String> getKeys(Page page) {
		logger.info("RedisServiceImpl.getKeys param: "+page.getSearchParam());
		
		Jedis jedis = null;//jedis对象
		Set<String> keys = null;//keys列表
		
		try{
			 //获取连接
			 jedis = jedisPool.getResource();
			 //根据前台传过来的规则获取缓存key列表
			 if(null != page.getSearchParam() && null != page.getSearchParam().get("keys")){
				 keys = jedis.keys(page.getSearchParam().get("keys"));
			 }
		}finally{
			//使用完毕后将jedis对象归还连接池
			if(jedis != null)
				jedisPool.returnResource(jedis);
		}
		return keys;
	}

	/**
	 * 方法描述: 查看一个键还有几秒过期 
	 * @param 缓存key
	 * @return 秒
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
    public long Ttl(String key) { 
        long result = 0L; //返回时间
        Jedis jedis = null;//jedis对象
        try { 
        	//获取连接
   		 	jedis = jedisPool.getResource();
            result = jedis.ttl(key); 
        }finally { 
        	//使用完毕后将jedis对象归还连接池
        	if(jedis != null)
				jedisPool.returnResource(jedis);
        } 
        return result; 
    }
}
