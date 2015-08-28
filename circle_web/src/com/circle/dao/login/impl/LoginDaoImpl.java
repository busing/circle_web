package com.circle.dao.login.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import com.circle.utils.StringUtils;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.dao.login.ILoginDao;
import com.circle.pojo.user.User;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 
 * 登录DAO接口实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 10:50:42
 * <p>
 * Company: Cooper's Summber
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
@Repository
public class LoginDaoImpl implements ILoginDao {
	
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(LoginDaoImpl.class) ;
	
	/**
	 * 修改用户最后登录时间
	 */
	public static final String UPDATA_USER_LASTLOGINTIMER = "update t_user t set t.last_login_time = now()  where  t.mobilephone = :mobilePhone";
	
	/**
	 * 根据微信openid修改用户最后登录时间
	 */
	public static final String UPDATA_USER_LASTLOGINTIMER_BY_WEIXIN = "update t_user t set t.last_login_time = now()  where t.weixin_openid = :openid";

	/**
	 * 用户登录
	 */
	public static final String QUERY_USER_BY_MOBILIPHONE_AND_PASS = "select t.id,t.mobilephone,t.name,t.nickname,t.password,t.email,t.head_image,t.status,t.register_time,t.last_login_time,t.invite_user_id inviteUserId,t.invite_code inviteCode,t.weixin_name weixinName,t.weixin_image weixinImage from t_user t where t.mobilephone=:mobilePhone and t.password=:password ";
	
	/**
	 * 用户微信登录
	 */
	public static final String QUERY_USER_BY_WEIXIN_OPENID = "select t.id,t.mobilephone,t.name,t.nickname,t.password,t.email,t.head_image,t.status,t.register_time,t.last_login_time,t.invite_user_id inviteUserId,t.invite_code inviteCode,t.weixin_name weixinName,t.weixin_image weixinImage,t.weixin_openid weixinOpenid from t_user t where t.weixin_openid = :openid";
	//public static final String QUERY_USER_BY_MOBILIPHONE_AND_PASS = "select t.id,t.mobilephone,t.name,t.nickname,t.password,t.email,t.head_image,t.status,t.register_time,t.last_login_time,t.invite_user_id inviteUserId,t.invite_code inviteCode,t.weixin_name weixinName,t.weixin_image weixinImage from t_user t where t.mobilephone=:mobilePhone";

	/**
	 * 插入验证码
	 */
	public static final String SEND_VALIDATE_MSG = "insert into t_message_verify (mobilephone,verify_code,is_verify,msg_type,send_time,out_time) values(:mobilePhone,:code,0,1,now(),DATE_ADD(now(),INTERVAL 5 MINUTE))";
	
	/**
	 * 验证码通过
	 */
	public static final String MSG_VALIDATE_OVER = "update t_message_verify set is_verify = 1 where mobilePhone = :mobilePhone and verify_code=:code";

	/**
	 * 删除同一手机未验证的验证码
	 */
	public static final String DELETE_MESSAGE = "delete from t_message_verify where mobilephone = :mobilePhone and is_verify = 0";
	
	/**
	 * 查看验证是否过期
	 */
	public static final String MSG_CODE_OUTTIME = "select count(*) from t_message_verify where mobilePhone = :mobilePhone and verify_code=:code and is_verify = 0 and out_time>now()";
	
	/**
	 * 查看验证是否正确
	 */
	public static final String MSG_CODE_CORRECT = "select count(*) from t_message_verify where mobilePhone = :mobilePhone and verify_code=:code";

	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	
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
		int count = commonDao.update(UPDATA_USER_LASTLOGINTIMER,paramMap);
		logger.debug("[UserDaoImpl.updateUserLastLoginTimer] end...") ;
		
