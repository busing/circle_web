package com.circle.pojo.good;

/**
 * 商品
 * @author Taiyuan
 *
 */
public class GoodBean {
	
	/**
	 * id
	 */
	private int id;
	
	/**
	 * 商品名字
	 */
	private String good_name;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 单位id
	 */
	private int unit;
	private String unit_str;
	
	/**
	 * 单位描述
	 * unit_intro 
	 */
	private String unit_intro;
	
	/**
	 * 简介
	 */
	private String intro;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 主图片
	 */
	private String image;
	
	/**
	 * 农场id
	 */
	private int farm_id;
	private String farm_str;
	
	/**
	 * 类型id
	 */
	private int type_id;
	private String type_str;
	
	/**
	 * 售价
	 */
	private double sell_price;
	
	/**
	 * 原价
	 * original_price 
	 */
	private double original_price;
	
	/**
	 * 成本价
	 * cost_price 
	 */
	private double cost_price;
	
	/**
	 * 创建时间
	 */
	private String create_time;
	
	/**
	 * 最后更新时间
	 */
	private String last_update_time;
	
	/**
	 * 浏览次数
	 */
	private int browse_num;
	
	/**
	 * 购买次数
	 */
	private int buy_num;
	
	/**
	 * 排序id
	 */
	private int sort_id;
	
	/**
	 * 创建用户id
	 */
	private int create_userid;
	
	/**
	 * 最后更新用户id
	 */
	private int last_upate_userid;
	
	/**
	 * 状态	 0：无效 	1：有效
	 */
	private int status;
	
	/**
	 * 是否上架 	0:下架	1：上架
	 */
	private int is_putaway;
	
	/**
	 * 是否首页推荐 0:否 1:是
	 */
	private int recommend;
	
	/**
	 * 是否选中
	 * @author zhoujie
	 */
	private Boolean isChecked = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getGood_name() {
		return good_name;
	}

	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getFarm_id() {
		return farm_id;
	}

	public void setFarm_id(int farm_id) {
		this.farm_id = farm_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public double getSell_price() {
		return sell_price;
	}

	public void setSell_price(double sell_price) {
		this.sell_price = sell_price;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}

	public int getBrowse_num() {
		return browse_num;
	}

	public void setBrowse_num(int browse_num) {
		this.browse_num = browse_num;
	}

	public int getBuy_num() {
		return buy_num;
	}

	public void setBuy_num(int buy_num) {
		this.buy_num = buy_num;
	}

	public int getSort_id() {
		return sort_id;
	}

	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}

	public int getCreate_userid() {
		return create_userid;
	}

	public void setCreate_userid(int create_userid) {
		this.create_userid = create_userid;
	}

	public int getLast_upate_userid() {
		return last_upate_userid;
	}

	public void setLast_upate_userid(int last_upate_userid) {
		this.last_upate_userid = last_upate_userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIs_putaway() {
		return is_putaway;
	}

	public void setIs_putaway(int is_putaway) {
		this.is_putaway = is_putaway;
	}

	public String getUnit_str() {
		return unit_str;
	}

	public void setUnit_str(String unit_str) {
		this.unit_str = unit_str;
	}

	public String getFarm_str() {
		return farm_str;
	}

	public void setFarm_str(String farm_str) {
		this.farm_str = farm_str;
	}

	public String getType_str() {
		return type_str;
	}

	public void setType_str(String type_str) {
		this.type_str = type_str;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public String getUnit_intro() {
		return unit_intro;
	}

	public void setUnit_intro(String unit_intro) {
		this.unit_intro = unit_intro;
	}

	public double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}

	public double getCost_price() {
		return cost_price;
	}

	public void setCost_price(double cost_price) {
		this.cost_price = cost_price;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
}