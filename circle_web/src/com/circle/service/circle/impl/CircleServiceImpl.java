package com.circle.service.circle.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.circle.constant.SystemDict;
import com.circle.dao.circle.ICircleDao;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.circle.CircleMember;
import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.user.User;
import com.circle.service.circle.ICircleService;
import com.circle.utils.FormatUtil;
import com.circle.utils.MapUtil;
import com.circle.utils.msg.MsgQueue;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.ProUtil;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 
 * 圈子信息业务逻辑层实现类. <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-14 下午09:33:38
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
@Service("circleService")
public class CircleServiceImpl implements ICircleService {
	@Override
	public int addCicle(Circle circle) throws SPTException {
		int res = 0;
		try {
			circleDao.addCicle(circle);
			CircleMember member = new CircleMember();
			member.setCircleId(Integer.parseInt(circle.getId()));
			member.setIdentityType(1002);
			member.setUserId(circle.getCreateUserId());
			int result = circleDao.addCircleMember(member);
			if(result == 0)
				return 0;

			//发送消息
			MessageBean msg = new MessageBean();
			msg.setTypeId("1");
			msg.setMsgContent("温馨提示：您已成功创建了" + circle.getName() + "农场,我们将尽快为您审核,请耐心等待!");
			msg.setSendUserId("1");
			msg.setReciveUserId(circle.getCreateUser());
			msg.setParams(circle.getId());
			MsgQueue.GROUP_QUEUE.add(msg);
			
			res = 1;
		} catch (SPTException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return res;
	}

	/**
	 * 注入DAO方法
	 */
	@Resource
	private ICircleDao circleDao;
	

	/**
	 * 
	 * 方法描述:根据圈子ID查询圈子详细信息
	 * @param id 圈子id
	 * @return Circle 圈子详细信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public Circle queryMyCircleById(String id,int userId) throws SPTException{
		Circle circle= circleDao.queryMyCircleById(id,userId);
		circle.setHeadPath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+circle.getHeadPath());
		return circle;
	}
	
	@Override
	public Circle queryJoinCircleById(String id, int userId) throws SPTException {
		Circle circle= circleDao.queryJoinCircleById(id,userId);
		circle.setHeadPath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+circle.getHeadPath());
		return circle;
	}

	/**
	 * 
	 * 方法描述:查询圈子列表
	 * @param page 分页查询控件
	 * @return List<Circle> 圈子列表信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<Circle> queryMyCircleList(int userId) throws SPTException{
		List<Circle> circleList= circleDao.queryMyCircleList(userId);
		for (Circle circle : circleList)
		{
			circle.setHeadPath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+circle.getHeadPath());
		}
		return circleList;
	}

	/**
	 * 
	 * 方法描述:查询圈子列表根据经纬度范围
	 *  @param minX 最小经度
	 *  @param minY 最小纬度
	 *  @param maxX 最大经度
	 *  @param maxY 最大纬度
	 * @return List<Circle> 圈子列表信息
	 * date:2014-12-18
	 * @author Cooper
	 */
	public List<Circle> queryCircleListByPoint(Double minX,Double minY,Double maxX,Double maxY, Double x, Double y) throws SPTException
	{
		List<Circle> circleList= circleDao.queryCircleListByPoint(minX, minY, maxX, maxY);
		for (Circle circle : circleList){
			double dis = MapUtil.getDistanceFromXtoY(y, x, FormatUtil.parseObject2Double(circle.getLatitude()), FormatUtil.parseObject2Double(circle.getLongitude()));
			
			circle.setHeadPath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+circle.getHeadPath());
			circle.setDistance(MapUtil.getMapDistance(dis));
		}
		return circleList;
	}
	
