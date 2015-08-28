package com.circle.pojo.batchsell;

import java.text.ParseException;
import java.util.List;

import com.circle.pojo.good.GoodBean;
import com.xwtec.xwserver.util.DateUtils;
import com.xwtec.xwserver.util.StringUtil;


/**
 * com.circle.pojo.batchsell.BatchSell
 * <p> 批次销售
 * @author ytai
 * @version 1.0.0
 * @2015-4-29 上午9:06:54
 */
public class BatchSell
{
	/**
	 * id （int）
	 * <p> 批次id
	 */
	private int id;
	
	/**
	 * batchName （String）
	 * <p> 批次名称
	 */
	private String batchName;
	
	/**
	 * startTime （String）
	 * <p> 开始时间
	 */
	private String startTime;
	
	/**
	 * endTime （String）
	 * <p> 结束时间
	 */
	private String endTime;
	
	/**
	 * shipTime （String）
	 * <p> 发货时间
	 */
	private String shipTime;
	
	/**
	 * status （String）
	 * <p> 状态
	 */
	private String status;
	
	/**
	 * remainderSeconds （String）
	 * <p> 剩余秒数
	 */
	private int remainderSeconds;
	
	/**
	 * goods (List<GoodBean>)
	 * 商品列表
	 * @author zhoujie
	 */
	private List<GoodBean> goods;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getShipTime() {
		return shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRemainderSeconds() {
		long cuurent=System.currentTimeMillis();
		long end=0;
		try
		{
			if(!StringUtil.isEmpty(getEndTime()))
			{
				end=DateUtils.parse(getEndTime(), "yyyy-MM-dd HH:mm:ss").getTime();
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		long remainder=end-cuurent;
		remainderSeconds=Integer.parseInt((remainder<=0?0:remainder/1000)+"");
		return remainderSeconds;
	}

	public void setRemainderSeconds(int remainderSeconds) {
		this.remainderSeconds = remainderSeconds;
	}
	
	public List<GoodBean> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodBean> goods) {
		this.goods = goods;
	}

	public static void main(String[] args) {
		BatchSell bs=new BatchSell();
		bs.setEndTime("2015/04/30 09:41:00");
		System.out.println(bs.getRemainderSeconds());
	}
}