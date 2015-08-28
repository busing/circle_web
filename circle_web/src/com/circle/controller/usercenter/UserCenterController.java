package com.circle.controller.usercenter;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.circle.constant.SystemDict;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.user.User;
import com.circle.service.circle.ICircleService;
import com.circle.service.commossion.ICommissionService;
import com.circle.service.msg.IMsgService;
import com.circle.service.order.IOrderService;
import com.circle.service.shopcart.IShopCartService;
import com.circle.service.user.IUserService;
import com.circle.utils.DateUtils;
import com.circle.utils.SystemExceptionUtil;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.FileUtil;
import com.xwtec.xwserver.util.ProUtil;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 用户中心. <br>
 * <p>
 * Copyright: Copyright (c) 2015年1月8日 下午7:05:18
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Controller
@RequestMapping("usercenter")
public class UserCenterController {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(UserCenterController.class);
	
	/**
	 * 注入购物车service
	 */
	@Resource
	IShopCartService shopCartService;
	
	/**
	 * 注入用户service
	 */
	@Resource
	IUserService userService;
	
	/**
	 * 注入购物车service
	 */
	@Resource
	IOrderService orderService;
	
	
	@Resource
	ICircleService circleService;
	
	@Resource
	IMsgService msgService;
	
	@Resource
	ICommissionService commissionService;

