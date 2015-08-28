package com.xwtec.xwserver.util.jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.xwtec.xwserver.constant.ConstantKeys;
import com.xwtec.xwserver.exception.SPTException;

/**
 * Redis缓存操作的工具类<br>
 * <p>
 * Copyright: Copyright (c) Nov 7, 2013 10:31:44 AM
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author houxu@xwtec.cn
 * @version 1.0.0
 */
public class JedisUtil {
	
	/**
	 * logger 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(JedisUtil.class);
	
	/**
	 * jedisPool redis连接池
	 */
	@Resource(name = "jedisPool")
	public JedisPool jedisPool;
	
	/**
	 * 方法描述:从连接池获取jedis对象
	 * @return 返回Jedis的有效对象
	 * date:Nov 14, 2013
	 * add by: houxu@xwtec.cn
	 */
	public Jedis getResource() throws SPTException{
		logger.debug("[JedisUtil:getResource]: get jedis Resource from Pool...");
		Jedis jedis = null;//声明jedis对象
		int cycleTimes = 0;//出现异常已经循环获取的次数
		try{
			jedis = this.jedisPool.getResource();//从pool中获取jedis对象
		}catch (JedisConnectionException ex) {
			try {
				//获取占用异常,捕获异常,等待0.5秒后继续执行获取
				logger.debug("[JedisUtil:getResource]:redis pool is full,Program will sleep 0.5s to wait an idle.message:\n"+ex.getMessage());
				
				while(cycleTimes < ConstantKeys.RedisUtilKey.CYCLE_TIMES){
					cycleTimes ++ ;//获取次数 +1;
		            Thread.sleep(ConstantKeys.RedisUtilKey.WAIT_TIME);//等待0.5s
		            
		        	logger.debug("[JedisUtil:getResource]:waiting to get an idle...");
		        	try{
		        		jedis = this.jedisPool.getResource();//重新获取jedis对象
		        	}catch(JedisConnectionException ex1){
			        	logger.debug("[JedisUtil:getResource]:get an idle failed.Program will try again.");
		        		//出现获取异常,继续执行
		        		continue;
		        	}
		        	
		        	//获取到对象后跳出循环
		        	if(jedis != null){
			        	//输出获取成功的消息
			        	logger.debug("[JedisUtil:getResource]:get an idle success.");
		        		break;
		        	}
		        	else{ //如果获取出对象为null,则继续循环等待获取.
			        	logger.debug("[JedisUtil:getResource]:get an idle is null.Program will try again.");
		        		continue;
		        	}
				}
            }
			//处理线程截断异常
            catch (InterruptedException e) {
            	logger.error("[JedisUtil:getResource]:get jedis object failed.message:\n"+e.getMessage());
            }
		}
		//获取对象如果不为空则返回
		if(jedis != null){
			logger.debug("[JedisUtil:getResource]: get jedis Resource from Pool success.");
		}else{//当循环获取超过三次直接抛出异常 返回null
        	logger.error("[JedisUtil:getResource]:get jedis object failed.if redis server is runing,please check the configration and Network connection.");
        	throw new SPTException("server can not get jedis Resource!");
		}
		return jedis;
	}
	
