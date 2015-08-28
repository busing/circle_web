package com.xwtec.xwserver.pojo.common;

/**
 * 调用接口服务时响应对象封装. <br>
 * <p>
 * Copyright: Copyright (c) 2014-4-8 下午2:51:21
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ResponseObj {
	private ResponseBody body;
	private ResponseHeader header;
	public ResponseBody getBody() {
		return body;
	}
	public void setBody(ResponseBody body) {
		this.body = body;
	}
	public ResponseHeader getHeader() {
		return header;
	}
	public void setHeader(ResponseHeader header) {
		this.header = header;
	}
}
