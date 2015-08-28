package com.xwtec.xwserver.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.codehaus.jackson.map.ObjectMapper;

import com.xwtec.xwserver.util.mail.MailMsg;

/**
 * 日志文件切割 <br>
 * 按照不同的应用和日期对应用进行切割,便于检查单个应用出现的故障<br>
 * <p>
 * Copyright: Copyright (c) Aug 18, 2013 11:11:57 PM
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author houxu@xwtec.cn
 * @version 1.0.0
 */
public class DatedFileAppender extends RollingFileAppender {

	private final static Logger log = Logger.getLogger(DatedFileAppender.class);

	private long nextCheck = 0L;

	private String originFilename = null;
	
	String curPath = "";
	
	/**
	 * 方法描述:重写父类setFile方法
	 * @param 文件名称
	 * date:2013-11-28
	 * add by: liu_tao@xwtec.cn
	 */
	public void setFile(String file) {
		this.originFilename = file;
		super.setFile(getDatedFilename());
	}
	
	/**
	 * 方法描述:获取按时间分割的文件名
	 * @return 按时间分割的文件名
	 * date:2013-11-28
	 * add by: liu_tao@xwtec.cn
	 */
	private String getDatedFilename() {
		//判断文件大小是否在设定的范围内，如果是，则直接追加log，否则新增一个文件
		if(!curPath.equals("")){
			File file = new File(curPath);
			long size = file.length();
			
			//如果文件存在，10485760
	        if (file.exists() && size < maxFileSize) {
	            return curPath;
	        }
		}
		String str = this.originFilename;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time14 = format.format(new Date());
		str = str.replaceAll("%yyyy", time14.substring(0, 4));
		str = str.replaceAll("%yy", time14.substring(0, 4));
		str = str.replaceAll("%mm", time14.substring(4, 6));
		str = str.replaceAll("%dd", time14.substring(6, 8));
		str = str.replaceAll("%hh", time14.substring(8, 10));
		//以当前时间产生日志文件
		format = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		str = str.replace("{yyyy-MM-dd hh:mm:ss}", format.format(new Date()));
		try {
			//日志存放路径 WEB-INF/classes/log/
			//String path = this.getClass().getClassLoader().getResource("/").getPath();
			//File directory = new File(path);
			//curPath = directory.getCanonicalPath();
			// 如果文件名设置为linux系统下的绝对路径，则将文件名做为curPath
			// 否则用WEB-INF/classes/log/+文件名做为curPath
			/*if(str.startsWith("file://")) {
				curPath = str;
			} else {
				curPath += "/" + str;
			}*/
			curPath = str;
			newFolder(curPath);
		} catch (Exception e) {
			log.error("[DatedFileAppender.getDatedFilename]:failed. throw e:" + e.getMessage());
		}
		return curPath;
	}
	
	/**
	 * 方法描述:调用按时间分割的文件名方法，获取文件名，如果与this.fileName相同不处理
	 *       不相同调用父类的setFile方法
	 * date:2013-11-28
	 * add by: liu_tao@xwtec.cn
	 */
	void switchFilename() throws IOException {
		String datedFilename = getDatedFilename();
		if (datedFilename.equals(this.fileName)) {
			return;
		}
		try {
			setFile(datedFilename, getAppend(), getBufferedIO(), getBufferSize());
		} catch (IOException e) {
			log.error("[DatedFileAppender.switchFilename]:failed. throw e:" + e.getMessage());
		}
		this.fileName = datedFilename;
	}
	
	/**
	 * 方法描述:重写父类追加日志方法，当有日志发生时，发送邮件通知相关人员
	 * @param event 日志发生触发事件
	 * date:2013-12-6
	 * add by: liu_tao@xwtec.cn
	 */
	protected void subAppend(LoggingEvent event) {
		if(Level.ERROR.equals(event.getLevel()) && "1".equals(ProUtil.get("sendErrorEmailFlag"))) {
			WxcsHttpClient httpClient = new WxcsHttpClient();
			Map<String,Object> map = new HashMap<String, Object>();
			MailMsg mailMsg = new MailMsg();
			map.put("body", mailMsg);
		    mailMsg.setToAddress(new String[]{"fanzhaode@xwtec.cn","wangfengtong@xwtec.cn"});
			mailMsg.setSubject("[江苏无线城市运营管理分析平台]出现异常");
			StringBuilder message = new StringBuilder();
			message.append("来自" + MDC.get("IP") + "的异常,异常信息如下:\n");
			message.append(event.getMessage().toString() + "\n");
			message.append("-----------------------------------\n");
			message.append("江苏无线城市运营服务支撑\n");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			message.append("tel: 025-84669869\n");
			message.append(sdf.format(new Date()));
			mailMsg.setContent(message.toString());
			mailMsg.setSourceName("运营管理分析平台");
			ObjectMapper mapper = new ObjectMapper();
			try {
				httpClient.sendRequestByPost(ProUtil.get("callMessagePlatform") + "SendMail", mapper.writeValueAsString(map));
			} catch (Exception e) {
				log.warn("[DatedFileAppender.subAppend]:failed. throw e:" + e.getMessage());
			}
		}
		long n = System.currentTimeMillis();
		if (n >= this.nextCheck) {
			this.nextCheck = (n + 30000L);
			try {
				switchFilename();
			} catch (IOException e) {
				log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			}
		}
		super.subAppend(event);
	}
	
	/**
	 * 方法描述:按指定路径创建文件夹
	 * @param 指定的路径
	 * date:2013-12-6
	 * add by: liu_tao@xwtec.cn
	 */
	private void newFolder(String folderPath) {
		String filePath = folderPath.toString();
		File myFilePath = new File(filePath);
		try {
			if (!myFilePath.isDirectory()) {
				myFilePath.getParentFile().mkdirs();
			}
		} catch (Exception e) {
			log.error("[DatedFileAppender.newFolder]:failed. throw e:" + e.getMessage());
		}
	}
}
