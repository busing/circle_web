package com.xwtec.xwserver.pojo.demo;

import java.util.List;
import java.util.Map;

/**
 * MVC代码层次结构划分演示. <br>
 * <p>
 * Copyright: Copyright (c) 2013-12-6 下午1:44:06
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class MvcDemo {
	
	private String str;
	
	private Map<String,String> map;
	
	private Map<String,MvcSubDemo> objMap;
	
	private List<String> list;
	
	private List<MvcSubDemo> mvcSubDemoList;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, MvcSubDemo> getObjMap() {
		return objMap;
	}

	public void setObjMap(Map<String, MvcSubDemo> objMap) {
		this.objMap = objMap;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<MvcSubDemo> getMvcSubDemoList() {
		return mvcSubDemoList;
	}

	public void setMvcSubDemoList(List<MvcSubDemo> mvcSubDemoList) {
		this.mvcSubDemoList = mvcSubDemoList;
	}
}
