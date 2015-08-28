package com.circle.controller.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.circle.constant.ConstantBusiKeys;
import com.circle.pojo.order.Order;
import com.circle.service.order.IOrderService;
import com.circle.service.order.impl.OrderServiceImpl;
import com.circle.utils.NumUtils;
import com.circle.utils.SystemExceptionUtil;
import com.sun.rmi.rmid.ExecPermission;
import com.xwtec.xwserver.constant.ConstantBusiKeys.ResultInfoKey;
import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.ProUtil;
import com.xwtec.xwserver.util.StringUtil;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年3月13日 下午9:48:15
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
@Controller
@RequestMapping("aliPay")
public class AliPayController {
	
	public static final Logger logger=Logger.getLogger(AliPayController.class);
	
	@Resource
	private IOrderService orderService;	
	
	@RequestMapping("payOrder")
	@ResponseBody
	public String payOrder(HttpServletRequest request, HttpServletResponse response,String orderIds)
	{
		String sHtmlText="";
		try {
			double total=0;
			StringBuffer orderNo=new StringBuffer();
			String[] orderArr;
			if(!StringUtil.isEmpty(orderIds))
			{
				orderArr=orderIds.split(",");
				Order order;
				for (String oid : orderArr)
				{
					order=orderService.queryOrderByOrderNo(oid);
					total+=order.getOrderAmount();
					orderNo.append(order.getOrderNo()).append(",");
				}
				total=Double.parseDouble(NumUtils.subZeroAndDot(total+""));
				if(orderNo.length()>0)
				{
					orderNo.deleteCharAt(orderNo.length()-1);
				}
				
				//支付类型
				String payment_type = "1";
				//必填，不能修改
				//服务器异步通知页面路径
				
				//页面跳转同步通知页面路径
				String return_url = ProUtil.get(ConstantBusiKeys.PropertiesKey.DOMAIN)+"/aliPay/aliPayReturn.action";
				System.out.println("页面跳转同步通知页面路径:"+return_url);
				
				String notify_url =ProUtil.get(ConstantBusiKeys.PropertiesKey.DOMAIN)+"/aliPay/aliPayNotify.action" ;
				//需http://格式的完整路径，不能加?id=123这类自定义参数

				
				
				String myOrderUrl = ProUtil.get(ConstantBusiKeys.PropertiesKey.DOMAIN)+"/usercenter/orderList.action";
				//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

				//卖家支付宝帐户
				String seller_email = new String(AlipayConfig.seller_email.getBytes("ISO-8859-1"),"UTF-8");
				//必填

				//商户订单号
//				String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
				String out_trade_no = new String(orderNo.toString().getBytes("ISO-8859-1"),"UTF-8");
				//商户网站订单系统中唯一订单号，必填

				//订单名称
				String subject = new String(orderNo.toString().getBytes("ISO-8859-1"),"UTF-8");
				//必填

				//付款金额
				String total_fee = total+"";
				//必填

				//订单描述

				String body = new String("团农订单".getBytes("ISO-8859-1"),"UTF-8");
				
				//商品展示地址
				String show_url = myOrderUrl;
				//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

				//防钓鱼时间戳
				String anti_phishing_key = "";
				//若要使用请调用类文件submit中的query_timestamp函数

				//////////////////////////////////////////////////////////////////////////////////
				
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "create_direct_pay_by_user");
				sParaTemp.put("partner", AlipayConfig.partner);
				sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("payment_type", payment_type);
				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("return_url", return_url);
				sParaTemp.put("seller_email", seller_email);
				sParaTemp.put("out_trade_no", out_trade_no);
				sParaTemp.put("subject", subject);
				sParaTemp.put("total_fee", total_fee);
				sParaTemp.put("body", body);
				sParaTemp.put("show_url", show_url);
				sParaTemp.put("anti_phishing_key", anti_phishing_key);
				sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
				
				//建立请求
				sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
//				response.getWriter().write(sHtmlText);
			}
			
		} catch (Exception e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} 
		logger.info(sHtmlText);
		return sHtmlText;
	}
	
	
	/**
	 * 支付宝支付同步回到url
	 * @return
	 */
	@RequestMapping("aliPayReturn")
	public ModelAndView aliPayReturn(HttpServletRequest request, HttpServletResponse response,ModelAndView mav)
	{
		boolean callBackFlag=alipayCallBack(request);
		logger.info("alipay 同步回调通知");
		if(callBackFlag)
		{
			mav.addObject("verifyMessage", "恭喜,你的订单已经成功支付");
		}
		else
		{
			mav.addObject("verifyMessage", "抱歉,您的订单支付失败，请重新再试");
		}
		//该页面可做页面美工编辑
		mav.setViewName("/pay/notify_url.jsp");
		return mav;
	}
	
	
	/**
	 * 支付宝支付异步回到url
	 * @return
	 */
	@ResponseBody
	@RequestMapping("aliPayNotify")
	public String aliPayNotify(HttpServletRequest request, HttpServletResponse response,ModelAndView mav)
	{
		boolean callBackFlag=alipayCallBack(request);
		logger.info("alipay 异步回调通知");
		if(callBackFlag)
		{
			return "success";
		}
		else
		{
			return "faild";
		}
	}


	private boolean alipayCallBack(HttpServletRequest request) {
		try
		{
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
			
			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
			

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			
			if(verify_result)
			{//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))
				{
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					String[] order_no_arr=out_trade_no.split(",");
					
					for (String order_no : order_no_arr) 
					{
						orderService.setOrderPayStatus(1, order_no,trade_no);
						System.out.println("回调处理订单："+order_no);
					}

				}
				
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch (Exception e)
		{
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return false;
			
		}
	}
}
