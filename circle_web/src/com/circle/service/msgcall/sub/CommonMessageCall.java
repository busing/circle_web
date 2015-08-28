package com.circle.service.msgcall.sub;

import com.circle.pojo.msg.MessageBean;
import com.circle.service.msgcall.AMessageCall;
import com.xwtec.xwserver.exception.SPTException;

/**
 * 普通消息处理回调子类（已读和删除按钮类型的消息）
 * @author Taiyuan
 *
 */
public class CommonMessageCall extends AMessageCall
{

	/* 
	 * 已读按钮回调方法
	 */
	@Override
	public boolean firstClickCall(MessageBean messageBean) throws SPTException
	{
		return super.firstClickCall(messageBean);
	}

	/* 
	 * 删除按钮回调方法
	 */
	@Override
	public boolean secondClickCall(MessageBean messageBean) throws SPTException
	{
		return super.secondClickCall(messageBean);
	}

	
	
}
