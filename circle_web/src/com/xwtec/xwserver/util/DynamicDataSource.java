//package com.xwtec.xwserver.util;
//
//import java.sql.SQLException;
//
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
///**
// * 动态数据源
// * Copyright: Copyright (c) 2013-11-15 上午10:48:35
// * <p>
// * Company: 欣网视讯
// * <p>
// * @author liuwenbing@xwtec.cn
// * @version 1.0.0
// */
//public class DynamicDataSource extends AbstractRoutingDataSource {
//	private static final Logger log = Logger.getLogger(DynamicDataSource.class);
//
//	@Override
//	protected Object determineCurrentLookupKey() {
//		String curDS = DataSourceContextHolder.getCurrentDataSource();
//		log.debug("switch to DS:" + curDS);
//		return curDS;
//	}
//
//	@Override
//	public <T> T unwrap(Class<T> iface) throws SQLException {
//		return null;
//	}
//
//	@Override
//	public boolean isWrapperFor(Class<?> iface) throws SQLException {
//		return false;
//	} 
//}
