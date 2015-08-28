package com.circle.service.msg.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.circle.constant.SystemDict;
import com.circle.dao.msg.IMsgDao;
import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.msg.MessageTypeBean;
import com.circle.service.msg.IMsgService;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 
 * 消息业务逻辑层实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2015-1-13 上午11:17:45
 * <p>
 * @version 1.0.0
 */
@Service("msgService")
public class MsgServiceImpl implements IMsgService {
	
	public static final Logger logger=Logger.getLogger(MsgServiceImpl.class);

	/**
	 * 注入DAO方法
	 */
	@Resource
	private IMsgDao msgDao;
	
	//第一个按钮规定的回调方法名称
	public static final String FIRSTCLICKCALL="firstClickCall";
	
	//第二个按钮规定的回调方法名称
	public static final String SECONDCLICKCALL="secondClickCall";
	
	
	
	/**
	 * 第一个按钮回调事件（已读，同意）
	 * @param messageBean
	 * @return
	 */
	@Override
	public boolean firstClickCall(String msgId) 
	{
		try
		{
			MessageBean messageBean = msgDao.getMessageInfoById(msgId);
			MessageTypeBean typeBean= SystemDict.MESSAGE_TYPE_MAP.get(messageBean.getTypeId());
			//反射按钮对应的回调类
			Class<?> clazz = Class.forName(typeBean.getClasspath());
			Object obj = clazz.newInstance();
			Method rM = obj.getClass().getDeclaredMethod(FIRSTCLICKCALL,new Class[]{MessageBean.class});
			//调用规定的方法
			boolean flag=(Boolean)rM.invoke(obj,messageBean);
			return flag;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}



	/**
	 * 第二个按钮回调事件（已读，同意）
	 * @param messageBean
	 * @return
	 */
	@Override
	public boolean secondClickCall(String msgId)
	{
		try
		{
			MessageBean messageBean = msgDao.getMessageInfoById(msgId);
			MessageTypeBean typeBean= SystemDict.MESSAGE_TYPE_MAP.get(messageBean.getTypeId());
			//反射按钮对应的回调类
			Class<?> clazz = Class.forName(typeBean.getClasspath());
			Object obj = clazz.newInstance();
			Method rM = obj.getClass().getDeclaredMethod(SECONDCLICKCALL,new Class[]{MessageBean.class});
			//调用规定的方法
			boolean flag=(Boolean)rM.invoke(obj,messageBean);
			return flag;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		
	}




	/**
	 * 
	 * 方法描述:批量添加消息信息
	 * @param msgList 消息实体列表
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public void batchAddMsgInfo(List<MessageBean> msgList) throws SPTException {
		/**
		 * 查询条件list
		 */
		List<Map<String, ?>> paramList = new ArrayList<Map<String,?>>();
		
		/**
		 * 循环msgList给paramList赋值
		 */
		for(MessageBean msg : msgList){
			
			Map<String, Object> paramMap = new HashMap<String, Object>() ;
			
			paramMap.put("typeId", msg.getTypeId());//消息类型
			paramMap.put("msgCountent", msg.getMsgContent()); //消息内容
			paramMap.put("sendUserid", msg.getSendUserId());  //发送人
			paramMap.put("reciveUserid", msg.getReciveUserId());  //接收人
			paramMap.put("params", msg.getParams());  //消息参数
			
			/**
			 * 添加到paramList
			 */
			paramList.add(paramMap) ;
		}
		/**
		 * 批量更新
		 */
		msgDao.batchAddMsgInfo(paramList);
	}
	
	
	/**
	 * 
	 * 方法描述:修改消息状态
	 * @param paramMap 消息参数
	 * @return 大于0修改成功，否则修改失败
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public boolean updateMsgStatus(Map<String, ?> paramMap) throws SPTException{
		int result = msgDao.updateMsgStatus(paramMap);
		return result > 0 ? true:false;
	}



	/**
	 * 方法描述:查询用户的消息
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	@Override
	public List<Map<String, Object>> getUserMessage(Page page,int id) throws SPTException {
		return msgDao.getUserMessage(page,id);
	}
	
	/**
	 * 
	 * 方法描述:查询用户消息数量
	 * @param userId 用户ID
	 * @return 消息数量
	 * date:2015-1-23
	 * @author wangfengtong@xwtec.cn
	 */
	public int queryMsgCount(String userId) throws SPTException{
		return msgDao.queryMsgCount(userId);
	}
	
	/**
	 * 
	 * 方法描述:查询圈子消息
	 * @param sendUserId 发送用户ID reciveUserId接收用户id
	 * @return 消息数量
	 * date:2015-1-23
	 * @author Cooper
	 */
	public int queryCircleMsgCount(String sendUserId,String reciveUserId) throws SPTException {
		return msgDao.queryCircleMsgCount(sendUserId, reciveUserId);
	}
	
}
