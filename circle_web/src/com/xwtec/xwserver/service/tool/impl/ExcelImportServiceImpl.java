package com.xwtec.xwserver.service.tool.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xwtec.xwserver.constant.ConstantKeys;
import com.xwtec.xwserver.dao.tool.IExcelImportDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.service.tool.IExcelImportService;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.DataSourceContextHolder;

/**
 * 上传excel并把excel的内容解析到数据库中
 * <p>
 * Copyright: Copyright (c) 2013-11-26 下午09:53:01
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
@Service
public class ExcelImportServiceImpl implements  IExcelImportService{

	@Resource
	IExcelImportDao excelImportDao ;
	
	/**
	 * 日志打印对象
	 */
	private static final Logger logger = Logger.getLogger(ExcelImportServiceImpl.class);

	/**
	 * 方法描述:把excel的数据插入数据库
	 * @param 前台传过来的数据
	 * @return 成功条数
	 * date:2013-11-26
	 * add by: liuwenbing@xwtec.cn
	 * @throws SPTException 
	 */
	public int addToDB(List<String[]> list,String name,String dataResourseType) throws SPTException {
		
		try {
			//切换成前台传过来的数据源
			DataSourceContextHolder.switchTo(dataResourseType);
			
			List<Object> titleList = new ArrayList<Object>();
			//拼接sql
			StringBuilder sql = new StringBuilder("insert into "+name+" ( ");
			
			if(list.size()>0){
				int i=0;
				String [] title = list.get(0);
				int size = title.length;
				for(Object key : title){
					i++;
					if(i != size){
						sql.append(key+" , ");
					}else{
						sql.append(key+" ) ");
					}
					titleList.add(key);
				}
			}
			sql.append("values ( ");
			//获取title数量
			int listSize = titleList.size();
			for(int i=0;i<listSize;i++){
				if(i != listSize-1){
					sql.append(":"+titleList.get(i)+" , ");
				}else{
					sql.append(":"+titleList.get(i)+" ) ");
				}
			}
			logger.info("ExcelImportServiceImpl.addToDB sql"+sql);
			
			//拼接数据
			List<Map<String,?>> paramListMap = new ArrayList<Map<String,?>>();
			int k = 0;
			for(Object[] objVal : list){
				Map<String,Object> map = new HashMap<String,Object>();
				int i=0;
				if( k != 0 ){
					for(int j=0;j<listSize;j++){
						map.put(CommonUtil.changeNullToEmpty((String)titleList.get(j)), objVal[i]);
						i++;
					}
				}
				k++;
				if(!map.isEmpty()){
					paramListMap.add(map);
				}
			}
			logger.info("ExcelImportServiceImpl.addToDB param"+paramListMap.toString());
			/**
			 * 批量加入数据库
			 */
			int [] num = excelImportDao.addToDB(sql.toString(), paramListMap);
			
			int count = 0;//添加 成功条数
			
			for(int no : num ){
				//如果返回为-2就是添加成功
				if(no == ConstantKeys.DataBase.BATCH_INSERT_DATA_SUCCESS_FLAG){
					count++;
				}
			}
			DataSourceContextHolder.clear();
			return count;
		} catch (Exception e) {
			DataSourceContextHolder.clear();
			throw new SPTException(e);
		}
	}
	

}
