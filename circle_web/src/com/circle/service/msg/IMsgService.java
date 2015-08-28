package com.circle.service.msg;

import java.util.List;
import java.util.Map;

import com.circle.pojo.msg.MessageBean;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;


/**
 * 
 * 消息业务逻辑层接口类. <br>
 * <p>
 * Copyright: Copyright (c) 2015-1-13 上午11:17:14
 * <p>
 * @version 1.0.0
 */
public interface IMsgService {
	
	/**
	 * 
	 * 方法描述:批量添加消息信息
	 * @param msgList 消息实体列表
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public void batchAddMsgInfo(List<MessageBean> msgList) throws SPTException;
	
	
	/**
	 * 第一个按钮回调事件（已读，同意）
	 * @param messageBean
	 * @return
	 */
	public boolean firstClickCall(String msgId)  throws SPTException;
	
	
	/**
	 * 第二个按钮回调时间（删除、拒绝）
	 * @param messageBean
	 * @return
	 */
	public boolean secondClickCall(String msgId)  throws SPTException;
	
	/**
	 * 
	 * 方法描述:修改消息状态
	 * @param paramMap 消息参数
	 * @return 大于0修改成功，否则修改失败
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public boolean updateMsgStatus(Map<String, ?> paramMap) throws SPTException;


	/**
	 * 方法描述:查询用户的消息
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	public List<Map<String, Object>> getUserMessage(Page page,int id)  throws SPTException;
	
	/**
	 * 
	 * 方法描述:查询用户消息数量
	 * @param userId 用户ID
	 * @return 消息数量
	 * date:2015-1-23
	 * @author wangfengtong@xwtec.cn
	 */
	public int queryMsgCount(String userId) throws SPTException;
	
	/**
	 * 
	 * 方法描述:查询圈子消息
	 * @param sendUserId 发送用户ID reciveUserId接收用户id
	 * @return 消息数量
	 * date:2015-1-23
	 * @author Cooper
	 */
	public int queryCircleMsgCount(String sendUserId,String reciveUserId) throws SPTException;
	
	
}
