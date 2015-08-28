package com.weixin.pojo;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年4月23日 下午10:08:56
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public class WeixinRegister {
	
	private int id;
	
	private String weixin_id;
	
	private String mobile_phone;
	
	private int step;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeixin_id() {
		return weixin_id;
	}

	public void setWeixin_id(String weixin_id) {
		this.weixin_id = weixin_id;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	
}
