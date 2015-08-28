package com.xwtec.xwserver.controller.tool;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.constant.ConstantKeys;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.service.tool.IRedisService;
import com.xwtec.xwserver.util.jedis.JedisUtil;
import com.xwtec.xwserver.util.json.JSONObject;

/**
 * redis增删改查工具
 * <p>
 * Copyright: Copyright (c) 2013-11-15 下午04:37:56
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
@Controller
@RequestMapping("redis")
public class RedisController {
	
	@Resource
	IRedisService redisService;
	
	
	/**
	 * 日志打印对象
	 */
	private static final Logger logger = Logger.getLogger(RedisController.class);

	@Resource
	JedisUtil jedisUtil;

	
	/**
	 * 方法描述:跳转到redis页面
	 * date:2013-11-14
	 * add by: liuwenbing@xwtec.cn
	 */
	@RequestMapping("init.action")
	public ModelAndView init(Page page,ModelAndView modelAndView){
		//跳转到查询页面
 		modelAndView.setViewName("/tool/redis_key_list.jsp") ;
 		return modelAndView;
	}
	
	/**
	 * 方法描述:根据key规则查询key列表
	 * date:2013-11-14
	 * add by: liuwenbing@xwtec.cn
	 */
	@RequestMapping("querykeyslist.action")
	public ModelAndView queryKeysList(Page page,ModelAndView modelAndView){
		logger.info("RedisController.queryKeysList param: "+page.getSearchParam());
		//根据key模糊查询相对应的缓存keys
		Set<String> keysList = redisService.getKeys(page);
		
		//返回前台数据
		modelAndView.addObject("keysList", keysList);
		
		logger.info("RedisController.queryKeysList returnValue: "+modelAndView);
		//跳转到查询页面
 		modelAndView.setViewName("/tool/redis_key_list.jsp") ;
 		return modelAndView;
	}
	
	/**
	 * 方法描述:	根据缓存key获取缓存详情
	 * @param key 缓存key
	 * @return 缓存详情
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	@RequestMapping("getkeyinfo.action")
	@ResponseBody
	public JSONObject getKeyInfo(Page page){
		logger.info("RedisController.getKeyInfo param: "+page.getSearchParam());
		JSONObject redisDetail = new JSONObject();
		try {
			//判断前台是否传入数据
			if(null != page.getSearchParam() && !page.getSearchParam().isEmpty()){
				
				String key = page.getSearchParam().get("key");//获取缓存key	
				String type=jedisUtil.getType(key);//数据类型
				if(type.equals("string")){//字符串(Strings)
					String val = jedisUtil.getRedisStrValue(key);//根据缓存key获取缓存详情
					redisDetail.put("value", val);//获取根据缓存key获取缓存详情
				}else if(type.equals("list")){//列表(Lists)
					List<String> val = jedisUtil.getRedisListValue(key);
					redisDetail.put("value", val);//获取根据缓存key获取缓存详情
				}else if(type.equals("set")){//集合(Sets)
					Set<String> val=jedisUtil.smembers(key);
					redisDetail.put("value", val);//获取根据缓存key获取缓存详情
				}else if(type.equals("zset")){//有序集合(Sorted Sets)
					Set<String> val=jedisUtil.getRedisSorSetValue(key);
					redisDetail.put("value", val);//获取根据缓存key获取缓存详情
				}else if(type.equals("hash")){//哈希(Hashes)
					List<String> val=jedisUtil.getRedisHashValue(key);
					redisDetail.put("value", val);//获取根据缓存key获取缓存详情
				}
				
				
				redisDetail.put("key", key);//缓存key
				
				redisDetail.put("expireTime", (int)redisService.Ttl(key)/60);//缓存时间分钟为单位
				
				redisDetail.put("status", ConstantKeys.WebKey.SUCCESS);//成功
				redisDetail.put("respCode", ConstantKeys.WebKey.SUCCESS_MSG);//成功原因
			}else{
				redisDetail.put("status", ConstantKeys.WebKey.FAIL_NUMBER);//失败
				redisDetail.put("respCode", ConstantBusiKeys.Tool.FAILED_MSG);//失败原因
			}
		} catch (Exception e) {
			redisDetail.put("status", ConstantKeys.WebKey.FAIL_NUMBER);//失败
			redisDetail.put("respCode", e.getMessage());//失败原因
			logger.error("RedisController.getKeyInfo error:"+e.getMessage());
		}
		logger.info("RedisController.getKeyInfo returnValue:"+redisDetail);
		//返回查询到的数据
 		return redisDetail;
	}
	
	/**
	 * 方法描述:	根据缓存key新增或者修改缓存信息
	 * @param key 缓存key
	 * @return 缓存详情
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	@RequestMapping("addorupdate.action")
	@ResponseBody
	public JSONObject addOrUpdate(Page page){
		logger.info("RedisController.addOrUpdate param: "+page.getSearchParam());
		
		boolean flag = false;//用来返回是成功还是失败false失败true成功
		JSONObject redisDetail = new JSONObject();//接收返回前台信息
		
		try {
			//判断前台是否传入数据
			if(null != page.getSearchParam() && !page.getSearchParam().isEmpty()){
				
				String key = page.getSearchParam().get("key");//缓存key
				String value = page.getSearchParam().get("value");//缓存value
				int expireTime = Integer.parseInt(page.getSearchParam().get("expireTime"));//缓存时间
				
				//根据缓存时间判断怎么塞到缓存中
				if(expireTime == 0){
					flag = jedisUtil.setRedisStrValue(key, value);
				}else{
					flag = jedisUtil.setRedisStrValue(key, value, expireTime*60);
				}
			}
			
			redisDetail.put("flag", flag);//成功
			redisDetail.put("desc", ConstantKeys.WebKey.SUCCESS_MSG);//成功原因
		} catch (Exception e) {
			redisDetail.put("flag", false);//失败
			redisDetail.put("desc", e.getMessage());//失败原因
			logger.error("RedisController.addOrUpdate error:"+e.getMessage());
		}
		logger.info("RedisController.addOrUpdate returnValue:"+redisDetail);
		//返回查询到的数据
 		return redisDetail;
	}
	
	/**
	 * 方法描述:	一个或者多个缓存key删除缓存
	 * @param key 缓存key
	 * @return 成功失败
	 * date:2013-11-19
	 * add by: liuwenbing@xwtec.cn
	 */
	@RequestMapping("delredis.action")
	@ResponseBody
	public JSONObject delRedis(Page page){
		logger.info("RedisController.delRedis param: "+page.getSearchParam());
		
		boolean flag = false;//判断是否删除成功
		JSONObject redisDetail = new JSONObject();//接收返回前台信息
		
		try {
			
			//判断前台是否传入数据
			if(null != page.getSearchParam() && !page.getSearchParam().isEmpty()){
				String key = page.getSearchParam().get("key");//缓存key
				//判断是一个还是多个缓存key
				if(null != key && key.contains(",")){
					String keys[]=key.substring(0, key.length()-1).split(",");//把获取过来的keys分割成数组
					flag = jedisUtil.delRedisStrValue(keys);//删除缓存
				}else{
					flag = jedisUtil.delRedisStrValue(key);//删除缓存
				}
			}
			
			redisDetail.put("flag", flag);//成功
			redisDetail.put("desc", ConstantKeys.WebKey.SUCCESS_MSG);//成功原因
			
		} catch (Exception e) {
			redisDetail.put("flag", flag);//失败
			redisDetail.put("desc", e.getMessage());//失败原因
			logger.error("RedisController.delRedis error:"+e.getMessage());
		}
		
		logger.info("RedisController.delRedis returnValue:"+redisDetail);
		//返回数据
 		return redisDetail;
	}
}
