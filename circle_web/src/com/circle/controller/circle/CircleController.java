package com.circle.controller.circle;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.circle.constant.SystemDict;
import com.circle.pojo.batchsell.BatchSell;
import com.circle.pojo.circle.Circle;
import com.circle.pojo.msg.MessageBean;
import com.circle.pojo.user.User;
import com.circle.service.batchsell.IBatchSellService;
import com.circle.service.circle.ICircleService;
import com.circle.service.good.IGoodService;
import com.circle.service.msg.IMsgService;
import com.circle.service.user.IUserService;
import com.circle.utils.SystemExceptionUtil;
import com.circle.utils.msg.MsgQueue;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.DateUtils;
import com.xwtec.xwserver.util.StringUtil;
import com.xwtec.xwserver.util.json.JSONArray;
import com.xwtec.xwserver.util.json.JSONObject;

/**
 * @author Taiyuan
 * 农场controller
 */
@Controller
@RequestMapping("circle")
public class CircleController {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(CircleController.class);

	/**
	 * 注入圈子管理service
	 */
	@Resource
	ICircleService circleService;
	
	/**
	 * 注入消息service
	 */
	@Resource
	IMsgService msgService;
	
	/**
	 * 注入用户管理service
	 */
	@Resource
	IUserService userService;
	
	/**
	 * 注入商品service
	 */
	@Resource
	IGoodService goodService;
	
