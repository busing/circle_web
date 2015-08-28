package com.circle.pojo.dict;

public class DictBean {
	
	/**
	 * 类型id
	 */
	public int type_id;
	
	/**
	 * 类型名称
	 */
	public String type_name;
	
	/**
	 * 类型code
	 */
	public String type_code;
	
	/**
	 * 状态
	 */
	public int status;
	
	/**
	 * 父id
	 */
	public int parent_id;
	
	/**
	 * 备注
	 */
	public String remark;

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