	/**
	 * 方法描述:跳转到用户中心首页
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan/zhoujie
	 */
	@RequestMapping("userCenter.action")
	public ModelAndView toUserCenter(ModelAndView mav, HttpServletRequest req){
		try {
			User user = (User) req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			List<Circle> list = circleService.queryCirclesByUserId(user.getId());
			mav.addObject("circles",list);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mav.addObject("circle", null);
		mav.setViewName("/usercenter/usercenter_index.jsp");
		mav.addObject("menuId", "1");
		return mav;
	}
	
	/**
	 * 方法描述:跳转用户中心我的订单内页
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@RequestMapping("orderList.action")
	public ModelAndView orderList(ModelAndView mav,Page page,HttpServletRequest request)
	{
		
		try {
			page=page==null?new Page():page;
			page.setSearchParam(page.getSearchParam()==null?new HashMap<String,String>():page.getSearchParam());
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			List<Map<String, Object>> orderList=orderService.queryOrderList(page, userId);
			StringBuilder orderId=new StringBuilder();
			for (Map<String, Object> map : orderList)
			{
				map.put("order_id", map.get("order_id").toString());
				orderId.append("'" + map.get("order_id") + "'");
				orderId.append(",");
			}
			if(orderId.length()>0)
			{
				orderId.deleteCharAt(orderId.length()-1);
			}
			Map<String, List<Map<String, Object>>> orderDetailList=orderService.queryOrderDetailList(orderId.toString());
			
			mav.addObject("page", page);
			mav.addObject("orderList", orderList);
			mav.addObject("orderDetailList", orderDetailList);
			mav.addObject("menuId", "2");
			mav.setViewName("/usercenter/order.jsp");
		} 
		catch (SPTException e)
		{
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	
	/**
	 * 方法描述:跳转用户中心我的购物车内页
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@RequestMapping("shopcart.action")
	public ModelAndView shopCart(ModelAndView mav,Page page,HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		int userId=user.getId();
		List<Map<String, Object>> shopCartList=null;
		try {
			shopCartList=shopCartService.queryShopCartList(page,userId);
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		mav.addObject("menuId", "3");
		mav.addObject("shopCartList", shopCartList);
		mav.addObject("page", page);
		mav.setViewName("/usercenter/shopcart.jsp");
		return mav;
	}
	
	
	/**
	 * 方法描述:跳转用户中心个人资料内页
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan/zhoujie
	 */
	@RequestMapping("info.action")
	public ModelAndView info(ModelAndView mav, HttpServletRequest req) throws Exception
	{
		User user = (User) req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		user = userService.queryUserById(user.getId() + "");
		req.getSession().setAttribute(ConstantBusiKeys.SessionKey.SYS_USER, user);
		mav.addObject("menuId", "4");
		mav.setViewName("/usercenter/info.jsp");
		return mav;
	}
	
	
	/**
	 * 跳转创建农场页面
	 * @param mav
	 * @param req
	 * @return ModelAndView
	 * @throws Exception
	 * @author zhoujie
	 */
	@RequestMapping("toCreateCircle.action")
	public ModelAndView toCreateCircle(ModelAndView mav) throws Exception{
		mav.setViewName("/circle/circle_edit.jsp");
		Circle circle=new Circle();
		circle.setHeadPath("/images/def_adv.png");
		circle.setImagePath(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN)+"/images/def_adv.png");
		mav.addObject("menuId", "1");
		mav.addObject("circle",circle);
		return mav;
	}
	
	
	/**
	 * 创建农场
	 * @param mav
	 * @param req
	 * @return ModelAndView
	 * @throws Exception
	 * @author zhoujie
	 */
	@RequestMapping("createCircle.action")
	public ModelAndView createCircle(Circle circle, ModelAndView mav, HttpServletRequest req){
		int result = 0;
		try {
			//读取session中的用户信息
			User user = (User)req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			
			//圈子信息
			circle.setCreateUserId(user.getId());
			circle.setCreateUser(user.getId()+"");
			circle.setStatus(SystemDict.CIRCLE_STATUS_AUDIT);
//			circle.setEndTime(DateUtils.getEndTime(circle.getIssueTime()));
			
			//创建圈子
			result = circleService.addCicle(circle);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//设置前台提示信息
		mav.addObject("msg", result > 0 ? "创建成功,审核通过之后您可享受一次不限金额不限人数的免远费试买服务，当你组织起达到5人的团购活动，我们即为您免费送上一斤新鲜可口的苹果。" : "创建失败");
		return toUserCenter(mav, req);
	}
	
	/**
	 * 用户退出圈子
	 * @param mav
	 * @param req
	 * @param circle
	 * @return ModelAndView
	 * @author zhoujie
	 */
	@RequestMapping("memberExitCircle.action")
	public ModelAndView memberExitCircle(ModelAndView mav, HttpServletRequest req, String circleId){
		try {
			//读取session中的用户信息
			User user = (User)req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			
			//查询圈子信息
			Circle circle = circleService.queryCircleByCircleId(circleId);
			
			int res = circleService.memberExitCircle(circle, user);
			
			//前台提示
			mav.addObject("msg", res == 0 ? "未成功退出农场" : "成功退出农场");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return toUserCenter(mav, req);
	}
	
	/**
	 * 根据圈子ID查询圈子信息、用户列表
	 * @param circleId
	 * @return String
	 * @author zhoujie
	 */
	@ResponseBody
	@RequestMapping("queryCircleAndUserByCircleId.action")
	public String queryCircleAndUserByCircleId(String circleId){
		try {
			//设置查询参数集合
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("circleId", circleId);
			
			//获取圈子信息
			Circle circle = circleService.queryCircleAndUserByCircleId(paramMap);
			//获取圈子成员信息列表
			List<User> list = circleService.queryUsersByCircleId(paramMap);
			
			JSONObject json = new JSONObject();
			json.put("circle", circle);
			json.put("list", list);
			return json.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 保存圈子的简介和公告
	 * @param circle
	 * @return ModelAndView
	 * @author zhoujie
	 */
	@ResponseBody
	@RequestMapping("saveCircleInfo.action")
	public Integer saveCircleInfo(Circle circle){
		try {
			//设置保存参数
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("circleId", circle.getId());
			paramMap.put("notice", circle.getNotice());
			paramMap.put("description", circle.getDescription());
			
			return circleService.saveCircleInfo(paramMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 发布发货时间和发货地址
	 * @param mav
	 * @param req
	 * @param circle
	 * @return ModelAndView
	 * @author zhoujie
	 */
	@ResponseBody
	@RequestMapping("saveCirclePublishInfo.action")
	public Integer saveCirclePublishInfo(HttpServletRequest req, Circle circle){
		try {
			//读取session中的用户信息
			User user = (User)req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			Circle _circle = circleService.queryCircleByCircleId(circle.getId());
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("circleId", circle.getId());
//			paramMap.put("type", SystemDict.CIRCLE_HISTORY_TYPE_ISSUE);
//			paramMap.put("intro", _circle.getDescription());
//			paramMap.put("issueTime", circle.getIssueTime());
//			paramMap.put("issueAddress", circle.getIssueAddress());
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(circle.getIssueTime());
			Calendar cal= Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
			cal.set(Calendar.HOUR_OF_DAY, 17);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
//			paramMap.put("endTime", DateUtils.getEndTime(circle.getIssueTime()));
			_circle.setEndTime(DateUtils.getEndTime(circle.getIssueTime()));
//			paramMap.put("createUserId",user.getId());
//			paramMap.put("status", 1);
//			paramMap.put("auditMsg", "");
			
			return circleService.saveCirclePublishInfo(circle, _circle, user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 消息中心
	 * @param mav
	 * @param req
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("messageCenter.action")
	public ModelAndView messageCenter(ModelAndView mav,HttpServletRequest req,Page page)
	{
		try {
			page=page==null?new Page():page;
			page.setSearchParam(page.getSearchParam()==null?new HashMap<String,String>():page.getSearchParam());
			
			if(!page.getSearchParam().containsKey("status") || StringUtil.isEmpty(page.getSearchParam().get("status")))
			{
				page.getSearchParam().put("status", "0");
			}
			User user = (User) req.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			List<Map<String, Object>> messageList = msgService.getUserMessage(page,user.getId());
			mav.addObject("messageList", messageList);
			mav.addObject("menuId", "5");
			mav.addObject("page", page);
			mav.setViewName("/usercenter/message_center.jsp");
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	
	@RequestMapping("myCommossion.action")
	public ModelAndView myCommission(ModelAndView mav,HttpServletRequest request)
	{
		try
		{
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			
			//邀请佣金 包括下单提成
			List<Map<String, Object>> inviteCommissionList=commissionService.statisticsCommossionHistory("", "",user.getId(),SystemDict.COMMISSION_TYPE_INVITE+","+SystemDict.COMMISSION_TYPE_ORDER);
			
			//售卖佣金
			List<Map<String, Object>> circleCommissionList=commissionService.statisticsCommossionHistory("", "",user.getId(),SystemDict.COMMISSION_TYPE_SELLER);
			
			//邀请获得佣金
			String inviteCommission=inviteCommissionList.get(0).get("commission")==null?"0":inviteCommissionList.get(0).get("commission").toString();
			//农场售卖佣金
			String sellerCommission=circleCommissionList.get(0).get("commission")==null?"0":circleCommissionList.get(0).get("commission").toString();
			//用户佣金总额
			Map<String, Object> userCommissionMap=commissionService.queryUserCommossion(user.getId());
			String userCommission="0";
			if(userCommissionMap!=null)
			{
				userCommission=userCommissionMap.get("commission").toString();
			}
			
			//佣金总排行
			List<Map<String, Object>> commissionTop=commissionService.statisticsCommossionHistory(null, null, 0, null);
			
			//推广佣金总排行
			List<Map<String, Object>> inviteCommissionTop=commissionService.statisticsCommossionHistory(null, null, 0, SystemDict.COMMISSION_TYPE_INVITE+","+SystemDict.COMMISSION_TYPE_ORDER);
			
			//农场售卖佣金总排行
			List<Map<String, Object>> sellerCommissionTop=commissionService.statisticsCommossionHistory(null, null, 0, SystemDict.COMMISSION_TYPE_SELLER);
			mav.addObject("inviteCommission", inviteCommission);
			mav.addObject("sellerCommission", sellerCommission);
			mav.addObject("userCommission", userCommission);
			mav.addObject("commissionTop", commissionTop);
			mav.addObject("inviteCommissionTop", inviteCommissionTop);
			mav.addObject("sellerCommissionTop", sellerCommissionTop);
			mav.setViewName("/usercenter/my_commission.jsp");
			mav.addObject("menuId", "6");
		}
		catch (SPTException e)
		{
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	/**
	 * 方法描述:上传群发号码文件 返回文件的绝对路径
	 * @date:2014-1-17
	 */
	@ResponseBody
	@RequestMapping("uploadFile.action")
	public String uploadFile(MultipartFile file,HttpServletRequest request) 
	{
		try {
			/**
			 * 上传文件的根目录
			 */
			String imageFilePath = new String(ProUtil.get("imageFilePath"));
			
			String fileUploadPath=new String(ProUtil.get("fileUploadPath"));
			
			String domain =	new String(ProUtil.get(com.circle.constant.ConstantBusiKeys.PropertiesKey.DOMAIN));
			
			String imgPath="/"+fileUploadPath+"/"+imageFilePath+"/" + FileUtil.upload(file, imageFilePath,request.getRealPath("/"));
			/**
			 * 文件上传后保存的绝对路径
			 */
			String path = domain+imgPath;
			/**
			 * 构造返回数据的json对象
			 */
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("imgPath", imgPath);
			jsonObj.accumulate("path", path);
			/**
			 * 返回的json字符串
			 */
			return jsonObj.toString();
		} catch (IllegalStateException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} catch (IOException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("morderList.action")
	public Map<String, Object> mOrderList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page page = new Page();
			Map<String,String> searchParam = new HashMap<String, String>();
			page.setSearchParam(searchParam);
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			map.put("user", user);
			int userId=user.getId();
			
			String pageSize = request.getParameter("pageSize");
			String pageNo = request.getParameter("pageNo");
			
			if(!StringUtil.isEmpty(pageSize)){
				page.setRowsPerPage(Integer.parseInt(pageSize));
			}else{
				page.setRowsPerPage(5);
			}
			
			if(!StringUtil.isEmpty(pageNo)){
				page.setCurrentPage(Integer.parseInt(pageNo));
			}
			
			List<Map<String, Object>> orderList=orderService.queryOrderList(page, userId);
			for (Map<String, Object> order : orderList)	{
				StringBuilder orderId=new StringBuilder();
				orderId.append("'" + order.get("order_id") + "'");
				List<Map<String, Object>> orderDetailList = orderService.queryMOrderDetailList(orderId.toString());
				order.put("orderDetailList", orderDetailList);
			}
			
			map.put("orderList", orderList);
			
			map.put("code", 1);
			map.put("message", "查询成功！");
		} catch (SPTException e){
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			map.put("code", 0);
			map.put("message", "查询失败，请稍后再试！");
		}
		return map;
	}
	
}