	/**
	 * 方法描述:使用完毕后将jedis对象归还连接池
	 * @param Jedis 从pool中获取的jedis对象
	 * date:Nov 14, 2013
	 * add by: houxu@xwtec.cn
	 */
	public void returnResource(Jedis jedis){
		try{
			if(jedis != null)
				this.jedisPool.returnResource(jedis);//归还对象至pool
			logger.debug("[JedisUtil:returnResource]: return jedis Resource to Pool  ...");
		}catch(JedisConnectionException ex){
			//归还失败,强制销毁该链接
			this.jedisPool.returnBrokenResource(jedis);
		}
	}
	/**
	 * 
	 * 方法描述:根据key值查询value数据类型
	 * date:2014-12-4
	 * add by: fanzhaode@xwtec.cn
	 */
	public String getType(String key)throws SPTException{
		Jedis jedis = null;//声明一个链接对象
		String type="";//数据类型
		try{
			jedis = this.getResource();//获取jedis资源
			//资源不为空,则获取对应的value
			if(jedis != null) 
				type=jedis.type(key);
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return type;
	}
	/**
	 * 方法描述:根据关键字从redis服务器中获取对应的value
	 * @param String key 键值
	 * @return 存储在redis中的value
	 * date:Nov 14, 2013
	 * add by: houxu@xwtec.cn
	 * @throws SPTException 
	 */
	public String getRedisStrValue(String key) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		String value = null;//获取的键值所对应的值
		
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 
				value = jedis.get(key);
			
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 方法描述:根据关键字从redis服务器中获取对应的value
	 * @param String key 键值
	 * @return 存储在redis中的list类型value
	 * date:2014-12-4
	 * add by: fanzhaode@xwtec.cn
	 * @throws SPTException 
	 */
	public List<String> getRedisListValue(String key) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		List<String> listValue=null;//获取的键值所对应的值
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 		
				listValue=jedis.lrange(key, 0, -1);		
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return listValue;
	}
	
	/**
	 * 方法描述:根据关键字从redis服务器中获取对应的value
	 * @param String key 键值
	 * @return 存储在redis中的set类型value
	 * date:2014-12-4
	 * add by: fanzhaode@xwtec.cn
	 * @throws SPTException 
	 */
	public Set<String> getRedisSorSetValue(String key)throws SPTException{
		Jedis jedis = null;//声明一个链接对象
		Set<String> set=new HashSet<String>();
		try{
			jedis = this.getResource();//获取jedis资源
			//资源不为空,则获取对应的value
			if(jedis!=null){
				set=jedis.zrange(key, 0, -1);
			}
		}finally{
			if(jedis!=null){
				this.returnResource(jedis);
			}
		}
		return set;
	}
	/**
	 * 方法描述:根据关键字从redis服务器中获取对应的value
	 * @param String key 键值
	 * @return 存储在redis中的hash类型value
	 * date:2014-12-4
	 * add by: fanzhaode@xwtec.cn
	 * @throws SPTException 
	 */
	public List<String> getRedisHashValue(String key)throws SPTException{
		Jedis jedis = null;//声明一个链接对象
		List<String> hash=null;
		try{
			jedis = this.getResource();//获取jedis资源
			//资源不为空,则获取对应的value
			if(jedis!=null){
				hash=jedis.hvals(key);
			}
		}finally{
			if(jedis!=null){
				this.returnResource(jedis);
			}
		}
		return hash;
	}


	/**
	 * 方法描述:往redis中注入缓存对象
	 * @param String key 对象的键值
	 * @param String value 键值所对应的值
	 * @return 返回成功与否,成功返回true 失败返回false
	 * date:Nov 18, 2013
	 * add by: houxu@xwtec.cn
	 * @throws SPTException 
	 */
	public boolean setRedisStrValue(String key,String value) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行注入操作 否则返回注入失败
			if(jedis != null) 
				jedis.set(key,value);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
	
	/**
	 * 
	 * 方法描述:插入list数据
	 * date:2014-12-4
	 * add by: fanzhaode@xwtec.cn
	 */
	public boolean rpush(String key,String value)throws SPTException{
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行删除操作 否则返回注入失败
			if(jedis != null)
				 jedis.rpush(key,value);
			else
				flag=false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}

