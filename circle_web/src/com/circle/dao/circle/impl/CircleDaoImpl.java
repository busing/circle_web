package com.circle.dao.circle.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.constant.SystemDict;
import com.circle.dao.circle.ICircleDao;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.circle.CircleMember;
import com.circle.pojo.user.User;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 
 * 圈子DAO接口实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-12 下午10:09:25
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
@Repository
public class CircleDaoImpl implements ICircleDao{
	
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(CircleDaoImpl.class) ;
	
	/**
	 * 增加圈子信息
	 */
	public static final String ADD_CIRCLE = "insert into t_circle(name,description,head_path,join_type,notice,create_time,create_userid,address,province,city,longitude,latitude,status,post_week,post_am_pm) values " +
			"(:name,:description,:headPath,:joinType,:notice,sysdate(),:createUserid,:address,:province,:city,:longitude,:latitude,"+SystemDict.CIRCLE_STATUS_AUDIT+",:postWeek,:postAmPm)" ;
	
	private static final String ADD_CIRCLE_MEMBER = "insert into t_circle_member(circle_id,user_id,identity_type,join_time) values(:circleId,:userId,:identityType,sysdate())";
	
	/**
	 * 更新圈子信息
	 */
	public static final String UPDATA_CIRCLE = "UPDATE t_circle role SET name=:name,description=:description,head_path=:headPath,join_type=:joinType," +
			"issue_time=:issueTime,issue_address=:issueAddress,address=:address,province=:province,city=:city,longitude=:longitude,latitude=:latitude " +
			"WHERE id=:id" ;
	
	/**
	 * 更新圈子地图信息
	 */
	public static final String UPDATA_CIRCLE_MAP = "UPDATE t_circle role SET longitude=:longitude,latitude=:latitude WHERE id=:id" ;
	
	/**
	 * 更改圈子状态
	 */
	public static final String UPDATE_CIRCLE_STATUS = "UPDATE t_circle SET status=:status,audit_msg=:auditMsg where id = :id";
	
	/**
	 * 根据圈子ID和用户查询圈子信息
	 */
	public static final String QUERY_CIRCLE_BY_ID = "select t1.id,t1.name,t1.description,t1.head_path headPath,t1.join_type joinType,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime,t1.issue_address issueAddress," +
			"t1.create_time createTime,t3.name createUser,t3.id createUserId,t1.address,t1.province,t1.city,t1.longitude,t1.latitude,t1.status,date_format(t1.end_time,'%Y-%m-%d %H:%i') endTime " +
			" t1.invite_user_id inviteUserId,t1.invite_code inviteCode,t1.weixin_name weixinName,t1.weixin_image weixinImage "+
			"from t_circle t1,t_circle_member t2,t_user t3 where t1.id = t2.circle_id and t1.create_userid=t3.id and t1.id = :id and t2.user_id=:userId and t1.status ="+SystemDict.CIRCLE_STATUS_AUDIT_PASS;
	
	
	/**
	 * 查询圈子列表信息
	 */
	public static final String QUERY_CIRCLE_LIST = "select t1.id,t1.name,t1.join_type joinType,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime,t1.issue_address issueAddress," +
	"t1.create_time createTime,t2.name createUser,t2.id createUserId, t2.mobilephone mobilePhone,t1.address,t1.province,t1.city,t1.longitude,t1.latitude,t1.status,t1.head_path headPath,date_format(t1.end_time,'%Y-%m-%d %H:%i') endTime  " +
	"from t_circle t1,t_user t2 where t1.status ="+SystemDict.CIRCLE_STATUS_AUDIT_PASS+" and t1.create_userid = t2.id";
	
	
	/**
	 * 查询用户加入的圈子
	 * QUERY_JOIN_CIRCLE_LIST 
	 */
	public static final String QUERY_JOIN_CIRCLE_LIST= "select t1.id,t1.name,t1.join_type joinType,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime,t1.issue_address issueAddress," +
			"t1.create_time createTime,t3.id createUserId,t1.address,t1.province,t1.city,t1.longitude,t1.latitude,t1.status,t1.head_path headPath,date_format(t1.end_time,'%Y-%m-%d %H:%i') endTime  " +
			"from t_circle t1,t_circle_member t2,t_user t3 where t1.id = t2.circle_id and t3.id=t1.create_userid and t2.user_id=:userId and t1.status ="+SystemDict.CIRCLE_STATUS_AUDIT_PASS;
	

