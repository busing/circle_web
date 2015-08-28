package com.circle.service.login;

import java.security.NoSuchAlgorithmException;

import com.circle.pojo.user.User;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 
 * 登录业务逻辑层接口类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 11:08:35
 * <p>
 * Company: Cooper's Summer’
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
public interface ILoginService {
	/**
	 * 
	 * 方法描述:修改用户登录时间
	 * @param mpbilePhone 用户手机
	 * date:2014-12-16
	 * @author Cooper
	 */
	public boolean updateUserLastLoginTimer(String mobilePhone) throws SPTException;
	
	/**
	 * 
	 * 方法描述:根据微信openid修改最后登录时间
	 * @param openid 微信对应订阅号唯一表示
	 * @return 大于0修改成功，否则失败
	 * @throws SPTException
	 * date:2015-6-1
	 * @author wangfengtong
	 */
	public boolean updateUserLastLoginTimerByWeixin(String openid) throws SPTException;
	
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
	public User login(String mobilePhone, String password) throws NoSuchAlgorithmException, SPTException;
	
	/**
	 * 
	 * 方法描述:用户微信登录
	 * @param openid 微信对应公众号唯一标识号
	 * @return User 用户详细信息
	 * @throws SPTException
	 * date:2015-6-1
	 * @author wangfengtong
	 */
	public User login(String openid) throws SPTException;
	
	/**
	 * 
	 * 方法描述: 发送验证码
	 * @param mobilePhone手机号 code 验证码
	 * @return 是否成功
	 * date:2015年1月9日
	 * add by: Cooper
	 * @throws SPTException 
	 */
	public int sendMsg(String mobilePhone,String code) throws SPTException;
	
	/**
	 * 查看验证码是否正确
	 * @param mobilePhone
	 * @param code
	 * @return Boolean
	 * @throws SPTException
	 */
	public Boolean checkMsgCorrect(String mobilePhone, String code) throws SPTException;
	
	/**
	 * 
	 * 方法描述: 验证通过
	 * @param mobilePhone手机号
	 * @return 是否成功
	 * date:2015年1月9日
	 * add by: Cooper
	 * @throws SPTException 
	 */
	public boolean msgValidateOver(String mobilePhone,String code) throws SPTException;
	
	/**
	 * 查看注册码是否过期
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public int checkVCodeIsOutTime(String mobilePhone,String code) throws SPTException;
	
	/**
	 * 方法描述：删除同一手机未验证的验证码
	 * @param mobilePhone
	 * @return Boolean
	 * @throws SPTException
	 * @date 2015-01-26
	 * @author zhoujie
	 */
	public Boolean deleteMessage(String mobilePhone) throws SPTException;
}
