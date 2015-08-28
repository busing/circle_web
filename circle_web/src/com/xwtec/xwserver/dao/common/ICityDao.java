package com.xwtec.xwserver.dao.common;

import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.xwtec.xwserver.exception.SPTException;

/**
 * 城市表相关操作.  <br>
 * <p>
 * Copyright: Copyright (c) 2014-1-10 下午12:24:06
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public interface ICityDao {
	/**
	 * 方法描述:根据参数取出城市表中地市信息，如果参数为true，表示取出全部，参数为false，表示只取地市不取省
	 * @param containAll 是否取出全部标记
	 * @return 以城市编码为KEY，城市名称为VALUE的城市信息Map
	 * date:2014-1-10
	 * add by: liu_tao@xwtec.cn
	 * @throws SPTException 
	 */
	@Cacheable(value="cityCache",key="#containAll")
	public Map<String,String> queryCityMap(boolean containAll) throws SPTException;
}
