package com.circle.constant;

public enum CircleTable {
	
	GOODS("t_goods"),GOOD_ARG_VALUE("t_goodarg_value"),GOOD_ATTR_VALUE("t_goodattr_value"),GOOD_IMAGE("t_good_image")
	,GOOD_TYPE("t_good_type"),GOOD_TYPE_ATTR("t_goodtype_attr"),GOOD_TYPE_ARG("t_goodtype_arg"),GOOD_COMMENT("t_good_comment")
	,ORDER("t_order"),SHOPCART("t_shopping_cart"),ORDER_DETAIL("t_order_detail"),CIRCLE("t_circle"),USER("t_user");
	
	private String tableName;
	
	private CircleTable(String tableName)
	{
		this.tableName=tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}
