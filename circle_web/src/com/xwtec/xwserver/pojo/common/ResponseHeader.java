package com.xwtec.xwserver.pojo.common;

/**
 * 调用接口服务时响应对象头封装. <br>
 * <p>
 * Copyright: Copyright (c) 2014-4-8 下午2:54:12
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ResponseHeader {
	private String resTime;
	private String status;
	private String req_seq;
	private String key;
	public String getResTime() {
		return resTime;
	}
	public void setResTime(String resTime) {
		this.resTime = resTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReq_seq() {
		return req_seq;
	}
	public void setReq_seq(String req_seq) {
		this.req_seq = req_seq;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
