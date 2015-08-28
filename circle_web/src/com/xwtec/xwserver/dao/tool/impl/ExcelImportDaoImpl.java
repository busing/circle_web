package com.xwtec.xwserver.dao.tool.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.dao.tool.IExcelImportDao;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 上传excel并把excel的内容解析到数据库中
 * <p>
 * Copyright: Copyright (c) 2013-11-26 下午09:54:12
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class ExcelImportDaoImpl implements IExcelImportDao {
	
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
	public int[] addToDB(String sql,List<Map<String,?>> paramListMap) throws SPTException {
		return commonDao.batchUpdate(sql, paramListMap);
	}
	
}