	/**
	 * 方法描述:往redis中注入缓存对象
	 * @param String key 对象的键值
	 * @param String value 键值所对应的值
	 * @param int seconds 键值存储时间,如果为负数,则不设存储上限时间 单位:秒
	 * @return 返回成功与否,成功返回true 失败返回false
	 * date:Nov 18, 2013
	 * add by: houxu@xwtec.cn
	 * @throws SPTException 
	 */
	public boolean setRedisStrValue(String key,String value,int seconds) throws SPTException{
		
		boolean flag = true;//返回标记,默认成功
		
		//如果设置时间为负数,则无上限时间
		if(seconds <= 0){
			this.setRedisStrValue(key, value);
			return flag;
		}
		
		Jedis jedis = null;//声明一个链接对象
		
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行注入操作 否则返回注入失败
			if(jedis != null) {
				//判断是否已经存在,如果已经存在则删除
				if(jedis.exists(key)){
					jedis.del(key);
				}
				//该方法内容为,如果含有相同的key值,则不覆盖.
				jedis.setex(key,seconds,value);
			}
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
	
	/**
	 * 方法描述:删除redis中的缓存
	 * @param 缓存的key值
	 * @return 返回是否成功,成功:true 失败:false
	 * date:Nov 18, 2013
	 * add by: houxu@xwtec.cn
	 * @throws SPTException 
	 */
	public boolean delRedisStrValue(String... keys) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行删除操作 否则返回注入失败
			if(jedis != null)
				jedis.del(keys);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}	
	
