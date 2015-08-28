package com.xwtec.xwserver.util.database;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 对Spring的存储过程类进行二次封装. <br>
 * <p>
 * Copyright: Copyright (c) 2014-1-7 下午7:20:38
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class OracleStoreTemplate extends StoredProcedure {
	
	/**
	 * 存储过程调用时的参数
	 */
	private Map<String,Object> params = new HashMap<String, Object>();
	
	/**
	 * 重写父类构造方法
	 * @param jdbcTemplate Spring的jdbcTemplate
	 * @param name 存储过程名称
	 */
	public OracleStoreTemplate(JdbcTemplate jdbcTemplate,String name) {
		super(jdbcTemplate,name);
	}
	
	/**
	 * 方法描述:设置out参数
	 * @param paramName 参数名
	 * @param type 参数类型
	 * date:2014-1-8
	 * add by: liu_tao@xwtec.cn
	 */
	public void setOutParam(String paramName,String type) throws SPTException {
		try {
			super.declareParameter(new SqlOutParameter(paramName, getParamTypeInteger(type)));
		} catch (Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:设置in参数
	 * @param paramName 参数名
	 * @param value 参数值
	 * @param type 参数类型
	 * date:2014-1-8
	 * add by: liu_tao@xwtec.cn
	 */
	public void setInParam(String paramName,Object value,String type) throws SPTException {
		try {
			super.declareParameter(new SqlParameter(paramName, getParamTypeInteger(type)));
			params.put(paramName, value);
		} catch (Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:设置in out参数
	 * @param paramName 参数名
	 * @param value 参数值
	 * @param type 参数类型
	 * date:2014-1-8
	 * add by: liu_tao@xwtec.cn
	 */
	public void setInOutParam(String paramName,Object value,String type) throws SPTException {
		try {
			super.declareParameter(new SqlInOutParameter(paramName, getParamTypeInteger(type)));
			params.put(paramName, value);
		} catch (Exception e) {
			throw new SPTException(e);
		}
	}
	
	public Map<String,Object> execute() {
		if (CommonUtil.isEmpty(super.getSql())) {
			return null;
		}
		super.compile();
		return super.execute(this.params);
	}
	
	/**
	 * 方法描述:根据类型名，从java.sql.Types中获取对应的常量定义的值
	 * @param type 类型名称
	 * @return 对应的常量值
	 * date:2014-1-8
	 * add by: liu_tao@xwtec.cn
	 * @throws SPTException 
	 */
	private int getParamTypeInteger(String type) throws SPTException {
		try {
			// TODO 存储过程中定义的参数类型与java.sql.Types中常量名并不能一一对应，此处以后还需维护，解析更多的对应关系
			// 目前只对varchar2与varchar、number与decimal的对应关系做处理，将类型名称中出现的数字移除
			// 并且要将类型名称转成全大写，才能与常量名对应
			if("NUMBER".equals(type)) {
				type = "DECIMAL";
			}
			Field field = Types.class.getField(type.replaceAll("\\d", "").toUpperCase());
			return Integer.valueOf(field.get(null).toString());
		} catch (Exception e) {
			throw new SPTException(e);
		}
	}
}