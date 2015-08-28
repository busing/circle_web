package com.circle.service.circle;

import java.util.List;
import java.util.Map;

import com.circle.pojo.circle.Circle;
import com.circle.pojo.user.User;
import com.xwtec.xwserver.exception.SPTException;



/** 
 * @ClassName: ICircleService 
 * @Description: TODO(圈子信息业务逻辑层接口类) 
 * @author Taiyuan
 * @date 2014年12月28日 上午10:08:02 
 *  
*/ 
    
public interface ICircleService {
	/**
	 * 
	 * 方法描述:添加圈子信息
	 * @param circle 圈子实体信息
	 * @return 1添加成功，否则失败
	 * date:2015-01-20
	 * @author zhoujie
	 */
	public int addCicle(Circle circle) throws SPTException;
	
	/**
	 * 
	 * 方法描述:根据圈子ID查询圈子详细信息
	 * @param id 圈子id
	 * @return Circle 圈子详细信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public Circle queryMyCircleById(String id,int userId) throws SPTException;
	
	/**
	 * 
	 * 方法描述:根据圈子ID查询圈子详细信息
	 * @param id 圈子id
	 * @return Circle 圈子详细信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public Circle queryJoinCircleById(String id,int userId) throws SPTException;
	
	
	/**
	 * 
	 * 方法描述:查询圈子列表
	 * @param page 分页查询控件
	 * @return List<Circle> 圈子列表信息
	 * date:2014-12-14
	 * @author wangfengtong@xwtec.cn
	 */
	public List<Circle> queryMyCircleList(int userId) throws SPTException;
	
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
	public List<Circle> queryCircleListByPoint(Double minX,Double minY,Double maxX,Double maxY, Double x, Double y) throws SPTException;
	
	
	/**
	 * 方法描述:根据圈子的id查询圈子信息
	 * @param circlrIds	圈子id 逗号分隔
	 * @return 
	 * date:2015年1月10日
	 * @author Taiyuan
	 */
	public List<Circle> queryCircleListCircleId(String circlrIds) throws SPTException;

	/**
	 * 
	 * 方法描述:用户加入圈子
	 * @param userId 用户ID
	 * @param circleId 圈子ID
	 * @return boolean 是否成功
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public boolean AddUserToCircle(int userId,String circleId,String type) throws SPTException;
	
	/**
	 * 
	 * 方法描述:查询圈子信息
	 * @param circleId 圈子ID
	 * @return Circle 圈子信息
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public Circle queryCircleByCircleId(String circleId) throws SPTException;
	
	
	/**
	 * 方法描述:查询首页第一次加载 初始化的农场
	 * @param 
	 * @return 
	 * date:2015年2月8日
	 * @author Taiyuan
	 */
	public List<Circle> queryInitMapCircle() throws SPTException;
	
	
	/**
	 * 
	 * 方法描述:判断用户是否已加入圈子
	 * @param circleId 圈子ID userId用户ID
	 * @return Circle 圈子信息
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public int queryCircleMemberByCircleIdAndUserId(String circleId,String userId) throws SPTException;
	
	
	/**
	 * 
	 * 方法描述:查询用户是否加入圈子审核
	 * @param circleId 圈子ID userId用户ID
	 * @return int 
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public int queryCircleMemberAuditByCircleIdAndUserId(String circleId,String userId) throws SPTException;
	
	/**
	 * 
	 * 方法描述:用户申请加入圈子（圈子人员审核）
	 * @param userId 用户ID
	 * @param circleId 圈子ID
	 * @return boolean 是否成功
	 * date:2015年1月9日 
	 * @author Cooper
	 */
	public boolean AddUserToCircleAudit(String userId,String circleId,String type,String status) throws SPTException;
	
	/**
	 * 查询用户的圈子列表
	 * @param userId
	 * @return List<Circle>
	 * @throws Exception
	 */
	public List<Circle> queryCirclesByUserId(Object userId) throws Exception;

	/**
	 * 方法描述:查询圈子购买人数
	 * @param 
	 * @return 
	 * date:2015年1月16日
	 * @author Taiyuan
	 */
	public int queryCircleBuyNum(String circleId) throws SPTException;
	
	/**
	 * 方法描述：用户退出圈子
	 * @param paramMap
	 * @return Integer
	 */
	public Integer memberExitCircle(Circle cirlce, User user) throws SPTException;
	
	/**
	 * 方法描述：根据圈子ID查询圈子、用户信息
	 * @return Circle
	 * @throws SPTException
	 * @author zhoujie
	 */
	public Circle queryCircleAndUserByCircleId(Map<String, Object> paramMap) throws SPTException;
	
	/**
	 * 方法描述：根据圈子ID查询用户列表
	 * @param paramMap
	 * @return List<User>
	 * @throws SPTException
	 */
	public List<User> queryUsersByCircleId(Map<String, Object> paramMap) throws SPTException;
	
	/**
	 * 方法描述：保存圈子公告和简介信息
	 * @param paramMap
	 * @return Integer
	 * @throws SPTException
	 */
	public Integer saveCircleInfo(Map<String, Object> paramMap) throws SPTException;
	
	/**
	 * 方法描述：发布发货时间和发货地址
	 * @param paramMap
	 * @return Integer
	 * @throws SPTException
	 */
	public Integer saveCirclePublishInfo(Circle circle, Circle _circle, User user) throws SPTException;
}