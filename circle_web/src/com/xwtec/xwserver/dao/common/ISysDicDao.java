package com.xwtec.xwserver.dao.common;

import java.util.Map;

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
public interface ISysDicDao {
	
	/**
	 * 方法描述:根据字典类型查询字典信息
	 * @param 字典类型
	 * @return 字典信息：以编码为KEY，名称为VALUE，并以SORT升序排列的字典信息Map
	 * date:2014-4-17
	 * add by: liu_tao@xwtec.cn
	 * @throws SPTException 
	 */
	public Map<String,String> queryDicMap(String type) throws SPTException;
}