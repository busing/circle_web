package com.xwtec.xwserver.dao.common.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.dao.common.ISysDicDao;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 字典表相关操作. <br>
 * <p>
 * Copyright: Copyright (c) 2014-4-17 上午9:59:29
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class SysDicDaoImpl implements ISysDicDao {
	
	@Resource
	private ICommonDao commonDao;
	
	/**
	 * 方法描述:根据字典类型查询字典信息
	 * @param 字典类型
	 * @return 字典信息：以编码为KEY，名称为VALUE，并以SORT升序排列的字典信息Map
	 * date:2014-4-17
	 * add by: liu_tao@xwtec.cn
	 * @throws SPTException 
	 */
	@Cacheable(value="dicCache",key="#type")
	public Map<String,String> queryDicMap(String type) throws SPTException {
		Map<String,String> result = new LinkedHashMap<String, String>();
		Map<String,String> paramMap = new HashMap<String, String>();
		String sql = "SELECT t.CODE code,t.NAME name FROM T_SPT_SYS_DIC t WHERE t.STATUS=1 AND t.TYPE=:type ORDER BY t.SORT";
		paramMap.put("type", type);
		List<Map<String, Object>> dicList = commonDao.queryForList(sql, paramMap);
		for(Map<String, Object> dic : dicList) {
			result.put(String.valueOf(dic.get("code")), String.valueOf(dic.get("name")));
		}
		return result;
	}
}