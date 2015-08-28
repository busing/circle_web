package com.circle.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月11日 下午5:55:01
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public class StringUtils {
	
	/**
	 * 年月日字符
	 * YMD 
	 */
	public static final String YMD=new SimpleDateFormat("yyyyMMdd").format(new Date());
	
	/**
	 * 方法描述:MD5 加密字符串
	 * @param 
	 * @return 
	 * date:2015年1月12日
	 * @author Taiyuan
	 */
	public static String md5Sum(String password)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(password.getBytes()); 
			password = new BigInteger(1, md.digest()).toString(16);   
		} catch (NoSuchAlgorithmException e) {
			password="";
		}
		return password;
	}
	
	/**
	 * 方法描述:生成订单号
	 * @param 
	 * @return 
	 * date:2015年1月12日
	 * @author Taiyuan
	 */
	public static String getOrderNo()
	{
		try {
			int uuid=UUID.randomUUID().toString().hashCode();
			if(uuid<0)
			{
				uuid=Math.abs(uuid);
			}
			StringBuffer uuidStr=new StringBuffer(uuid+"");
			if(uuidStr.length()<10)
			{
				int l=10-uuidStr.length();
				for(int i=0;i<l;i++)
				{
					uuidStr.append(0+"");
				}
			}
			if(uuidStr.length()>10)
			{
				uuidStr.delete(18, uuidStr.length());
			}
			uuidStr.insert(0, YMD);
			return uuidStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	

	public static void main(String[] args) {
		for(int i=0;i<100;i++)
		{
			System.out.println(getOrderNo());
		}
	}
}
