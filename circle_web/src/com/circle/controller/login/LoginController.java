package com.circle.controller.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.circle.constant.SystemDict;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.commission.CommissionHistory;
import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.user.User;
import com.circle.service.circle.ICircleService;
import com.circle.service.commossion.ICommissionService;
import com.circle.service.login.ILoginService;
import com.circle.service.msg.IMsgService;
import com.circle.service.user.IUserService;
import com.circle.utils.MessageUtil;
import com.circle.utils.commission.CommissionComputeFactory;
import com.circle.utils.commission.compute.InviteCommissionCompute;
import com.circle.utils.msg.MsgQueue;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.ProUtil;

/**
 * 登录. <br>
 * 2015年1月9日 10:58:59
 * Copyright: Copyright (c) 
 * <p>
 * Company: Cooper's summber
 * <p>
 * @author cooper
 * @version 1.0.0
 */
@Controller
@RequestMapping("login")
public class LoginController {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	/**
	 * 注入消息service
	 */
	@Resource
	IMsgService msgService;
	
	/**
	 * 注入登录service
	 */
	@Resource
	ILoginService loginService;
	
	/**
	 * 注入圈子service
	 */
	@Resource
	ICircleService circleService;
	
	/**
	 * 注入用户service
	 */
	@Resource
	IUserService userService;
	
	/**
	 * 注入佣金service
	 */
	@Resource
	ICommissionService commissionService;
	
