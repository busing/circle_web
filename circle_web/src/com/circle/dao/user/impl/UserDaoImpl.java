package com.circle.dao.user.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.constant.CircleTable;
import com.circle.dao.user.IUserDao;
import com.circle.pojo.user.User;
import com.circle.utils.StringUtils;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 
 * 用户DAO接口实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 10:50:42
 * <p>
 * Company: Cooper's Summber
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
@Repository
public class UserDaoImpl implements IUserDao {
	
	
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class) ;
	
	/**
	 * 查询用户列表信息
	 */
	public static final String QUERY_USER_LIST = "select t.id,t.mobilephone,t.name,t.password,t.email,t.head_image,t.status,t.register_time,t.last_login_time from t_user t where 1=1";
	
	/**
	 * 查询用户信息
	 */
	public static final String QUERY_USER_BY_ID = "select t.id,t.mobilephone,t.name,t.nickname,t.password,t.email,t.head_image headImage,t.status,t.register_time registerTime ,t.last_login_time lastLoginTime,t.weixin_image weixinImage,t.weixin_name weixinName,invite_code inviteCode from t_user t where t.id = :id";

	/**
	 * 查询圈子用户
	 */
	public static final String QUERY_CRICLE_USER = " select t1.id,t1.name,t1.password,t1.mobilephone,t1.email,t1.head_image,t1.status,t1.register_time,t1.last_login_time from t_user t1 LEFT JOIN  " +
			"t_circle_member t2 on t1.id = t2.user_id where t2.circle_id =:id";
	
	/**
	 * 修改用户最后登录时间
	 */
	public static final String UPDATA_USER_LASTLOGINTIMER = "update t_user t set t.last_login_time lastLoginTime =now()  where  t.mobilephone = :mobilePhone";

	/**
	 * 用户登录
	 */
	public static final String QUERY_USER_BY_MOBILIPHONE_AND_PASS = "select t.id,t.mobilephone,t.name,t.password,t.email,t.head_image,t.status,t.register_time,t.last_login_time from t_user t where t.mobilephone=:mobilePhone and t.password=:password ";
	
	/**
	 * 修改用户资料
	 */
	private static final String UPDATEUSERINFO = "update t_user set `name` = :name,nickname = :nickname,email = :email,head_image = :headImage,weixin_image=:weixinImage where id = :id";

	private static final String UPDATEPASSWORD = "update t_user set password = :password where id = :id";
	
	private static final String UPDATEPASSWORDBYMOBILEPHONE = "update t_user set password = :password where mobilephone = :mobilephone";

	private static final String UPDATEMobilePhone = "update t_user set mobilephone = :mobilePhone where id = :id";
	
	private static final String QUERY_CONUT_BY_MOBILEPHONE = "select count(*) from t_user where mobilephone=:mobilePhone";

	//注册用户
	private static final String ADD_USER= "insert into t_user (mobilephone,name,password,status,register_time,last_login_time,head_image,invite_code,invite_user_id) values(:mobilePhone,:name,:password,1,now(),now(),:headImage,:inviteCode,:inviteUserId)";
	
	//注册用户
	private static final String ADD_WEIXIN_USER= "insert into t_user (name,status,register_time,last_login_time,invite_code,invite_user_id,weixin_name,weixin_image,weixin_openid) values(:name,1,now(),now(),:inviteCode,:inviteUserId,:weixinName,:weixinImage,:weixinOpenid)";
	
	//根据邀请码查询用户信息
	private static final String GET_USER_BY_INVITE_CODE = "select * from t_user where invite_code = :inviteCode";

	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	
	/**
	 * 
	 * 方法描述:查询用户列表
	 * @param page 分页查询控件
	 * @return List<User> 用户列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<User> queryUserList(Page page) throws SPTException {
		logger.debug("[UserDaoImpl.queryUserList] start...") ;
		StringBuilder sql = new StringBuilder(QUERY_USER_LIST);
		if(page != null && page.getSearchParam() != null){
			if(page.getSearchParam().containsKey("name") && !StringUtil.isEmpty(page.getSearchParam().get("name"))){
				sql.append(" and t.name LIKE '%" + page.getSearchParam().get("name") + "%'");
			}
			if(page.getSearchParam().containsKey("mobilephone") && !StringUtil.isEmpty(page.getSearchParam().get("mobilephone"))){
				sql.append(" and t.mobilephone LIKE '%" + page.getSearchParam().get("mobilephone") + "%'");
			}
			if(page.getSearchParam().containsKey("status") && !StringUtil.isEmpty(page.getSearchParam().get("status"))){
				sql.append(" and t.status = :status");
			}
		}
		List<User> userList  = commonDao.queryForList(sql.toString(), page.getSearchParam(), page, User.class);
		logger.debug("[UserDaoImpl.queryUserList] end...") ;
		return userList;
	}
	
	/**
	 * 
	 * 方法描述:查询圈子用户列表
	 * @param circleId 圈子id
	 * @return List<User> 用户列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<User> queryCircleUserList(String id,Page page) throws SPTException {
		logger.debug("[UserDaoImpl.queryCircleUserList] start...") ;

		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("id", id) ;
		page.setSearchParam(paramMap);
		
		List<User> userList  = commonDao.queryForList(QUERY_CRICLE_USER, page.getSearchParam(), page, User.class);

		logger.debug("[UserDaoImpl.queryCircleUserList] end...") ;
		return userList;
	}
	
	
	/**
	 * 
	 * 方法描述:用户信息查看
	 * @param id 用户id
	 * @return User  用户信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public User queryUserById(String id) throws SPTException{
		logger.debug("[UserDaoImpl.queryUserById] start...") ;
	
		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("id", id) ;

		/**
		 * 查询角色实体类
		 */
		User user = commonDao.queryForObject(QUERY_USER_BY_ID, paramMap, User.class) ;
		logger.debug("[UserDaoImpl.queryUserById] end...") ;
		
		return user;
	}
	
	/**
	 * 
	 * 方法描述:修改用户登录时间
	 * @param mobilePhone 用户手机
	 * date:2014-12-16
	 * @author Cooper
	 */
	public boolean updateUserLastLoginTimer(String mobilePhone) throws SPTException{
		logger.debug("[UserDaoImpl.updateUserLastLoginTimer] start...") ;
		
		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("mobilePhone", mobilePhone) ;

		/**
		 * 修改登录时间
		 */
		boolean flag =  executeSql(UPDATA_USER_LASTLOGINTIMER,paramMap);
		logger.debug("[UserDaoImpl.updateUserLastLoginTimer] end...") ;
		
		return flag;
	}
	
	/**
	 * 
	 * 方法描述: 用户登录
	 * @param mobilePhone手机号 password密码
	 * @return 返回用户实体类对象
	 * date:2015年1月9日
	 * add by: Cooper
	 * @throws NoSuchAlgorithmException 
	 * @throws SPTException 
	 */
	public User login(String mobilePhone, String password) throws NoSuchAlgorithmException, SPTException{
		logger.info("[UserDaoImpl.login]: start...");
		//查询结果用户
		User user = null;
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		paramMap.put("password", password);
		logger.info("[LoginDaoImpl.login]: mobilePhone = " + mobilePhone);
		//查询获取单个用户信息
		user=commonDao.queryForObject(QUERY_USER_BY_MOBILIPHONE_AND_PASS, paramMap, User.class);
		logger.info("[UserDaoImpl.login]: end...");
		return user;
	}
	
	public boolean executeSql(String sql, Map<String, String> paramMap)
	{
		try {
			int count=commonDao.update(sql, paramMap);
			return count>0?true:false;
		} catch (SPTException e) {
			logger.error("执行sql异常");
			return false;
		}
	}
	
	/**
	 * 修改用户资料
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-13
	 */
	public Boolean updateUserInfo(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return commonDao.update(UPDATEUSERINFO, paramMap) > 0;
	}
	
	/**
	 * 修改用户密码
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-15
	 */
	public Boolean updatePassword(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return commonDao.update(UPDATEPASSWORD, paramMap) > 0;
	}
	
	
	/**
	 * 修改用户密码
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-03-11
	 */
	public Boolean updatePasswordByMobilePhone(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException
	{
		return commonDao.update(UPDATEPASSWORDBYMOBILEPHONE, paramMap) > 0;
	}
	
	/**
	 * 修改用户手机
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-15
	 */
	public Boolean updateMobilePhone(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return commonDao.update(UPDATEMobilePhone, paramMap) > 0;
	}
	
	
	/**
	 * 查看用户是否已经被注册
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public int checkUserIsExtists(String mobilePhone) throws SPTException{
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		return commonDao.queryForInt(QUERY_CONUT_BY_MOBILEPHONE, paramMap);
	}
	
	/**
	 * 添加用户
	 * @param User
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public boolean addUser(User user) throws SPTException{
		logger.info("[UserDaoImpl.addUser]: start...");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", user.getMobilePhone());
		paramMap.put("name", user.getName());
		paramMap.put("headImage", user.getHeadImage());
		paramMap.put("password", StringUtils.md5Sum(user.getPassword()));
		paramMap.put("inviteCode", user.getInviteCode());
		paramMap.put("inviteUserId", user.getInviteUserId() + "");
		logger.info("[UserDaoImpl.addUser]: end...");
		
		boolean flag= commonDao.update(ADD_USER,paramMap) >0?true:false;
		user.setId(commonDao.getLastId(CircleTable.USER.getTableName(), "id"));
		return flag;
	}
	
	/**
	 * 
	 * 方法描述:增加微信用户
	 * @param user 用户信息
	 * @return boolean true 添加成功，false添加失败
	 * @throws SPTException
	 * date:2015-6-1
	 * @author wangfengtong
	 */
	public boolean addWeixinUser(User user) throws SPTException{
		logger.info("[UserDaoImpl.addWeixinUser]: start...");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", user.getMobilePhone());
		paramMap.put("name", user.getName());
		paramMap.put("inviteCode", user.getInviteCode());
		paramMap.put("inviteUserId", user.getInviteUserId() + "");
		paramMap.put("weixinName", user.getWeixinName());
		paramMap.put("weixinImage", user.getWeixinImage());
		paramMap.put("weixinOpenid", user.getWeixinOpenid());
		logger.info("[UserDaoImpl.addWeixinUser]: end...");
		boolean flag= commonDao.update(ADD_WEIXIN_USER,paramMap) >0?true:false;
		user.setId(commonDao.getLastId(CircleTable.USER.getTableName(), "id"));
		return flag;
	}
	
	/**
	 * 根据验证码查询用户信息
	 * @param inviteCode
	 * @return User
	 * @author zhoujie
	 * @since 2015-04-02
	 */
	public User getUserByInviteCode(String inviteCode) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("inviteCode", inviteCode);
			return (User)commonDao.queryForObject(GET_USER_BY_INVITE_CODE, paramMap, User.class);
		} catch (SPTException e) {
			e.printStackTrace();
		}
		return null;
	}
}