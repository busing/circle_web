package com.circle.service.msgcall.sub;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import com.circle.constant.SystemDict;
import com.circle.pojo.msg.MessageBean;
import com.circle.service.circle.ICircleService;
import com.circle.service.msgcall.AMessageCall;
import com.circle.utils.msg.MsgQueue;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.SpringUtils;

/**
 * 成员加入圈子需要审核的消息通知（通过，拒绝按钮回调）
 * @author Taiyuan
 *
 */
public class JoinCircleAuditMessageCall extends AMessageCall
{
	/* 
	 * 通过按钮回调方法
	 */
	@Override
	@Transactional
	public boolean firstClickCall(MessageBean messageBean) throws SPTException {
		ICircleService circleService = SpringUtils.getBean("circleService");
		/**
		 * 根据messageBean获取存在message的参数信息（申请用户id，申请加入圈子的id，消息接收用户id）
		 * 调用圈子dao，将所申请加入的成员加入到圈子成员信息表，成为正式成员
		 * 发送消息通知申请用户加入圈子已被通过
		 */
		JSONObject josnResult = JSONObject.fromObject(messageBean.getParams());
		String userId =  josnResult.getString("userId");
		String circleId =  josnResult.getString("circleId");
		String createUserId = josnResult.getString("createUserId");
		String userName = josnResult.getString("userName");
		String circleName = josnResult.getString("circleName");
		
		if(circleService.AddUserToCircle(Integer.parseInt(messageBean.getSendUserId()), circleId,SystemDict.NORMAL_USER)){
			MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_NORMAL, "您好，"+userName+"加入了你的["+circleName+"]农场!",userId+"", createUserId+"","");
			MsgQueue.GROUP_QUEUE.add(msgBean);
			
			MessageBean msgBean2 = new MessageBean(SystemDict.MESSAGE_NORMAL, "您好，您成功加入了["+circleName+"]农场!",createUserId+"", userId+"","");
			MsgQueue.GROUP_QUEUE.add(msgBean2);
		}
		return super.firstClickCall(messageBean);
	}

	/*
	 * 拒绝按钮回调方法
	 */
	@Override
	@Transactional
	public boolean secondClickCall(MessageBean messageBean) throws SPTException {
		/**
		 * 发送消息通知申请用户加入圈子被拒绝
		 */
		JSONObject josnResult = JSONObject.fromObject(messageBean.getParams());
		String userId =  josnResult.getString("userId");
		String createUserId = josnResult.getString("createUserId");
		String userName = josnResult.getString("userName");
		String circleName = josnResult.getString("circleName");
		
		MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_NORMAL, "您好，"+userName+"您申请加入["+circleName+"]农场的请求被拒绝了!",userId+"", createUserId+"","");
		MsgQueue.GROUP_QUEUE.add(msgBean);
		
		//将消息置为已读
		return super.firstClickCall(messageBean);
	}

}