	/**
	 * 根据圈子ID和用户查询圈子信息
	 */
	public static final String QUERY_JOIN_CIRCLE_BY_ID =  "select t1.id,t1.name,t1.join_type joinType,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime,t1.issue_address issueAddress," +
			"t1.create_time createTime,t3.id createUserId,t1.address,t1.province,t1.city,t1.longitude,t1.latitude,t1.status,t1.head_path headPath,date_format(t1.end_time,'%Y-%m-%d %H:%i') endTime  " +
			"from t_circle t1,t_circle_member t2,t_user t3  where  t1.id = t2.circle_id and t3.id=t1.create_userid and t2.user_id=:userId and t1.id=:circleId and t1.status ="+SystemDict.CIRCLE_STATUS_AUDIT_PASS;
	
	/**
	 * 查询圈子列表信息
	 */
	public static final String QUERY_CIRCLE_LISTBYPOINT = "SELECT t1.id,t1.name,t1.description,t1.head_path headPath,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime, t1.issue_address issueAddress,t1.longitude,t1.latitude,t1.status,t2.name createUser from t_circle t1 left JOIN t_user t2 on t2.id = t1.create_userid " +
			" where  t1.status ="+SystemDict.CIRCLE_STATUS_AUDIT_PASS;
	
	/**
	 * 查询圈子历史信息列表
	 */
	public static final String QUERY_CIRCLE_HISTORY_LIST = "select t1.id,t2.name,t1.intro,t1.issue_time,t1.create_time,t1.status,t3.name username,date_format(t3.end_time,'%Y-%m-%d %H:%i') endTime " +
			"from t_circle_history t1,t_circle t2,t_user t3 where t1.status <> 0 and t1.circle_id=t2.id and t1.create_userid = t3.id";
	
	/**
	 * 根据圈子历史信息ID查询具体信息
	 */
	public static final String QUERY_CIRCLE_HISTORY_BY_ID = "select t1.id,t2.name,t1.intro,t1.issue_time,t1.issue_address,t1.create_time,t1.status,t1.audit_msg,t3.name username" +
			" from t_circle_history t1,t_circle t2,t_user t3 where t1.status <> 0 and t1.circle_id=t2.id and t1.create_userid = t3.id and t1.id=:id";
	
	/**
	 * 修改圈子历史信息状态
	 */
	public static final String UPDATE_CIRCLE_HISTORY_STATUS = "update t_circle_history set status=:status,audit_msg=:auditMsg where id=:id";

	/**
	 * 用户加入圈子
	 */
	public static final String ADD_USER_TO_CIRCLE = "insert into  t_circle_member(circle_id,user_id,identity_type,join_time) values (:circleId,:userId,:type,now())";

	
	/**
	 * 用户加入圈子(审核)
	 */
	public static final String ADD_USER_TO_CIRCLE_WITH_EXAMINE  = "insert into  t_circle_member_audit (circle_id,user_id,identity_type,join_time,audit_status) values (:circleId,:userId,:type,now(),:status)";

	
	/**
	 * 查询圈子加入状态
	 */
	public static final String QUERY_CIRCLE_BY_CIRCLEID = "select t1.id,t1.name,t1.description,t1.head_path headPath,t1.join_type joinType,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime, t1.issue_address issueAddress, t1.create_time createTime,t1.address,t1.province,t1.city,t1.longitude,t1.latitude,t1.status,t1.create_userid createUserId,date_format(t1.end_time,'%Y-%m-%d %H:%i') endTime,t2.weixin_name weixinName,t2.weixin_image weixinImage from t_circle t1,t_user t2 where t1.create_userid=t2.id and t1.id =:circleId";
	
	
	/**
	 * 查询首页第一次加载 初始化的农场
	 * QUERY_INIT_MAP_CIRCLE 
	 */
	public static final String QUERY_INIT_MAP_CIRCLE = "select t1.id,t1.name,t1.description,t1.head_path headPath,t1.join_type joinType,date_format(t1.issue_time,'%Y-%m-%d %H:%i') issueTime, t1.issue_address issueAddress, t1.create_time createTime,t1.address,t1.province,t1.city,t1.longitude,t1.latitude,t1.status,t1.create_userid createUserId,t1.end_time endTime,t2.name createUser from t_circle t1 left JOIN t_user t2 on t2.id = t1.create_userid where t1.status="+SystemDict.CIRCLE_STATUS_AUDIT_PASS;

