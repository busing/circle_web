package com.xwtec.xwserver.pojo.tool;

/**
 * 
 * 社保通咨询实体类. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2014-9-19 下午02:44:20
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author fanzhaode@xwtec.cn
 * @version 1.0.0
 */
public class CommonNews {
	/**
	 * 编号
	 */
	private String  nid ;
	/**
	 * 所属应用，如：MYDC（民意调查），HOUSETAX（房屋税收）
	 */
	private String appid;
	/**
	 * 内容分类：应用内部分类的编码，汉语拼音缩写
	 */
	private String cateid;
	/**
	 * 地市编码：地市名称缩写，如南京：NJDQ
	 */
	private String city_code;
	/**
	 * 标题
	 */
	private String ntitle;
	/**
	 * 图片
	 */
	private String nphoto;
	/**
	 * 内容
	 */
	private String ncontent;
	/**
	 * 发布时间
	 */
	private String npostdate;
	/**
	 * 是否删除：1删除，0未删除
	 */
	private String is_del;
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getCateid() {
		return cateid;
	}
	public void setCateid(String cateid) {
		this.cateid = cateid;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String cityCode) {
		city_code = cityCode;
	}
	public String getNtitle() {
		return ntitle;
	}
	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}
	public String getNphoto() {
		return nphoto;
	}
	public void setNphoto(String nphoto) {
		this.nphoto = nphoto;
	}
	public String getNcontent() {
		return ncontent;
	}
	public void setNcontent(String ncontent) {
		this.ncontent = ncontent;
	}
	public String getNpostdate() {
		return npostdate;
	}
	public void setNpostdate(String npostdate) {
		this.npostdate = npostdate;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String isDel) {
		is_del = isDel;
	}
	
}