	/**
	 * 注入商品service
	 */
	@Resource
	IBatchSellService batchSellService;


	
	/**
	 * 用户农场首页
	 * @param mav
	 * @param userid
	 * @param circleId
	 * @return
	 */
	@RequestMapping("circleIndex.action")
	public ModelAndView circleIndex(ModelAndView mav,String circleId,Page page,HttpServletRequest request)
	{
		try {
			boolean isMember=false;
			boolean isOutDate=false;
			boolean isCircleAdmin=false;
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			if(user!=null)
			{
				isMember=circleService.queryCircleMemberByCircleIdAndUserId(circleId, user.getId()+"")>0?true:false;
			}
			Circle circle=circleService.queryCircleByCircleId(circleId);
			mav.addObject("circle",circle);
			
			//判断发放货物时间是否过期
			if(!StringUtil.isEmpty(circle.getIssueTime()))
			{
				long current=System.currentTimeMillis();
				long issueLoneTime=0;
				try {
					issueLoneTime=DateUtils.parse( circle.getIssueTime(), "yyyy-MM-dd HH:mm").getTime() ;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(issueLoneTime-current<=0)
				{
					isOutDate=true;
				}
			}
			else
			{
				isOutDate=true;
			}	
			
			if(user!=null)
			{
				if(user.getId()==circle.getCreateUserId())
				{
					isCircleAdmin=true;
				}
			}
			
			
			
			BatchSell batchSell=batchSellService.queryCurrentBatchSell();
			mav.addObject("batchSell",batchSell);
			List<Map<String, Object>> goodList=null;
			if(batchSell!=null)
			{
				goodList=goodService.queryCurrentBatchGoodList(page, batchSell.getId());
			}
			mav.addObject("goodList", goodList);
			int buyNum=circleService.queryCircleBuyNum(circleId);
			mav.addObject("buyNum",buyNum);
			mav.addObject("isMember",isMember);
			mav.addObject("isOutDate",isOutDate);
			mav.addObject("isCircleAdmin",isCircleAdmin);
			mav.setViewName("/circle_index.jsp");
		} catch (SPTException e) {
			SystemExceptionUtil.logSPTException(mav, logger, e);
		}
		return mav;
	}
	
	
	/**
	 * 方法描述:加入圈子（农场）
	 * @param 
	 * @return 
	 * date:2015年1月7日
	 * @author Cooper
	 */
	@ResponseBody
	@RequestMapping("joinCircle.action")
	public String joinCircle(String circleId,HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject json = new JSONObject();
		String msg = "";
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		if(null == user){
			msg = "";
			json.put("msg", msg);
			return json.toString();
		}
		try {
			Circle circle = circleService.queryCircleByCircleId(circleId);
			if(user.getId() == circle.getCreateUserId()){
				msg = "您是农场主,无须加入!";
				json.put("msg", msg);
				return json.toString();
			}
			//圈子 允许 直接加入
			if(circle.getJoinType().equals(SystemDict.CIRCLE_AUTO_JOIN)){
				msg = circleService.queryCircleMemberByCircleIdAndUserId(circleId, user.getId()+"") == 0 ? "":"您已是农场成员!";
				if(!msg.equals("")){
					json.put("msg", msg);
					return json.toString();
				}
				
				msg = circleService.AddUserToCircle(user.getId(), circleId,SystemDict.NORMAL_USER) == true ? "success":"加入失败!";
				
				if(msg.equals("success")){
					List<Circle> myCircle = circleService.queryMyCircleList( user.getId());
					request.getSession().setAttribute(ConstantBusiKeys.SessionKey.MY_CIRCLE, myCircle);
				
					MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_NORMAL, "您好，"+user.getName()+"加入了你的["+circle.getName()+"]农场!",user.getId()+"", circle.getCreateUserId()+"","");
					MsgQueue.GROUP_QUEUE.add(msgBean);
				}
			}else
			{
				JSONObject jsonResult = new JSONObject();
				jsonResult.put("userId", user.getId());
				jsonResult.put("circleId",circleId);
				jsonResult.put("createUserId",circle.getCreateUserId());
				jsonResult.put("userName", user.getName());
				jsonResult.put("circleName", circle.getName());
				
				//需要农场主审核
				if( circleService.queryCircleMemberAuditByCircleIdAndUserId(circleId, user.getId()+"") == 0){
					boolean flag = circleService.AddUserToCircleAudit( user.getId()+"", circleId, SystemDict.NORMAL_USER, SystemDict.EXAMINEING);
					if(flag){
						//消息记录中没有，添加消息
						if(msgService.queryCircleMsgCount(user.getId()+"",circle.getCreateUserId()+"") == 0){
							MessageBean msgBean = new MessageBean(SystemDict.MESSAGE_CIRCLE, "您好，"+user.getName()+"申请加入你的[!"+circle.getName()+"]农场!",user.getId()+"", circle.getCreateUserId()+"",jsonResult.toString());
							MsgQueue.GROUP_QUEUE.add(msgBean);
						}
						msg ="已发送加入申请，请等待审核!";
					}else{
						msg ="加入农场失败!";
					}
				}else{
					msg ="您已是农场成员!";
				}
			}
		} catch (SPTException e) {
			e.printStackTrace();
			msg = "加入农场失败!";
		}
		json.put("msg", msg);
		
		return json.toString();
	}
	
	
	/**
	 * 
	 * 方法描述:查询圈子列表根据经纬度范围
	 *  @param x 经度
	 *  @param y 纬度
	 * @return List<Circle> 圈子列表信息
	 * date:2014-12-18
	 * @author Cooper
	 */
	@ResponseBody
	@RequestMapping("getCircleByPoint.action")
	public String getCircleByPoint(String x,String y,HttpServletRequest request,HttpServletResponse response)
	{
		JSONArray jsa = new JSONArray();
		try {
			double radius = 0.009;//1000m
			
			double maxX = Double.parseDouble(x)+2*radius;
			double minX = Double.parseDouble(x)-2*radius;
			double maxY = Double.parseDouble(y)+radius;
			double minY = Double.parseDouble(y)-radius;
			
			List<Circle> circleList = circleService.queryCircleListByPoint(minX, minY, maxX, maxY, 0.0, 0.0);
			jsa = JSONArray.fromObject(circleList);
			
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return jsa.toString();
	}
	
	
	/**
	 * 方法描述:查询我加入的圈子
	 * @param 
	 * @return 
	 * date:2015年2月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("getMyCircle.action")
	public String getMyCircle(HttpServletRequest request)
	{
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			if(user==null)
			{
				return "";
			}
			List<Circle> myCircle = circleService.queryMyCircleList( user.getId());
			return net.sf.json.JSONArray.fromObject(myCircle).toString();
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return "";
		}
	}
	
	
	/**
	 * 方法描述:查询首页第一次加载 初始化的农场
	 * @param 
	 * @return 
	 * date:2015年2月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("getInitMapCircle.action")
	public String getInitMapCircle(HttpServletRequest request)
	{
		try {
			List<Circle> myCircle = circleService.queryInitMapCircle();
			return net.sf.json.JSONArray.fromObject(myCircle).toString();
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return "";
		}
	}
	
	@ResponseBody
	@RequestMapping("getNextPostDate.action")
	public String getNextPostDate(int week,int amPm,HttpServletResponse response)
	{
		response.setCharacterEncoding("utf-8");
		StringBuilder sb=new StringBuilder("(下次投递：");
		week=week==7?1:(++week);
		Calendar currentCal=Calendar.getInstance();
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK,week);
		if(currentCal.getTimeInMillis()>cal.getTimeInMillis())
		{
			cal.add(Calendar.DAY_OF_YEAR, 7);
		}
		sb.append(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		sb.append((amPm==1?"上午":"下午"));
		sb.append(")");
//		return sb.toString();
		try {
			return URLEncoder.encode(sb.toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@ResponseBody
	@RequestMapping("isJoinFarm.action")
	public boolean isJoinFarm(String circleId,HttpServletRequest request,HttpServletResponse response)
	{
		boolean flag = false;
		response.setCharacterEncoding("utf-8");
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			//判断用户是否在是改农场的成员
			if(circleService.queryCircleMemberByCircleIdAndUserId(circleId, String.valueOf(userId)) > 0)
			{
				flag = true;
			}
		} catch (Exception e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag;
	}
	
	@ResponseBody
	@RequestMapping("farmIndex.action")
	public Map<String,Object> farmIndex(HttpServletRequest request)	{
		Map<String,Object> map = new HashMap<String, Object>();
		Page page = new Page();
		try {
			String circleId = request.getParameter("circleId");	// 订单号
			String pageSize = request.getParameter("pageSize");		// 每页展示条数
			String pageNo = request.getParameter("pageNo");		// 当前页
			
			if(!StringUtil.isEmpty(pageSize)){
				page.setRowsPerPage(Integer.parseInt(pageSize));
			}else{
				page.setRowsPerPage(10);
			}
			
			if(!StringUtil.isEmpty(pageNo)){
				page.setCurrentPage(Integer.parseInt(pageNo));
			}
			
			boolean isMember=false;			// 是否是农场成员
			boolean isOutDate=false;		// 发放货物时间是否过期
			boolean isCircleAdmin=false;	// 是否农场创始人
			
			/**
			 * 获取用户信息
			 */
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			if(user!=null)
			{
				isMember=circleService.queryCircleMemberByCircleIdAndUserId(circleId, user.getId()+"")>0?true:false;
			}
			
			/**
			 * 获取农场信息
			 */
			Circle circle=circleService.queryCircleByCircleId(circleId);
			request.getSession().setAttribute(ConstantBusiKeys.SessionKey.M_CIRCLE, circle);
			System.out.println("set session circle:" + circle);
			map.put("circle",circle);
			
			if(circle != null){
			//判断发放货物时间是否过期
				if(!StringUtil.isEmpty(circle.getIssueTime()))
				{
					long current=System.currentTimeMillis();
					long issueLoneTime=0;
					try {
						issueLoneTime=DateUtils.parse(circle.getIssueTime(), "yyyy-MM-dd HH:mm").getTime() ;
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(issueLoneTime-current<=0)
					{
						isOutDate=true;
					}
				}
				else
				{
					isOutDate=true;
				}	
				
				if(user!=null)
				{
					if(user.getId()==circle.getCreateUserId())
					{
						isCircleAdmin=true;
					}
				}
			}
			
			
			
			/**
			 * 查询当前批次信息
			 */
			BatchSell batchSell=batchSellService.queryCurrentBatchSell();
			map.put("batchSell",batchSell);
			
			
			List<Map<String, Object>> goodList = null;
			
			/**
			 * 查询商品信息
			 */
			if(batchSell!=null)
			{
				goodList=goodService.queryCurrentBatchGoodList(page, batchSell.getId());
			}
			
			map.put("goodList", goodList);
			
			/**
			 * 查询农场购买商品人数
			 */
			int buyNum=circleService.queryCircleBuyNum(circleId);
			
			map.put("buyNum",buyNum);
			map.put("isMember",isMember);
			map.put("isOutDate",isOutDate);
			map.put("isCircleAdmin",isCircleAdmin);
		} catch (SPTException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 查询附近的农场列表
	 * @param x
	 * @param y
	 * @return JSONArray
	 * @author zhoujie
	 */
	@ResponseBody
	@RequestMapping("queryCirclesNearBy.action")
	public JSONArray queryCirclesNearBy(Double x,Double y){
		List<Circle> list = new ArrayList<Circle>();
		System.out.println("x:" + x + ",y:" + y);
		try {
//			double radius = 0.009;//1000m
			double radius = 0.1;//1000m
			//double radius = 0.09;//10km
			
			double maxX = x + 2 * radius;
			double minX = x - 2 * radius;
			double maxY = y + radius;
			double minY = y - radius;
			
			list = circleService.queryCircleListByPoint(minX, minY, maxX, maxY, x, y);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return JSONArray.fromObject(list);
	}
	
	/**
	 * 查询我的农场列表
	 * @param request
	 * @return JSONArray
	 * @author zhoujie
	 */
	@ResponseBody
	@RequestMapping("queryMyCircles.action")
	public JSONArray queryMyCircles(HttpServletRequest request){
		List<Circle> list = new ArrayList<Circle>();
		try {
			Object obj = request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			if(obj == null)
				return JSONArray.fromObject(list);
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			list = circleService.queryCirclesByUserId(user.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return JSONArray.fromObject(list);
	}
	
}
