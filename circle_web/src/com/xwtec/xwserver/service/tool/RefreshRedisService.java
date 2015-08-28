package com.xwtec.xwserver.service.tool;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xwtec.xwserver.dao.tool.RefreshRedisDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.tool.CommonNews;
import com.xwtec.xwserver.util.jedis.JedisUtil;
import com.xwtec.xwserver.util.json.JSONObject;


/**
 * 
 * 刷新缓存数据. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2014-9-19 下午02:08:33
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author fanzhaode@xwtec.cn
 * @version 1.0.0
 */
@Service("RefreshRedisService")
public class RefreshRedisService {
	@Resource 
	RefreshRedisDao refreshDao;
	@Resource
	JedisUtil jedisUtil;
	/**
	 * 注入日志
	 */
	public static final Logger logger=Logger.getLogger(RefreshRedisService.class);
	
	/**
	 * 
	 * 插入缓存信息
	 * date:2014-9-19
	 * add by: fanzhaode@xwtec.cn
	 */
	public boolean doAction(List<Map<String,JSONObject>> mapList) throws Exception{
		boolean flag=false;
		for(int i=0;i<mapList.size();i++){
			Map<String,JSONObject> map=mapList.get(i);
			for(Entry<String,JSONObject> entry : map.entrySet()){
				String key=entry.getKey();
				String value=entry.getValue().toString();
				flag=jedisUtil.setRedisStrValue(key, value);
				if(flag==false){
					throw new Exception ("RefreshRedisService：+插入缓存错误");
				}
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * 获取社保通咨询数据集合: 
	 * date:2014-9-19
	 * add by: fanzhaode@xwtec.cn
	 */
	public List<CommonNews> getCommonNews()throws SPTException{
		
		return refreshDao.commonNesList();
	}
	

}
