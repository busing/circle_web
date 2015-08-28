package com.circle.thread;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.circle.constant.SystemDict;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.user.User;
import com.circle.service.circle.ICircleService;
import com.circle.service.msg.IMsgService;
import com.circle.utils.msg.MsgQueue;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.SpringUtils;

public class JoinCircleThread implements Runnable {
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(JoinCircleThread.class);
	
	private String circleId;

	private User user;

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		try {
			System.out.println("get circleId:" + circleId);
			ICircleService circleService = SpringUtils.getBean("circleService");
			IMsgService msgService = SpringUtils.getBean("msgService");
			
			Circle circle = circleService.queryCircleByCircleId(circleId);
			if(user.getId() != circle.getCreateUserId()){
				//圈子 允许 直接加入
				if(circle.getJoinType().equals(SystemDict.CIRCLE_AUTO_JOIN)){
					boolean isMember = circleService.queryCircleMemberByCircleIdAndUserId(circleId, user.getId()+"") == 0 ? false:true;
					
					if(!isMember){
						 circleService.AddUserToCircle(user.getId(), circleId,SystemDict.NORMAL_USER);;
					}
				}else{
					JSONObject jsonResult = new JSONObject();
					jsonResult.put("userId", user.getId());
					jsonResult.put("circleId",circleId);
					jsonResult.put("createUserId",circle.getCreateUserId());
					jsonResult.put("userName", user.getName());
					jsonResult.put("circleName", circle.getName());
					
					//需要农场主审核
					if(circleService.queryCircleMemberAuditByCircleIdAndUserId(circleId, user.getId()+"") == 0){
						boolean flag = circleService.AddUserToCircleAudit( user.getId()+"", circleId, SystemDict.NORMAL_USER, SystemDict.EXAMINEING);
						if(flag){
							//消息记录中没有，添加消息
							if(msgService.queryCircleMsgCount(user.getId()+"",circle.getCreateUserId()+"") == 0){
								MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_CIRCLE, "您好，"+user.getName()+"申请加入你的[!"+circle.getName()+"]农场!",user.getId()+"", circle.getCreateUserId()+"",jsonResult.toString());
								MsgQueue.GROUP_QUEUE.add(msgBean);
							}
						}
					}
				}
			}
			
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
	}

}
