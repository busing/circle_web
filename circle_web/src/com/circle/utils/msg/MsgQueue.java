package com.circle.utils.msg;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.circle.pojo.msg.MessageBean;

public class MsgQueue {
	//集团订单队列
	public static Queue<MessageBean> GROUP_QUEUE = new ConcurrentLinkedQueue<MessageBean>();
}
