package com.xwtec.xwserver.pojo.common;

/**
 * 调用接口服务时响应对象体封装. <br>
 * <p>
 * Copyright: Copyright (c) 2014-4-8 下午2:53:31
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ResponseBody {
	private String resp_desc;
	private String resp_code;
	private ResponseData data;
	private String log;
	public String getResp_desc() {
		return resp_desc;
	}
	public void setResp_desc(String resp_desc) {
		this.resp_desc = resp_desc;
	}
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public ResponseData getData() {
		return data;
	}
	public void setData(ResponseData data) {
		this.data = data;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
}
