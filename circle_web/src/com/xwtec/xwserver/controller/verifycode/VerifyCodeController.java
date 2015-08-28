package com.xwtec.xwserver.controller.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 
 * 获取验证码
 * <p>
 * Copyright: Copyright (c) 2013-11-19 下午05:30:25
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author chenxiang@xwtec.cn
 * @version 1.0.0
 */
@Controller
@RequestMapping("verifycode")
public class VerifyCodeController {
	
	/**
	 * 注入日志
	 */
	Logger logger = Logger.getLogger(VerifyCodeController.class);
	
	/**
	 * 方法描述:获取验证码
	 * @param 
	 * @return 
	 * date:Nov 28, 2013
	 * add by: chenxiang@xwtec.cn
	 */
	@RequestMapping("getverifycode.action")
    public void verifycode(HttpServletRequest request,HttpServletResponse response){
		
		HttpSession session = request.getSession(true);
	    response.setContentType("image/jpeg");
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 240);
	    int width = 60, height = 22;
	    ServletOutputStream out = null;
        try {
	        out = response.getOutputStream();
        }
        catch (IOException ioe) {
        	logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], ioe));
        }
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 设置图片大小的
	    Graphics gra = image.getGraphics();
	
	    Random random = new Random();
	
	    gra.setColor(getRandColor(200, 250)); // 设置背景色
	    gra.fillRect(0, 0, width, height);
	
	    gra.setColor(Color.black); // 设置字体色
	    System.setProperty("java.awt.headless", "true");
	    gra.setFont(new Font("Times New Roman", Font.PLAIN, 18));
	
	    // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	    gra.setColor(getRandColor(160, 200));
	    for (int i = 0; i < 155; i++) {
	    	int x = random.nextInt(width);
	    	int y = random.nextInt(height);
	    	int xl = random.nextInt(12);
	    	int yl = random.nextInt(12);
	    	gra.drawLine(x, y, x + xl, y + yl);
	    }
	
	    // 取随机产生的认证码(4位数字)
	    String sRand = "";
	    for (int i = 0; i < 4; i++) {
	    	char rand = getChar();
	    	sRand += rand;
	    	// 将认证码显示到图象中
	    	gra.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
	    	gra.drawString("" + rand, 13 * i + 6, 16);
	    }
	    //将验证码塞入缓存
	    /**
		 * Modify by liutao
		 * date 2013-11-26
		 * reason:verifyCode、sysUser没有用常量定义
		 */
	    session.setAttribute(ConstantBusiKeys.SessionKey.VERIFY_CODE, sRand);
	    try {
	        ImageIO.write(image,"JPEG",out);
        } catch (IOException e) {
        	logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
        } finally {
        	try {
        		if(out != null) {
        			out.flush();
    				out.close();
        		}
			} catch (IOException e) {
				logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			}
        }
	}
	
	/**
	 * 
	 * 方法描述:获取字符
	 * @param 
	 * @return 
	 * date:2013-11-19
	 * add by: chenxiang@xwtec.cn
	 */
	private char getChar() {
	  char ch = '0';
	  LinkedList<String> ls = new LinkedList<String>();
	  for (int i = 0; i < 10; i++) {// 0-9
	   ls.add(String.valueOf(48 + i));
	  }
	  int index = (int) (Math.random() * ls.size());
	  if (index > (ls.size() - 1)) {
	   index = ls.size() - 1;
	  }
	  ch = (char) Integer.parseInt(String.valueOf(ls.get(index)));
	  return ch;
	}
	
	/**
	 * 
	 * 方法描述:给定范围获得随机颜色
	 * @param 
	 * @return 
	 * date:2013-11-19
	 * add by: chenxiang@xwtec.cn
	 */
	private Color getRandColor(int fc, int bc) { 
	  Random random = new Random();
	  if (fc > 255)
	   fc = 255;
	  if (bc > 255)
	   bc = 255;
	  int r = fc + random.nextInt(bc - fc);
	  int g = fc + random.nextInt(bc - fc);
	  int b = fc + random.nextInt(bc - fc);
	  return new Color(r, g, b);
	 }
	
	
}