	/**
	 * 查询圈子成员信息
	 */
	public static final String QUERY_CIRCLEMEMBER_BY_CIRCLEID_AND_USERID = "select count(*) from t_circle_member where user_id=:userId and circle_id =:circleId";

	
	/**
	 * 查询圈子（审核）成员信息
	 */
	public static final String QUERY_CIRCLEMEMBERAUDIT_BY_CIRCLEID_AND_USERID = "select count(*) from t_circle_member_audit where user_id=:userId and circle_id =:circleId";
	
	/**
	 * 查询用户的圈子列表
	 */
	private static final String QUERY_CIRCLES_BY_USERID = "select c.id,c.`name`,c.description,c.head_path,date_format(c.issue_time,'%Y-%m-%d %H:%i') issueTime,c.issue_address,c.notice,c.create_time,"
		+ "c.end_time,m.join_time,c.create_userid,u.mobilephone,case c.create_userid when m.user_id then 1 else 0 end isSelfCreate "
		+ "from t_circle c,t_circle_member m,t_user u where u.id = c.create_userid and c.id = m.circle_id and m.user_id = :userId and c.status="+SystemDict.CIRCLE_STATUS_AUDIT_PASS;
	
	
	/**
	 * 查询圈子本次购物人数
	 * QUERY_CIRLCE_BUYNUM 
	 */
	public static final String QUERY_CIRLCE_BUYNUM="select count(1) from (select distinct(o.userid) from "+CircleTable.ORDER.getTableName()+" o,"+CircleTable.CIRCLE.getTableName()
			+" c where o.circle_id=c.id and date_format(o.end_time,'%Y-%m-%d %H:%i:%s')=date_format(c.end_time,'%Y-%m-%d %H:%i:%s') and c.id=:circleId ) r";

	/**
	 *  根据圈子ID查询圈子、用户信息(我的农场查看、编辑农场信息)
	 */
	private static final String QUERY_CIRCLE_USER_BY_CIRCLE_ID = "select c.id,c.description,c.notice,u.`name` createUser,u.mobilephone mobilePhone from t_circle c,t_user u where c.create_userid = u.id and c.id = :circleId";
	
	/**
	 * 成员退出圈子
	 */
	private static final String MEMBER_EXIT_CIRCLE = "delete from t_circle_member where circle_id = :circleId and user_id = :userId";
	
	/**
	 * 根据圈子ID查询用户列表
	 */
	private static final String QUERY_USERS_BY_CIRCLE_ID = "select u.id,u.`name`,u.mobilephone mobilePhone,u.head_image from t_circle_member m,t_user u where m.user_id = u.id and m.circle_id = :circleId order by m.identity_type desc";
	
	/**
	 * 保存圈子简介和公告
	 */
	private static final String SAVE_CIRCLE_INFO = "update t_circle set notice = :notice,description = :description where id = :circleId";
	
//	private static final String SAVE_CIRCLE_PUBLISH_INFO = "insert into t_circle_history(circle_id,type,intro,issue_time,issue_address,end_time,create_userid,create_time,`status`,audit_msg) "
//		+ "values(:circleId,:type,:intro,:issueTime,:issueAddress,:endTime,:createUserId,now(),:status,:auditMsg)";
	
	private static final String SAVE_CIRCLE_PUBLISH_INFO = "update t_circle set issue_time=:issueTime,issue_address=:issueAddress,end_time=:endTime where id=:circleId";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;

	/**
	 * 
	 * 方法描述:添加圈子信息
	 * @param circle 圈子实体信息
	 * @return 1添加成功，否则失败
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public int addCicle(Circle circle) throws SPTException{
		logger.debug("[CircleDaoImpl.addCicle] start...") ;
		
		/**
		 * 增加圈子信息返回结果参数默认为 -1 （失败）
		 */
		int resultNum = -1 ;
		
