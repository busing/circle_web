package com.xwtec.xwserver.dao.tool;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.tool.CommonNews;

/**
 * 
 * 刷新缓存dao层. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2014-9-19 下午02:51:38
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author fanzhaode@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class RefreshRedisDao {
	@Resource
	ICommonDao commonDao;
	/**
	 * 查询社保通咨询
	 */
	private static final String  COMMONNEWS_LIST="select nid,appid,cateid,city_code,ntitle,nphoto," +
			"ncontent,to_char(npostdate,'yyyy-mm-dd hh24:mi:ss') npostdate ,is_del from T_APP_COMMON_NEWS " +
			"where npostdate=to_date('2014-09-18 15:12:08','yyyy-mm-dd hh24:mi:ss')";
	
	/**
	 * 
	 * 方法描述:社保通咨询信息集合
	 * date:2014-9-19
	 * add by: fanzhaode@xwtec.cn
	 */
	public List<CommonNews> commonNesList()throws SPTException{
		return commonDao.queryForList(COMMONNEWS_LIST,CommonNews.class);
		
	}
}
