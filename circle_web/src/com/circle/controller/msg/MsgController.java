package com.circle.controller.msg;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.circle.pojo.user.User;
import com.circle.service.msg.IMsgService;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 
 * 消息Controller. <br>
 * <p>
 * Copyright: Copyright (c) 2015-1-13 下午03:15:03
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
@Controller
@RequestMapping("msg")
public class MsgController {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(MsgController.class);

	@Resource
	IMsgService msgService;
	
	/**
	 * 
	 * 方法描述:修改消息状态为已读状态
	 * @param id 消息状态
	 * @return 修改成功返回true，否则false
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	@ResponseBody
	@RequestMapping("firstbuttonclick.action")
	public boolean firstButtonClick(String msgId){
		try {
			return msgService.firstClickCall(msgId);
		} catch (SPTException e) 
		{
			return false;
		}
	}
	
	/**
	 * 
	 * 方法描述:删除消息
	 * @param id 消息
	 * @return 删除成功返回true，否则false
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	@ResponseBody
	@RequestMapping("secondbuttonclick.action")
	public boolean secondButtonClick(String msgId){
		try {
			return msgService.secondClickCall(msgId);
		} catch (SPTException e) 
		{
			return false;
		}
	}
	
	/**
	 * 
	 * 方法描述:删除消息
	 * @param id 消息
	 * @return 删除成功返回true，否则false
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	@ResponseBody
	@RequestMapping("delmsg.action")
	public boolean delMSg(String msgId){
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", msgId);
			paramMap.put("status", "-1");
			return msgService.updateMsgStatus(paramMap);
		} catch (SPTException e) 
		{
			return false;
		}
	}
	
	@ResponseBody
	@RequestMapping("getmsgcount.action")
	public int getMSgCount(HttpServletRequest request){
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			if(user==null)
			{
				return 0;
			}
			int cnt  = msgService.queryMsgCount(user.getId() + "");
			return cnt;
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return 0;
		}
	}
}