		/**
		 * 增加时各字段对应值 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("name", circle.getName()) ;
		paramMap.put("description", circle.getDescription()) ;
		paramMap.put("headPath", circle.getHeadPath()) ;   
		paramMap.put("joinType", circle.getJoinType());
		paramMap.put("issueAddress", circle.getIssueAddress());
		paramMap.put("createUserid", circle.getCreateUser());
		paramMap.put("issueTime", circle.getIssueTime());
		paramMap.put("endTime", circle.getEndTime());
		paramMap.put("address", circle.getAddress());
		paramMap.put("notice", circle.getNotice());
		paramMap.put("province", circle.getProvince());
		paramMap.put("city", circle.getCity());
		paramMap.put("longitude", circle.getLongitude());
		paramMap.put("latitude", circle.getLatitude());
		paramMap.put("postWeek", circle.getPostWeek()+"");
		paramMap.put("postAmPm", circle.getPostAmPm()+"");
		
		resultNum = commonDao.update(ADD_CIRCLE, paramMap);
		int id = commonDao.getLastId(CircleTable.CIRCLE.getTableName(), "id");
		circle.setId(id+"");
		logger.debug("[CircleDaoImpl.addCicle] end...") ;
		return resultNum;
	}
	
	/**
	 * 新增圈子成员
	 * @param member
	 * @return Integer
	 * @throws Exception
	 */
	public Integer addCircleMember(CircleMember member) throws SPTException{
		logger.debug("[CircleDaoImpl.addCicle] start...") ;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("circleId", member.getCircleId());
		paramMap.put("userId", member.getUserId());
		paramMap.put("identityType", member.getIdentityType());
		int res = commonDao.update(ADD_CIRCLE_MEMBER, paramMap);
		logger.debug("[CircleDaoImpl.addCicle] end...") ;
		return res;
	}

	/**
	 * 方法描述:根据ID修改圈子信息
	 * @param circle 圈子实体信息
	 * @return resultNum-受影响行数：1为更新成功，其他为更新失败
	 * date:2014-12-14
	 * add by: wangfengtong@xwtec.cn
	 * @throws SPTException 
	 */
	public int updateCircle(Circle circle) throws SPTException{
		logger.debug("[CircleDaoImpl.updateCircle] start...") ;
		
		/**
		 * 修改角色返回结果参数默认为 -1 （失败）
		 */
		int resultNum = -1 ;
		
		/**
		 * 更新时各字段对应值 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("id", circle.getId());
		paramMap.put("name", circle.getName()) ;
		paramMap.put("description", circle.getDescription()) ;
		paramMap.put("headPath", circle.getHeadPath()) ;   
		paramMap.put("joinType", circle.getJoinType());
		paramMap.put("issueAddress", circle.getIssueAddress());
		paramMap.put("createUserid", circle.getCreateUser());
		paramMap.put("address", circle.getAddress());
		paramMap.put("province", circle.getProvince());
		paramMap.put("city", circle.getCity());
		paramMap.put("longitude", circle.getLongitude());
		paramMap.put("latitude", circle.getLatitude());
		
		resultNum = commonDao.update(UPDATA_CIRCLE, paramMap);
		logger.debug("[CircleDaoImpl.updateCircle] end...") ;
		return resultNum;
	}

	
	/**
	 * 
	 * 方法描述:修改圈子地图经纬度信息
	 * @param circle圈子实体信息
	 * @return 等于1修改成功，否则失败
	 * date:2014-12-17
	 * @author wangfengtong@xwtec.cn
	 */
	public int updateCircleMap(Circle circle) throws SPTException{
		logger.debug("[CircleDaoImpl.updateCircleMap] start...") ;
		
		/**
		 * 修改角色返回结果参数默认为 -1 （失败）
		 */
		int resultNum = -1 ;
		
		/**
		 * 更新时各字段对应值 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("id", circle.getId());
		paramMap.put("longitude", circle.getLongitude());
		paramMap.put("latitude", circle.getLatitude());
		
		resultNum = commonDao.update(UPDATA_CIRCLE_MAP, paramMap);
		logger.debug("[CircleDaoImpl.updateCircleMap] end...") ;
		return resultNum;
	}

	/**
	 * 
	 * 方法描述:根据圈子ID查询圈子详细信息
	 * @param id 圈子id
	 * @return Circle 圈子详细信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public Circle queryMyCircleById(String id,int userId) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleById] start...") ;

		/**
		 * 查询条件 map
		 */
		Map<String, Object> paramMap = new HashMap<String, Object>() ;
		paramMap.put("id", id) ;
		paramMap.put("userId", userId) ;
		/**
		 * 查询角色实体类
		 */
		Circle circle = commonDao.queryForObject(QUERY_CIRCLE_BY_ID, paramMap, Circle.class) ;
		logger.debug("[CircleDaoImpl.queryCircleById] end...") ;
		return circle;
	}
	
	/**
	 * 
	 * 方法描述:根据圈子ID查询圈子详细信息
	 * @param id 圈子id
	 * @return Circle 圈子详细信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public Circle queryJoinCircleById(String id,int userId) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleById] start...") ;

		/**
		 * 查询条件 map
		 */
		Map<String, Object> paramMap = new HashMap<String, Object>() ;
		paramMap.put("userId", userId) ;
		paramMap.put("circleId", id) ;
		/**
		 * 查询角色实体类
		 */
		Circle circle = commonDao.queryForObject(QUERY_JOIN_CIRCLE_BY_ID, paramMap, Circle.class) ;
		logger.debug("[CircleDaoImpl.queryCircleById] end...") ;
		return circle;
	}
	
