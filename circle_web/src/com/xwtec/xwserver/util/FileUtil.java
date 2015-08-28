package com.xwtec.xwserver.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作相关. <br>
 * 提供包括文件上传、下载等功能.
 * <p>
 * Copyright: Copyright (c) 2013-11-15 上午11:01:54
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class FileUtil {	
	
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	/**
	 * 方法描述:文件上传，将controller接到的MultipartFile类型文件保存到config.properties文件中KEY值为“uploadFileBasePath”对应的绝对路径下
	 *       如果指定了相对路径，则存放在以绝对路径为根目录的相对路径下，文件名为：(时间戳+3位随机数)+原文件后缀名
	 * @param multipartFile controller接到的Spring封装后的文件
	 * @param relativePath 以“uploadFileBasePath”对应的路径为根目录的相对路径，指定本字段后，文件将保存在指定的相对路径下，如果为null或空字符串，依然保存在根目录中。
	 * @return 生成后的文件名
	 * date:2013-11-15
	 * add by: liu_tao@xwtec.cn
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static String upload(MultipartFile multipartFile,String relativePath) throws IllegalStateException, IOException {
		// 文件存放的根目录的绝对路径
		StringBuilder path = new StringBuilder(ProUtil.get("uploadFileBasePath"));
		if(!CommonUtil.isEmpty(relativePath)) {
			path.append(File.separator + relativePath);
		}
		path.append(File.separator);
		// 上传时的原文件名
		String oleFileName = multipartFile.getOriginalFilename();
		// 上传时的原文件名后缀
		String fileNameSuffix = "";
		if(!CommonUtil.isEmpty(oleFileName) && oleFileName.indexOf(".") >= 0) {
			fileNameSuffix = oleFileName.substring(oleFileName.lastIndexOf("."), oleFileName.length());
		}
		// 生成的新的文件名
		String newFileName = getFileName(path.toString(),fileNameSuffix);
		
		//update user liuwenbing@xwtec.cn
		File file = new File(path.toString());
		//判断路径是否存在，不存在就新建
		if(!file.isDirectory()){
			file.mkdirs();
		}
		
		multipartFile.transferTo(new File(path + newFileName));
		return newFileName;
	}
	
	public static String upload(MultipartFile multipartFile,String relativePath,String realPath) throws IllegalStateException, IOException {
		// 文件存放的根目录的绝对路径
		StringBuilder path = new StringBuilder(realPath + "uploadfiles");
		if(!CommonUtil.isEmpty(relativePath)) {
			path.append(File.separator + relativePath);
		}
		path.append(File.separator);
		// 上传时的原文件名
		String oleFileName = multipartFile.getOriginalFilename();
		// 上传时的原文件名后缀
		String fileNameSuffix = "";
		if(!CommonUtil.isEmpty(oleFileName) && oleFileName.indexOf(".") >= 0) {
			fileNameSuffix = oleFileName.substring(oleFileName.lastIndexOf("."), oleFileName.length());
		}
		// 生成的新的文件名
		String newFileName = getFileName(path.toString(),fileNameSuffix);
		
		//update user liuwenbing@xwtec.cn
		File file = new File(path.toString());
		//判断路径是否存在，不存在就新建
		if(!file.isDirectory()){
			file.mkdirs();
		}
		
		multipartFile.transferTo(new File(path + newFileName));
		return newFileName;
	}
	
	/**
	 * 方法描述:文件上传，将controller接到的MultipartFile类型文件保存到config.properties文件中KEY值为“uploadFileBasePath”对应的绝对路径下
	 *       文件名为：(时间戳+3位随机数)+原文件后缀名
	 * @param multipartFile controller接到的Spring封装后的文件
	 * @return 生成后的文件名
	 * date:2013-11-21
	 * add by: liu_tao@xwtec.cn
	 */
	public static String upload(MultipartFile multipartFile) throws IllegalStateException, IOException {
		return upload(multipartFile,null);
	}
	
	/**
	 * 方法描述:获取格式为：(时间戳+3位随机数)+文件后缀的文件名，并且当在同目录下有相同文件名的文件存在，重新生成。
	 * @param path 文件所在路径
	 * @param fileNameSuffix 文件后缀名
	 * @return (时间戳+3位随机数)+文件后缀的文件名
	 * date:2013-11-15
	 * add by: liu_tao@xwtec.cn
	 */
	private static String getFileName(String path,String fileNameSuffix) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random random = new Random();
		String fileName = sdf.format(new Date()) + String.format("%03d", random.nextInt(999)) + fileNameSuffix;
		File file = new File(path + fileName);
		// 如果文件已存在，需要重新生成文件名
		if(file.exists()) {
			return getFileName(path,fileNameSuffix);
		}
		return fileName;
	}
	
	/**
	 * 方法描述:按指定的文件全路径(包含文件名)创建一个新的文件，并把内容写入到文件中
	 *        如果这个绝对路径中文件夹与文件不存在将会自动创建，如果文件已经存在将会被覆盖
	 * @param fileFullPath 文件全路径(包含文件名)
	 * @param content 要写入文件的内容
	 * date:2014-4-17
	 * add by: liu_tao@xwtec.cn
	 */
	public static void createNewFile(String fileFullPath,String content) {
		createNewFile(fileFullPath, content, null);
	}
	
	/**
	 * 
	 * 方法描述:删除文件
	 * @param path 文件相对路径  fileName 文件名
	 * date:2014-4-25
	 * add by: fanzhaode@xwtec.cn
	 */
	public static void deleteFile(String relativePath,String fileName)throws Exception{
		// 文件存放的根目录的绝对路径
		StringBuilder path = new StringBuilder(ProUtil.get("uploadFileBasePath"));
		path.append(relativePath+File.separator+fileName);
		File file=new File(path.toString());
			if(file.exists()&&file.isFile()){	
				try{
					file.delete();
				}catch(Exception e){
					logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1],e));
				}
			}
	}
	
	/**
	 * 方法描述:按指定的文件全路径(包含文件名)创建一个新的文件，并把内容写入到文件中
	 *        如果这个绝对路径中文件夹与文件不存在将会自动创建，如果文件已经存在将会被覆盖
	 * @param fileFullPath 文件全路径(包含文件名)
	 * @param content 要写入文件的内容
	 * @param enc 文件编码格式
	 * date:2014-4-17
	 * add by: liu_tao@xwtec.cn
	 */
	public static void createNewFile(String fileFullPath,String content,String enc) {
		File file = new File(fileFullPath);
		if(file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		OutputStreamWriter os=null;
		try {
			 if(enc==null||enc.length()==0){
				 os = new OutputStreamWriter(new FileOutputStream(file));
			 }else{
				 os = new OutputStreamWriter(new FileOutputStream(file),enc);
			 }
			
			os.write(content);
			
			os.close();
		} catch (IOException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
				}
			}
		}
	}
	
	
	/**
	 * txt文件格式导出内容信息
	 * 方法描述:根据编码格式导出信息
	 * @param list 要导出内容  （内部list是每一行数据的集合   外部list是所有行的集合）
	 * @param enc 文件编码格式
	 * date:2014-6-20
	 * add by: fanzhaode@xwtec.cn
	 */
	public static void exportFile(HttpServletResponse response, List<List<String>> list,String enc) {
		PrintWriter out = null;
			try{
				
				//实现下载功能
				response.addHeader("Content-Disposition", "attachment;filename=" + new String("exportFile.txt".getBytes("GBK"),"ISO8859_1"));
				response.setContentType("text/html");
				//编码格式
				response.setCharacterEncoding(enc);
				out = response.getWriter();
				 /**
				  * 循环所有行的集合
				  */
				 for(int i=0;i<list.size();i++){

					 List<String> l1=list.get(i);
					 
					 /**
					  * 每行信息
					  */
					 for(int j=0;j<l1.size();j++){
						 out.write(l1.get(j));
						 out.write("\t");
					 }
					 //换行
					 out.write("\r\n");			 
				 }
				 out.close();
		} catch (IOException e) {
			logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (Exception e) {
					logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
				}
			}
		}
	}
}