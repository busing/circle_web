package com.circle.service.user.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.circle.dao.user.IUserDao;
import com.circle.pojo.user.User;
import com.circle.service.user.IUserService;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.ProUtil;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 
 * 用户信息业务逻辑层实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 11:11:37
 * <p>
 * Company: Cooper's Summer
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements IUserService {

	/**
	 * 注入DAO方法
	 */
	@Resource
	private IUserDao userDao;
	
	/**
	 * 
	 * 方法描述:查询用户列表
	 * @param page 分页查询控件
	 * @return List<Circle> 用户子列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<User> queryUserList(Page page) throws SPTException{
		return userDao.queryUserList(page);
	}
	
	/**
	 * 
	 * 方法描述:查询圈子用户列表
	 * @param circleId 圈子id
	 * @return List<User> 用户列表信息
	 * date:2014-12-16
	 * @author Cooper
	 */
	public List<User> queryCircleUserList(String id,Page page) throws SPTException
	{
		return userDao.queryCircleUserList(id,page);
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
		User user = userDao.queryUserById(id);
		if(user!=null){
			if(StringUtil.isEmpty(user.getHeadImage()))
			{
				user.setHeadImage("/images/def_adv.png");
			}
			user.setImagePath(user.getHeadImage());
			user.setHeadImage(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+user.getHeadImage());
		}
		return user;
	}
	
	/**
	 * 
	 * 方法描述:修改用户登录时间
	 * @param mpbilePhone 用户手机
	 * date:2014-12-16
	 * @author Cooper
	 */
	public boolean updateUserLastLoginTimer(String mpbilePhone) throws SPTException{
		return userDao.updateUserLastLoginTimer(mpbilePhone);
	}
	
	/**
	 * 修改用户资料
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-13
	 */
	public Boolean updateUserInfo(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return userDao.updateUserInfo(paramMap);
	}
	
	/**
	 * 修改用户密码
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-15
	 */
	public Boolean updatePassword(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return userDao.updatePassword(paramMap);
	}
	
	
	/**
	 * 修改用户密码
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-03-11
	 */
	public Boolean updatePasswordByMobilePhone(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return userDao.updatePasswordByMobilePhone(paramMap);
	}
	
	/**
	 * 修改用户手机
	 * @param paramMap
	 * @return Boolean
	 * @author zhoujie
	 * @since 2015-01-15
	 */
	public Boolean updateMobilePhone(Map<String, Object> paramMap) throws NoSuchAlgorithmException, SPTException{
		return userDao.updateMobilePhone(paramMap);
	}
	
	/**
	 * 查看用户是否已经被注册
	 * @param paramMap
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public int checkUserIsExtists(String mobilePhone) throws SPTException{
		return userDao.checkUserIsExtists(mobilePhone);
	}
	
	/**
	 * 添加用户
	 * @param User
	 * @return Boolean
	 * @author Cooper
	 * @since 2015-01-15
	 */
	public boolean addUser(User user) throws SPTException{
		return userDao.addUser(user);
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
		return userDao.addWeixinUser(user);
	}
	
	/**
	 * 根据验证码查询用户信息
	 * @param inviteCode
	 * @return User
	 * @author zhoujie
	 * @since 2015-04-02
	 */
	public User getUserByInviteCode(String inviteCode) {
		return userDao.getUserByInviteCode(inviteCode);
	}
}
