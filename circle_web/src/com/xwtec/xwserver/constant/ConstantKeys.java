package com.xwtec.xwserver.constant;


/**
 * 存放工程和框架级别的静态变量<br>
 * <p>
 * Copyright: Copyright (c) Nov 15, 2013 3:04:54 PM
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author houxu@xwtec.cn
 * @version 1.0.0
 */
public interface ConstantKeys {

	
	/**
	 * 框架静态参数说明<br>
	 * <p>
	 * Copyright: Copyright (c) Nov 15, 2013 3:07:21 PM
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * @author houxu@xwtec.cn
	 * @version 1.0.0
	 */
	public interface WebKey {

		public static final String CONTENT_TYPE = "application/json; charset=UTF-8";// ContentType类型1
		
		public static final String CONTENT_TYPE_HTML = "text/html;charset=UTF-8";// ContentType类型：text/html
		
		public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";// 默认编码格式

		public static final String CHARACTER_ENCODING_GBK = "GBK";// 默认编码格式
		
		public static final String CHARACTER_ENCODING_ISO_8859_1 = "ISO-8859-1";// 默认编码格式

		public static final String PARAM_KEY = "jsonStr";// 获取请求参数key

		public static final int BUFFER_SIZE = 1024;// 缓冲区大小

		public static final long CACHE_TIME = 1000 * 60 * 60 * 6;// 默认缓存时间6小时

		public static final String FAIL = "1";// 接口处理失败状态码

		public static final String SUCCESS = "0";// 接口处理成功状态码

		public static final String FAIL_MSG = "Interface call failed!";// 接口处理失败消息

		public static final String SUCCESS_MSG = "Interface call success!";// 接口处理成功消息
		
		public static final String NETWORK_BUSY = "The network is busy!";// 网络繁忙

		public static final int FAIL_NUMBER = 1;// 失败状态码

		public static final int SUCCESS_NUMBER = 0;// 成功状态码
		
		/** 
		 * add by houxu at 2013-09-16 18:51 
		 * to defined status code
		 */
		/**
		 * STATUS_200 200—调用成功
		 */
		public static final String STATUS_200 = "200";
		/**
		 * STATUS_403 403---无权限调用
		 */
		public static final String STATUS_403 = "403";
		/**
		 * STATUS_404 404---接口不存在
		 */
		public static final String STATUS_404 = "404";
		/**
		 * STATUS_408 408---请求服务系统接口超时
		 */
		public static final String STATUS_408 = "408";
		/**
		 * STATUS_500 500---请求服务系统接口出现错误
		 */
		public static final String STATUS_500 = "500";
		/**
		 * STATUS_996 996---超出流量控制
		 */
		public static final String STATUS_996 = "996";
		/**
		 * STATUS_997 997---未经授权IP
		 */
		public static final String STATUS_997 = "997";
		/**
		 * STATUS_998 998---签名错误
		 */
		public static final String STATUS_998 = "998";
		/**
		 * STATUS_999 999---发生其它未知异常导致失败
		 */
		public static final String STATUS_999 = "999";
	}
	
	
	/**
	 * redis工具类的变量定义<br>
	 * <p>
	 * Copyright: Copyright (c) Nov 18, 2013 6:11:08 PM
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * @author houxu@xwtec.cn
	 * @version 1.0.0
	 */
	public interface RedisUtilKey {
		
		/**
		 * WAIT_TIME 获取不到redis资源,等待500ms 
		 */
		public static final int WAIT_TIME = 500;
		
		/**
		 * CYCLE_TIMES 连续获取3次失败,则不再获取
		 */
		public static final int CYCLE_TIMES = 3;
		
	}
	
	/**
	 * 数据库变量
	 * <p>
	 * Copyright: Copyright (c) 2013-11-26 下午10:27:31
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * @author liuwenbing@xwtec.cn
	 * @version 1.0.0
	 */
	public interface DataBase{
		
		/**
		 * 数据批量插入返回-2为成功 
		 */
		public static final int BATCH_INSERT_DATA_SUCCESS_FLAG = -2;
		
		
	}
	

	/**
	 * 校验表单
	 * <p>
	 * Copyright: Copyright (c) 2013-11-26 下午10:27:31
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * @author houxu@xwtec.cn
	 * @version 1.0.0
	 */
	public interface Validate{
		
		/**
		 * 表单校验 ajax返回值 成功
		 */
		public static final String VALIDATE_AJAX_SUCCESS = "y";
		
		/**
		 * 表单校验 ajax返回值 失败
		 */
		public static final String VALIDATE_AJAX_ERROR = "n";
		
		
	}
	
	/**
	 * 从request中取工程根目录时的KEY值
	 */
	public static final String BASE_PATH_KEY = "basePath";
	
}
