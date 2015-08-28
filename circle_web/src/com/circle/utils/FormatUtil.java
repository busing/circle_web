package com.circle.utils;

public class FormatUtil {
	public static Boolean isEmpty(Object obj){
		return obj == null || obj.equals("");
	}
	
	public static String parseObject2String(Object obj){
		return isEmpty(obj) ? "" : (String)obj;
	}
	
	public static Double parseObject2Double(Object obj){
		try {
			return Double.parseDouble(parseObject2String(obj));
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}
}