	/**
	 * 
	 * 方法描述:查询圈子列表
	 * @param page 分页查询控件
	 * @return List<Circle> 圈子列表信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<Circle> queryMyCircleList(int userId) throws SPTException{
		logger.debug("[CircleDaoImpl.queryMyCircleList] start...") ;
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		List<Circle> circleList  = commonDao.queryForList(QUERY_JOIN_CIRCLE_LIST, paramsMap, Circle.class);
		logger.debug("[CircleDaoImpl.queryCircleList] end...") ;
		return circleList;
	}

	/**
	 * 
	 * 方法描述:查询圈子列表根据经纬度范围
	 *  @param minX 最小经度
	 *  @param minY 最小纬度
	 *  @param maxX 最大经度
	 *  @param maxY 最大纬度
	 * @return List<Circle> 圈子列表信息
	 * date:2014-12-18
	 * @author Cooper
	 */
	public List<Circle> queryCircleListByPoint(double minX, double minY,
			double maxX, double maxY) throws SPTException {
		logger.debug("[CircleDaoImpl.queryCircleListByPoint] start...") ;
		
		StringBuilder sql = new StringBuilder(QUERY_CIRCLE_LISTBYPOINT);
		sql.append(" and t1.longitude BETWEEN "+minX+" and "+maxX+" and t1.latitude BETWEEN "+minY+" and "+maxY);
	
		List<Circle> circleList  = commonDao.queryForList(sql.toString(),Circle.class);
		logger.debug("[CircleDaoImpl.queryCircleListByPoint] end...") ;
		return circleList;
	}
	
	
	