	/**
	 * 方法描述:将指定Key的Value原子性的递增1。如果该Key不存在，其初始值为0，在incr之后其值为1,返回递增后的值。
	 * @param String key 键值
	 * @return 返回递增后的值
	 * date:2014-6-4
	 * add by: liuwenbing@xwtec.cn
	 */
	public long incr(String key)throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		long value = 0;//获取的键值所对应的值
		
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 
				value = jedis.incr(key);
			
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 方法描述:将指定Key的Value原子性的递减1。如果该Key不存在，其初始值为0，在decr之后其值为-1。如果Value的值不能转换为整型值，如Hello，该操作将执行失败并返回相应的错误信息。注意：该操作的取值范围是64位有符号整型
	 * @param String key 键值
	 * @return 返回递减后的值
	 * date:2014-7-8
	 * add by: liuwenbing@xwtec.cn
	 */
	public long decr(String key)throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		long value = 0;//获取的键值所对应的值
		
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 
				value = jedis.decr(key);
			
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 方法描述:在指定Key所关联的List Value的头部插入参数中给出的所有Values。
	 * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入
	 * @param String key 对象的键值
	 * @param String... strings 键值所对应的值
	 * @return 返回成功与否,成功返回true 失败返回false
	 * date:Nov 18, 2013
	 * add by: houxu@xwtec.cn
	 */
	public boolean lpush(String key, String... strings)throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行注入操作 否则返回注入失败
			if(jedis != null) 
				jedis.lpush(key,strings);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	} 
	
	/**
	 * 
	 * 方法描述:返回并弹出指定Key关联的链表中的第一个元素，即头部元素，。如果该Key不存，返回null。
	 * @param String key 对象的键值
	 * @return 链表头部的元素
	 * date:2014-11-19
	 * @author wangfengtong@xwtec.cn
	 */
	public String lpop(String key)throws SPTException{
		Jedis jedis = null;//声明一个链接对象
		String value = null;//返回标记,默认成功
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行删除操作 否则返回注入失败
			if(jedis != null)
				value = jedis.lpop(key);
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 
	 * 方法描述:返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素，。如果该Key不存，返回null
	 * @param String key 
	 * @return 对象的键值 链表尾部的元素
	 * date:2014-11-19
	 * @author wangfengtong@xwtec.cn
	 */
	public String rpop(String key)throws SPTException{
		Jedis jedis = null;//声明一个链接对象
		String value = null;//返回标记,默认成功
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行删除操作 否则返回注入失败
			if(jedis != null)
				value = jedis.rpop(key);
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	
	
	/**
	 * 方法描述:查询对应的缓存keys
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	public Set<String> getKeys(String keyPrefix) {
		
		logger.info("RedisUtil.getKeys param: "+keyPrefix);
		
		Jedis jedis = null;//jedis对象
		Set<String> keys = null;//keys列表
		
		try{
			 //获取连接
			 jedis = this.getResource();
			 //根据前台传过来的规则获取缓存key列表
			 if(null != keyPrefix && !"".equals(keyPrefix)){
				 keys = jedis.keys(keyPrefix);
			 }
		}catch (SPTException e) {
			logger.error("[JedisUtil.getKeys]:failed. throw e:" + e.getMessage());
		}finally{
			//使用完毕后将jedis对象归还连接池
			if(jedis != null)
				jedisPool.returnResource(jedis);
		}
		
		return keys;
	}
	
	/**
	 * 
	 * 方法描述:如果在插入的过程用，参数中有的成员在Set中已经存在，该成员将被忽略，而其它成员仍将会被正常插入。
	 * 如果执行该命令之前，该Key并不存在，该命令将会创建一个新的Set，此后再将参数中的成员陆续插入
	 * @param String key Set的名称
	 * @param String members Set的成员值
	 * @return 返回成功与否,成功返回true 失败返回false
	 * date:2014-9-16
	 * @author wangfengtong@xwtec.cn
	 * @throws SPTException 
	 */
	public boolean sadd(String key,String members) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行注入操作 否则返回注入失败
			if(jedis != null) 
				jedis.sadd(key, members);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
	/**
	 * 
	 * 方法描述:插入 Sorted Set类型数据
	 * date:2014-12-5
	 * add by: fanzhaode@xwtec.cn
	 */
	public boolean zadd(String key,double score,String members) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行注入操作 否则返回注入失败
			if(jedis != null) 
				jedis.zadd(key,score, members);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
	/**
	 * 
	 * 方法描述:插入hash类型数据
	 * date:2014-12-5
	 * add by: fanzhaode@xwtec.cn
	 */
	public boolean hmset(String key,Map<String,String> hash) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行注入操作 否则返回注入失败
			if(jedis != null) 
				jedis.hmset(key,hash);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
	
	
	/**
	 * 方法描述:从与Key关联的Set中删除参数中指定的成员，不存在的参数成员将被忽略，如果该Key并不存在，将视为空Set处理
	 * @param key Set集合名称
	 * @param members Set集合中的成员值
	 * @return 返回是否成功,成功:true 失败:false
	 * date:2014-9-16
	 * @author wangfengtong@xwtec.cn 
	 * @throws SPTException 
	 */
	public boolean srem(String key, String... members) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean flag = true;//返回标记,默认成功
		try{
			jedis = this.getResource();//获取资源
			
			//资源不为空则执行删除操作 否则返回注入失败
			if(jedis != null)
				jedis.srem(key, members);
			else
				flag = false;
		}finally{
			//归还资源
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
	
	/**
	 * 方法描述:获取与该Key关联的Set中所有的成员
	 * @param String key 键值
	 * @return 返回Set中所有的成员
	 * date:2014-9-16
	 * @author wangfengtong@xwtec.cn
	 * @throws SPTException 
	 */
	public Set<String> smembers(String key) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		Set<String> value = null;//获取的键值所对应的值
		
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 
				value = jedis.smembers(key);
			
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 方法描述:判断参数中指定成员是否已经存在于与Key相关联的Set集合中
	 * @param String key 键值
	 * @param String member 成员值
	 * @return  true表示已经存在，false表示不存在，或该Key本身并不存在。 
	 * date:2014-9-16
	 * @author wangfengtong@xwtec.cn
	 * @throws SPTException 
	 */
	public boolean sismember(String key,String member) throws SPTException{
		
		Jedis jedis = null;//声明一个链接对象
		boolean value = false;//获取的键值所对应的值
		
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 
				value = jedis.sismember(key, member);
			
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return value;
	}
	/**
	 * 
	 * 方法描述:
	 * @param key 缓存key值
	 * @param field 缓存域
	 * @param value 缓存值
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
	 * 		       如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 * date:2014-12-4
	 * @author wangfengtong@xwtec.cn
	 */
	public Long hset(String key, String field, String value)throws SPTException {
		
		Jedis jedis = null;//声明一个链接对象
		Long flag = 0L;//获取的键值所对应的值
		
		try{
			jedis = this.getResource();//获取jedis资源
			
			//资源不为空,则获取对应的value
			if(jedis != null) 
				flag = jedis.hset(key, field, value);
			
		}finally{
			if(jedis != null)
				this.returnResource(jedis);
		}
		return flag;
	}
}
