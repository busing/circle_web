
package com.circle.controller.shopcart;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.circle.pojo.circle.Circle;
import com.circle.pojo.shopcart.ShopCart;
import com.circle.pojo.user.User;
import com.circle.service.circle.ICircleService;
import com.circle.service.good.IGoodService;
import com.circle.service.shopcart.IShopCartService;
import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.pojo.Page;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月5日 下午9:04:03
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Controller
@RequestMapping("shopcart")
public class ShopCartController {

	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(ShopCartController.class);
	
	/**
	 * 注入购物车service
	 */
	@Resource
	IShopCartService shopCartService;
	
	/**
	 * 注入圈子（农场）service
	 */
	@Resource
	ICircleService circleService;
	
	@Resource
	IGoodService goodService;
	
	/**
	 * 方法描述:添加购物车
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("addShopCart.action")
	public String addShopCart(ShopCart shopCart,HttpServletRequest request)
	{
		JSONObject jsonObj=new JSONObject();
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			boolean flag=false;
			String msg="";
			int count=0;
			//判断用户是否在是改农场的成员
			if(circleService.queryCircleMemberByCircleIdAndUserId(shopCart.getCircleId()+"", userId+"")>0)
			{
				shopCart.setUserId(userId);
				flag=shopCartService.addShopCart(shopCart);
				count=shopCartService.countShopCart(userId);
			}
			else 
			{
				msg="您不是该农场成员，请先加入该农场";
			}
			jsonObj.put("count", count);
			jsonObj.put("flag", flag);
			jsonObj.put("msg", msg);
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		
		return jsonObj.toString();
	}
	
	
	/**
	 * 方法描述:查询购物车商品数量
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("getShopCartCount.action")
	public String getShopCartCount(HttpServletRequest request)
	{
		JSONObject jsonObj=new JSONObject();
		int count=0;
		try
		{
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			count = shopCartService.countShopCart(userId);
		}
		catch (SPTException e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		jsonObj.put("count", count);
		return jsonObj.toString();
	}
	
	
	/**
	 * 方法描述:添加购物车
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("updateCartNum.action")
	public String updateCartNum(ShopCart shopCart,HttpServletRequest request)
	{
		JSONObject jsonObj=new JSONObject();
		try {
			User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			int userId=user.getId();
			shopCart.setUserId(userId);
			boolean flag=shopCartService.updateGoodNum(shopCart);
			double singleTotal=shopCartService.calTotal(new String[]{shopCart.getCartId()+""});
			jsonObj.put("flag", flag);
			jsonObj.put("singleTotal",singleTotal);
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return jsonObj.toString();
	}
	
	
	
	
	
	/**
	 * 方法描述:删除购物车商品
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("deleteShopCart.action")
	public boolean deleteShopCart(int cartId)
	{
		boolean flag=false;
		try {
			flag = shopCartService.deleteShopCart(cartId);
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return flag;
	}
	
	
	
	/**
	 * 方法描述:计算所选购物车商品总价
	 * @param 
	 * @return 
	 * date:2015年1月8日
	 * @author Taiyuan
	 */
	@ResponseBody
	@RequestMapping("calTotal.action")
	public double calTotal(String cartId)
	{
		double b=0;
		try {
			String[] cartArr=cartId.split(",");
			b=shopCartService.calTotal(cartArr);
		} catch (SPTException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return b;
	}
	
	@ResponseBody
	@RequestMapping("maddShopCart.action")
	public List<Map<String, Object>> mAddShopCart(HttpServletRequest request)
	{
		//JSONArray array = new JSONArray();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		String goodIds = request.getParameter("goodIds");
		String goodNums = request.getParameter("goodNums");
		
		String[] ids = goodIds.split(",");
		String[] nums = goodNums.split(",");
		
		for (int i = 0;i < nums.length;i++) {
			if(Integer.parseInt(nums[i]) > 0){
				try {
					Map<String, Object> queryGood = goodService.queryGood(ids[i]);
					queryGood.put("buy_num", nums[i]);
					list.add(queryGood);
				} catch (SPTException e) {
					logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
				}
			}
		}
		request.getSession().setAttribute(ConstantBusiKeys.SessionKey.M_SHOPCART, list);
		return list;
	}
	
	@ResponseBody
	@RequestMapping("mshopcart.action")
	public Map<String, Object> mShopCart(ModelAndView mav,Page page,HttpServletRequest request)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = (List<Map<String, Object>>) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.M_SHOPCART);
		Circle circle = (Circle) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.M_CIRCLE);
		User user = (User) request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
		
		map.put("user", user);
		map.put("list", list);
		map.put("circle", circle);
		return map;
	}
}
