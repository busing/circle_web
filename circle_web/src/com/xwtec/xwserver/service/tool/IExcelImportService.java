package com.xwtec.xwserver.service.tool;

import java.util.List;

import com.xwtec.xwserver.exception.SPTException;

/**
 * 上传excel并把excel的内容解析到数据库中
 * <p>
 * Copyright: Copyright (c) 2013-11-26 下午09:49:23
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
public interface IExcelImportService {
	
	/**
	 * 方法描述:把excel的数据插入数据库
	 * @param 前台传过来的数据
	 * @return 成功条数
	 * date:2013-11-26
	 * add by: liuwenbing@xwtec.cn
	 * @throws SPTException 
	 */
	public int addToDB(List<String[]> list,String name,String dataResourseType) throws SPTException;


}
