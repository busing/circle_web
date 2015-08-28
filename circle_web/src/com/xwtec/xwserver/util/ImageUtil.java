package com.xwtec.xwserver.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import com.xwtec.xwserver.pojo.util.ImagePojo;

/**
 *	图片工具类
 * <p>
 * Copyright: Copyright (c) 2013-11-26 下午12:30:05
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author chenxiang@xwtec.cn
 * @version 1.0.0
 */
public class ImageUtil {
	
	
	/**
	 * 
	 * 方法描述:压缩图片方法(<b>最精细的设置</b>)
	 * @param oldFile 将要压缩的图片路径
	 * @param width   压缩宽(px)
	 * @param height  压缩高(px)
	 * @param quality 压缩清晰度：建议为1.0,最清晰
	 * @param smallIcon 压缩图片后,添加的扩展名（在图片后缀名前添加,如原名：temp.jpg,压缩后为：temp_small.jpg）
	 * 					如果需要覆盖原图，则此参数置为空字符串："".
	 * @param percentage 是否等比压缩 若true宽高比率将将自动调整
	 * @return String 压缩后的图片文件名，如果为null则参数可能有误
	 * @throws Exception 
	 * @date:2013-11-26
	 * @add by: chenxiang@xwtec.cn
	 */
	public static String doCompress(String oldFile, int width, int height, float quality, String smallIcon, boolean percentage) throws Exception {
		   if (oldFile != null && width > 0 && height > 0) {
			     Image srcFile=null;
			     String newImage = null;
			     File file = new File(oldFile);
			     // 文件不存在
			     if (!file.exists()) {
			    	 return null;
			     }
			     /*读取图片信息*/
			     srcFile = ImageIO.read(file);
			     int new_w = width;
			     int new_h = height;
			     if (percentage) {
			    	 // 为等比缩放计算输出的图片宽度及高度
			    	 double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
			    	 double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
			    	 double rate = rate1 > rate2 ? rate1 : rate2;
			    	 new_w = (int) (((double) srcFile.getWidth(null)) / rate);
			    	 new_h = (int) (((double) srcFile.getHeight(null)) / rate);
			     }
			     /* 宽高设定*/
			     BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			     tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
			
			     /*压缩后的文件名 */
			     String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
			     newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
			
			     /*压缩之后临时存放位置*/
			     String formatName = newImage.substring(newImage.lastIndexOf(".") + 1); 
			     ImageIO.write(tag, /*"GIF"*/ formatName /* format desired */ , new File(newImage) /* target */ );  
//			     FileOutputStream out = new FileOutputStream(newImage);
//			     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			     JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			
			     /* 压缩质量 */
//			     jep.setQuality(quality, true);
//			     encoder.encode(tag, jep);
//			     out.close();
		    	srcFile.flush();
			    return newImage;
		   } else {
		    return null;
		   }
		}

	/**
	 * 
	 * 方法描述:压缩图片方法(缺省参数设置：<b>默认清晰度为0.5;不保留原图;不等比压缩</b>)
	 * @param oldFile 将要压缩的图片路径
	 * @param width   压缩宽(px)
	 * @param height  压缩高(px)
	 * @return String 压缩后的图片文件名，如果为null则参数可能有误
	 * @throws Exception 
	 * @date:2013-11-26
	 * @add by: chenxiang@xwtec.cn
	 */
	public static String doCompress(String oldFile, int width, int height) throws Exception{
		return doCompress(oldFile,width,height,(float) 0.5,"",false);
	}
	
	/**
	 * 
	 * 方法描述:压缩图片方法(缺省参数设置：<b>默认清晰度为0.5;不保留原图;等比压缩</b>)
	 * @param width   压缩宽(px)
	 * @param height  压缩高(px)
	 * @param oldFile 将要压缩的图片路径
	 * @return String 压缩后的图片文件名，如果为null则参数可能有误
	 * @throws Exception 
	 * @date:2013-11-26
	 * @add by: chenxiang@xwtec.cn
	 */
	public static String doCompress( int width, int height,String oldFile) throws Exception{
		return doCompress(oldFile,width,height,(float) 0.5,"",true);
	}
	
	/**
	 * 方法描述:获取图片的高和宽
	 * @param pathFile 图片路径
	 * @return ImagePojo 图片对象
	 * date:2013-12-24
	 * add by: liuwenbing@xwtec.cn
	 */
	public static ImagePojo getImagePojo(String pathFile) throws IOException{
		ImagePojo imagePojo = new ImagePojo();
	    File file = new File(pathFile);
	    /*读取图片信息*/
	    Image srcFile = ImageIO.read(file);
	    imagePojo.setHeight(srcFile.getHeight(null));//图片的高
	    imagePojo.setWidth(srcFile.getWidth(null));//图片的宽
	    return imagePojo;
	}
	
	/**
	 * 方法描述:为指定图片打上水印
	 * @param filePath	需要打上水印的图片路径
	 * @param destPath	打上水印后的图片路径（如果需要覆盖原图，则还写原路径）
	 * @param markContent	水印内容
	 * @param left	水印距离原始图片左侧距离
	 * @param top	水印距离原始图片上方距离
	 * @param r	水印文字RGB颜色中的R值
	 * @param g	水印文字RGB颜色中的G值
	 * @param b	水印文字RGB颜色中的B值
	 * @param fontSize 水印文字的大小
	 * @return 是否创建水印成功
	 * date:2014-7-31
	 * add by: chenxiang@xwtec.cn
	 */
	public static boolean createMark(String filePath,String destPath, String markContent,int left,int top, 
		    int r,int g,int b, int fontSize) throws Exception { 
		   Color markContentColor = new Color(r,g,b);
		   ImageIcon imgIcon = new ImageIcon(filePath); 
		   Image theImg = imgIcon.getImage(); 
		   int width = theImg.getWidth(null); 
		   int height = theImg.getHeight(null); 
		   
		   BufferedImage bimage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); 
		   Graphics2D graphics2D = bimage.createGraphics(); 
		   graphics2D.setColor(markContentColor); 
		   graphics2D.setBackground(Color.white); 
		   graphics2D.drawImage(theImg, 0, 0, null); 
		   AttributedString ats = new AttributedString(markContent);
		   Font f = new Font("微软雅黑", 0, fontSize); 
		   ats.addAttribute(TextAttribute.FONT, f, 0, markContent.length()); 
		   AttributedCharacterIterator iter = ats.getIterator(); 
		   graphics2D.drawString(iter, left, top);  
		   graphics2D.dispose();//画笔结束 
		   try { 
			   
		    String formatName = destPath.substring(destPath.lastIndexOf(".") + 1); 
		    ImageIO.write(bimage, /*"GIF"*/ formatName /* format desired */ , new File(destPath) /* target */ );  
			     
		    //输出 文件 到指定的路径 
//		    FileOutputStream out = new FileOutputStream(destPath); 
//		    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
//		    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage); 
//		    param.setQuality(1 , true); 
//		    encoder.encode(bimage, param); 
//		    out.close(); 
		   } catch (Exception e) { 
			   return false; 
		   } 
		   return true; 
		}
	
	
//	/**
//	 * 测试
//	 */
//	public static void main(String[] args) {
//		try {
//			ImageUtil.createMark("C:\\Users\\Administrator\\Desktop\\2.jpg","C:\\Users\\Administrator\\Desktop\\22.jpg","☆ 盗 图 可 耻 ☆",400,300,0,0,0,15);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
	
	
	
	
}
