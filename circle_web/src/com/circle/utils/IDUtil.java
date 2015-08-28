package com.circle.utils;

import java.util.UUID;

/**
 * 获取uuid当做唯一id
 * <p>
 * Copyright: Copyright (c) 2015-3-31 上午09:08:17
 * <p>
 * Company: 石头剪刀布
 * <p>
 * @author liuwenbing  405497431@qq.com
 * @version 1.0.0
 */
public class IDUtil {
	
	/**
	 * 方法描述:获取uuid
	 * @return 获取32位的uuid 
	 * date:2015-3-31
	 * add by: liuwenbing  405497431@qq.com
	 */
	public static String getID(){
		//获取uuid
		String id = UUID.randomUUID().toString();
		id = id.replace("-","");
		return id;
	}
}
