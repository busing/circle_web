package com.xwtec.xwserver.dao.common.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.Assert;
import com.xwtec.xwserver.util.database.OracleStoreTemplate;

/**
 * 
 * 共通DAO层实现类， <br>
 * 为所有Service层提供访问数据库功能
 * <p>
 * Copyright: Copyright (c) 2013-11-27 上午12:12:57
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class CommonDaoImpl extends NamedParameterJdbcDaoSupport implements ICommonDao{
	
	/**
	 * 方法描述:注入dataSource
	 * date:2013-11-15 update by liuwenbing@xwtec.cn  注入多数据源    把 dataSource改为dynamicDataSource
	 * @param 注入的数据源
	 * date:2013-11-7
	 * add by: liu_tao@xwtec.cn     
	 */
	@Resource(name = "dataSource")
	public void setSuperDataSource(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	/**
	 * 方法描述:查询出唯一的一条数据，查询不到返回空，查询出多条抛出异常
	 * @param sql 执行的sql语句
	 * @param paramMap 参数注入的HashMap<String,Object>对象
	 * @return 查询到的结果集
	 * date:2013-11-6
	 * add by: liu_tao@xwtec.cn
	 */
	public Map<String,Object> queryForMap(String sql,Map<String,?> paramMap) throws SPTException{
		try{
			return super.getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:查询出唯一的一条数据，并且将这条数据自动映射成指定的类型返回，查询不到返回空，查询出多条抛出异常.
	 * @param sql 执行的sql语句
	 * @param paramMap 参数注入的HashMap<String,Object>对象
	 * @param clazz 需要自动映射类型的class
	 * @return 查询到的对象
	 * date:2013-11-12
	 * add by: liu_tao@xwtec.cn
	 */
	public <T> T queryForObject(String sql,Map<String,?> paramMap,Class<T> clazz) throws SPTException {
		try{
			return super.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, new BeanPropertyRowMapper<T>(clazz));
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:不带参数查询出唯一的一条数据，并且将这条数据自动映射成指定的类型返回，查询不到返回空，查询出多条抛出异常.
	 * @param sql 执行的sql语句
	 * @param paramMap 参数注入的HashMap<String,Object>对象
	 * @param clazz 需要自动映射类型的class
	 * @return 查询到的对象
	 * date:2013-11-12
	 * add by: liu_tao@xwtec.cn
	 */
	public <T> T queryForObject(String sql,Class<T> clazz) throws SPTException{
		try{
			return this.queryForObject(sql, null,clazz);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:列表查询.
	 * @param sql 执行的sql语句
	 * @param paramMap 参数注入的HashMap<String,Object>对象
	 * @return 查询到的结果集
	 * date:2013-11-6
	 * add by: liu_tao@xwtec.cn
	 */
	public List<Map<String, Object>> queryForList(String sql,Map<String,?> paramMap) throws SPTException{
		try{
			return super.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:不带参数的列表查询
	 * @param sql 执行的sql语句
	 * @return 查询到的结果集
	 * date:2014-1-10
	 * add by: liu_tao@xwtec.cn
	 */
	public List<Map<String,Object>> queryForList(String sql) throws SPTException {
		Map<String,?> paramMap = new HashMap<String, String>();
		return this.queryForList(sql, paramMap);
	}
	
	/**
	 * 方法描述:列表查询(带分页，page为空时则不进行分页).
	 * @param sql 执行的sql语句
	 * @param paramMap 参数注入的HashMap<String,Object>对象
	 * @return 查询到的结果集
	 * date:2013-11-6
	 * add by: liu_tao@xwtec.cn
	 */
	public List<Map<String, Object>> queryForList(String sql,Map<String,?> paramMap,Page page) throws SPTException{
		try{
			if(page != null){
				int count = this.queryForInt(this.getCountSql(sql), paramMap);
				page.setTotalRow(count);
				return this.queryForList(this.getLimitSql(sql, page.getCurrentPage(), page.getRowsPerPage()), paramMap);
			} else {
				return this.queryForList(sql, paramMap);
			}
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:列表查询，并且将每行数据自动映射成对象，形成一个对象列表返回
	 * @param sql 执行的sql语句
	 * @param paramMap 参数注入的HashMap<String,Object>对象
	 * @param clazz 需要自动映射类型的class
	 * @return 查询到的结果集
	 * date:2013-11-12
	 * add by: liu_tao@xwtec.cn
	 */
	public <T> List<T> queryForList(String sql,Map<String,?> paramMap,Class<T> clazz) throws SPTException{
		try{
			return super.getNamedParameterJdbcTemplate().query(sql, paramMap, new BeanPropertyRowMapper<T>(clazz));
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:不带参数的列表查询，并且将每行数据自动映射成对象，形成一个对象列表返回
	 * @param sql 执行的sql语句
	 * @param clazz 需要自动映射类型的class
	 * @return 查询到的结果集
	 * date:2013-11-12
	 * add by: liu_tao@xwtec.cn
	 */
	public <T> List<T> queryForList(String sql,Class<T> clazz) throws SPTException{
		try{
			return super.getNamedParameterJdbcTemplate().query(sql, new BeanPropertyRowMapper<T>(clazz));
		} catch(Exception e){
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:列表查询(带分页，page为空时则不进行分页)，并且将每行数据自动映射成对象，形成一个对象列表返回，。
	 * @param selectSql - 结果查询语句
	 * @param paramMap - 查询参数
	 * @param page - 分页信息
	 * @param clazz 需要自动映射类型的class
	 * @return 查询到的结果集
	 * date:2013-11-7
	 * add by: liu_tao@xwtec.cn
	 */
	public <T> List<T> queryForList(String selectSql, Map<String,?> paramMap, Page page,Class<T> clazz) throws SPTException{
		try {
			if(page != null){
				int count = this.queryForInt(this.getCountSql(selectSql), paramMap);
				page.setTotalRow(count);
				return this.queryForList(this.getLimitSql(selectSql, page.getCurrentPage(), page.getRowsPerPage()), paramMap,clazz);
			} else {
				return this.queryForList(selectSql, paramMap,clazz);
			}
		} catch(Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:count,sum,max等查询
	 * @param sql - 查询语句
	 * @param paramMap - 参数注入的对象
	 * @return 查询到的count,sum,max等结果
	 * date:2013-11-7
	 * add by: liu_tao@xwtec.cn
	 */
	@SuppressWarnings("deprecation")
	public int queryForInt(String sql, Map<String,?> paramMap) throws SPTException{
		try {
			return super.getNamedParameterJdbcTemplate().queryForInt(sql, paramMap);
		} catch(Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:普通DML操作,如insert,update,delete
	 * @param sql - 查询语句
	 * @param paramMap - 参数注入的对象
	 * @return 普通DML操作影响到的条数
	 * date:2013-11-11
	 * add by: liu_tao@xwtec.cn
	 */
	public int update(String sql, Map<String,?> paramMap) throws SPTException {
		try {
			return super.getNamedParameterJdbcTemplate().update(sql, paramMap);
		} catch(Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:批量普通DML操作,如insert,update,delete
	 * @param sql 执行的sql语句
	 * @param paramListMap 批量参数
	 * @return 批量执行普通DML操作时每条语句影响的条数数组，数组中每个int数字含义解释：0：没有影响到记录，-2有影响（批量执行不能精确知道影响的条数）
	 * date:2013-11-19
	 * add by: liu_tao@xwtec.cn
	 */
	@SuppressWarnings("unchecked")
	public int[] batchUpdate(String sql, List<Map<String,?>> paramListMap) throws SPTException {
		try {
			return super.getNamedParameterJdbcTemplate().batchUpdate(sql, paramListMap.toArray(new HashMap[0]));
		} catch(Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述: 查询下个sequence值
	 * @param sequenceName	- sequence名称
	 * @return sequence值
	 * date:2013-11-19
	 * add by: liu_tao@xwtec.cn
	 */
	@SuppressWarnings("deprecation")
	public long querySequenceNextValue(String sequenceName) throws SPTException{
		try {
			return super.getJdbcTemplate().queryForLong(String.format("select %s.nextval from dual", sequenceName));
		} catch(Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述: 批量获取sequence值
	 * @param sequenceName	- sequence名称
	 * @param size			- 一次获取多少个?
	 * @return 批量的sequence值列表
	 * date:2013-11-19
	 * add by: liu_tao@xwtec.cn
	 */
	public List<Long> querySequenceNextValues(String sequenceName, final int size) throws SPTException{
		try {
			return this.getJdbcTemplate().query(String.format("select %s.nextval from dual connect by rownum <= ?", sequenceName), new Object[]{size}, new RowMapper<Long>(){
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
		} catch(Exception e) {
			throw new SPTException(e);
		}
	}
	
	/**
	 * 方法描述:调用存储过程
	 * @param name 存储过程名称，区分大小写，创建存储过程时用的名称
	 * @param params 存储过程所需的in参数值，key为参数名，value为参数值
	 * @return 存储过程执行后的out参数获取值
	 * date:2014-1-8
	 * add by: liu_tao@xwtec.cn
	 * @throws SPTException 
	 */
	public Map<String,Object> execProcedure(String name,Map<String,Object> params) throws SPTException {
		OracleStoreTemplate oracleStoreTemplate = new OracleStoreTemplate(super.getJdbcTemplate(),name);
		String sql = "select u.TEXT from user_source u where name=:name and type='PROCEDURE'";
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("name", name.toUpperCase());
		// 根据存储过程名，查询存储过程的定义语句，后面要根据定义语句解析存储过程的参数
		List<Map<String, Object>> procDefList = this.queryForList(sql, paramMap);
		StringBuilder procDef = new StringBuilder();
		// 参数字符串
		String paramStr = null;
		// 存储过程定义存在数据库中是多条数据，每条数据是在创建存储过程时定义的一行语句
		// 将这些语句拼接成一个完整的字符串方便解析参数
		for(Map<String, Object> procDefTemp : procDefList) {
			procDef.append(procDefTemp.get("TEXT").toString().replace("\n", ""));
		}
		// 从第一次找到存储过程名称处截断
		// (为防止存储过程定义语句再次出现与存储过程名称相同的单词，接下来会在第一次出现存储过程名称的后面寻找参数，后面再出现存储过程名称忽略)
		procDef.delete(0, procDef.indexOf(name));
		// 查询存储过程的参数
		// 正则表达式解释：现在字符串的开始处一定是存储过程名
		// 所以现在正则寻找的是从字符串开始处并且以存储过程名开始，后面跟不定个数的空格(JDK1.6在断言内使用*来匹配不定个数会报错，只能修改为定数，此处定数的数值需要足够大)，
		// 后找到"("，截取"("到第一次出现")"中间的字符串，如果从存储过程名开始后不是跟空格，找到其他字符再找到"("不算，说明存储过程没有参数
		Pattern pattern = Pattern.compile("(?<=^" + name + "\\s{0,999999}[(]).*?(?=[)])");
		Matcher matcher = pattern.matcher(procDef);
		if(matcher.find(0)) {
			paramStr = matcher.group(0);
		}
		// 参数字符串不为空存储过程才有参数
		if(paramStr != null) {
			// 开始解析参数
			String[] paramArr = paramStr.split(",");
			for(String param : paramArr) {
				// 参数的格式为：参数名在最前，参数类型在最后，中间是"IN"、"OUT"、"IN OUT"或者为空来标识当前参数是输入或输出参数
				// 参数名、输入输出类型与参数类型中间用空格分开
				// 所以以空格切分，取第一个为参数名，取最后一个为参数类型
				String[] paramArrTemp = param.split(" ");
				if(param.toUpperCase().indexOf("OUT") != -1) {
					// 输出参数
					oracleStoreTemplate.setOutParam(paramArrTemp[0], paramArrTemp[paramArrTemp.length - 1]);
				} else if(param.toUpperCase().indexOf("IN") != -1) {
					// 输入参数
					oracleStoreTemplate.setInParam(paramArrTemp[0], params.get(paramArrTemp[0]), paramArrTemp[paramArrTemp.length - 1]);
				} else if(param.toUpperCase().indexOf("IN OUT") != -1) {
					// 输入输出参数
					oracleStoreTemplate.setInOutParam(paramArrTemp[0], params.get(paramArrTemp[0]), paramArrTemp[paramArrTemp.length - 1]);
				} else {
					// 如果没有标识输入输出类型，默认为输入参数
					oracleStoreTemplate.setInParam(paramArrTemp[0], params.get(paramArrTemp[0]), paramArrTemp[paramArrTemp.length - 1]);
				}
			}
		}
		return oracleStoreTemplate.execute();
	}
	
	/**
	 * 方法描述:获取分页sql语句
	 * @param sql 执行的sql语句
	 * @param currentPage 当前的页码
	 * @param pageSize 每页显示的条数
	 * @return 拼接成带分页查询的sql语句
	 * date:2013-11-7
	 * add by: liu_tao@xwtec.cn
	 */
	private String getLimitSql(String sql, int currentPage, int pageSize) {
		Assert.isTrue(currentPage > 0, "'currentPage' 不能为负数!");
		Assert.isTrue(pageSize > 0, "'pageSize' 不能为负数!");
		String s = sql + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
		return s;
	}
	
	/**
	 * 方法描述:获取总记录数sql语句
	 * @param sql 执行的sql语句
	 * @return 获取总记录数sql语句
	 * date:2013-11-7
	 * add by: liu_tao@xwtec.cn
	 */
	private String getCountSql(String sql) {
		return String.format("select count(1) from (%s) as total", sql);
	}
	
	
	/**
	 * 获取表格最后插入数据的id
	 * @param tableName
	 * @param idColume
	 * @return
	 * @throws SPTException
	 */
	public int getLastId(String tableName,String idColume) throws SPTException
	{
		
		String sql="select max("+idColume+") from "+tableName;
		return this.queryForInt(sql,null);
	}
}