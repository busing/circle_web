package com.xwtec.xwserver.service.tool.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.constant.ConstantKeys;
import com.xwtec.xwserver.dao.tool.IDatabaseDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.service.tool.IDatabaseService;
import com.xwtec.xwserver.util.DataSourceContextHolder;

/**
 * 查询数据库工具业务逻辑处理类 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-11-14 下午06:16:29
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
@Service
public class DatabaseServiceImpl implements IDatabaseService {

	@Resource
	IDatabaseDao databaseDao ;
	
	/**
	 * 方法描述: 根据sql查询数据库 
	 * @param page 查询参数等等
	 * @return 查询结果
	 * date:2013-11-15
	 * add by: liuwenbing@xwtec.cn
	 * @throws SPTException 
	 */
	public List<Map<String, Object>> getDataBySql(Page page) throws SPTException {
		List<Map<String, Object>> datalist ;
		try {
			//数据源
			String dataResourseType = page.getSearchParam().get("dataResourseType");

			//切换成前台传过来的数据源
			DataSourceContextHolder.switchTo(dataResourseType);
			
			datalist  = databaseDao.getDataBySql(page);
			DataSourceContextHolder.clear();
			return datalist;
		} catch (Exception e) {
			// DataSourceContextHolder.clear();
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:校验sql是否合法
	 * @param model 返回页面的数据 page 查询的数据
	 * @return 通过返回false，不通过返回true
	 * date:2013-11-15
	 * add by: liuwenbing@xwtec.cn
	 */
	public boolean checkSql(ModelAndView modelAndView, Page page) {

		//sql语句
		String sql = page.getSearchParam().get("querySql");
		
		//数据源
		String dataResourseType = page.getSearchParam().get("dataResourseType");
		
		//判断sql是否为空
		if(null == sql || "".equals(sql)){
			modelAndView.addObject("isError",ConstantKeys.WebKey.FAIL_NUMBER);//是否报错 1为报错
			modelAndView.addObject("ErrorMessage",ConstantBusiKeys.Tool.SQL_NOT_EMPTY);
			return true;
		}
		
		//判断数据源是否存在
		if(null == dataResourseType){
			modelAndView.addObject("isError",ConstantKeys.WebKey.FAIL_NUMBER);//是否报错 1为报错
			modelAndView.addObject("ErrorMessage","");
			return true;
		}
		
		// 过滤所有非查询语句
		if (!sql.toUpperCase().trim().startsWith("SELECT")) {
			modelAndView.addObject("isError",ConstantKeys.WebKey.FAIL_NUMBER);//是否报错 1为报错
			modelAndView.addObject("ErrorMessage",ConstantBusiKeys.Tool.SQL_NOT_LEGITIMATE);
			return true;
		}
		
		return false;
    }

}