	/**
	 * 方法描述:根据圈子的id查询圈子信息
	 * @param circlrIds	圈子id 逗号分隔
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	public List<Circle> queryCircleListCircleId(String circlrIds) throws SPTException
	{
		return	circleDao.queryCircleListCircleId(circlrIds);
	}

	/**
	 * 
	 * 方法描述:用户加入圈子
	 * @param userId 用户ID
	 * @param circleId 圈子ID
	 * @return boolean 是否成功
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public boolean AddUserToCircle(int userId,String circleId,String type) throws SPTException{
		return circleDao.AddUserToCircle(userId+"", circleId,type);
	}
	
	/**
	 * 
	 * 方法描述:查询圈子信息
	 * @param circleId 圈子ID
	 * @return Circle 圈子信息
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public Circle queryCircleByCircleId(String circleId) throws SPTException{
		Circle cirlce= circleDao.queryCircleByCircleId(circleId);
		if(cirlce != null && !StringUtil.isEmpty(cirlce.getWeixinImage()))
		{
			cirlce.setWeixinImage(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+cirlce.getWeixinImage());
		}
		return cirlce;
	}
	
	
	
	/**
	 * 方法描述:查询首页第一次加载 初始化的农场
	 * @param 
	 * @return 
	 * date:2015年2月8日
	 * @author Taiyuan
	 */
	@Override
	public List<Circle> queryInitMapCircle() throws SPTException 
	{
		List<Circle> circle_list= circleDao.queryInitMapCircle();
		for (Circle circle : circle_list) {
			circle.setImagePath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN) + circle.getHeadPath());
		}
		return circle_list;
	}

	/**
	 * 
	 * 方法描述:判断用户是否已加入圈子
	 * @param circleId 圈子ID userId用户ID
	 * @return Circle 圈子信息
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public int queryCircleMemberByCircleIdAndUserId(String circleId,String userId) throws SPTException{
		return circleDao.queryCircleMemberByCircleIdAndUserId(circleId,userId);
	}
	
	/**
	 * 
	 * 方法描述:查询用户是否加入圈子审核
	 * @param circleId 圈子ID userId用户ID
	 * @return int 
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public int queryCircleMemberAuditByCircleIdAndUserId(String circleId,String userId) throws SPTException{
		return circleDao.queryCircleMemberAuditByCircleIdAndUserId(circleId,userId);
	}
	
	/**
	 * 
	 * 方法描述:用户申请加入圈子（圈子人员审核）
	 * @param userId 用户ID
	 * @param circleId 圈子ID
	 * @return boolean 是否成功
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public boolean AddUserToCircleAudit(String userId,String circleId,String type,String status) throws SPTException{
		return circleDao.AddUserToCircleAudit(userId,circleId,type,status);
	}
	
	/**
	 * 查询用户的圈子列表
	 * @param userId
	 * @return List<Circle>
	 * @throws Exception
	 */
	public List<Circle> queryCirclesByUserId(Object userId) throws Exception{
		List<Circle> list = circleDao.queryCirclesByUserId(userId);
		Circle circle = null;
		for (int i = 0; list != null && i < list.size(); i++) {
			circle = list.get(i);
			circle.setImagePath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN) + circle.getHeadPath());
		}
		return list;
	}
	
	/**
	 * 方法描述:查询圈子购买人数
	 * @param 
	 * @return 
	 * date:2015年1月16日
	 * @author Taiyuan
	 */
	@Override
	public int queryCircleBuyNum(String circleId) throws SPTException 
	{
		return circleDao.queryCircleBuyNum(circleId);
	}

	/**
	 * 方法描述：用户退出圈子
	 * @param paramMap
	 * @return Integer
	 */
	public Integer memberExitCircle(Circle circle, User user) throws SPTException {
		int res = 0;
		try {
			//设置参数
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("circleId", circle.getId());
			paramMap.put("userId", user.getId());
			
			int result = circleDao.memberExitCircle(paramMap);
			if(result == 0)
				return 0;
			
			//发送消息
			MessageBean msg = new MessageBean();
			msg.setTypeId("1");
			msg.setMsgContent("温馨提示：" + user.getName() + "退出了" + circle.getName() + "农场!");
			msg.setSendUserId(user.getId() + "");
			msg.setReciveUserId(circle.getCreateUserId() + "");
			msg.setParams(circle.getId());
			MsgQueue.GROUP_QUEUE.add(msg);
			res = 1;
		} catch (SPTException ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		return res;
	}

	/**
	 * 方法描述：根据圈子ID查询圈子、用户信息
	 * @return Circle
	 * @throws SPTException
	 * @author zhoujie
	 */
	@Override
	public Circle queryCircleAndUserByCircleId(Map<String, Object> paramMap) throws SPTException {
		return circleDao.queryCircleAndUserByCircleId(paramMap);
	}

	/**
	 * 方法描述：根据圈子ID查询用户列表
	 * @param paramMap
	 * @return List<User>
	 * @throws SPTException
	 */
	@Override
	public List<User> queryUsersByCircleId(Map<String, Object> paramMap) throws SPTException {
		List<User> list = circleDao.queryUsersByCircleId(paramMap);
		User user = null;
		for (int i = 0; list != null && i < list.size(); i++) {
			user = list.get(i);
			user.setImagePath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN) + user.getHeadImage());
		}
		return list;
	}

	/**
	 * 方法描述：保存圈子公告和简介信息
	 * @param paramMap
	 * @return Integer
	 * @throws SPTException
	 */
	@Override
	public Integer saveCircleInfo(Map<String, Object> paramMap) throws SPTException {
		return circleDao.saveCircleInfo(paramMap);
	}

	/**
	 * 方法描述：发布发货时间和发货地址
	 * @param paramMap
	 * @return Integer
	 * @throws SPTException
	 */
	@Override
	public Integer saveCirclePublishInfo(Circle circle, Circle _circle, User user) throws SPTException {
		int res = 0;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("circleId", circle.getId());
			paramMap.put("type", _circle.getJoinType());
			paramMap.put("intro", _circle.getDescription());
			paramMap.put("issueTime", circle.getIssueTime());
			paramMap.put("issueAddress", circle.getIssueAddress());
			paramMap.put("endTime", _circle.getEndTime());
			paramMap.put("createUserId", _circle.getCreateUserId());
			paramMap.put("createTime", _circle.getCreateTime());
			paramMap.put("endTime", _circle.getEndTime());
			paramMap.put("status", SystemDict.CIRCLE_HISTORY_STATUS_AUDIT);
			paramMap.put("auditMsg", "");
			
			int result = circleDao.saveCirclePublishInfo(paramMap);
			if(result == 0)
				return 0;
			
			//发送消息
//			MessageBean msg = new MessageBean();
//			msg.setTypeId("1");
//			msg.setMsgContent("温馨提示：您已成功发布了" + _circle.getName() + "农场的发货时间和地点,我们将尽快为您审核,请耐心等待!");
//			msg.setSendUserId("1");
//			msg.setReciveUserId(user.getId() + "");
//			msg.setParams(circle.getId());
//			MsgQueue.GROUP_QUEUE.add(msg);
			res = 1;
		} catch (SPTException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return res;
	}
}