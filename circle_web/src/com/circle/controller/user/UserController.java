package com.circle.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.circle.pojo.user.User;
import com.circle.service.login.ILoginService;
import com.circle.service.user.IUserService;
import com.circle.utils.MessageUtil;
import com.circle.utils.StringUtils;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 用户信息. <br>
 * 2015年1月13日
 * Copyright: Copyright (c) 
 * @author zhoujie
 * @version 1.0.0
 */
@Controller
@RequestMapping("user")
public class UserController {
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	/**
	 * 注入登录service
	 */
	@Resource
	private IUserService userService;
	
	@Resource
	private ILoginService loginService;
	
	@ResponseBody
	@RequestMapping("updateUserInfo.action")
	public String updateUserInfo(@ModelAttribute("user") User user, HttpServletRequest req){
		try {
			User _user = (User)req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", _user.getId());
			paramMap.put("name", user.getName());
			paramMap.put("nickname", user.getNickname());
			paramMap.put("email", user.getEmail());
			paramMap.put("headImage", user.getHeadImage());
			paramMap.put("weixinImage", user.getWeixinImage());
			
			boolean result = userService.updateUserInfo(paramMap);
			if(result){
//				_user = userService.queryUserById(_user.getId() + "");
				_user.setName(user.getName());
				_user.setNickname(user.getNickname());
				_user.setEmail(user.getEmail());
				_user.setImagePath(user.getHeadImage());
				req.getSession().setAttribute(ConstantBusiKeys.SessionKey.SYS_USER, _user);
			}
			return result ? "1" : "0";
		} catch (Exception ex) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], ex));
		}
		return "0";
	}
	
	@ResponseBody
	@RequestMapping("updatePassword.action")
	public String updatePassword(String curpassword, String password, HttpServletRequest req){
		try {
			User user = (User)req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			if(!StringUtils.md5Sum(curpassword).equals(user.getPassword()))
				return "0";
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", user.getId());
			paramMap.put("password", StringUtils.md5Sum(password));
			boolean result = userService.updatePassword(paramMap);
			if(result){
				user.setPassword(StringUtils.md5Sum(password));
				req.getSession().setAttribute(ConstantBusiKeys.SessionKey.SYS_USER, user);
			}
			return result ? "1" : "2";
		} catch (Exception ex) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], ex));
		}
		return "2";
	}

	
	@ResponseBody
	@RequestMapping("updatePasswordByMobilePhone.action")
	public String updatePasswordByMobilePhone(String curpassword,String mobilePhone,String code, HttpServletRequest req){
		boolean result = false;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("password",  StringUtils.md5Sum(curpassword));
			paramMap.put("mobilephone", mobilePhone);
			
			result = userService.updatePasswordByMobilePhone(paramMap);
			if(result){
				loginService.msgValidateOver(mobilePhone,code);
			}
		} catch (Exception ex) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], ex));
		}
		return result ? "0" : "";
	}
	
	
	/**
	 * 方法描述:发送验证码
	 * @param mobilePhone
	 * @param request
	 * @return String
	 * date:2015年1月26日
	 * @author zhoujie
	 */
	@ResponseBody
	@RequestMapping("sendValidateMsg.action")
	public String sendValidateMsg(String mobilePhone,HttpServletRequest request){
		try {
			boolean has = userService.checkUserIsExtists(mobilePhone) > 0;

			//手机号码已注册
			if (has)
				return "0";

			int result = 0;
			String s = "";
			while (s.length() < 6)
				s += (int) (Math.random() * 10);
			
			//调用短信接口发送短信
			boolean flag = MessageUtil.sendChangeMobile(mobilePhone, s);
			
			//发送验证码失败
			if(!flag)
				return "1";
			loginService.deleteMessage(mobilePhone);
			result = loginService.sendMsg(mobilePhone, s);

			return result > 0 ? s : "1";
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			e.printStackTrace();
		}
		return "1";
	}

	@ResponseBody
	@RequestMapping("updateMobilePhone.action")
	public String updateMobilePhone(String mobilePhone, String code, HttpServletRequest req){
		try {
			User user = (User)req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			
			boolean isMsgCorrect = loginService.checkMsgCorrect(mobilePhone, code);
			
			if(!isMsgCorrect)
				return "3";
			
			boolean isTimeOut = loginService.checkVCodeIsOutTime(mobilePhone, code) > 0;
			
			if(!isTimeOut)
				return "2";
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", user.getId());
			paramMap.put("mobilePhone", mobilePhone);
			boolean result = userService.updateMobilePhone(paramMap);
			loginService.msgValidateOver(mobilePhone, code);
			if(result){
				user.setMobilePhone(mobilePhone);
				req.getSession().setAttribute(ConstantBusiKeys.SessionKey.SYS_USER, user);
			}
			return result ? "1" : "0";
		} catch (Exception ex) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], ex));
		}
		return "0";
	}
}