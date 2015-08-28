package com.circle.service.msgcall;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.circle.pojo.msg.MessageBean;
import com.circle.service.msg.IMsgService;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.SpringUtils;

/**
 * 消息中心按钮操作回调抽象类
 * @author Taiyuan
 *
 */
public abstract class AMessageCall
{
	
	/**
	 * 注入DAO方法
	 */
//	@Resource
//	private IMsgService msgService;
	
	/**
	 * 第一个按钮回调事件（已读，同意）
	 * @param messageBean
	 * @return
	 * @throws SPTException 
	 */
	public boolean firstClickCall(MessageBean messageBean) throws SPTException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", messageBean.getId());
		paramMap.put("status", "1");
		IMsgService msgService = SpringUtils.getBean("msgService");
		return msgService.updateMsgStatus(paramMap);
	}
	
	/**
	 * 第二个按钮回调时间（删除、拒绝）
	 * @param messageBean
	 * @return
	 * @throws SPTException 
	 */
	public boolean secondClickCall(MessageBean messageBean) throws SPTException
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", messageBean.getId());
		paramMap.put("status", "-1");
		IMsgService msgService = SpringUtils.getBean("msgService");
		return msgService.updateMsgStatus(paramMap);
	}
	
}
