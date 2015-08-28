package com.xwtec.xwserver.dao.common.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.xwtec.xwserver.dao.common.ICityDao;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 城市表相关操作. <br>
 * <p>
 * Copyright: Copyright (c) 2014-1-10 上午11:48:59
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class CityDaoImpl implements ICityDao {
	
	@Resource
	private ICommonDao commonDao;
	
	/**
	 * 取出城市表中所有城市信息
	 */
	private static final String QUERY_CITY_LIST = "SELECT t.AREA_CODE areaCode,t.CITY_NAME cityName FROM T_SPT_SYS_CITIES_INFO t";
	
	/**
	 * 方法描述:根据参数取出城市表中地市信息，如果参数为true，表示取出全部，参数为false，表示只取地市不取省
	 * @param containAll 是否取出全部标记
	 * @return 以城市编码为KEY，城市名称为VALUE的城市信息Map
	 * date:2014-1-10
	 * add by: liu_tao@xwtec.cn
	 * @throws SPTException 
	 */
	@Cacheable(value="cityCache",key="#containAll")
	public Map<String,String> queryCityMap(boolean containAll) throws SPTException {
		Map<String,String> result = new LinkedHashMap<String, String>();
		StringBuilder sql = new StringBuilder(QUERY_CITY_LIST);
		if(!containAll) {
			sql.append(" WHERE t.CITY_LEVEL=1");
		}
		sql.append(" ORDER BY t.AREA_CODE");
		List<Map<String, Object>> cityList = commonDao.queryForList(sql.toString());
		for(Map<String, Object> city : cityList) {
			result.put(city.get("areaCode").toString(), city.get("cityName").toString());
		}
		return result;
	}
}