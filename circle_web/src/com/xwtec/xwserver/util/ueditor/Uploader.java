package com.xwtec.xwserver.util.ueditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.xwtec.xwserver.util.CommonUtil;
import com.xwtec.xwserver.util.ProUtil;

/**
 * UEditor文件上传辅助类. <br>
 * <p>
 * Copyright: Copyright (c) 2014-1-6 下午2:04:19
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class Uploader {
	
	// 文件大小常量, 单位kb
	private static final int MAX_SIZE = 10000;
	// 输出文件地址
	private String url = "";
	// 上传文件名
	private String fileName = "";
	// 状态
	private String state = "";
	// 文件类型
	private String type = "";
	// 原始文件名
	private String originalName = "";
	// 文件大小
	private String size = "";
	// http请求对象
	private HttpServletRequest request = null;
	// 图片标题
	private String title = "";
	// 保存路径
	private String savePath = "upload";
	// 文件允许格式
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf",".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	// 文件大小限制，单位Byte
	private long maxSize = 0;
	// 错误信息
	private HashMap<String, String> errorInfo = new HashMap<String, String>();
	// 参数
	private Map<String, String> params = null;
	// 上传的文件数据
	private byte[] fileBytes = null;
	// 编码
	public static final String ENCODE = "utf-8";
	private final static Logger log = Logger.getLogger(Uploader.class);
	
	/**
	 * 方法描述:带参构造方法。
	 * @param request http请求对象
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	public Uploader(HttpServletRequest request) {
		this.request = request;
		this.params = new HashMap<String, String>();
		this.setMaxSize(Uploader.MAX_SIZE);
		this.parseParams();
		HashMap<String, String> tmp = this.errorInfo;
		// 默认成功
		tmp.put("SUCCESS", "SUCCESS");
		// 未包含文件上传域
		tmp.put("NOFILE","未包含文件上传域");
		// 不允许的文件格式
		tmp.put("TYPE","不允许的文件格式");
		// 文件大小超出限制
		tmp.put("SIZE","文件大小超出限制");
		// 请求类型错误
		tmp.put("ENTYPE", "请求类型ENTYPE错误");
		// 上传请求异常
		tmp.put("REQUEST", "上传请求异常");
		// 未找到上传文件
		tmp.put("FILE", "未找到上传文件");
		// IO异常
		tmp.put("IO", "IO异常");
		// 目录创建失败
		tmp.put("DIR", "目录创建失败");
		// 未知错误
		tmp.put("UNKNOWN", "未知错误");
	}
	
	/**
	 * 方法描述:文件上传
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	public void upload() throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(this.request);
		if (!isMultipart) {
			this.state = this.errorInfo.get("NOFILE");
			return;
		}
		if (this.fileBytes == null) {
			this.state = this.errorInfo.get("FILE");
			return;
		}
		// 存储title
		this.title = this.getParameter("pictitle");
		try {
			String savePath = this.getFolder(this.savePath);
			if (!this.checkFileType(this.originalName)) {
				this.state = this.errorInfo.get("TYPE");
				return;
			}
			if (this.fileBytes.length > this.maxSize) {
				this.state = this.errorInfo.get("SIZE");
				return;
			}
			this.fileName = this.getName(this.originalName);
			this.type = this.getFileExt(this.fileName);
			this.url = savePath + "/" + this.fileName;
			FileOutputStream fos = new FileOutputStream(this.getPhysicalPath(this.url));
			fos.write(this.fileBytes);
			fos.close();
			this.state = this.errorInfo.get("SUCCESS");
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			this.state = this.errorInfo.get("IO");
		}
	}

	/**
	 * 方法描述:接受并保存以base64格式上传的文件
	 * @param fieldName 文件上传时参数名
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	public void uploadBase64(String fieldName) {
		String savePath = this.getFolder(this.savePath);
		String base64Data = this.request.getParameter(fieldName);
		this.fileName = this.getName("test.png");
		this.url = savePath + "/" + this.fileName;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			File outFile = new File(this.getPhysicalPath(this.url));
			OutputStream ro = new FileOutputStream(outFile);
			byte[] b = decoder.decodeBuffer(base64Data);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			ro.write(b);
			ro.flush();
			ro.close();
			this.state = this.errorInfo.get("SUCCESS");
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			this.state = this.errorInfo.get("IO");
		}
	}
	
	/**
	 * 方法描述:根据参数名从params中取参数值
	 * @param name 参数名
	 * @return 参数值
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	public String getParameter(String name) {
		return this.params.get(name);
	}

	/**
	 * 方法描述:文件类型判断
	 * @param fileName 文件名
	 * @return 如果文件类型是允许上传的文件类型，返回true，否则返回false
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 方法描述:获取文件扩展名
	 * @param fileName 文件名
	 * @return 文件的扩展名
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * 方法描述:将request中的参数存储到this.params中
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private void parseParams() {
		DiskFileItemFactory dff = new DiskFileItemFactory();
		try {
			ServletFileUpload sfu = new ServletFileUpload(dff);
			sfu.setSizeMax(this.maxSize);
			sfu.setHeaderEncoding("utf-8");
			FileItemIterator fii = sfu.getItemIterator(this.request);
			while (fii.hasNext()) {
				FileItemStream item = fii.next();
				// 普通参数存储
				if (item.isFormField()) {
					this.params.put(item.getFieldName(),this.getParameterValue(item.openStream()));
				} else {
					// 只保留一个
					if (this.fileBytes == null) {
						this.fileBytes = this.getFileOutputStream(item.openStream());
						this.originalName = item.getName();
					}
				}
			}
		} catch (SizeLimitExceededException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			this.state = this.errorInfo.get("SIZE");
		} catch (InvalidContentTypeException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			this.state = this.errorInfo.get("ENTYPE");
		} catch (FileUploadException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			this.state = this.errorInfo.get("REQUEST");
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			this.state = this.errorInfo.get("UNKNOWN");
		}
	}

	/**
	 * 方法描述:依据原始文件名生成新文件名
	 * @param 原始文件名
	 * @return 生成的新文件名
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private String getName(String fileName) {
		Random random = new Random();
		return this.fileName = "" + random.nextInt(10000) + System.currentTimeMillis() + this.getFileExt(fileName);
	}

	/**
	 * 方法描述:根据字符串创建本地目录 并按照日期建立子目录返回
	 * @param path 指定的相对根路径(专门存放UE上传的文件或图片的工程目录下的相对根路径)
	 * @return 在相对根路径后拼接生成按日期建立的子目录一并返回
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private String getFolder(String path) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		path += "/" + formater.format(new Date());
		File dir = new File(this.getPhysicalPath(path));
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
				this.state = this.errorInfo.get("DIR");
				return "";
			}
		}
		return path;
	}

	/**
	 * 方法描述:根据传入的虚拟路径获取物理路径
	 * @param path 虚拟路径
	 * @return 物理路径
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private String getPhysicalPath(String path) {
		return ProUtil.get("uploadFileBasePath") + File.separator + path;
	}

	/**
	 * 方法描述:从输入流中获取字符串数据
	 * @param in 给定的输入流， 结果字符串将从该流中读取
	 * @return 从流中读取到的字符串
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private String getParameterValue(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String result = "";
		String tmpString = null;
		try {
			while ((tmpString = reader.readLine()) != null) {
				result += tmpString;
			}
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return result;
	}
	
	/**
	 * 方法描述:将输入流以byte数组形式接受并返回
	 * @param 输入流
	 * @return 流的byte数组形式
	 * date:2014-1-6
	 * add by: liu_tao@xwtec.cn
	 */
	private byte[] getFileOutputStream(InputStream in) {
		try {
			return IOUtils.toByteArray(in);
		} catch (IOException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			return null;
		}
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setAllowFiles(String[] allowFiles) {
		this.allowFiles = allowFiles;
	}

	public void setMaxSize(long size) {
		this.maxSize = size * 1024;
	}

	public String getSize() {
		return this.size;
	}

	public String getUrl() {
		return this.url;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getOriginalName() {
		return this.originalName;
	}
}