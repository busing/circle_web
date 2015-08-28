package com.circle.pojo.batchsell;

import java.text.ParseException;

import com.xwtec.xwserver.util.DateUtils;
import com.xwtec.xwserver.util.StringUtil;


/**
 * 批次销售
 * @author zhoujie
 * @version 1.0.0
 * @2015-5-16 上午9:06:54
 */
public class BatchSellGood
{
	/**
	 * id (int)
	 * 商品id
	 */
	private int id;
	
	/**
	 * batchId (int)
	 * 批次id
	 */
	private Integer batchId;
	
	/**
	 * goodId (int)
	 * 商品id
	 */
	private Integer goodId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getGoodId() {
		return goodId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
}