	/**
	 * 方法描述:用户登录(返回毁掉url，登录结果 0：登录失败 1：登录成功)
	 * view viewId（1：创建圈子	2：加入圈子 其他：直接登录）
	 * date:2015-01-09 
	 * add by: Cooper
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("login.action")
	public String login(String mobilePhone,String password,String viewId,String circleId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		viewId=viewId==null?"":viewId;
		JSONObject josnResult=new JSONObject();
		HttpSession session = request.getSession();
		String msg="";
		String url="";
		int result=0;
		User user = null;//用户登录对象
		
		//登录结果
		if(!CommonUtil.isEmpty(mobilePhone) && !CommonUtil.isEmpty(password))
		{
            try
            {
            	user = loginService.login(mobilePhone,password);
            }
            catch (Exception e)
            {
            	logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
            }
            //登录失败
            if(null == user)
            {
            	msg = "用户名或密码错误，请重新输入!";
            	result=0;
			}
            //登录成功
            else
            {
				session.setAttribute(ConstantBusiKeys.SessionKey.SYS_USER, user);
				//记录最后登录质检
				try {
					loginService.updateUserLastLoginTimer(mobilePhone);
				} catch (SPTException e) {
					logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
				}
				msg = "";
            	result=1;
			}
		}
		
		//创建圈子登录
		if(viewId.equals("1"))
		{
			url="/usercenter/toCreateCircle.action";
		}
		//加入圈子登录
		else if (viewId.equals("2") && user != null) {
			try {
				Circle circle = circleService.queryCircleByCircleId(circleId);
				if(user.getId() == circle.getCreateUserId()){
					msg = "您是农场主,无须加入!";
					josnResult.put("msg", msg);
					josnResult.put("url", url);
					josnResult.put("result", result);
					josnResult.put("viewId", viewId);
					return josnResult.toString();
				}
				//圈子 允许 直接加入
				if(circle.getJoinType().equals(SystemDict.CIRCLE_AUTO_JOIN)){
					msg = circleService.queryCircleMemberByCircleIdAndUserId(circleId, user.getId()+"") == 0 ? "":"您已是农场成员!";
					if(!msg.equals("")){
						josnResult.put("msg", msg);
						josnResult.put("url", url);
						josnResult.put("result", result);
						josnResult.put("viewId", viewId);
						return josnResult.toString();
					}
					msg = circleService.AddUserToCircle(user.getId(), circleId,SystemDict.NORMAL_USER) == true ? "success":"加入失败!";
					
					if(msg.equals("success")){
						List<Circle> myCircle = circleService.queryMyCircleList( user.getId());
						request.getSession().setAttribute(ConstantBusiKeys.SessionKey.MY_CIRCLE, myCircle);
					
						MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_NORMAL, "您好，"+user.getName()+"加入了你的["+circle.getName()+"]农场!",user.getId()+"", circle.getCreateUserId()+"","");
						MsgQueue.GROUP_QUEUE.add(msgBean);
					}
				}else
				{
					JSONObject jsonResult = new JSONObject();
					jsonResult.put("userId", user.getId());
					jsonResult.put("circleId",circleId);
					jsonResult.put("createUserId",circle.getCreateUserId());
					jsonResult.put("userName", user.getName());
					jsonResult.put("circleName", circle.getName());
					
					//需要农场主审核
					if( circleService.queryCircleMemberAuditByCircleIdAndUserId(circleId, user.getId()+"") == 0){
						boolean flag = circleService.AddUserToCircleAudit( user.getId()+"", circleId, SystemDict.NORMAL_USER, SystemDict.EXAMINEING);
						if(flag){
							//消息记录中没有，添加消息
							if(msgService.queryCircleMsgCount(user.getId()+"",circle.getCreateUserId()+"") == 0){
								MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_CIRCLE, "您好，"+user.getName()+"申请加入你的[!"+circle.getName()+"]农场!",user.getId()+"", circle.getCreateUserId()+"",jsonResult.toString());
								MsgQueue.GROUP_QUEUE.add(msgBean);
							}
							msg ="已发送加入申请，请等待审核!";
						}else{
							msg ="加入农场失败!";
						}
					}else{
						msg ="您已是农场成员!";
					}
				}
				
			} catch (SPTException e) {
				e.printStackTrace();
				msg = "加入农场失败!";
			}
			
		}
		else if(viewId.equals("3") && user != null) 
		{//注册进入
			url = "";
			response.sendRedirect(request.getContextPath()+url);
			return null;
		}
		josnResult.put("msg", msg);
		josnResult.put("url", url);
		josnResult.put("result", result);
		josnResult.put("viewId", viewId);
		return josnResult.toString();
	}
	
	/**
	 * 方法描述:发送验证码
	 * @param 
	 * @return 
	 * date:2015年1月7日
	 * @author Cooper
	 */
	@ResponseBody
	@RequestMapping("sendValidateMsg.action")
	public String sendValidateMsg(String mobilePhone,String status,HttpServletRequest request){
		String result="";
		//手机已经注册
		try {
			if(status.equals(SystemDict.SEND_MSG_STATUS_REG)){
				result = userService.checkUserIsExtists(mobilePhone) > 0 ? "0" : "";

				if (result.equals("0")){
					return result;
				}
			}else{
				result = userService.checkUserIsExtists(mobilePhone) > 0 ? "" : "0";
				if (result.equals("0")){
					return result;
				}
			}
			
			boolean flag = sendSMS(mobilePhone,status);
			if(!flag){
				result = "1";
			}
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 方法描述:用户注册
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Cooper
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping("registUser.action")
	public String registUser(String mobilePhone,String userName,String password,String verificationCode,String inviteCode,HttpServletRequest request) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		
		JSONObject josnResult=new JSONObject();
		String msg="";
		String url="";
		int result=0;

		url = ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN);
		//添加用户
		boolean flag = false;
		
		User user = new User();
		user.setMobilePhone(mobilePhone);
		user.setPassword(password);
		user.setName(userName);
		user.setHeadImage(SystemDict.DEFAULT_USER_HEAD_PATH);
		
		String code = generateInviteCode(user);
		user.setInviteCode(code);
		
		//邀请码对应的用户信息
		User _user = userService.getUserByInviteCode(inviteCode);
		user.setInviteUserId(_user == null ? 0 : _user.getId());
		
		try {
			flag = userService.addUser(user);
			//改变短信信息 注册成功
			if(flag){
				//增加邀请用户的佣金
				if(user.getInviteUserId()!=0)
				{
					double money=CommissionComputeFactory.getCommissionCompute(SystemDict.COMMISSION_TYPE_INVITE).compute(user.getInviteUserId(), user.getId(), 0);
					CommissionHistory commissionHistory=new CommissionHistory(user.getInviteUserId(), user.getId(), money, Integer.parseInt(SystemDict.COMMISSION_TYPE_INVITE), 0,  user.getId());
					commissionService.increaseUserCommission(commissionHistory);
				}
				result = 1;
				loginService.msgValidateOver(user.getMobilePhone(),verificationCode);
				msg = "注册成功,现在您可以创建农场或者加入您附近的农场购物了!";
				HttpSession session = request.getSession();
				user.setImagePath(user.getHeadImage());
				user.setHeadImage(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+user.getHeadImage());
				session.setAttribute(ConstantBusiKeys.SessionKey.SYS_USER, user);
				//记录最后登录质检
				try {
					loginService.updateUserLastLoginTimer(mobilePhone);
				} catch (SPTException e) {
					logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
				}
			}else
			{
				result = 0;
				msg = "注册用户信息失败!";
			}
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			e.printStackTrace();
		}
		
		josnResult.put("msg", msg);
		josnResult.put("url", url);
		josnResult.put("result", result);
		
		return josnResult.toString();
	}
	
