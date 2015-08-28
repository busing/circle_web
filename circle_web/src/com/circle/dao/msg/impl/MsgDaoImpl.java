package com.circle.dao.msg.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.circle.dao.msg.IMsgDao;
import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.msg.MessageTypeBean;
import com.xwtec.xwserver.dao.common.ICommonDao;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;

/**
 * 
 * 用户DAO接口实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014年12月16日 10:50:42
 * <p>
 * Company: Cooper's Summber
 * <p>
 * @author Cooper
 * @version 1.0.0
 */
@Repository
public class MsgDaoImpl implements IMsgDao {
	
	/**
	 * 日志类 logger 
	 */
	private static final Logger logger = Logger.getLogger(MsgDaoImpl.class) ;
	
	/**
	 * 添加消息信息
	 */
	public static final String ADD_MSG_INFO = "insert into t_message(type_id,msg_content,create_time,status,send_userid,recive_userid,params) " +
			" values(:typeId,:msgCountent,sysdate(),0,:sendUserid,:reciveUserid,:params)";
	
	/**
	 * 查询消息类型
	 */
	public static final String QUERY_MESSAGETYP_SQL="select id,type_name typeName,classpath,button1,button2 from t_message_type";
	
	public static final String UPDATE_MSG_STATUS = "update t_message set status = :status where id = :id";
	
	/**
	 * 查询用户消息
	 * QUERY_USER_MESSAGE_LIST 
	 */
	public static final String QUERY_USER_MESSAGE_LIST="select m.id mid,m.type_id,m.msg_content,m.params,m.status,mt.*,u.name,u.nickname,u.head_image,date_format(m.create_time,'%Y-%m-%d %H:%i:%s') send_time from " +
			"t_message m,t_message_type mt,t_user u where mt.id=m.type_id and u.id=m.send_userid and m.status <> -1 and recive_userid=:userId and m.status = :status order by m.create_time desc";
	
	public static final String QUERY_MESSATE_INFO = "select id,type_id typeId,msg_content msgContent,create_time createTime,status,send_userid sendUserId,recive_userid reciveUserId,params from t_message" +
			" where id = :id and status <> -1";
	
	public static final String QUERY_MSG_CNT = "select count(*) from t_message where status = 0 and recive_userid=:userId";
	
	public static final String QUERY_CIRCLE_MSG_BY_USERID_AND_RECIVEID = "select count(*)  from t_message "+
			" where send_userid = :sid and recive_userid=:rid and type_id = '2' and status <> -1";
	
	/**
	 * 注入共通DAO方法类 commonDao 
	 */
	@Resource
	ICommonDao commonDao;
	
	/**
	 * 
	 * 方法描述:批量添加消息信息
	 * @param paramListMap 添加消息参数
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public void batchAddMsgInfo(List<Map<String, ?>> paramListMap) throws SPTException {
		logger.debug("[UserDaoImpl.queryUserList] start...") ;
		commonDao.batchUpdate(ADD_MSG_INFO, paramListMap);
		logger.debug("[UserDaoImpl.queryUserList] end...") ;
	}

	/**
	 * 方法描述:查询消息类型
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	@Override
	public List<MessageTypeBean> queryMessageTypeBeans() throws SPTException 
	{
		return commonDao.queryForList(QUERY_MESSAGETYP_SQL, MessageTypeBean.class);
	}
	
	/**
	 * 
	 * 方法描述:修改消息状态
	 * @param paramMap 消息参数
	 * @return 大于0修改成功，否则修改失败
	 * date:2015-1-13
	 * @author wangfengtong@xwtec.cn
	 */
	public int updateMsgStatus(Map<String, ?> paramMap) throws SPTException{
		return commonDao.update(UPDATE_MSG_STATUS, paramMap);
	}
	
	
	/**
	 * 方法描述:查询用户的消息
	 * @param 
	 * @return 
	 * date:2015年1月19日
	 * @author Taiyuan
	 */
	@Override
	public List<Map<String, Object>> getUserMessage(Page page,int id)  throws SPTException
	{
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("userId", id);
		paramsMap.put("status", page.getSearchParam().get("status"));
		return commonDao.queryForList(QUERY_USER_MESSAGE_LIST,paramsMap,page);
	}
	
	/**
	 * 
	 * 方法描述:根据消息ID查询消息详细信息
	 * @param id 消息ID
	 * @return 消息详细信息
	 * date:2015-1-20
	 * @author wangfengtong@xwtec.cn
	 */
	public MessageBean getMessageInfoById(String id)  throws SPTException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", id);
		return commonDao.queryForObject(QUERY_MESSATE_INFO, paramsMap, MessageBean.class);
	}
	
	/**
	 * 
	 * 方法描述:查询用户消息数量
	 * @param userId 用户ID
	 * @return 消息数量
	 * date:2015-1-23
	 * @author wangfengtong@xwtec.cn
	 */
	public int queryMsgCount(String userId) throws SPTException {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		return commonDao.queryForInt(QUERY_MSG_CNT, paramsMap);
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
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sid", sendUserId);
		paramsMap.put("rid", reciveUserId);
		return commonDao.queryForInt(QUERY_CIRCLE_MSG_BY_USERID_AND_RECIVEID, paramsMap);
	}
	
}