		return  count>0?true:false;
	}
	
	/**
	 * 
	 * 方法描述:根据微信openid修改最后登录时间
	 * @param openid 微信对应订阅号唯一表示
	 * @return 大于0修改成功，否则失败
	 * @throws SPTException
	 * date:2015-6-1
	 * @author wangfengtong
	 */
	public boolean updateUserLastLoginTimerByWeixin(String openid) throws SPTException{
		logger.debug("[UserDaoImpl.updateUserLastLoginTimerByWeixin] start...") ;
		
		/**
		 * 查询条件 map
		 */
		Map<String, String> paramMap = new HashMap<String, String>() ;
		paramMap.put("openid", openid) ;

		/**
		 * 修改登录时间
		 */
		int count = commonDao.update(UPDATA_USER_LASTLOGINTIMER_BY_WEIXIN,paramMap);
		logger.debug("[UserDaoImpl.updateUserLastLoginTimerByWeixin] end...") ;
		
		return  count>0?true:false;
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
		paramMap.put("password", StringUtils.md5Sum(password));
		logger.info("[LoginDaoImpl.login]: mobilePhone = " + mobilePhone);
		//查询获取单个用户信息
		user=commonDao.queryForObject(QUERY_USER_BY_MOBILIPHONE_AND_PASS, paramMap, User.class);
		logger.info("[UserDaoImpl.login]: end...");
		return user;
	}
	
	/**
	 * 
	 * 方法描述:用户微信登录
	 * @param openid 微信对应公众号唯一标识号
	 * @return User 用户详细信息
	 * @throws SPTException
	 * date:2015-6-1
	 * @author wangfengtong
	 */
	public User login(String openid) throws SPTException{
		logger.info("[UserDaoImpl.login]: start...");
		//查询结果用户
		User user = null;
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("openid", openid);
		logger.info("[LoginDaoImpl.login]: openid = " + openid);
		//查询获取单个用户信息
		user=commonDao.queryForObject(QUERY_USER_BY_WEIXIN_OPENID, paramMap, User.class);
		logger.info("[UserDaoImpl.login]: end...");
		return user;
	}
	
	/**
	 * 
	 * 方法描述: 发送验证码
	 * @param mobilePhone手机号 code 验证码
	 * @return 是否成功
	 * date:2015年1月9日
	 * add by: Cooper
	 * @throws SPTException 
	 */
	public int sendMsg(String mobilePhone,String code) throws SPTException{
		logger.info("[UserDaoImpl.sendMsg]: start...");
		//查询结果用户
		int result = 0 ;
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		paramMap.put("code", code);
		//查询获取单个用户信息
		result= commonDao.update(SEND_VALIDATE_MSG, paramMap);
		logger.info("[UserDaoImpl.sendMsg]: end...");
		return result;
	}
	
	/**
	 * 查看验证码是否正确
	 * @param mobilePhone
	 * @param code
	 * @return Boolean
	 * @throws SPTException
	 */
	public Boolean checkMsgCorrect(String mobilePhone, String code) throws SPTException{
		logger.info("[UserDaoImpl.sendMsg]: start...");
		//查询结果用户
		int result = 0 ;
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		paramMap.put("code", code);
		//查询获取单个用户信息
		result= commonDao.queryForInt(MSG_CODE_CORRECT, paramMap);
		logger.info("[UserDaoImpl.sendMsg]: end...");
		return result > 0;
	}
	
	/**
	 * 查看注册码是否过期
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public int checkVCodeIsOutTime(String mobilePhone,String code) throws SPTException{
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		paramMap.put("code", code);
		return commonDao.queryForInt(MSG_CODE_OUTTIME, paramMap);
	}
	
	/**
	 * 
	 * 方法描述: 验证通过
	 * @param mobilePhone手机号
	 * @return 是否成功
	 * date:2015年1月9日
	 * add by: Cooper
	 * @throws SPTException 
	 */
	public boolean msgValidateOver(String mobilePhone,String code) throws SPTException{
		logger.info("[UserDaoImpl.msgValidate]: start...");
		//查询结果用户
		int result = 0 ;
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		paramMap.put("code", code);
		//查询获取单个用户信息
		result= commonDao.update(MSG_VALIDATE_OVER, paramMap);
		logger.info("[UserDaoImpl.msgValidate]: end...");
		return result>0;
	}
	
	/**
	 * 方法描述：删除同一手机未验证的验证码
	 * @param mobilePhone
	 * @return Boolean
	 * @throws SPTException
	 * @date 2015-01-26
	 * @author zhoujie
	 */
	public Boolean deleteMessage(String mobilePhone) throws SPTException{
		logger.info("[UserDaoImpl.deleteMessage]: start...");
		//封装SQL参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobilePhone", mobilePhone);
		//查询获取单个用户信息
		int result= commonDao.update(DELETE_MESSAGE, paramMap);
		logger.info("[UserDaoImpl.deleteMessage]: end...");
		return result > 0;
	}
}
