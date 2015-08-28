package com.circle.service.user;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.circle.pojo.user.User;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 
 * 用户信息业务逻辑层接口类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 11:08:35
 * <p>
 * Company: Cooper's Summer’
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
public interface IUserService {
	/**
	 * 
	 * 方法描述:查询用户列表
	 * @param page 分页查询控件
	 * @return List<User> 用户列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<User> queryUserList(Page page) throws SPTException;

	/**
	 * 
	 * 方法描述:查询圈子用户列表
	 * @param circleId 圈子id
	 * @return List<User> 用户列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<User> queryCircleUserList(String id,Page page) throws SPTException;
	
	/**
	 * 
	 * 方法描述:用户信息查看
	 * @param id 用户id
	 * @return User  用户信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public User queryUserById(String id) throws SPTException;
	
	/**
	 * 
	 * 方法描述:修改用户登录时间
	 * @param mpbilePhone 用户手机
	 * date:2014-12-16
	 * @author Cooper
	 */
	public boolean updateUserLastLoginTimer(String mpbilePhone) throws SPTException;
	
	/**
	 * 修改用户资料
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-13
	 */
	public Boolean updateUserInfo(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException;
	
	/**
	 * 修改用户密码
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-15
	 */
	public Boolean updatePassword(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException;
	
	/**
	 * 修改用户密码
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-03-11
	 */
	public Boolean updatePasswordByMobilePhone(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException;
	
	/**
	 * 修改用户手机
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-15
	 */
	public Boolean updateMobilePhone(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException;

	/**
	 * 查看用户是否已经被注册
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public int checkUserIsExtists(String mobilePhone) throws SPTException;
	
	/**
	 * 添加用户
	 * @param User
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public boolean addUser(User user) throws SPTException;
	
	/**
	 * 
	 * 方法描述:增加微信用户
	 * @param user 用户信息
	 * @return boolean true 添加成功，false添加失败
	 * @throws SPTException
	 * date:2015-6-1
	 * @author wangfengtong
	 */
	public boolean addWeixinUser(User user) throws SPTException;
	
	/**
	 * 根据验证码查询用户信息
	 * @param inviteCode
	 * @return User
	 * @author zhoujie
	 * @since 2015-04-02
	 */
	public User getUserByInviteCode(String inviteCode);
}
