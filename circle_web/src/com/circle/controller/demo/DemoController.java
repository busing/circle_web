package com.circle.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.circle.pojo.msg.MessageBean;
import com.circle.utils.msg.MsgQueue;

/**
 * 
 * 测试类. <br>
 * <p>
 * Copyright: Copyright (c) 2015-1-13 下午02:18:39
 * <p>
 * @version 1.0.0
 */
@Controller
@RequestMapping("demo")
public class DemoController {

	
	@RequestMapping("demomsg.action")
	public void demoMsg(ModelAndView mav){
		MessageBean msg = new MessageBean("1", "内容", "1","1","参数");
		MsgQueue.GROUP_QUEUE.add(msg);
	}
}
