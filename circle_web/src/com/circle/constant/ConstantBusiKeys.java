package com.circle.constant;

import java.io.File;
import java.util.Properties;

/**
 * 存放业务分类的静态key<br>
 * <p>
 * Copyright: Copyright (c) Nov 15, 2013 3:05:30 PM
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author houxu@xwtec.cn
 * @version 1.0.0
 */
public interface ConstantBusiKeys {
	/**
	 * 存储全局的properties属性文件配置信息(延迟初始化)
	 */
	public static final Properties GLOBAL_CONFIG_PROPERTIES = new Properties();

	/**
	 * 用户管理模块静态变量
	 * <p>
	 * Copyright: Copyright (c) 2013-11-18 上午10:45:45
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author chenxiang@xwtec.cn
	 * @version 1.0.0
	 */
	public interface SysUserManage {

		/**
		 * 添加用户成功
		 */
		public static final String ADD_USER_SUCCESS = "添加用户信息成功！";

		/**
		 * 添加用户失败
		 */
		public static final String ADD_USER_FAILED = "添加用户信息失败！";

		/**
		 * 重置密码成功
		 */
		public static final String RESET_PWD_SUCCESS = "重置密码成功！";

		/**
		 * 重置密码失败
		 */
		public static final String RESET_PWD_FAILED = "重置密码失败！";

		/**
		 * 删除用户成功
		 */
		public static final String DEL_USER_SUCCESS = "删除用户成功！";

		/**
		 * 删除用户失败
		 */
		public static final String DEL_USER_FAILED = "删除用户失败！";

		/**
		 * 修改用户成功
		 */
		public static final String MODIFY_USER_SUCCESS = "修改用户成功！";

		/**
		 * 修改用户失败
		 */
		public static final String MODIFY_USER_FAILED = "修改用户失败！";

		/**
		 * 手机号码已经存在
		 */
		public static final String TEL_ERR = "手机号码已经存在！";

		/**
		 * 用户名已经存在
		 */
		public static final String USERNAME_ERR = "用户名已经存在！";

	}

	/**
	 * 工具模块静态变量
	 * <p>
	 * Copyright: Copyright (c) 2013-11-25 下午01:29:53
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author liuwenbing@xwtec.cn
	 * @version 1.0.0
	 */
	public interface Tool {

		/**
		 * 判断sql不能为空
		 */
		public static final String SQL_NOT_EMPTY = "输入的sql不可以为空!";

		/**
		 * sql不合法
		 */
		public static final String SQL_NOT_LEGITIMATE = "sql不合法，仅支持查询功能!";

		/**
		 * 没有选择数据源
		 */
		public static final String NO_DATA_SOURCE = "请选择数据源!";

		/**
		 * 缺少参数
		 */
		public static final String FAILED_MSG = "缺少参数!";

		/**
		 * 解析excel上传文件的路径
		 */
		public static final String EXCEL_IMPORT_PATH = "excelimport" + File.separator;

	}

	/**
	 * Session相关. <br>
	 * 向Session中存放的信息的KEY值.
	 * <p>
	 * Copyright: Copyright (c) 2013-11-26 下午9:38:07
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author liu_tao@xwtec.cn
	 * @version 1.0.0
	 */
	public interface SessionKey {

		/**
		 * 验证码
		 */
		public static final String VERIFY_CODE = "verifyCode";

		/**
		 * 用户信息
		 */
		public static final String SYS_USER = "sysUser";

		
		/**
		 * 我加入的圈子
		 */
		public static final String MY_CIRCLE = "myCircle";
		
		/**
		 * 用户的所拥有的菜单
		 */
		public static final String SYS_MENU = "menuList";

		/**
		 * 省管理员标识
		 */
		public static final String PRO_ADMINISTRATOR = "proAdmin";

		/**
		 * 用户所拥有的权限
		 */
		public static final String SYS_OPERATION = "userOperation";

		/**
		 * 是省管理员
		 */
		public static final int IS_PRO_ADMINISTRATOR = 1;

		/**
		 * 不是省管理员
		 */
		public static final int NOT_PRO_ADMINISTRATOR = 0;

		/**
		 * 用户上一次的登录时间
		 */
		public static final String USER_LAST_LOGIN_TIME = "userLastLoginTime";

		/**
		 * 用户角色名称
		 */
		public static final String USER_ROLE_NAME = "userRoleName";

		/**
		 * add by caozhenxing 2014-01-08 用户角色是否为系统管理员
		 */
		public static final String USER_IS_SYSTEM_ADMIN = "userIsSysAdmin";

	}

	/**
	 * resultInfo.jsp相关. <br>
	 * 需要向resultInfo.jsp传值的KEY.
	 * <p>
	 * Copyright: Copyright (c) 2013-11-26 下午9:52:40
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author liu_tao@xwtec.cn
	 * @version 1.0.0
	 */
	public interface ResultInfoKey {

		/**
		 * 操作结果信息
		 */
		public static final String MSG = "msg";

