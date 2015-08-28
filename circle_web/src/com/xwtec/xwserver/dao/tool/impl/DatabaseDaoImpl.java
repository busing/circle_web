package com.xwtec.xwserver.dao.tool.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.dao.tool.IDatabaseDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 *数据库查询工具数据查询
 * <p>
 * Copyright: Copyright (c) 2013-11-25 上午10:44:18
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class DatabaseDaoImpl implements IDatabaseDao {
	
	@Resource
	ICommonDao commonDao;
	
	/**
	 * 方法描述: 根据sql查询数据库 
	 * @param page 查询参数等等
	 * @return 查询结果
	 * date:2013-11-15
	 * add by: liuwenbing@xwtec.cn
	 * @throws SPTException 
	 */
	public List<Map<String, Object>> getDataBySql(Page page) throws SPTException {

		//sql语句
		String sql = page.getSearchParam().get("querySql");
		
		/**
		 * modified by houxu
		 * date 2013-11-28
		 * 过滤";"识别第一条SQL,并执行
		 */
		sql = sql.split(";")[0];
		
		//根据sql查询结果
		return commonDao.queryForList(sql,null,page);
	}
	
	
	
}
