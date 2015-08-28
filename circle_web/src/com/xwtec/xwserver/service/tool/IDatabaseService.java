package com.xwtec.xwserver.service.tool;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 *数据库查询工具业务逻辑处理接口
 * <p>
 * Copyright: Copyright (c) 2013-11-15 下午03:24:01
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
public interface IDatabaseService {
	
	/**
	 * 方法描述: 根据sql查询数据库 
	 * @param page 查询参数等等
	 * @return 查询结果
	 * date:2013-11-15
	 * add by: liuwenbing@xwtec.cn
	 * @throws SPTException 
	 */
	public List<Map<String, Object>> getDataBySql(Page page) throws SPTException;

	/**
	 * 方法描述:校验sql是否合法
	 * @param model 返回页面的数据 page 查询的数据
	 * @return 通过返回false，不通过返回true
	 * date:2013-11-15
	 * add by: liuwenbing@xwtec.cn
	 */
	public boolean checkSql(ModelAndView modelAndView, Page page);

}