		/**
		 * 操作后返回的URL
		 */
		public static final String URL = "url";

		/**
		 * 错误信息
		 */
		public static final String ERROR = "error";

		/**
		 * 结果页面相对路径
		 */
		public static final String RESULT_INFO_PATH = "/common/resultInfo.jsp";
	}

	/**
	 * 系统角色操作的变量主义. <br>
	 * <p>
	 * Copyright: Copyright (c) 2013-11-21 上午9:43:13
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author caozhenxing@mail.xwtec.cn
	 * @version 1.0.0
	 */
	public interface SysRoleKey {

		/**
		 * 系统角色无效状态
		 */
		public static final String SYS_ROLE_INVALID = "0";

		/**
		 * 系统角色e 效状态
		 */
		public static final String SYS_ROLE_VALID = "1";
	}

	/**
	 * 菜单管理常量定义 . <br>
	 * <p>
	 * Copyright: Copyright (c) 2013-11-28 下午2:16:33
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author caozhenxing@mail.xwtec.cn
	 * @version 1.0.0
	 */
	public interface MenuKey {

		/**
		 * 菜单有效状态
		 */
		public static final String MENU_VALID = "1";

		/**
		 * 菜单无效状态
		 */
		public static final String MENU_INVALID = "0";

		/**
		 * 菜单根节点ID
		 */
		public static final String MENU_ROOT_ID = "0";

		/**
		 * 菜单根节点父ID
		 */
		public static final String MENU_ROOT_PID = "0";

		/**
		 * 菜单根节点名称
		 */
		public static final String MENU_ROOT_NAME = "根目录";

		/**
		 * 显示时根节点是否展开 true默认展开
		 */
		public static final String MENU_ROOT_FLAG = "true";

		/**
		 * 菜单最末级菜单
		 */
		public static final String LAST_MENU = "3";

	}

	/**
	 * excel下载常量定义. <br>
	 * <p>
	 * Copyright: Copyright (c) 2013-11-28 下午2:16:51
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author caozhenxing@mail.xwtec.cn
	 * @version 1.0.0
	 */
	public interface PoiExcelKey {

		/**
		 * EXCEL默认名称
		 */
		public static final String POI_EXCELNAME = "excel.xls";

		/**
		 * SHEET的默认名称
		 */
		public static final String POI_SHEETNAME = "sheet0";

		/**
		 * 导出时传入ExcelUtil的数据为空时 表头默认显示的内容
		 */
		public static final String POI_DEFAULT_VALUE = "您好你下载的内容为空";
	}


	/**
	 * 数据库执行操作时返回的相关状态值定义. <br>
	 * <p>
	 * Copyright: Copyright (c) 2013-12-5 下午2:26:47
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * 
	 * @author liu_tao@xwtec.cn
	 * @version 1.0.0
	 */
	public interface DataBaseKey {

		/**
		 * 执行增、删、改操作语句时没有影响任何数据
		 */
		public static final int NONE = 0;

		/**
		 * 执行增、删、改操作语句时影响了一条数据
		 */
		public static final int ONE_CHANGED = 1;

		/**
		 * 执行批量增、删、改操作语句时对数据产生了影响(只能确定有影响，不能确定影响的条数)
		 */
		public static final int HAS_CHANGED = -2;
	}
	
	/**
	 * 
	 * 消息类型常量. <br>
	 * <p>
	 * Copyright: Copyright (c) 2015-1-23 上午10:16:01
	 * <p>
	 * Company: 欣网视讯
	 * <p>
	 * @author wangfengtong@xwtec.cn
	 * @version 1.0.0
	 */
	public interface MsgKey {

		/**
		 * 消息类型1
		 */
		public static final String MSG_TYPE_ONE = "1";

		/**
		 * 消息类型2
		 */
		public static final String MSG_TYPE_TWO = "2";

	}
	
	
	/**
	 * 配置文件key
	 * 标题、简要说明. <br>
	 * 类详细说明.
	 * <p>
	 * Copyright: Copyright (c) 2015年2月16日 下午6:58:39
	 * <p>
	 * Company: Taiyuan
	 * <p>
	 * @author Taiyuan
	 * @version 1.0.0
	 */
	public interface PropertiesKey
	{
		/**
		 * 文件上传绝对目录
		 * uploadFileBasePath 
		 */
		public static final String UPLOADFILEBASEPATH="uploadFileBasePath";
		
		/**
		 * 文件上传目录名
		 * fileUploadPath 
		 */
		public static final String FILEUPLOADPATH="fileUploadPath";
		
		/**
		 * 商品图片上传文件夹名称
		 * imageFilePath 
		 */
		public static final String IMAGEFILEPATH="imageFilePath";
		
		/**
		 * 网站域名(访问路径)
		 * domain 
		 */
		public static final String DOMAIN="domain";
		
		/**
		 * 发送邮件地址，后面要开发自己的邮箱发送，目前可以先用我们公司的
		 * callMessagePlatform 
		 */
		public static final String CALLMESSAGEPLATFORM="callMessagePlatform";
	}

}
