package com.xwtec.xwserver.util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 
 * @author linqs 短地址工具类
 * 
 */
public class ShortURLHelper {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString.trim();
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
		} catch (Exception ex) {
		}
		return resultString;
	}

	private static String[] ShortText(String string) {
		String key = "WuXianChengShi"; // 自定义生成MD5加密字符串前的混合KEY
		String[] chars = new String[] { // 要使用生成URL的字符
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		String hex = MD5Encode(key + string);
		int hexLen = hex.length();
		int subHexLen = hexLen / 8;
		String[] ShortStr = new String[4];

		for (int i = 0; i < subHexLen; i++) {
			String outChars = "";
			int j = i + 1;
			String subHex = hex.substring(i * 8, j * 8);
			long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

			for (int k = 0; k < 6; k++) {
				int index = (int) (Long.valueOf("0000003D", 16) & idx);
				outChars += chars[index];
				idx = idx >> 5;
			}
			ShortStr[i] = outChars;
		}

		return ShortStr;
	}

	/**
	 * 生成指定长度的随机码
	 * @param length
	 * @return
	 */
	private static String GenerateRandomString(int length) {
		if (length < 1)
			return "0";
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int base_length = base.length();
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			sb.append(base.charAt(new Random().nextInt(base_length)));
		}
		return sb.toString();
	}
	
	/**
	 * 根据传入的URL，生成对应的随机码
	 * @param url
	 * @return
	 */
	public static String genURLKey(String url) {
		if(url==null || url.length()<1){
			return GenerateRandomString(8);
		}
		try {
			String[] keys = ShortText(url);
			return keys[0];
		} catch (Exception e) {
			return GenerateRandomString(8);
		}
	}

	public static void main(String[] args) {
		System.out.println(genURLKey("http://app.jswxcs.cn/wapapps/mydc/index_detail.html?id=841&status=2"));
//		System.out.println(genURLKey("http://www.jswxcs.cn/11"));
//		System.out.println(genURLKey("http://www.jswxcs.cn/1/3"));
//		System.out.println(genURLKey("http://www.jswxcs.cn/1/4"));
//		System.out.println(genURLKey(null));
//		System.out.println(genURLKey(""));
	}

}
