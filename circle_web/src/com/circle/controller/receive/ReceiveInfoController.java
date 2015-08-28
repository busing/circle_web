package com.circle.controller.receive;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.circle.pojo.payship.PayAndShip;
import com.circle.pojo.receive.ReceiveInfo;
import com.circle.pojo.user.User;
import com.circle.service.receive.IReceiveInfoService;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.util.CommonUtil;

@Controller
@RequestMapping("receive")
public class ReceiveInfoController
{

	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(ReceiveInfoController.class);
	
	@Resource
	IReceiveInfoService receiveInfoService;
	
	
	@ResponseBody
	@RequestMapping("queryReceive")
	public String queryReceiveInfo(HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		int userId=user.getId();
		List<Map<String, Object>> receiveList=receiveInfoService.queryReceiveInfo(userId);
		JSONArray jsonArr=JSONArray.fromObject(receiveList);
		return jsonArr.toString();
		
	}
	
	/**
	 * 保存收货信息
	 * @param receiveInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveReceive")
	public String saveReceiveInfo(ReceiveInfo receiveInfo,HttpServletRequest request)
	{
		boolean flag=false;
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		int userId=user.getId();
		receiveInfo.setUserId(userId);
		try
		{
			flag=receiveInfoService.saveReceiveInfo(receiveInfo);
		}
		catch (Exception e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag?"true":"false";
	}
	
	
	/**
	 * 设置默认收货信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("defaultReceive")
	public String defaultReceiveInfo(int id,HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		int userId=user.getId();
		boolean flag=false;
		try
		{
			flag=receiveInfoService.defaultReceiveInfo(id, userId);
		}
		catch (Exception e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag?"true":"false";
	}
	
	
	/**
	 * 删除收货信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteReceive")
	public String deleteReceiveInfo(int id,HttpServletRequest request)
	{
		boolean flag=false;
		try
		{
			flag=receiveInfoService.deleteReceiveInfo(id);
		}
		catch (Exception e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag?"true":"false";
	}
	
	
	/**
	 * 保存支付方式和发货信息
	 * @param payAndShip
	 * @return
	 */
	@ResponseBody
	@RequestMapping("savePayAndShip")
	public String savePayAndShipInfo(PayAndShip payAndShip,HttpServletRequest request)
	{
		JSONObject jsonResult=new JSONObject();
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		int userId=user.getId();
		payAndShip.setUserId(userId);
		boolean flag=false;
		try
		{
			flag=receiveInfoService.savePayAndShipInfo(payAndShip);
			if(flag)
			{
				Map<String, Object> pay_ship_type=receiveInfoService.queryPayAndShipInfo(userId);
				jsonResult.put("pay_ship_type", pay_ship_type);	
			}
		}
		catch (Exception e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		jsonResult.put("result", flag);
		return jsonResult.toString();
	}
}
