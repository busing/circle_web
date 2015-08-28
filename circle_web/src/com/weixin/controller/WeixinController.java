package com.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.constance.WeixinConstance;
import com.weixin.pojo.TextMessage;
import com.weixin.util.CheckUtil;
import com.weixin.util.XmlUtils;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年4月23日 下午8:29:18
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public class WeixinController {
	
	@ResponseBody
	@RequestMapping(value="api.action",method=RequestMethod.GET)
	public String api(String signature,String timestamp,String nonce,String echostr)
	{
		if(CheckUtil.checkSignature(signature,timestamp,nonce))
		{
			return echostr;
		}
		return "";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="api.action",method=RequestMethod.POST)
	public String api(HttpServletRequest request,HttpServletResponse response)
	{
		TextMessage message=new TextMessage();
		try {
			Map<String, String> data=XmlUtils.xmlToMap(request.getInputStream());
			
			String toUserName=data.get("ToUserName");
			String fromUserName=data.get("FromUserName");
			String createTime=data.get("CreateTime");
			String msgType=data.get("MsgType");
			String content=data.get("Content");
			String msgId=data.get("MsgId");
			
			
			//注册团农事件
			if(WeixinConstance.MESSAGETYPE_EVENT.equals(msgType))
			{
				if(WeixinConstance.EVENTTYPE_CLICK.equals(data.get("Event")) && data.get("EventKey")=="1")
				{
					message.setContent("感谢您的关注,请回复您的手机号码进行注册团农网");
					message.setCreateTime(new Date().getTime()+"");
					message.setMsgType(WeixinConstance.MESSAGETYPE_TEXT);
					message.setToUserName(fromUserName);
					message.setFromUserName(toUserName);
				}
			}
			//
			else if(WeixinConstance.MESSAGETYPE_TEXT.equals(data.get("MsgType")))
			{
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return XmlUtils.textMessageToXml(message);
	}
	
	
	private void responseMessage(HttpServletResponse response,String message)
	{
		try {
			PrintWriter out=response.getWriter();
			out.print(message);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