	/**
	 * 方法描述:根据圈子的id查询圈子信息
	 * @param circlrIds	圈子id 逗号分隔
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	@Override
	public List<Circle> queryCircleListCircleId(String circlrIds) throws SPTException {
		StringBuilder sb=new StringBuilder(QUERY_CIRCLE_LIST);
		sb.append(" and t1.id in ("+circlrIds+")");
		return  commonDao.queryForList(sb.toString(),Circle.class);
	}

	/**
	 * 
	 * 方法描述:查询圈子历史信息列表
	 * @param page 分页实体
	 * @return List<Map<String,Object>> 圈子历史信息列表
	 * date:2014-12-23
	 * @author wangfengtong@xwtec.cn
	 */
	public List<Map<String,Object>> queryCircleHistoryList(Page page) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleHistoryList] start...") ;
		StringBuilder sql = new StringBuilder(QUERY_CIRCLE_HISTORY_LIST);
		if(page != null && page.getSearchParam() != null){
			if(page.getSearchParam().containsKey("name") && !StringUtil.isEmpty(page.getSearchParam().get("name"))){
				sql.append(" and t2.name LIKE '%" + page.getSearchParam().get("name") + "%'");
			}
			if(page.getSearchParam().containsKey("username") && !StringUtil.isEmpty(page.getSearchParam().get("username"))){
				sql.append(" and t3.name LIKE '%" + page.getSearchParam().get("username") + "%'");
			}
			if(page.getSearchParam().containsKey("beginTime") && !StringUtil.isEmpty(page.getSearchParam().get("beginTime"))){
				sql.append(" and t1.create_time >= :beginTime");
			}
			if(page.getSearchParam().containsKey("endTime") && !StringUtil.isEmpty(page.getSearchParam().get("endTime"))){
				sql.append(" and t1.create_time <= :endTime");
			}
			if(page.getSearchParam().containsKey("status") && !StringUtil.isEmpty(page.getSearchParam().get("status"))){
				sql.append(" and t1.status = :status");
			}
		}
		List<Map<String,Object>> circleHistoryList  = commonDao.queryForList(sql.toString(), page.getSearchParam(), page);
		logger.debug("[CircleDaoImpl.queryCircleHistoryList] end...") ;
		return circleHistoryList;
	}
	
	/**
	 * 
	 * 方法描述:根据圈子历史信息ID查询圈子历史信息详细信息
	 * @param id 圈子历史信息id
	 * @return Map<String,Object> 圈子历史信息详细信息
	 * date:2014-12-23
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String,Object> queryCircleHistoryById(String id) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleHistoryById] start...") ;

		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("id", id) ;
		
		/**
		 * 查询角色实体类
		 */
		Map<String,Object> circleHistory = commonDao.queryForMap(QUERY_CIRCLE_HISTORY_BY_ID, paramMap) ;
		logger.debug("[CircleDaoImpl.queryCircleHistoryById] end...") ;
		return circleHistory;
	}
	
	/**
	 * 
	 * 方法描述:修改圈子历史信息状态
	 * @param id 圈子历史信息ID
	 * @param status状态
	 * @param auditMsg 审核意见
	 * @return int 返回1修改成功，否则失败
	 * date:2014-12-23
	 * @author wangfengtong@xwtec.cn
	 */
	public int updateCircleHistoryStatus(String id,String status,String auditMsg) throws SPTException{
		logger.debug("[CircleDaoImpl.updateCircleHistoryStatus] start...") ;
		
		/**
		 * 修改圈子信息返回结果参数默认为 -1 （失败）
		 */
		int resultNum = -1 ;

		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("id", id);
		paramMap.put("status", status);
		paramMap.put("auditMsg", auditMsg);
		
		resultNum = commonDao.update(UPDATE_CIRCLE_HISTORY_STATUS, paramMap);
		logger.debug("[CircleDaoImpl.updateCircleHistoryStatus] end...") ;
		return resultNum;
	}
	
	/**
	 * 
	 * 方法描述:用户加入圈子
	 * @param userId 用户ID
	 * @param circleId 圈子ID
	 * @return boolean 是否成功
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public boolean AddUserToCircle(String userId,String circleId,String type) throws SPTException{
		logger.debug("[CircleDaoImpl.AddUserToCircle] start...") ;

		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("userId", userId) ;
		paramMap.put("circleId", circleId) ;
		paramMap.put("type", type) ;
		
		int count = commonDao.update(ADD_USER_TO_CIRCLE, paramMap);
		logger.debug("[CircleDaoImpl.AddUserToCircle] end...") ;
		
		return count>0?true:false;
	}

	
	/**
	 * 
	 * 方法描述:查询圈子信息
	 * @param circleId 圈子ID
	 * @return Circle 圈子信息
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public Circle queryCircleByCircleId(String circleId) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleByCircleId] start...") ;

		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("circleId", circleId) ;
		
		/**
		 * 查询角色实体类
		 */
		Circle circle = commonDao.queryForObject(QUERY_CIRCLE_BY_CIRCLEID,paramMap,Circle.class);
		logger.debug("[CircleDaoImpl.queryCircleByCircleId] end...") ;
		return circle;
	}
	
	
	
	
	
	/**
	 * 方法描述:查询首页第一次加载 初始化的农场
	 * @param 
	 * @return 
	 * date:2015年2月8日
	 * @author Taiyuan
	 */
	@Override
	public List<Circle> queryInitMapCircle() throws SPTException 
	{
		return  commonDao.queryForList(QUERY_INIT_MAP_CIRCLE,Circle.class);
	}

	/**
	 * 
	 * 方法描述:判断用户是否已加入圈子
	 * @param circleId 圈子ID userId用户ID
	 * @return Circle 圈子信息
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public int queryCircleMemberByCircleIdAndUserId(String circleId,String userId) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleMemberByCircleIdAndUserId] start...") ;
		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("circleId", circleId) ;
		paramMap.put("userId", userId) ;
		
		/**
		 * 查询角色实体类
		 */
		int num = commonDao.queryForInt(QUERY_CIRCLEMEMBER_BY_CIRCLEID_AND_USERID,paramMap);
		logger.debug("[CircleDaoImpl.queryCircleMemberByCircleIdAndUserId] end...") ;
		return num;
	}
	
	
	
	/**
	 * 
	 * 方法描述:查询用户是否加入圈子审核
	 * @param circleId 圈子ID userId用户ID
	 * @return int 
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public int queryCircleMemberAuditByCircleIdAndUserId(String circleId,String userId) throws SPTException{
		logger.debug("[CircleDaoImpl.queryCircleMemberAuditByCircleIdAndUserId] start...") ;
		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("circleId", circleId) ;
		paramMap.put("userId", userId) ;
		
		/**
		 * 查询角色实体类
		 */
		int num = commonDao.queryForInt(QUERY_CIRCLEMEMBERAUDIT_BY_CIRCLEID_AND_USERID,paramMap);
		logger.debug("[CircleDaoImpl.queryCircleMemberAuditByCircleIdAndUserId] end...") ;
		return num;
	}
	
	
	/**
	 * 
	 * 方法描述:用户申请加入圈子（圈子人员审核）
	 * @param userId 用户ID
	 * @param circleId 圈子ID
	 * @return boolean 是否成功
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public boolean AddUserToCircleAudit(String userId,String circleId,String type,String status) throws SPTException{
		logger.debug("[CircleDaoImpl.AddUserToCircleAudit] start...") ;
		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("userId", userId) ;
		paramMap.put("circleId", circleId) ;
		paramMap.put("type", type) ;
		paramMap.put("status", status) ;
		
		int count = commonDao.update(ADD_USER_TO_CIRCLE_WITH_EXAMINE, paramMap);
		logger.debug("[CircleDaoImpl.AddUserToCircleAudit] end...") ;
		
		return count>0?true:false;
	}
	
	/**
	 * 查询用户的圈子列表
	 * @param userId
	 * @return List<Circle>
	 * @throws Exception
	 */
	public List<Circle> queryCirclesByUserId(Object userId) throws Exception{
		logger.debug("[CircleDaoImpl.queryCircleByUserId] start...") ;
		Map<String, Object> paramMap = new HashMap<String, Object>() ;
		paramMap.put("userId", userId) ;
		List<Circle> list = commonDao.queryForList(QUERY_CIRCLES_BY_USERID, paramMap, Circle.class);
		logger.debug("[CircleDaoImpl.queryCircleByUserId] end...") ;
		return list;
	}
	
	/**
	 * 方法描述:查询圈子购买人数
	 * @param 
	 * @return 
	 * date:2015年1月16日
	 * @author Taiyuan
	 */
	@Override
	public int queryCircleBuyNum(String circleId) throws SPTException 
	{
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("circleId", circleId);
		return commonDao.queryForInt(QUERY_CIRLCE_BUYNUM, paramMap);
	}

	/**
	 * 方法描述：用户退出圈子
	 * @param paramMap
	 * @return Integer
	 */
	public Integer memberExitCircle(Map<String, Object> paramMap) throws SPTException {
		return commonDao.update(MEMBER_EXIT_CIRCLE, paramMap);
	}

	/**
	 * 方法描述：根据圈子ID查询圈子、用户信息
	 * @return Circle
	 * @throws SPTException
	 * @author zhoujie
	 */
	@Override
	public Circle queryCircleAndUserByCircleId(Map<String, Object> paramMap) throws SPTException {
		return commonDao.queryForObject(QUERY_CIRCLE_USER_BY_CIRCLE_ID, paramMap, Circle.class);
	}

	/**
	 * 方法描述：根据圈子ID查询用户列表
	 * @return Circle
	 * @throws SPTException
	 * @author zhoujie
	 */
	@Override
	public List<User> queryUsersByCircleId(Map<String, Object> paramMap) throws SPTException {
		return commonDao.queryForList(QUERY_USERS_BY_CIRCLE_ID, paramMap, User.class);
	}

	/**
	 * 方法描述：保存圈子公告和简介信息
	 * @param paramMap
	 * @return Integer
	 * @throws SPTException
	 */
	@Override
	public Integer saveCircleInfo(Map<String, Object> paramMap)	throws SPTException {
		return commonDao.update(SAVE_CIRCLE_INFO, paramMap);
	}

	/**
	 * 方法描述：发布发货时间和发货地址
	 * @param paramMap
	 * @return Integer
	 * @throws SPTException
	 */
	@Override
	public Integer saveCirclePublishInfo(Map<String, Object> paramMap) throws SPTException {
		return commonDao.update(SAVE_CIRCLE_PUBLISH_INFO, paramMap);
	}
}