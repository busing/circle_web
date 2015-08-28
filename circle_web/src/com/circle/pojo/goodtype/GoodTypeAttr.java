package com.circle.pojo.goodtype;

import java.util.List;

public class GoodTypeAttr {
	
	private int id;
	
	private String english_name;
	
	private String name;
	
	private String attr_value;
	
	public List<GoodTypeAttrValues> attr_value_list;
	
	private int type_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttr_value() {
		return attr_value;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public List<GoodTypeAttrValues> getAttr_value_list() {
		return attr_value_list;
	}

	public void setAttr_value_list(List<GoodTypeAttrValues> attr_value_list) {
		this.attr_value_list = attr_value_list;
	}
	
	
	

	
	
}
