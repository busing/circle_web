package com.circle.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class MessageUtil 
{
	/**
	 * 服务http地址
	 */
	private static String BASE_URI = "http://yunpian.com";
	/**
	 * 服务版本号
	 */
	private static String VERSION = "v1";
	/**
	 * 编码格式
	 */
	private static String ENCODING = "UTF-8";
	/**
	 * 查账户信息的http地址
	 */
	private static String URI_GET_USER_INFO = BASE_URI + "/" + VERSION + "/user/get.json";
	/**
	 * 通用发送接口的http地址
	 */
	private static String URI_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/send.json";
	
	/**
	 * 模板发送接口的http地址
	 */
	private static String URI_TPL_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/tpl_send.json";
	
	
	private final static String APIKEY = "d4540af47e83ee7ed220a41fe3eef2f3";
	
	private final static String  COMPANY="E农场";
	
	private final static int UPDATE_PASS_TPL_ID=707773;
	/**
	 * 注册信息
	 */
	private final static int REGISTER_TPL_ID=672263;
	
	/**
	 * 下单成功通知
	 */
	private final static int ORDERNOTICE_TPL_ID=2;
	
	/**
	 * 修改手机验证消息
	 */
	private final static int CHANGEMOBILE_TPL_ID=672289;
	
	/**
	 * 农场审核通过通知
	 */
	private final static int AUDITFARM_YES_TPL_ID=4;
	
	/**
	 * 农场审核不通过通知
	 */
	private final static int AUDITFARM_NO_TPL_ID=5;
	
	/**
	 * 农场发放货物时间地点审核不通过通知
	 */
	private final static int AUDITADDRESS_NO_TPL_ID=6;
	
	/**
	 * 农场发放货物时间地点审核通过通知
	 */
	private final static int AUDITADDRESS_YES_TPL_ID=7;
	
	public final static Logger logger=Logger.getLogger(MessageUtil.class);
	
	
	/**
	 * 通过模板发送短信
	 * @param apikey apikey
	 * @param tpl_id　模板id
	 * @param tpl_value　模板变量值　
	 * @param mobile　接受的手机号
	 * @return json格式字符串
	 * @throws IOException 
	 */
	public static boolean tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException{
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apikey", APIKEY));
		params.add(new BasicNameValuePair("tpl_id", String.valueOf(tpl_id)));
		params.add(new BasicNameValuePair("tpl_value", tpl_value));
		params.add(new BasicNameValuePair("mobile", mobile));
		
		HttpPost httpPost = new HttpPost(URI_TPL_SEND_SMS);
		UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params, "utf-8");
		httpPost.setEntity(entity);
		HttpResponse loginResponse= httpclient.execute(httpPost);
		HttpEntity responseEntity=loginResponse.getEntity();
		String result=EntityUtils.toString(responseEntity);
		JSONObject jsonObject=JSONObject.fromObject(result);
		logger.info("send message info:"+jsonObject.get("msg"));
		if("OK".equals(jsonObject.get("msg")))
		{
			return true;
		}
		else
		{
			logger.error("send message faild:"+jsonObject.get("msg"));
		}
		
		return false;
	}
	
	/**
	 * 发送注册验证码短信
	 * @param mobile	手机号码
	 * @param code	验证码
	 * @return
	 */
	public static Boolean sendRegisterCode(String mobile,String code)
	{
		try {
			String tpl_value="#code#="+code;
			return  tplSendSms(REGISTER_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 发送修改密码短信
	 * @param mobile	手机号码
	 * @param code	验证码
	 * @return
	 */
	public static Boolean sendUpdatePassCode(String mobile,String code)
	{
		try {
			String tpl_value="#code#="+code;
			return  tplSendSms(UPDATE_PASS_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 发送下单成功通知
	 * @param mobile	手机号码
	 * @param code	验证码
	 * @return
	 */
	public static Boolean sendOrderNotice(String mobile,String order_no)
	{
		try {
			String tpl_value="#order_no#="+order_no;;
			return  tplSendSms(ORDERNOTICE_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 发送更换手机验证码
	 * @param mobile	手机号码
	 * @param code	验证码
	 * @return
	 */
	public static Boolean sendChangeMobile(String mobile,String code)
	{
		try {
			String tpl_value="#code#="+code;;
			return  tplSendSms(CHANGEMOBILE_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * 审核农场注册成功
	 * @param mobile
	 * @param farm_name
	 * @return
	 */
	public static Boolean sendAuditFarmYes(String mobile,String farm_name)
	{
		try {
			String tpl_value="#farm_name#="+farm_name;;
			return  tplSendSms(AUDITFARM_YES_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * 审核农场注册不通过通知
	 * @param mobile
	 * @param farm_name
	 * @return
	 */
	public static Boolean sendAuditFarmNo(String mobile,String farm_name)
	{
		try {
			String tpl_value="#farm_name#="+farm_name;;
			return  tplSendSms(AUDITFARM_NO_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * 审核发放货物时间和地点通过短信通知
	 * @param mobile
	 * @param time
	 * @param address
	 * @return
	 */
	public static Boolean sendAuditAddessYes(String mobile,String time, String address)
	{
		try {
			String tpl_value="#time#="+time+"#address#="+address;
			return  tplSendSms(AUDITADDRESS_YES_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * 审核发放货物时间和地点不通过短信通知
	 * @param mobile
	 * @param time
	 * @param address
	 * @return
	 */
	public static Boolean sendAuditAddessNo(String mobile,String time, String address)
	{
		try {
			String tpl_value="#time#="+time+"#address#="+address;;
			return  tplSendSms(AUDITADDRESS_NO_TPL_ID, tpl_value, mobile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		//修改为您要发送的手机号
		String mobile = "13815412201";

		/**************** 使用模板接口发短信 *****************/
		//设置模板ID，如使用1号模板:您的验证码是#code#【#company#】
		long tpl_id=2;
		//设置对应的模板变量值
		String tpl_value="1234";
		//模板发送的调用示例
		System.out.println(MessageUtil.sendRegisterCode(mobile, tpl_value));
	}
}
