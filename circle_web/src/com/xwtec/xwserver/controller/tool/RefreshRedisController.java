package com.xwtec.xwserver.controller.tool;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwtec.xwserver.pojo.tool.CommonNews;
import com.xwtec.xwserver.service.tool.RefreshRedisService;
import com.xwtec.xwserver.util.jedis.JedisUtil;
import com.xwtec.xwserver.util.json.JSONObject;

/**
 * 
 * 刷新缓存. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2014-9-19 下午02:19:46
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author fanzhaode@xwtec.cn
 * @version 1.0.0
 */
@Controller
@RequestMapping("refresh")
public class RefreshRedisController {
	@Resource
	RefreshRedisService refreshService;
	
	@Resource
	JedisUtil jedisUtil;
	
	/**
	 * 日志打印对象
	 */
	private static final Logger logger = Logger.getLogger(RefreshRedisController.class);
	
	
	/**
	 * 
	 * 刷新缓存
	 * date:2014-9-19
	 * add by: fanzhaode@xwtec.cn
	 */
	@ResponseBody
	@RequestMapping("requestDispatcher.action")
	public JSONObject requestDispatcher() {
		JSONObject jsobject=new JSONObject();
		try {
			List<CommonNews> commonNewsList=refreshService.getCommonNews();
			List<Map<String,JSONObject>> mapList=new  ArrayList<Map<String,JSONObject>>();
			for(int i=0;i<commonNewsList.size();i++){
				String id=commonNewsList.get(i).getNid();//编号
				String title=commonNewsList.get(i).getNtitle();//标题
				String ncontent=commonNewsList.get(i).getNcontent();//内容
				String NPOSTDATE=commonNewsList.get(i).getNpostdate();//时间
				String cateid=commonNewsList.get(i).getCateid();
				JSONObject jsonObject = new JSONObject();
				//拼写缓存key值
				String key="APP_COMNEWS_WEB_"+cateid+"_DETAIL_"+id;
				jsonObject.put("ID", id);
				jsonObject.put("TITLE", title);
				jsonObject.put("NCONTENT", ncontent);
				jsonObject.put("NPOSTDATE", NPOSTDATE);
				Map<String,JSONObject> map=new HashMap<String,JSONObject>();
				map.put(key, jsonObject);
				mapList.add(map);
			}
			refreshService.doAction(mapList);
			jsobject.put("status", "success");
			jsobject.put("message", "");
		} catch (Exception e) {
			jsobject.put("status", "error");
			jsobject.put("message", e);
			logger.error(e);
		} 
		return jsobject;

	}
	
	
}
