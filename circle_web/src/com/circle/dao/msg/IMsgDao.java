package com.circle.dao.msg;

import java.util.List;
import java.util.Map;

import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.msg.MessageTypeBean;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;


/**
 * 
 * 消息DAO层方法统一接口. <br>
 * <p>
 * Copyright: Copyright (c) 2015-1-13 上午10:50:33
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
public interface IMsgDao {
	/**
	 * 
	 * 方法描述:批量添加消息信息
	 * @param paramListMap 添加消息参数
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public void batchAddMsgInfo(List<Map<String, ?>> paramListMap) throws SPTException;
	
	
	/**
	 * 查询消息类型数据
	 * @return
	 * @throws SPTException
	 */
	public List<MessageTypeBean> queryMessageTypeBeans()  throws SPTException;
	
	/**
	 * 
	 * 方法描述:修改消息状态
	 * @param paramMap 消息参数
	 * @return 大于0修改成功，否则修改失败
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public int updateMsgStatus(Map<String, ?> paramMap) throws SPTException;


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
	 * 方法描述:根据消息ID查询消息详细信息
	 * @param id 消息ID
	 * @return 消息详细信息
	 * date:2015-1-20
	 * @author wangfengtong@xwtec.cn
	 */
	public MessageBean getMessageInfoById(String id)  throws SPTException;
	
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