	/**
	 * 生成邀请码
	 * @return String
	 * @author zhoujie
	 * @since 2015-04-02
	 */
	private String generateInviteCode(User user){
		String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		String code = "";
		while (code.length() < 5){
			if(Math.random() < 0.5)
				code += (int) (Math.random() * 10);
			else
				code += letter[(int) (Math.random() * 26)];
		}
		user = userService.getUserByInviteCode(code);
		if(user != null)
			generateInviteCode(user);
		return code;
	}
	
	/**
	 * 方法描述:检查用户是否登录
	 * @param 
	 * @return 
	 * date:2015年1月7日
	 * @author Cooper
	 */
	@ResponseBody
	@RequestMapping("checkUserIsLogin.action")
	public String checkUserIsLogin(HttpServletRequest request)
	{
		String result = "0";
		
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		
		if(null == user)
			result = "0";
		else
			result = "1";
		
		return result;
	}
	
	/**
	 * 方法描述:检查用户手机是否已经注册
	 * @param 
	 * @return 
	 * date:2015年1月7日
	 * @author Cooper
	 */
	@ResponseBody
	@RequestMapping("checkRegister.action")
	public String checkRegister(String mobile,String code)
	{
		String result = "success";
		try {
			if(!loginService.checkMsgCorrect(mobile, code)){
				result="2";
				return result;
			}else
			{
				//验证码过期
				result = loginService.checkVCodeIsOutTime(mobile, code) == 0 ? "1":"success";
			}
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * @param req
	 * @param mav
	 * @return ModelAndView
	 */
	@RequestMapping("logout.action")
	public void logout(HttpServletRequest req, HttpServletResponse res){
		try {
			req.getSession().removeAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			res.sendRedirect("../index.jsp");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 发送短信
	 * @param phone
	 * @return boolean
	 */
	public  boolean sendSMS(String phone,String status) throws SPTException
	{
		String s = "";
		while (s.length() < 6)
		{
			s += (int) (Math.random() * 10);
		}
		boolean flag = false;
		if(loginService.sendMsg(phone, s) > 0){
			//调用短信接口发送短信
			flag =true;
			if(status.equals(SystemDict.SEND_MSG_STATUS_REG))
				MessageUtil.sendRegisterCode(phone, s);
			else
				MessageUtil.sendUpdatePassCode(phone, s);
		}
		return flag;
	}
}