package com.circle.pojo.farm;

/**
 * 农场
 * @author Taiyuan
 *
 */
public class Farm {
	
	private int id;
	
	/**
	 *农场名称 
	 */
	private String farm_name;
	
	/**
	 * 农场位置（地址）
	 */
	private String position;
	
	/**
	 * 简介
	 */
	private String intro;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 状态（0：无效 1:有效）
	 */
	private int status;
	
	/**
	 * 创建用户id
	 */
	private int create_userid;
	
	/**
	 * 创建时间
	 */
	private String create_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFarm_name() {
		return farm_name;
	}

	public void setFarm_name(String farm_name) {
		this.farm_name = farm_name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCreate_userid() {
		return create_userid;
	}

	public void setCreate_userid(int create_userid) {
		this.create_userid = create_userid;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}
